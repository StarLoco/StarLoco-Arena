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
/*    */ 
/*    */ public class KeyReleasedEvent
/*    */   extends Event
/*    */ {
/*    */   IComponent m_widget;
/*    */   char m_key;
/*    */   
/*    */   public KeyReleasedEvent(IComponent c, char key) {
/* 22 */     this.m_widget = c;
/* 23 */     this.m_key = key;
/*    */   }
/*    */   
/*    */   public void setKey(char key) {
/* 27 */     this.m_key = key;
/*    */   }
/*    */   
/*    */   public char getKey() {
/* 31 */     return this.m_key;
/*    */   }
/*    */   
/*    */   public IComponent getComponent() {
/* 35 */     return this.m_widget;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement getElement() {
/* 43 */     return (IElement)this.m_widget;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\KeyReleasedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */