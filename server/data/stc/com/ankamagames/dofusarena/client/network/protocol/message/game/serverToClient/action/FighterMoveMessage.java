/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action;
/*    */ 
/*    */ import com.ankamagames.dofusarena.common.game.fight.FightActionType;
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
/*    */ public class FighterMoveMessage
/*    */   extends FightActionMessage
/*    */ {
/* 20 */   private static int CELL_BYTE_SIZE = 10;
/*    */   
/*    */ 
/*    */ 
/*    */   private long m_fighterId;
/*    */   
/*    */ 
/*    */   private PathFindResult m_pathFindResult;
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 33 */     if (!checkMessageSize(rawDatas.length, 16, false)) {
/* 34 */       return false;
/*    */     }
/* 36 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 38 */     decodeFightActionHeader(bb);
/*    */     
/* 40 */     this.m_fighterId = bb.getLong();
/*    */     
/* 42 */     int pathLength = (rawDatas.length - 8) / CELL_BYTE_SIZE;
/*    */     
/* 44 */     int currentLength = 0;
/* 45 */     this.m_pathFindResult = new PathFindResult(pathLength);
/* 46 */     while (bb.remaining() != 0)
/*    */     {
/* 48 */       int worldX = bb.getInt();
/* 49 */       int worldY = bb.getInt();
/* 50 */       short altitude = bb.getShort();
/*    */       
/* 52 */       this.m_pathFindResult.setStep(currentLength++, worldX, worldY, altitude);
/*    */     }
/*    */     
/* 55 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 65 */     return 4524;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getFighterId()
/*    */   {
/* 72 */     return this.m_fighterId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getActionId()
/*    */   {
/* 81 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public FightActionType getFightActionType()
/*    */   {
/* 90 */     return FightActionType.MOVE;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public PathFindResult getPathResult()
/*    */   {
/* 97 */     return this.m_pathFindResult;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\action\FighterMoveMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */