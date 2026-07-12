/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action;
/*    */ 
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
/*    */ public class FighterDiesMessage
/*    */   extends FightActionMessage
/*    */ {
/*    */   private long m_fighterId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 21 */     if (!checkMessageSize(rawDatas.length, 16, true)) {
/* 22 */       return false;
/*    */     }
/* 24 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 26 */     decodeFightActionHeader(bb);
/* 27 */     this.m_fighterId = bb.getLong();
/*    */     
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 38 */     return 4520;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getFighterId() {
/* 45 */     return this.m_fighterId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getActionId() {
/* 54 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FightActionType getFightActionType() {
/* 63 */     return FightActionType.DIE;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\action\FighterDiesMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */