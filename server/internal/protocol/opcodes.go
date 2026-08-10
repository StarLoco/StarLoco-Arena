package protocol

// Wire opcodes used during the connection/login handshake and early world
// entry. Full opcode inventory lives in client/analysis/opcode_map.csv.
const (
	// Lifecycle (client -> server)
	OpDisconnect             = 1     // C2S: empty — DisconnectionNotificationMessage (graceful quit)
	OpTutorialChangeInstance = 4517  // C2S: empty (arch 3) — tutorial/instance-ready ack
	OpDestroyCoach           = 27529 // C2S: empty (arch 2) — "Détruire le coach" (delete coach)
	OpStatisticUpdate        = 22003 // C2S (arch 2): [i16 statId][i8 flag][i16 value] achievement/stat counter

	// Connection / auth (client -> server unless noted)
	OpClientVersion        = 7    // C2S: [u8 0x02][u16 ver=70][u8 len][ascii build]
	OpPing                 = 107  // both ways: latency/clock-sync handshake
	OpClientAuthentication = 1025 // C2S: [u8 loginLen][login][u8 passLen][pass] (plaintext)

	// Server -> client
	OpClientAuthResult       = 1024 // S2C: [u8 resultCode]
	OpWorldServerUnavailable = 1026 // S2C: empty
	OpInvalidClientVersion   = 8    // S2C: required version bytes

	// Coach creation / world entry
	OpCoachCreationRequest = 2048 // S2C: empty — shows coach-creation screen
	OpCoachCreation        = 2049 // C2S: [u8 nameLen][name][u8 skin][u8 hair][u8 sex]
	OpCoachCreationResult  = 2050 // S2C: [u8 resultCode]
	OpCoachInformations    = 2052 // S2C: serialized LocalCoach (existing coach -> lobby)
	OpEnterInstance        = 4600 // S2C: [f32 x][f32 y][i16 alt][i16 mapId][u8 dynamic] — renders world/lobby

	// Movement (overworld)
	OpActorMovement        = 4500 // S2C: [i64 actorId] + n×[i32 x][i32 y][i16 z]
	OpCoachMovementRequest = 4501 // C2S: n×[i32 x][i32 y][i16 z] (no count; from length)

	// Actors
	OpActorSpawn   = 4096 // S2C: (zlib-wrappable) [i32 count] + per actor
	OpActorDespawn = 4098 // S2C: [i32 count] + i64 ids
	OpActorAppear  = 4102 // S2C: [u8 count]{i64 id,i32 x,i32 y,i16 z,u8 dir} — inserts a fight actor into the render list + shows it (aEV/qg_2.g)

	// Interactive elements (overworld NPCs, Zaaps, Cardmasters) + Zaap teleport.
	// Elements are defined in each world's env layer, but the client CLEARS them
	// on ENTER_INSTANCE (4600); the server must (re)spawn the ones to show via 200
	// (by env instanceId). See internal/game/zaap.go + docs/OVERWORLD-MAP.md.
	OpInteractiveElementSpawn  = 200  // S2C rz_2: [i16 count]{[i64 instanceId][i16 len][partTableBlob]}
	OpInteractiveElementAction = 201  // C2S bd_2: [i64 instanceId][i16 actionOrdinal] — element click
	OpZaapTeleport             = 4512 // C2S Gs: [i32 cardTemplateId] — use a Zaap card; server replies EnterInstance(4600)
	// OpInstanceReady MUST be sent after every overworld EnterInstance(4600).
	// A Zaap request (4512) sets a movement lock client-side (auv_0) and this is
	// the ONLY thing that clears it — there is no timeout, so omitting it leaves
	// the coach permanently unable to walk. It also fires the client's
	// walked-onto-element triggers. See game.Session.sendEnterOverworld.
	OpInstanceReady = 4516  // S2C yu_1: empty — instance ready / movement unlock
	OpStatisticData = 22002 // S2C ls_0: [i32 byteLen]{[i16 crit][i16 val]}. DO NOT EMIT: the client (asA) unconditionally opens the tutorial-guide dialog on receipt. Deliver criteria via the 2052 coach descriptor instead.

	// Statistics
	OpPlayerStatisticsReport = 2400 // S2C: [u16 blobLen][stats field-map blob]

	// Inventory / equipment
	OpCoachInventoryUpdate        = 5200 // S2C: 4 sections (addEquip, removeShort, updInv, ints)
	OpCoachEquipmentUpdateRequest = 5201 // C2S: 14×i32 slot references
	OpCoachInventoryUpdateRequest = 5203 // C2S: [u16 count]+i64 uids — cards REMOVED from the inventory (client fh_0/sj_1.yG); the uids are client-local (eb_1 assigns uq_1.ahR()), so they cannot be resolved server-side
	OpCoachEquipmentUpdate        = 5202 // S2C: broadcast equipment change

	// Shop / economy (Card Master token purchase + wallet)
	OpWalletUpdate  = 4001 // S2C: [i8 count]{i8 currencyType, i32 amount} full wallet sync
	OpShopOpen      = 5300 // C2S: (empty) open Card Master / request catalog
	OpShopCatalog   = 5401 // S2C: [i8 flag][i32 shopId]{i32 cardId, i16 qty} offering
	OpShopBuy       = 5450 // C2S: [i32 shopId][i16 N]{i32 cardId} buy with tokens
	OpShopResult    = 5403 // S2C: [i8 result][i8 count]{i8 currencyType, i32 amount} new balance
	OpShopBarter    = 5400 // C2S: [i32 exId][i16 N]{i32 wantedId}[i16 M]{i32 givenId,i16 qty}
	OpFusionRequest = 5490 // C2S: [i32 count]{i32 cardId} (ids reversed) fuse cards
	OpFusionResult  = 5491 // S2C: [i8 result][i32 obtained][i32 notObtained][i32 recovered]

	// Card exchange / trading
	OpExchangeInvite            = 5101 // C2S: [i64 targetId]
	OpExchangeInvitationRequest = 5102 // S2C: [i64 exId][i64 inviterId][u8 len][name]
	OpExchangeAnswer            = 5103 // C2S: [i64 exId][i8 accept]
	OpExchangeConfirmation      = 5104 // S2C: [i8 result][i64 exId][i64 otherId]
	OpExchangeAddCard           = 5105 // C2S: [i64 exId][i64 cardUid][i16 qty]
	OpExchangeRemoveCard        = 5106 // C2S: [i64 exId][i64 cardUid][i16 qty]
	OpExchangeSetReady          = 5107 // C2S: [i64 exId] (toggle)
	OpExchangeCancel            = 5108 // C2S: [i64 exId]
	OpExchangeCardAdded         = 5109 // S2C: [i64 exId][i8 userIdx][card][i16 qty]
	OpExchangeCardRemoved       = 5110 // S2C: [i64 exId][i8 userIdx][card][i16 qty]
	OpExchangeEnd               = 5111 // S2C: [i8 reason][i64 exId] (0=success,1=cancel)
	OpExchangeUserReady         = 5112 // S2C: [i64 exId][i8 userIdx]

	// Exchange end reasons
	ExchangeEndSuccess = 0
	ExchangeEndCancel  = 1

	// Card object on the wire: [i32 refCardId][i64 uid][i8 flags]
	CardFlagLocked = 1
	CardFlagCursed = 2

	// Fighters / teams
	OpFighterCreate           = 6001 // C2S: [u8 flag][i16 slot][i16 len][et_2 blob]
	OpFighterCreateResult     = 6000 // S2C: [u8 result](+coachId,fighterId,blob,flag,slot)
	OpFighterDelete           = 6003 // C2S: [i64 fighterId][i16 slot]
	OpFighterDeleteResult     = 6002 // S2C: [u8 result][i64 fighterId][i64 secondaryId]
	OpFighterInformationList  = 6006 // S2C: [i64 leadId][u8 count]{i64 id,u16 len,blob}
	OpUpdateFighterInventory  = 6011 // C2S: [i64 fighterId][i16 teamId][i16 len][cards][i16 len][spells]
	OpUpdatedFighterInventory = 6010 // S2C: [i64 fighterId][i8 result](+[i16 len][cards][i16 len][spells])
	OpFighterAssignTeam       = 6013 // C2S: [i64 fighterId][i16 srcTeam][i16 dstTeam][i64 am] — qp_1 drag/assign; dst=-1 removes
	OpTeamPresetSave          = 6021 // C2S: [sw_1 blob][u8 pad]
	OpTeamPresetDelete        = 6023 // C2S: [i16 teamId]
	OpTeamPresetListRequest   = 6031 // C2S: (empty) request team presets
	OpTeamPresetList          = 6030 // S2C: [u8 presetCount]{sw_1}[u8 coachCount]{...}

	// Fight lifecycle
	OpCreateFight            = 8000 // S2C: the fight-presentation blob
	OpStartPresentation      = 8010 // S2C: empty
	OpReadyForPlacementAck   = 8012 // S2C: [i64 coachId]
	OpEndPresentation        = 8018 // S2C: empty
	OpStartPlacement         = 8020 // S2C: empty
	OpMoveToFreePlacement    = 8022 // S2C: [i64 fighterId][i32 x][i32 y][i16 z]
	OpReadyForObservationAck = 8024 // S2C: [i64 coachId]
	OpEndPlacement           = 8028 // S2C: empty
	OpStartObservation       = 8030 // S2C: empty
	OpReadyForActionAck      = 8032 // S2C: [i64 coachId]
	OpEndObservation         = 8038 // S2C: empty
	OpStartAction            = 8040 // S2C: empty
	OpNewTableTurnBegin      = 8100 // S2C: [i32 uid][i32 -1][i8 turn][i32 eventId]
	OpFighterTurnBegin       = 8104 // S2C: [i32 uid][i32 -1][i64 fighterId]
	OpFighterTurnEnd         = 8106 // S2C: [i32 uid][i32 -1][i64 fighterId]

	// Combat actions
	OpFighterMoveInFightReq = 4503 // C2S: [i64 fighterId] + path {i32 x,i32 y,i16 z}
	OpFighterMoveInFight    = 4524 // S2C: [i32 uid][i32 -1][i64 fighterId] + path
	OpFighterDirChangeReq   = 4521 // C2S lr_2 (arch 3): [i64 fighterId][u8 dir] — visual facing (qc_0), cosmetic
	OpFighterChangeDir      = 4522 // S2C u_0: [i32 uid][i32 -1][i64 fighterId][u8 dir]
	// Spell cast and in-fight CARD use are two DISTINCT actions with byte-identical
	// 22-byte requests (arch 3), which is how they were previously conflated (B-047):
	// the client's alx_2 holds a spell (yp_2) and sends 8109; abt_1 holds a card
	// (ve_0) and sends 8107. Their replies share one layout too.
	OpSpellCastRequest      = 8109 // C2S mc_2: [i64 fighterId][i32 spellId][i32 x][i32 y][i16 z]
	OpFighterCardUseRequest = 8107 // C2S sg_2: [i64 fighterId][i32 cardId][i32 x][i32 y][i16 z]
	OpFighterCardUse        = 8108 // S2C arn_0: header+[i64 user][i32 cardId][i8 miss](+crit+target); cardId resolved via the client card registry
	OpSpellCast             = 8110 // S2C: header+[i64 caster][i32 spell][i8 miss](+crit+target)
	OpCloseCombatRequest    = 8111 // C2S: [i64 fighterId][i32 x][i32 y][i16 z] (client aso_0, weapon attack)
	OpCloseCombat           = 8112 // S2C: header+[i64 attacker][i8 miss](+crit+target) (aAD, 17/28B)
	OpRunningEffect         = 8120 // S2C: header+[i8 now][i8 trig][i32 Nx][i32 effId][i16 len][blob]
	OpActionSequenceExecute = 8200 // S2C: empty (flush barrier)
	OpEffectAreaAction      = 6200 // S2C jD: header+[i8 entering][i64 areaInstanceId][i64 templateId][i64 fighterId] — plays a special cell / glyph animation
	// 8121 rq_2 ATTACHES a scripted effect to a fighter as a persistent buff
	// (of_1 adds it to the target's effect list and flags "hasBuff"); it never
	// EXECUTES one. Running an effect — including MapDestruction (mh_2 action 117,
	// sudden death) — goes through OpRunningEffect (8120) instead, whose handler
	// instantiates it and calls run(). Defined for the record; not emitted.
	OpRunScriptedEffect = 8121  // S2C rq_2: [i32 mh_2 actionId][i16 blobLen][blob][i64][i16][i8]
	OpFighterDies       = 4520  // S2C: [i32 uid][i32 -1][i64 fighterId]
	OpFighterTackled    = 4506  // S2C: [i32 uid][i32 -1][i64 tackledId][i64 tacklerId] (acg, 24B)
	OpEndFight          = 8300  // S2C: header+[i8 flee]+result
	OpEndFightDone      = 26321 // C2S: empty (ack result screen) (client nv_0, NOT 4321)

	// Mid-fight reconnect / resume. On reconnect the server pushes the empty
	// QUESTION (26333, client uz_0) while the coach is in the lobby; the client
	// shows a Yes/No "reconnectionInFightQuestion" dialog (pq_1) and replies with
	// the ANSWER (26334, client aiw_1) = 1 accept / 0 decline. On accept the server
	// replays the fight-presentation sequence (see sendFightResync).
	OpReconnectFightQuestion = 26333 // S2C: empty — "resume your fight?"
	OpReconnectFightAnswer   = 26334 // C2S (arch 2): [i8 accept] — 1=resume, 0=decline

	// Spectator. The client queries whether a coach is in a spectatable fight
	// (2260 py_0 → 2261 wv_2), and if so offers "enterSpectatorMode" which sends
	// the join (26331 x_0). 26332 (azb_0) tells a spectator's client to tear down
	// its local fight view.
	OpSpectateQuery    = 2260  // C2S: [i64 coachId] — is this coach in a spectatable fight?
	OpSpectateReply    = 2261  // S2C: [i8 spectatable]
	OpSpectateJoin     = 26331 // C2S (arch 2): [i64 coachId] — attach as a spectator to that coach's fight
	OpSpectateTeardown = 26332 // S2C: empty — local fight-view teardown

	// Direct challenge ("Proposer un entraînement" / training fight): coach A
	// directly challenges coach B. The handle carried on the wire (invitation /
	// accepted / cancelled) is the CHALLENGER's coach id; after B accepts, both
	// coaches confirm a team with OpFightReadyConfirm (26303) and the fight starts.
	OpChallengeInvite     = 26301 // C2S (arch 2): [i64 targetCoachId][i8 evoFlag] (hk_1)
	OpChallengeInvitation = 26300 // S2C: [i64 handle][i8 outgoing][i8 evo][i8 nNames]{[i32 len][name]} (wu_2)
	OpChallengeAccept     = 26305 // C2S (arch 2): [i64 handle][i8 evoFlag] (vT)
	OpChallengeAccepted   = 26302 // S2C: [i64 handle][i8 evo] (pu_1) — both open the team panel
	OpChallengeDecline    = 26307 // C2S (arch 2): [i64 handle] (mz_0) — decline / cancel / withdraw
	OpChallengeCancelled  = 26304 // S2C: [i64 handle] (gz_0) — the other side backed out
	// OpFightCreationError tells the client a fight could not be started and pops
	// its pending fight-setup states (do_2 / wg_2 / B). Without it, accepting an
	// overworld challenge leaves those states dangling with no feedback.
	OpFightCreationError = 26310 // S2C nx_1: [i64 fightId][i8 errorCode]

	// Fight-creation error codes carried by OpFightCreationError (client zN.M).
	FightErrUnableToCreate = 34 // "Impossible de créer le combat !"
	FightErrInternal       = 38
	FightErrNoInstance     = 39
	FightErrCancelled      = 40

	// Fireworks (the overworld "cardUsingSwitch" element, env type 12).
	OpFireworkShow   = 22094 // S2C la_1: [i32 cardId][i32 x][i32 y][i32 z][i64 elementId]
	OpFireworkLaunch = 22095 // C2S axf_0: [i32 cardId][i32 x][i32 y][i64 elementId]

	// Mailbox (the overworld mailbox element, env type 2). The client opens the
	// mailbox dialog ONLY when it receives OpMailList — replying to OpMailListRequest
	// is mandatory, an empty list is fine. See internal/game/handlers_mail.go.
	OpMailListRequest = 15000 // C2S ajs_0 (arch 3): empty — "open my mailbox"
	OpMailList        = 15001 // S2C ayV: [i16 count]{mail record} — opens the dialog
	OpMailSendResult  = 15003 // S2C Eh: [i64 result][mail record] (>0 ok, -2 full)
	OpMailDelete      = 15004 // C2S ads_0 (arch 3): [u8 count][i64 mailId]×count
	OpMailNewNotice   = 15005 // S2C wt_2: [u8 newMailCount] — "you have new mail" toast
	OpMailTakeCards   = 15006 // C2S akk_1 (arch 3): [i64 mailId](+1 pad byte)
	OpMailCardsTaken  = 15007 // S2C cb: [i64 mailId][i64 coachId][u8 n]{i32 cardId}
	OpMailSend        = 539   // C2S F (arch 3): a full mail record
	OpMailCheckName   = 15506 // C2S rr_1 (arch 2): [u8 len][utf8 coachName]
	OpMailNameResult  = 15507 // S2C afj_2: [i64 coachId] — 0 = no such coach

	// Mail send results carried by OpMailSendResult.
	MailSendOK   = 1  // any value > 0 = accepted
	MailSendFull = -2 // recipient's mailbox is full

	// Demon totems (env type 11) and tournament totems (env type 13). Clicking
	// either opens NOTHING by itself — the dialog is opened purely by the server's
	// reply, so without these the totems are inert.
	OpDemonLadderRequest = 27510 // C2S aid_1 (arch 2): [i16 demonId][i16 flag][i32 startRank] — per-demon drill-down
	OpDemonLadder        = 27511 // S2C anc_0: [i16 demonId][i16 flag][i32 start][i32 N]{guild,i64,i64,i64}[i64 affil]
	OpTournamentCalReq   = 17002 // C2S yq_1 (arch 3): empty
	OpTournamentCalendar = 17003 // S2C awa_0: [i16 count]{[i32 typeId=4][qr_0 tournament event]}
	OpTournamentListReq  = 28601 // C2S wa_2 (arch 2): empty
	OpTournamentList     = 28602 // S2C ng_2: [i32 count]{[i64 tid][u8 search][i8 status][i16 defId][u8 regOpen][i32 fp+i32[]][3×str32][u8 kind]}
	// Tournament registration + bracket. Registration is on the game/calendar arch
	// (3), not the tournament arch (2). The bracket reply IL(28650) is an empty tree
	// here (no live match graph): the client shows "tree unavailable" and closes.
	OpTournamentRegister      = 4607  // C2S aik_0 (arch 3): [i64 tid][i64 coachId][i16 teamPreset][i32 cardId]
	OpTournamentRegisterReply = 28608 // S2C dy_0: [i64 tid][i8 errorCode] (0=accepted, 2=full, else refused)
	OpTournamentTreeReq       = 28649 // C2S alf_0 (arch 2): [i64 tid][i32 round][i32 nameLen][name]
	OpTournamentTree          = 28650 // S2C IL: [i32 treeSize][i32 count]{[i32 id][i32 nameLen][name]}[i32 bib]

	// Evolution mode / graveyard. The client applies both of these OPTIMISTICALLY
	// and consumes no reply, so the server must reproduce the same state machine
	// and persist it; refreshed state reaches the client via 6006.
	OpFighterSetState  = 23000 // C2S Jc (arch 2): [i64 fighterId][u8 legendaryToggle]
	OpFighterUseItemOn = 22099 // C2S bw (arch 3): [i64 fighterId][i32 cardTemplateId]

	// Running-effect ids (2.70 client mh_2 / RunningEffectConstants). The client
	// looks the id up via mh_2.cr(); an unknown id makes case-8120 drop the
	// packet ("runningEffect inconnu"). Damage should instead be keyed to the
	// spell effect's own ActionID (1-5 direct / 130-134 "par sort"); this is only
	// the neutral fallback for a damage source with no spell effect.
	RunEffectHPLoss = 1  // mh_2 id 1 "Perte de point de vie" (neutral direct HP loss)
	RunEffectAPUse  = 91 // mh_2 id 91 "Utilisation de PA": SILENT AP debit (no chat) for action costs
	RunEffectMPUse  = 92 // mh_2 id 92 "Utilisation de PM": SILENT MP debit (no chat) for movement

	// Fight C2S
	OpReadyForPlacement   = 8011 // C2S: empty
	OpMoveToPlacementReq  = 8021 // C2S: [i64 fighterId][i32 x][i32 y][i16 z]
	OpReadyForObservation = 8023 // C2S: empty
	OpReadyForAction      = 8031 // C2S: empty
	OpFighterEndTurnReq   = 8105 // C2S: [i64 fighterId]
	OpGiveUpFight         = 8151 // C2S: empty

	// Fight creation (team-management panel buttons -> launch a fight)
	OpTeamTest             = 26330 // C2S (arch 2): [i32 fightType=12][i16 teamId] — alv_1, "Tester" (solo practice)
	OpClassicReadyForFight = 23103 // C2S (arch 2): [i64 coachId][i16 teamId] — atj_0, "Combattre" (ready-up + pair)
	OpFightReadyConfirm    = 26303 // C2S (arch 2): [i64 coachId][i16 teamId] — bl_1, in-fight "Prêt" team confirm

	// Matchmaking
	OpOpponentSearchRequest      = 2301  // C2S: [i16 mode][i16 sub][i32 N][i64×N ids]
	OpOpponentFound              = 2300  // S2C: opponent-found (opcode-only)
	OpOpponentSearchError        = 2302  // S2C: [i8 error]
	OpOpponentSearchCancel       = 2303  // C2S: empty
	OpOpponentSearchInProgress   = 2304  // S2C: empty
	OpOpponentSearchCancelResult = 2306  // S2C: [i8 result]
	OpMatchAcceptAlt             = 2308  // C2S: [i64 id][i16 mode][i32 N][ids][i8 accept]
	OpMatchFound                 = 23110 // S2C: match-found "do you accept?"
	OpMatchAccept                = 23114 // C2S: [i64 id][i64 opp][i16][i16][ids][i8 accept]
	OpMatchConfirm               = 23116 // S2C/C2S: [i32 N][i64×N] confirmed roster

	// Ladder — the ranking window has SEVEN tabs, one opcode pair each (client
	// afl_1, dialog ladderInformationDialog.xml). All C2S arch 2; strings are
	// [i32 len][utf8]; no length field is bounds-checked. Row loops index a
	// PRE-SIZED client list (list.get(n)), so an oversized row/total count throws
	// IndexOutOfBounds — empty boards must send zero counts, not just zero rows.
	OpLadderRequest      = 27500 // C2S dp_0: [i32 windowStart] — "1 vs 1" tab
	OpLadderResponse     = 27501 // S2C azd_0: 1v1 window (rows = end-start; trailing u8 searchBtn)
	OpGuildLadderRequest = 27502 // C2S pc_1: [i16 board][i32 windowStart] — "Clan" tab
	OpGuildLadder        = 27503 // S2C ij_1: [i16 board=1][i32 start][i32 N]{guild,leader,i32 score}
	OpLadder2v2Request   = 27504 // C2S vg_1: [i32 windowStart] — "2 vs 2" tab
	OpLadder2v2Response  = 27505 // S2C aka_0: 2v2 window (icon list; rows = end-start; trailing i32 searchBtn)
	// "Tournoi" tab (ladderInformation.tournamentType1Tab) — tournament POINTS,
	// three windows in ONE message: month, then trimester, then year.
	OpTournamentLadderRequest = 27506 // C2S qk_2: [i32 monthStart][i32 trimStart][i32 yearStart][i8 month][i8 trim][i16 year]
	OpTournamentLadder        = 27507 // S2C uj_0: [i8 m][i8 t][i16 y][i32 ptsM][i32 ptsT][i32 ptsY] + 3×{[i32 total][i32 start][i32 end][i32 myRank] rows{[i32 len][name][i32 pts]} [i8 search]}
	// Two reputation tabs (client afl_1):
	OpCoachReputationRequest = 27508 // C2S aa_2: [i32 startRank] — "Coach" reputation tab
	OpCoachReputation        = 27509 // S2C jw_0: derived-count window; trailing u8 searchBtn
	OpDemonListRequest       = 27512 // C2S ow_2: [i16 flag][i32 startIndex] — "Démon" tab (24-demon list)
	OpDemonList              = 27513 // S2C xn_2: [i16 flag][i32 start][i32 N]{i16 demonId, i64 repPts, guild}
	// "Ligue Pro" tab (ladderInformation.glickoRatingTab) — the pro-league board.
	OpProLeagueLadderRequest = 27514 // C2S ck_2: [i32 windowStart][i32 leagueId][i32 pageSize]
	OpProLeagueLadder        = 27515 // S2C amu_0: [i32 total][i32 start][i32 end][i32 myRank][i32 leagueId] rows{[i32 len][name][i32 len][guild][i16 rating]} [i8 search]

	// Chat
	OpVicinityContentMessage     = 3152 // S2C: sender name + i64 id + message
	OpUserVicinityContentMessage = 3153 // C2S: [u16 len]message
	OpPrivateContentMessage      = 3154 // S2C: sender name + i64 id + message
	OpUserPrivateContentMessage  = 3155 // C2S: [u8 len]target [u8 len]message
	OpChannelContentMessage      = 3140 // S2C: [u8 len]channel [u8 len]sender [u8 len]message
	OpUserChannelContentMessage  = 3151 // C2S: [u8 len]channel [u8 len]message
	OpChannelNotFound            = 3202 // S2C: [u8 len]channel
	OpUserNotFound               = 3204 // S2C: [u8 len]name

	// Social lists
	OpFriendList    = 3144 // S2C: [u8 count] + per friend {i16 len, blob}
	OpIgnoreList    = 3146 // S2C: [i8 count] + per ignore {u8 len, name}
	OpAddFriend     = 3129 // C2S: [u8 len]name
	OpAddIgnore     = 3131 // C2S: [u8 len]name
	OpRemoveFriend  = 3133 // C2S: [u8 len]name (or i64 id)
	OpRemoveIgnore  = 3135 // C2S
	OpFriendAdded   = 3156 // S2C
	OpFriendRemoved = 3160 // S2C
	OpIgnoreAdded   = 3158 // S2C
	OpIgnoreRemoved = 3162 // S2C

	// Presence notifications (pushed when a friend/ignored coach logs in/out).
	// Layouts verified vs the 2.70 client decoders dh_0/pv_0/jH/jf_0.
	OpFriendOnline  = 3148 // S2C: [u8 name][u8 s2][u8 s3][i64 id][i16][i8 sex][i64]
	OpFriendOffline = 3150 // S2C: [u8 name][u8 note]
	OpIgnoreOnline  = 3164 // S2C: [u8 name][i64 id]
	OpIgnoreOffline = 3166 // S2C: [u8 name]
)

// Auth result codes carried by OpClientAuthResult (1024).
const (
	AuthOK             = 0
	AuthInvalidLogin   = 2
	AuthAlreadyOnline  = 3
	AuthSaveInProgress = 4
	AuthClosedBeta     = 127
)

// Client version advertised in the OpClientVersion handshake. The client sends
// major=2, minor=70; the server must accept these or the client shows
// logon.invalidClientVersion.
const (
	VersionMajor = 2
	VersionMinor = 70
)
