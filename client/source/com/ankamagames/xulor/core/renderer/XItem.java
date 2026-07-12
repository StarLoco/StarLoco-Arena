/*     */ package com.ankamagames.xulor.core.renderer;
/*     */ 
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.template.IElement;
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
/*     */ public class XItem
/*     */   extends XElement
/*     */ {
/*     */   public static final String TAG = "Item";
/*  19 */   private String m_field = null;
/*  20 */   private String m_attribute = null;
/*  21 */   private ResultProvider m_resultProvider = null;
/*     */   
/*     */   public void add(IElement element) {
/*  24 */     if (element instanceof ResultProvider) {
/*  25 */       this.m_resultProvider = (ResultProvider)element;
/*     */     } else {
/*  27 */       super.add(element);
/*     */     } 
/*     */   }
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
/*     */   public Object getEncapsulatedObject() {
/*  43 */     return null;
/*     */   }
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
/*     */   public void buildXML() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/*  62 */     return "Item";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getField() {
/*  69 */     return this.m_field;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(String field) {
/*  76 */     this.m_field = field;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttribute() {
/*  83 */     return this.m_attribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(String attribute) {
/*  90 */     this.m_attribute = attribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultProvider getResultProvider() {
/*  97 */     return this.m_resultProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCondition(ResultProvider condition) {
/* 104 */     this.m_resultProvider = condition;
/*     */   }
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 108 */     XItem item = (XItem)element;
/* 109 */     item.m_attribute = this.m_attribute;
/* 110 */     item.m_resultProvider = this.m_resultProvider;
/* 111 */     item.m_field = this.m_field;
/* 112 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 119 */     XItem item = new XItem();
/* 120 */     copyElementData((IElement)item);
/* 121 */     return (IElement)item;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */