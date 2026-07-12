/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.PropertyType;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.ValueRounder;
/*     */ import com.ankamagames.dofusarena.common.game.effect.Elements;
/*     */ import com.ankamagames.dofusarena.common.game.fight.AbstractFight;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterPropertyType;
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
/*     */ public class Pull
/*     */   extends ArenaRunningEffect
/*     */ {
/*     */   public Pull() {
/*  32 */     this.m_lifePointsToLose = 0;
/*     */     
/*  34 */     this.m_mustBeExecuted = true;
/*     */   } private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<Pull>() { public Pull makeObject() {
/*     */           return new Pull();
/*     */         } }
/*     */     ); private int m_lifePointsToLose;
/*     */   public void onCheckOut() {
/*  40 */     super.onCheckOut();
/*  41 */     this.m_lifePointsToLose = 0;
/*  42 */     this.m_mustBeExecuted = true;
/*     */   }
/*     */   private Point3 m_arrivalCell; private boolean m_mustBeExecuted;
/*     */   public Pull newInstance() {
/*     */     Pull wre;
/*     */     try {
/*  48 */       wre = (Pull)m_staticPool.borrowObject();
/*  49 */       wre.m_pool = m_staticPool;
/*     */     }
/*  51 */     catch (Exception e) {
/*  52 */       wre = new Pull();
/*  53 */       wre.m_pool = null;
/*  54 */       m_logger.error("Erreur lors d'un checkOut sur un Pull : " + e.getMessage());
/*     */     } 
/*  56 */     wre.m_mustBeExecuted = this.m_mustBeExecuted;
/*  57 */     wre.cloneParameters(this);
/*  58 */     return wre;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger) {
/*  64 */     if (!this.m_mustBeExecuted)
/*     */       return; 
/*  66 */     if (this.m_target != null && this.m_target instanceof AbstractFighter) {
/*  67 */       AbstractFighter fighter = (AbstractFighter)this.m_target;
/*     */       
/*  69 */       Point3 startPos = new Point3(this.m_target.getPosition());
/*     */ 
/*     */       
/*  72 */       if (this.m_valueComputationEnabled) {
/*  73 */         if (this.m_lifePointsToLose > 0) {
/*  74 */           HPLoss hpLoss = HPLoss.checkOut(this.m_context, Elements.EARTH, this.m_lifePointsToLose, (EffectUser)fighter);
/*  75 */           hpLoss.disableValueComputation();
/*  76 */           hpLoss.execute((RunningEffect)null, false);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  81 */         computeMovement();
/*     */       } 
/*     */ 
/*     */       
/*  85 */       if (fighter.isCarrying() && !this.m_arrivalCell.equals(fighter.getPosition())) {
/*  86 */         fighter.uncarry(fighter.getPosition());
/*     */       }
/*     */ 
/*     */       
/*  90 */       fighter.setPosition(this.m_arrivalCell);
/*     */ 
/*     */       
/*  93 */       notifyExecution(linkedRE, trigger);
/*     */ 
/*     */       
/*  96 */       if (this.m_context.getEffectAreaManager() != null) {
/*  97 */         this.m_context.getEffectAreaManager().checkInAndOut(startPos, this.m_target.getPosition(), this.m_target);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 102 */     super.execute(linkedRE, trigger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {
/* 110 */     switch ((this.m_genericEffect.getParams()).length) {
/*     */       case 1:
/* 112 */         this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/*     */         break;
/*     */       default:
/* 115 */         m_logger.error("Nombre de paramètres incorrect dans un Pull : " + (this.m_genericEffect.getParams()).length);
/* 116 */         this.m_value = 0;
/*     */         break;
/*     */     } 
/* 119 */     if (this.m_target instanceof AbstractFighter) {
/*     */ 
/*     */       
/* 122 */       if (((AbstractFighter)this.m_target).getProperties().isActiveProperty((PropertyType)FighterPropertyType.STABILIZED) || ((AbstractFighter)this.m_target).getProperties().isActiveProperty((PropertyType)FighterPropertyType.ROOTED)) {
/* 123 */         this.m_mustBeExecuted = false;
/*     */         
/*     */         return;
/*     */       } 
/* 127 */       computeMovement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void computeMovement() {
/* 135 */     AbstractFighter fighter = (AbstractFighter)this.m_target;
/*     */     
/* 137 */     Direction8 dir = (new Vector3i(this.m_target.getPosition(), this.m_caster.getPosition())).toDirection4();
/* 138 */     int[] dirVector = dir.getVector();
/* 139 */     PathFindCell casterCell = this.m_context.getCellInformationProvider().getPathFindCell(this.m_caster.getPosition().getX(), this.m_caster.getPosition().getY(), this.m_caster.getPosition().getZ());
/* 140 */     PathFindCell cell = null;
/*     */ 
/*     */     
/* 143 */     int x = this.m_target.getPosition().getX();
/* 144 */     int y = this.m_target.getPosition().getY();
/* 145 */     short z = this.m_target.getPosition().getZ();
/*     */     
/* 147 */     int fallHeight = 0;
/*     */     
/* 149 */     AbstractFight abstractFight = fighter.getCurrentFight();
/*     */     int cellsCount;
/* 151 */     for (cellsCount = 0; cellsCount < this.m_value; cellsCount++) {
/* 152 */       cell = this.m_context.getCellInformationProvider().getPathFindCell(x + dirVector[0], y + dirVector[1], z);
/* 153 */       if (cell == null) {
/*     */         break;
/*     */       }
/*     */       
/* 157 */       if (cell == casterCell) {
/*     */         break;
/*     */       }
/*     */       
/* 161 */       PathFindParameters pathFindParams = new PathFindParameters();
/* 162 */       pathFindParams.m_limitHeightWithJumpCapacity = false;
/* 163 */       pathFindParams.m_useDiagonals = false;
/*     */       
/* 165 */       short cellArrivalAltitude = cell.getArrivalAltitude(
/* 166 */           (PathFindMover)fighter, 
/* 167 */           this.m_target.getPosition().getZ(), 
/* 168 */           dir, 
/* 169 */           pathFindParams);
/*     */       
/* 171 */       if (cellArrivalAltitude == Short.MIN_VALUE) {
/*     */         break;
/*     */       }
/*     */       
/* 175 */       if (abstractFight.getMovementObstacle(x + dirVector[0], y + dirVector[1], cellArrivalAltitude) != null) {
/*     */         break;
/*     */       }
/* 178 */       int heightDiff = cellArrivalAltitude - z;
/*     */       
/* 180 */       if (heightDiff > 2) {
/*     */         break;
/*     */       }
/*     */       
/* 184 */       if (heightDiff < 0) {
/* 185 */         fallHeight -= heightDiff;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       x = cell.getX();
/* 192 */       y = cell.getY();
/* 193 */       z = cellArrivalAltitude;
/*     */     } 
/*     */     
/* 196 */     this.m_arrivalCell = new Point3(x, y, z);
/*     */ 
/*     */     
/* 199 */     this.m_lifePointsToLose = fallHeight;
/*     */ 
/*     */     
/* 202 */     if (cell != casterCell) {
/* 203 */       int cellLeft = this.m_value - cellsCount;
/* 204 */       if (cellLeft > 0) {
/* 205 */         this.m_lifePointsToLose += cellLeft * ((cell == null) ? 6 : 3);
/*     */       }
/*     */     } 
/*     */     
/* 209 */     this.m_value = cellsCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useCaster() {
/* 214 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 218 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 222 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\Pull.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */