/*    */ package com.ankamagames.xulor.event;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.template.IMenu;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MenuClosedEvent
/*    */   extends Event
/*    */ {
/*    */   IMenu m_widget;
/*    */   
/*    */   public MenuClosedEvent(IMenu c)
/*    */   {
/* 21 */     this.m_widget = c;
/*    */   }
/*    */   
/*    */   public IMenu getMenu() {
/* 25 */     return this.m_widget;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IElement getElement()
/*    */   {
/* 33 */     return this.m_widget;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MenuClosedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */