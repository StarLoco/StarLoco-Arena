/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter;
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
/*    */ public class FighterTurnTimeEvent<F extends BasicFighter>
/*    */   extends TurnBasedTimeEvent
/*    */ {
/* 20 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*    */     public FighterTurnTimeEvent makeObject() {
/* 22 */       return new FighterTurnTimeEvent();
/*    */     }
/* 20 */   });
/*    */   
/*    */   private F m_fighter;
/*    */   
/*    */ 
/*    */   public static <F extends BasicFighter> FighterTurnTimeEvent<F> checkOut(int tableturn, int turn, TimeEventListener timeEventListener, F fighter)
/*    */   {
/*    */     FighterTurnTimeEvent<F> ftte;
/*    */     
/*    */     try
/*    */     {
/* 31 */       FighterTurnTimeEvent<F> ftte = (FighterTurnTimeEvent)m_staticPool.borrowObject();
/* 32 */       ftte.m_pool = m_staticPool;
/*    */     }
/*    */     catch (Exception e) {
/* 35 */       ftte = new FighterTurnTimeEvent();
/* 36 */       ftte.m_pool = null;
/* 37 */       m_logger.error("Erreur lors d'un checkOut sur un FighterTurnTimeEvent : ", e);
/*    */     }
/* 39 */     ftte.m_fighter = fighter;
/* 40 */     ftte.initialize(TurnBasedTimeUnit.checkOut(tableturn, turn), TurnBasedTimeInterval.checkOut(0, 1), timeEventListener, true, false);
/*    */     
/* 42 */     return ftte;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 46 */     return 107;
/*    */   }
/*    */   
/*    */   public F getTurnBasedFighter()
/*    */   {
/* 51 */     return this.m_fighter;
/*    */   }
/*    */   
/*    */   public void onCheckOut()
/*    */   {
/* 56 */     super.onCheckOut();
/* 57 */     this.m_fighter = null;
/*    */   }
/*    */   
/*    */   public void onCheckIn() {
/* 61 */     super.onCheckIn();
/* 62 */     this.m_fighter = null;
/*    */   }
/*    */   
/*    */ 
/*    */   public void switchStatus()
/*    */   {
/* 68 */     unvalidate();
/*    */     
/* 70 */     this.m_priority += 1;
/* 71 */     super.switchStatus();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\TurnBased\turnBasedTimeEvent\FighterTurnTimeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */