/*     */ package com.ankamagames.xulor.core;
/*     */ 
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ public class ElementMap
/*     */ {
/*     */   public static final String SEPARATOR = ".";
/*     */   private String m_id;
/*     */   private HashMap<String, IElement> m_elements;
/*     */   private String m_propertyNamespace;
/*  28 */   private ElementMap m_parent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementMap(String id) {
/*  36 */     this.m_id = id;
/*  37 */     this.m_elements = new HashMap<String, IElement>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String id, IElement element) {
/*  47 */     if (element != null && id != null) {
/*  48 */       element.setId(id);
/*  49 */       this.m_elements.put(id, element);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement getElement(String id) {
/*  58 */     IElement element = this.m_elements.get(id);
/*  59 */     if (element == null && this.m_parent != null) {
/*  60 */       return this.m_parent.getElement(id);
/*     */     }
/*  62 */     return element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<IElement> getElements() {
/*  69 */     return this.m_elements.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getElementIds() {
/*  76 */     return this.m_elements.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/*  83 */     return this.m_id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsElement(String id) {
/*  91 */     if (id == null) {
/*  92 */       return false;
/*     */     }
/*  94 */     return (getElement(id) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeElement(String id) {
/* 102 */     this.m_elements.remove(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParentElementMap(ElementMap parent) {
/* 110 */     this.m_parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementMap getParentElementMap() {
/* 118 */     return this.m_parent;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\ElementMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */