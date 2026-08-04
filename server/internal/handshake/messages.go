// Package handshake decodes the connection/login messages the client sends
// right after connecting, and encodes the server's replies.
package handshake

import (
	"fmt"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// ClientVersion is opcode 7: the first message the client sends.
// Payload: [u8 marker=0x02][u16 version][u8 len][ascii build string].
type ClientVersion struct {
	Marker  uint8
	Version uint16 // client sends 70 for 2.70
	Build   string // build version string, e.g. "2.70"
}

// DecodeClientVersion parses an opcode-7 payload.
func DecodeClientVersion(payload []byte) (*ClientVersion, error) {
	r := protocol.NewReader(payload)
	marker, err := r.U8()
	if err != nil {
		return nil, fmt.Errorf("clientVersion marker: %w", err)
	}
	version, err := r.U16()
	if err != nil {
		return nil, fmt.Errorf("clientVersion version: %w", err)
	}
	build, err := r.StringU8()
	if err != nil {
		return nil, fmt.Errorf("clientVersion build: %w", err)
	}
	return &ClientVersion{Marker: marker, Version: version, Build: build}, nil
}

// Accepted reports whether this client version is one the server serves.
func (v *ClientVersion) Accepted() bool {
	return v.Version == protocol.VersionMinor // 70
}

// ClientAuthentication is opcode 1025: credentials, sent in PLAINTEXT in 2.70.
// Payload: [u8 loginLen][login][u8 passLen][password].
type ClientAuthentication struct {
	Login    string
	Password string
}

// DecodeClientAuthentication parses an opcode-1025 payload.
func DecodeClientAuthentication(payload []byte) (*ClientAuthentication, error) {
	r := protocol.NewReader(payload)
	login, err := r.StringU8()
	if err != nil {
		return nil, fmt.Errorf("auth login: %w", err)
	}
	password, err := r.StringU8()
	if err != nil {
		return nil, fmt.Errorf("auth password: %w", err)
	}
	return &ClientAuthentication{Login: login, Password: password}, nil
}

// EncodeAuthResult builds an opcode-1024 S2C frame with a single result byte.
// Use protocol.AuthOK (0) for success.
func EncodeAuthResult(code uint8) ([]byte, error) {
	payload := protocol.NewWriter().U8(code).Bytes()
	return protocol.EncodeS2C(protocol.OpClientAuthResult, payload)
}

// EncodeWorldUnavailable builds an opcode-1026 S2C frame (empty payload).
func EncodeWorldUnavailable() ([]byte, error) {
	return protocol.EncodeS2C(protocol.OpWorldServerUnavailable, nil)
}

// EncodeInvalidClientVersion builds an opcode-8 S2C frame carrying the server's
// EXPECTED version as [u8 major][u16 minor] (the client's oq_1/kS.j formats
// 02 00 46 as "2.70"). On receipt the client shows the modal
// "logon.invalidClientVersion" popup and disconnects itself, so the server need
// only send this — it does not have to force-close the socket.
func EncodeInvalidClientVersion(major uint8, minor uint16) ([]byte, error) {
	payload := protocol.NewWriter().U8(major).U16(minor).Bytes()
	return protocol.EncodeS2C(protocol.OpInvalidClientVersion, payload)
}
