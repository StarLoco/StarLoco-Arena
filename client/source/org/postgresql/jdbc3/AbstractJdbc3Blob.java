/*     */ package org.postgresql.jdbc3;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.sql.SQLException;
/*     */ import org.postgresql.Driver;
/*     */ import org.postgresql.PGConnection;
/*     */ import org.postgresql.jdbc2.AbstractJdbc2Blob;
/*     */ import org.postgresql.largeobject.LargeObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractJdbc3Blob
/*     */   extends AbstractJdbc2Blob
/*     */ {
/*     */   public AbstractJdbc3Blob(PGConnection conn, int oid) throws SQLException {
/*  22 */     super(conn, oid);
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
/*     */   public int setBytes(long pos, byte[] bytes) throws SQLException {
/*  42 */     return setBytes(pos, bytes, 0, bytes.length);
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
/*     */   public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
/*  70 */     assertPosition(pos);
/*  71 */     LargeObject lo = getLO();
/*  72 */     lo.seek((int)(pos - 1L));
/*  73 */     lo.write(bytes, offset, len);
/*  74 */     return len;
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
/*     */   public OutputStream setBinaryStream(long pos) throws SQLException {
/*  93 */     assertPosition(pos);
/*  94 */     LargeObject lo = getLO();
/*  95 */     lo.seek((int)(pos - 1L));
/*  96 */     return lo.getOutputStream();
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
/*     */   public void truncate(long len) throws SQLException {
/* 111 */     throw Driver.notImplemented(getClass(), "truncate(long)");
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\AbstractJdbc3Blob.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */