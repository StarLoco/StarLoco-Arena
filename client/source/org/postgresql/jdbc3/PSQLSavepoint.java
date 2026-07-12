/*    */ package org.postgresql.jdbc3;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Savepoint;
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
/*    */ public class PSQLSavepoint
/*    */   implements Savepoint
/*    */ {
/*    */   private boolean _isValid;
/*    */   private boolean _isNamed;
/*    */   private int _id;
/*    */   private String _name;
/*    */   
/*    */   public PSQLSavepoint(int id) {
/* 26 */     this._isValid = true;
/* 27 */     this._isNamed = false;
/* 28 */     this._id = id;
/*    */   }
/*    */   
/*    */   public PSQLSavepoint(String name) {
/* 32 */     this._isValid = true;
/* 33 */     this._isNamed = true;
/* 34 */     this._name = name;
/*    */   }
/*    */   
/*    */   public int getSavepointId() throws SQLException {
/* 38 */     if (!this._isValid) {
/* 39 */       throw new PSQLException(GT.tr("Cannot reference a savepoint after it has been released."), PSQLState.INVALID_SAVEPOINT_SPECIFICATION);
/*    */     }
/*    */     
/* 42 */     if (this._isNamed) {
/* 43 */       throw new PSQLException(GT.tr("Cannot retrieve the id of a named savepoint."), PSQLState.WRONG_OBJECT_TYPE);
/*    */     }
/*    */     
/* 46 */     return this._id;
/*    */   }
/*    */   
/*    */   public String getSavepointName() throws SQLException {
/* 50 */     if (!this._isValid) {
/* 51 */       throw new PSQLException(GT.tr("Cannot reference a savepoint after it has been released."), PSQLState.INVALID_SAVEPOINT_SPECIFICATION);
/*    */     }
/*    */     
/* 54 */     if (!this._isNamed) {
/* 55 */       throw new PSQLException(GT.tr("Cannot retrieve the name of an unnamed savepoint."), PSQLState.WRONG_OBJECT_TYPE);
/*    */     }
/*    */     
/* 58 */     return this._name;
/*    */   }
/*    */   
/*    */   public void invalidate() {
/* 62 */     this._isValid = false;
/*    */   }
/*    */   
/*    */   public String getPGName() throws SQLException {
/* 66 */     if (!this._isValid) {
/* 67 */       throw new PSQLException(GT.tr("Cannot reference a savepoint after it has been released."), PSQLState.INVALID_SAVEPOINT_SPECIFICATION);
/*    */     }
/*    */     
/* 70 */     if (this._isNamed) {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 75 */       StringBuffer sb = new StringBuffer(this._name.length() + 2);
/* 76 */       sb.append("\"");
/* 77 */       for (int i = 0; i < this._name.length(); i++) {
/*    */         
/* 79 */         char c = this._name.charAt(i);
/* 80 */         if (c == '\\' || c == '"')
/* 81 */           sb.append(c); 
/* 82 */         sb.append(c);
/*    */       } 
/* 84 */       sb.append("\"");
/* 85 */       return sb.toString();
/*    */     } 
/*    */     
/* 88 */     return "JDBC_SAVEPOINT_" + this._id;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\PSQLSavepoint.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */