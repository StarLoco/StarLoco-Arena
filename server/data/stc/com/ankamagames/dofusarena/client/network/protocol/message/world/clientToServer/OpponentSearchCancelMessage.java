/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.world.clientToServer;
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
/*    */ public class OpponentSearchCancelMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   public byte[] encode()
/*    */   {
/* 21 */     return addClientHeader((byte)2, new byte[0]);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 30 */     return 2303;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\world\clientToServer\OpponentSearchCancelMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */