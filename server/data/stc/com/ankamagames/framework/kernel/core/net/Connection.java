/*     */ package com.ankamagames.framework.kernel.core.net;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.common.Validable;
/*     */ import com.ankamagames.framework.kernel.core.common.message.ProcessScheduler;
/*     */ import com.ankamagames.framework.kernel.events.NetworkEventsHandler;
/*     */ import com.ankamagames.framework.kernel.utils.BinaryFormatter;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SocketChannel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Connection
/*     */   implements Validable, Poolable
/*     */ {
/*     */   private static final boolean CHECK_MESSAGE_SIZE = true;
/*  40 */   private static final Logger m_logger = Logger.getLogger(Connection.class);
/*     */   
/*     */   private SocketChannel m_channel;
/*     */   
/*     */   protected ConnectionUser m_user;
/*     */   
/*     */   protected NetworkEventsHandler m_handler;
/*     */   
/*     */   protected ConnectionHandler m_connectionHandler;
/*     */   protected boolean m_persistant;
/*     */   protected String m_host;
/*     */   protected int m_port;
/*     */   protected boolean m_retrying;
/*     */   protected ByteBuffer m_outBuffer;
/*     */   protected ByteBuffer m_inBuffer;
/*  55 */   private long m_numBytesSent = 0L;
/*  56 */   private long m_numBytesReceived = 0L;
/*     */   
/*     */   private boolean m_cleanClose;
/*     */   
/*  60 */   protected final Object m_outBufferMutex = new Object();
/*     */   
/*  62 */   private static int m_globalId = 1;
/*     */   
/*     */   protected long m_id;
/*     */   
/*  66 */   private int m_connectionRetryCount = 0;
/*  67 */   private int m_maxConnectionRetries = Integer.MAX_VALUE;
/*  68 */   private int m_connectionRetryDelay = 500;
/*     */   
/*     */   private boolean m_connectionEstablished;
/*     */   
/*     */   private boolean m_connectionHasBeenEstablished;
/*     */   
/*     */   private boolean m_aboutToClose;
/*     */   
/*     */   private boolean m_registered;
/*     */   
/*     */   private static final int OUTPUT_BUFFER_INITIAL_SIZE = 8192;
/*     */   private static final int INPUT_BUFFER_INITIAL_SIZE = 8192;
/*     */   
/*     */   public Connection()
/*     */   {
/*  83 */     this.m_id = 0L;
/*  84 */     this.m_persistant = false;
/*  85 */     this.m_retrying = false;
/*     */     
/*  87 */     this.m_inBuffer = ByteBuffer.allocate(8192);
/*  88 */     this.m_inBuffer.clear();
/*     */     
/*  90 */     this.m_outBuffer = ByteBuffer.allocate(8192);
/*  91 */     this.m_outBuffer.clear();
/*     */     
/*  93 */     this.m_cleanClose = false;
/*     */     
/*  95 */     this.m_connectionEstablished = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onCheckOut()
/*     */   {
/* 102 */     this.m_id = (m_globalId++);
/* 103 */     this.m_connectionEstablished = false;
/* 104 */     this.m_aboutToClose = false;
/* 105 */     this.m_registered = false;
/* 106 */     this.m_cleanClose = false;
/* 107 */     this.m_connectionHasBeenEstablished = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onCheckIn()
/*     */   {
/* 114 */     this.m_id = 0L;
/* 115 */     this.m_connectionEstablished = false;
/* 116 */     this.m_aboutToClose = false;
/* 117 */     this.m_registered = false;
/* 118 */     this.m_connectionHasBeenEstablished = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 127 */     return this.m_id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(long id)
/*     */   {
/* 136 */     this.m_id = id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setUser(ConnectionUser user)
/*     */   {
/* 145 */     this.m_user = user;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ConnectionUser getUser()
/*     */   {
/* 154 */     return this.m_user;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setConnectionHandler(ConnectionHandler handler)
/*     */   {
/* 163 */     this.m_connectionHandler = handler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ConnectionHandler getConnectionHandler()
/*     */   {
/* 172 */     return this.m_connectionHandler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setNetworkEventsHandler(NetworkEventsHandler handler)
/*     */   {
/* 181 */     this.m_handler = handler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NetworkEventsHandler getNetworkEventsHandler()
/*     */   {
/* 188 */     return this.m_handler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setSocketChannel(SocketChannel channel)
/*     */   {
/* 197 */     this.m_channel = channel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   SocketChannel getSocketChannel()
/*     */   {
/* 206 */     return this.m_channel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isRegistered()
/*     */   {
/* 213 */     return this.m_registered;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setRegistered(boolean registered)
/*     */   {
/* 220 */     this.m_registered = registered;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isConnectionHasBeenEstablished()
/*     */   {
/* 227 */     return this.m_connectionHasBeenEstablished;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setConnectionHasBeenEstablished(boolean connectionHasBeenEstablished)
/*     */   {
/* 234 */     this.m_connectionHasBeenEstablished = connectionHasBeenEstablished;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void connectToHost(String host, int port)
/*     */     throws Exception
/*     */   {
/* 244 */     this.m_host = host;
/* 245 */     this.m_port = port;
/* 246 */     this.m_connectionEstablished = false;
/*     */     try
/*     */     {
/* 249 */       if (this.m_channel == null) {
/* 250 */         this.m_channel = SocketChannel.open();
/*     */       }
/* 252 */       if (!this.m_channel.isOpen()) {
/* 253 */         this.m_channel = SocketChannel.open();
/*     */       }
/* 255 */       if (!this.m_channel.isConnected()) {
/* 256 */         this.m_channel.connect(new InetSocketAddress(host, port));
/*     */       }
/*     */       
/* 259 */       this.m_connectionEstablished = this.m_channel.isConnected();
/*     */     }
/*     */     catch (IOException e) {
/* 262 */       this.m_handler.onConnectionError(this.m_connectionHandler, this);
/*     */       
/* 264 */       throw new IOException("Impossible de se connecter à l'hote spécifié : host=" + host + ", port=" + port + "(persistant=" + this.m_persistant + ", retries=" + this.m_connectionRetryCount + "/" + this.m_maxConnectionRetries + ")");
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
/*     */ 
/*     */ 
/*     */ 
/*     */   void close()
/*     */   {
/* 280 */     this.m_outBuffer.clear();
/* 281 */     this.m_inBuffer.clear();
/*     */     
/* 283 */     if (this.m_user != null) {
/* 284 */       if (this.m_connectionEstablished)
/* 285 */         this.m_user.onDisconnect();
/* 286 */       this.m_user.setConnection(null);
/*     */     }
/*     */     
/* 289 */     if ((this.m_handler != null) && (this.m_connectionHandler != null))
/*     */     {
/* 291 */       this.m_handler.onConnectionClose(this.m_connectionHandler, this);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 296 */     this.m_user = null;
/*     */     
/* 298 */     if (this.m_connectionHandler != null) {
/* 299 */       this.m_id = 0L;
/* 300 */       this.m_connectionEstablished = false;
/* 301 */       this.m_connectionHandler.releaseConnection(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void cleanClose()
/*     */   {
/* 311 */     this.m_cleanClose = true;
/*     */   }
/*     */   
/*     */   public boolean isCleanClose() {
/* 315 */     return this.m_cleanClose;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAboutToClose()
/*     */   {
/* 325 */     return this.m_aboutToClose;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setAboutToClose(boolean aboutToClose)
/*     */   {
/* 332 */     this.m_aboutToClose = aboutToClose;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isPersistant()
/*     */   {
/* 339 */     return (this.m_persistant) && (this.m_maxConnectionRetries > 0);
/*     */   }
/*     */   
/*     */   public void setPersistant(boolean persistant) {
/* 343 */     this.m_persistant = persistant;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isConnected()
/*     */   {
/* 352 */     if (this.m_channel != null) {
/* 353 */       return this.m_channel.isConnected();
/*     */     }
/* 355 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isConnectionPending()
/*     */   {
/* 362 */     if (this.m_channel != null) {
/* 363 */       return this.m_channel.isConnectionPending();
/*     */     }
/* 365 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ByteBuffer read()
/*     */   {
/* 399 */     if (this.m_channel == null) {
/* 400 */       m_logger.error("Socket channel is null");
/* 401 */       return null;
/*     */     }
/*     */     
/* 404 */     if ((!this.m_channel.isConnected()) || (!this.m_channel.isOpen())) {
/* 405 */       if (isPersistant()) {
/* 406 */         scheduleReconnection();
/* 407 */         this.m_inBuffer.flip();
/* 408 */         return this.m_inBuffer;
/*     */       }
/* 410 */       return null;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 415 */       if (this.m_inBuffer.position() == this.m_inBuffer.limit())
/*     */       {
/* 417 */         ByteBuffer newBuffer = ByteBuffer.allocate(this.m_inBuffer.limit() * 2);
/* 418 */         newBuffer.rewind();
/* 419 */         this.m_inBuffer.compact();
/* 420 */         newBuffer.put(this.m_inBuffer);
/* 421 */         this.m_inBuffer = newBuffer;
/*     */       }
/*     */       
/* 424 */       int nbReadBytes = this.m_channel.read(this.m_inBuffer);
/*     */       
/* 426 */       if (nbReadBytes <= 0) {
/* 427 */         this.m_handler.onConnectionError(this.m_connectionHandler, this);
/*     */         
/* 429 */         if (isPersistant()) {
/* 430 */           scheduleReconnection();
/* 431 */           this.m_inBuffer.flip();
/* 432 */           return this.m_inBuffer;
/*     */         }
/* 434 */         return null;
/*     */       }
/* 436 */       this.m_numBytesReceived += nbReadBytes;
/*     */     }
/*     */     catch (Throwable ex)
/*     */     {
/* 440 */       this.m_handler.onConnectionError(this.m_connectionHandler, this);
/*     */       
/* 442 */       if (isPersistant()) {
/* 443 */         scheduleReconnection();
/* 444 */         this.m_inBuffer.flip();
/* 445 */         return this.m_inBuffer;
/*     */       }
/*     */       
/* 448 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 452 */     this.m_inBuffer.flip();
/* 453 */     return this.m_inBuffer;
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
/*     */   boolean write()
/*     */   {
/* 466 */     if (this.m_channel == null) {
/* 467 */       m_logger.error("Socket channel is null");
/* 468 */       return false;
/*     */     }
/*     */     
/* 471 */     if ((!this.m_channel.isConnected()) || (!this.m_channel.isOpen())) {
/* 472 */       if (isPersistant()) {
/* 473 */         synchronized (this.m_outBufferMutex) {
/* 474 */           this.m_outBuffer.clear();
/*     */         }
/* 476 */         scheduleReconnection();
/*     */       }
/* 478 */       return false;
/*     */     }
/*     */     try
/*     */     {
/* 482 */       boolean closeConnection = false;
/*     */       
/* 484 */       synchronized (this.m_outBufferMutex)
/*     */       {
/* 486 */         this.m_outBuffer.flip();
/*     */         
/* 488 */         this.m_numBytesSent += this.m_outBuffer.remaining();
/*     */         
/* 490 */         while (this.m_outBuffer.remaining() > 0) {
/* 491 */           this.m_channel.write(this.m_outBuffer);
/* 492 */           this.m_outBuffer.compact();
/* 493 */           this.m_outBuffer.flip();
/*     */         }
/*     */         
/* 496 */         if ((this.m_cleanClose) && (this.m_outBuffer.remaining() <= 0)) {
/* 497 */           closeConnection = true;
/*     */         }
/*     */       }
/* 500 */       if (closeConnection) {
/* 501 */         this.m_connectionHandler.closeConnection(this);
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Throwable ex)
/*     */     {
/* 507 */       this.m_handler.onConnectionError(this.m_connectionHandler, this);
/*     */       
/* 509 */       m_logger.warn("[WRITE ERROR] : Connection closed : (exception : " + ex.toString() + ")");
/*     */       
/* 511 */       if (isPersistant()) {
/* 512 */         synchronized (this.m_outBufferMutex) {
/* 513 */           this.m_outBuffer.clear();
/*     */         }
/* 515 */         scheduleReconnection();
/*     */       }
/* 517 */       return false;
/*     */     }
/*     */     
/* 520 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean hasRemainingDatasToSend()
/*     */   {
/* 529 */     return this.m_outBuffer.hasRemaining();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void pushMessage(byte[] rawMessage)
/*     */   {
/* 539 */     if ((rawMessage != null) && (rawMessage.length > 0))
/*     */     {
/*     */ 
/*     */ 
/* 543 */       if (rawMessage.length < 4) {
/* 544 */         m_logger.warn("Taille de message inférieure à 4... étrange ! " + BinaryFormatter.toString(rawMessage));
/*     */       } else {
/* 546 */         int a = (rawMessage[0] & 0xFF) << 8;
/* 547 */         int b = rawMessage[1] & 0xFF;
/* 548 */         int msgLength = a | b;
/*     */         
/* 550 */         if (msgLength != rawMessage.length) {
/* 551 */           m_logger.error("Taille du message mauvaise: spécifié : " + msgLength + " réel : " + rawMessage.length);
/*     */         }
/*     */       }
/*     */       
/* 555 */       synchronized (this.m_outBufferMutex)
/*     */       {
/* 557 */         int size = this.m_outBuffer.limit();
/* 558 */         int sizeNeeded = this.m_outBuffer.position() + rawMessage.length;
/*     */         
/* 560 */         if (sizeNeeded > size) {
/* 561 */           ByteBuffer buffer = ByteBuffer.allocate(sizeNeeded * 2);
/* 562 */           if (this.m_outBuffer.position() > 0) {
/* 563 */             this.m_outBuffer.flip();
/* 564 */             buffer.put(this.m_outBuffer);
/*     */           }
/* 566 */           this.m_outBuffer = buffer;
/*     */         }
/*     */         
/* 569 */         this.m_outBuffer.put(rawMessage);
/*     */       }
/* 571 */       ConnectionWriter.getInstance().pushConnection(this);
/*     */     } else {
/* 573 */       m_logger.error("Données du message inexistantes ou de longueur nulle.", new Exception());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void scheduleReconnection()
/*     */   {
/*     */     try
/*     */     {
/* 582 */       if (isRegistered()) {
/* 583 */         m_logger.info("Désenregistrement de la connexion aupres du ConnectionHandler (established : " + this.m_connectionEstablished + ", connection : " + toString() + ")");
/* 584 */         this.m_connectionHandler.unregisterConnection(this);
/*     */       }
/*     */       
/* 587 */       if (!this.m_retrying)
/*     */       {
/* 589 */         if (this.m_connectionRetryCount < this.m_maxConnectionRetries)
/*     */         {
/* 591 */           final Connection connection = this;
/* 592 */           final ConnectionUser connectionUser = this.m_user;
/*     */           
/* 594 */           ProcessScheduler.getInstance().schedule(new Runnable() {
/*     */             public void run() {
/* 596 */               Connection.this.m_connectionRetryCount += 1;
/*     */               try
/*     */               {
/* 599 */                 Connection.m_logger.info("Tentative de reconnexion #" + Connection.this.m_connectionRetryCount + "/" + Connection.this.m_maxConnectionRetries + " à l'hôte " + Connection.this.m_host + ":" + Connection.this.m_port);
/*     */                 
/* 601 */                 Connection.this.connectToHost(Connection.this.m_host, Connection.this.m_port);
/* 602 */                 if (!Connection.this.isRegistered()) {
/* 603 */                   Connection.m_logger.info("Enregistrement de la connexion aupres du ConnectionHandler");
/* 604 */                   Connection.this.m_connectionHandler.registerConnection(connection, true);
/*     */                 }
/*     */                 
/* 607 */                 if ((Connection.this.m_channel != null) && 
/* 608 */                   (Connection.this.m_channel.isConnected())) {
/* 609 */                   Connection.this.m_handler.onConnectionRecovered(Connection.this.m_connectionHandler, connection);
/* 610 */                   Connection.this.m_connectionRetryCount = 0;
/*     */                 }
/*     */                 
/*     */ 
/* 614 */                 if ((connectionUser == null) && 
/* 615 */                   (!Connection.this.m_handler.onNewConnection(Connection.this.m_connectionHandler, connection))) {
/* 616 */                   Connection.this.m_retrying = false;
/* 617 */                   Connection.this.scheduleReconnection();
/*     */                 }
/*     */                 
/* 620 */                 Connection.this.m_retrying = false;
/*     */               }
/*     */               catch (Exception ex) {
/* 623 */                 Connection.m_logger.error("Impossible de se connecter à l'hôte");
/* 624 */                 Connection.this.m_retrying = false;
/* 625 */                 Connection.this.scheduleReconnection();
/*     */               }
/*     */             }
/* 628 */           }, this.m_connectionRetryDelay, 1);
/*     */           
/* 630 */           this.m_handler.onReconnectionScheduled(this.m_connectionHandler, this);
/* 631 */           this.m_retrying = true;
/*     */         }
/* 633 */         else if (this.m_connectionHasBeenEstablished) {
/* 634 */           this.m_handler.onConnectionClose(this.m_connectionHandler, this);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 638 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isRetrying() {
/* 643 */     return this.m_retrying;
/*     */   }
/*     */   
/*     */   public InetAddress getInetAddress() {
/* 647 */     if (this.m_channel == null) {
/* 648 */       return null;
/*     */     }
/* 650 */     Socket s = this.m_channel.socket();
/* 651 */     if (s == null)
/* 652 */       return null;
/* 653 */     return s.getInetAddress();
/*     */   }
/*     */   
/*     */   public String getHost() {
/* 657 */     return this.m_host;
/*     */   }
/*     */   
/*     */   public int getPort() {
/* 661 */     return this.m_port;
/*     */   }
/*     */   
/*     */   public void setConnectionEstablished(boolean connectionEstablished)
/*     */   {
/* 666 */     this.m_connectionEstablished = connectionEstablished;
/*     */   }
/*     */   
/*     */   public boolean isConnectionEstablished() {
/* 670 */     return this.m_connectionEstablished;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getConnectionRetryCount()
/*     */   {
/* 677 */     return this.m_connectionRetryCount;
/*     */   }
/*     */   
/*     */   public int getMaxConnectionRetries() {
/* 681 */     return this.m_maxConnectionRetries;
/*     */   }
/*     */   
/*     */   public void setMaxConnectionRetries(int maxConnectionRetries) {
/* 685 */     this.m_maxConnectionRetries = maxConnectionRetries;
/* 686 */     m_logger.info("set MaxConnectionRetries to " + maxConnectionRetries);
/*     */   }
/*     */   
/*     */   public int getConnectionRetryDelay() {
/* 690 */     return this.m_connectionRetryDelay;
/*     */   }
/*     */   
/*     */   public void setConnectionRetryDelay(int connectionRetryDelay) {
/* 694 */     this.m_connectionRetryDelay = connectionRetryDelay;
/* 695 */     m_logger.info("set ConnectionRetryDelay to " + connectionRetryDelay);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getNumBytesSent()
/*     */   {
/* 704 */     return this.m_numBytesSent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getNumBytesReceived()
/*     */   {
/* 713 */     return this.m_numBytesReceived;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SocketChannel getChannel()
/*     */   {
/* 722 */     return this.m_channel;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\net\Connection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */