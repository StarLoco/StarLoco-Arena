package com.ankamagames.framework.kernel.events;

import com.ankamagames.framework.kernel.FrameHandler;
import com.ankamagames.framework.kernel.core.common.message.MessageHandler;

public interface MessageFrame extends MessageHandler {
  void onFrameAdd(FrameHandler paramFrameHandler, boolean paramBoolean);
  
  void onFrameRemove(FrameHandler paramFrameHandler, boolean paramBoolean);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\events\MessageFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */