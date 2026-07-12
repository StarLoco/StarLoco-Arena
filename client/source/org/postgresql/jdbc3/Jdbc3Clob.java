/*    */ package org.postgresql.jdbc3;
/*    */ 
/*    */ import java.sql.Clob;
/*    */ import java.sql.SQLException;
/*    */ import org.postgresql.PGConnection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Jdbc3Clob
/*    */   extends AbstractJdbc3Clob
/*    */   implements Clob
/*    */ {
/*    */   public Jdbc3Clob(PGConnection conn, int oid) throws SQLException {
/* 18 */     super(conn, oid);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\Jdbc3Clob.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */