/*    */ package com.ankamagames.dofusarena.client.core.action;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatMessage;
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
/*    */ public class FightLogger
/*    */ {
/*    */   public void info(Object message)
/*    */   {
/* 24 */     ChatMessage chatMessage = new ChatMessage(message.toString());
/* 25 */     chatMessage.setPipeDestination(6);
/*    */     
/* 27 */     ChatManager.getInstance().pushMessage(chatMessage);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void error(Object message)
/*    */   {
/* 36 */     ChatMessage chatMessage = new ChatMessage(message.toString());
/* 37 */     chatMessage.setPipeDestination(6);
/*    */     
/* 39 */     ChatManager.getInstance().pushMessage(chatMessage);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\FightLogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */