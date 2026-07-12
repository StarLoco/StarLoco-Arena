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
/*    */ public class MouseReleasedEvent
/*    */   extends MouseEvent
/*    */ {
/*    */   protected int m_displayX;
/*    */   protected int m_displayY;
/* 19 */   private MouseButtons m_button = MouseButtons.BUTTON1;
/*    */   
/*    */   public MouseReleasedEvent(IComponent c, int displayX, int displayY, MouseButtons button) {
/* 22 */     super(c);
/* 23 */     this.m_displayX = displayX;
/* 24 */     this.m_displayY = displayY;
/* 25 */     this.m_button = button;
/*    */   }
/*    */   
/*    */   public void setDisplayX(int x) {
/* 29 */     this.m_displayX = x;
/*    */   }
/*    */   
/*    */   public void setDisplayY(int y) {
/* 33 */     this.m_displayY = y;
/*    */   }
/*    */   
/*    */   public void setButton(MouseButtons button) {
/* 37 */     this.m_button = button;
/*    */   }
/*    */   
/*    */   public int getDisplayX() {
/* 41 */     return this.m_displayX;
/*    */   }
/*    */   
/*    */   public int getDisplayY() {
/* 45 */     return this.m_displayY;
/*    */   }
/*    */   
/*    */   public MouseButtons getButton() {
/* 49 */     return this.m_button;
/*    */   }
/*    */   
/*    */   public IComponent getComponent() {
/* 53 */     return this.m_widget;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IElement getElement()
/*    */   {
/* 61 */     return this.m_widget;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MouseReleasedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */