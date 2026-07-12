/*    */ package org.postgresql.jdbc3;
/*    */ 
/*    */ import java.sql.Array;
/*    */ import java.sql.Blob;
/*    */ import java.sql.Clob;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Map;
/*    */ import java.util.Vector;
/*    */ import org.postgresql.PGConnection;
/*    */ import org.postgresql.core.BaseResultSet;
/*    */ import org.postgresql.core.BaseStatement;
/*    */ import org.postgresql.core.Field;
/*    */ import org.postgresql.core.Query;
/*    */ import org.postgresql.core.ResultCursor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Jdbc3ResultSet
/*    */   extends AbstractJdbc3ResultSet
/*    */   implements ResultSet
/*    */ {
/*    */   Jdbc3ResultSet(Query originalQuery, BaseStatement statement, Field[] fields, Vector tuples, ResultCursor cursor, int maxRows, int maxFieldSize, int rsType, int rsConcurrency, int rsHoldability) throws SQLException {
/* 28 */     super(originalQuery, statement, fields, tuples, cursor, maxRows, maxFieldSize, rsType, rsConcurrency, rsHoldability);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResultSetMetaData getMetaData() throws SQLException {
/* 33 */     checkClosed();
/* 34 */     return new Jdbc3ResultSetMetaData(this.connection, this.fields);
/*    */   }
/*    */ 
/*    */   
/*    */   public Clob getClob(int i) throws SQLException {
/* 39 */     checkResultSet(i);
/* 40 */     this.wasNullFlag = (this.this_row[i - 1] == null);
/* 41 */     if (this.wasNullFlag) {
/* 42 */       return null;
/*    */     }
/* 44 */     return new Jdbc3Clob((PGConnection)this.connection, getInt(i));
/*    */   }
/*    */ 
/*    */   
/*    */   public Blob getBlob(int i) throws SQLException {
/* 49 */     checkResultSet(i);
/* 50 */     this.wasNullFlag = (this.this_row[i - 1] == null);
/* 51 */     if (this.wasNullFlag) {
/* 52 */       return null;
/*    */     }
/* 54 */     return new Jdbc3Blob((PGConnection)this.connection, getInt(i));
/*    */   }
/*    */ 
/*    */   
/*    */   public Array createArray(int i) throws SQLException {
/* 59 */     checkResultSet(i);
/* 60 */     return new Jdbc3Array(this.connection, i, this.fields[i - 1], (BaseResultSet)this);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getObject(String s, Map map) throws SQLException {
/* 65 */     return getObjectImpl(s, map);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getObject(int i, Map map) throws SQLException {
/* 70 */     return getObjectImpl(i, map);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\Jdbc3ResultSet.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */