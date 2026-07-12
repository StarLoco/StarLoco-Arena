/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEvent;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TurnBasedTimeEvent
/*    */   extends TimeEvent<TurnBasedTimeUnit, TurnBasedTimeInterval>
/*    */   implements Poolable
/*    */ {
/*    */   public boolean isInfinite() {
/* 21 */     return ((TurnBasedTimeInterval)this.m_duration).isInfinite();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\TurnBased\TurnBasedTimeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */