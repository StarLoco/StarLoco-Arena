/*    */ package org.postgresql.jdbc3;
/*    */ 
/*    */ import java.sql.CallableStatement;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Jdbc3CallableStatement
/*    */   extends Jdbc3PreparedStatement
/*    */   implements CallableStatement
/*    */ {
/*    */   Jdbc3CallableStatement(Jdbc3Connection connection, String sql, int rsType, int rsConcurrency, int rsHoldability) throws SQLException {
/* 20 */     super(connection, sql, true, rsType, rsConcurrency, rsHoldability);
/* 21 */     if (!connection.haveMinimumServerVersion("8.1") || connection.getProtocolVersion() == 2)
/*    */     {
/*    */ 
/*    */       
/* 25 */       this.adjustIndex = this.outParmBeforeFunc;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getObject(int i, Map map) throws SQLException {
/* 31 */     return getObjectImpl(i, map);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getObject(String s, Map map) throws SQLException {
/* 36 */     return getObjectImpl(s, map);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\Jdbc3CallableStatement.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */