/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient;
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
/*    */ public class FightInvitationAcceptedMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private long m_invitationId;
/*    */   private long m_fightId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 29 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/* 30 */     this.m_invitationId = buffer.getLong();
/* 31 */     this.m_fightId = buffer.getLong();
/* 32 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 42 */     return 4302;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getInvitationId()
/*    */   {
/* 49 */     return this.m_invitationId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getFightId()
/*    */   {
/* 56 */     return this.m_fightId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\FightInvitationAcceptedMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */