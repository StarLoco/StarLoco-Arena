/*    */ package org.fenggui.io;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EncodingException
/*    */   extends MalformedElementException
/*    */ {
/*    */   public EncodingException(String message) {
/* 30 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EncodingException(String message, Throwable cause) {
/* 36 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EncodingException(Throwable cause) {
/* 42 */     super(cause);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\io\EncodingException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */