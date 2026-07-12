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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAXParseException
/*     */   extends SAXException
/*     */ {
/*     */   private String publicId;
/*     */   private String systemId;
/*     */   private int lineNumber;
/*     */   private int columnNumber;
/*     */   static final long serialVersionUID = -5651165872476709336L;
/*     */   
/*     */   public SAXParseException(String paramString, Locator paramLocator) {
/*  58 */     super(paramString);
/*  59 */     if (paramLocator != null) {
/*  60 */       init(paramLocator.getPublicId(), paramLocator.getSystemId(), paramLocator.getLineNumber(), paramLocator.getColumnNumber());
/*     */     } else {
/*     */       
/*  63 */       init(null, null, -1, -1);
/*     */     } 
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
/*     */   
/*     */   public SAXParseException(String paramString, Locator paramLocator, Exception paramException) {
/*  85 */     super(paramString, paramException);
/*  86 */     if (paramLocator != null) {
/*  87 */       init(paramLocator.getPublicId(), paramLocator.getSystemId(), paramLocator.getLineNumber(), paramLocator.getColumnNumber());
/*     */     } else {
/*     */       
/*  90 */       init(null, null, -1, -1);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXParseException(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2) {
/* 119 */     super(paramString1);
/* 120 */     init(paramString2, paramString3, paramInt1, paramInt2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXParseException(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, Exception paramException) {
/* 151 */     super(paramString1, paramException);
/* 152 */     init(paramString2, paramString3, paramInt1, paramInt2);
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
/*     */   private void init(String paramString1, String paramString2, int paramInt1, int paramInt2) {
/* 169 */     this.publicId = paramString1;
/* 170 */     this.systemId = paramString2;
/* 171 */     this.lineNumber = paramInt1;
/* 172 */     this.columnNumber = paramInt2;
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
/*     */   public String getPublicId() {
/* 185 */     return this.publicId;
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
/*     */   public String getSystemId() {
/* 201 */     return this.systemId;
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
/*     */   public int getLineNumber() {
/* 216 */     return this.lineNumber;
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
/*     */   public int getColumnNumber() {
/* 231 */     return this.columnNumber;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\SAXParseException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */