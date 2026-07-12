/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
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
/*    */ public class ActorMovementMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/* 20 */   private static int CELL_BYTE_SIZE = 10;
/*    */ 
/*    */ 
/*    */   
/*    */   private long m_actorId;
/*    */ 
/*    */ 
/*    */   
/*    */   private PathFindResult m_pathFindResult;
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 33 */     if (!checkMessageSize(rawDatas.length, 8, false)) {
/* 34 */       return false;
/*    */     }
/* 36 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 38 */     this.m_actorId = bb.getLong();
/*    */     
/* 40 */     int pathLength = (rawDatas.length - 8) / CELL_BYTE_SIZE;
/*    */     
/* 42 */     int currentLength = 0;
/* 43 */     this.m_pathFindResult = new PathFindResult(pathLength);
/* 44 */     while (bb.remaining() != 0) {
/*    */       
/* 46 */       int worldX = bb.getInt();
/* 47 */       int worldY = bb.getInt();
/* 48 */       short altitude = bb.getShort();
/*    */       
/* 50 */       this.m_pathFindResult.setStep(currentLength++, worldX, worldY, altitude);
/*    */     } 
/*    */     
/* 53 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 63 */     return 4500;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getActorId() {
/* 70 */     return this.m_actorId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PathFindResult getPathResult() {
/* 77 */     return this.m_pathFindResult;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\actor\ActorMovementMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */