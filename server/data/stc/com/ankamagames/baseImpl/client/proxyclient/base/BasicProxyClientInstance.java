/*    */ package com.ankamagames.baseImpl.client.proxyclient.base;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.AbstractClientMessageDecoder;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.proxy.ProxyClient;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.events.NetworkEventsHandler;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicProxyClientInstance
/*    */ {
/* 21 */   protected static Logger m_logger = Logger.getLogger(BasicProxyClientInstance.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private ProxyClient m_proxy;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 31 */   private ObjectFactory<NetworkEntity> m_networkEntityFactory = null;
/* 32 */   private AbstractClientMessageDecoder m_clientMessageDecoder = null;
/* 33 */   private NetworkEventsHandler m_networkEventHandler = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setClientMessageDecoder(AbstractClientMessageDecoder clientMessageDecoder)
/*    */   {
/* 46 */     this.m_clientMessageDecoder = clientMessageDecoder;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setNetworkEntityFactory(ObjectFactory<NetworkEntity> networkEntityFactory)
/*    */   {
/* 54 */     this.m_networkEntityFactory = networkEntityFactory;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setNetworkEventHandler(NetworkEventsHandler networkEventHandler)
/*    */   {
/* 62 */     this.m_networkEventHandler = networkEventHandler;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ProxyClient getProxy()
/*    */   {
/* 69 */     return this.m_proxy;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected void createProxyClient()
/*    */   {
/* 76 */     if ((this.m_networkEntityFactory != null) && (this.m_clientMessageDecoder != null) && (this.m_networkEventHandler != null))
/*    */     {
/* 78 */       this.m_proxy = new ProxyClient();
/* 79 */       this.m_proxy.setProxyEntityPool(this.m_networkEntityFactory);
/* 80 */       this.m_proxy.setProxyDecoder(this.m_clientMessageDecoder);
/* 81 */       this.m_proxy.setProxyEventsHandler(this.m_networkEventHandler);
/* 82 */       return;
/*    */     }
/* 84 */     m_logger.error("Impossible de créer le ProxyClient : tous ces paramètres n'ont pas été définis");
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\BasicProxyClientInstance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */