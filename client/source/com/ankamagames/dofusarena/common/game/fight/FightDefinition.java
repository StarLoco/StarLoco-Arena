/*     */ package com.ankamagames.dofusarena.common.game.fight;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FightDefinition
/*     */ {
/*     */   private byte m_id;
/*     */   private String m_description;
/*     */   private byte m_teamNumber;
/*     */   private byte m_coachNumberByTeam;
/*     */   private byte m_minFighterByTeam;
/*     */   private byte m_maxFighterByTeam;
/*     */   private int m_budget;
/*     */   private boolean m_training;
/*     */   private int m_ladderId;
/*     */   private boolean m_ranked;
/*     */   
/*     */   public FightDefinition(byte id, String description, byte teamNumber, byte coachNumberByTeam, byte minFighterByTeam, byte maxFighterByTeam, int budget, boolean training, int ladderId, boolean ranked) {
/*  23 */     this.m_id = id;
/*  24 */     this.m_description = description;
/*  25 */     this.m_teamNumber = teamNumber;
/*  26 */     this.m_coachNumberByTeam = coachNumberByTeam;
/*  27 */     this.m_minFighterByTeam = minFighterByTeam;
/*  28 */     this.m_maxFighterByTeam = maxFighterByTeam;
/*  29 */     this.m_budget = budget;
/*  30 */     this.m_training = training;
/*  31 */     this.m_ladderId = ladderId;
/*  32 */     this.m_ranked = ranked;
/*     */   }
/*     */   
/*     */   public byte getId() {
/*  36 */     return this.m_id;
/*     */   }
/*     */   
/*     */   public byte getTeamNumber() {
/*  40 */     return this.m_teamNumber;
/*     */   }
/*     */   
/*     */   public byte getCoachNumberByTeam() {
/*  44 */     return this.m_coachNumberByTeam;
/*     */   }
/*     */   
/*     */   public byte getMinFighterByTeam() {
/*  48 */     return this.m_minFighterByTeam;
/*     */   }
/*     */   
/*     */   public byte getMaxFighterByTeam() {
/*  52 */     return this.m_maxFighterByTeam;
/*     */   }
/*     */   
/*     */   public int getBudget() {
/*  56 */     return this.m_budget;
/*     */   }
/*     */   
/*     */   public boolean getTraining() {
/*  60 */     return this.m_training;
/*     */   }
/*     */   
/*     */   public int getLadderId() {
/*  64 */     return this.m_ladderId;
/*     */   }
/*     */   
/*     */   public boolean isRanked() {
/*  68 */     return this.m_ranked;
/*     */   }
/*     */   
/*     */   public boolean parametersValidation(byte teamNumber, byte numFighter, byte budget, boolean training, int ladderId, boolean ranked) {
/*  72 */     if (teamNumber != this.m_teamNumber)
/*  73 */       return false; 
/*  74 */     if (numFighter > this.m_maxFighterByTeam || numFighter < this.m_minFighterByTeam)
/*  75 */       return false; 
/*  76 */     if (budget > this.m_budget)
/*  77 */       return false; 
/*  78 */     if (training != this.m_training)
/*  79 */       return false; 
/*  80 */     if (ladderId != this.m_ladderId)
/*  81 */       return false; 
/*  82 */     if (ranked != this.m_ranked)
/*  83 */       return false; 
/*  84 */     return true;
/*     */   }
/*     */   
/*     */   public boolean parametersValidation(FightDefinitionParameters parameters) {
/*  88 */     if (parameters.isTeamNumberSet() && 
/*  89 */       parameters.getTeamNumber() != this.m_teamNumber)
/*  90 */       return false; 
/*  91 */     if (parameters.isPlayerNumberSet() && (
/*  92 */       parameters.getPlayerNumber() > this.m_maxFighterByTeam || parameters.getPlayerNumber() < this.m_minFighterByTeam))
/*  93 */       return false; 
/*  94 */     if (parameters.isBudgetSet() && 
/*  95 */       parameters.getBudget() > this.m_budget)
/*  96 */       return false; 
/*  97 */     if (parameters.isTrainingSet() && 
/*  98 */       parameters.isTraining() != this.m_training)
/*  99 */       return false; 
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 107 */     return this.m_description;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fight\FightDefinition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */