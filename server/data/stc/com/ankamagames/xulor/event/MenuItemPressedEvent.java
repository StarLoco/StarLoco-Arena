/*    */ package com.ankamagames.xulor.event;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.template.IMenuItem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MenuItemPressedEvent
/*    */   extends Event
/*    */ {
/*    */   IMenuItem m_widget;
/*    */   
/*    */   public MenuItemPressedEvent(IMenuItem c)
/*    */   {
/* 21 */     this.m_widget = c;
/*    */   }
/*    */   
/*    */   public IMenuItem getMenuItem() {
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


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MenuItemPressedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */