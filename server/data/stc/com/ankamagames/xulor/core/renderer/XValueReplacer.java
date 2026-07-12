/*     */ package com.ankamagames.xulor.core.renderer;
/*     */ 
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import java.util.Collection;
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
/*     */ public class XValueReplacer
/*     */   extends XElement
/*     */   implements ResultProvider
/*     */ {
/*     */   public static final String TAG = "ValueReplacer";
/*     */   public static final String SIZE_KEY = "size";
/*     */   protected ResultProviderParent m_parent;
/*  25 */   private String m_key = null;
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
/*     */   public String getKey()
/*     */   {
/*  40 */     return this.m_key;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setKey(String key)
/*     */   {
/*  48 */     this.m_key = key;
/*     */   }
/*     */   
/*     */   public void setResultProviderParent(ResultProviderParent parent) {
/*  52 */     this.m_parent = parent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getEncapsulatedObject()
/*     */   {
/*  60 */     return null;
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
/*     */   public void buildXML() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/*  79 */     return "ValueReplacer";
/*     */   }
/*     */   
/*     */   public Object getResult(Object object)
/*     */   {
/*  84 */     if (this.m_key == null)
/*  85 */       return null;
/*  86 */     if (this.m_key.equalsIgnoreCase("size")) {
/*  87 */       if ((object instanceof Collection))
/*  88 */         return Integer.valueOf(((Collection)object).size());
/*  89 */       if ((object instanceof Object[])) {
/*  90 */         return Integer.valueOf(((Object[])object).length);
/*     */       }
/*  92 */       return Integer.valueOf(0);
/*     */     }
/*  94 */     return null;
/*     */   }
/*     */   
/*     */   public void copyElementData(XValueReplacer replacer) {
/*  98 */     replacer.setKey(this.m_key);
/*  99 */     super.copyElementData(replacer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public XValueReplacer cloneElementStructure()
/*     */   {
/* 107 */     XValueReplacer clone = new XValueReplacer();
/* 108 */     copyElementData(clone);
/* 109 */     return clone;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XValueReplacer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */