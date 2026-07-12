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
/*    */ 
/*    */ public abstract class QueryResultsResultsMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private int m_queryResultCode;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 24 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/* 25 */     decodeQueryResultCode(bb);
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 35 */     return 8195;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void decodeQueryResultCode(ByteBuffer buffer) {
/* 44 */     this.m_queryResultCode = buffer.getInt();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getQueryResultCode() {
/* 51 */     return this.m_queryResultCode;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\QueryResultsResultsMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */