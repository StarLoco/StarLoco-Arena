/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.proxy;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProxyAddress
/*    */ {
/*    */   private String m_host;
/*    */   private int m_port;
/*    */   
/*    */   public ProxyAddress(String host, int port) {
/* 23 */     this.m_host = host;
/* 24 */     this.m_port = port;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHost() {
/* 31 */     return this.m_host;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getPort() {
/* 38 */     return this.m_port;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 46 */     return "ProxyAddress host=" + 
/* 47 */       this.m_host + 
/* 48 */       " port=" + Integer.toString(this.m_port);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\proxy\ProxyAddress.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */