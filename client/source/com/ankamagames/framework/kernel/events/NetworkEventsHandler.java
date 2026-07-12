package com.ankamagames.framework.kernel.events;

import com.ankamagames.framework.kernel.core.net.Connection;
import com.ankamagames.framework.kernel.core.net.ConnectionHandler;

public interface NetworkEventsHandler extends FrameworkEventsHandler {
  boolean onConnectionHandlerCreationError(ConnectionHandler paramConnectionHandler);
  
  boolean onConnectionHandlerInitializationError(ConnectionHandler paramConnectionHandler);
  
  boolean onConnectionHandlerInLoopError(ConnectionHandler paramConnectionHandler);
  
  boolean onNewConnection(ConnectionHandler paramConnectionHandler, Connection paramConnection);
  
  boolean onConnectionReadyRead(ConnectionHandler paramConnectionHandler, Connection paramConnection);
  
  boolean onConnectionClose(ConnectionHandler paramConnectionHandler, Connection paramConnection);
  
  boolean onConnectionError(ConnectionHandler paramConnectionHandler, Connection paramConnection);
  
  boolean onConnectionRecovered(ConnectionHandler paramConnectionHandler, Connection paramConnection);
  
  void onReconnectionScheduled(ConnectionHandler paramConnectionHandler, Connection paramConnection);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\events\NetworkEventsHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */