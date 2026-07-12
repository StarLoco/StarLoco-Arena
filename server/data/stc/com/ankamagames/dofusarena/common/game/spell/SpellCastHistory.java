/*     */ package com.ankamagames.dofusarena.common.game.spell;
/*     */ 
/*     */ import com.ankamagames.dofusarena.common.game.fight.SpellCastValidity;
/*     */ import com.ankamagames.framework.ai.targetfinder.Target;
/*     */ import gnu.trove.HashFunctions;
/*     */ import java.util.HashMap;
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
/*     */ public class SpellCastHistory
/*     */ {
/*  23 */   private final HashMap<AbstractSpell, Integer> m_spellsCasted = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*  27 */   private final HashMap<AbstractSpell, Integer> m_spellsCastedThisTurn = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*  31 */   private final HashMap<Long, Integer> m_spellsCastedThisTurnOnTarget = new HashMap();
/*     */   
/*     */   public void reset()
/*     */   {
/*  35 */     this.m_spellsCasted.clear();
/*  36 */     this.m_spellsCastedThisTurn.clear();
/*  37 */     this.m_spellsCastedThisTurnOnTarget.clear();
/*     */   }
/*     */   
/*     */   public void onNewTurn() {
/*  41 */     this.m_spellsCastedThisTurn.clear();
/*  42 */     this.m_spellsCastedThisTurnOnTarget.clear();
/*     */   }
/*     */   
/*     */   public void storeSpellCast(AbstractSpell spell, int currentTableTurn, Target target) {
/*  46 */     if (spell.getMinCastInterval() > 0) {
/*  47 */       this.m_spellsCasted.put(spell, Integer.valueOf(currentTableTurn));
/*     */     }
/*  49 */     if (spell.getCastMaxPerTurn() > 0) {
/*  50 */       Integer castsCount = (Integer)this.m_spellsCastedThisTurn.get(spell);
/*  51 */       if (castsCount == null) {
/*  52 */         this.m_spellsCastedThisTurn.put(spell, Integer.valueOf(1));
/*     */       } else {
/*  54 */         this.m_spellsCastedThisTurn.put(spell, Integer.valueOf(castsCount.intValue() + 1));
/*     */       }
/*     */     }
/*  57 */     if ((target != null) && (spell.getCastMaxPerTarget() > 0)) {
/*  58 */       long hash = getSpellOnTargetHashCode(spell, target);
/*  59 */       Integer castsCount = (Integer)this.m_spellsCastedThisTurnOnTarget.get(Long.valueOf(hash));
/*  60 */       if (castsCount == null) {
/*  61 */         this.m_spellsCastedThisTurnOnTarget.put(Long.valueOf(hash), Integer.valueOf(1));
/*     */       } else {
/*  63 */         this.m_spellsCastedThisTurnOnTarget.put(Long.valueOf(hash), Integer.valueOf(castsCount.intValue() + 1));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public SpellCastValidity canCastSpell(AbstractSpell spell, int currentTableTurn) {
/*  69 */     return canCastSpell(spell, currentTableTurn, null);
/*     */   }
/*     */   
/*     */   public SpellCastValidity canCastSpell(AbstractSpell spell, int currentTableTurn, Target target)
/*     */   {
/*  74 */     if (spell.getMinCastInterval() > 0) {
/*  75 */       Integer lastCastTime = (Integer)this.m_spellsCasted.get(spell);
/*  76 */       if (lastCastTime != null) {
/*  77 */         if ((spell.getMinCastInterval() == 63) || (currentTableTurn - lastCastTime.intValue() < spell.getMinCastInterval())) {
/*  78 */           return SpellCastValidity.LAST_CAST_TOO_RECENT;
/*     */         }
/*     */         
/*  81 */         this.m_spellsCasted.remove(spell);
/*     */       }
/*     */     }
/*     */     
/*  85 */     if (spell.getCastMaxPerTurn() > 0) {
/*  86 */       Integer castsCount = (Integer)this.m_spellsCastedThisTurn.get(spell);
/*  87 */       if ((castsCount != null) && 
/*  88 */         (castsCount.intValue() >= spell.getCastMaxPerTurn())) {
/*  89 */         return SpellCastValidity.TOO_MUCH_CASTS_THIS_TURN;
/*     */       }
/*     */     }
/*  92 */     if ((target != null) && (spell.getCastMaxPerTarget() > 0)) {
/*  93 */       long hash = getSpellOnTargetHashCode(spell, target);
/*  94 */       Integer castsCount = (Integer)this.m_spellsCastedThisTurnOnTarget.get(Long.valueOf(hash));
/*  95 */       if ((castsCount != null) && 
/*  96 */         (castsCount.intValue() >= spell.getCastMaxPerTarget())) {
/*  97 */         return SpellCastValidity.TOO_MUCH_CASTS_ON_THIS_TARGET;
/*     */       }
/*     */     }
/* 100 */     return SpellCastValidity.OK;
/*     */   }
/*     */   
/*     */   private long getSpellOnTargetHashCode(AbstractSpell spell, Target target) {
/* 104 */     return spell.getId() << 32 | HashFunctions.hash(target);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\spell\SpellCastHistory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */