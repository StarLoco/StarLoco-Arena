/*    */ package com.ankamagames.xulor.binding.fenggui.component;
/*    */ 
/*    */ import org.fenggui.IWidget;
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
/*    */   extends org.fenggui.text.TextView
/*    */   implements NonBlocking
/*    */ {
/* 17 */   private boolean m_nonBlocking = true;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isNonBlocking()
/*    */   {
/* 24 */     return this.m_nonBlocking;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setNonBlocking(boolean nonBlocking)
/*    */   {
/* 32 */     this.m_nonBlocking = nonBlocking;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IWidget getWidget(int x, int y)
/*    */   {
/* 42 */     if (this.m_nonBlocking) {
/* 43 */       return null;
/*    */     }
/* 45 */     return super.getWidget(x, y);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\TextView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */