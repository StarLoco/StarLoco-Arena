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
/*    */ public class ChannelFlagsMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_channelName;
/*    */   private byte m_flags;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 30 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 32 */     byte[] cn = new byte[bb.get()];
/* 33 */     bb.get(cn);
/* 34 */     this.m_channelName = StringUtils.fromUTF8(cn);
/*    */     
/* 36 */     this.m_flags = bb.get();
/*    */     
/* 38 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 47 */     return 3128;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getChannelName()
/*    */   {
/* 54 */     return this.m_channelName;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getFlags()
/*    */   {
/* 61 */     return this.m_flags;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\ChannelFlagsMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */