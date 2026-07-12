/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.ItemRenderer;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.ItemRendererManager;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.RenderableContainer;
/*     */ import com.ankamagames.xulor.event.ItemClickEvent;
/*     */ import com.ankamagames.xulor.event.ItemDoubleClickEvent;
/*     */ import com.ankamagames.xulor.event.MouseReleasedEvent;
/*     */ import com.ankamagames.xulor.event.listener.ItemClickListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemDoubleClickListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemOutListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemOverListener;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IObservable;
/*     */ import com.ankamagames.xulor.util.Item;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XRenderableContainer
/*     */   extends XContainer
/*     */   implements IObservable
/*     */ {
/*     */   public static final String TAG = "RenderableContainer";
/*     */   public static final String SHORT_TAG = "RC";
/*  39 */   private RenderableContainer m_renderableContainer = null;
/*     */   
/*  41 */   private Item m_item = null;
/*  42 */   private Object m_itemValue = null;
/*  43 */   private ArrayList<ItemRenderer> m_renderers = new ArrayList<ItemRenderer>();
/*  44 */   private ItemRendererManager m_rendererManager = null;
/*     */   
/*     */   private boolean m_draggable;
/*     */   private Vector<ItemOutListener> m_iol;
/*     */   private Vector<ItemOverListener> m_iovl;
/*     */   private Vector<ItemClickListener> m_icl;
/*     */   private Vector<ItemDoubleClickListener> m_idcl;
/*     */   
/*     */   public XRenderableContainer() {
/*  53 */     this(false);
/*     */   }
/*     */   
/*     */   public XRenderableContainer(boolean draggable) {
/*  57 */     this.m_draggable = draggable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllListeners() {
/*  67 */     super.removeAllListeners();
/*  68 */     if (this.m_iol != null)
/*  69 */       this.m_iol.clear(); 
/*  70 */     if (this.m_iovl != null)
/*  71 */       this.m_iovl.clear(); 
/*  72 */     if (this.m_icl != null)
/*  73 */       this.m_icl.clear(); 
/*  74 */     if (this.m_idcl != null) {
/*  75 */       this.m_idcl.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  84 */     if (this.m_renderableContainer == null) {
/*  85 */       this.m_renderableContainer = new RenderableContainer();
/*     */       
/*  87 */       this.m_renderableContainer.setXRenderableContainer(this);
/*  88 */       this.m_renderableContainer.setRenderers(this.m_renderers);
/*     */       
/*  90 */       applyAllAttributes();
/*     */       
/*  92 */       if (this.m_parent != null) {
/*  93 */         this.m_parent.addWidget((IElement)this);
/*     */       }
/*  95 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_container, (IElement)this);
/*     */       
/*  97 */       if (this.m_iol != null) {
/*  98 */         for (ItemOutListener l : this.m_iol) {
/*  99 */           this.m_renderableContainer.addItemOutListener(l);
/*     */         }
/* 101 */         this.m_iol.clear();
/*     */       } 
/* 103 */       if (this.m_iovl != null) {
/* 104 */         for (ItemOverListener l : this.m_iovl) {
/* 105 */           this.m_renderableContainer.addItemOverListener(l);
/*     */         }
/* 107 */         this.m_iovl.clear();
/*     */       } 
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/* 111 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/* 112 */       c.buildGUI();
/*     */       b++; }
/*     */     
/* 115 */     applyTheme();
/*     */   }
/*     */   
/*     */   public void applyAllAttributes() {
/* 119 */     if (this.m_renderableContainer != null) {
/* 120 */       if (this.m_item != null) {
/* 121 */         this.m_renderableContainer.setItem(this.m_item);
/* 122 */       } else if (this.m_itemValue != null) {
/* 123 */         this.m_renderableContainer.setItemValue(this.m_itemValue);
/*     */       } 
/* 125 */       if (this.m_renderableContainer instanceof com.ankamagames.xulor.binding.fenggui.component.Container && 
/* 126 */         this.m_wishedSize != null) {
/* 127 */         this.m_renderableContainer.setWishedSize(this.m_wishedSize);
/*     */       }
/* 129 */       super.applyAllAttributes();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyTheme() {
/* 135 */     if (this.m_themeNeedToBeApplied) {
/* 136 */       this.m_themeNeedToBeApplied = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem() {
/* 147 */     return this.m_item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContent(Object value) {
/* 156 */     Item item = null;
/* 157 */     if (value instanceof Item) {
/* 158 */       item = (Item)value;
/* 159 */     } else if (value != null) {
/* 160 */       item = new Item(value);
/*     */     } 
/* 162 */     this.m_item = item;
/* 163 */     if (this.m_renderableContainer != null) {
/* 164 */       this.m_renderableContainer.setItem(item);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContentValue(Object value) {
/* 174 */     this.m_itemValue = value;
/* 175 */     if (this.m_renderableContainer != null) {
/* 176 */       this.m_renderableContainer.setItemValue(value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getItemValue() {
/* 186 */     if (this.m_renderableContainer != null) {
/* 187 */       return this.m_renderableContainer.getItemValue();
/*     */     }
/* 189 */     return null;
/*     */   }
/*     */   
/*     */   public void addRenderer(ItemRenderer renderer) {
/* 193 */     this.m_renderers.add(renderer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemRenderer getRenderer() {
/* 200 */     if (this.m_renderableContainer != null) {
/* 201 */       this.m_renderableContainer.getRenderer();
/*     */     }
/*     */     
/* 204 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemRendererManager getRendererManager() {
/* 211 */     return this.m_rendererManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRendererManager(ItemRendererManager rendererManager) {
/* 218 */     this.m_rendererManager = rendererManager;
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
/*     */   public String getTag() {
/* 230 */     return "RenderableContainer";
/*     */   }
/*     */   
/*     */   public Widget getWidget() {
/* 234 */     return (Widget)this.m_renderableContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IElement element) {
/* 243 */     if (element instanceof ItemRenderer) {
/* 244 */       addRenderer((ItemRenderer)element);
/*     */     } else {
/* 246 */       super.add(element);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement getElement() {
/* 256 */     if (this.m_children != null) {
/* 257 */       return this.m_children.firstElement();
/*     */     }
/* 259 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElement(IElement element) {
/* 268 */     add(element);
/*     */   }
/*     */   
/*     */   public void setOnItemOver(ItemOverListener l) {
/* 272 */     if (this.m_renderableContainer == null) {
/* 273 */       if (this.m_iovl == null)
/* 274 */         this.m_iovl = new Vector<ItemOverListener>(); 
/* 275 */       this.m_iovl.add(l);
/*     */     } else {
/* 277 */       this.m_renderableContainer.addItemOverListener(l);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setOnItemOut(ItemOutListener l) {
/* 282 */     if (this.m_renderableContainer == null) {
/* 283 */       if (this.m_iol == null)
/* 284 */         this.m_iol = new Vector<ItemOutListener>(); 
/* 285 */       this.m_iol.add(l);
/*     */     } else {
/* 287 */       this.m_renderableContainer.addItemOutListener(l);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setOnItemClick(ItemClickListener l) {
/* 292 */     if (this.m_icl == null)
/* 293 */       this.m_icl = new Vector<ItemClickListener>(); 
/* 294 */     this.m_icl.add(l);
/*     */   }
/*     */   
/*     */   public void setOnItemDoubleClick(ItemDoubleClickListener l) {
/* 298 */     if (this.m_idcl == null)
/* 299 */       this.m_idcl = new Vector<ItemDoubleClickListener>(); 
/* 300 */     this.m_idcl.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doubleClick(MouseReleasedEvent event) {
/* 310 */     if (this.m_idcl != null) {
/* 311 */       ItemDoubleClickEvent idce = new ItemDoubleClickEvent(getItemValue(), event.getButton());
/* 312 */       for (ItemDoubleClickListener l : this.m_idcl) {
/* 313 */         l.run(idce);
/*     */       }
/*     */     } 
/* 316 */     super.doubleClick(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDoubleClickListener() {
/* 326 */     if (this.m_idcl != null && this.m_idcl.size() > 0) {
/* 327 */       return true;
/*     */     }
/* 329 */     return super.hasDoubleClickListener();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void simpleClick(MouseReleasedEvent event) {
/* 339 */     if (this.m_icl != null) {
/* 340 */       ItemClickEvent ice = new ItemClickEvent(getItemValue(), event.getButton());
/* 341 */       for (ItemClickListener l : this.m_icl) {
/* 342 */         l.run(ice);
/*     */       }
/*     */     } 
/* 345 */     super.simpleClick(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSelfFromParent() {
/* 355 */     if (this.m_idcl != null)
/* 356 */       this.m_idcl.clear(); 
/* 357 */     if (this.m_iol != null)
/* 358 */       this.m_iol.clear(); 
/* 359 */     if (this.m_iovl != null)
/* 360 */       this.m_iovl.clear(); 
/* 361 */     if (this.m_icl != null)
/* 362 */       this.m_icl.clear(); 
/* 363 */     super.removeSelfFromParent();
/*     */   }
/*     */   
/*     */   protected void copyElementData(XRenderableContainer container) {
/* 367 */     container.setContent(this.m_item);
/* 368 */     container.setContentValue(this.m_itemValue);
/* 369 */     if (this.m_iol != null) {
/* 370 */       for (ItemOutListener iol : this.m_iol) {
/* 371 */         container.setOnItemOut(iol);
/*     */       }
/*     */     }
/* 374 */     if (this.m_iovl != null) {
/* 375 */       for (ItemOverListener iovl : this.m_iovl) {
/* 376 */         container.setOnItemOver(iovl);
/*     */       }
/*     */     }
/* 379 */     if (this.m_icl != null) {
/* 380 */       for (ItemClickListener icl : this.m_icl) {
/* 381 */         container.setOnItemClick(icl);
/*     */       }
/*     */     }
/* 384 */     if (this.m_idcl != null) {
/* 385 */       for (ItemDoubleClickListener idcl : this.m_idcl) {
/* 386 */         container.setOnItemDoubleClick(idcl);
/*     */       }
/*     */     }
/* 389 */     for (ItemRenderer renderer : this.m_renderers) {
/* 390 */       container.addRenderer(renderer.cloneElementStructure());
/*     */     }
/* 392 */     copyElementData(container);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 401 */     XRenderableContainer container = new XRenderableContainer();
/* 402 */     copyElementData(container);
/* 403 */     return (IElement)container;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XRenderableContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */