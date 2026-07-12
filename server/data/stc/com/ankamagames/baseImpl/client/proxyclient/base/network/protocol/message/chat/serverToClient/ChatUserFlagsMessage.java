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
/*    */ public class ChatUserFlagsMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_userName;
/*    */   private byte m_flags;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 30 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 32 */     byte[] un = new byte[bb.get()];
/* 33 */     bb.get(un);
/* 34 */     this.m_userName = StringUtils.fromUTF8(un);
/*    */     
/* 36 */     this.m_flags = bb.get();
/*    */     
/* 38 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 47 */     return 3142;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getFlags()
/*    */   {
/* 54 */     return this.m_flags;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getUserName()
/*    */   {
/* 61 */     return this.m_userName;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\ChatUserFlagsMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */