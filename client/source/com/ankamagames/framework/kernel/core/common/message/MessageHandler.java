package com.ankamagames.framework.kernel.core.common.message;

import com.ankamagames.framework.kernel.core.common.Validable;

public interface MessageHandler extends Validable {
  boolean onMessage(Message paramMessage);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\message\MessageHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */