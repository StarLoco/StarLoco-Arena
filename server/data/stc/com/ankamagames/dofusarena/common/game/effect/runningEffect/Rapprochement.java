/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.EffectAreaManager;
/*     */ import com.ankamagames.dofusarena.common.game.effect.Elements;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectDefinition;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import com.ankamagames.framework.ai.dataProvider.MovementObstacleInformationProvider;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindCell;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindParameters;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.kernel.core.maths.Vector3i;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class Rapprochement
/*     */   extends ArenaRunningEffect
/*     */ {
/*  29 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*     */     public Rapprochement makeObject() {
/*  31 */       return new Rapprochement();
/*     */     }
/*  29 */   });
/*     */   
/*     */   private Point3 m_arrivalCell;
/*     */   
/*     */   private int m_lifePointsToLose;
/*     */   
/*     */ 
/*     */   public static Rapprochement checkOut(EffectContext context, int look, EffectUser target, Effect genericEffect, EffectUser caster, EffectContainer effectContainer)
/*     */   {
/*     */     Rapprochement re;
/*     */     try
/*     */     {
/*  41 */       Rapprochement re = (Rapprochement)m_staticPool.borrowObject();
/*  42 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  45 */       re = new Rapprochement();
/*  46 */       re.m_pool = null;
/*  47 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*     */     }
/*     */     
/*  50 */     re.m_id = RunningEffectConstants.STATE_APPLY.getId();
/*  51 */     re.m_status = ((StaticRunningEffect)RunningEffectConstants.STATE_APPLY.getObject()).getRunningEffectStatus();
/*  52 */     re.setTriggersToExecute();
/*  53 */     re.m_target = target;
/*  54 */     re.m_caster = caster;
/*  55 */     re.m_value = look;
/*  56 */     re.m_effectContainer = effectContainer;
/*  57 */     re.m_maxExecutionCount = -1;
/*  58 */     re.m_context = context;
/*  59 */     re.m_genericEffect = genericEffect;
/*  60 */     return re;
/*     */   }
/*     */   
/*     */   public Rapprochement newInstance()
/*     */   {
/*     */     Rapprochement re;
/*     */     try {
/*  67 */       Rapprochement re = (Rapprochement)m_staticPool.borrowObject();
/*  68 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  71 */       re = new Rapprochement();
/*  72 */       re.m_pool = null;
/*  73 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*     */     }
/*  75 */     re.cloneParameters(this);
/*  76 */     return re;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void execute(RunningEffect linkedRE, boolean trigger)
/*     */   {
/*  85 */     Point3 startPos = new Point3(this.m_targetCell);
/*     */     
/*     */ 
/*  88 */     if (this.m_valueComputationEnabled) {
/*  89 */       if (this.m_lifePointsToLose > 0) {
/*  90 */         HPLoss hpLoss = HPLoss.checkOut(this.m_context, Elements.EARTH, this.m_lifePointsToLose, this.m_caster);
/*  91 */         hpLoss.disableValueComputation();
/*  92 */         hpLoss.execute(null, false);
/*     */       }
/*     */       
/*     */     }
/*     */     else {
/*  97 */       computeMovement();
/*     */     }
/*     */     
/*     */ 
/* 101 */     this.m_caster.setPosition(this.m_arrivalCell);
/*     */     
/*     */ 
/* 104 */     notifyExecution(linkedRE, trigger);
/*     */     
/*     */ 
/* 107 */     if (this.m_context.getEffectAreaManager() != null) {
/* 108 */       this.m_context.getEffectAreaManager().checkInAndOut(startPos, this.m_caster.getPosition(), this.m_caster);
/*     */     }
/*     */     
/*     */ 
/* 112 */     super.execute(linkedRE, trigger);
/*     */   }
/*     */   
/*     */   public void unapply()
/*     */   {
/* 117 */     super.unapply();
/*     */   }
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {
/* 121 */     computeMovement();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void computeMovement()
/*     */   {
/* 130 */     if ((this.m_caster instanceof AbstractFighter))
/*     */     {
/*     */ 
/* 133 */       Direction8 dir = new Vector3i(this.m_caster.getPosition(), this.m_targetCell).toDirection4();
/* 134 */       int[] dirVector = dir.getVector();
/* 135 */       PathFindCell casterCell = this.m_context.getCellInformationProvider().getPathFindCell(this.m_caster.getPosition().getX(), this.m_caster.getPosition().getY(), this.m_caster.getPosition().getZ());
/* 136 */       PathFindCell cell = null;
/*     */       
/*     */ 
/* 139 */       int x = this.m_caster.getPosition().getX();
/* 140 */       int y = this.m_caster.getPosition().getY();
/* 141 */       short z = this.m_caster.getPosition().getZ();
/*     */       
/*     */ 
/* 144 */       this.m_value = (Math.max(Math.abs(x - this.m_targetCell.getX()), Math.abs(y - this.m_targetCell.getY())) - 1);
/*     */       
/*     */ 
/* 147 */       MovementObstacleInformationProvider obstacleInformationProvider = this.m_context.getMovementObstacleInformationProvider();
/*     */       
/* 149 */       for (int cellsCount = 0; cellsCount < this.m_value; cellsCount++) {
/* 150 */         cell = this.m_context.getCellInformationProvider().getPathFindCell(x + dirVector[0], y + dirVector[1], z);
/* 151 */         if (cell == null) {
/*     */           break;
/*     */         }
/*     */         
/* 155 */         if (this.m_targetCell.equals(cell.getX(), cell.getY())) {
/*     */           break;
/*     */         }
/*     */         
/* 159 */         PathFindParameters pathFindParams = new PathFindParameters();
/* 160 */         pathFindParams.m_limitHeightWithJumpCapacity = true;
/* 161 */         pathFindParams.m_useDiagonals = false;
/*     */         
/* 163 */         short cellArrivalAltitude = cell.getArrivalAltitude(
/* 164 */           (AbstractFighter)this.m_caster, 
/* 165 */           this.m_caster.getPosition().getZ(), 
/* 166 */           dir, 
/* 167 */           pathFindParams);
/*     */         
/*     */ 
/* 170 */         if (cellArrivalAltitude == Short.MIN_VALUE) {
/*     */           break;
/*     */         }
/*     */         
/* 174 */         if (obstacleInformationProvider.getMovementObstacle(x + dirVector[0], y + dirVector[1], cellArrivalAltitude) != null) {
/*     */           break;
/*     */         }
/*     */         
/* 178 */         x = cell.getX();
/* 179 */         y = cell.getY();
/* 180 */         z = cellArrivalAltitude;
/*     */       }
/*     */       
/* 183 */       this.m_arrivalCell = new Point3(x, y, z);
/*     */       
/*     */ 
/* 186 */       if (cell != casterCell) {
/* 187 */         int cellLeft = this.m_value - cellsCount;
/* 188 */         if (cellLeft > 0) {
/* 189 */           this.m_lifePointsToLose += cellLeft * (cell == null ? 6 : 3);
/*     */         }
/*     */       }
/*     */       
/* 193 */       this.m_value = cellsCount;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean useCaster()
/*     */   {
/* 199 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 203 */     return false;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 207 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\Rapprochement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */