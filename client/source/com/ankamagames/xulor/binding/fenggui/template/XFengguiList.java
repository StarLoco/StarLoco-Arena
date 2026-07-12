/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.IListSelectionChangedListener;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.ItemRenderer;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.List;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.ListSelectionChangedEvent;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XListAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.event.SelectionChangedEvent;
/*     */ import com.ankamagames.xulor.event.listener.ItemOutListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemOverListener;
/*     */ import com.ankamagames.xulor.event.listener.SelectionChangedListener;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.ISelection;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeListAppearance;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Dimension;
/*     */ import com.ankamagames.xulor.util.Item;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import org.fenggui.ScrollBar;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.Widget;
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
/*     */ public class XFengguiList
/*     */   extends XComponent
/*     */ {
/*     */   public static final String TAG = "List";
/*  42 */   private List m_list = null;
/*     */   
/*     */   private Dimension m_cellSize;
/*  45 */   private Item[] m_content = null;
/*  46 */   private ArrayList<ItemRenderer> m_renderers = new ArrayList<ItemRenderer>();
/*     */   private XFengguiList THIS;
/*  48 */   private Alignment m_align = null;
/*  49 */   private Dimension m_minSize = null;
/*     */   
/*  51 */   private int m_minDisplayedCells = 1;
/*     */   
/*     */   private boolean m_displayScrollBar = true;
/*     */   
/*     */   private boolean m_horizontal = false;
/*     */   private boolean m_oppositeScrollBarPosition = false;
/*     */   private boolean m_showOneMore = false;
/*     */   private boolean m_autoIdealSize = false;
/*     */   private boolean m_displayScrollBarInit = false;
/*     */   private boolean m_horizontalInit = false;
/*     */   private boolean m_oppositeScrollBarPositionInit = false;
/*     */   private boolean m_showOneMoreInit = false;
/*     */   private boolean m_autoIdealSizeInit = false;
/*  64 */   private ArrayList<SelectionChangedListener> m_scl = new ArrayList<SelectionChangedListener>();
/*  65 */   private IListSelectionChangedListener m_listSelectionChangedListener = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector<ItemOutListener> m_iol;
/*     */ 
/*     */   
/*     */   private Vector<ItemOverListener> m_iovl;
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  77 */     if (this.m_list == null) {
/*  78 */       this.m_list = new List(this.m_horizontal);
/*     */       
/*  80 */       this.m_listSelectionChangedListener = new IListSelectionChangedListener() {
/*     */           public void selectionChanged(ListSelectionChangedEvent event) {
/*  82 */             SelectionChangedEvent sce = new SelectionChangedEvent((IElement)XFengguiList.this.THIS, (ISelection)event.getItemRenderable(), event.getSelected(), event.getValue());
/*  83 */             for (SelectionChangedListener l : XFengguiList.this.m_scl) {
/*  84 */               l.run(sce);
/*     */             }
/*     */           }
/*     */         };
/*     */       
/*  89 */       if (this.m_iol != null) {
/*  90 */         for (ItemOutListener l : this.m_iol) {
/*  91 */           this.m_list.addItemOutListener(l);
/*     */         }
/*  93 */         this.m_iol.clear();
/*     */       } 
/*     */       
/*  96 */       if (this.m_iovl != null) {
/*  97 */         for (ItemOverListener l : this.m_iovl) {
/*  98 */           this.m_list.addItemOverListener(l);
/*     */         }
/* 100 */         this.m_iovl.clear();
/*     */       } 
/*     */       
/* 103 */       applyAllAttributes();
/*     */ 
/*     */ 
/*     */       
/* 107 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this); 
/* 108 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_list, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/* 111 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/* 112 */       c.buildGUI(); b++; }
/*     */     
/* 114 */     applyTheme();
/*     */   }
/*     */   
/*     */   public void applyAllAttributes() {
/* 118 */     if (this.m_list != null) {
/* 119 */       if (this.m_cellSize != null) this.m_list.setCellSize(this.m_cellSize); 
/* 120 */       if (this.m_content != null) this.m_list.setItems(this.m_content); 
/* 121 */       if (this.m_renderers != null) this.m_list.setRenderers(this.m_renderers); 
/* 122 */       if (this.m_align != null) this.m_list.setAlignment(this.m_align); 
/* 123 */       if (this.m_minSize != null) this.m_list.setWishedMinSize(this.m_minSize); 
/* 124 */       this.m_list.setMinDisplayedCells(this.m_minDisplayedCells);
/* 125 */       if (this.m_displayScrollBarInit) {
/* 126 */         if (this.m_displayScrollBar) {
/* 127 */           this.m_list.setScrollBarBehaviour(ScrollBar.ScrollBarBehaviour.FORCE_DISPLAY);
/*     */         } else {
/* 129 */           this.m_list.setScrollBarBehaviour(ScrollBar.ScrollBarBehaviour.FORCE_HIDE);
/*     */         } 
/*     */       }
/* 132 */       if (this.m_oppositeScrollBarPositionInit) this.m_list.setOppositeScrollBarPosition(this.m_oppositeScrollBarPosition); 
/* 133 */       if (this.m_showOneMoreInit) this.m_list.setShowOneMore(this.m_showOneMore); 
/* 134 */       if (this.m_autoIdealSizeInit) this.m_list.setAutoIdealSize(this.m_autoIdealSize); 
/* 135 */       applyComponentAttributes();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/* 140 */     if (this.m_themeNeedToBeApplied) {
/* 141 */       this.m_themeNeedToBeApplied = false;
/* 142 */       applyListTheme(this.m_list, this.m_themeElement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void add(IElement element) {
/* 147 */     if (element instanceof ItemRenderer) {
/* 148 */       addRenderer((ItemRenderer)element);
/*     */     } else {
/* 150 */       super.add(element);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item[] getContent() {
/* 158 */     return this.m_content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContent(Object[] content) {
/*     */     Item[] items;
/* 166 */     if (content != null) {
/* 167 */       items = new Item[content.length];
/* 168 */       for (int i = 0; i < content.length; i++) {
/* 169 */         Object value = content[i];
/* 170 */         if (value instanceof Item) {
/* 171 */           items[i] = (Item)value;
/*     */         } else {
/* 173 */           items[i] = new Item(value);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 177 */       items = (Item[])null;
/*     */     } 
/*     */     
/* 180 */     this.m_content = items;
/* 181 */     if (this.m_list != null) {
/* 182 */       this.m_list.setItems(this.m_content);
/*     */     }
/*     */   }
/*     */   
/*     */   public ArrayList<ItemRenderer> getRenderer() {
/* 187 */     return this.m_renderers;
/*     */   }
/*     */   
/*     */   public void addRenderer(ItemRenderer renderer) {
/* 191 */     this.m_renderers.add(renderer);
/*     */   }
/*     */   
/*     */   public void setOnSelectionChange(SelectionChangedListener l) {
/* 195 */     this.m_scl.add(l);
/*     */   }
/*     */   
/*     */   public void setOnItemOver(ItemOverListener l) {
/* 199 */     if (this.m_iovl == null)
/* 200 */       this.m_iovl = new Vector<ItemOverListener>(); 
/* 201 */     this.m_iovl.add(l);
/*     */   }
/*     */   
/*     */   public void setOnItemOut(ItemOutListener l) {
/* 205 */     if (this.m_iol == null)
/* 206 */       this.m_iol = new Vector<ItemOutListener>(); 
/* 207 */     this.m_iol.add(l);
/*     */   }
/*     */   
/*     */   public void setSelectedItem(Item item) {
/* 211 */     if (this.m_list != null) {
/* 212 */       this.m_list.setSelected(item);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setSelectedItemValue(Object value) {
/* 217 */     if (this.m_list != null) {
/* 218 */       this.m_list.setSelectedValue(value);
/*     */     }
/*     */   }
/*     */   
/*     */   public Item getSelectedItem() {
/* 223 */     if (this.m_list != null) {
/* 224 */       return this.m_list.getSelectedItem();
/*     */     }
/* 226 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 234 */     return (Widget)this.m_list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyElementData(XFengguiList list) {
/* 248 */     if (this.m_align != null) list.setAlign(this.m_align); 
/* 249 */     if (this.m_cellSize != null) list.setCellSize(this.m_cellSize.cloneDimension()); 
/* 250 */     if (this.m_displayScrollBarInit) list.setScrollBar(this.m_displayScrollBar); 
/* 251 */     if (this.m_horizontalInit) list.setHorizontal(this.m_horizontal); 
/* 252 */     if (this.m_showOneMoreInit) list.setShowOneMore(this.m_showOneMore); 
/* 253 */     if (this.m_autoIdealSizeInit) list.setAutoIdealSize(this.m_autoIdealSize); 
/* 254 */     if (this.m_minSize != null) list.setMinSize(this.m_minSize.cloneDimension()); 
/* 255 */     if (this.m_oppositeScrollBarPositionInit) list.setOppositeScrollBarPosition(this.m_oppositeScrollBarPosition); 
/* 256 */     list.setMinDisplayedCells(this.m_minDisplayedCells);
/*     */     
/* 258 */     for (ItemRenderer renderers : this.m_renderers) list.addRenderer(renderers); 
/* 259 */     for (SelectionChangedListener scl : this.m_scl) list.setOnSelectionChange(scl); 
/* 260 */     if (this.m_iol != null)
/* 261 */       for (ItemOutListener iol : this.m_iol) list.setOnItemOut(iol);  
/* 262 */     if (this.m_iovl != null)
/* 263 */       for (ItemOverListener iovl : this.m_iovl) list.setOnItemOver(iovl);  
/* 264 */     copyElementData((IElement)list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 271 */     XFengguiList list = new XFengguiList();
/* 272 */     copyElementData(list);
/* 273 */     return (IElement)list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 281 */     return "List";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getCellSize() {
/* 288 */     return this.m_cellSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCellSize(Dimension cellSize) {
/* 295 */     this.m_cellSize = cellSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isScrollBar() {
/* 302 */     return this.m_displayScrollBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScrollBar(boolean displayScrollBar) {
/* 309 */     this.m_displayScrollBar = displayScrollBar;
/* 310 */     this.m_displayScrollBarInit = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShowOneMore() {
/* 317 */     return this.m_showOneMore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShowOneMore(boolean showOneMore) {
/* 324 */     this.m_showOneMore = showOneMore;
/* 325 */     this.m_showOneMoreInit = true;
/*     */   }
/*     */   
/*     */   public boolean isAutoIdealSize() {
/* 329 */     return this.m_autoIdealSize;
/*     */   }
/*     */   
/*     */   public void setAutoIdealSize(boolean autoIdealSize) {
/* 333 */     this.m_autoIdealSize = autoIdealSize;
/* 334 */     this.m_autoIdealSizeInit = true;
/* 335 */     if (this.m_list != null) {
/* 336 */       this.m_list.setAutoIdealSize(this.m_autoIdealSize);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Alignment getAlign() {
/* 344 */     return this.m_align;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlign(Alignment align) {
/* 351 */     this.m_align = align;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHorizontal() {
/* 358 */     return this.m_horizontal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHorizontal(boolean horizontal) {
/* 365 */     this.m_horizontal = horizontal;
/* 366 */     this.m_horizontalInit = true;
/*     */   }
/*     */   
/*     */   public boolean isOppositeScrollBarPosition() {
/* 370 */     return this.m_oppositeScrollBarPosition;
/*     */   }
/*     */   
/*     */   public void setOppositeScrollBarPosition(boolean oppositeScrollBarPosition) {
/* 374 */     this.m_oppositeScrollBarPosition = oppositeScrollBarPosition;
/* 375 */     this.m_oppositeScrollBarPositionInit = true;
/* 376 */     if (this.m_list != null) {
/* 377 */       this.m_list.setOppositeScrollBarPosition(this.m_oppositeScrollBarPosition);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinDisplayedCells() {
/* 383 */     if (this.m_list != null) {
/* 384 */       return this.m_list.getMinDisplayedCells();
/*     */     }
/* 386 */     return this.m_minDisplayedCells;
/*     */   }
/*     */   
/*     */   public void setMinDisplayedCells(int minDisplayedCells) {
/* 390 */     this.m_minDisplayedCells = minDisplayedCells;
/* 391 */     if (this.m_list != null) {
/* 392 */       this.m_list.setMinDisplayedCells(minDisplayedCells);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinSize() {
/* 400 */     return this.m_minSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinSize(Dimension minSize) {
/* 407 */     this.m_minSize = minSize;
/* 408 */     if (this.m_list != null) {
/* 409 */       this.m_list.setWishedMinSize(minSize);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void applyListTheme(List list, ThemeElement element) {
/* 414 */     if (list == null || element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 418 */     list.getAppearance().removeAll();
/* 419 */     XComponent.applyThemeAttributes((Widget)list, element.getAttributes());
/* 420 */     XSpacingAppearance.setAppearance((StandardWidget)list, element);
/* 421 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 422 */     for (ThemeAppearance app : appearances) {
/* 423 */       if (app != null) {
/* 424 */         XDecoratorAppearance.setAppearance((StandardWidget)list, app);
/* 425 */         if (app instanceof ThemeListAppearance)
/* 426 */           XListAppearance.setAppearance(list, (ThemeListAppearance)app); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XFengguiList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */