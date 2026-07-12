/*    */ package com.ankamagames.xulor.theme;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ public abstract class ThemeBackground
/*    */   implements IThemeElement
/*    */ {
/*    */   protected boolean m_enabled = true;
/*    */   protected Span m_span;
/*    */   
/*    */   public boolean isEnabled() {
/* 23 */     return this.m_enabled;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setEnabled(boolean enabled) {
/* 29 */     this.m_enabled = enabled;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Span getSpan() {
/* 35 */     return this.m_span;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSpan(Span span) {
/* 41 */     this.m_span = span;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */