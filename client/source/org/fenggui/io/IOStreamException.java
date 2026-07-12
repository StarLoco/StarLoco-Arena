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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IOStreamException
/*    */   extends Exception
/*    */ {
/*    */   public IOStreamException(String message) {
/* 33 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IOStreamException(String message, Throwable cause) {
/* 39 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IOStreamException(Throwable cause) {
/* 45 */     super(cause);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\io\IOStreamException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */