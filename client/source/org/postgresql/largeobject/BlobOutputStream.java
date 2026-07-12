/*     */ package org.postgresql.largeobject;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlobOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private LargeObject lo;
/*     */   private byte[] buf;
/*     */   private int bsize;
/*     */   private int bpos;
/*     */   
/*     */   public BlobOutputStream(LargeObject lo) {
/*  47 */     this(lo, 1024);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlobOutputStream(LargeObject lo, int bsize) {
/*  57 */     this.lo = lo;
/*  58 */     this.bsize = bsize;
/*  59 */     this.buf = new byte[bsize];
/*  60 */     this.bpos = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int b) throws IOException {
/*     */     try {
/*  67 */       if (this.bpos >= this.bsize) {
/*     */         
/*  69 */         this.lo.write(this.buf);
/*  70 */         this.bpos = 0;
/*     */       } 
/*  72 */       this.buf[this.bpos++] = (byte)b;
/*     */     }
/*     */     catch (SQLException se) {
/*     */       
/*  76 */       throw new IOException(se.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] buf, int off, int len) throws IOException {
/*     */     try {
/*  85 */       if (this.bpos > 0) {
/*  86 */         flush();
/*     */       }
/*  88 */       if (off == 0 && len == buf.length) {
/*  89 */         this.lo.write(buf);
/*     */       } else {
/*  91 */         this.lo.write(buf, off, len);
/*     */       } 
/*     */     } catch (SQLException se) {
/*     */       
/*  95 */       throw new IOException(se.toString());
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
/*     */   public void flush() throws IOException {
/*     */     try {
/* 114 */       if (this.bpos > 0)
/* 115 */         this.lo.write(this.buf, 0, this.bpos); 
/* 116 */       this.bpos = 0;
/*     */     }
/*     */     catch (SQLException se) {
/*     */       
/* 120 */       throw new IOException(se.toString());
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
/*     */   public void close() throws IOException {
/*     */     try {
/* 138 */       flush();
/* 139 */       this.lo.close();
/* 140 */       this.lo = null;
/*     */     }
/*     */     catch (SQLException se) {
/*     */       
/* 144 */       throw new IOException(se.toString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\largeobject\BlobOutputStream.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */