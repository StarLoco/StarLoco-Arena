/*    */ package org.postgresql.jdbc3;
/*    */ 
/*    */ import java.sql.CallableStatement;
/*    */ import java.sql.Connection;
/*    */ import java.sql.DatabaseMetaData;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ import java.util.Map;
/*    */ import java.util.Properties;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Jdbc3Connection
/*    */   extends AbstractJdbc3Connection
/*    */   implements Connection
/*    */ {
/*    */   public Jdbc3Connection(String host, int port, String user, String database, Properties info, String url) throws SQLException {
/* 24 */     super(host, port, user, database, info, url);
/*    */   }
/*    */ 
/*    */   
/*    */   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/* 29 */     Jdbc3Statement s = new Jdbc3Statement(this, resultSetType, resultSetConcurrency, resultSetHoldability);
/* 30 */     s.setPrepareThreshold(getPrepareThreshold());
/* 31 */     return s;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/* 37 */     Jdbc3PreparedStatement s = new Jdbc3PreparedStatement(this, sql, resultSetType, resultSetConcurrency, resultSetHoldability);
/* 38 */     s.setPrepareThreshold(getPrepareThreshold());
/* 39 */     return s;
/*    */   }
/*    */ 
/*    */   
/*    */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/* 44 */     Jdbc3CallableStatement s = new Jdbc3CallableStatement(this, sql, resultSetType, resultSetConcurrency, resultSetHoldability);
/* 45 */     s.setPrepareThreshold(getPrepareThreshold());
/* 46 */     return s;
/*    */   }
/*    */ 
/*    */   
/*    */   public DatabaseMetaData getMetaData() throws SQLException {
/* 51 */     if (this.metadata == null)
/* 52 */       this.metadata = new Jdbc3DatabaseMetaData(this); 
/* 53 */     return this.metadata;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTypeMap(Map map) throws SQLException {
/* 58 */     setTypeMapImpl(map);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\Jdbc3Connection.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */