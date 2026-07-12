package dispatch

import (
	"time"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/netio"
)

// coachUserDataKey is the internal/netio.Session.UserData key under which
// the live *domain.Coach for an authenticated session is stashed, so
// handlers don't need to re-query the DB on every packet.
const coachUserDataKey = "coach"

// loginAtUserDataKey stores the wall-clock time this session finished
// login, used on disconnect to accrue TotalPlayTimeSecs (the coach's
// lifetime "Temps total de jeu" statistic).
const loginAtUserDataKey = "loginAt"

// setSessionLoginAt records when a session's play-time accrual window
// opened (called from completeLogin).
func setSessionLoginAt(session *netio.Session, at time.Time) {
	session.SetUserData(loginAtUserDataKey, at)
}

// sessionLoginAt retrieves the session's login time, if recorded.
func sessionLoginAt(session *netio.Session) (time.Time, bool) {
	v, ok := session.UserData(loginAtUserDataKey)
	if !ok {
		return time.Time{}, false
	}
	at, ok := v.(time.Time)
	return at, ok
}

// setSessionCoach attaches the live coach to a session after
// login/creation.
func setSessionCoach(session *netio.Session, coach *domain.Coach) {
	session.SetUserData(coachUserDataKey, coach)
}

// sessionCoach retrieves the live coach attached to a session, if any.
func sessionCoach(session *netio.Session) (*domain.Coach, bool) {
	v, ok := session.UserData(coachUserDataKey)
	if !ok {
		return nil, false
	}
	coach, ok := v.(*domain.Coach)
	return coach, ok
}

// sessionIsAdmin reports whether the session's authenticated account is
// flagged admin, gating the GM chat-cheat-commands (see
// docs/08-java-parity-roadmap.md §8.4). The flag is snapshotted at login
// time on netio.AccountRef (see handlers_connection.go's
// handleAuthentication) -- false (including for an unauthenticated
// session) is always the safe default.
func sessionIsAdmin(session *netio.Session) bool {
	ref := session.Account()
	return ref != nil && ref.IsAdmin
}
