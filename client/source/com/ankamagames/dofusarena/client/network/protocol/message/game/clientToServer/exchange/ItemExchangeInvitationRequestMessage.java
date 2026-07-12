/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.exchange;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
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
/*    */ 
/*    */ public class ItemExchangeInvitationRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_otherUserId;
/*    */   
/*    */   public byte[] encode() {
/* 32 */     ByteBuffer bb = ByteBuffer.allocate(8);
/* 33 */     bb.putLong(this.m_otherUserId);
/* 34 */     return addClientHeader((byte)3, bb.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 44 */     return 5101;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOtherUserId(long otherUserId) {
/* 51 */     this.m_otherUserId = otherUserId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\exchange\ItemExchangeInvitationRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */