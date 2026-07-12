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
/*    */ public class FightInvitationRejectedMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private long m_invitationId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 28 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/* 29 */     this.m_invitationId = buffer.getLong();
/* 30 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 40 */     return 4304;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getInvitationId()
/*    */   {
/* 47 */     return this.m_invitationId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\FightInvitationRejectedMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */