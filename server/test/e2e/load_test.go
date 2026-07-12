package e2e

import (
	"bufio"
	"encoding/binary"
	"fmt"
	"io"
	"net"
	"sync"
	"testing"
	"time"

	"github.com/dofusarena/go-server/internal/app"
	"github.com/dofusarena/go-server/internal/protocol"
	"github.com/dofusarena/go-server/internal/service"
)

// rawClient is a goroutine-safe (no *testing.T coupling), error-returning
// counterpart to testClient (client_test.go), for use inside worker
// goroutines in the concurrent load test below where calling t.Fatal from
// a non-test goroutine would be unsafe.
type rawClient struct {
	conn net.Conn
	r    *bufio.Reader
}

func dialRawClient(addr string) (*rawClient, error) {
	conn, err := net.DialTimeout("tcp", addr, 5*time.Second)
	if err != nil {
		return nil, err
	}
	_ = conn.SetDeadline(time.Now().Add(15 * time.Second))
	return &rawClient{conn: conn, r: bufio.NewReader(conn)}, nil
}

func (c *rawClient) Close() error { return c.conn.Close() }

func rawSend(c *rawClient, archTarget byte, opcode protocol.RecvOpcode, payload []byte) error {
	totalSize := uint16(protocol.InboundHeaderSize + len(payload))
	header := make([]byte, protocol.InboundHeaderSize)
	binary.BigEndian.PutUint16(header[0:2], totalSize)
	header[2] = archTarget
	binary.BigEndian.PutUint16(header[3:5], uint16(opcode))
	if _, err := c.conn.Write(header); err != nil {
		return fmt.Errorf("send header: %w", err)
	}
	if len(payload) > 0 {
		if _, err := c.conn.Write(payload); err != nil {
			return fmt.Errorf("send payload: %w", err)
		}
	}
	return nil
}

func rawRecvFrame(c *rawClient) (protocol.SendOpcode, []byte, error) {
	header := make([]byte, protocol.OutboundHeaderSize)
	if _, err := io.ReadFull(c.r, header); err != nil {
		return 0, nil, err
	}
	totalSize := binary.BigEndian.Uint16(header[0:2])
	opcode := protocol.SendOpcode(binary.BigEndian.Uint16(header[2:4]))
	payloadLen := int(totalSize) - protocol.OutboundHeaderSize
	payload := make([]byte, payloadLen)
	if payloadLen > 0 {
		if _, err := io.ReadFull(c.r, payload); err != nil {
			return 0, nil, err
		}
	}
	return opcode, payload, nil
}

// rawExpect reads frames until it sees the wanted opcode, tolerating
// (skipping) interleaved ACTOR_SPAWN/ACTOR_DESPAWN broadcast noise --
// unavoidable in this load test since many coaches join/leave
// concurrently and those broadcasts fan out to every online connection,
// arriving at unpredictable points relative to a given fight's own
// opcode sequence.
func rawExpect(c *rawClient, want protocol.SendOpcode) ([]byte, error) {
	const maxSkip = 32
	for i := 0; i < maxSkip; i++ {
		opcode, payload, err := rawRecvFrame(c)
		if err != nil {
			return nil, fmt.Errorf("recvFrame (expected %d): %w", want, err)
		}
		if opcode == want {
			return payload, nil
		}
		// Tolerate interleaved broadcast noise: online-presence spawns/
		// despawns from other concurrent fights, plus the fight-end
		// PLAYER_STATISTICS_REPORT (2400) pushed just before END_FIGHT.
		if opcode != protocol.SendActorSpawn && opcode != protocol.SendActorDespawn &&
			opcode != protocol.SendPlayerStatisticsReport {
			return nil, fmt.Errorf("got opcode %d, want %d", opcode, want)
		}
	}
	return nil, fmt.Errorf("rawExpect(%d): too many broadcast frames skipped", want)
}

func rawDrainUntil(c *rawClient, want protocol.SendOpcode, max int) error {
	seen := 0
	for seen < max {
		opcode, _, err := rawRecvFrame(c)
		if err != nil {
			return fmt.Errorf("drainUntil(%d): %w", want, err)
		}
		if opcode == want {
			return nil
		}
		// Interleaved online-presence broadcast noise from other
		// concurrently-running fights/logins in this load test doesn't
		// count against the frame budget, same tolerance as rawExpect
		// (including the pre-END_FIGHT PLAYER_STATISTICS_REPORT push).
		if opcode != protocol.SendActorSpawn && opcode != protocol.SendActorDespawn &&
			opcode != protocol.SendPlayerStatisticsReport {
			seen++
		}
	}
	return fmt.Errorf("drainUntil(%d): opcode not seen within %d frames", want, max)
}

func rawLogin(c *rawClient, login, password, coachName string) error {
	payload := append(pstring(login), pstring(password)...)
	if err := rawSend(c, 1, protocol.RecvAuthentication, payload); err != nil {
		return err
	}
	result, err := rawExpect(c, protocol.SendAuthenticationResult)
	if err != nil {
		return err
	}
	if result[0] != 0 {
		return fmt.Errorf("authenticate(%s) result code = %d, want 0", login, result[0])
	}

	if _, err := rawExpect(c, protocol.SendQueueNotification); err != nil {
		return err
	}

	opcode, _, err := rawRecvFrame(c)
	if err != nil {
		return fmt.Errorf("recvFrame after auth: %w", err)
	}
	if opcode == protocol.SendCoachCreationRequest {
		creation := append(pstring(coachName), []byte{0, 0, 0}...)
		if err := rawSend(c, 2, protocol.RecvCoachCreation, creation); err != nil {
			return err
		}
		result, err := rawExpect(c, protocol.SendCoachCreationResult)
		if err != nil {
			return err
		}
		if result[0] != 0 {
			return fmt.Errorf("coach creation result code = %d, want 0", result[0])
		}
		if _, err := rawExpect(c, protocol.SendCoachInformation); err != nil {
			return err
		}
	} else if opcode != protocol.SendCoachInformation {
		return fmt.Errorf("unexpected opcode after auth: %d", opcode)
	}

	// FRIEND_LIST, IGNORE_LIST, PLAYER_STATISTICS_REPORT precede
	// ENTER_WORLD_INSTANCE in the post-login sequence (see
	// client_test.go's mustLogin) -- drain past them along with any
	// interleaved presence-broadcast noise.
	if err := rawDrainUntil(c, protocol.SendEnterWorldInstance, 10); err != nil {
		return err
	}

	return nil
}

func rawCreateFighter(c *rawClient, name string) (int64, error) {
	payload := append([]byte{0, 0}, 1)
	payload = append(payload, putInt16(100)...)
	payload = append(payload, 1)
	payload = append(payload, pstring(name)...)
	payload = append(payload, 0, 0)
	payload = append(payload, putInt16(0)...)
	payload = append(payload, putInt16(0)...)

	if err := rawSend(c, 3, protocol.RecvFighterCreateRequest, payload); err != nil {
		return 0, err
	}
	result, err := rawExpect(c, protocol.SendFighterCreateResult)
	if err != nil {
		return 0, err
	}
	r := newPayloadReader(result)
	errCode := r.byte_()
	fighterID := r.int64()
	if errCode != 0 {
		return 0, fmt.Errorf("fighter create error code = %d", errCode)
	}
	return fighterID, nil
}

func seedAccountRaw(a *app.App, login, password string) error {
	hash, err := service.HashPassword(password)
	if err != nil {
		return err
	}
	return a.DB.Exec("INSERT INTO accounts (name, password_hash, connected, is_admin) VALUES (?, ?, false, false)", login, hash).Error
}

// TestE2E_ConcurrentFightsLoad drives N independent fights to completion
// concurrently against one server instance, per
// docs/08-java-parity-roadmap.md Phase I's "N concurrent connections + M
// concurrent fights" load-test item. Kept modest (not a full stress/bench
// suite) since this is a correctness/regression check run as part of the
// normal test suite, not a dedicated perf benchmark.
func TestE2E_ConcurrentFightsLoad(t *testing.T) {
	if testing.Short() {
		t.Skip("skipping load test in -short mode")
	}

	const numFights = 8
	a, addr := startTestServer(t)

	var wg sync.WaitGroup
	errCh := make(chan error, numFights)

	for i := 0; i < numFights; i++ {
		wg.Add(1)
		go func(i int) {
			defer wg.Done()
			if err := runOneFightToForfeit(a, addr, i); err != nil {
				errCh <- fmt.Errorf("fight %d: %w", i, err)
			}
		}(i)
	}

	wg.Wait()
	close(errCh)
	for err := range errCh {
		t.Error(err)
	}
}

// runOneFightToForfeit runs one complete fight (login -> matchmaking ->
// fight setup -> presentation/placement/observation clocks -> forfeit ->
// END_FIGHT) for a uniquely-named pair of accounts, returning any error
// encountered (rather than calling t.Fatal/t.Helper, since this runs
// inside a worker goroutine where those aren't safe to call for a *T from
// a different goroutine than the one running the subtest).
func runOneFightToForfeit(a *app.App, addr string, idx int) (err error) {
	defer func() {
		if r := recover(); r != nil {
			err = fmt.Errorf("panic: %v", r)
		}
	}()

	loginA := fmt.Sprintf("loadalice%d", idx)
	loginB := fmt.Sprintf("loadbob%d", idx)
	seedAccountRaw(a, loginA, "pw")
	seedAccountRaw(a, loginB, "pw")

	cAlice, err := dialRawClient(addr)
	if err != nil {
		return fmt.Errorf("dial alice: %w", err)
	}
	defer cAlice.Close()
	cBob, err := dialRawClient(addr)
	if err != nil {
		return fmt.Errorf("dial bob: %w", err)
	}
	defer cBob.Close()

	if err := rawLogin(cAlice, loginA, "pw", "LoadAlice"+fmt.Sprint(idx)); err != nil {
		return fmt.Errorf("alice login: %w", err)
	}
	if err := rawLogin(cBob, loginB, "pw", "LoadBob"+fmt.Sprint(idx)); err != nil {
		return fmt.Errorf("bob login: %w", err)
	}

	aliceFighterID, err := rawCreateFighter(cAlice, "AF"+fmt.Sprint(idx))
	if err != nil {
		return fmt.Errorf("alice create fighter: %w", err)
	}
	bobFighterID, err := rawCreateFighter(cBob, "BF"+fmt.Sprint(idx))
	if err != nil {
		return fmt.Errorf("bob create fighter: %w", err)
	}

	searchPayload := append([]byte{1}, putInt32(0)...)
	if err := rawSend(cAlice, 2, protocol.RecvOpponentSearchRequest, searchPayload); err != nil {
		return err
	}
	if _, err := rawExpect(cAlice, protocol.SendOpponentSearchInProgress); err != nil {
		return err
	}
	if err := rawSend(cBob, 2, protocol.RecvOpponentSearchRequest, searchPayload); err != nil {
		return err
	}
	if _, err := rawExpect(cBob, protocol.SendOpponentSearchInProgress); err != nil {
		return err
	}

	foundAlice, err := rawExpect(cAlice, protocol.SendOpponentFound)
	if err != nil {
		return err
	}
	if _, err := rawExpect(cBob, protocol.SendOpponentFound); err != nil {
		return err
	}
	duelID := newPayloadReader(foundAlice).int64()

	readyA := append(putInt64(duelID), 1)
	readyA = append(readyA, putInt64(aliceFighterID)...)
	if err := rawSend(cAlice, 2, protocol.RecvSetReadyForFight, readyA); err != nil {
		return err
	}
	if _, err := rawExpect(cAlice, protocol.SendReadyForFight); err != nil {
		return err
	}

	readyB := append(putInt64(duelID), 1)
	readyB = append(readyB, putInt64(bobFighterID)...)
	if err := rawSend(cBob, 2, protocol.RecvSetReadyForFight, readyB); err != nil {
		return err
	}
	if _, err := rawExpect(cBob, protocol.SendReadyForFight); err != nil {
		return err
	}

	if _, err := rawExpect(cAlice, protocol.SendCreateFight); err != nil {
		return err
	}
	if _, err := rawExpect(cBob, protocol.SendCreateFight); err != nil {
		return err
	}

	// After CREATE_FIGHT the server immediately teleports + spawns + starts
	// presentation (§8.22 -- no separate teleport gate). We don't need to
	// send any 8011 (presentation ends on its own clock, or we forfeit).
	if _, err := rawExpect(cAlice, protocol.SendEnterWorldInstance); err != nil {
		return err
	}
	if _, err := rawExpect(cAlice, protocol.SendActorAppear); err != nil {
		return err
	}
	if _, err := rawExpect(cAlice, protocol.SendStartPresentation); err != nil {
		return err
	}
	if _, err := rawExpect(cBob, protocol.SendEnterWorldInstance); err != nil {
		return err
	}
	if _, err := rawExpect(cBob, protocol.SendActorAppear); err != nil {
		return err
	}
	if _, err := rawExpect(cBob, protocol.SendStartPresentation); err != nil {
		return err
	}

	// Forfeit immediately once presentation phase has begun -- don't wait
	// for the full clock chain in the load test, since the point is
	// concurrency/stability, not exercising every phase transition again
	// (already covered by TestE2E_FightLifecycleReachesActionPhase).
	if err := rawSend(cAlice, 3, protocol.RecvGiveUpFightRequest, nil); err != nil {
		return err
	}
	if _, err := rawExpect(cAlice, protocol.SendEndFight); err != nil {
		return err
	}
	if _, err := rawExpect(cBob, protocol.SendEndFight); err != nil {
		return err
	}

	if err := rawSend(cAlice, 3, protocol.RecvEndFightDone, nil); err != nil {
		return err
	}
	if err := rawSend(cBob, 3, protocol.RecvEndFightDone, nil); err != nil {
		return err
	}

	return nil
}
