/*    */ package org.postgresql.jdbc3;
/*    */ 
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Jdbc3PreparedStatement
/*    */   extends Jdbc3Statement
/*    */   implements PreparedStatement
/*    */ {
/*    */   Jdbc3PreparedStatement(Jdbc3Connection connection, String sql, int rsType, int rsConcurrency, int rsHoldability) throws SQLException {
/* 18 */     this(connection, sql, false, rsType, rsConcurrency, rsHoldability);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Jdbc3PreparedStatement(Jdbc3Connection connection, String sql, boolean isCallable, int rsType, int rsConcurrency, int rsHoldability) throws SQLException {
/* 23 */     super(connection, sql, isCallable, rsType, rsConcurrency, rsHoldability);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\Jdbc3PreparedStatement.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */