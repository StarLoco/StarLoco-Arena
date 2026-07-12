/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.BasicEffectArea;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEventListener;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeInterval;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeUnit;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeEvent;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeInterval;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeUnit;
/*    */ import com.ankamagames.framework.ai.targetfinder.Target;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EffectAreaActivationTimeEvent
/*    */   extends TurnBasedTimeEvent
/*    */ {
/* 22 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<EffectAreaActivationTimeEvent>() {
/*    */         public EffectAreaActivationTimeEvent makeObject() {
/* 24 */           return new EffectAreaActivationTimeEvent();
/*    */         }
/*    */       });
/*    */   
/*    */   private BasicEffectArea m_area;
/*    */   private Target m_applicant;
/*    */   
/*    */   public static <F extends com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter> EffectAreaActivationTimeEvent checkOut(int tableturn, int turn, TimeEventListener timeEventListener, BasicEffectArea area, Target applicant) {
/*    */     EffectAreaActivationTimeEvent ftte;
/*    */     try {
/* 34 */       ftte = (EffectAreaActivationTimeEvent)m_staticPool.borrowObject();
/* 35 */       ftte.m_pool = m_staticPool;
/*    */     }
/* 37 */     catch (Exception e) {
/* 38 */       ftte = new EffectAreaActivationTimeEvent();
/* 39 */       ftte.m_pool = null;
/* 40 */       m_logger.error("Erreur lors d'un checkOut sur un FighterTurnTimeEvent : ", e);
/*    */     } 
/* 42 */     ftte.m_area = area;
/* 43 */     ftte.m_applicant = applicant;
/* 44 */     ftte.initialize((TimeUnit)TurnBasedTimeUnit.checkOut(tableturn, turn), (TimeInterval)TurnBasedTimeInterval.checkOut(0, 0), timeEventListener, true, true);
/* 45 */     ftte.m_priority = 1;
/* 46 */     return ftte;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 50 */     return 108;
/*    */   }
/*    */ 
/*    */   
/*    */   public BasicEffectArea getArea() {
/* 55 */     return this.m_area;
/*    */   }
/*    */ 
/*    */   
/*    */   public Target getApplicant() {
/* 60 */     return this.m_applicant;
/*    */   }
/*    */   
/*    */   public void onCheckOut() {
/* 64 */     super.onCheckOut();
/* 65 */     this.m_area = null;
/*    */   }
/*    */   
/*    */   public void onCheckIn() {
/* 69 */     super.onCheckIn();
/* 70 */     this.m_area = null;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\TurnBased\turnBasedTimeEvent\EffectAreaActivationTimeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */