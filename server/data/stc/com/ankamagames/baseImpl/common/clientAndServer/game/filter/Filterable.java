package com.ankamagames.baseImpl.common.clientAndServer.game.filter;

import java.util.List;

public abstract interface Filterable<F>
{
  public abstract List<F> filter(Filter<F> paramFilter);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\filter\Filterable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */