/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.effect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.Constants;
/*     */ import com.ankamagames.framework.ai.targetfinder.TargetValidator;
/*     */ import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffect;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import java.util.BitSet;
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
/*     */ public class Effect
/*     */ {
/*  35 */   protected static final Logger m_logger = Logger.getLogger(Effect.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int m_effectId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int m_actionId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String m_containerType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final float[] m_params;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final AreaOfEffect m_area;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final TargetValidator m_targetValidator;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long m_flags;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private final BitSet m_triggersBeforeToListen = new BitSet();
/*  79 */   private final BitSet m_triggersAfterToListen = new BitSet();
/*  80 */   private final BitSet m_endTriggers = new BitSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int[] m_duration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int[] m_applyDelay;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean m_affectedByLocalisation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Effect(int effectId, int actionId, String containerType, float[] params, AreaOfEffect area, int[] duration, int[] triggersBefore, int[] triggersAfter, int[] endTriggers, long flags, TargetValidator targetValidator, boolean affectedByLocalisation) {
/* 110 */     this.m_effectId = effectId;
/* 111 */     this.m_params = params;
/* 112 */     this.m_containerType = containerType;
/* 113 */     this.m_applyDelay = null;
/* 114 */     this.m_duration = duration;
/* 115 */     this.m_actionId = actionId;
/* 116 */     this.m_area = area;
/* 117 */     this.m_flags = flags;
/* 118 */     this.m_targetValidator = targetValidator;
/* 119 */     if (triggersBefore != null) {
/* 120 */       byte b; int i; int[] arrayOfInt; for (i = (arrayOfInt = triggersBefore).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/* 121 */         if (j > 0)
/* 122 */           this.m_triggersBeforeToListen.set(j);  b++; }
/*     */     
/* 124 */     }  if (triggersAfter != null) {
/* 125 */       byte b; int i; int[] arrayOfInt; for (i = (arrayOfInt = triggersAfter).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/* 126 */         if (j > 0)
/* 127 */           this.m_triggersAfterToListen.set(j);  b++; }
/*     */     
/* 129 */     }  if (endTriggers != null) {
/* 130 */       byte b; int i; int[] arrayOfInt; for (i = (arrayOfInt = endTriggers).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/* 131 */         if (j > 0)
/* 132 */           this.m_endTriggers.set(j);  b++; } 
/* 133 */     }  this.m_affectedByLocalisation = affectedByLocalisation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEffectId() {
/* 140 */     return this.m_effectId;
/*     */   }
/*     */   
/*     */   public int getActionId() {
/* 144 */     return this.m_actionId;
/*     */   }
/*     */   
/*     */   public float[] getParams() {
/* 148 */     return this.m_params;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getDuration() {
/* 153 */     return this.m_duration;
/*     */   }
/*     */   
/*     */   public int[] getApplyDelay() {
/* 157 */     return this.m_applyDelay;
/*     */   }
/*     */   
/*     */   public boolean checkFlags(long flagsToCheck) {
/* 161 */     return ((this.m_flags & flagsToCheck) == flagsToCheck);
/*     */   }
/*     */   
/*     */   public TargetValidator getTargetValidator() {
/* 165 */     return this.m_targetValidator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getParam(int num) {
/* 175 */     if (num >= 0 && num < this.m_params.length) {
/* 176 */       return this.m_params[num];
/*     */     }
/* 178 */     return -1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public BitSet getTriggersBeforeToListen() {
/* 183 */     return this.m_triggersBeforeToListen;
/*     */   }
/*     */   
/*     */   public BitSet getTriggersAfterToListen() {
/* 187 */     return this.m_triggersAfterToListen;
/*     */   }
/*     */   
/*     */   public BitSet getEndTriggers() {
/* 191 */     return this.m_endTriggers;
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
/*     */   public final void execute(EffectContainer cont, EffectUser launcher, EffectContext context, Constants<? extends StaticRunningEffect> constants, Point3 targetCell) {
/* 213 */     StaticRunningEffect sre = (StaticRunningEffect)constants.getObjectFromId(getActionId());
/*     */     
/* 215 */     sre.run(this, cont, context, launcher, targetCell, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public AreaOfEffect getAreaOfEffect() {
/* 220 */     return this.m_area;
/*     */   }
/*     */   
/*     */   public String getContainerType() {
/* 224 */     return this.m_containerType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAffectedByLocalisation() {
/* 229 */     return this.m_affectedByLocalisation;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\Effect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */