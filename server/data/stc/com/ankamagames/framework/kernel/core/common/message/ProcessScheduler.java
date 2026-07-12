/*     */ package com.ankamagames.framework.kernel.core.common.message;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class ProcessScheduler
/*     */ {
/*  18 */   protected static final Logger m_logger = Logger.getLogger(ProcessScheduler.class);
/*  19 */   private static final ProcessScheduler m_instance = new ProcessScheduler();
/*     */   
/*     */   public static ProcessScheduler getInstance() {
/*  22 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  31 */   private final ArrayList<ScheduledProcess> m_processesToReschedule = new ArrayList();
/*  32 */   private final ArrayList<ScheduledProcess> m_processes = new ArrayList();
/*     */   
/*  34 */   private final Object m_processesMutex = new Object();
/*  35 */   private final Object m_processesToRescheduleMutex = new Object();
/*     */   
/*     */   public void schedule(Runnable process) {
/*  38 */     schedule(process, 1L, 1);
/*     */   }
/*     */   
/*     */   public void schedule(Runnable process, long delay) {
/*  42 */     schedule(process, delay, -1);
/*     */   }
/*     */   
/*     */   public void schedule(Runnable process, long delay, int repeatCount) {
/*  46 */     ScheduledProcess scheduledProcess = ScheduledProcess.checkOut();
/*  47 */     scheduledProcess.setProcess(process);
/*  48 */     scheduledProcess.setRepeatCountsLeft(repeatCount);
/*  49 */     scheduledProcess.setRescheduleDelay(delay);
/*  50 */     synchronized (this.m_processesToRescheduleMutex) {
/*  51 */       this.m_processesToReschedule.add(scheduledProcess);
/*     */     }
/*  53 */     Worker.getInstance().wakeUp();
/*     */   }
/*     */   
/*     */   public void scheduleAndInvokeNow(Runnable process, long delay) {
/*  57 */     schedule(process);
/*  58 */     schedule(process, delay);
/*     */   }
/*     */   
/*     */   public void remove(Runnable runnable) {
/*  62 */     synchronized (this.m_processesMutex) {
/*  63 */       Iterator<ScheduledProcess> it = this.m_processes.iterator();
/*  64 */       while (it.hasNext()) {
/*  65 */         ScheduledProcess process = (ScheduledProcess)it.next();
/*  66 */         if (process.getProcess() == runnable) {
/*  67 */           it.remove();
/*  68 */           process.release();
/*  69 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  74 */     synchronized (this.m_processesToRescheduleMutex) {
/*  75 */       Iterator<ScheduledProcess> it = this.m_processesToReschedule.iterator();
/*  76 */       while (it.hasNext()) {
/*  77 */         ScheduledProcess process = (ScheduledProcess)it.next();
/*  78 */         if (process.getProcess() == runnable) {
/*  79 */           it.remove();
/*  80 */           process.release();
/*  81 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void reschedule(ScheduledProcess process, long referenceTime)
/*     */   {
/*  89 */     synchronized (this.m_processesMutex) {
/*  90 */       boolean bInserted = false;
/*  91 */       process.setNextSchedulingTime(referenceTime + process.getRescheduleDelay());
/*     */       
/*  93 */       for (int i = 0; i < this.m_processes.size(); i++) {
/*  94 */         ScheduledProcess scheduledProcess = (ScheduledProcess)this.m_processes.get(i);
/*  95 */         if (process.getNextSchedulingTime() < scheduledProcess.getNextSchedulingTime()) {
/*  96 */           this.m_processes.add(i, process);
/*  97 */           bInserted = true;
/*  98 */           break;
/*     */         }
/*     */       }
/*     */       
/* 102 */       if (!bInserted) {
/* 103 */         this.m_processes.add(process);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   long getMaximalSleepTime()
/*     */   {
/* 112 */     if (this.m_processes.isEmpty()) {
/* 113 */       return 1000L;
/*     */     }
/* 115 */     long maxSleepTime = ((ScheduledProcess)this.m_processes.get(0)).getNextSchedulingTime() - System.currentTimeMillis();
/* 116 */     return Math.min(1000L, Math.max(0L, maxSleepTime));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void update()
/*     */   {
/* 124 */     if ((this.m_processes.isEmpty()) && (this.m_processesToReschedule.isEmpty())) {
/* 125 */       return;
/*     */     }
/* 127 */     long referenceTime = System.currentTimeMillis();
/*     */     
/* 129 */     synchronized (this.m_processesToRescheduleMutex) {
/* 130 */       if (!this.m_processesToReschedule.isEmpty()) {
/* 131 */         for (int i = 0; i < this.m_processesToReschedule.size(); i++)
/* 132 */           reschedule((ScheduledProcess)this.m_processesToReschedule.get(i), referenceTime);
/* 133 */         this.m_processesToReschedule.clear();
/*     */       }
/*     */     }
/*     */     
/* 137 */     synchronized (this.m_processesMutex) {
/* 138 */       Iterator<ScheduledProcess> it = this.m_processes.iterator();
/* 139 */       while (it.hasNext()) {
/* 140 */         ScheduledProcess scheduledProcess = (ScheduledProcess)it.next();
/*     */         
/* 142 */         long deltaTime = referenceTime - scheduledProcess.getNextSchedulingTime();
/*     */         
/* 144 */         if (deltaTime >= 0L) {
/* 145 */           it.remove();
/*     */           
/* 147 */           int countsLeft = scheduledProcess.getRepeatCountsLeft();
/* 148 */           if (countsLeft != 0) {
/* 149 */             if (countsLeft > 0)
/* 150 */               countsLeft--;
/* 151 */             scheduledProcess.setRepeatCountsLeft(countsLeft);
/* 152 */             if (countsLeft != 0) {
/* 153 */               this.m_processesToReschedule.add(scheduledProcess);
/*     */             }
/*     */             try {
/* 156 */               scheduledProcess.getProcess().run();
/*     */             } catch (Throwable e) {
/* 158 */               m_logger.error("ProcessScheduler exception : ", e);
/*     */             }
/*     */             
/* 161 */             if (countsLeft == 0) {
/* 162 */               scheduledProcess.release();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 169 */     synchronized (this.m_processesToRescheduleMutex) {
/* 170 */       if (!this.m_processesToReschedule.isEmpty())
/*     */       {
/*     */ 
/* 173 */         for (int i = 0; i < this.m_processesToReschedule.size(); i++) {
/* 174 */           reschedule((ScheduledProcess)this.m_processesToReschedule.get(i), referenceTime);
/*     */         }
/* 176 */         this.m_processesToReschedule.clear();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\message\ProcessScheduler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */