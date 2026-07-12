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
/*    */ public class ChannelJoinMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_channelName;
/*    */   private String m_newMemberName;
/*    */   private byte m_newMemberFlags;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 31 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 33 */     byte[] cn = new byte[bb.get()];
/* 34 */     bb.get(cn);
/* 35 */     this.m_channelName = StringUtils.fromUTF8(cn);
/*    */     
/* 37 */     byte[] nmn = new byte[bb.get()];
/* 38 */     bb.get(nmn);
/* 39 */     this.m_newMemberName = StringUtils.fromUTF8(nmn);
/*    */     
/* 41 */     this.m_newMemberFlags = bb.get();
/*    */     
/* 43 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 52 */     return 3130;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getChannelName()
/*    */   {
/* 59 */     return this.m_channelName;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getNewMemberFlags()
/*    */   {
/* 66 */     return this.m_newMemberFlags;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getNewMemberName()
/*    */   {
/* 73 */     return this.m_newMemberName;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\ChannelJoinMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */