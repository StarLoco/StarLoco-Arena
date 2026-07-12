/*     */ package com.ankamagames.dofusarena.client;
/*     */ 
/*     */ import com.ankamagames.alea.AleaDocumentAccessor;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.AbstractClientMessageDecoder;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.proxy.ProxyGroup;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.statistics.StatisticsReportManager;
/*     */ import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
/*     */ import com.ankamagames.baseImpl.graphicalClient.alea.GameWorldScene;
/*     */ import com.ankamagames.baseImpl.graphicalClient.core.GameEntity;
/*     */ import com.ankamagames.baseImpl.graphicalClient.core.GamePreferences;
/*     */ import com.ankamagames.baseImpl.graphicalClient.core.contentLoader.ContentInitializer;
/*     */ import com.ankamagames.baseImpl.graphicalClient.ui.shortcuts.ShortcutManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.CustomElementFactory;
/*     */ import com.ankamagames.baseImpl.graphics.alea.CustomElementProcessor;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldElementManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldMapDocumentAccessor;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle.MovementStyleManager;
/*     */ import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
/*     */ import com.ankamagames.dofusarena.client.alea.element.DofusArenaCustomElementFactory;
/*     */ import com.ankamagames.dofusarena.client.alea.element.DofusArenaCustomElementProcessor;
/*     */ import com.ankamagames.dofusarena.client.alea.mobile.ThrowMovementStyle;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.contentInitializer.CardLoader;
/*     */ import com.ankamagames.dofusarena.client.core.contentInitializer.ChatInitializer;
/*     */ import com.ankamagames.dofusarena.client.core.contentInitializer.ConsoleLoader;
/*     */ import com.ankamagames.dofusarena.client.core.contentInitializer.EventLoader;
/*     */ import com.ankamagames.dofusarena.client.core.contentInitializer.FightDefinitionLoader;
/*     */ import com.ankamagames.dofusarena.client.core.contentInitializer.SpellLoader;
/*     */ import com.ankamagames.dofusarena.client.core.contentInitializer.StaticEffectLoader;
/*     */ import com.ankamagames.dofusarena.client.core.contentInitializer.SummoningLoader;
/*     */ import com.ankamagames.dofusarena.client.core.preferences.DofusArenaGamePreferences;
/*     */ import com.ankamagames.dofusarena.client.network.entity.DofusArenaNetworkEntity;
/*     */ import com.ankamagames.dofusarena.client.network.event.DofusArenaNetworkEventsHandler;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.DofusArenaMessageDecoder;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.actions.Actions;
/*     */ import com.ankamagames.dofusarena.client.ui.actions.ChatActions;
/*     */ import com.ankamagames.dofusarena.client.ui.actions.ConsoleActions;
/*     */ import com.ankamagames.dofusarena.client.ui.progress.DofusArenaProgressMonitorManager;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIAuthentificationFrame;
/*     */ import com.ankamagames.dofusarena.common.constants.Version;
/*     */ import com.ankamagames.framework.devices.DeviceSelector;
/*     */ import com.ankamagames.framework.fileFormat.properties.PropertiesReaderWriter;
/*     */ import com.ankamagames.framework.fileFormat.properties.PropertyException;
/*     */ import com.ankamagames.framework.graphics.opengl.RendererEventsHandler;
/*     */ import com.ankamagames.framework.graphics.opengl.TextureManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Texture;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.controllers.KeyboardController;
/*     */ import com.ankamagames.framework.kernel.core.translator.Translator;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.framework.kernel.events.NetworkEventsHandler;
/*     */ import com.ankamagames.framework.preferences.PreferenceStore;
/*     */ import com.ankamagames.framework.preferences.StackedPreferenceStore;
/*     */ import com.ankamagames.framework.script.LuaManager;
/*     */ import com.ankamagames.framework.sounds.SoundManager;
/*     */ import com.ankamagames.framework.sounds.group.DefaultSourceGroup;
/*     */ import com.ankamagames.framework.sounds.group.MusicGroup;
/*     */ import com.ankamagames.graphics.isometric.highlight.HighLightManager;
/*     */ import com.ankamagames.graphics.isometric.particles.IsoParticleSystemFactory;
/*     */ import com.ankamagames.graphics.opengl.DefaultEngineConfigurator;
/*     */ import com.ankamagames.graphics.opengl.GLWorkspace;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.Binding;
/*     */ import com.ankamagames.xulor.binding.XulorScene;
/*     */ import com.ankamagames.xulor.binding.XulorSceneFactory;
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiBinding;
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiScene;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
/*     */ import com.ankamagames.xulor.shortcuts.AbstractShortcutManager;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.DisplayMode;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.Toolkit;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DofusArenaClientInstance
/*     */   extends AbstractGameClientInstance {
/*  88 */   private static Logger m_logger = Logger.getLogger(DofusArenaClientInstance.class);
/*     */   
/*  90 */   private static DofusArenaClientInstance m_instance = new DofusArenaClientInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DofusArenaClientInstance() {
/*  96 */     super(DofusArenaConfiguration.getInstance().isStartInOpenGLThread());
/*     */ 
/*     */     
/*  99 */     setTitle("Dofus-Arena");
/* 100 */     setResizable(false);
/*     */ 
/*     */     
/* 103 */     setNetworkEntityFactory(new ObjectFactory<NetworkEntity>()
/*     */         {
/*     */           public NetworkEntity makeObject() {
/* 106 */             return (NetworkEntity)new DofusArenaNetworkEntity((GameEntity)DofusArenaGameEntity.getInstance());
/*     */           }
/*     */         });
/* 109 */     setClientMessageDecoder((AbstractClientMessageDecoder)new DofusArenaMessageDecoder());
/* 110 */     setNetworkEventHandler((NetworkEventsHandler)new DofusArenaNetworkEventsHandler());
/*     */ 
/*     */     
/* 113 */     createProxyClient();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DofusArenaClientInstance getInstance() {
/* 121 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Logger getLogger() {
/* 128 */     return m_logger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DofusArenaGamePreferences getGamePreferences() {
/* 138 */     return (DofusArenaGamePreferences)super.getGamePreferences();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createGamePreferences(StackedPreferenceStore preferenceStore) {
/* 148 */     setGamePreferences((GamePreferences)new DofusArenaGamePreferences((PreferenceStore)preferenceStore));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeUserPreferences(StackedPreferenceStore preferenceStore) {
/* 158 */     super.initializeUserPreferences(preferenceStore);
/* 159 */     preferenceStore.setAutoSave(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() throws Exception {
/* 170 */     DofusArenaConfiguration config = DofusArenaConfiguration.getInstance();
/* 171 */     DofusArenaGamePreferences gameOptions = getGamePreferences();
/*     */ 
/*     */     
/* 174 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 175 */     Dimension screenSize = toolkit.getScreenSize();
/* 176 */     boolean isStandardResolution = !((screenSize.width != 1024 || screenSize.height != 768) && (screenSize.width != 1280 || screenSize.height != 1024) && (
/* 177 */       screenSize.width != 1600 || screenSize.height != 1200));
/*     */ 
/*     */     
/* 180 */     int screenWidth = gameOptions.getScreenWidth();
/* 181 */     int screenHeight = gameOptions.getScreenHeight();
/* 182 */     int screenBpp = gameOptions.getScreenBpp();
/* 183 */     boolean bFullScreen = (gameOptions.getFullScreen() && isStandardResolution);
/* 184 */     boolean bDoubleBuffering = gameOptions.getDoubleBuffering();
/* 185 */     boolean bVSync = gameOptions.getVSync();
/*     */     
/* 187 */     initVideo(screenWidth, screenHeight, screenBpp, bDoubleBuffering, bVSync, bFullScreen, 30);
/*     */ 
/*     */     
/* 190 */     String soundDevice = config.getString("soundDevice");
/* 191 */     boolean soundEnable = config.getBoolean("soundEnable");
/*     */     
/* 193 */     if (soundEnable) {
/* 194 */       initSound(soundDevice, 32, 1000, gameOptions.getMusicVolume(), gameOptions.getMusicMute());
/*     */       
/* 196 */       MusicGroup musics = (MusicGroup)SoundManager.getInstance().getGroupByName("musics");
/* 197 */       if (musics != null) {
/* 198 */         musics.loadPlayList(config.getString("playlistFile"));
/*     */       }
/*     */       
/* 201 */       String sndPath = DofusArenaConfiguration.getInstance().getString("soundPath");
/* 202 */       DefaultSourceGroup soundsGroup = (DefaultSourceGroup)SoundManager.getInstance().getGroupByName("SoundScriptGroup");
/* 203 */       if (soundsGroup != null) {
/* 204 */         soundsGroup.setSoundFilesBasePath(sndPath);
/* 205 */         soundsGroup.setSoundFilesExtension("ogg");
/* 206 */         soundsGroup.setMaxGain(gameOptions.getSoundsVolume());
/* 207 */         soundsGroup.setMute(gameOptions.getSoundsMute());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 212 */     ShortcutManager shortcutManager = ShortcutManager.getInstance();
/* 213 */     String shortcutsFile = DofusArenaConfiguration.getInstance().getString("shortcutsFile");
/*     */     try {
/* 215 */       shortcutManager.loadFromXMLFile(shortcutsFile);
/* 216 */       shortcutManager.enableGroup("debug", true);
/* 217 */       getGameFrame().getGlInitializer().getRenderer().pushKeyboardController((KeyboardController)shortcutManager, false);
/* 218 */     } catch (Exception e) {
/* 219 */       m_logger.error("Exception : ", e);
/* 220 */       throw new Exception("Impossible de charger les raccourcis clavier depuis le fichier " + shortcutsFile + " !");
/*     */     } 
/*     */ 
/*     */     
/* 224 */     LuaManager.getInstance().setPath(DofusArenaConfiguration.getInstance().getString("scriptPath"));
/* 225 */     LuaManager.getInstance().addDefaultLibraries(new com.ankamagames.framework.script.JavaFunctionsLibrary[0]);
/*     */ 
/*     */     
/* 228 */     MovementStyleManager.getInstance().registerStyle("Throw", ThrowMovementStyle.class);
/*     */ 
/*     */     
/* 231 */     StatisticsReportManager.getInstance().loadModelsFromXMLFile(DofusArenaConfiguration.getInstance().getString("statisticsReportsModelsFile"));
/*     */ 
/*     */     
/* 234 */     showFrame();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerContentInitializers() {
/* 245 */     registerContentInitializer((ContentInitializer)ConsoleLoader.getInstance());
/* 246 */     registerContentInitializer((ContentInitializer)ChatInitializer.getInstance());
/*     */     
/* 248 */     registerContentInitializer((ContentInitializer)SpellLoader.getInstance());
/* 249 */     registerContentInitializer((ContentInitializer)EventLoader.getInstance());
/* 250 */     registerContentInitializer((ContentInitializer)StaticEffectLoader.getInstance());
/* 251 */     registerContentInitializer((ContentInitializer)SummoningLoader.getInstance());
/* 252 */     registerContentInitializer((ContentInitializer)CardLoader.getInstance());
/* 253 */     registerContentInitializer((ContentInitializer)FightDefinitionLoader.getInstance());
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
/*     */   public void start() {
/* 272 */     DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIAuthentificationFrame.getInstance());
/*     */ 
/*     */     
/* 275 */     PropertiesProvider propertiesProvider = Xulor.getInstance().getEnvironment().getPropertiesProvider();
/* 276 */     propertiesProvider.setPropertyValue("account.name", getGamePreferences().getLastLogin());
/* 277 */     propertiesProvider.setPropertyValue("account.remember", Boolean.valueOf(getGamePreferences().getRememberLastLogin()));
/*     */ 
/*     */ 
/*     */     
/* 281 */     propertiesProvider.setPropertyValue("proxy.list", 
/* 282 */         ProxyGroup.extractProxyGroupsFromProperties((PropertiesReaderWriter)DofusArenaConfiguration.getInstance(), "proxyGroup", "proxyAddresses").toArray());
/*     */ 
/*     */     
/* 285 */     Xulor.getInstance().load("logonDialog", Dialogs.getDialogPath("logonDialog"), 0L, (short)10000);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/* 296 */     super.cleanUp();
/*     */ 
/*     */     
/* 299 */     ShortcutManager.getInstance().enableGroup("common", false);
/* 300 */     ShortcutManager.getInstance().enableGroup("world", false);
/* 301 */     ShortcutManager.getInstance().enableGroup("fight", false);
/*     */ 
/*     */     
/* 304 */     DofusArenaGameEntity.getInstance().cleanUp();
/*     */ 
/*     */     
/* 307 */     Xulor.getInstance().unloadAll();
/*     */ 
/*     */     
/* 310 */     Xulor.getInstance().removeAllActionClass();
/*     */ 
/*     */     
/* 313 */     Xulor.getInstance().hideMouseImage();
/* 314 */     Xulor.getInstance().hidePopupMenu();
/* 315 */     Xulor.getInstance().hideTooltip();
/*     */ 
/*     */     
/* 318 */     loadDefaultXulorActionClasses();
/*     */ 
/*     */     
/* 321 */     getWorldScene().clean(false);
/*     */ 
/*     */     
/* 324 */     start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enumDevice(GraphicsDevice device) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enumDisplayMode(DisplayMode mode) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(DeviceSelector selector) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initXulor() throws Exception {
/* 361 */     super.initXulor();
/*     */ 
/*     */     
/* 364 */     Xulor.getInstance().setBinding((Binding)FengguiBinding.getInstance());
/*     */ 
/*     */     
/* 367 */     Xulor.getInstance().setShortcutManager((AbstractShortcutManager)ShortcutManager.getInstance());
/*     */ 
/*     */     
/* 370 */     Xulor.getInstance().setTranslator((Translator)DofusArenaTranslator.getInstance());
/*     */ 
/*     */     
/* 373 */     loadDefaultXulorActionClasses();
/*     */ 
/*     */     
/* 376 */     Xulor.getInstance().loadTheme(DofusArenaConfiguration.getInstance().getString("themeFile"), 
/* 377 */         DofusArenaConfiguration.getInstance().getString("themeDirectory"));
/*     */ 
/*     */ 
/*     */     
/* 381 */     Xulor.getInstance().setMessageBoxPath(Dialogs.getDialogPath("messageBoxDialog"));
/* 382 */     Xulor.getInstance().setPopupMenuPath(Dialogs.getDialogPath("popupDialog"));
/*     */ 
/*     */     
/* 385 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("buildVersion", Version.READABLE_VERSION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XulorScene createXulorScene() throws Exception {
/* 395 */     XulorScene scene = Xulor.getInstance().createScene(new XulorSceneFactory() {
/*     */           public XulorScene createScene(GLWorkspace workspace) {
/* 397 */             return (XulorScene)new FengguiScene();
/*     */           }
/* 399 */         },  getGameFrame().getGlInitializer().getWorkspace());
/* 400 */     return scene;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initXulorScene(XulorScene xulorScene) throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GameWorldScene createWorldScene() throws Exception {
/* 419 */     return (GameWorldScene)new DofusArenaWorldScene(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initWorldScene(AleaWorldScene worldScene) throws Exception {
/* 429 */     super.initWorldScene(worldScene);
/*     */ 
/*     */     
/* 432 */     DefaultEngineConfigurator engineConfig = new DefaultEngineConfigurator();
/* 433 */     engineConfig.setShadersPath(DofusArenaConfiguration.getInstance().getString("shadersPath"));
/* 434 */     getGameFrame().getGlInitializer().getRenderer().addRendererEventsHandler((RendererEventsHandler)engineConfig);
/*     */     
/*     */     try {
/* 437 */       WorldElementManager.getInstance().setCustomElementFactory((CustomElementFactory)DofusArenaCustomElementFactory.getInstance());
/* 438 */       WorldElementManager.getInstance().setElementsFileName(DofusArenaConfiguration.getInstance().getString("elementsFile"));
/* 439 */       WorldElementManager.getInstance().loadElementFile();
/*     */       
/* 441 */       WorldMapDocumentAccessor accessor = new WorldMapDocumentAccessor();
/* 442 */       accessor.setParticleActivated(DofusArenaConfiguration.getInstance().getBoolean("activateMapParticles"));
/* 443 */       accessor.setCustomElementHandler((CustomElementProcessor)DofusArenaCustomElementProcessor.getInstance());
/* 444 */       WorldManager.getInstance().setDocumentAccessor((AleaDocumentAccessor)accessor);
/* 445 */       worldScene.setGfxPath(DofusArenaConfiguration.getInstance().getString("gfxPath"));
/* 446 */       worldScene.setSndPath(DofusArenaConfiguration.getInstance().getString("soundPath"));
/*     */ 
/*     */       
/* 449 */       IsoParticleSystemFactory.getInstance().setPath(DofusArenaConfiguration.getInstance().getString("particlePath"));
/*     */     }
/* 451 */     catch (PropertyException e) {
/* 452 */       m_logger.error("Erreur à l'initialisation de la worldScene", (Throwable)e);
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
/*     */   public void onWorldSceneInitialized() {
/*     */     try {
/* 468 */       Texture texture = TextureManager.createRawTextureFromFile(DofusArenaConfiguration.getInstance().getString("highLightGfxFile"));
/* 469 */       BaseTexture defaultTexture = new BaseTexture();
/* 470 */       defaultTexture.setTexture(texture);
/* 471 */       HighLightManager.getInstance().setDefaultTexture(defaultTexture);
/*     */     }
/* 473 */     catch (Exception e) {
/* 474 */       m_logger.error("Erreur à l'initialisation du HighLightManager", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadDefaultXulorActionClasses() {
/* 483 */     Xulor.getInstance().putActionClass("dofusarena", Actions.class);
/* 484 */     Xulor.getInstance().putActionClass("console", ConsoleActions.class);
/* 485 */     Xulor.getInstance().putActionClass("dofusarena.chat", ChatActions.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onContentInitializeStart(int loadedContentLoaders) {
/* 495 */     DofusArenaProgressMonitorManager.getInstance().getProgressMonitor(true).beginTask(DofusArenaTranslator.getInstance().getString("loading", new Object[0]), loadedContentLoaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onContentInitializeFinished(int loadedContentLoaders) {
/* 505 */     DofusArenaProgressMonitorManager.getInstance().done();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onContentInitializerStart(ContentInitializer contentLoader) {
/* 515 */     DofusArenaProgressMonitorManager.getInstance().getProgressMonitor(true).subTask(contentLoader.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onContentInitializerrError(ContentInitializer contentLoader, Exception exception) {
/* 526 */     Xulor.getInstance().msgBox(String.valueOf(DofusArenaTranslator.getInstance().getString("error.loading", new Object[0])) + contentLoader.getName(), 66);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onContentInitializerDone(ContentInitializer contentLoader, int loadedContentLoaderCount) {
/* 537 */     DofusArenaProgressMonitorManager.getInstance().getProgressMonitor(true).worked(loadedContentLoaderCount);
/* 538 */     DofusArenaProgressMonitorManager.getInstance().getProgressMonitor(true).subTask(" ");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInitialisationError(Object obj, String message) {
/* 549 */     String text = Xulor.getInstance().getTranslatedString("error.unsupportedMaterial");
/* 550 */     JOptionPane.showMessageDialog((Component)getGameFrame(), text, "Error", 0);
/* 551 */     super.onInitialisationError(obj, message);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\DofusArenaClientInstance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */