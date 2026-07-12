/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.AbstractEffectManager;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUserInformationProvider;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.BasicEffectArea;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.EffectAreaManager;
/*     */ import com.ankamagames.dofusarena.common.game.effectArea.AbstractEffectArea;
/*     */ import com.ankamagames.dofusarena.common.game.effectArea.StaticEffectAreaManager;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SetEffectArea
/*     */   extends ArenaRunningEffect
/*     */ {
/*  22 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*     */     public SetEffectArea makeObject() {
/*  24 */       return new SetEffectArea();
/*     */     }
/*  22 */   });
/*     */   
/*     */ 
/*     */   private BasicEffectArea m_trap;
/*     */   
/*     */   private long m_newTargetId;
/*     */   
/*     */ 
/*     */   public SetEffectArea newInstance()
/*     */   {
/*     */     SetEffectArea re;
/*     */     
/*     */     try
/*     */     {
/*  36 */       SetEffectArea re = (SetEffectArea)m_staticPool.borrowObject();
/*  37 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  40 */       re = new SetEffectArea();
/*  41 */       re.m_pool = null;
/*  42 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*     */     }
/*  44 */     re.cloneParameters(this);
/*  45 */     re.m_newTargetId = this.m_newTargetId;
/*  46 */     re.m_trap = this.m_trap;
/*  47 */     return re;
/*     */   }
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger) {
/*  51 */     AbstractEffectArea modelTrap = StaticEffectAreaManager.getInstance().getEffectArea(this.m_value);
/*  52 */     if (modelTrap != null) {
/*  53 */       this.m_trap = modelTrap.instanceAnother(this.m_newTargetId, this.m_targetCell.getX(), this.m_targetCell.getY(), this.m_targetCell.getZ(), this.m_context, this.m_caster);
/*     */       
/*  55 */       notifyExecution(linkedRE, trigger);
/*  56 */       this.m_context.getEffectAreaManager().addEffectArea(this.m_trap);
/*  57 */       super.execute(linkedRE, trigger);
/*     */     }
/*     */   }
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {
/*  62 */     this.m_value = ((int)this.m_genericEffect.getParam(0));
/*  63 */     this.m_newTargetId = this.m_context.getEffectUserInformationProvider().getNextFreeEffectUserId();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void unapply()
/*     */   {
/*  70 */     if (this.m_trap != null)
/*  71 */       this.m_context.getEffectAreaManager().removeEffectArea(this.m_trap);
/*  72 */     super.unapply();
/*     */   }
/*     */   
/*     */   public boolean useCaster() {
/*  76 */     return false;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/*  80 */     return false;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/*  84 */     return true;
/*     */   }
/*     */   
/*     */   protected void serializeTarget(ByteBuffer buff) {
/*  88 */     buff.putLong(this.m_newTargetId);
/*     */   }
/*     */   
/*     */   protected boolean unserializeTarget(long targetId, AbstractEffectManager manager) {
/*  92 */     this.m_newTargetId = targetId;
/*  93 */     this.m_target = null;
/*  94 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mustBeExecutedNow()
/*     */   {
/* 104 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\SetEffectArea.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */