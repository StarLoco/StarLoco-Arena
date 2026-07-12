/*    */ package com.ankamagames.dofusarena.client.chat.console.command;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatMessage;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.clientToServer.UserPrivateContentMessage;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.utils.WordsModerator;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ public class PrivateContentCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/* 38 */     String dest = ((String)args.get(2)).replaceAll("\"", "");
/*    */ 
/*    */     
/* 41 */     String commandLine = args.get(3);
/*    */     
/* 43 */     LocalCoach coach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*    */     
/* 45 */     if (coach != null) {
/*    */ 
/*    */       
/* 48 */       commandLine = WordsModerator.makeValidSentence(commandLine);
/*    */ 
/*    */ 
/*    */       
/* 52 */       if (commandLine.equals("")) {
/*    */         
/* 54 */         String errorMessage = DofusArenaTranslator.getInstance().getString("error.chat.operationNotPermited", new Object[0]);
/* 55 */         ChatMessage message = new ChatMessage(errorMessage);
/* 56 */         message.setPipeDestination(4);
/* 57 */         ChatManager.getInstance().pushMessage(message);
/*    */ 
/*    */       
/*    */       }
/* 61 */       else if (!dest.equals(coach.getName())) {
/* 62 */         ChatMessage message = new ChatMessage(dest, coach.getId(), commandLine);
/* 63 */         message.setPipeDestination(2);
/* 64 */         ChatManager.getInstance().pushMessage(message, dest);
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 70 */     UserPrivateContentMessage privateMessage = new UserPrivateContentMessage();
/* 71 */     privateMessage.setUserName(dest);
/* 72 */     privateMessage.setMessageContent(commandLine);
/* 73 */     DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)privateMessage);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 82 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\chat\console\command\PrivateContentCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */