/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphicalClient.ui.progress.ProgressMonitor;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Timeline;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.MoveToFreePlacementMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.TeamMateSetReadyForObservationMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.progress.DofusArenaProgressMonitorManager;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightPlacementFrame;
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
/*     */ 
/*     */ public class NetFightPlacementFrame
/*     */   implements MessageFrame
/*     */ {
/*  33 */   protected static final Logger m_logger = Logger.getLogger(NetFightPlacementFrame.class);
/*     */   
/*  35 */   private static NetFightPlacementFrame m_instance = new NetFightPlacementFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static NetFightPlacementFrame getInstance()
/*     */   {
/*  41 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  50 */     switch (message.getId())
/*     */     {
/*     */     case 8022: 
/*  53 */       MoveToFreePlacementMessage msg = (MoveToFreePlacementMessage)message;
/*     */       
/*  55 */       Fight fight = DofusArenaGameEntity.getInstance().getFight();
/*  56 */       if (fight != null) {
/*  57 */         Fighter fighter = (Fighter)fight.getFighterById(msg.getFighterId());
/*  58 */         if (fighter != null) {
/*  59 */           fighter.setPosition(msg.getWorldX(), msg.getWorldY(), msg.getAltitude());
/*     */         } else {
/*  61 */           m_logger.error("Le fighter " + msg.getFighterId() + " est inconnu !");
/*     */         }
/*     */       }
/*     */       
/*  65 */       return false;
/*     */     
/*     */ 
/*     */     case 8024: 
/*  69 */       TeamMateSetReadyForObservationMessage msg = (TeamMateSetReadyForObservationMessage)message;
/*     */       
/*  71 */       LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  72 */       if (localCoach != null) {
/*  73 */         Coach fightingCoach = localCoach.getFightingCoach();
/*  74 */         if ((fightingCoach != null) && 
/*  75 */           (fightingCoach.getId() == msg.getCoachId()))
/*     */         {
/*     */ 
/*  78 */           DofusArenaProgressMonitorManager.getInstance().getProgressMonitor(true).beginTask(DofusArenaTranslator.getInstance().getString("waitingForOpponents", new Object[0]), 0);
/*     */           
/*     */ 
/*  81 */           DofusArenaGameEntity.getInstance().removeFrame(UIFightPlacementFrame.getInstance());
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  87 */       return false;
/*     */     
/*     */ 
/*     */     case 8028: 
/*     */       try
/*     */       {
/*  93 */         DofusArenaGameEntity.getInstance().getFight().getTimeline().askForPlacementEnd();
/*     */       } catch (Exception e) {
/*  95 */         m_logger.error("Error : ", e);
/*     */       }
/*  97 */       return false;
/*     */     }
/*     */     
/*     */     
/* 101 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 110 */     return 0L;
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
/* 137 */     if (!isAboutToBeRemoved)
/*     */     {
/* 139 */       DofusArenaProgressMonitorManager.getInstance().done();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetFightPlacementFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */