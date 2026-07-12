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
/*    */ public class UserChannelContentMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private String m_channelName;
/*    */   private String m_messageContent;
/*    */   
/*    */   public byte[] encode() {
/* 32 */     byte[] channel = new byte[0];
/* 33 */     channel = StringUtils.toUTF8(this.m_channelName);
/*    */     
/* 35 */     byte[] content = new byte[0];
/*    */     try {
/* 37 */       content = StringUtils.toUTF8(this.m_messageContent);
/* 38 */     } catch (Exception e) {
/* 39 */       content = this.m_messageContent.getBytes();
/*    */     } 
/*    */     
/* 42 */     ByteBuffer bb = ByteBuffer.allocate(1 + content.length + 1 + channel.length);
/*    */     
/* 44 */     bb.put((byte)channel.length);
/* 45 */     bb.put(channel);
/*    */     
/* 47 */     bb.put((byte)content.length);
/* 48 */     bb.put(content);
/*    */     
/* 50 */     return addClientHeader((byte)4, bb.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 59 */     return 3151;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setChannelName(String channelName) {
/* 66 */     this.m_channelName = channelName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMessageContent(String messageContent) {
/* 73 */     this.m_messageContent = messageContent;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\clientToServer\UserChannelContentMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */