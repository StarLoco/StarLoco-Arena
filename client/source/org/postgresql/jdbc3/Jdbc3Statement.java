/*    */ package org.postgresql.jdbc3;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ import java.util.Vector;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ class Jdbc3Statement
/*    */   extends AbstractJdbc3Statement
/*    */   implements Statement
/*    */ {
/*    */   Jdbc3Statement(Jdbc3Connection c, int rsType, int rsConcurrency, int rsHoldability) throws SQLException {
/* 25 */     super(c, rsType, rsConcurrency, rsHoldability);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Jdbc3Statement(Jdbc3Connection connection, String sql, boolean isCallable, int rsType, int rsConcurrency, int rsHoldability) throws SQLException {
/* 30 */     super(connection, sql, isCallable, rsType, rsConcurrency, rsHoldability);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ResultSet createResultSet(Query originalQuery, Field[] fields, Vector tuples, ResultCursor cursor) throws SQLException {
/* 36 */     Jdbc3ResultSet newResult = new Jdbc3ResultSet(originalQuery, (BaseStatement)this, fields, tuples, cursor, getMaxRows(), getMaxFieldSize(), getResultSetType(), getResultSetConcurrency(), getResultSetHoldability());
/*    */ 
/*    */     
/* 39 */     newResult.setFetchSize(getFetchSize());
/* 40 */     newResult.setFetchDirection(getFetchDirection());
/* 41 */     return newResult;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\Jdbc3Statement.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */