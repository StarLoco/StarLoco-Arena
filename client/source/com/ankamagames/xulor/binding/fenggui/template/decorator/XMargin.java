/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.util.Spacing;
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
/*     */ public class XMargin
/*     */   extends XElement
/*     */ {
/*     */   public static final String TAG = "Margin";
/*  21 */   private Spacing m_spacing = Spacing.ZERO_SPACING;
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
/*     */   
/*     */   public void buildGUI() {
/*  40 */     if (!(this.m_parent instanceof XSpacingAppearance)) {
/*     */       return;
/*     */     }
/*     */     
/*  44 */     ((XSpacingAppearance)this.m_parent).setMargin(this.m_spacing);
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
/*     */   public void buildXML() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Spacing getSpacing() {
/*  66 */     return this.m_spacing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpacing(Spacing spacing) {
/*  74 */     this.m_spacing = spacing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEncapsulatedObject() {
/*  82 */     return this.m_spacing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/*  90 */     return "Margin";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/*  98 */     XMargin margin = (XMargin)element;
/*  99 */     margin.m_spacing = this.m_spacing;
/* 100 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 107 */     XMargin margin = new XMargin();
/* 108 */     copyElementData((IElement)margin);
/* 109 */     return (IElement)margin;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XMargin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */