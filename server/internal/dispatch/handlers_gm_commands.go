package dispatch

import (
	"context"
	"fmt"
	"strconv"
	"strings"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
)

// This file ports the legacy VicinityMessage.java's '/'-prefixed GM
// chat-cheat-commands (see docs/08-java-parity-roadmap.md §8.4), with two
// deliberate fixes over the original:
//
//  1. A real permission check. Java executed every command for any
//     authenticated player with zero gating -- anyone could teleport
//     anywhere or mint free cards. Here, handleGMCommand requires
//     sessionIsAdmin(session) (backed by the new Account.IsAdmin flag,
//     also used by the web admin console, see docs/10-web-portal.md).
//  2. Real client feedback. Java's /CELLID (and any unrecognized command)
//     only wrote to the SERVER's stdout, telling the requesting player
//     nothing. Every reply here is sent back to the client as an actual
//     packet via sendGMFeedback, reusing the PRIVATE_MESSAGE wire shape
//     with a synthetic "Server" sender (coachID 0, which no real coach
//     ever has) since the protocol has no dedicated system-message opcode.
//
// Deliberately NOT ported: the client also ships an entirely separate,
// unrelated CONSOLE_ADMIN_COMMAND (opcode 8193/8194) protocol wired to its
// own encrypted AdminServerInstance listener -- a server-operations/
// monitoring console (query live "proxy"/"worldmanager"/"connection"/
// "game"/"chat" subsystem properties), not in-game GM player powers. It was
// never wired up server-side even in the legacy Java game server (dead
// opcode, no dispatch case) and belongs to a different subsystem entirely,
// so it's out of scope here; see docs/08-java-parity-roadmap.md §8.4 for
// the exploration notes.

// gmFeedbackSender is the synthetic sender name used for GM command
// replies, delivered via the existing PRIVATE_MESSAGE wire shape.
const gmFeedbackSender = "Server"

// sendGMFeedback sends a one-off text reply to the requesting client only,
// rendered by the client the same way any other private message is.
func sendGMFeedback(session *netio.Session, message string) {
	w := protocol.NewWriter(10 + len(gmFeedbackSender) + len(message))
	w.PutString(gmFeedbackSender).PutInt64(0).PutString(message)
	session.Send(protocol.OutboundFrame{Opcode: protocol.SendPrivateMessage, Payload: w.Bytes()})
}

// handleGMCommand parses and, if authorized, executes a '/'-prefixed chat
// message as a GM command. Non-admins get a silent no-op (matching Java's
// behavior of never broadcasting '/'-prefixed text as vicinity chat either
// way) -- no command ever executes for them, which is the actual fix over
// the legacy version.
func handleGMCommand(session *netio.Session, sender *domain.Coach, message string, deps *Deps) {
	if !sessionIsAdmin(session) {
		return
	}

	fields := strings.Fields(message)
	if len(fields) == 0 {
		return
	}
	cmd := strings.ToUpper(strings.TrimPrefix(fields[0], "/"))
	args := fields[1:]
	ctx := context.Background()

	switch cmd {
	case "STATS":
		// Re-send PLAYER_STATISTICS_REPORT, mirroring
		// VicinityMessage.java's PlayerStatisticsReport.parse call. Read
		// the sender's stats via a registry view (value snapshot under the
		// lock) rather than off the live shared coach pointer: this runs on
		// the sender's own goroutine, but a concurrent fight-end writes the
		// same stat fields on the fight goroutine, so a direct read races.
		if v, ok := deps.World.ViewOf(sender.ID); ok {
			snap := domain.Coach{
				ID:                v.ID,
				TotalPlayTimeSecs: v.TotalPlayTimeSecs,
				TimeInFightSecs:   v.TimeInFightSecs,
				StatFights:        v.StatFights,
				StatWins:          v.StatWins,
				StatLosses:        v.StatLosses,
				Strength:          v.Strength,
				ConsecutiveWins:   v.ConsecutiveWins,
			}
			session.Send(buildPlayerStatisticsReport(&snap))
		} else {
			session.Send(buildPlayerStatisticsReport(sender))
		}

	case "CELLID":
		// Java printed this to the server console only; replying to the
		// client is strictly more useful and was an explicit roadmap fix.
		sendGMFeedback(session, fmt.Sprintf("%d,%d,%d", sender.PosX, sender.PosY, sender.PosZ))

	case "TP":
		handleGMTeleport(ctx, session, sender, args, deps)

	case "CARD":
		handleGMCard(ctx, session, sender, args, deps)

	case "ALLCARDS":
		handleGMAllCards(ctx, session, sender, deps)

	case "PRES":
		session.Send(buildStartPresentation())

	case "CANCEL":
		handleGMCancel(session, args)

	case "APPEAR":
		handleGMAppear(session, args, deps)

	default:
		sendGMFeedback(session, "Unknown command: "+fields[0])
	}
}

// handleGMAppear re-sends ACTOR_APPEAR (4102) for every fighter in the
// requester's current fight, so the exact wire values can be tweaked and
// re-tested LIVE during a fight without restarting the server or replaying
// the whole matchmaking->placement flow. This is a debugging aid for the
// "fighters don't render / render at wrong altitude" investigation.
//
// Usage:
//
//	/APPEAR              -- resend with each fighter's real position (current behavior)
//	/APPEAR z <zval>     -- resend forcing every fighter's altitude to <zval>
//	/APPEAR dir <d>      -- resend forcing every fighter's direction to <d> (0-7)
//	/APPEAR z <zval> dir <d>
//	/APPEAR raw <z> <d>  -- shorthand: force both altitude and direction
//	/APPEAR here         -- resend every fighter at the requester's OWN coach
//	                        cell (x,y,z), to test whether a known-good coach
//	                        position renders where the real fighter one doesn't
//
// Any packet sent is also logged (server side, via trace_packets) so the
// exact bytes tried are captured alongside the visual result.
func handleGMAppear(session *netio.Session, args []string, deps *Deps) {
	fight, coach, ok := sessionFight(session, deps)
	if !ok {
		sendGMFeedback(session, "/APPEAR: you are not currently in a fight")
		return
	}

	var (
		forceZ, forceDir *int
		forceHere        bool
	)
	// Parse flexible key/value args.
	for i := 0; i < len(args); i++ {
		switch strings.ToLower(args[i]) {
		case "z":
			if i+1 < len(args) {
				if v, err := strconv.Atoi(args[i+1]); err == nil {
					forceZ = &v
					i++
				}
			}
		case "dir":
			if i+1 < len(args) {
				if v, err := strconv.Atoi(args[i+1]); err == nil {
					forceDir = &v
					i++
				}
			}
		case "raw":
			if i+2 < len(args) {
				if z, err := strconv.Atoi(args[i+1]); err == nil {
					forceZ = &z
				}
				if d, err := strconv.Atoi(args[i+2]); err == nil {
					forceDir = &d
				}
				i += 2
			}
		case "here":
			forceHere = true
		}
	}

	var entries []actorAppearEntry
	for _, fr := range fight.AllFighters() {
		e := actorAppearEntry{
			ID: fr.ID, X: fr.Position.X, Y: fr.Position.Y, Z: fr.Position.Z, Direction: fr.Direction,
		}
		if forceHere {
			e.X, e.Y, e.Z = coach.PosX, coach.PosY, coach.PosZ
		}
		if forceZ != nil {
			e.Z = int16(*forceZ)
		}
		if forceDir != nil {
			e.Direction = combat.Direction8(byte(*forceDir))
		}
		entries = append(entries, e)
	}

	frame := buildActorAppear(entries)
	session.Send(frame)

	// Echo exactly what was sent so it's visible in-client without needing
	// to alt-tab to the server log.
	var summary strings.Builder
	summary.WriteString(fmt.Sprintf("/APPEAR sent %d fighter entries: ", len(entries)))
	for i, e := range entries {
		if i > 0 {
			summary.WriteString(" | ")
		}
		summary.WriteString(fmt.Sprintf("id=%d (%d,%d,%d) dir=%d", e.ID, e.X, e.Y, e.Z, byte(e.Direction)))
	}
	sendGMFeedback(session, summary.String())
}

// handleGMTeleport ports the /TP x y z map orientation command
// (VicinityMessage.java's TP case), persisting the new position and
// re-sending ENTER_WORLD_INSTANCE to move the client's view. Unlike Java
// (which let a malformed command throw an uncaught
// NumberFormatException), invalid arguments get a usage reply instead of
// silently failing the request.
func handleGMTeleport(ctx context.Context, session *netio.Session, sender *domain.Coach, args []string, deps *Deps) {
	if len(args) < 5 {
		sendGMFeedback(session, "Usage: /TP x y z map orientation")
		return
	}
	x, errX := strconv.Atoi(args[0])
	y, errY := strconv.Atoi(args[1])
	z, errZ := strconv.Atoi(args[2])
	mapID, errMap := strconv.Atoi(args[3])
	orientation, errOrient := strconv.Atoi(args[4])
	if errX != nil || errY != nil || errZ != nil || errMap != nil || errOrient != nil {
		sendGMFeedback(session, "Usage: /TP x y z map orientation (all integers)")
		return
	}

	if err := deps.Coach.UpdatePosition(ctx, sender.ID, int32(x), int32(y), int16(z)); err != nil {
		deps.Logger.Error().Err(err).Uint("coach_id", sender.ID).Msg("dispatch: gm /TP failed to persist position")
		sendGMFeedback(session, "Teleport failed: could not save position")
		return
	}
	// Keep the in-memory coach (shared with world.Registry) in sync so a
	// subsequent /CELLID or ACTOR_SPAWN reflects the new position
	// immediately, mirroring how handleActorMovementRequest updates it.
	// Routed through the registry lock (not a direct field write) so it
	// can't race a concurrent broadcast reading this coach's position via a
	// view; the registry coach is the same object as `sender`.
	deps.World.UpdatePosition(sender.ID, int32(x), int32(y), int16(z))

	session.Send(buildEnterWorldInstance(float32(x), float32(y), int16(z), int16(mapID), orientation != 0))
}

// handleGMCard ports the /CARD id command (VicinityMessage.java's CARD
// case): grants one coach card by template id. Unlike Java, the template
// is validated against the real gamedata.Store.CoachCards repository
// before granting (Java's version had the same null check, so this
// matches its "reject unknown id" behavior, just with client feedback
// instead of a server-console-only println).
func handleGMCard(ctx context.Context, session *netio.Session, sender *domain.Coach, args []string, deps *Deps) {
	if len(args) < 1 {
		sendGMFeedback(session, "Usage: /CARD id")
		return
	}
	id, err := strconv.Atoi(args[0])
	if err != nil {
		sendGMFeedback(session, "Usage: /CARD id (integer template id)")
		return
	}

	if _, ok := deps.Data.CoachCards.Get(int32(id)); !ok {
		sendGMFeedback(session, fmt.Sprintf("Unknown coach card template %d", id))
		return
	}

	// GM-granted cards are not cursed, matching VicinityMessage.java's CARD
	// case (VicinityMessage.java:58, flag 0).
	card, err := deps.Coach.AddCard(ctx, sender.ID, int32(id), 1, 0)
	if err != nil {
		deps.Logger.Error().Err(err).Uint("coach_id", sender.ID).Int32("template_id", int32(id)).Msg("dispatch: gm /CARD failed")
		sendGMFeedback(session, "Failed to grant card")
		return
	}

	if frame, ok := (&inventoryDelta{AddInventory: []domain.CoachCard{*card}}).build(); ok {
		session.Send(frame)
	}
	sendGMFeedback(session, fmt.Sprintf("Granted card template %d", id))
}

// handleGMAllCards ports the /ALLCARDS command (VicinityMessage.java's
// ALLCARDS case): grants one of every known coach card template. Java
// hardcoded a brittle `for (id = 1; id < 1000; id++)` loop that stopped at
// the first gap in template ids; this iterates the real, fully-loaded
// template set instead (gamedata.Store.CoachCards.All()), so it can't miss
// templates past a gap or accidentally stop early.
func handleGMAllCards(ctx context.Context, session *netio.Session, sender *domain.Coach, deps *Deps) {
	templates := deps.Data.CoachCards.All()
	delta := &inventoryDelta{}
	granted := 0
	for _, tmpl := range templates {
		// GM-granted cards are not cursed, matching VicinityMessage.java's
		// ALLCARDS case (VicinityMessage.java:76, flag 0).
		card, err := deps.Coach.AddCard(ctx, sender.ID, tmpl.ID, 1, 0)
		if err != nil {
			deps.Logger.Error().Err(err).Int32("template_id", tmpl.ID).Msg("dispatch: gm /ALLCARDS failed to grant a card")
			continue
		}
		delta.AddInventory = append(delta.AddInventory, *card)
		granted++
	}
	if frame, ok := delta.build(); ok {
		session.Send(frame)
	}
	sendGMFeedback(session, fmt.Sprintf("Granted %d card templates", granted))
}

// handleGMCancel ports the /CANCEL id command (VicinityMessage.java's
// CANCEL case): sends FIGHT_CREATION_CANCELED_MESSAGE to self with an
// arbitrary reason byte, for testing the client's cancel-reason UI. Matches
// Java's behavior of hardcoding fightId=0 exactly.
func handleGMCancel(session *netio.Session, args []string) {
	if len(args) < 1 {
		sendGMFeedback(session, "Usage: /CANCEL id")
		return
	}
	id, err := strconv.Atoi(args[0])
	if err != nil || id < 0 || id > 255 {
		sendGMFeedback(session, "Usage: /CANCEL id (0-255)")
		return
	}
	session.Send(buildFightCreationCanceled(0, byte(id)))
}
