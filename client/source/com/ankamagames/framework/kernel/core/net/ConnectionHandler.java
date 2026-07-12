/*     */ package com.ankamagames.framework.kernel.core.net;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.message.ProcessScheduler;
/*     */ import com.ankamagames.framework.kernel.core.monitor.Monitored;
/*     */ import com.ankamagames.framework.kernel.events.NetworkEventsHandler;
/*     */ import com.ankamagames.framework.kernel.utils.ExceptionFormatter;
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.net.BindException;
/*     */ import java.net.ConnectException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConnectionHandler
/*     */   extends Thread
/*     */   implements Monitored
/*     */ {
/*  37 */   private static Logger m_logger = Logger.getLogger(ConnectionHandler.class);
/*     */   
/*     */   private ServerSocketChannel m_channel;
/*     */   
/*     */   private Selector m_selector;
/*     */   private ObjectPool m_connectionPool;
/*  43 */   private final Object m_connectionPoolMutex = new Object();
/*     */   
/*     */   private NetworkEventsHandler m_eventsHandler;
/*     */   private int m_id;
/*  47 */   private static int m_chCount = 0;
/*     */   
/*     */   public int m_bindPort;
/*     */   
/*     */   public String m_bindAddress;
/*     */   
/*     */   public int m_connectionCount;
/*     */   public int m_numRaisedExceptions;
/*     */   public boolean m_running;
/*     */   public boolean m_online;
/*     */   private final List<Object> m_lastRaisedExceptions;
/*     */   private int m_externalId;
/*     */   private String m_externalName;
/*  60 */   private int m_connectionRetryDelay = 500;
/*  61 */   private int m_maxConnectionRetries = Integer.MAX_VALUE;
/*     */   
/*  63 */   private final Lock m_selectorLock = new ReentrantLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectionHandler(NetworkEventsHandler eventsHandler) {
/*  71 */     setName("ConnectionHandler");
/*  72 */     this.m_eventsHandler = eventsHandler;
/*     */     
/*  74 */     this.m_id = ++m_chCount;
/*  75 */     this.m_connectionCount = 0;
/*  76 */     this.m_numRaisedExceptions = 0;
/*  77 */     this.m_lastRaisedExceptions = Collections.synchronizedList(new ArrayList());
/*  78 */     this.m_externalName = "listener";
/*     */     
/*  80 */     if (this.m_eventsHandler == null) {
/*  81 */       throw new IllegalArgumentException("L'argument 'eventsHandler' ne doit pas etre nul");
/*     */     }
/*     */     try {
/*  84 */       this.m_connectionPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ConnectionPoolFactory());
/*  85 */       this.m_channel = ServerSocketChannel.open();
/*     */       
/*  87 */       this.m_selector = Selector.open();
/*  88 */       this.m_running = false;
/*  89 */       this.m_online = false;
/*  90 */       this.m_bindAddress = "";
/*  91 */       this.m_bindPort = 0;
/*  92 */     } catch (Exception ex) {
/*  93 */       m_logger.error(ExceptionFormatter.toString(ex));
/*  94 */       this.m_eventsHandler.onConnectionHandlerCreationError(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(String bindAddress, int bindPort) {
/* 106 */     this.m_bindAddress = bindAddress;
/* 107 */     this.m_bindPort = bindPort;
/*     */     
/*     */     try {
/* 110 */       this.m_channel.socket().setReuseAddress(true);
/* 111 */       this.m_channel.socket().bind(new InetSocketAddress(this.m_bindAddress, this.m_bindPort));
/* 112 */       this.m_channel.configureBlocking(false);
/* 113 */       this.m_channel.register(this.m_selector, 16);
/* 114 */     } catch (BindException ex) {
/* 115 */       m_logger.error("Ouverture de socket impossible : " + this.m_bindAddress + ":" + this.m_bindPort + ". Port probablement déjà utilisé.");
/* 116 */       this.m_eventsHandler.onConnectionHandlerInitializationError(this);
/* 117 */     } catch (IOException ex) {
/* 118 */       m_logger.error(ExceptionFormatter.toString(ex));
/* 119 */       this.m_eventsHandler.onConnectionHandlerInitializationError(this);
/*     */     } 
/*     */     
/* 122 */     m_logger.info("ConnectionHandler initialized : server mode");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void start() {
/* 128 */     if (!this.m_running) {
/* 129 */       this.m_running = true;
/* 130 */       super.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeAsClient() {
/* 139 */     m_logger.info("ConnectionHandler initialized : client mode");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBindPort() {
/* 148 */     return this.m_bindPort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBindAddress() {
/* 157 */     return this.m_bindAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getID() {
/* 166 */     return this.m_id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getConnectionCount() {
/* 175 */     return this.m_connectionCount;
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
/*     */   public synchronized Connection openConnection(String host, int port) throws Exception {
/* 190 */     Connection connection = null;
/*     */     
/* 192 */     connection = createConnection(SocketChannel.open(), true);
/* 193 */     if (connection != null) {
/* 194 */       connection.setPersistant(true);
/* 195 */       connection.setConnectionHandler(this);
/*     */       
/* 197 */       connection.setNetworkEventsHandler(this.m_eventsHandler);
/* 198 */       connection.setMaxConnectionRetries(this.m_maxConnectionRetries);
/* 199 */       connection.setConnectionRetryDelay(this.m_connectionRetryDelay);
/*     */       
/*     */       try {
/* 202 */         connection.connectToHost(host, port);
/* 203 */       } catch (Exception e) {
/* 204 */         e.printStackTrace();
/*     */       } 
/*     */     } else {
/*     */       
/* 208 */       m_logger.error("Unable to create a connection");
/*     */     } 
/* 210 */     return connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRunning() {
/* 219 */     return this.m_running;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnline() {
/* 228 */     return this.m_online;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getLastRaisedExceptions() {
/* 237 */     synchronized (this.m_lastRaisedExceptions) {
/* 238 */       return this.m_lastRaisedExceptions.toArray();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnline(boolean flag) {
/* 248 */     this.m_online = flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection createConnection(SocketChannel channel, boolean isClientConnection) {
/* 255 */     Connection connection = null;
/*     */     try {
/* 257 */       synchronized (this.m_connectionPoolMutex) {
/* 258 */         connection = (Connection)this.m_connectionPool.borrowObject();
/*     */         
/* 260 */         if (connection != null) {
/* 261 */           if (channel != null) {
/* 262 */             connection.setSocketChannel(channel);
/*     */           }
/* 264 */           if (!registerConnection(connection, isClientConnection)) {
/* 265 */             m_logger.error("Unable to register connection");
/* 266 */             releaseConnection(connection);
/* 267 */             connection = null;
/*     */           } 
/*     */         } 
/*     */       } 
/* 271 */     } catch (Throwable e) {
/* 272 */       this.m_eventsHandler.onConnectionHandlerInLoopError(this);
/* 273 */       m_logger.error("createConnection exception : ", e);
/*     */     } 
/* 275 */     return connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void releaseConnection(Connection connection) {
/*     */     try {
/* 283 */       synchronized (this.m_connectionPoolMutex) {
/* 284 */         if (connection != null) {
/* 285 */           if (connection.isRegistered() && 
/* 286 */             !unregisterConnection(connection)) {
/* 287 */             m_logger.error("Erreur durant le désenregistrement de la connexion");
/*     */           }
/*     */           
/* 290 */           this.m_connectionPool.returnObject(connection);
/*     */         } 
/*     */       } 
/* 293 */     } catch (Throwable ex) {
/* 294 */       this.m_eventsHandler.onConnectionHandlerInLoopError(this);
/* 295 */       m_logger.error("releaseConnection exception : ", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean registerConnection(Connection connection, boolean isClientConnection) {
/* 307 */     boolean bRegistered = false;
/*     */     
/* 309 */     if (connection != null) {
/* 310 */       SocketChannel channel = connection.getSocketChannel();
/* 311 */       if (channel != null) {
/*     */         try {
/* 313 */           this.m_selector.wakeup();
/* 314 */           this.m_selectorLock.lock();
/*     */           
/* 316 */           channel.configureBlocking(false);
/* 317 */           channel.socket().setKeepAlive(false);
/* 318 */           channel.socket().setTcpNoDelay(true);
/* 319 */           channel.register(this.m_selector, 0x1 | (isClientConnection ? 8 : 0), connection);
/* 320 */           this.m_connectionCount++;
/*     */           
/* 322 */           connection.setRegistered(true);
/* 323 */           bRegistered = true;
/*     */         }
/* 325 */         catch (Throwable e) {
/* 326 */           m_logger.error("registerConnection : exception : ", e);
/*     */         } finally {
/* 328 */           this.m_selectorLock.unlock();
/*     */         } 
/*     */       }
/*     */     } 
/* 332 */     return bRegistered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean unregisterConnection(Connection connection) {
/* 341 */     boolean bUnregistered = false;
/*     */     
/* 343 */     if (connection != null) {
/* 344 */       SocketChannel channel = connection.getSocketChannel();
/* 345 */       if (channel != null) {
/*     */         try {
/* 347 */           this.m_selector.wakeup();
/* 348 */           this.m_selectorLock.lock();
/*     */           
/* 350 */           SelectionKey key = channel.keyFor(this.m_selector);
/* 351 */           if (key != null) {
/* 352 */             key.cancel();
/*     */           }
/* 354 */           this.m_connectionCount--;
/*     */           
/* 356 */           channel.close();
/*     */           
/* 358 */           connection.setRegistered(false);
/* 359 */           bUnregistered = true;
/*     */         }
/* 361 */         catch (Throwable e) {
/* 362 */           m_logger.error("unregisterConnection : exception : ", e);
/*     */         } finally {
/* 364 */           this.m_selectorLock.unlock();
/*     */         } 
/*     */       }
/*     */     } 
/* 368 */     return bUnregistered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean readKey(SelectionKey key) {
/*     */     try {
/* 376 */       if (key.isReadable()) {
/*     */         
/*     */         try {
/* 379 */           Connection connection = (Connection)key.attachment();
/*     */           
/* 381 */           if (connection == null) {
/* 382 */             return true;
/*     */           }
/*     */           
/* 385 */           if (connection.isAboutToClose()) {
/* 386 */             return true;
/*     */           }
/* 388 */           if (!connection.isRegistered()) {
/* 389 */             return true;
/*     */           }
/* 391 */           boolean bSuccess = this.m_eventsHandler.onConnectionReadyRead(this, connection);
/* 392 */           if (!bSuccess) {
/* 393 */             return false;
/*     */           }
/*     */         }
/* 396 */         catch (Throwable e) {
/* 397 */           m_logger.error("read exception : ", e);
/* 398 */           return false;
/*     */         } 
/*     */       }
/* 401 */     } catch (Throwable e) {
/* 402 */       m_logger.error("key exception : ", e);
/* 403 */       return false;
/*     */     } 
/*     */     
/* 406 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeConnection(final Connection connection) {
/* 414 */     if (connection.isAboutToClose()) {
/*     */       return;
/*     */     }
/* 417 */     connection.setAboutToClose(true);
/*     */     
/* 419 */     ProcessScheduler.getInstance().schedule(new Runnable() {
/*     */           public void run() {
/* 421 */             ConnectionHandler.this.m_selectorLock.lock();
/*     */             try {
/* 423 */               connection.close();
/*     */             } finally {
/* 425 */               ConnectionHandler.this.m_selectorLock.unlock();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 436 */     m_logger.info("ConnectionHandler started : bindAddress=" + this.m_bindAddress + ", bindPort=" + this.m_bindPort);
/*     */     
/* 438 */     while (this.m_running) {
/*     */ 
/*     */       
/* 441 */       while (this.m_running) {
/*     */         try {
/* 443 */           int selectResult = 0;
/*     */           
/* 445 */           Thread.yield();
/* 446 */           Thread.sleep(10L);
/*     */           
/* 448 */           if (this.m_selectorLock.tryLock()) {
/*     */             try {
/*     */               try {
/* 451 */                 selectResult = this.m_selector.select(100L);
/* 452 */               } catch (Throwable ex) {
/* 453 */                 m_logger.error("select() exception : ", ex);
/*     */               } 
/* 455 */             } catch (Throwable e) {
/* 456 */               m_logger.error("run exception : ", e);
/*     */             } finally {
/* 458 */               this.m_selectorLock.unlock();
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 464 */           if (selectResult <= 0) {
/*     */             continue;
/*     */           }
/*     */           try {
/* 468 */             this.m_selectorLock.lock();
/*     */             
/* 470 */             Set<SelectionKey> keys = this.m_selector.selectedKeys();
/*     */             
/* 472 */             Iterator<SelectionKey> it = keys.iterator();
/*     */             
/* 474 */             while (it.hasNext()) {
/* 475 */               SelectionKey key = it.next();
/* 476 */               it.remove();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/* 484 */                 if (key.isValid() && key.isAcceptable()) {
/*     */                   try {
/* 486 */                     SocketChannel clientChannel = this.m_channel.accept();
/* 487 */                     Connection connection = createConnection(clientChannel, false);
/* 488 */                     connection.setConnectionHandler(this);
/* 489 */                     connection.setNetworkEventsHandler(this.m_eventsHandler);
/* 490 */                     connection.setSocketChannel(clientChannel);
/* 491 */                     connection.setConnectionEstablished(true);
/*     */                     
/* 493 */                     if (!this.m_eventsHandler.onNewConnection(this, connection)) {
/* 494 */                       closeConnection(connection);
/*     */                     }
/* 496 */                   } catch (Throwable ex) {
/* 497 */                     m_logger.error("accept() exception : ", ex);
/*     */                   }  continue;
/*     */                 } 
/* 500 */                 if (key.isValid() && key.isConnectable()) {
/* 501 */                   SocketChannel channel = (SocketChannel)key.channel();
/* 502 */                   Connection connection = (Connection)key.attachment();
/*     */                   
/* 504 */                   if (channel.finishConnect()) {
/* 505 */                     if (!this.m_eventsHandler.onNewConnection(this, connection)) {
/* 506 */                       closeConnection(connection); continue;
/*     */                     } 
/* 508 */                     connection.setConnectionHasBeenEstablished(true); continue;
/*     */                   } 
/* 510 */                   this.m_eventsHandler.onConnectionError(this, connection);
/* 511 */                   closeConnection(connection);
/*     */                   continue;
/*     */                 } 
/* 514 */                 if (key.isValid() && !readKey(key)) {
/* 515 */                   closeConnection((Connection)key.attachment());
/*     */                 }
/*     */               }
/* 518 */               catch (ConnectException ce) {
/* 519 */                 Connection connection = (Connection)key.attachment();
/* 520 */                 this.m_eventsHandler.onConnectionError(this, connection);
/* 521 */                 if (connection.isPersistant()) {
/* 522 */                   connection.scheduleReconnection(); continue;
/*     */                 } 
/* 524 */                 closeConnection(connection);
/*     */               
/*     */               }
/* 527 */               catch (Throwable e) {
/* 528 */                 m_logger.error("key exception : ", e);
/* 529 */                 Connection connection = (Connection)key.attachment();
/* 530 */                 System.err.println("Connection = " + connection);
/* 531 */                 if (connection.isPersistant()) {
/* 532 */                   connection.scheduleReconnection(); continue;
/*     */                 } 
/* 534 */                 closeConnection(connection);
/*     */               }
/*     */             
/*     */             } 
/* 538 */           } catch (Throwable e) {
/* 539 */             m_logger.error("run exception : ", e); continue;
/*     */           } finally {
/* 541 */             this.m_selectorLock.unlock();
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 546 */         catch (Throwable ioex) {
/* 547 */           m_logger.error("run exception : ", ioex);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 552 */     m_logger.info("ConnectionHandler stopped ");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeException(Throwable ex) {
/* 561 */     StringWriter strException = ExceptionFormatter.toString(ex);
/* 562 */     m_logger.error("Exception raised : ", ex);
/*     */     
/* 564 */     this.m_numRaisedExceptions++;
/* 565 */     synchronized (this.m_lastRaisedExceptions) {
/* 566 */       if (this.m_numRaisedExceptions >= 10)
/* 567 */         this.m_lastRaisedExceptions.remove(0); 
/* 568 */       this.m_lastRaisedExceptions.add(strException.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExternalName() {
/* 578 */     return this.m_externalName;
/*     */   }
/*     */   
/*     */   public void setExternalName(String name) {
/* 582 */     this.m_externalName = name;
/*     */   }
/*     */   
/*     */   public int getExternalID() {
/* 586 */     return this.m_externalId;
/*     */   }
/*     */   
/*     */   public void setExternalID(int id) {
/* 590 */     this.m_externalId = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConnectionRetryDelay(int connectionRetryDelay) {
/* 598 */     this.m_connectionRetryDelay = connectionRetryDelay;
/*     */   }
/*     */   
/*     */   public void setMaxConnectionRetries(int maxConnectionRetries) {
/* 602 */     this.m_maxConnectionRetries = maxConnectionRetries;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\net\ConnectionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */