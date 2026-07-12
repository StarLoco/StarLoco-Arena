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

public abstract interface StaticRunningEffect
{
  public abstract void setId(int paramInt);
  
  public abstract int getId();
  
  public abstract void setRunningEffectStatus(RunningEffectStatus paramRunningEffectStatus);
  
  public abstract RunningEffectStatus getRunningEffectStatus();
  
  public abstract Collection<EffectUser> determineTargets(Effect paramEffect, Target paramTarget, EffectContext paramEffectContext, Point3 paramPoint3);
  
  public abstract void run(Effect paramEffect, EffectContainer paramEffectContainer, EffectContext paramEffectContext, EffectUser paramEffectUser, Point3 paramPoint3, boolean paramBoolean);
  
  public abstract RunningEffect newParameterizedInstance(Effect paramEffect, EffectContainer paramEffectContainer, EffectContext paramEffectContext, EffectUser paramEffectUser1, EffectUser paramEffectUser2, Point3 paramPoint3);
  
  public abstract RunningEffect newUnserializedInstance(ByteBuffer paramByteBuffer, EffectContext paramEffectContext, AbstractEffectManager paramAbstractEffectManager);
  
  public abstract BitSet getTriggersToExecute();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\runningEffect\StaticRunningEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */