/*    */ package com.ankamagames.dofusarena.common.game.effect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*    */ import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffectEnum;
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
/*    */ public class DefaultEffect
/*    */   extends Effect
/*    */ {
/* 18 */   private static final DefaultEffect m_instance = new DefaultEffect();
/*    */   
/*    */   public static DefaultEffect getInstance()
/*    */   {
/* 22 */     return m_instance;
/*    */   }
/*    */   
/*    */   private DefaultEffect() {
/* 26 */     super(-1, -1, "", new float[0], AreaOfEffectEnum.POINT.newInstance(null), new int[2], new int[1], new int[0], new int[0], 0L, null, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\DefaultEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */