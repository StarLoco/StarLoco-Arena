/*    */ package com.ankamagames.framework.kernel.core.common.collections;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectPair<F, S>
/*    */ {
/*    */   private F m_first;
/*    */   
/*    */ 
/*    */ 
/*    */   private S m_second;
/*    */   
/*    */ 
/*    */ 
/*    */   public ObjectPair() {}
/*    */   
/*    */ 
/*    */ 
/*    */   public ObjectPair(F first, S second)
/*    */   {
/* 22 */     this.m_first = first;
/* 23 */     this.m_second = second;
/*    */   }
/*    */   
/*    */   public F getFirst() {
/* 27 */     return (F)this.m_first;
/*    */   }
/*    */   
/*    */   public void setFirst(F first) {
/* 31 */     this.m_first = first;
/*    */   }
/*    */   
/*    */   public S getSecond() {
/* 35 */     return (S)this.m_second;
/*    */   }
/*    */   
/*    */   public void setSecond(S second) {
/* 39 */     this.m_second = second;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\collections\ObjectPair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */