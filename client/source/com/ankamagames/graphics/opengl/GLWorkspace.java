/*     */ package com.ankamagames.graphics.opengl;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.Renderer;
/*     */ import com.sun.opengl.util.Animator;
/*     */ import java.awt.DisplayMode;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.awt.event.MouseWheelListener;
/*     */ import javax.media.opengl.GLAutoDrawable;
/*     */ import javax.media.opengl.GLCanvas;
/*     */ import javax.media.opengl.GLCapabilities;
/*     */ import javax.media.opengl.GLEventListener;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GLWorkspace
/*     */   extends GLCanvas
/*     */ {
/*  24 */   private static Logger m_logger = Logger.getLogger(GLWorkspace.class);
/*     */   
/*     */   private Renderer m_renderer;
/*     */   private Animator m_animator;
/*     */   
/*     */   public GLWorkspace(GLCapabilities caps, int frameRate) {
/*  30 */     super(caps);
/*  31 */     this.m_animator = new Animator((GLAutoDrawable)this);
/*  32 */     this.m_animator.start();
/*  33 */     m_logger.info("Animator started : in Animator thread");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Renderer getRenderer() {
/*  40 */     return this.m_renderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Animator getAnimator() {
/*  47 */     return this.m_animator;
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
/*     */   
/*     */   public static GLCapabilities createCapabilities(GraphicsDevice device, int bpp, int depth, boolean doubleBuffered, boolean antiAliased) {
/*  61 */     if (device == null) {
/*  62 */       return null;
/*     */     }
/*  64 */     GLCapabilities caps = new GLCapabilities();
/*     */     
/*  66 */     caps.setHardwareAccelerated(true);
/*  67 */     caps.setDoubleBuffered(doubleBuffered);
/*  68 */     caps.setSampleBuffers(antiAliased);
/*  69 */     caps.setDepthBits(depth);
/*  70 */     caps.setStencilBits(0);
/*     */     
/*  72 */     switch (bpp) {
/*     */       case 16:
/*  74 */         caps.setRedBits(4);
/*  75 */         caps.setGreenBits(4);
/*  76 */         caps.setBlueBits(4);
/*  77 */         caps.setAlphaBits(4);
/*     */         break;
/*     */       
/*     */       case 32:
/*  81 */         caps.setAlphaBits(8);
/*     */       
/*     */       case 24:
/*  84 */         caps.setRedBits(8);
/*  85 */         caps.setGreenBits(8);
/*  86 */         caps.setBlueBits(8);
/*     */         break;
/*     */     } 
/*     */     
/*  90 */     return caps;
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
/*     */ 
/*     */   
/*     */   public static GLCapabilities createCapabilities(GraphicsDevice device, DisplayMode mode, int bpp, boolean doubleBuffered, boolean antiAliased) {
/* 105 */     if (device == null) {
/* 106 */       return null;
/*     */     }
/* 108 */     if (mode == null) {
/* 109 */       return null;
/*     */     }
/* 111 */     GLCapabilities caps = new GLCapabilities();
/*     */     
/* 113 */     caps.setHardwareAccelerated(true);
/* 114 */     caps.setDoubleBuffered(doubleBuffered);
/* 115 */     caps.setSampleBuffers(antiAliased);
/* 116 */     caps.setDepthBits(mode.getBitDepth());
/* 117 */     caps.setStencilBits(8);
/*     */     
/* 119 */     switch (bpp) {
/*     */       case 16:
/* 121 */         caps.setRedBits(4);
/* 122 */         caps.setGreenBits(4);
/* 123 */         caps.setBlueBits(4);
/* 124 */         caps.setAlphaBits(4);
/*     */         break;
/*     */       
/*     */       case 32:
/* 128 */         caps.setAlphaBits(8);
/*     */       
/*     */       case 24:
/* 131 */         caps.setRedBits(8);
/* 132 */         caps.setGreenBits(8);
/* 133 */         caps.setBlueBits(8);
/*     */         break;
/*     */     } 
/*     */     
/* 137 */     return caps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderer(Renderer renderer) {
/* 146 */     if (renderer != this.m_renderer && renderer != null) {
/* 147 */       if (this.m_renderer != null) {
/* 148 */         removeGLEventListener((GLEventListener)this.m_renderer);
/* 149 */         removeMouseListener((MouseListener)this.m_renderer);
/* 150 */         removeMouseMotionListener((MouseMotionListener)this.m_renderer);
/* 151 */         removeKeyListener((KeyListener)this.m_renderer);
/*     */       } 
/* 153 */       this.m_renderer = renderer;
/* 154 */       addGLEventListener((GLEventListener)this.m_renderer);
/* 155 */       addMouseListener((MouseListener)this.m_renderer);
/* 156 */       addMouseMotionListener((MouseMotionListener)this.m_renderer);
/* 157 */       addMouseWheelListener((MouseWheelListener)this.m_renderer);
/* 158 */       addKeyListener((KeyListener)this.m_renderer);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\opengl\GLWorkspace.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */