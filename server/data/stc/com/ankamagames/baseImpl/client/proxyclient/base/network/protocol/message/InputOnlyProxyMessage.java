/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message;
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
/*    */ public abstract class InputOnlyProxyMessage
/*    */   extends ClientProxyMessage
/*    */ {
/*    */   public byte[] encode()
/*    */   {
/* 23 */     throw new UnsupportedOperationException(getClass().getName() + " ne peut être encodé");
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\InputOnlyProxyMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */