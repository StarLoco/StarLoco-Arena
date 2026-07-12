/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XDNDContainer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XRenderableContainer;
/*     */ import com.ankamagames.xulor.core.ElementMap;
/*     */ import com.ankamagames.xulor.core.Factory;
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.core.renderer.ResultProvider;
/*     */ import com.ankamagames.xulor.core.renderer.XItem;
/*     */ import com.ankamagames.xulor.event.IActivationListener;
/*     */ import com.ankamagames.xulor.event.IFocusListener;
/*     */ import com.ankamagames.xulor.event.IKeyPressedListener;
/*     */ import com.ankamagames.xulor.event.IKeyReleasedListener;
/*     */ import com.ankamagames.xulor.event.IMouseClickListener;
/*     */ import com.ankamagames.xulor.event.IMouseDoubleClickListener;
/*     */ import com.ankamagames.xulor.event.IMouseDraggedListener;
/*     */ import com.ankamagames.xulor.event.IMouseEnteredListener;
/*     */ import com.ankamagames.xulor.event.IMouseExitedListener;
/*     */ import com.ankamagames.xulor.event.IMouseMovedListener;
/*     */ import com.ankamagames.xulor.event.IMousePressedListener;
/*     */ import com.ankamagames.xulor.event.IMouseReleasedListener;
/*     */ import com.ankamagames.xulor.event.IMouseWheelListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemClickListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemDoubleClickListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemOutListener;
/*     */ import com.ankamagames.xulor.event.listener.ItemOverListener;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
/*     */ import com.ankamagames.xulor.property.Property;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IDragNDropable;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IItemRenderable;
/*     */ import com.ankamagames.xulor.template.IProperty;
/*     */ import com.ankamagames.xulor.util.Item;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ItemRenderer extends XElement
/*     */ {
/*     */   public static final String TAG = "ItemRenderer";
/*  42 */   private static Logger m_logger = Logger.getLogger(ItemRenderer.class);
/*     */   
/*  44 */   private ArrayList<IElement> m_template = new ArrayList();
/*  45 */   private ArrayList<XItem> m_items = new ArrayList();
/*  46 */   private boolean m_neverRendered = true;
/*  47 */   private IElement m_dragNDropable = null;
/*  48 */   private String m_name = "default";
/*     */   private ResultProvider m_condition;
/*  50 */   private ArrayList<IProperty> m_properties = new ArrayList();
/*     */   
/*  52 */   private ArrayList<IActivationListener> m_al = new ArrayList();
/*  53 */   private ArrayList<IMouseClickListener> m_mcl = new ArrayList();
/*  54 */   private ArrayList<IMouseDoubleClickListener> m_mdcl = new ArrayList();
/*  55 */   private ArrayList<IFocusListener> m_fl = new ArrayList();
/*  56 */   private ArrayList<IKeyPressedListener> m_kpl = new ArrayList();
/*  57 */   private ArrayList<IKeyReleasedListener> m_krl = new ArrayList();
/*  58 */   private ArrayList<IMouseDraggedListener> m_mdl = new ArrayList();
/*  59 */   private ArrayList<IMouseEnteredListener> m_mel = new ArrayList();
/*  60 */   private ArrayList<IMouseExitedListener> m_mexl = new ArrayList();
/*  61 */   private ArrayList<IMouseMovedListener> m_mml = new ArrayList();
/*  62 */   private ArrayList<IMousePressedListener> m_mpl = new ArrayList();
/*  63 */   private ArrayList<IMouseReleasedListener> m_mrl = new ArrayList();
/*  64 */   private ArrayList<IMouseWheelListener> m_mwl = new ArrayList();
/*  65 */   private ArrayList<ItemOutListener> m_iol = new ArrayList();
/*  66 */   private ArrayList<ItemOverListener> m_iovl = new ArrayList();
/*  67 */   private ArrayList<ItemClickListener> m_icl = new ArrayList();
/*  68 */   private ArrayList<ItemDoubleClickListener> m_idcl = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */   public void render(Item item) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  78 */     return this.m_name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  85 */     this.m_name = name;
/*     */   }
/*     */   
/*     */   public void addListeners(XRenderableContainer m) {
/*  89 */     for (IActivationListener l : this.m_al)
/*  90 */       m.setOnActivation(l);
/*  91 */     for (IFocusListener l : this.m_fl)
/*  92 */       m.setOnFocus(l);
/*  93 */     for (IMouseClickListener l : this.m_mcl)
/*  94 */       m.setOnClick(l);
/*  95 */     for (IMouseDoubleClickListener l : this.m_mdcl)
/*  96 */       m.setOnDoubleClick(l);
/*  97 */     for (IKeyPressedListener l : this.m_kpl)
/*  98 */       m.setOnKeyPress(l);
/*  99 */     for (IKeyReleasedListener l : this.m_krl)
/* 100 */       m.setOnKeyRelease(l);
/* 101 */     for (IMouseDraggedListener l : this.m_mdl)
/* 102 */       m.setOnMouseDrag(l);
/* 103 */     for (IMouseEnteredListener l : this.m_mel)
/* 104 */       m.setOnMouseEnter(l);
/* 105 */     for (IMouseExitedListener l : this.m_mexl)
/* 106 */       m.setOnMouseExit(l);
/* 107 */     for (IMouseMovedListener l : this.m_mml)
/* 108 */       m.setOnMouseMove(l);
/* 109 */     for (IMousePressedListener l : this.m_mpl)
/* 110 */       m.setOnMousePress(l);
/* 111 */     for (IMouseReleasedListener l : this.m_mrl)
/* 112 */       m.setOnMouseRelease(l);
/* 113 */     for (IMouseWheelListener l : this.m_mwl)
/* 114 */       m.setOnMouseWheel(l);
/* 115 */     for (ItemOutListener l : this.m_iol)
/* 116 */       m.setOnItemOut(l);
/* 117 */     for (ItemOverListener l : this.m_iovl)
/* 118 */       m.setOnItemOver(l);
/* 119 */     for (ItemClickListener l : this.m_icl)
/* 120 */       m.setOnItemClick(l);
/* 121 */     for (ItemDoubleClickListener l : this.m_idcl) {
/* 122 */       m.setOnItemDoubleClick(l);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnActivation(IActivationListener al)
/*     */   {
/* 131 */     this.m_al.add(al);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnClick(IMouseClickListener l)
/*     */   {
/* 140 */     this.m_mcl.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnDoubleClick(IMouseDoubleClickListener l)
/*     */   {
/* 149 */     this.m_mdcl.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnFocus(IFocusListener fl)
/*     */   {
/* 158 */     this.m_fl.add(fl);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnKeyPress(IKeyPressedListener l)
/*     */   {
/* 167 */     this.m_kpl.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnKeyRelease(IKeyReleasedListener l)
/*     */   {
/* 176 */     this.m_krl.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnMouseDrag(IMouseDraggedListener l)
/*     */   {
/* 185 */     this.m_mdl.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnMouseEnter(IMouseEnteredListener l)
/*     */   {
/* 194 */     this.m_mel.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnMouseExit(IMouseExitedListener l)
/*     */   {
/* 203 */     this.m_mexl.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnMouseMove(IMouseMovedListener l)
/*     */   {
/* 212 */     this.m_mml.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnMousePress(IMousePressedListener l)
/*     */   {
/* 221 */     this.m_mpl.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnMouseRelease(IMouseReleasedListener l)
/*     */   {
/* 230 */     this.m_mrl.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnMouseWheel(IMouseWheelListener l)
/*     */   {
/* 239 */     this.m_mwl.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnItemOut(ItemOutListener l)
/*     */   {
/* 248 */     this.m_iol.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnItemOver(ItemOverListener l)
/*     */   {
/* 257 */     this.m_iovl.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnItemClick(ItemClickListener l)
/*     */   {
/* 266 */     this.m_icl.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setOnItemDoubleClick(ItemDoubleClickListener l)
/*     */   {
/* 275 */     this.m_idcl.add(l);
/*     */   }
/*     */   
/*     */   public void add(IElement element) {
/* 279 */     if ((element instanceof ResultProvider)) {
/* 280 */       this.m_condition = ((ResultProvider)element);
/* 281 */     } else if ((element instanceof IProperty)) {
/* 282 */       this.m_properties.add((IProperty)element);
/*     */     } else {
/* 284 */       this.m_template.add(element);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isRenderableCompatible(IItemRenderable renderable) {
/* 289 */     if (this.m_condition != null) {
/* 290 */       Object value = this.m_condition.getResult(renderable);
/* 291 */       if ((value instanceof Boolean)) {
/* 292 */         return ((Boolean)value).booleanValue();
/*     */       }
/*     */     }
/*     */     
/* 296 */     return true;
/*     */   }
/*     */   
/*     */   public void render(RenderableContainer renderable) {
/* 300 */     ArrayList<XElement> itemElements = new ArrayList();
/* 301 */     this.m_dragNDropable = null;
/* 302 */     com.ankamagames.xulor.template.IObservable renderableElement = renderable.getXRenderableContainer();
/*     */     
/*     */ 
/* 305 */     ElementMap map = new ElementMap(null);
/* 306 */     map.setParentElementMap(this.m_elementMap);
/*     */     
/* 308 */     for (IProperty iProperty : this.m_properties) {
/* 309 */       iProperty.buildProperty();
/* 310 */       Property property = iProperty.getProperty();
/* 311 */       if (property != null)
/*     */       {
/*     */ 
/* 314 */         property.removePropertyClient(renderableElement);
/*     */       }
/* 316 */       iProperty.addPropertyClient(renderableElement);
/*     */     }
/*     */     
/* 319 */     addListeners((XRenderableContainer)renderableElement);
/*     */     
/* 321 */     for (IElement template : this.m_template) {
/* 322 */       IElement element = template.cloneElementStructure();
/* 323 */       element.buildGUI();
/* 324 */       addElementAndItem(element, itemElements, renderable, null, map);
/* 325 */       renderable.addChild((org.fenggui.Widget)element.getEncapsulatedObject());
/*     */     }
/*     */     
/* 328 */     if (this.m_dragNDropable != null) {
/* 329 */       IDragNDropable dnd = (IDragNDropable)this.m_dragNDropable.getEncapsulatedObject();
/* 330 */       renderable.setDragNDropable(dnd);
/*     */     }
/*     */     
/* 333 */     renderable.setRenderableChildren((XElement[])itemElements.toArray(new XElement[0]));
/* 334 */     this.m_neverRendered = false;
/*     */   }
/*     */   
/*     */   private void addElementAndItem(IElement element, ArrayList<XElement> itemElements, RenderableContainer renderable, IDragNDropable dnd, ElementMap map) {
/* 338 */     IDragNDropable dragndrop = dnd;
/*     */     
/* 340 */     element.setElementMap(map);
/* 341 */     if (element.getId() != null) {
/* 342 */       map.add(element.getId(), element);
/*     */     }
/*     */     
/* 345 */     if ((element instanceof XItem)) {
/* 346 */       if (this.m_neverRendered) {
/* 347 */         this.m_items.add((XItem)element);
/*     */       }
/* 349 */       if (element.getParent() != null) {
/* 350 */         itemElements.add((XElement)element.getParent());
/*     */       } else {
/* 352 */         itemElements.add(renderable.getXRenderableContainer());
/*     */       }
/*     */     }
/* 355 */     if ((element instanceof XDNDContainer)) {
/* 356 */       ((XDNDContainer)element).setRenderableParent(renderable);
/* 357 */       this.m_dragNDropable = element;
/* 358 */       dragndrop = (XDNDContainer)element;
/*     */     }
/*     */     
/* 361 */     if (((element instanceof IComponent)) && (dragndrop != null)) {
/* 362 */       ((IComponent)element).setDragAndDropParent(dragndrop);
/*     */     }
/*     */     
/* 365 */     if ((element instanceof IComponent)) {
/* 366 */       element.setRenderableParent(renderable);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/* 369 */     int j = (arrayOfIElement = element.getChildren()).length; for (int i = 0; i < j; i++) { IElement child = arrayOfIElement[i];
/* 370 */       addElementAndItem(child, itemElements, renderable, dragndrop, map);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void applyValue(XElement element, String attribute, Item item, String field, ResultProvider resultProvider)
/*     */   {
/* 382 */     if ((element == null) || (attribute == null)) {
/* 383 */       return;
/*     */     }
/*     */     
/* 386 */     IElement xElement = element;
/* 387 */     if (xElement != null) {
/* 388 */       Factory fac = new com.ankamagames.xulor.core.DefaultFactory(xElement.getClass());
/* 389 */       Method method = fac.guessSetter(attribute);
/*     */       try {
/* 391 */         com.ankamagames.xulor.util.MethodUtil.castInvokeWithItem(method, xElement, item, field, resultProvider);
/*     */       }
/*     */       catch (Exception e) {
/* 394 */         m_logger.error("Erreur à l'invoke method=" + method, e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyItemValue(XElement[] elements, Item item)
/*     */   {
/* 406 */     if ((elements != null) && (this.m_items != null)) {
/* 407 */       for (int i = 0; i < elements.length; i++) {
/* 408 */         if (i >= this.m_items.size()) {
/* 409 */           return;
/*     */         }
/* 411 */         XItem xItem = (XItem)this.m_items.get(i);
/* 412 */         if (xItem != null) {
/* 413 */           if (item != null)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 420 */             applyValue(elements[i], xItem.getAttribute(), item, xItem.getField(), xItem.getResultProvider());
/*     */           }
/*     */           else {
/* 423 */             applyValue(elements[i], xItem.getAttribute(), null, null, xItem.getResultProvider());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCompatible(Item item)
/*     */   {
/* 437 */     if (this.m_items == null) {
/* 438 */       return true;
/*     */     }
/* 440 */     if ((item != null) && ((item.getValue() instanceof FieldProvider))) {
/* 441 */       FieldProvider fieldProvider = (FieldProvider)item.getValue();
/* 442 */       if (fieldProvider == null) {
/* 443 */         return false;
/*     */       }
/* 445 */       String[] fields = fieldProvider.getFields();
/*     */       
/*     */ 
/* 448 */       for (XItem xItem : this.m_items) {
/* 449 */         boolean contains = false;
/* 450 */         String itemFieldName = xItem.getField();
/* 451 */         if (itemFieldName != null) { String[] arrayOfString1;
/* 452 */           int j = (arrayOfString1 = fields).length; for (int i = 0; i < j; i++) { String field = arrayOfString1[i];
/* 453 */             if (itemFieldName.equalsIgnoreCase(field)) {
/* 454 */               contains = true;
/* 455 */               break;
/*     */             }
/*     */           }
/*     */         } else {
/* 459 */           contains = true;
/*     */         }
/* 461 */         if (!contains) {
/* 462 */           return false;
/*     */         }
/*     */       }
/* 465 */       return true;
/*     */     }
/* 467 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getEncapsulatedObject()
/*     */   {
/* 487 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI() {}
/*     */   
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
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 512 */     return "ItemRenderer";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemRenderer cloneElementStructure()
/*     */   {
/* 521 */     ItemRenderer renderer = new ItemRenderer();
/* 522 */     copyElementData(renderer);
/* 523 */     return renderer;
/*     */   }
/*     */   
/*     */   protected void copyElementData(ItemRenderer renderer)
/*     */   {
/* 528 */     for (IActivationListener al : this.m_al)
/* 529 */       renderer.setOnActivation(al);
/* 530 */     for (IMouseClickListener mcl : this.m_mcl)
/* 531 */       renderer.setOnClick(mcl);
/* 532 */     for (IMouseDoubleClickListener mdcl : this.m_mdcl)
/* 533 */       renderer.setOnDoubleClick(mdcl);
/* 534 */     for (IFocusListener fl : this.m_fl)
/* 535 */       renderer.setOnFocus(fl);
/* 536 */     for (IKeyPressedListener kpl : this.m_kpl)
/* 537 */       renderer.setOnKeyPress(kpl);
/* 538 */     for (IKeyReleasedListener krl : this.m_krl)
/* 539 */       renderer.setOnKeyRelease(krl);
/* 540 */     for (IMouseDraggedListener mdl : this.m_mdl)
/* 541 */       renderer.setOnMouseDrag(mdl);
/* 542 */     for (IMouseEnteredListener mel : this.m_mel)
/* 543 */       renderer.setOnMouseEnter(mel);
/* 544 */     for (IMouseExitedListener mexl : this.m_mexl)
/* 545 */       renderer.setOnMouseExit(mexl);
/* 546 */     for (IMousePressedListener mpl : this.m_mpl)
/* 547 */       renderer.setOnMousePress(mpl);
/* 548 */     for (IMouseReleasedListener mrl : this.m_mrl)
/* 549 */       renderer.setOnMouseRelease(mrl);
/* 550 */     for (IMouseWheelListener mwl : this.m_mwl)
/* 551 */       renderer.setOnMouseWheel(mwl);
/* 552 */     for (ItemOutListener iol : this.m_iol)
/* 553 */       renderer.setOnItemOut(iol);
/* 554 */     for (ItemOverListener iovl : this.m_iovl)
/* 555 */       renderer.setOnItemOver(iovl);
/* 556 */     for (ItemClickListener icl : this.m_icl)
/* 557 */       renderer.setOnItemClick(icl);
/* 558 */     for (ItemDoubleClickListener idcl : this.m_idcl) {
/* 559 */       renderer.setOnItemDoubleClick(idcl);
/*     */     }
/* 561 */     for (IElement template : this.m_template)
/* 562 */       renderer.add(template.cloneElementStructure());
/* 563 */     if (this.m_condition != null)
/* 564 */       renderer.m_condition = ((ResultProvider)this.m_condition.cloneElementStructure());
/* 565 */     for (IProperty properties : this.m_properties) {
/* 566 */       renderer.add(properties);
/*     */     }
/* 568 */     super.copyElementData(renderer);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\ItemRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */