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
/*    */ 
/*    */ public class ChannelLeaveMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_channelName;
/*    */   private String m_memberName;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 31 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 33 */     byte[] cn = new byte[bb.get()];
/* 34 */     bb.get(cn);
/*    */     
/* 36 */     this.m_channelName = StringUtils.fromUTF8(cn);
/*    */     
/* 38 */     byte[] nmn = new byte[bb.get()];
/* 39 */     bb.get(nmn);
/* 40 */     this.m_memberName = StringUtils.fromUTF8(nmn);
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
/* 51 */     return 3132;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getChannelName()
/*    */   {
/* 58 */     return this.m_channelName;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getMemberName()
/*    */   {
/* 65 */     return this.m_memberName;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\ChannelLeaveMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */