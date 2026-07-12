/*     */ package com.ankamagames.dofusarena.client.core.game.fight;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEventListener;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeUnit;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.FighterTurnTimeEvent;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.common.game.fight.AbstractFight;
/*     */ import com.ankamagames.dofusarena.common.game.time.AbstractFightTimeline;
/*     */ import com.ankamagames.dofusarena.common.game.time.timeEvent.FightClockedPeriodTimeEvent;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Timeline
/*     */   extends AbstractFightTimeline<Fighter>
/*     */   implements FieldProvider
/*     */ {
/*     */   public static final String FIGHTERS_FIELD = "fighters";
/*     */   public static final String CURRENT_TABLE_TURN_FIELD = "currentTableTurn";
/*  28 */   public static final String[] FIELDS = new String[] {
/*  29 */       "fighters", 
/*  30 */       "currentTableTurn"
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timeline(TimeEventListener listener) {
/*  40 */     setGlobalListener(listener);
/*  41 */     onCheckOut();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushFighterTimeEvent(Fighter fighter, TurnBasedTimeUnit timeLocation) {
/*  51 */     FighterTurnTimeEvent<Fighter> timeEvent = FighterTurnTimeEvent.checkOut(timeLocation.getTableTurn(), timeLocation.getTurn(), (TimeEventListener)this, (BasicFighter)fighter);
/*     */ 
/*     */     
/*  54 */     timeEvent.unvalidate();
/*  55 */     addTimeEvent((TimeEvent)timeEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushClockedTimeEvent(int priority, AbstractFight.FightStatus status) {
/*  65 */     FightClockedPeriodTimeEvent timeEvent = FightClockedPeriodTimeEvent.checkOut(getCurrentTableturn(), getCurrentTurn(), priority, status, (TimeEventListener)this);
/*  66 */     timeEvent.unvalidate();
/*  67 */     addTimeEvent((TimeEvent)timeEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTurnEnded(Fighter fighter) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTurnStarted(Fighter fighter) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNewTableTurn() {
/*  93 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged("fight.timeline", "currentTableTurn");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFighterAdded(Fighter fighterToAdd) {
/* 103 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged("fight.timeline", "fighters");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFighterRemoved(Fighter fighter) {
/* 112 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged("fight.timeline", "fighters");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCurrentFighterChange(Fighter fighter) {
/* 121 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.timeline.currentFighter", fighter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendFieldValue(String fieldName, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getFieldValue(String fieldName) {
/* 139 */     if (fieldName.equals("fighters")) {
/* 140 */       return this.m_orderedFigthers.toArray();
/*     */     }
/* 142 */     if (fieldName.equals("currentTableTurn")) {
/* 143 */       return "Tour " + getCurrentTableturn();
/*     */     }
/* 145 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getFields() {
/* 154 */     return FIELDS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFieldSynchronisable(String fieldName) {
/* 163 */     return false;
/*     */   }
/*     */   
/*     */   public void prependFieldValue(String fieldName, Object value) {}
/*     */   
/*     */   public void setFieldValue(String fieldName, Object value) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fight\Timeline.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */