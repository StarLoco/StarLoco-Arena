/*    */ package org.postgresql.ds;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.Serializable;
/*    */ import javax.sql.DataSource;
/*    */ import org.postgresql.Driver;
/*    */ import org.postgresql.ds.common.BaseDataSource;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PGSimpleDataSource
/*    */   extends BaseDataSource
/*    */   implements Serializable, DataSource
/*    */ {
/*    */   public String getDescription() {
/* 35 */     return "Non-Pooling DataSource from " + Driver.getVersion();
/*    */   }
/*    */ 
/*    */   
/*    */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 40 */     writeBaseObject(out);
/*    */   }
/*    */ 
/*    */   
/*    */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 45 */     readBaseObject(in);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\ds\PGSimpleDataSource.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */