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
/*    */ public class ActivationEvent
/*    */   extends Event
/*    */ {
/*    */   IComponent m_widget;
/* 19 */   boolean m_isEnabled = true;
/*    */   
/*    */   public ActivationEvent(IComponent c, boolean isEnabled) {
/* 22 */     this.m_widget = c;
/* 23 */     this.m_isEnabled = isEnabled;
/*    */   }
/*    */   
/*    */   public void setEnabled(boolean enabled) {
/* 27 */     this.m_isEnabled = enabled;
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 31 */     return this.m_isEnabled;
/*    */   }
/*    */   
/*    */   public IComponent getComponent() {
/* 35 */     return this.m_widget;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IElement getElement()
/*    */   {
/* 43 */     return this.m_widget;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\ActivationEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */