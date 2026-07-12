package com.ankamagames.framework.kernel.core.net;

import com.ankamagames.framework.kernel.core.common.message.Message;

public interface ConnectionUser {
  void setConnection(Connection paramConnection);
  
  boolean isConnected();
  
  void onConnect();
  
  void onDisconnect();
  
  void sendMessage(Message paramMessage);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\net\ConnectionUser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */