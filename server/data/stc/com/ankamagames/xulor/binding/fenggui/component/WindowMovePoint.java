/*    */ package com.ankamagames.xulor.binding.fenggui.component;
/*    */ 
/*    */ import org.fenggui.event.mouse.MouseEnteredEvent;
/*    */ import org.fenggui.event.mouse.MouseExitedEvent;
/*    */ import org.fenggui.render.Binding;
/*    */ import org.fenggui.render.Cursor;
/*    */ import org.fenggui.render.CursorFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowMovePoint
/*    */   extends Container
/*    */ {
/*    */   public WindowMovePoint()
/*    */   {
/* 21 */     setNonBlocking(false);
/*    */   }
/*    */   
/*    */   public void mouseEntered(MouseEnteredEvent event) {
/* 25 */     Binding.getInstance().getCursorFactory().getMoveCursor().show();
/* 26 */     super.mouseEntered(event);
/*    */   }
/*    */   
/*    */   public void mouseExited(MouseExitedEvent event) {
/* 30 */     Binding.getInstance().getCursorFactory().getDefaultCursor().show();
/* 31 */     super.mouseExited(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\WindowMovePoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */