/*     */ package com.ankamagames.baseImpl.graphicalClient.core;
/*     */ 
/*     */ import com.ankamagames.framework.preferences.PreferenceStore;
/*     */ import com.ankamagames.framework.sounds.SoundManager;
/*     */ import com.ankamagames.framework.sounds.group.DefaultSourceGroup;
/*     */ import com.ankamagames.framework.sounds.group.MusicGroup;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
/*     */ import java.util.Locale;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GamePreferences
/*     */   implements FieldProvider
/*     */ {
/*     */   public static final String LANGUAGE_PREFERENCE_KEY = "language";
/*     */   public static final String SCREEN_WIDTH_PREFERENCE_KEY = "screenWidth";
/*     */   public static final String SCREEN_HEIGHT_PREFERENCE_KEY = "screenHeight";
/*     */   public static final String SCREEN_BPP_PREFERENCE_KEY = "screenBpp";
/*     */   public static final String FULL_SCREEN_PREFERENCE_KEY = "fullScreen";
/*     */   public static final String DOUBLE_BUFFERING_PREFERENCE_KEY = "doubleBufferingy";
/*     */   public static final String V_SYNC_PREFERENCE_KEY = "vSync";
/*     */   public static final String MUSIC_VOLUME_PREFERENCE_KEY = "musicVolume";
/*     */   public static final String SOUNDS_VOLUME_PREFERENCE_KEY = "soundsVolume";
/*     */   public static final String MUSIC_MUTE_PREFERENCE_KEY = "musicMute";
/*     */   public static final String SOUNDS_MUTE_PREFERENCE_KEY = "soundsMute";
/*  41 */   public static final String[] FIELDS = new String[] {
/*  42 */       "language", 
/*     */       
/*  44 */       "fullScreen", 
/*     */       
/*  46 */       "musicVolume", 
/*  47 */       "soundsVolume", 
/*  48 */       "musicMute", 
/*  49 */       "soundsMute"
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   private final PreferenceStore m_preferenceStore;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GamePreferences(PreferenceStore preferenceStore) {
/*  60 */     this.m_preferenceStore = preferenceStore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PreferenceStore getPreferenceStore() {
/*  67 */     return this.m_preferenceStore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLanguage() {
/*  74 */     return getPreferenceStore().getString("language");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLanguage(String language) {
/*  81 */     getPreferenceStore().setValue("language", language);
/*  82 */     firePropertyValueChanged("language");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScreenWidth() {
/*  89 */     return getPreferenceStore().getInt("screenWidth");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScreenHeight() {
/*  96 */     return getPreferenceStore().getInt("screenHeight");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScreenBpp() {
/* 103 */     return getPreferenceStore().getInt("screenBpp");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFullScreen() {
/* 110 */     return getPreferenceStore().getBoolean("fullScreen");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFullScreen(boolean fullScreen) {
/* 117 */     getPreferenceStore().setValue("fullScreen", fullScreen);
/* 118 */     firePropertyValueChanged("fullScreen");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getDoubleBuffering() {
/* 125 */     return getPreferenceStore().getBoolean("doubleBufferingy");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getVSync() {
/* 132 */     return getPreferenceStore().getBoolean("vSync");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMusicVolume() {
/* 139 */     return getPreferenceStore().getFloat("musicVolume");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMusicVolume(float musicVolume) {
/* 148 */     getPreferenceStore().setValue("musicVolume", musicVolume);
/* 149 */     firePropertyValueChanged("musicVolume");
/*     */ 
/*     */     
/* 152 */     MusicGroup musicsGroup = (MusicGroup)SoundManager.getInstance().getGroupByName("musics");
/* 153 */     if (musicsGroup != null) {
/* 154 */       musicsGroup.setMaxGain(musicVolume);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSoundsVolume() {
/* 163 */     return getPreferenceStore().getFloat("soundsVolume");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSoundsVolume(float soundsVolume) {
/* 172 */     getPreferenceStore().setValue("soundsVolume", soundsVolume);
/* 173 */     firePropertyValueChanged("soundsVolume");
/*     */ 
/*     */     
/* 176 */     DefaultSourceGroup soundsGroup = (DefaultSourceGroup)SoundManager.getInstance().getGroupByName("SoundScriptGroup");
/* 177 */     if (soundsGroup != null) {
/* 178 */       soundsGroup.setMaxGain(soundsVolume);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getMusicMute() {
/* 187 */     return getPreferenceStore().getBoolean("musicMute");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMusicMute(boolean musicMute) {
/* 196 */     getPreferenceStore().setValue("musicMute", musicMute);
/* 197 */     firePropertyValueChanged("musicMute");
/*     */ 
/*     */     
/* 200 */     MusicGroup musicsGroup = (MusicGroup)SoundManager.getInstance().getGroupByName("musics");
/* 201 */     if (musicsGroup != null) {
/* 202 */       musicsGroup.setMute(musicMute);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSoundsMute() {
/* 211 */     return getPreferenceStore().getBoolean("soundsMute");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSoundsMute(boolean soundsMute) {
/* 220 */     getPreferenceStore().setValue("soundsMute", soundsMute);
/* 221 */     firePropertyValueChanged("soundsMute");
/*     */ 
/*     */     
/* 224 */     DefaultSourceGroup soundsGroup = (DefaultSourceGroup)SoundManager.getInstance().getGroupByName("SoundScriptGroup");
/* 225 */     if (soundsGroup != null) {
/* 226 */       soundsGroup.setMute(soundsMute);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeDefaultValues() {
/* 237 */     getPreferenceStore().setDefault("language", Locale.getDefault().getLanguage());
/*     */ 
/*     */     
/* 240 */     getPreferenceStore().setDefault("screenWidth", 1024);
/* 241 */     getPreferenceStore().setDefault("screenHeight", 768);
/* 242 */     getPreferenceStore().setDefault("screenBpp", 32);
/* 243 */     getPreferenceStore().setDefault("fullScreen", false);
/* 244 */     getPreferenceStore().setDefault("doubleBufferingy", true);
/* 245 */     getPreferenceStore().setDefault("vSync", true);
/*     */ 
/*     */     
/* 248 */     getPreferenceStore().setDefault("musicVolume", 0.5D);
/* 249 */     getPreferenceStore().setDefault("soundsVolume", 0.5D);
/* 250 */     getPreferenceStore().setDefault("musicMute", false);
/* 251 */     getPreferenceStore().setDefault("soundsMute", false);
/*     */   }
/*     */ 
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
/* 270 */     if (fieldName.equals("language")) {
/* 271 */       return getLanguage();
/*     */     }
/*     */     
/* 274 */     if (fieldName.equals("fullScreen")) {
/* 275 */       return Boolean.valueOf(getFullScreen());
/*     */     }
/*     */     
/* 278 */     if (fieldName.equals("musicVolume")) {
/* 279 */       return Float.valueOf(getMusicVolume());
/*     */     }
/* 281 */     if (fieldName.equals("soundsVolume")) {
/* 282 */       return Float.valueOf(getSoundsVolume());
/*     */     }
/* 284 */     if (fieldName.equals("musicMute")) {
/* 285 */       return Boolean.valueOf(getMusicMute());
/*     */     }
/* 287 */     if (fieldName.equals("soundsMute")) {
/* 288 */       return Boolean.valueOf(getSoundsMute());
/*     */     }
/*     */     
/* 291 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getFields() {
/* 300 */     return FIELDS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFieldSynchronisable(String fieldName) {
/* 309 */     return false;
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
/*     */   public void setFieldValue(String fieldName, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void firePropertyValueChanged(String field) {
/* 336 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, field);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\core\GamePreferences.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */