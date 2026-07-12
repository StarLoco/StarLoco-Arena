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
/*    */ public class ThemePlainBorder
/*    */   extends ThemeBorder
/*    */   implements IThemeElement
/*    */ {
/*    */   public static final String TAG = "PlainBorder";
/*    */   private Color m_color;
/*    */   
/*    */   public void add(IThemeElement elem)
/*    */   {
/* 21 */     if ((elem instanceof ThemeColor)) {
/* 22 */       this.m_color = ((ThemeColor)elem).getColor();
/*    */     }
/*    */   }
/*    */   
/*    */ 
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
/*    */   public IThemeElement cloneAppearance() {
/* 41 */     ThemePlainBorder border = new ThemePlainBorder();
/*    */     
/* 43 */     border.setColor(this.m_color);
/* 44 */     border.setEnabled(this.m_enabled);
/* 45 */     border.setSpacing(this.m_spacing);
/* 46 */     border.setAsBorderSpacing(this.m_asBorderSpacing);
/*    */     
/* 48 */     return border;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemePlainBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */