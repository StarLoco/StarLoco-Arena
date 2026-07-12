/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.spell;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.iterators.ArrayIterator;
/*     */ import java.util.Iterator;
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
/*     */ public abstract class BasicSpellStep
/*     */   implements EffectContainer
/*     */ {
/*     */   private short m_startStep;
/*     */   private short m_endStep;
/*  25 */   protected Effect[] m_effects = new Effect[0];
/*     */   
/*     */   public BasicSpellStep(short start, short end)
/*     */   {
/*  29 */     this.m_startStep = start;
/*  30 */     this.m_endStep = end;
/*     */   }
/*     */   
/*     */   public short getStartLevel() {
/*  34 */     return this.m_startStep;
/*     */   }
/*     */   
/*     */   public void setStartLevel(short startLevel) {
/*  38 */     this.m_startStep = startLevel;
/*     */   }
/*     */   
/*     */   public short getEndLevel() {
/*  42 */     return this.m_endStep;
/*     */   }
/*     */   
/*     */   public void setEndLevel(short endLevel) {
/*  46 */     this.m_endStep = endLevel;
/*     */   }
/*     */   
/*     */ 
/*     */   public void addEffect(Effect effect)
/*     */   {
/*  52 */     if (effect == null)
/*  53 */       return;
/*  54 */     int effectsCount = this.m_effects.length;
/*  55 */     Effect[] newArray = new Effect[effectsCount + 1];
/*  56 */     System.arraycopy(this.m_effects, 0, newArray, 0, effectsCount);
/*  57 */     newArray[effectsCount] = effect;
/*  58 */     this.m_effects = newArray;
/*     */   }
/*     */   
/*     */   public void addEffects(Effect[] effects) {
/*  62 */     if ((effects == null) || (effects.length == 0))
/*  63 */       return;
/*  64 */     int effectsCount = this.m_effects.length;
/*  65 */     Effect[] newArray = new Effect[effectsCount + effects.length];
/*  66 */     System.arraycopy(this.m_effects, 0, newArray, 0, effectsCount);
/*  67 */     System.arraycopy(this.m_effects, effectsCount, effects, 0, effects.length);
/*  68 */     this.m_effects = newArray;
/*     */   }
/*     */   
/*     */   public void setEffects(Effect[] effects) {
/*  72 */     if (effects == null) {
/*  73 */       this.m_effects = new Effect[0];
/*  74 */       return;
/*     */     }
/*  76 */     this.m_effects = effects;
/*     */   }
/*     */   
/*     */   public Effect getEffectById(int effectId) {
/*     */     Effect[] arrayOfEffect;
/*  81 */     int j = (arrayOfEffect = this.m_effects).length; for (int i = 0; i < j; i++) { Effect eff = arrayOfEffect[i];
/*  82 */       if (eff.getEffectId() == effectId) {
/*  83 */         return eff;
/*     */       }
/*     */     }
/*  86 */     return null;
/*     */   }
/*     */   
/*     */   public long getEffectContainerId()
/*     */   {
/*  91 */     return 0L;
/*     */   }
/*     */   
/*     */   public int getContainerType() {
/*  95 */     return 2;
/*     */   }
/*     */   
/*     */   public int getEffectsCount() {
/*  99 */     return this.m_effects.length;
/*     */   }
/*     */   
/*     */   public Iterator<Effect> iterator() {
/* 103 */     return new ArrayIterator(this.m_effects, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\spell\BasicSpellStep.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */