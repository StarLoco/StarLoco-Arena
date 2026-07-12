package com.ankamagames.baseImpl.common.clientAndServer.game.effectArea;

import com.ankamagames.framework.ai.targetfinder.Target;

public abstract interface EffectAreaActionListener
{
  public abstract void onEffectAreaAdded(BasicEffectArea paramBasicEffectArea);
  
  public abstract void onEffectAreaApplication(BasicEffectArea paramBasicEffectArea, Target paramTarget);
  
  public abstract void onEffectAreaUnapplication(BasicEffectArea paramBasicEffectArea, Target paramTarget);
  
  public abstract void onEffectAreaRemoved(BasicEffectArea paramBasicEffectArea);
  
  public abstract void onEffectAreaExecuted(BasicEffectArea paramBasicEffectArea);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effectArea\EffectAreaActionListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */