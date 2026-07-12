/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ import com.ankamagames.xulor.util.Spacing;
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
/*    */ public abstract class ThemeSpacing
/*    */   implements IThemeElement
/*    */ {
/*    */   private Spacing m_spacing;
/*    */   
/*    */   public Spacing getSpacing()
/*    */   {
/* 25 */     return this.m_spacing;
/*    */   }
/*    */   
/*    */   public void setSpacing(Spacing spacing) {
/* 29 */     this.m_spacing = spacing;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeSpacing.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */