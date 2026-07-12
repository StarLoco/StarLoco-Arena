/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.clientToServer;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
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
/*    */ public class UserPrivateContentMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private String m_userName;
/*    */   private String m_messageContent;
/*    */   
/*    */   public byte[] encode() {
/* 33 */     byte[] un = new byte[0];
/* 34 */     un = StringUtils.toUTF8(this.m_userName);
/*    */     
/* 36 */     byte[] mc = new byte[0];
/*    */     try {
/* 38 */       mc = StringUtils.toUTF8(this.m_messageContent);
/* 39 */     } catch (Exception e) {
/* 40 */       mc = this.m_messageContent.getBytes();
/*    */     } 
/*    */     
/* 43 */     ByteBuffer bb = ByteBuffer.allocate(1 + un.length + 1 + mc.length);
/*    */     
/* 45 */     bb.put((byte)un.length);
/* 46 */     bb.put(un);
/*    */     
/* 48 */     bb.put((byte)mc.length);
/* 49 */     bb.put(mc);
/*    */     
/* 51 */     return addClientHeader((byte)4, bb.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 60 */     return 3155;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setUserName(String userName) {
/* 67 */     this.m_userName = userName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMessageContent(String messageContent) {
/* 74 */     this.m_messageContent = messageContent;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\clientToServer\UserPrivateContentMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */