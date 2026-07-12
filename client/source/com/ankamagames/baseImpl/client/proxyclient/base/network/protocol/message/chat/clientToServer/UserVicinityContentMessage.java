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
/*    */ 
/*    */ 
/*    */ public class UserVicinityContentMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private String m_messageContent;
/*    */   
/*    */   public byte[] encode() {
/* 34 */     byte[] mc = StringUtils.toUTF8(this.m_messageContent);
/*    */     
/* 36 */     ByteBuffer bb = ByteBuffer.allocate(2 + mc.length);
/*    */     
/* 38 */     bb.putShort((short)mc.length);
/* 39 */     bb.put(mc);
/*    */     
/* 41 */     return addClientHeader((byte)3, bb.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 50 */     return 3153;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMessageContent(String messageContent) {
/* 57 */     this.m_messageContent = messageContent;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\clientToServer\UserVicinityContentMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */