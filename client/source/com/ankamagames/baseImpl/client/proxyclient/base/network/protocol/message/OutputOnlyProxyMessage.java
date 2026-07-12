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
/*    */ 
/*    */ public abstract class OutputOnlyProxyMessage
/*    */   extends ClientProxyMessage
/*    */ {
/*    */   public boolean decode(byte[] rawDatas) {
/* 23 */     throw new UnsupportedOperationException(String.valueOf(getClass().getName()) + " ne peut être décodé");
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\OutputOnlyProxyMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */