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
/*    */ public class ThemeListAppearance
/*    */   extends ThemeCompositeAppearance
/*    */ {
/*    */   public static final String TAG = "ListAppearance";
/* 19 */   private Color m_mouseOverColor = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void add(IThemeElement elem)
/*    */   {
/* 27 */     if ((elem instanceof ThemeColor)) {
/* 28 */       this.m_mouseOverColor = ((ThemeColor)elem).getColor();
/*    */     } else {
/* 30 */       super.add(elem);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Color getMouseOverColor()
/*    */   {
/* 38 */     return this.m_mouseOverColor;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setMouseOverColor(Color mouseOverColor)
/*    */   {
/* 45 */     this.m_mouseOverColor = mouseOverColor;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IThemeElement cloneAppearance()
/*    */   {
/* 53 */     ThemeListAppearance app = new ThemeListAppearance();
/*    */     
/* 55 */     copyAttributes(app);
/*    */     
/* 57 */     return app;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void copyAttributes(ThemeListAppearance app)
/*    */   {
/* 65 */     super.copyAttributes(app);
/* 66 */     app.setMouseOverColor(this.m_mouseOverColor);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeListAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */