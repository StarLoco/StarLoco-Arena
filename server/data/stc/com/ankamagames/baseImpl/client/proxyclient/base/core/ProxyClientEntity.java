/*     */ package com.ankamagames.baseImpl.client.proxyclient.base.core;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.frame.NetBasicsFrame;
/*     */ import com.ankamagames.framework.kernel.FrameworkEntity;
/*     */ import com.ankamagames.framework.kernel.core.net.Connection;
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
/*     */ public abstract class ProxyClientEntity
/*     */   extends FrameworkEntity
/*     */ {
/*     */   private NetworkEntity m_networkEntity;
/*  30 */   private int m_maxConnectionRetries = 0;
/*  31 */   private int m_connectionRetryDelay = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private byte[] m_ticket;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ProxyClientEntity()
/*     */   {
/*  43 */     cleanUp();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NetworkEntity getNetworkEntity()
/*     */   {
/*  50 */     return this.m_networkEntity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNetworkEntity(NetworkEntity networkEntity)
/*     */   {
/*  58 */     this.m_networkEntity = networkEntity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setConnectionRetryParameters(int maxConnectionRetries, int connectionRetryDelay)
/*     */   {
/*  68 */     this.m_maxConnectionRetries = maxConnectionRetries;
/*  69 */     this.m_connectionRetryDelay = connectionRetryDelay;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public byte[] getTicket()
/*     */   {
/*  76 */     return this.m_ticket;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTicket(byte[] ticket)
/*     */   {
/*  83 */     this.m_ticket = ticket;
/*  84 */     if (this.m_ticket == null) {
/*  85 */       desactivateConectionRetry();
/*     */     } else {
/*  87 */       activateConectionRetry();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void cleanUp()
/*     */   {
/*  95 */     setTicket(null);
/*     */     
/*     */ 
/*  98 */     removeAllFrames();
/*     */     
/*     */ 
/* 101 */     pushFrame(new NetBasicsFrame(this));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void activateConectionRetry()
/*     */   {
/* 108 */     if (getNetworkEntity() != null) {
/* 109 */       Connection connection = getNetworkEntity().getConnection();
/* 110 */       if (connection != null) {
/* 111 */         connection.setMaxConnectionRetries(this.m_maxConnectionRetries);
/* 112 */         connection.setConnectionRetryDelay(this.m_connectionRetryDelay);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void desactivateConectionRetry()
/*     */   {
/* 121 */     if (getNetworkEntity() != null) {
/* 122 */       Connection connection = getNetworkEntity().getConnection();
/* 123 */       if (connection != null) {
/* 124 */         connection.setMaxConnectionRetries(0);
/* 125 */         connection.setConnectionRetryDelay(0);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract void onQueuePositionUpdate(int paramInt);
/*     */   
/*     */   public abstract void onQueueFinished();
/*     */   
/*     */   public abstract void onQueryResult(int paramInt);
/*     */   
/*     */   public abstract void onInvalidClientVersion(byte[] paramArrayOfByte);
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\core\ProxyClientEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */