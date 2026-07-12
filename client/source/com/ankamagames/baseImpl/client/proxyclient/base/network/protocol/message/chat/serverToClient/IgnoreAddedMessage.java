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
/*    */ public class IgnoreAddedMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_ignoreName;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 28 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 30 */     byte[] cn = new byte[bb.get() & 0xFF];
/* 31 */     bb.get(cn);
/* 32 */     this.m_ignoreName = StringUtils.fromUTF8(cn);
/*    */     
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 43 */     return 3158;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getIgnoreName() {
/* 50 */     return this.m_ignoreName;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\IgnoreAddedMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */