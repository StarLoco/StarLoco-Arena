/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.exchange;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
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
/*    */ public class UIReadyForExchangeRequestMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private long m_exchangeId;
/*    */   
/*    */   public int getId() {
/* 25 */     return 16803;
/*    */   }
/*    */   
/*    */   public void setExchangeId(long exchangeId) {
/* 29 */     this.m_exchangeId = exchangeId;
/*    */   }
/*    */   
/*    */   public long getExchangeId() {
/* 33 */     return this.m_exchangeId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\exchange\UIReadyForExchangeRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */