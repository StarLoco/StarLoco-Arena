/*    */ package org.postgresql.jdbc2;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import org.postgresql.PGConnection;
/*    */ import org.postgresql.largeobject.LargeObject;
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
/*    */ 
/*    */ public abstract class AbstractJdbc2Blob
/*    */   extends AbstractJdbc2BlobClob
/*    */ {
/*    */   private LargeObject lo;
/*    */   
/*    */   public AbstractJdbc2Blob(PGConnection conn, int oid) throws SQLException {
/* 23 */     super(conn, oid);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc2\AbstractJdbc2Blob.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */