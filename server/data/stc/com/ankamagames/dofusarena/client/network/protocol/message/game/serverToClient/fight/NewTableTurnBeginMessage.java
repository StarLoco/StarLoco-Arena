/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.game.event.Event;
/*    */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FightActionMessage;
/*    */ import com.ankamagames.dofusarena.common.game.event.AbstractEventManager;
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
/*    */ public class NewTableTurnBeginMessage
/*    */   extends FightActionMessage
/*    */ {
/*    */   private byte m_numTurns;
/*    */   private Event m_event;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 32 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/* 33 */     decodeFightActionHeader(buffer);
/* 34 */     this.m_numTurns = buffer.get();
/* 35 */     int eventId = buffer.getInt();
/* 36 */     this.m_event = ((Event)AbstractEventManager.getInstance().getAbstractEventFromId(eventId));
/*    */     
/* 38 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 48 */     return 8100;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getNumTurns()
/*    */   {
/* 55 */     return this.m_numTurns;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Event getEvent()
/*    */   {
/* 62 */     return this.m_event;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getActionId()
/*    */   {
/* 71 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public FightActionType getFightActionType()
/*    */   {
/* 80 */     return FightActionType.NEW_TABLE_TURN;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\fight\NewTableTurnBeginMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */