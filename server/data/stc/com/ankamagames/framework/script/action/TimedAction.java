/*    */ package com.ankamagames.framework.script.action;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.framework.kernel.core.common.message.MessageHandler;
/*    */ import com.ankamagames.framework.kernel.core.common.message.scheduler.MessageScheduler;
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
/*    */ 
/*    */ 
/*    */ public abstract class TimedAction
/*    */   extends Action
/*    */   implements MessageHandler
/*    */ {
/*    */   public TimedAction(int uniqueId, int actionType, int actionId)
/*    */   {
/* 25 */     super(uniqueId, actionType, actionId);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final void run()
/*    */   {
/* 34 */     long runTime = onRun();
/* 35 */     MessageScheduler.getInstance().addClock(this, runTime, -1, 1);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected abstract long onRun();
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean onMessage(Message message)
/*    */   {
/* 47 */     if (message.getId() == Integer.MIN_VALUE) {
/* 48 */       fireActionFinishedEvent();
/* 49 */       return false;
/*    */     }
/*    */     
/* 52 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long getId()
/*    */   {
/* 61 */     return -1L;
/*    */   }
/*    */   
/*    */   public void setId(long id) {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\action\TimedAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */