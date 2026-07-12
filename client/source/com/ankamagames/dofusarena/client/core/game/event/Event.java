/*     */ package com.ankamagames.dofusarena.client.core.game.event;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.contentInitializer.CastableDescriptionGenerator;
/*     */ import com.ankamagames.dofusarena.common.game.event.AbstractEvent;
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
/*     */ public class Event
/*     */   extends AbstractEvent
/*     */   implements FieldProvider
/*     */ {
/*     */   public static final String NAME_FIELD = "name";
/*     */   public static final String DESCRIPTION_FIELD = "description";
/*     */   public static final String ICON_URL_FIELD = "iconUrl";
/*     */   public static final String ILLUSTRATION_URL_FIELD = "illustrationUrl";
/*     */   public static final String CARD_TYPE_FIELD = "cardType";
/*  27 */   public static final String[] FIELDS = new String[] {
/*  28 */       "name", 
/*  29 */       "description", 
/*  30 */       "iconUrl", 
/*  31 */       "illustrationUrl", 
/*     */       
/*  33 */       "cardType"
/*     */     };
/*     */   
/*  36 */   private String m_description = null;
/*     */ 
/*     */   
/*     */   boolean m_eventUseAutomaticDescription;
/*     */ 
/*     */ 
/*     */   
/*     */   public Event(int id, boolean eventUseAutomaticDescription) {
/*  44 */     super(id);
/*  45 */     this.m_eventUseAutomaticDescription = eventUseAutomaticDescription;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  52 */     return DofusArenaTranslator.getInstance().getString(8, getId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  59 */     if (this.m_description == null) {
/*  60 */       this.m_description = CastableDescriptionGenerator.generateDescription(getId(), this.m_eventUseAutomaticDescription, (Iterable)getEventEffects(), null, 0, 0, true, false, (byte)0, (byte)0, (byte)0, 27, 9);
/*     */     }
/*  62 */     return this.m_description;
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
/*     */   public Object getFieldValue(String fieldName) {
/*  80 */     if (fieldName.equals("name")) {
/*  81 */       return getName();
/*     */     }
/*  83 */     if (fieldName.equals("description")) {
/*  84 */       return getDescription();
/*     */     }
/*  86 */     if (fieldName.equals("iconUrl")) {
/*     */       try {
/*  88 */         return String.format(DofusArenaConfiguration.getInstance().getString("eventsIconsPath"), new Object[] { Integer.valueOf(getId()) });
/*  89 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/*  92 */     if (fieldName.equals("illustrationUrl")) {
/*     */       try {
/*  94 */         return String.format(DofusArenaConfiguration.getInstance().getString("eventsIllustrationsPath"), new Object[] { Integer.valueOf(getId()) });
/*  95 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/*  99 */     if (fieldName.equals("cardType")) {
/* 100 */       return "event";
/*     */     }
/* 102 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getFields() {
/* 111 */     return FIELDS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFieldSynchronisable(String fieldName) {
/* 120 */     return false;
/*     */   }
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
/*     */   
/*     */   public void setFieldValue(String fieldName, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 148 */     return "Event : " + getName();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\event\Event.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */