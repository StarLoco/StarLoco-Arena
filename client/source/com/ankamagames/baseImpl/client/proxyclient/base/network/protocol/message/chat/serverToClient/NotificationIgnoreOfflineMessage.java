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
/*    */ public class NotificationIgnoreOfflineMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_ignoreName;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 23 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 25 */     byte[] fn = new byte[buffer.get() & 0xFF];
/* 26 */     buffer.get(fn);
/* 27 */     this.m_ignoreName = StringUtils.fromUTF8(fn);
/*    */     
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 39 */     return 3166;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getIgnoreName() {
/* 46 */     return this.m_ignoreName;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\NotificationIgnoreOfflineMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */