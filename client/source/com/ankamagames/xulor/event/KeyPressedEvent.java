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
/*    */ public class KeyPressedEvent
/*    */   extends Event
/*    */ {
/*    */   private IComponent m_widget;
/*    */   private char m_key;
/*    */   private Key m_keyClass;
/*    */   
/*    */   public KeyPressedEvent(IComponent c, char key, Key keyClass) {
/* 22 */     this.m_widget = c;
/* 23 */     this.m_key = key;
/* 24 */     this.m_keyClass = keyClass;
/*    */   }
/*    */   
/*    */   public void setKey(char key) {
/* 28 */     this.m_key = key;
/*    */   }
/*    */   
/*    */   public char getKey() {
/* 32 */     return this.m_key;
/*    */   }
/*    */   
/*    */   public Key getKeyClass() {
/* 36 */     return this.m_keyClass;
/*    */   }
/*    */   
/*    */   public void setKeyClass(Key keyClass) {
/* 40 */     this.m_keyClass = keyClass;
/*    */   }
/*    */   
/*    */   public IComponent getComponent() {
/* 44 */     return this.m_widget;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement getElement() {
/* 52 */     return (IElement)this.m_widget;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\KeyPressedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */