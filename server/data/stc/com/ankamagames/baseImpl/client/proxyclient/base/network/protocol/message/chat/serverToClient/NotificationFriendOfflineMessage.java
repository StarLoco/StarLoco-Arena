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
/*    */ public class NotificationFriendOfflineMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_friendName;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 29 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 31 */     byte[] fn = new byte[buffer.get() & 0xFF];
/* 32 */     buffer.get(fn);
/* 33 */     this.m_friendName = StringUtils.fromUTF8(fn);
/*    */     
/* 35 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 45 */     return 3150;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getFriendName()
/*    */   {
/* 52 */     return this.m_friendName;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\NotificationFriendOfflineMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */