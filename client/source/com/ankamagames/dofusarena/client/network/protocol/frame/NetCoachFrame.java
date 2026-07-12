/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.CoachCreationResultMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.CoachInformationsMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.PlayerStatisticsReportMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UICoachCreationFrame;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
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
/*     */ public class NetCoachFrame
/*     */   implements MessageFrame
/*     */ {
/*  32 */   protected static final Logger m_logger = Logger.getLogger(NetCoachFrame.class);
/*     */   
/*  34 */   private static NetCoachFrame m_instance = new NetCoachFrame();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetCoachFrame getInstance() {
/*  40 */     return m_instance;
/*     */   }
/*     */   
/*     */   public boolean onMessage(Message message) {
/*     */     LocalCoach emptyLocalCoach;
/*     */     CoachCreationResultMessage coachCreationResultMessage;
/*     */     CoachInformationsMessage coachInformationsMessage;
/*     */     PlayerStatisticsReportMessage msg;
/*     */     LocalCoach localCoach;
/*  49 */     switch (message.getId()) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 2048:
/*  54 */         emptyLocalCoach = new LocalCoach();
/*  55 */         emptyLocalCoach.randomizeLook();
/*     */         
/*  57 */         DofusArenaGameEntity.getInstance().setLocalCoach(emptyLocalCoach);
/*     */ 
/*     */         
/*  60 */         DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UICoachCreationFrame.getInstance());
/*     */         
/*  62 */         return false;
/*     */ 
/*     */       
/*     */       case 2050:
/*  66 */         coachCreationResultMessage = (CoachCreationResultMessage)message;
/*     */ 
/*     */         
/*  69 */         switch (coachCreationResultMessage.getErrorCode()) {
/*     */ 
/*     */ 
/*     */           
/*     */           case 0:
/*  74 */             DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UICoachCreationFrame.getInstance());
/*     */             break;
/*     */ 
/*     */           
/*     */           case 11:
/*     */           case 12:
/*  80 */             Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.coachCreation.invalidName", new Object[] { Byte.valueOf(coachCreationResultMessage.getErrorCode()) }), 66);
/*     */             break;
/*     */           case 10:
/*     */           case 13:
/*  84 */             Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.coachCreation", new Object[] { Byte.valueOf(coachCreationResultMessage.getErrorCode()) }), 66);
/*     */             break;
/*     */         } 
/*     */         
/*  88 */         return false;
/*     */ 
/*     */       
/*     */       case 2052:
/*  92 */         coachInformationsMessage = (CoachInformationsMessage)message;
/*     */ 
/*     */         
/*  95 */         DofusArenaGameEntity.getInstance().setLocalCoach(coachInformationsMessage.getLocalCoach());
/*     */ 
/*     */         
/*  98 */         DofusArenaGameEntity.getInstance().pushFrame(NetCoachUpdateFrame.getInstance());
/*     */         
/* 100 */         return false;
/*     */ 
/*     */       
/*     */       case 2400:
/* 104 */         msg = (PlayerStatisticsReportMessage)message;
/*     */ 
/*     */         
/* 107 */         localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/* 108 */         if (localCoach != null) {
/* 109 */           localCoach.setStatisticsReport(msg.getPlayerStatisics());
/*     */         } else {
/* 111 */           m_logger.error("Impossible de sauvegerde les statistiques de coach si aucun coach local n'est défini !");
/*     */         } 
/*     */         
/* 114 */         return false;
/*     */     } 
/*     */ 
/*     */     
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 127 */     return 0L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetCoachFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */