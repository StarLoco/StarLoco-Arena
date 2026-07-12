/*    */ package org.postgresql.jdbc3;
/*    */ 
/*    */ import java.sql.Array;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Map;
/*    */ import org.postgresql.core.BaseConnection;
/*    */ import org.postgresql.core.BaseResultSet;
/*    */ import org.postgresql.core.Field;
/*    */ import org.postgresql.jdbc2.AbstractJdbc2Array;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Jdbc3Array
/*    */   extends AbstractJdbc2Array
/*    */   implements Array
/*    */ {
/*    */   public Jdbc3Array(BaseConnection conn, int idx, Field field, BaseResultSet rs) throws SQLException {
/* 21 */     super(conn, idx, field, rs);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getArray(Map map) throws SQLException {
/* 26 */     return getArrayImpl(map);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getArray(long index, int count, Map map) throws SQLException {
/* 31 */     return getArrayImpl(index, count, map);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResultSet getResultSet(Map map) throws SQLException {
/* 36 */     return getResultSetImpl(map);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResultSet getResultSet(long index, int count, Map map) throws SQLException {
/* 41 */     return getResultSetImpl(index, count, map);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\Jdbc3Array.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */