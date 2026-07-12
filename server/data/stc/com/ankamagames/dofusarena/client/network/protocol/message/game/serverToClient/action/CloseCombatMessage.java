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
/*     */ public class CloseCombatMessage
/*     */   extends FightActionMessage
/*     */ {
/*     */   private long m_userId;
/*     */   private int m_usePositionX;
/*     */   private int m_usePositionY;
/*     */   private short m_usePositionZ;
/*     */   private boolean m_criticalHit;
/*     */   private boolean m_criticalMiss;
/*     */   
/*     */   public boolean decode(byte[] rawDatas)
/*     */   {
/*  34 */     if (!checkMessageSize(rawDatas.length, 17, false)) {
/*  35 */       return false;
/*     */     }
/*  37 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*     */     
/*  39 */     decodeFightActionHeader(bb);
/*     */     
/*  41 */     this.m_userId = bb.getLong();
/*  42 */     this.m_criticalMiss = (bb.get() == 1);
/*  43 */     if (!this.m_criticalMiss) {
/*  44 */       if (!checkMessageSize(rawDatas.length, 28, false))
/*  45 */         return false;
/*  46 */       this.m_criticalHit = (bb.get() == 1);
/*  47 */       this.m_usePositionX = bb.getInt();
/*  48 */       this.m_usePositionY = bb.getInt();
/*  49 */       this.m_usePositionZ = bb.getShort();
/*     */     } else {
/*  51 */       this.m_criticalHit = false;
/*     */     }
/*     */     
/*  54 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/*  64 */     return 8112;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getUserId()
/*     */   {
/*  71 */     return this.m_userId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getUsePositionX()
/*     */   {
/*  78 */     return this.m_usePositionX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getUsePositionY()
/*     */   {
/*  85 */     return this.m_usePositionY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public short getUsePositionZ()
/*     */   {
/*  92 */     return this.m_usePositionZ;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isCriticalHit()
/*     */   {
/*  99 */     return this.m_criticalHit;
/*     */   }
/*     */   
/*     */   public boolean isCriticalMiss() {
/* 103 */     return this.m_criticalMiss;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getActionId()
/*     */   {
/* 112 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FightActionType getFightActionType()
/*     */   {
/* 121 */     return FightActionType.CLOSE_COMBAT;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\action\CloseCombatMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */