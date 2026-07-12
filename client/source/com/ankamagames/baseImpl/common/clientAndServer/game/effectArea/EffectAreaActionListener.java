package com.ankamagames.baseImpl.common.clientAndServer.game.effectArea;

import com.ankamagames.framework.ai.targetfinder.Target;

public interface EffectAreaActionListener {
  void onEffectAreaAdded(BasicEffectArea paramBasicEffectArea);
  
  void onEffectAreaApplication(BasicEffectArea paramBasicEffectArea, Target paramTarget);
  
  void onEffectAreaUnapplication(BasicEffectArea paramBasicEffectArea, Target paramTarget);
  
  void onEffectAreaRemoved(BasicEffectArea paramBasicEffectArea);
  
  void onEffectAreaExecuted(BasicEffectArea paramBasicEffectArea);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effectArea\EffectAreaActionListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */