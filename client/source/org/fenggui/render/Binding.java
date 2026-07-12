/*     */ package org.fenggui.render;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.event.DisplayResizedEvent;
/*     */ import org.fenggui.event.IDisplayResizedListener;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Binding
/*     */ {
/*  43 */   private ArrayList<IDisplayResizedListener> displayResizedHook = new ArrayList<IDisplayResizedListener>();
/*     */   
/*  45 */   private static Binding instance = null;
/*  46 */   private Graphics graphics = null;
/*  47 */   private IOpenGL openGL = null;
/*     */   
/*     */   private boolean useClassLoader = false;
/*     */   
/*     */   public Binding(IOpenGL gl) {
/*  52 */     this.openGL = gl;
/*  53 */     this.graphics = new Graphics(gl);
/*     */     
/*  55 */     instance = this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ITexture getTexture(InputStream paramInputStream) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ITexture getTexture(BufferedImage paramBufferedImage);
/*     */ 
/*     */   
/*     */   public boolean isUsingClassLoader() {
/*  68 */     return this.useClassLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseClassLoader(boolean useClassLoader) {
/*  78 */     this.useClassLoader = useClassLoader;
/*     */   }
/*     */ 
/*     */   
/*     */   public IOpenGL getOpenGL() {
/*  83 */     return this.openGL;
/*     */   }
/*     */ 
/*     */   
/*     */   public Graphics getGraphics() {
/*  88 */     return this.graphics;
/*     */   }
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
/*     */   public InputStream getResource(String filename) throws FileNotFoundException {
/* 101 */     if (this.useClassLoader) {
/*     */       
/* 103 */       InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
/* 104 */       if (is == null) throw new FileNotFoundException(
/* 105 */             "The method call Thread.currentThread().getContextClassLoader().getResourceAsStream(\"" + filename + 
/* 106 */             "\") returned null!"); 
/* 107 */       return is;
/*     */     } 
/*     */ 
/*     */     
/* 111 */     return new FileInputStream(filename);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITexture getTexture(String filename) throws IOException {
/* 123 */     return getTexture(getResource(filename));
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract int getCanvasWidth();
/*     */   
/*     */   public abstract int getCanvasHeight();
/*     */   
/*     */   public abstract CursorFactory getCursorFactory();
/*     */   
/*     */   public static Binding getInstance() {
/* 134 */     if (instance == null) throw new IllegalStateException("The Binding has not been initiated yet!");
/*     */     
/* 136 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDisplayResizedListener(IDisplayResizedListener l) {
/* 145 */     if (!this.displayResizedHook.contains(l))
/*     */     {
/* 147 */       this.displayResizedHook.add(l);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeDisplayResizedListener(IDisplayResizedListener l) {
/* 157 */     this.displayResizedHook.remove(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireDisplayResizedEvent(int width, int height) {
/* 168 */     DisplayResizedEvent e = new DisplayResizedEvent(width, height);
/* 169 */     IDisplayResizedListener[] listeners = new IDisplayResizedListener[this.displayResizedHook.size()];
/* 170 */     this.displayResizedHook.toArray(listeners); byte b; int i; IDisplayResizedListener[] arrayOfIDisplayResizedListener1;
/* 171 */     for (arrayOfIDisplayResizedListener1 = listeners, b = 0, i = arrayOfIDisplayResizedListener1.length; b < i; ) { IDisplayResizedListener l = arrayOfIDisplayResizedListener1[b];
/*     */       
/* 173 */       l.displayResized(e);
/*     */       b++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\Binding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */