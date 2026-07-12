package com.ankamagames.baseImpl.common.clientAndServer.game.effect;

public interface EffectContainer extends Iterable<Effect> {
  void addEffect(Effect paramEffect);
  
  void addEffects(Effect[] paramArrayOfEffect);
  
  int getContainerType();
  
  long getEffectContainerId();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\EffectContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */