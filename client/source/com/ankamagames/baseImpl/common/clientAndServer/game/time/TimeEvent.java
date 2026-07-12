/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.time;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.Releasable;
/*     */ import com.ankamagames.framework.kernel.core.common.Validable;
/*     */ import com.ankamagames.framework.kernel.utils.ExceptionFormatter;
/*     */ import gnu.trove.TIntObjectHashMap;
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
/*     */ public abstract class TimeEvent<TU extends TimeUnit<TU, TI>, TI extends TimeInterval>
/*     */   implements Validable, Releasable
/*     */ {
/*  24 */   protected static final Logger m_logger = Logger.getLogger(TimeEvent.class);
/*     */   
/*     */   protected TU m_start;
/*     */   
/*     */   protected TimeEventListener m_timeEventListener;
/*     */   
/*     */   protected TI m_duration;
/*     */   protected boolean m_isActive;
/*     */   protected boolean m_isInstant;
/*     */   protected int m_uniqueId;
/*     */   protected int m_priority;
/*     */   protected boolean m_valid = true;
/*     */   private static final int ID_FIND_MAX_TRIES = 100;
/*     */   public static int m_idCounter;
/*  38 */   public final TimeEventValidator m_validator = new TimeEventValidator(); private String m_DEBUG_releaseStackTrace;
/*     */   private DerniereOperation m_DEBUG_lastOperation;
/*  40 */   private static final TIntObjectHashMap<TimeEvent> m_checker = new TIntObjectHashMap();
/*     */   protected ObjectPool m_pool;
/*     */   
/*     */   enum DerniereOperation
/*     */   {
/*  45 */     RELEASE,
/*  46 */     CHECKOUT;
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
/*     */   public void onCheckOut() {
/*  64 */     this.m_DEBUG_lastOperation = DerniereOperation.CHECKOUT;
/*  65 */     this.m_start = null;
/*  66 */     this.m_timeEventListener = null;
/*  67 */     this.m_duration = null;
/*  68 */     this.m_isActive = false;
/*  69 */     this.m_isInstant = false;
/*  70 */     this.m_uniqueId = -1;
/*  71 */     this.m_priority = 0;
/*  72 */     this.m_valid = true;
/*  73 */     this.m_DEBUG_releaseStackTrace = "";
/*     */   }
/*     */   
/*     */   public void onCheckIn() {
/*  77 */     this.m_DEBUG_lastOperation = DerniereOperation.RELEASE;
/*  78 */     this.m_DEBUG_releaseStackTrace = ExceptionFormatter.toString(new Exception()).toString();
/*     */     
/*  80 */     if (this.m_start != null) {
/*  81 */       this.m_start.release();
/*  82 */       this.m_start = null;
/*     */     } 
/*  84 */     if (this.m_duration != null) {
/*  85 */       this.m_duration.release();
/*  86 */       this.m_duration = null;
/*     */     } 
/*  88 */     this.m_timeEventListener = null;
/*  89 */     this.m_isActive = false;
/*  90 */     this.m_isInstant = false;
/*  91 */     this.m_uniqueId = -1;
/*  92 */     this.m_priority = 0;
/*  93 */     this.m_valid = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize(TU start, TI duration, TimeEventListener timeEventListener, boolean isActive, boolean isInstant) {
/*  98 */     this.m_start = start;
/*  99 */     this.m_duration = duration;
/* 100 */     this.m_timeEventListener = timeEventListener;
/* 101 */     this.m_isActive = isActive;
/* 102 */     this.m_isInstant = isInstant;
/* 103 */     initUniqueId(0);
/* 104 */     this.m_validator.setup(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initUniqueId(int turn) {
/* 109 */     m_idCounter++;
/* 110 */     if (m_idCounter == Integer.MAX_VALUE)
/* 111 */       m_idCounter = Integer.MIN_VALUE; 
/* 112 */     if (!m_checker.contains(m_idCounter)) {
/* 113 */       this.m_uniqueId = m_idCounter;
/*     */     }
/* 115 */     else if (turn > 100) {
/*     */ 
/*     */       
/* 118 */       this.m_uniqueId = m_idCounter;
/*     */     } else {
/* 120 */       turn++;
/* 121 */       initUniqueId(turn);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 128 */     return this.m_uniqueId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public boolean needValidation() {
/* 135 */     return !this.m_valid;
/*     */   }
/*     */   
/*     */   public void validate() {
/* 139 */     this.m_valid = true;
/*     */   }
/*     */   
/*     */   public void unvalidate() {
/* 143 */     this.m_valid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 148 */     if (this.m_validator != null) {
/* 149 */       return this.m_validator.isItemValid();
/*     */     }
/* 151 */     return false;
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
/*     */   public void shiftStart(TU executionTime, TI shiftInterval) {
/* 163 */     this.m_start.increment(shiftInterval);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TU when() {
/* 172 */     if (this.m_start == null) {
/* 173 */       m_logger.error("When va retourner null. Type : " + getType() + " Dernier release : " + this.m_DEBUG_releaseStackTrace);
/*     */     }
/*     */     
/* 176 */     return this.m_start;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TI getDuration() {
/* 186 */     return this.m_duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isInfinite();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPriority() {
/* 198 */     return this.m_priority;
/*     */   }
/*     */   
/*     */   public abstract int getType();
/*     */   
/*     */   public TimeEventListener getListener() {
/* 204 */     return this.m_timeEventListener;
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 208 */     return this.m_isActive;
/*     */   }
/*     */   
/*     */   public void switchStatus() {
/* 212 */     this.m_isActive = !this.m_isActive;
/*     */   }
/*     */   
/*     */   public boolean isInstant() {
/* 216 */     return this.m_isInstant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/* 224 */     if (this.m_pool != null) {
/* 225 */       if (this.m_DEBUG_lastOperation == DerniereOperation.RELEASE) {
/* 226 */         m_logger.error("2 release d'affilée !!!.");
/* 227 */         m_logger.error("Appel précédent : " + this.m_DEBUG_releaseStackTrace);
/* 228 */         m_logger.error("Cet appel : ", new Exception());
/*     */       } 
/*     */       try {
/* 231 */         this.m_pool.returnObject(this);
/* 232 */       } catch (Exception e) {
/* 233 */         m_logger.error("Exception dans le release de " + getClass().toString() + " normalement impossible");
/*     */       } 
/* 235 */       this.m_pool = null;
/*     */     } else {
/* 237 */       onCheckIn();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\TimeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */