/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThemeWindowAppearance
/*    */   extends ThemeCompositeAppearance
/*    */ {
/*    */   public static final String TAG = "WindowAppearance";
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IThemeElement cloneAppearance()
/*    */   {
/* 22 */     ThemeWindowAppearance app = new ThemeWindowAppearance();
/*    */     
/* 24 */     copyAttributes(app);
/*    */     
/* 26 */     return app;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void copyAttributes(ThemeWindowAppearance app)
/*    */   {
/* 34 */     super.copyAttributes(app);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeWindowAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */