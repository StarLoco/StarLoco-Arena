/*     */ package com.ankamagames.dofusarena.client.core.game.card.coach.filter;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.common.game.card.CoachCardType;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TypeFilter
/*     */   implements CoachCardFilter, FieldProvider
/*     */ {
/*     */   public static final String PANT_FILTER_FIELD = "pantFilter";
/*     */   public static final String HAIRS_FILTER_FIELD = "hairsFilter";
/*     */   public static final String TATOO_FILTER_FIELD = "tatooFilter";
/*     */   public static final String ARMBAND_FILTER_FIELD = "armbandFilter";
/*     */   public static final String SHOES_FILTER_FIELD = "shoesFilter";
/*     */   public static final String SHOULDERPAD_FILTER_FIELD = "shoulderpadFilter";
/*     */   public static final String CLOAK_FILTER_FIELD = "cloakFilter";
/*     */   public static final String TROUSERS_FILTER_FIELD = "trousersFilter";
/*     */   public static final String SHIR_FILTER_FIELD = "shirFilter";
/*     */   public static final String HAT_FILTER_FIELD = "hatFilter";
/*     */   public static final String STAFF_FILTER_FIELD = "staffFilter";
/*     */   public static final String PET_FILTER_FIELD = "petFilter";
/*     */   public static final String ALL_FILTER_DISABLED_FIELD = "allFilterDisabled";
/*  36 */   public static final String[] FIELDS = {
/*  37 */     "pantFilter", 
/*  38 */     "hairsFilter", 
/*  39 */     "tatooFilter", 
/*  40 */     "armbandFilter", 
/*  41 */     "shoesFilter", 
/*  42 */     "shoulderpadFilter", 
/*  43 */     "cloakFilter", 
/*  44 */     "trousersFilter", 
/*  45 */     "shirFilter", 
/*  46 */     "hatFilter", 
/*  47 */     "staffFilter", 
/*  48 */     "petFilter", "allFilterDisabled" };
/*     */   
/*     */ 
/*  51 */   private static final HashMap<CoachCardType, String[]> TYPE_FILEDS_CORRELATION = new HashMap();
/*     */   
/*  53 */   static { TYPE_FILEDS_CORRELATION.put(CoachCardType.PANT, new String[] { "pantFilter" });
/*  54 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.HAIRS, new String[] { "hairsFilter" });
/*  55 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.TATOO, new String[] { "tatooFilter" });
/*  56 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.ARMBAND, new String[] { "armbandFilter" });
/*  57 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.SHOES, new String[] { "shoesFilter" });
/*  58 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.SHOULDERPAD, new String[] { "shoulderpadFilter" });
/*  59 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.CLOAK, new String[] { "cloakFilter" });
/*  60 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.TROUSERS, new String[] { "trousersFilter" });
/*  61 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.SHIR, new String[] { "shirFilter" });
/*  62 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.HAT, new String[] { "hatFilter" });
/*  63 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.STAFF, new String[] { "staffFilter" });
/*  64 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.PET, new String[] { "petFilter" });
/*     */   }
/*     */   
/*  67 */   private final ArrayList<CoachCardType> m_types = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public TypeFilter()
/*     */   {
/*  74 */     for (CoachCardType type : TYPE_FILEDS_CORRELATION.keySet()) {
/*  75 */       this.m_types.add(type);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addType(CoachCardType type)
/*     */   {
/*  85 */     if (!this.m_types.contains(type)) {
/*  86 */       this.m_types.add(type);
/*     */       
/*     */ 
/*  89 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, (String[])TYPE_FILEDS_CORRELATION.get(type));
/*  90 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "allFilterDisabled");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeType(CoachCardType type)
/*     */   {
/* 100 */     this.m_types.remove(type);
/*     */     
/*     */ 
/* 103 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, (String[])TYPE_FILEDS_CORRELATION.get(type));
/* 104 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "allFilterDisabled");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addAll()
/*     */   {
/* 111 */     this.m_types.clear();
/* 112 */     for (CoachCardType type : TYPE_FILEDS_CORRELATION.keySet()) {
/* 113 */       addType(type);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeAll()
/*     */   {
/* 121 */     int size = this.m_types.size();
/* 122 */     for (int i = size - 1; i >= 0; i--) {
/* 123 */       removeType((CoachCardType)this.m_types.get(i));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 131 */     return this.m_types.isEmpty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(CoachCardType type)
/*     */   {
/* 139 */     return this.m_types.contains(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean accept(CoachCard coachCard)
/*     */   {
/* 148 */     return this.m_types.contains(coachCard.getType());
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
/* 166 */     if (fieldName.equals("pantFilter")) {
/* 167 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.PANT));
/*     */     }
/* 169 */     if (fieldName.equals("tatooFilter")) {
/* 170 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.TATOO));
/*     */     }
/* 172 */     if (fieldName.equals("armbandFilter")) {
/* 173 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.ARMBAND));
/*     */     }
/* 175 */     if (fieldName.equals("shoesFilter")) {
/* 176 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.SHOES));
/*     */     }
/* 178 */     if (fieldName.equals("shoulderpadFilter")) {
/* 179 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.SHOULDERPAD));
/*     */     }
/* 181 */     if (fieldName.equals("cloakFilter")) {
/* 182 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.CLOAK));
/*     */     }
/* 184 */     if (fieldName.equals("trousersFilter")) {
/* 185 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.TROUSERS));
/*     */     }
/* 187 */     if (fieldName.equals("shirFilter")) {
/* 188 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.SHIR));
/*     */     }
/* 190 */     if (fieldName.equals("hatFilter")) {
/* 191 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.HAT));
/*     */     }
/* 193 */     if (fieldName.equals("staffFilter")) {
/* 194 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.STAFF));
/*     */     }
/* 196 */     if (fieldName.equals("petFilter")) {
/* 197 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.PET));
/*     */     }
/* 199 */     if (fieldName.equals("hairsFilter")) {
/* 200 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.HAIRS));
/*     */     }
/* 202 */     if (fieldName.equals("allFilterDisabled")) {
/* 203 */       return Boolean.valueOf((!this.m_types.contains(CoachCardType.PANT)) && (!this.m_types.contains(CoachCardType.TATOO)) && 
/* 204 */         (!this.m_types.contains(CoachCardType.ARMBAND)) && (!this.m_types.contains(CoachCardType.SHOES)) && 
/* 205 */         (!this.m_types.contains(CoachCardType.SHOULDERPAD)) && (!this.m_types.contains(CoachCardType.CLOAK)) && 
/* 206 */         (!this.m_types.contains(CoachCardType.TROUSERS)) && (!this.m_types.contains(CoachCardType.SHIR)) && 
/* 207 */         (!this.m_types.contains(CoachCardType.HAT)) && (!this.m_types.contains(CoachCardType.STAFF)) && 
/* 208 */         (!this.m_types.contains(CoachCardType.PET)) && (!this.m_types.contains(CoachCardType.HAIRS)));
/*     */     }
/* 210 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getFields()
/*     */   {
/* 219 */     return FIELDS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFieldSynchronisable(String fieldName)
/*     */   {
/* 228 */     return false;
/*     */   }
/*     */   
/*     */   public void prependFieldValue(String fieldName, Object value) {}
/*     */   
/*     */   public void setFieldValue(String fieldName, Object value) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\coach\filter\TypeFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */