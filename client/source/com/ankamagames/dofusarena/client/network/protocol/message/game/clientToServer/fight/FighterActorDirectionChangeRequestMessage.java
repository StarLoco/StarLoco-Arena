/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
/*    */ import com.ankamagames.framework.kernel.core.maths.Direction8;
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
/*    */ public class FighterActorDirectionChangeRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_fighterId;
/*    */   private Direction8 m_direction8;
/*    */   
/*    */   public byte[] encode() {
/* 31 */     ByteBuffer buffer = ByteBuffer.allocate(9);
/*    */     
/* 33 */     buffer.putLong(this.m_fighterId);
/* 34 */     buffer.put((byte)this.m_direction8.getIndex());
/*    */     
/* 36 */     return addClientHeader((byte)3, buffer.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 46 */     return 4521;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFighterId(long fighterId) {
/* 53 */     this.m_fighterId = fighterId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDirection8(Direction8 direction8) {
/* 60 */     this.m_direction8 = direction8;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\fight\FighterActorDirectionChangeRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */