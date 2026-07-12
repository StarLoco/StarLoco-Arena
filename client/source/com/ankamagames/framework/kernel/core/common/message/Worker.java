/*     */ package com.ankamagames.framework.kernel.core.common.message;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.monitor.Monitored;
/*     */ import com.ankamagames.framework.kernel.utils.ExceptionFormatter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.media.opengl.GLException;
/*     */ import javax.media.opengl.Threading;
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
/*     */ public final class Worker
/*     */   extends Thread
/*     */   implements Monitored
/*     */ {
/*  28 */   private static final Logger m_logger = Logger.getLogger(Worker.class);
/*     */   
/*  30 */   protected static final Worker m_instance = new Worker();
/*     */   
/*     */   public boolean m_running;
/*  33 */   protected final LinkedList<Message> m_messages = new LinkedList<Message>();
/*  34 */   protected final Object m_messagesMutex = new Object();
/*     */   
/*     */   private final List<String> m_lastExceptions;
/*     */   
/*     */   private int m_externalId;
/*     */   
/*     */   public int m_numPendingMessages;
/*     */   
/*     */   public int m_numMaxPendingMessages;
/*     */   
/*     */   public int m_numProcessedMessages;
/*     */   
/*     */   public int m_numErroneousMessages;
/*     */   public int m_numRaisedExceptions;
/*     */   public final ArrayList<String> m_stackTraceReport;
/*     */   private boolean m_standAloneThread = true;
/*     */   
/*     */   private Worker() {
/*  52 */     this.m_running = false;
/*  53 */     setName("Worker");
/*     */     
/*  55 */     this.m_lastExceptions = Collections.synchronizedList(new ArrayList<String>());
/*  56 */     this.m_stackTraceReport = new ArrayList<String>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Worker getInstance() {
/*  64 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  71 */     if (!this.m_running) {
/*  72 */       this.m_running = true;
/*  73 */       final Worker workerThread = this;
/*     */       
/*  75 */       (new Thread(new Runnable() {
/*     */             public void run() {
/*  77 */               Worker.this.setName("Worker");
/*  78 */               Worker.m_logger.info("Worker running");
/*  79 */               while (workerThread.m_running) {
/*  80 */                 Thread.yield();
/*  81 */                 workerThread.run();
/*     */               } 
/*     */             }
/*  84 */           })).start();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void startInOpenGLThread() {
/*  90 */     if (!this.m_running) {
/*  91 */       this.m_running = true;
/*  92 */       this.m_standAloneThread = false;
/*  93 */       final Worker workerThread = this;
/*     */       
/*  95 */       (new Thread(new Runnable() {
/*     */             public void run() {
/*  97 */               Worker.this.setName("Worker (in OpenGL thread)");
/*  98 */               Worker.m_logger.info("Worker running");
/*  99 */               while (workerThread.m_running) {
/* 100 */                 Thread.yield();
/*     */                 try {
/* 102 */                   Threading.invokeOnOpenGLThread(workerThread);
/* 103 */                 } catch (GLException e) {
/* 104 */                   e.printStackTrace();
/*     */                 }
/*     */               
/*     */               } 
/*     */             }
/* 109 */           })).start();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushMessage(Message message) {
/* 118 */     if (message != null) {
/*     */       
/* 120 */       message.m_pushTime = System.currentTimeMillis();
/*     */       
/* 122 */       synchronized (this.m_messagesMutex) {
/* 123 */         this.m_messages.addLast(message);
/* 124 */         this.m_messagesMutex.notifyAll();
/*     */         
/* 126 */         this.m_numPendingMessages++;
/* 127 */         if (this.m_numPendingMessages > this.m_numMaxPendingMessages) {
/* 128 */           this.m_numMaxPendingMessages = this.m_numPendingMessages;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPendingMessageCount() {
/* 138 */     return this.m_numPendingMessages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxPendingMessageCount() {
/* 146 */     return this.m_numMaxPendingMessages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetStatisticsCounters() {
/* 154 */     this.m_numMaxPendingMessages = 0;
/* 155 */     this.m_numProcessedMessages = 0;
/* 156 */     this.m_numErroneousMessages = 0;
/* 157 */     this.m_numRaisedExceptions = 0;
/*     */     
/* 159 */     this.m_lastExceptions.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNbProcessedMessages() {
/* 168 */     return this.m_numProcessedMessages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNbErroneousMessages() {
/* 177 */     return this.m_numErroneousMessages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNbRaisedException() {
/* 186 */     return this.m_numRaisedExceptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getLastRaisedExceptions() {
/* 194 */     synchronized (this.m_lastExceptions) {
/* 195 */       return this.m_lastExceptions.toArray();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRunning() {
/* 204 */     return this.m_running;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void wakeUp() {
/* 211 */     synchronized (this.m_messagesMutex) {
/* 212 */       this.m_messagesMutex.notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 221 */     if (!this.m_standAloneThread) {
/*     */       try {
/* 223 */         ProcessScheduler.getInstance().update();
/* 224 */         processMessages();
/* 225 */       } catch (Exception e) {
/* 226 */         e.printStackTrace();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 232 */       Message message = null;
/*     */       
/* 234 */       ProcessScheduler.getInstance().update();
/*     */       
/* 236 */       synchronized (this.m_messagesMutex) {
/* 237 */         if (!this.m_messages.isEmpty()) {
/* 238 */           message = this.m_messages.removeFirst();
/* 239 */           this.m_numPendingMessages--;
/* 240 */           if (message == null)
/* 241 */             this.m_numErroneousMessages++; 
/*     */         } else {
/* 243 */           long timeToWait = ProcessScheduler.getInstance().getMaximalSleepTime();
/* 244 */           if (timeToWait != 0L) {
/* 245 */             this.m_messagesMutex.wait(timeToWait);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       try {
/* 250 */         if (message != null) {
/* 251 */           message.execute();
/* 252 */           message.release();
/* 253 */           this.m_numProcessedMessages++;
/*     */         }
/*     */       
/* 256 */       } catch (Throwable ex) {
/* 257 */         if (message != null)
/* 258 */           message.release(); 
/* 259 */         storeException(ex);
/*     */       }
/*     */     
/* 262 */     } catch (Throwable ex) {
/* 263 */       storeException(ex);
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
/*     */   protected void processMessages() throws Exception {
/* 275 */     Message message = null;
/*     */     
/* 277 */     while (!this.m_messages.isEmpty()) {
/*     */       
/* 279 */       Thread.yield();
/*     */       
/* 281 */       synchronized (this.m_messagesMutex) {
/* 282 */         if (!this.m_messages.isEmpty()) {
/* 283 */           message = this.m_messages.removeFirst();
/* 284 */           this.m_numPendingMessages--;
/* 285 */           if (message == null) {
/* 286 */             this.m_numErroneousMessages++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*     */       try {
/* 292 */         if (message != null) {
/* 293 */           message.execute();
/* 294 */           message.release();
/* 295 */           this.m_numProcessedMessages++;
/*     */         }
/*     */       
/* 298 */       } catch (Throwable ex) {
/* 299 */         if (message != null)
/* 300 */           message.release(); 
/* 301 */         storeException(ex);
/*     */       } 
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
/*     */   private void storeException(Throwable ex) {
/* 314 */     m_logger.error("Exception catchée dans le Worker : ", ex);
/* 315 */     StringWriter strException = ExceptionFormatter.toString(ex);
/*     */     
/* 317 */     this.m_numRaisedExceptions++;
/* 318 */     synchronized (this.m_lastExceptions) {
/* 319 */       if (this.m_numRaisedExceptions >= 10) {
/* 320 */         this.m_lastExceptions.remove(0);
/*     */       }
/* 322 */       this.m_lastExceptions.add(strException.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExternalName() {
/* 331 */     return "Worker";
/*     */   }
/*     */   
/*     */   public int getExternalID() {
/* 335 */     return this.m_externalId;
/*     */   }
/*     */   
/*     */   public void setExternalID(int id) {
/* 339 */     this.m_externalId = id;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\message\Worker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */