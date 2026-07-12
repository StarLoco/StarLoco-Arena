/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.proxy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProxyAddress
/*    */ {
/*    */   private String m_host;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private int m_port;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ProxyAddress(String host, int port)
/*    */   {
/* 23 */     this.m_host = host;
/* 24 */     this.m_port = port;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getHost()
/*    */   {
/* 31 */     return this.m_host;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getPort()
/*    */   {
/* 38 */     return this.m_port;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 46 */     return 
/*    */     
/* 48 */       "ProxyAddress host=" + this.m_host + " port=" + Integer.toString(this.m_port);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\proxy\ProxyAddress.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */