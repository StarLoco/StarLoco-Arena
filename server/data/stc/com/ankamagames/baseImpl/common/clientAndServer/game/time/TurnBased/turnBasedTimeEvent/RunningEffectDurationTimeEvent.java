/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
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
/*    */ public class RunningEffectDurationTimeEvent
/*    */   extends TurnBasedTimeEvent
/*    */ {
/* 21 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/* 22 */     public RunningEffectDurationTimeEvent makeObject() { return new RunningEffectDurationTimeEvent(null); }
/* 21 */   });
/*    */   
/*    */ 
/*    */   private RunningEffect m_re;
/*    */   
/*    */ 
/*    */   public static RunningEffectDurationTimeEvent checkOut(int tableturn, int turn, int tableTurnDuration, int turnDuration, TimeEventListener timeEventListener, RunningEffect re)
/*    */   {
/*    */     RunningEffectDurationTimeEvent ftte;
/*    */     
/*    */     try
/*    */     {
/* 33 */       RunningEffectDurationTimeEvent ftte = (RunningEffectDurationTimeEvent)m_staticPool.borrowObject();
/* 34 */       ftte.m_pool = m_staticPool;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 38 */       ftte = new RunningEffectDurationTimeEvent();
/* 39 */       ftte.m_pool = null;
/* 40 */       m_logger.error("Erreur lors d'un checkOut sur un TurnBasedApplyDelayTimeEvent : " + e.getMessage());
/*    */     }
/* 42 */     ftte.m_re = re;
/* 43 */     boolean isInstant = false;
/* 44 */     ftte.initialize(TurnBasedTimeUnit.checkOut(tableturn, turn), TurnBasedTimeInterval.checkOut(tableTurnDuration, turnDuration), timeEventListener, true, isInstant);
/* 45 */     ftte.m_priority = 1;
/* 46 */     return ftte;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 50 */     return 1;
/*    */   }
/*    */   
/*    */   public RunningEffect getRunningEffect() {
/* 54 */     return this.m_re;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\TurnBased\turnBasedTimeEvent\RunningEffectDurationTimeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */