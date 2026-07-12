/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Image;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Separator;
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
/*     */   private boolean m_horizontalInit = false;
/*     */   
/*     */   public void buildGUI() {
/*  31 */     if (this.m_separator == null) {
/*  32 */       this.m_separator = new Separator();
/*     */       
/*  34 */       applyAllAttributes();
/*     */       
/*  36 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this); 
/*  37 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_separator, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  40 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement component = arrayOfIElement[b];
/*  41 */       component.buildGUI(); b++; }
/*     */     
/*  43 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Separator getWidget() {
/*  53 */     return this.m_separator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
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
/*     */   public void buildXML() {
/*     */     byte b;
/*     */     int i;
/*     */     IElement[] arrayOfIElement;
/*  87 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement component = arrayOfIElement[b];
/*  88 */       component.buildXML();
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/*  99 */     return "separator";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 108 */     XSeparator elem = (XSeparator)element;
/* 109 */     if (this.m_horizontalInit) elem.setHorizontal(this.m_horizontal); 
/* 110 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 119 */     XSeparator elem = new XSeparator();
/* 120 */     copyElementData((IElement)elem);
/* 121 */     return (IElement)elem;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XSeparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */