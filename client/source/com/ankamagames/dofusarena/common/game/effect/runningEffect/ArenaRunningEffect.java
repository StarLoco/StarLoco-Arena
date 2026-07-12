/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.AbstractEffectManager;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffectConfigFlags;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffectStatus;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.BasicEffectArea;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEventListener;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeUnit;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeUnit;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeline;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.RunningEffectDurationTimeEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.StaticRunningEffectDelayedTimeEvent;
/*     */ import com.ankamagames.dofusarena.common.game.effect.DefaultEffect;
/*     */ import com.ankamagames.dofusarena.common.game.effectArea.StaticEffectAreaManager;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightActionUniqueIDGenerator;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.framework.ai.targetfinder.Target;
/*     */ import com.ankamagames.framework.ai.targetfinder.TargetFinder;
/*     */ import com.ankamagames.framework.ai.targetfinder.TargetValidity;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ArenaRunningEffect
/*     */   extends RunningEffect
/*     */ {
/*  42 */   public static int SERIALIZED_DATA_LENGTH_WITHOUT_ID = 34;
/*  43 */   public static int SERIALIZED_DATA_LENGTH_WITH_ID = SERIALIZED_DATA_LENGTH_WITHOUT_ID + 4;
/*     */ 
/*     */ 
/*     */   
/*     */   private int m_uniqueId;
/*     */ 
/*     */ 
/*     */   
/*     */   private int m_triggeringEffectUniqueId;
/*     */ 
/*     */   
/*     */   private boolean m_notified = false;
/*     */ 
/*     */   
/*  57 */   protected final TurnBasedTimeUnit m_beginning = new TurnBasedTimeUnit(-1, -1);
/*  58 */   protected final TurnBasedTimeUnit m_end = new TurnBasedTimeUnit(-1, -1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setConfigFlags(EnumSet<RunningEffectConfigFlags> flags) {
/*  65 */     this.m_configFlags = flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<EffectUser> determineTargets(Effect genericEffect, Target applicant, EffectContext context, Point3 point3) {
/*  73 */     HashMap<Long, EffectUser> targets = new HashMap<Long, EffectUser>();
/*  74 */     for (EffectUser eu : TargetFinder.getInstance().getTargets(applicant, context.getTargetInformationProvider(), 
/*  75 */         genericEffect.getAreaOfEffect(), 
/*  76 */         point3, 
/*  77 */         genericEffect.getTargetValidator())) {
/*     */ 
/*     */       
/*  80 */       if (eu instanceof AbstractFighter && getEffectContainer() != null && (
/*  81 */         getEffectContainer().getContainerType() == 3 || 
/*  82 */         getEffectContainer().getContainerType() == 2 || 
/*  83 */         getEffectContainer().getContainerType() == 13 || 
/*  84 */         getEffectContainer().getContainerType() == 12)) {
/*     */         
/*  86 */         AbstractFighter fighter = (AbstractFighter)eu;
/*  87 */         boolean isSpecialCell = false;
/*     */         
/*  89 */         if (getEffectContainer().getContainerType() == 3 && 
/*  90 */           StaticEffectAreaManager.getInstance().getSpecialCell(((BasicEffectArea)getEffectContainer()).getBaseId()) != null) {
/*  91 */           isSpecialCell = true;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  97 */         if (fighter.isCarried()) {
/*     */           
/*  99 */           switch (genericEffect.getTargetValidator().getTargetValidity((Target)fighter.getCarriedByFighter(), (Target)fighter.getCarriedByFighter())) {
/*     */             case VALID:
/*     */             case VALID_IF_IN_AOE:
/* 102 */               targets.put(Long.valueOf(fighter.getCarriedByFighter().getId()), fighter.getCarriedByFighter());
/*     */               break;
/*     */           } 
/*     */ 
/*     */           
/* 107 */           if (!isSpecialCell && getRunningEffectStatus() == RunningEffectStatus.POSITIVE) {
/* 108 */             targets.put(Long.valueOf(fighter.getId()), fighter);
/*     */           }
/*     */           continue;
/*     */         } 
/* 112 */         if (fighter.isCarrying() && getRunningEffectStatus() == RunningEffectStatus.POSITIVE) {
/* 113 */           targets.put(Long.valueOf(fighter.getId()), fighter);
/* 114 */           if (!isSpecialCell) {
/* 115 */             switch (genericEffect.getTargetValidator().getTargetValidity((Target)fighter.getCarriedFighter(), applicant)) {
/*     */               case VALID:
/*     */               case VALID_IF_IN_AOE:
/* 118 */                 targets.put(Long.valueOf(fighter.getCarriedFighter().getId()), fighter.getCarriedFighter());
/*     */                 continue;
/*     */             } 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */           continue;
/*     */         } 
/* 127 */         targets.put(Long.valueOf(fighter.getId()), fighter);
/*     */         continue;
/*     */       } 
/* 130 */       targets.put(Long.valueOf(eu.getId()), eu);
/*     */     } 
/*     */     
/* 133 */     return targets.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckOut() {
/* 140 */     super.onCheckOut();
/* 141 */     this.m_uniqueId = FightActionUniqueIDGenerator.getNextID();
/* 142 */     this.m_triggeringEffectUniqueId = -1;
/* 143 */     this.m_value = 0;
/* 144 */     this.m_notified = false;
/*     */     
/* 146 */     if (this.m_beginning != null)
/* 147 */       this.m_beginning.setTurnBasedTimeUnit(-1, -1); 
/* 148 */     if (this.m_end != null) {
/* 149 */       this.m_end.setTurnBasedTimeUnit(-1, -1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckIn() {
/* 158 */     super.onCheckIn();
/* 159 */     this.m_value = 0;
/* 160 */     this.m_beginning.setTurnBasedTimeUnit(-1, -1);
/* 161 */     this.m_end.setTurnBasedTimeUnit(-1, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyExecution(RunningEffect triggerRE, boolean trigger) {
/* 168 */     this.m_notified = true;
/* 169 */     if (this.m_context != null && this.m_context.getEffectExecutionListener() != null && 
/* 170 */       !trigger) {
/* 171 */       this.m_context.getEffectExecutionListener().onEffectDirectExecution(this);
/*     */     }
/*     */ 
/*     */     
/* 175 */     if (triggerRE != null) {
/* 176 */       this.m_triggeringEffectUniqueId = ((ArenaRunningEffect)triggerRE).getUniqueId();
/*     */     } else {
/* 178 */       this.m_triggeringEffectUniqueId = -1;
/*     */     } 
/* 180 */     if (this.m_context != null && this.m_context.getEffectExecutionListener() != null && 
/* 181 */       trigger) {
/* 182 */       this.m_context.getEffectExecutionListener().onEffectTriggeredExecution(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(RunningEffect triggerRE, boolean trigger) {
/* 190 */     if (!this.m_notified)
/* 191 */       notifyExecution(triggerRE, trigger); 
/* 192 */     this.m_notified = false;
/* 193 */     super.execute(triggerRE, trigger);
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
/*     */   public void pushRunningEffectDelayedTimeEventInTimeline(Effect genericEffect, EffectContainer container, EffectContext context, EffectUser launcher, Point3 targetCell) {
/* 205 */     if (genericEffect != null && context.getTimeline() != null) {
/*     */       int startTurn, startTableTurn;
/*     */       
/* 208 */       TurnBasedTimeUnit tu = ((TurnBasedTimeline)this.m_context.getTimeline()).now();
/*     */       
/* 210 */       if (genericEffect.getApplyDelay() != null && (genericEffect.getApplyDelay()).length == 2) {
/* 211 */         startTableTurn = genericEffect.getApplyDelay()[0];
/* 212 */         startTurn = genericEffect.getApplyDelay()[1];
/*     */       } else {
/* 214 */         startTableTurn = tu.getTableTurn();
/* 215 */         startTurn = tu.getTurn();
/*     */       } 
/*     */       
/* 218 */       StaticRunningEffectDelayedTimeEvent te = StaticRunningEffectDelayedTimeEvent.checkOut(
/* 219 */           startTableTurn, startTurn, 
/* 220 */           (TimeEventListener)context.getTimeline(), (StaticRunningEffect)this, 
/* 221 */           genericEffect, container, context, launcher, targetCell);
/* 222 */       this.m_linkedTimeEventInTimeline = (TimeEvent)te;
/* 223 */       ((TurnBasedTimeline)this.m_context.getTimeline()).addTimeEvent((TimeEvent)te);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushRunningEffectDurationTimeEventInTimeline() {
/* 230 */     if (this.m_genericEffect != null && this.m_context.getTimeline() != null) {
/*     */       int tableTurnDuration, turnDuration;
/*     */       
/* 233 */       TurnBasedTimeUnit tu = ((TurnBasedTimeline)this.m_context.getTimeline()).now();
/*     */ 
/*     */       
/* 236 */       int startTableTurn = tu.getTableTurn();
/* 237 */       int startTurn = tu.getTurn();
/*     */       
/* 239 */       this.m_beginning.setTurnBasedTimeUnit(startTableTurn, startTurn);
/*     */       
/* 241 */       if (this.m_genericEffect.getDuration() != null && (this.m_genericEffect.getDuration()).length == 2) {
/* 242 */         tableTurnDuration = this.m_genericEffect.getDuration()[0];
/* 243 */         turnDuration = this.m_genericEffect.getDuration()[1];
/*     */       } else {
/* 245 */         m_logger.error("on veut pousser dans la timeline un runningEffect instantané");
/*     */         
/*     */         return;
/*     */       } 
/* 249 */       this.m_end.setTurnBasedTimeUnit(startTableTurn + tableTurnDuration, startTurn + turnDuration);
/*     */       
/* 251 */       RunningEffectDurationTimeEvent te = RunningEffectDurationTimeEvent.checkOut(
/* 252 */           startTableTurn, startTurn, 
/* 253 */           tableTurnDuration, turnDuration, 
/* 254 */           (TimeEventListener)this.m_context.getTimeline(), this);
/* 255 */       this.m_linkedTimeEventInTimeline = (TimeEvent)te;
/* 256 */       this.m_context.getTimeline().addTimeEvent((TimeEvent)te);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public TurnBasedTimeUnit getStartTime() {
/* 262 */     if (this.m_beginning != null && this.m_beginning.isValid())
/* 263 */       return this.m_beginning; 
/* 264 */     return null;
/*     */   }
/*     */   
/*     */   public TurnBasedTimeUnit getEndTime() {
/* 268 */     if (this.m_end != null && this.m_end.isValid())
/* 269 */       return this.m_end; 
/* 270 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasDuration() {
/* 274 */     if (this.m_genericEffect != null && 
/* 275 */       this.m_genericEffect.getDuration() != null && (
/* 276 */       this.m_genericEffect.getDuration()).length == 2 && (
/* 277 */       this.m_genericEffect.getDuration()[0] > 0 || this.m_genericEffect.getDuration()[1] > 0)) return true; 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public boolean isInfinite() {
/* 282 */     if (this.m_genericEffect != null && 
/* 283 */       this.m_genericEffect.getDuration() != null && (
/* 284 */       this.m_genericEffect.getDuration()).length == 2 && (
/* 285 */       this.m_genericEffect.getDuration()[0] == 63 || this.m_genericEffect.getDuration()[1] == 63)) return true; 
/*     */     return false;
/*     */   }
/*     */   public boolean hasApplyDelay() {
/* 289 */     if (this.m_genericEffect != null && 
/* 290 */       this.m_genericEffect.getApplyDelay() != null && (
/* 291 */       this.m_genericEffect.getApplyDelay()).length == 2 && (
/* 292 */       this.m_genericEffect.getApplyDelay()[0] > 0 || this.m_genericEffect.getApplyDelay()[1] > 0)) return true;
/*     */     
/*     */     return false;
/*     */   }
/*     */   
/*     */   public int getUniqueId() {
/* 298 */     return this.m_uniqueId;
/*     */   }
/*     */   
/*     */   public int getTriggeringEffectUniqueId() {
/* 302 */     return this.m_triggeringEffectUniqueId;
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
/*     */   public void serialize(ByteBuffer buff) {
/* 315 */     buff.putInt(getId());
/* 316 */     buff.putInt((this.m_genericEffect == null) ? DefaultEffect.getInstance().getEffectId() : this.m_genericEffect.getEffectId());
/* 317 */     serializeCaster(buff);
/* 318 */     serializeTarget(buff);
/* 319 */     if (this.m_targetCell == null) {
/* 320 */       buff.putInt(0);
/* 321 */       buff.putInt(0);
/* 322 */       buff.putShort((short)0);
/*     */     } else {
/* 324 */       buff.putInt(this.m_targetCell.getX());
/* 325 */       buff.putInt(this.m_targetCell.getY());
/* 326 */       buff.putShort(this.m_targetCell.getZ());
/*     */     } 
/* 328 */     buff.putInt(this.m_value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void serializeCaster(ByteBuffer buff) {
/* 334 */     buff.putLong((this.m_caster != null) ? this.m_caster.getId() : 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void serializeTarget(ByteBuffer buff) {
/* 340 */     buff.putLong((this.m_target != null) ? this.m_target.getId() : 0L);
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
/*     */   public boolean unserialize(ByteBuffer buff, EffectContext context, AbstractEffectManager manager) {
/* 360 */     int genericEffectId = buff.getInt();
/* 361 */     this.m_genericEffect = manager.getEffect(genericEffectId);
/*     */     
/* 363 */     if (this.m_genericEffect == null) {
/* 364 */       m_logger.error("Impossible de désérialiser un ArenaRunningEffect : generic effet inconnu : " + genericEffectId);
/*     */     }
/*     */     
/* 367 */     this.m_context = context;
/*     */     
/* 369 */     if (!unserializeCaster(buff.getLong(), manager))
/* 370 */       return false; 
/* 371 */     if (!unserializeTarget(buff.getLong(), manager))
/* 372 */       return false; 
/* 373 */     this.m_targetCell = new Point3(buff.getInt(), buff.getInt(), buff.getShort());
/* 374 */     this.m_value = buff.getInt();
/* 375 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean unserializeTarget(long targetId, AbstractEffectManager manager) {
/* 382 */     if (targetId == 0L) {
/* 383 */       this.m_target = null;
/*     */     } else {
/* 385 */       this.m_target = this.m_context.getEffectUserInformationProvider().getEffectUserFromId(targetId);
/* 386 */       if (this.m_target == null) {
/* 387 */         m_logger.error("Impossible de désérialiser un ArenaRunningEffect : target inconnue : " + targetId);
/* 388 */         return false;
/*     */       } 
/*     */     } 
/* 391 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean unserializeCaster(long casterId, AbstractEffectManager manager) {
/* 398 */     if (casterId == 0L) {
/* 399 */       this.m_caster = null;
/*     */     } else {
/* 401 */       this.m_caster = this.m_context.getEffectUserInformationProvider().getEffectUserFromId(casterId);
/* 402 */       if (this.m_caster == null) {
/* 403 */         m_logger.error("Impossible de désérialiser un ArenaRunningEffect : caster inconnu : " + casterId);
/*     */       }
/*     */     } 
/* 406 */     return true;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\ArenaRunningEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */