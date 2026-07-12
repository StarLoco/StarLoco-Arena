package com.ankamagames.baseImpl.common.clientAndServer.game.effect;

import java.util.Iterator;

public interface EffectUserInformationProvider {
  Iterator<? extends EffectUser> getEffectUsers();
  
  EffectUser getEffectUserFromId(long paramLong);
  
  long getNextFreeEffectUserId();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\EffectUserInformationProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */