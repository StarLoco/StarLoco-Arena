/*    */ package com.ankamagames.xulor.binding.fenggui.component;
/*    */ 
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.text.TextView;
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
/*    */ public class TextView
/*    */   extends TextView
/*    */   implements NonBlocking
/*    */ {
/*    */   private boolean m_nonBlocking = true;
/*    */   
/*    */   public boolean isNonBlocking() {
/* 24 */     return this.m_nonBlocking;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setNonBlocking(boolean nonBlocking) {
/* 32 */     this.m_nonBlocking = nonBlocking;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IWidget getWidget(int x, int y) {
/* 42 */     if (this.m_nonBlocking) {
/* 43 */       return null;
/*    */     }
/* 45 */     return super.getWidget(x, y);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\TextView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */