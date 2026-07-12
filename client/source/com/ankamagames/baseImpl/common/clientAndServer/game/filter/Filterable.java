package com.ankamagames.baseImpl.common.clientAndServer.game.filter;

import java.util.List;

public interface Filterable<F> {
  List<F> filter(Filter<F> paramFilter);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\filter\Filterable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */