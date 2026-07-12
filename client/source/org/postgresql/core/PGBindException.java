/*    */ package org.postgresql.core;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PGBindException
/*    */   extends IOException
/*    */ {
/*    */   private IOException _ioe;
/*    */   
/*    */   public PGBindException(IOException ioe) {
/* 19 */     this._ioe = ioe;
/*    */   }
/*    */   
/*    */   public IOException getIOException() {
/* 23 */     return this._ioe;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\PGBindException.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */