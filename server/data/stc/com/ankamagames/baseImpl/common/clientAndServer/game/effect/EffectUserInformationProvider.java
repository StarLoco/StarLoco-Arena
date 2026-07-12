package com.ankamagames.baseImpl.common.clientAndServer.game.effect;

import java.util.Iterator;

public abstract interface EffectUserInformationProvider
{
  public abstract Iterator<? extends EffectUser> getEffectUsers();
  
  public abstract EffectUser getEffectUserFromId(long paramLong);
  
  public abstract long getNextFreeEffectUserId();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\EffectUserInformationProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */