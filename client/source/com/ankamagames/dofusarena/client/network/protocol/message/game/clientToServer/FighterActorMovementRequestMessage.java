/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
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
/*    */ public class FighterActorMovementRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private PathFindResult m_pathResult;
/*    */   private long m_fighterId;
/*    */   
/*    */   public byte[] encode() {
/* 34 */     int numCells = this.m_pathResult.getPathLength();
/* 35 */     int lCells = numCells * 10;
/*    */     
/* 37 */     ByteBuffer bb = ByteBuffer.allocate(8 + lCells);
/*    */     
/* 39 */     bb.putLong(this.m_fighterId);
/*    */     
/* 41 */     for (int i = 0; i < numCells; i++) {
/*    */       
/* 43 */       int[] step = this.m_pathResult.getPathStep(i);
/*    */       
/* 45 */       bb.putInt(step[0]);
/* 46 */       bb.putInt(step[1]);
/* 47 */       bb.putShort((short)step[2]);
/*    */     } 
/*    */     
/* 50 */     return addClientHeader((byte)3, bb.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 60 */     return 4503;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPathResult(PathFindResult pathResult) {
/* 67 */     this.m_pathResult = pathResult;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFighterId(long fighterId) {
/* 74 */     this.m_fighterId = fighterId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\FighterActorMovementRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */