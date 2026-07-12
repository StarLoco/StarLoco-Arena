/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.clientToServer;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
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
/*    */ 
/*    */ 
/*    */ public class AddFriendMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private String m_friendName;
/*    */   
/*    */   public byte[] encode() {
/* 32 */     byte[] fn = StringUtils.toUTF8(this.m_friendName);
/*    */     
/* 34 */     ByteBuffer bb = ByteBuffer.allocate(1 + fn.length);
/*    */     
/* 36 */     bb.put((byte)fn.length);
/* 37 */     bb.put(fn);
/*    */     
/* 39 */     return addClientHeader((byte)4, bb.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 48 */     return 3129;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFriendName(String friendName) {
/* 55 */     this.m_friendName = friendName;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\clientToServer\AddFriendMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */