/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message;
/*    */ 
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
/*    */ public class DefaultResultsMessage
/*    */   extends QueryResultsResultsMessage
/*    */ {
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 22 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/* 23 */     decodeQueryResultCode(bb);
/* 24 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 33 */     return 8195;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\DefaultResultsMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */