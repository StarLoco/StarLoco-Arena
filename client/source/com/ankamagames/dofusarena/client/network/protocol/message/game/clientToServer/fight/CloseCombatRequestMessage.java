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
/*    */ public class CloseCombatRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_fighterId;
/*    */   private int m_usePositionX;
/*    */   private int m_usePositionY;
/*    */   private short m_usePositionZ;
/*    */   
/*    */   public byte[] encode() {
/* 35 */     ByteBuffer bb = ByteBuffer.allocate(18);
/*    */     
/* 37 */     bb.putLong(this.m_fighterId);
/* 38 */     bb.putInt(this.m_usePositionX);
/* 39 */     bb.putInt(this.m_usePositionY);
/* 40 */     bb.putShort(this.m_usePositionZ);
/*    */     
/* 42 */     return addClientHeader((byte)3, bb.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 52 */     return 8111;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFighterId(long fighterId) {
/* 59 */     this.m_fighterId = fighterId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setUsePosition(int x, int y, short z) {
/* 68 */     this.m_usePositionX = x;
/* 69 */     this.m_usePositionY = y;
/* 70 */     this.m_usePositionZ = z;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\fight\CloseCombatRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */