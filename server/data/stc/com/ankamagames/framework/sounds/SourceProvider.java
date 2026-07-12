package com.ankamagames.framework.sounds;

import net.java.games.sound3d.Source;

public abstract interface SourceProvider
{
  public abstract Source checkOutSource();
  
  public abstract void releaseSource(Source paramSource);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\sounds\SourceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */