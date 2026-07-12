/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.filter.Filter;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.filter.TimeEventRelatedToEffectUserFilter;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.BasicTimeline;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeUnit;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.EffectAreaActivationTimeEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.FighterTurnTimeEvent;
/*     */ import com.ankamagames.framework.kernel.core.common.message.MessageHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.scheduler.MessageScheduler;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TurnBasedTimeline<F extends BasicFighter>
/*     */   extends BasicTimeline<TurnBasedTimeUnit, TurnBasedTimeInterval>
/*     */   implements MessageHandler
/*     */ {
/*     */   protected byte m_currentTableturn;
/*     */   protected byte m_currentTurn;
/*     */   private F m_currentFighter;
/*  29 */   private final TurnBasedTimeUnit m_now = new TurnBasedTimeUnit(0, 0);
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<F> m_orderedFigthers;
/*     */ 
/*     */ 
/*     */   
/*     */   public long startClock(int timeInMilli, int clockType) {
/*  38 */     return MessageScheduler.getInstance().addClock(this, timeInMilli, clockType, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckIn() {
/*  46 */     super.onCheckIn();
/*  47 */     this.m_currentTableturn = 0;
/*  48 */     this.m_currentTurn = 0;
/*     */   }
/*     */   
/*     */   public void onCheckOut() {
/*  52 */     super.onCheckOut();
/*  53 */     this.m_currentTableturn = 0;
/*  54 */     this.m_currentTurn = 0;
/*     */   }
/*     */   
/*     */   public void setOrderedFigthers(List<F> orderedFigthers) {
/*  58 */     this.m_orderedFigthers = orderedFigthers;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/*  64 */     return 1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public byte getCurrentTableturn() {
/*  71 */     return this.m_currentTableturn;
/*     */   }
/*     */   
/*     */   public byte getCurrentTurn() {
/*  75 */     return this.m_currentTurn;
/*     */   }
/*     */   
/*     */   public F getCurrentFighter() {
/*  79 */     return this.m_currentFighter;
/*     */   }
/*     */   
/*     */   public void setCurrentFighter(F currentFighter) {
/*  83 */     this.m_currentFighter = currentFighter;
/*  84 */     onCurrentFighterChange(currentFighter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void forceNextTimeEvent() {
/*  90 */     if (getFirstTimeEvent() != null)
/*     */     {
/*  92 */       nextTimeEvent();
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
/*     */   public boolean askForFighterStartTurn(BasicFighter fighter) {
/* 105 */     if (!isEmpty() && getFirstTimeEvent().getType() == 107) {
/* 106 */       FighterTurnTimeEvent<F> ftte = (FighterTurnTimeEvent<F>)getFirstTimeEvent();
/* 107 */       if (ftte.isActive() && ftte.getTurnBasedFighter() == fighter) {
/*     */         
/* 109 */         ftte.validate();
/* 110 */         nextTimeEvent();
/* 111 */         return true;
/*     */       } 
/*     */     } 
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean askForFighterEndTurn(BasicFighter fighter) {
/* 125 */     if (fighter == this.m_currentFighter && 
/* 126 */       !isEmpty() && getFirstTimeEvent().getType() == 107) {
/* 127 */       FighterTurnTimeEvent<F> ftte = (FighterTurnTimeEvent<F>)getFirstTimeEvent();
/* 128 */       if (!ftte.isActive() && ftte.getTurnBasedFighter() == this.m_currentFighter) {
/*     */         
/* 130 */         ftte.validate();
/* 131 */         nextTimeEvent();
/* 132 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean endFighterTurn(F fighter) {
/* 147 */     if (fighter == this.m_currentFighter) {
/* 148 */       setCurrentFighter((F)null);
/* 149 */       onTurnEnded(fighter);
/* 150 */       return true;
/*     */     } 
/* 152 */     m_logger.error("TIMELINE : fin de tour d'un joueur autre que celui en cours");
/* 153 */     return false;
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
/*     */   public boolean startFighterTurn(F fighter) {
/* 165 */     setCurrentFighter(fighter);
/* 166 */     onTurnStarted(fighter);
/* 167 */     this.m_currentTurn = (byte)(this.m_currentTurn + 1);
/* 168 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void newTableTurn() {
/* 173 */     this.m_currentTurn = 0;
/* 174 */     this.m_currentTableturn = (byte)(this.m_currentTableturn + 1);
/* 175 */     initFighterTurnForOneTableTurn();
/* 176 */     onNewTableTurn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initFighterTurnForOneTableTurn() {
/* 184 */     TurnBasedTimeUnit tu = new TurnBasedTimeUnit(this.m_currentTableturn, this.m_currentTurn);
/* 185 */     for (BasicFighter basicFighter : this.m_orderedFigthers) {
/* 186 */       tu.increment();
/* 187 */       pushFighterTimeEvent((F)basicFighter, tu);
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
/*     */   public void addFighterTimeEventAt(F fighter, int tableTurn, int turn) {
/* 219 */     TurnBasedTimeUnit tu = new TurnBasedTimeUnit(tableTurn, turn);
/* 220 */     for (TimeEvent<TurnBasedTimeUnit, TurnBasedTimeInterval> te : (Iterable<TimeEvent<TurnBasedTimeUnit, TurnBasedTimeInterval>>)this.m_timeline) {
/*     */       
/* 222 */       if (((TurnBasedTimeUnit)te.when()).compareTo(tu) > 0) {
/* 223 */         TurnBasedTimeInterval ti = TurnBasedTimeInterval.checkOut(0, 1);
/* 224 */         te.shiftStart(te.when(), ti);
/* 225 */         ti.release(); continue;
/* 226 */       }  if (((TurnBasedTimeUnit)te.when()).compareTo(tu) == 0)
/*     */       {
/*     */         
/* 229 */         if (te.getPriority() == 0) {
/* 230 */           TurnBasedTimeInterval ti = TurnBasedTimeInterval.checkOut(0, 1);
/* 231 */           te.shiftStart(te.when(), ti);
/* 232 */           ti.release();
/*     */         } 
/*     */       }
/*     */     } 
/* 236 */     pushFighterTimeEvent(fighter, tu);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFighter(F fighter) {
/* 246 */     if (this.m_orderedFigthers.remove(fighter)) {
/*     */       
/* 248 */       boolean decalage = false;
/*     */ 
/*     */       
/* 251 */       for (TimeEvent<TurnBasedTimeUnit, TurnBasedTimeInterval> te : (Iterable<TimeEvent<TurnBasedTimeUnit, TurnBasedTimeInterval>>)this.m_timeline) {
/* 252 */         if (!decalage && te.getType() == 107) {
/* 253 */           FighterTurnTimeEvent<F> fte = (FighterTurnTimeEvent)te;
/* 254 */           if (fte.getTurnBasedFighter() == fighter) {
/* 255 */             decalage = true;
/*     */           }
/*     */         } 
/* 258 */         if (decalage) {
/* 259 */           TurnBasedTimeInterval ti = TurnBasedTimeInterval.checkOut(0, -1);
/* 260 */           te.shiftStart(te.when(), ti);
/* 261 */           ti.release();
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 267 */       TimeEventRelatedToEffectUserFilter timeEventRelatedToEffectUserFilter = new TimeEventRelatedToEffectUserFilter((EffectUser)fighter, (fighter == getCurrentFighter()));
/* 268 */       for (TimeEvent te : filter((Filter)timeEventRelatedToEffectUserFilter)) {
/* 269 */         removeTimeEvent(te);
/*     */       }
/*     */     } 
/*     */     
/* 273 */     onFighterRemoved(fighter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TurnBasedTimeUnit now() {
/* 281 */     this.m_now.setTurnBasedTimeUnit(this.m_currentTableturn, this.m_currentTurn);
/* 282 */     return this.m_now;
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
/*     */   public void onTimeEventActivated(TimeEvent te) {
/*     */     FighterTurnTimeEvent<F> ftte;
/*     */     EffectAreaActivationTimeEvent eaate;
/* 302 */     switch (te.getType()) {
/*     */       case 107:
/* 304 */         ftte = (FighterTurnTimeEvent<F>)te;
/* 305 */         startFighterTurn((F)ftte.getTurnBasedFighter());
/* 306 */         super.onTimeEventActivated(te);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 106:
/* 311 */         newTableTurn();
/* 312 */         super.onTimeEventActivated(te);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 108:
/* 317 */         eaate = (EffectAreaActivationTimeEvent)te;
/* 318 */         eaate.getArea().setActive(eaate.getApplicant());
/* 319 */         super.onTimeEventActivated(te);
/*     */         break;
/*     */     } 
/*     */     
/* 323 */     super.onTimeEventActivated(te);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTimeEventDesactivated(TimeEvent te) {
/*     */     FighterTurnTimeEvent<F> ftte;
/* 333 */     switch (te.getType()) {
/*     */       case 107:
/* 335 */         ftte = (FighterTurnTimeEvent<F>)te;
/* 336 */         endFighterTurn((F)ftte.getTurnBasedFighter());
/* 337 */         super.onTimeEventDesactivated(te);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 106:
/* 342 */         super.onTimeEventDesactivated(te);
/*     */         return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 348 */     super.onTimeEventDesactivated(te);
/*     */   }
/*     */   
/*     */   public abstract void pushFighterTimeEvent(F paramF, TurnBasedTimeUnit paramTurnBasedTimeUnit);
/*     */   
/*     */   public abstract void addFighter(F paramF, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   public abstract void onTurnEnded(F paramF);
/*     */   
/*     */   public abstract void onTurnStarted(F paramF);
/*     */   
/*     */   public abstract void onNewTableTurn();
/*     */   
/*     */   public abstract void onFighterAdded(F paramF);
/*     */   
/*     */   public abstract void onFighterRemoved(F paramF);
/*     */   
/*     */   public abstract void onCurrentFighterChange(F paramF);
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\TurnBased\TurnBasedTimeline.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */