/*     */ package com.ankamagames.dofusarena.common.constants;
/*     */ 
/*     */ import java.util.regex.Pattern;
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
/*     */ public class DofusArenaConstants
/*     */ {
/*     */   public static final boolean PRODUCTION = true;
/*     */   public static final int ANKAMAGAMES_GAME_ID = 1004;
/*     */   public static final boolean APPLY_CLOSED_BETA_RESTRICTIONS = false;
/*     */   public static final boolean LOAD_EMOTE_CARDS = false;
/*     */   public static final boolean LOAD_PET_CARDS = false;
/*     */   public static final boolean LOAD_SMILIES_CARDS = false;
/*     */   public static final boolean LOAD_CURSED_DEFNIED_CARDS = false;
/*     */   public static final int COACH_MAX_NAME_LENGTH = 32;
/*     */   public static final byte MAX_FIGHTER_BY_COACH = 32;
/*     */   public static final short COACH_EQUIPMENT_INVENTORY_LENGTH = 14;
/*     */   public static final short COACH_INVENTORY_LENGTH = 100;
/*     */   public static final short COACH_INVENTORY_MAX_LOCKS_COUNT = 10;
/*     */   public static final float COACH_COLOR_THRESHOLD = 1.25F;
/*     */   public static final int COACH_MIN_LEVEL = 1;
/*     */   public static final int COACH_MAX_LEVEL = 50;
/*     */   public static final int COACH_MIN_STRENGTH = 1000;
/*     */   public static final int COACH_MAX_STRENGTH = 3000;
/*     */   public static final int FIGTHER_MAX_NAME_LENGTH = 32;
/*     */   public static final short FIGHTER_EQUIPMENT_INVENTORY_LENGTH = 6;
/*     */   public static final short FIGHTER_SPELL_INVENTORY_LENGTH = 6;
/*     */   public static final byte FIGHTER_MAX_SKIN_INDEX = 4;
/*     */   public static final int TEAM_MAX_NAME_LENGTH = 32;
/*     */   public static final byte MAX_FIGHTER_BY_TEAM = 10;
/*     */   
/*     */   public static int strengthToLevel(int strength) {
/*  58 */     if (strength < 1)
/*  59 */       return 0; 
/*  60 */     return 1 + Math.round((strength - 1000) / 2000.0F * 49.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int strengthToRank(int strength) {
/*  65 */     return levelToRank(strengthToLevel(strength));
/*     */   }
/*     */ 
/*     */   
/*     */   public static short levelToRank(int level) {
/*  70 */     if (level <= 15)
/*  71 */       return 1; 
/*  72 */     if (level <= 30) {
/*  73 */       return 2;
/*     */     }
/*  75 */     return 3;
/*     */   }
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
/* 102 */   public static final String[] NAME_EXCLUSION_LIST = new String[] { "" };
/* 103 */   public static final Pattern FIGHTER_NAME_PATTERN = Pattern.compile("([\\p{L}]|[\\p{L}][ '-]){2,}\\p{L}", 64);
/*     */   public static final int FIGHTER_NAME_MAX_SIZE = 16;
/* 105 */   public static final Pattern COACH_NAME_PATTERN = Pattern.compile("([\\p{L}]|[\\p{L}][ '-]){2,}\\p{L}", 64);
/*     */   public static final int COACH_NAME_MAX_SIZE = 20;
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\constants\DofusArenaConstants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */