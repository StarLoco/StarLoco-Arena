/*    */ package com.ankamagames.xulor.theme;
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
/*    */ 
/*    */ 
/*    */ public class ThemeScrollContainerAppearance
/*    */   extends ThemeCompositeAppearance
/*    */ {
/*    */   public static final String TAG = "ScrollContainerAppearance";
/*    */   
/*    */   public IThemeElement cloneAppearance() {
/* 22 */     ThemeScrollContainerAppearance app = new ThemeScrollContainerAppearance();
/*    */     
/* 24 */     copyAttributes(app);
/*    */     
/* 26 */     return app;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void copyAttributes(ThemeScrollContainerAppearance app) {
/* 34 */     copyAttributes(app);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeScrollContainerAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */