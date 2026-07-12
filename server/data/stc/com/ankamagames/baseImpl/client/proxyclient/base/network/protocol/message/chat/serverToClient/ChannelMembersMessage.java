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
/*    */ public class ChannelMembersMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_channelName;
/*    */   private byte[] m_serializedMembers;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 30 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 32 */     byte[] cn = new byte[bb.get() & 0xFF];
/* 33 */     bb.get(cn);
/* 34 */     this.m_channelName = StringUtils.fromUTF8(cn);
/*    */     
/* 36 */     this.m_serializedMembers = new byte[bb.remaining()];
/* 37 */     bb.get(this.m_serializedMembers);
/*    */     
/* 39 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 48 */     return 3138;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getChannelName()
/*    */   {
/* 55 */     return this.m_channelName;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte[] getSerializedMembers()
/*    */   {
/* 62 */     return this.m_serializedMembers;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\ChannelMembersMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */