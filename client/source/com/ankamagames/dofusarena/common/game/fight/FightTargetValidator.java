/*     */ package com.ankamagames.dofusarena.common.game.fight;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.Breed;
/*     */ import com.ankamagames.framework.ai.targetfinder.Target;
/*     */ import com.ankamagames.framework.ai.targetfinder.TargetValidator;
/*     */ import com.ankamagames.framework.ai.targetfinder.TargetValidity;
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
/*     */ public class FightTargetValidator
/*     */   implements TargetValidator
/*     */ {
/*     */   public static final int CONDITION_IN_AOE = 1;
/*     */   public static final int CONDITION_IS_CASTER = 2;
/*     */   public static final int CONDITION_IS_ALLY = 4;
/*     */   public static final int CONDITION_IS_ENEMY = 8;
/*     */   public static final int CONDITION_IS_HUMAN = 16;
/*     */   public static final int CONDITION_IS_SUMMONED = 32;
/*     */   public static final int CONDITION_IS_EFFECT_AREA = 64;
/*     */   public static final int CONDITION_IS_ALLY_EXCEPT_CASTER = 128;
/*     */   public static final int CONDITION_IS_NOT_CASTER = 256;
/*     */   private static final int BREEDS_CONDITIONS_SHIFT = 16;
/*     */   public static final int CONDITION_BREED_FECA = 65536;
/*     */   public static final int CONDITION_BREED_OSAMODAS = 131072;
/*     */   public static final int CONDITION_BREED_ENUTROF = 262144;
/*     */   public static final int CONDITION_BREED_SRAM = 524288;
/*     */   public static final int CONDITION_BREED_XELOR = 1048576;
/*     */   public static final int CONDITION_BREED_ECAFLIP = 2097152;
/*     */   public static final int CONDITION_BREED_ENIRIPSA = 4194304;
/*     */   public static final int CONDITION_BREED_IOP = 8388608;
/*     */   public static final int CONDITION_BREED_CRA = 16777216;
/*     */   public static final int CONDITION_BREED_SADIDA = 33554432;
/*     */   public static final int CONDITION_BREED_SACRIER = 67108864;
/*     */   public static final int CONDITION_BREED_PANDAWA = 134217728;
/*     */   private final int[] m_conditions;
/*     */   
/*     */   public FightTargetValidator(int... conditions) {
/*  53 */     this.m_conditions = conditions;
/*     */   }
/*     */ 
/*     */   
/*     */   public TargetValidity getTargetValidity(Target target, Target applicant) {
/*  58 */     TargetValidity bestAnswer = TargetValidity.INVALID; byte b;
/*     */     int i, arrayOfInt[];
/*  60 */     for (i = (arrayOfInt = this.m_conditions).length, b = 0; b < i; ) { int condition = arrayOfInt[b];
/*     */ 
/*     */       
/*  63 */       if ((0x2 & condition) != 0 && 
/*  64 */         target != applicant) {
/*     */         continue;
/*     */       }
/*  67 */       if ((0x100 & condition) != 0 && 
/*  68 */         target == applicant) {
/*     */         continue;
/*     */       }
/*  71 */       if ((0x80 & condition) != 0) {
/*  72 */         if (target == applicant)
/*     */           continue; 
/*  74 */         if (applicant == null)
/*     */           continue; 
/*  76 */         if (!(target instanceof BasicFighter) || !(applicant instanceof BasicFighter))
/*     */           continue; 
/*  78 */         if (((BasicFighter)target).getTeamMate() != ((BasicFighter)applicant).getTeamMate()) {
/*     */           continue;
/*     */         }
/*     */       } 
/*  82 */       if ((0x4 & condition) != 0) {
/*  83 */         if (applicant == null)
/*     */           continue; 
/*  85 */         if (!(target instanceof BasicFighter) || !(applicant instanceof BasicFighter))
/*     */           continue; 
/*  87 */         if (((BasicFighter)target).getTeamMate() != ((BasicFighter)applicant).getTeamMate()) {
/*     */           continue;
/*     */         }
/*     */       } 
/*  91 */       if ((0x8 & condition) != 0) {
/*  92 */         if (applicant == null)
/*     */           continue; 
/*  94 */         if (target instanceof BasicFighter && applicant instanceof BasicFighter && (
/*  95 */           (BasicFighter)target).getTeamMate() == ((BasicFighter)applicant).getTeamMate()) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/* 100 */       if ((0x10 & condition) != 0) {
/* 101 */         if (!(target instanceof AbstractFighter))
/*     */           continue; 
/* 103 */         if (target.getId() <= 0L)
/*     */           continue; 
/*     */       } 
/* 106 */       if ((0x20 & condition) != 0) {
/* 107 */         if (!(target instanceof AbstractFighter))
/*     */           continue; 
/* 109 */         if (target.getId() >= 0L) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/* 114 */       if ((0x40 & condition) == 0 || 
/* 115 */         target instanceof com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.BasicEffectArea)
/*     */       {
/*     */         
/* 118 */         if ((0x10000 & condition) == 0 || 
/* 119 */           checkBreed(target, 0x10000 & condition))
/*     */         {
/* 121 */           if ((0x20000 & condition) == 0 || 
/* 122 */             checkBreed(target, 0x20000 & condition))
/*     */           {
/* 124 */             if ((0x40000 & condition) == 0 || 
/* 125 */               checkBreed(target, 0x40000 & condition))
/*     */             {
/* 127 */               if ((0x80000 & condition) == 0 || 
/* 128 */                 checkBreed(target, 0x80000 & condition))
/*     */               {
/* 130 */                 if ((0x100000 & condition) == 0 || 
/* 131 */                   checkBreed(target, 0x100000 & condition))
/*     */                 {
/* 133 */                   if ((0x200000 & condition) == 0 || 
/* 134 */                     checkBreed(target, 0x200000 & condition))
/*     */                   {
/* 136 */                     if ((0x400000 & condition) == 0 || 
/* 137 */                       checkBreed(target, 0x400000 & condition))
/*     */                     {
/* 139 */                       if ((0x800000 & condition) == 0 || 
/* 140 */                         checkBreed(target, 0x800000 & condition))
/*     */                       {
/* 142 */                         if ((0x1000000 & condition) == 0 || 
/* 143 */                           checkBreed(target, 0x1000000 & condition))
/*     */                         {
/* 145 */                           if ((0x2000000 & condition) == 0 || 
/* 146 */                             checkBreed(target, 0x2000000 & condition))
/*     */                           {
/* 148 */                             if ((0x4000000 & condition) == 0 || 
/* 149 */                               checkBreed(target, 0x4000000 & condition))
/*     */                             {
/* 151 */                               if ((0x8000000 & condition) == 0 || 
/* 152 */                                 checkBreed(target, 0x8000000 & condition))
/*     */                               {
/*     */ 
/*     */                                 
/* 156 */                                 if ((0x1 & condition) != 0) {
/*     */                                   
/* 158 */                                   bestAnswer = TargetValidity.VALID_IF_IN_AOE;
/*     */                                 } else {
/*     */                                   
/* 161 */                                   return TargetValidity.VALID;
/*     */                                 }  }  }  }  }  }  }  }  }  }  }  }  }  }  continue;
/*     */       b++; }
/*     */     
/* 165 */     return bestAnswer;
/*     */   }
/*     */   
/*     */   private boolean checkBreed(Target target, int condition) {
/* 169 */     if (!(target instanceof AbstractFighter))
/* 170 */       return false; 
/* 171 */     Breed breed = ((AbstractFighter)target).getBreed();
/* 172 */     if (breed == null) {
/* 173 */       return false;
/*     */     }
/* 175 */     return (condition >> 16 == 1 << breed.getId() - 1);
/*     */   }
/*     */   
/*     */   public int[] getConditions() {
/* 179 */     return this.m_conditions;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fight\FightTargetValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */