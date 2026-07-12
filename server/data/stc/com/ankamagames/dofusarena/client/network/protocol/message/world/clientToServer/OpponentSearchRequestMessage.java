/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.world.clientToServer;
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
/*    */ public class OpponentSearchRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private byte m_fightTypeId;
/*    */   private int m_bet;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 29 */     ByteBuffer buffer = ByteBuffer.allocate(5);
/* 30 */     buffer.put(this.m_fightTypeId);
/* 31 */     buffer.putInt(this.m_bet);
/* 32 */     return addClientHeader((byte)2, buffer.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 41 */     return 2301;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setFightTypeId(byte fightTypeId)
/*    */   {
/* 48 */     this.m_fightTypeId = fightTypeId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setBet(int bet)
/*    */   {
/* 55 */     this.m_bet = bet;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\world\clientToServer\OpponentSearchRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */