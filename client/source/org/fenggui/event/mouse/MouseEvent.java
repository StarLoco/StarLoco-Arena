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
/*    */ public abstract class MouseEvent
/*    */   extends Event
/*    */ {
/*    */   public MouseEvent(IWidget source) {
/* 42 */     super(source);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract int getDisplayX();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract int getDisplayY();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLocalX(IWidget w) {
/* 64 */     return getDisplayX() - w.getDisplayX();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLocalY(IWidget w) {
/* 74 */     return getDisplayY() - w.getDisplayY();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\mouse\MouseEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */