/*    */ package com.ankamagames.dofusarena.client.ui.actions;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.exchange.UIExchangeMoveCardMessage;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.exchange.UIReadyForExchangeRequestMessage;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*    */ import com.ankamagames.xulor.event.DragEvent;
/*    */ import com.ankamagames.xulor.event.DropEvent;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.event.ItemDoubleClickEvent;
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
/*    */ public class ExchangeActions
/*    */ {
/*    */   public static final String PACKAGE = "dofusarena.exchange";
/*    */   
/*    */   public static void setReadyForExchange(Event event, Long exchangeId) {
/* 34 */     UIReadyForExchangeRequestMessage message = new UIReadyForExchangeRequestMessage();
/* 35 */     message.setExchangeId(exchangeId.longValue());
/* 36 */     Worker.getInstance().pushMessage((Message)message);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void dragCard(DragEvent event, Long exchangeId) {
/* 41 */     Object value = event.getValue();
/* 42 */     if (value != null && value instanceof CoachCard) {
/*    */ 
/*    */       
/* 45 */       UIExchangeMoveCardMessage message = new UIExchangeMoveCardMessage();
/* 46 */       message.setId(16808);
/* 47 */       message.setExchangeId(exchangeId.longValue());
/* 48 */       message.setCoachCard((CoachCard)value);
/*    */       
/* 50 */       Worker.getInstance().pushMessage((Message)message);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void removeCard(ItemDoubleClickEvent event, Long exchangeId) {
/* 55 */     Object value = event.getItemValue();
/* 56 */     if (value != null && value instanceof CoachCard) {
/*    */ 
/*    */       
/* 59 */       UIExchangeMoveCardMessage message = new UIExchangeMoveCardMessage();
/* 60 */       message.setId(16808);
/* 61 */       message.setExchangeId(exchangeId.longValue());
/* 62 */       message.setCoachCard((CoachCard)value);
/*    */       
/* 64 */       Worker.getInstance().pushMessage((Message)message);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void dropCard(DropEvent event, Long exchangeId) {
/* 69 */     Object value = event.getValue();
/* 70 */     if (value != null && value instanceof CoachCard) {
/*    */ 
/*    */       
/* 73 */       UIExchangeMoveCardMessage message = new UIExchangeMoveCardMessage();
/* 74 */       message.setId(16807);
/* 75 */       message.setExchangeId(exchangeId.longValue());
/* 76 */       message.setCoachCard((CoachCard)value);
/*    */       
/* 78 */       Worker.getInstance().pushMessage((Message)message);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void closeCoachExchangeDialog(Event event, Long exchangeId) {
/* 85 */     UIMessage message = new UIMessage();
/* 86 */     message.setId(16809);
/* 87 */     message.setLongValue(exchangeId.longValue());
/* 88 */     Worker.getInstance().pushMessage((Message)message);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\actions\ExchangeActions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */