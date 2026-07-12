/*     */ package com.ankamagames.dofusarena.common.game.fight;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffectManager;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFight;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FightersGroup;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.turnBased.AbstractTurnBasedFight;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.StackInventory;
/*     */ import com.ankamagames.dofusarena.common.game.card.AbstractFighterCard;
/*     */ import com.ankamagames.dofusarena.common.game.effect.ArenaEffectContext;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
/*     */ import com.ankamagames.dofusarena.common.game.event.AbstractEvent;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.Breed;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*     */ import com.ankamagames.dofusarena.common.game.spell.AbstractSpell;
/*     */ import com.ankamagames.dofusarena.common.game.spell.SpellCastHistory;
/*     */ import com.ankamagames.dofusarena.common.game.time.AbstractFightTimeline;
/*     */ import com.ankamagames.framework.ai.LOS.LineOfSightUtils;
/*     */ import com.ankamagames.framework.ai.criteria.Criterion;
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import gnu.trove.TByteObjectHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public abstract class AbstractFight<F extends AbstractFighter> extends AbstractTurnBasedFight<F> implements com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectExecutionListener, com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEventListener
/*     */ {
/*     */   protected FightStatus m_status;
/*     */   protected byte m_readyCount;
/*     */   protected byte m_coachCount;
/*     */   protected TByteObjectHashMap<ArrayList<Point3>> m_fighterPositionsByTeam;
/*     */   protected TByteObjectHashMap<Point3> m_coachPositionByTeam;
/*     */   protected ArrayList<AbstractEvent> m_events;
/*     */   
/*     */   public static enum FightStatus
/*     */   {
/*  43 */     PRESENTATION,  PLACEMENT,  OBSERVATION,  ACTION,  NONE;
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
/*     */   public void onCheckOut()
/*     */   {
/*  63 */     super.onCheckOut();
/*  64 */     this.m_fighterPositionsByTeam = null;
/*  65 */     this.m_coachPositionByTeam = null;
/*  66 */     this.m_coachCount = 0;
/*  67 */     this.m_readyCount = 0;
/*  68 */     this.m_status = FightStatus.NONE;
/*     */   }
/*     */   
/*     */   public void onCheckIn()
/*     */   {
/*  73 */     super.onCheckIn();
/*  74 */     this.m_fighterPositionsByTeam = null;
/*  75 */     this.m_coachPositionByTeam = null;
/*  76 */     this.m_coachCount = 0;
/*  77 */     this.m_readyCount = 0;
/*  78 */     this.m_status = FightStatus.NONE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractFight() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public AbstractFight(FightDefinition definition)
/*     */   {
/*  91 */     this.m_typeId = definition.getId();
/*  92 */     this.m_minTeam = definition.getTeamNumber();
/*  93 */     this.m_maxTeam = definition.getTeamNumber();
/*  94 */     this.m_minFighterByTeam = definition.getMinFighterByTeam();
/*  95 */     this.m_maxFighterByTeam = definition.getMaxFighterByTeam();
/*     */   }
/*     */   
/*     */   public boolean canCreateFight(FightersGroup... groups)
/*     */   {
/* 100 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setParam(Map param) {}
/*     */   
/*     */ 
/*     */   public ArrayList<AbstractEvent> getEvents()
/*     */   {
/* 110 */     return this.m_events;
/*     */   }
/*     */   
/*     */   public void setEvents(ArrayList<AbstractEvent> events) {
/* 114 */     this.m_events = events;
/*     */   }
/*     */   
/*     */   public void addEvents(ArrayList<AbstractEvent> events) {
/* 118 */     this.m_events.addAll(events);
/*     */   }
/*     */   
/*     */   public void onFighterJoinFight(F f) {
/* 122 */     super.onFighterJoinFight(f);
/*     */   }
/*     */   
/*     */   public void onFighterStartTurn(F fighter) {
/* 126 */     fighter.getCharacteristic(FighterCharacteristicType.AP).toMax();
/* 127 */     fighter.getCharacteristic(FighterCharacteristicType.MP).toMax();
/* 128 */     super.onFighterStartTurn(fighter);
/*     */   }
/*     */   
/*     */   public void onFighterDeath(F f)
/*     */   {
/* 133 */     super.onFighterDeath(f);
/*     */     
/* 135 */     for (Iterator<? extends EffectUser> it = getEffectUsers(); it.hasNext();) {
/* 136 */       EffectUser user = (EffectUser)it.next();
/*     */       
/* 138 */       if (user.getRunningEffectManager() != null) {
/* 139 */         user.getRunningEffectManager().removeLinkedToCaster(f);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 144 */     getTimeline().removeFighter(f);
/*     */   }
/*     */   
/*     */   public AbstractFightTimeline getTimeline() {
/* 148 */     return (AbstractFightTimeline)this.m_timeline;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void start()
/*     */   {
/* 155 */     getTimeline().start();
/* 156 */     onFightStarted();
/*     */   }
/*     */   
/*     */   public boolean startPresentation() {
/* 160 */     this.m_status = FightStatus.PRESENTATION;
/* 161 */     getTimeline().startPresentation();
/* 162 */     onPresentationStart();
/* 163 */     return true;
/*     */   }
/*     */   
/*     */   public boolean startPlacement() {
/* 167 */     this.m_status = FightStatus.PLACEMENT;
/* 168 */     getTimeline().startPlacement();
/* 169 */     onPlacementStart();
/* 170 */     return true;
/*     */   }
/*     */   
/*     */   public void endPresentation() {
/* 174 */     onPresentationEnd();
/*     */   }
/*     */   
/*     */   public void endPlacement() {
/* 178 */     onPlacementEnd();
/*     */   }
/*     */   
/*     */   public boolean startObservation() {
/* 182 */     if (this.m_status == FightStatus.PLACEMENT) {
/* 183 */       getTimeline().startObservation();
/* 184 */       this.m_status = FightStatus.OBSERVATION;
/* 185 */       onObservationStart();
/* 186 */       return true;
/*     */     }
/* 188 */     BasicFight.m_logger.error("on passe en mode observation sans etre en mode placement");
/* 189 */     return false;
/*     */   }
/*     */   
/*     */   public void endObservation()
/*     */   {
/* 194 */     onObservationEnd();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean startAction()
/*     */   {
/* 200 */     if (this.m_status != FightStatus.OBSERVATION) {
/* 201 */       BasicFight.m_logger.error("on passe en mode action sans être en mode observation");
/* 202 */       return false;
/*     */     }
/*     */     
/* 205 */     this.m_status = FightStatus.ACTION;
/*     */     
/*     */ 
/* 208 */     pushNewTableTurnEvent();
/*     */     
/* 210 */     onActionStart();
/*     */     
/* 212 */     return true;
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
/*     */   public SpellCastValidity getSpellCastValidity(AbstractFighter fighter, AbstractSpell spell, Point3 targetCell)
/*     */   {
/* 231 */     if (spell == null) {
/* 232 */       m_logger.error("on tente de lancer un sort null");
/* 233 */       return SpellCastValidity.INVALID_SPELL;
/*     */     }
/*     */     
/*     */ 
/* 237 */     if (!fighter.isSummoned()) {
/* 238 */       if (fighter.getSpellInventory() == null) {
/* 239 */         m_logger.error("Inventaire de sorts du joueur " + fighter + " null. Etrange...");
/* 240 */         return SpellCastValidity.SPELL_UNKNOWN;
/*     */       }
/*     */       
/* 243 */       if ((!fighter.getSpellInventory().containsUniqueId(spell.getUniqueId())) && 
/* 244 */         (fighter.getTeamMateSpellInventory() != null) && (!fighter.getTeamMateSpellInventory().containsUniqueId(spell.getUniqueId()))) {
/* 245 */         return SpellCastValidity.SPELL_UNKNOWN;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 250 */     if (spell.getActionPoints() > fighter.getCharacteristic(FighterCharacteristicType.AP).value())
/* 251 */       return SpellCastValidity.NOT_ENOUGH_PA;
/*     */     int poBoost;
/*     */     int distanceMax;
/* 254 */     if ((targetCell != null) && (this.m_cellInformationProvider != null))
/*     */     {
/* 256 */       if (!this.m_cellInformationProvider.getCellValidity(targetCell.getX(), targetCell.getY(), targetCell.getZ())) {
/* 257 */         return SpellCastValidity.INVALID_TARGET_CELL;
/*     */       }
/*     */       
/* 260 */       int distance = Math.abs(targetCell.getX() - fighter.getPosition().getX()) + Math.abs(targetCell.getY() - fighter.getPosition().getY());
/* 261 */       poBoost = fighter.getCharacteristicValue(FighterCharacteristicType.RANGE);
/* 262 */       int distanceMin = spell.getRangeMin();
/* 263 */       distanceMax = spell.getRangeMax();
/*     */       
/* 265 */       if (distanceMax > 1) {
/* 266 */         distanceMax = Math.max(distanceMax + poBoost, distanceMin);
/*     */       }
/* 268 */       if ((distance < distanceMin) || (distance > distanceMax)) {
/* 269 */         return SpellCastValidity.INVALID_RANGE;
/*     */       }
/*     */       
/* 272 */       if ((spell.castOnlyInLine()) && 
/* 273 */         (targetCell.getX() != fighter.getPosition().getX()) && (targetCell.getY() != fighter.getPosition().getY())) {
/* 274 */         return SpellCastValidity.CELLS_NOT_ALIGNED;
/*     */       }
/*     */       
/* 277 */       AbstractFighter target = getTargetOnPosition(targetCell);
/*     */       
/* 279 */       if ((target != null) && (spell.hasToTestFreeCell())) {
/* 280 */         return SpellCastValidity.CELL_NOT_FREE;
/*     */       }
/*     */       
/*     */ 
/* 284 */       SpellCastValidity validity = fighter.getSpellCastHistory().canCastSpell(spell, getTimeline().getCurrentTableturn(), target);
/* 285 */       if (!validity.isValid()) {
/* 286 */         return validity;
/*     */       }
/*     */       
/* 289 */       if (spell.hasToTestLineOfSight()) {
/* 290 */         if (!LineOfSightUtils.check(fighter, this.m_cellInformationProvider, this, fighter.getPosition(), targetCell, null)) {
/* 291 */           if (fighter.getStandardHeight() > 0) {
/* 292 */             Point3 headPosition = new Point3(fighter.getPosition());
/* 293 */             headPosition.setZ((short)(headPosition.getZ() + fighter.getStandardHeight()));
/* 294 */             if (!LineOfSightUtils.check(fighter, this.m_cellInformationProvider, this, headPosition, targetCell, null)) {
/* 295 */               return SpellCastValidity.INVALID_LINE_OF_SIGHT;
/*     */             }
/*     */           } else {
/* 298 */             return SpellCastValidity.INVALID_LINE_OF_SIGHT;
/*     */           }
/*     */           
/*     */         }
/*     */       }
/* 303 */       else if (!this.m_cellInformationProvider.getLineOfSightEndValidity(targetCell.getX(), targetCell.getY(), targetCell.getZ())) {
/* 304 */         return SpellCastValidity.INVALID_LINE_OF_SIGHT;
/*     */       }
/*     */     }
/*     */     else {
/* 308 */       SpellCastValidity validity = fighter.getSpellCastHistory().canCastSpell(spell, getTimeline().getCurrentTableturn());
/* 309 */       if (!validity.isValid()) {
/* 310 */         return validity;
/*     */       }
/*     */     }
/* 313 */     if (spell.getCastCriterions() != null) {
/* 314 */       for (Criterion criterion : spell.getCastCriterions()) {
/* 315 */         if (!criterion.isValid(fighter, null, spell, this)) {
/* 316 */           return SpellCastValidity.CAST_CRITERIONS_NOT_VALID;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 323 */     if ((targetCell != null) && (this.m_cellInformationProvider != null))
/*     */     {
/* 325 */       AbstractFighter target = getTargetOnPosition(targetCell);
/* 326 */       if (target != null) {
/* 327 */         boolean validTarget = false;
/* 328 */         for (Effect eff : spell.getEffects()) {
/* 329 */           if (eff.getTargetValidator() == null) {
/* 330 */             validTarget = true;
/* 331 */             break;
/*     */           }
/* 333 */           switch (eff.getTargetValidator().getTargetValidity(target, fighter)) {
/*     */           case INVALID: 
/*     */           case VALID_IF_IN_AOE: 
/* 336 */             validTarget = true;
/*     */           }
/*     */           
/*     */           
/*     */ 
/* 341 */           if (target.isCarrying()) {
/* 342 */             StaticRunningEffect sre = (StaticRunningEffect)RunningEffectConstants.getInstance().getObjectFromId(eff.getActionId());
/* 343 */             if ((sre != null) && (sre.getRunningEffectStatus() == com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffectStatus.POSITIVE)) {
/* 344 */               switch (eff.getTargetValidator().getTargetValidity(target.getCarriedFighter(), fighter)) {
/*     */               case INVALID: 
/*     */               case VALID_IF_IN_AOE: 
/* 347 */                 validTarget = true;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 352 */         if (!validTarget) { return SpellCastValidity.OK_BUT_NO_EFFECT_ON_TARGET;
/*     */         }
/*     */       }
/*     */     }
/* 356 */     return SpellCastValidity.OK;
/*     */   }
/*     */   
/*     */   public CardUseValidity getCardUseValidity(AbstractFighter fighter, AbstractFighterCard card, Point3 targetCell)
/*     */   {
/* 361 */     if (card == null) {
/* 362 */       m_logger.error("on tente d'utiliser une carte nulle");
/* 363 */       return CardUseValidity.INVALID_CARD;
/*     */     }
/*     */     
/*     */ 
/* 367 */     if (!card.isUsable()) {
/* 368 */       return CardUseValidity.INVALID_CARD;
/*     */     }
/*     */     
/*     */ 
/* 372 */     if (card.getActionPoints() > fighter.getCharacteristic(FighterCharacteristicType.AP).value()) {
/* 373 */       return CardUseValidity.NOT_ENOUGH_PA;
/*     */     }
/*     */     
/* 376 */     if (!fighter.getEquipmentInventory().containsUniqueId(card.getUniqueId())) {
/* 377 */       return CardUseValidity.CARD_NOT_OWNED;
/*     */     }
/*     */     
/*     */ 
/* 381 */     if ((!card.canUseWhenCarrying()) && (fighter.isCarrying()))
/* 382 */       return CardUseValidity.CRITERIONS_NOT_VALID;
/* 383 */     if ((!card.canUseWhenCarried()) && (fighter.isCarried())) {
/* 384 */       return CardUseValidity.CRITERIONS_NOT_VALID;
/*     */     }
/*     */     
/* 387 */     if (targetCell != null)
/*     */     {
/* 389 */       if (!this.m_cellInformationProvider.getCellValidity(targetCell.getX(), targetCell.getY(), targetCell.getZ())) {
/* 390 */         return CardUseValidity.INVALID_TARGET_CELL;
/*     */       }
/*     */       
/*     */ 
/* 394 */       int distance = Math.abs(targetCell.getX() - fighter.getPosition().getX()) + Math.abs(targetCell.getY() - fighter.getPosition().getY());
/*     */       
/* 396 */       if ((distance < card.getRangeMin()) || (distance > card.getRangeMax())) {
/* 397 */         return CardUseValidity.INVALID_RANGE;
/*     */       }
/*     */       
/* 400 */       if ((card.useOnlyInLine()) && 
/* 401 */         (targetCell.getX() != fighter.getPosition().getX()) && (targetCell.getY() != fighter.getPosition().getY())) {
/* 402 */         return CardUseValidity.CELLS_NOT_ALIGNED;
/*     */       }
/*     */       
/* 405 */       AbstractFighter target = getTargetOnPosition(targetCell);
/*     */       
/* 407 */       if ((target != null) && (card.hasToTestCellFree())) {
/* 408 */         return CardUseValidity.CELL_NOT_FREE;
/*     */       }
/*     */       
/*     */ 
/* 412 */       if (card.hasToTestLineOfSight()) {
/* 413 */         if (!LineOfSightUtils.check(fighter, this.m_cellInformationProvider, this, fighter.getPosition(), targetCell, null)) {
/* 414 */           return CardUseValidity.INVALID_LINE_OF_SIGHT;
/*     */         }
/*     */       }
/* 417 */       else if (!this.m_cellInformationProvider.getLineOfSightEndValidity(targetCell.getX(), targetCell.getY(), targetCell.getZ())) {
/* 418 */         return CardUseValidity.INVALID_LINE_OF_SIGHT;
/*     */       }
/*     */     }
/*     */     
/* 422 */     return CardUseValidity.OK;
/*     */   }
/*     */   
/*     */   public CloseCombatValidity getCloseCombatValidity(AbstractFighter fighter, Point3 targetCell)
/*     */   {
/* 427 */     Breed b = fighter.getBreed();
/* 428 */     if (b == null) {
/* 429 */       return null;
/*     */     }
/*     */     
/* 432 */     if (b.getCloseCombatAp() > fighter.getCharacteristic(FighterCharacteristicType.AP).value()) {
/* 433 */       return CloseCombatValidity.NOT_ENOUGH_PA;
/*     */     }
/*     */     
/* 436 */     if (targetCell != null)
/*     */     {
/* 438 */       if ((!this.m_cellInformationProvider.getCellValidity(targetCell.getX(), targetCell.getY(), targetCell.getZ())) || 
/* 439 */         (!this.m_cellInformationProvider.getLineOfSightEndValidity(targetCell.getX(), targetCell.getY(), targetCell.getZ()))) {
/* 440 */         return CloseCombatValidity.INVALID_TARGET_CELL;
/*     */       }
/*     */       
/*     */ 
/* 444 */       int distance = Math.abs(targetCell.getX() - fighter.getPosition().getX()) + Math.abs(targetCell.getY() - fighter.getPosition().getY());
/* 445 */       if (distance != 1) {
/* 446 */         return CloseCombatValidity.INVALID_RANGE;
/*     */       }
/*     */     }
/*     */     
/* 450 */     return CloseCombatValidity.OK;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract boolean castSpell(F paramF, AbstractSpell paramAbstractSpell, Point3 paramPoint3);
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract boolean useCard(F paramF, AbstractFighterCard paramAbstractFighterCard, Point3 paramPoint3);
/*     */   
/*     */ 
/*     */   public abstract boolean doCloseCombat(F paramF, Point3 paramPoint3);
/*     */   
/*     */ 
/*     */   public F getTargetOnPosition(Point3 pos)
/*     */   {
/* 467 */     for (Iterator<F> it = getPossibleTargets(); it.hasNext();) {
/* 468 */       F f = (AbstractFighter)it.next();
/* 469 */       Point3 fPos = f.getPosition();
/* 470 */       if ((pos.getX() == fPos.getX()) && (pos.getY() == fPos.getY()) && 
/* 471 */         (fPos.getZ() <= pos.getZ()) && (fPos.getZ() + f.getHeight() >= pos.getZ()) && 
/* 472 */         (!f.isCarried())) {
/* 473 */         return f;
/*     */       }
/*     */     }
/*     */     
/* 477 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onFightCreatedAndInitialized()
/*     */   {
/* 484 */     this.m_context = ArenaEffectContext.checkOut(this);
/* 485 */     super.onFightCreatedAndInitialized();
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract void onCoachReady();
/*     */   
/*     */   public void onFightStarted()
/*     */   {
/* 493 */     startPresentation();
/*     */   }
/*     */   
/*     */   public void onPresentationStart() {
/* 497 */     m_logger.info("phase de présentation ");
/*     */   }
/*     */   
/*     */   public void onPlacementStart() {
/* 501 */     m_logger.info("phase de placement ");
/*     */   }
/*     */   
/*     */ 
/*     */   public void onPresentationEnd() {}
/*     */   
/*     */   public void onPlacementEnd() {}
/*     */   
/*     */   public void onObservationStart()
/*     */   {
/* 511 */     m_logger.info("phase d'observation ");
/*     */   }
/*     */   
/*     */   public void onObservationEnd() {}
/*     */   
/*     */   public void onActionStart()
/*     */   {
/* 518 */     m_logger.info("phase d'Action");
/*     */   }
/*     */   
/*     */   public void onTableTurnEnd() {
/* 522 */     super.onTableTurnEnd();
/* 523 */     if ((this.m_events != null) && (this.m_events.size() > 0)) {
/* 524 */       this.m_events.remove(0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fight\AbstractFight.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */