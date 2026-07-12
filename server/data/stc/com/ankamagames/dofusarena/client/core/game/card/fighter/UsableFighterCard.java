/*     */ package com.ankamagames.dofusarena.client.core.game.card.fighter;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.common.game.fight.CardUseValidity;
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
/*     */ public class UsableFighterCard
/*     */   extends FighterCard
/*     */ {
/*     */   public static final String USABLE_FIELD = "usable";
/*     */   public static final String SMALL_DESCRIPTION_FIELD = "smallDescription";
/*  25 */   public static final String[] FIELDS = {
/*  26 */     "usable", 
/*  27 */     "smallDescription" };
/*     */   
/*     */ 
/*     */ 
/*  31 */   public static final String[] ALL_FIELDS = new String[FIELDS.length + FighterCard.FIELDS.length];
/*  32 */   static { System.arraycopy(FIELDS, 0, ALL_FIELDS, 0, FIELDS.length);
/*  33 */     System.arraycopy(FighterCard.FIELDS, 0, ALL_FIELDS, FIELDS.length, FighterCard.FIELDS.length);
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
/*     */ 
/*     */   private Fighter m_fighter;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UsableFighterCard(FighterCard fighterCard)
/*     */   {
/*  57 */     super(fighterCard.getId(), fighterCard.getType(), fighterCard.getSubType(), fighterCard.getActionPoints(), fighterCard.useOnlyInLine(), fighterCard.getRangeMin(), fighterCard.getRangeMax(), fighterCard.hasToTestLineOfSight(), fighterCard.hasToTestCellFree(), fighterCard.getValue(), fighterCard.canUseWhenCarried(), fighterCard.canUseWhenCarrying(), fighterCard.getScriptId());
/*     */     
/*  59 */     for (Effect effect : fighterCard) {
/*  60 */       addEffect(effect);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Fighter getFighter()
/*     */   {
/*  68 */     return this.m_fighter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setFighter(Fighter fighter)
/*     */   {
/*  75 */     this.m_fighter = fighter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CardUseValidity getCastValidity()
/*     */   {
/*  82 */     return DofusArenaGameEntity.getInstance().getFight().getCardUseValidity(this.m_fighter, this, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getFields()
/*     */   {
/*  92 */     return ALL_FIELDS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getFieldValue(String fieldName)
/*     */   {
/* 102 */     if (fieldName.equals("usable")) {
/* 103 */       if (getCastValidity() == CardUseValidity.OK) return Boolean.valueOf(true); return Boolean.valueOf(false);
/*     */     }
/* 105 */     if (fieldName.equals("smallDescription")) {
/* 106 */       StringBuilder smallDescriptionBuilder = new StringBuilder(getName());
/* 107 */       if (getActionPoints() != 0) {
/* 108 */         smallDescriptionBuilder.append(" (").append(getActionPoints()).append(' ').append(DofusArenaTranslator.getInstance().getString("AP", new Object[0])).append(")");
/*     */       }
/* 110 */       CardUseValidity castValidity = getCastValidity();
/* 111 */       if (castValidity != CardUseValidity.OK) {
/* 112 */         smallDescriptionBuilder.append('\n').append(castValidity);
/*     */       }
/* 114 */       return smallDescriptionBuilder.toString();
/*     */     }
/* 116 */     return super.getFieldValue(fieldName);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\fighter\UsableFighterCard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */