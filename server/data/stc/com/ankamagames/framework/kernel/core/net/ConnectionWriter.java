/*     */ package com.ankamagames.framework.kernel.core.net;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.utils.ExceptionFormatter;
/*     */ import gnu.trove.TLinkedList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.pool.ObjectPool;
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
/*     */ public final class ConnectionWriter
/*     */   extends Thread
/*     */ {
/*  25 */   protected static final Logger m_logger = Logger.getLogger(ConnectionWriter.class);
/*     */   
/*  27 */   protected static final ConnectionWriter m_instance = new ConnectionWriter();
/*     */   
/*     */   protected boolean m_running;
/*     */   protected final ObjectPool m_connectionValidatorPool;
/*  31 */   protected final Object m_validatorsMutex = new Object();
/*     */   
/*     */   protected final TLinkedList<ConnectionValidator> m_validators;
/*     */   
/*     */   private final ArrayList<Connection> m_connectionsRequestingWrite;
/*     */   
/*     */   protected ConnectionWriter()
/*     */   {
/*  39 */     super.setName("ConnectionWriter");
/*  40 */     this.m_connectionValidatorPool = new MonitoredPool(new ConnectionValidatorPoolFactory());
/*  41 */     this.m_validators = new TLinkedList();
/*  42 */     this.m_connectionsRequestingWrite = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ConnectionWriter getInstance()
/*     */   {
/*  50 */     return m_instance;
/*     */   }
/*     */   
/*     */   public void start() {
/*  54 */     if (!this.m_running) {
/*  55 */       this.m_running = true;
/*  56 */       super.start();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isRunning() {
/*  61 */     return this.m_running;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void pushConnection(Connection connection)
/*     */   {
/*     */     try
/*     */     {
/*  70 */       if (!this.m_connectionsRequestingWrite.contains(connection)) {
/*  71 */         ConnectionValidator validator = (ConnectionValidator)this.m_connectionValidatorPool.borrowObject();
/*  72 */         if (validator != null) {
/*  73 */           validator.setup(connection);
/*  74 */           synchronized (this.m_validatorsMutex) {
/*  75 */             this.m_validators.add(validator);
/*  76 */             this.m_validatorsMutex.notifyAll();
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (Exception ex) {
/*  81 */       m_logger.error(ExceptionFormatter.toString(ex));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void run()
/*     */   {
/*  88 */     m_logger.info("ConnectionWriter running");
/*     */     
/*  90 */     while (this.m_running) {
/*     */       try
/*     */       {
/*     */         do
/*     */         {
/*  95 */           ConnectionValidator validator = null;
/*     */           
/*  97 */           Thread.yield();
/*     */           
/*  99 */           synchronized (this.m_validatorsMutex) {
/* 100 */             if (!this.m_validators.isEmpty()) {
/* 101 */               Iterator it = this.m_validators.iterator();
/* 102 */               if (it.hasNext()) {
/* 103 */                 validator = (ConnectionValidator)it.next();
/* 104 */                 this.m_connectionsRequestingWrite.remove((Connection)validator.getItem());
/* 105 */                 it.remove();
/*     */               }
/*     */             } else {
/* 108 */               this.m_validatorsMutex.wait();
/*     */             }
/*     */           }
/* 111 */           if (validator != null) {
/* 112 */             if (validator.isItemValid()) {
/* 113 */               Connection connection = (Connection)validator.getItem();
/* 114 */               if (!connection.write()) {
/* 115 */                 m_logger.warn("Des données n'ont pas pues être envoyées au destinataire : on abnadonne.");
/*     */               }
/*     */             }
/*     */             try
/*     */             {
/* 120 */               this.m_connectionValidatorPool.returnObject(validator);
/*     */             } catch (Exception ex) {
/* 122 */               m_logger.error(ExceptionFormatter.toString(ex));
/*     */             }
/*     */           }
/*  94 */         } while (this.m_running);
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
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
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
/* 128 */         m_logger.error(ExceptionFormatter.toString(ex));
/*     */       }
/*     */     }
/*     */     
/* 132 */     m_logger.info("ConnectionWriter stopped");
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\net\ConnectionWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */