/*    */ package org.fenggui.render.jogl;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Cursor;
/*    */ import java.awt.Point;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.image.BufferedImage;
/*    */ import org.fenggui.render.Cursor;
/*    */ import org.fenggui.render.CursorFactory;
/*    */ 
/*    */ public class JOGLCursorFactory
/*    */   extends CursorFactory {
/* 13 */   private Component component = null;
/*    */ 
/*    */   
/*    */   public JOGLCursorFactory(Component parent) {
/* 17 */     this.component = parent;
/*    */     
/* 19 */     setDefaultCursor(new JOGLCursor(Cursor.getPredefinedCursor(0), parent));
/* 20 */     setMoveCursor(new JOGLCursor(Cursor.getPredefinedCursor(13), parent));
/* 21 */     setTextCursor(new JOGLCursor(Cursor.getPredefinedCursor(2), parent));
/* 22 */     setVerticalResizeCursor(new JOGLCursor(Cursor.getPredefinedCursor(8), parent));
/* 23 */     setHorizontalResizeCursor(new JOGLCursor(Cursor.getPredefinedCursor(11), parent));
/* 24 */     setNWResizeCursor(new JOGLCursor(Cursor.getPredefinedCursor(6), parent));
/* 25 */     setSWResizeCursor(new JOGLCursor(Cursor.getPredefinedCursor(4), parent));
/* 26 */     setHandCursor(new JOGLCursor(Cursor.getPredefinedCursor(12), parent));
/* 27 */     setForbiddenCursor(new JOGLCursor(Cursor.getPredefinedCursor(3), parent));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Cursor createCursor(int xHotspot, int yHotspot, BufferedImage image) {
/* 34 */     Toolkit tk = Toolkit.getDefaultToolkit();
/* 35 */     return new JOGLCursor(tk.createCustomCursor(image, new Point(xHotspot, yHotspot), null), this.component);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\jogl\JOGLCursorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */