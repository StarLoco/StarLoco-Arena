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
/*     */ import com.ankamagames.framework.kernel.utils.ExceptionFormatter;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerInstance
/*     */   implements NetworkEventsHandler, ServerEventsHandler, MessageDecoder
/*     */ {
/*  35 */   private static final HashMap<String, ServerInstance> m_listeners = new HashMap();
/*     */   
/*     */   public static void registerListener(String name, ServerInstance connector) {
/*  38 */     m_listeners.put(name, connector);
/*     */   }
/*     */   
/*     */   public static ServerInstance getListener(String name) {
/*  42 */     return (ServerInstance)m_listeners.get(name);
/*     */   }
/*     */   
/*     */   public static int getListenersCount() {
/*  46 */     return m_listeners.size();
/*     */   }
/*     */   
/*     */   public static Iterable<String> getListeners() {
/*  50 */     return (Iterable)m_listeners.keySet();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  57 */   protected static final Logger m_logger = Logger.getLogger(ServerInstance.class);
/*     */   
/*     */   public final ConnectionHandler m_connectionHandler;
/*     */   public NetworkEventsHandler m_connectionEventHandler;
/*     */   public ServerEventsHandler m_serverEventsHandler;
/*     */   public MessageDecoder m_messageDecoder;
/*     */   public ObjectPool m_entityPool;
/*  64 */   private boolean m_releaseEntityOnDisconnection = true;
/*     */   
/*  66 */   private final Object m_connectionsMutex = new Object();
/*  67 */   private final ArrayList<Connection> m_connections = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */   public ServerInstance(String listenerName)
/*     */   {
/*  73 */     this.m_connectionHandler = new ConnectionHandler(this);
/*  74 */     registerListener(listenerName, this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isRunning()
/*     */   {
/*  82 */     if (this.m_connectionHandler != null)
/*  83 */       return this.m_connectionHandler.isRunning();
/*  84 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setReleaseEntityOnDisconnection(boolean releaseEntityOnDisconnection)
/*     */   {
/*  94 */     this.m_releaseEntityOnDisconnection = releaseEntityOnDisconnection;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initialize(String bindAddress, int bindPort)
/*     */     throws Exception
/*     */   {
/* 105 */     if (this.m_messageDecoder == null) {
/* 106 */       throw new Exception("Le décodeur de messages n'a pas été spécifié");
/*     */     }
/* 108 */     if (this.m_entityPool == null) {
/* 109 */       throw new Exception("Le pool de ConnectionUser+MessageUser n'a pas été spécifié");
/*     */     }
/* 111 */     this.m_connectionHandler.initialize(bindAddress, bindPort);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void start()
/*     */   {
/* 118 */     this.m_connectionHandler.setOnline(true);
/* 119 */     this.m_connectionHandler.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void registerNetworkEventsHandler(NetworkEventsHandler handler)
/*     */   {
/* 127 */     this.m_connectionEventHandler = handler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void registerServerEventsHandler(ServerEventsHandler handler)
/*     */   {
/* 135 */     this.m_serverEventsHandler = handler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void registerMessageDecoder(MessageDecoder messageDecoder)
/*     */   {
/* 143 */     this.m_messageDecoder = messageDecoder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void registerEntityFactory(ObjectFactory<? extends FrameworkEntity> factory)
/*     */   {
/* 152 */     this.m_entityPool = new MonitoredPool(factory);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onConnectionHandlerCreationError(ConnectionHandler ch)
/*     */   {
/* 158 */     if (this.m_connectionEventHandler != null)
/* 159 */       return this.m_connectionEventHandler.onConnectionHandlerCreationError(ch);
/* 160 */     return false;
/*     */   }
/*     */   
/*     */   public boolean onConnectionHandlerInitializationError(ConnectionHandler ch) {
/* 164 */     if (this.m_connectionEventHandler != null)
/* 165 */       return this.m_connectionEventHandler.onConnectionHandlerInitializationError(ch);
/* 166 */     return false;
/*     */   }
/*     */   
/*     */   public boolean onConnectionHandlerInLoopError(ConnectionHandler ch) {
/* 170 */     if (this.m_connectionEventHandler != null)
/* 171 */       return this.m_connectionEventHandler.onConnectionHandlerInLoopError(ch);
/* 172 */     return false;
/*     */   }
/*     */   
/*     */   public synchronized boolean onNewConnection(ConnectionHandler ch, Connection connection)
/*     */   {
/*     */     try
/*     */     {
/* 179 */       FrameworkEntity user = (FrameworkEntity)this.m_entityPool.borrowObject();
/*     */       
/* 181 */       if (user == null) {
/* 182 */         return false;
/*     */       }
/* 184 */       user.setPool(this.m_entityPool);
/* 185 */       user.setConnection(connection);
/* 186 */       connection.setUser(user);
/* 187 */       user.onConnect();
/*     */       
/* 189 */       synchronized (this.m_connectionsMutex) {
/* 190 */         this.m_connections.add(connection);
/*     */       }
/*     */       
/* 193 */       if (this.m_connectionEventHandler != null) {
/* 194 */         return this.m_connectionEventHandler.onNewConnection(ch, connection);
/*     */       }
/*     */     } catch (Throwable ex) {
/* 197 */       m_logger.error("ServerInstance exception : ", ex);
/* 198 */       return false;
/*     */     }
/*     */     
/* 201 */     return true;
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
/*     */   public synchronized boolean onConnectionReadyRead(ConnectionHandler ch, Connection connection)
/*     */   {
/* 216 */     ByteBuffer rawMessage = connection.read();
/*     */     
/* 218 */     if (rawMessage != null)
/*     */     {
/*     */       Message message;
/*     */       do
/*     */       {
/* 223 */         message = this.m_messageDecoder.decode(rawMessage);
/* 224 */         if (message != null) {
/* 225 */           if (message.getHandler() == null)
/* 226 */             message.setHandler((MessageHandler)connection.getUser());
/* 227 */           Worker.getInstance().pushMessage(message);
/*     */         }
/* 229 */       } while (message != null);
/*     */       
/* 231 */       rawMessage.compact();
/*     */       
/* 233 */       return true;
/*     */     }
/* 235 */     return false;
/*     */   }
/*     */   
/*     */   public synchronized boolean onConnectionError(ConnectionHandler ch, Connection connection)
/*     */   {
/* 240 */     boolean retValue = true;
/*     */     try
/*     */     {
/* 243 */       if (this.m_connectionEventHandler != null) {
/* 244 */         retValue = this.m_connectionEventHandler.onConnectionError(ch, connection);
/*     */       }
/*     */     } catch (Exception ex) {
/* 247 */       m_logger.error(ExceptionFormatter.toString(ex));
/*     */     }
/*     */     
/* 250 */     return retValue;
/*     */   }
/*     */   
/*     */   public synchronized boolean onConnectionClose(ConnectionHandler ch, Connection connection)
/*     */   {
/* 255 */     boolean bRetValue = true;
/*     */     try
/*     */     {
/* 258 */       synchronized (this.m_connectionsMutex) {
/* 259 */         this.m_connections.remove(connection);
/*     */       }
/*     */       
/* 262 */       if (this.m_connectionEventHandler != null) {
/* 263 */         bRetValue = this.m_connectionEventHandler.onConnectionClose(ch, connection);
/*     */       }
/*     */       
/*     */ 
/* 267 */       if (this.m_releaseEntityOnDisconnection) {
/* 268 */         FrameworkEntity entity = (FrameworkEntity)connection.getUser();
/* 269 */         if (entity != null) {
/* 270 */           entity.release();
/*     */         }
/*     */       }
/*     */     } catch (Exception ex) {
/* 274 */       m_logger.error(ExceptionFormatter.toString(ex));
/* 275 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 279 */     return bRetValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized boolean onConnectionRecovered(ConnectionHandler ch, Connection connection)
/*     */   {
/* 290 */     FrameworkEntity entity = (FrameworkEntity)connection.getUser();
/* 291 */     if (entity != null) {
/* 292 */       entity.onReconnectionPending();
/*     */     }
/* 294 */     if (this.m_connectionEventHandler != null) {
/* 295 */       return this.m_connectionEventHandler.onConnectionRecovered(ch, connection);
/*     */     }
/* 297 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void onReconnectionScheduled(ConnectionHandler ch, Connection connection)
/*     */   {
/* 308 */     if (this.m_connectionEventHandler != null) {
/* 309 */       this.m_connectionEventHandler.onReconnectionScheduled(ch, connection);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Message decode(ByteBuffer rawMessage)
/*     */   {
/* 318 */     if (this.m_messageDecoder != null)
/* 319 */       return this.m_messageDecoder.decode(rawMessage);
/* 320 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ConnectionHandler getConnectionHandler()
/*     */   {
/* 328 */     return this.m_connectionHandler;
/*     */   }
/*     */   
/*     */   public void onEvent(FrameworkEvent event) {}
/*     */   
/*     */   /* Error */
/*     */   public int getConnectionCount()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 253	com/ankamagames/framework/kernel/ServerInstance:m_connectionsMutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 254	com/ankamagames/framework/kernel/ServerInstance:m_connections	Ljava/util/ArrayList;
/*     */     //   11: invokevirtual 281	java/util/ArrayList:size	()I
/*     */     //   14: aload_1
/*     */     //   15: monitorexit
/*     */     //   16: ireturn
/*     */     //   17: aload_1
/*     */     //   18: monitorexit
/*     */     //   19: athrow
/*     */     // Line number table:
/*     */     //   Java source line #336	-> byte code offset #0
/*     */     //   Java source line #337	-> byte code offset #7
/*     */     //   Java source line #336	-> byte code offset #17
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	20	0	this	ServerInstance
/*     */     //   5	13	1	Ljava/lang/Object;	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	16	17	finally
/*     */     //   17	19	17	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public Iterable<Connection> getConnections()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 253	com/ankamagames/framework/kernel/ServerInstance:m_connectionsMutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 254	com/ankamagames/framework/kernel/ServerInstance:m_connections	Ljava/util/ArrayList;
/*     */     //   11: aload_1
/*     */     //   12: monitorexit
/*     */     //   13: areturn
/*     */     //   14: aload_1
/*     */     //   15: monitorexit
/*     */     //   16: athrow
/*     */     // Line number table:
/*     */     //   Java source line #342	-> byte code offset #0
/*     */     //   Java source line #343	-> byte code offset #7
/*     */     //   Java source line #342	-> byte code offset #14
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	17	0	this	ServerInstance
/*     */     //   5	10	1	Ljava/lang/Object;	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	13	14	finally
/*     */     //   14	16	14	finally
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\ServerInstance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */