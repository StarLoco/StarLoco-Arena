/*     */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action;
/*     */ 
/*     */ import com.ankamagames.dofusarena.common.game.effect.runningEffect.ArenaRunningEffect;
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
/*     */ public class RunningEffectActionMessage
/*     */   extends FightActionMessage
/*     */ {
/*     */   private int m_runningEffectId;
/*  21 */   byte[] m_serializedRunningEffect = new byte[ArenaRunningEffect.SERIALIZED_DATA_LENGTH_WITHOUT_ID];
/*     */   private boolean m_triggered;
/*  23 */   private boolean m_mustBeExecutedNow = false;
/*     */   
/*     */ 
/*     */   private long m_effectContainerId;
/*     */   
/*     */ 
/*     */   private int m_effectContainerType;
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean decode(byte[] rawDatas)
/*     */   {
/*  35 */     if (!checkMessageSize(rawDatas.length, 10 + ArenaRunningEffect.SERIALIZED_DATA_LENGTH_WITH_ID + 4 + 8, true)) {
/*  36 */       return false;
/*     */     }
/*  38 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*     */     
/*  40 */     decodeFightActionHeader(bb);
/*     */     
/*  42 */     this.m_mustBeExecutedNow = (bb.get() == 1);
/*  43 */     this.m_triggered = (bb.get() == 1);
/*     */     
/*  45 */     this.m_runningEffectId = bb.getInt();
/*     */     
/*  47 */     bb.get(this.m_serializedRunningEffect);
/*     */     
/*  49 */     this.m_effectContainerType = bb.getInt();
/*  50 */     this.m_effectContainerId = bb.getLong();
/*     */     
/*  52 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckIn()
/*     */   {
/*  61 */     super.onCheckIn();
/*  62 */     this.m_serializedRunningEffect = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckOut()
/*     */   {
/*  71 */     super.onCheckOut();
/*  72 */     this.m_serializedRunningEffect = new byte[ArenaRunningEffect.SERIALIZED_DATA_LENGTH_WITHOUT_ID];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/*  82 */     return 8120;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public byte[] getSerializedRunningEffect()
/*     */   {
/*  89 */     return this.m_serializedRunningEffect;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getRunningEffectId()
/*     */   {
/*  96 */     return this.m_runningEffectId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getActionId()
/*     */   {
/* 105 */     return this.m_runningEffectId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FightActionType getFightActionType()
/*     */   {
/* 114 */     return FightActionType.EFFECT_EXECUTION;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isTriggered()
/*     */   {
/* 121 */     return this.m_triggered;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean mustBeExecutedNow()
/*     */   {
/* 128 */     return this.m_mustBeExecutedNow;
/*     */   }
/*     */   
/*     */   public long getEffectContainerId()
/*     */   {
/* 133 */     return this.m_effectContainerId;
/*     */   }
/*     */   
/*     */   public int getEffectContainerType() {
/* 137 */     return this.m_effectContainerType;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\action\RunningEffectActionMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */