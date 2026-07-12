/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.coachManagement.CoachEquipmentUpdateMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.coachManagement.CoachInventoryUpdateMessage;
/*     */ import com.ankamagames.dofusarena.common.game.card.AbstractCoachCard;
/*     */ import com.ankamagames.dofusarena.common.game.card.CoachCardInventories;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.ObjectPair;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class NetCoachUpdateFrame
/*     */   implements MessageFrame
/*     */ {
/*  33 */   private static NetCoachUpdateFrame m_instance = new NetCoachUpdateFrame();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetCoachUpdateFrame getInstance() {
/*  39 */     return m_instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMessage(Message message) {
/*     */     CoachEquipmentUpdateMessage coachEquipmentUpdateMessage;
/*     */     CoachInventoryUpdateMessage msg;
/*     */     Mobile mobile;
/*     */     LocalCoach localCoach;
/*  48 */     switch (message.getId()) {
/*     */       
/*     */       case 5202:
/*  51 */         coachEquipmentUpdateMessage = (CoachEquipmentUpdateMessage)message;
/*     */         
/*  53 */         mobile = MobileManager.getInstance().getMobile(coachEquipmentUpdateMessage.getCoachId());
/*  54 */         if (mobile instanceof Coach) {
/*  55 */           Coach coach = (Coach)mobile;
/*  56 */           coach.getCardInventories().unserializeEquipment(coachEquipmentUpdateMessage.getEquipmentData());
/*     */         } 
/*     */         
/*  59 */         return false;
/*     */ 
/*     */       
/*     */       case 5200:
/*  63 */         msg = (CoachInventoryUpdateMessage)message;
/*     */ 
/*     */         
/*  66 */         localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  67 */         if (localCoach != null) {
/*     */           
/*  69 */           CoachCardInventories<CoachCard> cards = localCoach.getCardInventories();
/*     */ 
/*     */           
/*  72 */           List<Short> removedEquipmentPositions = msg.getEquipmentRemovedItems();
/*  73 */           for (Iterator<Short> iterator = removedEquipmentPositions.iterator(); iterator.hasNext(); ) { short position = ((Short)iterator.next()).shortValue();
/*  74 */             cards.removeEquipmentAt(position); }
/*     */ 
/*     */ 
/*     */           
/*  78 */           List<ObjectPair<Short, CoachCard>> addedEquipments = msg.getEquipmentAddedItems();
/*  79 */           for (ObjectPair<Short, CoachCard> addedPair : addedEquipments) {
/*  80 */             short pos = ((Short)addedPair.getFirst()).shortValue();
/*  81 */             CoachCard addedEquipment = (CoachCard)addedPair.getSecond();
/*  82 */             if (cards.getEquipmentAt(pos) == null) {
/*  83 */               cards.removeEquipmentAt(pos);
/*     */             }
/*     */             try {
/*  86 */               cards.addEquipmentAt((AbstractCoachCard)addedEquipment, pos);
/*  87 */             } catch (Exception e) {
/*  88 */               e.printStackTrace();
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/*  93 */           List<CoachCard> addedItems = msg.getInventoryAddedItems();
/*  94 */           for (CoachCard addedItem : addedItems) {
/*     */             try {
/*  96 */               if (cards.containsInInventory((AbstractCoachCard)addedItem)) {
/*  97 */                 cards.removeFromInventory((AbstractCoachCard)addedItem);
/*     */               }
/*  99 */               cards.addToInventory((AbstractCoachCard)addedItem);
/* 100 */             } catch (Exception exception) {}
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 105 */           List<Long> removedItemUniqueIds = msg.getInventoryRemovedItems();
/* 106 */           for (Iterator<Long> iterator1 = removedItemUniqueIds.iterator(); iterator1.hasNext(); ) { long itemUniqueId = ((Long)iterator1.next()).longValue();
/* 107 */             cards.removeFromInventory(itemUniqueId); }
/*     */           
/* 109 */           localCoach.setInventorySynchronized(true);
/*     */         } 
/* 111 */         return false;
/*     */     } 
/*     */ 
/*     */     
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 124 */     return 0L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetCoachUpdateFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */