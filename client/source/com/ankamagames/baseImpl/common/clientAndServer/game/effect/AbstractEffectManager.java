/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.effect;
/*    */ 
/*    */ import gnu.trove.TIntObjectHashMap;
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
/*    */ 
/*    */ public abstract class AbstractEffectManager
/*    */ {
/* 17 */   private final TIntObjectHashMap<Effect> m_effects = new TIntObjectHashMap();
/*    */ 
/*    */ 
/*    */   
/*    */   public void addEffect(Effect e) {
/* 22 */     this.m_effects.put(e.getEffectId(), e);
/*    */   }
/*    */   
/*    */   public Effect getEffect(int effectId) {
/* 26 */     return (Effect)this.m_effects.get(effectId);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\AbstractEffectManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */