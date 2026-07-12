/*    */ package com.ankamagames.framework.graphics.opengl.base.animation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimedObject
/*    */ {
/*    */   protected boolean m_pause;
/*    */   
/*    */   protected long m_currentTime;
/*    */   
/* 11 */   protected float m_timeSpeed = 1.0F;
/*    */   
/*    */   public boolean isPaused()
/*    */   {
/* 15 */     return this.m_pause;
/*    */   }
/*    */   
/* 18 */   public void setPause(boolean pause) { this.m_pause = pause; }
/*    */   
/*    */   public void setAnimationSpeed(float speed)
/*    */   {
/* 22 */     this.m_timeSpeed = speed;
/*    */   }
/*    */   
/* 25 */   public float getAnimationSpeed() { return this.m_timeSpeed; }
/*    */   
/*    */ 
/*    */   public void incTime(int deltaTime)
/*    */   {
/* 30 */     if (!this.m_pause) {
/* 31 */       setCurrentTime(this.m_currentTime + (int)(this.m_timeSpeed * deltaTime));
/*    */     }
/*    */   }
/*    */   
/*    */   public void setCurrentTime(long time) {
/* 36 */     this.m_currentTime = time;
/*    */   }
/*    */   
/*    */   public long getCurrentTime() {
/* 40 */     return this.m_currentTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\animation\TimedObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */