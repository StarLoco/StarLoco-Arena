/*     */ package com.ankamagames.framework.kernel.core.common.message.scheduler;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.message.MessageHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
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
/*     */ public final class MessageScheduler
/*     */   extends Thread
/*     */ {
/*     */   private static final long ROUNDING_TRIP = 3L;
/*  42 */   private static final Logger m_logger = Logger.getLogger(MessageScheduler.class);
/*     */   
/*  44 */   protected static final MessageScheduler m_instance = new MessageScheduler();
/*     */   
/*     */   protected boolean m_running;
/*     */   
/*     */   protected final ObjectPool m_clockMessagePool;
/*     */   
/*  50 */   protected final TLinkedList<SchedulerListener> m_listeners = new TLinkedList();
/*     */   
/*     */ 
/*  53 */   protected final Object m_listenersMutex = new Object();
/*     */   
/*  55 */   protected long m_lastClockId = 0L;
/*     */   
/*     */ 
/*     */ 
/*     */   private MessageScheduler()
/*     */   {
/*  61 */     super("MessageScheduler");
/*  62 */     this.m_running = false;
/*  63 */     this.m_clockMessagePool = new MonitoredPool(new ObjectFactory() {
/*  64 */       public ClockMessage makeObject() { return new ClockMessage(); }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static MessageScheduler getInstance()
/*     */   {
/*  72 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasHandler(MessageHandler messageHandler)
/*     */   {
/*  82 */     if (messageHandler == null) {
/*  83 */       return false;
/*     */     }
/*  85 */     synchronized (this.m_listenersMutex) {
/*  86 */       for (Object objListener : this.m_listeners) {
/*  87 */         SchedulerListener listener = (SchedulerListener)objListener;
/*  88 */         if (listener.getItem() == messageHandler)
/*  89 */           return true;
/*     */       }
/*  91 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long addClock(MessageHandler messageHandler, long clockDelay, int clockSubId)
/*     */   {
/* 103 */     return addClock(messageHandler, clockDelay, clockSubId, -1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long addClock(MessageHandler messageHandler, long clockDelay, int clockSubId, int executionsCount)
/*     */   {
/* 115 */     SchedulerListener listener = new SchedulerListener();
/* 116 */     listener.setup(messageHandler);
/* 117 */     listener.setClockDelay(clockDelay);
/* 118 */     listener.setSubId(clockSubId);
/* 119 */     listener.setRepetitionsCount(executionsCount);
/* 120 */     listener.setTriggered(System.currentTimeMillis());
/* 121 */     synchronized (this.m_listenersMutex) {
/* 122 */       this.m_lastClockId += 1L;
/* 123 */       listener.setClockId(this.m_lastClockId);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 128 */       insertListener(listener);
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
/* 146 */       if (this.m_listeners.getFirst() == listener) {
/* 147 */         this.m_listenersMutex.notifyAll();
/*     */       }
/*     */     }
/* 150 */     return listener.getClockId();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeClock(long clockId)
/*     */   {
/* 158 */     synchronized (this.m_listenersMutex) {
/* 159 */       Iterator it = this.m_listeners.iterator();
/* 160 */       while (it.hasNext()) {
/* 161 */         SchedulerListener listener = (SchedulerListener)it.next();
/* 162 */         if (listener.getClockId() == clockId) {
/* 163 */           listener.discard();
/* 164 */           it.remove();
/*     */           
/*     */ 
/* 167 */           if (listener == this.m_listeners.getFirst()) {
/* 168 */             this.m_listenersMutex.notifyAll();
/*     */           }
/* 170 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAllClocks()
/*     */   {
/* 182 */     synchronized (this.m_listenersMutex) {
/* 183 */       this.m_listeners.clear();
/* 184 */       this.m_listenersMutex.notifyAll();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAllClocks(MessageHandler messageHandler)
/*     */   {
/* 193 */     synchronized (this.m_listenersMutex) {
/* 194 */       Iterator it = this.m_listeners.iterator();
/* 195 */       while (it.hasNext()) {
/* 196 */         SchedulerListener listener = (SchedulerListener)it.next();
/* 197 */         if (listener.getItem() == messageHandler) {
/* 198 */           it.remove();
/*     */         }
/*     */       }
/* 201 */       this.m_listenersMutex.notifyAll();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAllClocks(MessageHandler messageHandler, int clockSubId)
/*     */   {
/* 211 */     synchronized (this.m_listenersMutex) {
/* 212 */       Iterator it = this.m_listeners.iterator();
/* 213 */       while (it.hasNext()) {
/* 214 */         SchedulerListener listener = (SchedulerListener)it.next();
/* 215 */         if ((listener.getItem() == messageHandler) && (listener.getSubId() == clockSubId)) {
/* 216 */           it.remove();
/*     */         }
/*     */       }
/* 219 */       this.m_listenersMutex.notifyAll();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void start()
/*     */   {
/* 227 */     if (!this.m_running) {
/* 228 */       this.m_running = true;
/* 229 */       super.start();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isRunning() {
/* 234 */     return this.m_running;
/*     */   }
/*     */   
/*     */   private void insertListener(SchedulerListener listener)
/*     */   {
/* 239 */     boolean bInserted = false;
/* 240 */     long listenerNextTime = listener.getNextTime();
/*     */     
/* 242 */     for (SchedulerListener nListener : this.m_listeners) {
/* 243 */       if (nListener.getNextTime() > listenerNextTime) {
/* 244 */         this.m_listeners.addBefore(nListener, listener);
/* 245 */         bInserted = true;
/* 246 */         break;
/*     */       }
/*     */     }
/*     */     
/* 250 */     if (!bInserted)
/* 251 */       this.m_listeners.add(listener);
/*     */   }
/*     */   
/*     */   private void pushClockMessageForListener(SchedulerListener listener, long referenceTime) {
/*     */     try {
/* 256 */       ClockMessage message = (ClockMessage)this.m_clockMessagePool.borrowObject();
/* 257 */       message.setPool(this.m_clockMessagePool);
/* 258 */       message.setHandler((MessageHandler)listener.getItem());
/* 259 */       message.setClockId(listener.getClockId());
/* 260 */       message.setSubId(listener.getSubId());
/* 261 */       message.setTimeStamp(referenceTime);
/* 262 */       Worker.getInstance().pushMessage(message);
/*     */     } catch (Exception e) {
/* 264 */       m_logger.error("Unable to push ClockMessage, exception raised : " + e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void run()
/*     */   {
/* 271 */     ArrayList<SchedulerListener> m_listenersToReschedule = new ArrayList();
/*     */     
/* 273 */     m_logger.info("MessageScheduler running");
/*     */     
/* 275 */     while (this.m_running) {
/*     */       try
/*     */       {
/* 278 */         Thread.yield();
/*     */         
/* 280 */         synchronized (this.m_listenersMutex)
/*     */         {
/* 282 */           if (!this.m_listeners.isEmpty()) {
/* 283 */             long referenceTime = System.currentTimeMillis();
/*     */             
/* 285 */             m_listenersToReschedule.clear();
/*     */             
/* 287 */             Iterator<SchedulerListener> it = this.m_listeners.iterator();
/* 288 */             while (it.hasNext()) {
/* 289 */               SchedulerListener listener = (SchedulerListener)it.next();
/*     */               
/* 291 */               if (listener.getNextTime() <= referenceTime + 3L) {
/* 292 */                 if ((listener.isItemValid()) && (!listener.isDiscarded())) {
/* 293 */                   pushClockMessageForListener(listener, referenceTime);
/* 294 */                   listener.setTriggered(referenceTime);
/* 295 */                   it.remove();
/* 296 */                   if (listener.canBeRepeated())
/* 297 */                     m_listenersToReschedule.add(listener);
/*     */                 } else {
/* 299 */                   it.remove();
/*     */                 }
/*     */               } else {
/* 302 */                 if (!m_listenersToReschedule.isEmpty()) break;
/* 303 */                 this.m_listenersMutex.wait(Math.max(1L, listener.getNextTime() - referenceTime));
/* 304 */                 break;
/*     */               }
/*     */             }
/*     */             
/* 308 */             if (!m_listenersToReschedule.isEmpty()) {
/* 309 */               for (SchedulerListener schedulerListener : m_listenersToReschedule) {
/* 310 */                 insertListener(schedulerListener);
/*     */               }
/*     */             }
/*     */           }
/*     */           else {
/* 315 */             this.m_listenersMutex.wait();
/*     */           }
/*     */         }
/*     */       } catch (Exception ex) {
/* 319 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\message\scheduler\MessageScheduler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */