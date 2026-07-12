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
/*     */ public class XPadding
/*     */   extends XElement
/*     */ {
/*     */   public static final String TAG = "Padding";
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
/*     */   public void buildGUI()
/*     */   {
/*  40 */     if (!(this.m_parent instanceof XSpacingAppearance)) {
/*  41 */       return;
/*     */     }
/*     */     
/*  44 */     ((XSpacingAppearance)this.m_parent).setPadding(this.m_spacing);
/*     */   }
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
/*     */   public Spacing getSpacing()
/*     */   {
/*  61 */     return this.m_spacing;
/*     */   }
/*     */   
/*     */   public void setSpacing(Spacing spacing) {
/*  65 */     this.m_spacing = spacing;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getEncapsulatedObject()
/*     */   {
/*  73 */     return this.m_spacing;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/*  81 */     return "Padding";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/*  89 */     XPadding padding = (XPadding)element;
/*  90 */     padding.m_spacing = this.m_spacing;
/*  91 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/*  98 */     XPadding padding = new XPadding();
/*  99 */     copyElementData(padding);
/* 100 */     return padding;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XPadding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */