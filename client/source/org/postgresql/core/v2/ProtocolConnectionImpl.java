/*     */ package org.postgresql.core.v2;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.util.ArrayList;
/*     */ import org.postgresql.Driver;
/*     */ import org.postgresql.PGNotification;
/*     */ import org.postgresql.core.Encoding;
/*     */ import org.postgresql.core.PGStream;
/*     */ import org.postgresql.core.ProtocolConnection;
/*     */ import org.postgresql.core.QueryExecutor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ProtocolConnectionImpl
/*     */   implements ProtocolConnection
/*     */ {
/*     */   private String serverVersion;
/*     */   private int cancelPid;
/*     */   private int cancelKey;
/*     */   private int transactionState;
/*     */   private SQLWarning warnings;
/*     */   private boolean closed;
/*     */   private final ArrayList notifications;
/*     */   private final PGStream pgStream;
/*     */   private final String user;
/*     */   private final String database;
/*     */   private final QueryExecutorImpl executor;
/*     */   
/*     */   ProtocolConnectionImpl(PGStream pgStream, String user, String database) {
/* 203 */     this.closed = false;
/*     */     
/* 205 */     this.notifications = new ArrayList();
/*     */     this.pgStream = pgStream;
/*     */     this.user = user;
/*     */     this.database = database;
/*     */     this.executor = new QueryExecutorImpl(this, pgStream);
/*     */   }
/*     */   
/*     */   public String getHost() {
/*     */     return this.pgStream.getHost();
/*     */   }
/*     */   
/*     */   public int getPort() {
/*     */     return this.pgStream.getPort();
/*     */   }
/*     */   
/*     */   public String getUser() {
/*     */     return this.user;
/*     */   }
/*     */   
/*     */   public String getDatabase() {
/*     */     return this.database;
/*     */   }
/*     */   
/*     */   public String getServerVersion() {
/*     */     return this.serverVersion;
/*     */   }
/*     */   
/*     */   public synchronized int getTransactionState() {
/*     */     return this.transactionState;
/*     */   }
/*     */   
/*     */   public synchronized PGNotification[] getNotifications() throws SQLException {
/*     */     this.executor.processNotifies();
/*     */     PGNotification[] array = (PGNotification[])this.notifications.toArray((Object[])new PGNotification[this.notifications.size()]);
/*     */     this.notifications.clear();
/*     */     return array;
/*     */   }
/*     */   
/*     */   public synchronized SQLWarning getWarnings() {
/*     */     SQLWarning chain = this.warnings;
/*     */     this.warnings = null;
/*     */     return chain;
/*     */   }
/*     */   
/*     */   public QueryExecutor getQueryExecutor() {
/*     */     return this.executor;
/*     */   }
/*     */   
/*     */   public void sendQueryCancel() throws SQLException {
/*     */     if (this.cancelPid <= 0)
/*     */       return; 
/*     */     PGStream cancelStream = null;
/*     */     try {
/*     */       if (Driver.logDebug)
/*     */         Driver.debug(" FE=> CancelRequest(pid=" + this.cancelPid + ",ckey=" + this.cancelKey + ")"); 
/*     */       cancelStream = new PGStream(this.pgStream.getHost(), this.pgStream.getPort());
/*     */       cancelStream.SendInteger4(16);
/*     */       cancelStream.SendInteger2(1234);
/*     */       cancelStream.SendInteger2(5678);
/*     */       cancelStream.SendInteger4(this.cancelPid);
/*     */       cancelStream.SendInteger4(this.cancelKey);
/*     */       cancelStream.flush();
/*     */       cancelStream.ReceiveEOF();
/*     */       cancelStream.close();
/*     */       cancelStream = null;
/*     */     } catch (IOException e) {
/*     */       if (Driver.logDebug)
/*     */         Driver.debug("Ignoring exception on cancel request:", e); 
/*     */     } finally {
/*     */       if (cancelStream != null)
/*     */         try {
/*     */           cancelStream.close();
/*     */         } catch (IOException e) {} 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() {
/*     */     if (this.closed)
/*     */       return; 
/*     */     try {
/*     */       if (Driver.logDebug)
/*     */         Driver.debug(" FE=> Terminate"); 
/*     */       this.pgStream.SendChar(88);
/*     */       this.pgStream.flush();
/*     */       this.pgStream.close();
/*     */     } catch (IOException ioe) {
/*     */       if (Driver.logDebug)
/*     */         Driver.debug("Discarding IOException on close:", ioe); 
/*     */     } 
/*     */     this.closed = true;
/*     */   }
/*     */   
/*     */   public Encoding getEncoding() {
/*     */     return this.pgStream.getEncoding();
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/*     */     return this.closed;
/*     */   }
/*     */   
/*     */   void setEncoding(Encoding encoding) throws IOException {
/*     */     this.pgStream.setEncoding(encoding);
/*     */   }
/*     */   
/*     */   void setServerVersion(String serverVersion) {
/*     */     this.serverVersion = serverVersion;
/*     */   }
/*     */   
/*     */   void setBackendKeyData(int cancelPid, int cancelKey) {
/*     */     this.cancelPid = cancelPid;
/*     */     this.cancelKey = cancelKey;
/*     */   }
/*     */   
/*     */   synchronized void addWarning(SQLWarning newWarning) {
/*     */     if (this.warnings == null) {
/*     */       this.warnings = newWarning;
/*     */     } else {
/*     */       this.warnings.setNextWarning(newWarning);
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized void addNotification(PGNotification notification) {
/*     */     this.notifications.add(notification);
/*     */   }
/*     */   
/*     */   synchronized void setTransactionState(int state) {
/*     */     this.transactionState = state;
/*     */   }
/*     */   
/*     */   public int getProtocolVersion() {
/*     */     return 2;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\v2\ProtocolConnectionImpl.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */