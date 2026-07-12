/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.event.SelectionChangedEvent;
/*     */ import com.ankamagames.xulor.event.listener.SelectionChangedListener;
/*     */ import com.ankamagames.xulor.util.Item;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Button;
/*     */ import org.fenggui.DecoratorAppearance;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.IBasicContainer;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.ObservableWidget;
/*     */ import org.fenggui.ScrollBar;
/*     */ import org.fenggui.event.Event;
/*     */ import org.fenggui.event.IEventListener;
/*     */ import org.fenggui.event.mouse.IMousePressedListener;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.util.Dimension;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComboBox
/*     */   extends ObservableWidget
/*     */   implements IBasicContainer
/*     */ {
/*     */   private ComboBoxAppearance m_appearance;
/*     */   private Button m_button;
/*     */   private RenderableContainer m_renderable;
/*     */   private List m_list;
/*     */   private boolean m_listIsBeingDisplayed = false;
/*  34 */   private Item[] m_items = null;
/*  35 */   private final ArrayList<SelectionChangedListener> m_selectionChangedListeners = new ArrayList<SelectionChangedListener>();
/*  36 */   private IEventListener m_el = null;
/*  37 */   private int m_maxRows = -1;
/*     */   
/*  39 */   private MousePressedEvent m_originalEvent = null;
/*     */   
/*     */   public ComboBox() {
/*  42 */     this.m_appearance = new ComboBoxAppearance((IWidget)this);
/*  43 */     this.m_button = new Button();
/*     */     
/*  45 */     this.m_button.setParent(this);
/*     */     
/*  47 */     updateMinSize();
/*  48 */     createListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addedToWidgetTree() {
/*  56 */     getDisplay().addGlobalEventListener(this.m_el);
/*  57 */     if (this.m_list != null) {
/*  58 */       this.m_list.setParent((IBasicContainer)getDisplay());
/*  59 */       this.m_list.addedToWidgetTree();
/*     */     } 
/*     */     
/*  62 */     if (this.m_button != null) {
/*  63 */       this.m_button.addedToWidgetTree();
/*     */     }
/*     */     
/*  66 */     if (this.m_renderable != null) {
/*  67 */       this.m_renderable.addedToWidgetTree();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removedFromWidgetTree() {
/*  76 */     getDisplay().removeGlobalEventListener(this.m_el);
/*  77 */     if (this.m_list != null) {
/*  78 */       if (this.m_list.getParent() != null) {
/*  79 */         getDisplay().removeWidget((IWidget)this.m_list);
/*     */       }
/*  81 */       this.m_list.removedFromWidgetTree();
/*     */     } 
/*     */     
/*  84 */     if (this.m_button != null) {
/*  85 */       this.m_button.removedFromWidgetTree();
/*     */     }
/*     */     
/*  88 */     if (this.m_renderable != null) {
/*  89 */       this.m_renderable.removedFromWidgetTree();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createListeners() {
/*  98 */     this.m_button.addMousePressedListener(new IMousePressedListener() {
/*     */           public void mousePressed(MousePressedEvent e) {
/* 100 */             ComboBox.this.m_originalEvent = e;
/* 101 */             ComboBox.this.switchPopup();
/*     */           }
/*     */         });
/*     */     
/* 105 */     this.m_el = new IEventListener() {
/*     */         public void processEvent(Event event) {
/* 107 */           if (event instanceof MousePressedEvent && ComboBox.this.m_listIsBeingDisplayed && event != ComboBox.this.m_originalEvent) {
/* 108 */             MousePressedEvent mpe = (MousePressedEvent)event;
/* 109 */             ScrollBar sb = ComboBox.this.m_list.getScrollBar();
/* 110 */             if (!sb.getAppearance().insideMargin(mpe.getLocalX((IWidget)sb), mpe.getLocalY((IWidget)sb)))
/*     */             {
/* 112 */               ComboBox.this.switchPopup();
/*     */             }
/*     */           } 
/* 115 */           ComboBox.this.m_originalEvent = null;
/*     */         }
/*     */       };
/*     */     
/* 119 */     setupListListeners();
/* 120 */     setupRenderableListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupListListeners() {
/* 129 */     if (this.m_list != null) {
/* 130 */       this.m_list.addListSelectionChangedListener(new IListSelectionChangedListener() {
/*     */             public void selectionChanged(ListSelectionChangedEvent event) {
/* 132 */               boolean selected = event.getSelected();
/* 133 */               Item item = event.getValue();
/* 134 */               SelectionChangedEvent sce = new SelectionChangedEvent(Xulor.getInstance().getEnvironment().getElementByWidget(this), null, selected, item);
/* 135 */               for (SelectionChangedListener scl : ComboBox.this.m_selectionChangedListeners) {
/* 136 */                 scl.run(sce);
/*     */               }
/* 138 */               if (selected) {
/* 139 */                 ComboBox.this.m_renderable.setItem(item);
/*     */               }
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   private void setupRenderableListeners() {
/* 147 */     if (this.m_renderable != null) {
/* 148 */       this.m_renderable.addMousePressedListener(new IMousePressedListener() {
/*     */             public void mousePressed(MousePressedEvent e) {
/* 150 */               ComboBox.this.m_originalEvent = e;
/* 151 */               ComboBox.this.switchPopup();
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSelectionChangedListener(SelectionChangedListener listener) {
/* 162 */     this.m_selectionChangedListeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSelectionChangedListener(IListSelectionChangedListener listener) {
/* 170 */     this.m_selectionChangedListeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComboBoxAppearance getAppearance() {
/* 178 */     return this.m_appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAppearance(ComboBoxAppearance appearance) {
/* 186 */     this.m_appearance = appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Button getButton() {
/* 194 */     return this.m_button;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setButton(Button button) {
/* 202 */     this.m_button = button;
/* 203 */     this.m_button.setParent(this);
/* 204 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getList() {
/* 212 */     return this.m_list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setList(List list) {
/* 220 */     this.m_list = list;
/* 221 */     if (this.m_list != null) {
/* 222 */       if (isInWidgetTree()) {
/* 223 */         this.m_list.setParent((IBasicContainer)getDisplay());
/*     */       }
/* 225 */       this.m_list.setParent(this);
/* 226 */       setupListListeners();
/* 227 */       this.m_list.setItems(this.m_items);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getSelectedItem() {
/* 237 */     if (this.m_list != null) {
/* 238 */       return this.m_list.getSelectedItem();
/*     */     }
/* 240 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedItem(Item selectedItem) {
/* 248 */     if (this.m_list != null) {
/* 249 */       this.m_list.setSelectedValue((selectedItem != null) ? selectedItem.getValue() : null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getSelectedValue() {
/* 258 */     if (this.m_list != null && this.m_list.getSelectedItem() != null) {
/* 259 */       return this.m_list.getSelectedItem().getValue();
/*     */     }
/* 261 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedValue(Object value) {
/* 269 */     if (this.m_list != null) {
/* 270 */       this.m_list.setSelectedValue(value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderableContainer getRenderable() {
/* 279 */     return this.m_renderable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderable(RenderableContainer renderable) {
/* 287 */     this.m_renderable = renderable;
/* 288 */     if (this.m_renderable != null) {
/* 289 */       this.m_renderable.setParent(this);
/* 290 */       setupRenderableListeners();
/*     */     } 
/*     */     
/* 293 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItems(Item[] items) {
/* 301 */     if (this.m_list != null) {
/* 302 */       this.m_list.setItems(items);
/* 303 */       if (items != null && items.length > 0 && this.m_list.getSelectedItem() == null) {
/* 304 */         this.m_list.setSelectedValue(items[0].getValue());
/*     */       }
/*     */     } 
/* 307 */     if (this.m_renderable != null) {
/* 308 */       if (this.m_list != null && this.m_list.getSelectedItem() != null) {
/* 309 */         this.m_renderable.setItemValue(this.m_list.getSelectedItem().getValue());
/* 310 */       } else if (items != null && items.length > 0) {
/* 311 */         this.m_renderable.setItemValue(items[0].getValue());
/*     */       } else {
/* 313 */         this.m_renderable.setItemValue((Object)null);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     this.m_items = items;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxRows() {
/* 330 */     return this.m_maxRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxRows(int maxRows) {
/* 338 */     this.m_maxRows = maxRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getNextTraversableWidget(IWidget start) {
/* 347 */     return getParent().getNextTraversableWidget((IWidget)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getPreviousTraversableWidget(IWidget start) {
/* 356 */     return getParent().getPreviousTraversableWidget((IWidget)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getNextWidget(IWidget start) {
/* 365 */     return getParent().getNextWidget((IWidget)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getPreviousWidget(IWidget start) {
/* 374 */     return getParent().getPreviousWidget((IWidget)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getWidget(int x, int y) {
/*     */     ComboBox comboBox;
/* 383 */     if (!getAppearance().insideMargin(x, y)) {
/* 384 */       return null;
/*     */     }
/*     */     
/* 387 */     IWidget ret = null;
/*     */     
/* 389 */     x -= getAppearance().getLeftMargins();
/* 390 */     y -= getAppearance().getBottomMargins();
/*     */     
/* 392 */     ret = this.m_renderable.getWidget(x - this.m_renderable.getX(), y - this.m_renderable.getY());
/*     */     
/* 394 */     if (ret == null) {
/* 395 */       ret = this.m_button.getWidget(x - this.m_button.getX(), y - this.m_button.getY());
/*     */     }
/* 397 */     if (ret == null) {
/* 398 */       comboBox = this;
/*     */     }
/*     */     
/* 401 */     return (IWidget)comboBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout() {
/* 409 */     int availableWidth = this.m_appearance.getContentWidth();
/* 410 */     int availableHeight = this.m_appearance.getContentHeight();
/*     */ 
/*     */     
/* 413 */     this.m_button.setHeight(availableHeight);
/* 414 */     this.m_button.setWidth(this.m_button.getAppearance().getMinSizeHint().getWidth());
/* 415 */     this.m_button.setX(availableWidth - this.m_button.getWidth());
/* 416 */     this.m_button.setY(0);
/*     */     
/* 418 */     this.m_renderable.setHeight(availableHeight);
/* 419 */     this.m_renderable.setWidth(availableWidth - this.m_button.getAppearance().getMinSizeHint().getWidth());
/* 420 */     this.m_renderable.setX(0);
/* 421 */     this.m_renderable.setY(0);
/*     */     
/* 423 */     this.m_button.layout();
/* 424 */     this.m_renderable.layout();
/*     */   }
/*     */   
/*     */   private void switchPopup() {
/* 428 */     if (this.m_listIsBeingDisplayed) {
/* 429 */       closePopup();
/*     */     } else {
/* 431 */       openPopup();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void closePopup() {
/* 436 */     getDisplay().getContent().remove(this.m_list);
/* 437 */     this.m_listIsBeingDisplayed = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void openPopup() {
/* 445 */     this.m_list.updateMinSize();
/*     */     
/* 447 */     Dimension idealSize = this.m_list.getIdealSize(this.m_maxRows, -1);
/* 448 */     this.m_list.setWidth(Math.max(this.m_list.getMinWidth(), getWidth()));
/* 449 */     this.m_list.setHeight(idealSize.getHeight());
/*     */     
/* 451 */     int displayY = getDisplayY();
/* 452 */     int displayX = getDisplayX();
/* 453 */     int x = displayX, y = displayY;
/*     */ 
/*     */     
/* 456 */     if (displayY - idealSize.getHeight() >= 0) {
/* 457 */       y = displayY - this.m_list.getHeight();
/* 458 */     } else if (displayY + getHeight() + idealSize.getHeight() <= getDisplay().getHeight()) {
/* 459 */       y = displayY + getHeight() + idealSize.getHeight();
/*     */     } else {
/* 461 */       this.m_list.setHeight(displayY);
/*     */     } 
/*     */     
/* 464 */     this.m_list.setWidth(Math.max(this.m_list.getWidth(), getWidth()));
/*     */     
/* 466 */     this.m_list.layout();
/*     */     
/* 468 */     this.m_list.setX(displayX);
/* 469 */     this.m_list.setY(y);
/*     */ 
/*     */     
/* 472 */     getDisplay().getContent().add(this.m_list);
/*     */     
/* 474 */     this.m_listIsBeingDisplayed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MousePressedEvent mousePressedEvent) {
/* 482 */     if (this.m_list == null || !this.m_list.isInWidgetTree()) {
/* 483 */       switchPopup();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class ComboBoxAppearance
/*     */     extends DecoratorAppearance
/*     */   {
/*     */     public ComboBoxAppearance(IWidget w) {
/* 495 */       super(w);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension getContentMinSizeHint() {
/* 503 */       int width = 0, height = 0;
/* 504 */       width = Math.max(ComboBox.this.m_button.getMinWidth(), (ComboBox.this.m_renderable != null) ? ComboBox.this.m_renderable.getMinWidth() : 10);
/* 505 */       height = Math.max(ComboBox.this.m_button.getMinHeight(), (ComboBox.this.m_renderable != null) ? ComboBox.this.m_renderable.getMinHeight() : 10);
/* 506 */       return new Dimension(width, height);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintContent(Graphics g, IOpenGL gl) {
/* 514 */       g.translate(ComboBox.this.m_button.getX(), ComboBox.this.m_button.getY());
/* 515 */       ComboBox.this.m_button.paint(g);
/* 516 */       g.translate(-ComboBox.this.m_button.getX(), -ComboBox.this.m_button.getY());
/*     */       
/* 518 */       g.translate(ComboBox.this.m_renderable.getX(), ComboBox.this.m_renderable.getY());
/* 519 */       ComboBox.this.m_renderable.paint(g);
/* 520 */       g.translate(-ComboBox.this.m_renderable.getX(), -ComboBox.this.m_renderable.getY());
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\ComboBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */