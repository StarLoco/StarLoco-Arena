/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.serverStatus;
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
/*    */ public class NoInstanceServerAvailableMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 23 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 32 */     return 5000;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\serverStatus\NoInstanceServerAvailableMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */