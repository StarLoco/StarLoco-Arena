/*     */ package com.ankamagames.graphics.opengl;
/*     */ 
/*     */ import com.ankamagames.graphics.opengl.initializer.DefaultGLInitializer;
/*     */ import java.awt.Container;
/*     */ import java.awt.DisplayMode;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.HeadlessException;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.JFrame;
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
/*     */ public class GLFrame
/*     */   extends JFrame
/*     */ {
/*     */   private DefaultGLInitializer m_glInitializer;
/*     */   
/*     */   public GLFrame()
/*     */     throws HeadlessException
/*     */   {
/*  31 */     setDefaultCloseOperation(3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DefaultGLInitializer getGlInitializer()
/*     */   {
/*  38 */     return this.m_glInitializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setGlInitializer(DefaultGLInitializer glInitializer)
/*     */   {
/*  45 */     this.m_glInitializer = glInitializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addWorkspaceToContentPane()
/*     */   {
/*  52 */     if (this.m_glInitializer != null) {
/*  53 */       GLWorkspace workspace = this.m_glInitializer.getWorkspace();
/*  54 */       if (workspace != null) {
/*  55 */         getContentPane().add(workspace);
/*  56 */         workspace.setFocusable(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFullScreen(boolean fullScreen)
/*     */   {
/*  68 */     if (this.m_glInitializer != null) {
/*  69 */       GraphicsDevice device = this.m_glInitializer.getDevice();
/*  70 */       DisplayMode mode = this.m_glInitializer.getMode();
/*  71 */       if ((device != null) && (mode != null) && (device.isFullScreenSupported())) {
/*     */         try {
/*  73 */           if (fullScreen)
/*     */           {
/*  75 */             setResizable(false);
/*  76 */             if (!isDisplayable()) {
/*  77 */               setUndecorated(true);
/*     */             }
/*  79 */             device.setFullScreenWindow(this);
/*  80 */             device.setDisplayMode(mode);
/*     */           }
/*  82 */           else if (isFullScreen()) {
/*  83 */             device.setDisplayMode(mode);
/*  84 */             device.setFullScreenWindow(null);
/*  85 */             setResizable(true);
/*  86 */             if (!isDisplayable()) {
/*  87 */               setUndecorated(false);
/*     */             }
/*     */           } else {
/*  90 */             setSize(mode.getWidth(), mode.getHeight());
/*     */           }
/*     */         } catch (UnsupportedOperationException e) {
/*  93 */           e.printStackTrace();
/*     */         }
/*     */       } else {
/*  96 */         System.err.println("Le mode plein écran n'est pas supporté ou 'device' et 'mode' n'ont pas été définis !");
/*     */       }
/*     */     } else {
/*  99 */       System.err.println("Impossible de passer en plein écran car aucun glInitializer n'est défini");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFullScreen()
/*     */   {
/* 110 */     return (this.m_glInitializer != null) && (this.m_glInitializer.getDevice() != null) && (this.m_glInitializer.getDevice().isFullScreenSupported()) && (this.m_glInitializer.getDevice().getFullScreenWindow() != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\opengl\GLFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */