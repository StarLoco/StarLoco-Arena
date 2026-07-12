/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ import com.ankamagames.xulor.util.Color;
/*    */ import com.ankamagames.xulor.util.Font;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThemeProgressBarAppearance
/*    */   extends ThemeAppearance
/*    */   implements IThemeElement, IFontable
/*    */ {
/*    */   public static final String TAG = "ProgressBarAppearance";
/* 19 */   private Color m_textColor = Color.BLACK;
/* 20 */   private Color m_barColor = Color.BLACK;
/* 21 */   private Font m_font = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Font getFont()
/*    */   {
/* 28 */     return this.m_font;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setFont(Font font)
/*    */   {
/* 36 */     this.m_font = font;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Color getTextColor()
/*    */   {
/* 43 */     return this.m_textColor;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setTextColor(Color textColor)
/*    */   {
/* 50 */     this.m_textColor = textColor;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Color getBarColor()
/*    */   {
/* 58 */     return this.m_barColor;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setBarColor(Color barColor)
/*    */   {
/* 65 */     this.m_barColor = barColor;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void add(IThemeElement elem)
/*    */   {
/* 73 */     if ((elem instanceof ThemeNamedColor)) {
/* 74 */       ThemeNamedColor color = (ThemeNamedColor)elem;
/* 75 */       if (color.getName().equalsIgnoreCase("text")) {
/* 76 */         this.m_textColor = color.getColor();
/* 77 */       } else if (color.getName().equalsIgnoreCase("progressBar")) {
/* 78 */         this.m_barColor = color.getColor();
/*    */       }
/*    */     } else {
/* 81 */       super.add(elem);
/*    */     }
/*    */   }
/*    */   
/*    */   protected void copyAttributes(ThemeProgressBarAppearance app) {
/* 86 */     super.copyAttributes(app);
/* 87 */     app.setFont(this.m_font);
/* 88 */     app.setTextColor(this.m_textColor);
/* 89 */     app.setBarColor(this.m_barColor);
/*    */   }
/*    */   
/*    */   public IThemeElement cloneAppearance() {
/* 93 */     ThemeProgressBarAppearance app = new ThemeProgressBarAppearance();
/*    */     
/* 95 */     copyAttributes(app);
/*    */     
/* 97 */     return app;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeProgressBarAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */