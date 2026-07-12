package com.ankamagames.baseImpl.common.clientAndServer.game.fight;

import com.ankamagames.framework.kernel.core.common.Poolable;

public interface TeamMate<F extends BasicFighter> extends Poolable {
  long getTeamMateId();
  
  FightingTeam<F> getTeam();
  
  void setTeam(FightingTeam<F> paramFightingTeam);
  
  boolean addFighter(F paramF);
  
  boolean removeFighter(F paramF);
  
  int getFightersCount();
  
  Iterable<F> getFighters();
  
  BasicFight<F> getCurrentFight();
  
  void onFightEnd();
  
  void onTeamMateJoinFight(BasicFight<F> paramBasicFight);
  
  String getName();
  
  void setPooled(boolean paramBoolean);
  
  void unserialize(byte[] paramArrayOfbyte);
  
  byte[] serialize();
  
  void release();
  
  short getBudget();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\fight\TeamMate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */