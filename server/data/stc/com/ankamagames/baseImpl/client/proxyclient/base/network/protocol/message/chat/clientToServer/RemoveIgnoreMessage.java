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
/*    */ public class RemoveIgnoreMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private String m_ignoreName;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 31 */     byte[] in = new byte[0];
/* 32 */     in = StringUtils.toUTF8(this.m_ignoreName);
/*    */     
/* 34 */     ByteBuffer bb = ByteBuffer.allocate(1 + in.length);
/*    */     
/* 36 */     bb.put((byte)in.length);
/* 37 */     bb.put(in);
/*    */     
/* 39 */     return addClientHeader((byte)4, bb.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 48 */     return 3135;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setIgnoreName(String ignoreName)
/*    */   {
/* 55 */     this.m_ignoreName = ignoreName;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\clientToServer\RemoveIgnoreMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */