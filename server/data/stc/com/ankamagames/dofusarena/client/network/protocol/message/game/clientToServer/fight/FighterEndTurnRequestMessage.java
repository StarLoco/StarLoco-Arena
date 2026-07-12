/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight;
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
/*    */ public class FighterEndTurnRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_fighterId;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 29 */     ByteBuffer buffer = ByteBuffer.allocate(8);
/*    */     
/* 31 */     buffer.putLong(this.m_fighterId);
/*    */     
/* 33 */     return addClientHeader((byte)3, buffer.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 43 */     return 8105;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setFighterId(long fighterId)
/*    */   {
/* 50 */     this.m_fighterId = fighterId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\fight\FighterEndTurnRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */