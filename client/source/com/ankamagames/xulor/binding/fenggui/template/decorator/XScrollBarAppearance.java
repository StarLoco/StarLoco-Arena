/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XButton;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XComponent;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XSlider;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeScrollBarAppearance;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Button;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.ScrollBar;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.Widget;
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
/*     */ public class XScrollBarAppearance
/*     */   extends XDecoratorAppearance
/*     */ {
/*     */   public static final String TAG = "ScrollBarAppearance";
/*  30 */   private ArrayList<ThemeAppearance> m_appearances = new ArrayList<ThemeAppearance>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IAppearance getAppearance() {
/*  37 */     return null;
/*     */   }
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
/*     */   public void buildGUI() {}
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
/*     */   public IElement cloneElementStructure() {
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/*  77 */     return "ScrollBarAppearance";
/*     */   }
/*     */   
/*     */   public static void setAppearance(ScrollBar scrollBar, ThemeScrollBarAppearance theme) {
/*  81 */     if (theme == null || scrollBar == null) {
/*     */       return;
/*     */     }
/*     */     
/*  85 */     ThemeElement incApp = theme.getThemeElement("increaseButton");
/*  86 */     Button button = scrollBar.getIncreaseButton();
/*  87 */     button.setText("");
/*  88 */     button.updateMinSize();
/*  89 */     XButton.applyButtonTheme(button, incApp);
/*     */     
/*  91 */     ThemeElement decApp = theme.getThemeElement("decreaseButton");
/*  92 */     button = scrollBar.getDecreaseButton();
/*  93 */     button.setText("");
/*  94 */     button.updateMinSize();
/*  95 */     XButton.applyButtonTheme(button, decApp);
/*     */     
/*  97 */     if (scrollBar.isHorizontal()) {
/*  98 */       ThemeElement sliderElement = theme.getThemeElement("horizontalSlider");
/*  99 */       XSlider.applySliderTheme(scrollBar.getSlider(), sliderElement);
/*     */     } else {
/* 101 */       ThemeElement sliderElement = theme.getThemeElement("verticalSlider");
/* 102 */       XSlider.applySliderTheme(scrollBar.getSlider(), sliderElement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void applyScrollBarTheme(ScrollBar scrollBar, ThemeElement element) {
/* 107 */     if (scrollBar == null || element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     scrollBar.getAppearance().removeAll();
/* 112 */     XComponent.applyThemeAttributes((Widget)scrollBar, element.getAttributes());
/* 113 */     XSpacingAppearance.setAppearance((StandardWidget)scrollBar, element);
/* 114 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 115 */     for (ThemeAppearance app : appearances) {
/* 116 */       if (app != null) {
/* 117 */         XDecoratorAppearance.setAppearance((StandardWidget)scrollBar, app);
/* 118 */         if (app instanceof ThemeScrollBarAppearance)
/* 119 */           setAppearance(scrollBar, (ThemeScrollBarAppearance)app); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XScrollBarAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */