/*      */ package com.ankamagames.xulor.binding.fenggui.component;
/*      */ 
/*      */ import com.ankamagames.xulor.Xulor;
/*      */ import com.ankamagames.xulor.binding.fenggui.FengguiScene;
/*      */ import com.ankamagames.xulor.binding.fenggui.template.XRenderableContainer;
/*      */ import com.ankamagames.xulor.event.listener.ItemOutListener;
/*      */ import com.ankamagames.xulor.event.listener.ItemOverListener;
/*      */ import com.ankamagames.xulor.template.IItemRenderable;
/*      */ import com.ankamagames.xulor.util.Alignment;
/*      */ import com.ankamagames.xulor.util.Color;
/*      */ import com.ankamagames.xulor.util.Item;
/*      */ import com.ankamagames.xulor.util.Percentage;
/*      */ import java.util.ArrayList;
/*      */ import org.fenggui.DecoratorAppearance;
/*      */ import org.fenggui.Display;
/*      */ import org.fenggui.IAppearance;
/*      */ import org.fenggui.IWidget;
/*      */ import org.fenggui.ObservableWidget;
/*      */ import org.fenggui.ScrollBar;
/*      */ import org.fenggui.ScrollBar.ScrollBarAppearance;
/*      */ import org.fenggui.ScrollBar.ScrollBarBehaviour;
/*      */ import org.fenggui.Slider;
/*      */ import org.fenggui.event.Event;
/*      */ import org.fenggui.event.IEventListener;
/*      */ import org.fenggui.event.ISliderMovedListener;
/*      */ import org.fenggui.event.SliderMovedEvent;
/*      */ import org.fenggui.event.mouse.IMouseEnteredListener;
/*      */ import org.fenggui.event.mouse.MouseEnteredEvent;
/*      */ import org.fenggui.event.mouse.MouseExitedEvent;
/*      */ import org.fenggui.event.mouse.MousePressedEvent;
/*      */ import org.fenggui.event.mouse.MouseWheelEvent;
/*      */ import org.fenggui.render.Graphics;
/*      */ import org.fenggui.render.IOpenGL;
/*      */ 
/*      */ public class List extends ObservableWidget implements org.fenggui.IBasicContainer, RenderableCollection
/*      */ {
/*   37 */   private ScrollBar m_scrollBar = null;
/*      */   
/*      */   private final List THIS;
/*      */   
/*   41 */   private com.ankamagames.xulor.util.Dimension m_wishedMinSize = null;
/*      */   
/*      */ 
/*   44 */   private int m_currentColumnCount = -1;
/*      */   
/*   46 */   private int m_currentRowCount = -1;
/*      */   
/*   48 */   private com.ankamagames.xulor.util.Dimension m_cellSize = new com.ankamagames.xulor.util.Dimension();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   54 */   private int m_offset = 0;
/*      */   
/*   56 */   private int m_minDisplayedCells = 1;
/*      */   
/*   58 */   private boolean m_beingLayouted = false;
/*      */   
/*      */   private boolean m_horizontal;
/*      */   
/*   62 */   private boolean m_oppositeScrollBarPosition = false;
/*      */   
/*   64 */   private int m_showOneMore = 0;
/*      */   
/*   66 */   private boolean m_displayScrollbar = true;
/*      */   
/*   68 */   private boolean m_autoIdealSize = false;
/*      */   
/*   70 */   private ScrollBar.ScrollBarBehaviour m_scrollBarBehaviour = ScrollBar.ScrollBarBehaviour.WHEN_NEEDED;
/*      */   
/*      */   private final ListAppearance m_appearance;
/*      */   
/*      */   private ArrayList<RenderableContainer> m_renderables;
/*      */   
/*   76 */   private IEventListener m_globalListener = null;
/*      */   
/*   78 */   private RenderableContainer m_mouseOverRenderable = null;
/*      */   
/*   80 */   private Color m_mouseOverColor = null;
/*      */   
/*   82 */   private RenderableContainer m_selectedRenderable = null;
/*      */   
/*   84 */   private Item m_selectedValue = null;
/*      */   
/*   86 */   private int m_selectedOffset = -1;
/*      */   
/*   88 */   private ArrayList<ItemRenderer> m_renderers = null;
/*      */   
/*   90 */   private ItemRendererManager m_rendererManager = null;
/*      */   
/*   92 */   private ArrayList<Item> m_items = null;
/*      */   
/*      */   private Alignment m_alignment;
/*      */   
/*   96 */   private final ArrayList<IListSelectionChangedListener> m_selectionChangedListeners = new ArrayList();
/*      */   
/*   98 */   private final ArrayList<ItemOutListener> m_itemOutListeners = new ArrayList();
/*      */   
/*  100 */   private final ArrayList<ItemOverListener> m_itemOverListeners = new ArrayList();
/*      */   
/*      */   public List() {
/*  103 */     this(false);
/*      */   }
/*      */   
/*      */   public List(boolean horizontal) {
/*  107 */     this.m_appearance = new ListAppearance(this);
/*      */     
/*  109 */     this.m_horizontal = horizontal;
/*      */     
/*  111 */     this.m_scrollBar = new ScrollBar(this.m_horizontal);
/*  112 */     this.m_scrollBar.setParent(this);
/*      */     
/*  114 */     if (!this.m_horizontal) {
/*  115 */       this.m_scrollBar.getSlider().setValue(1.0D);
/*      */       
/*  117 */       this.m_alignment = Alignment.NORTH;
/*      */     } else {
/*  119 */       this.m_alignment = Alignment.WEST;
/*      */     }
/*      */     
/*  122 */     this.m_scrollBar.getSlider().addSliderMovedListener(
/*  123 */       new ISliderMovedListener()
/*      */       {
/*      */         public void sliderMoved(SliderMovedEvent sliderMovedEvent) {
/*  126 */           List.this.setListOffset(List.this.sliderValueToOffset(sliderMovedEvent.getPosition()));
/*      */         }
/*  128 */       });
/*  129 */     this.m_renderables = new ArrayList();
/*      */     
/*  131 */     this.THIS = this;
/*      */   }
/*      */   
/*      */ 
/*      */   public void addListSelectionChangedListener(IListSelectionChangedListener listener)
/*      */   {
/*  137 */     this.m_selectionChangedListeners.add(listener);
/*      */   }
/*      */   
/*      */   public void removeListSelectionChangedListener(IListSelectionChangedListener listener)
/*      */   {
/*  142 */     this.m_selectionChangedListeners.remove(listener);
/*      */   }
/*      */   
/*      */   public void addItemOverListener(ItemOverListener listener) {
/*  146 */     this.m_itemOverListeners.add(listener);
/*  147 */     for (RenderableContainer renderable : this.m_renderables) {
/*  148 */       renderable.addItemOverListener(listener);
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeItemOverListener(ItemOverListener listener) {
/*  153 */     this.m_itemOverListeners.remove(listener);
/*  154 */     for (RenderableContainer renderable : this.m_renderables) {
/*  155 */       renderable.removeItemOverListener(listener);
/*      */     }
/*      */   }
/*      */   
/*      */   public void addItemOutListener(ItemOutListener listener) {
/*  160 */     this.m_itemOutListeners.add(listener);
/*  161 */     for (RenderableContainer renderable : this.m_renderables) {
/*  162 */       renderable.addItemOutListener(listener);
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeItemOutListener(ItemOutListener listener) {
/*  167 */     this.m_itemOutListeners.remove(listener);
/*  168 */     for (RenderableContainer renderable : this.m_renderables) {
/*  169 */       renderable.removeItemOutListener(listener);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addedToWidgetTree()
/*      */   {
/*  180 */     this.m_scrollBar.addedToWidgetTree();
/*  181 */     super.addedToWidgetTree();
/*      */     
/*  183 */     if (getDisplay() != null) {
/*  184 */       this.m_globalListener = new IEventListener() {
/*      */         public void processEvent(Event event) {
/*  186 */           if ((event instanceof MouseWheelEvent)) {
/*  187 */             MouseWheelEvent mouseWheelEvent = (MouseWheelEvent)event;
/*  188 */             if (List.this.m_appearance.insideMargin(
/*  189 */               mouseWheelEvent.getLocalX(List.this.THIS), 
/*  190 */               mouseWheelEvent.getLocalY(List.this.THIS))) {
/*  191 */               List.this.mouseWheel((MouseWheelEvent)event);
/*      */             }
/*      */           }
/*      */         }
/*  195 */       };
/*  196 */       getDisplay().addGlobalEventListener(this.m_globalListener);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removedFromWidgetTree()
/*      */   {
/*  207 */     for (RenderableContainer renderable : this.m_renderables) {
/*  208 */       renderable.removedFromWidgetTree();
/*      */     }
/*  210 */     this.m_scrollBar.removedFromWidgetTree();
/*  211 */     this.m_renderables.clear();
/*  212 */     this.m_selectedRenderable = null;
/*  213 */     this.m_items = null;
/*  214 */     this.m_rendererManager = null;
/*  215 */     this.m_renderers = null;
/*      */     
/*  217 */     this.m_selectionChangedListeners.clear();
/*  218 */     this.m_itemOutListeners.clear();
/*  219 */     this.m_itemOverListeners.clear();
/*  220 */     if (isInWidgetTree()) {
/*  221 */       getDisplay().removeGlobalEventListener(this.m_globalListener);
/*      */     }
/*      */     
/*  224 */     super.removedFromWidgetTree();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Display getDisplay()
/*      */   {
/*  234 */     Display display = super.getDisplay();
/*  235 */     if (display == null) {
/*  236 */       FengguiScene scene = (FengguiScene)Xulor.getInstance().getScene();
/*  237 */       if (scene != null) {
/*  238 */         display = scene.getDisplay();
/*      */       }
/*      */     }
/*  241 */     return display;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IWidget getWidget(int x, int y)
/*      */   {
/*  251 */     if (!getAppearance().insideMargin(x, y)) {
/*  252 */       return null;
/*      */     }
/*      */     
/*  255 */     IWidget ret = null;
/*  256 */     IWidget found = this;
/*      */     
/*  258 */     x -= getAppearance().getLeftMargins();
/*  259 */     y -= getAppearance().getBottomMargins();
/*      */     
/*  261 */     for (IWidget w : this.m_renderables) {
/*  262 */       ret = w.getWidget(x - w.getX(), y - w.getY());
/*      */       
/*  264 */       if (ret != null) {
/*  265 */         found = ret;
/*      */       }
/*      */     }
/*      */     
/*  269 */     if (this.m_displayScrollbar) {
/*  270 */       ret = this.m_scrollBar.getWidget(x - this.m_scrollBar.getX(), y - 
/*  271 */         this.m_scrollBar.getY());
/*      */     }
/*      */     
/*  274 */     if (ret != null) {
/*  275 */       found = ret;
/*      */     }
/*  277 */     return found;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void mouseWheel(MouseWheelEvent e)
/*      */   {
/*  288 */     super.mouseWheel(e);
/*  289 */     int rotation = e.wheeledUp() ? -e.getRotations() : e.getRotations();
/*  290 */     int offset = this.m_offset;
/*  291 */     float value = offsetToSliderValue(this.m_offset + rotation);
/*      */     
/*  293 */     this.m_scrollBar.getSlider().setValue(value);
/*      */     
/*  295 */     if ((this.m_offset != offset) && (this.m_mouseOverRenderable != null)) {
/*  296 */       this.m_mouseOverRenderable.fireMouseEntered();
/*      */     }
/*      */   }
/*      */   
/*      */   private float offsetToSliderValue(int offset) {
/*  301 */     if (offset < 0) offset = 0;
/*  302 */     if (this.m_horizontal) {
/*  303 */       int count = getPotentialColumnCount(this.m_currentRowCount) - 
/*  304 */         this.m_currentColumnCount + this.m_showOneMore;
/*  305 */       return offset / count;
/*      */     }
/*  307 */     int count = getPotentialRowCount(this.m_currentColumnCount) - 
/*  308 */       this.m_currentRowCount + this.m_showOneMore;
/*  309 */     return 1.0F - offset / count;
/*      */   }
/*      */   
/*      */   private int sliderValueToOffset(double value)
/*      */   {
/*  314 */     if (this.m_horizontal) {
/*  315 */       int count = getPotentialColumnCount(this.m_currentRowCount) - 
/*  316 */         this.m_currentColumnCount + this.m_showOneMore;
/*  317 */       return (int)Math.round(count * value);
/*      */     }
/*  319 */     int count = getPotentialRowCount(this.m_currentColumnCount) - 
/*  320 */       this.m_currentRowCount + this.m_showOneMore;
/*  321 */     return count - (int)Math.round(count * value);
/*      */   }
/*      */   
/*      */   private int getPotentialRowCount(int columnCount)
/*      */   {
/*  326 */     if (this.m_items == null) {
/*  327 */       return 0;
/*      */     }
/*  329 */     return (int)Math.ceil(this.m_items.size() / columnCount);
/*      */   }
/*      */   
/*      */   private int getPotentialColumnCount(int rowCount) {
/*  333 */     if (this.m_items == null) {
/*  334 */       return 0;
/*      */     }
/*  336 */     return (int)Math.ceil(this.m_items.size() / rowCount);
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
/*      */   public void setListOffset(int offset)
/*      */   {
/*  389 */     this.m_offset = offset;
/*      */     
/*  391 */     updateValues(false);
/*      */   }
/*      */   
/*      */   private void updateValues(boolean updateAtOnce) {
/*  395 */     if ((this.m_beingLayouted) || (this.m_renderables == null)) {
/*  396 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  407 */     RenderableContainer old = this.m_selectedRenderable;
/*  408 */     boolean isSelectedDisplayed = false;
/*      */     int offset;
/*  410 */     int offset; if (this.m_horizontal) {
/*  411 */       offset = this.m_offset * this.m_currentRowCount;
/*      */     } else {
/*  413 */       offset = this.m_offset * this.m_currentColumnCount;
/*      */     }
/*  415 */     for (int i = 0; i < this.m_renderables.size(); i++) {
/*  416 */       RenderableContainer renderable = (RenderableContainer)this.m_renderables.get(i);
/*  417 */       if ((this.m_items != null) && (i + offset >= 0) && 
/*  418 */         (i + offset < this.m_items.size())) {
/*  419 */         if ((i + offset == this.m_selectedOffset) && (!isSelectedDisplayed)) {
/*  420 */           isSelectedDisplayed = true;
/*  421 */           this.m_selectedRenderable = renderable;
/*      */         }
/*  423 */         renderable.setItem((Item)this.m_items.get(i + offset), true, updateAtOnce);
/*      */       } else {
/*  425 */         renderable.setItem(null, true, updateAtOnce);
/*      */       }
/*      */       
/*  428 */       if (!isSelectedDisplayed) {
/*  429 */         this.m_selectedRenderable = null;
/*  430 */         if (old != null) {
/*  431 */           old.updateRenderer(true, updateAtOnce);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void setCellSize(com.ankamagames.xulor.util.Dimension cellSize)
/*      */   {
/*  440 */     this.m_cellSize = cellSize;
/*  441 */     updateMinSize();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ScrollBar.ScrollBarBehaviour getScrollbarBehaviour()
/*      */   {
/*  448 */     return this.m_scrollBarBehaviour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setScrollBarBehaviour(ScrollBar.ScrollBarBehaviour behaviour)
/*      */   {
/*  455 */     this.m_scrollBarBehaviour = behaviour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Color getMouseOverColor()
/*      */   {
/*  462 */     return this.m_mouseOverColor;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setMouseOverColor(Color mouseOverColor)
/*      */   {
/*  469 */     this.m_mouseOverColor = mouseOverColor;
/*      */   }
/*      */   
/*      */   public int getMinDisplayedCells() {
/*  473 */     return this.m_minDisplayedCells;
/*      */   }
/*      */   
/*      */   public void setMinDisplayedCells(int minDisplayedCells) {
/*  477 */     this.m_minDisplayedCells = minDisplayedCells;
/*  478 */     updateMinSize();
/*      */   }
/*      */   
/*      */   public boolean isAutoIdealSize() {
/*  482 */     return this.m_autoIdealSize;
/*      */   }
/*      */   
/*      */   public void setAutoIdealSize(boolean autoIdealSize) {
/*  486 */     this.m_autoIdealSize = autoIdealSize;
/*  487 */     updateMinSize();
/*      */   }
/*      */   
/*      */   public void setShowOneMore(boolean show) {
/*  491 */     if (show) {
/*  492 */       this.m_showOneMore = 1;
/*      */     } else {
/*  494 */       this.m_showOneMore = 0;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Alignment getAlignment()
/*      */   {
/*  502 */     return this.m_alignment;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setAlignment(Alignment alignment)
/*      */   {
/*  509 */     if (this.m_horizontal) {
/*  510 */       if ((alignment.equals(Alignment.NORTH)) || 
/*  511 */         (alignment.equals(Alignment.CENTER)) || 
/*  512 */         (alignment.equals(Alignment.SOUTH)))
/*      */       {
/*  514 */         this.m_alignment = alignment;
/*      */       }
/*      */     }
/*  517 */     else if ((alignment.equals(Alignment.WEST)) || 
/*  518 */       (alignment.equals(Alignment.CENTER)) || 
/*  519 */       (alignment.equals(Alignment.EAST)))
/*      */     {
/*  521 */       this.m_alignment = alignment;
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isOppositeScrollBarPosition()
/*      */   {
/*  527 */     return this.m_oppositeScrollBarPosition;
/*      */   }
/*      */   
/*      */   public void setOppositeScrollBarPosition(boolean oppositeScrollBarPosition) {
/*  531 */     this.m_oppositeScrollBarPosition = oppositeScrollBarPosition;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public com.ankamagames.xulor.util.Dimension getWishedMinSize()
/*      */   {
/*  538 */     return this.m_wishedMinSize;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWishedMinSize(com.ankamagames.xulor.util.Dimension wishedMinSize)
/*      */   {
/*  546 */     this.m_wishedMinSize = wishedMinSize;
/*  547 */     updateMinSize();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public org.fenggui.util.Dimension getIdealSize()
/*      */   {
/*  555 */     return getIdealSize(-1, -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public org.fenggui.util.Dimension getIdealSize(int maxRows, int maxColumns)
/*      */   {
/*  565 */     org.fenggui.util.Dimension dim = getContentIdealSize(maxRows, maxColumns);
/*  566 */     int width = dim.getWidth();
/*  567 */     int height = dim.getHeight();
/*      */     
/*  569 */     width += this.m_appearance.getLeftMargins() + this.m_appearance.getRightMargins();
/*  570 */     height += this.m_appearance.getTopMargins() + this.m_appearance.getBottomMargins();
/*      */     
/*  572 */     return new org.fenggui.util.Dimension(width, height);
/*      */   }
/*      */   
/*      */   public org.fenggui.util.Dimension getContentIdealSize(int maxRows, int maxColumns) {
/*  576 */     int width = 10;int height = 10;
/*      */     
/*      */ 
/*  579 */     if ((this.m_cellSize.getHeightPercentage() == null) || (this.m_cellSize.getWidthPercentage() == null)) {
/*  580 */       if (this.m_cellSize.getWidthPercentage() != null) {
/*  581 */         int realColumnCount = (int)(1.0D / this.m_cellSize.getWidthPercentage().getValue() * 100.0D);
/*  582 */         int columnCount = maxColumns >= 0 ? Math.min(realColumnCount, maxColumns) : realColumnCount;
/*  583 */         height = this.m_cellSize.getHeight() * getPotentialRowCount(columnCount);
/*  584 */       } else if (this.m_cellSize.getHeightPercentage() != null) {
/*  585 */         int realRowCount = (int)(1.0D / this.m_cellSize.getHeightPercentage().getValue() * 100.0D);
/*  586 */         int rowCount = maxRows >= 0 ? Math.min(realRowCount, maxRows) : realRowCount;
/*  587 */         width = this.m_cellSize.getWidth() * getPotentialColumnCount(rowCount);
/*      */       } else {
/*  589 */         int size = this.m_items == null ? 0 : this.m_items.size();
/*  590 */         if (this.m_horizontal) {
/*  591 */           height = this.m_cellSize.getHeight();
/*  592 */           width = this.m_cellSize.getWidth() * size;
/*      */         } else {
/*  594 */           height = this.m_cellSize.getHeight() * size;
/*  595 */           width = this.m_cellSize.getWidth();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  600 */     height = maxRows >= 0 ? Math.min(this.m_cellSize.getHeight() * maxRows, height) : height;
/*  601 */     width = maxColumns >= 0 ? Math.min(this.m_cellSize.getWidth() * maxColumns, width) : width;
/*      */     
/*  603 */     if (this.m_displayScrollbar) {
/*  604 */       if (this.m_horizontal) {
/*  605 */         height += this.m_scrollBar.getHeight();
/*      */       } else {
/*  607 */         width += this.m_scrollBar.getWidth();
/*      */       }
/*      */     }
/*      */     
/*  611 */     return new org.fenggui.util.Dimension(width, height);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ListAppearance getAppearance()
/*      */   {
/*  621 */     return this.m_appearance;
/*      */   }
/*      */   
/*      */   public ScrollBar getScrollBar() {
/*  625 */     return this.m_scrollBar;
/*      */   }
/*      */   
/*      */   public void setSelected(Item item) {
/*  629 */     this.m_selectedOffset = this.m_items.indexOf(item);
/*  630 */     updateSelectedAppearance();
/*      */   }
/*      */   
/*      */   public void setSelectedValue(Object value) {
/*  634 */     int oldSelection = this.m_selectedOffset;
/*  635 */     this.m_selectedOffset = -1;
/*      */     
/*  637 */     for (int i = 0; i < this.m_items.size(); i++) {
/*  638 */       Item item = (Item)this.m_items.get(i);
/*  639 */       if (item.getValue() == value) {
/*  640 */         this.m_selectedOffset = i;
/*  641 */         break;
/*      */       }
/*      */     }
/*      */     
/*  645 */     if (oldSelection != this.m_selectedOffset) {
/*  646 */       if (oldSelection != -1) {
/*  647 */         fireSelectionChanged(new ListSelectionChangedEvent(this, getSelectedRenderableBySelectedOffset(this.m_selectedOffset), (Item)this.m_items.get(this.m_selectedOffset), false));
/*      */       }
/*  649 */       if (this.m_selectedOffset != -1) {
/*  650 */         fireSelectionChanged(new ListSelectionChangedEvent(this, getSelectedRenderableBySelectedOffset(this.m_selectedOffset), (Item)this.m_items.get(this.m_selectedOffset), true));
/*      */       }
/*  652 */       updateSelectedAppearance();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Item getSelectedItem()
/*      */   {
/*  662 */     if ((this.m_selectedOffset < 0) || (this.m_selectedOffset >= this.m_items.size())) {
/*  663 */       return null;
/*      */     }
/*  665 */     return (Item)this.m_items.get(this.m_selectedOffset);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public RenderableContainer getSelected()
/*      */   {
/*  674 */     return this.m_selectedRenderable;
/*      */   }
/*      */   
/*      */   public int getSelectedOffsetByValue(Object value) {
/*  678 */     for (int offset = 0; 
/*  679 */         offset < this.m_items.size(); offset++) {
/*  680 */       if (((Item)this.m_items.get(offset)).getValue() == value) {
/*      */         break;
/*      */       }
/*      */     }
/*  684 */     if (offset == this.m_items.size()) {
/*  685 */       return -1;
/*      */     }
/*  687 */     return offset;
/*      */   }
/*      */   
/*      */   public RenderableContainer getSelectedRenderableBySelectedOffset(int offset) { int number;
/*      */     int number;
/*  692 */     if (this.m_horizontal) {
/*  693 */       number = offset - this.m_currentRowCount * this.m_offset;
/*      */     } else {
/*  695 */       number = offset - this.m_currentColumnCount * this.m_offset;
/*      */     }
/*  697 */     if ((number < 0) || (number >= this.m_renderables.size())) {
/*  698 */       return null;
/*      */     }
/*  700 */     return (RenderableContainer)this.m_renderables.get(number);
/*      */   }
/*      */   
/*      */ 
/*      */   public int getSelectedOffsetBySelectedRenderable(RenderableContainer selected)
/*      */   {
/*  706 */     if (selected == null)
/*  707 */       return -1;
/*      */     int offset;
/*      */     int offset;
/*  710 */     if (this.m_horizontal) {
/*  711 */       offset = this.m_currentRowCount * this.m_offset + 
/*  712 */         this.m_renderables.indexOf(selected);
/*      */     } else {
/*  714 */       offset = this.m_currentColumnCount * this.m_offset + 
/*  715 */         this.m_renderables.indexOf(selected);
/*      */     }
/*      */     
/*  718 */     if (offset >= this.m_items.size()) {
/*  719 */       offset = -1;
/*      */     }
/*      */     
/*  722 */     return offset;
/*      */   }
/*      */   
/*      */   private void updateSelectedAppearance() {
/*  726 */     RenderableContainer old = this.m_selectedRenderable;
/*      */     
/*  728 */     this.m_selectedRenderable = getSelectedRenderableBySelectedOffset(this.m_selectedOffset);
/*  729 */     if (this.m_selectedRenderable != null) {
/*  730 */       this.m_selectedRenderable.updateRenderer(true, false);
/*      */     }
/*      */     
/*  733 */     if ((old != null) && (old != this.m_selectedRenderable)) {
/*  734 */       old.updateRenderer(true, false);
/*      */     }
/*      */   }
/*      */   
/*      */   private void fireSelectionChanged(RenderableContainer selected) {
/*  739 */     RenderableContainer old = this.m_selectedRenderable;
/*  740 */     if (selected == this.m_selectedRenderable) {
/*  741 */       return;
/*      */     }
/*  743 */     Item oldItem = getSelectedItem();
/*      */     
/*  745 */     this.m_selectedRenderable = selected;
/*  746 */     if (old != null) {
/*  747 */       old.updateRenderer(true, false);
/*      */     }
/*      */     
/*  750 */     if (this.m_selectedRenderable != null) {
/*  751 */       this.m_selectedOffset = getSelectedOffsetBySelectedRenderable(this.m_selectedRenderable);
/*  752 */       this.m_selectedRenderable.updateRenderer(true, false);
/*      */     } else {
/*  754 */       this.m_selectedOffset = -1;
/*      */     }
/*      */     
/*  757 */     if (this.m_selectedOffset == -1) {
/*  758 */       this.m_selectedValue = null;
/*      */     } else {
/*  760 */       this.m_selectedValue = ((Item)this.m_items.get(this.m_selectedOffset));
/*      */     }
/*      */     
/*  763 */     if (old != null) {
/*  764 */       ListSelectionChangedEvent e = new ListSelectionChangedEvent(
/*  765 */         this);
/*  766 */       e.setItemRenderable(old);
/*  767 */       e.setSelected(false);
/*  768 */       e.setValue(oldItem);
/*  769 */       fireSelectionChanged(e);
/*      */     }
/*  771 */     if (this.m_selectedRenderable != null) {
/*  772 */       ListSelectionChangedEvent e = new ListSelectionChangedEvent(
/*  773 */         this);
/*  774 */       e.setItemRenderable(this.m_selectedRenderable);
/*  775 */       e.setSelected(true);
/*  776 */       e.setValue(getSelectedItem());
/*  777 */       fireSelectionChanged(e);
/*      */     }
/*      */   }
/*      */   
/*      */   private void fireSelectionChanged(ListSelectionChangedEvent event)
/*      */   {
/*  783 */     for (IListSelectionChangedListener l : this.m_selectionChangedListeners) {
/*  784 */       l.selectionChanged(event);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int computeRowCount(int availableHeight, int cellHeight)
/*      */   {
/*      */     int rowCount;
/*      */     
/*      */ 
/*      */     int rowCount;
/*      */     
/*  797 */     if (this.m_horizontal) { int rowCount;
/*  798 */       if (!this.m_alignment.equals(Alignment.CENTER)) {
/*  799 */         rowCount = (int)Math.floor(availableHeight / cellHeight);
/*      */       } else { int rowCount;
/*  801 */         if ((this.m_items != null) && (this.m_items.size() > 0)) {
/*  802 */           int count = (int)Math.floor(availableHeight / cellHeight);
/*  803 */           rowCount = Math.min(this.m_items.size(), count);
/*      */         } else {
/*  805 */           rowCount = 0;
/*      */         }
/*      */       }
/*      */     } else {
/*  809 */       rowCount = (int)Math.floor(availableHeight / cellHeight);
/*      */     }
/*  811 */     return rowCount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int computeColumnCount(int availableWidth, int cellWidth)
/*      */   {
/*      */     int columnCount;
/*      */     
/*      */ 
/*      */     int columnCount;
/*      */     
/*  823 */     if (this.m_horizontal) {
/*  824 */       columnCount = (int)Math.floor(availableWidth / cellWidth);
/*      */     } else { int columnCount;
/*  826 */       if (!this.m_alignment.equals(Alignment.CENTER)) {
/*  827 */         columnCount = (int)Math.floor(availableWidth / cellWidth);
/*      */       } else { int columnCount;
/*  829 */         if ((this.m_items != null) && (this.m_items.size() > 0)) {
/*  830 */           int count = (int)Math.floor(availableWidth / cellWidth);
/*  831 */           columnCount = Math.min(this.m_items.size(), count);
/*      */         } else {
/*  833 */           columnCount = 0;
/*      */         }
/*      */       }
/*      */     }
/*  837 */     return columnCount;
/*      */   }
/*      */   
/*      */ 
/*      */   public void layout()
/*      */   {
/*  843 */     if (this.m_rendererManager == null) {
/*  844 */       return;
/*      */     }
/*      */     
/*  847 */     this.m_beingLayouted = true;
/*  848 */     int availableWidth = this.m_appearance.getContentWidth();
/*  849 */     int availableHeight = this.m_appearance.getContentHeight();
/*  850 */     int x = 0;int y = 0;
/*      */     
/*  852 */     int cellWidth = this.m_cellSize.getWidthPercentage() != null ? (int)Math.round(availableWidth * this.m_cellSize.getWidthPercentage().getValue() / 100.0D) : this.m_cellSize.getWidth();
/*  853 */     int cellHeight = this.m_cellSize.getHeightPercentage() != null ? (int)Math.round(availableHeight * this.m_cellSize.getHeightPercentage().getValue() / 100.0D) : this.m_cellSize.getHeight();
/*      */     
/*  855 */     int rowCount = computeRowCount(availableHeight, cellHeight);
/*  856 */     int columnCount = computeColumnCount(availableWidth, cellWidth);
/*      */     
/*      */ 
/*  859 */     switch (this.m_scrollBarBehaviour) {
/*      */     case WHEN_NEEDED: 
/*  861 */       this.m_displayScrollbar = false;
/*  862 */       break;
/*      */     case FORCE_HIDE: 
/*  864 */       this.m_displayScrollbar = true;
/*  865 */       break;
/*      */     case FORCE_DISPLAY: 
/*  867 */       if (((this.m_horizontal) && (getPotentialColumnCount(rowCount) + this.m_showOneMore > columnCount)) || (
/*  868 */         (!this.m_horizontal) && (getPotentialRowCount(columnCount) + this.m_showOneMore > rowCount))) {
/*  869 */         this.m_displayScrollbar = true;
/*      */       } else {
/*  871 */         this.m_displayScrollbar = false;
/*      */       }
/*  873 */       break;
/*      */     }
/*      */     
/*      */     
/*      */ 
/*  878 */     if (this.m_displayScrollbar)
/*      */     {
/*      */ 
/*      */ 
/*  882 */       if (this.m_horizontal) {
/*  883 */         int scrollBarHeight = this.m_scrollBar.getAppearance()
/*  884 */           .getMinSizeHint().getHeight();
/*  885 */         this.m_scrollBar.setWidth(availableWidth);
/*  886 */         this.m_scrollBar.setHeight(scrollBarHeight);
/*  887 */         availableHeight -= scrollBarHeight;
/*  888 */         if (!this.m_oppositeScrollBarPosition) {
/*  889 */           y += scrollBarHeight;
/*  890 */           this.m_scrollBar.setY(0);
/*      */         } else {
/*  892 */           this.m_scrollBar.setY(availableHeight);
/*      */         }
/*  894 */         this.m_scrollBar.setX(0);
/*  895 */         cellHeight = this.m_cellSize.getHeightPercentage() != null ? (int)Math.round(availableHeight * this.m_cellSize.getHeightPercentage().getValue() / 100.0D) : this.m_cellSize.getHeight();
/*  896 */         rowCount = computeRowCount(availableHeight, cellHeight);
/*      */       } else {
/*  898 */         int scrollBarWidth = this.m_scrollBar.getAppearance()
/*  899 */           .getMinSizeHint().getWidth();
/*  900 */         this.m_scrollBar.setWidth(scrollBarWidth);
/*  901 */         this.m_scrollBar.setHeight(availableHeight);
/*  902 */         availableWidth -= scrollBarWidth;
/*  903 */         if (!this.m_oppositeScrollBarPosition) {
/*  904 */           this.m_scrollBar.setX(availableWidth);
/*      */         } else {
/*  906 */           x += scrollBarWidth;
/*  907 */           this.m_scrollBar.setX(0);
/*      */         }
/*  909 */         this.m_scrollBar.setY(0);
/*      */         
/*      */ 
/*  912 */         cellWidth = this.m_cellSize.getWidthPercentage() != null ? (int)Math.round(availableWidth * this.m_cellSize.getWidthPercentage().getValue() / 100.0D) : this.m_cellSize.getWidth();
/*  913 */         columnCount = computeColumnCount(availableWidth, cellWidth);
/*      */       }
/*      */     }
/*      */     
/*  917 */     int oldX = x;
/*  918 */     int deltaY = (this.m_oppositeScrollBarPosition) && (this.m_horizontal) ? this.m_scrollBar.getHeight() : 0;
/*  919 */     if (this.m_alignment.equals(Alignment.SOUTH)) {
/*  920 */       y = this.m_appearance.getContentHeight() - cellHeight - deltaY - (
/*  921 */         availableHeight - cellHeight * rowCount);
/*  922 */     } else if ((this.m_alignment.equals(Alignment.CENTER)) && (this.m_horizontal)) {
/*  923 */       y = this.m_appearance.getContentHeight() - cellHeight - deltaY - 
/*  924 */         (availableHeight - cellHeight * rowCount) / 2;
/*      */     } else {
/*  926 */       y = this.m_appearance.getContentHeight() - cellHeight - deltaY;
/*      */     }
/*      */     
/*  929 */     int i = 0;
/*  930 */     int j = 0;
/*  931 */     for (i = 0; i < rowCount; i++) {
/*  932 */       if (this.m_alignment.equals(Alignment.EAST)) {
/*  933 */         x = oldX + availableWidth - cellWidth * columnCount;
/*  934 */       } else if (this.m_alignment.equals(Alignment.CENTER)) {
/*  935 */         x = oldX + (availableWidth - cellWidth * columnCount) / 2;
/*      */       } else {
/*  937 */         x = oldX;
/*      */       }
/*  939 */       for (j = 0; j < columnCount; j++) {
/*      */         int offset;
/*      */         int offset;
/*  942 */         if (this.m_horizontal) {
/*  943 */           offset = i + j * rowCount;
/*      */         } else
/*  945 */           offset = i * columnCount + j;
/*      */         RenderableContainer container;
/*  947 */         if (this.m_renderables.size() <= offset) {
/*  948 */           XRenderableContainer xcontainer = new XRenderableContainer();
/*  949 */           xcontainer.buildGUI();
/*  950 */           RenderableContainer container = (RenderableContainer)xcontainer.getWidget();
/*  951 */           container.setCollection(this);
/*      */           
/*  953 */           container.setRenderers(this.m_renderers);
/*  954 */           container.setRendererManager(this.m_rendererManager);
/*  955 */           for (ItemOutListener listener : this.m_itemOutListeners) {
/*  956 */             container.addItemOutListener(listener);
/*      */           }
/*  958 */           for (ItemOverListener listener : this.m_itemOverListeners) {
/*  959 */             container.addItemOverListener(listener);
/*      */           }
/*      */           
/*  962 */           container.addMousePressedListener(new org.fenggui.event.mouse.IMousePressedListener() {
/*      */             public void mousePressed(MousePressedEvent mousePressedEvent) {
/*  964 */               List.this.fireSelectionChanged((RenderableContainer)mousePressedEvent.getSource());
/*      */             }
/*  966 */           });
/*  967 */           container.addMouseEnteredListener(new IMouseEnteredListener() {
/*      */             public void mouseEntered(MouseEnteredEvent event) {
/*  969 */               RenderableContainer container = (RenderableContainer)event.getEntered();
/*  970 */               if (container.getItemValue() != null) {
/*  971 */                 List.this.m_mouseOverRenderable = ((RenderableContainer)event.getEntered());
/*      */               }
/*      */             }
/*  974 */           });
/*  975 */           container.addMouseExitedListener(new org.fenggui.event.mouse.IMouseExitedListener() {
/*      */             public void mouseExited(MouseExitedEvent event) {
/*  977 */               List.this.m_mouseOverRenderable = null;
/*      */             }
/*  979 */           });
/*  980 */           this.m_renderables.add(container);
/*  981 */           container.setParent(this);
/*  982 */           if (getDisplay() != null) {
/*  983 */             container.addedToWidgetTree();
/*      */           }
/*      */         } else {
/*  986 */           container = (RenderableContainer)this.m_renderables.get(offset);
/*      */         }
/*  988 */         if ((this.m_items != null) && (offset < this.m_items.size())) {
/*  989 */           container.setItem((Item)this.m_items.get(offset));
/*      */         } else {
/*  991 */           container.setItem(null);
/*      */         }
/*      */         
/*  994 */         if (container != null) {
/*  995 */           container.setWidth(cellWidth);
/*  996 */           container.setHeight(cellHeight);
/*  997 */           container.setX(x);
/*  998 */           container.setY(y);
/*      */         }
/*      */         
/* 1001 */         x += cellWidth;
/*      */       }
/* 1003 */       y -= cellHeight;
/*      */     }
/*      */     
/* 1006 */     if ((this.m_currentColumnCount > columnCount) || (this.m_currentRowCount > rowCount)) {
/* 1007 */       ArrayList<RenderableContainer> toRemove = new ArrayList();
/* 1008 */       for (int k = 0; k < rowCount; k++) {
/* 1009 */         for (int l = j; l < this.m_currentColumnCount; l++) {
/* 1010 */           if (k * this.m_currentColumnCount + l < this.m_renderables.size())
/* 1011 */             toRemove.add(
/* 1012 */               (RenderableContainer)this.m_renderables.get(k * this.m_currentColumnCount + l));
/*      */         }
/*      */       }
/*      */       int l;
/* 1016 */       for (int k = i; k < this.m_currentRowCount; k++) {
/* 1017 */         for (l = 0; l < columnCount; l++) {
/* 1018 */           if (k * columnCount + l < this.m_renderables.size()) {
/* 1019 */             toRemove.add((RenderableContainer)this.m_renderables.get(k * columnCount + l));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1024 */       for (RenderableContainer container : toRemove) {
/* 1025 */         this.m_renderables.remove(container);
/* 1026 */         container.removedFromWidgetTree();
/*      */       }
/*      */     }
/*      */     
/* 1030 */     this.m_currentColumnCount = columnCount;
/* 1031 */     this.m_currentRowCount = rowCount;
/*      */     
/* 1033 */     if (this.m_displayScrollbar) {
/* 1034 */       updateScrollBarLayout();
/* 1035 */       this.m_scrollBar.layout();
/*      */     }
/*      */     
/* 1038 */     this.m_beingLayouted = false;
/* 1039 */     updateValues(true);
/*      */   }
/*      */   
/*      */   private void updateScrollBarLayout()
/*      */   {
/* 1044 */     if (this.m_displayScrollbar) {
/* 1045 */       if (this.m_horizontal) {
/* 1046 */         double potentialColumnCount = getPotentialColumnCount(this.m_currentRowCount);
/* 1047 */         if (this.m_currentRowCount + this.m_showOneMore > 0)
/*      */         {
/* 1049 */           if (potentialColumnCount - this.m_currentColumnCount + this.m_showOneMore > 0.0D) {
/* 1050 */             if (!this.m_scrollBar.isEnabled()) {
/* 1051 */               this.m_scrollBar.setEnabled(true);
/*      */             }
/* 1053 */             this.m_scrollBar.setButtonJump(1.0D / (potentialColumnCount - 
/* 1054 */               this.m_currentColumnCount + this.m_showOneMore));
/* 1055 */             this.m_scrollBar.getSlider().setSize(
/* 1056 */               this.m_currentColumnCount / (
/* 1057 */               potentialColumnCount + this.m_showOneMore)); return;
/*      */           }
/*      */         }
/*      */         
/* 1061 */         this.m_scrollBar.setEnabled(false);
/*      */       }
/*      */       else {
/* 1064 */         double potentialRowCount = getPotentialRowCount(this.m_currentColumnCount);
/* 1065 */         if (this.m_currentColumnCount + this.m_showOneMore > 0)
/*      */         {
/* 1067 */           if (potentialRowCount - this.m_currentRowCount + this.m_showOneMore > 0.0D) {
/* 1068 */             if (!this.m_scrollBar.isEnabled()) {
/* 1069 */               this.m_scrollBar.setEnabled(true);
/*      */             }
/* 1071 */             this.m_scrollBar.setButtonJump(1.0D / (potentialRowCount - 
/* 1072 */               this.m_currentRowCount + this.m_showOneMore));
/* 1073 */             this.m_scrollBar.getSlider().setSize(
/* 1074 */               this.m_currentRowCount / (
/* 1075 */               potentialRowCount + this.m_showOneMore)); return;
/*      */           }
/*      */         }
/*      */         
/* 1079 */         this.m_scrollBar.setEnabled(false);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public class ListAppearance
/*      */     extends DecoratorAppearance
/*      */     implements IAppearance
/*      */   {
/*      */     List m_list;
/*      */     
/*      */     public ListAppearance(IWidget widget)
/*      */     {
/* 1093 */       super();
/* 1094 */       this.m_list = ((List)widget);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public org.fenggui.util.Dimension getContentMinSizeHint()
/*      */     {
/* 1104 */       int minWidth = 30;
/* 1105 */       int minHeight = 30;
/* 1106 */       if (List.this.m_autoIdealSize) {
/* 1107 */         return List.this.getContentIdealSize(-1, -1);
/*      */       }
/* 1109 */       int widthMult = List.this.m_horizontal ? List.this.m_minDisplayedCells : 1;
/* 1110 */       int heightMult = List.this.m_horizontal ? 1 : List.this.m_minDisplayedCells;
/* 1111 */       if (List.this.m_cellSize != null) {
/* 1112 */         minWidth = List.this.m_cellSize.getWidth() * widthMult;
/* 1113 */         minHeight = List.this.m_cellSize.getHeight() * heightMult;
/*      */       }
/* 1115 */       if (List.this.m_wishedMinSize != null) {
/* 1116 */         minWidth = Math.max(minWidth, List.this.m_wishedMinSize.getWidth());
/* 1117 */         minHeight = Math.max(minHeight, List.this.m_wishedMinSize.getHeight());
/*      */       }
/* 1119 */       return new org.fenggui.util.Dimension(minWidth, minHeight);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void paintContent(Graphics g, IOpenGL gl)
/*      */     {
/* 1131 */       if ((List.this.m_mouseOverRenderable != null) && (List.this.m_mouseOverColor != null)) {
/* 1132 */         g.setColor((float)List.this.m_mouseOverColor.getRed(), 
/* 1133 */           (float)List.this.m_mouseOverColor.getGreen(), 
/* 1134 */           (float)List.this.m_mouseOverColor.getBlue(), 
/* 1135 */           (float)List.this.m_mouseOverColor.getAlpha());
/* 1136 */         g.drawFilledRectangle(List.this.m_mouseOverRenderable.getX(), 
/* 1137 */           List.this.m_mouseOverRenderable.getY(), 
/* 1138 */           List.this.m_mouseOverRenderable.getWidth(), List.this.m_mouseOverRenderable.getHeight());
/*      */       }
/*      */       
/* 1141 */       if (List.this.m_displayScrollbar) {
/* 1142 */         gl.pushMatrix();
/* 1143 */         g.translate(List.this.m_scrollBar.getX(), List.this.m_scrollBar.getY());
/*      */         
/* 1145 */         List.this.m_scrollBar.paint(g);
/*      */         
/* 1147 */         g.translate(-List.this.m_scrollBar.getX(), -List.this.m_scrollBar.getY());
/* 1148 */         gl.popMatrix();
/*      */       }
/*      */       
/* 1151 */       for (int i = 0; i < List.this.m_renderables.size(); i++) {
/* 1152 */         IWidget c = (IWidget)List.this.m_renderables.get(i);
/*      */         
/* 1154 */         if ((c.getX() <= this.m_list.getWidth()) && 
/* 1155 */           (c.getY() <= this.m_list.getHeight()))
/*      */         {
/*      */ 
/* 1158 */           gl.pushMatrix();
/* 1159 */           g.translate(c.getX(), c.getY());
/*      */           
/* 1161 */           c.paint(g);
/*      */           
/* 1163 */           g.translate(-c.getX(), -c.getY());
/* 1164 */           gl.popMatrix();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isHorizontal()
/*      */   {
/* 1175 */     return this.m_horizontal;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Item[] getItems()
/*      */   {
/* 1182 */     return (Item[])this.m_items.toArray(new Item[0]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setItems(Item[] items)
/*      */   {
/* 1189 */     Object selectedValue = getSelectedItem() != null ? getSelectedItem().getValue() : null;
/*      */     
/* 1191 */     this.m_items = new ArrayList();
/*      */     
/* 1193 */     if (items != null) { Item[] arrayOfItem;
/* 1194 */       int j = (arrayOfItem = items).length; for (int i = 0; i < j; i++) { Item item = arrayOfItem[i];
/* 1195 */         this.m_items.add(item);
/*      */       }
/*      */     }
/*      */     
/* 1199 */     this.m_selectedOffset = getSelectedOffsetByValue(selectedValue);
/*      */     
/* 1201 */     for (int i = this.m_items.size(); i < this.m_renderables.size(); i++) {
/* 1202 */       ((RenderableContainer)this.m_renderables.get(i)).setRenderer(null);
/*      */     }
/*      */     
/* 1205 */     checkOffsetValidity();
/* 1206 */     if (this.m_autoIdealSize) {
/* 1207 */       updateMinSize();
/*      */     }
/* 1209 */     layout();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void checkOffsetValidity()
/*      */   {
/* 1218 */     int displayLength = (this.m_horizontal ? this.m_currentColumnCount : this.m_currentRowCount) - this.m_showOneMore;
/*      */     
/* 1220 */     if (this.m_items.size() < displayLength) {
/* 1221 */       this.m_offset = 0;
/* 1222 */     } else if ((this.m_items.size() - this.m_offset < displayLength) || (this.m_offset < 0)) {
/* 1223 */       this.m_offset = (this.m_items.size() - displayLength);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getRenderableIndex(RenderableContainer container)
/*      */   {
/* 1233 */     return this.m_renderables.indexOf(container);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getItemIndex(Object value)
/*      */   {
/* 1242 */     int i = 0;
/* 1243 */     for (Item item : this.m_items) {
/* 1244 */       if (item.getValue() == value) {
/* 1245 */         return i;
/*      */       }
/* 1247 */       i++;
/*      */     }
/* 1249 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ArrayList<RenderableContainer> getRenderables()
/*      */   {
/* 1256 */     return this.m_renderables;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setRenderables(ArrayList<RenderableContainer> renderables)
/*      */   {
/* 1263 */     this.m_renderables = renderables;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ArrayList<ItemRenderer> getRenderers()
/*      */   {
/* 1270 */     return this.m_renderers;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setRenderers(ArrayList<ItemRenderer> renderers)
/*      */   {
/* 1277 */     this.m_renderers = renderers;
/* 1278 */     if (this.m_rendererManager == null) {
/* 1279 */       this.m_rendererManager = new ItemRendererManager(this.m_renderers);
/*      */     } else {
/* 1281 */       this.m_rendererManager.setRenderers(this.m_renderers);
/*      */     }
/*      */     
/* 1284 */     for (IItemRenderable renderable : this.m_renderables) {
/* 1285 */       renderable.setRendererManager(this.m_rendererManager);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public com.ankamagames.xulor.util.Dimension getCellSize()
/*      */   {
/* 1294 */     return this.m_cellSize;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public IWidget getNextTraversableWidget(IWidget start)
/*      */   {
/* 1302 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public IWidget getPreviousTraversableWidget(IWidget start)
/*      */   {
/* 1310 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IWidget getNextWidget(IWidget start)
/*      */   {
/* 1320 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public IWidget getPreviousWidget(IWidget start)
/*      */   {
/* 1330 */     return null;
/*      */   }
/*      */   
/*      */   private void addAndUpdateOffset(Item item, int position) {
/* 1334 */     this.m_items.add(position, item);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addItem(Item item)
/*      */   {
/* 1343 */     if (this.m_items == null) {
/* 1344 */       this.m_items = new ArrayList();
/*      */     }
/* 1346 */     addAndUpdateOffset(item, this.m_items.size());
/* 1347 */     item.cleanAssociatedValue();
/* 1348 */     this.m_selectedOffset = (this.m_items.size() - 1);
/* 1349 */     layout();
/* 1350 */     updateSelectedAppearance();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean addItem(int position, Item item)
/*      */   {
/* 1360 */     if (this.m_items == null) {
/* 1361 */       this.m_items = new ArrayList();
/*      */     }
/* 1363 */     if ((position >= 0) || (position <= this.m_items.size())) {
/* 1364 */       addAndUpdateOffset(item, position);
/* 1365 */       item.cleanAssociatedValue();
/* 1366 */       this.m_selectedOffset = position;
/* 1367 */       layout();
/* 1368 */       updateSelectedAppearance();
/* 1369 */       return true;
/*      */     }
/* 1371 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addItem(Item oldItem, Item newItem)
/*      */   {
/* 1381 */     boolean isSet = false;
/* 1382 */     if (this.m_items != null) {
/* 1383 */       for (int i = 0; i < this.m_items.size(); i++) {
/* 1384 */         if (oldItem == this.m_items.get(i)) {
/* 1385 */           addAndUpdateOffset(newItem, i);
/* 1386 */           newItem.cleanAssociatedValue();
/* 1387 */           this.m_selectedOffset = i;
/* 1388 */           isSet = true;
/* 1389 */           break;
/*      */         }
/*      */       }
/* 1392 */       if (isSet) {
/* 1393 */         layout();
/* 1394 */         updateSelectedAppearance();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean replaceItem(Item oldItem, Item newItem)
/*      */   {
/* 1406 */     boolean isSet = false;
/*      */     
/* 1408 */     if (this.m_items != null) {
/* 1409 */       for (int i = 0; i < this.m_items.size(); i++) {
/* 1410 */         if (oldItem == this.m_items.get(i)) {
/* 1411 */           this.m_items.set(i, newItem);
/* 1412 */           isSet = true;
/* 1413 */           break;
/*      */         }
/*      */       }
/* 1416 */       if (isSet) {
/* 1417 */         newItem.cleanAssociatedValue();
/* 1418 */         this.m_selectedOffset = i;
/* 1419 */         layout();
/* 1420 */         updateSelectedAppearance();
/*      */       }
/* 1422 */       return isSet;
/*      */     }
/* 1424 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Item getItem(int i)
/*      */   {
/* 1433 */     if (this.m_items != null) {
/* 1434 */       return (Item)this.m_items.get(i);
/*      */     }
/* 1436 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeItem(Item item)
/*      */   {
/* 1445 */     if (this.m_items != null)
/*      */     {
/* 1447 */       this.m_items.remove(item);
/* 1448 */       item.cleanAssociatedValue();
/* 1449 */       this.m_selectedOffset = -1;
/* 1450 */       layout();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int size()
/*      */   {
/* 1461 */     if (this.m_items != null) {
/* 1462 */       return this.m_items.size();
/*      */     }
/* 1464 */     return 0;
/*      */   }
/*      */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\List.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */