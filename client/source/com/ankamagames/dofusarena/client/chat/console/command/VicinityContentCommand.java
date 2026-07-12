/*    */ package com.ankamagames.dofusarena.client.chat.console.command;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatMessage;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.clientToServer.UserVicinityContentMessage;
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
/*    */ public class VicinityContentCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/* 36 */     String commandLine = args.get(0);
/*    */     
/* 38 */     LocalCoach coach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*    */     
/* 40 */     if (coach != null) {
/*    */       
/* 42 */       commandLine = WordsModerator.makeValidSentence(commandLine);
/*    */ 
/*    */       
/* 45 */       if (commandLine.equals("")) {
/*    */         
/* 47 */         String errorMessage = DofusArenaTranslator.getInstance().getString("error.chat.operationNotPermited", new Object[0]);
/* 48 */         ChatMessage message = new ChatMessage(errorMessage);
/* 49 */         message.setPipeDestination(4);
/* 50 */         ChatManager.getInstance().pushMessage(message);
/*    */       
/*    */       }
/*    */       else {
/*    */         
/* 55 */         ChatMessage message = new ChatMessage(coach.getName(), coach.getId(), commandLine);
/* 56 */         message.setPipeDestination(1);
/* 57 */         ChatManager.getInstance().pushMessage(message);
/*    */       } 
/*    */     } 
/* 60 */     UserVicinityContentMessage vicinityMessage = new UserVicinityContentMessage();
/* 61 */     vicinityMessage.setMessageContent(commandLine);
/* 62 */     DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)vicinityMessage);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\chat\console\command\VicinityContentCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */