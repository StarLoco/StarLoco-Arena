package com.ankamagames.baseImpl.common.clientAndServer.game.fight;

import java.util.Iterator;

public interface FightersGroup<F extends BasicFighter> extends Iterable<F> {
  Iterator<F> iterator();
  
  int size();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\fight\FightersGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */