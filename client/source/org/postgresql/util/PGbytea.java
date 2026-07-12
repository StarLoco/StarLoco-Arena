/*     */ package org.postgresql.util;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PGbytea
/*     */ {
/*     */   public static byte[] toBytes(byte[] s) throws SQLException {
/*  26 */     if (s == null)
/*  27 */       return null; 
/*  28 */     int slength = s.length;
/*  29 */     byte[] buf = new byte[slength];
/*  30 */     int bufpos = 0;
/*     */ 
/*     */ 
/*     */     
/*  34 */     for (int i = 0; i < slength; i++) {
/*     */       
/*  36 */       byte nextbyte = s[i];
/*  37 */       if (nextbyte == 92) {
/*     */         
/*  39 */         byte secondbyte = s[++i];
/*  40 */         if (secondbyte == 92)
/*     */         {
/*     */           
/*  43 */           buf[bufpos++] = 92;
/*     */         }
/*     */         else
/*     */         {
/*  47 */           int thebyte = (secondbyte - 48) * 64 + (s[++i] - 48) * 8 + s[++i] - 48;
/*  48 */           if (thebyte > 127)
/*  49 */             thebyte -= 256; 
/*  50 */           buf[bufpos++] = (byte)thebyte;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  55 */         buf[bufpos++] = nextbyte;
/*     */       } 
/*     */     } 
/*  58 */     byte[] l_return = new byte[bufpos];
/*  59 */     System.arraycopy(buf, 0, l_return, 0, bufpos);
/*  60 */     return l_return;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toPGString(byte[] p_buf) throws SQLException {
/*  69 */     if (p_buf == null)
/*  70 */       return null; 
/*  71 */     StringBuffer l_strbuf = new StringBuffer(2 * p_buf.length);
/*  72 */     for (int i = 0; i < p_buf.length; i++) {
/*     */       
/*  74 */       int l_int = p_buf[i];
/*  75 */       if (l_int < 0)
/*     */       {
/*  77 */         l_int = 256 + l_int;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  83 */       if (l_int < 32 || l_int > 126) {
/*     */ 
/*     */ 
/*     */         
/*  87 */         l_strbuf.append("\\");
/*  88 */         l_strbuf.append((char)((l_int >> 6 & 0x3) + 48));
/*  89 */         l_strbuf.append((char)((l_int >> 3 & 0x7) + 48));
/*  90 */         l_strbuf.append((char)((l_int & 0x7) + 48));
/*     */       }
/*  92 */       else if (p_buf[i] == 92) {
/*     */ 
/*     */ 
/*     */         
/*  96 */         l_strbuf.append("\\\\");
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 101 */         l_strbuf.append((char)p_buf[i]);
/*     */       } 
/*     */     } 
/* 104 */     return l_strbuf.toString();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresq\\util\PGbytea.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */