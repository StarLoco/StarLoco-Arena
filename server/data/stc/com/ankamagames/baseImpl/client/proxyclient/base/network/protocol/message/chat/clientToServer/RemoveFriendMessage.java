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
/*    */ public class RemoveFriendMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private String m_friendName;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 33 */     byte[] fn = StringUtils.toUTF8(this.m_friendName);
/*    */     
/* 35 */     ByteBuffer bb = ByteBuffer.allocate(1 + fn.length);
/*    */     
/* 37 */     bb.put((byte)fn.length);
/* 38 */     bb.put(fn);
/*    */     
/* 40 */     return addClientHeader((byte)4, bb.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 49 */     return 3133;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setFriendName(String friendName)
/*    */   {
/* 56 */     this.m_friendName = friendName;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\clientToServer\RemoveFriendMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */