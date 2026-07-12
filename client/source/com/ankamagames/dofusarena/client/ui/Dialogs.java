/*    */ package com.ankamagames.dofusarena.client.ui;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Dialogs
/*    */ {
/*    */   public static final String MESSAGE_BOX_ID = "messageBoxDialog";
/*    */   public static final String POPUP_ID = "popupDialog";
/*    */   public static final String PROGRESS_DIALOG_ID = "progressDialog";
/*    */   public static final String CONSOLE_DIALOG_ID = "consoleDialog";
/*    */   public static final String OPTIONS_DIALOG_ID = "optionsDialog";
/*    */   public static final String LOGON_DIALOG_ID = "logonDialog";
/*    */   public static final String MENU_DIALOG_ID = "menuDialog";
/*    */   public static final String MENU_BAR_DIALOG_ID = "menuBarDialog";
/*    */   public static final String EMOTES_BAR_DIALOG_ID = "emotesBarDialog";
/*    */   public static final String FIGHT_MENU_BAR_DIALOG_ID = "fightMenuBarDialog";
/*    */   public static final String CHAT_DIALOG_ID = "chatDialog";
/*    */   public static final String CONTACT_LIST_DIALOG_ID = "contactListDialog";
/*    */   public static final String PRIVATE_MESSAGE_DIALOG_ID = "privateMessageDialog";
/*    */   public static final String COACH_CREATION_DIALOG_ID = "coachCreationDialog";
/*    */   public static final String COACH_STATISTICS_DIALOG_ID = "coachStatisticsDialog";
/*    */   public static final String TEAM_MANAGEMENT_DIALOG_ID = "teamManagementDialog";
/*    */   public static final String FIGHTER_CREATION_DIALOG_ID = "fighterCreationDialog";
/*    */   public static final String FIGHTER_EDITION_DIALOG_ID = "fighterEditionDialog";
/*    */   public static final String RANDOM_FIGHT_CREATION_DIALOG_ID = "randomFightCreationDialog";
/*    */   public static final String RANDOM_FIGHT_SEARCH_STATUS_DIALOG_ID = "randomFightSearchStatusDialog";
/*    */   public static final String RANDOM_FIGHT_TEAM_MANAGEMENT_DIALOG_ID = "randomFightTeamManagementDialog";
/*    */   public static final String FIGHT_TEAM_MANAGEMENT_DIALOG_ID = "fightTeamManagementDialog";
/*    */   public static final String TIMELINE_DIALOG_ID = "timelineDialog";
/*    */   public static final String FIGHT_COUNTDOWN_DIALOG_ID = "fightCountdownDialog";
/*    */   public static final String FIGHT_EVENT_CARDS_DIALOG_ID = "fightEventCardsDialog";
/*    */   public static final String FIGHT_PRESENTATION_DIALOG_ID = "fightPresentationDialog";
/*    */   public static final String FIGHT_OBSERVATION_DIALOG_ID = "fightObservationDialog";
/*    */   public static final String FIGHT_PLACEMENT_DIALOG_ID = "fightPlacementDialog";
/*    */   public static final String FIGHT_RESULT_DIALOG_ID = "fightResultDialog";
/*    */   public static final String FIGHTER_CONTROLS_DIALOG_ID = "fighterControlsDialog";
/*    */   public static final String FIGHTER_INFORMATIONS_DIALOG_ID = "fighterInformationsDialog";
/*    */   public static final String COACH_EQUIPMENT_DIALOG_ID = "coachEquipmentDialog";
/*    */   public static final String COACH_INVENTORY_DIALOG_ID = "coachInventoryDialog";
/*    */   public static final String FUSION_LABORATORY_DIALOG_ID = "fusionLaboratoryDialog";
/*    */   public static final String SINGLE_CARD_DIALOG_ID = "singleCardDialog";
/*    */   public static final String EXCHANGE_DIALOG_ID = "exchangeDialog";
/*    */   public static final short MESSAGE_BOX_MODAL_LEVEL = 20000;
/*    */   public static final short POPUP_MODAL_LEVEL = 30000;
/*    */   public static final short PROGRESS_DIALOG_MODAL_LEVEL = 19500;
/*    */   public static final short CONSOLE_DIALOG_MODAL_LEVEL = 30000;
/*    */   public static final short OPTIONS_DIALOG_MODAL_LEVEL = 10000;
/*    */   public static final short LOGON_DIALOG_MODAL_LEVEL = 10000;
/*    */   public static final short MENU_DIALOG_MODAL_LEVEL = 19500;
/*    */   public static final short MENU_BAR_DIALOG_MODAL_LEVEL = 19000;
/*    */   public static final short EMOTES_BAR_DIALOG_MODAL_LEVEL = 12000;
/*    */   public static final short FIGHT_MENU_BAR_DIALOG_MODAL_LEVEL = 10000;
/*    */   public static final short CHAT_DIALOG_MODAL_LEVEL = 19000;
/*    */   public static final short CONTACT_LIST_DIALOG_MODAL_LEVEL = 19000;
/*    */   public static final short PRIVATE_MESSAGE_DIALOG_MODAL_LEVEL = 10000;
/*    */   public static final short COACH_CREATION_DIALOG_MODAL_LEVEL = 10000;
/*    */   public static final short COACH_STATISTICS_DIALOG_MODAL_LEVEL = 10000;
/*    */   public static final short TEAM_MANAGEMENT_DIALOG_MODAL_LEVEL = 10001;
/*    */   public static final short FIGHTER_EDITION_DIALOG_MODAL_LEVEL = 10002;
/*    */   public static final short FIGHTER_CREATION_DIALOG_MODAL_LEVEL = 10003;
/*    */   public static final short RANDOM_FIGHT_CREATION_DIALOG_MODAL_LEVEL = 12000;
/*    */   public static final short RANDOM_FIGHT_SEARCH_STATUS_DIALOG_MODAL_LEVEL = 12000;
/*    */   public static final short RANDOM_FIGHT_TEAM_MANAGEMENT_DIALOG_MODAL_LEVEL = 10001;
/*    */   public static final short FIGHT_TEAM_MANAGEMENT_DIALOG_MODAL_LEVEL = 10001;
/*    */   public static final short TIMELINE_DIALOG_MODAL_LEVEL = 10000;
/*    */   public static final short FIGHT_COUNTDOWN_DIALOG_MODAL_LEVEL = 10000;
/*    */   public static final short FIGHT_EVENT_CARDS_DIALOG_MODAL_LEVEL = 10000;
/*    */   public static final short FIGHT_PRESENTATION_DIALOG_MODAL_LEVEL = 10100;
/*    */   public static final short FIGHT_OBSERVATION_DIALOG_MODAL_LEVEL = 10000;
/*    */   public static final short FIGHT_PLACEMENT_DIALOG_MODAL_LEVEL = 10000;
/*    */   public static final short FIGHT_RESULT_DIALOG_MODAL_LEVEL = 10001;
/*    */   public static final short FIGHTER_CONTROLS_DIALOG_MODAL_LEVEL = 10000;
/*    */   public static final short FIGHTER_INFORMATIONS_DIALOG_MODAL_LEVEL = 10000;
/*    */   public static final short COACH_EQUIPMENT_DIALOG_MODAL_LEVEL = 10001;
/*    */   public static final short COACH_INVENTORY_DIALOG_MODAL_LEVEL = 10001;
/*    */   public static final short FUSION_LABORATORY_DIALOG_MODAL_LEVEL = 10001;
/*    */   public static final short SINGLE_CARD_DIALOG_MODAL_LEVEL = 10100;
/*    */   public static final short EXCHANGE_DIALOG_MODAL_LEVEL = 10001;
/*    */   public static final String CHAT_WINDOW_COMPONENT_ID = "chatDialog";
/*    */   
/*    */   public static String getDialogPath(String dialogId) {
/* 99 */     return String.format(DofusArenaConfiguration.getInstance().getDialogsPath(), new Object[] { dialogId });
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\Dialogs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */