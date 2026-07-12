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
/*    */ public class ItemOutEvent
/*    */   extends Event
/*    */ {
/* 16 */   private Object m_itemValue = null;
/*    */   
/*    */   public ItemOutEvent(Object value) {
/* 19 */     this.m_itemValue = value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IElement getElement()
/*    */   {
/* 27 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Object getItemValue()
/*    */   {
/* 34 */     return this.m_itemValue;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setItemValue(Object itemValue)
/*    */   {
/* 41 */     this.m_itemValue = itemValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\ItemOutEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */