/*    */ package org.fenggui.render;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ 
/*    */ public abstract class CursorFactory
/*    */ {
/*  7 */   private Cursor defaultCursor = null;
/*  8 */   private Cursor moveCursor = null;
/*  9 */   private Cursor textCursor = null;
/* 10 */   private Cursor horizontalResizeCursor = null;
/* 11 */   private Cursor verticalResizeCursor = null;
/* 12 */   private Cursor NWResizeCursor = null;
/* 13 */   private Cursor SWResizeCursor = null;
/* 14 */   private Cursor handCursor = null;
/* 15 */   private Cursor forbiddenCursor = null;
/*    */   
/*    */   public abstract Cursor createCursor(int paramInt1, int paramInt2, BufferedImage paramBufferedImage);
/*    */   
/*    */   public Cursor getDefaultCursor() {
/* 20 */     return this.defaultCursor;
/*    */   }
/*    */   
/*    */   public void setDefaultCursor(Cursor defaultCursor) {
/* 24 */     this.defaultCursor = defaultCursor;
/*    */   }
/*    */   
/*    */   public Cursor getHandCursor() {
/* 28 */     return this.handCursor;
/*    */   }
/*    */   
/*    */   public void setHandCursor(Cursor handCursor) {
/* 32 */     this.handCursor = handCursor;
/*    */   }
/*    */   
/*    */   public Cursor getHorizontalResizeCursor() {
/* 36 */     return this.horizontalResizeCursor;
/*    */   }
/*    */   
/*    */   public void setHorizontalResizeCursor(Cursor horizontalResizeCursor) {
/* 40 */     this.horizontalResizeCursor = horizontalResizeCursor;
/*    */   }
/*    */   
/*    */   public Cursor getMoveCursor() {
/* 44 */     return this.moveCursor;
/*    */   }
/*    */   
/*    */   public void setMoveCursor(Cursor moveCursor) {
/* 48 */     this.moveCursor = moveCursor;
/*    */   }
/*    */   
/*    */   public Cursor getNWResizeCursor() {
/* 52 */     return this.NWResizeCursor;
/*    */   }
/*    */   
/*    */   public void setNWResizeCursor(Cursor resizeCursor) {
/* 56 */     this.NWResizeCursor = resizeCursor;
/*    */   }
/*    */   
/*    */   public Cursor getSWResizeCursor() {
/* 60 */     return this.SWResizeCursor;
/*    */   }
/*    */   
/*    */   public void setSWResizeCursor(Cursor resizeCursor) {
/* 64 */     this.SWResizeCursor = resizeCursor;
/*    */   }
/*    */   
/*    */   public Cursor getTextCursor() {
/* 68 */     return this.textCursor;
/*    */   }
/*    */   
/*    */   public void setTextCursor(Cursor textCursor) {
/* 72 */     this.textCursor = textCursor;
/*    */   }
/*    */   
/*    */   public Cursor getVerticalResizeCursor() {
/* 76 */     return this.verticalResizeCursor;
/*    */   }
/*    */   
/*    */   public void setVerticalResizeCursor(Cursor verticalResizeCursor) {
/* 80 */     this.verticalResizeCursor = verticalResizeCursor;
/*    */   }
/*    */   
/*    */   public Cursor getForbiddenCursor() {
/* 84 */     return this.forbiddenCursor;
/*    */   }
/*    */   
/*    */   public void setForbiddenCursor(Cursor forbiddenCursor) {
/* 88 */     this.forbiddenCursor = forbiddenCursor;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\CursorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */