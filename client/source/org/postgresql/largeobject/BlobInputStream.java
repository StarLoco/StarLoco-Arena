/*     */ package org.postgresql.largeobject;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class BlobInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private LargeObject lo;
/*     */   private byte[] buffer;
/*     */   private int bpos;
/*     */   private int bsize;
/*  44 */   private int mpos = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlobInputStream(LargeObject lo) {
/*  51 */     this(lo, 1024);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlobInputStream(LargeObject lo, int bsize) {
/*  60 */     this.lo = lo;
/*  61 */     this.buffer = null;
/*  62 */     this.bpos = 0;
/*  63 */     this.bsize = bsize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/*     */     try {
/*  73 */       if (this.buffer == null || this.bpos >= this.buffer.length) {
/*     */         
/*  75 */         this.buffer = this.lo.read(this.bsize);
/*  76 */         this.bpos = 0;
/*     */       } 
/*     */ 
/*     */       
/*  80 */       if (this.bpos >= this.buffer.length)
/*     */       {
/*  82 */         return -1;
/*     */       }
/*     */       
/*  85 */       int ret = this.buffer[this.bpos] & Byte.MAX_VALUE;
/*  86 */       if ((this.buffer[this.bpos] & 0x80) == 128)
/*     */       {
/*  88 */         ret |= 0x80;
/*     */       }
/*     */       
/*  91 */       this.bpos++;
/*     */       
/*  93 */       return ret;
/*     */     }
/*     */     catch (SQLException se) {
/*     */       
/*  97 */       throw new IOException(se.toString());
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
/* 115 */       this.lo.close();
/* 116 */       this.lo = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void mark(int readlimit) {
/*     */     try {
/* 152 */       this.mpos = this.lo.tell();
/*     */     
/*     */     }
/* 155 */     catch (SQLException se) {}
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
/*     */   public synchronized void reset() throws IOException {
/*     */     try {
/* 172 */       this.lo.seek(this.mpos);
/*     */     }
/*     */     catch (SQLException se) {
/*     */       
/* 176 */       throw new IOException(se.toString());
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
/*     */   public boolean markSupported() {
/* 192 */     return true;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\largeobject\BlobInputStream.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */