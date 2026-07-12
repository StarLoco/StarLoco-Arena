/*     */ package com.ankamagames.baseImpl.graphicalClient;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.BasicProxyClientInstance;
/*     */ import com.ankamagames.baseImpl.graphicalClient.alea.GameWorldScene;
/*     */ import com.ankamagames.baseImpl.graphicalClient.core.GamePreferences;
/*     */ import com.ankamagames.baseImpl.graphicalClient.core.contentLoader.ContentInitializer;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*     */ import com.ankamagames.framework.devices.DeviceSelector;
/*     */ import com.ankamagames.framework.devices.DeviceSelectorEventsHandler;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.DescriptorLibraryManager;
/*     */ import com.ankamagames.framework.graphics.opengl.Renderer;
/*     */ import com.ankamagames.framework.graphics.opengl.RendererInitialisationListener;
/*     */ import com.ankamagames.framework.graphics.opengl.TextureManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.GLRenderable;
/*     */ import com.ankamagames.framework.graphics.opengl.base.states.DefaultScenePostRenderStates;
/*     */ import com.ankamagames.framework.graphics.opengl.base.states.DefaultScenePreRenderStates;
/*     */ import com.ankamagames.framework.graphics.opengl.base.states.GLRenderStates;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.framework.kernel.core.common.message.scheduler.MessageScheduler;
/*     */ import com.ankamagames.framework.kernel.core.controllers.KeyboardController;
/*     */ import com.ankamagames.framework.kernel.core.controllers.MouseController;
/*     */ import com.ankamagames.framework.preferences.PreferenceStore;
/*     */ import com.ankamagames.framework.preferences.StackedPreferenceStore;
/*     */ import com.ankamagames.framework.sounds.SoundManager;
/*     */ import com.ankamagames.framework.sounds.group.AudioSourceGroup;
/*     */ import com.ankamagames.framework.sounds.group.MusicGroup;
/*     */ import com.ankamagames.graphics.opengl.GLFrame;
/*     */ import com.ankamagames.graphics.opengl.initializer.DefaultGLInitializer;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.XulorScene;
/*     */ import com.ankamagames.xulor.binding.XulorSceneEventListener;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.DisplayMode;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.Toolkit;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.JOptionPane;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractGameClientInstance
/*     */   extends BasicProxyClientInstance
/*     */   implements DeviceSelectorEventsHandler, RendererInitialisationListener
/*     */ {
/*     */   private static final String DEFAULT_USER_PREFERENCES_FILE = "userPreferences.properties";
/*     */   public static final String MUSICS_GROUP_NAME = "musics";
/*     */   public static final String GAME_PREFERENCES_PROPERTY_NAME = "gamePreferences";
/*     */   private String m_title;
/*     */   private boolean m_resizable;
/*     */   protected XulorScene m_xulorScene;
/*     */   protected AleaWorldScene m_worldScene;
/*     */   private GLFrame m_gameFrame;
/*  94 */   private final List<ContentInitializer> m_contentInitializers = new ArrayList<ContentInitializer>();
/*  95 */   private int m_currentContentInitializerIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   private final StackedPreferenceStore m_preferenceStore = new StackedPreferenceStore("userPreferences.properties");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GamePreferences m_gamePreferences;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractGameClientInstance(boolean startInOpenGLThread) {
/* 117 */     setTitle("Ankama Games");
/*     */ 
/*     */     
/* 120 */     createGamePreferences(getPreferenceStore());
/*     */ 
/*     */     
/* 123 */     initializeUserPreferences(getPreferenceStore());
/*     */ 
/*     */     
/* 126 */     if (startInOpenGLThread) {
/* 127 */       Worker.getInstance().startInOpenGLThread();
/*     */     } else {
/* 129 */       Worker.getInstance().start();
/*     */     } 
/* 131 */     MessageScheduler.getInstance().start();
/*     */ 
/*     */     
/* 134 */     createGameFrame();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String title) {
/* 142 */     this.m_title = title;
/* 143 */     if (this.m_gameFrame != null) {
/* 144 */       this.m_gameFrame.setTitle(title);
/* 145 */       URL iconUrl = getClass().getResource("icon.png");
/* 146 */       if (iconUrl != null) {
/* 147 */         this.m_gameFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(iconUrl));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResizable(boolean resizable) {
/* 156 */     this.m_resizable = resizable;
/* 157 */     if (this.m_gameFrame != null) {
/* 158 */       this.m_gameFrame.setResizable(resizable);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GLFrame getGameFrame() {
/* 166 */     return this.m_gameFrame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XulorScene getXulorScene() {
/* 173 */     return this.m_xulorScene;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AleaWorldScene getWorldScene() {
/* 180 */     return this.m_worldScene;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackedPreferenceStore getPreferenceStore() {
/* 187 */     return this.m_preferenceStore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GamePreferences getGamePreferences() {
/* 194 */     if (this.m_gamePreferences == null) {
/* 195 */       setGamePreferences(new GamePreferences((PreferenceStore)getPreferenceStore()));
/*     */     }
/* 197 */     return this.m_gamePreferences;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setGamePreferences(GamePreferences gamePreferences) {
/* 204 */     this.m_gamePreferences = gamePreferences;
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
/*     */   public void initializeUserPreferences(StackedPreferenceStore preferenceStore) {
/*     */     try {
/* 219 */       preferenceStore.load();
/* 220 */     } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     GamePreferences gamePreferences = getGamePreferences();
/* 226 */     if (gamePreferences != null) {
/*     */ 
/*     */       
/* 229 */       gamePreferences.initializeDefaultValues();
/*     */ 
/*     */       
/* 232 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("gamePreferences", gamePreferences);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createGameFrame() {
/* 252 */     this.m_gameFrame = new GLFrame();
/* 253 */     setTitle(this.m_title);
/* 254 */     setResizable(this.m_resizable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initXulor() throws Exception {
/* 264 */     Xulor.getInstance().setPreferenceStore((PreferenceStore)getPreferenceStore());
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
/*     */   protected void initWorldScene(AleaWorldScene worldScene) throws Exception {
/* 299 */     if (this.m_worldScene != null) {
/* 300 */       this.m_worldScene.setFrustumCentered(true);
/* 301 */       this.m_worldScene.setUsingZSorting(true);
/* 302 */       this.m_worldScene.setPreRenderStates((GLRenderStates)new DefaultScenePreRenderStates());
/* 303 */       this.m_worldScene.setPostRenderStates((GLRenderStates)new DefaultScenePostRenderStates());
/*     */     } else {
/* 305 */       BasicProxyClientInstance.m_logger.error("Impossible d'initialiser la WorldScene car elle n'a pas été créée !");
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
/*     */ 
/*     */   
/*     */   protected void initSound(String soundDeviceName, int nbSources, int nbBuffers, float musicVolume, boolean musicMute) throws Exception {
/* 319 */     if (soundDeviceName != null) {
/* 320 */       SoundManager.getInstance().initialize(soundDeviceName, nbSources, nbBuffers);
/* 321 */       SoundManager.getInstance().setPriority(10);
/* 322 */       SoundManager.getInstance().start();
/*     */       
/* 324 */       MusicGroup musics = new MusicGroup("musics");
/* 325 */       SoundManager.getInstance().addGroup((AudioSourceGroup)musics);
/* 326 */       musics.setMaxGain(musicVolume);
/* 327 */       musics.setMute(musicMute);
/*     */       return;
/*     */     } 
/* 330 */     BasicProxyClientInstance.m_logger.error("Impossible d'initialier SoundDevice, car aucun nom de soundDevice n'est spécifié !");
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
/*     */   protected boolean initVideo(int screenWidth, int screenHeight, int screenBpp, boolean bDoubleBuffering, boolean bVSync, boolean bFullScreen, int frameRate) throws Exception {
/* 346 */     DefaultGLInitializer glInitializer = new DefaultGLInitializer(true);
/* 347 */     glInitializer.setScreenWidth(screenWidth);
/* 348 */     glInitializer.setScreenHeight(screenHeight);
/* 349 */     glInitializer.setBpp(screenBpp);
/* 350 */     glInitializer.setDoubleBuffered(bDoubleBuffering);
/* 351 */     glInitializer.setFrameRate(frameRate);
/*     */ 
/*     */     
/* 354 */     Renderer renderer = glInitializer.getRenderer();
/* 355 */     if (renderer != null) {
/* 356 */       renderer.setDoubleBuffering(bDoubleBuffering);
/* 357 */       renderer.setSyncWait(bVSync);
/* 358 */       renderer.setInitialisationListener(this);
/*     */     } 
/*     */ 
/*     */     
/* 362 */     DescriptorLibraryManager.getInstance().setRenderer(renderer);
/*     */ 
/*     */ 
/*     */     
/* 366 */     if (this.m_gameFrame != null) {
/* 367 */       this.m_gameFrame.setGlInitializer(glInitializer);
/* 368 */       this.m_gameFrame.addWorkspaceToContentPane();
/* 369 */       this.m_gameFrame.setFullScreen(bFullScreen);
/*     */     } 
/*     */ 
/*     */     
/* 373 */     initXulor();
/* 374 */     this.m_xulorScene = createXulorScene();
/* 375 */     this.m_xulorScene.addEventListener(new XulorSceneEventListener()
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onProcess(XulorScene scene, long realTime, int frameCount)
/*     */           {
/*     */             try {
/* 385 */               AbstractGameClientInstance.this.registerContentInitializers();
/* 386 */               AbstractGameClientInstance.this.runInitializers();
/* 387 */             } catch (Exception e) {
/* 388 */               JOptionPane.showMessageDialog((Component)AbstractGameClientInstance.this.getGameFrame(), String.valueOf(e.getMessage()) + " (" + e.getClass().getName() + ")");
/*     */             } 
/* 390 */             scene.removeEventListener(this);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onXulorSceneInitializationComplete(XulorScene scene) {}
/*     */         });
/* 402 */     initXulorScene(this.m_xulorScene);
/* 403 */     if (this.m_xulorScene != null) {
/* 404 */       renderer.pushScene((GLRenderable)this.m_xulorScene, true);
/* 405 */       renderer.pushMouseController((MouseController)this.m_xulorScene, true);
/* 406 */       renderer.pushKeyboardController((KeyboardController)this.m_xulorScene, false);
/*     */     } 
/*     */ 
/*     */     
/* 410 */     this.m_worldScene = (AleaWorldScene)createWorldScene();
/* 411 */     initWorldScene(this.m_worldScene);
/* 412 */     if (this.m_worldScene != null) {
/* 413 */       renderer.pushScene((GLRenderable)this.m_worldScene, false);
/* 414 */       renderer.pushMouseController((MouseController)this.m_worldScene, false);
/* 415 */       renderer.pushKeyboardController((KeyboardController)this.m_worldScene, false);
/*     */     } 
/*     */     
/* 418 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void showFrame() {
/* 425 */     if (this.m_gameFrame != null) {
/*     */ 
/*     */       
/* 428 */       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 429 */       this.m_gameFrame.setLocation((screenSize.width - this.m_gameFrame.getWidth()) / 2, (screenSize.height - this.m_gameFrame.getHeight()) / 2);
/*     */ 
/*     */       
/* 432 */       this.m_gameFrame.setVisible(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerContentInitializer(ContentInitializer loader) {
/* 443 */     this.m_contentInitializers.add(loader);
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
/*     */ 
/*     */   
/*     */   private void runInitializers() throws Exception {
/* 461 */     this.m_currentContentInitializerIndex = -1;
/* 462 */     onContentInitializeStart(this.m_contentInitializers.size() - 1);
/* 463 */     processNextContentInitializer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processNextContentInitializer() {
/* 470 */     if (++this.m_currentContentInitializerIndex < this.m_contentInitializers.size()) {
/* 471 */       ContentInitializer contentLoader = this.m_contentInitializers.get(this.m_currentContentInitializerIndex);
/* 472 */       if (contentLoader != null) {
/*     */         try {
/* 474 */           contentLoader.init(this);
/* 475 */         } catch (Exception e) {
/* 476 */           onContentInitializerrError(contentLoader, e);
/*     */         } 
/*     */       }
/*     */     } else {
/* 480 */       onContentInitializeFinished(this.m_currentContentInitializerIndex);
/* 481 */       start();
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
/*     */   public void fireContentInitializerDone(ContentInitializer contentInitializer) {
/* 493 */     onContentInitializerDone(contentInitializer, this.m_currentContentInitializerIndex);
/*     */     
/*     */     try {
/* 496 */       ContentInitializer contentLoader = this.m_contentInitializers.get(this.m_currentContentInitializerIndex + 2);
/* 497 */       onContentInitializerStart(contentLoader);
/* 498 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 504 */     this.m_xulorScene.addEventListener(new XulorSceneEventListener()
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onProcess(XulorScene scene, long realTime, int frameCount)
/*     */           {
/* 513 */             AbstractGameClientInstance.this.processNextContentInitializer();
/* 514 */             scene.removeEventListener(this);
/*     */           }
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
/*     */           public void onXulorSceneInitializationComplete(XulorScene scene) {}
/*     */         });
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
/*     */   public void cleanUp() {
/* 575 */     MobileManager.getInstance().removeAllMobiles();
/*     */ 
/*     */     
/* 578 */     TextureManager.getInstance().releaseAllResources();
/*     */     
/* 580 */     WorldManager.getInstance().releaseAllResources();
/* 581 */     WorldManager.getInstance().update();
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
/*     */   public void onInitialisationError(Object obj, String message) {
/* 614 */     m_logger.trace(message);
/* 615 */     System.exit(0);
/*     */   }
/*     */   
/*     */   protected abstract void createGamePreferences(StackedPreferenceStore paramStackedPreferenceStore);
/*     */   
/*     */   public abstract void initialize() throws Exception;
/*     */   
/*     */   protected abstract XulorScene createXulorScene() throws Exception;
/*     */   
/*     */   protected abstract void initXulorScene(XulorScene paramXulorScene) throws Exception;
/*     */   
/*     */   protected abstract GameWorldScene createWorldScene() throws Exception;
/*     */   
/*     */   protected abstract void registerContentInitializers();
/*     */   
/*     */   protected abstract void onContentInitializeStart(int paramInt);
/*     */   
/*     */   protected abstract void onContentInitializeFinished(int paramInt);
/*     */   
/*     */   protected abstract void onContentInitializerStart(ContentInitializer paramContentInitializer);
/*     */   
/*     */   protected abstract void onContentInitializerrError(ContentInitializer paramContentInitializer, Exception paramException);
/*     */   
/*     */   protected abstract void onContentInitializerDone(ContentInitializer paramContentInitializer, int paramInt);
/*     */   
/*     */   protected abstract void start();
/*     */   
/*     */   public abstract void onWorldSceneInitialized();
/*     */   
/*     */   public abstract void enumDevice(GraphicsDevice paramGraphicsDevice);
/*     */   
/*     */   public abstract void enumDisplayMode(DisplayMode paramDisplayMode);
/*     */   
/*     */   public abstract void initialize(DeviceSelector paramDeviceSelector);
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\AbstractGameClientInstance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */