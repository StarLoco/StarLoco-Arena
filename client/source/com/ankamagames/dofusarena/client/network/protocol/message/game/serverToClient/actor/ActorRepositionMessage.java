/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
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
/*    */ public class ActorRepositionMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   public class CharacterAppearInformation
/*    */   {
/*    */     private long m_id;
/*    */     private int m_worldX;
/*    */     private int m_worldY;
/*    */     private short m_altitude;
/*    */     
/*    */     public CharacterAppearInformation(long id, int worldX, int worldY, short altitude) {
/* 28 */       this.m_id = id;
/* 29 */       this.m_worldX = worldX;
/* 30 */       this.m_worldY = worldY;
/* 31 */       this.m_altitude = altitude;
/*    */     }
/*    */     
/*    */     public long getId() {
/* 35 */       return this.m_id;
/*    */     }
/*    */     
/*    */     public int getWorldX() {
/* 39 */       return this.m_worldX;
/*    */     }
/*    */     
/*    */     public int getWorldY() {
/* 43 */       return this.m_worldY;
/*    */     }
/*    */     
/*    */     public short getAltitude() {
/* 47 */       return this.m_altitude;
/*    */     }
/*    */   }
/*    */   
/* 51 */   private final ArrayList<CharacterAppearInformation> m_informations = new ArrayList<CharacterAppearInformation>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 61 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 63 */     this.m_informations.clear();
/*    */     
/* 65 */     int count = buffer.getShort();
/* 66 */     for (int i = 0; i < count; i++) {
/* 67 */       this.m_informations.add(new CharacterAppearInformation(buffer.getLong(), buffer.getInt(), buffer.getInt(), buffer.getShort()));
/*    */     }
/*    */     
/* 70 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 80 */     return 4106;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterable<CharacterAppearInformation> getCharacterInformations() {
/* 87 */     return this.m_informations;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCharacterInformationsCount() {
/* 94 */     return this.m_informations.size();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\actor\ActorRepositionMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */