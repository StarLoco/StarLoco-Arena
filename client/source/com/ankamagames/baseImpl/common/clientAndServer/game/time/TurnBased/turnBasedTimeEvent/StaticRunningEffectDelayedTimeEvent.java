/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEventListener;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeInterval;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeUnit;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeEvent;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeInterval;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeUnit;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StaticRunningEffectDelayedTimeEvent
/*    */   extends TurnBasedTimeEvent
/*    */ {
/* 27 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<StaticRunningEffectDelayedTimeEvent>() { public StaticRunningEffectDelayedTimeEvent makeObject() {
/* 28 */           return new StaticRunningEffectDelayedTimeEvent(null);
/*    */         } }
/*    */     );
/*    */   private StaticRunningEffect m_sre;
/*    */   private Effect m_genericEffect;
/*    */   private EffectContainer m_container;
/*    */   private EffectContext m_context;
/*    */   private EffectUser m_launcher;
/*    */   private Point3 m_targetCell;
/*    */   
/*    */   private StaticRunningEffectDelayedTimeEvent() {}
/*    */   
/*    */   public static StaticRunningEffectDelayedTimeEvent checkOut(int tableturn, int turn, TimeEventListener timeEventListener, StaticRunningEffect re, Effect genericEffect, EffectContainer container, EffectContext context, EffectUser launcher, Point3 targetCell) {
/*    */     StaticRunningEffectDelayedTimeEvent ftte;
/*    */     try {
/* 43 */       ftte = (StaticRunningEffectDelayedTimeEvent)m_staticPool.borrowObject();
/* 44 */       ftte.m_pool = m_staticPool;
/*    */     }
/* 46 */     catch (Exception e) {
/*    */       
/* 48 */       ftte = new StaticRunningEffectDelayedTimeEvent();
/* 49 */       ftte.m_pool = null;
/* 50 */       m_logger.error("Erreur lors d'un checkOut sur un TurnBasedApplyDelayTimeEvent : " + e.getMessage());
/*    */     } 
/* 52 */     ftte.m_sre = re;
/* 53 */     ftte.m_genericEffect = genericEffect;
/* 54 */     ftte.m_container = container;
/* 55 */     ftte.m_context = context;
/* 56 */     ftte.m_launcher = launcher;
/* 57 */     ftte.m_targetCell = targetCell;
/* 58 */     boolean isInstant = true;
/* 59 */     ftte.initialize((TimeUnit)TurnBasedTimeUnit.checkOut(tableturn, turn), (TimeInterval)TurnBasedTimeInterval.checkOut(0, 0), timeEventListener, true, isInstant);
/*    */     
/* 61 */     return ftte;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 65 */     return 2;
/*    */   }
/*    */   
/*    */   public StaticRunningEffect getStaticRunningEffect() {
/* 69 */     return this.m_sre;
/*    */   }
/*    */   
/*    */   public Effect getGenericEffect() {
/* 73 */     return this.m_genericEffect;
/*    */   }
/*    */   
/*    */   public EffectContainer getContainer() {
/* 77 */     return this.m_container;
/*    */   }
/*    */   
/*    */   public EffectContext getContext() {
/* 81 */     return this.m_context;
/*    */   }
/*    */   
/*    */   public EffectUser getLauncher() {
/* 85 */     return this.m_launcher;
/*    */   }
/*    */   
/*    */   public Point3 getTargetCell() {
/* 89 */     return this.m_targetCell;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\TurnBased\turnBasedTimeEvent\StaticRunningEffectDelayedTimeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */