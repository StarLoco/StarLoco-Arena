/*    */ package org.postgresql.jdbc3;
/*    */ 
/*    */ import java.sql.Blob;
/*    */ import java.sql.SQLException;
/*    */ import org.postgresql.PGConnection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Jdbc3Blob
/*    */   extends AbstractJdbc3Blob
/*    */   implements Blob
/*    */ {
/*    */   public Jdbc3Blob(PGConnection conn, int oid) throws SQLException {
/* 20 */     super(conn, oid);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\Jdbc3Blob.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */