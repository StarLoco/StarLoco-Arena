/*     */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCardManager;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightActionType;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ public class FighterCardUseMessage
/*     */   extends FightActionMessage
/*     */ {
/*     */   private long m_userId;
/*     */   private FighterCard m_card;
/*     */   private int m_usePositionX;
/*     */   private int m_usePositionY;
/*     */   private short m_usePositionZ;
/*     */   private boolean m_criticalHit;
/*     */   private boolean m_criticalMiss;
/*     */   
/*     */   public boolean decode(byte[] rawDatas) {
/*  37 */     if (!checkMessageSize(rawDatas.length, 21, false)) {
/*  38 */       return false;
/*     */     }
/*  40 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*     */     
/*  42 */     decodeFightActionHeader(bb);
/*     */     
/*  44 */     this.m_userId = bb.getLong();
/*  45 */     this.m_card = (FighterCard)FighterCardManager.getInstance().get(bb.getInt());
/*  46 */     this.m_criticalMiss = (bb.get() == 1);
/*  47 */     if (!this.m_criticalMiss) {
/*  48 */       if (!checkMessageSize(rawDatas.length, 32, true))
/*  49 */         return false; 
/*  50 */       this.m_criticalHit = (bb.get() == 1);
/*  51 */       this.m_usePositionX = bb.getInt();
/*  52 */       this.m_usePositionY = bb.getInt();
/*  53 */       this.m_usePositionZ = bb.getShort();
/*     */     } else {
/*  55 */       this.m_criticalHit = false;
/*     */     } 
/*     */     
/*  58 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  68 */     return 8108;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getUserId() {
/*  75 */     return this.m_userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FighterCard getCard() {
/*  82 */     return this.m_card;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUsePositionX() {
/*  89 */     return this.m_usePositionX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUsePositionY() {
/*  96 */     return this.m_usePositionY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getUsePositionZ() {
/* 103 */     return this.m_usePositionZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCriticalHit() {
/* 110 */     return this.m_criticalHit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCriticalMiss() {
/* 117 */     return this.m_criticalMiss;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getActionId() {
/* 126 */     if (this.m_card != null) {
/* 127 */       return this.m_card.getId();
/*     */     }
/* 129 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FightActionType getFightActionType() {
/* 138 */     return FightActionType.CARD_USE;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\action\FighterCardUseMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */