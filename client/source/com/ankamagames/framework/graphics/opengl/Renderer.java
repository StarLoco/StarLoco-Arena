/*     */ package com.ankamagames.framework.graphics.opengl;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.Scene;
/*     */ import com.ankamagames.framework.graphics.opengl.base.animation.AnimationManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.GLRenderable;
/*     */ import com.ankamagames.framework.kernel.core.controllers.KeyboardController;
/*     */ import com.ankamagames.framework.kernel.core.controllers.MouseController;
/*     */ import com.ankamagames.framework.kernel.core.resource.BaseResourceManager;
/*     */ import com.sun.opengl.util.GLUT;
/*     */ import com.sun.opengl.util.j2d.TextRenderer;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import java.awt.event.MouseWheelListener;
/*     */ import java.util.ArrayList;
/*     */ import javax.media.opengl.GL;
/*     */ import javax.media.opengl.GLAutoDrawable;
/*     */ import javax.media.opengl.GLEventListener;
/*     */ import javax.media.opengl.glu.GLU;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class Renderer
/*     */   implements GLEventListener, KeyListener, MouseListener, MouseWheelListener, MouseMotionListener
/*     */ {
/*  32 */   protected static final Logger m_logger = Logger.getLogger(Renderer.class);
/*     */   
/*  34 */   public static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;
/*     */   
/*  36 */   private float m_backgroundColorRed = 0.0F;
/*  37 */   private float m_backgroundColorGreen = 0.0F;
/*  38 */   private float m_backgroundColorBlue = 0.0F;
/*  39 */   private float m_backgroundColorAlpha = 0.0F;
/*     */   
/*  41 */   private final Object m_scenesMutex = new Object();
/*  42 */   private ArrayList<GLRenderable> m_scenes = new ArrayList<GLRenderable>();
/*     */   
/*  44 */   private final Object m_renderablesToInitMutex = new Object();
/*  45 */   private ArrayList<GLRenderable> m_renderablesToInitialize = new ArrayList<GLRenderable>();
/*     */   
/*     */   private int m_timer;
/*     */   
/*     */   private GL m_gl;
/*  50 */   private static GLU m_glu = new GLU();
/*  51 */   private static GLUT m_glut = new GLUT();
/*     */   
/*     */   private ArrayList<MouseController> m_mouseControllers;
/*     */   
/*     */   private ArrayList<KeyboardController> m_keyboardControllers;
/*     */   
/*     */   private ArrayList<RendererEventsHandler> m_rendererHandlers;
/*     */   
/*     */   private ArrayList<BaseResourceManager> m_resourceManagers;
/*     */   
/*     */   private ArrayList<BaseResourceManager> m_resourceManagersToAdd;
/*     */   
/*     */   private ArrayList<BaseResourceManager> m_resourceManagersToRemove;
/*     */   
/*     */   private static final boolean OPENGL_DEBUG = false;
/*     */   
/*     */   private boolean m_doubleBuffering;
/*     */   
/*     */   private boolean m_syncWait;
/*     */   
/*     */   private long m_lastDisplayTime;
/*     */   private static final long m_fps = 30L;
/*  73 */   private int m_displayWidth = 0;
/*  74 */   private int m_displayHeight = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   private long m_timeStart = System.nanoTime();
/*  80 */   private long m_diffSum = 0L;
/*  81 */   private int m_tickCount = 30;
/*  82 */   private int m_tick = 30;
/*     */ 
/*     */   
/*     */   private long m_currentFps;
/*     */   
/*     */   private boolean m_showDebugInfosOnDisplay = false;
/*     */   
/*  89 */   private TextRenderer m_textRenderer = new TextRenderer(new Font("Monospaced", 0, 12));
/*     */ 
/*     */   
/*     */   private boolean m_initialized;
/*     */   
/*     */   private RendererInitialisationListener m_initialisationListener;
/*     */ 
/*     */   
/*     */   public Renderer() {
/*  98 */     setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
/*  99 */     this.m_timer = 0;
/*     */     
/* 101 */     this.m_mouseControllers = new ArrayList<MouseController>();
/* 102 */     this.m_keyboardControllers = new ArrayList<KeyboardController>();
/*     */     
/* 104 */     this.m_doubleBuffering = true;
/* 105 */     this.m_syncWait = true;
/*     */     
/* 107 */     this.m_rendererHandlers = new ArrayList<RendererEventsHandler>();
/*     */     
/* 109 */     this.m_resourceManagers = new ArrayList<BaseResourceManager>();
/* 110 */     this.m_resourceManagersToAdd = new ArrayList<BaseResourceManager>();
/* 111 */     this.m_resourceManagersToRemove = new ArrayList<BaseResourceManager>();
/*     */   }
/*     */   
/*     */   public void addResourceManagerToUpdate(BaseResourceManager manager) {
/* 115 */     if (!this.m_resourceManagers.contains(manager) && !this.m_resourceManagersToAdd.contains(manager)) {
/* 116 */       this.m_resourceManagersToAdd.add(manager);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeResourceManager(BaseResourceManager manager) {
/* 121 */     if (this.m_resourceManagers.contains(manager)) {
/* 122 */       this.m_resourceManagersToRemove.add(manager);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundColor(Color backgroundColor) {
/* 131 */     this.m_backgroundColorRed = backgroundColor.getRed() / 255.0F;
/* 132 */     this.m_backgroundColorGreen = backgroundColor.getGreen() / 255.0F;
/* 133 */     this.m_backgroundColorBlue = backgroundColor.getBlue() / 255.0F;
/* 134 */     this.m_backgroundColorAlpha = backgroundColor.getAlpha() / 255.0F;
/*     */   }
/*     */   
/*     */   public GL getGl() {
/* 138 */     return this.m_gl;
/*     */   }
/*     */   
/*     */   public static GLU getGlu() {
/* 142 */     return m_glu;
/*     */   }
/*     */   
/*     */   public static GLUT getGlut() {
/* 146 */     return m_glut;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDisplayHeight() {
/* 153 */     return this.m_displayHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDisplayWidth() {
/* 160 */     return this.m_displayWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShowDebugInfosOnDisplay() {
/* 167 */     return this.m_showDebugInfosOnDisplay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShowDebugInfosOnDisplay(boolean showDebugInfosOnDisplay) {
/* 175 */     this.m_showDebugInfosOnDisplay = showDebugInfosOnDisplay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushScene(GLRenderable scene, boolean bPushFront) {
/* 185 */     synchronized (this.m_scenesMutex) {
/* 186 */       synchronized (this.m_renderablesToInitMutex) {
/* 187 */         if (!this.m_scenes.contains(scene)) {
/* 188 */           if (!bPushFront) {
/* 189 */             this.m_scenes.add(0, scene);
/* 190 */             this.m_renderablesToInitialize.add(0, scene);
/*     */           } else {
/* 192 */             this.m_scenes.add(scene);
/* 193 */             this.m_renderablesToInitialize.add(scene);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeScene(Scene scene) {
/* 207 */     this.m_scenes.remove(scene);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRendererEventsHandler(RendererEventsHandler handler) {
/* 217 */     if (!this.m_rendererHandlers.contains(handler)) {
/* 218 */       this.m_rendererHandlers.add(handler);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeRendererEventsHandler(RendererEventsHandler handler) {
/* 229 */     this.m_rendererHandlers.remove(handler);
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
/*     */   
/*     */   public void init(GLAutoDrawable glAutoDrawable) {
/* 245 */     this.m_gl = glAutoDrawable.getGL();
/*     */     
/* 247 */     GLDebugger.dump(this.m_gl);
/* 248 */     GLDebugger.getInstance().readVersion(this.m_gl);
/*     */     
/* 250 */     if (GLDebugger.getInstance().isVersionLowerThan(1, 3)) {
/* 251 */       if (this.m_initialisationListener != null)
/* 252 */         this.m_initialisationListener.onInitialisationError(this, "Matériel non supporté"); 
/*     */       return;
/*     */     } 
/* 255 */     this.m_initialisationListener = null;
/*     */     
/* 257 */     this.m_gl.glClearColor(this.m_backgroundColorRed, this.m_backgroundColorGreen, this.m_backgroundColorBlue, this.m_backgroundColorAlpha);
/* 258 */     this.m_gl.glClear(16384);
/*     */     
/* 260 */     this.m_gl.glViewport(0, 0, glAutoDrawable.getWidth(), glAutoDrawable.getHeight());
/*     */     
/* 262 */     glAutoDrawable.setSize(glAutoDrawable.getWidth(), glAutoDrawable.getHeight());
/*     */     
/* 264 */     this.m_gl.glTexParameterf(3553, 10242, 10496.0F);
/* 265 */     this.m_gl.glTexParameterf(3553, 10243, 10496.0F);
/* 266 */     this.m_gl.glTexParameterf(3553, 10240, 9729.0F);
/* 267 */     this.m_gl.glTexParameterf(3553, 10241, 9728.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 274 */     this.m_gl.glHint(3152, 4353);
/*     */     
/* 276 */     this.m_gl.glDisable(2896);
/* 277 */     this.m_gl.glDisable(3089);
/* 278 */     this.m_gl.glDisable(2929);
/*     */     
/* 280 */     this.m_gl.glAlphaFunc(517, 0.0F);
/* 281 */     this.m_gl.glEnable(3008);
/*     */     
/* 283 */     this.m_gl.glDisable(2960);
/* 284 */     this.m_gl.glDisable(2884);
/* 285 */     this.m_gl.glDisable(3042);
/* 286 */     this.m_gl.glShadeModel(7425);
/* 287 */     this.m_gl.glPixelZoom(1.0F, 1.0F);
/* 288 */     this.m_gl.glDepthMask(false);
/*     */     
/* 290 */     this.m_gl.glStencilMask(0);
/* 291 */     this.m_gl.glStencilOp(7680, 7680, 7680);
/*     */     
/* 293 */     this.m_gl.glDisable(34037);
/* 294 */     this.m_gl.glEnable(3553);
/*     */     
/* 296 */     this.m_gl.glIndexMask(0);
/*     */     
/* 298 */     if (this.m_doubleBuffering) {
/* 299 */       this.m_gl.glDrawBuffer(1029);
/*     */     } else {
/* 301 */       this.m_gl.glDrawBuffer(1028);
/*     */     } 
/* 303 */     glAutoDrawable.setAutoSwapBufferMode((this.m_doubleBuffering && this.m_syncWait));
/* 304 */     this.m_gl.setSwapInterval(this.m_syncWait ? 1 : 0);
/*     */ 
/*     */     
/* 307 */     this.m_lastDisplayTime = 0L;
/*     */     
/* 309 */     for (RendererEventsHandler handler : this.m_rendererHandlers) {
/* 310 */       handler.onInit(glAutoDrawable);
/*     */     }
/* 312 */     this.m_initialized = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void display(GLAutoDrawable glAutoDrawable) {
/* 321 */     if (this.m_initialized) {
/*     */       try {
/* 323 */         if (this.m_timer % 30L == 0L) {
/* 324 */           TextureManager.getInstance().update();
/*     */           int i;
/* 326 */           for (i = 0; i < this.m_resourceManagersToRemove.size(); i++) {
/* 327 */             this.m_resourceManagers.remove(this.m_resourceManagersToRemove.get(i));
/*     */           }
/* 329 */           this.m_resourceManagersToRemove.clear();
/*     */           
/* 331 */           for (i = 0; i < this.m_resourceManagersToAdd.size(); i++) {
/* 332 */             this.m_resourceManagers.add(this.m_resourceManagersToAdd.get(i));
/*     */           }
/* 334 */           this.m_resourceManagersToAdd.clear();
/*     */           
/* 336 */           for (i = 0; i < this.m_resourceManagers.size(); i++) {
/* 337 */             ((BaseResourceManager)this.m_resourceManagers.get(i)).update();
/*     */           }
/*     */         } 
/*     */         
/* 341 */         this.m_timer++;
/*     */         
/* 343 */         synchronized (this.m_scenesMutex) {
/* 344 */           long realTime = System.nanoTime() / 1000000L;
/*     */           
/* 346 */           EffectManager.getInstance().preProcessEffects(realTime, this.m_timer);
/*     */           
/* 348 */           synchronized (this.m_renderablesToInitMutex) {
/* 349 */             for (int j = 0; j < this.m_renderablesToInitialize.size(); j++) {
/* 350 */               GLRenderable renderable = this.m_renderablesToInitialize.get(j);
/* 351 */               renderable.init(glAutoDrawable);
/* 352 */               renderable.setFrustumSize(glAutoDrawable.getWidth(), glAutoDrawable.getHeight());
/*     */             } 
/* 354 */             this.m_renderablesToInitialize.clear();
/*     */           } 
/*     */           
/* 357 */           for (int i = 0; i < this.m_scenes.size(); i++) {
/* 358 */             ((GLRenderable)this.m_scenes.get(i)).process(realTime, this.m_timer);
/*     */           }
/*     */           
/* 361 */           AnimationManager.getInstance().process(realTime, this.m_timer);
/*     */         } 
/*     */         
/* 364 */         if (this.m_rendererHandlers != null)
/* 365 */           for (int i = 0; i < this.m_rendererHandlers.size(); i++) {
/* 366 */             ((RendererEventsHandler)this.m_rendererHandlers.get(i)).onDisplay(glAutoDrawable);
/*     */           } 
/* 368 */         synchronized (this.m_scenesMutex) {
/*     */           
/* 370 */           if (this.m_doubleBuffering && !this.m_syncWait) {
/* 371 */             glAutoDrawable.swapBuffers();
/*     */           }
/* 373 */           this.m_gl.glDepthMask(false);
/* 374 */           this.m_gl.glDisable(2929);
/* 375 */           this.m_gl.glClear(16384);
/*     */           int i;
/* 377 */           for (i = 0; i < this.m_scenes.size(); i++) {
/* 378 */             ((GLRenderable)this.m_scenes.get(i)).processGeometry(this.m_gl);
/*     */           }
/* 380 */           for (i = 0; i < this.m_scenes.size(); i++) {
/* 381 */             ((GLRenderable)this.m_scenes.get(i)).display(this.m_gl);
/*     */           }
/*     */           
/* 384 */           if (this.m_showDebugInfosOnDisplay) {
/* 385 */             this.m_textRenderer.beginRendering(this.m_displayWidth, this.m_displayHeight);
/*     */             
/* 387 */             int y = this.m_displayHeight - 20;
/* 388 */             int x = 10;
/* 389 */             this.m_gl.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */ 
/*     */             
/* 392 */             long timeEnd = System.nanoTime();
/* 393 */             this.m_diffSum += (timeEnd - this.m_timeStart) / 1000000L;
/* 394 */             this.m_timeStart = System.nanoTime();
/* 395 */             if (this.m_tick < 0) {
/* 396 */               this.m_tick = this.m_tickCount;
/* 397 */               long d = this.m_diffSum / this.m_tickCount;
/* 398 */               if (d > 0L) {
/* 399 */                 this.m_currentFps = 1000L / d;
/*     */               }
/* 401 */               this.m_diffSum = 0L;
/*     */             } 
/* 403 */             this.m_tick--;
/* 404 */             this.m_textRenderer.draw("FPS : " + this.m_currentFps, x, y);
/*     */ 
/*     */             
/* 407 */             y -= 15;
/* 408 */             long totalMem = Runtime.getRuntime().totalMemory() / 1048576L;
/* 409 */             long freeMem = Runtime.getRuntime().freeMemory() / 1048576L;
/* 410 */             this.m_textRenderer.draw("memTotal : " + totalMem + " Mo, freeMem : " + freeMem + " Mo, use : " + (totalMem - freeMem) + "Mo", x, y);
/*     */ 
/*     */             
/* 413 */             y -= 15;
/* 414 */             this.m_textRenderer.draw("Scenes :", x, y);
/* 415 */             int sceneIndex = 0;
/* 416 */             for (int j = 0; j < this.m_scenes.size(); j++) {
/* 417 */               y -= 15;
/* 418 */               this.m_textRenderer.draw("Scene " + sceneIndex++ + " : " + ((GLRenderable)this.m_scenes.get(j)).toString(), x + 5, y);
/*     */             } 
/*     */             
/* 421 */             this.m_textRenderer.endRendering();
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 426 */         long now = System.currentTimeMillis();
/* 427 */         float effectiveTime = (float)(now - this.m_lastDisplayTime);
/* 428 */         float neededTime = 33.333332F;
/*     */         try {
/* 430 */           Thread.sleep((long)Math.max(1.0F, neededTime - effectiveTime));
/* 431 */         } catch (InterruptedException e) {
/* 432 */           e.printStackTrace();
/*     */         } 
/* 434 */         this.m_lastDisplayTime = System.currentTimeMillis();
/*     */       }
/* 436 */       catch (Throwable t) {
/* 437 */         m_logger.error("Exception dans le process du renderer : ", t);
/*     */       } 
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
/*     */   public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
/* 450 */     this.m_displayWidth = width;
/* 451 */     this.m_displayHeight = height;
/*     */     
/* 453 */     if (drawable.getWidth() != 0 && drawable.getHeight() != 0) {
/* 454 */       for (GLRenderable s : this.m_scenes) {
/* 455 */         s.setFrustumSize(drawable.getWidth(), drawable.getHeight());
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 461 */     for (RendererEventsHandler handler : this.m_rendererHandlers) {
/* 462 */       handler.onReshape(drawable, x, y, width, height);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
/* 473 */     for (RendererEventsHandler handler : this.m_rendererHandlers) {
/* 474 */       handler.onDisplayChanged(drawable, modeChanged, deviceChanged);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyTyped(KeyEvent keyEvent) {
/* 483 */     for (KeyboardController controller : this.m_keyboardControllers) {
/* 484 */       if (controller.keyTyped(keyEvent)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(KeyEvent keyEvent) {
/* 494 */     for (KeyboardController controller : this.m_keyboardControllers) {
/* 495 */       if (controller.keyReleased(keyEvent)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(KeyEvent keyEvent) {
/* 505 */     for (KeyboardController controller : this.m_keyboardControllers) {
/* 506 */       if (controller.keyPressed(keyEvent))
/*     */         break; 
/*     */     } 
/*     */   }
/*     */   public void pushKeyboardController(KeyboardController controller, boolean bPushFront) {
/* 511 */     if (!this.m_keyboardControllers.contains(controller))
/* 512 */       if (!bPushFront) {
/* 513 */         this.m_keyboardControllers.add(controller);
/*     */       } else {
/* 515 */         this.m_keyboardControllers.add(0, controller);
/*     */       }  
/*     */   }
/*     */   
/*     */   public void removeKeyboardController(KeyboardController controller) {
/* 520 */     if (!this.m_keyboardControllers.contains(controller)) {
/* 521 */       this.m_keyboardControllers.remove(controller);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(MouseEvent mouseEvent) {
/* 531 */     for (MouseController controller : this.m_mouseControllers) {
/* 532 */       if (controller.mouseClicked(mouseEvent)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MouseEvent mouseEvent) {
/* 542 */     for (MouseController controller : this.m_mouseControllers) {
/* 543 */       if (controller.mousePressed(mouseEvent)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(MouseEvent mouseEvent) {
/* 553 */     for (MouseController controller : this.m_mouseControllers) {
/* 554 */       if (controller.mouseReleased(mouseEvent)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseEntered(MouseEvent mouseEvent) {
/* 564 */     for (MouseController controller : this.m_mouseControllers) {
/* 565 */       if (controller.mouseEntered(mouseEvent)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseEvent mouseEvent) {
/* 575 */     for (MouseController controller : this.m_mouseControllers) {
/* 576 */       if (controller.mouseExited(mouseEvent)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseDragged(MouseEvent mouseEvent) {
/* 586 */     for (MouseController controller : this.m_mouseControllers) {
/* 587 */       if (controller.mouseDragged(mouseEvent)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(MouseEvent mouseEvent) {
/* 597 */     for (MouseController controller : this.m_mouseControllers) {
/* 598 */       if (controller.mouseMoved(mouseEvent))
/*     */         break; 
/*     */     } 
/*     */   }
/*     */   public void mouseWheelMoved(MouseWheelEvent mouseEvent) {
/* 603 */     for (MouseController controller : this.m_mouseControllers) {
/* 604 */       if (controller.mouseWheelMoved(mouseEvent))
/*     */         break; 
/*     */     } 
/*     */   }
/*     */   public void pushMouseController(MouseController controller, boolean bPushFront) {
/* 609 */     if (!this.m_mouseControllers.contains(controller)) {
/* 610 */       if (!bPushFront) {
/* 611 */         this.m_mouseControllers.add(controller);
/*     */       } else {
/* 613 */         this.m_mouseControllers.add(0, controller);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeMouseController(MouseController controller) {
/* 619 */     if (!this.m_mouseControllers.contains(controller)) {
/* 620 */       this.m_mouseControllers.remove(controller);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isSyncWait() {
/* 625 */     return this.m_syncWait;
/*     */   }
/*     */   
/*     */   public void setSyncWait(boolean syncWait) {
/* 629 */     this.m_syncWait = syncWait;
/*     */   }
/*     */   
/*     */   public boolean isDoubleBuffering() {
/* 633 */     return this.m_doubleBuffering;
/*     */   }
/*     */   
/*     */   public void setDoubleBuffering(boolean doubleBuffering) {
/* 637 */     this.m_doubleBuffering = doubleBuffering;
/*     */   }
/*     */   
/*     */   public void setInitialisationListener(RendererInitialisationListener listener) {
/* 641 */     this.m_initialisationListener = listener;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\Renderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */