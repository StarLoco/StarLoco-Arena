/*    */ package com.ankamagames.dofusarena.client.ui.actions;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*    */ import com.ankamagames.xulor.core.form.Form;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.property.Property;
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
/*    */ 
/*    */ public class RandomFightSearchActions
/*    */ {
/*    */   public static final String PACKAGE = "dofusarena.randomFightSearch";
/*    */   
/*    */   public static void startRandomFightSearch(Event event, Form form)
/*    */   {
/* 32 */     int bet = 0;
/*    */     
/*    */ 
/* 35 */     form.synchronizeProperties();
/* 36 */     Property property = form.getProperty("randomFight.withBet");
/* 37 */     if (property != null) {
/* 38 */       bet = property.getBoolean() ? 1 : 0;
/*    */     }
/*    */     
/*    */ 
/* 42 */     UIMessage message = new UIMessage();
/* 43 */     message.setId(19501);
/* 44 */     message.setIntValue(bet);
/* 45 */     Worker.getInstance().pushMessage(message);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void closeRandomFightCreation(Event event)
/*    */   {
/* 55 */     UIMessage message = new UIMessage();
/* 56 */     message.setId(19502);
/* 57 */     Worker.getInstance().pushMessage(message);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void cancelRandomFightSearch(Event event)
/*    */   {
/* 67 */     UIMessage message = new UIMessage();
/* 68 */     message.setId(19503);
/* 69 */     Worker.getInstance().pushMessage(message);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\actions\RandomFightSearchActions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */