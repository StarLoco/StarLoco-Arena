/*     */ package com.ankamagames.dofusarena.client.core.game.card.fighter;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.contentInitializer.CastableDescriptionGenerator;
/*     */ import com.ankamagames.dofusarena.common.constants.FighterCardType;
/*     */ import com.ankamagames.dofusarena.common.game.card.AbstractFighterCard;
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
/*     */ public class FighterCard
/*     */   extends AbstractFighterCard
/*     */   implements FieldProvider, Comparable
/*     */ {
/*     */   public static final String ID_FIELD = "id";
/*     */   public static final String NAME_FIELD = "name";
/*     */   public static final String DESCRIPTION_FIELD = "description";
/*     */   public static final String ICON_URL_FIELD = "iconUrl";
/*     */   public static final String ILLUSTRATION_URL_FIELD = "illustrationUrl";
/*     */   public static final String TYPE_ICON_URL_FIELD = "typeIconUrl";
/*     */   public static final String CARD_TYPE_FIELD = "cardType";
/*     */   public static final String VALUE_FIELD = "value";
/*     */   public static final String ACTION_POINTS_FIELD = "actionPoints";
/*  33 */   public static final String[] FIELDS = {
/*  34 */     "id", 
/*  35 */     "name", 
/*  36 */     "description", 
/*  37 */     "iconUrl", 
/*  38 */     "illustrationUrl", 
/*  39 */     "typeIconUrl", 
/*     */     
/*  41 */     "cardType", 
/*     */     
/*  43 */     "value", 
/*  44 */     "actionPoints" };
/*     */   
/*     */ 
/*     */   private int m_subType;
/*     */   
/*     */   private int m_scriptId;
/*     */   
/*  51 */   private String m_description = null;
/*     */   
/*     */   public FighterCard(int id, FighterCardType type, int cardSubType, int actionPoints, boolean useOnlyInLine, int useRangeMin, int useRangeMax, boolean useTestLineOfSight, boolean useTestCellFree, int goldValue, boolean canUseWhenCarried, boolean canUseWhenCarrying, int scriptId)
/*     */   {
/*  55 */     super(id, type, actionPoints, useOnlyInLine, useRangeMin, useRangeMax, useTestLineOfSight, useTestCellFree, goldValue, canUseWhenCarried, canUseWhenCarrying);
/*     */     
/*  57 */     this.m_subType = cardSubType;
/*  58 */     this.m_scriptId = scriptId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  65 */     return DofusArenaTranslator.getInstance().getString(1, getId());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  72 */     if (this.m_description == null) {
/*  73 */       this.m_description = CastableDescriptionGenerator.generateDescription(getId(), true, getUseTimeEffects(), getEquippementTimeEffects(), getRangeMin(), getRangeMax(), true, false, (byte)0, (byte)0, (byte)0, 21, 2);
/*     */     }
/*  75 */     return this.m_description;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getScriptId()
/*     */   {
/*  82 */     return this.m_scriptId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getSubType()
/*     */   {
/*  89 */     return this.m_subType;
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
/*     */   public Object getFieldValue(String fieldName)
/*     */   {
/* 107 */     if (fieldName.equals("id")) {
/* 108 */       return Integer.valueOf(getId());
/*     */     }
/* 110 */     if (fieldName.equals("name")) {
/* 111 */       return getName();
/*     */     }
/* 113 */     if (fieldName.equals("description")) {
/* 114 */       return getDescription();
/*     */     }
/* 116 */     if (fieldName.equals("iconUrl")) {
/*     */       try {
/* 118 */         return String.format(DofusArenaConfiguration.getInstance().getString("fighterEquipmentIconsPath"), new Object[] { Integer.valueOf(getId()) });
/*     */       }
/*     */       catch (Exception localException) {}
/*     */     }
/* 122 */     if (fieldName.equals("illustrationUrl")) {
/*     */       try {
/* 124 */         return String.format(DofusArenaConfiguration.getInstance().getString("fighterEquipmentIllustrationsPath"), new Object[] { Integer.valueOf(getId()) });
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */     }
/* 128 */     if (fieldName.equals("typeIconUrl")) {
/*     */       try {
/* 130 */         return String.format(DofusArenaConfiguration.getInstance().getString("fighterEquipmentTypeIconPath"), new Object[] { Byte.valueOf(getType().getIndex()), Integer.valueOf(getSubType()) });
/*     */       }
/*     */       catch (Exception localException2) {}
/*     */     }
/*     */     
/* 135 */     if (fieldName.equals("cardType")) {
/* 136 */       return "equipment";
/*     */     }
/*     */     
/* 139 */     if (fieldName.equals("value")) {
/* 140 */       return Integer.valueOf(getValue());
/*     */     }
/* 142 */     if (fieldName.equals("actionPoints")) {
/* 143 */       return Integer.valueOf(getActionPoints());
/*     */     }
/* 145 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getFields()
/*     */   {
/* 154 */     return FIELDS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFieldSynchronisable(String fieldName)
/*     */   {
/* 163 */     return false;
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
/*     */   public int compareTo(Object o)
/*     */   {
/* 190 */     if ((o instanceof FighterCard))
/*     */     {
/* 192 */       FighterCard comparedCard = (FighterCard)o;
/*     */       
/* 194 */       if (getSubType() > comparedCard.getSubType())
/* 195 */         return 1;
/* 196 */       if (getSubType() < comparedCard.getSubType()) {
/* 197 */         return -1;
/*     */       }
/* 199 */       return getName().compareTo(comparedCard.getName());
/*     */     }
/* 201 */     throw new RuntimeException("attempting to compare a " + o.getClass().getName() + " to a " + getClass().getName());
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\fighter\FighterCard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */