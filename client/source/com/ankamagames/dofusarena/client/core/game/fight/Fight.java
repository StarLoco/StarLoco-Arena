/*     */ package com.ankamagames.dofusarena.client.core.game.fight;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.BasicEffectArea;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFight;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FightingTeam;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.TeamMate;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEventListener;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.FighterTurnTimeEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.RunningEffectDurationTimeEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.StaticRunningEffectDelayedTimeEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.TableTurnTimeEvent;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.alea.highlightingCells.StartPointManager;
/*     */ import com.ankamagames.dofusarena.client.alea.highlightingCells.StaticEffectAreaDisplayer;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.core.game.event.Event;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.core.game.team.DofusArenaNamedFightingTeam;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetFightActionFrame;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetFightActorsFrame;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetFightFrame;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetFightGlobalActionFrame;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetFightObservationFrame;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetFightPlacementFrame;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetFightPresentationFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.progress.DofusArenaProgressMonitorManager;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightObservationFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightPlacementFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightPresentationFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFighterFrame;
/*     */ import com.ankamagames.dofusarena.common.game.card.AbstractFighterCard;
/*     */ import com.ankamagames.dofusarena.common.game.event.AbstractEvent;
/*     */ import com.ankamagames.dofusarena.common.game.fight.AbstractFight;
/*     */ import com.ankamagames.dofusarena.common.game.fight.CloseCombatValidity;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightDefinition;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.dofusarena.common.game.spell.AbstractSpell;
/*     */ import com.ankamagames.dofusarena.common.game.time.AbstractFightTimeline;
/*     */ import com.ankamagames.dofusarena.common.game.time.timeEvent.FightClockedPeriodTimeEvent;
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import com.ankamagames.framework.ai.targetfinder.Target;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Fight
/*     */   extends AbstractFight<Fighter>
/*     */ {
/*     */   private final Countdown m_countdown;
/*     */   private long m_startTime;
/*     */   private int m_bet;
/*     */   
/*     */   public Fight(FightDefinition definition) {
/*  69 */     super(definition);
/*  70 */     onCheckOut();
/*  71 */     this.m_countdown = new Countdown();
/*  72 */     this.m_startTime = 0L;
/*  73 */     this.m_bet = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Countdown getCountdown() {
/*  80 */     return this.m_countdown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDuration() {
/*  87 */     if (this.m_startTime != 0L) {
/*  88 */       return (int)(System.currentTimeMillis() - this.m_startTime) / 60000;
/*     */     }
/*  90 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timeline getTimeline() {
/* 100 */     return (Timeline)this.m_timeline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBet(int bet) {
/* 107 */     this.m_bet = bet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBet() {
/* 114 */     return this.m_bet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicFight newParameterizedInstance(CellInformationProvider cellInfoProvider, Map paramss) {
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DofusArenaNamedFightingTeam<Fighter> newTeam() {
/* 135 */     return new DofusArenaNamedFightingTeam();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addTeam(FightingTeam<Fighter> team) {
/* 145 */     if (getTeamsCount() == 0) {
/* 146 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.team0", team);
/* 147 */     } else if (getTeamsCount() == 1) {
/* 148 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.team1", team);
/*     */     } 
/* 150 */     return super.addTeam(team);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEvent(AbstractEvent event) {
/* 159 */     if (getEvents() != null) {
/* 160 */       getEvents().add(event);
/*     */     }
/* 162 */     updateEventProperty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Event getCurrentEvent() {
/* 169 */     Event event = null;
/* 170 */     if (((getEvents() != null) ? true : false) == (getEvents().isEmpty() ? false : true)) {
/* 171 */       event = getEvents().get(0);
/*     */     }
/* 173 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateEventProperty() {
/* 180 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.eventCards", getEvents().toArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushNewTableTurnEvent() {
/* 190 */     TableTurnTimeEvent te = TableTurnTimeEvent.checkOut(getTimeline().getCurrentTableturn() + 1, (TimeEventListener)getTimeline());
/* 191 */     te.unvalidate();
/* 192 */     getTimeline().addTimeEvent((TimeEvent)te);
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
/*     */   public boolean castSpell(Fighter fighter, AbstractSpell spell, Point3 targetCell) {
/* 204 */     return false;
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
/*     */   public boolean useCard(Fighter fighter, AbstractFighterCard card, Point3 targetCell) {
/* 216 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CloseCombatValidity getCloseCombatValidity(AbstractFighter fighter, Point3 targetCell) {
/* 227 */     if (fighter.isCarrying()) {
/* 228 */       return CloseCombatValidity.CRITERIONS_NOT_VALID;
/*     */     }
/* 230 */     return super.getCloseCombatValidity(fighter, targetCell);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doCloseCombat(Fighter fighter, Point3 targetCell) {
/* 241 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeActionOnEffectArea(Fighter fighter, BasicEffectArea area, int actionId) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPresentationStart() {
/* 261 */     super.onPresentationStart();
/*     */ 
/*     */     
/* 264 */     DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)NetFightPresentationFrame.getInstance());
/* 265 */     DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIFightPresentationFrame.getInstance());
/*     */ 
/*     */     
/* 268 */     getCountdown().start(20);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPresentationEnd() {
/* 278 */     super.onPresentationEnd();
/*     */ 
/*     */     
/* 281 */     DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)NetFightPresentationFrame.getInstance());
/* 282 */     DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFightPresentationFrame.getInstance());
/*     */ 
/*     */     
/* 285 */     getCountdown().stop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlacementStart() {
/* 295 */     super.onPlacementStart();
/*     */ 
/*     */     
/* 298 */     StartPointManager.getInstance().activate(DofusArenaClientInstance.getInstance().getWorldScene());
/* 299 */     StaticEffectAreaDisplayer.getInstance().activate();
/*     */ 
/*     */     
/* 302 */     DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)NetFightPlacementFrame.getInstance());
/* 303 */     DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIFightPlacementFrame.getInstance());
/*     */ 
/*     */     
/* 306 */     getCountdown().start(30);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlacementEnd() {
/* 316 */     super.onPlacementEnd();
/*     */ 
/*     */     
/* 319 */     StartPointManager.getInstance().desactivate();
/*     */ 
/*     */     
/* 322 */     DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)NetFightPlacementFrame.getInstance());
/* 323 */     DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFightPlacementFrame.getInstance());
/*     */ 
/*     */     
/* 326 */     getCountdown().stop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onObservationStart() {
/* 336 */     super.onObservationStart();
/*     */ 
/*     */     
/* 339 */     DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)NetFightObservationFrame.getInstance());
/* 340 */     DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIFightObservationFrame.getInstance());
/*     */ 
/*     */     
/* 343 */     for (Fighter fighter : getCurrentFighters()) {
/* 344 */       fighter.getActor().hideActiveParticleSystem();
/* 345 */       if (fighter.getActor().getDisplayObject() != null) {
/* 346 */         fighter.getActor().getDisplayObject().resetColor();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 351 */     getCountdown().start(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onObservationEnd() {
/* 361 */     super.onObservationEnd();
/*     */ 
/*     */     
/* 364 */     DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)NetFightObservationFrame.getInstance());
/* 365 */     DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFightObservationFrame.getInstance());
/*     */ 
/*     */     
/* 368 */     getCountdown().stop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onActionStart() {
/* 378 */     super.onActionStart();
/*     */ 
/*     */     
/* 381 */     DofusArenaProgressMonitorManager.getInstance().done();
/*     */ 
/*     */     
/* 384 */     this.m_startTime = System.currentTimeMillis();
/*     */ 
/*     */     
/* 387 */     DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)NetFightActionFrame.getInstance());
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
/*     */   public void onTableTurnBegin() {
/* 399 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("singleCardData", getCurrentEvent());
/*     */ 
/*     */     
/* 402 */     Xulor.getInstance().load("singleCardDialog", Dialogs.getDialogPath("singleCardDialog"), 2000, (short)10100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTableTurnEnd() {
/* 413 */     super.onTableTurnEnd();
/* 414 */     updateEventProperty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFighterStartTurn(Fighter fighter) {
/* 424 */     super.onFighterStartTurn((AbstractFighter)fighter);
/* 425 */     if (isLocalCoachFighter(fighter) && !fighter.isSummoned())
/*     */     {
/* 427 */       DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIFighterFrame.getInstance());
/*     */     }
/*     */     
/* 430 */     getCountdown().start(30);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFighterEndTurn(Fighter fighter) {
/* 440 */     super.onFighterEndTurn((BasicFighter)fighter);
/* 441 */     if (isLocalCoachFighter(fighter) && !fighter.isSummoned())
/*     */     {
/* 443 */       DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFighterFrame.getInstance());
/*     */     }
/*     */     
/* 446 */     getCountdown().stop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFighterDeath(Fighter fighter) {
/* 456 */     super.onFighterDeath((AbstractFighter)fighter);
/*     */     
/* 458 */     if (fighter.isCarrying()) {
/* 459 */       fighter.getCarriedFighter().setPosition(fighter.getPosition());
/*     */     }
/*     */     
/* 462 */     MobileManager.getInstance().removeMobile((Mobile)fighter.getActor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEffectDirectExecution(RunningEffect effect) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEffectTriggeredExecution(RunningEffect effect) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFightStarted() {
/* 488 */     super.onFightStarted();
/*     */ 
/*     */     
/* 491 */     DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIFightFrame.getInstance());
/* 492 */     DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)NetFightActorsFrame.getInstance());
/* 493 */     DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)NetFightGlobalActionFrame.getInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFightEnded() {
/* 503 */     super.onFightEnded();
/*     */ 
/*     */     
/* 506 */     getCountdown().stop();
/*     */ 
/*     */     
/* 509 */     for (FightingTeam<Fighter> team : (Iterable<FightingTeam<Fighter>>)getTeams()) {
/* 510 */       Iterator<Fighter> fighterIterator = team.getFighterIterator();
/* 511 */       while (fighterIterator.hasNext()) {
/* 512 */         Fighter fighter = fighterIterator.next();
/* 513 */         fighter.onCheckIn();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 518 */     DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)NetFightFrame.getInstance());
/* 519 */     DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)NetFightGlobalActionFrame.getInstance());
/*     */ 
/*     */     
/* 522 */     StaticEffectAreaDisplayer.getInstance().deactivate();
/*     */ 
/*     */     
/* 525 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("fight.team0");
/* 526 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("fight.team1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCoachReady() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTeamMateRemovedFromFight(TeamMate<Fighter> teamMate) {
/* 546 */     super.onTeamMateRemovedFromFight(teamMate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTeamWin(FightingTeam<Fighter> winner) {
/* 556 */     super.onTeamWin(winner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTeamLose(FightingTeam<Fighter> looser) {
/* 566 */     super.onTeamLose(looser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEffectAreaAdded(BasicEffectArea area) {
/* 576 */     super.onEffectAreaAdded(area);
/*     */     
/* 578 */     StaticEffectAreaDisplayer.getInstance().addStaticEffectArea(area, DofusArenaClientInstance.getInstance().getWorldScene());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEffectAreaRemoved(BasicEffectArea area) {
/* 588 */     super.onEffectAreaRemoved(area);
/*     */     
/* 590 */     StaticEffectAreaDisplayer.getInstance().removeStaticEffectArea(area, DofusArenaClientInstance.getInstance().getWorldScene());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTimeEventActivated(TimeEvent te) {
/*     */     StaticRunningEffectDelayedTimeEvent staticRunningEffectDelayedTimeEvent;
/*     */     FighterTurnTimeEvent cte;
/*     */     StaticRunningEffect re;
/* 601 */     switch (te.getType()) {
/*     */       
/*     */       case 2:
/* 604 */         staticRunningEffectDelayedTimeEvent = (StaticRunningEffectDelayedTimeEvent)te;
/* 605 */         re = staticRunningEffectDelayedTimeEvent.getStaticRunningEffect();
/* 606 */         re.run(staticRunningEffectDelayedTimeEvent.getGenericEffect(), staticRunningEffectDelayedTimeEvent.getContainer(), staticRunningEffectDelayedTimeEvent.getContext(), staticRunningEffectDelayedTimeEvent.getLauncher(), staticRunningEffectDelayedTimeEvent.getTargetCell(), true);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 107:
/* 615 */         cte = (FighterTurnTimeEvent)te;
/* 616 */         onFighterStartTurn((Fighter)cte.getTurnBasedFighter());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 106:
/* 621 */         onTableTurnBegin();
/*     */         break;
/*     */     } 
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
/*     */   public void onTimeEventDesactivated(TimeEvent te) {
/*     */     RunningEffectDurationTimeEvent runningEffectDurationTimeEvent;
/*     */     FighterTurnTimeEvent fighterTurnTimeEvent;
/*     */     FightClockedPeriodTimeEvent cte;
/* 639 */     switch (te.getType()) {
/*     */       
/*     */       case 1:
/* 642 */         runningEffectDurationTimeEvent = (RunningEffectDurationTimeEvent)te;
/* 643 */         runningEffectDurationTimeEvent.getRunningEffect().askForUnapplication();
/*     */         break;
/*     */ 
/*     */       
/*     */       case 107:
/* 648 */         fighterTurnTimeEvent = (FighterTurnTimeEvent)te;
/* 649 */         onFighterEndTurn((Fighter)fighterTurnTimeEvent.getTurnBasedFighter());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 106:
/* 654 */         onTableTurnEnd();
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1001:
/* 659 */         cte = (FightClockedPeriodTimeEvent)te;
/* 660 */         switch (cte.getFightStatus()) {
/*     */           
/*     */           case PRESENTATION:
/* 663 */             endPresentation();
/* 664 */             startPlacement();
/*     */             break;
/*     */           
/*     */           case PLACEMENT:
/* 668 */             endPlacement();
/* 669 */             startObservation();
/*     */             break;
/*     */           
/*     */           case OBSERVATION:
/* 673 */             endObservation();
/* 674 */             startAction();
/*     */             break;
/*     */         } 
/*     */         break;
/*     */     } 
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
/*     */   public void onEffectAreaApplication(BasicEffectArea area, Target applicant) {}
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
/*     */   public void onEffectAreaExecuted(BasicEffectArea area) {
/* 703 */     super.onEffectAreaExecuted(area);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEffectAreaUnapplication(BasicEffectArea area, Target applicant) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocalCoachFighter(Fighter fighter) {
/* 720 */     if (fighter != null) {
/* 721 */       LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/* 722 */       if (localCoach != null) {
/* 723 */         TeamMate currentTeamMate = fighter.getTeamMate();
/* 724 */         if (currentTeamMate != null && localCoach.getFightingCoach().equals(currentTeamMate)) {
/* 725 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 729 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fight\Fight.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */