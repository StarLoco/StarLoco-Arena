/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterPropertyType;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetVisible
/*    */   extends ArenaRunningEffect
/*    */ {
/* 17 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<SetVisible>() { public SetVisible makeObject() {
/* 18 */           return new SetVisible();
/*    */         } }
/*    */     );
/*    */ 
/*    */ 
/*    */   
/*    */   public SetVisible newInstance() {
/*    */     SetVisible re;
/*    */     try {
/* 27 */       re = (SetVisible)m_staticPool.borrowObject();
/* 28 */       re.m_pool = m_staticPool;
/*    */     }
/* 30 */     catch (Exception e) {
/*    */       
/* 32 */       re = new SetVisible();
/* 33 */       re.m_pool = null;
/* 34 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*    */     } 
/* 36 */     re.cloneParameters(this);
/* 37 */     return re;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 44 */     if (this.m_target instanceof AbstractFighter)
/*    */     {
/*    */       
/* 47 */       ((AbstractFighter)this.m_target).getProperties().remove(FighterPropertyType.INVISIBLE);
/*    */     }
/*    */     
/* 50 */     super.execute(linkedRE, trigger);
/*    */   }
/*    */ 
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {}
/*    */ 
/*    */   
/*    */   public void unapply() {}
/*    */ 
/*    */   
/*    */   public boolean useCaster() {
/* 61 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 65 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\SetVisible.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */