/*     */ package org.postgresql.core;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Encoding
/*     */ {
/*  26 */   private static final Encoding DEFAULT_ENCODING = new Encoding(null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private static final Hashtable encodings = new Hashtable();
/*     */   
/*     */   private final String encoding;
/*     */   
/*     */   static {
/*  36 */     encodings.put("SQL_ASCII", new String[] { "ASCII", "us-ascii" });
/*  37 */     encodings.put("UNICODE", new String[] { "UTF-8", "UTF8" });
/*  38 */     encodings.put("UTF8", new String[] { "UTF-8", "UTF8" });
/*  39 */     encodings.put("LATIN1", new String[] { "ISO8859_1" });
/*  40 */     encodings.put("LATIN2", new String[] { "ISO8859_2" });
/*  41 */     encodings.put("LATIN3", new String[] { "ISO8859_3" });
/*  42 */     encodings.put("LATIN4", new String[] { "ISO8859_4" });
/*  43 */     encodings.put("ISO_8859_5", new String[] { "ISO8859_5" });
/*  44 */     encodings.put("ISO_8859_6", new String[] { "ISO8859_6" });
/*  45 */     encodings.put("ISO_8859_7", new String[] { "ISO8859_7" });
/*  46 */     encodings.put("ISO_8859_8", new String[] { "ISO8859_8" });
/*  47 */     encodings.put("LATIN5", new String[] { "ISO8859_9" });
/*  48 */     encodings.put("LATIN7", new String[] { "ISO8859_13" });
/*  49 */     encodings.put("LATIN9", new String[] { "ISO8859_15_FDIS" });
/*  50 */     encodings.put("EUC_JP", new String[] { "EUC_JP" });
/*  51 */     encodings.put("EUC_CN", new String[] { "EUC_CN" });
/*  52 */     encodings.put("EUC_KR", new String[] { "EUC_KR" });
/*  53 */     encodings.put("JOHAB", new String[] { "Johab" });
/*  54 */     encodings.put("EUC_TW", new String[] { "EUC_TW" });
/*  55 */     encodings.put("SJIS", new String[] { "MS932", "SJIS" });
/*  56 */     encodings.put("BIG5", new String[] { "Big5", "MS950", "Cp950" });
/*  57 */     encodings.put("GBK", new String[] { "GBK", "MS936" });
/*  58 */     encodings.put("UHC", new String[] { "MS949", "Cp949", "Cp949C" });
/*  59 */     encodings.put("TCVN", new String[] { "Cp1258" });
/*  60 */     encodings.put("WIN1256", new String[] { "Cp1256" });
/*  61 */     encodings.put("WIN1250", new String[] { "Cp1250" });
/*  62 */     encodings.put("WIN874", new String[] { "MS874", "Cp874" });
/*  63 */     encodings.put("WIN", new String[] { "Cp1251" });
/*  64 */     encodings.put("ALT", new String[] { "Cp866" });
/*     */     
/*  66 */     encodings.put("KOI8", new String[] { "KOI8_U", "KOI8_R" });
/*     */ 
/*     */     
/*  69 */     encodings.put("UNKNOWN", new String[0]);
/*     */     
/*  71 */     encodings.put("MULE_INTERNAL", new String[0]);
/*  72 */     encodings.put("LATIN6", new String[0]);
/*  73 */     encodings.put("LATIN8", new String[0]);
/*  74 */     encodings.put("LATIN10", new String[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Encoding(String encoding) {
/*  81 */     this.encoding = encoding;
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
/*     */   public static Encoding getJVMEncoding(String jvmEncoding) {
/*  93 */     if (isAvailable(jvmEncoding)) {
/*     */       
/*  95 */       if (jvmEncoding.equals("UTF-8") || jvmEncoding.equals("UTF8")) {
/*  96 */         return new UTF8Encoding(jvmEncoding);
/*     */       }
/*  98 */       return new Encoding(jvmEncoding);
/*     */     } 
/*     */     
/* 101 */     return defaultEncoding();
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
/*     */   public static Encoding getDatabaseEncoding(String databaseEncoding) {
/* 118 */     if (encodings.containsKey(databaseEncoding)) {
/*     */       
/* 120 */       String[] candidates = (String[])encodings.get(databaseEncoding);
/* 121 */       for (int i = 0; i < candidates.length; i++) {
/*     */         
/* 123 */         if (isAvailable(candidates[i]))
/*     */         {
/* 125 */           return new Encoding(candidates[i]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 132 */     if (isAvailable(databaseEncoding)) {
/* 133 */       return new Encoding(databaseEncoding);
/*     */     }
/*     */     
/* 136 */     return defaultEncoding();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/* 146 */     return this.encoding;
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
/*     */   public byte[] encode(String s) throws IOException {
/* 158 */     if (s == null) {
/* 159 */       return null;
/*     */     }
/* 161 */     if (this.encoding == null) {
/* 162 */       return s.getBytes();
/*     */     }
/* 164 */     return s.getBytes(this.encoding);
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
/*     */   public String decode(byte[] encodedString, int offset, int length) throws IOException {
/* 178 */     if (this.encoding == null) {
/* 179 */       return new String(encodedString, offset, length);
/*     */     }
/* 181 */     return new String(encodedString, offset, length, this.encoding);
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
/*     */   public String decode(byte[] encodedString) throws IOException {
/* 193 */     return decode(encodedString, 0, encodedString.length);
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
/*     */   public Reader getDecodingReader(InputStream in) throws IOException {
/* 205 */     if (this.encoding == null) {
/* 206 */       return new InputStreamReader(in);
/*     */     }
/* 208 */     return new InputStreamReader(in, this.encoding);
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
/*     */   public Writer getEncodingWriter(OutputStream out) throws IOException {
/* 220 */     if (this.encoding == null) {
/* 221 */       return new OutputStreamWriter(out);
/*     */     }
/* 223 */     return new OutputStreamWriter(out, this.encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Encoding defaultEncoding() {
/* 232 */     return DEFAULT_ENCODING;
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
/*     */   private static boolean isAvailable(String encodingName) {
/*     */     try {
/* 245 */       "DUMMY".getBytes(encodingName);
/* 246 */       return true;
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/*     */       
/* 250 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 255 */     return (this.encoding == null) ? "<default JVM encoding>" : this.encoding;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\Encoding.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */