/*    */ package com.ankamagames.dofusarena.client.core.game.effect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.AbstractEffectManager;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*    */ import com.ankamagames.dofusarena.common.game.effect.DefaultEffect;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EffectManager
/*    */   extends AbstractEffectManager
/*    */ {
/* 19 */   private static final EffectManager m_uniqueInstance = new EffectManager();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static EffectManager getInstance() {
/* 28 */     return m_uniqueInstance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Effect getEffect(int effectId) {
/* 37 */     if (effectId == -1) {
/* 38 */       return (Effect)DefaultEffect.getInstance();
/*    */     }
/* 40 */     return super.getEffect(effectId);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\effect\EffectManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */