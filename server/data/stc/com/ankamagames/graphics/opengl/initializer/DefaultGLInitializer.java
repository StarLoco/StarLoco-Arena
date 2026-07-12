/*     */ package com.ankamagames.graphics.opengl.initializer;
/*     */ 
/*     */ import com.ankamagames.framework.devices.DeviceSelector;
/*     */ import com.ankamagames.framework.devices.DeviceSelectorEventsHandler;
/*     */ import com.ankamagames.framework.graphics.opengl.Renderer;
/*     */ import com.ankamagames.graphics.opengl.GLWorkspace;
/*     */ import java.awt.DisplayMode;
/*     */ import java.awt.GraphicsDevice;
/*     */ import javax.media.opengl.GLCapabilities;
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
/*     */ public class DefaultGLInitializer
/*     */   implements DeviceSelectorEventsHandler
/*     */ {
/*     */   private static final int DEFAULT_FRAMERATE = 30;
/*     */   private static final boolean DEFAULT_DOUBLE_BUFFERED = true;
/*     */   private static final boolean DEFAULT_ANTIALIASED = false;
/*     */   private static final int DEFAULT_BPP = 32;
/*     */   private static final int DEFAULT_SCREEN_WIDTH = 1024;
/*     */   private static final int DEFAULT_SCREEN_HEIGHT = 768;
/*  35 */   private int m_frameRate = 30;
/*  36 */   private boolean m_doubleBuffered = true;
/*  37 */   private boolean m_antiAliased = false;
/*  38 */   private int m_bpp = 32;
/*  39 */   private int m_screenWidth = 1024;
/*  40 */   private int m_screenHeight = 768;
/*     */   
/*  42 */   private boolean m_bppChanged = false;
/*  43 */   private boolean m_screenWidthChanged = false;
/*  44 */   private boolean m_screenHeightChanged = false;
/*     */   
/*  46 */   private GraphicsDevice m_device = null;
/*  47 */   private DisplayMode m_mode = null;
/*     */   
/*     */   private GLWorkspace m_workspace;
/*     */   
/*     */   private Renderer m_renderer;
/*     */   
/*     */ 
/*     */   public DefaultGLInitializer(boolean initialize)
/*     */   {
/*  56 */     if (initialize) {
/*  57 */       initialize();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isAntiAliased()
/*     */   {
/*  65 */     return this.m_antiAliased;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAntiAliased(boolean antiAliased)
/*     */   {
/*  73 */     this.m_antiAliased = antiAliased;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getBpp()
/*     */   {
/*  80 */     return this.m_bpp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBpp(int bpp)
/*     */   {
/*  88 */     this.m_bpp = bpp;
/*  89 */     this.m_bppChanged = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isDoubleBuffered()
/*     */   {
/*  96 */     return this.m_doubleBuffered;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDoubleBuffered(boolean doubleBuffered)
/*     */   {
/* 104 */     this.m_doubleBuffered = doubleBuffered;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFrameRate()
/*     */   {
/* 111 */     return this.m_frameRate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFrameRate(int frameRate)
/*     */   {
/* 119 */     this.m_frameRate = frameRate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getScreenHeight()
/*     */   {
/* 126 */     return this.m_screenHeight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setScreenHeight(int screenHeight)
/*     */   {
/* 134 */     this.m_screenHeight = screenHeight;
/* 135 */     this.m_screenHeightChanged = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getScreenWidth()
/*     */   {
/* 142 */     return this.m_screenWidth;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setScreenWidth(int screenWidth)
/*     */   {
/* 150 */     this.m_screenWidth = screenWidth;
/* 151 */     this.m_screenWidthChanged = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public GraphicsDevice getDevice()
/*     */   {
/* 158 */     if (this.m_device == null) {
/* 159 */       DeviceSelector selector = new DeviceSelector(this);
/* 160 */       selector.initialize();
/* 161 */       this.m_device = selector.getDefaultGraphicsDevice();
/*     */     }
/* 163 */     return this.m_device;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDevice(GraphicsDevice device)
/*     */   {
/* 171 */     this.m_device = device;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DisplayMode getMode()
/*     */   {
/* 178 */     if ((this.m_mode == null) || (this.m_bppChanged) || (this.m_screenWidthChanged) || (this.m_screenHeightChanged)) {
/* 179 */       DeviceSelector selector = new DeviceSelector(this);
/* 180 */       selector.initialize();
/* 181 */       this.m_mode = selector.findDisplayMode(this.m_device, getScreenWidth(), getScreenHeight(), getBpp());
/*     */     }
/* 183 */     return this.m_mode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMode(DisplayMode mode)
/*     */   {
/* 191 */     this.m_mode = mode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public GLWorkspace getWorkspace()
/*     */   {
/* 198 */     return this.m_workspace;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Renderer getRenderer()
/*     */   {
/* 205 */     return this.m_renderer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initialize()
/*     */   {
/* 212 */     createRenderer();
/* 213 */     initRenderer();
/* 214 */     createWorkspace();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void createRenderer()
/*     */   {
/* 221 */     this.m_renderer = new Renderer();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initRenderer() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private GLWorkspace createWorkspace()
/*     */   {
/* 236 */     GLCapabilities caps = GLWorkspace.createCapabilities(getDevice(), getMode(), 24, isDoubleBuffered(), isAntiAliased());
/* 237 */     this.m_workspace = new GLWorkspace(caps, getFrameRate());
/* 238 */     this.m_workspace.setRenderer(this.m_renderer);
/* 239 */     return this.m_workspace;
/*     */   }
/*     */   
/*     */   public void initialize(DeviceSelector selector) {}
/*     */   
/*     */   public void enumDevice(GraphicsDevice device) {}
/*     */   
/*     */   public void enumDisplayMode(DisplayMode mode) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\opengl\initializer\DefaultGLInitializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */