// Package botclient is a minimal, reusable raw-wire client for the
// DofusArena binary protocol. It exists so multiple tools -- cmd/loadtest
// (throughput benchmarking against an in-process server) and cmd/botswarm
// (a large, behavior-rich swarm against a LIVE external server) -- can
// share one implementation of framing, login, and per-opcode payload
// helpers instead of each duplicating the raw socket handling that
// previously lived only inside cmd/loadtest/main.go.
//
// The client is deliberately dumb: it speaks the exact wire format
// (internal/protocol) and knows the login/coach-creation handshake, but it
// makes no gameplay decisions. Higher layers (a swarm bot, a fight AI)
// build on top of Client's Send/Recv primitives.
//
// # Read pump
//
// Unlike the old loadtest helper (which read synchronously, one expected
// frame at a time), Client runs a background goroutine that continuously
// reads frames off the socket into a buffered channel. This matters at
// swarm scale: the server's outbound queue is only 256 frames deep and
// *drops* frames when full (internal/netio/session.go), and every login/
// logout/move/chat fans out a broadcast to EVERY online coach (an O(N^2)
// firehose at 1000+ bots). A bot that stops draining its socket would
// silently lose frames it is waiting for and stall. The read pump keeps
// the socket drained at all times; callers consume frames via Recv/Expect,
// and unmatched broadcast noise is skipped without ever blocking the peer.
package botclient

import (
	"bufio"
	"encoding/binary"
	"errors"
	"fmt"
	"io"
	"net"
	"sync"
	"time"

	"github.com/dofusarena/go-server/internal/protocol"
)

// Frame is one decoded server->client message handed to callers by the
// read pump.
type Frame struct {
	Opcode  protocol.SendOpcode
	Payload []byte
}

// Client is a single simulated connection to the server.
type Client struct {
	conn net.Conn
	w    *bufio.Writer

	frames chan Frame
	// readErr is set once by the read pump when the socket dies, then
	// frames is closed. Guarded by readErrMu for the happens-before with
	// the channel close.
	readErrMu sync.Mutex
	readErr   error

	closeOnce sync.Once

	// writeMu serializes concurrent Send calls (a bot may send from a
	// behavior goroutine while another path reacts to a frame).
	writeMu sync.Mutex
	// writeTimeout bounds each Send's socket write. Set once by Dial before
	// the read pump starts and before any Send can occur, so it needs no
	// synchronization.
	writeTimeout time.Duration
}

// Dial connects to addr and starts the background read pump. writeTimeout,
// if > 0, bounds each individual write; readIdleTimeout, if > 0, is the
// maximum time the read pump will wait for the next frame before treating
// the connection as dead (0 = block forever, which is valid since the
// server has no keepalive requirement).
func Dial(addr string, dialTimeout, readIdleTimeout, writeTimeout time.Duration) (*Client, error) {
	conn, err := net.DialTimeout("tcp", addr, dialTimeout)
	if err != nil {
		return nil, err
	}
	if tcp, ok := conn.(*net.TCPConn); ok {
		// Disable Nagle: bots send many tiny frames (a single 8-byte
		// end-turn, a move) and latency-per-frame is what this tool
		// measures; batching them behind Nagle would distort the picture.
		_ = tcp.SetNoDelay(true)
	}
	c := &Client{
		conn:         conn,
		w:            bufio.NewWriter(conn),
		frames:       make(chan Frame, 512),
		writeTimeout: writeTimeout,
	}
	go c.readPump(readIdleTimeout)
	return c, nil
}

// readPump reads frames until the socket errors, then records the error and
// closes the frames channel so consumers observe EOF.
func (c *Client) readPump(readIdleTimeout time.Duration) {
	r := bufio.NewReader(c.conn)
	header := make([]byte, protocol.OutboundHeaderSize)
	for {
		if readIdleTimeout > 0 {
			_ = c.conn.SetReadDeadline(time.Now().Add(readIdleTimeout))
		}
		if _, err := io.ReadFull(r, header); err != nil {
			c.setReadErr(err)
			close(c.frames)
			return
		}
		totalSize := binary.BigEndian.Uint16(header[0:2])
		opcode := protocol.SendOpcode(binary.BigEndian.Uint16(header[2:4]))
		payloadLen := int(totalSize) - protocol.OutboundHeaderSize
		if payloadLen < 0 || int(totalSize) > protocol.MaxFrameSize {
			c.setReadErr(fmt.Errorf("botclient: bad frame size %d (opcode %d)", totalSize, opcode))
			close(c.frames)
			return
		}
		var payload []byte
		if payloadLen > 0 {
			payload = make([]byte, payloadLen)
			if _, err := io.ReadFull(r, payload); err != nil {
				c.setReadErr(err)
				close(c.frames)
				return
			}
		}
		c.frames <- Frame{Opcode: opcode, Payload: payload}
	}
}

func (c *Client) setReadErr(err error) {
	c.readErrMu.Lock()
	if c.readErr == nil {
		c.readErr = err
	}
	c.readErrMu.Unlock()
}

// ReadErr returns the error that terminated the read pump, if any.
func (c *Client) ReadErr() error {
	c.readErrMu.Lock()
	defer c.readErrMu.Unlock()
	return c.readErr
}

// Close closes the underlying connection. The read pump observes the
// resulting error and closes the frames channel. Safe to call multiple
// times and concurrently.
func (c *Client) Close() error {
	var err error
	c.closeOnce.Do(func() {
		err = c.conn.Close()
	})
	return err
}

// Send writes a client->server frame. archTarget is the legacy routing byte
// (ignored by this monolith server; pass any of the values the real client
// uses for fidelity). payload may be nil for empty-body opcodes.
func (c *Client) Send(archTarget byte, opcode protocol.RecvOpcode, payload []byte) error {
	c.writeMu.Lock()
	defer c.writeMu.Unlock()

	totalSize := protocol.InboundHeaderSize + len(payload)
	if totalSize > protocol.MaxFrameSize {
		return protocol.ErrFrameTooLarge
	}
	header := make([]byte, protocol.InboundHeaderSize)
	binary.BigEndian.PutUint16(header[0:2], uint16(totalSize))
	header[2] = archTarget
	binary.BigEndian.PutUint16(header[3:5], uint16(opcode))

	if c.writeTimeout > 0 {
		_ = c.conn.SetWriteDeadline(time.Now().Add(c.writeTimeout))
	}
	if _, err := c.w.Write(header); err != nil {
		return fmt.Errorf("botclient: send header (opcode %d): %w", opcode, err)
	}
	if len(payload) > 0 {
		if _, err := c.w.Write(payload); err != nil {
			return fmt.Errorf("botclient: send payload (opcode %d): %w", opcode, err)
		}
	}
	if err := c.w.Flush(); err != nil {
		return fmt.Errorf("botclient: flush (opcode %d): %w", opcode, err)
	}
	return nil
}

// ErrClosed is returned by Recv/Expect when the read pump has ended (socket
// closed or errored). Callers should treat it as a terminal connection
// error; the underlying cause is available via ReadErr.
var ErrClosed = errors.New("botclient: connection closed")

// Recv returns the next frame from the read pump, or ErrClosed if the
// connection has ended. timeout, if > 0, bounds the wait.
func (c *Client) Recv(timeout time.Duration) (Frame, error) {
	if timeout <= 0 {
		f, ok := <-c.frames
		if !ok {
			return Frame{}, c.closedErr()
		}
		return f, nil
	}
	timer := time.NewTimer(timeout)
	defer timer.Stop()
	select {
	case f, ok := <-c.frames:
		if !ok {
			return Frame{}, c.closedErr()
		}
		return f, nil
	case <-timer.C:
		return Frame{}, fmt.Errorf("botclient: recv timeout after %s", timeout)
	}
}

// Drain discards all frames currently buffered in the read pump without
// blocking, returning how many were dropped. Call this between independent
// request/response exchanges (e.g. when a bot switches from one behavior to
// another) so leftover broadcast noise or trailing frames from the previous
// exchange don't get mistaken for the next exchange's reply by Expect.
func (c *Client) Drain() int {
	n := 0
	for {
		select {
		case _, ok := <-c.frames:
			if !ok {
				return n
			}
			n++
		default:
			return n
		}
	}
}

func (c *Client) closedErr() error {
	if e := c.ReadErr(); e != nil && !errors.Is(e, io.EOF) {
		return fmt.Errorf("%w: %v", ErrClosed, e)
	}
	return ErrClosed
}
