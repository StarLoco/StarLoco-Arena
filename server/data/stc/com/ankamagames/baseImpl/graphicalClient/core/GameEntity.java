/*     */ package com.ankamagames.baseImpl.graphicalClient.core;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.core.ProxyClientEntity;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.proxy.ProxyAddress;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.proxy.ProxyClient;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.proxy.ProxyGroup;
/*     */ import com.ankamagames.framework.kernel.core.net.Connection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GameEntity
/*     */   extends ProxyClientEntity
/*     */ {
/*     */   private String m_login;
/*     */   private String m_password;
/*     */   private boolean m_logged;
/*     */   private ProxyGroup m_proxyGroup;
/*     */   
/*     */   public String getLogin()
/*     */   {
/*  48 */     return this.m_login;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLogin(String login)
/*     */   {
/*  56 */     this.m_login = login;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getPassword()
/*     */   {
/*  63 */     return this.m_password;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPassword(String password)
/*     */   {
/*  71 */     this.m_password = password;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isLogged()
/*     */   {
/*  78 */     return this.m_logged;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setLogged(boolean logged)
/*     */   {
/*  85 */     this.m_logged = logged;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ProxyGroup getProxyGroup()
/*     */   {
/*  92 */     return this.m_proxyGroup;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setProxyGroup(ProxyGroup proxyGroup)
/*     */   {
/* 100 */     this.m_proxyGroup = proxyGroup;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void cleanUp()
/*     */   {
/* 110 */     super.cleanUp();
/* 111 */     this.m_login = null;
/* 112 */     this.m_password = null;
/* 113 */     this.m_logged = false;
/* 114 */     this.m_proxyGroup = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean connect(ProxyClient proxyClient)
/*     */   {
/* 126 */     if (getProxyGroup() != null)
/*     */     {
/* 128 */       ProxyAddress proxyAddress = getProxyGroup().getRandomProxyAddress();
/* 129 */       if (proxyAddress != null) {
/*     */         try
/*     */         {
/* 132 */           m_logger.info("Connexion au proxy :" + proxyAddress.getHost() + ":" + proxyAddress.getPort());
/* 133 */           proxyClient.connectToProxy(proxyAddress.getHost(), proxyAddress.getPort());
/* 134 */           return true;
/*     */         } catch (Exception e) {
/* 136 */           m_logger.error("connect :", e);
/*     */         }
/*     */       }
/*     */     }
/* 140 */     onConnectionToProxyFaild();
/* 141 */     m_logger.error("Aucun proxy n'est disponible");
/* 142 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void logon()
/*     */   {
/* 149 */     if ((!this.m_logged) && (getNetworkEntity() != null) && (getNetworkEntity().getConnection().isConnected())) {
/* 150 */       onLogonRequest();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void logoff()
/*     */   {
/* 160 */     if ((this.m_logged) && (getNetworkEntity() != null) && (getNetworkEntity().getConnection().isConnected())) {
/* 161 */       onLogoffRequest();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void quit()
/*     */   {
/* 171 */     onQuitRequest();
/*     */   }
/*     */   
/*     */   protected abstract void onConnectionToProxyFaild();
/*     */   
/*     */   protected abstract void onLogonRequest();
/*     */   
/*     */   protected abstract void onLogoffRequest();
/*     */   
/*     */   protected abstract void onQuitRequest();
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\core\GameEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */