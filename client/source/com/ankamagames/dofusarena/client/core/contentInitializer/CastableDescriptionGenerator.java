/*     */ package com.ankamagames.dofusarena.client.core.contentInitializer;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightTargetValidator;
/*     */ import com.ankamagames.framework.kernel.utils.StringFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CastableDescriptionGenerator
/*     */ {
/*     */   private static final String BACKGROUND_COLOR = "333333";
/*     */   
/*     */   public static String generateDescription(int id, boolean useAutomaticDescription, Iterable<Effect> useEffects, Iterable<Effect> bonusEffects, int rangeMin, int rangeMax, boolean onlyDisplayLongRange, boolean hasToTestFreeCell, byte castInterval, byte castMaxPerTarget, byte castMaxPerTurn, int freeDescriptionTranslationType, int backgroundDescriptionTranslationType) {
/*  36 */     StringBuilder description = new StringBuilder();
/*     */     
/*  38 */     if (useAutomaticDescription) {
/*  39 */       String targetDescriptionKey = "cast.targetFree";
/*     */       
/*  41 */       if (useEffects != null) {
/*     */         
/*  43 */         StringBuilder effectDescription = new StringBuilder();
/*  44 */         StringBuilder criticalEffectDescription = new StringBuilder();
/*  45 */         boolean firstEffect = true;
/*  46 */         boolean firstCriticalEffect = true;
/*     */         
/*  48 */         for (Effect effect : useEffects) {
/*  49 */           StringBuilder currentStringBuilder = effectDescription;
/*     */ 
/*     */           
/*  52 */           if (effect.checkFlags(1L)) {
/*  53 */             currentStringBuilder = criticalEffectDescription;
/*     */             
/*  55 */             if (!firstCriticalEffect) {
/*  56 */               currentStringBuilder.append(", ");
/*     */             } else {
/*  58 */               firstCriticalEffect = false;
/*     */             } 
/*  60 */           } else if (!firstEffect) {
/*  61 */             currentStringBuilder.append(", ");
/*     */           } else {
/*  63 */             firstEffect = false;
/*     */           } 
/*     */           
/*  66 */           String effectText = DofusArenaTranslator.getInstance().getString(22, effect.getActionId());
/*     */           
/*  68 */           int effectParamsCount = (effect.getParams()).length;
/*  69 */           Object[] params = new Object[effectParamsCount];
/*     */           
/*  71 */           for (int j = 0; j < effectParamsCount; j++) {
/*  72 */             params[j] = Integer.valueOf((int)effect.getParams()[j]);
/*     */           }
/*  74 */           currentStringBuilder.append(StringFormatter.format(effectText, params));
/*     */ 
/*     */           
/*  77 */           if (effect.getDuration() != null && (effect.getDuration()).length > 0)
/*     */           {
/*  79 */             if (effect.getDuration()[0] >= 63) {
/*  80 */               currentStringBuilder.append(' ').append(DofusArenaTranslator.getInstance().getString("cast.infiniteDuration", new Object[0]));
/*  81 */             } else if (effect.getDuration()[0] > 0) {
/*  82 */               currentStringBuilder.append(' ').append(DofusArenaTranslator.getInstance().getString("cast.durationDescription", new Object[] { Integer.valueOf(effect.getDuration()[0]) }));
/*     */             } 
/*     */           }
/*     */           
/*  86 */           if (rangeMax == 0) {
/*  87 */             targetDescriptionKey = "cast.targetCaster"; continue;
/*     */           } 
/*  89 */           int[] conditions = ((FightTargetValidator)effect.getTargetValidator()).getConditions(); byte b; int i, arrayOfInt1[];
/*  90 */           for (i = (arrayOfInt1 = conditions).length, b = 0; b < i; ) { int c = arrayOfInt1[b];
/*  91 */             if ((0x4 & c) != 0) {
/*  92 */               targetDescriptionKey = "cast.targetAlly";
/*  93 */             } else if ((0x8 & c) != 0) {
/*  94 */               targetDescriptionKey = "cast.targetEnemy";
/*  95 */             } else if ((0x20 & c) != 0) {
/*  96 */               targetDescriptionKey = "cast.targetSummon";
/*     */             } 
/*     */             b++; }
/*     */         
/*     */         } 
/* 101 */         if (hasToTestFreeCell) {
/* 102 */           targetDescriptionKey = "cast.targetFreeCell";
/*     */         }
/* 104 */         if (!firstEffect) {
/* 105 */           description.append(DofusArenaTranslator.getInstance().getString("cast.effectDescription", new Object[] { effectDescription.toString() }));
/*     */         }
/* 107 */         if (!firstCriticalEffect) {
/* 108 */           description.append(DofusArenaTranslator.getInstance().getString("cast.criticalEffectDescription", new Object[] { criticalEffectDescription.toString() }));
/*     */         }
/*     */       } 
/* 111 */       if (bonusEffects != null) {
/*     */ 
/*     */         
/* 114 */         StringBuilder effectDescription = new StringBuilder();
/* 115 */         boolean firstEffect = true;
/* 116 */         for (Effect effect : bonusEffects) {
/* 117 */           String effectText = DofusArenaTranslator.getInstance().getString(22, effect.getActionId());
/*     */           
/* 119 */           int effectParamsCount = (effect.getParams()).length;
/* 120 */           Object[] params = new Object[effectParamsCount];
/*     */           
/* 122 */           for (int j = 0; j < effectParamsCount; j++) {
/* 123 */             params[j] = Integer.valueOf((int)effect.getParams()[j]);
/*     */           }
/* 125 */           if (!firstEffect) {
/* 126 */             effectDescription.append(", ");
/*     */           } else {
/* 128 */             firstEffect = false;
/*     */           } 
/* 130 */           effectDescription.append(StringFormatter.format(effectText, params));
/*     */         } 
/*     */         
/* 133 */         if (!firstEffect) {
/* 134 */           description.append(StringFormatter.format(DofusArenaTranslator.getInstance().getString("cast.bonusDescription", new Object[0]), new Object[] { effectDescription.toString() }));
/*     */         }
/*     */       } 
/* 137 */       if (rangeMin == rangeMax) {
/*     */         
/* 139 */         if (rangeMax > 1 || !onlyDisplayLongRange) {
/* 140 */           description.append(DofusArenaTranslator.getInstance().getString("cast.rangeDescription", new Object[] { Integer.valueOf(rangeMin)
/* 141 */                 })).append(DofusArenaTranslator.getInstance().getString(targetDescriptionKey, new Object[0]))
/* 142 */             .append("\n");
/*     */         }
/*     */       } else {
/* 145 */         description.append(DofusArenaTranslator.getInstance().getString("cast.rangeDescription", new Object[] { Integer.valueOf(rangeMin), Integer.valueOf(rangeMax)
/* 146 */               })).append(DofusArenaTranslator.getInstance().getString(targetDescriptionKey, new Object[0]))
/* 147 */           .append("\n");
/*     */       } 
/* 149 */       if (castInterval >= 63) {
/* 150 */         description.append(DofusArenaTranslator.getInstance().getString("cast.useInfiniteInterval", new Object[0]));
/* 151 */       } else if (castInterval > 0) {
/* 152 */         description.append(DofusArenaTranslator.getInstance().getString("cast.useInterval", new Object[] { Byte.valueOf(castInterval) }));
/* 153 */       } else if (castMaxPerTurn > 0) {
/* 154 */         description.append(DofusArenaTranslator.getInstance().getString("cast.useMaxPerTurn", new Object[] { Byte.valueOf(castMaxPerTurn) }));
/* 155 */       } else if (castMaxPerTarget > 0) {
/* 156 */         description.append(DofusArenaTranslator.getInstance().getString("cast.useMaxPerTarget", new Object[] { Byte.valueOf(castMaxPerTarget) }));
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     if (DofusArenaTranslator.getInstance().containsContentKey(freeDescriptionTranslationType, id)) {
/* 161 */       description.append(DofusArenaTranslator.getInstance().getString(freeDescriptionTranslationType, id));
/*     */     }
/*     */     
/* 164 */     description.append(" \n<style=italic,color=").append("333333").append("|")
/* 165 */       .append(DofusArenaTranslator.getInstance().getString(backgroundDescriptionTranslationType, id))
/* 166 */       .append(">");
/*     */     
/* 168 */     return description.toString();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\contentInitializer\CastableDescriptionGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */