package com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect;

import com.ankamagames.baseImpl.common.clientAndServer.game.effect.AbstractEffectManager;
import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
import com.ankamagames.framework.ai.targetfinder.Target;
import com.ankamagames.framework.kernel.core.maths.Point3;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Collection;

public interface StaticRunningEffect {
  void setId(int paramInt);
  
  int getId();
  
  void setRunningEffectStatus(RunningEffectStatus paramRunningEffectStatus);
  
  RunningEffectStatus getRunningEffectStatus();
  
  Collection<EffectUser> determineTargets(Effect paramEffect, Target paramTarget, EffectContext paramEffectContext, Point3 paramPoint3);
  
  void run(Effect paramEffect, EffectContainer paramEffectContainer, EffectContext paramEffectContext, EffectUser paramEffectUser, Point3 paramPoint3, boolean paramBoolean);
  
  RunningEffect newParameterizedInstance(Effect paramEffect, EffectContainer paramEffectContainer, EffectContext paramEffectContext, EffectUser paramEffectUser1, EffectUser paramEffectUser2, Point3 paramPoint3);
  
  RunningEffect newUnserializedInstance(ByteBuffer paramByteBuffer, EffectContext paramEffectContext, AbstractEffectManager paramAbstractEffectManager);
  
  BitSet getTriggersToExecute();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\runningEffect\StaticRunningEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */