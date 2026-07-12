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
/*    */ 
/*    */ public abstract class MouseEvent
/*    */   extends Event
/*    */ {
/*    */   protected IComponent m_widget;
/*    */   protected int m_x;
/*    */   protected int m_y;
/*    */   
/*    */   public MouseEvent(IComponent c) {
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
/*    */   
/*    */   public abstract int getDisplayX();
/*    */   
/*    */   public IComponent getComponent() {
/* 36 */     return this.m_widget;
/*    */   }
/*    */   
/*    */   public abstract int getDisplayY();
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MouseEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */