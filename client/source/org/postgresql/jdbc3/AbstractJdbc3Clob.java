/*     */ package org.postgresql.jdbc3;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ import java.sql.SQLException;
/*     */ import org.postgresql.Driver;
/*     */ import org.postgresql.PGConnection;
/*     */ import org.postgresql.jdbc2.AbstractJdbc2Clob;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractJdbc3Clob
/*     */   extends AbstractJdbc2Clob
/*     */ {
/*     */   public AbstractJdbc3Clob(PGConnection conn, int oid) throws SQLException {
/*  20 */     super(conn, oid);
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
/*     */   public int setString(long pos, String str) throws SQLException {
/*  40 */     throw Driver.notImplemented(getClass(), "setString(long,str)");
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
/*     */   public int setString(long pos, String str, int offset, int len) throws SQLException {
/*  63 */     throw Driver.notImplemented(getClass(), "setString(long,String,int,int)");
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
/*     */   public OutputStream setAsciiStream(long pos) throws SQLException {
/*  82 */     throw Driver.notImplemented(getClass(), "setAsciiStream(long)");
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
/*     */   public Writer setCharacterStream(long pos) throws SQLException {
/* 102 */     throw Driver.notImplemented(getClass(), "setCharacteStream(long)");
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
/*     */   public void truncate(long len) throws SQLException {
/* 118 */     throw Driver.notImplemented(getClass(), "truncate(long)");
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\AbstractJdbc3Clob.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */