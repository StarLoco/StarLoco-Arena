/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FightActionMessage;
/*    */ import com.ankamagames.dofusarena.common.game.fight.FightActionType;
/*    */ import com.ankamagames.framework.kernel.core.maths.Direction8;
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
/*    */ public class FighterChangeDirectionMessage
/*    */   extends FightActionMessage
/*    */ {
/*    */   private long m_fighterId;
/*    */   private Direction8 m_direction;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 30 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/* 31 */     decodeFightActionHeader(buffer);
/* 32 */     this.m_fighterId = buffer.getLong();
/* 33 */     this.m_direction = Direction8.getDirectionFromIndex(buffer.get());
/* 34 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 43 */     return 4522;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getFighterId()
/*    */   {
/* 50 */     return this.m_fighterId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Direction8 getDirection()
/*    */   {
/* 57 */     return this.m_direction;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getActionId()
/*    */   {
/* 66 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public FightActionType getFightActionType()
/*    */   {
/* 75 */     return FightActionType.CHANGE_DIRECTION;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\fight\FighterChangeDirectionMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */