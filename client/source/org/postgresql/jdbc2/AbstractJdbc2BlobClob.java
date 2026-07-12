/*     */ package org.postgresql.jdbc2;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.sql.Blob;
/*     */ import java.sql.SQLException;
/*     */ import org.postgresql.PGConnection;
/*     */ import org.postgresql.largeobject.LargeObject;
/*     */ import org.postgresql.largeobject.LargeObjectManager;
/*     */ import org.postgresql.util.GT;
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
/*     */ public class AbstractJdbc2BlobClob
/*     */ {
/*     */   private LargeObject lo;
/*     */   
/*     */   public AbstractJdbc2BlobClob(PGConnection conn, int oid) throws SQLException {
/*  38 */     LargeObjectManager lom = conn.getLargeObjectAPI();
/*  39 */     this.lo = lom.open(oid);
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() throws SQLException {
/*  44 */     return this.lo.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getBytes(long pos, int length) throws SQLException {
/*  49 */     assertPosition(pos);
/*  50 */     this.lo.seek((int)(pos - 1L), 0);
/*  51 */     return this.lo.read(length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getBinaryStream() throws SQLException {
/*  57 */     return this.lo.getInputStream();
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
/*     */   public long position(byte[] pattern, long start) throws SQLException {
/*  69 */     assertPosition(start, pattern.length);
/*     */     
/*  71 */     int position = 1;
/*  72 */     int patternIdx = 0;
/*  73 */     long result = -1L;
/*  74 */     int tmpPosition = 1;
/*     */     
/*  76 */     for (LOIterator i = new LOIterator(this, start - 1L); i.hasNext(); position++) {
/*     */       
/*  78 */       byte b = i.next();
/*  79 */       if (b == pattern[patternIdx]) {
/*     */         
/*  81 */         if (patternIdx == 0)
/*     */         {
/*  83 */           tmpPosition = position;
/*     */         }
/*  85 */         patternIdx++;
/*  86 */         if (patternIdx == pattern.length) {
/*     */           
/*  88 */           result = tmpPosition;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } else {
/*  94 */         patternIdx = 0;
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private class LOIterator
/*     */   {
/*     */     private static final int BUFFER_SIZE = 8096;
/*     */     
/*     */     private byte[] buffer;
/*     */     
/*     */     private int idx;
/*     */     
/*     */     private int numBytes;
/*     */     
/*     */     private final AbstractJdbc2BlobClob this$0;
/*     */     
/*     */     public LOIterator(AbstractJdbc2BlobClob this$0, long start) throws SQLException {
/* 115 */       this.this$0 = this$0; this.buffer = new byte[8096]; this.idx = 8096; this.numBytes = 8096;
/* 116 */       this$0.lo.seek((int)start);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() throws SQLException {
/* 121 */       boolean result = false;
/* 122 */       if (this.idx < this.numBytes) {
/*     */         
/* 124 */         result = true;
/*     */       }
/*     */       else {
/*     */         
/* 128 */         this.numBytes = this.this$0.lo.read(this.buffer, 0, 8096);
/* 129 */         this.idx = 0;
/* 130 */         result = (this.numBytes > 0);
/*     */       } 
/* 132 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     private byte next() {
/* 137 */       return this.buffer[this.idx++];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long position(Blob pattern, long start) throws SQLException {
/* 147 */     return position(pattern.getBytes(1L, (int)pattern.length()), start);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LargeObject getLO() {
/* 155 */     return this.lo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void assertPosition(long pos) throws SQLException {
/* 166 */     assertPosition(pos, 0L);
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
/*     */   protected void assertPosition(long pos, long len) throws SQLException {
/* 178 */     if (pos < 1L)
/*     */     {
/* 180 */       throw new PSQLException(GT.tr("LOB positioning offsets start at 1."), PSQLState.INVALID_PARAMETER_VALUE);
/*     */     }
/* 182 */     if (pos + len - 1L > 2147483647L)
/*     */     {
/* 184 */       throw new PSQLException(GT.tr("PostgreSQL LOBs can only index to: {0}", new Integer(2147483647)), PSQLState.INVALID_PARAMETER_VALUE);
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc2\AbstractJdbc2BlobClob.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */