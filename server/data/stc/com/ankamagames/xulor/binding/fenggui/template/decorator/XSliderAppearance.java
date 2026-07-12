/*    */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*    */ 
/*    */ import com.ankamagames.xulor.binding.fenggui.template.XButton;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*    */ import com.ankamagames.xulor.theme.ThemeElement;
/*    */ import com.ankamagames.xulor.theme.ThemeSliderAppearance;
/*    */ import java.util.ArrayList;
/*    */ import org.fenggui.Button;
/*    */ import org.fenggui.IAppearance;
/*    */ import org.fenggui.Slider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XSliderAppearance
/*    */   extends XDecoratorAppearance
/*    */ {
/*    */   public static final String TAG = "SliderAppearance";
/* 28 */   private ArrayList<ThemeAppearance> m_appearances = new ArrayList();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IAppearance getAppearance()
/*    */   {
/* 35 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void applyAllAttributes() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void buildGUI() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void buildXML() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IElement cloneElementStructure()
/*    */   {
/* 68 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getTag()
/*    */   {
/* 75 */     return "SliderAppearance";
/*    */   }
/*    */   
/*    */   public static void setAppearance(Slider slider, ThemeSliderAppearance theme) {
/* 79 */     if ((slider == null) || (theme == null)) {
/* 80 */       return;
/*    */     }
/*    */     
/* 83 */     ThemeElement buttonApp = theme.getThemeElement("button");
/* 84 */     Button button = slider.getSliderButton();
/* 85 */     XButton.applyButtonTheme(button, buttonApp);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XSliderAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */