/*     */ package com.ankamagames.baseImpl.client.proxyclient.base.network.proxy;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.ConnectorInstance;
/*     */ import com.ankamagames.framework.kernel.FrameworkEntity;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.message.MessageDecoder;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.framework.kernel.core.common.message.scheduler.MessageScheduler;
/*     */ import com.ankamagames.framework.kernel.core.net.ConnectionWriter;
/*     */ import com.ankamagames.framework.kernel.events.NetworkEventsHandler;
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
/*     */ public class ProxyClient
/*     */ {
/*  26 */   private static boolean m_threadStarded = false;
/*     */   
/*     */   private ObjectFactory<? extends FrameworkEntity> m_proxyEntityPool;
/*     */   
/*     */   private ConnectorInstance m_proxyConnection;
/*     */   
/*     */   private MessageDecoder m_proxyDecoder;
/*     */   
/*     */   private NetworkEventsHandler m_proxyEventsHandler;
/*     */   private boolean m_proxyConnectionInitialized;
/*  36 */   private int m_connectionRetryDelay = 1000;
/*  37 */   private int m_maxConnectionRetries = 0;
/*     */   
/*     */   public ProxyClient() {
/*  40 */     this.m_proxyConnection = new ConnectorInstance("ProxyClient");
/*     */     
/*  42 */     this.m_proxyEntityPool = null;
/*  43 */     this.m_proxyEventsHandler = null;
/*  44 */     this.m_proxyDecoder = null;
/*     */     
/*  46 */     this.m_proxyConnectionInitialized = false;
/*     */     
/*  48 */     if (!m_threadStarded) {
/*  49 */       Worker.getInstance().start();
/*  50 */       ConnectionWriter.getInstance().start();
/*  51 */       MessageScheduler.getInstance().start();
/*  52 */       m_threadStarded = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initProxyConnection()
/*     */     throws Exception
/*     */   {
/*  62 */     this.m_proxyConnection.registerEntityFactory(this.m_proxyEntityPool);
/*  63 */     this.m_proxyConnection.registerMessageDecoder(this.m_proxyDecoder);
/*  64 */     this.m_proxyConnection.registerNetworkEventsHandler(this.m_proxyEventsHandler);
/*     */     
/*  66 */     this.m_proxyConnection.setConnectionRetryDelay(this.m_connectionRetryDelay);
/*  67 */     this.m_proxyConnection.setMaxConnectionRetries(this.m_maxConnectionRetries);
/*     */     
/*  69 */     this.m_proxyConnection.initialize();
/*     */     
/*  71 */     this.m_proxyConnectionInitialized = true;
/*     */   }
/*     */   
/*     */   private boolean isConfigured() {
/*  75 */     if (this.m_proxyEntityPool == null)
/*  76 */       return false;
/*  77 */     if (this.m_proxyEventsHandler == null)
/*  78 */       return false;
/*  79 */     if (this.m_proxyDecoder == null)
/*  80 */       return false;
/*  81 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ConnectorInstance getProxyConnection()
/*     */   {
/*  88 */     return this.m_proxyConnection;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void connectToProxy(String hostName, int port)
/*     */     throws Exception
/*     */   {
/*  98 */     if ((!this.m_proxyConnectionInitialized) && (isConfigured())) {
/*  99 */       initProxyConnection();
/* 100 */       this.m_proxyConnection.start();
/*     */     }
/*     */     
/* 103 */     this.m_proxyConnection.openConnection(hostName, port);
/*     */   }
/*     */   
/*     */   public ObjectFactory<? extends FrameworkEntity> getProxyEntityPool() {
/* 107 */     return this.m_proxyEntityPool;
/*     */   }
/*     */   
/*     */   public void setProxyEntityPool(ObjectFactory<? extends FrameworkEntity> proxyEntityPool) {
/* 111 */     this.m_proxyEntityPool = proxyEntityPool;
/*     */   }
/*     */   
/*     */   public MessageDecoder getProxyDecoder() {
/* 115 */     return this.m_proxyDecoder;
/*     */   }
/*     */   
/*     */   public void setProxyDecoder(MessageDecoder proxyDecoder) {
/* 119 */     this.m_proxyDecoder = proxyDecoder;
/*     */   }
/*     */   
/*     */   public NetworkEventsHandler getProxyEventsHandler() {
/* 123 */     return this.m_proxyEventsHandler;
/*     */   }
/*     */   
/*     */   public void setProxyEventsHandler(NetworkEventsHandler proxyEventsHandler) {
/* 127 */     this.m_proxyEventsHandler = proxyEventsHandler;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setConnectionRetryDelay(int connectionRetryDelay)
/*     */   {
/* 133 */     this.m_connectionRetryDelay = connectionRetryDelay;
/*     */   }
/*     */   
/*     */   public void setMaxConnectionRetries(int maxConnectionRetries) {
/* 137 */     this.m_maxConnectionRetries = maxConnectionRetries;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\proxy\ProxyClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */