/*    */ package com.ankamagames.dofusarena.client.chat;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatMessage;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.graphicalClient.chat.AbstractChatView;
/*    */ import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
/*    */ import com.ankamagames.dofusarena.client.chat.console.command.VicinityContentCommand;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatView
/*    */   extends AbstractChatView
/*    */ {
/* 24 */   private static Logger m_logger = Logger.getLogger(ChatView.class);
/*    */   
/*    */   public ChatView(int viewId) {
/* 27 */     super(viewId);
/*    */     
/* 29 */     this.m_console.setGarbageCommand(new VicinityContentCommand());
/*    */     
/* 31 */     if (DofusArenaClientConstants.CHAT_COMMANDS_PATH != null) {
/* 32 */       this.m_console.addCommandListFromXmlFile(DofusArenaClientConstants.CHAT_COMMANDS_PATH);
/*    */     } else {
/* 34 */       m_logger.error("Impossible de charger les commandes de chat !");
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected String formatMessage(ChatMessage message)
/*    */   {
/* 47 */     StringBuilder formattedMessage = new StringBuilder();
/*    */     
/* 49 */     switch (message.getPipeDestination()) {
/*    */     case 6: 
/* 51 */       formattedMessage.append("<color=").append("4BFF21").append("|").append(message.getMessage()).append(">\n");
/* 52 */       break;
/*    */     case 5: 
/* 54 */       formattedMessage.append("<color=").append("4BFF21").append("|").append(message.getMessage()).append(">\n");
/* 55 */       break;
/*    */     case 2: 
/* 57 */       formattedMessage.append("<color=").append("F26AD7").append("|");
/*    */       String messageTranslatorKey;
/*    */       String messageTranslatorKey;
/* 60 */       if (message.getSourceId() != DofusArenaGameEntity.getInstance().getLocalCoach().getId()) {
/* 61 */         messageTranslatorKey = "chat.privateMessageFrom";
/*    */       } else {
/* 63 */         messageTranslatorKey = "chat.privateMessageTo";
/*    */       }
/* 65 */       formattedMessage.append(DofusArenaTranslator.getInstance().getString(messageTranslatorKey, new Object[] { message.getSourceName(), message.getMessage() }));
/* 66 */       formattedMessage.append(">\n");
/*    */       
/* 68 */       break;
/*    */     
/*    */     case 4: 
/* 71 */       if (message.getMessage() != null) {
/* 72 */         if (message.getMessage().length() > 0) {
/* 73 */           formattedMessage.append("<color=").append("FF2727").append("|").append(message.getMessage()).append(">\n");
/*    */         } else {
/* 75 */           formattedMessage.append("<color=").append("FF2727").append("|").append("error").append(">\n");
/*    */         }
/*    */       } else {
/* 78 */         formattedMessage.append("<color=").append("FF2727").append("|").append("error").append(">\n");
/*    */       }
/*    */       
/* 81 */       break;
/*    */     case 3: default: 
/* 83 */       formattedMessage.append("<color=").append("FFFFFF").append("|").append(message.getSourceName()).append(" : ").append(message.getMessage()).append(">\n");
/*    */     }
/*    */     
/* 86 */     return formattedMessage.toString();
/*    */   }
/*    */   
/*    */ 
/*    */   public void err(String text)
/*    */   {
/* 92 */     String errorMessage = DofusArenaTranslator.getInstance().getString("error.chat.malformedCommand", new Object[0]);
/* 93 */     ChatMessage message = new ChatMessage(errorMessage);
/* 94 */     message.setPipeDestination(4);
/* 95 */     ChatManager.getInstance().pushMessage(message);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\chat\ChatView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */