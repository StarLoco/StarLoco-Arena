package com.ankamagames.framework.sounds;

import net.java.games.sound3d.Buffer;

public interface BufferProvider {
  Buffer checkOutBuffer();
  
  void releaseBuffer(Buffer paramBuffer);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\sounds\BufferProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */