/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ import com.ankamagames.xulor.util.Alignment;
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
/*    */ 
/*    */ 
/*    */ public class ThemeComboListAppearance
/*    */   extends ThemeAppearance
/*    */   implements IThemeElement, IFontable
/*    */ {
/*    */   public static final String TAG = "ComboListAppearance";
/* 23 */   private Color m_color = null;
/* 24 */   private Font m_font = null;
/* 25 */   private Alignment m_alignment = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(IThemeElement elem) {
/* 31 */     if (elem instanceof ThemeColor) {
/* 32 */       this.m_color = ((ThemeColor)elem).getColor();
/*    */     }
/* 34 */     super.add(elem);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Font getFont() {
/* 41 */     return this.m_font;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFont(Font font) {
/* 48 */     this.m_font = font;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Color getColor() {
/* 55 */     return this.m_color;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setColor(Color color) {
/* 62 */     this.m_color = color;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Alignment getAlignment() {
/* 69 */     return this.m_alignment;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAlignment(Alignment alignment) {
/* 76 */     this.m_alignment = alignment;
/*    */   }
/*    */   
/*    */   protected void copyAttributes(ThemeComboListAppearance app) {
/* 80 */     copyAttributes(app);
/* 81 */     app.setFont(this.m_font);
/* 82 */     app.setColor(this.m_color);
/* 83 */     app.setAlignment(this.m_alignment);
/*    */   }
/*    */   
/*    */   public IThemeElement cloneAppearance() {
/* 87 */     ThemeComboListAppearance app = new ThemeComboListAppearance();
/*    */     
/* 89 */     copyAttributes(app);
/*    */     
/* 91 */     return app;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeComboListAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */