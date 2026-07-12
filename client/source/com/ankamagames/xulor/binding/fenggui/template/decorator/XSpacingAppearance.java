/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XComponent;
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.util.Spacing;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.SpacingAppearance;
/*     */ import org.fenggui.StandardWidget;
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
/*     */ public abstract class XSpacingAppearance
/*     */   extends XElement
/*     */ {
/*  29 */   protected String m_state = "default";
/*     */   
/*  31 */   private Spacing m_margin = Spacing.ZERO_SPACING;
/*  32 */   private Spacing m_border = Spacing.ZERO_SPACING;
/*  33 */   private Spacing m_padding = Spacing.ZERO_SPACING;
/*     */   
/*     */   public void setMargin(Spacing margin) {
/*  36 */     this.m_margin = margin;
/*     */     
/*  38 */     if (this.m_parent == null || !(this.m_parent.getEncapsulatedObject() instanceof StandardWidget)) {
/*     */       return;
/*     */     }
/*  41 */     Object obj = this.m_parent.getEncapsulatedObject();
/*  42 */     SpacingAppearance app = (SpacingAppearance)((StandardWidget)obj).getAppearance();
/*  43 */     app.setMargin(FengguiConstant.toFengguiSpacing(margin));
/*     */   }
/*     */   
/*     */   public void setBorder(Spacing border) {
/*  47 */     this.m_border = border;
/*     */     
/*  49 */     if (this.m_parent == null || !(this.m_parent.getEncapsulatedObject() instanceof StandardWidget)) {
/*     */       return;
/*     */     }
/*  52 */     Object obj = this.m_parent.getEncapsulatedObject();
/*  53 */     SpacingAppearance app = (SpacingAppearance)((StandardWidget)obj).getAppearance();
/*  54 */     app.setBorder(FengguiConstant.toFengguiSpacing(border));
/*     */   }
/*     */   
/*     */   public void setPadding(Spacing padding) {
/*  58 */     this.m_padding = padding;
/*     */     
/*  60 */     if (this.m_parent == null || !(this.m_parent.getEncapsulatedObject() instanceof StandardWidget)) {
/*     */       return;
/*     */     }
/*  63 */     Object obj = this.m_parent.getEncapsulatedObject();
/*  64 */     SpacingAppearance app = (SpacingAppearance)((StandardWidget)obj).getAppearance();
/*  65 */     app.setPadding(FengguiConstant.toFengguiSpacing(padding));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Spacing getBorder() {
/*  73 */     return this.m_border;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Spacing getMargin() {
/*  80 */     return this.m_margin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Spacing getPadding() {
/*  87 */     return this.m_padding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getState() {
/*  94 */     return this.m_state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(String state) {
/* 101 */     this.m_state = state;
/*     */   }
/*     */   
/*     */   public void applySpacingAttributes() {
/* 105 */     if (this.m_parent instanceof XComponent) {
/* 106 */       ThemeElement element = ((XComponent)this.m_parent).getThemeElement();
/* 107 */       if (element == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 112 */       if (element != null) {
/* 113 */         if (this.m_margin != null) element.setMargin(this.m_margin); 
/* 114 */         if (this.m_padding != null) element.setPadding(this.m_padding);
/*     */       
/*     */       } 
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEncapsulatedObject() {
/* 135 */     return getAppearance();
/*     */   }
/*     */   
/*     */   public abstract IAppearance getAppearance();
/*     */   
/*     */   public static void setAppearance(StandardWidget widget, ThemeElement theme) {
/* 141 */     if (theme == null || widget == null) {
/*     */       return;
/*     */     }
/* 144 */     SpacingAppearance app = (SpacingAppearance)widget.getAppearance();
/*     */     
/* 146 */     if (theme.getPadding() != null) {
/* 147 */       app.setPadding(FengguiConstant.toFengguiSpacing(theme.getPadding()));
/*     */     } else {
/* 149 */       app.setPadding(FengguiConstant.toFengguiSpacing(Spacing.ZERO_SPACING));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     app.setBorder(FengguiConstant.toFengguiSpacing(Spacing.ZERO_SPACING));
/*     */ 
/*     */     
/* 158 */     if (theme.getMargin() != null) {
/* 159 */       app.setMargin(FengguiConstant.toFengguiSpacing(theme.getMargin()));
/*     */     } else {
/* 161 */       app.setMargin(FengguiConstant.toFengguiSpacing(Spacing.ZERO_SPACING));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 170 */     XSpacingAppearance elem = (XSpacingAppearance)element;
/* 171 */     elem.setBorder(this.m_border);
/* 172 */     elem.setMargin(this.m_margin);
/* 173 */     elem.setPadding(this.m_padding);
/* 174 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */   protected void copyThemeAppearanceAttributes(ThemeAppearance app) {
/* 178 */     app.setState(this.m_state);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XSpacingAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */