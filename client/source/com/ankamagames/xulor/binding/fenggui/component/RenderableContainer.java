/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiScene;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XRenderableContainer;
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.event.ItemOutEvent;
/*     */ import com.ankamagames.xulor.event.ItemOverEvent;
/*     */ import com.ankamagames.xulor.event.listener.ItemOutListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemOverListener;
/*     */ import com.ankamagames.xulor.template.IDragNDropable;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IItemRenderable;
/*     */ import com.ankamagames.xulor.util.Item;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.LayoutManager;
/*     */ import org.fenggui.event.mouse.MouseEnteredEvent;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.layout.RowLayout;
/*     */ import org.fenggui.render.Graphics;
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
/*     */ public class RenderableContainer
/*     */   extends Container
/*     */   implements IItemRenderable
/*     */ {
/*     */   private XElement[] m_elements;
/*  37 */   private XRenderableContainer m_xElement = null;
/*     */   
/*  39 */   private Item m_item = null;
/*     */   
/*  41 */   private ItemRenderer m_renderer = null;
/*     */   
/*  43 */   private ArrayList<ItemRenderer> m_renderers = new ArrayList<ItemRenderer>();
/*     */   
/*     */   private ItemRendererManager m_rendererManager;
/*     */   
/*  47 */   private ArrayList<IWidget> m_children = null;
/*     */   
/*  49 */   private RenderableCollection m_collection = null;
/*     */   
/*     */   private IDragNDropable m_dragNDropable;
/*     */   
/*     */   private boolean m_itemNeedToBeApplied = false;
/*     */   
/*  55 */   private DragNDropListener m_dragNDropListener = null;
/*     */   
/*  57 */   private final ArrayList<ItemOutListener> m_itemOutListeners = new ArrayList<ItemOutListener>();
/*     */   
/*  59 */   private final ArrayList<ItemOverListener> m_itemOverListeners = new ArrayList<ItemOverListener>();
/*     */   
/*     */   private boolean m_needRendering = true;
/*     */   
/*     */   public RenderableContainer() {
/*  64 */     this((ArrayList<ItemRenderer>)null, (RenderableCollection)null);
/*     */   }
/*     */   
/*     */   public RenderableContainer(ArrayList<ItemRenderer> itemRenderers) {
/*  68 */     this(itemRenderers, (RenderableCollection)null);
/*     */   }
/*     */   
/*     */   public RenderableContainer(RenderableCollection collection) {
/*  72 */     this((ArrayList<ItemRenderer>)null, collection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderableContainer(ArrayList<ItemRenderer> itemRenderers, RenderableCollection collection) {
/*  79 */     this.m_children = new ArrayList<IWidget>();
/*  80 */     this.m_collection = collection;
/*     */     
/*  82 */     setLayoutManager((LayoutManager)new RowLayout(true));
/*     */     
/*  84 */     if (itemRenderers != null) {
/*  85 */       setRenderers(itemRenderers);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setXRenderableContainer(XRenderableContainer renderable) {
/*  90 */     this.m_xElement = renderable;
/*     */   }
/*     */   
/*     */   public XRenderableContainer getXRenderableContainer() {
/*  94 */     return this.m_xElement;
/*     */   }
/*     */   
/*     */   private void addDragNDropListener() {
/*  98 */     FengguiScene scene = (FengguiScene)Xulor.getInstance().getScene();
/*  99 */     if (scene == null || scene.getDisplay() == null) {
/*     */       return;
/*     */     }
/* 102 */     if (this.m_dragNDropListener != null) {
/* 103 */       scene.getDisplay().removeDndListener(this.m_dragNDropListener);
/*     */     }
/* 105 */     this.m_dragNDropListener = new DragNDropListener(this);
/* 106 */     scene.getDisplay().addDndListener(this.m_dragNDropListener);
/*     */   }
/*     */   
/*     */   public void addChild(IWidget widget) {
/* 110 */     this.m_children.add(widget);
/* 111 */     addWidget(widget);
/*     */   }
/*     */   
/*     */   public void setRenderableChildren(XElement[] elements) {
/* 115 */     this.m_elements = elements;
/*     */   }
/*     */   
/*     */   public void setRenderer(ItemRenderer renderer) {
/* 119 */     if (renderer != this.m_renderer) {
/* 120 */       this.m_renderer = renderer;
/* 121 */       for (IWidget widget : this.m_children) {
/* 122 */         IElement element = Xulor.getInstance().getEnvironment()
/* 123 */           .getElementByWidget(widget);
/* 124 */         if (element != null && element.getParent() != null) {
/* 125 */           element.getParent().removeChild(element);
/*     */         }
/*     */       } 
/* 128 */       removeAllWidgets();
/* 129 */       this.m_children.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<ItemRenderer> getRenderers() {
/* 137 */     return this.m_renderers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderers(ArrayList<ItemRenderer> renderers) {
/* 145 */     this.m_renderers = renderers;
/* 146 */     if (this.m_rendererManager == null) {
/* 147 */       this.m_rendererManager = new ItemRendererManager(this.m_renderers);
/*     */     } else {
/* 149 */       this.m_rendererManager.setRenderers(this.m_renderers);
/*     */     } 
/* 151 */     updateRenderer(false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemRendererManager getRendererManager() {
/* 160 */     return this.m_rendererManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRendererManager(ItemRendererManager manager) {
/* 169 */     if (manager != null) {
/* 170 */       this.m_rendererManager = manager;
/* 171 */       manager.setRenderers(this.m_renderers);
/* 172 */       updateRenderer(false, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fireMouseEntered() {
/* 177 */     mouseEntered(new MouseEnteredEvent((IWidget)this, (IWidget)this));
/*     */   }
/*     */   
/*     */   public void fireMouseExited() {
/* 181 */     mouseExited(new MouseExitedEvent((IWidget)this, (IWidget)this));
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseEntered(MouseEnteredEvent event) {
/* 186 */     super.mouseEntered(event);
/* 187 */     Object value = (this.m_item == null) ? null : this.m_item.getValue();
/* 188 */     ItemOverEvent ioe = new ItemOverEvent(value);
/* 189 */     for (ItemOverListener listener : this.m_itemOverListeners) {
/* 190 */       listener.run(ioe);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseExitedEvent event) {
/* 196 */     super.mouseExited(event);
/* 197 */     Object value = (this.m_item == null) ? null : this.m_item.getValue();
/* 198 */     ItemOutEvent ioe = new ItemOutEvent(value);
/* 199 */     for (ItemOutListener listener : this.m_itemOutListeners) {
/* 200 */       listener.run(ioe);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addItemOverListener(ItemOverListener listener) {
/* 205 */     this.m_itemOverListeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeItemOverListener(ItemOverListener listener) {
/* 209 */     this.m_itemOverListeners.remove(listener);
/*     */   }
/*     */   
/*     */   public void addItemOutListener(ItemOutListener listener) {
/* 213 */     this.m_itemOutListeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeItemOutListener(ItemOutListener listener) {
/* 217 */     this.m_itemOutListeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyItem() {
/* 226 */     if (this.m_renderer != null && this.m_renderer.isCompatible(this.m_item) && 
/* 227 */       this.m_elements != null && this.m_elements.length != 0) {
/* 228 */       this.m_renderer.applyItemValue(this.m_elements, this.m_item);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderableCollection getCollection() {
/* 237 */     return this.m_collection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCollection(RenderableCollection collection) {
/* 245 */     this.m_collection = collection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItem(Item item) {
/* 254 */     setItem(item, true, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItem(Item item, boolean apply, boolean now) {
/* 264 */     boolean applyItem = false;
/* 265 */     if (this.m_item != item) {
/* 266 */       this.m_item = item;
/* 267 */       applyItem = true;
/*     */     } 
/* 269 */     updateRenderer(applyItem, now);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRenderer(boolean applyItem, boolean now) {
/* 274 */     if (this.m_rendererManager == null) {
/*     */       return;
/*     */     }
/* 277 */     if (this.m_rendererManager.assign(this)) {
/* 278 */       render();
/* 279 */       applyItem = true;
/*     */     } 
/* 281 */     if (applyItem) {
/* 282 */       this.m_itemNeedToBeApplied = true;
/* 283 */       if (now) {
/* 284 */         applyItem();
/* 285 */         this.m_itemNeedToBeApplied = false;
/*     */       } 
/*     */     } 
/* 288 */     layout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderableCollection getRenderableCollection() {
/* 297 */     return this.m_collection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemValue(Object value) {
/* 306 */     setItem(new Item(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getItemValue() {
/* 315 */     if (this.m_item != null) {
/* 316 */       return this.m_item.getValue();
/*     */     }
/* 318 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem() {
/* 327 */     return this.m_item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemRenderer getRenderer() {
/* 336 */     return this.m_renderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void layout() {
/* 341 */     if (this.m_needRendering) {
/* 342 */       render();
/* 343 */       this.m_needRendering = false;
/*     */     } 
/* 345 */     super.layout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render() {
/* 354 */     if (this.m_children != null && this.m_children.size() == 0 && this.m_renderer != null) {
/* 355 */       this.m_xElement.removeAllListeners();
/* 356 */       this.m_renderer.render(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void paint(Graphics g) {
/* 362 */     if (this.m_itemNeedToBeApplied) {
/* 363 */       applyItem();
/* 364 */       layout();
/*     */     } 
/* 366 */     this.m_itemNeedToBeApplied = false;
/* 367 */     super.paint(g);
/*     */   }
/*     */   
/*     */   public IWidget getWidget(int x, int y) {
/*     */     IWidget iWidget1;
/* 372 */     if (!getAppearance().insideMargin(x, y)) {
/* 373 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 379 */     IWidget ret = null;
/*     */     
/* 381 */     RenderableContainer renderableContainer = this;
/*     */     
/* 383 */     x -= getAppearance().getLeftMargins();
/* 384 */     y -= getAppearance().getBottomMargins();
/*     */     
/* 386 */     for (IWidget w : this.notifyList) {
/* 387 */       ret = w.getWidget(x - w.getX(), y - w.getY());
/*     */       
/* 389 */       if (ret != null) {
/* 390 */         iWidget1 = ret;
/*     */       }
/*     */     } 
/*     */     
/* 394 */     return iWidget1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDragNDropable getDragNDropable() {
/* 403 */     return this.m_dragNDropable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDragNDropable(IDragNDropable dnd) {
/* 412 */     this.m_dragNDropable = dnd;
/* 413 */     addDragNDropListener();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removedFromWidgetTree() {
/* 418 */     if (getDisplay() != null) {
/* 419 */       getDisplay().removeDndListener(this.m_dragNDropListener);
/* 420 */       this.m_dragNDropListener = null;
/*     */     } 
/* 422 */     super.removedFromWidgetTree();
/* 423 */     Xulor.getInstance().getEnvironment().cleanElementFromRenderableParent(this);
/* 424 */     setParent(null);
/* 425 */     this.m_xElement = null;
/* 426 */     this.m_collection = null;
/* 427 */     this.m_dragNDropable = null;
/* 428 */     this.m_elements = null;
/* 429 */     this.m_item = null;
/* 430 */     this.m_renderer = null;
/* 431 */     this.m_rendererManager = null;
/* 432 */     this.m_renderers = null;
/* 433 */     this.m_itemOutListeners.clear();
/* 434 */     this.m_itemOverListeners.clear();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\RenderableContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */