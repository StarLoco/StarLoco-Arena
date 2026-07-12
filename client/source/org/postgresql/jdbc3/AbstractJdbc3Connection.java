/*     */ package org.postgresql.jdbc3;
/*     */ 
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Savepoint;
/*     */ import java.sql.Statement;
/*     */ import java.util.Properties;
/*     */ import org.postgresql.jdbc2.AbstractJdbc2Connection;
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
/*     */ public abstract class AbstractJdbc3Connection
/*     */   extends AbstractJdbc2Connection
/*     */ {
/*  26 */   private int rsHoldability = 2;
/*  27 */   private int savepointId = 0;
/*     */   
/*     */   protected AbstractJdbc3Connection(String host, int port, String user, String database, Properties info, String url) throws SQLException {
/*  30 */     super(host, port, user, database, info, url);
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
/*     */   public void setHoldability(int holdability) throws SQLException {
/*  50 */     switch (holdability) {
/*     */       
/*     */       case 2:
/*  53 */         this.rsHoldability = holdability;
/*     */         return;
/*     */       case 1:
/*  56 */         this.rsHoldability = holdability;
/*     */         return;
/*     */     } 
/*  59 */     throw new PSQLException(GT.tr("Unknown ResultSet holdability setting: {0}.", new Integer(holdability)), PSQLState.INVALID_PARAMETER_VALUE);
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
/*     */   public int getHoldability() throws SQLException {
/*  78 */     return this.rsHoldability;
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
/*     */   public Savepoint setSavepoint() throws SQLException {
/*  94 */     if (!haveMinimumServerVersion("8.0"))
/*  95 */       throw new PSQLException(GT.tr("Server versions prior to 8.0 do not support savepoints."), PSQLState.NOT_IMPLEMENTED); 
/*  96 */     if (getAutoCommit()) {
/*  97 */       throw new PSQLException(GT.tr("Cannot establish a savepoint in auto-commit mode."), PSQLState.NO_ACTIVE_SQL_TRANSACTION);
/*     */     }
/*     */     
/* 100 */     PSQLSavepoint savepoint = new PSQLSavepoint(this.savepointId++);
/*     */ 
/*     */ 
/*     */     
/* 104 */     Statement stmt = createStatement();
/* 105 */     stmt.executeUpdate("SAVEPOINT " + savepoint.getPGName());
/* 106 */     stmt.close();
/*     */     
/* 108 */     return savepoint;
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
/*     */   public Savepoint setSavepoint(String name) throws SQLException {
/* 125 */     if (!haveMinimumServerVersion("8.0"))
/* 126 */       throw new PSQLException(GT.tr("Server versions prior to 8.0 do not support savepoints."), PSQLState.NOT_IMPLEMENTED); 
/* 127 */     if (getAutoCommit()) {
/* 128 */       throw new PSQLException(GT.tr("Cannot establish a savepoint in auto-commit mode."), PSQLState.NO_ACTIVE_SQL_TRANSACTION);
/*     */     }
/*     */     
/* 131 */     PSQLSavepoint savepoint = new PSQLSavepoint(name);
/*     */ 
/*     */ 
/*     */     
/* 135 */     Statement stmt = createStatement();
/* 136 */     stmt.executeUpdate("SAVEPOINT " + savepoint.getPGName());
/* 137 */     stmt.close();
/*     */     
/* 139 */     return savepoint;
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
/*     */   public void rollback(Savepoint savepoint) throws SQLException {
/* 159 */     if (!haveMinimumServerVersion("8.0")) {
/* 160 */       throw new PSQLException(GT.tr("Server versions prior to 8.0 do not support savepoints."), PSQLState.NOT_IMPLEMENTED);
/*     */     }
/* 162 */     PSQLSavepoint pgSavepoint = (PSQLSavepoint)savepoint;
/* 163 */     execSQLUpdate("ROLLBACK TO SAVEPOINT " + pgSavepoint.getPGName());
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
/*     */   public void releaseSavepoint(Savepoint savepoint) throws SQLException {
/* 180 */     if (!haveMinimumServerVersion("8.0")) {
/* 181 */       throw new PSQLException(GT.tr("Server versions prior to 8.0 do not support savepoints."), PSQLState.NOT_IMPLEMENTED);
/*     */     }
/* 183 */     PSQLSavepoint pgSavepoint = (PSQLSavepoint)savepoint;
/* 184 */     execSQLUpdate("RELEASE SAVEPOINT " + pgSavepoint.getPGName());
/* 185 */     pgSavepoint.invalidate();
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
/*     */   public abstract Statement createStatement(int paramInt1, int paramInt2, int paramInt3) throws SQLException;
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
/*     */   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
/* 223 */     return createStatement(resultSetType, resultSetConcurrency, getHoldability());
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
/*     */   public abstract PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2, int paramInt3) throws SQLException;
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
/*     */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/* 266 */     return prepareStatement(sql, resultSetType, resultSetConcurrency, getHoldability());
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
/*     */   public abstract CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2, int paramInt3) throws SQLException;
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
/*     */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/* 308 */     return prepareCall(sql, resultSetType, resultSetConcurrency, getHoldability());
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
/*     */   public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
/* 351 */     if (autoGeneratedKeys != 2)
/* 352 */       throw new PSQLException(GT.tr("Returning autogenerated keys is not supported."), PSQLState.NOT_IMPLEMENTED); 
/* 353 */     return prepareStatement(sql);
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
/*     */   public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
/* 399 */     if (columnIndexes.length != 0)
/* 400 */       throw new PSQLException(GT.tr("Returning autogenerated keys is not supported."), PSQLState.NOT_IMPLEMENTED); 
/* 401 */     return prepareStatement(sql);
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
/*     */   public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
/* 447 */     if (columnNames.length != 0)
/* 448 */       throw new PSQLException(GT.tr("Returning autogenerated keys is not supported."), PSQLState.NOT_IMPLEMENTED); 
/* 449 */     return prepareStatement(sql);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\AbstractJdbc3Connection.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */