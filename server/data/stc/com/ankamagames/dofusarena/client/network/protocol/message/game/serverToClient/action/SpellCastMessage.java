/*     */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action;
/*     */ 
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
/*     */ public class SpellCastMessage
/*     */   extends FightActionMessage
/*     */ {
/*     */   private long m_casterId;
/*     */   private int m_spellId;
/*     */   private int m_castPositionX;
/*     */   private int m_castPositionY;
/*     */   private short m_castPositionZ;
/*     */   private boolean m_criticalHit;
/*     */   private boolean m_criticalMiss;
/*     */   
/*     */   public boolean decode(byte[] rawDatas)
/*     */   {
/*  35 */     if (!checkMessageSize(rawDatas.length, 21, false)) {
/*  36 */       return false;
/*     */     }
/*  38 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*     */     
/*  40 */     decodeFightActionHeader(bb);
/*     */     
/*  42 */     this.m_casterId = bb.getLong();
/*  43 */     this.m_spellId = bb.getInt();
/*  44 */     this.m_criticalMiss = (bb.get() == 1);
/*  45 */     if (!this.m_criticalMiss) {
/*  46 */       if (!checkMessageSize(rawDatas.length, 32, false))
/*  47 */         return false;
/*  48 */       this.m_criticalHit = (bb.get() == 1);
/*  49 */       this.m_castPositionX = bb.getInt();
/*  50 */       this.m_castPositionY = bb.getInt();
/*  51 */       this.m_castPositionZ = bb.getShort();
/*     */     } else {
/*  53 */       this.m_criticalHit = false;
/*     */     }
/*     */     
/*  56 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/*  66 */     return 8110;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getCasterId()
/*     */   {
/*  73 */     return this.m_casterId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getSpellId()
/*     */   {
/*  80 */     return this.m_spellId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getCastPositionX()
/*     */   {
/*  87 */     return this.m_castPositionX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getCastPositionY()
/*     */   {
/*  94 */     return this.m_castPositionY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public short getCastPositionZ()
/*     */   {
/* 101 */     return this.m_castPositionZ;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isCriticalHit()
/*     */   {
/* 108 */     return this.m_criticalHit;
/*     */   }
/*     */   
/*     */   public boolean isCriticalMiss() {
/* 112 */     return this.m_criticalMiss;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getActionId()
/*     */   {
/* 121 */     return this.m_spellId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FightActionType getFightActionType()
/*     */   {
/* 130 */     return FightActionType.SPELL_CAST;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\action\SpellCastMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */