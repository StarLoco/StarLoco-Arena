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
/*    */ public class MissingElementException
/*    */   extends IOStreamException
/*    */ {
/*    */   public MissingElementException(String message) {
/* 16 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MissingElementException(String message, Throwable cause) {
/* 22 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MissingElementException(Throwable cause) {
/* 28 */     super(cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MissingElementException createDefault(Iterable<?> names, String parsingContext, String nameList) {
/* 39 */     String namesStr = nameList;
/* 40 */     return new MissingElementException("required child element: " + namesStr + "\n\n" + parsingContext);
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
/*    */   public static MalformedElementException createDefault(String name, String valueFormatDescrption, String parsingContext) {
/* 52 */     return new MalformedElementException("the attribute " + name + " should be " + valueFormatDescrption + "\n\n" + parsingContext);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\io\MissingElementException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */