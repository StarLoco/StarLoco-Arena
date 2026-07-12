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
/*    */ public class ThemeRoundedBorder
/*    */   extends ThemeBorder
/*    */   implements IThemeElement
/*    */ {
/*    */   public static final String TAG = "RoundedBorder";
/*    */   private Color m_color;
/*    */   private int m_width;
/*    */   private int m_radius;
/*    */   
/*    */   public void add(IThemeElement elem) {
/* 28 */     if (elem instanceof ThemeColor) {
/* 29 */       this.m_color = ((ThemeColor)elem).getColor();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Color getColor() {
/* 37 */     return this.m_color;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRadius() {
/* 44 */     return this.m_radius;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 51 */     return this.m_width;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRadius(int radius) {
/* 58 */     this.m_radius = radius;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setWidth(int width) {
/* 65 */     this.m_width = width;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setColor(Color color) {
/* 72 */     this.m_color = color;
/*    */   }
/*    */   
/*    */   public IThemeElement cloneAppearance() {
/* 76 */     ThemeRoundedBorder border = new ThemeRoundedBorder();
/*    */     
/* 78 */     border.setColor(this.m_color);
/* 79 */     border.setAsBorderSpacing(this.m_asBorderSpacing);
/* 80 */     border.setRadius(this.m_radius);
/* 81 */     border.setWidth(this.m_width);
/* 82 */     border.setEnabled(this.m_enabled);
/* 83 */     border.setSpacing(this.m_spacing);
/*    */     
/* 85 */     return border;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeRoundedBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */