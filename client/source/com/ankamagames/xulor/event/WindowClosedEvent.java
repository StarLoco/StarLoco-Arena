/*    */ package com.ankamagames.xulor.event;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.template.IWindow;
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
/*    */ public class WindowClosedEvent
/*    */   extends Event
/*    */ {
/*    */   IWindow m_window;
/*    */   
/*    */   public WindowClosedEvent(IWindow w) {
/* 22 */     this.m_window = w;
/*    */   }
/*    */   
/*    */   public IWindow getWindow() {
/* 26 */     return this.m_window;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement getElement() {
/* 34 */     return (IElement)this.m_window;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\WindowClosedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */