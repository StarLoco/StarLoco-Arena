/*     */ package org.jdom.input;
/*     */ 
/*     */ import org.jdom.Document;
/*     */ import org.jdom.JDOMException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDOMParseException
/*     */   extends JDOMException
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: JDOMParseException.java,v $ $Revision: 1.7 $ $Date: 2004/02/17 02:29:24 $ $Name: jdom_1_0 $";
/*     */   private final Document partialDocument;
/*     */   
/*     */   public JDOMParseException(String message, Throwable cause) {
/*  91 */     this(message, cause, null);
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
/*     */   public JDOMParseException(String message, Throwable cause, Document partialDocument) {
/* 109 */     super(message, cause);
/* 110 */     this.partialDocument = partialDocument;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document getPartialDocument() {
/* 120 */     return this.partialDocument;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPublicId() {
/* 131 */     return (getCause() instanceof SAXParseException) ? (
/* 132 */       (SAXParseException)getCause()).getPublicId() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/* 143 */     return (getCause() instanceof SAXParseException) ? (
/* 144 */       (SAXParseException)getCause()).getSystemId() : null;
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
/*     */   public int getLineNumber() {
/* 157 */     return (getCause() instanceof SAXParseException) ? (
/* 158 */       (SAXParseException)getCause()).getLineNumber() : -1;
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
/*     */   public int getColumnNumber() {
/* 171 */     return (getCause() instanceof SAXParseException) ? (
/* 172 */       (SAXParseException)getCause()).getColumnNumber() : -1;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\input\JDOMParseException.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */