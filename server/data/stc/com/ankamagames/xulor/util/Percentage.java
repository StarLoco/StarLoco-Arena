/*    */ package com.ankamagames.xulor.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Percentage
/*    */ {
/*    */   private double m_value;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Percentage(int value)
/*    */   {
/* 22 */     this.m_value = value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Percentage(double value)
/*    */   {
/* 30 */     this.m_value = value;
/*    */   }
/*    */   
/*    */   public static Percentage valueOf(String value) {
/* 34 */     if (value.charAt(value.length() - 1) != '%') {
/* 35 */       return null;
/*    */     }
/*    */     
/* 38 */     double percent = Double.valueOf(value.substring(0, value.length() - 1)).doubleValue();
/* 39 */     return new Percentage(percent);
/*    */   }
/*    */   
/*    */   public double getValue() {
/* 43 */     return this.m_value;
/*    */   }
/*    */   
/*    */   public void setValue(double value) {
/* 47 */     this.m_value = value;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\Percentage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */