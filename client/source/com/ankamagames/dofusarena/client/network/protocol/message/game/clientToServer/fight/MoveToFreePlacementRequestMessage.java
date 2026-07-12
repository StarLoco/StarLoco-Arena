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
/*    */ public class MoveToFreePlacementRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_fighterId;
/*    */   private int m_worldX;
/*    */   private int m_worldY;
/*    */   private short m_altitude;
/*    */   
/*    */   public byte[] encode() {
/* 32 */     ByteBuffer buffer = ByteBuffer.allocate(18);
/*    */     
/* 34 */     buffer.putLong(this.m_fighterId);
/* 35 */     buffer.putInt(this.m_worldX);
/* 36 */     buffer.putInt(this.m_worldY);
/* 37 */     buffer.putShort(this.m_altitude);
/*    */     
/* 39 */     return addClientHeader((byte)3, buffer.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 49 */     return 8021;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAltitude(short altitude) {
/* 56 */     this.m_altitude = altitude;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFighterId(long fighterId) {
/* 63 */     this.m_fighterId = fighterId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setWorldX(int worldX) {
/* 70 */     this.m_worldX = worldX;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setWorldY(int worldY) {
/* 77 */     this.m_worldY = worldY;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\fight\MoveToFreePlacementRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */