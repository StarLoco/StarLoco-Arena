/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XComponent;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeProgressBarAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeWindowAppearance;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.ProgressBar;
/*     */ import org.fenggui.ProgressBarAppearance;
/*     */ import org.fenggui.render.Font;
/*     */ import org.fenggui.util.Color;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XProgressBarAppearance
/*     */   extends XDecoratorAppearance
/*     */ {
/*     */   public static final String TAG = "ProgressBarAppearance";
/*     */   
/*     */   public IAppearance getAppearance()
/*     */   {
/*  43 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  51 */     if ((this.m_parent instanceof XComponent)) {
/*  52 */       ThemeElement element = ((XComponent)this.m_parent).getThemeElement();
/*  53 */       if (element == null) {
/*  54 */         return;
/*     */       }
/*  56 */       ThemeAppearance theme = element.getThemeAppearance(this.m_state);
/*     */       
/*  58 */       if (theme == null) {
/*  59 */         theme = new ThemeWindowAppearance();
/*  60 */         theme.setState(this.m_state);
/*  61 */         element.addThemeAppearance(theme);
/*     */       }
/*     */       
/*  64 */       applySpacingAttributes();
/*  65 */       applyDecoratorAttributes();
/*     */       
/*  67 */       ((IComponent)this.m_parent).applyTheme();
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
/*     */   public void buildGUI()
/*     */   {
/*  83 */     for (IElement c : this.m_children) {
/*  84 */       c.buildGUI();
/*     */     }
/*     */     
/*  87 */     applyAllAttributes();
/*     */   }
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
/*     */   public String getTag()
/*     */   {
/* 102 */     return "ProgressBarAppearance";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 110 */     XProgressBarAppearance elem = (XProgressBarAppearance)element;
/* 111 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 118 */     XProgressBarAppearance elem = new XProgressBarAppearance();
/* 119 */     copyElementData(elem);
/* 120 */     return elem;
/*     */   }
/*     */   
/*     */   protected void copyThemeAppearanceAttributes(ThemeWindowAppearance app) {
/* 124 */     super.copyThemeAppearanceAttributes(app);
/*     */   }
/*     */   
/*     */   public ThemeAppearance toThemeAppearance()
/*     */   {
/* 129 */     ThemeWindowAppearance app = new ThemeWindowAppearance();
/* 130 */     copyThemeAppearanceAttributes(app);
/* 131 */     return app;
/*     */   }
/*     */   
/*     */   public static void setAppearance(ProgressBar progressBar, ThemeProgressBarAppearance theme) {
/* 135 */     if ((progressBar == null) || (theme == null)) {
/* 136 */       return;
/*     */     }
/*     */     
/* 139 */     ProgressBarAppearance app = progressBar.getAppearance();
/*     */     
/* 141 */     Color color = FengguiConstant.toFengguiColor(theme.getBarColor());
/* 142 */     if (color != null) {
/* 143 */       app.setProgressBarColor(color);
/*     */     }
/*     */     
/* 146 */     color = FengguiConstant.toFengguiColor(theme.getTextColor());
/* 147 */     if (color != null) {
/* 148 */       app.setTextColor(color);
/*     */     }
/*     */     
/* 151 */     Font font = FengguiConstant.toFengguiFont(theme.getFont());
/* 152 */     if (font != null) {
/* 153 */       app.setFont(font);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XProgressBarAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */