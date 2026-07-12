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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SliderMovedEvent
/*    */   extends Event
/*    */ {
/*    */   IComponent m_widget;
/*    */   double m_value;
/*    */   
/*    */   public SliderMovedEvent(IComponent c, double value)
/*    */   {
/* 26 */     this.m_widget = c;
/* 27 */     this.m_value = value;
/*    */   }
/*    */   
/*    */   public void setValue(double value) {
/* 31 */     this.m_value = value;
/*    */   }
/*    */   
/*    */   public double getValue() {
/* 35 */     return this.m_value;
/*    */   }
/*    */   
/*    */   public IComponent getComponent() {
/* 39 */     return this.m_widget;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IElement getElement()
/*    */   {
/* 47 */     return this.m_widget;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\SliderMovedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */