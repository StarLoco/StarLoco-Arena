/*     */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.coachManagement;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.ObjectPair;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
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
/*     */ public class CoachInventoryUpdateMessage
/*     */   extends InputOnlyProxyMessage
/*     */ {
/*  23 */   private final List<ObjectPair<Short, CoachCard>> m_equipmentAddedItems = new ArrayList<ObjectPair<Short, CoachCard>>();
/*  24 */   private final List<Short> m_equipmentRemovedItems = new ArrayList<Short>();
/*  25 */   private final List<CoachCard> m_inventoryAddedItems = new ArrayList<CoachCard>();
/*  26 */   private final List<Long> m_inventoryRemovedItems = new ArrayList<Long>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decode(byte[] rawDatas) {
/*  35 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*     */     
/*     */     short i;
/*  38 */     for (i = buffer.getShort(); i > 0; i = (short)(i - 1)) {
/*  39 */       short pos = buffer.getShort();
/*  40 */       CoachCard card = new CoachCard();
/*  41 */       if (!card.unserialize(buffer)) {
/*  42 */         m_logger.error("Impossible de désérialiser une carte pour une modification d'inventaire");
/*     */       } else {
/*     */         
/*  45 */         this.m_equipmentAddedItems.add(new ObjectPair(Short.valueOf(pos), card));
/*     */       } 
/*     */     } 
/*     */     
/*  49 */     for (i = buffer.getShort(); i > 0; i = (short)(i - 1)) {
/*  50 */       this.m_equipmentRemovedItems.add(Short.valueOf(buffer.getShort()));
/*     */     }
/*     */ 
/*     */     
/*  54 */     for (i = buffer.getShort(); i > 0; i = (short)(i - 1)) {
/*  55 */       CoachCard card = new CoachCard();
/*  56 */       if (!card.unserialize(buffer)) {
/*  57 */         m_logger.error("Impossible de désérialiser une carte pour une modification d'inventaire");
/*     */       } else {
/*     */         
/*  60 */         card.setQuantity(buffer.getShort());
/*  61 */         this.m_inventoryAddedItems.add(card);
/*     */       } 
/*     */     } 
/*     */     
/*  65 */     for (i = buffer.getShort(); i > 0; i = (short)(i - 1)) {
/*  66 */       this.m_inventoryRemovedItems.add(Long.valueOf(buffer.getLong()));
/*     */     }
/*     */     
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  78 */     return 5200;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ObjectPair<Short, CoachCard>> getEquipmentAddedItems() {
/*  85 */     return this.m_equipmentAddedItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Short> getEquipmentRemovedItems() {
/*  92 */     return this.m_equipmentRemovedItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CoachCard> getInventoryAddedItems() {
/*  99 */     return this.m_inventoryAddedItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Long> getInventoryRemovedItems() {
/* 106 */     return this.m_inventoryRemovedItems;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\coachManagement\CoachInventoryUpdateMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */