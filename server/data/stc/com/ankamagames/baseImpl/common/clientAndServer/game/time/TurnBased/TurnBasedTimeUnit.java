/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeUnit;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
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
/*     */ public class TurnBasedTimeUnit
/*     */   implements TimeUnit<TurnBasedTimeUnit, TurnBasedTimeInterval>
/*     */ {
/*  19 */   protected static final Logger m_logger = Logger.getLogger(TurnBasedTimeUnit.class);
/*  20 */   private static final ObjectPool m_pool = new MonitoredPool(new ObjectFactory() {
/*     */     public TurnBasedTimeUnit makeObject() {
/*  22 */       return new TurnBasedTimeUnit(true, null);
/*     */     }
/*  20 */   });
/*     */   
/*     */   private boolean m_pooled;
/*     */   
/*     */   private int m_tableTurn;
/*     */   
/*     */   private int m_turn;
/*     */   
/*     */   private boolean DEBUG_released;
/*     */   
/*     */   private TurnBasedTimeUnit(boolean pooled)
/*     */   {
/*  32 */     this.m_pooled = pooled;
/*     */   }
/*     */   
/*     */   public TurnBasedTimeUnit(int tableturn, int turn) {
/*  36 */     this.m_tableTurn = tableturn;
/*  37 */     this.m_turn = turn;
/*  38 */     this.m_pooled = false;
/*  39 */     this.DEBUG_released = false;
/*     */   }
/*     */   
/*     */   public TurnBasedTimeUnit(int turn) {
/*  43 */     this.m_turn = turn;
/*     */   }
/*     */   
/*     */   public void increment() {
/*  47 */     this.m_turn += 1;
/*     */   }
/*     */   
/*     */   public void increment(TurnBasedTimeInterval t)
/*     */   {
/*  52 */     if (t != null) {
/*  53 */       this.m_turn += t.getTurnDuration();
/*  54 */       this.m_tableTurn += t.getTableTurnDuration();
/*     */     }
/*     */   }
/*     */   
/*     */   public void decrement() {
/*  59 */     this.m_turn -= 1;
/*     */   }
/*     */   
/*     */   public void decrement(TurnBasedTimeInterval t)
/*     */   {
/*  64 */     if (t != null) {
/*  65 */       this.m_turn -= t.getTurnDuration();
/*  66 */       this.m_tableTurn -= t.getTableTurnDuration();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getTurn() {
/*  71 */     return this.m_turn;
/*     */   }
/*     */   
/*     */   public int getTableTurn() {
/*  75 */     return this.m_tableTurn;
/*     */   }
/*     */   
/*     */   public int compareTo(TurnBasedTimeUnit tu)
/*     */   {
/*  80 */     if (tu == null) {
/*  81 */       m_logger.error("turnBasedTimeUnit null, Pooled : " + (this.m_pooled ? "oui" : "non") + " released : " + (this.DEBUG_released ? "oui" : "non"), new NullPointerException());
/*  82 */       return -1;
/*     */     }
/*     */     
/*  85 */     if (this.m_tableTurn > tu.getTableTurn())
/*  86 */       return 1;
/*  87 */     if (this.m_tableTurn == tu.getTableTurn()) {
/*  88 */       return this.m_turn - tu.getTurn();
/*     */     }
/*  90 */     return -1;
/*     */   }
/*     */   
/*     */   public void release()
/*     */   {
/*  95 */     if (this.m_pooled) {
/*     */       try {
/*  97 */         m_pool.returnObject(this);
/*     */       } catch (Exception e) {
/*  99 */         m_logger.error("Exception dans le release de " + getClass().toString() + " normalement impossible");
/*     */       }
/*     */     } else {
/* 102 */       onCheckIn();
/*     */     }
/*     */   }
/*     */   
/*     */   public static TurnBasedTimeUnit checkOut(int startTableturn, int startTurn) {
/*     */     TurnBasedTimeUnit tu;
/*     */     try {
/* 109 */       tu = (TurnBasedTimeUnit)m_pool.borrowObject();
/*     */     } catch (Exception e) {
/*     */       TurnBasedTimeUnit tu;
/* 112 */       tu = new TurnBasedTimeUnit(false);
/* 113 */       m_logger.error("Erreur lors d'un checkOut sur un TurnBasedTimeUnit : " + e.getMessage());
/*     */     }
/* 115 */     tu.m_tableTurn = startTableturn;
/* 116 */     tu.m_turn = startTurn;
/* 117 */     return tu;
/*     */   }
/*     */   
/*     */   public void onCheckOut() {
/* 121 */     this.m_turn = 0;
/* 122 */     this.m_tableTurn = 0;
/* 123 */     this.DEBUG_released = false;
/*     */   }
/*     */   
/*     */   public void onCheckIn() {
/* 127 */     this.m_turn = 0;
/* 128 */     this.m_tableTurn = 0;
/* 129 */     this.DEBUG_released = true;
/*     */   }
/*     */   
/*     */   public void setTurnBasedTimeUnit(int tableTurn, int turn)
/*     */   {
/* 134 */     this.m_tableTurn = tableTurn;
/* 135 */     this.m_turn = turn;
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/* 139 */     return (this.m_tableTurn >= 0) && (this.m_turn >= 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\TurnBased\TurnBasedTimeUnit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */