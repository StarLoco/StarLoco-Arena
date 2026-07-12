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
/*    */ public class ChannelMemberKickMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_channelName;
/*    */   private String m_memberName;
/*    */   private String m_kickReason;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 32 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 34 */     byte[] cn = new byte[bb.get()];
/* 35 */     bb.get(cn);
/* 36 */     this.m_channelName = StringUtils.fromUTF8(cn);
/*    */     
/* 38 */     byte[] nmn = new byte[bb.get()];
/* 39 */     bb.get(nmn);
/* 40 */     this.m_memberName = StringUtils.fromUTF8(nmn);
/*    */     
/* 42 */     byte[] kr = new byte[bb.get()];
/* 43 */     bb.get(kr);
/* 44 */     this.m_kickReason = StringUtils.fromUTF8(kr);
/*    */     
/* 46 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 55 */     return 3136;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getChannelName()
/*    */   {
/* 62 */     return this.m_channelName;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getKickReason()
/*    */   {
/* 69 */     return this.m_kickReason;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getMemberName()
/*    */   {
/* 76 */     return this.m_memberName;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\ChannelMemberKickMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */