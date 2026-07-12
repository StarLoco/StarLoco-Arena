/*    */ package com.ankamagames.xulor.event;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IComponent;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ 
/*    */ 
/*    */ public class MouseClickEvent
/*    */   extends MouseEvent
/*    */ {
/*    */   protected int m_displayX;
/*    */   protected int m_displayY;
/*    */   protected int m_clickCount;
/* 13 */   private MouseButtons m_button = MouseButtons.BUTTON1;
/*    */   
/*    */   public MouseClickEvent(IComponent c, int x, int y, int clickCount, MouseButtons button) {
/* 16 */     super(c);
/* 17 */     this.m_displayX = x;
/* 18 */     this.m_displayY = y;
/* 19 */     this.m_clickCount = clickCount;
/* 20 */     this.m_button = button;
/*    */   }
/*    */   
/*    */   public void setDisplayX(int x) {
/* 24 */     this.m_displayX = x;
/*    */   }
/*    */   
/*    */   public void setDisplayY(int y) {
/* 28 */     this.m_displayY = y;
/*    */   }
/*    */   
/*    */   public void setClickCount(int cc) {
/* 32 */     this.m_clickCount = cc;
/*    */   }
/*    */   
/*    */   public void setButton(MouseButtons button) {
/* 36 */     this.m_button = button;
/*    */   }
/*    */   
/*    */   public int getDisplayX() {
/* 40 */     return this.m_displayX;
/*    */   }
/*    */   
/*    */   public int getDisplayY() {
/* 44 */     return this.m_displayY;
/*    */   }
/*    */   
/*    */   public int getClickCount() {
/* 48 */     return this.m_clickCount;
/*    */   }
/*    */   
/* 51 */   public MouseButtons getButton() { return this.m_button; }
/*    */   
/*    */   public IComponent getComponent()
/*    */   {
/* 55 */     return this.m_widget;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IElement getElement()
/*    */   {
/* 63 */     return this.m_widget;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MouseClickEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */