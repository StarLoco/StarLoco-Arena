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
/*    */ public abstract class OutputOnlyProxyMessage
/*    */   extends ClientProxyMessage
/*    */ {
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 23 */     throw new UnsupportedOperationException(getClass().getName() + " ne peut être décodé");
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\OutputOnlyProxyMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */