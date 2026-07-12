/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.Scene;
/*     */ import com.ankamagames.framework.graphics.opengl.base.animation.AnimationManager;
/*     */ import javax.media.opengl.GL;
/*     */ import javax.media.opengl.glu.GLU;
/*     */ import org.fenggui.Canvas;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.render.Graphics;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SceneCanvas
/*     */   extends Canvas
/*     */   implements NonBlocking
/*     */ {
/*  25 */   private Scene m_scene = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_nonBlocking = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScene(Scene scene) {
/*  40 */     this.m_scene = scene;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Scene getScene() {
/*  47 */     return this.m_scene;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNonBlocking() {
/*  54 */     return this.m_nonBlocking;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNonBlocking(boolean nonBlocking) {
/*  62 */     this.m_nonBlocking = nonBlocking;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getWidget(int x, int y) {
/*  72 */     return this.m_nonBlocking ? null : super.getWidget(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics g) {
/*  82 */     super.paint(g);
/*  83 */     if (this.m_scene != null) {
/*     */ 
/*     */       
/*  86 */       GL gl = GLU.getCurrentGL();
/*     */ 
/*     */       
/*  89 */       long realTime = System.nanoTime() / 1000000L;
/*     */ 
/*     */       
/*  92 */       this.m_scene.getViewPort().setX(getDisplayX());
/*  93 */       this.m_scene.getViewPort().setY(getDisplayY());
/*     */ 
/*     */       
/*  96 */       this.m_scene.process(realTime, 0);
/*  97 */       AnimationManager.getInstance().process(this.m_scene, realTime, 0);
/*  98 */       this.m_scene.display(gl);
/*     */ 
/*     */       
/* 101 */       gl.glMatrixMode(5888);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout() {
/* 113 */     super.layout();
/* 114 */     if (this.m_scene != null)
/* 115 */       this.m_scene.setFrustumSize(getWidth(), getHeight()); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\SceneCanvas.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */