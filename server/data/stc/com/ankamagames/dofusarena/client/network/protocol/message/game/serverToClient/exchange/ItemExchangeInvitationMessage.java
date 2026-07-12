/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemExchangeInvitationMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private long m_exchangeId;
/*    */   private long m_requesterId;
/*    */   private String m_requesterName;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 34 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/* 35 */     this.m_exchangeId = buffer.getLong();
/* 36 */     this.m_requesterId = buffer.getLong();
/* 37 */     byte[] data = new byte[buffer.get() & 0xFF];
/* 38 */     buffer.get(data);
/* 39 */     this.m_requesterName = StringUtils.fromUTF8(data);
/* 40 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 51 */     return 5102;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getExchangeId()
/*    */   {
/* 58 */     return this.m_exchangeId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getRequesterId()
/*    */   {
/* 65 */     return this.m_requesterId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getRequesterName()
/*    */   {
/* 72 */     return this.m_requesterName;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\exchange\ItemExchangeInvitationMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */