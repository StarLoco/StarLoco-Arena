/*    */ package org.postgresql.util;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.sql.SQLException;
/*    */ import org.postgresql.Driver;
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
/*    */ public class PSQLException
/*    */   extends SQLException
/*    */ {
/*    */   private ServerErrorMessage _serverError;
/*    */   
/*    */   public PSQLException(String msg, PSQLState state, Throwable cause) {
/* 25 */     super(addCauseToMessage(msg, cause), (state == null) ? null : state.getState());
/* 26 */     initCause(cause);
/* 27 */     if (Driver.logDebug) {
/* 28 */       Driver.debug("Exception: " + this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public PSQLException(String msg, PSQLState state) {
/* 34 */     this(msg, state, (Throwable)null);
/*    */   }
/*    */ 
/*    */   
/*    */   public PSQLException(ServerErrorMessage serverError) {
/* 39 */     this(serverError.toString(), new PSQLState(serverError.getSQLState()));
/* 40 */     this._serverError = serverError;
/*    */   }
/*    */ 
/*    */   
/*    */   public ServerErrorMessage getServerErrorMessage() {
/* 45 */     return this._serverError;
/*    */   }
/*    */   
/*    */   private static String addCauseToMessage(String msg, Throwable cause) {
/* 49 */     boolean hasInitCause = true;
/*    */     
/* 51 */     if (!hasInitCause && cause != null) {
/*    */       
/*    */       try {
/*    */         
/* 55 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 56 */         PrintWriter pw = new PrintWriter(baos);
/* 57 */         pw.println();
/* 58 */         pw.println(GT.tr("Exception: {0}", cause.toString()));
/* 59 */         pw.println(GT.tr("Stack Trace:"));
/* 60 */         cause.printStackTrace(pw);
/* 61 */         pw.println(GT.tr("End of Stack Trace"));
/* 62 */         pw.flush();
/* 63 */         msg = msg + baos.toString();
/* 64 */         pw.close();
/* 65 */         baos.close();
/*    */       }
/*    */       catch (IOException ioe) {
/*    */         
/* 69 */         msg = msg + GT.tr("Exception generating stacktrace for: {0} encountered: {1}", new Object[] { cause.toString(), ioe.toString() });
/*    */       } 
/*    */     }
/* 72 */     return msg;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresq\\util\PSQLException.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */