/*     */ package org.postgresql.largeobject;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.sql.SQLException;
/*     */ import org.postgresql.fastpath.Fastpath;
/*     */ import org.postgresql.fastpath.FastpathArg;
/*     */ import org.postgresql.util.PSQLException;
/*     */ import org.postgresql.util.PSQLState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LargeObject
/*     */ {
/*     */   public static final int SEEK_SET = 0;
/*     */   public static final int SEEK_CUR = 1;
/*     */   public static final int SEEK_END = 2;
/*     */   private Fastpath fp;
/*     */   private int oid;
/*     */   private int fd;
/*     */   private BlobOutputStream os;
/*     */   private boolean closed = false;
/*     */   
/*     */   protected LargeObject(Fastpath fp, int oid, int mode) throws SQLException {
/*  85 */     this.fp = fp;
/*  86 */     this.oid = oid;
/*     */     
/*  88 */     FastpathArg[] args = new FastpathArg[2];
/*  89 */     args[0] = new FastpathArg(oid);
/*  90 */     args[1] = new FastpathArg(mode);
/*  91 */     this.fd = fp.getInteger("lo_open", args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws SQLException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOID() {
/* 110 */     return this.oid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/* 120 */     if (!this.closed) {
/*     */ 
/*     */       
/* 123 */       if (this.os != null) {
/*     */         
/*     */         try {
/*     */ 
/*     */           
/* 128 */           this.os.flush();
/*     */         }
/*     */         catch (IOException ioe) {
/*     */           
/* 132 */           throw new PSQLException("Exception flushing output stream", PSQLState.DATA_ERROR, ioe);
/*     */         
/*     */         }
/*     */         finally {
/*     */           
/* 137 */           this.os = null;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 142 */       FastpathArg[] args = new FastpathArg[1];
/* 143 */       args[0] = new FastpathArg(this.fd);
/* 144 */       this.fp.fastpath("lo_close", false, args);
/* 145 */       this.closed = true;
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
/*     */   public byte[] read(int len) throws SQLException {
/* 160 */     FastpathArg[] args = new FastpathArg[2];
/* 161 */     args[0] = new FastpathArg(this.fd);
/* 162 */     args[1] = new FastpathArg(len);
/* 163 */     return this.fp.getData("loread", args);
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
/*     */   public int read(byte[] buf, int off, int len) throws SQLException {
/* 177 */     byte[] b = read(len);
/* 178 */     if (b.length < len)
/* 179 */       len = b.length; 
/* 180 */     System.arraycopy(b, 0, buf, off, len);
/* 181 */     return len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] buf) throws SQLException {
/* 192 */     FastpathArg[] args = new FastpathArg[2];
/* 193 */     args[0] = new FastpathArg(this.fd);
/* 194 */     args[1] = new FastpathArg(buf);
/* 195 */     this.fp.fastpath("lowrite", false, args);
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
/*     */   public void write(byte[] buf, int off, int len) throws SQLException {
/* 208 */     FastpathArg[] args = new FastpathArg[2];
/* 209 */     args[0] = new FastpathArg(this.fd);
/* 210 */     args[1] = new FastpathArg(buf, off, len);
/* 211 */     this.fp.fastpath("lowrite", false, args);
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
/*     */   public void seek(int pos, int ref) throws SQLException {
/* 226 */     FastpathArg[] args = new FastpathArg[3];
/* 227 */     args[0] = new FastpathArg(this.fd);
/* 228 */     args[1] = new FastpathArg(pos);
/* 229 */     args[2] = new FastpathArg(ref);
/* 230 */     this.fp.fastpath("lo_lseek", false, args);
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
/*     */   public void seek(int pos) throws SQLException {
/* 244 */     seek(pos, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tell() throws SQLException {
/* 253 */     FastpathArg[] args = new FastpathArg[1];
/* 254 */     args[0] = new FastpathArg(this.fd);
/* 255 */     return this.fp.getInteger("lo_tell", args);
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
/*     */   public int size() throws SQLException {
/* 270 */     int cp = tell();
/* 271 */     seek(0, 2);
/* 272 */     int sz = tell();
/* 273 */     seek(cp, 0);
/* 274 */     return sz;
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
/*     */   public InputStream getInputStream() throws SQLException {
/* 287 */     return new BlobInputStream(this, 4096);
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
/*     */   public OutputStream getOutputStream() throws SQLException {
/* 300 */     if (this.os == null)
/* 301 */       this.os = new BlobOutputStream(this, 4096); 
/* 302 */     return this.os;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\largeobject\LargeObject.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */