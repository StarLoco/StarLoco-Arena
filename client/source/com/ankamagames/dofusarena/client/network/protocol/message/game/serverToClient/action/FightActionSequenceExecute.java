/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
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
/*    */ public class FightActionSequenceExecute
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   public boolean decode(byte[] rawDatas) {
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 35 */     return 8200;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\action\FightActionSequenceExecute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */