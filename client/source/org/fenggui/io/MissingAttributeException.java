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
/*    */ public class MissingAttributeException
/*    */   extends IOStreamException
/*    */ {
/*    */   public MissingAttributeException(String message) {
/* 30 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MissingAttributeException(String message, Throwable cause) {
/* 36 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MissingAttributeException(Throwable cause) {
/* 42 */     super(cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MissingAttributeException createDefault(String attribute, String parsingContext) {
/* 53 */     return new MissingAttributeException("required attribute: " + attribute + "\n\n" + parsingContext);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\io\MissingAttributeException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */