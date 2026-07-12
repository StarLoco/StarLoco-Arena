/*    */ package com.ankamagames.framework.kernel.utils;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ExceptionFormatter
/*    */ {
/*    */   public static StringWriter toString(Throwable t)
/*    */   {
/* 26 */     StringWriter sw = new StringWriter();
/*    */     
/* 28 */     if (t == null) {
/* 29 */       return sw;
/*    */     }
/* 31 */     PrintWriter w = new PrintWriter(sw);
/* 32 */     t.printStackTrace(w);
/* 33 */     w.close();
/* 34 */     return sw;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\utils\ExceptionFormatter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */