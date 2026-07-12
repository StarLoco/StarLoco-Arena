/*     */ package com.ankamagames.dofusarena.client.core.game.spell;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.common.game.fight.SpellCastValidity;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
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
/*     */ public class UsableSpell
/*     */   extends Spell
/*     */ {
/*     */   public static final String USABLE_FIELD = "usable";
/*     */   public static final String SMALL_DESCRIPTION_FIELD = "smallDescription";
/*  24 */   public static final String[] FIELDS = new String[] {
/*  25 */       "usable", 
/*  26 */       "smallDescription"
/*     */     };
/*     */ 
/*     */   
/*  30 */   public static final String[] ALL_FIELDS = new String[FIELDS.length + Spell.FIELDS.length]; static {
/*  31 */     System.arraycopy(FIELDS, 0, ALL_FIELDS, 0, FIELDS.length);
/*  32 */     System.arraycopy(Spell.FIELDS, 0, ALL_FIELDS, FIELDS.length, Spell.FIELDS.length);
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
/*     */ 
/*     */   
/*     */   public UsableSpell(Spell spell) {
/*  58 */     super(spell.getId(), spell.getBreedId(), spell.getActionPoints(), spell.getCastMaxPerTarget(), spell.getCastMaxPerTurn(), spell.getCastInterval(), spell.hasToTestLineOfSight(), spell.castOnlyInLine(), spell.getRangeMin(), spell.getRangeMax(), spell.getValue(), spell.getTarget(), spell.hasToTestFreeCell(), spell.getScriptId(), spell.getCastCriterions(), spell.isUseAutomaticDescription());
/*     */     
/*  60 */     this.m_effects = spell.getEffects();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fighter getFighter() {
/*  67 */     return this.m_fighter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFighter(Fighter fighter) {
/*  74 */     this.m_fighter = fighter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpellCastValidity getCastValidity() {
/*  81 */     if (this.m_fighter != null) {
/*  82 */       return DofusArenaGameEntity.getInstance().getFight().getSpellCastValidity((AbstractFighter)this.m_fighter, this, null);
/*     */     }
/*  84 */     return SpellCastValidity.OK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getFields() {
/*  95 */     return ALL_FIELDS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getFieldValue(String fieldName) {
/* 105 */     if (fieldName.equals("usable")) {
/* 106 */       return (getCastValidity() == SpellCastValidity.OK) ? Boolean.valueOf(true) : Boolean.valueOf(false);
/*     */     }
/* 108 */     if (fieldName.equals("smallDescription")) {
/* 109 */       StringBuilder smallDescriptionBuilder = new StringBuilder(getName());
/* 110 */       smallDescriptionBuilder.append(" (").append(getActionPoints()).append(' ').append(DofusArenaTranslator.getInstance().getString("AP", new Object[0])).append(")");
/* 111 */       SpellCastValidity castValidity = getCastValidity();
/* 112 */       if (castValidity != SpellCastValidity.OK) {
/* 113 */         smallDescriptionBuilder.append('\n').append(DofusArenaTranslator.getInstance().getString(castValidity.toString(), new Object[0]));
/*     */       }
/* 115 */       return smallDescriptionBuilder.toString();
/*     */     } 
/* 117 */     return super.getFieldValue(fieldName);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\spell\UsableSpell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */