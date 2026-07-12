package com.ankamagames.baseImpl.common.clientAndServer.game.time;

import com.ankamagames.framework.kernel.core.common.Releasable;

public interface TimeUnit<T, TI extends TimeInterval> extends Comparable<T>, Releasable {
  void increment();
  
  void increment(TI paramTI);
  
  void decrement();
  
  void decrement(TI paramTI);
  
  void release();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\TimeUnit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */