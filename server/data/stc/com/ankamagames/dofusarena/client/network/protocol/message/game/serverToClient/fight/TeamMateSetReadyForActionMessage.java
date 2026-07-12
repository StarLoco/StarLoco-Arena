/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight;
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
/*    */ public class TeamMateSetReadyForActionMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private long m_coachId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 28 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/* 29 */     this.m_coachId = buffer.getLong();
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
/* 40 */     return 8032;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getCoachId()
/*    */   {
/* 47 */     return this.m_coachId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\fight\TeamMateSetReadyForActionMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */