package com.ankamagames.baseImpl.common.clientAndServer.game.fight;

import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
import com.ankamagames.framework.ai.LOS.LineOfSightObstacle;
import com.ankamagames.framework.ai.pathfinder.MovementObstacle;
import com.ankamagames.framework.ai.pathfinder.PathFindMover;
import com.ankamagames.framework.kernel.core.common.Poolable;
import com.ankamagames.framework.kernel.core.common.Releasable;

public interface BasicFighter extends Poolable, Releasable, EffectUser, LineOfSightObstacle, MovementObstacle, PathFindMover {
  void onJoinFight(BasicFight<? extends BasicFighter> paramBasicFight);
  
  void onRemovedFromFight();
  
  void onNowAbleToFight();
  
  void onNowUnableToFight();
  
  boolean isOnFight();
  
  boolean canJoinFight();
  
  long getId();
  
  void setId(long paramLong);
  
  BasicFight getCurrentFight();
  
  TeamMate getTeamMate();
  
  FightingTeam getTeam();
  
  void setTeamMate(TeamMate<? extends BasicFighter> paramTeamMate);
  
  void onSpecialFighterEvent(int paramInt);
  
  boolean getFlag(int paramInt);
  
  void switchFlag(int paramInt);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\fight\BasicFighter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */