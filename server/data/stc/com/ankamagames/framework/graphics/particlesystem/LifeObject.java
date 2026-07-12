/*    */ package com.ankamagames.framework.graphics.particlesystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class LifeObject
/*    */ {
/*    */   private Object m_lock;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 14 */   private boolean m_dead = false;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void kill()
/*    */   {
/* 21 */     this.m_dead = true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isDead()
/*    */   {
/* 28 */     if (isLocked()) {
/* 29 */       return false;
/*    */     }
/* 31 */     return this.m_dead;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setLock(Object o)
/*    */   {
/* 39 */     this.m_lock = o;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isLocked()
/*    */   {
/* 47 */     return this.m_lock != null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setDead(boolean dead)
/*    */   {
/* 54 */     this.m_dead = dead;
/*    */   }
/*    */   
/*    */   public abstract void release();
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\LifeObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */