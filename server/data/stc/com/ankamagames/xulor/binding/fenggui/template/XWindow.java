/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Window;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XWindowAppearance;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.event.listener.WindowClosedListener;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IWindow;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeWindowAppearance;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import org.fenggui.Container;
/*     */ import org.fenggui.Container.ContainerAppearance;
/*     */ import org.fenggui.LayoutManager;
/*     */ import org.fenggui.Widget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XWindow
/*     */   extends XContainer
/*     */   implements IWindow
/*     */ {
/*     */   public static final String TAG = "Window";
/*  35 */   private Window m_window = null;
/*     */   
/*  37 */   private String m_title = null;
/*     */   private boolean m_pack;
/*  39 */   private boolean m_packInit = false;
/*     */   
/*  41 */   private org.fenggui.event.IWindowClosedListener m_windowClosedListener = null;
/*  42 */   private Vector<com.ankamagames.xulor.event.IWindowClosedListener> m_wcl = new Vector();
/*     */   
/*  44 */   private boolean m_closeButton = false;
/*  45 */   private boolean m_maxButton = false;
/*  46 */   private boolean m_minButton = false;
/*  47 */   private boolean m_resizable = true;
/*  48 */   private boolean m_movable = true;
/*  49 */   private boolean m_displayTitleBar = true;
/*     */   private IWindow THIS;
/*     */   
/*     */   public XWindow()
/*     */   {
/*  54 */     this.THIS = this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/*  62 */     this.m_title = title;
/*  63 */     if (this.m_window != null) {
/*  64 */       this.m_window.setTitle(this.m_title);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  75 */     if (this.m_window == null) {
/*  76 */       this.m_window = new Window(this.m_closeButton, this.m_maxButton, this.m_minButton, this.m_resizable, this.m_movable, this.m_displayTitleBar);
/*     */       
/*  78 */       applyAllAttributes();
/*     */       
/*  80 */       this.m_windowClosedListener = new org.fenggui.event.IWindowClosedListener() {
/*     */         public void windowClosed(org.fenggui.event.WindowClosedEvent windowClosedEvent) {
/*  82 */           com.ankamagames.xulor.event.WindowClosedEvent event = new com.ankamagames.xulor.event.WindowClosedEvent(XWindow.this.THIS);
/*  83 */           for (com.ankamagames.xulor.event.IWindowClosedListener l : XWindow.this.m_wcl)
/*  84 */             ((WindowClosedListener)l).run(event);
/*     */         }
/*  86 */       };
/*  87 */       this.m_window.addWindowClosedListener(this.m_windowClosedListener);
/*     */       
/*  89 */       addContainerListeners();
/*     */       
/*  91 */       if (this.m_parent != null) { this.m_parent.addWidget(this);
/*     */       }
/*  93 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_window, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  96 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  97 */       c.buildGUI();
/*     */     }
/*     */     
/* 100 */     applyTheme();
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
/*     */   public void applyAllAttributes()
/*     */   {
/* 114 */     if (this.m_window == null) {
/* 115 */       return;
/*     */     }
/* 117 */     if (this.m_title != null) {
/* 118 */       this.m_window.setTitle(this.m_title);
/*     */     }
/* 120 */     this.m_window.setId(this.m_id);
/*     */     
/* 122 */     if (this.m_wishedSize != null) this.m_window.setWishedSize(this.m_wishedSize);
/* 123 */     if (this.m_packInit) this.m_window.setPack(this.m_pack);
/* 124 */     super.applyAllAttributes();
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/* 128 */     if (this.m_themeNeedToBeApplied) {
/* 129 */       this.m_themeNeedToBeApplied = false;
/* 130 */       applyWindowTheme(this.m_window, this.m_themeElement);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeSelfFromParent() {
/* 135 */     if (this.m_window != null) {
/* 136 */       this.m_window.removeWindowClosedListener(this.m_windowClosedListener);
/*     */     }
/* 138 */     super.removeSelfFromParent();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addWidget(IElement w)
/*     */   {
/* 147 */     Widget widget = (Widget)w.getEncapsulatedObject();
/* 148 */     if ((widget == null) || (this.m_window == null)) {
/* 149 */       return;
/*     */     }
/*     */     
/* 152 */     this.m_window.addWidgetToContent(widget);
/*     */     
/* 154 */     if (((w instanceof IComponent)) && (widget.isInWidgetTree())) {
/* 155 */       ((IComponent)w).setAddedToWidgetTree(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLayoutManager(LayoutManager lm)
/*     */   {
/* 165 */     this.m_window.getContentContainer().setLayoutManager(lm);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/* 174 */     System.out.println("<window title=\"" + this.m_title + "\">");
/* 175 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/* 176 */       c.buildXML();
/*     */     }
/* 178 */     System.out.println("</window>");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnClose(com.ankamagames.xulor.event.IWindowClosedListener l)
/*     */   {
/* 188 */     this.m_wcl.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void pushToTop()
/*     */   {
/* 196 */     if (this.m_window != null) {
/* 197 */       this.m_window.putSelfOnTop();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isCloseButton()
/*     */   {
/* 205 */     return this.m_closeButton;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setCloseButton(boolean closeButton)
/*     */   {
/* 212 */     this.m_closeButton = closeButton;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isMaxButton()
/*     */   {
/* 219 */     return this.m_maxButton;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMaxButton(boolean maxButton)
/*     */   {
/* 226 */     this.m_maxButton = maxButton;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isMinButton()
/*     */   {
/* 233 */     return this.m_minButton;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMinButton(boolean minButton)
/*     */   {
/* 240 */     this.m_minButton = minButton;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isMovable()
/*     */   {
/* 247 */     return this.m_movable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMovable(boolean movable)
/*     */   {
/* 254 */     this.m_movable = movable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isResizable()
/*     */   {
/* 261 */     return this.m_resizable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setResizable(boolean resizable)
/*     */   {
/* 268 */     this.m_resizable = resizable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isDisplayTitleBar()
/*     */   {
/* 275 */     return this.m_displayTitleBar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDisplayTitleBar(boolean displayTitleBar)
/*     */   {
/* 282 */     this.m_displayTitleBar = displayTitleBar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isPack()
/*     */   {
/* 289 */     return this.m_pack;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPack(boolean pack)
/*     */   {
/* 296 */     this.m_packInit = true;
/* 297 */     this.m_pack = pack;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Widget getWidget()
/*     */   {
/* 305 */     return this.m_window;
/*     */   }
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 309 */     XWindow window = (XWindow)element;
/* 310 */     window.m_title = this.m_title;
/* 311 */     if (this.m_packInit) window.setPack(this.m_pack);
/* 312 */     window.setDisplayTitleBar(this.m_displayTitleBar);
/* 313 */     com.ankamagames.xulor.event.IWindowClosedListener listener; for (Iterator localIterator = this.m_wcl.iterator(); localIterator.hasNext(); window.setOnClose(listener)) listener = (com.ankamagames.xulor.event.IWindowClosedListener)localIterator.next();
/* 314 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 321 */     XWindow window = new XWindow();
/* 322 */     copyElementData(window);
/* 323 */     return window;
/*     */   }
/*     */   
/*     */   public String getTag() {
/* 327 */     return "Window";
/*     */   }
/*     */   
/*     */   public static void applyWindowTheme(Window window, ThemeElement element) {
/* 331 */     if ((window == null) || (element == null)) {
/* 332 */       return;
/*     */     }
/*     */     
/* 335 */     window.getAppearance().removeAll();
/* 336 */     XComponent.applyThemeAttributes(window, element.getAttributes());
/* 337 */     XSpacingAppearance.setAppearance(window, element);
/* 338 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 339 */     for (ThemeAppearance app : appearances) {
/* 340 */       if (app != null) {
/* 341 */         XDecoratorAppearance.setAppearance(window, app);
/* 342 */         if ((app instanceof ThemeWindowAppearance)) {
/* 343 */           XWindowAppearance.setAppearance(window, (ThemeWindowAppearance)app);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XWindow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */