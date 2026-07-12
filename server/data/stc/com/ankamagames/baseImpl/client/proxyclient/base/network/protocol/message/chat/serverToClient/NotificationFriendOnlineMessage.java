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
/*    */ 
/*    */ public class NotificationFriendOnlineMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_friendName;
/*    */   private long m_userId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 32 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 34 */     byte[] name = new byte[buffer.get() & 0xFF];
/* 35 */     buffer.get(name);
/* 36 */     this.m_friendName = StringUtils.fromUTF8(name);
/* 37 */     this.m_userId = buffer.getLong();
/*    */     
/* 39 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 46 */     return 3148;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getFriendName()
/*    */   {
/* 53 */     return this.m_friendName;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getUserId()
/*    */   {
/* 60 */     return this.m_userId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\NotificationFriendOnlineMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */