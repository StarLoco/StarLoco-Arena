/*      */ package com.ankamagames.xulor;
/*      */ 
/*      */ import com.ankamagames.framework.kernel.core.translator.Translator;
/*      */ import com.ankamagames.framework.preferences.PreferencePropertyChangeListener;
/*      */ import com.ankamagames.framework.preferences.PreferenceStore;
/*      */ import com.ankamagames.graphics.isometric.text.BackgroundedText;
/*      */ import com.ankamagames.graphics.opengl.GLWorkspace;
/*      */ import com.ankamagames.xulor.actions.XulorActions;
/*      */ import com.ankamagames.xulor.binding.Binding;
/*      */ import com.ankamagames.xulor.binding.ModalManager;
/*      */ import com.ankamagames.xulor.binding.XulorScene;
/*      */ import com.ankamagames.xulor.binding.XulorSceneEventListener;
/*      */ import com.ankamagames.xulor.binding.XulorSceneFactory;
/*      */ import com.ankamagames.xulor.binding.fenggui.FengguiBinding;
/*      */ import com.ankamagames.xulor.binding.fenggui.MouseImageManager;
/*      */ import com.ankamagames.xulor.core.ElementMap;
/*      */ import com.ankamagames.xulor.core.Environment;
/*      */ import com.ankamagames.xulor.core.GLImpl.Tooltip;
/*      */ import com.ankamagames.xulor.core.GenericParser;
/*      */ import com.ankamagames.xulor.core.messagebox.MessageBoxControler;
/*      */ import com.ankamagames.xulor.core.messagebox.MessageBoxFormater;
/*      */ import com.ankamagames.xulor.event.FocusManager;
/*      */ import com.ankamagames.xulor.shortcuts.AbstractShortcutManager;
/*      */ import com.ankamagames.xulor.template.IElement;
/*      */ import com.ankamagames.xulor.template.IMessageBox;
/*      */ import com.ankamagames.xulor.template.IPopupMenu;
/*      */ import com.ankamagames.xulor.theme.ThemeParser;
/*      */ import com.ankamagames.xulor.util.Alignment;
/*      */ import com.ankamagames.xulor.util.Color;
/*      */ import com.ankamagames.xulor.util.ElementAttributes;
/*      */ import com.ankamagames.xulor.util.ToolTipAttributes;
/*      */ import com.ankamagames.xulor.util.XulorInsert;
/*      */ import com.ankamagames.xulor.util.XulorLoad;
/*      */ import com.ankamagames.xulor.util.XulorLoadMouseImage;
/*      */ import com.ankamagames.xulor.util.XulorLoadUnload;
/*      */ import com.ankamagames.xulor.util.XulorUnload;
/*      */ import com.ankamagames.xulor.util.XulorUnloadMouseImage;
/*      */ import java.awt.Font;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import org.apache.log4j.Logger;
/*      */ import org.jdom.Document;
/*      */ import org.jdom.input.SAXBuilder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Xulor
/*      */   implements XulorSceneEventListener
/*      */ {
/*      */   public static final long OPTION_NONE = 0L;
/*      */   public static final long OPTION_ON_TOP = 1L;
/*      */   public static final long OPTION_ON_MSGBOX_LAYER = 2L;
/*      */   public static final long OPTION_FORCE_RELOAD = 4L;
/*      */   public static final long OPTION_DONT_REBUILD = 8L;
/*      */   public static final long OPTION_PARSE_ONLY = 16L;
/*      */   public static final long OPTION_RETURN_CREATED_ELEMENT = 32L;
/*      */   public static final long OPTION_MODAL = 64L;
/*      */   public static final long OPTION_PSEUDO_MODAL = 128L;
/*      */   public static final long OPTION_USER_DEFINED = 256L;
/*      */   public static final int MAX_DURATION = 2147483647;
/*  115 */   private static Logger m_logger = Logger.getLogger(Xulor.class);
/*      */   
/*  117 */   private static final Binding DEFAULT_BINDING = (Binding)FengguiBinding.getInstance();
/*      */   
/*  119 */   private URL m_currentDirectory = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  124 */   private final ArrayList<XulorLoadUnload> m_loadUnloadDocuments = new ArrayList<XulorLoadUnload>();
/*  125 */   private final ArrayList<XulorUnload> m_timedUnloadDocuments = new ArrayList<XulorUnload>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  130 */   private final ArrayList<IElement> m_elementsNeedingLayout = new ArrayList<IElement>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  135 */   private static Xulor m_instance = new Xulor();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  140 */   private XulorScene m_scene = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  145 */   private final HashMap<String, IElement> m_loadedElements = new HashMap<String, IElement>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  150 */   private final ArrayList<String> m_loadedModals = new ArrayList<String>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  155 */   private IElement m_bottomRootContainer = null;
/*  156 */   private IElement m_topRootContainer = null;
/*  157 */   private IElement m_msgBoxRootContainer = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  162 */   private Binding m_binding = DEFAULT_BINDING;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  167 */   private final Environment m_environment = new Environment();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final GenericParser m_parser;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final ThemeParser m_themeParser;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AbstractShortcutManager m_shortcutManager;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final HashMap<String, Class> m_actionClasses;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  192 */   private Translator m_translator = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  197 */   private PreferenceStore m_preferenceStore = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean m_needToRebuildUI = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  208 */   private URL m_messageBoxURL = null;
/*  209 */   private int m_messageBoxId = 0;
/*      */   
/*  211 */   private URL m_popupMenuURL = null;
/*  212 */   private int m_popupMenuId = 0;
/*      */   
/*  214 */   private final ToolTipAttributes m_toolTipAttributes = new ToolTipAttributes();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private IPopupMenu m_popupMenu;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Xulor() {
/*  227 */     this.m_actionClasses = new HashMap<String, Class<?>>();
/*  228 */     putActionClass("xulor", XulorActions.class);
/*      */ 
/*      */     
/*  231 */     this.m_parser = new GenericParser(this.m_environment);
/*  232 */     this.m_themeParser = ThemeParser.getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Xulor getInstance() {
/*  240 */     return m_instance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBinding(Binding binding) {
/*  249 */     if (binding == null) {
/*  250 */       this.m_binding = DEFAULT_BINDING;
/*      */     } else {
/*  252 */       this.m_binding = binding;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShortcutManager(AbstractShortcutManager shortcutManager) {
/*  261 */     this.m_shortcutManager = shortcutManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AbstractShortcutManager getShortcutManager() {
/*  269 */     return this.m_shortcutManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Binding getBinding() {
/*  276 */     return this.m_binding;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Environment getEnvironment() {
/*  283 */     return this.m_environment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ThemeParser getThemeParser() {
/*  290 */     return this.m_themeParser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GenericParser getParser() {
/*  299 */     return this.m_parser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassLoader getClassLoader() {
/*  306 */     return getClass().getClassLoader();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTranslatedString(String key) {
/*  313 */     if (this.m_translator != null) {
/*  314 */       return this.m_translator.getString(key, new Object[0]);
/*      */     }
/*  316 */     return key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTranslator(Translator translator) {
/*  323 */     this.m_translator = translator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPreferenceStore(PreferenceStore preferenceStore) {
/*  330 */     this.m_preferenceStore = preferenceStore;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreferenceStore getPreferenceStore() {
/*  337 */     if (this.m_preferenceStore == null) {
/*  338 */       this.m_preferenceStore = new PreferenceStore();
/*      */     }
/*  340 */     return this.m_preferenceStore;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IElement getTopRootContainer() {
/*  349 */     return this.m_topRootContainer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IElement getBottomRootContainer() {
/*  358 */     return this.m_bottomRootContainer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IElement getMsgBoxRootContainer() {
/*  367 */     return this.m_msgBoxRootContainer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XulorScene getScene() {
/*  376 */     return this.m_scene;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XulorScene createScene(XulorSceneFactory factory, GLWorkspace workspace) {
/*  390 */     XulorScene scene = factory.createScene(workspace);
/*      */ 
/*      */     
/*  393 */     scene.addEventListener(this);
/*      */     
/*  395 */     return scene;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBottomRootContainer(IElement container) {
/*  405 */     this.m_bottomRootContainer = container;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTopRootContainer(IElement container) {
/*  414 */     this.m_topRootContainer = container;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMsgBoxRootContainer(IElement container) {
/*  422 */     this.m_msgBoxRootContainer = container;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMessageBoxPath(String path) {
/*      */     try {
/*  430 */       this.m_messageBoxURL = new URL(path);
/*      */       return;
/*  432 */     } catch (MalformedURLException e1) {
/*  433 */       m_logger.error("Le chemin '" + path + "' vers le fichier de définition des messageBox est invalide !");
/*      */       
/*  435 */       this.m_messageBoxURL = null;
/*      */       return;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPopupMenuPath(String path) {
/*      */     try {
/*  443 */       this.m_popupMenuURL = new URL(path);
/*      */       return;
/*  445 */     } catch (MalformedURLException e1) {
/*  446 */       m_logger.error("Le chemin '" + path + "' vers le fichier de définition des popupMenu est invalide !");
/*      */       
/*  448 */       this.m_popupMenuURL = null;
/*      */       return;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putActionClass(String packageName, Class actionClass) {
/*  459 */     if (this.m_actionClasses.containsKey(packageName)) {
/*  460 */       m_logger.warn("La classe d'actions référencée sous le package " + packageName + " est remplacée par la nouvelle définition.");
/*      */     }
/*  462 */     this.m_actionClasses.put(packageName, actionClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeActionClass(String packageName) {
/*  472 */     if (this.m_actionClasses.containsKey(packageName)) {
/*  473 */       this.m_actionClasses.remove(packageName);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllActionClass() {
/*  481 */     this.m_actionClasses.clear();
/*  482 */     putActionClass("xulor", XulorActions.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> getActionClass() {
/*  490 */     return getActionClass("xulor");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> getActionClass(String packageName) {
/*  499 */     if (packageName == null) {
/*  500 */       return this.m_actionClasses.get("xulor");
/*      */     }
/*  502 */     if (!this.m_actionClasses.containsKey(packageName)) {
/*  503 */       m_logger.error("Le package " + packageName + " est inconnue !");
/*  504 */       return null;
/*      */     } 
/*  506 */     return this.m_actionClasses.get(packageName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadTheme(URI uri, URI directory) throws Exception {
/*  516 */     this.m_themeParser.loadTheme(uri, directory.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadTheme(String path, String directory) throws Exception {
/*  526 */     this.m_themeParser.loadTheme(path, directory);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setElementAttributes(String id, ElementAttributes attributes) {
/*  535 */     IElement element = this.m_loadedElements.get(id);
/*  536 */     element.setElementAttributes(attributes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ElementAttributes getElementAttributes(String id) {
/*  545 */     IElement element = this.m_loadedElements.get(id);
/*  546 */     return element.getElementAttributes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLoaded(String id) {
/*  556 */     return this.m_loadedElements.containsKey(id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IElement load(String id, String resource, short modalLevel) {
/*  569 */     return load(id, resource, 0L, modalLevel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IElement load(String id, String resource, int duration, short modalLevel) {
/*  583 */     return load(id, resource, duration, 0L, modalLevel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IElement load(String id, String resource, long options, short modalLevel) {
/*  597 */     return load(id, resource, 2147483647, options, modalLevel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IElement load(String id, String resource, int duration, long options, short modalLevel) {
/*  612 */     if ((options & 0x1L) == 1L)
/*  613 */       return loadInto(id, resource, this.m_topRootContainer, null, null, duration, options, modalLevel); 
/*  614 */     if ((options & 0x2L) == 2L) {
/*  615 */       return loadInto(id, resource, this.m_msgBoxRootContainer, null, null, duration, options, modalLevel);
/*      */     }
/*  617 */     return loadInto(id, resource, this.m_bottomRootContainer, null, null, duration, options, modalLevel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IElement loadInto(String resource, IElement container, ElementMap elementMap, long options, short modalLevel) {
/*  632 */     return loadInto(null, resource, container, elementMap, null, 2147483647, options, modalLevel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IElement loadInto(String id, String resource, IElement container, ElementMap elementMap, URL baseDirectory, int duration, long options, short modalLevel) {
/*  646 */     URL url = null;
/*      */     
/*      */     try {
/*  649 */       url = new URL(resource);
/*  650 */     } catch (MalformedURLException malformedURLException) {}
/*      */ 
/*      */ 
/*      */     
/*  654 */     if (url == null) {
/*      */       try {
/*  656 */         url = new URL(this.m_currentDirectory, resource);
/*  657 */       } catch (MalformedURLException e1) {
/*  658 */         m_logger.error("url impossible à charger : " + url);
/*      */       } 
/*      */     }
/*      */     
/*  662 */     this.m_currentDirectory = url;
/*      */     
/*  664 */     IElement element = null;
/*  665 */     if ((options & 0x20L) == 32L) {
/*  666 */       element = loadInto(id, url, container, elementMap, this.m_currentDirectory, options, modalLevel);
/*      */     } else {
/*  668 */       synchronized (this.m_loadUnloadDocuments) {
/*  669 */         this.m_loadUnloadDocuments.add(new XulorLoad(url, id, elementMap, container, this.m_currentDirectory, duration, options, modalLevel));
/*      */       } 
/*      */     } 
/*      */     
/*  673 */     return element;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private IElement loadInto(String id, URL url, IElement container, ElementMap elementMap, URL baseDirectory, long options, short modalLevel) {
/*  688 */     IElement newElement = null;
/*      */     
/*  690 */     if ((options & 0x4L) == 4L) {
/*  691 */       unloadId(id);
/*      */     }
/*      */     
/*  694 */     if (id != null && elementMap == null && !this.m_loadedElements.containsKey(id))
/*      */     {
/*  696 */       elementMap = this.m_environment.createElementMap(id);
/*      */     }
/*      */     
/*  699 */     if (elementMap != null) {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  704 */         ElementMap previousElementMap = this.m_environment.getCurrentElementMap();
/*      */ 
/*      */         
/*  707 */         this.m_environment.setCurrentElementMap(elementMap);
/*      */         
/*  709 */         URL oldDirectory = this.m_currentDirectory;
/*  710 */         this.m_currentDirectory = baseDirectory;
/*      */ 
/*      */         
/*  713 */         if ((options & 0x10L) != 16L) {
/*  714 */           newElement = insert(url, container);
/*      */         } else {
/*  716 */           newElement = parse(url);
/*      */         } 
/*      */         
/*  719 */         if ((options & 0x40L) == 64L) {
/*  720 */           ModalManager.getInstance().addModalElement(newElement);
/*  721 */           this.m_loadedModals.add(id);
/*      */         } 
/*      */         
/*  724 */         if ((options & 0x80L) == 128L) {
/*  725 */           ModalManager.getInstance().addPseudoModalElement(newElement);
/*  726 */           this.m_loadedModals.add(id);
/*      */         } 
/*      */         
/*  729 */         this.m_currentDirectory = oldDirectory;
/*      */ 
/*      */         
/*  732 */         this.m_environment.setCurrentElementMap(previousElementMap);
/*      */       }
/*  734 */       catch (Exception e) {
/*  735 */         m_logger.error("Le chargement de " + url + " a échoué", e);
/*      */       } 
/*      */       
/*  738 */       if (newElement != null) {
/*      */         
/*  740 */         newElement.setModalLevel(modalLevel);
/*  741 */         if (id != null)
/*      */         {
/*  743 */           this.m_loadedElements.put(id, newElement);
/*      */         }
/*      */         
/*  746 */         if ((options & 0x8L) != 8L)
/*      */         {
/*  748 */           this.m_needToRebuildUI = true;
/*      */         }
/*      */         
/*  751 */         if ((options & 0x100L) == 256L) {
/*      */           
/*  753 */           newElement.setStatic(false);
/*  754 */           newElement.loadPreferences();
/*  755 */           this.m_preferenceStore.addPreferencePropertyChangedListener((PreferencePropertyChangeListener)newElement);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  762 */     return newElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unload(String id) {
/*  772 */     this.m_loadUnloadDocuments.add(new XulorUnload(id));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unloadId(String id) {
/*  782 */     if (this.m_loadedElements.containsKey(id)) {
/*  783 */       IElement element = this.m_loadedElements.remove(id);
/*  784 */       if (element != null) {
/*  785 */         if (this.m_loadedModals.contains(id)) {
/*  786 */           this.m_loadedModals.remove(id);
/*  787 */           ModalManager.getInstance().removeElement(element);
/*      */         } 
/*  789 */         if (element.equals(this.m_bottomRootContainer) || element.equals(this.m_topRootContainer) || element.equals(this.m_msgBoxRootContainer)) {
/*  790 */           element.removeChildren();
/*      */         } else {
/*  792 */           element.getParent().removeChild(element);
/*      */         } 
/*      */       } 
/*  795 */       this.m_needToRebuildUI = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unloadAll() {
/*  803 */     this.m_loadUnloadDocuments.add(new XulorUnload(true));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unloadAllId() {
/*  810 */     for (String id : this.m_loadedModals) {
/*  811 */       ModalManager.getInstance().removeElement(this.m_loadedElements.get(id));
/*      */     }
/*  813 */     this.m_loadedModals.clear();
/*  814 */     this.m_bottomRootContainer.removeChildren();
/*  815 */     this.m_topRootContainer.removeChildren();
/*  816 */     this.m_msgBoxRootContainer.removeChildren();
/*  817 */     this.m_loadedElements.clear();
/*      */     
/*  819 */     this.m_needToRebuildUI = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MessageBoxControler msgBox(String message) {
/*  828 */     return msgBox(message, " ", 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MessageBoxControler msgBox(String message, int options) {
/*  838 */     return msgBox(message, " ", options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MessageBoxControler msgBox(String message, String title, int options) {
/*  849 */     MessageBoxControler controler = null;
/*      */     try {
/*  851 */       if (this.m_messageBoxURL != null) {
/*      */ 
/*      */ 
/*      */         
/*  855 */         String id = "MessageBox_" + this.m_messageBoxId++;
/*  856 */         if (this.m_messageBoxId > 2147483646) {
/*  857 */           this.m_messageBoxId = 0;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  862 */         ElementMap elementMap = this.m_environment.createElementMap(id);
/*  863 */         this.m_environment.setCurrentElementMap(elementMap);
/*  864 */         IElement element = parse(this.m_messageBoxURL);
/*      */         
/*  866 */         if (element != null && element instanceof IMessageBox) {
/*  867 */           IMessageBox messageBox = (IMessageBox)element;
/*  868 */           messageBox.setModalLevel(ModalManager.MSG_BOX_MODAL_LEVEL);
/*      */ 
/*      */           
/*  871 */           controler = new MessageBoxControler(id, messageBox);
/*      */ 
/*      */           
/*  874 */           MessageBoxFormater.format(messageBox, controler, message, title, options);
/*      */           
/*  876 */           long loadOptions = ((options & 0x1) == 1) ? 64L : 0L;
/*      */           
/*  878 */           this.m_loadUnloadDocuments.add(new XulorInsert((IElement)messageBox, this.m_msgBoxRootContainer, id, loadOptions));
/*      */         } 
/*      */         
/*  881 */         this.m_needToRebuildUI = true;
/*      */       } 
/*  883 */     } catch (Exception e) {
/*  884 */       m_logger.error(e.getMessage());
/*      */     } 
/*      */     
/*  887 */     return controler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IPopupMenu popupMenu() {
/*  896 */     IPopupMenu popupMenu = null;
/*      */     try {
/*  898 */       if (this.m_popupMenuURL != null) {
/*      */ 
/*      */ 
/*      */         
/*  902 */         String id = "PopupMenu_" + this.m_popupMenuId++;
/*  903 */         if (this.m_popupMenuId > 2147483646) {
/*  904 */           this.m_popupMenuId = 0;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  909 */         ElementMap elementMap = this.m_environment.createElementMap(id);
/*  910 */         this.m_environment.setCurrentElementMap(elementMap);
/*  911 */         IElement element = parse(this.m_popupMenuURL);
/*      */         
/*  913 */         if (element != null && element instanceof IPopupMenu) {
/*  914 */           popupMenu = (IPopupMenu)element;
/*  915 */           popupMenu.setId(id);
/*  916 */           popupMenu.setModalLevel(ModalManager.POP_UP_MODAL_LEVEL);
/*      */ 
/*      */           
/*  919 */           this.m_msgBoxRootContainer.add((IElement)popupMenu);
/*      */ 
/*      */           
/*  922 */           this.m_loadedElements.put(id, popupMenu);
/*      */         } 
/*      */         
/*  925 */         this.m_needToRebuildUI = true;
/*      */       } 
/*  927 */     } catch (Exception e) {
/*  928 */       m_logger.error(e.getMessage());
/*      */     } 
/*      */     
/*  931 */     return popupMenu;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showPopupMenu(IPopupMenu popupMenu) {
/*  940 */     hidePopupMenu();
/*  941 */     this.m_popupMenu = popupMenu;
/*  942 */     popupMenu.show();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showPopupMenu(IPopupMenu popupMenu, int x, int y) {
/*  953 */     hidePopupMenu();
/*  954 */     this.m_popupMenu = popupMenu;
/*  955 */     popupMenu.show(x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hidePopupMenu() {
/*  962 */     if (this.m_popupMenu != null) {
/*  963 */       unload(this.m_popupMenu.getId());
/*      */     }
/*      */   }
/*      */   
/*      */   public void showMouseImage(String url, int xOffset, int yOffset, Alignment hotPointPosition) {
/*      */     try {
/*  969 */       showMouseImage(new URL(url), xOffset, yOffset, hotPointPosition);
/*  970 */     } catch (MalformedURLException e) {
/*  971 */       m_logger.warn("URL malformée : \"" + url + "\"");
/*      */     } 
/*      */   }
/*      */   
/*      */   public void showMouseImage(URL url, int xOffset, int yOffset, Alignment hotPointPosition) {
/*  976 */     this.m_loadUnloadDocuments.add(new XulorLoadMouseImage(url, xOffset, yOffset, hotPointPosition));
/*      */   }
/*      */   
/*      */   public void hideMouseImage() {
/*  980 */     this.m_loadUnloadDocuments.add(new XulorUnloadMouseImage());
/*      */   }
/*      */   
/*      */   public void saveToolTipAttributes() {
/*  984 */     if (this.m_scene != null) {
/*  985 */       Tooltip tt = this.m_scene.getTooltip();
/*  986 */       this.m_toolTipAttributes.TEXT = tt.getText();
/*  987 */       this.m_toolTipAttributes.X_OFFSET = Integer.valueOf(tt.getXOffset());
/*  988 */       this.m_toolTipAttributes.Y_OFFSET = Integer.valueOf(tt.getYOffset());
/*  989 */       this.m_toolTipAttributes.DURATION = Integer.valueOf(tt.getDuration());
/*  990 */       this.m_toolTipAttributes.MAX_WIDTH = Integer.valueOf((int)tt.getMaxWidth());
/*  991 */       if (this.m_toolTipAttributes.TEXT_COLOR != null) {
/*  992 */         this.m_toolTipAttributes.TEXT_COLOR.setValue(tt.getColor());
/*      */       } else {
/*  994 */         this.m_toolTipAttributes.TEXT_COLOR = new Color(tt.getColor());
/*      */       } 
/*  996 */       if (this.m_toolTipAttributes.BACKGROUND_COLOR != null) {
/*  997 */         this.m_toolTipAttributes.BACKGROUND_COLOR.setValue(tt.getBackgroundColor());
/*      */       } else {
/*  999 */         this.m_toolTipAttributes.BACKGROUND_COLOR = new Color(tt.getBackgroundColor());
/*      */       } 
/* 1001 */       if (this.m_toolTipAttributes.BORDER_COLOR != null) {
/* 1002 */         this.m_toolTipAttributes.BORDER_COLOR.setValue(tt.getBorderColor());
/*      */       } else {
/* 1004 */         this.m_toolTipAttributes.BORDER_COLOR = new Color(tt.getBorderColor());
/*      */       } 
/*      */       
/* 1007 */       this.m_toolTipAttributes.FONT = tt.getFont();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void loadToolTipAttributes() {
/* 1012 */     if (this.m_scene != null) {
/* 1013 */       Tooltip tt = this.m_scene.getTooltip();
/* 1014 */       tt.setText(this.m_toolTipAttributes.TEXT);
/* 1015 */       tt.setFont(this.m_toolTipAttributes.FONT);
/* 1016 */       tt.setOffset(this.m_toolTipAttributes.X_OFFSET.intValue(), this.m_toolTipAttributes.Y_OFFSET.intValue());
/* 1017 */       tt.setDuration(this.m_toolTipAttributes.DURATION.intValue());
/* 1018 */       tt.setMaxWidth(this.m_toolTipAttributes.MAX_WIDTH.intValue());
/* 1019 */       setTooltipTextColor(this.m_toolTipAttributes.TEXT_COLOR);
/* 1020 */       setTooltipBackgroundColor(this.m_toolTipAttributes.BACKGROUND_COLOR);
/* 1021 */       setTooltipBorderColor(this.m_toolTipAttributes.BORDER_COLOR);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTooltipFont(Font font) {
/* 1031 */     if (this.m_scene != null) {
/* 1032 */       this.m_scene.getTooltip().setFont(font);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTooltipMaxWidth(int maxWidth) {
/* 1042 */     if (this.m_scene != null) {
/* 1043 */       this.m_scene.getTooltip().setMaxWidth(maxWidth);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTooltipTextColor(Color c) {
/* 1053 */     if (this.m_scene != null) {
/* 1054 */       this.m_scene.getTooltip().setColor((float)c.getRed(), (float)c.getGreen(), (float)c.getBlue(), (float)c.getAlpha());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTooltipBackgroundColor(Color c) {
/* 1064 */     if (this.m_scene != null) {
/* 1065 */       this.m_scene.getTooltip().setBackgroundColor((float)c.getRed(), (float)c.getGreen(), (float)c.getBlue(), (float)c.getAlpha());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTooltipBorderColor(Color c) {
/* 1075 */     if (this.m_scene != null) {
/* 1076 */       this.m_scene.getTooltip().setBorderColor((float)c.getRed(), (float)c.getGreen(), (float)c.getBlue(), (float)c.getAlpha());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTooltipHotPointPosition(BackgroundedText.BackgroundedTextHotPointPosition hotPointPosition) {
/* 1086 */     if (this.m_scene != null) {
/* 1087 */       this.m_scene.getTooltip().setHotPointPosition(hotPointPosition);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showTooltip(String text) {
/* 1097 */     int x = 0;
/* 1098 */     int y = 0;
/* 1099 */     if (this.m_scene != null) {
/* 1100 */       x = this.m_scene.getMouseX();
/* 1101 */       y = (int)this.m_scene.getFrustumHeight() - this.m_scene.getMouseY();
/*      */     } 
/* 1103 */     showTooltip(text, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showTooltip(String text, int x, int y) {
/* 1114 */     showTooltip(text, x, y, 3000);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showTooltip(String text, int x, int y, int duration) {
/* 1125 */     showTooltip(text, x, y, duration, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showTooltip(String text, int x, int y, int duration, int xOffset, int yOffset) {
/* 1136 */     if (this.m_scene != null) {
/* 1137 */       Tooltip tooltip = this.m_scene.getTooltip();
/* 1138 */       x -= (int)(this.m_scene.getFrustumWidth() / 2.0F);
/* 1139 */       y -= (int)(this.m_scene.getFrustumHeight() / 2.0F);
/* 1140 */       tooltip.setPosition(x, y, 0.0F, 0.0F);
/* 1141 */       tooltip.setOffset(xOffset, yOffset);
/* 1142 */       tooltip.setDuration(duration);
/* 1143 */       tooltip.setText(text);
/* 1144 */       tooltip.setVisible(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hideTooltip() {
/* 1152 */     if (this.m_scene != null) {
/* 1153 */       this.m_scene.getTooltip().setVisible(false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private IElement insert(URL url, IElement container) throws Exception {
/* 1166 */     if (container != null) {
/* 1167 */       IElement element = parse(url);
/* 1168 */       container.add(element);
/* 1169 */       return element;
/*      */     } 
/* 1171 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IElement parse(URL url) throws Exception {
/* 1182 */     Reader reader = null;
/*      */     try {
/* 1184 */       InputStream in = url.openStream();
/* 1185 */       if (in == null) {
/* 1186 */         throw new IOException("Impossible de  trouver la ressource : " + url.toString());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1195 */       reader = new InputStreamReader(in);
/* 1196 */       Document document = (new SAXBuilder()).build(reader);
/*      */ 
/*      */ 
/*      */       
/* 1200 */       return this.m_parser.parse(document);
/*      */     } finally {
/*      */       try {
/* 1203 */         reader.close();
/* 1204 */       } catch (Exception ex) {
/* 1205 */         ex.printStackTrace();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addToElementsNeedingLayout(String id) {
/* 1211 */     IElement element = this.m_loadedElements.get(id);
/* 1212 */     synchronized (this.m_loadedElements) {
/* 1213 */       if (element != null && !this.m_elementsNeedingLayout.contains(element)) {
/* 1214 */         this.m_elementsNeedingLayout.add(element);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onProcess(XulorScene scene, long realTime, int frameCount) {
/* 1226 */     boolean needToLayoutScene = false;
/*      */     
/* 1228 */     this.m_environment.getPropertiesProvider().onProcess();
/*      */     
/* 1230 */     synchronized (this.m_loadUnloadDocuments) {
/* 1231 */       int size = this.m_timedUnloadDocuments.size();
/* 1232 */       for (int i = 0; i < size; i++) {
/* 1233 */         XulorUnload unload = this.m_timedUnloadDocuments.get(i);
/* 1234 */         if (realTime - unload.getDuration() - unload.getStartTime() > 0L) {
/* 1235 */           this.m_loadUnloadDocuments.add((XulorLoadUnload)this.m_timedUnloadDocuments.remove(i));
/* 1236 */           i--;
/* 1237 */           size--;
/*      */         } 
/*      */       } 
/* 1240 */       while (!this.m_loadUnloadDocuments.isEmpty()) {
/* 1241 */         XulorLoadUnload loadUnload = this.m_loadUnloadDocuments.remove(0);
/* 1242 */         if (loadUnload instanceof XulorUnload) {
/* 1243 */           XulorUnload unload = (XulorUnload)loadUnload;
/* 1244 */           if (unload.isAll()) {
/* 1245 */             unloadAllId(); continue;
/*      */           } 
/* 1247 */           unloadId(unload.getId()); continue;
/*      */         } 
/* 1249 */         if (loadUnload instanceof XulorLoad) {
/* 1250 */           XulorLoad doc = (XulorLoad)loadUnload;
/* 1251 */           if (doc.getDuration() != Integer.MAX_VALUE) {
/* 1252 */             this.m_timedUnloadDocuments.add(new XulorUnload(doc.getId(), doc.getDuration(), realTime));
/*      */           }
/* 1254 */           loadInto(doc.getId(), doc.getDocumentUrl(), doc.getParent(), doc.getElementMap(), doc.getCurrentDirectory(), doc.getOptions(), doc.getLevel()); continue;
/* 1255 */         }  if (loadUnload instanceof XulorInsert) {
/* 1256 */           XulorInsert doc = (XulorInsert)loadUnload;
/* 1257 */           doc.m_parent.add(doc.m_element);
/* 1258 */           if ((doc.m_options & 0x40L) == 64L) {
/* 1259 */             ModalManager.getInstance().addModalElement(doc.m_element);
/* 1260 */             this.m_loadedModals.add(doc.m_id);
/*      */           } 
/* 1262 */           if ((doc.m_options & 0x80L) == 128L) {
/* 1263 */             ModalManager.getInstance().addPseudoModalElement(doc.m_element);
/* 1264 */             this.m_loadedModals.add(doc.m_id);
/*      */           } 
/* 1266 */           this.m_loadedElements.put(doc.m_id, doc.m_element); continue;
/* 1267 */         }  if (loadUnload instanceof XulorLoadMouseImage) {
/* 1268 */           XulorLoadMouseImage xlmi = (XulorLoadMouseImage)loadUnload;
/* 1269 */           MouseImageManager.getInstance().setURL(xlmi.URL);
/* 1270 */           MouseImageManager.getInstance().setXOffset(xlmi.XOFFSET);
/* 1271 */           MouseImageManager.getInstance().setYOffset(xlmi.YOFFSET);
/* 1272 */           MouseImageManager.getInstance().setHotPoint(xlmi.HOTPOINT);
/* 1273 */           MouseImageManager.getInstance().show(); continue;
/* 1274 */         }  if (loadUnload instanceof XulorUnloadMouseImage) {
/* 1275 */           MouseImageManager.getInstance().hide();
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1282 */     if (this.m_needToRebuildUI) {
/* 1283 */       if (this.m_bottomRootContainer != null) {
/* 1284 */         this.m_bottomRootContainer.buildGUI();
/*      */       }
/* 1286 */       if (this.m_topRootContainer != null) {
/* 1287 */         this.m_topRootContainer.buildGUI();
/*      */       }
/* 1289 */       if (this.m_msgBoxRootContainer != null) {
/* 1290 */         this.m_msgBoxRootContainer.buildGUI();
/*      */       }
/* 1292 */       this.m_needToRebuildUI = false;
/* 1293 */       needToLayoutScene = true;
/*      */     } 
/*      */     
/* 1296 */     if (this.m_scene != null) {
/* 1297 */       if (needToLayoutScene) {
/* 1298 */         this.m_scene.layout();
/* 1299 */         needToLayoutScene = false;
/*      */       } else {
/* 1301 */         for (IElement element : this.m_elementsNeedingLayout) {
/* 1302 */           if (element.getParent() != null) {
/* 1303 */             element.getParent().layout();
/*      */           }
/*      */         } 
/* 1306 */         this.m_elementsNeedingLayout.clear();
/*      */       } 
/*      */     }
/*      */     
/* 1310 */     FocusManager.getInstance().focus();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onXulorSceneInitializationComplete(XulorScene scene) {
/* 1319 */     this.m_scene = scene;
/* 1320 */     setMsgBoxRootContainer(scene.getMsgBoxRootContainer());
/* 1321 */     setBottomRootContainer(scene.getBackRootContainer());
/* 1322 */     setTopRootContainer(scene.getTopRootContainer());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDebugInfos() {
/* 1329 */     StringBuilder builder = new StringBuilder("# XULOR INFOS #");
/*      */ 
/*      */     
/* 1332 */     builder.append('\n').append("- loadedElementCount = ").append(this.m_loadedElements.size());
/* 1333 */     builder.append('\n').append("- loadedElements = ");
/* 1334 */     ArrayList<String> elementIds = new ArrayList<String>();
/* 1335 */     for (String elementId : this.m_loadedElements.keySet()) {
/* 1336 */       elementIds.add(elementId);
/*      */     }
/* 1338 */     builder.append(elementIds.toString());
/*      */     
/* 1340 */     return builder.toString();
/*      */   }
/*      */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\Xulor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */