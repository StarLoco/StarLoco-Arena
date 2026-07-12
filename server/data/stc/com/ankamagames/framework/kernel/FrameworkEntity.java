/*     */ package com.ankamagames.framework.kernel;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.net.Connection;
/*     */ import com.ankamagames.framework.kernel.core.net.ConnectionHandler;
/*     */ import com.ankamagames.framework.kernel.core.net.ConnectionUser;
/*     */ import com.ankamagames.framework.kernel.core.net.ConnectionWriter;
/*     */ import com.ankamagames.framework.kernel.utils.ExceptionFormatter;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FrameworkEntity
/*     */   extends FrameHandler
/*     */   implements ConnectionUser, Poolable
/*     */ {
/*     */   private static final boolean DEBUG_MODE = false;
/*  25 */   protected static final Logger m_logger = Logger.getLogger(FrameworkEntity.class);
/*     */   
/*     */ 
/*     */   protected Connection m_connection;
/*     */   
/*     */   protected ObjectPool m_pool;
/*     */   
/*     */ 
/*     */   public FrameworkEntity()
/*     */   {
/*  35 */     this.m_pool = null;
/*  36 */     setRunningFrame(false);
/*     */   }
/*     */   
/*     */   public void onCheckOut()
/*     */   {
/*  41 */     setRunningFrame(false);
/*  42 */     this.m_connection = null;
/*  43 */     this.m_pool = null;
/*     */   }
/*     */   
/*     */   public void onCheckIn() {
/*  47 */     this.m_connection = null;
/*  48 */     this.m_pool = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onConnect() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onReconnect()
/*     */   {
/*  67 */     System.out.println("FrameworkEntity::onReConnect()");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onDisconnect() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onReconnectionPending() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onConnectionRecovered() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPool(ObjectPool pool)
/*     */   {
/*  92 */     this.m_pool = pool;
/*     */   }
/*     */   
/*     */   public ObjectPool getPool() {
/*  96 */     return this.m_pool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void release()
/*     */   {
/* 104 */     setId(0L);
/* 105 */     if (this.m_pool != null) {
/*     */       try {
/* 107 */         this.m_pool.returnObject(this);
/*     */       } catch (Exception e) {
/* 109 */         m_logger.error("Exception levée lors de la libération d'une FrameworkEntity : ", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setConnection(Connection connection)
/*     */   {
/* 120 */     this.m_connection = connection;
/*     */   }
/*     */   
/*     */   public Connection getConnection() {
/* 124 */     return this.m_connection;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void flushAndCloseConnection()
/*     */   {
/* 131 */     if (this.m_connection != null) {
/* 132 */       this.m_connection.cleanClose();
/* 133 */       ConnectionWriter.getInstance().pushConnection(this.m_connection);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void closeConnection()
/*     */   {
/* 141 */     if (this.m_connection != null)
/*     */     {
/* 143 */       this.m_connection.getConnectionHandler().closeConnection(this.m_connection);
/* 144 */       this.m_connection = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void acquireConnection(FrameworkEntity entity)
/*     */   {
/* 156 */     if (entity == null) {
/* 157 */       return;
/*     */     }
/* 159 */     if (this.m_connection != null) {
/* 160 */       m_logger.info("Fermeture de l'ancienne connection du FrameworkEntity");
/*     */       
/* 162 */       this.m_connection.getConnectionHandler().closeConnection(this.m_connection);
/* 163 */       this.m_connection = null;
/*     */     }
/* 165 */     Connection conn = entity.m_connection;
/* 166 */     if (conn != null)
/*     */     {
/*     */ 
/* 169 */       this.m_connection = conn;
/* 170 */       this.m_connection.setUser(this);
/* 171 */       entity.setConnection(null);
/*     */     }
/*     */   }
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
/*     */   public boolean isConnected()
/*     */   {
/* 194 */     return (this.m_connection != null) && (!this.m_connection.isCleanClose()) && (!this.m_connection.isAboutToClose());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void sendMessage(Message message)
/*     */   {
/* 204 */     if (this.m_connection != null) {
/*     */       try {
/* 206 */         this.m_connection.pushMessage(message.encode());
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 211 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 216 */       message.release();
/*     */     }
/*     */     catch (Exception e) {
/* 219 */       if (this.m_connection != null) {
/* 220 */         ConnectionHandler ch = this.m_connection.getConnectionHandler();
/* 221 */         if (ch != null) {
/* 222 */           ch.storeException(e);
/*     */         } else
/* 224 */           m_logger.error(ExceptionFormatter.toString(e));
/*     */       } else {
/* 226 */         m_logger.error(ExceptionFormatter.toString(e));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void sendRawMessage(byte[] rawMessage)
/*     */   {
/* 237 */     if (this.m_connection != null) {
/* 238 */       this.m_connection.pushMessage(rawMessage);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\FrameworkEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */