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
/*    */ public class ThemePadding
/*    */   extends ThemeSpacing
/*    */   implements IThemeElement
/*    */ {
/*    */   public static final String TAG = "Padding";
/*    */   
/*    */   public void add(IThemeElement elem) {}
/*    */   
/*    */   public IThemeElement cloneAppearance() {
/* 23 */     ThemePadding padding = new ThemePadding();
/*    */     
/* 25 */     padding.setSpacing(getSpacing());
/*    */     
/* 27 */     return padding;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemePadding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */