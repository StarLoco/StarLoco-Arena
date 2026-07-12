/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import com.ankamagames.framework.kernel.utils.StringUtils;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UserNotFoundMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_userName;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 35 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 37 */     byte[] un = new byte[bb.get()];
/* 38 */     bb.get(un);
/*    */     
/* 40 */     this.m_userName = StringUtils.fromUTF8(un);
/*    */     
/* 42 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 51 */     return 3204;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getUserName()
/*    */   {
/* 58 */     return this.m_userName;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\errorMessage\UserNotFoundMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */