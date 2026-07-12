/*     */ package com.ankamagames.dofusarena.common.game.fighter;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.dofusarena.common.game.effect.Elements;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightTargetValidator;
/*     */ import com.ankamagames.framework.ai.targetfinder.TargetValidator;
/*     */ import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffectEnum;
/*     */ import com.ankamagames.framework.external.ExportableEnum;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Breed
/*     */   implements ExportableEnum
/*     */ {
/*  22 */   NONE((byte)-1),
/*  23 */   FECA((byte)1, 70, 6, 3, 50, 5, 1, 400, Elements.WATER, 5, 5, 7),
/*  24 */   OSAMODAS((byte)2, 65, 6, 3, 60, 5, 1, 400, Elements.EARTH, 5, 5, 7),
/*  25 */   ENUTROF((byte)3, 65, 6, 3, 60, 5, 1, 400, Elements.WATER, 5, 5, 7),
/*  26 */   SRAM((byte)4, 70, 6, 3, 50, 5, 1, 400, Elements.WIND, 5, 5, 7),
/*  27 */   XELOR((byte)5, 60, 6, 3, 70, 5, 1, 400, Elements.FIRE, 5, 5, 7),
/*  28 */   ECAFLIP((byte)6, 70, 6, 3, 50, 5, 1, 400, Elements.WATER, 5, 5, 7),
/*  29 */   ENIRIPSA((byte)7, 60, 6, 3, 70, 5, 1, 400, Elements.FIRE, 5, 5, 7),
/*  30 */   IOP((byte)8, 75, 6, 3, 40, 5, 1, 400, Elements.EARTH, 5, 5, 7),
/*  31 */   CRA((byte)9, 65, 6, 3, 60, 5, 1, 400, Elements.WATER, 5, 5, 7),
/*  32 */   SADIDA((byte)10, 65, 6, 3, 60, 5, 1, 400, Elements.EARTH, 5, 5, 7),
/*  33 */   SACRIER((byte)11, 80, 6, 3, 30, 5, 1, 400, Elements.WIND, 5, 5, 7),
/*  34 */   PANDAWA((byte)12, 75, 6, 3, 40, 5, 1, 400, Elements.FIRE, 5, 5, 7),
/*  35 */   MONSTER((byte)0),
/*  36 */   GOD((byte)98),
/*  37 */   COACH((byte)99); protected static final Logger m_logger;
/*     */   static {
/*  39 */     m_logger = Logger.getLogger(Breed.class);
/*     */   }
/*     */   
/*     */   private final byte m_id;
/*     */   private final int m_value;
/*     */   private final int m_baseHp;
/*     */   private final int m_baseAp;
/*     */   private final int m_baseMp;
/*     */   private final int m_baseInit;
/*     */   private final int m_baseCH;
/*     */   private final int m_baseCM;
/*     */   private final Elements m_closeCombatElement;
/*     */   private final int m_closeCombatAp;
/*     */   private final int m_closeCombatDamages;
/*     */   private final int m_closeCombatCriticalDamages;
/*     */   private final Effect m_closeCombatEffect;
/*     */   private final Effect m_closeCombatCriticalEffect;
/*     */   
/*     */   Breed(byte id, int baseHp, int baseAp, int baseMp, int baseInit, int baseCC, int baseEC, int value, Elements closeCombatElement, int closeCombatAp, int closeCombatDamages, int closeCombatCriticalDamages) {
/*  58 */     this.m_id = id;
/*  59 */     this.m_value = value;
/*  60 */     this.m_baseHp = baseHp;
/*  61 */     this.m_baseAp = baseAp;
/*  62 */     this.m_baseMp = baseMp;
/*  63 */     this.m_baseInit = baseInit;
/*  64 */     this.m_baseCH = baseCC;
/*  65 */     this.m_baseCM = baseEC;
/*  66 */     this.m_closeCombatElement = closeCombatElement;
/*  67 */     this.m_closeCombatAp = closeCombatAp;
/*  68 */     this.m_closeCombatDamages = closeCombatDamages;
/*  69 */     this.m_closeCombatCriticalDamages = closeCombatCriticalDamages;
/*  70 */     this.m_closeCombatEffect = new Effect(-1, 
/*  71 */         RunningEffectConstants.HP_LOSS.getId(), 
/*  72 */         "", 
/*  73 */         new float[] { this.m_closeCombatDamages
/*  74 */         }, AreaOfEffectEnum.newInstance(AreaOfEffectEnum.POINT.getIndex(), null), 
/*  75 */         new int[0], 
/*  76 */         new int[0], 
/*  77 */         new int[0], 
/*  78 */         new int[0], 
/*  79 */         0L, 
/*  80 */         (TargetValidator)new FightTargetValidator(new int[] { 1
/*  81 */           }, ), true);
/*  82 */     this.m_closeCombatCriticalEffect = new Effect(-2, 
/*  83 */         RunningEffectConstants.HP_LOSS.getId(), 
/*  84 */         "", 
/*  85 */         new float[] { this.m_closeCombatCriticalDamages
/*  86 */         }, AreaOfEffectEnum.newInstance(AreaOfEffectEnum.POINT.getIndex(), null), 
/*  87 */         new int[0], 
/*  88 */         new int[0], 
/*  89 */         new int[0], 
/*  90 */         new int[0], 
/*  91 */         0L, 
/*  92 */         (TargetValidator)new FightTargetValidator(new int[] { 1
/*  93 */           }, ), true);
/*     */   }
/*     */   
/*     */   Breed(byte id) {
/*  97 */     this.m_id = id;
/*  98 */     this.m_value = 0;
/*  99 */     this.m_baseHp = 0;
/* 100 */     this.m_baseAp = 0;
/* 101 */     this.m_baseMp = 0;
/* 102 */     this.m_baseInit = 0;
/* 103 */     this.m_baseCH = 0;
/* 104 */     this.m_baseCM = 0;
/* 105 */     this.m_closeCombatElement = Elements.EARTH;
/* 106 */     this.m_closeCombatAp = 0;
/* 107 */     this.m_closeCombatDamages = 0;
/* 108 */     this.m_closeCombatCriticalDamages = 0;
/* 109 */     this.m_closeCombatEffect = null;
/* 110 */     this.m_closeCombatCriticalEffect = null;
/*     */   }
/*     */   
/*     */   public byte getId() {
/* 114 */     return this.m_id;
/*     */   }
/*     */   
/*     */   public String getEnumId() {
/* 118 */     return Byte.valueOf(getId()).toString();
/*     */   }
/*     */   
/*     */   public String getEnumLabel() {
/* 122 */     return toString();
/*     */   }
/*     */   
/*     */   public int getValue() {
/* 126 */     return this.m_value;
/*     */   }
/*     */   
/*     */   public int getBaseHp() {
/* 130 */     return this.m_baseHp;
/*     */   }
/*     */   
/*     */   public int getBaseAp() {
/* 134 */     return this.m_baseAp;
/*     */   }
/*     */   
/*     */   public int getBaseMp() {
/* 138 */     return this.m_baseMp;
/*     */   }
/*     */   
/*     */   public int getBaseInit() {
/* 142 */     return this.m_baseInit;
/*     */   }
/*     */   
/*     */   public int getBaseCH() {
/* 146 */     return this.m_baseCH;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseCM() {
/* 151 */     return this.m_baseCM;
/*     */   }
/*     */   
/*     */   public Elements getCloseCombatElement() {
/* 155 */     return this.m_closeCombatElement;
/*     */   }
/*     */   
/*     */   public int getCloseCombatAp() {
/* 159 */     return this.m_closeCombatAp;
/*     */   }
/*     */   
/*     */   public int getCloseCombatDamages() {
/* 163 */     return this.m_closeCombatDamages;
/*     */   }
/*     */   
/*     */   public int getCloseCombatCriticalDamages() {
/* 167 */     return this.m_closeCombatCriticalDamages;
/*     */   }
/*     */   
/*     */   public Effect getCloseCombatEffect() {
/* 171 */     return this.m_closeCombatEffect;
/*     */   }
/*     */   
/*     */   public Effect getCloseCombatCriticalEffect() {
/* 175 */     return this.m_closeCombatCriticalEffect; } public static Breed getBreedFromId(int id) {
/*     */     byte b;
/*     */     int i;
/*     */     Breed[] arrayOfBreed;
/* 179 */     for (i = (arrayOfBreed = values()).length, b = 0; b < i; ) { Breed breed = arrayOfBreed[b];
/* 180 */       if (breed.getId() == id)
/* 181 */         return breed;  b++; }
/*     */     
/* 183 */     return NONE;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fighter\Breed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */