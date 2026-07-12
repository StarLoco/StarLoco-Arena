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
/*     */ public class CDATA
/*     */   extends Text
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: CDATA.java,v $ $Revision: 1.30 $ $Date: 2004/02/27 11:32:57 $ $Name: jdom_1_0 $";
/*     */   
/*     */   protected CDATA() {}
/*     */   
/*     */   public CDATA(String str) {
/*  95 */     setText(str);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Text setText(String str) {
/* 115 */     if (str == null) {
/* 116 */       this.value = "";
/* 117 */       return this;
/*     */     } 
/*     */     String reason;
/* 120 */     if ((reason = Verifier.checkCDATASection(str)) != null) {
/* 121 */       throw new IllegalDataException(str, "CDATA section", reason);
/*     */     }
/* 123 */     this.value = str;
/* 124 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(String str) {
/* 144 */     if (str == null)
/*     */       return; 
/*     */     String reason;
/* 147 */     if ((reason = Verifier.checkCDATASection(str)) != null) {
/* 148 */       throw new IllegalDataException(str, "CDATA section", reason);
/*     */     }
/*     */     
/* 151 */     if (this.value == "")
/* 152 */     { this.value = str; }
/* 153 */     else { this.value = String.valueOf(this.value) + str; }
/*     */   
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
/*     */   public String toString() {
/* 167 */     return (new StringBuffer(64))
/* 168 */       .append("[CDATA: ")
/* 169 */       .append(getText())
/* 170 */       .append("]")
/* 171 */       .toString();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\CDATA.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */