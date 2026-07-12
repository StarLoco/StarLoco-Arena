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
/*    */ public class ThemeNamedColor
/*    */   extends ThemeColor
/*    */   implements IThemeElement, INamed
/*    */ {
/*    */   public static final String TAG = "NamedColor";
/*    */   private String m_name;
/*    */   
/*    */   public void add(IThemeElement element) {
/* 19 */     if (element instanceof ThemeColor) {
/* 20 */       setColor(((ThemeColor)element).getColor());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 28 */     return this.m_name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setName(String name) {
/* 35 */     this.m_name = name;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeNamedColor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */