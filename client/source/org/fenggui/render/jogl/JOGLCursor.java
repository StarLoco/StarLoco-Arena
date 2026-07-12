/*    */ package org.fenggui.render.jogl;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Cursor;
/*    */ import org.fenggui.render.Cursor;
/*    */ 
/*    */ public class JOGLCursor
/*    */   extends Cursor {
/*  9 */   private Cursor cursor = null;
/* 10 */   private Component component = null;
/*    */ 
/*    */   
/*    */   public JOGLCursor(Cursor c, Component awtComponent) {
/* 14 */     this.cursor = c;
/* 15 */     this.component = awtComponent;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void show() {
/* 21 */     this.component.setCursor(this.cursor);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\jogl\JOGLCursor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */