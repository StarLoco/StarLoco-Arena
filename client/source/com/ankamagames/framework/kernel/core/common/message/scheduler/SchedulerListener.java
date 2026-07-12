/*     */ package com.ankamagames.framework.kernel.core.common.message.scheduler;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.Validator;
/*     */ import gnu.trove.TLinkable;
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
/*     */ class SchedulerListener
/*     */   extends Validator
/*     */   implements TLinkable, Comparable
/*     */ {
/*     */   protected TLinkable m_next;
/*     */   protected TLinkable m_previous;
/*  40 */   protected int m_subId = 0;
/*  41 */   protected int m_repetitionsIndex = 0;
/*     */   
/*     */   protected long m_clockId;
/*     */   protected long m_clockDelayMS;
/*     */   protected long m_nextTime;
/*     */   protected int m_repetitionsCount;
/*     */   private boolean m_bDiscarded = false;
/*     */   
/*     */   public void setSubId(int subId) {
/*  50 */     this.m_subId = subId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSubId() {
/*  58 */     return this.m_subId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TLinkable getNext() {
/*  66 */     return this.m_next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNext(TLinkable linkable) {
/*  73 */     this.m_next = linkable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TLinkable getPrevious() {
/*  80 */     return this.m_previous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrevious(TLinkable linkable) {
/*  87 */     this.m_previous = linkable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getClockId() {
/*  97 */     return this.m_clockId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setClockId(long clockId) {
/* 106 */     this.m_clockId = clockId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRepetitionsCount() {
/* 114 */     return this.m_repetitionsCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRepetitionsCount(int repetitionsCount) {
/* 122 */     this.m_repetitionsCount = repetitionsCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClockDelay(long delayMS) {
/* 130 */     this.m_clockDelayMS = delayMS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getClockDelay() {
/* 138 */     return this.m_clockDelayMS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getNextTime() {
/* 147 */     return this.m_nextTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeRepeated() {
/* 156 */     return (isItemValid() && (this.m_repetitionsCount == -1 || this.m_repetitionsIndex <= this.m_repetitionsCount));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setTriggered(long now) {
/* 163 */     this.m_repetitionsIndex++;
/* 164 */     this.m_nextTime = now + this.m_clockDelayMS;
/*     */   }
/*     */   
/*     */   public boolean isDiscarded() {
/* 168 */     return this.m_bDiscarded;
/*     */   }
/*     */   
/*     */   public void discard() {
/* 172 */     this.m_bDiscarded = true;
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
/*     */   public int compareTo(Object o) {
/* 214 */     SchedulerListener listener = (SchedulerListener)o;
/*     */     
/* 216 */     if (listener == null) {
/* 217 */       throw new UnsupportedOperationException("Comparaison d'un listener avec null.");
/*     */     }
/* 219 */     if (this.m_nextTime < listener.m_nextTime)
/* 220 */       return -1; 
/* 221 */     if (this.m_nextTime > listener.m_nextTime) {
/* 222 */       return 1;
/*     */     }
/* 224 */     return 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 250 */     return String.valueOf(getItem().getClass().getName()) + ", nextTime : " + this.m_nextTime;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\message\scheduler\SchedulerListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */