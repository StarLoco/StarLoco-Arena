/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExchangePosition
/*     */   extends ArenaRunningEffect
/*     */ {
/*  18 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<ExchangePosition>() {
/*     */         public ExchangePosition makeObject() {
/*  20 */           return new ExchangePosition();
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExchangePosition newInstance() {
/*     */     ExchangePosition re;
/*     */     try {
/*  30 */       re = (ExchangePosition)m_staticPool.borrowObject();
/*  31 */       re.m_pool = m_staticPool;
/*     */     }
/*  33 */     catch (Exception e) {
/*  34 */       re = new ExchangePosition();
/*  35 */       re.m_pool = null;
/*  36 */       m_logger.error("Erreur lors d'un checkOut sur un Push : " + e.getMessage());
/*     */     } 
/*  38 */     re.cloneParameters(this);
/*  39 */     return re;
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger) {
/*  44 */     Point3 casterPos = new Point3(this.m_caster.getPosition());
/*  45 */     int targetX = this.m_target.getPosition().getX();
/*  46 */     int targetY = this.m_target.getPosition().getY();
/*  47 */     short targetZ = this.m_target.getPosition().getZ();
/*     */ 
/*     */     
/*  50 */     if (this.m_target instanceof AbstractFighter) {
/*  51 */       AbstractFighter target = (AbstractFighter)this.m_target;
/*     */ 
/*     */       
/*  54 */       if (target.isCarrying()) {
/*     */         return;
/*     */       }
/*  57 */       if (this.m_caster instanceof AbstractFighter) {
/*  58 */         AbstractFighter caster = (AbstractFighter)this.m_caster;
/*  59 */         AbstractFighter casterCarrier = caster.getCarriedByFighter();
/*  60 */         AbstractFighter targetCarrier = target.getCarriedByFighter();
/*     */         
/*  62 */         if (casterCarrier != null) {
/*  63 */           casterCarrier.uncarry();
/*  64 */           casterCarrier.carry(target);
/*     */         } 
/*     */         
/*  67 */         if (targetCarrier != null) {
/*  68 */           targetCarrier.uncarry();
/*  69 */           targetCarrier.carry(caster);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  75 */     this.m_caster.setPosition(targetX, targetY, targetZ);
/*  76 */     this.m_target.setPosition(casterPos.getX(), casterPos.getY(), casterPos.getZ());
/*     */ 
/*     */     
/*  79 */     if (linkedRE != null) {
/*  80 */       linkedRE.setTarget(this.m_caster);
/*     */     }
/*     */ 
/*     */     
/*  84 */     notifyExecution(linkedRE, trigger);
/*     */ 
/*     */     
/*  87 */     if (this.m_context.getEffectAreaManager() != null) {
/*  88 */       this.m_context.getEffectAreaManager().checkInAndOut(this.m_target.getPosition(), this.m_caster.getPosition(), this.m_caster);
/*  89 */       this.m_context.getEffectAreaManager().checkInAndOut(this.m_caster.getPosition(), this.m_target.getPosition(), this.m_target);
/*     */     } 
/*     */     
/*  92 */     super.execute(linkedRE, trigger);
/*     */   }
/*     */ 
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {}
/*     */   
/*     */   public boolean useCaster() {
/*  99 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 103 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 107 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\ExchangePosition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */