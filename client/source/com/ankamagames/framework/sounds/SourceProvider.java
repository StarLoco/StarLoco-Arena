package com.ankamagames.framework.sounds;

import net.java.games.sound3d.Source;

public interface SourceProvider {
  Source checkOutSource();
  
  void releaseSource(Source paramSource);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\sounds\SourceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */