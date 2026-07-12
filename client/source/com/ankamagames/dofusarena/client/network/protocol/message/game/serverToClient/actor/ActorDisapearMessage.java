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
/*    */ public class ActorDisapearMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/* 20 */   private final ArrayList<Long> m_characterIds = new ArrayList<Long>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 30 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 32 */     int count = buffer.getShort();
/* 33 */     for (int i = 0; i < count; i++)
/* 34 */       this.m_characterIds.add(Long.valueOf(buffer.getLong())); 
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 45 */     return 4104;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterable<Long> getCharacterIds() {
/* 52 */     return this.m_characterIds;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCharacterIDsCount() {
/* 59 */     return this.m_characterIds.size();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\actor\ActorDisapearMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */