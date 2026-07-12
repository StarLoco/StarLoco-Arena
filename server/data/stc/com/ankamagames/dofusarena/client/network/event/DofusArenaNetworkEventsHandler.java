/*     */ package com.ankamagames.dofusarena.client.network.event;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.ReconnectionTicketRequestMessage;
/*     */ import com.ankamagames.baseImpl.graphicalClient.ui.progress.ProgressMonitor;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetAuthenticationFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.progress.DofusArenaProgressMonitorManager;
/*     */ import com.ankamagames.framework.kernel.core.net.Connection;
/*     */ import com.ankamagames.framework.kernel.core.net.ConnectionHandler;
/*     */ import com.ankamagames.framework.kernel.events.FrameworkEvent;
/*     */ import com.ankamagames.framework.kernel.events.NetworkEventsHandler;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DofusArenaNetworkEventsHandler
/*     */   implements NetworkEventsHandler
/*     */ {
/*  28 */   private static Logger m_logger = Logger.getLogger(DofusArenaNetworkEventsHandler.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onConnectionClose(ConnectionHandler ch, Connection connection)
/*     */   {
/*  37 */     m_logger.info("onConnectionClose");
/*     */     
/*     */ 
/*  40 */     if (DofusArenaGameEntity.getInstance().isLogged())
/*     */     {
/*     */ 
/*  43 */       DofusArenaClientInstance.getInstance().cleanUp();
/*     */       
/*     */ 
/*  46 */       Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("connection.closed", new Object[0]), 66);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  51 */     DofusArenaProgressMonitorManager.getInstance().done();
/*     */     
/*  53 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onConnectionError(ConnectionHandler ch, Connection connection)
/*     */   {
/*  63 */     m_logger.info("onConnectionError isConnected=" + connection.isConnected() + " isRetrying=" + connection.isRetrying());
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  68 */     if ((!connection.isConnected()) && (!connection.isRetrying())) {
/*  69 */       DofusArenaGameEntity.getInstance().connect();
/*  70 */     } else if (connection.isRetrying())
/*     */     {
/*  72 */       DofusArenaProgressMonitorManager.getInstance().getProgressMonitor(true).beginTask(DofusArenaTranslator.getInstance().getString("connection.retrying", new Object[0]), connection.getMaxConnectionRetries());
/*  73 */       DofusArenaProgressMonitorManager.getInstance().getProgressMonitor().worked(connection.getConnectionRetryCount());
/*     */     }
/*     */     
/*  76 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onConnectionHandlerCreationError(ConnectionHandler ch)
/*     */   {
/*  85 */     m_logger.info("onConnectionHandlerCreationError");
/*  86 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onConnectionHandlerInLoopError(ConnectionHandler ch)
/*     */   {
/*  95 */     m_logger.info("onConnectionHandlerInLoopError");
/*  96 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onConnectionHandlerInitializationError(ConnectionHandler ch)
/*     */   {
/* 105 */     m_logger.info("onConnectionHandlerInitializationError");
/* 106 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onConnectionReadyRead(ConnectionHandler ch, Connection connection)
/*     */   {
/* 116 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onConnectionRecovered(ConnectionHandler ch, Connection connection)
/*     */   {
/* 126 */     m_logger.debug("onConnectionRecovered");
/*     */     
/*     */ 
/* 129 */     DofusArenaProgressMonitorManager.getInstance().done();
/*     */     
/*     */ 
/* 132 */     byte[] ticket = DofusArenaGameEntity.getInstance().getTicket();
/* 133 */     if (ticket != null) {
/* 134 */       ReconnectionTicketRequestMessage message = new ReconnectionTicketRequestMessage(ticket);
/* 135 */       DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(message);
/*     */     }
/*     */     else {
/* 138 */       DofusArenaGameEntity.getInstance().getNetworkEntity().closeConnection();
/*     */     }
/*     */     
/* 141 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onNewConnection(ConnectionHandler ch, Connection connection)
/*     */   {
/* 151 */     m_logger.info("onNewConnection");
/*     */     
/* 153 */     DofusArenaGameEntity gameEntity = DofusArenaGameEntity.getInstance();
/*     */     
/*     */ 
/* 156 */     gameEntity.pushFrame(NetAuthenticationFrame.getInstance());
/*     */     
/*     */ 
/* 159 */     gameEntity.logon();
/*     */     
/* 161 */     return true;
/*     */   }
/*     */   
/*     */   public void onReconnectionScheduled(ConnectionHandler ch, Connection connection) {}
/*     */   
/*     */   public void onEvent(FrameworkEvent event) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\event\DofusArenaNetworkEventsHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */