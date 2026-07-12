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
/*    */ public class MouseMovedEvent
/*    */   extends MouseEvent
/*    */ {
/*    */   protected int m_displayX;
/*    */   protected int m_displayY;
/*    */   
/*    */   public MouseMovedEvent(IComponent c, int x, int y) {
/* 21 */     super(c);
/* 22 */     this.m_displayX = x;
/* 23 */     this.m_displayY = y;
/*    */   }
/*    */   
/*    */   public void setDisplayX(int x) {
/* 27 */     this.m_displayX = x;
/*    */   }
/*    */   
/*    */   public void setDisplayY(int y) {
/* 31 */     this.m_displayY = y;
/*    */   }
/*    */   
/*    */   public int getDisplayX() {
/* 35 */     return this.m_displayX;
/*    */   }
/*    */   
/*    */   public int getDisplayY() {
/* 39 */     return this.m_displayY;
/*    */   }
/*    */   
/*    */   public IComponent getComponent() {
/* 43 */     return this.m_widget;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement getElement() {
/* 51 */     return (IElement)this.m_widget;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MouseMovedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */