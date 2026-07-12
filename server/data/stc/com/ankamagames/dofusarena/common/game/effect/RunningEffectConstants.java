/*     */ package com.ankamagames.dofusarena.common.game.effect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffectStatus;
/*     */ import com.ankamagames.dofusarena.common.game.effect.runningEffect.AutomaticEndTurn;
/*     */ import com.ankamagames.dofusarena.common.game.effect.runningEffect.CharacBuff;
/*     */ import com.ankamagames.dofusarena.common.game.effect.runningEffect.CharacDebuff;
/*     */ import com.ankamagames.dofusarena.common.game.effect.runningEffect.CharacGain;
/*     */ import com.ankamagames.dofusarena.common.game.effect.runningEffect.CharacLoss;
/*     */ import com.ankamagames.dofusarena.common.game.effect.runningEffect.HPLeech;
/*     */ import com.ankamagames.dofusarena.common.game.effect.runningEffect.HPLoss;
/*     */ import com.ankamagames.dofusarena.common.game.effect.runningEffect.Petrified;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*     */ 
/*     */ public class RunningEffectConstants extends com.ankamagames.baseImpl.common.clientAndServer.utils.Constants<com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect>
/*     */ {
/*  16 */   private static final RunningEffectConstants m_instance = new RunningEffectConstants();
/*     */   
/*     */   public static RunningEffectConstants getInstance() {
/*  19 */     return m_instance;
/*     */   }
/*     */   
/*  22 */   public static final RunningEffectDefinition HP_LOSS = new RunningEffectDefinition(1, new HPLoss(Elements.PHYSICAL), m_instance, RunningEffectDefinition.HP_LOSS_SCRIPT_ID, "perte de point de vie", RunningEffectStatus.NEGATIVE, new int[] { 1, 3 });
/*     */   
/*  24 */   public static final RunningEffectDefinition HP_FIRE_LOSS = new RunningEffectDefinition(2, new HPLoss(Elements.FIRE), m_instance, RunningEffectDefinition.HP_LOSS_SCRIPT_ID, "perte de point de vie - Feu", RunningEffectStatus.NEGATIVE, new int[] { 1, 3 });
/*     */   
/*  26 */   public static final RunningEffectDefinition HP_EARTH_LOSS = new RunningEffectDefinition(3, new HPLoss(Elements.EARTH), m_instance, RunningEffectDefinition.HP_LOSS_SCRIPT_ID, "perte de point de vie - Terre", RunningEffectStatus.NEGATIVE, new int[] { 1, 3 });
/*     */   
/*  28 */   public static final RunningEffectDefinition HP_WATER_LOSS = new RunningEffectDefinition(4, new HPLoss(Elements.WATER), m_instance, RunningEffectDefinition.HP_LOSS_SCRIPT_ID, "perte de point de vie- Eau", RunningEffectStatus.NEGATIVE, new int[] { 1, 3 });
/*     */   
/*  30 */   public static final RunningEffectDefinition HP_WIND_LOSS = new RunningEffectDefinition(5, new HPLoss(Elements.WIND), m_instance, RunningEffectDefinition.HP_LOSS_SCRIPT_ID, "perte de point de vie - Vent", RunningEffectStatus.NEGATIVE, new int[] { 1, 3 });
/*     */   
/*  32 */   public static final RunningEffectDefinition HP_LEECH = new RunningEffectDefinition(6, new HPLeech(Elements.PHYSICAL), m_instance, RunningEffectDefinition.HP_LEECH_SCRIPT_ID, "Vol de vie", RunningEffectStatus.NEGATIVE, new int[] { 1, 3 });
/*     */   
/*  34 */   public static final RunningEffectDefinition HP_LEECH_FIRE = new RunningEffectDefinition(7, new HPLeech(Elements.FIRE), m_instance, RunningEffectDefinition.HP_LEECH_SCRIPT_ID, "Vol de vie - Feu", RunningEffectStatus.NEGATIVE, new int[] { 1, 3 });
/*     */   
/*  36 */   public static final RunningEffectDefinition HP_LEECH_EARTH = new RunningEffectDefinition(8, new HPLeech(Elements.EARTH), m_instance, RunningEffectDefinition.HP_LEECH_SCRIPT_ID, "Vol de vie - Terre", RunningEffectStatus.NEGATIVE, new int[] { 1, 3 });
/*     */   
/*  38 */   public static final RunningEffectDefinition HP_LEECH_WATER = new RunningEffectDefinition(9, new HPLeech(Elements.WATER), m_instance, RunningEffectDefinition.HP_LEECH_SCRIPT_ID, "Vol de vie - Eau", RunningEffectStatus.NEGATIVE, new int[] { 1, 3 });
/*     */   
/*  40 */   public static final RunningEffectDefinition HP_LEECH_WIND = new RunningEffectDefinition(10, new HPLeech(Elements.WIND), m_instance, RunningEffectDefinition.HP_LEECH_SCRIPT_ID, "Vol de vie - Vent", RunningEffectStatus.NEGATIVE, new int[] { 1, 3 });
/*     */   
/*  42 */   public static final RunningEffectDefinition HP_BOOST = new RunningEffectDefinition(11, new CharacBuff(FighterCharacteristicType.HP), m_instance, RunningEffectDefinition.HP_GAIN_SCRIPT_ID, "Boost de HP", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/*  44 */   public static final RunningEffectDefinition HP_DEBOOST = new RunningEffectDefinition(12, new com.ankamagames.dofusarena.common.game.effect.runningEffect.HPDebuff(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Deboost de HP", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/*  46 */   public static final RunningEffectDefinition AP_BOOST = new RunningEffectDefinition(13, new CharacBuff(FighterCharacteristicType.AP), m_instance, RunningEffectDefinition.AP_GAIN_SCRIPT_ID, "Boost de AP", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/*  48 */   public static final RunningEffectDefinition AP_DEBOOST = new RunningEffectDefinition(14, new CharacDebuff(FighterCharacteristicType.AP), m_instance, RunningEffectDefinition.AP_LOSS_SCRIPT_ID, "Deboost de AP", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/*  50 */   public static final RunningEffectDefinition AP_GAIN = new RunningEffectDefinition(15, new CharacGain(FighterCharacteristicType.AP), m_instance, RunningEffectDefinition.AP_GAIN_SCRIPT_ID, "Gain de points d'action", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/*  52 */   public static final RunningEffectDefinition AP_LOSS = new RunningEffectDefinition(16, new com.ankamagames.dofusarena.common.game.effect.runningEffect.APLoss(), m_instance, RunningEffectDefinition.AP_LOSS_SCRIPT_ID, "Perte de point d'action", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/*  54 */   public static final RunningEffectDefinition MP_BOOST = new RunningEffectDefinition(17, new CharacBuff(FighterCharacteristicType.MP), m_instance, RunningEffectDefinition.MP_GAIN_SCRIPT_ID, "Boost de MP", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/*  56 */   public static final RunningEffectDefinition MP_DEBOOST = new RunningEffectDefinition(18, new CharacDebuff(FighterCharacteristicType.MP), m_instance, RunningEffectDefinition.MP_LOSS_SCRIPT_ID, "Deboost de MP", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/*  58 */   public static final RunningEffectDefinition MP_GAIN = new RunningEffectDefinition(19, new CharacGain(FighterCharacteristicType.MP), m_instance, RunningEffectDefinition.MP_GAIN_SCRIPT_ID, "Gain de points de mouvement", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/*  60 */   public static final RunningEffectDefinition MP_LOSS = new RunningEffectDefinition(20, new CharacLoss(FighterCharacteristicType.MP), m_instance, RunningEffectDefinition.MP_LOSS_SCRIPT_ID, "Perte de point de mouvement", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/*  62 */   public static final RunningEffectDefinition RES_FIRE_GAIN = new RunningEffectDefinition(21, new CharacGain(FighterCharacteristicType.RES_FIRE), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain de la résistance feu", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/*  64 */   public static final RunningEffectDefinition RES_FIRE_LOSS = new RunningEffectDefinition(22, new CharacLoss(FighterCharacteristicType.RES_FIRE), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte de la résistance feu", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/*  66 */   public static final RunningEffectDefinition RES_EARTH_GAIN = new RunningEffectDefinition(23, new CharacGain(FighterCharacteristicType.RES_EARTH), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain de la résistance terre", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/*  68 */   public static final RunningEffectDefinition RES_EARTH_LOSS = new RunningEffectDefinition(24, new CharacLoss(FighterCharacteristicType.RES_EARTH), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte de la résistance terre", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/*  70 */   public static final RunningEffectDefinition RES_WATER_GAIN = new RunningEffectDefinition(25, new CharacGain(FighterCharacteristicType.RES_WATER), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain de la résistance eau", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/*  72 */   public static final RunningEffectDefinition RES_WATER_LOSS = new RunningEffectDefinition(26, new CharacLoss(FighterCharacteristicType.RES_WATER), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte de la résistance eau", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/*  74 */   public static final RunningEffectDefinition RES_WIND_GAIN = new RunningEffectDefinition(27, new CharacGain(FighterCharacteristicType.RES_WIND), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain de la résistance air", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/*  76 */   public static final RunningEffectDefinition RES_WIND_LOSS = new RunningEffectDefinition(28, new CharacLoss(FighterCharacteristicType.RES_WIND), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte de la résistance air", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/*  78 */   public static final RunningEffectDefinition RES_FIRE_GAIN_PERCENT = new RunningEffectDefinition(29, new CharacGain(FighterCharacteristicType.RES_FIRE_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain de la résistance feu %", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/*  80 */   public static final RunningEffectDefinition RES_FIRE_LOSS_PERCENT = new RunningEffectDefinition(30, new CharacLoss(FighterCharacteristicType.RES_FIRE_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte de la résistance feu %", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/*  82 */   public static final RunningEffectDefinition RES_EARTH_GAIN_PERCENT = new RunningEffectDefinition(31, new CharacGain(FighterCharacteristicType.RES_EARTH_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain de la résistance terre %", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/*  84 */   public static final RunningEffectDefinition RES_EARTH_LOSS_PERCENT = new RunningEffectDefinition(32, new CharacLoss(FighterCharacteristicType.RES_EARTH_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte de la résistance terre %", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/*  86 */   public static final RunningEffectDefinition RES_WATER_GAIN_PERCENT = new RunningEffectDefinition(33, new CharacGain(FighterCharacteristicType.RES_WATER_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain de la résistance eau %", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/*  88 */   public static final RunningEffectDefinition RES_WATER_LOSS_PERCENT = new RunningEffectDefinition(34, new CharacLoss(FighterCharacteristicType.RES_WATER_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte de la résistance eau %", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/*  90 */   public static final RunningEffectDefinition RES_WIND_GAIN_PERCENT = new RunningEffectDefinition(35, new CharacGain(FighterCharacteristicType.RES_WIND_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain de la résistance air %", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/*  92 */   public static final RunningEffectDefinition RES_WIND_LOSS_PERCENT = new RunningEffectDefinition(36, new CharacLoss(FighterCharacteristicType.RES_WIND_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte de la résistance air %", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/*  94 */   public static final RunningEffectDefinition PUSH = new RunningEffectDefinition(37, new com.ankamagames.dofusarena.common.game.effect.runningEffect.Push(), m_instance, RunningEffectDefinition.SLIDE_MOBILE_SCRIPT_ID, "poussage", RunningEffectStatus.NEUTRAL, new int[] { 1, 3 });
/*     */   
/*  96 */   public static final RunningEffectDefinition PULL = new RunningEffectDefinition(38, new com.ankamagames.dofusarena.common.game.effect.runningEffect.Pull(), m_instance, RunningEffectDefinition.SLIDE_MOBILE_SCRIPT_ID, "tirage", RunningEffectStatus.NEUTRAL, new int[] { 1, 3 });
/*     */   
/*  98 */   public static final RunningEffectDefinition TELEPORT = new RunningEffectDefinition(39, new com.ankamagames.dofusarena.common.game.effect.runningEffect.Teleport(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "teleport", RunningEffectStatus.NEUTRAL, new int[] { 0 });
/*     */   
/* 100 */   public static final RunningEffectDefinition DMG_FIRE_GAIN = new RunningEffectDefinition(40, new CharacGain(FighterCharacteristicType.DMG_FIRE), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain au dommage de feu", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 102 */   public static final RunningEffectDefinition DMG_FIRE_LOSS = new RunningEffectDefinition(41, new CharacLoss(FighterCharacteristicType.DMG_FIRE), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte au dommage de feu", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 104 */   public static final RunningEffectDefinition DMG_EARTH_GAIN = new RunningEffectDefinition(42, new CharacGain(FighterCharacteristicType.DMG_EARTH), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain au dommage de terre", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 106 */   public static final RunningEffectDefinition DMG_EARTH_LOSS = new RunningEffectDefinition(43, new CharacLoss(FighterCharacteristicType.DMG_EARTH), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte au dommage de terre", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 108 */   public static final RunningEffectDefinition DMG_WATER_GAIN = new RunningEffectDefinition(44, new CharacGain(FighterCharacteristicType.DMG_WATER), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain au dommage d' eau", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 110 */   public static final RunningEffectDefinition DMG_WATER_LOSS = new RunningEffectDefinition(45, new CharacLoss(FighterCharacteristicType.DMG_WATER), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte au dommage d' eau", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 112 */   public static final RunningEffectDefinition DMG_WIND_GAIN = new RunningEffectDefinition(46, new CharacGain(FighterCharacteristicType.DMG_WIND), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain au dommage d'air", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 114 */   public static final RunningEffectDefinition DMG_WIND_LOSS = new RunningEffectDefinition(47, new CharacLoss(FighterCharacteristicType.DMG_WIND), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte au dommage d'air", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 116 */   public static final RunningEffectDefinition DMG_FIRE_GAIN_PERCENT = new RunningEffectDefinition(48, new CharacGain(FighterCharacteristicType.DMG_FIRE_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain au dommage de feu (%)", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 118 */   public static final RunningEffectDefinition DMG_FIRE_LOSS_PERCENT = new RunningEffectDefinition(49, new CharacLoss(FighterCharacteristicType.DMG_FIRE_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte au dommage de feu (%)", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 120 */   public static final RunningEffectDefinition DMG_EARTH_GAIN_PERCENT = new RunningEffectDefinition(50, new CharacGain(FighterCharacteristicType.DMG_EARTH_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain au dommage de terre (%)", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 122 */   public static final RunningEffectDefinition DMG_EARTH_LOSS_PERCENT = new RunningEffectDefinition(51, new CharacLoss(FighterCharacteristicType.DMG_EARTH_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte au dommage de terre (%)", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 124 */   public static final RunningEffectDefinition DMG_WATER_GAIN_PERCENT = new RunningEffectDefinition(52, new CharacGain(FighterCharacteristicType.DMG_WATER_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain au dommage d' eau (%)", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 126 */   public static final RunningEffectDefinition DMG_WATER_LOSS_PERCENT = new RunningEffectDefinition(53, new CharacLoss(FighterCharacteristicType.DMG_WATER_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte au dommage d' eau (%)", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 128 */   public static final RunningEffectDefinition DMG_WIND_GAIN_PERCENT = new RunningEffectDefinition(54, new CharacGain(FighterCharacteristicType.DMG_WIND_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain au dommage de air (%)", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 130 */   public static final RunningEffectDefinition DMG_WIND_LOSS_PERCENT = new RunningEffectDefinition(55, new CharacLoss(FighterCharacteristicType.DMG_WIND_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte  au dommage de air (%)", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 132 */   public static final RunningEffectDefinition END_OF_TURN = new RunningEffectDefinition(56, new AutomaticEndTurn(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Fin de tour", RunningEffectStatus.NEGATIVE, new int[] { 0 });
/*     */   
/* 134 */   public static final RunningEffectDefinition INVISIBLE = new RunningEffectDefinition(57, new com.ankamagames.dofusarena.common.game.effect.runningEffect.SetInvisible(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Devenir invisible", RunningEffectStatus.POSITIVE, new int[] { 0 });
/*     */   
/* 136 */   public static final RunningEffectDefinition CARRY = new RunningEffectDefinition(58, new com.ankamagames.dofusarena.common.game.effect.runningEffect.Carry(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Porter quelqu'un", RunningEffectStatus.NEUTRAL, new int[] { 0 });
/*     */   
/* 138 */   public static final RunningEffectDefinition THROW = new RunningEffectDefinition(59, new com.ankamagames.dofusarena.common.game.effect.runningEffect.Throw(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Jeter quelqu'un", RunningEffectStatus.NEUTRAL, new int[] { 0 });
/*     */   
/* 140 */   public static final RunningEffectDefinition CHANGE_LOOK = new RunningEffectDefinition(60, new com.ankamagames.dofusarena.common.game.effect.runningEffect.ChangeLook(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Changer de look", RunningEffectStatus.NEUTRAL, new int[] { 1 });
/*     */   
/* 142 */   public static final RunningEffectDefinition POISON = new RunningEffectDefinition(61, new com.ankamagames.dofusarena.common.game.effect.runningEffect.CharacPoison(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Poison : perte de point de vie sur déclencheur", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 144 */   public static final RunningEffectDefinition DECURSE = new RunningEffectDefinition(62, new com.ankamagames.dofusarena.common.game.effect.runningEffect.Decurse(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Désenvoutement", RunningEffectStatus.NEUTRAL, new int[] { 0, 1 });
/*     */   
/* 146 */   public static final RunningEffectDefinition DEATH = new RunningEffectDefinition(63, new com.ankamagames.dofusarena.common.game.effect.runningEffect.Death(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "mort instantanée", RunningEffectStatus.NEGATIVE, new int[] { 0 });
/*     */   
/* 148 */   public static final RunningEffectDefinition EXCHANGE_POSITION = new RunningEffectDefinition(64, new com.ankamagames.dofusarena.common.game.effect.runningEffect.ExchangePosition(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "échange de position", RunningEffectStatus.NEUTRAL, new int[] { 0 });
/*     */   
/* 150 */   public static final RunningEffectDefinition ROOTED = new RunningEffectDefinition(65, new com.ankamagames.dofusarena.common.game.effect.runningEffect.Root(), m_instance, RunningEffectDefinition.ROOTED_SCRIPT_ID, "immobilisé", RunningEffectStatus.NEGATIVE, new int[] { 0, 1 });
/*     */   
/* 152 */   public static final RunningEffectDefinition SET_EFFECT_AREA = new RunningEffectDefinition(66, new com.ankamagames.dofusarena.common.game.effect.runningEffect.SetEffectArea(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "pose un piège", RunningEffectStatus.NEUTRAL, new int[] { 1 });
/*     */   
/* 154 */   public static final RunningEffectDefinition SUMMON = new RunningEffectDefinition(67, new com.ankamagames.dofusarena.common.game.effect.runningEffect.Summon(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "invoque une créature", RunningEffectStatus.NEUTRAL, new int[] { 1 });
/*     */   
/* 156 */   public static final RunningEffectDefinition ATTRACT_SIGHT = new RunningEffectDefinition(68, new com.ankamagames.dofusarena.common.game.effect.runningEffect.TurnSightOnCell(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "tourne le regard vers la cellule ciblée", RunningEffectStatus.NEUTRAL, new int[] { 0 });
/*     */   
/* 158 */   public static final RunningEffectDefinition HP_GAIN = new RunningEffectDefinition(69, new com.ankamagames.dofusarena.common.game.effect.runningEffect.HPGain(), m_instance, RunningEffectDefinition.HP_GAIN_SCRIPT_ID, "soin", RunningEffectStatus.POSITIVE, new int[] { 1, 3 });
/*     */   
/* 160 */   public static final RunningEffectDefinition CC_GAIN = new RunningEffectDefinition(70, new CharacGain(FighterCharacteristicType.CRITICAL_RATE), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "augmente le taux de coup critique", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 162 */   public static final RunningEffectDefinition EC_GAIN = new RunningEffectDefinition(71, new CharacGain(FighterCharacteristicType.FUMBLE_RATE), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "augmente le taux d'echec critique", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 164 */   public static final RunningEffectDefinition RANGE_GAIN = new RunningEffectDefinition(72, new CharacGain(FighterCharacteristicType.RANGE), m_instance, RunningEffectDefinition.RG_GAIN_SCRIPT_ID, "augmente la portée", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 166 */   public static final RunningEffectDefinition RANGE_LOSS = new RunningEffectDefinition(73, new CharacLoss(FighterCharacteristicType.RANGE), m_instance, RunningEffectDefinition.RG_LOSS_SCRIPT_ID, "diminue la portée", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 168 */   public static final RunningEffectDefinition NB_SUMMONS_GAIN = new RunningEffectDefinition(74, new CharacGain(FighterCharacteristicType.NB_SUMMONS), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "augmente le nombre d'invocs", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 170 */   public static final RunningEffectDefinition SUMMON_DOUBLE = new RunningEffectDefinition(75, new com.ankamagames.dofusarena.common.game.effect.runningEffect.SummonDouble(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "invoque un double", RunningEffectStatus.NEUTRAL, new int[] { 0 });
/*     */   
/* 172 */   public static final RunningEffectDefinition INIT_BOOST = new RunningEffectDefinition(76, new CharacBuff(FighterCharacteristicType.INIT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Boost d'initiative", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 174 */   public static final RunningEffectDefinition INIT_DEBOOST = new RunningEffectDefinition(77, new CharacDebuff(FighterCharacteristicType.INIT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Deboost d'initiative", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 176 */   public static final RunningEffectDefinition HEAL_GAIN = new RunningEffectDefinition(78, new CharacGain(FighterCharacteristicType.HEAL), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Boost des soins", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 178 */   public static final RunningEffectDefinition HEAL_LOSS = new RunningEffectDefinition(79, new CharacLoss(FighterCharacteristicType.HEAL), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Deboost des soins", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 180 */   public static final RunningEffectDefinition RES_GAIN_IN_PERCENT = new RunningEffectDefinition(80, new CharacGain(FighterCharacteristicType.RES_IN_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain de résistance à tous les éléments(%)", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 182 */   public static final RunningEffectDefinition RES_LOSS_IN_PERCENT = new RunningEffectDefinition(81, new CharacLoss(FighterCharacteristicType.RES_IN_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte de résistance à tous les éléments(%)", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 184 */   public static final RunningEffectDefinition DMG_GAIN_IN_PERCENT = new RunningEffectDefinition(82, new CharacGain(FighterCharacteristicType.DMG_IN_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain aux dommages (%)", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 186 */   public static final RunningEffectDefinition DMG_LOSS_IN_PERCENT = new RunningEffectDefinition(83, new CharacLoss(FighterCharacteristicType.DMG_IN_PERCENT), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Perte aux dommages (%)", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 188 */   public static final RunningEffectDefinition REVEAL_INVISIBLE = new RunningEffectDefinition(84, new com.ankamagames.dofusarena.common.game.effect.runningEffect.SetVisible(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Révéler l'invisible", RunningEffectStatus.POSITIVE, new int[] { 0 });
/*     */   
/* 190 */   public static final RunningEffectDefinition AP_LEECH = new RunningEffectDefinition(85, new com.ankamagames.dofusarena.common.game.effect.runningEffect.CharacLeech(FighterCharacteristicType.AP), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Vol de point d'action (gain pour le caster, Debuff pour l'autre)", RunningEffectStatus.NEGATIVE, new int[] { 1 });
/*     */   
/* 192 */   public static final RunningEffectDefinition RES_AP_DEBUFF_GAIN = new RunningEffectDefinition(86, new CharacGain(FighterCharacteristicType.RES_AP_LOSS), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain de résistance aux pertes de PA", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 194 */   public static final RunningEffectDefinition RES_MP_DEBUFF_GAIN = new RunningEffectDefinition(87, new CharacGain(FighterCharacteristicType.RES_MP_LOSS), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Gain de résistance aux pertes de PM", RunningEffectStatus.POSITIVE, new int[] { 1 });
/*     */   
/* 196 */   public static final RunningEffectDefinition SPELL_REBOUND = new RunningEffectDefinition(88, new com.ankamagames.dofusarena.common.game.effect.runningEffect.SpellRebound(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Renvoi de sort", RunningEffectStatus.POSITIVE, new int[] { 0, 1 });
/*     */   
/* 198 */   public static final RunningEffectDefinition DAMAGES_REBOUND_IN_PERCENT = new RunningEffectDefinition(89, new CharacGain(FighterCharacteristicType.DMG_REBOUND), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Renvoi de dommages (%)", RunningEffectStatus.POSITIVE, new int[] { 0, 1 });
/*     */   
/* 200 */   public static final RunningEffectDefinition STRIKE_BACK = new RunningEffectDefinition(90, new com.ankamagames.dofusarena.common.game.effect.runningEffect.StrikeBack(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Dommages en retour", RunningEffectStatus.POSITIVE, new int[] { 0, 1 });
/*     */   
/* 202 */   public static final RunningEffectDefinition AP_USE = new RunningEffectDefinition(91, new com.ankamagames.dofusarena.common.game.effect.runningEffect.APUse(), m_instance, RunningEffectDefinition.AP_LOSS_SCRIPT_ID, "Utilisation de PA", RunningEffectStatus.NEGATIVE, new int[] { 0 });
/*     */   
/* 204 */   public static final RunningEffectDefinition MP_USE = new RunningEffectDefinition(92, new com.ankamagames.dofusarena.common.game.effect.runningEffect.MPUse(), m_instance, RunningEffectDefinition.MP_LOSS_SCRIPT_ID, "Utilisation de PM", RunningEffectStatus.NEGATIVE, new int[] { 0 });
/*     */   
/* 206 */   public static final RunningEffectDefinition CARD_EQUIPPED = new RunningEffectDefinition(93, new com.ankamagames.dofusarena.common.game.effect.runningEffect.CardEquipped(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Equippement d'une carte", RunningEffectStatus.NEUTRAL, new int[] { 0 });
/*     */   
/* 208 */   public static final RunningEffectDefinition STABILIZED = new RunningEffectDefinition(94, new com.ankamagames.dofusarena.common.game.effect.runningEffect.Stabilize(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Stabilisation (impossible à déplacer)", RunningEffectStatus.NEUTRAL, new int[] { 0 });
/*     */   
/*     */ 
/*     */ 
/* 212 */   public static final RunningEffectDefinition PETRIFIED = new RunningEffectDefinition(96, new Petrified(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Pétrifié, ne peut jouer", RunningEffectStatus.NEGATIVE, new int[] { 3 });
/*     */   
/* 214 */   public static final RunningEffectDefinition SUMMON_MIRROR = new RunningEffectDefinition(97, new com.ankamagames.dofusarena.common.game.effect.runningEffect.SummonMirror(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "invoque un mirroir", RunningEffectStatus.NEUTRAL, new int[] { 1 });
/*     */   
/* 216 */   public static final RunningEffectDefinition ADAPT_LOOK = new RunningEffectDefinition(98, new com.ankamagames.dofusarena.common.game.effect.runningEffect.AdaptLook(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Adapter le look", RunningEffectStatus.NEUTRAL, new int[] { 1 });
/*     */   
/* 218 */   public static final RunningEffectDefinition SKIP_TURN = new RunningEffectDefinition(111, new AutomaticEndTurn(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "skip son tour", RunningEffectStatus.NEUTRAL, new int[] { 0 });
/*     */   
/* 220 */   public static final RunningEffectDefinition STATE_APPLY = new RunningEffectDefinition(112, new com.ankamagames.dofusarena.common.game.effect.runningEffect.ApplyState(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "applique un Etat", RunningEffectStatus.NEUTRAL, new int[] { 2 });
/*     */   
/* 222 */   public static final RunningEffectDefinition RAPPROCHEMENT = new RunningEffectDefinition(113, new com.ankamagames.dofusarena.common.game.effect.runningEffect.Rapprochement(), m_instance, RunningEffectDefinition.NO_SCRIPT_ID, "Se raproche de la cible", RunningEffectStatus.NEUTRAL, new int[] { 0 });
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\RunningEffectConstants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */