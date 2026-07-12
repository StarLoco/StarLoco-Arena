/*    */ package org.postgresql.core.types;
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
/*    */ public class PGUnknown
/*    */   implements PGType
/*    */ {
/*    */   Object val;
/*    */   
/*    */   public PGUnknown(Object x) {
/* 24 */     this.val = x;
/*    */   }
/*    */   
/*    */   public static PGType castToServerType(Object val, int targetType) {
/* 28 */     return new PGUnknown(val);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     return this.val.toString();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\types\PGUnknown.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */