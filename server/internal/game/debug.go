package game

import (
	"encoding/hex"
	"fmt"
	"net/http"
	"strconv"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// StartDebugInject starts a DEV-ONLY loopback HTTP endpoint for live packet
// injection, so wire layouts can be iterated against the retail client WITHOUT
// rebuilding the server. It does nothing when addr is empty (production default)
// and must only ever bind to loopback.
//
// Payloads are hex (big-endian, the message body only — the framing is added for
// you on /s2c and /c2s). Routes (all GET):
//
//	/s2c?opcode=8000&hex=00ab..   frame [u16 len][u16 opcode][payload] -> every live client
//	/raw?hex=..                   send the given bytes as a finished frame, verbatim
//	/c2s?opcode=26330&hex=..&arch=2[&coach=2]  run through the real router as if
//	                                           that client sent it (coach= targets one)
//	/fight                        snapshot of active fights (phase, turn, fighters, states)
//	/script?cmds=goto 177;cast..  run a fight scenario on the actor (see debug_script.go)
//	/sessions                     list the connected coaches
//
// Example (from a shell):
//
//	curl "http://127.0.0.1:5599/s2c?opcode=8022&hex=<i64 id><i32 x><i32 y><i16 z>"
func (s *Server) StartDebugInject(addr string) {
	if addr == "" {
		return
	}
	mux := http.NewServeMux()

	mux.HandleFunc("/s2c", func(w http.ResponseWriter, r *http.Request) {
		op, payload, err := opcodeAndHex(r)
		if err != nil {
			http.Error(w, err.Error(), http.StatusBadRequest)
			return
		}
		frame, err := protocol.EncodeS2C(uint16(op), payload)
		if err != nil {
			http.Error(w, err.Error(), http.StatusBadRequest)
			return
		}
		// ?coach=<id> targets one client; without it, every connected client.
		wantCoach, _ := strconv.Atoi(r.URL.Query().Get("coach"))
		n := 0
		s.deps.Sessions.Each(func(sess *Session) {
			if wantCoach != 0 && (sess.Coach == nil || int(sess.Coach.ID) != wantCoach) {
				return
			}
			n++
			_ = sess.Send(frame)
		})
		fmt.Fprintf(w, "s2c opcode=%d payload=%dB frame=%dB clients=%d\n", op, len(payload), len(frame), n)
	})

	mux.HandleFunc("/raw", func(w http.ResponseWriter, r *http.Request) {
		frame, err := hex.DecodeString(r.URL.Query().Get("hex"))
		if err != nil {
			http.Error(w, "bad hex: "+err.Error(), http.StatusBadRequest)
			return
		}
		// ?coach=<id> targets one client; without it, every connected client.
		wantCoach, _ := strconv.Atoi(r.URL.Query().Get("coach"))
		n := 0
		s.deps.Sessions.Each(func(sess *Session) {
			if wantCoach != 0 && (sess.Coach == nil || int(sess.Coach.ID) != wantCoach) {
				return
			}
			n++
			_ = sess.Send(frame)
		})
		fmt.Fprintf(w, "raw frame=%dB clients=%d\n", len(frame), n)
	})

	mux.HandleFunc("/c2s", func(w http.ResponseWriter, r *http.Request) {
		op, payload, err := opcodeAndHex(r)
		if err != nil {
			http.Error(w, err.Error(), http.StatusBadRequest)
			return
		}
		arch, _ := strconv.Atoi(r.URL.Query().Get("arch"))
		frame := &protocol.C2SFrame{Arch: byte(arch), Opcode: uint16(op), Payload: payload}
		// Optional ?coach=<id> targets ONE session. Without it this dispatches to
		// every connected client, which is useless for anything involving two
		// players: "A invites B" cannot be expressed by a frame both of them send.
		wantCoach, _ := strconv.Atoi(r.URL.Query().Get("coach"))
		n := 0
		s.deps.Sessions.Each(func(sess *Session) {
			if wantCoach != 0 && (sess.Coach == nil || int(sess.Coach.ID) != wantCoach) {
				return
			}
			n++
			if derr := s.router.Dispatch(sess, frame); derr != nil {
				fmt.Fprintf(w, "dispatch error: %v\n", derr)
			}
		})
		fmt.Fprintf(w, "c2s opcode=%d payload=%dB dispatched=%d\n", op, len(payload), n)
	})

	mux.HandleFunc("/fight", func(w http.ResponseWriter, r *http.Request) {
		fmt.Fprint(w, s.deps.Fights.DebugDump())
	})

	// /script runs a whole fight scenario in-process on the fight actor (see
	// debug_script.go) — the deep-combat test driver that replaces brittle
	// external poll-act loops.
	mux.HandleFunc("/script", s.handleScript)

	mux.HandleFunc("/sessions", func(w http.ResponseWriter, r *http.Request) {
		n := s.deps.Sessions.Each(func(sess *Session) {
			name, id := "-", uint(0)
			if sess.Coach != nil {
				name, id = sess.Coach.Name, sess.Coach.ID
			}
			fmt.Fprintf(w, "coach=%s id=%d\n", name, id)
		})
		fmt.Fprintf(w, "total=%d\n", n)
	})

	go func() {
		s.deps.Log.Warn("DEV packet-inject endpoint listening (loopback, do not enable in prod)", "addr", addr)
		if err := http.ListenAndServe(addr, mux); err != nil {
			s.deps.Log.Warn("debug inject server stopped", "err", err)
		}
	}()
}

// opcodeAndHex parses the shared ?opcode=&hex= query params.
func opcodeAndHex(r *http.Request) (int, []byte, error) {
	op, err := strconv.Atoi(r.URL.Query().Get("opcode"))
	if err != nil {
		return 0, nil, fmt.Errorf("bad opcode: %w", err)
	}
	payload, err := hex.DecodeString(r.URL.Query().Get("hex"))
	if err != nil {
		return 0, nil, fmt.Errorf("bad hex: %w", err)
	}
	return op, payload, nil
}
