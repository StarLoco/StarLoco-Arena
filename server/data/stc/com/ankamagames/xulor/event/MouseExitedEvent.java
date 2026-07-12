/*    */ package com.ankamagames.xulor.event;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IComponent;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MouseExitedEvent
/*    */   extends Event
/*    */ {
/*    */   private IComponent m_entered;
/*    */   private IComponent m_exited;
/*    */   
/*    */   public MouseExitedEvent(IComponent entered, IComponent exited)
/*    */   {
/* 21 */     this.m_entered = entered;
/* 22 */     this.m_exited = exited;
/*    */   }
/*    */   
/*    */   public void setEntered(IComponent c) {
/* 26 */     this.m_entered = c;
/*    */   }
/*    */   
/*    */   public void setExited(IComponent c) {
/* 30 */     this.m_exited = c;
/*    */   }
/*    */   
/*    */   public IComponent getComponent() {
/* 34 */     return this.m_exited;
/*    */   }
/*    */   
/*    */   public IComponent getEntered() {
/* 38 */     return this.m_entered;
/*    */   }
/*    */   
/*    */   public IComponent getExited() {
/* 42 */     return this.m_exited;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IElement getElement()
/*    */   {
/* 50 */     return this.m_exited;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MouseExitedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */