/*    */ package org.postgresql.jdbc2;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.Reader;
/*    */ import java.sql.Clob;
/*    */ import java.sql.SQLException;
/*    */ import org.postgresql.Driver;
/*    */ import org.postgresql.PGConnection;
/*    */ import org.postgresql.largeobject.LargeObject;
/*    */ import org.postgresql.largeobject.LargeObjectManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AbstractJdbc2Clob
/*    */ {
/*    */   private LargeObject lo;
/*    */   
/*    */   public AbstractJdbc2Clob(PGConnection conn, int oid) throws SQLException {
/* 28 */     LargeObjectManager lom = conn.getLargeObjectAPI();
/* 29 */     this.lo = lom.open(oid);
/*    */   }
/*    */ 
/*    */   
/*    */   public long length() throws SQLException {
/* 34 */     return this.lo.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream getAsciiStream() throws SQLException {
/* 39 */     return this.lo.getInputStream();
/*    */   }
/*    */ 
/*    */   
/*    */   public Reader getCharacterStream() throws SQLException {
/* 44 */     return new InputStreamReader(this.lo.getInputStream());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSubString(long i, int j) throws SQLException {
/* 49 */     this.lo.seek((int)i - 1);
/* 50 */     return new String(this.lo.read(j));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long position(String pattern, long start) throws SQLException {
/* 58 */     throw Driver.notImplemented(getClass(), "position(String,long)");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long position(Clob pattern, long start) throws SQLException {
/* 66 */     throw Driver.notImplemented(getClass(), "position(Clob,start)");
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc2\AbstractJdbc2Clob.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */