/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.fight;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.iterators.MergedIterator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FightingTeam<F extends BasicFighter>
/*     */   implements Poolable
/*     */ {
/*  15 */   protected static final Logger m_logger = Logger.getLogger(FightingTeam.class);
/*     */   
/*     */   protected ObjectPool m_pool;
/*     */   
/*     */   protected byte m_id;
/*     */   
/*  21 */   private HashMap<Long, TeamMate<F>> m_teamMates = new HashMap<Long, TeamMate<F>>();
/*     */   
/*  23 */   private long m_leaderId = -1L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/*  29 */     if (this.m_pool != null) {
/*     */       try {
/*  31 */         this.m_pool.returnObject(this);
/*  32 */         this.m_pool = null;
/*  33 */       } catch (Exception e) {
/*  34 */         m_logger.error("ne peut arriver normalement");
/*     */       } 
/*     */     } else {
/*  37 */       onCheckIn();
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getLeaderId() {
/*  42 */     return this.m_leaderId;
/*     */   }
/*     */   
/*     */   public void setLeader(long leaderId) {
/*  46 */     this.m_leaderId = leaderId;
/*     */   }
/*     */   
/*     */   public void onCheckOut() {
/*  50 */     this.m_id = -1;
/*  51 */     this.m_teamMates.clear();
/*  52 */     this.m_leaderId = -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckIn() {
/*  58 */     this.m_id = -1;
/*  59 */     this.m_teamMates.clear();
/*  60 */     this.m_leaderId = -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  65 */     return this.m_id;
/*     */   }
/*     */   
/*     */   public void setId(byte id) {
/*  69 */     this.m_id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addTeamMate(TeamMate<F> teamMate) {
/*  79 */     if (!this.m_teamMates.containsKey(Long.valueOf(teamMate.getTeamMateId()))) {
/*     */       
/*  81 */       this.m_teamMates.put(Long.valueOf(teamMate.getTeamMateId()), teamMate);
/*  82 */       teamMate.setTeam(this);
/*  83 */       if (this.m_leaderId == -1L) {
/*  84 */         this.m_leaderId = teamMate.getTeamMateId();
/*     */       }
/*  86 */       return true;
/*     */     } 
/*  88 */     teamMate.setTeam(this);
/*  89 */     return false;
/*     */   }
/*     */   
/*     */   public void removeTeamMate(long teamMateId) {
/*  93 */     if (this.m_teamMates.containsKey(Long.valueOf(teamMateId))) {
/*  94 */       ((TeamMate)this.m_teamMates.get(Long.valueOf(teamMateId))).setTeam(null);
/*  95 */       this.m_teamMates.remove(Long.valueOf(teamMateId));
/*     */     } 
/*     */   }
/*     */   
/*     */   public TeamMate<F> getTeamMateById(long teamMateId) {
/* 100 */     return this.m_teamMates.get(Long.valueOf(teamMateId));
/*     */   }
/*     */   
/*     */   public int getTeamMatesCount() {
/* 104 */     return this.m_teamMates.size();
/*     */   }
/*     */   
/*     */   public Iterable<TeamMate<F>> getTeamMates() {
/* 108 */     return this.m_teamMates.values();
/*     */   }
/*     */   
/*     */   public int getFightersCount() {
/* 112 */     int count = 0;
/* 113 */     for (TeamMate<F> teammate : this.m_teamMates.values()) {
/* 114 */       count += teammate.getFightersCount();
/*     */     }
/* 116 */     return count;
/*     */   }
/*     */   
/*     */   public Iterator<F> getFighterIterator() {
/* 120 */     MergedIterator<F> mergedIterator = new MergedIterator();
/* 121 */     for (TeamMate<F> teammate : this.m_teamMates.values()) {
/* 122 */       mergedIterator.merge(teammate.getFighters().iterator());
/*     */     }
/* 124 */     return (Iterator<F>)mergedIterator;
/*     */   }
/*     */   
/*     */   public void onTeamLeftFight() {
/* 128 */     for (TeamMate<F> teammate : getTeamMates())
/* 129 */       teammate.onFightEnd(); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\fight\FightingTeam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */