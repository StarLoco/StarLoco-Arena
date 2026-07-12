/*    */ package com.ankamagames.xulor.event;
/*    */ 
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
/*    */ 
/*    */ public class ItemClickEvent
/*    */   extends Event
/*    */ {
/* 17 */   private Object m_itemValue = null;
/* 18 */   private MouseButtons m_button = null;
/*    */   
/*    */   public ItemClickEvent(Object itemValue, MouseButtons button) {
/* 21 */     this.m_itemValue = itemValue;
/* 22 */     this.m_button = button;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IElement getElement()
/*    */   {
/* 30 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Object getItemValue()
/*    */   {
/* 37 */     return this.m_itemValue;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public MouseButtons getButton()
/*    */   {
/* 45 */     return this.m_button;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setItemValue(Object itemValue)
/*    */   {
/* 52 */     this.m_itemValue = itemValue;
/*    */   }
/*    */   
/*    */   public void setButton(MouseButtons button) {
/* 56 */     this.m_button = button;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\ItemClickEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */