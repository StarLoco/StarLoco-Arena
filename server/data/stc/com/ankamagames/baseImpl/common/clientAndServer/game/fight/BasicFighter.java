package com.ankamagames.baseImpl.common.clientAndServer.game.fight;

import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
import com.ankamagames.framework.ai.LOS.LineOfSightObstacle;
import com.ankamagames.framework.ai.pathfinder.MovementObstacle;
import com.ankamagames.framework.ai.pathfinder.PathFindMover;
import com.ankamagames.framework.kernel.core.common.Poolable;
import com.ankamagames.framework.kernel.core.common.Releasable;

public abstract interface BasicFighter
  extends Poolable, Releasable, EffectUser, LineOfSightObstacle, MovementObstacle, PathFindMover
{
  public abstract void onJoinFight(BasicFight<? extends BasicFighter> paramBasicFight);
  
  public abstract void onRemovedFromFight();
  
  public abstract void onNowAbleToFight();
  
  public abstract void onNowUnableToFight();
  
  public abstract boolean isOnFight();
  
  public abstract boolean canJoinFight();
  
  public abstract long getId();
  
  public abstract void setId(long paramLong);
  
  public abstract BasicFight getCurrentFight();
  
  public abstract TeamMate getTeamMate();
  
  public abstract FightingTeam getTeam();
  
  public abstract void setTeamMate(TeamMate<? extends BasicFighter> paramTeamMate);
  
  public abstract void onSpecialFighterEvent(int paramInt);
  
  public abstract boolean getFlag(int paramInt);
  
  public abstract void switchFlag(int paramInt);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\fight\BasicFighter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */