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
/*     */ 
/*     */ 
/*     */ public class IllegalNameException
/*     */   extends IllegalArgumentException
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: IllegalNameException.java,v $ $Revision: 1.13 $ $Date: 2004/02/06 09:28:30 $ $Name: jdom_1_0 $";
/*     */   
/*     */   IllegalNameException(String name, String construct, String reason) {
/*  83 */     super(
/*  84 */         "The name \"" + 
/*  85 */         name + 
/*  86 */         "\" is not legal for JDOM/XML " + 
/*  87 */         construct + 
/*  88 */         "s: " + 
/*  89 */         reason + 
/*  90 */         ".");
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
/*     */   
/*     */   IllegalNameException(String name, String construct) {
/* 104 */     super(
/* 105 */         "The name \"" + 
/* 106 */         name + 
/* 107 */         "\" is not legal for JDOM/XML " + 
/* 108 */         construct + 
/* 109 */         "s.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IllegalNameException(String reason) {
/* 119 */     super(reason);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\IllegalNameException.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */