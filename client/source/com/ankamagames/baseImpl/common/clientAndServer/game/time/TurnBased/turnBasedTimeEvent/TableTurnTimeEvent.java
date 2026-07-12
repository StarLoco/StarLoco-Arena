/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEventListener;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeInterval;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeUnit;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeEvent;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeInterval;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeUnit;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TableTurnTimeEvent
/*    */   extends TurnBasedTimeEvent
/*    */ {
/* 20 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<TableTurnTimeEvent>() { public TableTurnTimeEvent makeObject() {
/* 21 */           return new TableTurnTimeEvent();
/*    */         } }
/*    */     );
/*    */   public static TableTurnTimeEvent checkOut(int tableturn, TimeEventListener timeEventListener) {
/*    */     TableTurnTimeEvent ttte;
/*    */     try {
/* 27 */       ttte = (TableTurnTimeEvent)m_staticPool.borrowObject();
/* 28 */       ttte.m_pool = m_staticPool;
/*    */     }
/* 30 */     catch (Exception e) {
/*    */       
/* 32 */       ttte = new TableTurnTimeEvent();
/* 33 */       ttte.m_pool = null;
/* 34 */       m_logger.error("Erreur lors d'un checkOut sur un TableTurnTimeEvent : " + e.getMessage());
/*    */     } 
/*    */     
/* 37 */     ttte.initialize((TimeUnit)TurnBasedTimeUnit.checkOut(tableturn, 0), (TimeInterval)TurnBasedTimeInterval.checkOut(1, 0), timeEventListener, true, false);
/*    */     
/* 39 */     return ttte;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 43 */     return 106;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void shiftStart(TurnBasedTimeUnit executionTime, TurnBasedTimeInterval shiftInterval) {
/* 49 */     super.shiftStart((TimeUnit)executionTime, (TimeInterval)shiftInterval);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\TurnBased\turnBasedTimeEvent\TableTurnTimeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */