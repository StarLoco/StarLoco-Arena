/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphicalClient.ui.progress.ProgressMonitor;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Timeline;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.TeamMateSetReadyForActionMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.progress.DofusArenaProgressMonitorManager;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightObservationFrame;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
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
/*     */ public class NetFightObservationFrame
/*     */   implements MessageFrame
/*     */ {
/*  30 */   protected static final Logger m_logger = Logger.getLogger(NetFightObservationFrame.class);
/*     */   
/*  32 */   private static NetFightObservationFrame m_instance = new NetFightObservationFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static NetFightObservationFrame getInstance()
/*     */   {
/*  38 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  47 */     switch (message.getId())
/*     */     {
/*     */     case 8032: 
/*  50 */       TeamMateSetReadyForActionMessage msg = (TeamMateSetReadyForActionMessage)message;
/*     */       
/*  52 */       LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  53 */       if (localCoach != null) {
/*  54 */         Coach fightingCoach = localCoach.getFightingCoach();
/*  55 */         if ((fightingCoach != null) && 
/*  56 */           (fightingCoach.getId() == msg.getCoachId()))
/*     */         {
/*     */ 
/*  59 */           DofusArenaProgressMonitorManager.getInstance().getProgressMonitor(true).beginTask(DofusArenaTranslator.getInstance().getString("waitingForOpponents", new Object[0]), 0);
/*     */           
/*     */ 
/*  62 */           DofusArenaGameEntity.getInstance().removeFrame(UIFightObservationFrame.getInstance());
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  68 */       return false;
/*     */     
/*     */ 
/*     */     case 8038: 
/*     */       try
/*     */       {
/*  74 */         DofusArenaGameEntity.getInstance().getFight().getTimeline().askForObservationEnd();
/*     */       } catch (Exception e) {
/*  76 */         m_logger.error("Error : ", e);
/*     */       }
/*  78 */       return false;
/*     */     }
/*     */     
/*     */     
/*  82 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/*  91 */     return 0L;
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
/*     */ 
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved)
/*     */   {
/* 118 */     if (!isAboutToBeRemoved)
/*     */     {
/* 120 */       DofusArenaProgressMonitorManager.getInstance().done();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetFightObservationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */