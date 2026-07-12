/*     */ package com.ankamagames.dofusarena.client.core.contentInitializer;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
/*     */ import com.ankamagames.baseImpl.graphicalClient.core.contentLoader.ContentInitializer;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.SpellManager;
/*     */ import com.ankamagames.dofusarena.common.game.ai.CriteriaCompiler;
/*     */ import com.ankamagames.dofusarena.common.game.spell.AbstractSpell;
/*     */ import com.ankamagames.framework.ai.criteria.Criterion;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpellLoader
/*     */   extends EffectContentDocumentLoader
/*     */ {
/*  26 */   private static final SpellLoader m_instance = new SpellLoader();
/*     */   
/*     */   public static SpellLoader getInstance() {
/*  29 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SpellLoader() {
/*  36 */     setContentDocumentExtension(".dat");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  45 */     return DofusArenaTranslator.getInstance().getString("contentLoader.spell", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(AbstractGameClientInstance clientInstance) throws Exception {
/*  54 */     open(DofusArenaConfiguration.getInstance().getString("contentSpellFile"));
/*  55 */     clientInstance.fireContentInitializerDone((ContentInitializer)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(DocumentContainer container) {
/*  65 */     if (container == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  71 */       int spellCount = readInteger();
/*     */       
/*  73 */       for (int i = 0; i < spellCount; i++) {
/*  74 */         int spellId = readInteger();
/*  75 */         byte spellActionPoints = readByte();
/*  76 */         byte spellCastFrequencyMaxPerPlayer = readByte();
/*  77 */         byte spellCastFrequencyMaxPerTurn = readByte();
/*  78 */         byte spellCastFrequencyMinInterval = readByte();
/*  79 */         boolean spellCastTestLos = readBoolean();
/*  80 */         boolean spellCastOnlyLine = readBoolean();
/*  81 */         byte spellCastRangeMin = readByte();
/*  82 */         byte spellCastRangeMax = readByte();
/*  83 */         int spellValue = readInteger();
/*  84 */         int spellAiTargetId = readInteger();
/*  85 */         boolean testFreeCell = readBoolean();
/*  86 */         int spellScriptId = readInteger();
/*  87 */         int breedId = readInteger();
/*  88 */         String criterionString = readString();
/*  89 */         List<Criterion> criterion = CriteriaCompiler.compile(null, criterionString);
/*  90 */         boolean useAutomaticDescription = readBoolean();
/*     */         
/*  92 */         Spell spell = new Spell(spellId, breedId, spellActionPoints, spellCastFrequencyMaxPerPlayer, spellCastFrequencyMaxPerTurn, spellCastFrequencyMinInterval, spellCastTestLos, 
/*  93 */             spellCastOnlyLine, spellCastRangeMin, spellCastRangeMax, spellValue, spellAiTargetId, testFreeCell, spellScriptId, 
/*  94 */             criterion, useAutomaticDescription);
/*     */         
/*  96 */         SpellManager.getInstance().addSpell((AbstractSpell)spell);
/*     */       } 
/*     */ 
/*     */       
/* 100 */       int effectCount = readInteger();
/*     */       
/* 102 */       for (int j = 0; j < effectCount; j++) {
/* 103 */         readAndLoadEffect();
/*     */       }
/*     */     }
/* 106 */     catch (Exception e) {
/* 107 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 110 */     container.notifyOnLoadComplete();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEffectLoaded(Effect effect, String parentType, int parentId) {
/* 121 */     Spell spell = (Spell)SpellManager.getInstance().getSpell(parentId);
/* 122 */     if (spell != null) {
/* 123 */       spell.addEffect(effect);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOnLoadComplete() {
/* 132 */     m_logger.info("Spells loaded successfully");
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\contentInitializer\SpellLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */