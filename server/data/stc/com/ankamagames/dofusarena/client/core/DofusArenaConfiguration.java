/*     */ package com.ankamagames.dofusarena.client.core;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.properties.PropertiesReaderWriter;
/*     */ import com.ankamagames.framework.fileFormat.properties.PropertyException;
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
/*     */ public class DofusArenaConfiguration
/*     */   extends PropertiesReaderWriter
/*     */ {
/*     */   public static final String CONFIGURATION_FILE = "config.properties";
/*     */   public static final String I18N_PATH = "i18nPath";
/*     */   public static final String DIALOGS_PATH = "dialogsPath";
/*     */   public static final String MAPS_PATH = "mapsPath";
/*     */   public static final String GFX_PATH = "gfxPath";
/*     */   public static final String MOBILE_GFX_PATH = "mobileGfxPath";
/*     */   public static final String SOUND_PATH = "soundPath";
/*     */   public static final String MUSIC_PATH = "musicPath";
/*     */   public static final String SHADERS_PATH = "shadersPath";
/*     */   public static final String PARTICLE_PATH = "particlePath";
/*     */   public static final String SCRIPT_PATH = "scriptPath";
/*     */   public static final String SPELLS_ICONS_PATH = "spellsIconsPath";
/*     */   public static final String SPELLS_ILLUSTRACTIONS_PATH = "spellsIllustrationsPath";
/*     */   public static final String EVENTS_ICONS_PATH = "eventsIconsPath";
/*     */   public static final String EVENTS_ILLUSTRACTIONS_PATH = "eventsIllustrationsPath";
/*     */   public static final String COACH_RANK_ICONS_PATH = "coachRankIconsPath";
/*     */   public static final String COACH_EQUIPMENT_PATH = "coachEquipmentPath";
/*     */   public static final String COACH_EQUIPMENT_ICONS_PATH = "coachEquipmentIconsPath";
/*     */   public static final String COACH_EQUIPMENT_ILLUSTRATIONS_PATH = "coachEquipmentIllustrationsPath";
/*     */   public static final String COACH_EQUIPMENT_TYPE_ICON_PATH = "coachEquipmentTypeIconPath";
/*     */   public static final String FIGHTER_SKIN_PATH = "fighterSkinPath";
/*     */   public static final String FIGHTER_EQUIPMENT_ICONS_PATH = "fighterEquipmentIconsPath";
/*     */   public static final String FIGHTER_EQUIPMENT_ILLUSTRATIONS_PATH = "fighterEquipmentIllustrationsPath";
/*     */   public static final String FIGHTER_EQUIPMENT_TYPE_ICON_PATH = "fighterEquipmentTypeIconPath";
/*     */   public static final String FIGHTER_EQUIPMENT_PATH = "fighterEquipmentPath";
/*     */   public static final String BREEDS_TIMELINE_ICON_PATH = "breedsTimelineIconPath";
/*     */   public static final String BREEDS_BACKGROUND_PATH = "breedsBackgroundPath";
/*     */   public static final String ACTIVATE_MAP_PARTICLES = "activateMapParticles";
/*     */   public static final String ACTIVATE_MAP_VISUAL_EFFECT = "activateMapVisualEffect";
/*     */   public static final String CONTENT_SPELL_FILE = "contentSpellFile";
/*     */   public static final String CONTENT_EVENT_FILE = "contentEventFile";
/*     */   public static final String CONTENT_SUMMONING_FILE = "contentSummoningFile";
/*     */   public static final String CONTENT_CARD_FILE = "contentCardFile";
/*     */   public static final String CONTENT_STATIC_EFFECT_FILE = "contentStaticEffectFile";
/*     */   public static final String THEME_DIRECTORY = "themeDirectory";
/*     */   public static final String THEME_FILE = "themeFile";
/*     */   public static final String FIGHT_DEFINITIONS_FILE = "fightDefinitionsFile";
/*     */   public static final String SHORTCUTS_FILE = "shortcutsFile";
/*     */   public static final String PLAYLIST_FILE = "playlistFile";
/*     */   public static final String STATISTICS_REPORTS_MODELS_FILE = "statisticsReportsModelsFile";
/*     */   public static final String ELEMENTS_FILE = "elementsFile";
/*     */   public static final String HIGHLIGHT_GFX_FILE = "highLightGfxFile";
/*     */   public static final String START_IN_OPENGL_THREAD = "startInOpenGLThread";
/*     */   public static final String SOUND_DEVICE = "soundDevice";
/*     */   public static final String SOUND_ENABLE = "soundEnable";
/*     */   public static final String CONNECTION_RETRY_COUNT = "connectionRetryCount";
/*     */   public static final String CONNECTION_RETRY_DELAY = "connectionRetryDelay";
/*     */   public static final String LAST_PROXY_GROUP_INDEX = "lastProxyGroupIndex";
/*     */   public static final String PROXY_GROUP = "proxyGroup";
/*     */   public static final String PROXY_ADDRESSES = "proxyAddresses";
/*     */   public static final String LAST_SELECTED_TEAM_PRESET_ID = "lastSelectedTeamPresetId";
/*  87 */   private static DofusArenaConfiguration m_instance = new DofusArenaConfiguration();
/*     */   
/*  89 */   public boolean m_safeMode = true;
/*     */   
/*     */ 
/*     */ 
/*     */   public static DofusArenaConfiguration getInstance()
/*     */   {
/*  95 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isStartInOpenGLThread()
/*     */   {
/*     */     try
/*     */     {
/* 103 */       return getBoolean("startInOpenGLThread");
/*     */     }
/*     */     catch (PropertyException localPropertyException) {}
/*     */     
/* 107 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setStartInOpenGLThread(boolean startInOpenGLThread)
/*     */   {
/* 114 */     setBoolean("startInOpenGLThread", startInOpenGLThread);
/* 115 */     save();
/*     */   }
/*     */   
/*     */ 
/*     */   public String getDialogsPath()
/*     */   {
/*     */     try
/*     */     {
/* 123 */       return getString("dialogsPath");
/*     */     }
/*     */     catch (PropertyException localPropertyException) {}
/*     */     
/* 127 */     return "";
/*     */   }
/*     */   
/*     */ 
/*     */   public short getLastSelectedTeamPresetId()
/*     */   {
/*     */     try
/*     */     {
/* 135 */       return (short)getInteger("lastSelectedTeamPresetId");
/*     */     }
/*     */     catch (PropertyException localPropertyException) {}
/*     */     
/* 139 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLastSelectedTeamPresetId(short teamPresetid)
/*     */   {
/* 148 */     setInteger("lastSelectedTeamPresetId", teamPresetid);
/* 149 */     save();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean load()
/*     */   {
/* 158 */     return load("");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean load(String fileName)
/*     */   {
/* 169 */     return super.load((fileName == null) || (fileName.length() == 0) ? "config.properties" : fileName);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean save()
/*     */   {
/* 178 */     return super.save("config.properties");
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\DofusArenaConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */