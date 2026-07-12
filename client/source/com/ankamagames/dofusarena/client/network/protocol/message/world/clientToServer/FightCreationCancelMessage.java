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
/*    */ 
/*    */ public class FightCreationCancelMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_fightId;
/*    */   
/*    */   public byte[] encode() {
/* 28 */     ByteBuffer buffer = ByteBuffer.allocate(8);
/* 29 */     buffer.putLong(this.m_fightId);
/* 30 */     return addClientHeader((byte)2, buffer.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 39 */     return 4311;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFightId(long fightId) {
/* 46 */     this.m_fightId = fightId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\world\clientToServer\FightCreationCancelMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */