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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FighterTackledMessage
/*    */   extends FightActionMessage
/*    */ {
/*    */   private long m_tackledFighterId;
/*    */   private long m_tacklerId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 30 */     if (!checkMessageSize(rawDatas.length, 24, true)) {
/* 31 */       return false;
/*    */     }
/* 33 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*    */     
/* 35 */     decodeFightActionHeader(bb);
/*    */     
/* 37 */     this.m_tackledFighterId = bb.getLong();
/* 38 */     this.m_tacklerId = bb.getLong();
/*    */     
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 50 */     return 4506;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getTackledFighterId() {
/* 57 */     return this.m_tackledFighterId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getTacklerId() {
/* 64 */     return this.m_tacklerId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getActionId() {
/* 73 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FightActionType getFightActionType() {
/* 82 */     return FightActionType.TACKLE;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\action\FighterTackledMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */