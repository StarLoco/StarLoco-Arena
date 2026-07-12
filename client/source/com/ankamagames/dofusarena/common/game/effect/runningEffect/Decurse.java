/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import java.util.ArrayList;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Decurse
/*    */   extends ArenaRunningEffect
/*    */ {
/* 22 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<Decurse>() {
/*    */         public Decurse makeObject() {
/* 24 */           return new Decurse();
/*    */         }
/*    */       });
/*    */ 
/*    */ 
/*    */   
/*    */   public Decurse newInstance() {
/*    */     Decurse re;
/*    */     try {
/* 33 */       re = (Decurse)m_staticPool.borrowObject();
/* 34 */       re.m_pool = m_staticPool;
/*    */     }
/* 36 */     catch (Exception e) {
/* 37 */       re = new Decurse();
/* 38 */       re.m_pool = null;
/* 39 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*    */     } 
/* 41 */     re.cloneParameters(this);
/* 42 */     return re;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 47 */     ArrayList<RunningEffect> reToUnapply = new ArrayList<RunningEffect>();
/* 48 */     for (RunningEffect re : this.m_target.getRunningEffectManager()) {
/*    */       
/* 50 */       if (re.getEffectContainer() != null) {
/* 51 */         switch (re.getEffectContainer().getContainerType()) {
/*    */           case 3:
/*    */           case 13:
/* 54 */             reToUnapply.add(re);
/*    */         } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/*    */       }
/*    */     } 
/* 64 */     for (RunningEffect re : reToUnapply) {
/* 65 */       re.askForUnapplication();
/*    */     }
/*    */     
/* 68 */     super.execute(linkedRE, trigger);
/*    */   }
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {
/* 72 */     if (this.m_genericEffect != null && (this.m_genericEffect.getParams()).length > 0) {
/* 73 */       this.m_value = (int)this.m_genericEffect.getParam(0);
/*    */     } else {
/* 75 */       this.m_value = 0;
/*    */     } 
/*    */   }
/*    */   public boolean useCaster() {
/* 79 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 83 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 87 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\Decurse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */