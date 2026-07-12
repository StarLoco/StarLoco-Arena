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
/*    */ public class FriendRemovedMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_friendName;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 27 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 29 */     byte[] cn = new byte[bb.get() & 0xFF];
/* 30 */     bb.get(cn);
/* 31 */     this.m_friendName = StringUtils.fromUTF8(cn);
/*    */     
/* 33 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getFriendName()
/*    */   {
/* 40 */     return this.m_friendName;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 49 */     return 3160;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\FriendRemovedMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */