/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Image;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.PopupMenu;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Separator;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XButton;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XComponent;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XImage;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XLabel;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemePopupMenuAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeWindowAppearance;
/*     */ import org.fenggui.Button;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.Label;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XPopupMenuAppearance
/*     */   extends XDecoratorAppearance
/*     */ {
/*     */   public static final String TAG = "PopupMenuAppearance";
/*     */   
/*     */   public IAppearance getAppearance()
/*     */   {
/*  44 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  52 */     if ((this.m_parent instanceof XComponent)) {
/*  53 */       ThemeElement element = ((XComponent)this.m_parent).getThemeElement();
/*  54 */       if (element == null) {
/*  55 */         return;
/*     */       }
/*  57 */       ThemeAppearance theme = element.getThemeAppearance(this.m_state);
/*     */       
/*  59 */       if (theme == null) {
/*  60 */         theme = new ThemeWindowAppearance();
/*  61 */         theme.setState(this.m_state);
/*  62 */         element.addThemeAppearance(theme);
/*     */       }
/*     */       
/*  65 */       applySpacingAttributes();
/*  66 */       applyDecoratorAttributes();
/*     */       
/*  68 */       ((IComponent)this.m_parent).applyTheme();
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
/*  84 */     for (IElement c : this.m_children) {
/*  85 */       c.buildGUI();
/*     */     }
/*     */     
/*  88 */     applyAllAttributes();
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
/* 103 */     return "PopupMenuAppearance";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 111 */     XPopupMenuAppearance elem = (XPopupMenuAppearance)element;
/* 112 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 119 */     XPopupMenuAppearance elem = new XPopupMenuAppearance();
/* 120 */     copyElementData(elem);
/* 121 */     return elem;
/*     */   }
/*     */   
/*     */   protected void copyThemeAppearanceAttributes(ThemeWindowAppearance app) {
/* 125 */     super.copyThemeAppearanceAttributes(app);
/*     */   }
/*     */   
/*     */   public ThemeAppearance toThemeAppearance()
/*     */   {
/* 130 */     ThemeWindowAppearance app = new ThemeWindowAppearance();
/* 131 */     copyThemeAppearanceAttributes(app);
/* 132 */     return app;
/*     */   }
/*     */   
/*     */   public static void setAppearance(PopupMenu popupMenu, ThemePopupMenuAppearance theme) {
/* 136 */     if ((popupMenu == null) || (theme == null)) {
/* 137 */       return;
/*     */     }
/*     */     
/* 140 */     ThemeElement buttonElement = theme.getThemeElement("button");
/* 141 */     ThemeElement labelElement = theme.getThemeElement("label");
/* 142 */     ThemeElement separatorElement = theme.getThemeElement("separator");
/*     */     
/* 144 */     for (StandardWidget widget : popupMenu.getWidgets()) {
/* 145 */       if (((widget instanceof Label)) && (labelElement != null)) {
/* 146 */         XLabel.applyLabelTheme((Label)widget, labelElement);
/* 147 */       } else if (((widget instanceof Button)) && (buttonElement != null)) {
/* 148 */         XButton.applyButtonTheme((Button)widget, buttonElement);
/* 149 */       } else if (((widget instanceof Separator)) && (separatorElement != null)) {
/* 150 */         XImage.applyImageTheme((Image)widget, separatorElement);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XPopupMenuAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */