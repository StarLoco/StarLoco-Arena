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
/*    */ public class ThemePlainBackground
/*    */   extends ThemeBackground
/*    */   implements IThemeElement
/*    */ {
/*    */   public static final String TAG = "PlainBackground";
/*    */   private Color m_color;
/*    */   
/*    */   public void add(IThemeElement elem)
/*    */   {
/* 24 */     if ((elem instanceof ThemeColor)) {
/* 25 */       this.m_color = ((ThemeColor)elem).getColor();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Color getColor()
/*    */   {
/* 33 */     return this.m_color;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setColor(Color color)
/*    */   {
/* 40 */     this.m_color = color;
/*    */   }
/*    */   
/*    */   public IThemeElement cloneAppearance() {
/* 44 */     ThemePlainBackground bg = new ThemePlainBackground();
/*    */     
/* 46 */     bg.setColor(this.m_color);
/* 47 */     bg.setEnabled(this.m_enabled);
/*    */     
/* 49 */     return bg;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemePlainBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */