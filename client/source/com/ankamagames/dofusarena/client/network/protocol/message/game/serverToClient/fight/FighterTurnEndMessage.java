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
/*    */ 
/*    */ public class FighterTurnEndMessage
/*    */   extends FightActionMessage
/*    */ {
/*    */   private long m_figtherId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 28 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/* 29 */     decodeFightActionHeader(buffer);
/*    */     
/* 31 */     this.m_figtherId = buffer.getLong();
/*    */     
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 42 */     return 8106;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getActionId() {
/* 51 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FightActionType getFightActionType() {
/* 60 */     return FightActionType.TURN_END;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getFighterId() {
/* 67 */     return this.m_figtherId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\fight\FighterTurnEndMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */