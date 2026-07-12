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
/*    */ public class FightInvitationRejectMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_invitationId;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 29 */     ByteBuffer buffer = ByteBuffer.allocate(8);
/* 30 */     buffer.putLong(this.m_invitationId);
/* 31 */     return addClientHeader((byte)2, buffer.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 41 */     return 4307;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setInvitationId(long fightId)
/*    */   {
/* 48 */     this.m_invitationId = fightId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\FightInvitationRejectMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */