/*    */ package com.ankamagames.xulor.binding.fenggui.component;
/*    */ 
/*    */ import com.ankamagames.xulor.util.Alignment;
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.event.mouse.MouseEnteredEvent;
/*    */ import org.fenggui.event.mouse.MouseExitedEvent;
/*    */ import org.fenggui.render.Binding;
/*    */ 
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
/* 23 */     this.m_appearance = new Image.ImageAppearance(this, (IWidget)this);
/* 24 */     this.m_alignment = alignment;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Image.ImageAppearance getAppearance() {
/* 32 */     return this.m_appearance;
/*    */   }
/*    */   
/*    */   public Alignment getPointAlignment() {
/* 36 */     return this.m_alignment;
/*    */   }
/*    */   
/*    */   public void mouseEntered(MouseEnteredEvent mouseEnteredEvent) {
/* 40 */     switch (this.m_alignment) {
/*    */       case NORTH_EAST:
/*    */       case SOUTH_WEST:
/* 43 */         Binding.getInstance().getCursorFactory().getSWResizeCursor().show();
/* 44 */         this.m_window.setShowResizeCursor(true);
/*    */         break;
/*    */       case NORTH_WEST:
/*    */       case SOUTH_EAST:
/* 48 */         Binding.getInstance().getCursorFactory().getNWResizeCursor().show();
/* 49 */         this.m_window.setShowResizeCursor(true);
/*    */         break;
/*    */       case WEST:
/*    */       case EAST:
/* 53 */         Binding.getInstance().getCursorFactory().getHorizontalResizeCursor().show();
/* 54 */         this.m_window.setShowResizeCursor(true);
/*    */         break;
/*    */       case NORTH:
/*    */       case SOUTH:
/* 58 */         Binding.getInstance().getCursorFactory().getVerticalResizeCursor().show();
/* 59 */         this.m_window.setShowResizeCursor(true);
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void mouseExited(MouseExitedEvent mouseExitedEvent) {
/* 65 */     Binding.getInstance().getCursorFactory().getDefaultCursor().show();
/*    */   }
/*    */   
/*    */   public void setWindow(Window window) {
/* 69 */     this.m_window = window;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\WindowResizePoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */