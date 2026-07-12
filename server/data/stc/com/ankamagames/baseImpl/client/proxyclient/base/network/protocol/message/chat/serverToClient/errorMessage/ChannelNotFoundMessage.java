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
/*    */ public class ChannelNotFoundMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_channelName;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 29 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 31 */     byte[] cn = new byte[bb.get()];
/* 32 */     bb.get(cn);
/*    */     
/* 34 */     this.m_channelName = StringUtils.fromUTF8(cn);
/*    */     
/*    */ 
/* 37 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 46 */     return 3202;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getChannelName()
/*    */   {
/* 53 */     return this.m_channelName;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\errorMessage\ChannelNotFoundMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */