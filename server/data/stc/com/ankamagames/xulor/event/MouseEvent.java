/*    */ package com.ankamagames.xulor.event;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MouseEvent
/*    */   extends Event
/*    */ {
/*    */   protected IComponent m_widget;
/*    */   protected int m_x;
/*    */   protected int m_y;
/*    */   
/*    */   public MouseEvent(IComponent c)
/*    */   {
/* 21 */     this.m_widget = c;
/*    */   }
/*    */   
/*    */   public int getX(IComponent c) {
/* 25 */     return getDisplayX() - c.getDisplayX();
/*    */   }
/*    */   
/*    */   public int getY(IComponent c) {
/* 29 */     return getDisplayY() - c.getDisplayY();
/*    */   }
/*    */   
/*    */   public abstract int getDisplayX();
/*    */   
/*    */   public abstract int getDisplayY();
/*    */   
/* 36 */   public IComponent getComponent() { return this.m_widget; }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MouseEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */