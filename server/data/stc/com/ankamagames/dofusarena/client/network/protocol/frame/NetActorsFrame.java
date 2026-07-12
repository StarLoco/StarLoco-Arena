/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.adviser.AdviserManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileSelectionChangeListener;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.PathMobile;
/*     */ import com.ankamagames.dofusarena.client.alea.adviser.DofusArenaOverHeadInfos;
/*     */ import com.ankamagames.dofusarena.client.core.game.actor.ActorHolder;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorDespawnMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorMovementMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorSpawnMessage;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class NetActorsFrame
/*     */   implements MessageFrame
/*     */ {
/*  36 */   protected static final Logger m_logger = Logger.getLogger(NetActorsFrame.class);
/*     */   
/*  38 */   private static NetActorsFrame m_instance = new NetActorsFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static NetActorsFrame getInstance()
/*     */   {
/*  44 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  53 */     switch (message.getId())
/*     */     {
/*     */     case 4096: 
/*  56 */       ActorSpawnMessage msg = (ActorSpawnMessage)message;
/*     */       
/*     */ 
/*  59 */       Iterable<ActorHolder> actorHolders = msg.getActorHolders();
/*  60 */       for (ActorHolder actorHolder : actorHolders) {
/*  61 */         addMobile(actorHolder.getActor());
/*     */       }
/*     */       
/*  64 */       return false;
/*     */     
/*     */ 
/*     */     case 4098: 
/*  68 */       ActorDespawnMessage msg = (ActorDespawnMessage)message;
/*     */       
/*     */ 
/*  71 */       ArrayList<Long> characterIds = msg.getActorIds();
/*  72 */       for (Iterator localIterator2 = characterIds.iterator(); localIterator2.hasNext();) { long characterId = ((Long)localIterator2.next()).longValue();
/*  73 */         MobileManager.getInstance().removeMobile(characterId);
/*     */       }
/*     */       
/*  76 */       return false;
/*     */     
/*     */ 
/*     */     case 4500: 
/*  80 */       ActorMovementMessage msg = (ActorMovementMessage)message;
/*     */       
/*     */ 
/*  83 */       PathMobile pathMobile = (PathMobile)MobileManager.getInstance().getMobile(msg.getActorId());
/*  84 */       if (pathMobile != null)
/*     */       {
/*     */ 
/*  87 */         PathFindResult node = msg.getPathResult();
/*  88 */         pathMobile.setPath(node, true);
/*     */       }
/*     */       else {
/*  91 */         m_logger.error("Impossible de déplacer le personnage " + msg.getActorId() + " car il n'existe pas !");
/*     */       }
/*     */       
/*  94 */       return false;
/*     */     }
/*     */     
/*     */     
/*  98 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 107 */     return 0L;
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
/*     */ 
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void addMobile(Mobile mobile)
/*     */   {
/* 146 */     MobileManager.getInstance().addMobile(mobile);
/*     */     
/* 148 */     mobile.removeAllSelectionChangedListener();
/*     */     
/* 150 */     mobile.addSelectionChangedListener(new MobileSelectionChangeListener() {
/*     */       DofusArenaOverHeadInfos m_overHeadInfos;
/*     */       
/* 153 */       public void selectionChanged(Mobile mobile, boolean selected) { if (selected) {
/* 154 */           if ((mobile instanceof Coach)) {
/* 155 */             Coach coach = (Coach)mobile;
/* 156 */             this.m_overHeadInfos = new DofusArenaOverHeadInfos(coach.getName() + " (" + coach.getLevel() + ")");
/* 157 */             this.m_overHeadInfos.setTarget(mobile);
/* 158 */             AdviserManager.getInstance().addAdviser(this.m_overHeadInfos);
/*     */           }
/*     */         } else {
/* 161 */           AdviserManager.getInstance().removeAdviser(this.m_overHeadInfos);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetActorsFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */