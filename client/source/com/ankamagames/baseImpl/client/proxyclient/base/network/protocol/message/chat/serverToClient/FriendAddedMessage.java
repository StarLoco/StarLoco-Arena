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
/*    */ public class FriendAddedMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_friendName;
/*    */   private long m_friendId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 28 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 30 */     byte[] cn = new byte[bb.get() & 0xFF];
/* 31 */     bb.get(cn);
/* 32 */     this.m_friendName = StringUtils.fromUTF8(cn);
/* 33 */     this.m_friendId = bb.getLong();
/*    */     
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 44 */     return 3156;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFriendName() {
/* 51 */     return this.m_friendName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getFriendId() {
/* 58 */     return this.m_friendId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\FriendAddedMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */