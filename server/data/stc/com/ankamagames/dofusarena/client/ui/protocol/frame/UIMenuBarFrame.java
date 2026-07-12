/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetTeamManagementFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UIMenuBarFrame
/*     */   extends UIAbstractMenuBarFrame
/*     */ {
/*  24 */   private static UIMenuBarFrame m_instance = new UIMenuBarFrame();
/*     */   
/*     */   public static final String MENU_BAR_TEAM_MANAGEMENT_BUTTON = "menuBar.teamManagementButton";
/*     */   
/*     */   public static final String MENU_BAR_COACH_INVENTORY_BUTTON = "menuBar.coachInventoryButton";
/*     */   
/*     */   public static final String MENU_BAR_COACH_STATISTICS_BUTTON = "menuBar.coachStatisticsButton";
/*     */   public static final String MENU_BAR_RANDOM_FIGHT_BUTTON = "menuBar.randomFightButton";
/*     */   
/*     */   public static UIMenuBarFrame getInstance()
/*     */   {
/*  35 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  44 */     switch (message.getId())
/*     */     {
/*     */ 
/*     */     case 20009: 
/*  48 */       if (Xulor.getInstance().isLoaded("coachStatisticsDialog"))
/*     */       {
/*  50 */         DofusArenaGameEntity.getInstance().removeFrame(UICoachStatisticsFrame.getInstance());
/*     */       }
/*     */       else {
/*  53 */         DofusArenaGameEntity.getInstance().pushFrame(UICoachStatisticsFrame.getInstance());
/*     */       }
/*     */       
/*  56 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 20006: 
/*  61 */       if (Xulor.getInstance().isLoaded("coachInventoryDialog"))
/*     */       {
/*  63 */         DofusArenaGameEntity.getInstance().removeFrame(UICoachInventoryManagementFrame.getInstance());
/*     */ 
/*     */       }
/*  66 */       else if (DofusArenaGameEntity.getInstance().getLocalCoach().isInventorySynchronized()) {
/*  67 */         DofusArenaGameEntity.getInstance().pushFrame(UICoachInventoryManagementFrame.getInstance());
/*     */       }
/*     */       
/*     */ 
/*  71 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 20004: 
/*  76 */       if (Xulor.getInstance().isLoaded("teamManagementDialog"))
/*     */       {
/*  78 */         DofusArenaGameEntity.getInstance().removeFrame(UITeamManagementFrame.getInstance());
/*  79 */         DofusArenaGameEntity.getInstance().removeFrame(NetTeamManagementFrame.getInstance());
/*     */       }
/*     */       else
/*     */       {
/*  83 */         DofusArenaGameEntity.getInstance().pushFrame(UITeamManagementFrame.getInstance());
/*  84 */         DofusArenaGameEntity.getInstance().pushFrame(NetTeamManagementFrame.getInstance());
/*     */       }
/*     */       
/*     */ 
/*  88 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 19500: 
/*  93 */       if (!DofusArenaGameEntity.getInstance().isRandomFightSearchInProgress())
/*     */       {
/*  95 */         DofusArenaGameEntity.getInstance().pushFrame(UIRandomFightCreationFrame.getInstance());
/*     */       }
/*     */       
/*  98 */       return false;
/*     */     
/*     */ 
/*     */     case 20100: 
/* 102 */       if (Xulor.getInstance().isLoaded("teamManagementDialog")) {
/* 103 */         DofusArenaGameEntity.getInstance().removeFrame(UITeamManagementFrame.getInstance());
/* 104 */         DofusArenaGameEntity.getInstance().removeFrame(NetTeamManagementFrame.getInstance());
/*     */       }
/* 106 */       if (Xulor.getInstance().isLoaded("fightTeamManagementDialog")) {
/* 107 */         DofusArenaGameEntity.getInstance().removeFrame(UIFightTeamManagementFrame.getInstance());
/*     */       }
/* 109 */       if (Xulor.getInstance().isLoaded("coachInventoryDialog")) {
/* 110 */         DofusArenaGameEntity.getInstance().removeFrame(UICoachInventoryManagementFrame.getInstance());
/*     */       }
/* 112 */       if (Xulor.getInstance().isLoaded("randomFightCreationDialog")) {
/* 113 */         DofusArenaGameEntity.getInstance().removeFrame(UIRandomFightCreationFrame.getInstance());
/*     */       }
/* 115 */       if (Xulor.getInstance().isLoaded("coachStatisticsDialog")) {
/* 116 */         DofusArenaGameEntity.getInstance().removeFrame(UICoachStatisticsFrame.getInstance());
/*     */       }
/* 118 */       if (Xulor.getInstance().isLoaded("optionsDialog")) {
/* 119 */         Xulor.getInstance().unload("optionsDialog");
/*     */       }
/* 121 */       if (Xulor.getInstance().isLoaded("chatDialog")) {
/* 122 */         Xulor.getInstance().unload("chatDialog");
/*     */       }
/* 124 */       if (Xulor.getInstance().isLoaded("contactListDialog")) {
/* 125 */         Xulor.getInstance().unload("contactListDialog");
/*     */       }
/* 127 */       return false;
/*     */     }
/*     */     
/*     */     
/* 131 */     return super.onMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 140 */     return 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(long id) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded)
/*     */   {
/* 158 */     if (!isAboutToBeAdded)
/*     */     {
/*     */ 
/* 161 */       Xulor.getInstance().load("menuBarDialog", Dialogs.getDialogPath("menuBarDialog"), (short)19000);
/*     */       
/*     */ 
/* 164 */       enableAllMenuBarButtons();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void enableAllMenuBarButtons()
/*     */   {
/* 173 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.teamManagementButton", Boolean.valueOf(true));
/* 174 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.coachInventoryButton", Boolean.valueOf(true));
/* 175 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.coachStatisticsButton", Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved)
/*     */   {
/* 185 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */ 
/* 188 */       Xulor.getInstance().unload("menuBarDialog");
/*     */       
/*     */ 
/*     */ 
/* 192 */       Xulor.getInstance().unload("coachStatisticsDialog");
/*     */     }
/*     */     
/* 195 */     super.onFrameRemove(frameHandler, isAboutToBeRemoved);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIMenuBarFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */