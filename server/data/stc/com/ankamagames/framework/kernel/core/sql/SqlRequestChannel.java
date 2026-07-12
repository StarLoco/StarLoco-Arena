/*     */ package com.ankamagames.framework.kernel.core.sql;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.framework.kernel.core.monitor.Monitored;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import gnu.trove.TIntObjectIterator;
/*     */ import gnu.trove.TLinkedList;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public final class SqlRequestChannel
/*     */   extends Thread
/*     */   implements Monitored
/*     */ {
/*  31 */   protected static final Logger m_logger = Logger.getLogger(SqlRequestChannel.class);
/*     */   
/*     */ 
/*     */   private static final int MAX_REQUEST_TRIES = 10;
/*     */   
/*     */ 
/*     */   private static final long MAX_CHANNEL_IDLE = 5000L;
/*     */   
/*     */ 
/*     */   protected final DataSource m_dataSource;
/*     */   
/*     */   protected Connection m_connection;
/*     */   
/*     */   protected final TLinkedList<SqlRequest> m_requests;
/*     */   
/*  46 */   protected final Object m_requestsMutex = new Object();
/*     */   
/*     */   public boolean m_running;
/*     */   
/*     */   public int m_pendingRequestsCount;
/*     */   
/*     */   protected int m_externalId;
/*     */   
/*     */   protected String m_externalName;
/*     */   
/*     */   public int m_numRaisedExceptions;
/*     */   
/*     */   private long m_channelLastRequestTime;
/*     */   
/*     */   private TIntObjectHashMap<PreparedStatement> m_statements;
/*     */   
/*     */   public SqlRequestChannel(DataSource dataSource)
/*     */   {
/*  64 */     super.setName("SqlRequestChannel");
/*  65 */     this.m_dataSource = dataSource;
/*  66 */     this.m_connection = null;
/*  67 */     this.m_requests = new TLinkedList();
/*  68 */     this.m_running = false;
/*  69 */     this.m_pendingRequestsCount = 0;
/*  70 */     this.m_numRaisedExceptions = 0;
/*  71 */     this.m_statements = new TIntObjectHashMap();
/*     */   }
/*     */   
/*     */   public void start() {
/*  75 */     this.m_running = true;
/*  76 */     super.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void pushRequest(SqlRequest request)
/*     */   {
/*  85 */     if (request != null) {
/*  86 */       synchronized (this.m_requestsMutex) {
/*  87 */         this.m_requests.add(request);
/*  88 */         this.m_pendingRequestsCount += 1;
/*  89 */         this.m_requestsMutex.notifyAll();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private SqlRequest getNextRequest()
/*     */     throws Exception
/*     */   {
/* 102 */     SqlRequest request = null;
/*     */     
/* 104 */     synchronized (this.m_requestsMutex) {
/* 105 */       if (!this.m_requests.isEmpty()) {
/* 106 */         request = (SqlRequest)this.m_requests.getFirst();
/*     */       } else {
/* 108 */         this.m_requestsMutex.wait(1000L);
/*     */       }
/*     */     }
/* 111 */     return request;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeRequest(SqlRequest request)
/*     */   {
/* 120 */     synchronized (this.m_requestsMutex) {
/* 121 */       if ((!this.m_requests.isEmpty()) && 
/* 122 */         (this.m_requests.remove(request))) {
/* 123 */         this.m_pendingRequestsCount -= 1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getPendingRequestsCount()
/*     */   {
/* 133 */     return this.m_pendingRequestsCount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Connection getConnection()
/*     */   {
/* 142 */     return this.m_connection;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void putStatement(int id, PreparedStatement stmt)
/*     */   {
/* 153 */     this.m_statements.put(id, stmt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final PreparedStatement getStatement(int id)
/*     */   {
/* 163 */     return (PreparedStatement)this.m_statements.get(id);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void releaseStatements()
/*     */   {
/* 170 */     TIntObjectIterator it = this.m_statements.iterator();
/* 171 */     while (it.hasNext()) {
/* 172 */       it.advance();
/*     */       try {
/* 174 */         ((PreparedStatement)it.value()).close();
/*     */       } catch (SQLException e) {
/* 176 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 179 */     this.m_statements.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/* 187 */     m_logger.info("SqlRequestChannel [" + this.m_externalName + "] started ");
/*     */     
/* 189 */     int requestTries = 0;
/*     */     try
/*     */     {
/* 192 */       while (this.m_running)
/*     */       {
/*     */ 
/* 195 */         Thread.yield();
/*     */         
/* 197 */         SqlRequest request = getNextRequest();
/* 198 */         long now = System.currentTimeMillis();
/*     */         
/* 200 */         if (request != null)
/*     */         {
/* 202 */           this.m_channelLastRequestTime = now;
/*     */           
/*     */           try
/*     */           {
/* 206 */             requestTries++; if (requestTries < 10)
/*     */             {
/* 208 */               if (this.m_connection == null) {
/* 209 */                 this.m_connection = this.m_dataSource.getConnection();
/* 210 */                 releaseStatements();
/* 211 */               } else if (this.m_connection.isClosed()) {
/* 212 */                 this.m_connection.close();
/* 213 */                 this.m_connection = this.m_dataSource.getConnection();
/* 214 */                 releaseStatements();
/*     */               }
/*     */               
/* 217 */               if ((request.isRecipientValid()) || (!request.hasRecipient()))
/*     */               {
/* 219 */                 Message message = request.execute(this);
/* 220 */                 if (message != null) {
/* 221 */                   message.setHandler(request.getRecipient());
/* 222 */                   Worker.getInstance().pushMessage(message);
/*     */                 }
/*     */               } else {
/* 225 */                 m_logger.warn("[" + this.m_externalName + "] Le destinataire de la réponse à cette requete n'est plus valide : requestType = " + request.getClass().getSimpleName());
/*     */               }
/*     */             } else {
/* 228 */               m_logger.warn("[" + this.m_externalName + "] Request (" + request.getClass().getSimpleName() + ")canceled (too much retries) : " + request);
/*     */             }
/*     */             
/*     */ 
/* 232 */             removeRequest(request);
/* 233 */             request.release();
/* 234 */             requestTries = 0;
/*     */           }
/*     */           catch (SQLException ex) {
/* 237 */             m_logger.error("[" + this.m_externalName + "] SQLException levée lors de l'éxécution d'une requête de type : " + request.getClass().getSimpleName(), ex);
/* 238 */             this.m_numRaisedExceptions += 1;
/*     */           }
/*     */           catch (Throwable ex) {
/* 241 */             m_logger.error("[" + this.m_externalName + "] Throwable capté lors de l'éxécution d'une requête de type : " + request.getClass().getSimpleName(), ex);
/* 242 */             this.m_numRaisedExceptions += 1;
/*     */           }
/*     */           
/*     */         }
/* 246 */         else if ((this.m_connection != null) && (!this.m_connection.isClosed()) && 
/* 247 */           (now - this.m_channelLastRequestTime >= 5000L) && (this.m_pendingRequestsCount == 0)) {
/* 248 */           m_logger.info("[" + this.m_externalName + "] Fermeture de la connexion (cause IDLE > " + 5000L + " ms) - Pas de requêtes en attente.");
/* 249 */           this.m_connection.close();
/* 250 */           this.m_connection = null;
/* 251 */           releaseStatements();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 258 */       if (this.m_connection != null) {
/* 259 */         this.m_connection.close();
/* 260 */         this.m_connection = null;
/*     */       }
/*     */     }
/*     */     catch (Throwable ex) {
/* 264 */       m_logger.error("[" + this.m_externalName + "] Throwable capté lors de la connexion à la base", ex);
/* 265 */       this.m_numRaisedExceptions += 1;
/*     */     }
/*     */     
/* 268 */     m_logger.info("SqlRequestChannel stopped ");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getExternalName()
/*     */   {
/* 279 */     return this.m_externalName;
/*     */   }
/*     */   
/*     */   public void setExternalName(String name) {
/* 283 */     this.m_externalName = name;
/*     */   }
/*     */   
/*     */   public int getExternalID() {
/* 287 */     return this.m_externalId;
/*     */   }
/*     */   
/*     */   public void setExternalID(int id) {
/* 291 */     this.m_externalId = id;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\sql\SqlRequestChannel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */