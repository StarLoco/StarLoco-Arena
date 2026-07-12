/*     */ package com.ankamagames.dofusarena.client.core.contentInitializer;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
/*     */ import com.ankamagames.baseImpl.graphicalClient.core.contentLoader.ContentInitializer;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCardSet;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.ReferenceCoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.ReferenceCoachCardManager;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCardManager;
/*     */ import com.ankamagames.dofusarena.common.constants.FighterCardType;
/*     */ import com.ankamagames.dofusarena.common.game.card.AbstractFighterCard;
/*     */ import com.ankamagames.dofusarena.common.game.card.AbstractReferenceCoachCard;
/*     */ import com.ankamagames.dofusarena.common.game.card.CardSet;
/*     */ import com.ankamagames.dofusarena.common.game.card.CardSetManager;
/*     */ import com.ankamagames.dofusarena.common.game.card.CoachCardType;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CardLoader
/*     */   extends EffectContentDocumentLoader
/*     */ {
/*  30 */   private static final CardLoader m_instance = new CardLoader();
/*     */   
/*     */   public static CardLoader getInstance() {
/*  33 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CardLoader() {
/*  40 */     setContentDocumentExtension(".dat");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  49 */     return DofusArenaTranslator.getInstance().getString("contentLoader.card", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(AbstractGameClientInstance clientInstance) throws Exception {
/*  58 */     open(DofusArenaConfiguration.getInstance().getString("contentCardFile"));
/*  59 */     clientInstance.fireContentInitializerDone((ContentInitializer)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(DocumentContainer container) {
/*  70 */     if (container == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  76 */       int cardCount = readInteger();
/*     */       int i;
/*  78 */       for (i = 0; i < cardCount; i++) {
/*     */         CoachCardSet coachCardSet;
/*  80 */         int cardId = readInteger();
/*  81 */         int cardType = readInteger();
/*  82 */         int cardValue = readInteger();
/*  83 */         int cardSet = readInteger();
/*     */         
/*  85 */         ReferenceCoachCard coachCard = new ReferenceCoachCard(cardId, CoachCardType.getFromId(cardType), cardSet, cardValue);
/*     */         
/*  87 */         ReferenceCoachCardManager.getInstance().add((AbstractReferenceCoachCard)coachCard);
/*     */         
/*  89 */         CardSet<AbstractReferenceCoachCard> set = CardSetManager.getInstance().get(cardSet);
/*  90 */         if (set == null) {
/*  91 */           coachCardSet = new CoachCardSet(cardSet);
/*  92 */           CardSetManager.getInstance().add((CardSet)coachCardSet);
/*     */         } 
/*  94 */         coachCardSet.addReferenceCard((AbstractReferenceCoachCard)coachCard);
/*     */       } 
/*     */ 
/*     */       
/*  98 */       cardCount = readInteger();
/*     */       
/* 100 */       for (i = 0; i < cardCount; i++) {
/*     */         
/* 102 */         int cardId = readInteger();
/* 103 */         FighterCardType cardType = FighterCardType.getTypeFromIndex(readByte());
/* 104 */         byte cardWeaponActionPoints = readByte();
/* 105 */         boolean cardWeaponOnlyLine = readBoolean();
/* 106 */         int cardWeaponRangeMin = readInteger();
/* 107 */         int cardWeaponRangeMax = readInteger();
/* 108 */         boolean cardWeaponTestLos = readBoolean();
/* 109 */         boolean cardWeaponTestFreeCell = readBoolean();
/* 110 */         int cardValue = readInteger();
/* 111 */         boolean cardWeaponAllowedWhenCarried = readBoolean();
/* 112 */         boolean cardWeaponAllowedWhenCarrying = readBoolean();
/* 113 */         int cardScriptId = readInteger();
/* 114 */         int cardSubType = readInteger();
/*     */         
/* 116 */         FighterCard fighterCard = new FighterCard(cardId, cardType, cardSubType, cardWeaponActionPoints, cardWeaponOnlyLine, cardWeaponRangeMin, cardWeaponRangeMax, cardWeaponTestLos, 
/* 117 */             cardWeaponTestFreeCell, cardValue, cardWeaponAllowedWhenCarrying, cardWeaponAllowedWhenCarried, cardScriptId);
/*     */         
/* 119 */         FighterCardManager.getInstance().add((AbstractFighterCard)fighterCard);
/*     */       } 
/*     */ 
/*     */       
/* 123 */       int effectCount = readInteger();
/*     */       
/* 125 */       for (int j = 0; j < effectCount; j++) {
/* 126 */         readAndLoadEffect();
/*     */       }
/*     */     }
/* 129 */     catch (Exception e) {
/* 130 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 133 */     notifyOnLoadComplete();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEffectLoaded(Effect effect, String parentType, int parentId) {
/* 145 */     if (parentType.startsWith("FIGHTER_CARD")) {
/* 146 */       FighterCard fighterCard = (FighterCard)FighterCardManager.getInstance().get(parentId);
/* 147 */       if (fighterCard != null) {
/* 148 */         fighterCard.addEffect(effect);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOnLoadComplete() {
/* 158 */     m_logger.info("Cards loaded successfully");
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\contentInitializer\CardLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */