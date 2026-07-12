package world

import (
	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/protocol"
)

// RegistryBroadcaster implements combat.Broadcaster on top of the online-
// coach Registry, the concrete "how does the Fight actor's goroutine emit
// outbound packets back through netio.Session" boundary called for in
// docs/08-java-parity-roadmap.md Phase B. Kept in package world (rather
// than dispatch) since it only needs Registry, avoiding a dispatch<->combat
// import cycle (Duel, in this same package, holds a *combat.Fight).
type RegistryBroadcaster struct {
	Registry *Registry
}

// SendToCoach implements combat.Broadcaster.
func (b RegistryBroadcaster) SendToCoach(coachID uint, frame protocol.OutboundFrame) {
	if oc, ok := b.Registry.Get(coachID); ok {
		oc.Session.Send(frame)
	}
}

var _ combat.Broadcaster = RegistryBroadcaster{}
