/*    */ package com.ankamagames.dofusarena.common.game.time.timeEvent;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEventListener;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeInterval;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeUnit;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeEvent;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeInterval;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeUnit;
/*    */ import com.ankamagames.dofusarena.common.game.fight.AbstractFight;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ public class FightClockedPeriodTimeEvent
/*    */   extends TurnBasedTimeEvent
/*    */ {
/* 20 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<FightClockedPeriodTimeEvent>() {
/*    */         public FightClockedPeriodTimeEvent makeObject() {
/* 22 */           return new FightClockedPeriodTimeEvent();
/*    */         }
/*    */       });
/*    */ 
/*    */   
/*    */   private AbstractFight.FightStatus m_fightStatus;
/*    */ 
/*    */   
/*    */   public static FightClockedPeriodTimeEvent checkOut(int startTableTurn, int startturn, int priority, AbstractFight.FightStatus fightStatus, TimeEventListener timeEventListener) {
/*    */     FightClockedPeriodTimeEvent ftte;
/*    */     try {
/* 33 */       ftte = (FightClockedPeriodTimeEvent)m_staticPool.borrowObject();
/* 34 */       ftte.m_pool = m_staticPool;
/*    */     }
/* 36 */     catch (Exception e) {
/* 37 */       ftte = new FightClockedPeriodTimeEvent();
/* 38 */       ftte.m_pool = null;
/* 39 */       m_logger.error("Erreur lors d'un checkOut sur un FightClockedPeriodTimeEvent : " + e.getMessage());
/*    */     } 
/* 41 */     ftte.m_priority = priority;
/* 42 */     ftte.m_fightStatus = fightStatus;
/* 43 */     ftte.initialize((TimeUnit)TurnBasedTimeUnit.checkOut(startTableTurn, startturn), (TimeInterval)TurnBasedTimeInterval.checkOut(0, 1), timeEventListener, true, false);
/* 44 */     return ftte;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 48 */     return 1001;
/*    */   }
/*    */   
/*    */   public AbstractFight.FightStatus getFightStatus() {
/* 52 */     return this.m_fightStatus;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void switchStatus() {
/* 58 */     unvalidate();
/* 59 */     super.switchStatus();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\time\timeEvent\FightClockedPeriodTimeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */