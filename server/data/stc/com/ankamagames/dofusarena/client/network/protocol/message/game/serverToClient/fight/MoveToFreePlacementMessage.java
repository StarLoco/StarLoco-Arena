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
/*    */ public class MoveToFreePlacementMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private long m_fighterId;
/*    */   private int m_worldX;
/*    */   private int m_worldY;
/*    */   private short m_altitude;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 31 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 33 */     this.m_fighterId = buffer.getLong();
/* 34 */     this.m_worldX = buffer.getInt();
/* 35 */     this.m_worldY = buffer.getInt();
/* 36 */     this.m_altitude = buffer.getShort();
/*    */     
/* 38 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 48 */     return 8022;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public short getAltitude()
/*    */   {
/* 55 */     return this.m_altitude;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getFighterId()
/*    */   {
/* 62 */     return this.m_fighterId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getWorldX()
/*    */   {
/* 69 */     return this.m_worldX;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getWorldY()
/*    */   {
/* 76 */     return this.m_worldY;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\fight\MoveToFreePlacementMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */