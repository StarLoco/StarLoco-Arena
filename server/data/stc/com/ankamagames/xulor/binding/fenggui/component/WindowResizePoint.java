/*    */ package com.ankamagames.xulor.binding.fenggui.component;
/*    */ 
/*    */ import com.ankamagames.xulor.util.Alignment;
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
/*    */ public class WindowResizePoint
/*    */   extends Image
/*    */ {
/*    */   private Image.ImageAppearance m_appearance;
/* 19 */   private Alignment m_alignment = Alignment.NORTH_WEST;
/* 20 */   private Window m_window = null;
/*    */   
/*    */   public WindowResizePoint(Alignment alignment) {
/* 23 */     this.m_appearance = new Image.ImageAppearance(this, this);
/* 24 */     this.m_alignment = alignment;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Image.ImageAppearance getAppearance()
/*    */   {
/* 32 */     return this.m_appearance;
/*    */   }
/*    */   
/*    */   public Alignment getPointAlignment() {
/* 36 */     return this.m_alignment;
/*    */   }
/*    */   
/*    */   public void mouseEntered(MouseEnteredEvent mouseEnteredEvent) {
/* 40 */     switch (this.m_alignment) {
/*    */     case NORTH: 
/*    */     case SOUTH_SOUTH_WEST: 
/* 43 */       Binding.getInstance().getCursorFactory().getSWResizeCursor().show();
/* 44 */       this.m_window.setShowResizeCursor(true);
/* 45 */       break;
/*    */     case CENTER: 
/*    */     case WEST_SOUTH_WEST: 
/* 48 */       Binding.getInstance().getCursorFactory().getNWResizeCursor().show();
/* 49 */       this.m_window.setShowResizeCursor(true);
/* 50 */       break;
/*    */     case NORTH_NORTH_WEST: 
/*    */     case SOUTH: 
/* 53 */       Binding.getInstance().getCursorFactory().getHorizontalResizeCursor().show();
/* 54 */       this.m_window.setShowResizeCursor(true);
/* 55 */       break;
/*    */     case EAST_NORTH_EAST: 
/*    */     case WEST: 
/* 58 */       Binding.getInstance().getCursorFactory().getVerticalResizeCursor().show();
/* 59 */       this.m_window.setShowResizeCursor(true);
/*    */     }
/*    */   }
/*    */   
/*    */   public void mouseExited(MouseExitedEvent mouseExitedEvent)
/*    */   {
/* 65 */     Binding.getInstance().getCursorFactory().getDefaultCursor().show();
/*    */   }
/*    */   
/*    */   public void setWindow(Window window) {
/* 69 */     this.m_window = window;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\WindowResizePoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */