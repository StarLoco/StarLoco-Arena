/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterPropertyType;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterPropertymanager;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetInvisible
/*    */   extends ArenaRunningEffect
/*    */ {
/* 17 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*    */     public SetInvisible makeObject() {
/* 19 */       return new SetInvisible();
/*    */     }
/* 17 */   });
/*    */   
/*    */ 
/*    */ 
/*    */   public SetInvisible newInstance()
/*    */   {
/*    */     SetInvisible re;
/*    */     
/*    */ 
/*    */     try
/*    */     {
/* 28 */       SetInvisible re = (SetInvisible)m_staticPool.borrowObject();
/* 29 */       re.m_pool = m_staticPool;
/*    */     }
/*    */     catch (Exception e) {
/* 32 */       re = new SetInvisible();
/* 33 */       re.m_pool = null;
/* 34 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*    */     }
/* 36 */     re.cloneParameters(this);
/* 37 */     return re;
/*    */   }
/*    */   
/*    */ 
/*    */   public void execute(RunningEffect linkedRE, boolean trigger)
/*    */   {
/* 43 */     if ((this.m_target instanceof AbstractFighter))
/*    */     {
/* 45 */       ((AbstractFighter)this.m_target).getProperties().add(FighterPropertyType.INVISIBLE);
/*    */     }
/*    */     
/* 48 */     super.execute(linkedRE, trigger);
/*    */   }
/*    */   
/*    */ 
/*    */   public void computeValue(RunningEffect triggerRE) {}
/*    */   
/*    */   public void unapply()
/*    */   {
/* 56 */     if ((this.m_target instanceof AbstractFighter))
/*    */     {
/* 58 */       ((AbstractFighter)this.m_target).getProperties().substract(FighterPropertyType.INVISIBLE);
/*    */     }
/* 60 */     super.unapply();
/*    */   }
/*    */   
/*    */   public boolean useCaster() {
/* 64 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 68 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\SetInvisible.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */