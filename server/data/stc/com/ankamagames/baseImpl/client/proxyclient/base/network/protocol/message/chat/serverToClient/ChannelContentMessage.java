/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient;
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
/*    */ public class ChannelContentMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_channelName;
/*    */   private String m_memberTalking;
/*    */   private String m_messageContent;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 31 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 33 */     byte[] cn = new byte[bb.get()];
/* 34 */     bb.get(cn);
/* 35 */     this.m_channelName = StringUtils.fromUTF8(cn);
/*    */     
/* 37 */     byte[] mt = new byte[bb.get()];
/* 38 */     bb.get(mt);
/* 39 */     this.m_memberTalking = StringUtils.fromUTF8(mt);
/*    */     
/* 41 */     byte[] mc = new byte[bb.get()];
/* 42 */     bb.get(mc);
/* 43 */     this.m_messageContent = StringUtils.fromUTF8(mc);
/*    */     
/* 45 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 55 */     return 3140;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getChannelName()
/*    */   {
/* 63 */     return this.m_channelName;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getMemberTalking()
/*    */   {
/* 71 */     return this.m_memberTalking;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getMessageContent()
/*    */   {
/* 79 */     return this.m_messageContent;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\ChannelContentMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */