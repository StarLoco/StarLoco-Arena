/*     */ package com.ankamagames.dofusarena.client.chat;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UserFilter
/*     */   implements FieldProvider
/*     */ {
/*     */   public static final String FRIEND = "friend";
/*     */   public static final String IGNORE = "ignore";
/*     */   public static final String PARTY = "party";
/*     */   public static final String GUILD = "guild";
/*  22 */   public static final String[] FIELDS = new String[] { "friend", "ignore", "party", "guild" };
/*     */   
/*  24 */   private ArrayList<String> m_typeEnable = new ArrayList<String>();
/*     */   
/*     */   UserFilter() {
/*  27 */     this.m_typeEnable.add("friend");
/*  28 */     this.m_typeEnable.add("ignore");
/*  29 */     this.m_typeEnable.add("party");
/*  30 */     this.m_typeEnable.add("guild");
/*  31 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("contact.list.filter", this);
/*     */   }
/*     */   
/*     */   public boolean isEnabled(String type) {
/*  35 */     return this.m_typeEnable.contains(type);
/*     */   }
/*     */   
/*     */   public void setEnabled(String type) {
/*  39 */     if (!isEnabled(type)) {
/*  40 */       this.m_typeEnable.add(type);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDisabled(String type) {
/*  45 */     if (isEnabled(type)) {
/*  46 */       this.m_typeEnable.remove(type);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean accept(DofusArenaUser user) {
/*  51 */     if (this.m_typeEnable.contains("friend") && user.isType(DofusArenaUser.FRIEND)) {
/*  52 */       return true;
/*     */     }
/*  54 */     if (this.m_typeEnable.contains("ignore") && user.isType(DofusArenaUser.IGNORE)) {
/*  55 */       return true;
/*     */     }
/*  57 */     if (this.m_typeEnable.contains("party") && user.isType(DofusArenaUser.PARTY)) {
/*  58 */       return true;
/*     */     }
/*  60 */     if (this.m_typeEnable.contains("guild") && user.isType(DofusArenaUser.GUILD)) {
/*  61 */       return true;
/*     */     }
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getFields() {
/*  70 */     return FIELDS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getFieldValue(String fieldName) {
/*  80 */     return Boolean.valueOf(this.m_typeEnable.contains(fieldName));
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
/* 115 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\chat\UserFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */