/*    */ package org.postgresql.jdbc3;
/*    */ 
/*    */ import java.sql.ParameterMetaData;
/*    */ import java.sql.SQLException;
/*    */ import org.postgresql.core.BaseConnection;
/*    */ import org.postgresql.util.GT;
/*    */ import org.postgresql.util.PSQLException;
/*    */ import org.postgresql.util.PSQLState;
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
/*    */ public class PSQLParameterMetaData
/*    */   implements ParameterMetaData
/*    */ {
/*    */   private final BaseConnection _connection;
/*    */   private final int[] _oids;
/*    */   
/*    */   public PSQLParameterMetaData(BaseConnection connection, int[] oids) {
/* 26 */     this._connection = connection;
/* 27 */     this._oids = oids;
/*    */   }
/*    */   
/*    */   public String getParameterClassName(int param) throws SQLException {
/* 31 */     checkParamIndex(param);
/* 32 */     return this._connection.getJavaClass(this._oids[param - 1]);
/*    */   }
/*    */   
/*    */   public int getParameterCount() {
/* 36 */     return this._oids.length;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getParameterMode(int param) throws SQLException {
/* 42 */     checkParamIndex(param);
/* 43 */     return 1;
/*    */   }
/*    */   
/*    */   public int getParameterType(int param) throws SQLException {
/* 47 */     checkParamIndex(param);
/* 48 */     return this._connection.getSQLType(this._oids[param - 1]);
/*    */   }
/*    */   
/*    */   public String getParameterTypeName(int param) throws SQLException {
/* 52 */     checkParamIndex(param);
/* 53 */     return this._connection.getPGType(this._oids[param - 1]);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPrecision(int param) throws SQLException {
/* 58 */     checkParamIndex(param);
/* 59 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getScale(int param) throws SQLException {
/* 64 */     checkParamIndex(param);
/* 65 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int isNullable(int param) throws SQLException {
/* 70 */     checkParamIndex(param);
/* 71 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSigned(int param) throws SQLException {
/* 76 */     checkParamIndex(param);
/* 77 */     return true;
/*    */   }
/*    */   
/*    */   private void checkParamIndex(int param) throws PSQLException {
/* 81 */     if (param < 1 || param > this._oids.length)
/* 82 */       throw new PSQLException(GT.tr("The parameter index is out of range: {0}, number of parameters: {1}.", new Object[] { new Integer(param), new Integer(this._oids.length) }), PSQLState.INVALID_PARAMETER_VALUE); 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\PSQLParameterMetaData.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */