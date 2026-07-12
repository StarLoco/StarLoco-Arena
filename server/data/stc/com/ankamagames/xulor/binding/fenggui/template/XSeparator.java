/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Separator;
/*     */ import com.ankamagames.xulor.core.Environment;
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
/*     */ public class XSeparator
/*     */   extends XImage
/*     */ {
/*     */   public static final String TAG = "separator";
/*     */   private Separator m_separator;
/*     */   private boolean m_horizontal;
/*  22 */   private boolean m_horizontalInit = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  31 */     if (this.m_separator == null) {
/*  32 */       this.m_separator = new Separator();
/*     */       
/*  34 */       applyAllAttributes();
/*     */       
/*  36 */       if (this.m_parent != null) this.m_parent.addWidget(this);
/*  37 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_separator, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  40 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement component = arrayOfIElement[i];
/*  41 */       component.buildGUI();
/*     */     }
/*  43 */     applyTheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Separator getWidget()
/*     */   {
/*  53 */     return this.m_separator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  63 */     if (this.m_separator != null) {
/*  64 */       if (this.m_horizontalInit) this.m_separator.setHorizontal(this.m_horizontal);
/*  65 */       super.applyAllAttributes();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isHorizontal() {
/*  70 */     return this.m_horizontal;
/*     */   }
/*     */   
/*     */   public void setHorizontal(boolean horizontal) {
/*  74 */     this.m_horizontal = horizontal;
/*  75 */     this.m_horizontalInit = true;
/*  76 */     if (this.m_separator != null) {
/*  77 */       this.m_separator.setHorizontal(horizontal);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*     */     IElement[] arrayOfIElement;
/*     */     
/*  87 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement component = arrayOfIElement[i];
/*  88 */       component.buildXML();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/*  99 */     return "separator";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 108 */     XSeparator elem = (XSeparator)element;
/* 109 */     if (this.m_horizontalInit) elem.setHorizontal(this.m_horizontal);
/* 110 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 119 */     XSeparator elem = new XSeparator();
/* 120 */     copyElementData(elem);
/* 121 */     return elem;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XSeparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */