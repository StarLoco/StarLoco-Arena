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
/*    */ public class NotificationIgnoreOnlineMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_IgnoreName;
/*    */   private long m_userId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 24 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 26 */     byte[] name = new byte[buffer.get() & 0xFF];
/* 27 */     buffer.get(name);
/* 28 */     this.m_IgnoreName = StringUtils.fromUTF8(name);
/* 29 */     this.m_userId = buffer.getLong();
/*    */     
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 38 */     return 3164;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getIgnoreName() {
/* 45 */     return this.m_IgnoreName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getUserId() {
/* 52 */     return this.m_userId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\NotificationIgnoreOnlineMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */