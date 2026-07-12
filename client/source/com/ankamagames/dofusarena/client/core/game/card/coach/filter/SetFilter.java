/*     */ package com.ankamagames.dofusarena.client.core.game.card.coach.filter;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.ReferenceCoachCard;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SetFilter
/*     */   implements CoachCardFilter, FieldProvider
/*     */ {
/*  18 */   private String m_currentSetName = null;
/*     */   
/*     */   public static final String CURRENT_SET_NAME = "currentSetName";
/*     */   
/*  22 */   public static final String[] FIELDS = new String[] {
/*  23 */       "currentSetName"
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accept(CoachCard coachCard) {
/*  31 */     if (this.m_currentSetName == null) {
/*  32 */       return false;
/*     */     }
/*  34 */     return ((ReferenceCoachCard)coachCard.getReferenceCard()).getCardSetName().equals(this.m_currentSetName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentSetName(String currentSetName) {
/*  42 */     this.m_currentSetName = currentSetName;
/*     */     
/*  44 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "currentSetName");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCurrentSetName() {
/*  51 */     return this.m_currentSetName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getFields() {
/*  58 */     return FIELDS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getFieldValue(String fieldName) {
/*  68 */     if (fieldName.equals("currentSetName")) {
/*  69 */       return this.m_currentSetName;
/*     */     }
/*  71 */     return null;
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
/* 106 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\coach\filter\SetFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */