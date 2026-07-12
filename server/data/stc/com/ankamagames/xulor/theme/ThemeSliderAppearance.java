/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThemeSliderAppearance
/*    */   extends ThemeCompositeAppearance
/*    */ {
/*    */   public static final String TAG = "SliderAppearance";
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IThemeElement cloneAppearance()
/*    */   {
/* 22 */     ThemeSliderAppearance app = new ThemeSliderAppearance();
/*    */     
/* 24 */     copyAttributes(app);
/*    */     
/* 26 */     return app;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void copyAttributes(ThemeSliderAppearance app)
/*    */   {
/* 34 */     super.copyAttributes(app);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeSliderAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */