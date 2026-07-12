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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FighterCardUseRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_fighterId;
/*    */   private int m_cardId;
/*    */   private int m_usePositionX;
/*    */   private int m_usePositionY;
/*    */   private short m_usePositionZ;
/*    */   
/*    */   public byte[] encode() {
/* 36 */     ByteBuffer bb = ByteBuffer.allocate(22);
/*    */     
/* 38 */     bb.putLong(this.m_fighterId);
/* 39 */     bb.putInt(this.m_cardId);
/* 40 */     bb.putInt(this.m_usePositionX);
/* 41 */     bb.putInt(this.m_usePositionY);
/* 42 */     bb.putShort(this.m_usePositionZ);
/*    */     
/* 44 */     return addClientHeader((byte)3, bb.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 54 */     return 8107;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFighterId(long fighterId) {
/* 61 */     this.m_fighterId = fighterId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCardId(int cardId) {
/* 68 */     this.m_cardId = cardId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setUsePosition(int x, int y, short z) {
/* 77 */     this.m_usePositionX = x;
/* 78 */     this.m_usePositionY = y;
/* 79 */     this.m_usePositionZ = z;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\fight\FighterCardUseRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */