/*    */ package com.ankamagames.dofusarena.client.core.contentInitializer;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*    */ import com.ankamagames.baseImpl.graphicalClient.core.contentLoader.ContentDocumentLoader;
/*    */ import com.ankamagames.dofusarena.client.core.game.effect.EffectManager;
/*    */ import com.ankamagames.dofusarena.common.game.fight.FightTargetValidator;
/*    */ import com.ankamagames.framework.ai.targetfinder.TargetValidator;
/*    */ import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffect;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EffectContentDocumentLoader
/*    */   extends ContentDocumentLoader
/*    */ {
/*    */   public void readAndLoadEffect()
/*    */   {
/* 28 */     int effectId = readInteger();
/* 29 */     String effectParentType = readString();
/* 30 */     int effectParentId = readInteger();
/* 31 */     readShort();
/* 32 */     int[] effectDuration = readIntegerArray();
/* 33 */     int effectActionId = readInteger();
/* 34 */     boolean effectIsCritical = readBoolean();
/* 35 */     float[] effectParams = readFloatArray();
/* 36 */     short effectAreaShape = readShort();
/* 37 */     int[] effectAreaSize = readIntegerArray();
/* 38 */     int[] effectTargets = readIntegerArray();
/* 39 */     int[] effectTriggersAfter = readIntegerArray();
/* 40 */     int[] effectTriggersBefore = readIntegerArray();
/* 41 */     int[] effectEndTriggers = (int[])null;
/*    */     
/* 43 */     AreaOfEffect area = AreaOfEffectEnum.newInstance(effectAreaShape, effectAreaSize);
/* 44 */     TargetValidator validator = new FightTargetValidator(effectTargets);
/*    */     
/* 46 */     long flags = 0L;
/* 47 */     if (effectIsCritical) {
/* 48 */       flags |= 1L;
/*    */     }
/*    */     
/* 51 */     boolean affectedByLocalisation = readBoolean();
/*    */     
/* 53 */     Effect effect = new Effect(effectId, effectActionId, effectParentType, effectParams, area, effectDuration, effectTriggersBefore, effectTriggersAfter, effectEndTriggers, flags, validator, affectedByLocalisation);
/*    */     
/* 55 */     onEffectLoaded(effect, effectParentType, effectParentId);
/* 56 */     EffectManager.getInstance().addEffect(effect);
/*    */   }
/*    */   
/*    */   public abstract void onEffectLoaded(Effect paramEffect, String paramString, int paramInt);
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\contentInitializer\EffectContentDocumentLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */