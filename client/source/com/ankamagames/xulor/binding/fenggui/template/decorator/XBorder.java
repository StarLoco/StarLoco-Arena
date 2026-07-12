/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.decorator.IDecorator;
/*     */ import com.ankamagames.xulor.theme.IThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeBorder;
/*     */ import com.ankamagames.xulor.util.Spacing;
/*     */ import org.fenggui.border.Border;
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
/*     */ public abstract class XBorder
/*     */   extends XElement
/*     */   implements IDecorator
/*     */ {
/*  25 */   protected String m_state = "default";
/*     */   
/*     */   protected boolean m_enabled = true;
/*     */   protected boolean m_asBorderSpacing = true;
/*  29 */   protected Spacing m_spacing = new Spacing(0, 0, 0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(String text) {
/*  36 */     this.m_state = text;
/*     */   }
/*     */   
/*     */   public String getState() {
/*  40 */     return this.m_state;
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  44 */     this.m_enabled = enabled;
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/*  48 */     return this.m_enabled;
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
/*     */   public void setSpacing(Spacing spacing) {
/*  60 */     this.m_spacing = spacing;
/*     */   }
/*     */   
/*     */   public Spacing getSpacing() {
/*  64 */     return this.m_spacing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAsBorderSpacing() {
/*  75 */     return this.m_asBorderSpacing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAsBorderSpacing(boolean asBorderSpacing) {
/*  82 */     this.m_asBorderSpacing = asBorderSpacing;
/*     */   }
/*     */   
/*     */   public void applyBorderAttributes() {
/*  86 */     Border border = getBorder();
/*  87 */     if (border != null) {
/*  88 */       border.setEnabled(this.m_enabled);
/*     */       
/*  90 */       border.setLabel(this.m_state);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Border getBorder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEncapsulatedObject() {
/* 106 */     return getBorder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ThemeBorder toThemeBorder();
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 116 */     XBorder elem = (XBorder)element;
/* 117 */     elem.setAsBorderSpacing(this.m_asBorderSpacing);
/* 118 */     elem.setEnabled(this.m_enabled);
/* 119 */     elem.setSpacing(this.m_spacing);
/* 120 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */   public IThemeElement toThemeElement() {
/* 124 */     return (IThemeElement)toThemeBorder();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */