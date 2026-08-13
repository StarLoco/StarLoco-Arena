package game

import (
	"log/slog"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// Deps is the dependency container every handler closure captures.
type Deps struct {
	Store    *store.Store
	World    *Registry
	Cards    *gamedata.Cards    // nil if data files absent
	CardSets *gamedata.CardSets // card-set (panoplie) bonuses; nil if data files absent
	// FusionLabs is the type-1100 altar table (power / quality / slot count). The
	// 5490 request names no altar, so the fusion handler uses FusionLabs.Default().
	FusionLabs    *gamedata.FusionLabs    // nil if data files absent
	Conditions    *gamedata.Conditions    // persistent fighter conditions/wounds (type 902); nil if data files absent
	Spells        *gamedata.Spells        // nil if data files absent
	FighterCards  *gamedata.FighterCards  // nil if data files absent
	Summonings    *gamedata.Summonings    // summon-creature templates; nil if data files absent
	StaticEffects *gamedata.StaticEffects // trap/glyph (type-210) templates; nil if data files absent
	// ChallengeDefs is the type-400 challenge TABLE (PvE definitions behind the
	// overworld's DemonChallenge / BreedMaster elements); nil if data files
	// absent. Not to be confused with Challenges below, which is the live
	// coach-vs-coach direct-challenge manager.
	ChallengeDefs *gamedata.Challenges
	// FightMaps holds every arena decoded from data/maps; nil falls back to the
	// hand-decoded world 5 (see arena_registry.go).
	FightMaps *gamedata.FightMaps
	// Events is the per-round event-card table (type 230). nil/empty leaves the
	// round-card mechanic inert (eventId 0, no effects) — see events.go.
	Events     *gamedata.Events
	Exchanges  *ExchangeManager
	Matchmaker *Matchmaker
	Challenges *ChallengeManager
	Fights     *FightManager
	Sessions   *SessionRegistry
	// Tournaments tracks which coaches registered for which standing tournament
	// (the tournament totem's list/calendar). In-memory, process-lived.
	Tournaments *TournamentManager
	Log         *slog.Logger
}

// RegisterAll wires every feature handler group onto the router.
func RegisterAll(r *Router, d *Deps) {
	registerConnectionHandlers(r, d)
	registerLifecycleHandlers(r, d)
	registerMovementHandlers(r, d)
	registerChatHandlers(r, d)
	registerSocialHandlers(r, d)
	registerInventoryHandlers(r, d)
	registerShopHandlers(r, d)
	registerFusionHandlers(r, d)
	registerExchangeHandlers(r, d)
	registerFighterHandlers(r, d)
	registerTeamHandlers(r, d)
	registerMatchmakingHandlers(r, d)
	registerLadderHandlers(r, d)
	registerFightHandlers(r, d)
	registerFightCreationHandlers(r, d)
	registerReconnectHandlers(r, d)
	registerSpectateHandlers(r, d)
	registerChallengeHandlers(r, d)
	registerElementHandlers(r, d)
	registerZaapHandlers(r, d)
	registerMailHandlers(r, d)
	registerEvolutionHandlers(r, d)
	registerTotemHandlers(r, d)
}
