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
/*    */ public class MalformedElementException
/*    */   extends IOStreamException
/*    */ {
/*    */   public MalformedElementException(String message) {
/* 30 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MalformedElementException(String message, Throwable cause) {
/* 36 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MalformedElementException(Throwable cause) {
/* 42 */     super(cause);
/*    */   }
/*    */ 
/*    */   
/*    */   public static MalformedElementException createDefaultMalformedAttributeException(String name, String content) throws MalformedElementException {
/* 47 */     throw new MalformedElementException("Malformed attribute " + name + ", " + content);
/*    */   }
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
/*    */   public static MalformedElementException createDefault(String name, String valueFormatDescrption, String parsingContext) {
/* 60 */     return new MalformedElementException("the element " + name + " should be " + valueFormatDescrption + "\n\n" + parsingContext);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\io\MalformedElementException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */