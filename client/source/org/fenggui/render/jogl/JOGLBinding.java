/*     */ package org.fenggui.render.jogl;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.media.opengl.GL;
/*     */ import javax.media.opengl.GLCanvas;
/*     */ import org.fenggui.FengGUI;
/*     */ import org.fenggui.render.Binding;
/*     */ import org.fenggui.render.CursorFactory;
/*     */ import org.fenggui.render.ITexture;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JOGLBinding
/*     */   extends Binding
/*     */ {
/*  44 */   private JOGLCursorFactory cursorFactory = null;
/*     */   private Component canvas;
/*  46 */   private JOGLTextureLoader tl = null;
/*     */ 
/*     */   
/*     */   public JOGLBinding(GLCanvas canvas) {
/*  50 */     this((Component)canvas, canvas.getGL());
/*     */   }
/*     */ 
/*     */   
/*     */   public JOGLBinding(Component component, GL gl) {
/*  55 */     super(new JOGLOpenGL(gl));
/*     */     
/*  57 */     if (component == null) throw new NullPointerException("component == null"); 
/*  58 */     if (gl == null) throw new NullPointerException("gl == null");
/*     */     
/*  60 */     this.canvas = component;
/*  61 */     this.tl = new JOGLTextureLoader(gl);
/*     */     
/*  63 */     FengGUI.TYPE_REGISTRY.register("Texture", JOGLTexture.class);
/*     */     
/*  65 */     this.cursorFactory = new JOGLCursorFactory(this.canvas.getParent());
/*     */     
/*  67 */     this.canvas.addComponentListener(new ComponentListener()
/*     */         {
/*     */           public void componentResized(ComponentEvent ce)
/*     */           {
/*  71 */             JOGLBinding.this.fireDisplayResizedEvent(JOGLBinding.this.canvas.getWidth(), JOGLBinding.this.canvas.getHeight());
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void componentMoved(ComponentEvent arg0) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void componentShown(ComponentEvent arg0) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void componentHidden(ComponentEvent arg0) {}
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ITexture getTexture(InputStream stream) throws IOException {
/*  94 */     return this.tl.getTexture(stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITexture getTexture(BufferedImage bi) {
/* 102 */     return this.tl.getTexture(bi);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCanvasWidth() {
/* 112 */     return this.canvas.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCanvasHeight() {
/* 120 */     return this.canvas.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JOGLCursorFactory getCursorFactory() {
/* 126 */     return this.cursorFactory;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\jogl\JOGLBinding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */