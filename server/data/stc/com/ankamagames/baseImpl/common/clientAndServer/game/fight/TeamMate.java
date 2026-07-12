package com.ankamagames.baseImpl.common.clientAndServer.game.fight;

import com.ankamagames.framework.kernel.core.common.Poolable;

public abstract interface TeamMate<F extends BasicFighter>
  extends Poolable
{
  public abstract long getTeamMateId();
  
  public abstract FightingTeam<F> getTeam();
  
  public abstract void setTeam(FightingTeam<F> paramFightingTeam);
  
  public abstract boolean addFighter(F paramF);
  
  public abstract boolean removeFighter(F paramF);
  
  public abstract int getFightersCount();
  
  public abstract Iterable<F> getFighters();
  
  public abstract BasicFight<F> getCurrentFight();
  
  public abstract void onFightEnd();
  
  public abstract void onTeamMateJoinFight(BasicFight<F> paramBasicFight);
  
  public abstract String getName();
  
  public abstract void setPooled(boolean paramBoolean);
  
  public abstract void unserialize(byte[] paramArrayOfByte);
  
  public abstract byte[] serialize();
  
  public abstract void release();
  
  public abstract short getBudget();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\fight\TeamMate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */