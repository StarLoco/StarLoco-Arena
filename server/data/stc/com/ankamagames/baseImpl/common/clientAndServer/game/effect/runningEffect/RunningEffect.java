/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.AbstractEffectManager;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.BasicTimeline;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeUnit;
/*     */ import com.ankamagames.framework.kernel.core.common.Releasable;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import org.apache.commons.pool.ObjectPool;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RunningEffect
/*     */   implements StaticRunningEffect, Releasable
/*     */ {
/*  51 */   protected static final Logger m_logger = Logger.getLogger(RunningEffect.class);
/*     */   
/*     */ 
/*     */ 
/*     */   protected ObjectPool m_pool;
/*     */   
/*     */ 
/*     */ 
/*     */   private byte m_referenceCount;
/*     */   
/*     */ 
/*     */ 
/*     */   protected Effect m_genericEffect;
/*     */   
/*     */ 
/*     */ 
/*     */   protected EffectContainer m_effectContainer;
/*     */   
/*     */ 
/*     */ 
/*     */   protected EffectUser m_caster;
/*     */   
/*     */ 
/*     */ 
/*     */   protected EffectUser m_target;
/*     */   
/*     */ 
/*     */ 
/*     */   protected Point3 m_targetCell;
/*     */   
/*     */ 
/*     */ 
/*     */   protected EffectContext m_context;
/*     */   
/*     */ 
/*     */ 
/*     */   protected TimeEvent m_linkedTimeEventInTimeline;
/*     */   
/*     */ 
/*     */   protected int m_id;
/*     */   
/*     */ 
/*     */   protected RunningEffectStatus m_status;
/*     */   
/*     */ 
/*     */   protected int m_value;
/*     */   
/*     */ 
/*     */   protected EnumSet<RunningEffectConfigFlags> m_configFlags;
/*     */   
/*     */ 
/* 102 */   protected final BitSet m_triggers = new BitSet();
/*     */   
/*     */ 
/*     */   protected int m_maxExecutionCount;
/*     */   
/*     */   private RunningEffect m_parent;
/*     */   
/* 109 */   protected boolean m_valueComputationEnabled = true;
/*     */   
/* 111 */   private final ArrayList<RunningEffect> m_stackedRunningEffect = new ArrayList();
/*     */   
/*     */ 
/*     */   private RunningEffect m_stackingRunningEffect;
/*     */   
/*     */   private boolean m_unapplied;
/*     */   
/*     */ 
/*     */   protected RunningEffect()
/*     */   {
/* 121 */     onCheckOut();
/*     */   }
/*     */   
/*     */   public int getId() {
/* 125 */     return this.m_id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/* 129 */     this.m_id = id;
/*     */   }
/*     */   
/*     */   public RunningEffectStatus getRunningEffectStatus()
/*     */   {
/* 134 */     return this.m_status;
/*     */   }
/*     */   
/*     */   public void setRunningEffectStatus(RunningEffectStatus status) {
/* 138 */     this.m_status = status;
/*     */   }
/*     */   
/*     */   public EffectUser getCaster() {
/* 142 */     return this.m_caster;
/*     */   }
/*     */   
/*     */   public EffectUser getTarget() {
/* 146 */     return this.m_target;
/*     */   }
/*     */   
/*     */   public EffectContainer getEffectContainer()
/*     */   {
/* 151 */     return this.m_effectContainer;
/*     */   }
/*     */   
/*     */   public void setTarget(EffectUser target) {
/* 155 */     this.m_target = target;
/*     */   }
/*     */   
/*     */   public void setEffectContainer(EffectContainer effectContainer)
/*     */   {
/* 160 */     this.m_effectContainer = effectContainer;
/*     */   }
/*     */   
/*     */   public void setContext(EffectContext context) {
/* 164 */     this.m_context = context;
/*     */   }
/*     */   
/*     */   public EffectContext getContext() {
/* 168 */     return this.m_context;
/*     */   }
/*     */   
/*     */   public int getValue() {
/* 172 */     return this.m_value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void forceValue(int forcedValue)
/*     */   {
/* 181 */     this.m_value = forcedValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mustBeExecutedNow()
/*     */   {
/* 190 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void release()
/*     */   {
/* 198 */     if (this.m_referenceCount > 0) {
/* 199 */       return;
/*     */     }
/*     */     
/* 202 */     if (this.m_pool != null) {
/*     */       try {
/* 204 */         this.m_pool.returnObject(this);
/*     */       } catch (Exception e) {
/* 206 */         m_logger.error("Exception dans le release de " + getClass().toString() + " normalement impossible");
/*     */       }
/* 208 */       this.m_pool = null;
/*     */     } else {
/* 210 */       onCheckIn();
/*     */     }
/*     */   }
/*     */   
/*     */   public Collection<EffectUser> determineTargets() {
/* 215 */     return determineTargets(this.m_genericEffect, this.m_caster, this.m_context, this.m_targetCell);
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
/*     */   public void run(Effect genericEffect, EffectContainer container, EffectContext context, EffectUser launcher, Point3 targetCell, boolean forceNow)
/*     */   {
/* 233 */     setParameters(genericEffect, container, context, launcher, null, targetCell);
/*     */     
/* 235 */     if ((!forceNow) && (hasApplyDelay())) {
/* 236 */       pushRunningEffectDelayedTimeEventInTimeline(genericEffect, container, context, launcher, targetCell);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 241 */     if ((useTargetCell()) && (!useTarget()))
/*     */     {
/* 243 */       RunningEffect reOnCellOnly = newParameterizedInstance(genericEffect, container, context, launcher, null, targetCell);
/* 244 */       if (reOnCellOnly != null) {
/* 245 */         if ((reOnCellOnly.hasDuration()) && (!reOnCellOnly.isInfinite())) {
/* 246 */           reOnCellOnly.pushRunningEffectDurationTimeEventInTimeline();
/*     */         }
/*     */         
/* 249 */         if (reOnCellOnly.isValueComputationEnabled())
/* 250 */           reOnCellOnly.computeValue(null);
/* 251 */         reOnCellOnly.execute(null, false);
/*     */       }
/*     */     }
/*     */     
/* 255 */     if (useTarget())
/*     */     {
/* 257 */       Collection<EffectUser> targets = determineTargets(genericEffect, launcher, context, targetCell);
/* 258 */       applyOnTargets((EffectUser[])targets.toArray(new EffectUser[0]));
/*     */     }
/*     */     
/*     */ 
/* 262 */     clearParameters();
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
/*     */   public final void executeOnTrigger(RunningEffect linkedRE)
/*     */   {
/* 275 */     if ((this.m_target != null) && (this.m_target.getRunningEffectManager() != null))
/*     */     {
/* 277 */       if (isValueComputationEnabled()) {
/* 278 */         computeValue(linkedRE);
/*     */       }
/* 280 */       this.m_target.getRunningEffectManager().trigger(this, (byte)1);
/* 281 */       if (this.m_target.isDead()) return;
/* 282 */       this.m_target.getRunningEffectManager().trigger(this, (byte)2);
/* 283 */       if (this.m_target.isDead()) { return;
/*     */       }
/* 285 */       execute(linkedRE, true);
/*     */     }
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
/*     */   protected boolean setParameters(Effect genericEffect, EffectContainer container, EffectContext context, EffectUser caster, EffectUser target, Point3 targetCell)
/*     */   {
/* 304 */     this.m_genericEffect = genericEffect;
/* 305 */     this.m_effectContainer = container;
/* 306 */     this.m_caster = caster;
/* 307 */     this.m_context = context;
/* 308 */     this.m_target = target;
/* 309 */     this.m_targetCell = targetCell;
/*     */     
/* 311 */     if (((useCaster()) && (caster == null)) || 
/* 312 */       ((useTarget()) && (target == null)) || (
/* 313 */       (useTargetCell()) && (targetCell == null))) {
/* 314 */       return false;
/*     */     }
/*     */     
/* 317 */     return true;
/*     */   }
/*     */   
/*     */   protected void clearParameters() {
/* 321 */     this.m_genericEffect = null;
/* 322 */     this.m_effectContainer = null;
/* 323 */     this.m_caster = null;
/* 324 */     this.m_context = null;
/* 325 */     this.m_target = null;
/* 326 */     this.m_targetCell = null;
/*     */   }
/*     */   
/*     */   public RunningEffect getParent()
/*     */   {
/* 331 */     return this.m_parent;
/*     */   }
/*     */   
/*     */   public void setParent(RunningEffect parent) {
/* 335 */     this.m_parent = parent;
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
/*     */   public RunningEffect newParameterizedInstance(Effect genericEffect, EffectContainer container, EffectContext context, EffectUser caster, EffectUser target, Point3 targetCell)
/*     */   {
/* 351 */     RunningEffect re = newInstance();
/* 352 */     re.setId(getId());
/* 353 */     re.setRunningEffectStatus(getRunningEffectStatus());
/* 354 */     re.setParameters(genericEffect, container, context, caster, target, targetCell);
/* 355 */     return re;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RunningEffect newUnserializedInstance(ByteBuffer dataBuff, EffectContext context, AbstractEffectManager manager)
/*     */   {
/* 367 */     RunningEffect re = newInstance();
/* 368 */     re.setId(getId());
/* 369 */     re.setRunningEffectStatus(getRunningEffectStatus());
/* 370 */     re.unserialize(dataBuff, context, manager);
/* 371 */     return re;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RunningEffect duplicateInstance()
/*     */   {
/* 381 */     RunningEffect re = newInstance();
/* 382 */     re.cloneParameters(this);
/* 383 */     return re;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract RunningEffect newInstance();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckOut()
/*     */   {
/* 396 */     this.m_unapplied = false;
/* 397 */     this.m_referenceCount = 0;
/* 398 */     this.m_maxExecutionCount = -1;
/* 399 */     this.m_caster = null;
/* 400 */     this.m_target = null;
/* 401 */     this.m_context = null;
/* 402 */     this.m_genericEffect = null;
/* 403 */     this.m_effectContainer = null;
/* 404 */     this.m_valueComputationEnabled = true;
/* 405 */     this.m_triggers.clear();
/* 406 */     this.m_stackingRunningEffect = null;
/* 407 */     this.m_stackedRunningEffect.clear();
/*     */   }
/*     */   
/*     */   public void onCheckIn() {
/* 411 */     this.m_unapplied = false;
/* 412 */     this.m_referenceCount = 0;
/* 413 */     removeRunningEffectFromTimeline();
/* 414 */     if (this.m_caster != null) {
/* 415 */       this.m_caster = null;
/*     */     }
/*     */     
/* 418 */     this.m_target = null;
/* 419 */     this.m_context = null;
/* 420 */     this.m_genericEffect = null;
/* 421 */     this.m_effectContainer = null;
/*     */     
/* 423 */     this.m_targetCell = null;
/*     */     
/* 425 */     this.m_linkedTimeEventInTimeline = null;
/*     */     
/* 427 */     this.m_value = 0;
/*     */     
/* 429 */     this.m_triggers.clear();
/*     */     
/* 431 */     this.m_maxExecutionCount = -1;
/*     */     
/* 433 */     this.m_parent = null;
/*     */     
/* 435 */     this.m_stackingRunningEffect = null;
/*     */     
/* 437 */     for (RunningEffect re : this.m_stackedRunningEffect) {
/* 438 */       re.release();
/*     */     }
/* 440 */     this.m_stackedRunningEffect.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void cloneParameters(RunningEffect re)
/*     */   {
/* 451 */     this.m_genericEffect = re.m_genericEffect;
/* 452 */     this.m_effectContainer = re.m_effectContainer;
/* 453 */     this.m_caster = re.m_caster;
/* 454 */     this.m_target = re.m_target;
/* 455 */     this.m_targetCell = re.m_targetCell;
/* 456 */     this.m_context = re.m_context;
/* 457 */     this.m_linkedTimeEventInTimeline = re.m_linkedTimeEventInTimeline;
/* 458 */     this.m_id = re.m_id;
/* 459 */     this.m_status = re.m_status;
/* 460 */     this.m_value = re.m_value;
/* 461 */     this.m_triggers.clear();
/* 462 */     this.m_triggers.or(re.m_triggers);
/* 463 */     this.m_maxExecutionCount = re.m_maxExecutionCount;
/* 464 */     this.m_parent = re.m_parent;
/*     */   }
/*     */   
/*     */   public void disableValueComputation()
/*     */   {
/* 469 */     this.m_valueComputationEnabled = false;
/*     */   }
/*     */   
/*     */   public boolean isValueComputationEnabled()
/*     */   {
/* 474 */     return this.m_valueComputationEnabled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitSet getActivatingTriggersListeningForBefore()
/*     */   {
/* 483 */     if (this.m_genericEffect == null)
/* 484 */       return null;
/* 485 */     return this.m_genericEffect.getTriggersBeforeToListen();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitSet getActivatingTriggersListeningDuring()
/*     */   {
/* 494 */     if (this.m_genericEffect == null)
/* 495 */       return null;
/* 496 */     return this.m_genericEffect.getTriggersAfterToListen();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addReference()
/*     */   {
/* 503 */     this.m_referenceCount = ((byte)(this.m_referenceCount + 1));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeReference()
/*     */   {
/* 510 */     if (this.m_referenceCount > 0) {
/* 511 */       this.m_referenceCount = ((byte)(this.m_referenceCount - 1));
/*     */     } else {
/* 513 */       m_logger.error("ON TENTE DE RETIRER PLUS DE REFERENCE QU'IL N'Y EN A SUR UN RUNNINGEFFECT");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitSet getDeactivatedTriggersListening()
/*     */   {
/* 523 */     if (this.m_genericEffect == null)
/* 524 */       return null;
/* 525 */     return this.m_genericEffect.getEndTriggers();
/*     */   }
/*     */   
/*     */   public BitSet getTriggersToExecute() {
/* 529 */     return this.m_triggers;
/*     */   }
/*     */   
/*     */   public void setTriggersToExecute() {
/* 533 */     this.m_triggers.clear();
/*     */   }
/*     */   
/*     */   public boolean mustBeTriggered() {
/* 537 */     if ((getActivatingTriggersListeningDuring() != null) && (getActivatingTriggersListeningDuring().length() > 0))
/* 538 */       return true;
/* 539 */     if ((getActivatingTriggersListeningForBefore() != null) && (getActivatingTriggersListeningForBefore().length() > 0))
/* 540 */       return true;
/* 541 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean useCaster();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean useTarget();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean useTargetCell();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(int whatToUpdate, float howMuchToUpate, boolean set) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyOnTargets(EffectUser... targets)
/*     */   {
/* 582 */     ArrayList<RunningEffect> runningEffectsToApply = new ArrayList();
/* 583 */     EffectUser[] arrayOfEffectUser; int j = (arrayOfEffectUser = targets).length; for (int i = 0; i < j; i++) { EffectUser target = arrayOfEffectUser[i];
/*     */       
/* 585 */       RunningEffect re = newParameterizedInstance(this.m_genericEffect, this.m_effectContainer, this.m_context, this.m_caster, target, this.m_targetCell);
/*     */       
/*     */ 
/* 588 */       if (!re.mustBeTriggered())
/*     */       {
/* 590 */         if (isValueComputationEnabled())
/* 591 */           re.computeValue(null);
/* 592 */         if (target != null)
/*     */         {
/* 594 */           if (target.getRunningEffectManager() != null) {
/* 595 */             target.getRunningEffectManager().trigger(re, (byte)1);
/*     */           }
/* 597 */           if ((re.getTarget() != null) && (re.getTarget().isDead())) return;
/* 598 */           if ((re.getCaster() != null) && (re.getCaster().isDead())) return;
/*     */         }
/* 600 */         runningEffectsToApply.add(re);
/*     */ 
/*     */       }
/* 603 */       else if ((re.hasDuration()) && (!re.isInfinite())) {
/* 604 */         re.pushRunningEffectDurationTimeEventInTimeline();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 609 */       if ((re.hasDuration()) && 
/* 610 */         (re.getTarget() != null) && (re.getTarget().getRunningEffectManager() != null))
/*     */       {
/* 612 */         if (re.mustBeStacked()) {
/* 613 */           re.getTarget().getRunningEffectManager().stackEffect(re);
/*     */         } else {
/* 615 */           re.getTarget().getRunningEffectManager().storeEffect(re);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 622 */     for (RunningEffect re : runningEffectsToApply)
/*     */     {
/*     */ 
/* 625 */       if ((re.getTarget() != null) && (re.getTarget().isDead())) return;
/* 626 */       if ((re.getCaster() != null) && (re.getCaster().isDead())) { return;
/*     */       }
/*     */       
/*     */ 
/* 630 */       if ((re.getTarget() != null) && (re.getTarget().getRunningEffectManager() != null)) {
/* 631 */         re.getTarget().getRunningEffectManager().trigger(re, (byte)2);
/*     */         
/* 633 */         if ((re.getTarget() != null) && (re.getTarget().isDead())) return;
/* 634 */         if ((re.getCaster() != null) && (re.getCaster().isDead())) { return;
/*     */         }
/*     */       }
/*     */       
/* 638 */       if ((re.hasDuration()) && (!re.isInfinite())) {
/* 639 */         re.pushRunningEffectDurationTimeEventInTimeline();
/*     */       }
/*     */       
/*     */ 
/* 643 */       if (re.getStackingRunningEffect() == null) {
/* 644 */         re.execute(null, false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void askForUnapplication()
/*     */   {
/* 651 */     RunningEffect parent = getStackingRunningEffect();
/* 652 */     if (parent != null) {
/* 653 */       parent.unStackWith(this);
/* 654 */       if (parent.getStackedRunningEffectCount() == 0) {
/* 655 */         parent.askForUnapplication();
/*     */       }
/*     */       
/*     */     }
/* 659 */     else if ((hasDuration()) && (this.m_target != null)) {
/* 660 */       if (!this.m_target.getRunningEffectManager().removeEffect(this))
/*     */       {
/* 662 */         unapply();
/*     */       }
/*     */     } else {
/* 665 */       unapply();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasBeenUnnaplied()
/*     */   {
/* 671 */     return this.m_unapplied;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void unapply()
/*     */   {
/* 678 */     checkEffectUserDeath();
/* 679 */     this.m_unapplied = true;
/* 680 */     release();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void pushRunningEffectDelayedTimeEventInTimeline(Effect paramEffect, EffectContainer paramEffectContainer, EffectContext paramEffectContext, EffectUser paramEffectUser, Point3 paramPoint3);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void pushRunningEffectDurationTimeEventInTimeline();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract TimeUnit getStartTime();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract TimeUnit getEndTime();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean hasApplyDelay();
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract boolean hasDuration();
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract boolean isInfinite();
/*     */   
/*     */ 
/*     */ 
/*     */   public void execute(RunningEffect triggerRE, boolean isTriggered)
/*     */   {
/* 720 */     checkEffectUserDeath();
/* 721 */     checkEffectValidityAfterExecution();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void checkEffectValidityAfterExecution()
/*     */   {
/* 730 */     boolean needToBeUnapply = false;
/*     */     
/*     */ 
/* 733 */     if (this.m_maxExecutionCount >= 0)
/*     */     {
/* 735 */       if (this.m_maxExecutionCount > 0) {
/* 736 */         this.m_maxExecutionCount -= 1;
/*     */       }
/*     */       
/* 739 */       if (this.m_maxExecutionCount == 0) {
/* 740 */         needToBeUnapply = true;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 746 */     if (((!hasDuration()) && (!mustBeTriggered())) || (needToBeUnapply)) {
/* 747 */       askForUnapplication();
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkEffectUserDeath() {
/* 752 */     if ((this.m_target != null) && (this.m_target.shouldBeDead()) && (!this.m_target.isDead())) {
/* 753 */       this.m_target.die();
/*     */     }
/* 755 */     if ((this.m_caster != null) && (this.m_caster.shouldBeDead()) && (!this.m_caster.isDead())) {
/* 756 */       this.m_caster.die();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void computeValue(RunningEffect paramRunningEffect);
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeRunningEffectFromTimeline()
/*     */   {
/* 769 */     if (this.m_linkedTimeEventInTimeline != null) {
/* 770 */       if (this.m_linkedTimeEventInTimeline.isValid())
/*     */       {
/* 772 */         if ((this.m_context != null) && (this.m_context.getTimeline() != null))
/* 773 */           this.m_context.getTimeline().removeTimeEvent(this.m_linkedTimeEventInTimeline);
/*     */       }
/* 775 */       this.m_linkedTimeEventInTimeline = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public RunningEffect getStackingRunningEffect()
/*     */   {
/* 781 */     return this.m_stackingRunningEffect;
/*     */   }
/*     */   
/*     */   public void setStackingRunningEffect(RunningEffect stackingRunningEffect) {
/* 785 */     this.m_stackingRunningEffect = stackingRunningEffect;
/*     */   }
/*     */   
/*     */   public int getStackedRunningEffectCount() {
/* 789 */     return this.m_stackedRunningEffect.size();
/*     */   }
/*     */   
/*     */   public Iterable<RunningEffect> getStackedRunningEffect() {
/* 793 */     return this.m_stackedRunningEffect;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mustBeStacked()
/*     */   {
/* 803 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canBeStackedWith(RunningEffect reToStack)
/*     */   {
/* 814 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void stackWith(RunningEffect reToStack)
/*     */   {
/* 824 */     if (isInfinite()) {
/* 825 */       reToStack.setStackingRunningEffect(this);
/* 826 */       this.m_stackedRunningEffect.add(reToStack);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unStackWith(RunningEffect reToStack)
/*     */   {
/* 837 */     this.m_stackedRunningEffect.remove(reToStack);
/* 838 */     reToStack.setStackingRunningEffect(null);
/*     */   }
/*     */   
/*     */   public abstract void serialize(ByteBuffer paramByteBuffer);
/*     */   
/*     */   public abstract boolean unserialize(ByteBuffer paramByteBuffer, EffectContext paramEffectContext, AbstractEffectManager paramAbstractEffectManager);
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\runningEffect\RunningEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */