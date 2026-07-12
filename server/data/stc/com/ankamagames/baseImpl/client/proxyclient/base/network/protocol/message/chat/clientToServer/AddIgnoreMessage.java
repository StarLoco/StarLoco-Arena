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
/*    */ public class AddIgnoreMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private String m_ignoreName;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 31 */     byte[] in = new byte[0];
/*    */     
/* 33 */     in = StringUtils.toUTF8(this.m_ignoreName);
/*    */     
/* 35 */     ByteBuffer bb = ByteBuffer.allocate(1 + in.length);
/*    */     
/* 37 */     bb.put((byte)in.length);
/* 38 */     bb.put(in);
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
/* 49 */     return 3131;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setIgnoreName(String ignoreName)
/*    */   {
/* 56 */     this.m_ignoreName = ignoreName;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\clientToServer\AddIgnoreMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */