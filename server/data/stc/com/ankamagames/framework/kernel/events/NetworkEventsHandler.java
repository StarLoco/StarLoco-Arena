package com.ankamagames.framework.kernel.events;

import com.ankamagames.framework.kernel.core.net.Connection;
import com.ankamagames.framework.kernel.core.net.ConnectionHandler;

public abstract interface NetworkEventsHandler
  extends FrameworkEventsHandler
{
  public abstract boolean onConnectionHandlerCreationError(ConnectionHandler paramConnectionHandler);
  
  public abstract boolean onConnectionHandlerInitializationError(ConnectionHandler paramConnectionHandler);
  
  public abstract boolean onConnectionHandlerInLoopError(ConnectionHandler paramConnectionHandler);
  
  public abstract boolean onNewConnection(ConnectionHandler paramConnectionHandler, Connection paramConnection);
  
  public abstract boolean onConnectionReadyRead(ConnectionHandler paramConnectionHandler, Connection paramConnection);
  
  public abstract boolean onConnectionClose(ConnectionHandler paramConnectionHandler, Connection paramConnection);
  
  public abstract boolean onConnectionError(ConnectionHandler paramConnectionHandler, Connection paramConnection);
  
  public abstract boolean onConnectionRecovered(ConnectionHandler paramConnectionHandler, Connection paramConnection);
  
  public abstract void onReconnectionScheduled(ConnectionHandler paramConnectionHandler, Connection paramConnection);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\events\NetworkEventsHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */