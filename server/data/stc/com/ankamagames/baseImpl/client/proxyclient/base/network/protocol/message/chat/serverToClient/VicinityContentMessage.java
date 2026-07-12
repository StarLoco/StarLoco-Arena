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
/*    */ public class VicinityContentMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_memberTalking;
/*    */   private long m_memberIDTalking;
/*    */   private String m_messageContent;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 31 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 33 */     byte[] mt = new byte[bb.get() & 0xFF];
/* 34 */     bb.get(mt);
/*    */     
/* 36 */     this.m_memberTalking = StringUtils.fromUTF8(mt);
/*    */     
/*    */ 
/* 39 */     this.m_memberIDTalking = bb.getLong();
/*    */     
/* 41 */     byte[] mc = new byte[bb.get() & 0xFF];
/* 42 */     bb.get(mc);
/*    */     
/* 44 */     this.m_messageContent = StringUtils.fromUTF8(mc);
/*    */     
/*    */ 
/* 47 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 54 */     return 3152;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getMemberIDTalking()
/*    */   {
/* 61 */     return this.m_memberIDTalking;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getMemberTalking()
/*    */   {
/* 68 */     return this.m_memberTalking;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getMessageContent()
/*    */   {
/* 75 */     return this.m_messageContent;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\VicinityContentMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */