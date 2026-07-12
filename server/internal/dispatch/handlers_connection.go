package dispatch

import (
	"context"
	"strings"
	"time"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/world"
)

// RegisterConnectionHandlers wires the login/version/disconnect/
// coach-creation opcodes -- the full authentication and world-entry flow,
// see docs/02-protocol.md §2.4.1-§2.4.6.
func RegisterConnectionHandlers(r *Router, deps *Deps) {
	r.Register(protocol.RecvVersion, func(session *netio.Session, payload *protocol.Reader) {
		handleClientVersion(session, payload, deps)
	})

	r.Register(protocol.RecvDisconnect, func(session *netio.Session, _ *protocol.Reader) {
		session.Close()
	})

	r.Register(protocol.RecvAuthentication, func(session *netio.Session, payload *protocol.Reader) {
		handleAuthentication(session, payload, deps)
	})

	r.Register(protocol.RecvCoachCreation, func(session *netio.Session, payload *protocol.Reader) {
		handleCoachCreation(session, payload, deps)
	})
}

// handleClientVersion validates the client's reported version/revision/
// build against the server's configured values, mirroring
// ClientVersion.java:13-30. On mismatch, sends INVALID_VERSION and closes
// the connection (kicks the client) exactly as the legacy server does.
func handleClientVersion(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	version := payload.Byte()
	revision := payload.Uint16()
	build := payload.String()
	if payload.Err() != nil {
		session.Close()
		return
	}
	build = strings.ReplaceAll(build, " ", "")

	expected := deps.Server.Version
	if version != expected.Major || revision != expected.Revision || build != expected.Build {
		w := protocol.NewWriter(3)
		w.PutByte(expected.Major).PutUint16(expected.Revision)
		session.Send(protocol.OutboundFrame{Opcode: protocol.SendInvalidVersion, Payload: w.Bytes()})
		session.Close()
	}
}

func handleAuthentication(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	login := payload.String()
	password := payload.String()
	if payload.Err() != nil {
		session.Close()
		return
	}

	ctx := context.Background()
	account, result, err := deps.Auth.Authenticate(ctx, login, password)
	if err != nil {
		deps.Logger.Error().Err(err).Str("login", login).Msg("dispatch: authentication error")
		sendAuthResult(session, protocol.AuthInvalidLogin)
		session.Close()
		return
	}

	switch result {
	case service.AuthResultInvalidLogin:
		sendAuthResult(session, protocol.AuthInvalidLogin)
		session.Close()
		return
	case service.AuthResultAlreadyConnected:
		sendAuthResult(session, protocol.AuthAlreadyConnected)
		session.Close()
		return
	}

	session.SetAccount(&netio.AccountRef{AccountID: account.ID, IsAdmin: account.IsAdmin})
	sendAuthResult(session, protocol.AuthOK)
	sendQueueNotification(session, -1)

	if account.CoachID == nil {
		session.Send(protocol.OutboundFrame{Opcode: protocol.SendCoachCreationRequest})
		return
	}

	coach, err := deps.Coach.GetCoachByAccountID(ctx, account.ID)
	if err != nil {
		deps.Logger.Error().Err(err).Uint("account_id", account.ID).Msg("dispatch: failed to load coach after auth")
		return
	}

	completeLogin(session, account, coach, deps)
}

func handleCoachCreation(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	name := payload.String()
	skin := payload.Byte()
	hair := payload.Byte()
	sex := payload.Byte()
	if payload.Err() != nil {
		return
	}

	accountRef := session.Account()
	if accountRef == nil {
		return
	}

	ctx := context.Background()
	coach, result, err := deps.Coach.CreateCoach(ctx, accountRef.AccountID, name, skin, hair, sex)
	if err != nil {
		deps.Logger.Error().Err(err).Str("name", name).Msg("dispatch: coach creation error")
		sendCoachCreationResult(session, service.CoachCreationInvalidName)
		return
	}
	if result != service.CoachCreationOK {
		sendCoachCreationResult(session, result)
		return
	}

	sendCoachCreationResult(session, service.CoachCreationOK)

	accountRef.CoachID = coach.ID
	session.SetAccount(accountRef)

	// Reload with associations empty-but-initialized (a brand new coach has
	// no inventory/friends/ignored yet) so downstream builders don't nil-panic.
	coach.Inventory = nil
	coach.Friends = nil
	coach.Ignored = nil

	var account domain.Account
	account.ID = accountRef.AccountID
	completeLogin(session, &account, coach, deps)
}

// completeLogin runs the shared post-authentication / post-creation
// sequence: attach coach to session, send info + social lists, register in
// the world, broadcast join, and enter the world.
func completeLogin(session *netio.Session, account *domain.Account, coach *domain.Coach, deps *Deps) {
	setSessionCoach(session, coach)

	accountRef := session.Account()
	if accountRef != nil {
		accountRef.CoachID = coach.ID
		session.SetAccount(accountRef)
	}

	session.Send(buildCoachInformation(coach))
	session.Send(buildFriendList(coach, deps.World))
	session.Send(buildIgnoreList(coach))
	session.Send(buildPlayerStatisticsReport(coach))

	// Record when this session's play-time accrual window opened, so
	// HandleDisconnect can add the elapsed seconds to TotalPlayTimeSecs.
	setSessionLoginAt(session, time.Now())

	enterWorld(session, coach, deps)
}

// enterWorld registers the coach in the online registry, notifies them and
// every other online player of the join (ACTOR_SPAWN), mirroring
// World.addOnlineCoach's broadcast-to-everyone-including-self behavior
// from Coach.java:252-258 + World.java:32-35.
func enterWorld(session *netio.Session, coach *domain.Coach, deps *Deps) {
	session.Send(buildEnterWorldInstance(float32(coach.PosX), float32(coach.PosY), coach.PosZ, 0, false))

	online := &world.OnlineCoach{Coach: coach, Session: session}
	if !deps.World.Add(online) {
		deps.Logger.Warn().Uint("coach_id", coach.ID).Msg("dispatch: coach already registered online, possible duplicate login")
		return
	}

	// Every online coach (including the one who just joined) receives an
	// ACTOR_SPAWN listing every *other* online coach, matching the legacy
	// World.addOnlineCoach -> onJoinMap fan-out.
	for _, oc := range deps.World.Snapshot() {
		others := deps.World.SnapshotWithout(oc.ID())
		if len(others) == 0 {
			continue
		}
		oc.Session.Send(buildActorSpawn(others))
	}
}

func sendAuthResult(session *netio.Session, code protocol.AuthResultCode) {
	w := protocol.NewWriter(1)
	w.PutByte(byte(code))
	session.Send(protocol.OutboundFrame{Opcode: protocol.SendAuthenticationResult, Payload: w.Bytes()})
}

func sendQueueNotification(session *netio.Session, position int32) {
	w := protocol.NewWriter(4)
	w.PutInt32(position)
	session.Send(protocol.OutboundFrame{Opcode: protocol.SendQueueNotification, Payload: w.Bytes()})
}

func sendCoachCreationResult(session *netio.Session, result service.CoachCreationResult) {
	w := protocol.NewWriter(1)
	w.PutByte(byte(result))
	session.Send(protocol.OutboundFrame{Opcode: protocol.SendCoachCreationResult, Payload: w.Bytes()})
}
