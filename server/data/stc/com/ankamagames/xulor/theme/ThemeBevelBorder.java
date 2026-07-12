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
/*    */ public class ThemeBevelBorder
/*    */   extends ThemeBorder
/*    */   implements IThemeElement
/*    */ {
/*    */   public static final String TAG = "BevelBorder";
/*    */   private Color m_elevated;
/*    */   private Color m_lowered;
/*    */   
/*    */   public void add(IThemeElement elem)
/*    */   {
/* 24 */     if ((elem instanceof ThemeNamedColor)) {
/* 25 */       ThemeNamedColor tnc = (ThemeNamedColor)elem;
/* 26 */       if (tnc.getName().equalsIgnoreCase("elevated")) {
/* 27 */         this.m_elevated = tnc.getColor();
/* 28 */       } else if (tnc.getName().equalsIgnoreCase("lowered")) {
/* 29 */         this.m_lowered = tnc.getColor();
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Color getElevated()
/*    */   {
/* 38 */     return this.m_elevated;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Color getLowered()
/*    */   {
/* 45 */     return this.m_lowered;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setElevated(Color elevated)
/*    */   {
/* 52 */     this.m_elevated = elevated;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setLowered(Color lowered)
/*    */   {
/* 59 */     this.m_lowered = lowered;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public IThemeElement cloneAppearance()
/*    */   {
/* 66 */     ThemeBevelBorder border = new ThemeBevelBorder();
/* 67 */     border.setAsBorderSpacing(this.m_asBorderSpacing);
/* 68 */     border.setElevated(this.m_elevated);
/* 69 */     border.setEnabled(this.m_enabled);
/* 70 */     border.setLowered(this.m_lowered);
/* 71 */     border.setSpacing(this.m_spacing);
/*    */     
/* 73 */     return border;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeBevelBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */