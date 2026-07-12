package com.ankamagames.baseImpl.common.clientAndServer.game.effect;

import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;

public abstract interface EffectExecutionListener
{
  public abstract void onEffectDirectExecution(RunningEffect paramRunningEffect);
  
  public abstract void onEffectTriggeredExecution(RunningEffect paramRunningEffect);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\EffectExecutionListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */