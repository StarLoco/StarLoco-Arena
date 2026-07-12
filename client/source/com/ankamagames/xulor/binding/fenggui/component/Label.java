/*    */ package com.ankamagames.xulor.binding.fenggui.component;
/*    */ 
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.Label;
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
/*    */   extends Label
/*    */   implements NonBlocking
/*    */ {
/*    */   private boolean m_nonBlocking = false;
/*    */   
/*    */   public IWidget getWidget(int x, int y) {
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
/*    */   
/*    */   public boolean isNonBlocking() {
/* 34 */     return this.m_nonBlocking;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setNonBlocking(boolean nonBlocking) {
/* 41 */     this.m_nonBlocking = nonBlocking;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\Label.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */