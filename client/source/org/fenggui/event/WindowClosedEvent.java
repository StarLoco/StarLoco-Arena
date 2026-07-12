/*    */ package org.fenggui.event;
/*    */ 
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.composites.Window;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowClosedEvent
/*    */   extends Event
/*    */ {
/* 32 */   private Window window = null;
/*    */ 
/*    */   
/*    */   public WindowClosedEvent(Window w) {
/* 36 */     super((IWidget)w);
/* 37 */     this.window = w;
/*    */   }
/*    */   
/*    */   public Window getWindow() {
/* 41 */     return this.window;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\WindowClosedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */