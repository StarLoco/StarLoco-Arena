/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.HashMap;
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
/*    */ public class FighterInformationListMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/* 20 */   private final HashMap<Long, byte[]> m_fighterInformations = (HashMap)new HashMap<Long, byte>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 28 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 30 */     this.m_fighterInformations.clear();
/*    */     
/* 32 */     int fightersCount = buffer.get();
/* 33 */     for (int i = 0; i < fightersCount; i++) {
/* 34 */       long id = buffer.getLong();
/*    */       
/* 36 */       byte[] bArray = new byte[buffer.getShort()];
/* 37 */       buffer.get(bArray);
/* 38 */       this.m_fighterInformations.put(Long.valueOf(id), bArray);
/*    */     } 
/*    */ 
/*    */     
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 51 */     return 6006;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HashMap<Long, byte[]> getFighterInformations() {
/* 58 */     return this.m_fighterInformations;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\teamManagement\FighterInformationListMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */