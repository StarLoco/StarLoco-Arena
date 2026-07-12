/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThemeScrollBarAppearance
/*    */   extends ThemeCompositeAppearance
/*    */ {
/*    */   public static final String TAG = "ScrollBarAppearance";
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IThemeElement cloneAppearance()
/*    */   {
/* 22 */     ThemeScrollBarAppearance app = new ThemeScrollBarAppearance();
/*    */     
/* 24 */     copyAttributes(app);
/*    */     
/* 26 */     return app;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void copyAttributes(ThemeScrollBarAppearance app)
/*    */   {
/* 34 */     super.copyAttributes(app);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeScrollBarAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */