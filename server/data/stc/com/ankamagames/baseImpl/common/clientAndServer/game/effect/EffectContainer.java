package com.ankamagames.baseImpl.common.clientAndServer.game.effect;

public abstract interface EffectContainer
  extends Iterable<Effect>
{
  public abstract void addEffect(Effect paramEffect);
  
  public abstract void addEffects(Effect[] paramArrayOfEffect);
  
  public abstract int getContainerType();
  
  public abstract long getEffectContainerId();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\EffectContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */