/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ import com.ankamagames.xulor.util.Pixmap;
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
/*    */ public class ThemeComboBoxAppearance
/*    */   extends ThemeCompositeAppearance
/*    */ {
/*    */   public static final String TAG = "ComboBoxAppearance";
/* 19 */   private Pixmap m_pixmap = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(IThemeElement elem) {
/* 26 */     if (elem instanceof ThemePixmap) {
/* 27 */       this.m_pixmap = ((ThemePixmap)elem).getPixmap();
/*    */     } else {
/* 29 */       super.add(elem);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Pixmap getPixmap() {
/* 37 */     return this.m_pixmap;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPixmap(Pixmap pixmap) {
/* 44 */     this.m_pixmap = pixmap;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IThemeElement cloneAppearance() {
/* 52 */     ThemeComboBoxAppearance app = new ThemeComboBoxAppearance();
/*    */     
/* 54 */     copyAttributes(app);
/*    */     
/* 56 */     return app;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void copyAttributes(ThemeComboBoxAppearance app) {
/* 64 */     copyAttributes(app);
/* 65 */     app.setPixmap(this.m_pixmap);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeComboBoxAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */