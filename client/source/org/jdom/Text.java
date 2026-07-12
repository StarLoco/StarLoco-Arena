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
/*     */ 
/*     */ 
/*     */ public class Text
/*     */   extends Content
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: Text.java,v $ $Revision: 1.24 $ $Date: 2004/02/27 11:32:57 $ $Name: jdom_1_0 $";
/*     */   static final String EMPTY_STRING = "";
/*     */   protected String value;
/*     */   
/*     */   protected Text() {}
/*     */   
/*     */   public Text(String str) {
/*  99 */     setText(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 109 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextTrim() {
/* 119 */     return getText().trim();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextNormalize() {
/* 130 */     return normalizeString(getText());
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
/*     */   public static String normalizeString(String str) {
/* 145 */     if (str == null) {
/* 146 */       return "";
/*     */     }
/* 148 */     char[] c = str.toCharArray();
/* 149 */     char[] n = new char[c.length];
/* 150 */     boolean white = true;
/* 151 */     int pos = 0;
/* 152 */     for (int i = 0; i < c.length; i++) {
/* 153 */       if (" \t\n\r".indexOf(c[i]) != -1) {
/* 154 */         if (!white) {
/* 155 */           n[pos++] = ' ';
/* 156 */           white = true;
/*     */         } 
/*     */       } else {
/*     */         
/* 160 */         n[pos++] = c[i];
/* 161 */         white = false;
/*     */       } 
/*     */     } 
/* 164 */     if (white && pos > 0) {
/* 165 */       pos--;
/*     */     }
/* 167 */     return new String(n, 0, pos);
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
/*     */   public Text setText(String str) {
/* 182 */     if (str == null) {
/* 183 */       this.value = "";
/* 184 */       return this;
/*     */     } 
/*     */     String reason;
/* 187 */     if ((reason = Verifier.checkCharacterData(str)) != null) {
/* 188 */       throw new IllegalDataException(str, "character content", reason);
/*     */     }
/* 190 */     this.value = str;
/* 191 */     return this;
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
/*     */   public void append(String str) {
/* 206 */     if (str == null)
/*     */       return; 
/*     */     String reason;
/* 209 */     if ((reason = Verifier.checkCharacterData(str)) != null) {
/* 210 */       throw new IllegalDataException(str, "character content", reason);
/*     */     }
/*     */     
/* 213 */     if (str == "")
/* 214 */     { this.value = str; }
/* 215 */     else { this.value = String.valueOf(this.value) + str; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(Text text) {
/* 225 */     if (text == null) {
/*     */       return;
/*     */     }
/* 228 */     this.value = String.valueOf(this.value) + text.getText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 238 */     return this.value;
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
/*     */   public String toString() {
/* 252 */     return (new StringBuffer(64))
/* 253 */       .append("[Text: ")
/* 254 */       .append(getText())
/* 255 */       .append("]")
/* 256 */       .toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 266 */     Text text = (Text)super.clone();
/* 267 */     text.value = this.value;
/* 268 */     return text;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\Text.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */