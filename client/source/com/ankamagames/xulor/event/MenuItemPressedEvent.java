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
/*    */ 
/*    */ public class MenuItemPressedEvent
/*    */   extends Event
/*    */ {
/*    */   IMenuItem m_widget;
/*    */   
/*    */   public MenuItemPressedEvent(IMenuItem c) {
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
/*    */   
/*    */   public IElement getElement() {
/* 33 */     return (IElement)this.m_widget;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MenuItemPressedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */