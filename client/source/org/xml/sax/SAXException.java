/*     */ package org.xml.sax;
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
/*     */ public class SAXException
/*     */   extends Exception
/*     */ {
/*     */   private Exception exception;
/*     */   static final long serialVersionUID = 583241635256073760L;
/*     */   
/*     */   public SAXException() {
/*  46 */     this.exception = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXException(String paramString) {
/*  56 */     super(paramString);
/*  57 */     this.exception = null;
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
/*     */   public SAXException(Exception paramException) {
/*  73 */     this.exception = paramException;
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
/*     */   public SAXException(String paramString, Exception paramException) {
/*  88 */     super(paramString);
/*  89 */     this.exception = paramException;
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
/*     */   public String getMessage() {
/* 104 */     String str = super.getMessage();
/*     */     
/* 106 */     if (str == null && this.exception != null) {
/* 107 */       return this.exception.getMessage();
/*     */     }
/* 109 */     return str;
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
/*     */   public Exception getException() {
/* 121 */     return this.exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 132 */     if (this.exception != null) {
/* 133 */       return this.exception.toString();
/*     */     }
/* 135 */     return super.toString();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\SAXException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */