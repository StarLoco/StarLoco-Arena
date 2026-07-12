/*     */ package com.ankamagames.dofusarena.common.game.fight;
/*     */ 
/*     */ 
/*     */ public final class FightDefinitionParameters
/*     */ {
/*     */   private byte m_fieldSet;
/*     */   
/*     */   private byte m_teamNumber;
/*     */   
/*     */   private byte m_playerNumber;
/*     */   
/*     */   private int m_budget;
/*     */   
/*     */   private boolean m_training;
/*     */   
/*     */   private int m_ladderId;
/*     */   
/*     */   private boolean m_ranked;
/*     */   
/*     */   private static final byte TEAM_NUMBER_SET = 1;
/*     */   
/*     */   private static final byte PLAYER_NUMBER_SET = 2;
/*     */   
/*     */   private static final byte BUDGET_SET = 4;
/*     */   private static final byte TRAINING_SET = 8;
/*     */   private static final byte LADDER_ID_SET = 16;
/*     */   private static final byte RANKED_SET = 32;
/*     */   private static final byte UNUSED = 64;
/*     */   
/*     */   public void set(FightDefinitionParameters params)
/*     */   {
/*  32 */     this.m_teamNumber = params.m_teamNumber;
/*  33 */     this.m_playerNumber = params.m_playerNumber;
/*  34 */     this.m_budget = params.m_budget;
/*  35 */     this.m_training = params.m_training;
/*  36 */     this.m_ladderId = params.m_ladderId;
/*  37 */     this.m_ranked = params.m_ranked;
/*     */     
/*  39 */     this.m_fieldSet = params.m_fieldSet;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  43 */     this.m_teamNumber = 0;
/*  44 */     this.m_playerNumber = 0;
/*  45 */     this.m_budget = 0;
/*  46 */     this.m_training = false;
/*  47 */     this.m_ladderId = 0;
/*  48 */     this.m_ranked = false;
/*  49 */     this.m_fieldSet = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getTeamNumber()
/*     */   {
/*  57 */     return this.m_teamNumber;
/*     */   }
/*     */   
/*     */   public byte getPlayerNumber() {
/*  61 */     return this.m_playerNumber;
/*     */   }
/*     */   
/*     */   public int getBudget() {
/*  65 */     return this.m_budget;
/*     */   }
/*     */   
/*     */   public boolean isTraining() {
/*  69 */     return this.m_training;
/*     */   }
/*     */   
/*     */   public int getLadderId() {
/*  73 */     return this.m_ladderId;
/*     */   }
/*     */   
/*     */   public boolean isRanked() {
/*  77 */     return this.m_ranked;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isTeamNumberSet()
/*     */   {
/*  85 */     return (this.m_fieldSet & 0x1) > 0;
/*     */   }
/*     */   
/*     */   public boolean isPlayerNumberSet() {
/*  89 */     return (this.m_fieldSet & 0x2) > 0;
/*     */   }
/*     */   
/*     */   public boolean isBudgetSet() {
/*  93 */     return (this.m_fieldSet & 0x4) > 0;
/*     */   }
/*     */   
/*     */   public boolean isTrainingSet() {
/*  97 */     return (this.m_fieldSet & 0x8) > 0;
/*     */   }
/*     */   
/*     */   public boolean isLadderIdSet() {
/* 101 */     return (this.m_fieldSet & 0x10) > 0;
/*     */   }
/*     */   
/*     */   public boolean isRankedSet() {
/* 105 */     return (this.m_fieldSet & 0x20) > 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTeamNumber(byte teamNumber)
/*     */   {
/* 113 */     this.m_teamNumber = teamNumber;
/* 114 */     this.m_fieldSet = ((byte)(this.m_fieldSet | 0x1));
/*     */   }
/*     */   
/*     */   public void setPlayerNumber(byte playerNumber) {
/* 118 */     this.m_playerNumber = playerNumber;
/* 119 */     this.m_fieldSet = ((byte)(this.m_fieldSet | 0x2));
/*     */   }
/*     */   
/*     */   public void setBudget(byte budget) {
/* 123 */     this.m_budget = budget;
/* 124 */     this.m_fieldSet = ((byte)(this.m_fieldSet | 0x4));
/*     */   }
/*     */   
/*     */   public void setTraining(boolean training) {
/* 128 */     this.m_training = training;
/* 129 */     this.m_fieldSet = ((byte)(this.m_fieldSet | 0x8));
/*     */   }
/*     */   
/*     */   public void setLadderId(int ladderId) {
/* 133 */     this.m_ladderId = ladderId;
/* 134 */     this.m_fieldSet = ((byte)(this.m_fieldSet | 0x10));
/*     */   }
/*     */   
/*     */   public void setRanked(boolean ranked) {
/* 138 */     this.m_ranked = ranked;
/* 139 */     this.m_fieldSet = ((byte)(this.m_fieldSet | 0x20));
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fight\FightDefinitionParameters.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */