package com.ankamagames.baseImpl.common.clientAndServer.game.fight;

import java.util.Iterator;

public abstract interface FightersGroup<F extends BasicFighter>
  extends Iterable<F>
{
  public abstract Iterator<F> iterator();
  
  public abstract int size();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\fight\FightersGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */