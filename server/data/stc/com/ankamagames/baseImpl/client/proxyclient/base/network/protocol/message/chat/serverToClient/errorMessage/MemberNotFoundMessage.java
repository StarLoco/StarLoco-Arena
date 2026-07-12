/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage;
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
/*    */ public class MemberNotFoundMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private String m_memberName;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 29 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 31 */     byte[] mn = new byte[bb.get()];
/* 32 */     bb.get(mn);
/*    */     
/* 34 */     this.m_memberName = StringUtils.fromUTF8(mn);
/*    */     
/* 36 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 45 */     return 3208;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getMemberName()
/*    */   {
/* 52 */     return this.m_memberName;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\errorMessage\MemberNotFoundMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */