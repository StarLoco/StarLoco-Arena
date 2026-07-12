/*     */ package org.jdom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IllegalDataException
/*     */   extends IllegalArgumentException
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: IllegalDataException.java,v $ $Revision: 1.13 $ $Date: 2004/02/06 09:28:30 $ $Name: jdom_1_0 $";
/*     */   
/*     */   IllegalDataException(String data, String construct, String reason) {
/*  81 */     super(
/*  82 */         "The data \"" + 
/*  83 */         data + 
/*  84 */         "\" is not legal for a JDOM " + 
/*  85 */         construct + 
/*  86 */         ": " + 
/*  87 */         reason + 
/*  88 */         ".");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   IllegalDataException(String data, String construct) {
/* 101 */     super(
/* 102 */         "The data \"" + 
/* 103 */         data + 
/* 104 */         "\" is not legal for a JDOM " + 
/* 105 */         construct + 
/* 106 */         ".");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IllegalDataException(String reason) {
/* 116 */     super(reason);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\IllegalDataException.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */