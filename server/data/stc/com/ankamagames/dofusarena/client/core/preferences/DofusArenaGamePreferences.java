/*     */ package com.ankamagames.dofusarena.client.core.preferences;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphicalClient.core.GamePreferences;
/*     */ import com.ankamagames.framework.preferences.PreferenceStore;
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
/*     */ public class DofusArenaGamePreferences
/*     */   extends GamePreferences
/*     */ {
/*     */   public static final String REMEMBER_LAST_LOGIN_PREFERENCE_KEY = "rememberLastLogin";
/*     */   public static final String LAST_LOGIN_PREFERENCE_KEY = "lastLogin";
/*  21 */   public static final String[] FIELDS = {
/*  22 */     "rememberLastLogin", 
/*  23 */     "lastLogin" };
/*     */   
/*     */ 
/*     */ 
/*  27 */   public static final String[] ALL_FIELDS = new String[FIELDS.length + GamePreferences.FIELDS.length];
/*  28 */   static { System.arraycopy(FIELDS, 0, ALL_FIELDS, 0, FIELDS.length);
/*  29 */     System.arraycopy(GamePreferences.FIELDS, 0, ALL_FIELDS, FIELDS.length, GamePreferences.FIELDS.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DofusArenaGamePreferences(PreferenceStore preferenceStore)
/*     */   {
/*  38 */     super(preferenceStore);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initializeDefaultValues()
/*     */   {
/*  48 */     super.initializeDefaultValues();
/*     */     
/*  50 */     getPreferenceStore().setDefault("fullScreen", true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getRememberLastLogin()
/*     */   {
/*  58 */     return getPreferenceStore().getBoolean("rememberLastLogin");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setRememberLastLogin(boolean rememberLastLogin)
/*     */   {
/*  65 */     getPreferenceStore().setValue("rememberLastLogin", rememberLastLogin);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getLastLogin()
/*     */   {
/*  72 */     return getPreferenceStore().getString("lastLogin");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setLastLogin(String lastLogin)
/*     */   {
/*  79 */     getPreferenceStore().setValue("lastLogin", lastLogin);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getFields()
/*     */   {
/*  89 */     return ALL_FIELDS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getFieldValue(String fieldName)
/*     */   {
/*  99 */     if (fieldName.equals("rememberLastLogin")) {
/* 100 */       return Boolean.valueOf(getRememberLastLogin());
/*     */     }
/* 102 */     if (fieldName.equals("lastLogin")) {
/* 103 */       return getLastLogin();
/*     */     }
/* 105 */     return super.getFieldValue(fieldName);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\preferences\DofusArenaGamePreferences.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */