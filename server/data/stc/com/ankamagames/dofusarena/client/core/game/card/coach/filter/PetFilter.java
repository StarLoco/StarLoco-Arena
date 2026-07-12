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
/*     */ public class PetFilter
/*     */   implements CoachCardFilter, FieldProvider
/*     */ {
/*     */   public static final String HEART_FILTER = "heartFilter";
/*     */   public static final String MEMBER_FILTER = "memberFilter";
/*     */   public static final String HEAD_FILTER = "headFilter";
/*     */   public static final String BODY_FILTER = "bodyFilter";
/*     */   public static final String ACCESSORY_FILTER = "accessoryFilter";
/*  28 */   public static final String[] FIELDS = {
/*  29 */     "heartFilter", 
/*  30 */     "memberFilter", 
/*  31 */     "headFilter", 
/*  32 */     "bodyFilter", 
/*  33 */     "accessoryFilter" };
/*     */   
/*     */ 
/*  36 */   private static final HashMap<CoachCardType, String[]> TYPE_FILEDS_CORRELATION = new HashMap();
/*     */   
/*  38 */   static { TYPE_FILEDS_CORRELATION.put(CoachCardType.PET_HEART, new String[] { "heartFilter" });
/*  39 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.PET_MEMBER, new String[] { "memberFilter" });
/*  40 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.PET_HEAD, new String[] { "headFilter" });
/*  41 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.PET_BODY, new String[] { "bodyFilter" });
/*  42 */     TYPE_FILEDS_CORRELATION.put(CoachCardType.PET_ACCESSORY, new String[] { "accessoryFilter" });
/*     */   }
/*     */   
/*  45 */   private final ArrayList<CoachCardType> m_types = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PetFilter()
/*     */   {
/*  52 */     for (CoachCardType type : TYPE_FILEDS_CORRELATION.keySet()) {
/*  53 */       this.m_types.add(type);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addType(CoachCardType type)
/*     */   {
/*  63 */     if (!this.m_types.contains(type)) {
/*  64 */       this.m_types.add(type);
/*     */       
/*     */ 
/*  67 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, (String[])TYPE_FILEDS_CORRELATION.get(type));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeType(CoachCardType type)
/*     */   {
/*  77 */     this.m_types.remove(type);
/*     */     
/*     */ 
/*  80 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, (String[])TYPE_FILEDS_CORRELATION.get(type));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addAll()
/*     */   {
/*  87 */     this.m_types.clear();
/*  88 */     for (CoachCardType type : TYPE_FILEDS_CORRELATION.keySet()) {
/*  89 */       addType(type);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeAll()
/*     */   {
/*  97 */     int size = this.m_types.size();
/*  98 */     for (int i = size - 1; i >= 0; i--) {
/*  99 */       removeType((CoachCardType)this.m_types.get(i));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 107 */     return this.m_types.isEmpty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(CoachCardType type)
/*     */   {
/* 115 */     return this.m_types.contains(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean accept(CoachCard coachCard)
/*     */   {
/* 124 */     return this.m_types.contains(coachCard.getType());
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
/* 142 */     if (fieldName.equals("heartFilter")) {
/* 143 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.PET_HEART));
/*     */     }
/* 145 */     if (fieldName.equals("memberFilter")) {
/* 146 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.PET_MEMBER));
/*     */     }
/* 148 */     if (fieldName.equals("headFilter")) {
/* 149 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.PET_HEAD));
/*     */     }
/* 151 */     if (fieldName.equals("bodyFilter")) {
/* 152 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.PET_BODY));
/*     */     }
/* 154 */     if (fieldName.equals("accessoryFilter")) {
/* 155 */       return Boolean.valueOf(this.m_types.contains(CoachCardType.PET_ACCESSORY));
/*     */     }
/* 157 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getFields()
/*     */   {
/* 166 */     return FIELDS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFieldSynchronisable(String fieldName)
/*     */   {
/* 175 */     return false;
/*     */   }
/*     */   
/*     */   public void prependFieldValue(String fieldName, Object value) {}
/*     */   
/*     */   public void setFieldValue(String fieldName, Object value) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\coach\filter\PetFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */