/*    */ package com.ankamagames.framework.graphics.opengl.base.effects;
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
/*    */ public abstract class EffectContext
/*    */ {
/*    */   private Effect m_effect;
/*    */   
/*    */   protected EffectContext(Effect effect) {
/* 17 */     this.m_effect = effect;
/*    */   }
/*    */   
/*    */   public Effect getEffect() {
/* 21 */     return this.m_effect;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\effects\EffectContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */