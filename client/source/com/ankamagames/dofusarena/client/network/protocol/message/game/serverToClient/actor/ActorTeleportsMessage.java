/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor;
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
/*    */ 
/*    */ 
/*    */ public class ActorTeleportsMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private long m_actorId;
/*    */   private int m_worldX;
/*    */   private int m_worldY;
/*    */   private short m_altitude;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 32 */     if (!checkMessageSize(rawDatas.length, 18, true)) {
/* 33 */       return false;
/*    */     }
/* 35 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 37 */     this.m_actorId = bb.getLong();
/* 38 */     this.m_worldX = bb.getInt();
/* 39 */     this.m_worldY = bb.getInt();
/* 40 */     this.m_altitude = bb.getShort();
/*    */     
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 52 */     return 4510;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getActorId() {
/* 59 */     return this.m_actorId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public short getAltitude() {
/* 66 */     return this.m_altitude;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWorldX() {
/* 73 */     return this.m_worldX;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWorldY() {
/* 80 */     return this.m_worldY;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\actor\ActorTeleportsMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */