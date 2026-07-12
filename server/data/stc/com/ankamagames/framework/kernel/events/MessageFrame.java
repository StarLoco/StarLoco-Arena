package com.ankamagames.framework.kernel.events;

import com.ankamagames.framework.kernel.FrameHandler;
import com.ankamagames.framework.kernel.core.common.message.MessageHandler;

public abstract interface MessageFrame
  extends MessageHandler
{
  public abstract void onFrameAdd(FrameHandler paramFrameHandler, boolean paramBoolean);
  
  public abstract void onFrameRemove(FrameHandler paramFrameHandler, boolean paramBoolean);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\events\MessageFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */