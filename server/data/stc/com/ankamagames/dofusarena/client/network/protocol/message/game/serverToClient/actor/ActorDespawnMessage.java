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
/*    */ public class ActorDespawnMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/* 20 */   private final ArrayList<Long> m_actorIds = new ArrayList();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 30 */     if (!checkMessageSize(rawDatas.length, 1, false)) {
/* 31 */       return false;
/*    */     }
/* 33 */     this.m_actorIds.clear();
/*    */     
/* 35 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 37 */     int num = bb.get();
/*    */     
/* 39 */     for (int i = 0; i < num; i++) {
/* 40 */       long id = bb.getLong();
/*    */       
/* 42 */       this.m_actorIds.add(Long.valueOf(id));
/*    */     }
/* 44 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 54 */     return 4098;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ArrayList<Long> getActorIds()
/*    */   {
/* 61 */     return this.m_actorIds;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\actor\ActorDespawnMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */