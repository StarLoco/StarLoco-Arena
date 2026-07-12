/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.dofusarena.common.game.effect.Elements;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HPLeech
/*    */   extends HPLoss
/*    */ {
/* 18 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<HPLeech>() {
/*    */         public HPLeech makeObject() {
/* 20 */           return new HPLeech(null);
/*    */         }
/*    */       });
/*    */ 
/*    */   
/*    */   private HPLeech() {}
/*    */ 
/*    */   
/*    */   public HPLeech(Elements element) {
/* 29 */     super(element);
/*    */   }
/*    */ 
/*    */   
/*    */   public HPLeech newInstance() {
/*    */     HPLeech re;
/*    */     try {
/* 36 */       re = (HPLeech)m_staticPool.borrowObject();
/* 37 */       re.m_pool = m_staticPool;
/*    */     }
/* 39 */     catch (Exception e) {
/* 40 */       re = new HPLeech();
/* 41 */       re.m_pool = null;
/* 42 */       m_logger.error("Erreur lors d'un checkOut sur un HPLeech : " + e.getMessage());
/*    */     } 
/* 44 */     re.m_staticElement = this.m_staticElement;
/* 45 */     re.cloneParameters(this);
/* 46 */     return re;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTriggersToExecute() {
/* 51 */     super.setTriggersToExecute();
/* 52 */     this.m_triggers.set(13);
/* 53 */     if (this.m_staticElement != null) {
/* 54 */       switch (this.m_staticElement) {
/*    */ 
/*    */         
/*    */         case null:
/* 58 */           this.m_triggers.set(12);
/*    */           break;
/*    */         case FIRE:
/* 61 */           this.m_triggers.set(9);
/*    */           break;
/*    */         case WATER:
/* 64 */           this.m_triggers.set(10);
/*    */           break;
/*    */         case WIND:
/* 67 */           this.m_triggers.set(11);
/*    */           break;
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 74 */     if (this.m_target != null && this.m_target.hasCharacteristic((CharacteristicType)FighterCharacteristicType.HP) && this.m_caster != null && this.m_caster.hasCharacteristic((CharacteristicType)FighterCharacteristicType.HP)) {
/* 75 */       this.m_value = Math.min(this.m_value, this.m_target.getCharacteristic((CharacteristicType)FighterCharacteristicType.HP).value());
/* 76 */       this.m_caster.getCharacteristic((CharacteristicType)FighterCharacteristicType.HP).add(this.m_value);
/* 77 */       super.execute(linkedRE, trigger);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean useCaster() {
/* 82 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 86 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 90 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\HPLeech.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */