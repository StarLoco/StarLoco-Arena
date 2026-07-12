/*     */ package com.ankamagames.dofusarena.client.core;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphicalClient.core.GameContentTranslator;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.framework.fileFormat.properties.PropertyException;
/*     */ import com.ankamagames.framework.kernel.core.translator.Language;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DofusArenaTranslator
/*     */   extends GameContentTranslator
/*     */ {
/*     */   public static final int FIGHTER_CARD_NAME_TRANSLATION_TYPE = 1;
/*     */   public static final int FIGHTER_CARD_BACKGROUND_DESCRIPTION_TRANSLATION_TYPE = 2;
/*     */   public static final int FIGHTER_CARD_FREE_DESCRIPTION_TRANSLATION_TYPE = 21;
/*     */   public static final int SPELL_NAME_TRANSLATION_TYPE = 3;
/*     */   public static final int SPELL_BACKGROUND_DESCRIPTION_TRANSLATION_TYPE = 4;
/*     */   public static final int SPELL_FREE_DESCRIPTION_TRANSLATION_TYPE = 20;
/*     */   public static final int EFFECT_DESCRIPTION_TRANSLATION_TYPE = 22;
/*     */   public static final int BREED_NAME_TRANSLATION_TYPE = 5;
/*     */   public static final int BREED_DESCRIPTION_TRANSLATION_TYPE = 6;
/*     */   public static final int EVENT_NAME_TRANSLATION_TYPE = 8;
/*     */   public static final int EVENT_BACKGROUND_DESCRIPTION_TRANSLATION_TYPE = 9;
/*     */   public static final int EVENT_FREE_DESCRIPTION_TRANSLATION_TYPE = 27;
/*     */   public static final int SUMMONING_NAME_TRANSLATION_TYPE = 10;
/*     */   public static final int SUMMONING_DESCRIPTION_TRANSLATION_TYPE = 11;
/*     */   public static final int COACH_CARD_NAME_TRANSLATION_TYPE = 23;
/*     */   public static final int COACH_CARD_DESCRIPTION_TRANSLATION_TYPE = 24;
/*     */   public static final int COACH_CARD_SET_NAME_TRANSLATION_TYPE = 25;
/*     */   public static final int COACH_CARD_SET_DESCRIPTION_TRANSLATION_TYPE = 26;
/*     */   public static final String DATE_FORMAT_HMS = "dateFormat.hms";
/*     */   public static final String DATE_FORMAT_MS = "dateFormat.ms";
/*     */   public static final String CHAT_PRIVATE_MESSAGE_TO = "chat.privateMessageTo";
/*     */   public static final String CHAT_PRIVATE_MESSAGE_FROM = "chat.privateMessageFrom";
/*     */   public static final String CHAT_FRIENDLIST = "chat.friendList";
/*     */   public static final String CHAT_IGNORELIST = "chat.ignoreList";
/*     */   public static final String CHAT_ADD_TO_FRIEND_LIST = "chat.addToFriendList";
/*     */   public static final String CHAT_REMOVE_FROM_FRIEND_LIST = "chat.removeFromFriendList";
/*     */   public static final String CHAT_ADD_TO_IGNORE_LIST = "chat.addToIgnoreList";
/*     */   public static final String CHAT_REMOVE_FROM_IGNORE_LIST = "chat.removeFromIgnoreList";
/*     */   public static final String CHAT_HELP_MESSAGE = "chat.help";
/*     */   public static final String CHAT_SEND_PRIVATE_MESSAGE = "chat.sendPrivateMessage";
/*     */   public static final String CHAT_NOTIFY_ADDFRIEND = "chat.notify.addFriend";
/*     */   public static final String CHAT_NOTIFY_REMOVEFRIEND = "chat.notify.removeFriend";
/*     */   public static final String CHAT_NOTIFY_ADDIGNORE = "chat.notify.addIgnore";
/*     */   public static final String CHAT_NOTIFY_REMOVEIGNORE = "chat.notify.removeIgnore";
/*     */   public static final String CHAT_NOTIFY_FRIENDONLINE = "chat.notify.friendOnline";
/*     */   public static final String CHAT_NOTIFY_FRIENDOFFLINE = "chat.notify.friendOffline";
/*     */   public static final String CHAT_NOTIFY_IGNOREONLINE = "chat.notify.ignoreOnline";
/*     */   public static final String CHAT_NOTIFY_IGNOREOFFLINE = "chat.notify.ignoreOffline";
/*     */   public static final String CHAT_PIPE_NAME_VICINITY = "chat.pipeName.vicinity";
/*     */   public static final String CHAT_PIPE_NAME_PRIVATE = "chat.pipeName.private";
/*     */   public static final String CHAT_PIPE_NAME_GAME_ERROR = "chat.pipeName.gameError";
/*     */   public static final String CHAT_PIPE_NAME_FIGHT_INFORMATION = "chat.pipeName.fightInformation";
/*     */   public static final String CHAT_PIPE_NAME_GAME_INFORMATION = "chat.pipeName.gameInformation";
/*     */   public static final String ERROR_CONNECTION_WORLD_LOADING = "error.connection.worldLoading";
/*     */   public static final String ERROR_CONNECTION_INVALID_LOGIN = "error.connection.invalidLogin";
/*     */   public static final String ERROR_CONNECTION_ALREADY_CONNECTED = "error.connection.alreadyConnected";
/*     */   public static final String ERROR_CONNECTION_SAVE_IN_PROGRESS = "error.connection.saveInProgress";
/*     */   public static final String ERROR_CONNECTION_CLOSED_BETA = "error.connection.closedBeta";
/*     */   public static final String ERROR_CHAT_USER_NOT_FOUND = "error.chat.userNotFound";
/*     */   public static final String ERROR_CHAT_CHANNEL_NOT_FOUND = "error.chat.channelNotFound";
/*     */   public static final String ERROR_CHAT_MALFORMED_COMMAND = "error.chat.malformedCommand";
/*     */   public static final String ERROR_CHAT_NOT_YET_IMPLEMENTED = "error.chat.notYetImplemented";
/*     */   public static final String ERROR_CHAT_NOT_ENOUGH_PRIVILEGES = "error.chat.notEnoughPrivileges";
/*     */   public static final String ERROR_CHAT_OPERATION_NOT_PERMITED = "error.chat.operationNotPermited";
/*     */   public static final String ERROR_CHAT_TARGET_IS_YOURSELF = "error.chat.targetIsYourself";
/*     */   public static final String ERROR_FIGHTER_CREATION_INVALID_NAME = "error.fighterCreation.invalidName";
/*     */   public static final String ERROR_COACH_CREATION_INVALID_NAME = "error.coachCreation.invalidName";
/*     */   public static final String ERROR_COACH_CREATION = "error.coachCreation";
/*     */   public static final String ERROR_TEAM_MANAGEMENT_TEAM_EMPTY = "error.teamManagement.teamEmpty";
/*     */   public static final String ERROR_TEAM_MANAGEMENT_BUDGET_EXPLODED = "error.teamManagement.budgetExploded";
/*     */   public static final String ERROR_TEAM_MANAGEMENT_FIGHTERS_COUNT_EXPLODED = "error.teamManagement.fightersCountExploded";
/*     */   public static final String ERROR_TEAM_MANAGEMENT_NO_MORE_ROOM = "error.teamManagement.noMoreRoom";
/*     */   public static final String ERROR_TEAM_MANAGEMENT_FIGHTER_CREATION = "error.teamManagement.fighterCreation";
/*     */   public static final String ERROR_TEAM_MANAGEMENT_FIGHTER_DELETION = "error.teamManagement.fighterDeletion";
/*     */   public static final String ERROR_TEAM_MANAGEMENT_FIGHTER_SAVE = "error.teamManagement.fighterSave";
/*     */   public static final String ERROR_TEAM_MANAGEMENT_TEAM_PRESET_SAVE = "error.teamManagement.teamPresetSave";
/*     */   public static final String ERROR_TEAM_MANAGEMENT_TEAM_PRESET_DELETION = "error.teamManagement.teamPresetDeletion";
/*     */   public static final String ERROR_COACH_MANAGEMENT_TOO_MANY_LOCKS = "error.coachManagement.tooManyLocks";
/*     */   public static final String ERROR_FIGHT_CREATION_TARGET_NOT_FOUND = "error.fight.creation.targetNotFound";
/*     */   public static final String ERROR_FIGHT_CREATION_TARGET_BUSY = "error.fight.creation.targetBusy";
/*     */   public static final String ERROR_FIGHT_CREATION_YOU_ARE_BUSY = "error.fight.creation.youreBusy";
/*     */   public static final String ERROR_FIGHT_CREATION_TARGET_IS_YOURSELF = "error.fight.creation.targetIsYourself";
/*     */   public static final String ERROR_FIGHT_CREATION_UNABLE_TO_CREATE_FIGHT = "error.fight.creation.unableToCreateFight";
/*     */   public static final String ERROR_FIGHT_CREATION_TARGET_NOT_CONNECTED = "error.fight.creation.targetDisconnected";
/*     */   public static final String ERROR_FIGHT_CREATION_TEAM_NOT_SELECTED = "error.fight.creation.noSelectedTeam";
/*     */   public static final String ERROR_FIGHT_CREATION_NO_PENDING_FIGHT = "error.fight.creation.noPendingFight";
/*     */   public static final String ERROR_FIGHT_CREATION_DURING_FIGHT_CREATION = "error.fight.creation.internalErrorDuringCreation";
/*     */   public static final String ERROR_FIGHT_CREATION_NO_INSTANCE_AVAILABLE = "error.fight.creation.noInstanceServer";
/*     */   public static final String ERROR_FIGHT_CREATION_CANCELED_BY_PLAYER = "error.fight.creation.canceledByOpponent";
/*     */   public static final String ERROR_FIGHT_CREATION_BAD_FIGHT_PARAMS = "error.fight.creation.badFightParameters";
/*     */   public static final String ERROR_FIGHT_CREATION_FIGHTERS_SELECTION_EMPTY = "error.fight.creation.noSelectedFighter";
/*     */   public static final String ERROR_FIGHT_CREATION_NOT_ENOUGH_FIGHTERS = "error.fight.creation.notEnoughFighters";
/*     */   public static final String ERROR_FIGHT_CREATION_NOT_ENOUGH_COACH_PER_TEAM = "error.fight.creation.notEnoughCoach";
/*     */   public static final String ERROR_FIGHT_CREATION_INVALID_FIGHTERS_COUNT = "error.fight.creation.invalidFightersCount";
/*     */   public static final String ERROR_FIGHT_CREATION_INVALID_TEAM_BUDGET = "error.fight.creation.invalidTeamBudget";
/*     */   public static final String ERROR_FIGHT_CREATION_FIGHTER_CANT_HOLD_THE_BET = "error.fight.creation.cantHoldTheBet";
/*     */   public static final String ERROR_LOADING = "error.loading";
/*     */   public static final String ERROR_UNSUPPORTED_MATERIAL = "error.unsupportedMaterial";
/*     */   public static final String LOADING = "loading";
/*     */   public static final String QUIT = "quit";
/*     */   public static final String DISCONNECT = "disconnect";
/*     */   public static final String OPTIONS = "options";
/*     */   public static final String MENU = "menu";
/*     */   public static final String DEFY = "defy";
/*     */   public static final String CLOSE_COMBAT = "closeCombat";
/*     */   public static final String CONTENT_LOADER_CONSOLE = "contentLoader.console";
/*     */   public static final String CONTENT_LOADER_CHAT = "contentLoader.chat";
/*     */   public static final String CONTENT_LOADER_ANIMATIONS = "contentLoader.animations";
/*     */   public static final String CONTENT_LOADER_SUMMONING = "contentLoader.summoning";
/*     */   public static final String CONTENT_LOADER_STATIC_EFFECT = "contentLoader.staticEffect";
/*     */   public static final String CONTENT_LOADER_SPELL = "contentLoader.spell";
/*     */   public static final String CONTENT_LOADER_FIGHT_DEFINITION = "contentLoader.fightDefinition";
/*     */   public static final String CONTENT_LOADER_EVENT = "contentLoader.event";
/*     */   public static final String CONTENT_LOADER_CARD = "contentLoader.card";
/*     */   public static final String LOGON_PROGRESS = "logon.progress";
/*     */   public static final String LOGON_NO_PROXY_AVAILABLE = "logon.noProxyAvailable";
/*     */   public static final String LOGON_INVALID_CLIENT_VERSION = "logon.invalidClientVersion";
/*     */   public static final String EXCHANGE_INVITATION = "exchange.invitation";
/*     */   public static final String EXCHANGE_PROPOSITION_CARD_COUNT = "exchange.propositionCardCount";
/*     */   public static final String EXCHANGE_INVITATION_MESSAGE_IN = "exchangeInvitation.messageIn";
/*     */   public static final String EXCHANGE_INVITATION_MESSAGE_OUT = "exchangeInvitation.messageOut";
/*     */   public static final String FIGHT_INVITATION_TRAINING = "fightInvitation.training";
/*     */   public static final String FIGHT_INVITATION_TRAINING_WITH_BET = "fightInvitation.trainingWithBet";
/*     */   public static final String FIGHT_INVITATION_MESSAGE_IN = "fightInvitation.messageIn";
/*     */   public static final String FIGHT_INVITATION_MESSAGE_OUT = "fightInvitation.messageOut";
/*     */   public static final String FIGHT_INVITATION_WITH_BET = "fightInvitation.withBet";
/*     */   public static final String QUESTION_QUIT = "question.quit";
/*     */   public static final String QUESTION_DISCONNECT = "question.disconnect";
/*     */   public static final String QUESTION_GIVE_UP_FIGHT = "question.giveUpFight";
/*     */   public static final String QUESTION_REMOVE_FIGHTER = "question.removeFighter";
/*     */   public static final String QUESTION_REMOVE_TEAM_PRESET = "question.removeTeamPreset";
/*     */   public static final String QUESTION_SAVE_EDITABLE_FIGHTER = "question.saveEditableFighter";
/*     */   public static final String QUESTION_COACH_DELETE_EQUIPMENT = "question.deleteCoachEquipment";
/*     */   public static final String INIT = "INIT";
/*     */   public static final String HP = "HP";
/*     */   public static final String AP = "AP";
/*     */   public static final String MP = "MP";
/*     */   public static final String WAITING_FOR_OPPONENTS = "waitingForOpponents";
/*     */   public static final String CONNECTION_CLOSED = "connection.closed";
/*     */   public static final String CONNECTION_RETRYING = "connection.retrying";
/*     */   public static final String TEAM_MANAGEMENT_TEAM_PRESET_LIST = "teamManagement.teamPresetList";
/*     */   public static final String COACH = "coach";
/*     */   public static final String COACH_STATISTICS = "coach.statistics";
/*     */   public static final String COACH_EQUIPMENT = "coach.equipment";
/*     */   public static final String COACH_CARD_COST_FROM = "coachCardCost.from";
/*     */   public static final String COACH_CARD_COST_TO = "coachCardCost.to";
/*     */   public static final String COACH_CARD_COST_LESS_THAN = "coachCardCost.lessThan";
/*     */   public static final String COACH_CARD_COST_MORE_THAN = "coachCardCost.moreThan";
/*     */   public static final String COACH_CARD_COST_ALL = "coachCardCost.all";
/*     */   public static final String COACH_CARD_COST_UNIT = "coachCardCost.unit";
/*     */   public static final String FIGHT_HISTORY_SPELL_CAST = "fight.spellCast";
/*     */   public static final String FIGHT_HISTORY_CARD_USE = "fight.cardUse";
/*     */   public static final String FIGHT_HISTORY_CLOSE_COMBAT = "fight.closeCombat";
/*     */   public static final String FIGHT_HISTORY_DIE = "fight.die";
/*     */   public static final String FIGHT_HISTORY_TACKLED = "fight.tackled";
/*     */   public static final String FIGHT_DURATION_VALUE = "fight.durationValue";
/*     */   public static final String CAST_EFFECT_DESCRIPTION = "cast.effectDescription";
/*     */   public static final String CAST_CRITICAL_EFFECT_DESCRIPTION = "cast.criticalEffectDescription";
/*     */   public static final String CAST_BONUS_DESCRIPTION = "cast.bonusDescription";
/*     */   public static final String CAST_RANGE_DESCRIPTION = "cast.rangeDescription";
/*     */   public static final String CAST_DURATION_DESCRIPTION = "cast.durationDescription";
/*     */   public static final String CAST_INFINITE_DURATION = "cast.infiniteDuration";
/*     */   public static final String CAST_USE_INFINITE_INTERVAL = "cast.useInfiniteInterval";
/*     */   public static final String CAST_USE_INTERVAL = "cast.useInterval";
/*     */   public static final String CAST_USE_MAX_PER_TURN = "cast.useMaxPerTurn";
/*     */   public static final String CAST_USE_MAX_PER_TARGET = "cast.useMaxPerTarget";
/*     */   public static final String CAST_TARGET_ENEMY = "cast.targetEnemy";
/*     */   public static final String CAST_TARGET_ALLY = "cast.targetAlly";
/*     */   public static final String CAST_TARGET_SUMMON = "cast.targetSummon";
/*     */   public static final String CAST_TARGET_FREE = "cast.targetFree";
/*     */   public static final String CAST_TARGET_FREE_CELL = "cast.targetFreeCell";
/*     */   public static final String CAST_TARGET_CASTER = "cast.targetCaster";
/* 236 */   private static DofusArenaTranslator m_instance = new DofusArenaTranslator();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DofusArenaTranslator() {
/* 242 */     String languageCode = DofusArenaClientInstance.getInstance().getGamePreferences().getLanguage();
/* 243 */     setLanguage(Language.getLanguage(languageCode));
/*     */     try {
/* 245 */       setPath(DofusArenaConfiguration.getInstance().getString("i18nPath"));
/* 246 */     } catch (PropertyException e) {
/* 247 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DofusArenaTranslator getInstance() {
/* 255 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLanguage(Language language) {
/* 265 */     super.setLanguage(language);
/* 266 */     DofusArenaClientInstance.getInstance().getGamePreferences().setLanguage(language.getLocale().getLanguage());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String formatDateWithDay(long miliseconde) {
/* 275 */     short days = (short)(int)(miliseconde / 86400000L);
/* 276 */     return String.valueOf((days > 0) ? (String.valueOf(days) + getString("formatDate.dayShort", new Object[0])) : "") + " " + formatDate(new Date(miliseconde), getString("dateFormat.hms", new Object[0]));
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\DofusArenaTranslator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */