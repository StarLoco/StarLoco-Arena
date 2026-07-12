/*    */ package com.ankamagames.xulor.event;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IComponent;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ 
/*    */ 
/*    */ public class MouseDoubleClickEvent
/*    */   extends MouseEvent
/*    */ {
/*    */   protected int m_displayX;
/*    */   protected int m_displayY;
/*    */   protected int m_clickCount;
/* 13 */   private MouseButtons m_button = MouseButtons.BUTTON1;
/*    */   
/*    */   public MouseDoubleClickEvent(IComponent c, int x, int y, int clickCount, MouseButtons button) {
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
/*    */   public MouseButtons getButton() {
/* 51 */     return this.m_button;
/*    */   }
/*    */   
/*    */   public IComponent getComponent() {
/* 55 */     return this.m_widget;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement getElement() {
/* 63 */     return (IElement)this.m_widget;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MouseDoubleClickEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */