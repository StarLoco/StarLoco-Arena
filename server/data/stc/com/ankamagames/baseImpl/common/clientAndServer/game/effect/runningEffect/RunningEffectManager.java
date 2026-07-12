/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class RunningEffectManager
/*     */   implements Iterable<RunningEffect>
/*     */ {
/*  25 */   protected static final Logger m_logger = Logger.getLogger(RunningEffectManager.class);
/*     */   
/*  27 */   private final ArrayList<RunningEffect> m_effects = new ArrayList();
/*     */   
/*     */   private boolean m_triggersEnabled;
/*     */   
/*     */   public static final byte CHECK_BOTH_TRIGGERS = 0;
/*     */   public static final byte CHECK_BEFORE_APPLY_TRIGGERS = 1;
/*     */   public static final byte CHECK_AFTER_APPLY_TRIGGERS = 2;
/*     */   
/*     */   public RunningEffectManager()
/*     */   {
/*  37 */     this.m_triggersEnabled = true;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  41 */     for (RunningEffect effect : this.m_effects)
/*     */     {
/*  43 */       effect.unapply();
/*     */     }
/*  45 */     this.m_effects.clear();
/*     */   }
/*     */   
/*     */   public void destroyAll() {
/*  49 */     for (RunningEffect effect : this.m_effects) {
/*  50 */       effect.release();
/*     */     }
/*  52 */     this.m_effects.clear();
/*     */   }
/*     */   
/*     */   public void disableTriggers()
/*     */   {
/*  57 */     this.m_triggersEnabled = false;
/*     */   }
/*     */   
/*     */   public void enableTriggers() {
/*  61 */     this.m_triggersEnabled = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void storeEffect(RunningEffect effect)
/*     */   {
/*  70 */     if (!this.m_effects.contains(effect)) {
/*  71 */       this.m_effects.add(effect);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void stackEffect(RunningEffect effect)
/*     */   {
/*  81 */     for (RunningEffect re : this.m_effects) {
/*  82 */       if (re.canBeStackedWith(effect)) {
/*  83 */         re.stackWith(effect);
/*  84 */         return;
/*     */       }
/*     */     }
/*  87 */     storeEffect(effect);
/*     */   }
/*     */   
/*     */   public boolean removeEffect(RunningEffect effect)
/*     */   {
/*  92 */     if (this.m_effects.remove(effect)) {
/*  93 */       effect.unapply();
/*  94 */       return true;
/*     */     }
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public void removeChildEffect(RunningEffect parentEffect) {
/* 100 */     List<RunningEffect> effectsToRemove = null;
/* 101 */     for (Iterator<RunningEffect> it = this.m_effects.iterator(); it.hasNext();) {
/* 102 */       RunningEffect re = (RunningEffect)it.next();
/* 103 */       if (re.getParent() == parentEffect) {
/* 104 */         if (effectsToRemove == null)
/* 105 */           effectsToRemove = new ArrayList();
/* 106 */         effectsToRemove.add(re);
/* 107 */         it.remove();
/*     */       }
/*     */     }
/* 110 */     if (effectsToRemove != null)
/* 111 */       unapplyEffects(effectsToRemove);
/*     */   }
/*     */   
/*     */   public void removeLinkedToCaster(EffectUser caster) {
/* 115 */     List<RunningEffect> effectsToRemove = null;
/* 116 */     for (Iterator<RunningEffect> it = this.m_effects.iterator(); it.hasNext();) {
/* 117 */       RunningEffect re = (RunningEffect)it.next();
/* 118 */       if (re.m_caster == caster) {
/* 119 */         if (effectsToRemove == null)
/* 120 */           effectsToRemove = new ArrayList();
/* 121 */         effectsToRemove.add(re);
/* 122 */         it.remove();
/*     */       }
/*     */     }
/* 125 */     if (effectsToRemove != null)
/* 126 */       unapplyEffects(effectsToRemove);
/*     */   }
/*     */   
/*     */   public void removeLinkedToContainer(EffectContainer container) {
/* 130 */     List<RunningEffect> effectsToRemove = null;
/* 131 */     for (Iterator<RunningEffect> it = this.m_effects.iterator(); it.hasNext();) {
/* 132 */       RunningEffect re = (RunningEffect)it.next();
/* 133 */       if (re.m_effectContainer == container) {
/* 134 */         if (effectsToRemove == null)
/* 135 */           effectsToRemove = new ArrayList();
/* 136 */         effectsToRemove.add(re);
/* 137 */         it.remove();
/*     */       }
/*     */     }
/* 140 */     if (effectsToRemove != null) {
/* 141 */       unapplyEffects(effectsToRemove);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void unapplyEffects(List<RunningEffect> effects) {
/* 146 */     if (effects == null)
/* 147 */       return;
/* 148 */     for (int i = 0; i < effects.size(); i++) {
/* 149 */       ((RunningEffect)effects.get(i)).unapply();
/*     */     }
/*     */   }
/*     */   
/*     */   public Iterator<RunningEffect> iterator() {
/* 154 */     return this.m_effects.iterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void trigger(RunningEffect linkedRE, byte check)
/*     */   {
/* 166 */     if (!this.m_triggersEnabled) {
/* 167 */       return;
/*     */     }
/* 169 */     if (linkedRE == null) {
/* 170 */       return;
/*     */     }
/* 172 */     if (linkedRE.getTriggersToExecute() == null) {
/* 173 */       return;
/*     */     }
/* 175 */     trigger(linkedRE.getTriggersToExecute(), check, linkedRE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean triggerSpecific(BitSet triggers)
/*     */   {
/* 186 */     if (!this.m_triggersEnabled) {
/* 187 */       return false;
/*     */     }
/* 189 */     if (triggers == null) {
/* 190 */       return false;
/*     */     }
/* 192 */     return trigger(triggers, (byte)0, null);
/*     */   }
/*     */   
/*     */   private boolean trigger(BitSet triggers, byte check, RunningEffect linkedRE) {
/* 196 */     boolean triggeredSomething = false;
/* 197 */     ArrayList<RunningEffect> effectToActivate = new ArrayList();
/* 198 */     ArrayList<RunningEffect> effectToDeactivate = new ArrayList();
/* 199 */     for (RunningEffect re : this.m_effects)
/*     */     {
/* 201 */       if ((re.getDeactivatedTriggersListening() != null) && (re.getDeactivatedTriggersListening().intersects(triggers))) {
/* 202 */         effectToDeactivate.add(re);
/* 203 */         triggeredSomething = true;
/*     */       }
/*     */       
/* 206 */       switch (check) {
/*     */       case 1: 
/* 208 */         if ((re.getActivatingTriggersListeningForBefore() != null) && (re.getActivatingTriggersListeningForBefore().intersects(triggers))) {
/* 209 */           effectToActivate.add(re);
/* 210 */           triggeredSomething = true;
/*     */         }
/* 212 */         break;
/*     */       
/*     */       case 2: 
/* 215 */         if ((re.getActivatingTriggersListeningDuring() != null) && (re.getActivatingTriggersListeningDuring().intersects(triggers))) {
/* 216 */           effectToActivate.add(re);
/* 217 */           triggeredSomething = true;
/*     */         }
/* 219 */         break;
/*     */       
/*     */       case 0: 
/* 222 */         if (((re.getActivatingTriggersListeningForBefore() != null) && (re.getActivatingTriggersListeningForBefore().intersects(triggers))) || (
/* 223 */           (re.getActivatingTriggersListeningDuring() != null) && (re.getActivatingTriggersListeningDuring().intersects(triggers)))) {
/* 224 */           effectToActivate.add(re);
/* 225 */           triggeredSomething = true;
/*     */         }
/*     */         break;
/*     */       }
/*     */       
/*     */     }
/* 231 */     if (triggeredSomething) {
/* 232 */       for (RunningEffect re : effectToDeactivate) {
/* 233 */         re.askForUnapplication();
/*     */       }
/* 235 */       for (RunningEffect re : effectToActivate) {
/* 236 */         re.executeOnTrigger(linkedRE);
/*     */       }
/*     */     }
/* 239 */     return triggeredSomething;
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/* 244 */     return this.m_effects.isEmpty();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\runningEffect\RunningEffectManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */