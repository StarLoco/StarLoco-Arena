/*    */ package com.ankamagames.dofusarena.client.core.game.fight;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.framework.kernel.core.common.message.MessageHandler;
/*    */ import com.ankamagames.framework.kernel.core.common.message.scheduler.MessageScheduler;
/*    */ import com.ankamagames.xulor.Xulor;
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
/*    */ 
/*    */ 
/*    */ public class Countdown
/*    */   implements MessageHandler
/*    */ {
/*    */   private int m_duration;
/*    */   private long m_clockId;
/*    */   
/*    */   public void start(int duration) {
/* 29 */     stop();
/* 30 */     setDuration(duration);
/* 31 */     this.m_clockId = MessageScheduler.getInstance().addClock(this, 1000L, 0, duration);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void stop() {
/* 38 */     setDuration(0);
/* 39 */     MessageScheduler.getInstance().removeClock(this.m_clockId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void setDuration(int duration) {
/* 48 */     this.m_duration = Math.max(duration, 0);
/*    */ 
/*    */     
/* 51 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("countdown", Integer.valueOf(this.m_duration));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onMessage(Message message) {
/* 60 */     setDuration(this.m_duration - 1);
/* 61 */     if (this.m_duration == 0) {
/* 62 */       stop();
/*    */     }
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getId() {
/* 73 */     return 1L;
/*    */   }
/*    */   
/*    */   public void setId(long id) {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fight\Countdown.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */