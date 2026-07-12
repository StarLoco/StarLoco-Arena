/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffectConfigFlags;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*    */ import com.ankamagames.dofusarena.common.game.card.AbstractFighterCard;
/*    */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import java.util.EnumSet;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CardEquipped
/*    */   extends DynamicallyDefinedArenaRunningEffect
/*    */ {
/* 24 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<CardEquipped>() { public CardEquipped makeObject() {
/* 25 */           return new CardEquipped();
/*    */         } }
/*    */     );
/*    */   private AbstractFighterCard m_card;
/*    */   
/*    */   public CardEquipped() {
/* 31 */     setConfigFlags(EnumSet.of(RunningEffectConfigFlags.NO_AUTO_CANCEL));
/* 32 */     setTriggersToExecute();
/*    */   }
/*    */ 
/*    */   
/*    */   public CardEquipped newInstance() {
/*    */     CardEquipped re;
/*    */     try {
/* 39 */       re = (CardEquipped)m_staticPool.borrowObject();
/* 40 */       re.m_pool = m_staticPool;
/*    */     }
/* 42 */     catch (Exception e) {
/*    */       
/* 44 */       re = new CardEquipped();
/* 45 */       re.m_pool = null;
/* 46 */       m_logger.error("Erreur lors d'un newInstance sur un CardEquipped : " + e.getMessage());
/*    */     } 
/* 48 */     re.cloneParameters(this);
/* 49 */     return re;
/*    */   }
/*    */ 
/*    */   
/*    */   public static CardEquipped checkOut(EffectContext context, AbstractFighter cardUser, AbstractFighterCard card) {
/*    */     CardEquipped re;
/*    */     try {
/* 56 */       re = (CardEquipped)m_staticPool.borrowObject();
/* 57 */       re.m_pool = m_staticPool;
/*    */     }
/* 59 */     catch (Exception e) {
/*    */       
/* 61 */       re = new CardEquipped();
/* 62 */       re.m_pool = null;
/* 63 */       m_logger.error("Erreur lors d'un checkOut sur un CardEquipped : " + e.getMessage());
/*    */     } 
/* 65 */     re.m_id = RunningEffectConstants.CARD_EQUIPPED.getId();
/* 66 */     re.m_status = ((StaticRunningEffect)RunningEffectConstants.CARD_EQUIPPED.getObject()).getRunningEffectStatus();
/* 67 */     re.setTriggersToExecute();
/* 68 */     re.m_caster = (EffectUser)cardUser;
/* 69 */     re.m_card = card;
/* 70 */     re.m_target = null;
/* 71 */     re.m_maxExecutionCount = -1;
/* 72 */     re.m_context = context;
/* 73 */     return re;
/*    */   }
/*    */   
/*    */   public void setTriggersToExecute() {
/* 77 */     super.setTriggersToExecute();
/* 78 */     this.m_triggers.set(2002);
/*    */   }
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 82 */     super.execute(linkedRE, trigger);
/*    */   }
/*    */ 
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {}
/*    */   
/*    */   public boolean useCaster() {
/* 89 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 93 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 97 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\CardEquipped.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */