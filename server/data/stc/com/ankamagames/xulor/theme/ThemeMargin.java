/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThemeMargin
/*    */   extends ThemeSpacing
/*    */   implements IThemeElement
/*    */ {
/*    */   public static final String TAG = "Margin";
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void add(IThemeElement elem) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IThemeElement cloneAppearance()
/*    */   {
/* 23 */     ThemeMargin margin = new ThemeMargin();
/*    */     
/* 25 */     margin.setSpacing(getSpacing());
/*    */     
/* 27 */     return margin;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeMargin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */