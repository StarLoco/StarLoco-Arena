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
/*    */ public class CoachActorMovementRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private PathFindResult m_pathResult;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 33 */     int numCells = this.m_pathResult.getPathLength();
/* 34 */     int lCells = numCells * 10;
/*    */     
/* 36 */     ByteBuffer bb = ByteBuffer.allocate(lCells);
/*    */     
/* 38 */     for (int i = 0; i < numCells; i++)
/*    */     {
/* 40 */       int[] step = this.m_pathResult.getPathStep(i);
/*    */       
/* 42 */       bb.putInt(step[0]);
/* 43 */       bb.putInt(step[1]);
/* 44 */       bb.putShort((short)step[2]);
/*    */     }
/*    */     
/* 47 */     return addClientHeader((byte)3, bb.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 57 */     return 4501;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setPathResult(PathFindResult pathResult)
/*    */   {
/* 64 */     this.m_pathResult = pathResult;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\CoachActorMovementRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */