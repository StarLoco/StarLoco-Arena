/*    */ package org.jdom;
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
/*    */ public class IllegalTargetException
/*    */   extends IllegalArgumentException
/*    */ {
/*    */   private static final String CVS_ID = "@(#) $RCSfile: IllegalTargetException.java,v $ $Revision: 1.14 $ $Date: 2004/02/06 09:28:30 $ $Name: jdom_1_0 $";
/*    */   
/*    */   IllegalTargetException(String target, String reason) {
/* 80 */     super(
/* 81 */         "The target \"" + 
/* 82 */         target + 
/* 83 */         "\" is not legal for JDOM/XML Processing Instructions: " + 
/* 84 */         reason + 
/* 85 */         ".");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IllegalTargetException(String reason) {
/* 95 */     super(reason);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\IllegalTargetException.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */