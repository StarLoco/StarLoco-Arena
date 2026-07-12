package com.ankamagames.baseImpl.common.clientAndServer.game.time;

import com.ankamagames.framework.kernel.core.common.Releasable;

public abstract interface TimeUnit<T, TI extends TimeInterval>
  extends Comparable<T>, Releasable
{
  public abstract void increment();
  
  public abstract void increment(TI paramTI);
  
  public abstract void decrement();
  
  public abstract void decrement(TI paramTI);
  
  public abstract void release();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\TimeUnit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */