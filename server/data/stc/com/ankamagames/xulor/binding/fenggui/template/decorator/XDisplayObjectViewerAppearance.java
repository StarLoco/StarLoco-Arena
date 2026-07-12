/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.BaseDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.DescriptorLibraryManager;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.ModifiableDescriptorLibrary;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.DisplayObjectViewer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XComponent;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeDisplayObjectViewerAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeLabelAppearance;
/*     */ import org.fenggui.IAppearance;
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
/*     */ public class XDisplayObjectViewerAppearance
/*     */   extends XDecoratorAppearance
/*     */ {
/*     */   public static final String TAG = "displayObjectViewerAppearance";
/*  34 */   private IAppearance m_displayObjectViewerAppearance = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  41 */     if ((this.m_parent instanceof XComponent)) {
/*  42 */       ThemeElement element = ((XComponent)this.m_parent).getThemeElement();
/*  43 */       if (element == null) {
/*  44 */         return;
/*     */       }
/*  46 */       ThemeAppearance theme = element.getThemeAppearance(this.m_state);
/*     */       
/*  48 */       if (theme == null) {
/*  49 */         theme = new ThemeDisplayObjectViewerAppearance();
/*  50 */         theme.setState(this.m_state);
/*  51 */         element.addThemeAppearance(theme);
/*     */       }
/*     */       
/*  54 */       applySpacingAttributes();
/*  55 */       applyDecoratorAttributes();
/*     */       
/*  57 */       if ((theme instanceof ThemeDisplayObjectViewerAppearance)) {
/*  58 */         ThemeLabelAppearance localThemeLabelAppearance = (ThemeLabelAppearance)theme;
/*     */       }
/*     */       
/*     */ 
/*  62 */       ((IComponent)this.m_parent).applyTheme();
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
/*     */   public void buildGUI()
/*     */   {
/*  82 */     for (IElement c : this.m_children) {
/*  83 */       c.buildGUI();
/*     */     }
/*     */     
/*  86 */     applyAllAttributes();
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
/*     */   public IAppearance getAppearance()
/*     */   {
/* 102 */     return this.m_displayObjectViewerAppearance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 110 */     return "displayObjectViewerAppearance";
/*     */   }
/*     */   
/*     */   public static void setAppearance(StandardWidget widget, ThemeDisplayObjectViewerAppearance theme) {
/* 114 */     if (!(widget instanceof DisplayObjectViewer)) {
/* 115 */       return;
/*     */     }
/*     */     
/* 118 */     DisplayObjectViewer w = (DisplayObjectViewer)widget;
/*     */     
/*     */ 
/* 121 */     String linkage = theme.getLinkage();
/* 122 */     if (linkage != null) w.setLinkage(linkage);
/* 123 */     if (theme.isScaleInit()) w.setScale(theme.getScale());
/* 124 */     if (theme.isXOffsetInit()) w.setXOffset(theme.getXOffset());
/* 125 */     if (theme.isYOffsetInit()) { w.setXOffset(theme.getYOffset());
/*     */     }
/* 127 */     if (theme.getDescriptorLibrary() != null) {
/* 128 */       BaseDescriptorLibrary bds = DescriptorLibraryManager.getInstance().getDescriptorLibrary(theme.getDescriptorLibrary());
/* 129 */       w.setDescriptorLibrary(new ModifiableDescriptorLibrary(bds));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 138 */     XDisplayObjectViewerAppearance elem = (XDisplayObjectViewerAppearance)element;
/* 139 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 146 */     XDisplayObjectViewerAppearance elem = new XDisplayObjectViewerAppearance();
/* 147 */     copyElementData(elem);
/* 148 */     return elem;
/*     */   }
/*     */   
/*     */   protected void copyThemeAppearanceAttributes(ThemeDisplayObjectViewerAppearance app) {
/* 152 */     super.copyThemeAppearanceAttributes(app);
/*     */   }
/*     */   
/*     */   public ThemeAppearance toThemeAppearance() {
/* 156 */     ThemeDisplayObjectViewerAppearance app = new ThemeDisplayObjectViewerAppearance();
/* 157 */     copyThemeAppearanceAttributes(app);
/* 158 */     return app;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XDisplayObjectViewerAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */