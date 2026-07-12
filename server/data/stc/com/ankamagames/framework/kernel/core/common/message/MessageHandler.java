package com.ankamagames.framework.kernel.core.common.message;

import com.ankamagames.framework.kernel.core.common.Validable;

public abstract interface MessageHandler
  extends Validable
{
  public abstract boolean onMessage(Message paramMessage);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\message\MessageHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */