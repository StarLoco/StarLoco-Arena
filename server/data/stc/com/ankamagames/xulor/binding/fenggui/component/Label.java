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
/*    */ 
/*    */ public class Label
/*    */   extends org.fenggui.Label
/*    */   implements NonBlocking
/*    */ {
/* 18 */   private boolean m_nonBlocking = false;
/*    */   
/*    */   public IWidget getWidget(int x, int y)
/*    */   {
/* 22 */     if (this.m_nonBlocking) {
/* 23 */       return null;
/*    */     }
/* 25 */     return super.getWidget(x, y);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isNonBlocking()
/*    */   {
/* 34 */     return this.m_nonBlocking;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setNonBlocking(boolean nonBlocking)
/*    */   {
/* 41 */     this.m_nonBlocking = nonBlocking;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\Label.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */