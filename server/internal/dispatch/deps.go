package dispatch

import (
	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/config"
	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/world"
)

// Deps bundles every dependency the opcode handlers need: services, world
// state, game data, and server config. Passed to each Register* function so
// handler closures can capture exactly what they need without a global
// singleton (unlike the legacy Java version's static-everywhere design).
type Deps struct {
	Server config.ServerConfig
	Combat config.CombatConfig

	Auth        *service.AuthService
	Coach       *service.CoachService
	Fighter     *service.FighterService
	Team        *service.TeamService
	Social      *service.SocialService
	Matchmaking *service.MatchmakingService

	World       *world.Registry
	Duels       *world.DuelManager
	Invitations *world.InvitationManager
	Exchanges   *world.ExchangeManager
	Fights      *combat.Manager
	Data        *gamedata.Store

	Logger zerolog.Logger
}

// RegisterAll wires every opcode handler group onto router. This is the
// single place that needs updating when a new handler group is added.
func RegisterAll(router *Router, deps *Deps) {
	RegisterConnectionHandlers(router, deps)
	RegisterSocialHandlers(router, deps)
	RegisterChatHandlers(router, deps)
	RegisterFighterHandlers(router, deps)
	RegisterTeamHandlers(router, deps)
	RegisterMatchmakingHandlers(router, deps)
	RegisterCoachManagementHandlers(router, deps)
	RegisterFightHandlers(router, deps)
	RegisterExchangeHandlers(router, deps)
	RegisterAdminConsoleHandlers(router, deps)
}
