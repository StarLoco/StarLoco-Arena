/*     */ package com.ankamagames.dofusarena.common.game.time;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeUnit;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeline;
/*     */ import com.ankamagames.dofusarena.common.game.fight.AbstractFight.FightStatus;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*     */ import com.ankamagames.dofusarena.common.game.filter.FighterTurnTimeEventFilter;
/*     */ import com.ankamagames.dofusarena.common.game.time.timeEvent.FightClockedPeriodTimeEvent;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.scheduler.ClockMessage;
/*     */ import com.ankamagames.framework.kernel.core.common.message.scheduler.MessageScheduler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public abstract class AbstractFightTimeline<F extends AbstractFighter>
/*     */   extends TurnBasedTimeline<F>
/*     */ {
/*  37 */   protected long m_lastStepClock = -1L;
/*  38 */   protected long m_lastTurnEndClock = -1L;
/*     */   
/*     */ 
/*     */   public void onCheckOut()
/*     */   {
/*  43 */     super.onCheckOut();
/*  44 */     this.m_orderedFigthers = new ArrayList();
/*  45 */     this.m_timeline = new Vector();
/*     */   }
/*     */   
/*     */   public void onCheckIn() {
/*  49 */     super.onCheckIn();
/*  50 */     if (this.m_lastTurnEndClock > 0L)
/*  51 */       MessageScheduler.getInstance().removeClock(this.m_lastTurnEndClock);
/*  52 */     this.m_orderedFigthers.clear();
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract void pushClockedTimeEvent(int paramInt, AbstractFight.FightStatus paramFightStatus);
/*     */   
/*     */ 
/*     */   public void askForPresentation()
/*     */   {
/*  61 */     if ((!isEmpty()) && (getFirstTimeEvent().getType() == 1001)) {
/*  62 */       getFirstTimeEvent().validate();
/*  63 */       nextTimeEvent();
/*     */     }
/*     */   }
/*     */   
/*     */   public void askForStartPlacement() {
/*  68 */     if ((!isEmpty()) && (getFirstTimeEvent().getType() == 1001)) {
/*  69 */       getFirstTimeEvent().validate();
/*  70 */       nextTimeEvent();
/*     */     }
/*     */   }
/*     */   
/*     */   public void askForStartObservation() {
/*  75 */     if ((!isEmpty()) && (getFirstTimeEvent().getType() == 1001)) {
/*  76 */       getFirstTimeEvent().validate();
/*  77 */       nextTimeEvent();
/*     */     }
/*     */   }
/*     */   
/*     */   public void askForStartAction() {
/*  82 */     if ((!isEmpty()) && (getFirstTimeEvent().getType() == 1001)) {
/*  83 */       getFirstTimeEvent().validate();
/*  84 */       nextTimeEvent();
/*     */     }
/*     */   }
/*     */   
/*     */   public void askForStartTurn() {
/*  89 */     if ((!isEmpty()) && (getFirstTimeEvent().getType() == 106)) {
/*  90 */       getFirstTimeEvent().validate();
/*  91 */       nextTimeEvent();
/*     */     }
/*     */   }
/*     */   
/*     */   public void startPresentation()
/*     */   {
/*  97 */     this.m_lastStepClock = startClock(20000, 1);
/*  98 */     pushClockedTimeEvent(3, AbstractFight.FightStatus.PRESENTATION);
/*     */   }
/*     */   
/*     */   public void startPlacement() {
/* 102 */     this.m_lastStepClock = startClock(30000, 2);
/* 103 */     pushClockedTimeEvent(2, AbstractFight.FightStatus.PLACEMENT);
/*     */   }
/*     */   
/*     */   public void startObservation()
/*     */   {
/* 108 */     this.m_lastStepClock = startClock(10000, 3);
/* 109 */     pushClockedTimeEvent(1, AbstractFight.FightStatus.OBSERVATION);
/*     */   }
/*     */   
/*     */   private void startTurnClock() {
/* 113 */     if (this.m_isRunning) {
/* 114 */       this.m_lastTurnEndClock = startClock(30000, 4);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean startFighterTurn(F fighter)
/*     */   {
/* 120 */     if (super.startFighterTurn(fighter)) {
/* 121 */       startTurnClock();
/* 122 */       return true;
/*     */     }
/* 124 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void askForPresentationEnd()
/*     */   {
/* 130 */     if ((!isEmpty()) && (getFirstTimeEvent().getType() == 1001)) {
/* 131 */       FightClockedPeriodTimeEvent te = (FightClockedPeriodTimeEvent)getFirstTimeEvent();
/* 132 */       if (te.getFightStatus() == AbstractFight.FightStatus.PRESENTATION) {
/* 133 */         MessageScheduler.getInstance().removeClock(this.m_lastStepClock);
/* 134 */         getFirstTimeEvent().validate();
/* 135 */         nextTimeEvent();
/*     */       } else {
/* 137 */         m_logger.error("demande de fin de présentation en dehors de la phase de présentation");
/*     */       }
/*     */     } else {
/* 140 */       m_logger.error("askForPresentationEnd : aucun timeEvent dans la timeline");
/*     */     }
/*     */   }
/*     */   
/*     */   public void askForPlacementEnd()
/*     */   {
/* 146 */     if ((!isEmpty()) && (getFirstTimeEvent().getType() == 1001)) {
/* 147 */       FightClockedPeriodTimeEvent te = (FightClockedPeriodTimeEvent)getFirstTimeEvent();
/* 148 */       if (te.getFightStatus() == AbstractFight.FightStatus.PLACEMENT) {
/* 149 */         MessageScheduler.getInstance().removeClock(this.m_lastStepClock);
/* 150 */         getFirstTimeEvent().validate();
/* 151 */         nextTimeEvent();
/*     */       } else {
/* 153 */         m_logger.error("demande de fin de placement en dehors de la phase de placement");
/*     */       }
/*     */     } else {
/* 156 */       m_logger.error("askForPlacementEnd : aucun timeEvent dans la timeline");
/*     */     }
/*     */   }
/*     */   
/*     */   public void askForObservationEnd() {
/* 161 */     if ((!isEmpty()) && (getFirstTimeEvent().getType() == 1001)) {
/* 162 */       FightClockedPeriodTimeEvent te = (FightClockedPeriodTimeEvent)getFirstTimeEvent();
/* 163 */       if (te.getFightStatus() == AbstractFight.FightStatus.OBSERVATION) {
/* 164 */         MessageScheduler.getInstance().removeClock(this.m_lastStepClock);
/* 165 */         getFirstTimeEvent().validate();
/* 166 */         nextTimeEvent();
/*     */       } else {
/* 168 */         m_logger.error("demande de fin d'observation en dehors de la phase d'Observation");
/*     */       }
/*     */     } else {
/* 171 */       m_logger.error("askForObservationEnd : aucun timeEvent dans la timeline");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean askForFighterEndTurn(BasicFighter fighter)
/*     */   {
/* 177 */     MessageScheduler.getInstance().removeClock(this.m_lastTurnEndClock);
/* 178 */     if (super.askForFighterEndTurn(fighter)) {
/* 179 */       return true;
/*     */     }
/* 181 */     return false;
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
/*     */   public void addFighter(F fighterToAdd, boolean pushEvent, boolean ordered)
/*     */   {
/* 198 */     if (fighterToAdd.isDead()) { return;
/*     */     }
/* 200 */     if (this.m_orderedFigthers.size() == 0) {
/* 201 */       this.m_orderedFigthers.add(fighterToAdd);
/*     */     } else {
/* 203 */       int index = 0;
/*     */       
/* 205 */       if (!fighterToAdd.isSummoned()) {
/* 206 */         if (ordered) {
/* 207 */           int init = fighterToAdd.getCharacteristicValue(FighterCharacteristicType.INIT);
/* 208 */           for (F fighter : this.m_orderedFigthers) {
/* 209 */             int currentInit = fighter.getCharacteristicValue(FighterCharacteristicType.INIT);
/* 210 */             if (init >= currentInit) {
/*     */               break;
/*     */             }
/* 213 */             index++;
/*     */           }
/*     */         } else {
/* 216 */           index = this.m_orderedFigthers.size();
/*     */         }
/*     */         
/*     */       }
/*     */       else
/*     */       {
/* 222 */         boolean fatherFound = false;
/* 223 */         for (F fighter : this.m_orderedFigthers)
/*     */         {
/* 225 */           if ((fatherFound) && (!fighter.isSummoned())) {
/*     */             break;
/*     */           }
/* 228 */           if ((!fatherFound) && (fighterToAdd.getFather() == fighter)) {
/* 229 */             fatherFound = true;
/*     */           }
/* 231 */           index++;
/*     */         }
/*     */       }
/* 234 */       if (pushEvent)
/*     */       {
/* 236 */         boolean addLast = false;
/* 237 */         F fighterTimeIndex; F fighterTimeIndex; if (index == this.m_orderedFigthers.size()) {
/* 238 */           addLast = true;
/*     */           
/* 240 */           fighterTimeIndex = (AbstractFighter)this.m_orderedFigthers.get(this.m_orderedFigthers.size() - 1);
/*     */         }
/*     */         else
/*     */         {
/* 244 */           fighterTimeIndex = (AbstractFighter)this.m_orderedFigthers.get(index);
/*     */         }
/*     */         
/*     */ 
/* 248 */         Object lte = filter(new FighterTurnTimeEventFilter(fighterTimeIndex));
/* 249 */         if (((List)lte).size() > 0) {
/* 250 */           TurnBasedTimeUnit tu = (TurnBasedTimeUnit)((TimeEvent)((List)lte).get(0)).when();
/*     */           
/* 252 */           addFighterTimeEventAt(fighterToAdd, tu.getTableTurn(), tu.getTurn() + (addLast ? 1 : 0));
/*     */         } else {
/* 254 */           m_logger.error("aucun timeEvent dans la timeline pour un figther donné, impossible d'obtenir une base de temps pour en ajouter un");
/*     */         }
/*     */       }
/*     */       
/* 258 */       this.m_orderedFigthers.add(index, fighterToAdd);
/*     */     }
/*     */     
/* 261 */     onFighterAdded(fighterToAdd);
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
/*     */   public boolean onMessage(Message message)
/*     */   {
/* 275 */     if (!(message instanceof ClockMessage)) {
/* 276 */       return true;
/*     */     }
/*     */     
/* 279 */     ClockMessage msg = (ClockMessage)message;
/*     */     
/*     */ 
/* 282 */     switch (msg.getSubId()) {
/*     */     case 1: 
/* 284 */       if (this.m_lastStepClock == msg.getClockId())
/* 285 */         askForPresentationEnd();
/* 286 */       break;
/*     */     
/*     */     case 2: 
/* 289 */       if (this.m_lastStepClock == msg.getClockId())
/* 290 */         askForPlacementEnd();
/* 291 */       break;
/*     */     
/*     */     case 3: 
/* 294 */       if (this.m_lastStepClock == msg.getClockId())
/* 295 */         askForObservationEnd();
/* 296 */       break;
/*     */     
/*     */     case 4: 
/* 299 */       if (this.m_lastTurnEndClock == msg.getClockId()) {
/* 300 */         askForFighterEndTurn(getCurrentFighter());
/*     */       } else {
/* 302 */         m_logger.error("on recup une clock de fin de tour qu'on ne connait pas " + this.m_lastTurnEndClock + "vs" + msg.getClockId());
/*     */       }
/*     */       break;
/*     */     }
/* 306 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\time\AbstractFightTimeline.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */