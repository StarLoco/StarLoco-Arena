/*     */ package com.ankamagames.dofusarena.client.core.contentInitializer;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.effectArea.EffectArea;
/*     */ import com.ankamagames.dofusarena.common.game.effectArea.AbstractEffectArea;
/*     */ import com.ankamagames.dofusarena.common.game.effectArea.StaticEffectAreaManager;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightTargetValidator;
/*     */ import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffect;
/*     */ import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffectEnum;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import java.util.BitSet;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StaticEffectLoader
/*     */   extends EffectContentDocumentLoader
/*     */ {
/*  28 */   private static final StaticEffectLoader m_instance = new StaticEffectLoader();
/*     */   
/*     */   public static StaticEffectLoader getInstance() {
/*  31 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private StaticEffectLoader()
/*     */   {
/*  38 */     setContentDocumentExtension(".dat");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  47 */     return DofusArenaTranslator.getInstance().getString("contentLoader.staticEffect", new Object[0]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void init(AbstractGameClientInstance clientInstance)
/*     */     throws Exception
/*     */   {
/*  56 */     open(DofusArenaConfiguration.getInstance().getString("contentStaticEffectFile"));
/*  57 */     clientInstance.fireContentInitializerDone(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void read(DocumentContainer container)
/*     */   {
/*  67 */     if (container == null) {
/*  68 */       return;
/*     */     }
/*     */     
/*  71 */     int areaCount = readInteger();
/*     */     
/*  73 */     for (int i = 0; i < areaCount; i++)
/*     */     {
/*  75 */       int id = readInteger();
/*  76 */       int scriptId = readInteger();
/*  77 */       int areaId = readShort();
/*  78 */       int[] area_params = readIntegerArray();
/*     */       
/*  80 */       AreaOfEffect aoe = AreaOfEffectEnum.newInstance(areaId, area_params);
/*  81 */       if (aoe == null) {
/*  82 */         throw new NullPointerException("AOE incorrecte");
/*     */       }
/*     */       
/*  85 */       int[] effectAreaApplicationTriggers = readIntegerArray();
/*     */       
/*  87 */       BitSet applicationTriggers = new BitSet();
/*  88 */       if (effectAreaApplicationTriggers != null) { int[] arrayOfInt1;
/*  89 */         int j = (arrayOfInt1 = effectAreaApplicationTriggers).length; for (int i = 0; i < j; i++) { int trigger = arrayOfInt1[i];
/*  90 */           applicationTriggers.set(trigger);
/*     */         }
/*     */       }
/*     */       
/*  94 */       int[] effectAreaUnapplicationTriggers = readIntegerArray();
/*     */       
/*  96 */       BitSet unapplicationtriggers = new BitSet();
/*  97 */       if (effectAreaUnapplicationTriggers != null) { int[] arrayOfInt2;
/*  98 */         int m = (arrayOfInt2 = effectAreaUnapplicationTriggers).length; for (int k = 0; k < m; k++) { int trigger = arrayOfInt2[k];
/*  99 */           unapplicationtriggers.set(trigger);
/*     */         }
/*     */       }
/* 102 */       int maxExecutionCount = readShort();
/*     */       
/* 104 */       int[] applicationTargets = readIntegerArray();
/* 105 */       FightTargetValidator applicationValidator = new FightTargetValidator(applicationTargets);
/*     */       
/* 107 */       int[] unapplicationTargets = readIntegerArray();
/* 108 */       FightTargetValidator unapplicationValidator = new FightTargetValidator(unapplicationTargets);
/*     */       
/* 110 */       int targetsToShow = readInteger();
/*     */       
/* 112 */       String effectAreaType = readString();
/*     */       
/* 114 */       int[] deactivationDelay = readIntegerArray();
/*     */       
/* 116 */       int applicationCondition = readInteger();
/*     */       
/*     */ 
/* 119 */       EffectArea effectArea = new EffectArea(id, 
/* 120 */         aoe, 
/* 121 */         applicationTriggers, 
/* 122 */         unapplicationtriggers, 
/* 123 */         maxExecutionCount, 
/* 124 */         scriptId, 
/* 125 */         targetsToShow, 
/* 126 */         deactivationDelay, 
/* 127 */         applicationCondition, 
/* 128 */         applicationValidator, 
/* 129 */         unapplicationValidator);
/*     */       
/* 131 */       if (effectAreaType.equals("TRAP"))
/* 132 */         StaticEffectAreaManager.getInstance().addEffectArea(effectArea);
/* 133 */       if (effectAreaType.equals("SPECIAL")) {
/* 134 */         StaticEffectAreaManager.getInstance().addSpecialCell(effectArea);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 139 */     int effectCount = readInteger();
/*     */     
/* 141 */     for (int i = 0; i < effectCount; i++) {
/* 142 */       readAndLoadEffect();
/*     */     }
/*     */     
/* 145 */     container.notifyOnLoadComplete();
/*     */   }
/*     */   
/*     */   public void onEffectLoaded(Effect effect, String parentType, int parentId) {
/* 149 */     AbstractEffectArea effectArea = StaticEffectAreaManager.getInstance().getEffectArea(parentId);
/*     */     
/* 151 */     if (effectArea != null)
/* 152 */       effectArea.addEffect(effect);
/*     */   }
/*     */   
/*     */   public void notifyOnLoadComplete() {
/* 156 */     m_logger.info("Statics effects loaded succesfully");
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\contentInitializer\StaticEffectLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */