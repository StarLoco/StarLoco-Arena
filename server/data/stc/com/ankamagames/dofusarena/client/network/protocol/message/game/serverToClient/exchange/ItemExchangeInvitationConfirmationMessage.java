/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
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
/*    */ public class ItemExchangeInvitationConfirmationMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private byte m_invitationResult;
/*    */   private long m_exchangeId;
/*    */   private long m_requestedId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 31 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 33 */     this.m_invitationResult = buffer.get();
/* 34 */     this.m_exchangeId = buffer.getLong();
/* 35 */     this.m_requestedId = buffer.getLong();
/*    */     
/* 37 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 47 */     return 5104;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getExchangeId()
/*    */   {
/* 54 */     return this.m_exchangeId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getRequestedId()
/*    */   {
/* 61 */     return this.m_requestedId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getInvitationResult()
/*    */   {
/* 68 */     return this.m_invitationResult;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\exchange\ItemExchangeInvitationConfirmationMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */