/*    */ package com.ankamagames.dofusarena.client;
/*    */ 
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface DofusArenaClientConstants
/*    */ {
/*    */   public static final boolean DEBUG = false;
/*    */   public static final String ONLINE_HELP = "http://www.dofus-arena.com";
/*    */   public static final String DEBUG_CONSOLE_SHORTCUT_KEY = "debug";
/*    */   public static final String COMMON_SHORTCUT_KEY = "common";
/*    */   public static final String WORLD_SHORTCUT_KEY = "world";
/*    */   public static final String FIGHT_SHORTCUT_KEY = "fight";
/* 34 */   public static final URL DEBUG_CONSOLE_COMMANDS_PATH = DofusArenaClientConstants.class.getResource("/com/ankamagames/dofusarena/client/console/DofusArenaDebugConsoleCommandDescriptorSet.xml");
/* 35 */   public static final URL CONSOLE_COMMANDS_PATH = DofusArenaClientConstants.class.getResource("/com/ankamagames/dofusarena/client/console/DofusArenaConsoleCommandDescriptorSet.xml");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public static final URL CHAT_COMMANDS_PATH = DofusArenaClientConstants.class.getResource("/com/ankamagames/dofusarena/client/chat/console/ChatCommandDescriptorSet.xml");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final String PETRIFIED_FIGHTER_GFX_FORMAT = "%d%d_0";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final String DEFAULT_COACH_GFX_FORMAT = "700%d";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public static final int[] FIGHTER_ACTIVE_PARTICLE_SYSTEM_FILE_ID = new int[] { 9002, 9003 };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public static final int[] FIGHTER_TEAM_PARTICLE_SYSTEM_FILE_ID = new int[] { 9000, 9001 };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final int FIGHTER_ROOT_PARTICLE_SYSTEM_FILE_ID = 9004;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public static final float[] ZONE_EFFECT_COLOR = new float[] { 1.0F, 0.0F, 0.0F, 0.6F };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 75 */   public static final float[] RANGE_COLOR = new float[] { 0.0F, 0.8F, 1.0F, 0.6F };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 80 */   public static final float[] RANGE_COLOR_WITH_CONSTRAINTS = new float[] { 0.2F, 0.2F, 0.6F, 0.8F };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 85 */   public static final float[][] TEAM_COLOR = new float[][] { { 0.8F, 0.2F, 0.2F, 0.6F }, { 0.2F, 0.4F, 0.8F, 0.6F } };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 90 */   public static final float[] PATH_COLOR = new float[] { 0.2F, 0.6F, 0.0F, 0.6F };
/*    */   public static final float DEFAULT_ZOOM_FACTOR_IN_FIGHT = 0.9F;
/*    */   public static final float DEFAULT_ZOOM_FACTOR_IN_WORLD = 1.0F;
/*    */   public static final String CHAT_FIGHT_INFORMATION_COLOR = "4BFF21";
/*    */   public static final String CHAT_GAME_INFORMATION_COLOR = "4BFF21";
/*    */   public static final String CHAT_PRIVATE_COLOR = "F26AD7";
/*    */   public static final String CHAT_GAME_ERROR_COLOR = "FF2727";
/*    */   public static final String CHAT_DEFAULT_COLOR = "FFFFFF";
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\DofusArenaClientConstants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */