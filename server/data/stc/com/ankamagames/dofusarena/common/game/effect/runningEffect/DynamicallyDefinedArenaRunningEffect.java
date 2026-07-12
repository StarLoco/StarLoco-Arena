/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*    */ import com.ankamagames.framework.kernel.core.maths.Point3;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DynamicallyDefinedArenaRunningEffect
/*    */   extends ArenaRunningEffect
/*    */ {
/*    */   public void run(Effect genericEffect, EffectContainer container, EffectContext context, EffectUser launcher, Point3 targetCell, boolean forceNow)
/*    */   {
/* 29 */     throw new UnsupportedOperationException("DynamicallyDefinedArenaRunningEffect can't use the 'run' method. Use 'apply' instead. " + this);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\DynamicallyDefinedArenaRunningEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */