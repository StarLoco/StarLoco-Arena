/*    */ package org.fenggui.event.mouse;
/*    */ 
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.event.Event;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MouseExitedEvent
/*    */   extends Event
/*    */ {
/* 27 */   private IWidget entered = null;
/* 28 */   private IWidget exited = null;
/*    */ 
/*    */   
/*    */   public MouseExitedEvent(IWidget entered, IWidget exited) {
/* 32 */     super(exited);
/* 33 */     this.entered = entered;
/* 34 */     this.exited = exited;
/*    */   }
/*    */ 
/*    */   
/*    */   public IWidget getEntered() {
/* 39 */     return this.entered;
/*    */   }
/*    */   
/*    */   public IWidget getExited() {
/* 43 */     return this.exited;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\mouse\MouseExitedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */