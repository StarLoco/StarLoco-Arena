/*    */ package org.fenggui.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Timer
/*    */ {
/* 11 */   private long delay = 100L;
/* 12 */   private int numberOfStates = 2;
/* 13 */   private long start = System.currentTimeMillis();
/*    */ 
/*    */   
/*    */   public Timer(int numberOfStates, long delay) {
/* 17 */     this.numberOfStates = numberOfStates;
/* 18 */     this.delay = delay;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getState() {
/* 23 */     long tmp = (System.currentTimeMillis() - this.start) / this.delay;
/*    */     
/* 25 */     return (int)(tmp % this.numberOfStates);
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 30 */     setState(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setState(int state) {
/* 35 */     this.start = System.currentTimeMillis() - state * this.delay;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\Timer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */