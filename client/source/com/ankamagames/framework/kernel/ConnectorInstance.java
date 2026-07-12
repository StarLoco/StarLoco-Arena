/*     */ package com.ankamagames.framework.kernel;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.MessageDecoder;
/*     */ import com.ankamagames.framework.kernel.core.common.message.MessageHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.framework.kernel.core.net.Connection;
/*     */ import com.ankamagames.framework.kernel.core.net.ConnectionHandler;
/*     */ import com.ankamagames.framework.kernel.events.FrameworkEvent;
/*     */ import com.ankamagames.framework.kernel.events.NetworkEventsHandler;
/*     */ import com.ankamagames.framework.kernel.events.ServerEventsHandler;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConnectorInstance
/*     */   implements NetworkEventsHandler, ServerEventsHandler, MessageDecoder
/*     */ {
/*  28 */   private static final HashMap<String, ConnectorInstance> m_connectors = new HashMap<String, ConnectorInstance>();
/*     */   
/*     */   public static void registerConnector(String name, ConnectorInstance connector) {
/*  31 */     m_connectors.put(name, connector);
/*     */   }
/*     */   
/*     */   public static ConnectorInstance getConnector(String name) {
/*  35 */     return m_connectors.get(name);
/*     */   }
/*     */   
/*     */   public static int getConnectorsCount() {
/*  39 */     return m_connectors.size();
/*     */   }
/*     */   
/*     */   public static Iterable<String> getConnectors() {
/*  43 */     return m_connectors.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   protected static final Logger m_logger = Logger.getLogger(ServerInstance.class);
/*     */   
/*     */   public final ConnectionHandler m_connectionHandler;
/*     */   
/*     */   public NetworkEventsHandler m_connectionEventHandler;
/*     */   public ServerEventsHandler m_serverEventsHandler;
/*     */   public MessageDecoder m_messageDecoder;
/*     */   public ObjectPool m_entityPool;
/*  57 */   private int m_connectionRetryDelay = 500;
/*  58 */   private int m_maxConnectionRetries = Integer.MAX_VALUE;
/*     */   
/*  60 */   private final Object m_connectionsMutex = new Object();
/*  61 */   private final ArrayList<Connection> m_connections = new ArrayList<Connection>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectorInstance(String connectorName) {
/*  69 */     this.m_connectionHandler = new ConnectionHandler(this);
/*  70 */     registerConnector(connectorName, this);
/*     */   }
/*     */   
/*     */   public boolean isRunning() {
/*  74 */     return (this.m_connectionHandler != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() throws Exception {
/*  84 */     if (this.m_messageDecoder == null) {
/*  85 */       throw new Exception("Le décodeur de messages n'a pas été spécifié");
/*     */     }
/*  87 */     if (this.m_entityPool == null) {
/*  88 */       throw new Exception("Le pool de ConnectionUser+MessageUser n'a pas été spécifié");
/*     */     }
/*  90 */     this.m_connectionHandler.initializeAsClient();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  97 */     this.m_connectionHandler.setOnline(true);
/*  98 */     this.m_connectionHandler.setConnectionRetryDelay(this.m_connectionRetryDelay);
/*  99 */     this.m_connectionHandler.setMaxConnectionRetries(this.m_maxConnectionRetries);
/* 100 */     this.m_connectionHandler.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerNetworkEventsHandler(NetworkEventsHandler handler) {
/* 109 */     this.m_connectionEventHandler = handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerServerEventsHandler(ServerEventsHandler handler) {
/* 118 */     this.m_serverEventsHandler = handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerMessageDecoder(MessageDecoder messageDecoder) {
/* 127 */     this.m_messageDecoder = messageDecoder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEntityFactory(ObjectFactory<? extends FrameworkEntity> factory) {
/* 137 */     this.m_entityPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)factory);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onConnectionHandlerCreationError(ConnectionHandler ch) {
/* 142 */     if (this.m_connectionEventHandler != null) {
/* 143 */       return this.m_connectionEventHandler.onConnectionHandlerCreationError(ch);
/*     */     }
/* 145 */     m_logger.warn("onConnectionHandlerCreationError non forwardé : pas de handler défini");
/* 146 */     return false;
/*     */   }
/*     */   
/*     */   public boolean onConnectionHandlerInitializationError(ConnectionHandler ch) {
/* 150 */     if (this.m_connectionEventHandler != null) {
/* 151 */       return this.m_connectionEventHandler.onConnectionHandlerInitializationError(ch);
/*     */     }
/* 153 */     m_logger.warn("onConnectionHandlerInitializationError non forwardé : pas de handler défini");
/* 154 */     return false;
/*     */   }
/*     */   
/*     */   public boolean onConnectionHandlerInLoopError(ConnectionHandler ch) {
/* 158 */     if (this.m_connectionEventHandler != null) {
/* 159 */       return this.m_connectionEventHandler.onConnectionHandlerInLoopError(ch);
/*     */     }
/* 161 */     m_logger.warn("onConnectionHandlerInLoopError non forwardé : pas de handler défini");
/* 162 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onNewConnection(ConnectionHandler ch, Connection connection) {
/*     */     try {
/* 169 */       FrameworkEntity user = (FrameworkEntity)this.m_entityPool.borrowObject();
/*     */       
/* 171 */       if (user == null) {
/* 172 */         return false;
/*     */       }
/* 174 */       user.setPool(this.m_entityPool);
/* 175 */       user.setConnection(connection);
/* 176 */       connection.setUser(user);
/* 177 */       user.onConnect();
/*     */       
/* 179 */       synchronized (this.m_connectionsMutex) {
/* 180 */         this.m_connections.add(connection);
/*     */       } 
/*     */       
/* 183 */       if (this.m_connectionEventHandler != null) {
/* 184 */         return this.m_connectionEventHandler.onNewConnection(ch, connection);
/*     */       }
/* 186 */       m_logger.warn("onNewConnection non forwardé : pas de handler défini");
/*     */     }
/* 188 */     catch (Exception ex) {
/* 189 */       ch.storeException(ex);
/* 190 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onConnectionReadyRead(ConnectionHandler ch, Connection connection) {
/* 209 */     ByteBuffer rawMessage = connection.read();
/*     */     
/* 211 */     if (rawMessage != null) {
/*     */       Message message;
/*     */ 
/*     */       
/*     */       do {
/* 216 */         message = this.m_messageDecoder.decode(rawMessage);
/* 217 */         if (message == null)
/* 218 */           continue;  if (message.getHandler() == null)
/* 219 */           message.setHandler((MessageHandler)connection.getUser()); 
/* 220 */         Worker.getInstance().pushMessage(message);
/*     */       }
/* 222 */       while (message != null);
/*     */       
/* 224 */       rawMessage.compact();
/*     */     }
/*     */     else {
/*     */       
/* 228 */       return false;
/*     */     } 
/*     */     
/* 231 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onConnectionError(ConnectionHandler ch, Connection connection) {
/* 236 */     if (this.m_connectionEventHandler != null) {
/* 237 */       return this.m_connectionEventHandler.onConnectionError(ch, connection);
/*     */     }
/* 239 */     m_logger.warn("onConnectionError non forwardé : pas de handler défini");
/*     */     
/* 241 */     return true;
/*     */   }
/*     */   
/*     */   public boolean onConnectionClose(ConnectionHandler ch, Connection connection) {
/*     */     try {
/* 246 */       synchronized (this.m_connectionsMutex) {
/* 247 */         this.m_connections.remove(connection);
/*     */       } 
/*     */       
/* 250 */       FrameworkEntity entity = (FrameworkEntity)connection.getUser();
/* 251 */       if (entity != null) {
/* 252 */         entity.onDisconnect();
/*     */       }
/* 254 */     } catch (Exception ex) {
/* 255 */       ch.storeException(ex);
/* 256 */       return false;
/*     */     } 
/*     */     
/* 259 */     if (this.m_connectionEventHandler != null) {
/* 260 */       return this.m_connectionEventHandler.onConnectionClose(ch, connection);
/*     */     }
/* 262 */     m_logger.warn("onConnectionClose non forwardé : pas de handler défini");
/*     */ 
/*     */     
/* 265 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onConnectionRecovered(ConnectionHandler ch, Connection connection) {
/* 277 */     if (connection != null) {
/* 278 */       FrameworkEntity entity = (FrameworkEntity)connection.getUser();
/* 279 */       if (entity != null) {
/* 280 */         entity.onConnectionRecovered();
/*     */         
/* 282 */         if (this.m_connectionEventHandler != null) {
/* 283 */           return this.m_connectionEventHandler.onConnectionRecovered(ch, connection);
/*     */         }
/* 285 */         m_logger.warn("onConnectionRecovered non forwardé : pas de handler défini");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 293 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onReconnectionScheduled(ConnectionHandler ch, Connection connection) {
/* 305 */     FrameworkEntity entity = (FrameworkEntity)connection.getUser();
/* 306 */     if (entity != null) {
/* 307 */       entity.onReconnectionPending();
/*     */     }
/* 309 */     if (this.m_connectionEventHandler != null) {
/* 310 */       this.m_connectionEventHandler.onReconnectionScheduled(ch, connection);
/*     */     } else {
/* 312 */       m_logger.warn("onReconnectionScheduled non forwardé : pas de handler défini");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message decode(ByteBuffer rawMessage) {
/* 323 */     if (this.m_messageDecoder != null)
/* 324 */       return this.m_messageDecoder.decode(rawMessage); 
/* 325 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectionHandler getConnectionHandler() {
/* 334 */     return this.m_connectionHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection openConnection(String host, int port) {
/*     */     try {
/* 347 */       if (this.m_connectionHandler != null) {
/* 348 */         this.m_connectionHandler.setMaxConnectionRetries(this.m_maxConnectionRetries);
/* 349 */         this.m_connectionHandler.setConnectionRetryDelay(this.m_connectionRetryDelay);
/* 350 */         return this.m_connectionHandler.openConnection(host, port);
/*     */       } 
/* 352 */     } catch (Exception e) {
/* 353 */       m_logger.error("openConnection exception : ", e);
/*     */     } 
/*     */     
/* 356 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEvent(FrameworkEvent event) {}
/*     */   
/*     */   public boolean onMessage(Message message) {
/* 363 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 372 */     if (this.m_connectionHandler != null) {
/* 373 */       return this.m_connectionHandler.getId();
/*     */     }
/* 375 */     m_logger.warn("getId() retourne -1 : pas de handler défini");
/*     */     
/* 377 */     return -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(long id) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConnectionRetryDelay(int connectionRetryDelay) {
/* 392 */     this.m_connectionRetryDelay = connectionRetryDelay;
/*     */   }
/*     */   
/*     */   public void setMaxConnectionRetries(int maxConnectionRetries) {
/* 396 */     this.m_maxConnectionRetries = maxConnectionRetries;
/*     */   }
/*     */   
/*     */   public int getConnectionCount() {
/* 400 */     synchronized (this.m_connectionsMutex) {
/* 401 */       return this.m_connections.size();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Iterable<Connection> getConnections() {
/* 406 */     synchronized (this.m_connectionsMutex) {
/* 407 */       return this.m_connections;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\ConnectorInstance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */