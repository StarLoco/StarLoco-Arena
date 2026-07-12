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
/*    */ 
/*    */ public class ThemeTextViewAppearance
/*    */   extends ThemeAppearance
/*    */   implements IFontable
/*    */ {
/*    */   public static final String TAG = "TextViewAppearance";
/* 20 */   private Font m_font = null;
/* 21 */   private Color m_textColor = null;
/*    */   
/*    */   public void add(IThemeElement elem) {
/* 24 */     if ((elem instanceof ThemeColor)) {
/* 25 */       ThemeColor color = (ThemeColor)elem;
/* 26 */       this.m_textColor = color.getColor();
/*    */     }
/* 28 */     super.add(elem);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IThemeElement cloneAppearance()
/*    */   {
/* 36 */     ThemeTextViewAppearance app = new ThemeTextViewAppearance();
/*    */     
/* 38 */     copyAttributes(app);
/*    */     
/* 40 */     return app;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void copyAttributes(ThemeTextViewAppearance app)
/*    */   {
/* 48 */     app.setFont(this.m_font);
/* 49 */     app.setTextColor(this.m_textColor);
/* 50 */     super.copyAttributes(app);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Font getFont()
/*    */   {
/* 57 */     return this.m_font;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setFont(Font font)
/*    */   {
/* 64 */     this.m_font = font;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Color getTextColor()
/*    */   {
/* 71 */     return this.m_textColor;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setTextColor(Color textColor)
/*    */   {
/* 78 */     this.m_textColor = textColor;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeTextViewAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */