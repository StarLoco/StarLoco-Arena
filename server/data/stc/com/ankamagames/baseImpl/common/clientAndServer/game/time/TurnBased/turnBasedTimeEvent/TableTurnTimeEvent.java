/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEventListener;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeEvent;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeInterval;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeUnit;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TableTurnTimeEvent
/*    */   extends TurnBasedTimeEvent
/*    */ {
/* 20 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/* 21 */     public TableTurnTimeEvent makeObject() { return new TableTurnTimeEvent(); }
/* 20 */   });
/*    */   
/*    */   public static TableTurnTimeEvent checkOut(int tableturn, TimeEventListener timeEventListener)
/*    */   {
/*    */     TableTurnTimeEvent ttte;
/*    */     try
/*    */     {
/* 27 */       TableTurnTimeEvent ttte = (TableTurnTimeEvent)m_staticPool.borrowObject();
/* 28 */       ttte.m_pool = m_staticPool;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 32 */       ttte = new TableTurnTimeEvent();
/* 33 */       ttte.m_pool = null;
/* 34 */       m_logger.error("Erreur lors d'un checkOut sur un TableTurnTimeEvent : " + e.getMessage());
/*    */     }
/*    */     
/* 37 */     ttte.initialize(TurnBasedTimeUnit.checkOut(tableturn, 0), TurnBasedTimeInterval.checkOut(1, 0), timeEventListener, true, false);
/*    */     
/* 39 */     return ttte;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 43 */     return 106;
/*    */   }
/*    */   
/*    */ 
/*    */   public void shiftStart(TurnBasedTimeUnit executionTime, TurnBasedTimeInterval shiftInterval)
/*    */   {
/* 49 */     super.shiftStart(executionTime, shiftInterval);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\TurnBased\turnBasedTimeEvent\TableTurnTimeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */