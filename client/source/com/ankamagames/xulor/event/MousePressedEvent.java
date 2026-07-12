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
/*    */ public class MousePressedEvent
/*    */   extends MouseEvent
/*    */ {
/*    */   protected int m_displayX;
/*    */   protected int m_displayY;
/*    */   protected int m_clickCount;
/* 19 */   private MouseButtons m_button = MouseButtons.BUTTON1;
/*    */   
/*    */   public MousePressedEvent(IComponent c, int displayX, int displayY, MouseButtons button) {
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
/*    */   public void setClickCount(int cc) {
/* 37 */     this.m_clickCount = cc;
/*    */   }
/*    */   
/*    */   public void setButton(MouseButtons button) {
/* 41 */     this.m_button = button;
/*    */   }
/*    */   
/*    */   public int getDisplayX() {
/* 45 */     return this.m_displayX;
/*    */   }
/*    */   
/*    */   public int getDisplayY() {
/* 49 */     return this.m_displayY;
/*    */   }
/*    */   
/*    */   public int getClickCount() {
/* 53 */     return this.m_clickCount;
/*    */   }
/*    */   public MouseButtons getButton() {
/* 56 */     return this.m_button;
/*    */   }
/*    */   
/*    */   public IComponent getComponent() {
/* 60 */     return this.m_widget;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement getElement() {
/* 68 */     return (IElement)this.m_widget;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MousePressedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */