/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.PropertyType;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.ValueRounder;
/*     */ import com.ankamagames.dofusarena.common.game.effect.Elements;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterPropertyType;
/*     */ import com.ankamagames.framework.ai.dataProvider.MovementObstacleInformationProvider;
/*     */ import com.ankamagames.framework.ai.pathfinder.MovementObstacle;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindCell;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindMover;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindParameters;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.kernel.core.maths.Vector3i;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Push
/*     */   extends ArenaRunningEffect
/*     */ {
/*     */   public Push() {
/*  34 */     this.m_lifePointsToLose = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     this.m_mustBeExecuted = true;
/*  40 */     this.m_obstacle = null;
/*     */   } private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<Push>() { public Push makeObject() { return new Push(); } }
/*     */     ); private int m_lifePointsToLose; private Point3 m_arrivalCell; private int m_cellsCount; public void onCheckOut() {
/*  43 */     super.onCheckOut();
/*  44 */     this.m_lifePointsToLose = 0;
/*  45 */     this.m_mustBeExecuted = true;
/*  46 */     this.m_obstacle = null;
/*     */   }
/*     */   private int m_fallHeight; private boolean m_stoppedOnVoid; private boolean m_mustBeExecuted; private MovementObstacle m_obstacle;
/*     */   
/*     */   public void onCheckIn() {
/*  51 */     this.m_obstacle = null;
/*     */   }
/*     */   
/*     */   public Push newInstance() {
/*     */     Push wre;
/*     */     try {
/*  57 */       wre = (Push)m_staticPool.borrowObject();
/*  58 */       wre.m_pool = m_staticPool;
/*     */     }
/*  60 */     catch (Exception e) {
/*  61 */       wre = new Push();
/*  62 */       wre.m_pool = null;
/*  63 */       m_logger.error("Erreur lors d'un checkOut sur un Push : " + e.getMessage());
/*     */     } 
/*  65 */     wre.m_mustBeExecuted = this.m_mustBeExecuted;
/*  66 */     wre.cloneParameters(this);
/*  67 */     return wre;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger) {
/*  73 */     if (!this.m_mustBeExecuted)
/*     */       return; 
/*  75 */     if (this.m_target != null && this.m_target instanceof AbstractFighter) {
/*  76 */       AbstractFighter fighter = (AbstractFighter)this.m_target;
/*  77 */       Point3 startPos = new Point3(fighter.getPosition());
/*     */       
/*  79 */       if (this.m_valueComputationEnabled) {
/*  80 */         if (this.m_lifePointsToLose > 0) {
/*  81 */           HPLoss hpLoss = HPLoss.checkOut(this.m_context, Elements.PHYSICAL, this.m_lifePointsToLose, (EffectUser)fighter);
/*  82 */           hpLoss.disableValueComputation();
/*  83 */           hpLoss.execute((RunningEffect)null, false);
/*  84 */           if (this.m_obstacle != null && this.m_obstacle instanceof EffectUser) {
/*  85 */             EffectUser obstacle = (EffectUser)this.m_obstacle;
/*  86 */             HPLoss obstacleHpLoss = HPLoss.checkOut(this.m_context, Elements.EARTH, this.m_lifePointsToLose, obstacle);
/*  87 */             obstacleHpLoss.disableValueComputation();
/*  88 */             obstacleHpLoss.execute((RunningEffect)null, false);
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/*  94 */         computeMovement();
/*     */       } 
/*     */ 
/*     */       
/*  98 */       if (fighter.isCarrying() && !this.m_arrivalCell.equals(fighter.getPosition())) {
/*  99 */         fighter.uncarry(fighter.getPosition());
/*     */       }
/*     */ 
/*     */       
/* 103 */       fighter.setPosition(this.m_arrivalCell);
/*     */ 
/*     */       
/* 106 */       notifyExecution(linkedRE, trigger);
/*     */ 
/*     */       
/* 109 */       if (this.m_context.getEffectAreaManager() != null) {
/* 110 */         this.m_context.getEffectAreaManager().checkInAndOut(startPos, this.m_arrivalCell, (EffectUser)fighter);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 115 */     super.execute(linkedRE, trigger);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {
/* 121 */     this.m_mustBeExecuted = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     switch ((this.m_genericEffect.getParams()).length) {
/*     */       case 1:
/* 128 */         this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/*     */         break;
/*     */       default:
/* 131 */         m_logger.error("Nombre de paramètres incorrect dans un Push : " + (this.m_genericEffect.getParams()).length);
/* 132 */         this.m_value = 0;
/*     */         break;
/*     */     } 
/* 135 */     if (this.m_target instanceof AbstractFighter) {
/*     */ 
/*     */       
/* 138 */       if (((AbstractFighter)this.m_target).getProperties().isActiveProperty((PropertyType)FighterPropertyType.STABILIZED) || ((AbstractFighter)this.m_target).getProperties().isActiveProperty((PropertyType)FighterPropertyType.ROOTED)) {
/* 139 */         this.m_mustBeExecuted = false;
/*     */         
/*     */         return;
/*     */       } 
/* 143 */       computeMovement();
/*     */ 
/*     */       
/* 146 */       this.m_lifePointsToLose = this.m_fallHeight;
/*     */       
/* 148 */       int cellLeft = this.m_value - this.m_cellsCount;
/* 149 */       if (cellLeft > 0) {
/* 150 */         this.m_lifePointsToLose += cellLeft * (this.m_stoppedOnVoid ? 6 : 3);
/*     */       }
/*     */       
/* 153 */       this.m_value = this.m_cellsCount;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void computeMovement() {
/* 160 */     AbstractFighter fighter = (AbstractFighter)this.m_target;
/*     */     
/* 162 */     Direction8 dir = (new Vector3i(this.m_caster.getPosition(), this.m_target.getPosition())).toDirection4();
/* 163 */     int[] dirVector = dir.getVector();
/* 164 */     PathFindCell cell = null;
/*     */ 
/*     */     
/* 167 */     int x = this.m_target.getPosition().getX();
/* 168 */     int y = this.m_target.getPosition().getY();
/* 169 */     short z = this.m_target.getPosition().getZ();
/*     */     
/* 171 */     this.m_fallHeight = 0;
/*     */     
/* 173 */     MovementObstacleInformationProvider obstacleInformationProvider = this.m_context.getMovementObstacleInformationProvider();
/* 174 */     MovementObstacle obstacle = null;
/* 175 */     for (this.m_cellsCount = 0; this.m_cellsCount < this.m_value; this.m_cellsCount++) {
/* 176 */       cell = this.m_context.getCellInformationProvider().getPathFindCell(x + dirVector[0], y + dirVector[1], z);
/* 177 */       if (cell == null) {
/*     */         break;
/*     */       }
/*     */       
/* 181 */       PathFindParameters pathFindParams = new PathFindParameters();
/* 182 */       pathFindParams.m_limitHeightWithJumpCapacity = false;
/* 183 */       pathFindParams.m_useDiagonals = false;
/*     */       
/* 185 */       short cellArrivalAltitude = cell.getArrivalAltitude(
/* 186 */           (PathFindMover)fighter, 
/* 187 */           this.m_target.getPosition().getZ(), 
/* 188 */           dir, 
/* 189 */           pathFindParams);
/*     */       
/* 191 */       if (cellArrivalAltitude == Short.MIN_VALUE) {
/*     */         break;
/*     */       }
/*     */       
/* 195 */       obstacle = obstacleInformationProvider.getMovementObstacle(x + dirVector[0], y + dirVector[1], cellArrivalAltitude);
/* 196 */       if (obstacle != null) {
/*     */         break;
/*     */       }
/* 199 */       int heightDiff = cellArrivalAltitude - z;
/*     */       
/* 201 */       if (heightDiff > 2) {
/*     */         break;
/*     */       }
/*     */       
/* 205 */       if (heightDiff < 0) {
/* 206 */         this.m_fallHeight -= heightDiff;
/*     */       }
/*     */ 
/*     */       
/* 210 */       x = cell.getX();
/* 211 */       y = cell.getY();
/* 212 */       z = cellArrivalAltitude;
/*     */     } 
/*     */     
/* 215 */     this.m_arrivalCell = new Point3(x, y, z);
/* 216 */     this.m_stoppedOnVoid = (cell == null);
/* 217 */     this.m_obstacle = obstacle;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useCaster() {
/* 222 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 226 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 230 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\Push.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */