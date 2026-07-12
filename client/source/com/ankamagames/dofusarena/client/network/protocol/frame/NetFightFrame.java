/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*     */ import com.ankamagames.dofusarena.client.core.preferences.FightPreferenceStore;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.FightCreationMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.progress.DofusArenaProgressMonitorManager;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightMenuBarFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightObservationFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightPlacementFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightPresentationFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightTeamManagementFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFighterFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIRandomFightTeamManagementFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UITeamManagementFrame;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.framework.preferences.PreferenceStore;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class NetFightFrame
/*     */   implements MessageFrame
/*     */ {
/*  43 */   protected static final Logger m_logger = Logger.getLogger(NetFightFrame.class);
/*     */   
/*  45 */   private static NetFightFrame m_instance = new NetFightFrame();
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetFightFrame getInstance() {
/*  50 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMessage(Message message) {
/*     */     FightCreationMessage msg;
/*  59 */     switch (message.getId()) {
/*     */       
/*     */       case 8000:
/*  62 */         msg = (FightCreationMessage)message;
/*     */         
/*  64 */         if (msg.getErrorCode() == 0) {
/*     */           
/*  66 */           Fight fight = msg.getFight();
/*  67 */           if (fight != null) {
/*     */ 
/*     */             
/*  70 */             DofusArenaGameEntity.getInstance().setFight(fight);
/*     */ 
/*     */             
/*  73 */             DofusArenaGameEntity.getInstance().removeFrame(NetFightCreationFrame.getInstance());
/*  74 */             DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UITeamManagementFrame.getInstance());
/*  75 */             DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFightTeamManagementFrame.getInstance());
/*  76 */             DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIRandomFightTeamManagementFrame.getInstance());
/*     */ 
/*     */             
/*  79 */             Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.timeline", fight.getTimeline());
/*     */             
/*  81 */             fight.start();
/*     */           } else {
/*     */             
/*  84 */             m_logger.error("Fight est null dans le StartFightMessage !");
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/*  89 */           Xulor.getInstance().msgBox("Erreur à la création du combat (" + msg.getErrorCode() + ") !", 66);
/*     */ 
/*     */           
/*  92 */           DofusArenaGameEntity.getInstance().removeFrame(this);
/*     */         } 
/*     */         
/*  95 */         return false;
/*     */ 
/*     */       
/*     */       case 8010:
/*     */         try {
/* 100 */           DofusArenaGameEntity.getInstance().getFight().getTimeline().askForPresentation();
/* 101 */         } catch (Exception e) {
/* 102 */           m_logger.error("Error START_PRESENTATION_MESSAGE : ", e);
/* 103 */           m_logger.error("Fight : " + DofusArenaGameEntity.getInstance().getFight());
/* 104 */           if (DofusArenaGameEntity.getInstance().getFight() != null) {
/* 105 */             m_logger.error("Timeline : " + DofusArenaGameEntity.getInstance().getFight().getTimeline());
/*     */           }
/*     */         } 
/* 108 */         return false;
/*     */ 
/*     */       
/*     */       case 8020:
/*     */         try {
/* 113 */           DofusArenaGameEntity.getInstance().getFight().getTimeline().askForStartPlacement();
/* 114 */         } catch (Exception e) {
/* 115 */           m_logger.error("Error START_PLACEMENT_MESSAGE : ", e);
/*     */         } 
/* 117 */         return false;
/*     */ 
/*     */       
/*     */       case 8030:
/*     */         try {
/* 122 */           DofusArenaGameEntity.getInstance().getFight().getTimeline().askForStartObservation();
/* 123 */         } catch (Exception e) {
/* 124 */           m_logger.error("Error START_OBSERVATION_MESSAGE : ", e);
/*     */         } 
/* 126 */         return false;
/*     */ 
/*     */       
/*     */       case 8040:
/*     */         try {
/* 131 */           DofusArenaGameEntity.getInstance().getFight().getTimeline().askForStartAction();
/* 132 */         } catch (Exception e) {
/* 133 */           m_logger.error("Error START_ACTION_MESSAGE : ", e);
/*     */         } 
/* 135 */         return false;
/*     */     } 
/*     */ 
/*     */     
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 148 */     return 0L;
/*     */   }
/*     */ 
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
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {
/* 167 */     if (!isAboutToBeAdded) {
/*     */ 
/*     */       
/* 170 */       DofusArenaClientInstance.getInstance().getPreferenceStore().pushChild((PreferenceStore)FightPreferenceStore.getInstance());
/*     */ 
/*     */       
/* 173 */       if (Xulor.getInstance().isLoaded("contactListDialog")) {
/* 174 */         Xulor.getInstance().unload("contactListDialog");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {
/* 187 */     if (!isAboutToBeRemoved) {
/*     */ 
/*     */       
/* 190 */       DofusArenaClientInstance.getInstance().getPreferenceStore().removeChild((PreferenceStore)FightPreferenceStore.getInstance());
/*     */ 
/*     */       
/* 193 */       DofusArenaProgressMonitorManager.getInstance().done();
/*     */ 
/*     */       
/* 196 */       Xulor.getInstance().unload("fightCountdownDialog");
/* 197 */       Xulor.getInstance().unload("fightEventCardsDialog");
/* 198 */       Xulor.getInstance().unload("timelineDialog");
/*     */ 
/*     */       
/* 201 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("fight.timeline");
/* 202 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("fight.timeline.selectedFighter");
/* 203 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("singleCardData");
/* 204 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("fight.eventCards");
/*     */ 
/*     */       
/* 207 */       DofusArenaGameEntity.getInstance().removeFrame(NetFightCreationFrame.getInstance());
/* 208 */       DofusArenaGameEntity.getInstance().removeFrame(NetFightActionFrame.getInstance());
/* 209 */       DofusArenaGameEntity.getInstance().removeFrame(NetFightActorsFrame.getInstance());
/* 210 */       DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFightPresentationFrame.getInstance());
/* 211 */       DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFightPlacementFrame.getInstance());
/* 212 */       DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFightObservationFrame.getInstance());
/* 213 */       DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFightFrame.getInstance());
/* 214 */       DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFighterFrame.getInstance());
/* 215 */       DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFightMenuBarFrame.getInstance());
/*     */ 
/*     */       
/* 218 */       DofusArenaGameEntity.getInstance().setFight(null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetFightFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */