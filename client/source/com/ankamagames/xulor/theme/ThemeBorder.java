/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ import com.ankamagames.xulor.util.Spacing;
/*    */ import com.ankamagames.xulor.util.Span;
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
/*    */ public abstract class ThemeBorder
/*    */   implements IThemeElement
/*    */ {
/*    */   protected boolean m_enabled = true;
/*    */   protected boolean m_asBorderSpacing = true;
/* 20 */   protected Span m_span = Span.BORDER;
/* 21 */   protected Spacing m_spacing = new Spacing(1, 1, 1, 1);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAsBorderSpacing() {
/* 27 */     return this.m_asBorderSpacing;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAsBorderSpacing(boolean asBorderSpacing) {
/* 33 */     this.m_asBorderSpacing = asBorderSpacing;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/* 39 */     return this.m_enabled;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setEnabled(boolean enabled) {
/* 45 */     this.m_enabled = enabled;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Spacing getSpacing() {
/* 51 */     return this.m_spacing;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSpacing(Spacing spacing) {
/* 57 */     this.m_spacing = spacing;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Span getSpan() {
/* 63 */     return this.m_span;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSpan(Span span) {
/* 69 */     this.m_span = span;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */