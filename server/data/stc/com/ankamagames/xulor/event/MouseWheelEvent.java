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
/*    */ 
/*    */ 
/*    */ public class MouseWheelEvent
/*    */   extends Event
/*    */ {
/*    */   private IComponent m_widget;
/* 19 */   private boolean m_up = true;
/* 20 */   private int m_rotation = 0;
/*    */   
/*    */   public MouseWheelEvent(IComponent c, boolean up, int rotations) {
/* 23 */     this.m_widget = c;
/* 24 */     this.m_up = up;
/* 25 */     this.m_rotation = rotations;
/*    */   }
/*    */   
/*    */   public void setWheelUp(boolean up) {
/* 29 */     this.m_up = up;
/*    */   }
/*    */   
/*    */   public void setRotation(int rotation) {
/* 33 */     this.m_rotation = rotation;
/*    */   }
/*    */   
/*    */   public IComponent getComponent() {
/* 37 */     return this.m_widget;
/*    */   }
/*    */   
/*    */   public boolean getWheelUp() {
/* 41 */     return this.m_up;
/*    */   }
/*    */   
/*    */   public int getRotation() {
/* 45 */     return this.m_rotation;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IElement getElement()
/*    */   {
/* 53 */     return this.m_widget;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MouseWheelEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */