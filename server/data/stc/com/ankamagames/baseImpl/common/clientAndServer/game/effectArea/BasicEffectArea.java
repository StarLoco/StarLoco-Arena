/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.effectArea;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUserInformationProvider;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffectManager;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.part.PartLocalisator;
/*     */ import com.ankamagames.framework.ai.targetfinder.Target;
/*     */ import com.ankamagames.framework.ai.targetfinder.TargetValidator;
/*     */ import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffect;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.GrowingArray;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BasicEffectArea
/*     */   implements EffectContainer, Poolable, EffectUser
/*     */ {
/*  32 */   protected static final Logger m_logger = Logger.getLogger(BasicEffectArea.class);
/*     */   
/*     */   protected BitSet m_applicationTriggers;
/*     */   
/*     */   protected BitSet m_unapplicationTriggers;
/*     */   
/*     */   protected GrowingArray<Effect> m_effects;
/*     */   
/*     */   protected long m_baseId;
/*     */   
/*     */   protected long m_id;
/*     */   
/*  44 */   protected final Point3 m_position = new Point3();
/*     */   
/*     */   protected AreaOfEffect m_area;
/*     */   
/*     */   protected EffectContext m_context;
/*     */   
/*     */   protected ObjectPool m_pool;
/*     */   
/*     */   protected EffectUser m_owner;
/*     */   
/*     */   protected int m_maxExecutionCount;
/*     */   
/*  56 */   protected final ArrayList<Target> m_unactiveForTargets = new ArrayList();
/*     */   
/*     */   protected int m_activateCondition;
/*     */   
/*     */   protected int[] m_deactivationDelay;
/*     */   
/*     */   private EffectAreaActionListener m_listener;
/*     */   
/*     */   protected int m_targetsToShow;
/*     */   
/*     */   protected TargetValidator m_applicationValidator;
/*     */   
/*     */   protected TargetValidator m_unapplicationValidator;
/*     */   
/*     */ 
/*     */   protected BasicEffectArea() {}
/*     */   
/*     */   public BasicEffectArea(int baseId, AreaOfEffect area, BitSet applicationtriggers, BitSet unapplicationtriggers, int maxExecutionCount, int targetsToShow, int[] deactivationDelay, int applicationCondition, TargetValidator applicationValidator, TargetValidator unapplicationValidator)
/*     */   {
/*  75 */     onCheckOut();
/*  76 */     this.m_baseId = baseId;
/*  77 */     this.m_area = area;
/*  78 */     this.m_applicationTriggers = applicationtriggers;
/*  79 */     this.m_unapplicationTriggers = unapplicationtriggers;
/*  80 */     this.m_maxExecutionCount = maxExecutionCount;
/*  81 */     this.m_targetsToShow = targetsToShow;
/*  82 */     this.m_deactivationDelay = deactivationDelay;
/*  83 */     this.m_activateCondition = applicationCondition;
/*  84 */     this.m_applicationValidator = applicationValidator;
/*  85 */     this.m_unapplicationValidator = unapplicationValidator;
/*     */   }
/*     */   
/*     */   public abstract BasicEffectArea instanceAnother(long paramLong, int paramInt1, int paramInt2, short paramShort, EffectContext paramEffectContext, EffectUser paramEffectUser);
/*     */   
/*     */   public void onCheckOut()
/*     */   {
/*  92 */     this.m_effects = new GrowingArray();
/*  93 */     this.m_id = 0L;
/*  94 */     this.m_position.setX(0);
/*  95 */     this.m_position.setY(0);
/*  96 */     this.m_position.setZ((short)0);
/*  97 */     this.m_area = null;
/*  98 */     this.m_context = null;
/*  99 */     this.m_owner = null;
/* 100 */     this.m_maxExecutionCount = 0;
/* 101 */     this.m_listener = null;
/* 102 */     this.m_unactiveForTargets.clear();
/*     */   }
/*     */   
/*     */ 
/*     */   public void onCheckIn()
/*     */   {
/* 108 */     this.m_effects = null;
/* 109 */     this.m_id = 0L;
/* 110 */     this.m_position.setX(0);
/* 111 */     this.m_position.setY(0);
/* 112 */     this.m_position.setZ((short)0);
/* 113 */     this.m_area = null;
/* 114 */     this.m_context = null;
/* 115 */     this.m_owner = null;
/* 116 */     this.m_maxExecutionCount = 0;
/* 117 */     this.m_listener = null;
/* 118 */     this.m_unactiveForTargets.clear();
/*     */   }
/*     */   
/*     */   public void release() {
/* 122 */     if (this.m_pool != null) {
/*     */       try {
/* 124 */         this.m_pool.returnObject(this);
/*     */       } catch (Exception e) {
/* 126 */         m_logger.error("impossible");
/*     */       }
/* 128 */       this.m_pool = null;
/*     */     } else {
/* 130 */       onCheckIn();
/*     */     }
/*     */   }
/*     */   
/*     */   public long getBaseId()
/*     */   {
/* 136 */     return this.m_baseId;
/*     */   }
/*     */   
/*     */   public void setListener(EffectAreaActionListener listener)
/*     */   {
/* 141 */     this.m_listener = listener;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract boolean hasNoExecutionCount();
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract boolean hasActivationDelay();
/*     */   
/*     */ 
/*     */   public EffectUser getOwner()
/*     */   {
/* 155 */     return this.m_owner;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AbstractCharacteristic getCharacteristic(CharacteristicType charac)
/*     */   {
/* 166 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasCharacteristic(CharacteristicType charac) {
/* 170 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getCharacteristicValue(CharacteristicType charac)
/*     */     throws UnsupportedOperationException
/*     */   {
/* 180 */     AbstractCharacteristic cha = getCharacteristic(charac);
/* 181 */     if (cha != null) {
/* 182 */       return cha.value();
/*     */     }
/* 184 */     throw new UnsupportedOperationException("caractéristique inexistante");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Direction getDirection()
/*     */   {
/* 194 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDirection(Direction direction) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public PartLocalisator getPartLocalisator()
/*     */   {
/* 206 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean shouldBeDead()
/*     */   {
/* 215 */     return this.m_maxExecutionCount == 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RunningEffectManager getRunningEffectManager()
/*     */   {
/* 224 */     return null;
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 228 */     for (Iterator<? extends EffectUser> it = this.m_context.getEffectUserInformationProvider().getEffectUsers(); it.hasNext();)
/*     */     {
/* 230 */       EffectUser user = (EffectUser)it.next();
/* 231 */       user.getRunningEffectManager().removeLinkedToCaster(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public long getId() {
/* 236 */     return this.m_id;
/*     */   }
/*     */   
/*     */   public long getEffectContainerId()
/*     */   {
/* 241 */     return this.m_id;
/*     */   }
/*     */   
/*     */   public Point3 getPosition() {
/* 245 */     return this.m_position;
/*     */   }
/*     */   
/*     */   public void setPosition(int x, int y, short alt) {
/* 249 */     this.m_position.setX(x);
/* 250 */     this.m_position.setY(y);
/* 251 */     this.m_position.setZ(alt);
/*     */   }
/*     */   
/*     */   public void setPosition(Point3 pos) {
/* 255 */     this.m_position.setX(pos.getX());
/* 256 */     this.m_position.setY(pos.getY());
/* 257 */     this.m_position.setZ(pos.getZ());
/*     */   }
/*     */   
/*     */   public int getContainerType() {
/* 261 */     return 3;
/*     */   }
/*     */   
/*     */   public void addEffect(Effect effect) {
/* 265 */     this.m_effects.add(effect);
/*     */   }
/*     */   
/*     */   public void addEffects(Effect[] effects) {
/* 269 */     this.m_effects.add(effects);
/*     */   }
/*     */   
/*     */   public Iterator<Effect> iterator() {
/* 273 */     return this.m_effects.iterator();
/*     */   }
/*     */   
/*     */   public void setArea(AreaOfEffect area) {
/* 277 */     this.m_area = area;
/*     */   }
/*     */   
/*     */   public AreaOfEffect getArea() {
/* 281 */     return this.m_area;
/*     */   }
/*     */   
/*     */   public void setPool(ObjectPool pool) {
/* 285 */     this.m_pool = pool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(Point3 pt)
/*     */   {
/* 295 */     return this.m_area.isPointInside(this.m_position, this.m_position, pt);
/*     */   }
/*     */   
/*     */   public void die() {
/* 299 */     setUnactive(null);
/* 300 */     onDeath();
/*     */   }
/*     */   
/*     */   public boolean checkTriggers(int specificTrigger, Target applicant) {
/* 304 */     BitSet trigger = new BitSet();
/* 305 */     trigger.set(specificTrigger);
/* 306 */     return checkTriggers(trigger, applicant);
/*     */   }
/*     */   
/*     */   public boolean checkTriggers(BitSet triggers, Target applicant)
/*     */   {
/* 311 */     if (this.m_applicationTriggers.intersects(triggers)) {
/* 312 */       return true;
/*     */     }
/*     */     
/* 315 */     if (this.m_unapplicationTriggers.intersects(triggers)) {
/* 316 */       return true;
/*     */     }
/* 318 */     return false;
/*     */   }
/*     */   
/*     */   public void triggers(int specificTrigger, Target applicant) {
/* 322 */     BitSet trigger = new BitSet();
/* 323 */     trigger.set(specificTrigger);
/* 324 */     triggers(trigger, applicant);
/*     */   }
/*     */   
/*     */   public void triggers(BitSet triggers, Target applicant)
/*     */   {
/* 329 */     if (this.m_applicationTriggers.intersects(triggers)) {
/* 330 */       apply(applicant);
/*     */     }
/*     */     
/* 333 */     if (this.m_unapplicationTriggers.intersects(triggers)) {
/* 334 */       unapply(applicant);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setActive(Target triggerer)
/*     */   {
/* 342 */     if (triggerer != null) {
/* 343 */       this.m_unactiveForTargets.remove(triggerer);
/*     */     }
/* 345 */     onActivation(triggerer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setUnactive(Target triggerer)
/*     */   {
/* 352 */     if ((triggerer != null) && 
/* 353 */       (!this.m_unactiveForTargets.contains(triggerer))) {
/* 354 */       this.m_unactiveForTargets.add(triggerer);
/*     */     }
/* 356 */     onDeactivation(triggerer);
/*     */   }
/*     */   
/*     */   public boolean apply(Target triggerer) {
/* 360 */     if (canBeApply(triggerer)) {
/* 361 */       if (hasActivationDelay()) {
/* 362 */         setUnactive(triggerer);
/* 363 */         pushActivationEventForTargetInTimeline(triggerer);
/*     */       }
/* 365 */       if ((!hasNoExecutionCount()) && (this.m_maxExecutionCount > 0)) {
/* 366 */         this.m_maxExecutionCount -= 1;
/*     */       }
/*     */       
/* 369 */       onApplication(triggerer);
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
/* 383 */       execute(triggerer);
/* 384 */       if (this.m_listener != null) {
/* 385 */         this.m_listener.onEffectAreaExecuted(this);
/*     */       }
/*     */       
/* 388 */       return true;
/*     */     }
/* 390 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void execute(Target paramTarget);
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract boolean canBeApply(Target paramTarget);
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract void pushActivationEventForTargetInTimeline(Target paramTarget);
/*     */   
/*     */ 
/*     */ 
/*     */   public void unapply(Target triggerer)
/*     */   {
/* 410 */     if ((triggerer != null) && ((triggerer instanceof EffectUser))) {
/* 411 */       ((EffectUser)triggerer).getRunningEffectManager().removeLinkedToCaster(this);
/*     */     }
/* 413 */     onUnapplication(triggerer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isDead()
/*     */   {
/* 421 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onDeath() {}
/*     */   
/*     */   public void onEffectUsed() {}
/*     */   
/*     */   public void onApplication(Target applicant)
/*     */   {
/* 431 */     m_logger.info("zone appliquée");
/* 432 */     this.m_listener.onEffectAreaApplication(this, applicant);
/*     */   }
/*     */   
/*     */   public void onUnapplication(Target unapplicant) {
/* 436 */     m_logger.info("zone desappliquée");
/* 437 */     this.m_listener.onEffectAreaUnapplication(this, unapplicant);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onActivation(Target triggerer)
/*     */   {
/* 444 */     m_logger.info("zone active");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onDeactivation(Target triggerer)
/*     */   {
/* 451 */     m_logger.info("zone inactive");
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effectArea\BasicEffectArea.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */