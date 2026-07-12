/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.adviser.Adviser;
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
/*     */ import com.ankamagames.graphics.isometric.IsoWorldTarget;
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
/*     */ public class NetActorsFrame
/*     */   implements MessageFrame
/*     */ {
/*  36 */   protected static final Logger m_logger = Logger.getLogger(NetActorsFrame.class);
/*     */   
/*  38 */   private static NetActorsFrame m_instance = new NetActorsFrame();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetActorsFrame getInstance() {
/*  44 */     return m_instance;
/*     */   } public boolean onMessage(Message message) {
/*     */     ActorSpawnMessage actorSpawnMessage;
/*     */     ActorDespawnMessage actorDespawnMessage;
/*     */     ActorMovementMessage msg;
/*     */     Iterable<ActorHolder> actorHolders;
/*     */     ArrayList<Long> characterIds;
/*     */     PathMobile pathMobile;
/*     */     Iterator<Long> iterator;
/*  53 */     switch (message.getId()) {
/*     */       
/*     */       case 4096:
/*  56 */         actorSpawnMessage = (ActorSpawnMessage)message;
/*     */ 
/*     */         
/*  59 */         actorHolders = actorSpawnMessage.getActorHolders();
/*  60 */         for (ActorHolder actorHolder : actorHolders) {
/*  61 */           addMobile((Mobile)actorHolder.getActor());
/*     */         }
/*     */         
/*  64 */         return false;
/*     */ 
/*     */       
/*     */       case 4098:
/*  68 */         actorDespawnMessage = (ActorDespawnMessage)message;
/*     */ 
/*     */         
/*  71 */         characterIds = actorDespawnMessage.getActorIds();
/*  72 */         for (iterator = characterIds.iterator(); iterator.hasNext(); ) { long characterId = ((Long)iterator.next()).longValue();
/*  73 */           MobileManager.getInstance().removeMobile(characterId); }
/*     */ 
/*     */         
/*  76 */         return false;
/*     */ 
/*     */       
/*     */       case 4500:
/*  80 */         msg = (ActorMovementMessage)message;
/*     */ 
/*     */         
/*  83 */         pathMobile = (PathMobile)MobileManager.getInstance().getMobile(msg.getActorId());
/*  84 */         if (pathMobile != null) {
/*     */ 
/*     */           
/*  87 */           PathFindResult node = msg.getPathResult();
/*  88 */           pathMobile.setPath(node, true);
/*     */         } else {
/*     */           
/*  91 */           m_logger.error("Impossible de déplacer le personnage " + msg.getActorId() + " car il n'existe pas !");
/*     */         } 
/*     */         
/*  94 */         return false;
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
/*     */   
/*     */   public long getId() {
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
/*     */   public static void addMobile(Mobile mobile) {
/* 146 */     MobileManager.getInstance().addMobile(mobile);
/*     */     
/* 148 */     mobile.removeAllSelectionChangedListener();
/*     */     
/* 150 */     mobile.addSelectionChangedListener(new MobileSelectionChangeListener() { DofusArenaOverHeadInfos m_overHeadInfos;
/*     */           
/*     */           public void selectionChanged(Mobile mobile, boolean selected) {
/* 153 */             if (selected) {
/* 154 */               if (mobile instanceof Coach) {
/* 155 */                 Coach coach = (Coach)mobile;
/* 156 */                 this.m_overHeadInfos = new DofusArenaOverHeadInfos(String.valueOf(coach.getName()) + " (" + coach.getLevel() + ")");
/* 157 */                 this.m_overHeadInfos.setTarget((IsoWorldTarget)mobile);
/* 158 */                 AdviserManager.getInstance().addAdviser((Adviser)this.m_overHeadInfos);
/*     */               } 
/*     */             } else {
/* 161 */               AdviserManager.getInstance().removeAdviser((Adviser)this.m_overHeadInfos);
/*     */             } 
/*     */           } }
/*     */       );
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetActorsFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */