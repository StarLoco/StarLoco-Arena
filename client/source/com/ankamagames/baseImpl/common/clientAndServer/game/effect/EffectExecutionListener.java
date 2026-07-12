package com.ankamagames.baseImpl.common.clientAndServer.game.effect;

import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;

public interface EffectExecutionListener {
  void onEffectDirectExecution(RunningEffect paramRunningEffect);
  
  void onEffectTriggeredExecution(RunningEffect paramRunningEffect);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\EffectExecutionListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */