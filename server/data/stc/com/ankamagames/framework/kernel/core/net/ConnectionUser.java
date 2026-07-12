package com.ankamagames.framework.kernel.core.net;

import com.ankamagames.framework.kernel.core.common.message.Message;

public abstract interface ConnectionUser
{
  public abstract void setConnection(Connection paramConnection);
  
  public abstract boolean isConnected();
  
  public abstract void onConnect();
  
  public abstract void onDisconnect();
  
  public abstract void sendMessage(Message paramMessage);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\net\ConnectionUser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */