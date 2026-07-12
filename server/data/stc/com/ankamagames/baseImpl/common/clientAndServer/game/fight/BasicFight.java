/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.fight;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUserInformationProvider;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffectManager;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.BasicEffectArea;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.EffectAreaActionListener;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.EffectAreaManager;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.BasicTimeline;
/*     */ import com.ankamagames.framework.ai.LOS.LineOfSightObstacle;
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import com.ankamagames.framework.ai.dataProvider.LineOfSightObstacleInformationProvider;
/*     */ import com.ankamagames.framework.ai.dataProvider.MovementObstacleInformationProvider;
/*     */ import com.ankamagames.framework.ai.dataProvider.TargetInformationProvider;
/*     */ import com.ankamagames.framework.ai.pathfinder.MovementObstacle;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.iterators.MergedIterator;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.pool.ObjectPool;
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
/*     */ public abstract class BasicFight<F extends BasicFighter>
/*     */   implements Poolable, LineOfSightObstacleInformationProvider, MovementObstacleInformationProvider, EffectUserInformationProvider, TargetInformationProvider<F>, EffectAreaActionListener, FighterSpecialEventListener
/*     */ {
/*  44 */   protected static final Logger m_logger = Logger.getLogger(BasicFight.class);
/*     */   
/*     */ 
/*     */   private boolean m_createdAndInitialized;
/*     */   
/*     */   protected byte m_minTeam;
/*     */   
/*     */   protected byte m_maxTeam;
/*     */   
/*     */   protected byte m_minFighterByTeam;
/*     */   
/*     */   protected byte m_maxFighterByTeam;
/*     */   
/*     */   protected List<FightingTeam<F>> m_teams;
/*     */   
/*     */   private HashMap<Long, F> m_currentFighters;
/*     */   
/*  61 */   private final ArrayList<F> m_fightersBoundToThis = new ArrayList();
/*     */   
/*     */   protected BasicTimeline m_timeline;
/*     */   
/*     */   protected CellInformationProvider m_cellInformationProvider;
/*     */   
/*     */   protected EffectContext m_context;
/*     */   
/*     */   protected EffectAreaManager m_effectAreaManager;
/*     */   
/*     */   protected ObjectPool m_pool;
/*     */   
/*     */   private int m_id;
/*     */   protected byte m_typeId;
/*  75 */   private long m_nextSummoningId = -1L;
/*     */   
/*     */   public long getNextFreeEffectUserId() {
/*  78 */     this.m_nextSummoningId -= 1L;
/*  79 */     if (this.m_nextSummoningId > 0L)
/*  80 */       this.m_nextSummoningId = -1L;
/*  81 */     return this.m_nextSummoningId;
/*     */   }
/*     */   
/*     */   public abstract BasicFight newParameterizedInstance(CellInformationProvider paramCellInformationProvider, Map paramMap);
/*     */   
/*     */   public void onCheckOut()
/*     */   {
/*  88 */     this.m_id = -1;
/*  89 */     this.m_nextSummoningId = -1L;
/*  90 */     this.m_effectAreaManager = null;
/*  91 */     this.m_timeline = null;
/*  92 */     this.m_context = null;
/*  93 */     this.m_cellInformationProvider = null;
/*  94 */     this.m_effectAreaManager = EffectAreaManager.checkOut(this);
/*  95 */     this.m_currentFighters = new HashMap();
/*  96 */     this.m_teams = new Vector();
/*  97 */     this.m_createdAndInitialized = false;
/*  98 */     this.m_fightersBoundToThis.clear();
/*     */   }
/*     */   
/*     */   public void onCheckIn() {
/* 102 */     this.m_id = -1;
/* 103 */     this.m_nextSummoningId = -1L;
/* 104 */     this.m_typeId = 0;
/* 105 */     this.m_minTeam = 0;
/* 106 */     this.m_maxTeam = 0;
/* 107 */     this.m_minFighterByTeam = 0;
/* 108 */     this.m_maxFighterByTeam = 0;
/*     */     
/* 110 */     if (this.m_effectAreaManager != null) {
/* 111 */       this.m_effectAreaManager.release();
/* 112 */       this.m_effectAreaManager = null;
/*     */     }
/* 114 */     if ((this.m_teams != null) && (this.m_teams.size() > 0)) {
/* 115 */       this.m_teams.clear();
/* 116 */       this.m_teams = null;
/*     */     }
/* 118 */     this.m_currentFighters.clear();
/* 119 */     this.m_currentFighters = null;
/*     */     
/* 121 */     if (this.m_timeline != null)
/* 122 */       this.m_timeline.release();
/* 123 */     this.m_timeline = null;
/* 124 */     if (this.m_context != null)
/* 125 */       this.m_context.release();
/* 126 */     this.m_context = null;
/*     */     
/* 128 */     this.m_cellInformationProvider = null;
/* 129 */     this.m_createdAndInitialized = false;
/* 130 */     for (F figther : this.m_fightersBoundToThis) {
/* 131 */       figther.release();
/*     */     }
/* 133 */     this.m_fightersBoundToThis.clear();
/*     */   }
/*     */   
/*     */   public void release() {
/* 137 */     if (this.m_pool != null) {
/*     */       try {
/* 139 */         this.m_pool.returnObject(this);
/*     */       } catch (Exception e) {
/* 141 */         m_logger.error("Exception dans le release de " + getClass().toString() + " normalement impossible");
/*     */       }
/* 143 */       this.m_pool = null;
/*     */     } else {
/* 145 */       onCheckIn();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void setId(int id)
/*     */   {
/* 152 */     this.m_id = id;
/*     */   }
/*     */   
/*     */   public int getId() {
/* 156 */     return this.m_id;
/*     */   }
/*     */   
/*     */   public CellInformationProvider getCellInformationProvider()
/*     */   {
/* 161 */     return this.m_cellInformationProvider;
/*     */   }
/*     */   
/*     */   public byte getTypeId() {
/* 165 */     return this.m_typeId;
/*     */   }
/*     */   
/*     */   public BasicTimeline getTimeline() {
/* 169 */     return this.m_timeline;
/*     */   }
/*     */   
/*     */   public void setTimeline(BasicTimeline timeline) {
/* 173 */     this.m_timeline = timeline;
/*     */   }
/*     */   
/*     */   public EffectAreaManager getEffectAreaManager() {
/* 177 */     return this.m_effectAreaManager;
/*     */   }
/*     */   
/*     */   public int getTeamsCount() {
/* 181 */     return this.m_teams.size();
/*     */   }
/*     */   
/*     */   public Iterable<FightingTeam<F>> getTeams() {
/* 185 */     return this.m_teams;
/*     */   }
/*     */   
/*     */   public int getFightersCount() {
/* 189 */     return this.m_currentFighters.size();
/*     */   }
/*     */   
/*     */   public Iterable<F> getCurrentFighters() {
/* 193 */     if (this.m_currentFighters != null) {
/* 194 */       return (Iterable)this.m_currentFighters.values();
/*     */     }
/* 196 */     return null;
/*     */   }
/*     */   
/*     */   public void setCellInformationProvider(CellInformationProvider cellInformationProvider)
/*     */   {
/* 201 */     this.m_cellInformationProvider = cellInformationProvider;
/*     */   }
/*     */   
/*     */   public F getFighterById(long fighterId) {
/* 205 */     return (BasicFighter)this.m_currentFighters.get(Long.valueOf(fighterId));
/*     */   }
/*     */   
/*     */   public EffectContext getContext() {
/* 209 */     return this.m_context;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void setParam(Map paramMap);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canInitializeFightWithTheseOpponents(List<? extends FightingTeam> opponents)
/*     */   {
/* 224 */     if (opponents == null) return false;
/* 225 */     if ((opponents.size() < this.m_minTeam) || (opponents.size() > this.m_maxTeam)) return false;
/* 226 */     label80: for (Iterator localIterator = opponents.iterator(); localIterator.hasNext(); 
/*     */         
/* 228 */         return false)
/*     */     {
/* 226 */       FightingTeam team = (FightingTeam)localIterator.next();
/* 227 */       int count = team.getFightersCount();
/* 228 */       if ((count >= this.m_minFighterByTeam) && (count <= this.m_maxFighterByTeam)) break label80;
/*     */     }
/* 230 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract <T extends FightingTeam<F>> T newTeam();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean addTeam(FightingTeam<F> team)
/*     */   {
/* 251 */     if (this.m_teams.size() < this.m_maxTeam)
/*     */     {
/*     */ 
/* 254 */       team.setId((byte)this.m_teams.size());
/*     */       
/* 256 */       if (!this.m_teams.contains(team)) {
/* 257 */         this.m_teams.add(team);
/*     */       } else {
/* 259 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 263 */       for (TeamMate<F> teammate : team.getTeamMates()) {
/* 264 */         if (!addTeamMate(teammate)) { return false;
/*     */         }
/*     */       }
/* 267 */       return true;
/*     */     }
/* 269 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean addTeamMate(TeamMate<F> teamMate)
/*     */   {
/* 279 */     if (teamMate == null) { return false;
/*     */     }
/*     */     
/* 282 */     if ((teamMate.getFightersCount() > this.m_maxFighterByTeam) || (teamMate.getFightersCount() < this.m_minFighterByTeam)) {
/* 283 */       return false;
/*     */     }
/*     */     
/* 286 */     for (F fighter : teamMate.getFighters()) {
/* 287 */       addFighter(fighter, false);
/*     */     }
/*     */     
/* 290 */     onTeamMateJoinTeam(teamMate);
/*     */     
/* 292 */     return true;
/*     */   }
/*     */   
/*     */   public void addFighter(F fighter, boolean liveOnlyThisFight)
/*     */   {
/* 297 */     this.m_currentFighters.put(Long.valueOf(fighter.getId()), fighter);
/* 298 */     if (liveOnlyThisFight) {
/* 299 */       this.m_fightersBoundToThis.add(fighter);
/*     */     }
/* 301 */     onFighterJoinFight(fighter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean removeTeamMate(TeamMate<F> teamMate)
/*     */   {
/* 312 */     if (teamMate == null) { return false;
/*     */     }
/*     */     
/* 315 */     for (F fighter : teamMate.getFighters()) {
/* 316 */       killFighter(fighter);
/*     */     }
/*     */     
/*     */ 
/* 320 */     if (!checkFightEnd()) {
/* 321 */       onTeamMateRemovedFromFight(teamMate);
/* 322 */       return true;
/*     */     }
/*     */     
/* 325 */     return false;
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
/*     */   public void killFighter(F fighter)
/*     */   {
/* 338 */     if (fighter == null) {
/* 339 */       throw new IllegalArgumentException("appel de killFighter avec fighter = nulll !!!!!");
/*     */     }
/* 341 */     TeamMate teamMate = fighter.getTeamMate();
/* 342 */     if (teamMate == null) {
/* 343 */       throw new UnsupportedOperationException("Ce fighter n'a pas de teammate");
/*     */     }
/* 345 */     onFighterDeath(fighter);
/* 346 */     removeFighter(fighter);
/* 347 */     FightingTeam<F> team = teamMate.getTeam();
/* 348 */     if (team != null) {
/* 349 */       for (F otherfighter : this.m_currentFighters.values()) {
/* 350 */         if (otherfighter.getTeamMate().getTeam() == team) { return;
/*     */         }
/*     */       }
/*     */       
/* 354 */       onTeamLose(team);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeFighter(F fighter)
/*     */   {
/* 366 */     if (fighter != null) {
/* 367 */       this.m_currentFighters.remove(Long.valueOf(fighter.getId()));
/* 368 */       onFighterRemovedFromFight(fighter);
/*     */     }
/*     */     else
/*     */     {
/* 372 */       m_logger.error("on veut retirer un fighter null de la partie");
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isTeamFighter(F fighter) { Iterator<F> it;
/* 377 */     for (Iterator localIterator = this.m_teams.iterator(); localIterator.hasNext(); 
/* 378 */         it.hasNext())
/*     */     {
/* 377 */       FightingTeam<F> team = (FightingTeam)localIterator.next();
/* 378 */       it = team.getFighterIterator(); continue;
/* 379 */       if (it.next() == fighter) {
/* 380 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 384 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean checkFightEnd()
/*     */   {
/* 394 */     if (!this.m_createdAndInitialized) { return false;
/*     */     }
/* 396 */     HashMap<Byte, FightingTeam<F>> teamsLeft = new HashMap();
/* 397 */     for (F fighter : this.m_currentFighters.values()) {
/* 398 */       if (fighter.getTeamMate() != null) {
/* 399 */         FightingTeam<F> team = fighter.getTeamMate().getTeam();
/* 400 */         if ((team != null) && (!teamsLeft.containsKey(Byte.valueOf(team.getId())))) {
/* 401 */           teamsLeft.put(Byte.valueOf(team.getId()), team);
/* 402 */           if (teamsLeft.size() >= this.m_minTeam) { return false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 408 */     for (FightingTeam<F> team : teamsLeft.values()) {
/* 409 */       onTeamWin(team);
/*     */     }
/*     */     
/* 412 */     endFight();
/*     */     
/* 414 */     return true;
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
/*     */   public boolean AreOpponent(BasicFighter f1, BasicFighter f2)
/*     */   {
/* 427 */     return (f1 != null) && (f1.getTeamMate() != null) && (f1.getTeamMate().getTeam() != null) && (f2 != null) && (f2.getTeamMate() != null) && (f2.getTeamMate().getTeam() != null) && (f1.getTeamMate().getTeam() != f2.getTeamMate().getTeam());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void addFighterDuringFight(F paramF);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void endFight()
/*     */   {
/* 447 */     getTimeline().stop();
/* 448 */     getTimeline().reset();
/*     */     
/*     */ 
/* 451 */     for (F f : this.m_currentFighters.values()) {
/* 452 */       onFighterRemovedFromFight(f);
/*     */     }
/* 454 */     this.m_currentFighters.clear();
/*     */     
/* 456 */     this.m_effectAreaManager.destroyAll();
/*     */     Iterator localIterator2;
/* 458 */     for (??? = this.m_teams.iterator(); ???.hasNext(); 
/* 459 */         localIterator2.hasNext())
/*     */     {
/* 458 */       FightingTeam<?> team = (FightingTeam)???.next();
/* 459 */       localIterator2 = team.getTeamMates().iterator(); continue;TeamMate<?> teammate = (TeamMate)localIterator2.next();
/* 460 */       teammate.onFightEnd();
/*     */     }
/*     */     
/* 463 */     this.m_teams.clear();
/*     */     
/* 465 */     onFightEnded();
/*     */     
/*     */ 
/* 468 */     FightManager.getInstance().destroyFight(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<? extends EffectUser> getEffectUsers()
/*     */   {
/* 479 */     return new MergedIterator(new Iterator[] { this.m_currentFighters.values().iterator(), this.m_effectAreaManager.iterator() });
/*     */   }
/*     */   
/*     */   public EffectUser getEffectUserFromId(long effectUserId) {
/* 483 */     EffectUser user = (EffectUser)this.m_currentFighters.get(Long.valueOf(effectUserId));
/* 484 */     if (user != null)
/* 485 */       return user;
/* 486 */     for (EffectUser area : this.m_effectAreaManager.getActiveEffectAreas())
/* 487 */       if (area.getId() == effectUserId)
/* 488 */         return area;
/* 489 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<? extends LineOfSightObstacle> getLineOfSightObstacles()
/*     */   {
/* 498 */     return this.m_currentFighters.values().iterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MovementObstacle getMovementObstacle(int x, int y, int z)
/*     */   {
/* 507 */     for (BasicFighter fighter : this.m_currentFighters.values()) {
/* 508 */       if ((fighter.getPosition().getX() == x) && (fighter.getPosition().getY() == y)) {
/* 509 */         int bottom = fighter.getPosition().getZ();
/* 510 */         int top = fighter.getHeight() + bottom;
/* 511 */         if ((z >= bottom) && (z <= top))
/* 512 */           return fighter;
/*     */       }
/*     */     }
/* 515 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<F> getPossibleTargets()
/*     */   {
/* 524 */     return this.m_currentFighters.values().iterator();
/*     */   }
/*     */   
/*     */ 
/*     */   public void onTeamMateJoinTeam(TeamMate<F> teamMate)
/*     */   {
/* 530 */     teamMate.onTeamMateJoinFight(this);
/*     */   }
/*     */   
/*     */   public void onTeamMateRemovedFromFight(TeamMate<F> teamMate) {
/* 534 */     teamMate.onFightEnd();
/*     */   }
/*     */   
/*     */   public void onFighterJoinFight(F f)
/*     */   {
/* 539 */     f.onJoinFight(this);
/* 540 */     ArrayList<BasicEffectArea> areasIn = new ArrayList();
/* 541 */     for (BasicEffectArea area : this.m_effectAreaManager) {
/* 542 */       if (area.contains(f.getPosition()))
/* 543 */         areasIn.add(area);
/*     */     }
/* 545 */     if (!areasIn.isEmpty()) {
/* 546 */       for (BasicEffectArea area : areasIn) {
/* 547 */         area.triggers(1, f);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void onFighterDeath(F f)
/*     */   {
/* 554 */     if (getEffectAreaManager() != null)
/* 555 */       getEffectAreaManager().removeEffectAreaOwnedByEffectUser(f);
/* 556 */     f.onDeath();
/*     */   }
/*     */   
/*     */   public void onFighterRemovedFromFight(F f) {
/* 560 */     f.onRemovedFromFight();
/*     */   }
/*     */   
/*     */   public void onEffectUserSpellCast(EffectUser f, long targetId, int spellId) {
/* 564 */     f.onEffectUsed();
/*     */   }
/*     */   
/*     */   public void onFighterUnableToFight(BasicFighter f) {
/* 568 */     f.onNowUnableToFight();
/*     */   }
/*     */   
/*     */   public void onFighterAbleToFight(BasicFighter f) {
/* 572 */     f.onNowAbleToFight();
/*     */   }
/*     */   
/*     */ 
/*     */   public void onFighterMove(F f, List<int[]> path) {}
/*     */   
/*     */   public void onFightCreatedAndInitialized()
/*     */   {
/* 580 */     this.m_createdAndInitialized = true;
/*     */   }
/*     */   
/*     */   public void onFightEnded()
/*     */   {
/* 585 */     m_logger.info("fin de combat");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onTeamWin(FightingTeam<F> winner) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void onTeamLose(FightingTeam<F> looser) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void onEffectAreaAdded(BasicEffectArea area) {}
/*     */   
/*     */ 
/*     */   public void onEffectAreaRemoved(BasicEffectArea area)
/*     */   {
/* 603 */     ArrayList<EffectUser> users = new ArrayList();
/* 604 */     EffectUser user; for (Iterator<? extends EffectUser> it = getEffectUsers(); it.hasNext();) {
/* 605 */       user = (EffectUser)it.next();
/*     */       
/* 607 */       if (user.getRunningEffectManager() != null)
/* 608 */         users.add(user);
/*     */     }
/* 610 */     for (EffectUser user : users) {
/* 611 */       user.getRunningEffectManager().removeLinkedToCaster(area);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFighterSpecialEvent(BasicFighter fighter, int specialEventId) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onEffectAreaExecuted(BasicEffectArea area)
/*     */   {
/* 627 */     if (!checkFightEnd())
/*     */     {
/* 629 */       if (area.shouldBeDead()) {
/* 630 */         this.m_effectAreaManager.removeEffectArea(area);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\fight\BasicFight.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */