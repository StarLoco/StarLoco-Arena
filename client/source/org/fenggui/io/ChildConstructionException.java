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
/*    */ 
/*    */ 
/*    */ public class ChildConstructionException
/*    */   extends IOStreamException
/*    */ {
/*    */   public ChildConstructionException(String message) {
/* 35 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ChildConstructionException(String message, Throwable cause) {
/* 41 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ChildConstructionException(Throwable cause) {
/* 47 */     super(cause);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ChildConstructionException createMultipleDefinitionsException(String name, String parsingContext) {
/* 53 */     return new ChildConstructionException("multiple definitionsfor the element " + name + "\n\n" + parsingContext);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ChildConstructionException createMultipleDefinitionsException(Iterable<?> names, String parsingContext, String nameList) {
/* 59 */     return createMultipleDefinitionsException(parsingContext, nameList);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\io\ChildConstructionException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */