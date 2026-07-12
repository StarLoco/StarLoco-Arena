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
/*    */ public class DataConversionException
/*    */   extends JDOMException
/*    */ {
/*    */   private static final String CVS_ID = "@(#) $RCSfile: DataConversionException.java,v $ $Revision: 1.13 $ $Date: 2004/02/06 09:28:30 $ $Name: jdom_1_0 $";
/*    */   
/*    */   public DataConversionException(String name, String dataType) {
/* 80 */     super(
/* 81 */         "The XML construct " + 
/* 82 */         name + 
/* 83 */         " could not be converted to a " + 
/* 84 */         dataType);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\DataConversionException.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */