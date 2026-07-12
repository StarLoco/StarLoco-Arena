/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.decorator.IDecorator;
/*     */ import com.ankamagames.xulor.theme.IThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeBackground;
/*     */ import com.ankamagames.xulor.theme.ThemeBevelBorder;
/*     */ import com.ankamagames.xulor.theme.ThemeBorder;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeGradientBackground;
/*     */ import com.ankamagames.xulor.theme.ThemePixmapBackground;
/*     */ import com.ankamagames.xulor.theme.ThemePixmapBorder;
/*     */ import com.ankamagames.xulor.theme.ThemePixmapBorder16;
/*     */ import com.ankamagames.xulor.theme.ThemePlainBackground;
/*     */ import com.ankamagames.xulor.theme.ThemePlainBorder;
/*     */ import com.ankamagames.xulor.theme.ThemeRoundedBorder;
/*     */ import com.ankamagames.xulor.theme.ThemeTitledBorder;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.DecoratorAppearance;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.background.Background;
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
/*     */ 
/*     */ public abstract class XDecoratorAppearance
/*     */   extends XSpacingAppearance
/*     */ {
/*     */   public static final String FENGGUI_DEFAULT_STATE = "default";
/*     */   private boolean m_IsEnabled;
/*  42 */   private ArrayList<IDecorator> m_decorators = new ArrayList<IDecorator>();
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  45 */     this.m_IsEnabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBorder(XBorder border) {
/*  54 */     this.m_decorators.add(border);
/*     */     
/*  56 */     if (this.m_parent == null || !(this.m_parent.getEncapsulatedObject() instanceof StandardWidget)) {
/*     */       return;
/*     */     }
/*  59 */     Object obj = this.m_parent.getEncapsulatedObject();
/*  60 */     DecoratorAppearance app = (DecoratorAppearance)((StandardWidget)obj).getAppearance();
/*     */     
/*  62 */     Border b = (Border)border.getEncapsulatedObject();
/*  63 */     app.add(border.getState(), b, border.isAsBorderSpacing());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBackground(XBackground bg) {
/*  68 */     this.m_decorators.add(bg);
/*     */ 
/*     */     
/*  71 */     if (this.m_parent == null || !(this.m_parent.getEncapsulatedObject() instanceof StandardWidget)) {
/*     */       return;
/*     */     }
/*  74 */     Object obj = this.m_parent.getEncapsulatedObject();
/*  75 */     DecoratorAppearance app = (DecoratorAppearance)((StandardWidget)obj).getAppearance();
/*  76 */     Background b = (Background)bg.getEncapsulatedObject();
/*     */     
/*  78 */     app.add(bg.getState(), b, b.getSpan());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/*  87 */     XDecoratorAppearance elem = (XDecoratorAppearance)element;
/*  88 */     elem.m_decorators = (ArrayList<IDecorator>)elem.m_decorators.clone();
/*  89 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyDecoratorAttributes() {
/*  94 */     if (this.m_parent instanceof XComponent) {
/*  95 */       ThemeElement element = ((XComponent)this.m_parent).getThemeElement();
/*  96 */       if (element == null) {
/*     */         return;
/*     */       }
/*  99 */       ThemeAppearance theme = element.getThemeAppearance(this.m_state);
/*     */       
/* 101 */       if (theme != null) {
/* 102 */         for (IDecorator decorator : this.m_decorators) {
/* 103 */           if (decorator instanceof XBorder) {
/* 104 */             theme.add((IThemeElement)((XBorder)decorator).toThemeBorder()); continue;
/* 105 */           }  if (decorator instanceof XBackground) {
/* 106 */             theme.add((IThemeElement)((XBackground)decorator).toThemeBackground());
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setAppearance(StandardWidget widget, ThemeAppearance theme) {
/* 114 */     DecoratorAppearance app = (DecoratorAppearance)widget.getAppearance();
/*     */     
/* 116 */     for (IThemeElement element : theme.getDecorators()) {
/* 117 */       if (element instanceof ThemeBackground) {
/* 118 */         Background background = getBackground((ThemeBackground)element);
/* 119 */         if (background != null)
/* 120 */           app.add(theme.getState(), background);  continue;
/*     */       } 
/* 122 */       if (element instanceof ThemeBorder) {
/* 123 */         Border border = getBorder((ThemeBorder)element);
/* 124 */         if (border != null) {
/* 125 */           app.add(theme.getState(), border);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Background getBackground(ThemeBackground theme) {
/* 133 */     if (theme instanceof ThemeGradientBackground)
/* 134 */       return (Background)XGradientBackground.getGradientBackground((ThemeGradientBackground)theme); 
/* 135 */     if (theme instanceof ThemePlainBackground)
/* 136 */       return (Background)XPlainBackground.getPlainBackground((ThemePlainBackground)theme); 
/* 137 */     if (theme instanceof ThemePixmapBackground) {
/* 138 */       return (Background)XPixmapBackground.getPixmapBackground((ThemePixmapBackground)theme);
/*     */     }
/* 140 */     return null;
/*     */   }
/*     */   
/*     */   private static Border getBorder(ThemeBorder theme) {
/* 144 */     if (theme instanceof ThemeBevelBorder)
/* 145 */       return (Border)XBevelBorder.getBevelBorder((ThemeBevelBorder)theme); 
/* 146 */     if (theme instanceof ThemePixmapBorder)
/* 147 */       return (Border)XPixmapBorder.getPixmapBorder((ThemePixmapBorder)theme); 
/* 148 */     if (theme instanceof ThemePixmapBorder16)
/* 149 */       return (Border)XPixmapBorder16.getPixmapBorder16((ThemePixmapBorder16)theme); 
/* 150 */     if (theme instanceof ThemePlainBorder)
/* 151 */       return (Border)XPlainBorder.getPlainBorder((ThemePlainBorder)theme); 
/* 152 */     if (theme instanceof ThemeRoundedBorder)
/* 153 */       return (Border)XRoundedBorder.getRoundedBorder((ThemeRoundedBorder)theme); 
/* 154 */     if (theme instanceof ThemeTitledBorder) {
/* 155 */       return (Border)XTitledBorder.getTitledBorder((ThemeTitledBorder)theme);
/*     */     }
/* 157 */     return null;
/*     */   }
/*     */   
/*     */   protected void copyThemeAppearanceAttributes(ThemeAppearance app) {
/* 161 */     super.copyThemeAppearanceAttributes(app);
/* 162 */     for (IDecorator decorator : this.m_decorators) {
/* 163 */       app.add(decorator.toThemeElement());
/*     */     }
/*     */   }
/*     */   
/*     */   public ThemeAppearance toThemeAppearance() {
/* 168 */     ThemeAppearance app = new ThemeAppearance();
/* 169 */     copyThemeAppearanceAttributes(app);
/* 170 */     return app;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XDecoratorAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */