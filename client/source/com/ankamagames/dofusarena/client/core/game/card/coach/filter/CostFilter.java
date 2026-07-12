/*     */ package com.ankamagames.dofusarena.client.core.game.card.coach.filter;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.ReferenceCoachCard;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
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
/*     */ public class CostFilter
/*     */   implements CoachCardFilter, FieldProvider
/*     */ {
/*     */   public static final int LEVEL_1_COST_FIELD = 200;
/*     */   public static final int LEVEL_2_COST_FIELD = 5000;
/*     */   public static final int LEVEL_3_COST_FIELD = 30000;
/*     */   public static final int LEVEL_4_COST_FIELD = 40000;
/*     */   public static final String MINIMUM_FIELD = "minimum";
/*     */   public static final String MAXIMUM_FIELD = "maximum";
/*     */   public static final String NAME_FIELD = "name";
/*     */   public static final short LAST_COST_LEVEL = 4;
/*  29 */   public static final String[] FIELDS = new String[] { "minimum", "maximum", "name" };
/*     */ 
/*     */ 
/*     */   
/*     */   private final int m_maximun;
/*     */ 
/*     */   
/*     */   private final int m_minimum;
/*     */ 
/*     */ 
/*     */   
/*     */   public CostFilter(int minimum, int maximum) {
/*  41 */     this.m_maximun = maximum;
/*  42 */     this.m_minimum = minimum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accept(CoachCard coachCard) {
/*  51 */     int cardValue = ((ReferenceCoachCard)coachCard.getReferenceCard()).getValue();
/*  52 */     return (cardValue >= this.m_minimum && cardValue <= this.m_maximun);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getFields() {
/*  61 */     return FIELDS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getFieldValue(String fieldName) {
/*  70 */     if (fieldName.equals("minimum")) {
/*  71 */       return Integer.valueOf(this.m_minimum);
/*     */     }
/*  73 */     if (fieldName.equals("maximum")) {
/*  74 */       return Integer.valueOf(this.m_maximun);
/*     */     }
/*  76 */     if (fieldName.equals("name")) {
/*  77 */       return toString();
/*     */     }
/*  79 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFieldValue(String fieldName, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prependFieldValue(String fieldName, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendFieldValue(String fieldName, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFieldSynchronisable(String fieldName) {
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     String value;
/* 126 */     if (this.m_minimum == 0 && this.m_maximun == Integer.MAX_VALUE) {
/* 127 */       return DofusArenaTranslator.getInstance().getString("coachCardCost.all", new Object[0]);
/*     */     }
/*     */     
/* 130 */     if (this.m_minimum == 0) {
/* 131 */       value = String.valueOf(DofusArenaTranslator.getInstance().getString("coachCardCost.lessThan", new Object[0])) + " " + this.m_maximun;
/* 132 */     } else if (this.m_maximun == Integer.MAX_VALUE) {
/* 133 */       value = String.valueOf(DofusArenaTranslator.getInstance().getString("coachCardCost.moreThan", new Object[0])) + " " + this.m_minimum;
/*     */     } else {
/* 135 */       value = String.valueOf(DofusArenaTranslator.getInstance().getString("coachCardCost.from", new Object[0])) + " " + this.m_minimum + " ";
/* 136 */       value = String.valueOf(value) + DofusArenaTranslator.getInstance().getString("coachCardCost.to", new Object[0]) + " " + this.m_maximun;
/*     */     } 
/* 138 */     return String.valueOf(value) + " " + DofusArenaTranslator.getInstance().getString("coachCardCost.unit", new Object[0]);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\coach\filter\CostFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */