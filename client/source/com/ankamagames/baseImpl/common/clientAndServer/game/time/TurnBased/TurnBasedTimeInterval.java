/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeInterval;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TurnBasedTimeInterval
/*    */   implements TimeInterval
/*    */ {
/* 18 */   protected static final Logger m_logger = Logger.getLogger(TurnBasedTimeInterval.class);
/* 19 */   private static final ObjectPool m_pool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<TurnBasedTimeInterval>() { public TurnBasedTimeInterval makeObject() {
/* 20 */           return new TurnBasedTimeInterval(true);
/*    */         } }
/*    */     );
/*    */   private boolean m_pooled;
/*    */   public int m_turnDuration;
/*    */   public int m_tableTurnDuration;
/*    */   
/*    */   public TurnBasedTimeInterval(boolean pooled) {
/* 28 */     this.m_pooled = pooled;
/*    */   }
/*    */   
/*    */   public boolean isInfinite() {
/* 32 */     return !(this.m_turnDuration < 63 && this.m_tableTurnDuration < 63);
/*    */   }
/*    */   
/*    */   public int getTurnDuration() {
/* 36 */     return this.m_turnDuration;
/*    */   }
/*    */   
/*    */   public int getTableTurnDuration() {
/* 40 */     return this.m_tableTurnDuration;
/*    */   }
/*    */   
/*    */   public void release() {
/* 44 */     if (this.m_pooled) {
/*    */       try {
/* 46 */         m_pool.returnObject(this);
/* 47 */       } catch (Exception e) {
/* 48 */         m_logger.error("Exception dans le release de " + getClass().toString() + " normalement impossible");
/*    */       } 
/*    */     } else {
/* 51 */       onCheckIn();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static TurnBasedTimeInterval checkOut(int tableturnDuration, int turnDuration) {
/*    */     TurnBasedTimeInterval tu;
/*    */     try {
/* 59 */       tu = (TurnBasedTimeInterval)m_pool.borrowObject();
/*    */     }
/* 61 */     catch (Exception e) {
/*    */       
/* 63 */       tu = new TurnBasedTimeInterval(false);
/* 64 */       m_logger.error("Erreur lors d'un checkOut sur un TurnBasedTimeInterval : " + e.getMessage());
/*    */     } 
/* 66 */     tu.m_tableTurnDuration = tableturnDuration;
/* 67 */     tu.m_turnDuration = turnDuration;
/* 68 */     return tu;
/*    */   }
/*    */   
/*    */   public void onCheckOut() {
/* 72 */     this.m_tableTurnDuration = 0;
/* 73 */     this.m_turnDuration = 0;
/*    */   }
/*    */   
/*    */   public void onCheckIn() {
/* 77 */     this.m_tableTurnDuration = 0;
/* 78 */     this.m_turnDuration = 0;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\TurnBased\TurnBasedTimeInterval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */