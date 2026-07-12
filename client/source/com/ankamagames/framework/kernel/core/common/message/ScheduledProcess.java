/*     */ package com.ankamagames.framework.kernel.core.common.message;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ScheduledProcess
/*     */   implements Poolable
/*     */ {
/*     */   private long m_nextSchedulingTime;
/*     */   private long m_rescheduleDelay;
/*     */   private Runnable m_process;
/*     */   private int m_repeatCountsLeft;
/*     */   private boolean m_pooled;
/*     */   
/*  26 */   private static final ObjectPool m_pool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<ScheduledProcess>() { public ScheduledProcess makeObject() {
/*  27 */           return new ScheduledProcess(null);
/*     */         } }
/*     */     );
/*     */ 
/*     */   
/*     */   private ScheduledProcess() {}
/*     */   
/*     */   static ScheduledProcess checkOut() {
/*     */     ScheduledProcess msg;
/*     */     try {
/*  37 */       msg = (ScheduledProcess)m_pool.borrowObject();
/*  38 */       msg.m_pooled = true;
/*     */     }
/*  40 */     catch (Exception e) {
/*     */       
/*  42 */       msg = new ScheduledProcess();
/*  43 */       msg.m_pooled = false;
/*     */     } 
/*  45 */     return msg;
/*     */   }
/*     */   
/*     */   void release() {
/*     */     try {
/*  50 */       if (this.m_pooled)
/*  51 */         m_pool.returnObject(this); 
/*  52 */     } catch (Exception e) {
/*  53 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckOut() {
/*  61 */     this.m_rescheduleDelay = -1L;
/*  62 */     this.m_nextSchedulingTime = -1L;
/*  63 */     this.m_process = null;
/*  64 */     this.m_repeatCountsLeft = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckIn() {
/*  71 */     this.m_rescheduleDelay = -1L;
/*  72 */     this.m_nextSchedulingTime = -1L;
/*  73 */     this.m_process = null;
/*  74 */     this.m_repeatCountsLeft = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getNextSchedulingTime() {
/*  82 */     return this.m_nextSchedulingTime;
/*     */   }
/*     */   
/*     */   public void setNextSchedulingTime(long nextSchedulingTime) {
/*  86 */     this.m_nextSchedulingTime = nextSchedulingTime;
/*     */   }
/*     */   
/*     */   public long getRescheduleDelay() {
/*  90 */     return this.m_rescheduleDelay;
/*     */   }
/*     */   
/*     */   public void setRescheduleDelay(long rescheduleDelay) {
/*  94 */     this.m_rescheduleDelay = rescheduleDelay;
/*     */   }
/*     */   
/*     */   public Runnable getProcess() {
/*  98 */     return this.m_process;
/*     */   }
/*     */   
/*     */   public void setProcess(Runnable process) {
/* 102 */     this.m_process = process;
/*     */   }
/*     */   
/*     */   public int getRepeatCountsLeft() {
/* 106 */     return this.m_repeatCountsLeft;
/*     */   }
/*     */   
/*     */   public void setRepeatCountsLeft(int repeatCountsLeft) {
/* 110 */     this.m_repeatCountsLeft = repeatCountsLeft;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\message\ScheduledProcess.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */