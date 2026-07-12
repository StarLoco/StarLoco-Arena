/*     */ package com.ankamagames.dofusarena.client.ui.protocol.message.connection;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.proxy.ProxyGroup;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
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
/*     */ public class UILogonRequestMessage
/*     */   extends UIMessage
/*     */ {
/*  24 */   private static final ObjectPool m_pool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<UILogonRequestMessage>() {
/*     */         public UILogonRequestMessage makeObject() {
/*  26 */           return new UILogonRequestMessage(null);
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */   
/*     */   private ProxyGroup m_proxyGroup;
/*     */   
/*     */   private String m_login;
/*     */   
/*     */   private String m_password;
/*     */   
/*     */   private boolean m_remember;
/*     */ 
/*     */   
/*     */   private UILogonRequestMessage() {}
/*     */ 
/*     */   
/*     */   public static UILogonRequestMessage checkOut() {
/*     */     UILogonRequestMessage msg;
/*     */     try {
/*  47 */       msg = (UILogonRequestMessage)m_pool.borrowObject();
/*  48 */       msg.setPool(m_pool);
/*  49 */     } catch (Exception e) {
/*  50 */       msg = new UILogonRequestMessage();
/*  51 */       m_logger.error("Erreur lors d'un checkOut sur un message de type UIChatOperationMessage : " + e.getMessage());
/*     */     } 
/*  53 */     return msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  63 */     return 16385;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLogin(String login) {
/*  70 */     this.m_login = login;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPassword(String password) {
/*  77 */     this.m_password = password;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProxyGroup(ProxyGroup proxyGroup) {
/*  84 */     this.m_proxyGroup = proxyGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRemember(Boolean remember) {
/*  91 */     this.m_remember = remember.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProxyGroup getProxyGroup() {
/*  98 */     return this.m_proxyGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLogin() {
/* 105 */     return this.m_login;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPassword() {
/* 112 */     return this.m_password;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getRemember() {
/* 119 */     return Boolean.valueOf(this.m_remember);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\connection\UILogonRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */