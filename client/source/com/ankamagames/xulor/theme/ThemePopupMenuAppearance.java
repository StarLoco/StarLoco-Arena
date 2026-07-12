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
/*    */ public class ThemePopupMenuAppearance
/*    */   extends ThemeCompositeAppearance
/*    */ {
/*    */   public static final String TAG = "PopupMenuAppearance";
/*    */   
/*    */   public IThemeElement cloneAppearance() {
/* 22 */     ThemePopupMenuAppearance app = new ThemePopupMenuAppearance();
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
/*    */   protected void copyAttributes(ThemePopupMenuAppearance app) {
/* 34 */     copyAttributes(app);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemePopupMenuAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */