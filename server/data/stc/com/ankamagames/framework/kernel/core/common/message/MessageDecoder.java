package com.ankamagames.framework.kernel.core.common.message;

import java.nio.ByteBuffer;

public abstract interface MessageDecoder
{
  public abstract Message decode(ByteBuffer paramByteBuffer);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\message\MessageDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */