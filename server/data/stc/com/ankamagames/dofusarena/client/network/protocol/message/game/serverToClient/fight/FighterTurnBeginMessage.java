/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FightActionMessage;
/*    */ import com.ankamagames.dofusarena.common.game.fight.FightActionType;
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
/*    */ public class FighterTurnBeginMessage
/*    */   extends FightActionMessage
/*    */ {
/*    */   private long m_fighterId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 28 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/* 29 */     decodeFightActionHeader(buffer);
/*    */     
/* 31 */     this.m_fighterId = buffer.getLong();
/* 32 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 41 */     return 8104;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getFighterId()
/*    */   {
/* 48 */     return this.m_fighterId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getActionId()
/*    */   {
/* 57 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public FightActionType getFightActionType()
/*    */   {
/* 66 */     return FightActionType.TURN_START;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\fight\FighterTurnBeginMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */