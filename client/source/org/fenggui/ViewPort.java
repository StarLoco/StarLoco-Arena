/*    */ package org.fenggui;
/*    */ 
/*    */ import org.fenggui.event.IViewPortPaintListener;
/*    */ import org.fenggui.render.Graphics;
/*    */ import org.fenggui.render.IOpenGL;
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
/*    */ public class ViewPort
/*    */   extends Widget
/*    */ {
/* 39 */   private IViewPortPaintListener viewPortPaintListener = null;
/* 40 */   private Display display = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public void addedToWidgetTree() {
/* 45 */     this.display = getDisplay();
/*    */   }
/*    */ 
/*    */   
/*    */   public ViewPort() {
/* 50 */     updateMinSize();
/*    */   }
/*    */ 
/*    */   
/*    */   public void paint(Graphics g) {
/* 55 */     if (this.viewPortPaintListener == null)
/*    */       return; 
/* 57 */     IOpenGL opengl = g.getOpenGL();
/* 58 */     opengl.pushAllAttribs();
/* 59 */     opengl.setModelMatrixMode();
/* 60 */     opengl.pushMatrix();
/* 61 */     opengl.setProjectionMatrixMode();
/* 62 */     opengl.pushMatrix();
/* 63 */     opengl.setModelMatrixMode();
/*    */     
/* 65 */     int viewPortWidth = getWidth();
/* 66 */     int viewPortHeight = getHeight();
/*    */     
/* 68 */     opengl.setViewPort(g.getTranslation().getX(), g.getTranslation().getY(), 
/* 69 */         viewPortWidth, viewPortHeight);
/*    */     
/* 71 */     this.viewPortPaintListener.paint(g, viewPortWidth, viewPortHeight);
/*    */     
/* 73 */     opengl.setViewPort(0, 0, this.display.getWidth(), this.display.getHeight());
/*    */     
/* 75 */     opengl.setProjectionMatrixMode();
/* 76 */     opengl.popMatrix();
/* 77 */     opengl.setModelMatrixMode();
/* 78 */     opengl.popMatrix();
/* 79 */     opengl.popAllAttribs();
/*    */   }
/*    */   
/*    */   public IViewPortPaintListener getViewPortPaintListener() {
/* 83 */     return this.viewPortPaintListener;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setViewPortPaintListener(IViewPortPaintListener viewPortPaintListener) {
/* 88 */     this.viewPortPaintListener = viewPortPaintListener;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\ViewPort.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */