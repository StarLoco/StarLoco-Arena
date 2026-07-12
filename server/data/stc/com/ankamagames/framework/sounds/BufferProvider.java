package com.ankamagames.framework.sounds;

import net.java.games.sound3d.Buffer;

public abstract interface BufferProvider
{
  public abstract Buffer checkOutBuffer();
  
  public abstract void releaseBuffer(Buffer paramBuffer);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\sounds\BufferProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */