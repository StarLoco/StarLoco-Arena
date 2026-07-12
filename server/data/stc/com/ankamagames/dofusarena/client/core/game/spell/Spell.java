/*     */ package com.ankamagames.dofusarena.client.core.game.spell;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.contentInitializer.CastableDescriptionGenerator;
/*     */ import com.ankamagames.dofusarena.common.game.spell.AbstractSpell;
/*     */ import com.ankamagames.framework.ai.criteria.Criterion;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
/*     */ import java.net.URL;
/*     */ import java.util.List;
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
/*     */ public class Spell
/*     */   extends AbstractSpell
/*     */   implements FieldProvider, Comparable
/*     */ {
/*     */   public static final String ID_FIELD = "id";
/*     */   public static final String NAME_FIELD = "name";
/*     */   public static final String DESCRIPTION_FIELD = "description";
/*     */   public static final String ICON_URL_FIELD = "iconUrl";
/*     */   public static final String ILLUSTRATION_URL_FIELD = "illustrationUrl";
/*     */   public static final String CARD_TYPE_FIELD = "cardType";
/*     */   public static final String VALUE_FIELD = "value";
/*     */   public static final String ACTION_POINTS_FIELD = "actionPoints";
/*  37 */   public static final String[] FIELDS = {
/*  38 */     "id", 
/*  39 */     "name", 
/*  40 */     "description", 
/*  41 */     "iconUrl", 
/*  42 */     "illustrationUrl", 
/*     */     
/*  44 */     "cardType", 
/*     */     
/*  46 */     "value", 
/*  47 */     "actionPoints" };
/*     */   
/*     */   private final int m_scriptId;
/*     */   
/*  51 */   private String m_description = null;
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
/*     */   private final boolean m_useAutomaticDescription;
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
/*     */   public Spell(int id, int breedId, byte actionPoints, byte castMaxPerTarget, byte castMaxPerTurn, byte castInterval, boolean lineOfSight, boolean castOnLine, byte rangeMin, byte rangeMax, int value, int target, boolean testFreeCell, int scriptId, List<Criterion> castCriterion, boolean useAutomaticDescription)
/*     */   {
/*  76 */     super(id, breedId, actionPoints, castMaxPerTarget, castMaxPerTurn, castInterval, lineOfSight, castOnLine, rangeMin, rangeMax, value, target, testFreeCell, castCriterion);
/*     */     
/*  78 */     this.m_scriptId = scriptId;
/*  79 */     this.m_useAutomaticDescription = useAutomaticDescription;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  88 */     return DofusArenaTranslator.getInstance().getString(3, getId());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  95 */     if (this.m_description == null) {
/*  96 */       this.m_description = CastableDescriptionGenerator.generateDescription(getId(), isUseAutomaticDescription(), getEffects(), null, getRangeMin(), getRangeMax(), false, hasToTestFreeCell(), getCastInterval(), getCastMaxPerTarget(), getCastMaxPerTurn(), 20, 4);
/*     */     }
/*  98 */     return this.m_description;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getScriptId()
/*     */   {
/* 107 */     return this.m_scriptId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isUseAutomaticDescription()
/*     */   {
/* 114 */     return this.m_useAutomaticDescription;
/*     */   }
/*     */   
/*     */ 
/*     */   public URL getIconUrl()
/*     */   {
/*     */     try
/*     */     {
/* 122 */       return new URL(String.format(DofusArenaConfiguration.getInstance().getString("spellsIconsPath"), new Object[] { Integer.valueOf(getId()) }));
/*     */     }
/*     */     catch (Exception localException) {}
/* 125 */     return null;
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
/* 143 */     if (fieldName.equals("id")) {
/* 144 */       return Integer.valueOf(getId());
/*     */     }
/* 146 */     if (fieldName.equals("name")) {
/* 147 */       return getName();
/*     */     }
/* 149 */     if (fieldName.equals("description")) {
/* 150 */       return getDescription();
/*     */     }
/* 152 */     if (fieldName.equals("iconUrl")) {
/*     */       try {
/* 154 */         return String.format(DofusArenaConfiguration.getInstance().getString("spellsIconsPath"), new Object[] { Integer.valueOf(getId()) });
/*     */       }
/*     */       catch (Exception localException) {}
/*     */     }
/*     */     
/* 159 */     if (fieldName.equals("illustrationUrl")) {
/*     */       try {
/* 161 */         return String.format(DofusArenaConfiguration.getInstance().getString("spellsIllustrationsPath"), new Object[] { Integer.valueOf(getId()) });
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */     }
/*     */     
/* 166 */     if (fieldName.equals("cardType")) {
/* 167 */       return "spell";
/*     */     }
/*     */     
/* 170 */     if (fieldName.equals("actionPoints")) {
/* 171 */       return Byte.valueOf(getActionPoints());
/*     */     }
/* 173 */     if (fieldName.equals("value")) {
/* 174 */       return Integer.valueOf(getValue());
/*     */     }
/*     */     
/* 177 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getFields()
/*     */   {
/* 186 */     return FIELDS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFieldSynchronisable(String fieldName)
/*     */   {
/* 195 */     return false;
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
/* 222 */     if ((o instanceof Spell)) {
/* 223 */       return getName().compareTo(((Spell)o).getName());
/*     */     }
/* 225 */     throw new RuntimeException("attempting to compare a " + o.getClass().getName() + " to a " + getClass().getName());
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\spell\Spell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */