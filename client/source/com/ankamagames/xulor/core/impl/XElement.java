/*     */ package com.ankamagames.xulor.core.impl;
/*     */ 
/*     */ import com.ankamagames.framework.preferences.PreferencePropertyChangeEvent;
/*     */ import com.ankamagames.framework.preferences.PreferencePropertyChangeListener;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.ElementMap;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.event.FocusManager;
/*     */ import com.ankamagames.xulor.property.Property;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IItemRenderable;
/*     */ import com.ankamagames.xulor.util.ElementAttributes;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XElement
/*     */   implements IElement
/*     */ {
/*  26 */   protected String m_id = null;
/*  27 */   protected short m_modalLevel = -1;
/*     */   
/*     */   protected boolean m_static = true;
/*     */   
/*  31 */   protected ElementMap m_elementMap = null;
/*     */   
/*  33 */   protected IElement m_parent = null;
/*  34 */   protected Vector<IElement> m_children = new Vector<IElement>();
/*  35 */   protected IItemRenderable m_renderableParent = null;
/*  36 */   protected ArrayList<Property> m_properties = new ArrayList<Property>();
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
/*     */   public String getId() {
/*  48 */     return this.m_id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/*  57 */     this.m_id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getModalLevel() {
/*  66 */     return this.m_modalLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModalLevel(short modalLevel) {
/*  75 */     this.m_modalLevel = modalLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IElement childElement) {
/*  84 */     this.m_children.add(childElement);
/*  85 */     childElement.setParent(this);
/*     */   }
/*     */   
/*     */   public void removeSelfFromParent() {
/*  89 */     removeChildren();
/*  90 */     this.m_parent = null;
/*     */     
/*  92 */     FocusManager.getInstance().loseFocus(this, true);
/*     */     
/*  94 */     if (!this.m_static) {
/*  95 */       storePreferences();
/*  96 */       Xulor.getInstance().getPreferenceStore().removePreferencePropertyChangedListener((PreferencePropertyChangeListener)this);
/*     */     } 
/*     */     
/*  99 */     Environment environment = Xulor.getInstance().getEnvironment();
/*     */     
/* 101 */     if (getEncapsulatedObject() != null) {
/* 102 */       environment.removeElementByWidget(getEncapsulatedObject());
/*     */     }
/*     */     
/* 105 */     if (this.m_elementMap != null) {
/* 106 */       if (this.m_id != null) {
/* 107 */         this.m_elementMap.removeElement(this.m_id);
/*     */       }
/* 109 */       environment.removeElementMap(this.m_elementMap.getId());
/*     */     } 
/*     */     
/* 112 */     this.m_elementMap = null;
/* 113 */     this.m_id = null;
/*     */     
/* 115 */     for (Property property : this.m_properties) {
/* 116 */       property.removePropertyClient(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public IElement getChild(IElement element) {
/* 121 */     if (!this.m_children.remove(element)) {
/* 122 */       return null;
/*     */     }
/* 124 */     return element;
/*     */   }
/*     */   
/*     */   public void removeChild(IElement childElement) {
/* 128 */     if (this.m_children != null && this.m_children.contains(childElement)) {
/* 129 */       ((XElement)childElement).removeSelfFromParent();
/*     */     }
/* 131 */     this.m_children.remove(childElement);
/*     */   }
/*     */   
/*     */   public void removeChildren() {
/* 135 */     for (int i = 0; i < this.m_children.size(); i++) {
/* 136 */       ((XElement)this.m_children.get(i)).removeSelfFromParent();
/*     */     }
/* 138 */     this.m_children.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(IElement parent) {
/* 147 */     this.m_parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement getParent() {
/* 156 */     return this.m_parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addWidget(IElement w) {
/* 165 */     if (this.m_parent != null) {
/* 166 */       this.m_parent.addWidget(w);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Object getEncapsulatedObject();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProperty(Property property) {
/* 181 */     this.m_properties.add(property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement[] getChildren() {
/* 190 */     if (this.m_children != null) {
/* 191 */       return this.m_children.<IElement>toArray(new IElement[this.m_children.size()]);
/*     */     }
/* 193 */     return (IElement[])new XElement[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getChildrenCount() {
/* 202 */     if (this.m_children != null)
/* 203 */       return this.m_children.size(); 
/* 204 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementMap(ElementMap tree) {
/* 213 */     this.m_elementMap = tree;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementMap getElementMap() {
/* 222 */     return this.m_elementMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getElementValue() {
/* 237 */     return this;
/*     */   }
/*     */   
/*     */   public void propagateStyle(String style) {
/*     */     byte b;
/*     */     int i;
/*     */     IElement[] arrayOfIElement;
/* 244 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement elem = arrayOfIElement[b];
/* 245 */       if (elem instanceof com.ankamagames.xulor.template.IComponent) {
/* 246 */         elem.propagateStyle(style);
/*     */       }
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   public void setElementAttributes(ElementAttributes attributes) {}
/*     */   
/*     */   public ElementAttributes getElementAttributes() {
/* 255 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PreferencePropertyChangeEvent event) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadPreferences() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void storePreferences() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getStatic() {
/* 283 */     return this.m_static;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatic(boolean static1) {
/* 291 */     this.m_static = static1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void applyAllAttributes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllChildrenAttributes() {
/* 307 */     IElement[] components = getChildren(); byte b; int i; IElement[] arrayOfIElement1;
/* 308 */     for (i = (arrayOfIElement1 = components).length, b = 0; b < i; ) { IElement c = arrayOfIElement1[b];
/* 309 */       c.applyAllAttributes();
/* 310 */       c.applyAllChildrenAttributes();
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 316 */     element.setId(this.m_id);
/* 317 */     element.setElementMap(this.m_elementMap); byte b; int i; IElement[] arrayOfIElement;
/* 318 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement child = arrayOfIElement[b];
/* 319 */       element.add(child.cloneElementStructure());
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IItemRenderable getRenderableParent() {
/* 327 */     return this.m_renderableParent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderableParent(IItemRenderable renderable) {
/* 334 */     if (this.m_renderableParent != renderable) {
/* 335 */       this.m_renderableParent = renderable;
/* 336 */       Xulor.getInstance().getEnvironment().addElementToRenderable(renderable, this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void finalize() {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\impl\XElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */