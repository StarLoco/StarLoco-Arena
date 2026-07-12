/*     */ package com.ankamagames.dofusarena.client.core.game.card.coach;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.common.game.card.AbstractCoachCard;
/*     */ import com.ankamagames.dofusarena.common.game.card.CardSet;
/*     */ import com.ankamagames.dofusarena.common.game.card.CoachCardType;
/*     */ import com.ankamagames.framework.kernel.core.common.GUIDGenerator;
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
/*     */ public class CoachCard
/*     */   extends AbstractCoachCard<ReferenceCoachCard>
/*     */   implements FieldProvider, Comparable<CoachCard>
/*     */ {
/*     */   public static final String ID_FIELD = "id";
/*     */   public static final String NAME_FIELD = "name";
/*     */   public static final String QUANTITY_FIELD = "quantity";
/*     */   public static final String LOCKED_FIELD = "locked";
/*     */   public static final String DESCRIPTION_FIELD = "description";
/*     */   public static final String ICON_URL_FIELD = "iconUrl";
/*     */   public static final String ILLUSTRATION_URL_FIELD = "illustrationUrl";
/*     */   public static final String TYPE_ICON_URL_FIELD = "typeIconUrl";
/*     */   public static final String CARD_SET_NAME_FIELD = "cardSetName";
/*     */   public static final String CARD_SET_SIZE_FIELD = "cardSetSize";
/*     */   public static final String CARD_INDEX_IN_SET_FIELD = "cardIndexInSet";
/*     */   public static final String CARD_TYPE_FIELD = "cardType";
/*     */   public static final String VALUE_FIELD = "value";
/*  36 */   public static final String[] FIELDS = {
/*  37 */     "id", 
/*  38 */     "name", 
/*  39 */     "quantity", 
/*  40 */     "locked", 
/*  41 */     "description", 
/*  42 */     "iconUrl", 
/*  43 */     "illustrationUrl", 
/*  44 */     "typeIconUrl", 
/*  45 */     "cardSetName", 
/*  46 */     "cardSetSize", 
/*  47 */     "cardIndexInSet", 
/*     */     
/*  49 */     "cardType", 
/*     */     
/*  51 */     "value" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CoachCard()
/*     */   {
/*  58 */     super(ReferenceCoachCardManager.getInstance());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  65 */     return ((ReferenceCoachCard)getReferenceCard()).getName();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CoachCardType getType()
/*     */   {
/*  72 */     return ((ReferenceCoachCard)getReferenceCard()).getType();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  79 */     return ((ReferenceCoachCard)getReferenceCard()).getDescription();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getCardSetName()
/*     */   {
/*  86 */     return ((ReferenceCoachCard)getReferenceCard()).getCardSetName();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getCardSetSize()
/*     */   {
/*  94 */     if (((ReferenceCoachCard)getReferenceCard()).getCardSet() != null) {
/*  95 */       return ((ReferenceCoachCard)getReferenceCard()).getCardSet().size();
/*     */     }
/*  97 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getCardIndexInSet()
/*     */   {
/* 107 */     if (((ReferenceCoachCard)getReferenceCard()).getCardSet() != null) {
/* 108 */       return ((ReferenceCoachCard)getReferenceCard()).getCardSet().indexOf(getReferenceCard()) + 1;
/*     */     }
/* 110 */     return 0;
/*     */   }
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
/*     */   public Object getFieldValue(String fieldName)
/*     */   {
/* 129 */     if (fieldName.equals("id")) {
/* 130 */       return Integer.valueOf(getReferenceId());
/*     */     }
/* 132 */     if (fieldName.equals("name")) {
/* 133 */       return getName();
/*     */     }
/* 135 */     if (fieldName.equals("quantity")) {
/* 136 */       return Short.valueOf(getQuantity());
/*     */     }
/* 138 */     if (fieldName.equals("locked")) {
/* 139 */       return Boolean.valueOf(isLocked());
/*     */     }
/* 141 */     if (fieldName.equals("description")) {
/* 142 */       return getDescription();
/*     */     }
/* 144 */     if (fieldName.equals("iconUrl")) {
/*     */       try {
/* 146 */         return String.format(DofusArenaConfiguration.getInstance().getString("coachEquipmentIconsPath"), new Object[] { Integer.valueOf(getReferenceId()) });
/*     */       }
/*     */       catch (Exception localException) {}
/*     */     }
/* 150 */     if (fieldName.equals("illustrationUrl")) {
/*     */       try {
/* 152 */         return String.format(DofusArenaConfiguration.getInstance().getString("coachEquipmentIllustrationsPath"), new Object[] { Integer.valueOf(getReferenceId()) });
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */     }
/* 156 */     if (fieldName.equals("typeIconUrl")) {
/*     */       try {
/* 158 */         return String.format(DofusArenaConfiguration.getInstance().getString("coachEquipmentTypeIconPath"), new Object[] { Integer.valueOf(getType().getId()) });
/*     */       }
/*     */       catch (Exception localException2) {}
/*     */     }
/* 162 */     if (fieldName.equals("cardSetName")) {
/* 163 */       return getCardSetName();
/*     */     }
/*     */     
/* 166 */     if (fieldName.equals("cardSetSize")) {
/* 167 */       return Integer.valueOf(getCardSetSize());
/*     */     }
/*     */     
/* 170 */     if (fieldName.equals("cardIndexInSet")) {
/* 171 */       return Integer.valueOf(getCardIndexInSet());
/*     */     }
/*     */     
/* 174 */     if (fieldName.equals("cardType")) {
/* 175 */       return "coachCard";
/*     */     }
/*     */     
/* 178 */     if (fieldName.equals("value")) {
/* 179 */       return Integer.valueOf(((ReferenceCoachCard)getReferenceCard()).getValue());
/*     */     }
/* 181 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getFields()
/*     */   {
/* 190 */     return FIELDS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFieldSynchronisable(String fieldName)
/*     */   {
/* 199 */     return false;
/*     */   }
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
/*     */   public void setFieldValue(String fieldName, Object value) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void release() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CoachCard getCopy()
/*     */   {
/* 234 */     CoachCard newCard = new CoachCard();
/* 235 */     newCard.m_referenceCard = ((ReferenceCoachCard)this.m_referenceCard);
/* 236 */     newCard.setQuantity(getQuantity());
/* 237 */     newCard.m_uid = GUIDGenerator.getGUID();
/* 238 */     return newCard;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CoachCard getClone()
/*     */   {
/* 247 */     CoachCard newCard = new CoachCard();
/* 248 */     newCard.m_referenceCard = ((ReferenceCoachCard)this.m_referenceCard);
/* 249 */     newCard.setQuantity(getQuantity());
/* 250 */     newCard.m_uid = this.m_uid;
/* 251 */     return newCard;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int compareTo(CoachCard coachCard)
/*     */   {
/* 260 */     return getName().compareTo(coachCard.getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 270 */     return getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\coach\CoachCard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */