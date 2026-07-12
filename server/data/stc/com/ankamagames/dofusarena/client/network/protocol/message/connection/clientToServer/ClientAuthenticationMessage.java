/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.connection.clientToServer;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class ClientAuthenticationMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private String m_login;
/*    */   private String m_password;
/*    */   
/*    */   public ClientAuthenticationMessage()
/*    */   {
/* 27 */     this.m_login = "";
/* 28 */     this.m_password = "";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] encode()
/*    */   {
/* 38 */     byte[] login = this.m_login.getBytes();
/* 39 */     byte lLogin = (byte)login.length;
/* 40 */     byte[] password = this.m_password.getBytes();
/* 41 */     byte lPassword = (byte)password.length;
/*    */     
/* 43 */     ByteBuffer bb = ByteBuffer.allocate(lLogin + lPassword + 2);
/*    */     
/* 45 */     bb.put(lLogin);
/* 46 */     bb.put(login);
/* 47 */     bb.put(lPassword);
/* 48 */     bb.put(password);
/*    */     
/* 50 */     return addClientHeader((byte)1, bb.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 59 */     return 1025;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setLogin(String login)
/*    */   {
/* 66 */     this.m_login = login;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setPassword(String password)
/*    */   {
/* 73 */     this.m_password = password;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\connection\clientToServer\ClientAuthenticationMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */