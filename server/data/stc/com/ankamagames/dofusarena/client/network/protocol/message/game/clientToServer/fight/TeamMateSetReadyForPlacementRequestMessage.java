/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
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
/*    */ public class TeamMateSetReadyForPlacementRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   public byte[] encode()
/*    */   {
/* 25 */     return addClientHeader((byte)3, new byte[0]);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 35 */     return 8011;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\fight\TeamMateSetReadyForPlacementRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */