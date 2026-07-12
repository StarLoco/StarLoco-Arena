/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiScene;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Button;
/*     */ import org.fenggui.DecoratorAppearance;
/*     */ import org.fenggui.Display;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.IBasicContainer;
/*     */ import org.fenggui.ILabel;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.Label;
/*     */ import org.fenggui.ObservableWidget;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.Event;
/*     */ import org.fenggui.event.IEventListener;
/*     */ import org.fenggui.event.IMenuClosedListener;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.util.Dimension;
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
/*     */ public class PopupMenu
/*     */   extends ObservableWidget
/*     */   implements IBasicContainer
/*     */ {
/*     */   private PopupMenuAppearance m_appearance;
/*     */   private ArrayList<StandardWidget> m_widgets;
/*     */   private boolean m_show = false;
/*  43 */   private IEventListener m_currentHideListener = null;
/*  44 */   private ArrayList<IMenuClosedListener> m_mcl = new ArrayList<IMenuClosedListener>();
/*  45 */   private Alignment m_hotSpotPosition = Alignment.NORTH_WEST;
/*     */   private int m_wishedX;
/*     */   private int m_wishedY;
/*     */   
/*     */   public PopupMenu() {
/*  50 */     this.m_appearance = new PopupMenuAppearance((IWidget)this);
/*  51 */     this.m_widgets = new ArrayList<StandardWidget>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLabel(Label label) {
/*  59 */     if (label != null) {
/*  60 */       this.m_widgets.add(label);
/*  61 */       label.setParent(this);
/*  62 */       updateMinSize();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addButton(Button button) {
/*  71 */     if (button != null) {
/*  72 */       this.m_widgets.add(button);
/*  73 */       button.setParent(this);
/*  74 */       updateMinSize();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addWidget(StandardWidget widget) {
/*  79 */     if (widget != null) {
/*  80 */       this.m_widgets.add(widget);
/*  81 */       widget.setParent(this);
/*  82 */       updateMinSize();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Button addButton(String label) {
/*  92 */     Button button = new Button(label);
/*  93 */     this.m_widgets.add(button);
/*  94 */     button.setParent(this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     updateMinSize();
/* 101 */     return button;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<StandardWidget> getWidgets() {
/* 109 */     return this.m_widgets;
/*     */   }
/*     */   
/*     */   public void removeLabel(Label label) {
/* 113 */     if (label != null) {
/* 114 */       this.m_widgets.remove(label);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeButton(Button button) {
/* 123 */     if (button != null) {
/* 124 */       this.m_widgets.remove(button);
/* 125 */       button.removedFromWidgetTree();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeButton(String label) {
/*     */     StandardWidget standardWidget;
/* 134 */     Widget toRemove = null;
/* 135 */     for (StandardWidget widget : this.m_widgets) {
/* 136 */       if (widget instanceof ILabel && (
/* 137 */         (ILabel)widget).getText().equalsIgnoreCase(label)) {
/* 138 */         standardWidget = widget;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 143 */     if (standardWidget != null) {
/* 144 */       this.m_widgets.remove(standardWidget);
/* 145 */       standardWidget.removedFromWidgetTree();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void show(int x, int y) {
/* 150 */     this.m_wishedX = x;
/* 151 */     this.m_wishedY = y;
/*     */     
/* 153 */     this.m_show = true;
/*     */     
/* 155 */     Display display = ((FengguiScene)Xulor.getInstance().getScene()).getDisplay();
/* 156 */     this.m_currentHideListener = new IEventListener() {
/*     */         public void processEvent(Event e) {
/* 158 */           if (e instanceof org.fenggui.event.mouse.MouseReleasedEvent || e instanceof org.fenggui.event.ButtonPressedEvent) {
/* 159 */             PopupMenu.this.hide();
/*     */           }
/*     */         }
/*     */       };
/* 163 */     display.addGlobalEventListener(this.m_currentHideListener);
/*     */   }
/*     */   
/*     */   public void hide() {
/* 167 */     this.m_show = false;
/* 168 */     if (this.m_currentHideListener != null) {
/* 169 */       Display display = ((FengguiScene)Xulor.getInstance().getScene()).getDisplay();
/* 170 */       display.removeGlobalEventListener(this.m_currentHideListener);
/* 171 */       fireMenuClosed();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Alignment getHotSpotPosition() {
/* 179 */     return this.m_hotSpotPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHotSpotPosition(Alignment align) {
/* 186 */     if (align != null) {
/* 187 */       this.m_hotSpotPosition = align;
/*     */     }
/*     */   }
/*     */   
/*     */   private void fireMenuClosed() {
/* 192 */     for (IMenuClosedListener l : this.m_mcl) {
/* 193 */       l.menuClosed(null);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addMenuClosedListener(IMenuClosedListener listener) {
/* 198 */     this.m_mcl.add(listener);
/*     */   }
/*     */   
/*     */   public void removeMenuClosedListener(IMenuClosedListener listener) {
/* 202 */     this.m_mcl.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getWidget(int x, int y) {
/* 210 */     if (!this.m_appearance.insideMargin(x, y))
/*     */     {
/* 212 */       return null;
/*     */     }
/*     */     
/* 215 */     IWidget ret = null;
/* 216 */     IWidget found = null;
/*     */     
/* 218 */     x -= this.m_appearance.getLeftMargins();
/* 219 */     y -= this.m_appearance.getBottomMargins();
/*     */     
/* 221 */     for (IWidget w : this.m_widgets) {
/*     */       
/* 223 */       ret = w.getWidget(x - w.getX(), y - w.getY());
/*     */       
/* 225 */       if (ret != null) found = ret;
/*     */     
/*     */     } 
/*     */     
/* 229 */     if (ret != null) found = ret;
/*     */     
/* 231 */     return found;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PopupMenuAppearance getAppearance() {
/* 240 */     return this.m_appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getNextTraversableWidget(IWidget currentWidget) {
/* 248 */     if (!this.m_widgets.contains(currentWidget)) {
/* 249 */       throw new IllegalArgumentException("currentWidget is not child of this container!");
/*     */     }
/* 251 */     IWidget w = getNextWidget(currentWidget);
/*     */     
/* 253 */     for (; w != null && !w.isTraversable(); w = getNextWidget(w));
/*     */     
/* 255 */     return w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getPreviousTraversableWidget(IWidget currentWidget) {
/* 263 */     if (!this.m_widgets.contains(currentWidget)) {
/* 264 */       throw new IllegalArgumentException("currentWidget is not child of this container!");
/*     */     }
/* 266 */     IWidget w = getPreviousWidget(currentWidget);
/*     */     
/* 268 */     for (; w != null && !w.isTraversable(); w = getPreviousWidget(w));
/*     */     
/* 270 */     return w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getNextWidget(IWidget start) {
/*     */     int offset;
/* 278 */     if (start != null) {
/* 279 */       offset = this.m_widgets.indexOf(start) + 1;
/*     */     } else {
/* 281 */       offset = 0;
/*     */     } 
/*     */     
/* 284 */     if (offset > this.m_widgets.size()) {
/* 285 */       return getParent().getNextWidget(start);
/*     */     }
/*     */     
/* 288 */     return (IWidget)this.m_widgets.get(offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getPreviousWidget(IWidget start) {
/*     */     int offset;
/* 296 */     if (start != null) {
/* 297 */       offset = this.m_widgets.indexOf(start) - 1;
/*     */     } else {
/* 299 */       offset = this.m_widgets.size() - 1;
/*     */     } 
/*     */     
/* 302 */     if (offset < 0) {
/* 303 */       return getParent().getPreviousWidget(start);
/*     */     }
/*     */     
/* 306 */     return (IWidget)this.m_widgets.get(offset);
/*     */   }
/*     */   
/*     */   public void layout() {
/* 310 */     setSizeToMinSize();
/*     */ 
/*     */     
/* 313 */     setX(this.m_wishedX - this.m_hotSpotPosition.getX(getWidth()));
/* 314 */     setY(this.m_wishedY - this.m_hotSpotPosition.getY(getHeight()));
/*     */     
/* 316 */     int y = this.m_appearance.getContentHeight();
/*     */     
/* 318 */     for (StandardWidget widget : this.m_widgets) {
/* 319 */       widget.setWidth(this.m_appearance.getContentWidth());
/* 320 */       widget.setHeight(widget.getAppearance().getMinSizeHint().getHeight());
/* 321 */       y -= widget.getHeight();
/* 322 */       widget.setX(0);
/* 323 */       widget.setY(y);
/* 324 */       widget.layout();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removedFromWidgetTree() {
/* 334 */     for (Widget widget : this.m_widgets) {
/* 335 */       widget.removedFromWidgetTree();
/*     */     }
/* 337 */     super.removedFromWidgetTree();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class PopupMenuAppearance
/*     */     extends DecoratorAppearance
/*     */     implements IAppearance
/*     */   {
/*     */     public PopupMenuAppearance(IWidget w) {
/* 349 */       super(w);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension getContentMinSizeHint() {
/* 357 */       int minWidth = 0, minHeight = 0;
/* 358 */       for (StandardWidget widget : PopupMenu.this.m_widgets) {
/* 359 */         Dimension dim = widget.getAppearance().getMinSizeHint();
/* 360 */         minWidth = Math.max(dim.getWidth(), minWidth);
/* 361 */         minHeight += dim.getHeight();
/*     */       } 
/* 363 */       return new Dimension(minWidth, minHeight);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintContent(Graphics g, IOpenGL gl) {
/* 371 */       if (PopupMenu.this.m_show)
/* 372 */         for (StandardWidget widget : PopupMenu.this.m_widgets) {
/* 373 */           gl.pushMatrix();
/* 374 */           g.translate(widget.getX(), widget.getY());
/*     */           
/* 376 */           widget.paint(g);
/*     */           
/* 378 */           g.translate(-widget.getX(), -widget.getY());
/* 379 */           gl.popMatrix();
/*     */         }  
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\PopupMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */