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
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public ConnectionHandler(NetworkEventsHandler eventsHandler)
/*     */   {
/*  71 */     super.setName("ConnectionHandler");
/*  72 */     this.m_eventsHandler = eventsHandler;
/*     */     
/*  74 */     this.m_id = (++m_chCount);
/*  75 */     this.m_connectionCount = 0;
/*  76 */     this.m_numRaisedExceptions = 0;
/*  77 */     this.m_lastRaisedExceptions = Collections.synchronizedList(new ArrayList());
/*  78 */     this.m_externalName = "listener";
/*     */     
/*  80 */     if (this.m_eventsHandler == null) {
/*  81 */       throw new IllegalArgumentException("L'argument 'eventsHandler' ne doit pas etre nul");
/*     */     }
/*     */     try {
/*  84 */       this.m_connectionPool = new MonitoredPool(new ConnectionPoolFactory());
/*  85 */       this.m_channel = ServerSocketChannel.open();
/*     */       
/*  87 */       this.m_selector = Selector.open();
/*  88 */       this.m_running = false;
/*  89 */       this.m_online = false;
/*  90 */       this.m_bindAddress = "";
/*  91 */       this.m_bindPort = 0;
/*     */     } catch (Exception ex) {
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
/*     */   public void initialize(String bindAddress, int bindPort)
/*     */   {
/* 106 */     this.m_bindAddress = bindAddress;
/* 107 */     this.m_bindPort = bindPort;
/*     */     try
/*     */     {
/* 110 */       this.m_channel.socket().setReuseAddress(true);
/* 111 */       this.m_channel.socket().bind(new InetSocketAddress(this.m_bindAddress, this.m_bindPort));
/* 112 */       this.m_channel.configureBlocking(false);
/* 113 */       this.m_channel.register(this.m_selector, 16);
/*     */     } catch (BindException ex) {
/* 115 */       m_logger.error("Ouverture de socket impossible : " + this.m_bindAddress + ":" + this.m_bindPort + ". Port probablement déjà utilisé.");
/* 116 */       this.m_eventsHandler.onConnectionHandlerInitializationError(this);
/*     */     } catch (IOException ex) {
/* 118 */       m_logger.error(ExceptionFormatter.toString(ex));
/* 119 */       this.m_eventsHandler.onConnectionHandlerInitializationError(this);
/*     */     }
/*     */     
/* 122 */     m_logger.info("ConnectionHandler initialized : server mode");
/*     */   }
/*     */   
/*     */ 
/*     */   public synchronized void start()
/*     */   {
/* 128 */     if (!this.m_running) {
/* 129 */       this.m_running = true;
/* 130 */       super.start();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initializeAsClient()
/*     */   {
/* 139 */     m_logger.info("ConnectionHandler initialized : client mode");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBindPort()
/*     */   {
/* 148 */     return this.m_bindPort;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getBindAddress()
/*     */   {
/* 157 */     return this.m_bindAddress;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getID()
/*     */   {
/* 166 */     return this.m_id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getConnectionCount()
/*     */   {
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
/*     */   public synchronized Connection openConnection(String host, int port)
/*     */     throws Exception
/*     */   {
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
/*     */       try
/*     */       {
/* 202 */         connection.connectToHost(host, port);
/*     */       } catch (Exception e) {
/* 204 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     else {
/* 208 */       m_logger.error("Unable to create a connection");
/*     */     }
/* 210 */     return connection;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isRunning()
/*     */   {
/* 219 */     return this.m_running;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isOnline()
/*     */   {
/* 228 */     return this.m_online;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public Object[] getLastRaisedExceptions()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 477	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_lastRaisedExceptions	Ljava/util/List;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 477	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_lastRaisedExceptions	Ljava/util/List;
/*     */     //   11: invokeinterface 569 1 0
/*     */     //   16: aload_1
/*     */     //   17: monitorexit
/*     */     //   18: areturn
/*     */     //   19: aload_1
/*     */     //   20: monitorexit
/*     */     //   21: athrow
/*     */     // Line number table:
/*     */     //   Java source line #237	-> byte code offset #0
/*     */     //   Java source line #238	-> byte code offset #7
/*     */     //   Java source line #237	-> byte code offset #19
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	22	0	this	ConnectionHandler
/*     */     //   5	15	1	Ljava/lang/Object;	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	18	19	finally
/*     */     //   19	21	19	finally
/*     */   }
/*     */   
/*     */   public void setOnline(boolean flag)
/*     */   {
/* 248 */     this.m_online = flag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private Connection createConnection(SocketChannel channel, boolean isClientConnection)
/*     */   {
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
/*     */     } catch (Throwable e) {
/* 272 */       this.m_eventsHandler.onConnectionHandlerInLoopError(this);
/* 273 */       m_logger.error("createConnection exception : ", e);
/*     */     }
/* 275 */     return connection;
/*     */   }
/*     */   
/*     */ 
/*     */   void releaseConnection(Connection connection)
/*     */   {
/*     */     try
/*     */     {
/* 283 */       synchronized (this.m_connectionPoolMutex) {
/* 284 */         if (connection != null) {
/* 285 */           if ((connection.isRegistered()) && 
/* 286 */             (!unregisterConnection(connection))) {
/* 287 */             m_logger.error("Erreur durant le désenregistrement de la connexion");
/*     */           }
/*     */           
/* 290 */           this.m_connectionPool.returnObject(connection);
/*     */         }
/*     */       }
/*     */     } catch (Throwable ex) {
/* 294 */       this.m_eventsHandler.onConnectionHandlerInLoopError(this);
/* 295 */       m_logger.error("releaseConnection exception : ", ex);
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   boolean registerConnection(Connection connection, boolean isClientConnection)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: iconst_0
/*     */     //   1: istore_3
/*     */     //   2: aload_1
/*     */     //   3: ifnull +147 -> 150
/*     */     //   6: aload_1
/*     */     //   7: invokevirtual 499	com/ankamagames/framework/kernel/core/net/Connection:getSocketChannel	()Ljava/nio/channels/SocketChannel;
/*     */     //   10: astore 4
/*     */     //   12: aload 4
/*     */     //   14: ifnull +136 -> 150
/*     */     //   17: aload_0
/*     */     //   18: getfield 475	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selector	Ljava/nio/channels/Selector;
/*     */     //   21: invokevirtual 539	java/nio/channels/Selector:wakeup	()Ljava/nio/channels/Selector;
/*     */     //   24: pop
/*     */     //   25: aload_0
/*     */     //   26: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   29: invokeinterface 573 1 0
/*     */     //   34: aload 4
/*     */     //   36: iconst_0
/*     */     //   37: invokevirtual 549	java/nio/channels/SocketChannel:configureBlocking	(Z)Ljava/nio/channels/SelectableChannel;
/*     */     //   40: pop
/*     */     //   41: aload 4
/*     */     //   43: invokevirtual 548	java/nio/channels/SocketChannel:socket	()Ljava/net/Socket;
/*     */     //   46: iconst_0
/*     */     //   47: invokevirtual 528	java/net/Socket:setKeepAlive	(Z)V
/*     */     //   50: aload 4
/*     */     //   52: invokevirtual 548	java/nio/channels/SocketChannel:socket	()Ljava/net/Socket;
/*     */     //   55: iconst_1
/*     */     //   56: invokevirtual 529	java/net/Socket:setTcpNoDelay	(Z)V
/*     */     //   59: aload 4
/*     */     //   61: aload_0
/*     */     //   62: getfield 475	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selector	Ljava/nio/channels/Selector;
/*     */     //   65: iconst_1
/*     */     //   66: iload_2
/*     */     //   67: ifeq +8 -> 75
/*     */     //   70: bipush 8
/*     */     //   72: goto +4 -> 76
/*     */     //   75: iconst_0
/*     */     //   76: ior
/*     */     //   77: aload_1
/*     */     //   78: invokevirtual 552	java/nio/channels/SocketChannel:register	(Ljava/nio/channels/Selector;ILjava/lang/Object;)Ljava/nio/channels/SelectionKey;
/*     */     //   81: pop
/*     */     //   82: aload_0
/*     */     //   83: dup
/*     */     //   84: getfield 463	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_connectionCount	I
/*     */     //   87: iconst_1
/*     */     //   88: iadd
/*     */     //   89: putfield 463	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_connectionCount	I
/*     */     //   92: aload_1
/*     */     //   93: iconst_1
/*     */     //   94: invokevirtual 495	com/ankamagames/framework/kernel/core/net/Connection:setRegistered	(Z)V
/*     */     //   97: iconst_1
/*     */     //   98: istore_3
/*     */     //   99: goto +42 -> 141
/*     */     //   102: astore 5
/*     */     //   104: getstatic 480	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_logger	Lorg/apache/log4j/Logger;
/*     */     //   107: ldc_w 242
/*     */     //   110: aload 5
/*     */     //   112: invokevirtual 558	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   115: aload_0
/*     */     //   116: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   119: invokeinterface 574 1 0
/*     */     //   124: goto +26 -> 150
/*     */     //   127: astore 6
/*     */     //   129: aload_0
/*     */     //   130: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   133: invokeinterface 574 1 0
/*     */     //   138: aload 6
/*     */     //   140: athrow
/*     */     //   141: aload_0
/*     */     //   142: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   145: invokeinterface 574 1 0
/*     */     //   150: iload_3
/*     */     //   151: ireturn
/*     */     // Line number table:
/*     */     //   Java source line #307	-> byte code offset #0
/*     */     //   Java source line #309	-> byte code offset #2
/*     */     //   Java source line #310	-> byte code offset #6
/*     */     //   Java source line #311	-> byte code offset #12
/*     */     //   Java source line #313	-> byte code offset #17
/*     */     //   Java source line #314	-> byte code offset #25
/*     */     //   Java source line #316	-> byte code offset #34
/*     */     //   Java source line #317	-> byte code offset #41
/*     */     //   Java source line #318	-> byte code offset #50
/*     */     //   Java source line #319	-> byte code offset #59
/*     */     //   Java source line #320	-> byte code offset #82
/*     */     //   Java source line #322	-> byte code offset #92
/*     */     //   Java source line #323	-> byte code offset #97
/*     */     //   Java source line #325	-> byte code offset #102
/*     */     //   Java source line #326	-> byte code offset #104
/*     */     //   Java source line #328	-> byte code offset #115
/*     */     //   Java source line #327	-> byte code offset #127
/*     */     //   Java source line #328	-> byte code offset #129
/*     */     //   Java source line #329	-> byte code offset #138
/*     */     //   Java source line #328	-> byte code offset #141
/*     */     //   Java source line #332	-> byte code offset #150
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	152	0	this	ConnectionHandler
/*     */     //   0	152	1	connection	Connection
/*     */     //   0	152	2	isClientConnection	boolean
/*     */     //   1	150	3	bRegistered	boolean
/*     */     //   10	50	4	channel	SocketChannel
/*     */     //   102	9	5	e	Throwable
/*     */     //   127	12	6	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   17	99	102	java/lang/Throwable
/*     */     //   17	115	127	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   boolean unregisterConnection(Connection connection)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: iconst_0
/*     */     //   1: istore_2
/*     */     //   2: aload_1
/*     */     //   3: ifnull +121 -> 124
/*     */     //   6: aload_1
/*     */     //   7: invokevirtual 499	com/ankamagames/framework/kernel/core/net/Connection:getSocketChannel	()Ljava/nio/channels/SocketChannel;
/*     */     //   10: astore_3
/*     */     //   11: aload_3
/*     */     //   12: ifnull +112 -> 124
/*     */     //   15: aload_0
/*     */     //   16: getfield 475	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selector	Ljava/nio/channels/Selector;
/*     */     //   19: invokevirtual 539	java/nio/channels/Selector:wakeup	()Ljava/nio/channels/Selector;
/*     */     //   22: pop
/*     */     //   23: aload_0
/*     */     //   24: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   27: invokeinterface 573 1 0
/*     */     //   32: aload_3
/*     */     //   33: aload_0
/*     */     //   34: getfield 475	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selector	Ljava/nio/channels/Selector;
/*     */     //   37: invokevirtual 551	java/nio/channels/SocketChannel:keyFor	(Ljava/nio/channels/Selector;)Ljava/nio/channels/SelectionKey;
/*     */     //   40: astore 4
/*     */     //   42: aload 4
/*     */     //   44: ifnull +8 -> 52
/*     */     //   47: aload 4
/*     */     //   49: invokevirtual 530	java/nio/channels/SelectionKey:cancel	()V
/*     */     //   52: aload_0
/*     */     //   53: dup
/*     */     //   54: getfield 463	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_connectionCount	I
/*     */     //   57: iconst_1
/*     */     //   58: isub
/*     */     //   59: putfield 463	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_connectionCount	I
/*     */     //   62: aload_3
/*     */     //   63: invokevirtual 546	java/nio/channels/SocketChannel:close	()V
/*     */     //   66: aload_1
/*     */     //   67: iconst_0
/*     */     //   68: invokevirtual 495	com/ankamagames/framework/kernel/core/net/Connection:setRegistered	(Z)V
/*     */     //   71: iconst_1
/*     */     //   72: istore_2
/*     */     //   73: goto +42 -> 115
/*     */     //   76: astore 4
/*     */     //   78: getstatic 480	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_logger	Lorg/apache/log4j/Logger;
/*     */     //   81: ldc_w 246
/*     */     //   84: aload 4
/*     */     //   86: invokevirtual 558	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   89: aload_0
/*     */     //   90: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   93: invokeinterface 574 1 0
/*     */     //   98: goto +26 -> 124
/*     */     //   101: astore 5
/*     */     //   103: aload_0
/*     */     //   104: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   107: invokeinterface 574 1 0
/*     */     //   112: aload 5
/*     */     //   114: athrow
/*     */     //   115: aload_0
/*     */     //   116: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   119: invokeinterface 574 1 0
/*     */     //   124: iload_2
/*     */     //   125: ireturn
/*     */     // Line number table:
/*     */     //   Java source line #341	-> byte code offset #0
/*     */     //   Java source line #343	-> byte code offset #2
/*     */     //   Java source line #344	-> byte code offset #6
/*     */     //   Java source line #345	-> byte code offset #11
/*     */     //   Java source line #347	-> byte code offset #15
/*     */     //   Java source line #348	-> byte code offset #23
/*     */     //   Java source line #350	-> byte code offset #32
/*     */     //   Java source line #351	-> byte code offset #42
/*     */     //   Java source line #352	-> byte code offset #47
/*     */     //   Java source line #354	-> byte code offset #52
/*     */     //   Java source line #356	-> byte code offset #62
/*     */     //   Java source line #358	-> byte code offset #66
/*     */     //   Java source line #359	-> byte code offset #71
/*     */     //   Java source line #361	-> byte code offset #76
/*     */     //   Java source line #362	-> byte code offset #78
/*     */     //   Java source line #364	-> byte code offset #89
/*     */     //   Java source line #363	-> byte code offset #101
/*     */     //   Java source line #364	-> byte code offset #103
/*     */     //   Java source line #365	-> byte code offset #112
/*     */     //   Java source line #364	-> byte code offset #115
/*     */     //   Java source line #368	-> byte code offset #124
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	126	0	this	ConnectionHandler
/*     */     //   0	126	1	connection	Connection
/*     */     //   1	124	2	bUnregistered	boolean
/*     */     //   10	53	3	channel	SocketChannel
/*     */     //   40	8	4	key	SelectionKey
/*     */     //   76	9	4	e	Throwable
/*     */     //   101	12	5	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   15	73	76	java/lang/Throwable
/*     */     //   15	89	101	finally
/*     */   }
/*     */   
/*     */   private boolean readKey(SelectionKey key)
/*     */   {
/*     */     try
/*     */     {
/* 376 */       if (key.isReadable()) {
/*     */         try
/*     */         {
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
/*     */         catch (Throwable e) {
/* 397 */           m_logger.error("read exception : ", e);
/* 398 */           return false;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 406 */       return true;
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 402 */       m_logger.error("key exception : ", e);
/* 403 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void closeConnection(final Connection connection)
/*     */   {
/* 414 */     if (connection.isAboutToClose()) {
/* 415 */       return;
/*     */     }
/* 417 */     connection.setAboutToClose(true);
/*     */     
/* 419 */     ProcessScheduler.getInstance().schedule(new Runnable()
/*     */     {
/*     */       /* Error */
/*     */       public void run()
/*     */       {
/*     */         // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: getfield 39	com/ankamagames/framework/kernel/core/net/ConnectionHandler$1:this$0	Lcom/ankamagames/framework/kernel/core/net/ConnectionHandler;
/*     */         //   4: invokestatic 41	com/ankamagames/framework/kernel/core/net/ConnectionHandler:access$0	(Lcom/ankamagames/framework/kernel/core/net/ConnectionHandler;)Ljava/util/concurrent/locks/Lock;
/*     */         //   7: invokeinterface 43 1 0
/*     */         //   12: aload_0
/*     */         //   13: getfield 38	com/ankamagames/framework/kernel/core/net/ConnectionHandler$1:val$connection	Lcom/ankamagames/framework/kernel/core/net/Connection;
/*     */         //   16: invokevirtual 40	com/ankamagames/framework/kernel/core/net/Connection:close	()V
/*     */         //   19: goto +18 -> 37
/*     */         //   22: astore_1
/*     */         //   23: aload_0
/*     */         //   24: getfield 39	com/ankamagames/framework/kernel/core/net/ConnectionHandler$1:this$0	Lcom/ankamagames/framework/kernel/core/net/ConnectionHandler;
/*     */         //   27: invokestatic 41	com/ankamagames/framework/kernel/core/net/ConnectionHandler:access$0	(Lcom/ankamagames/framework/kernel/core/net/ConnectionHandler;)Ljava/util/concurrent/locks/Lock;
/*     */         //   30: invokeinterface 44 1 0
/*     */         //   35: aload_1
/*     */         //   36: athrow
/*     */         //   37: aload_0
/*     */         //   38: getfield 39	com/ankamagames/framework/kernel/core/net/ConnectionHandler$1:this$0	Lcom/ankamagames/framework/kernel/core/net/ConnectionHandler;
/*     */         //   41: invokestatic 41	com/ankamagames/framework/kernel/core/net/ConnectionHandler:access$0	(Lcom/ankamagames/framework/kernel/core/net/ConnectionHandler;)Ljava/util/concurrent/locks/Lock;
/*     */         //   44: invokeinterface 44 1 0
/*     */         //   49: return
/*     */         // Line number table:
/*     */         //   Java source line #421	-> byte code offset #0
/*     */         //   Java source line #423	-> byte code offset #12
/*     */         //   Java source line #424	-> byte code offset #22
/*     */         //   Java source line #425	-> byte code offset #23
/*     */         //   Java source line #426	-> byte code offset #35
/*     */         //   Java source line #425	-> byte code offset #37
/*     */         //   Java source line #427	-> byte code offset #49
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	signature
/*     */         //   0	50	0	this	1
/*     */         //   22	14	1	localObject	Object
/*     */         // Exception table:
/*     */         //   from	to	target	type
/*     */         //   12	22	22	finally
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public void run()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: getstatic 480	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_logger	Lorg/apache/log4j/Logger;
/*     */     //   3: new 261	java/lang/StringBuilder
/*     */     //   6: dup
/*     */     //   7: ldc_w 232
/*     */     //   10: invokespecial 516	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   13: aload_0
/*     */     //   14: getfield 473	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_bindAddress	Ljava/lang/String;
/*     */     //   17: invokevirtual 519	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   20: ldc_w 230
/*     */     //   23: invokevirtual 519	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   26: aload_0
/*     */     //   27: getfield 461	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_bindPort	I
/*     */     //   30: invokevirtual 517	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*     */     //   33: invokevirtual 515	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   36: invokevirtual 557	org/apache/log4j/Logger:info	(Ljava/lang/Object;)V
/*     */     //   39: goto +580 -> 619
/*     */     //   42: iconst_0
/*     */     //   43: istore_1
/*     */     //   44: invokestatic 522	java/lang/Thread:yield	()V
/*     */     //   47: ldc2_w 226
/*     */     //   50: invokestatic 523	java/lang/Thread:sleep	(J)V
/*     */     //   53: aload_0
/*     */     //   54: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   57: invokeinterface 575 1 0
/*     */     //   62: ifeq +75 -> 137
/*     */     //   65: aload_0
/*     */     //   66: getfield 475	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selector	Ljava/nio/channels/Selector;
/*     */     //   69: ldc2_w 228
/*     */     //   72: invokevirtual 537	java/nio/channels/Selector:select	(J)I
/*     */     //   75: istore_1
/*     */     //   76: goto +52 -> 128
/*     */     //   79: astore_2
/*     */     //   80: getstatic 480	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_logger	Lorg/apache/log4j/Logger;
/*     */     //   83: ldc_w 245
/*     */     //   86: aload_2
/*     */     //   87: invokevirtual 558	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   90: goto +38 -> 128
/*     */     //   93: astore_2
/*     */     //   94: getstatic 480	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_logger	Lorg/apache/log4j/Logger;
/*     */     //   97: ldc_w 244
/*     */     //   100: aload_2
/*     */     //   101: invokevirtual 558	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   104: aload_0
/*     */     //   105: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   108: invokeinterface 574 1 0
/*     */     //   113: goto +24 -> 137
/*     */     //   116: astore_3
/*     */     //   117: aload_0
/*     */     //   118: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   121: invokeinterface 574 1 0
/*     */     //   126: aload_3
/*     */     //   127: athrow
/*     */     //   128: aload_0
/*     */     //   129: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   132: invokeinterface 574 1 0
/*     */     //   137: iload_1
/*     */     //   138: ifgt +6 -> 144
/*     */     //   141: goto +457 -> 598
/*     */     //   144: aload_0
/*     */     //   145: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   148: invokeinterface 573 1 0
/*     */     //   153: aload_0
/*     */     //   154: getfield 475	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selector	Ljava/nio/channels/Selector;
/*     */     //   157: invokevirtual 540	java/nio/channels/Selector:selectedKeys	()Ljava/util/Set;
/*     */     //   160: astore_2
/*     */     //   161: aload_2
/*     */     //   162: invokeinterface 572 1 0
/*     */     //   167: astore_3
/*     */     //   168: goto +372 -> 540
/*     */     //   171: aload_3
/*     */     //   172: invokeinterface 568 1 0
/*     */     //   177: checkcast 270	java/nio/channels/SelectionKey
/*     */     //   180: astore 4
/*     */     //   182: aload_3
/*     */     //   183: invokeinterface 566 1 0
/*     */     //   188: aload 4
/*     */     //   190: invokevirtual 534	java/nio/channels/SelectionKey:isValid	()Z
/*     */     //   193: ifeq +97 -> 290
/*     */     //   196: aload 4
/*     */     //   198: invokevirtual 531	java/nio/channels/SelectionKey:isAcceptable	()Z
/*     */     //   201: ifeq +89 -> 290
/*     */     //   204: aload_0
/*     */     //   205: getfield 476	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_channel	Ljava/nio/channels/ServerSocketChannel;
/*     */     //   208: invokevirtual 544	java/nio/channels/ServerSocketChannel:accept	()Ljava/nio/channels/SocketChannel;
/*     */     //   211: astore 6
/*     */     //   213: aload_0
/*     */     //   214: aload 6
/*     */     //   216: iconst_0
/*     */     //   217: invokespecial 506	com/ankamagames/framework/kernel/core/net/ConnectionHandler:createConnection	(Ljava/nio/channels/SocketChannel;Z)Lcom/ankamagames/framework/kernel/core/net/Connection;
/*     */     //   220: astore 5
/*     */     //   222: aload 5
/*     */     //   224: aload_0
/*     */     //   225: invokevirtual 496	com/ankamagames/framework/kernel/core/net/Connection:setConnectionHandler	(Lcom/ankamagames/framework/kernel/core/net/ConnectionHandler;)V
/*     */     //   228: aload 5
/*     */     //   230: aload_0
/*     */     //   231: getfield 471	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_eventsHandler	Lcom/ankamagames/framework/kernel/events/NetworkEventsHandler;
/*     */     //   234: invokevirtual 497	com/ankamagames/framework/kernel/core/net/Connection:setNetworkEventsHandler	(Lcom/ankamagames/framework/kernel/events/NetworkEventsHandler;)V
/*     */     //   237: aload 5
/*     */     //   239: aload 6
/*     */     //   241: invokevirtual 500	com/ankamagames/framework/kernel/core/net/Connection:setSocketChannel	(Ljava/nio/channels/SocketChannel;)V
/*     */     //   244: aload 5
/*     */     //   246: iconst_1
/*     */     //   247: invokevirtual 492	com/ankamagames/framework/kernel/core/net/Connection:setConnectionEstablished	(Z)V
/*     */     //   250: aload_0
/*     */     //   251: getfield 471	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_eventsHandler	Lcom/ankamagames/framework/kernel/events/NetworkEventsHandler;
/*     */     //   254: aload_0
/*     */     //   255: aload 5
/*     */     //   257: invokeinterface 565 3 0
/*     */     //   262: ifne +278 -> 540
/*     */     //   265: aload_0
/*     */     //   266: aload 5
/*     */     //   268: invokevirtual 501	com/ankamagames/framework/kernel/core/net/ConnectionHandler:closeConnection	(Lcom/ankamagames/framework/kernel/core/net/Connection;)V
/*     */     //   271: goto +269 -> 540
/*     */     //   274: astore 6
/*     */     //   276: getstatic 480	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_logger	Lorg/apache/log4j/Logger;
/*     */     //   279: ldc_w 238
/*     */     //   282: aload 6
/*     */     //   284: invokevirtual 558	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   287: goto +253 -> 540
/*     */     //   290: aload 4
/*     */     //   292: invokevirtual 534	java/nio/channels/SelectionKey:isValid	()Z
/*     */     //   295: ifeq +94 -> 389
/*     */     //   298: aload 4
/*     */     //   300: invokevirtual 532	java/nio/channels/SelectionKey:isConnectable	()Z
/*     */     //   303: ifeq +86 -> 389
/*     */     //   306: aload 4
/*     */     //   308: invokevirtual 536	java/nio/channels/SelectionKey:channel	()Ljava/nio/channels/SelectableChannel;
/*     */     //   311: checkcast 273	java/nio/channels/SocketChannel
/*     */     //   314: astore 6
/*     */     //   316: aload 4
/*     */     //   318: invokevirtual 535	java/nio/channels/SelectionKey:attachment	()Ljava/lang/Object;
/*     */     //   321: checkcast 250	com/ankamagames/framework/kernel/core/net/Connection
/*     */     //   324: astore 5
/*     */     //   326: aload 6
/*     */     //   328: invokevirtual 547	java/nio/channels/SocketChannel:finishConnect	()Z
/*     */     //   331: ifeq +36 -> 367
/*     */     //   334: aload_0
/*     */     //   335: getfield 471	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_eventsHandler	Lcom/ankamagames/framework/kernel/events/NetworkEventsHandler;
/*     */     //   338: aload_0
/*     */     //   339: aload 5
/*     */     //   341: invokeinterface 565 3 0
/*     */     //   346: ifne +12 -> 358
/*     */     //   349: aload_0
/*     */     //   350: aload 5
/*     */     //   352: invokevirtual 501	com/ankamagames/framework/kernel/core/net/ConnectionHandler:closeConnection	(Lcom/ankamagames/framework/kernel/core/net/Connection;)V
/*     */     //   355: goto +185 -> 540
/*     */     //   358: aload 5
/*     */     //   360: iconst_1
/*     */     //   361: invokevirtual 493	com/ankamagames/framework/kernel/core/net/Connection:setConnectionHasBeenEstablished	(Z)V
/*     */     //   364: goto +176 -> 540
/*     */     //   367: aload_0
/*     */     //   368: getfield 471	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_eventsHandler	Lcom/ankamagames/framework/kernel/events/NetworkEventsHandler;
/*     */     //   371: aload_0
/*     */     //   372: aload 5
/*     */     //   374: invokeinterface 563 3 0
/*     */     //   379: pop
/*     */     //   380: aload_0
/*     */     //   381: aload 5
/*     */     //   383: invokevirtual 501	com/ankamagames/framework/kernel/core/net/ConnectionHandler:closeConnection	(Lcom/ankamagames/framework/kernel/core/net/Connection;)V
/*     */     //   386: goto +154 -> 540
/*     */     //   389: aload 4
/*     */     //   391: invokevirtual 534	java/nio/channels/SelectionKey:isValid	()Z
/*     */     //   394: ifeq +146 -> 540
/*     */     //   397: aload_0
/*     */     //   398: aload 4
/*     */     //   400: invokespecial 505	com/ankamagames/framework/kernel/core/net/ConnectionHandler:readKey	(Ljava/nio/channels/SelectionKey;)Z
/*     */     //   403: ifne +137 -> 540
/*     */     //   406: aload_0
/*     */     //   407: aload 4
/*     */     //   409: invokevirtual 535	java/nio/channels/SelectionKey:attachment	()Ljava/lang/Object;
/*     */     //   412: checkcast 250	com/ankamagames/framework/kernel/core/net/Connection
/*     */     //   415: invokevirtual 501	com/ankamagames/framework/kernel/core/net/ConnectionHandler:closeConnection	(Lcom/ankamagames/framework/kernel/core/net/Connection;)V
/*     */     //   418: goto +122 -> 540
/*     */     //   421: astore 6
/*     */     //   423: aload 4
/*     */     //   425: invokevirtual 535	java/nio/channels/SelectionKey:attachment	()Ljava/lang/Object;
/*     */     //   428: checkcast 250	com/ankamagames/framework/kernel/core/net/Connection
/*     */     //   431: astore 5
/*     */     //   433: aload_0
/*     */     //   434: getfield 471	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_eventsHandler	Lcom/ankamagames/framework/kernel/events/NetworkEventsHandler;
/*     */     //   437: aload_0
/*     */     //   438: aload 5
/*     */     //   440: invokeinterface 563 3 0
/*     */     //   445: pop
/*     */     //   446: aload 5
/*     */     //   448: invokevirtual 487	com/ankamagames/framework/kernel/core/net/Connection:isPersistant	()Z
/*     */     //   451: ifeq +11 -> 462
/*     */     //   454: aload 5
/*     */     //   456: invokevirtual 485	com/ankamagames/framework/kernel/core/net/Connection:scheduleReconnection	()V
/*     */     //   459: goto +81 -> 540
/*     */     //   462: aload_0
/*     */     //   463: aload 5
/*     */     //   465: invokevirtual 501	com/ankamagames/framework/kernel/core/net/ConnectionHandler:closeConnection	(Lcom/ankamagames/framework/kernel/core/net/Connection;)V
/*     */     //   468: goto +72 -> 540
/*     */     //   471: astore 6
/*     */     //   473: getstatic 480	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_logger	Lorg/apache/log4j/Logger;
/*     */     //   476: ldc_w 240
/*     */     //   479: aload 6
/*     */     //   481: invokevirtual 558	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   484: aload 4
/*     */     //   486: invokevirtual 535	java/nio/channels/SelectionKey:attachment	()Ljava/lang/Object;
/*     */     //   489: checkcast 250	com/ankamagames/framework/kernel/core/net/Connection
/*     */     //   492: astore 5
/*     */     //   494: getstatic 481	java/lang/System:err	Ljava/io/PrintStream;
/*     */     //   497: new 261	java/lang/StringBuilder
/*     */     //   500: dup
/*     */     //   501: ldc_w 231
/*     */     //   504: invokespecial 516	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   507: aload 5
/*     */     //   509: invokevirtual 518	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   512: invokevirtual 515	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   515: invokevirtual 510	java/io/PrintStream:println	(Ljava/lang/String;)V
/*     */     //   518: aload 5
/*     */     //   520: invokevirtual 487	com/ankamagames/framework/kernel/core/net/Connection:isPersistant	()Z
/*     */     //   523: ifeq +11 -> 534
/*     */     //   526: aload 5
/*     */     //   528: invokevirtual 485	com/ankamagames/framework/kernel/core/net/Connection:scheduleReconnection	()V
/*     */     //   531: goto +9 -> 540
/*     */     //   534: aload_0
/*     */     //   535: aload 5
/*     */     //   537: invokevirtual 501	com/ankamagames/framework/kernel/core/net/ConnectionHandler:closeConnection	(Lcom/ankamagames/framework/kernel/core/net/Connection;)V
/*     */     //   540: aload_3
/*     */     //   541: invokeinterface 567 1 0
/*     */     //   546: ifne -375 -> 171
/*     */     //   549: goto +40 -> 589
/*     */     //   552: astore_2
/*     */     //   553: getstatic 480	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_logger	Lorg/apache/log4j/Logger;
/*     */     //   556: ldc_w 244
/*     */     //   559: aload_2
/*     */     //   560: invokevirtual 558	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   563: aload_0
/*     */     //   564: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   567: invokeinterface 574 1 0
/*     */     //   572: goto +26 -> 598
/*     */     //   575: astore 7
/*     */     //   577: aload_0
/*     */     //   578: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   581: invokeinterface 574 1 0
/*     */     //   586: aload 7
/*     */     //   588: athrow
/*     */     //   589: aload_0
/*     */     //   590: getfield 478	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_selectorLock	Ljava/util/concurrent/locks/Lock;
/*     */     //   593: invokeinterface 574 1 0
/*     */     //   598: aload_0
/*     */     //   599: getfield 470	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_running	Z
/*     */     //   602: ifne -560 -> 42
/*     */     //   605: goto +14 -> 619
/*     */     //   608: astore_1
/*     */     //   609: getstatic 480	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_logger	Lorg/apache/log4j/Logger;
/*     */     //   612: ldc_w 244
/*     */     //   615: aload_1
/*     */     //   616: invokevirtual 558	org/apache/log4j/Logger:error	(Ljava/lang/Object;Ljava/lang/Throwable;)V
/*     */     //   619: aload_0
/*     */     //   620: getfield 470	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_running	Z
/*     */     //   623: ifne -25 -> 598
/*     */     //   626: getstatic 480	com/ankamagames/framework/kernel/core/net/ConnectionHandler:m_logger	Lorg/apache/log4j/Logger;
/*     */     //   629: ldc_w 233
/*     */     //   632: invokevirtual 557	org/apache/log4j/Logger:info	(Ljava/lang/Object;)V
/*     */     //   635: return
/*     */     // Line number table:
/*     */     //   Java source line #436	-> byte code offset #0
/*     */     //   Java source line #438	-> byte code offset #39
/*     */     //   Java source line #443	-> byte code offset #42
/*     */     //   Java source line #445	-> byte code offset #44
/*     */     //   Java source line #446	-> byte code offset #47
/*     */     //   Java source line #448	-> byte code offset #53
/*     */     //   Java source line #451	-> byte code offset #65
/*     */     //   Java source line #452	-> byte code offset #79
/*     */     //   Java source line #453	-> byte code offset #80
/*     */     //   Java source line #455	-> byte code offset #93
/*     */     //   Java source line #456	-> byte code offset #94
/*     */     //   Java source line #458	-> byte code offset #104
/*     */     //   Java source line #457	-> byte code offset #116
/*     */     //   Java source line #458	-> byte code offset #117
/*     */     //   Java source line #459	-> byte code offset #126
/*     */     //   Java source line #458	-> byte code offset #128
/*     */     //   Java source line #464	-> byte code offset #137
/*     */     //   Java source line #465	-> byte code offset #141
/*     */     //   Java source line #468	-> byte code offset #144
/*     */     //   Java source line #470	-> byte code offset #153
/*     */     //   Java source line #472	-> byte code offset #161
/*     */     //   Java source line #474	-> byte code offset #168
/*     */     //   Java source line #475	-> byte code offset #171
/*     */     //   Java source line #476	-> byte code offset #182
/*     */     //   Java source line #484	-> byte code offset #188
/*     */     //   Java source line #486	-> byte code offset #204
/*     */     //   Java source line #487	-> byte code offset #213
/*     */     //   Java source line #488	-> byte code offset #222
/*     */     //   Java source line #489	-> byte code offset #228
/*     */     //   Java source line #490	-> byte code offset #237
/*     */     //   Java source line #491	-> byte code offset #244
/*     */     //   Java source line #493	-> byte code offset #250
/*     */     //   Java source line #494	-> byte code offset #265
/*     */     //   Java source line #496	-> byte code offset #274
/*     */     //   Java source line #497	-> byte code offset #276
/*     */     //   Java source line #500	-> byte code offset #290
/*     */     //   Java source line #501	-> byte code offset #306
/*     */     //   Java source line #502	-> byte code offset #316
/*     */     //   Java source line #504	-> byte code offset #326
/*     */     //   Java source line #505	-> byte code offset #334
/*     */     //   Java source line #506	-> byte code offset #349
/*     */     //   Java source line #508	-> byte code offset #358
/*     */     //   Java source line #510	-> byte code offset #367
/*     */     //   Java source line #511	-> byte code offset #380
/*     */     //   Java source line #514	-> byte code offset #389
/*     */     //   Java source line #515	-> byte code offset #406
/*     */     //   Java source line #518	-> byte code offset #421
/*     */     //   Java source line #519	-> byte code offset #423
/*     */     //   Java source line #520	-> byte code offset #433
/*     */     //   Java source line #521	-> byte code offset #446
/*     */     //   Java source line #522	-> byte code offset #454
/*     */     //   Java source line #524	-> byte code offset #462
/*     */     //   Java source line #527	-> byte code offset #471
/*     */     //   Java source line #528	-> byte code offset #473
/*     */     //   Java source line #529	-> byte code offset #484
/*     */     //   Java source line #530	-> byte code offset #494
/*     */     //   Java source line #531	-> byte code offset #518
/*     */     //   Java source line #532	-> byte code offset #526
/*     */     //   Java source line #534	-> byte code offset #534
/*     */     //   Java source line #474	-> byte code offset #540
/*     */     //   Java source line #538	-> byte code offset #552
/*     */     //   Java source line #539	-> byte code offset #553
/*     */     //   Java source line #541	-> byte code offset #563
/*     */     //   Java source line #540	-> byte code offset #575
/*     */     //   Java source line #541	-> byte code offset #577
/*     */     //   Java source line #542	-> byte code offset #586
/*     */     //   Java source line #541	-> byte code offset #589
/*     */     //   Java source line #441	-> byte code offset #598
/*     */     //   Java source line #546	-> byte code offset #608
/*     */     //   Java source line #547	-> byte code offset #609
/*     */     //   Java source line #438	-> byte code offset #619
/*     */     //   Java source line #552	-> byte code offset #626
/*     */     //   Java source line #553	-> byte code offset #635
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	636	0	this	ConnectionHandler
/*     */     //   43	95	1	selectResult	int
/*     */     //   608	8	1	ioex	Throwable
/*     */     //   79	8	2	ex	Throwable
/*     */     //   93	8	2	e	Throwable
/*     */     //   160	2	2	keys	java.util.Set
/*     */     //   552	8	2	e	Throwable
/*     */     //   116	11	3	localObject1	Object
/*     */     //   167	374	3	it	java.util.Iterator
/*     */     //   180	305	4	key	SelectionKey
/*     */     //   220	47	5	connection	Connection
/*     */     //   324	58	5	connection	Connection
/*     */     //   431	33	5	connection	Connection
/*     */     //   492	44	5	connection	Connection
/*     */     //   211	29	6	clientChannel	SocketChannel
/*     */     //   274	9	6	ex	Throwable
/*     */     //   314	13	6	channel	SocketChannel
/*     */     //   421	3	6	ce	java.net.ConnectException
/*     */     //   471	9	6	e	Throwable
/*     */     //   575	12	7	localObject2	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   65	76	79	java/lang/Throwable
/*     */     //   65	90	93	java/lang/Throwable
/*     */     //   65	104	116	finally
/*     */     //   204	271	274	java/lang/Throwable
/*     */     //   188	418	421	java/net/ConnectException
/*     */     //   188	418	471	java/lang/Throwable
/*     */     //   144	549	552	java/lang/Throwable
/*     */     //   144	563	575	finally
/*     */     //   42	605	608	java/lang/Throwable
/*     */   }
/*     */   
/*     */   public void storeException(Throwable ex)
/*     */   {
/* 561 */     StringWriter strException = ExceptionFormatter.toString(ex);
/* 562 */     m_logger.error("Exception raised : ", ex);
/*     */     
/* 564 */     this.m_numRaisedExceptions += 1;
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
/*     */   public String getExternalName()
/*     */   {
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
/*     */   public void setConnectionRetryDelay(int connectionRetryDelay)
/*     */   {
/* 598 */     this.m_connectionRetryDelay = connectionRetryDelay;
/*     */   }
/*     */   
/*     */   public void setMaxConnectionRetries(int maxConnectionRetries) {
/* 602 */     this.m_maxConnectionRetries = maxConnectionRetries;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\net\ConnectionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */