/*     */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActorAppearMessage
/*     */   extends InputOnlyProxyMessage
/*     */ {
/*     */   public class CharacterAppearInformation
/*     */   {
/*     */     private long m_id;
/*     */     private int m_worldX;
/*     */     private int m_worldY;
/*     */     private short m_altitude;
/*     */     private Direction8 m_direction;
/*     */     
/*     */     public CharacterAppearInformation(long id, int worldX, int worldY, short altitude, byte directionIndex) {
/*  30 */       this.m_id = id;
/*  31 */       this.m_worldX = worldX;
/*  32 */       this.m_worldY = worldY;
/*  33 */       this.m_altitude = altitude;
/*  34 */       this.m_direction = Direction8.getDirectionFromIndex(directionIndex);
/*     */     }
/*     */     
/*     */     public long getId() {
/*  38 */       return this.m_id;
/*     */     }
/*     */     
/*     */     public int getWorldX() {
/*  42 */       return this.m_worldX;
/*     */     }
/*     */     
/*     */     public int getWorldY() {
/*  46 */       return this.m_worldY;
/*     */     }
/*     */     
/*     */     public short getAltitude() {
/*  50 */       return this.m_altitude;
/*     */     }
/*     */     
/*     */     public Direction8 getDirection() {
/*  54 */       return this.m_direction;
/*     */     }
/*     */   }
/*     */   
/*  58 */   private final ArrayList<CharacterAppearInformation> m_informations = new ArrayList<CharacterAppearInformation>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decode(byte[] rawDatas) {
/*  68 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*     */     
/*  70 */     this.m_informations.clear();
/*     */     
/*  72 */     int count = buffer.get();
/*  73 */     for (int i = 0; i < count; i++) {
/*  74 */       this.m_informations.add(new CharacterAppearInformation(buffer.getLong(), buffer.getInt(), buffer.getInt(), buffer.getShort(), buffer.get()));
/*     */     }
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  87 */     return 4102;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<CharacterAppearInformation> getCharacterInformations() {
/*  94 */     return this.m_informations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCharacterInformationsCount() {
/* 101 */     return this.m_informations.size();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\actor\ActorAppearMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */