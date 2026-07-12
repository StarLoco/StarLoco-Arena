/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Window;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XWindowAppearance;
/*     */ import com.ankamagames.xulor.event.IWindowClosedListener;
/*     */ import com.ankamagames.xulor.event.WindowClosedEvent;
/*     */ import com.ankamagames.xulor.event.listener.WindowClosedListener;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IWindow;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeWindowAppearance;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.LayoutManager;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.IWindowClosedListener;
/*     */ import org.fenggui.event.WindowClosedEvent;
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
/*     */   
/*     */   private boolean m_pack;
/*     */   private boolean m_packInit = false;
/*  41 */   private IWindowClosedListener m_windowClosedListener = null;
/*  42 */   private Vector<IWindowClosedListener> m_wcl = new Vector<IWindowClosedListener>();
/*     */   
/*     */   private boolean m_closeButton = false;
/*     */   
/*     */   private boolean m_maxButton = false;
/*     */   private boolean m_minButton = false;
/*     */   private boolean m_resizable = true;
/*     */   private boolean m_movable = true;
/*     */   private boolean m_displayTitleBar = true;
/*     */   private IWindow THIS;
/*     */   
/*     */   public XWindow() {
/*  54 */     this.THIS = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String title) {
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
/*     */   
/*     */   public void buildGUI() {
/*  75 */     if (this.m_window == null) {
/*  76 */       this.m_window = new Window(this.m_closeButton, this.m_maxButton, this.m_minButton, this.m_resizable, this.m_movable, this.m_displayTitleBar);
/*     */       
/*  78 */       applyAllAttributes();
/*     */       
/*  80 */       this.m_windowClosedListener = new IWindowClosedListener() {
/*     */           public void windowClosed(WindowClosedEvent windowClosedEvent) {
/*  82 */             WindowClosedEvent event = new WindowClosedEvent(XWindow.this.THIS);
/*  83 */             for (IWindowClosedListener l : XWindow.this.m_wcl)
/*  84 */               ((WindowClosedListener)l).run(event); 
/*     */           }
/*     */         };
/*  87 */       this.m_window.addWindowClosedListener(this.m_windowClosedListener);
/*     */       
/*  89 */       addContainerListeners();
/*     */       
/*  91 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this);
/*     */       
/*  93 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_window, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  96 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  97 */       c.buildGUI();
/*     */       b++; }
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
/*     */   
/*     */   public void applyAllAttributes() {
/* 114 */     if (this.m_window == null) {
/*     */       return;
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
/*     */   
/*     */   public void addWidget(IElement w) {
/* 147 */     Widget widget = (Widget)w.getEncapsulatedObject();
/* 148 */     if (widget == null || this.m_window == null) {
/*     */       return;
/*     */     }
/*     */     
/* 152 */     this.m_window.addWidgetToContent((IWidget)widget);
/*     */     
/* 154 */     if (w instanceof IComponent && widget.isInWidgetTree()) {
/* 155 */       ((IComponent)w).setAddedToWidgetTree(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLayoutManager(LayoutManager lm) {
/* 165 */     this.m_window.getContentContainer().setLayoutManager(lm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/* 174 */     System.out.println("<window title=\"" + this.m_title + "\">"); byte b; int i; IElement[] arrayOfIElement;
/* 175 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/* 176 */       c.buildXML(); b++; }
/*     */     
/* 178 */     System.out.println("</window>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnClose(IWindowClosedListener l) {
/* 188 */     this.m_wcl.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushToTop() {
/* 196 */     if (this.m_window != null) {
/* 197 */       this.m_window.putSelfOnTop();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCloseButton() {
/* 205 */     return this.m_closeButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCloseButton(boolean closeButton) {
/* 212 */     this.m_closeButton = closeButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMaxButton() {
/* 219 */     return this.m_maxButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxButton(boolean maxButton) {
/* 226 */     this.m_maxButton = maxButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMinButton() {
/* 233 */     return this.m_minButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinButton(boolean minButton) {
/* 240 */     this.m_minButton = minButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMovable() {
/* 247 */     return this.m_movable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMovable(boolean movable) {
/* 254 */     this.m_movable = movable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isResizable() {
/* 261 */     return this.m_resizable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResizable(boolean resizable) {
/* 268 */     this.m_resizable = resizable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisplayTitleBar() {
/* 275 */     return this.m_displayTitleBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayTitleBar(boolean displayTitleBar) {
/* 282 */     this.m_displayTitleBar = displayTitleBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPack() {
/* 289 */     return this.m_pack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPack(boolean pack) {
/* 296 */     this.m_packInit = true;
/* 297 */     this.m_pack = pack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 305 */     return (Widget)this.m_window;
/*     */   }
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 309 */     XWindow window = (XWindow)element;
/* 310 */     window.m_title = this.m_title;
/* 311 */     if (this.m_packInit) window.setPack(this.m_pack); 
/* 312 */     window.setDisplayTitleBar(this.m_displayTitleBar);
/* 313 */     for (IWindowClosedListener listener : this.m_wcl) window.setOnClose(listener); 
/* 314 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 321 */     XWindow window = new XWindow();
/* 322 */     copyElementData(window);
/* 323 */     return (IElement)window;
/*     */   }
/*     */   
/*     */   public String getTag() {
/* 327 */     return "Window";
/*     */   }
/*     */   
/*     */   public static void applyWindowTheme(Window window, ThemeElement element) {
/* 331 */     if (window == null || element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 335 */     window.getAppearance().removeAll();
/* 336 */     XComponent.applyThemeAttributes((Widget)window, element.getAttributes());
/* 337 */     XSpacingAppearance.setAppearance((StandardWidget)window, element);
/* 338 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 339 */     for (ThemeAppearance app : appearances) {
/* 340 */       if (app != null) {
/* 341 */         XDecoratorAppearance.setAppearance((StandardWidget)window, app);
/* 342 */         if (app instanceof ThemeWindowAppearance)
/* 343 */           XWindowAppearance.setAppearance(window, (ThemeWindowAppearance)app); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XWindow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */