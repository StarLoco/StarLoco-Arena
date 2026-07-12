/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.effect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.framework.ai.LOS.LineOfSightObstacle;
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import com.ankamagames.framework.ai.dataProvider.LineOfSightObstacleInformationProvider;
/*     */ import com.ankamagames.framework.ai.dataProvider.MovementObstacleInformationProvider;
/*     */ import com.ankamagames.framework.ai.dataProvider.TargetInformationProvider;
/*     */ import com.ankamagames.framework.ai.pathfinder.MovementObstacle;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindCell;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.iterators.EmptyIterator;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class EffectContextForUniqueEffectUser
/*     */   extends EffectContext
/*     */   implements CellInformationProvider, LineOfSightObstacleInformationProvider, EffectUserInformationProvider, EffectExecutionListener, TargetInformationProvider<EffectUser>, MovementObstacleInformationProvider
/*     */ {
/*     */   private EffectUser m_effectUser;
/*  33 */   private List<EffectUser> m_effectUserList = new ArrayList(1);
/*  34 */   private long m_nextSummoningId = -1L;
/*     */   
/*     */   public EffectContextForUniqueEffectUser(EffectUser effectUser) {
/*  37 */     this.m_effectUser = effectUser;
/*  38 */     this.m_effectUserList.add(effectUser);
/*     */   }
/*     */   
/*     */   public EffectUserInformationProvider getEffectUserInformationProvider()
/*     */   {
/*  43 */     return this;
/*     */   }
/*     */   
/*     */   public CellInformationProvider getCellInformationProvider() {
/*  47 */     return this;
/*     */   }
/*     */   
/*     */   public LineOfSightObstacleInformationProvider getObstacleInformationProvider() {
/*  51 */     return this;
/*     */   }
/*     */   
/*     */   public TargetInformationProvider<? extends EffectUser> getTargetInformationProvider() {
/*  55 */     return this;
/*     */   }
/*     */   
/*     */   public MovementObstacleInformationProvider getMovementObstacleInformationProvider()
/*     */   {
/*  60 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getCellValidity(int x, int y, short z)
/*     */   {
/*  72 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PathFindCell getPathFindCell(int x, int y, short z)
/*     */   {
/*  84 */     return null;
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
/*     */   public boolean getLineOfSightValidity(int x, int y, short z, Direction8 direction)
/*     */   {
/*  97 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getLineOfSightEndValidity(int x, int y, short z)
/*     */   {
/* 109 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<? extends LineOfSightObstacle> getLineOfSightObstacles()
/*     */   {
/* 118 */     return new EmptyIterator();
/*     */   }
/*     */   
/*     */   public MovementObstacle getMovementObstacle(int x, int y, int z)
/*     */   {
/* 123 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<? extends EffectUser> getEffectUsers()
/*     */   {
/* 132 */     return this.m_effectUserList.iterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EffectUser getEffectUserFromId(long effectId)
/*     */   {
/* 141 */     if (effectId == this.m_effectUser.getId())
/* 142 */       return this.m_effectUser;
/* 143 */     return null;
/*     */   }
/*     */   
/*     */   public long getNextFreeEffectUserId()
/*     */   {
/* 148 */     this.m_nextSummoningId -= 1L;
/* 149 */     if (this.m_nextSummoningId > 0L)
/* 150 */       this.m_nextSummoningId = -1L;
/* 151 */     return this.m_nextSummoningId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onEffectDirectExecution(RunningEffect effect) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void onEffectTriggeredExecution(RunningEffect effect) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public Iterator<EffectUser> getPossibleTargets()
/*     */   {
/* 166 */     return this.m_effectUserList.iterator();
/*     */   }
/*     */   
/*     */   public byte getType() {
/* 170 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\EffectContextForUniqueEffectUser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */