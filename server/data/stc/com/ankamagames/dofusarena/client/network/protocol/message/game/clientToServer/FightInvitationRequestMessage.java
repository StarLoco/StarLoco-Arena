/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer;
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
/*    */ public class FightInvitationRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_targetCoachId;
/*    */   private byte m_fightTypeId;
/*    */   private int m_bet;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 31 */     ByteBuffer buffer = ByteBuffer.allocate(13);
/*    */     
/* 33 */     buffer.putLong(this.m_targetCoachId);
/* 34 */     buffer.put(this.m_fightTypeId);
/* 35 */     buffer.putInt(this.m_bet);
/*    */     
/* 37 */     return addClientHeader((byte)2, buffer.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 47 */     return 4301;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setTargetCoachId(long targetCoachID)
/*    */   {
/* 54 */     this.m_targetCoachId = targetCoachID;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setFightTypeId(byte fightTypeId)
/*    */   {
/* 61 */     this.m_fightTypeId = fightTypeId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setBet(int bet)
/*    */   {
/* 68 */     this.m_bet = bet;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\FightInvitationRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */