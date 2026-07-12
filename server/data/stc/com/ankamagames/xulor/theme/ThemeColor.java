/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ import com.ankamagames.xulor.util.Color;
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
/*    */ 
/*    */ 
/*    */ public class ThemeColor
/*    */   implements IThemeElement
/*    */ {
/*    */   public static final String TAG = "Color";
/*    */   private Color m_color;
/*    */   
/*    */   public void add(IThemeElement elem) {}
/*    */   
/*    */   public Color getColor()
/*    */   {
/* 30 */     return this.m_color;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setColor(Color color)
/*    */   {
/* 37 */     this.m_color = color;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public IThemeElement cloneAppearance()
/*    */   {
/* 44 */     ThemeColor color = new ThemeColor();
/*    */     
/* 46 */     color.setColor(this.m_color);
/*    */     
/* 48 */     return color;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeColor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */