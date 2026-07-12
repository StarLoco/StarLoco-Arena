/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindCell;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import java.util.List;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Teleport
/*     */   extends ArenaRunningEffect
/*     */ {
/*  24 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<Teleport>() {
/*     */         public Teleport makeObject() {
/*  26 */           return new Teleport();
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */   
/*     */   public Teleport newInstance() {
/*     */     Teleport re;
/*     */     try {
/*  35 */       re = (Teleport)m_staticPool.borrowObject();
/*  36 */       re.m_pool = m_staticPool;
/*     */     }
/*  38 */     catch (Exception e) {
/*  39 */       re = new Teleport();
/*  40 */       re.m_pool = null;
/*  41 */       m_logger.error("Erreur lors d'un checkOut sur un Push : " + e.getMessage());
/*     */     } 
/*  43 */     re.cloneParameters(this);
/*  44 */     return re;
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger) {
/*  49 */     if (this.m_targetCell != null) {
/*  50 */       Point3 startPos = new Point3(this.m_caster.getPosition());
/*     */       
/*  52 */       this.m_caster.setPosition(this.m_targetCell.getX(), this.m_targetCell.getY(), this.m_targetCell.getZ());
/*     */ 
/*     */       
/*  55 */       notifyExecution(linkedRE, trigger);
/*     */ 
/*     */       
/*  58 */       if (this.m_context.getEffectAreaManager() != null) {
/*  59 */         this.m_context.getEffectAreaManager().checkInAndOut(startPos, this.m_caster.getPosition(), this.m_caster);
/*     */       }
/*     */     } 
/*  62 */     super.execute(linkedRE, trigger);
/*     */   }
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {
/*  66 */     if (this.m_caster instanceof com.ankamagames.dofusarena.common.game.fighter.AbstractFighter && this.m_targetCell != null) {
/*     */       
/*  68 */       PathFindCell cell = this.m_context.getCellInformationProvider().getPathFindCell(this.m_targetCell.getX(), this.m_targetCell.getY(), this.m_caster.getPosition().getZ());
/*     */       
/*  70 */       if (cell != null) {
/*  71 */         int x = cell.getX();
/*  72 */         int y = cell.getY();
/*  73 */         short z = cell.getMaximumAltitude();
/*     */         
/*  75 */         if (z == Short.MIN_VALUE) {
/*  76 */           m_logger.error("hauteur impossible pour les coordonnées : " + x + "-" + y);
/*  77 */           this.m_targetCell = null;
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/*  82 */         if (!cell.isWalkable(z)) {
/*  83 */           this.m_targetCell = null;
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<EffectUser> determineTargets(EffectContext context, int x, int y, short alt) {
/*  93 */     return null;
/*     */   }
/*     */   
/*     */   public boolean useCaster() {
/*  97 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 101 */     return false;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 105 */     return true;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\Teleport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */