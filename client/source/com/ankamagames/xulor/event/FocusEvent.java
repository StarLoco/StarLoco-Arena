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
/*    */ public class FocusEvent
/*    */   extends Event
/*    */ {
/*    */   IComponent m_widget;
/*    */   boolean m_hasFocus = true;
/*    */   
/*    */   public FocusEvent(IComponent c, boolean hasFocus) {
/* 22 */     this.m_widget = c;
/* 23 */     this.m_hasFocus = hasFocus;
/*    */   }
/*    */   
/*    */   public void setFocus(boolean hasFocus) {
/* 27 */     this.m_hasFocus = hasFocus;
/*    */   }
/*    */   
/*    */   public boolean hasFocus() {
/* 31 */     return this.m_hasFocus;
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


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\FocusEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */