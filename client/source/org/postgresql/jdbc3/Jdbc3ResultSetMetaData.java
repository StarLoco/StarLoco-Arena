/*    */ package org.postgresql.jdbc3;
/*    */ 
/*    */ import java.sql.ResultSetMetaData;
/*    */ import org.postgresql.core.BaseConnection;
/*    */ import org.postgresql.core.Field;
/*    */ import org.postgresql.jdbc2.AbstractJdbc2ResultSetMetaData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Jdbc3ResultSetMetaData
/*    */   extends AbstractJdbc2ResultSetMetaData
/*    */   implements ResultSetMetaData
/*    */ {
/*    */   public Jdbc3ResultSetMetaData(BaseConnection connection, Field[] fields) {
/* 19 */     super(connection, fields);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\Jdbc3ResultSetMetaData.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */