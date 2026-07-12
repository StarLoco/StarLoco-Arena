/*    */ package com.ankamagames.dofusarena.client.chat.console.command.ListFriendsCommand;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatMessage;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.dofusarena.client.chat.DofusArenaUser;
/*    */ import com.ankamagames.dofusarena.client.chat.DofusArenaUserGroupManager;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListFriendsCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/* 26 */     HashMap<String, DofusArenaUser> friendGroup = DofusArenaUserGroupManager.getInstance().getFriendGroup();
/* 27 */     if (friendGroup != null) {
/* 28 */       String messageText = DofusArenaTranslator.getInstance().getString("chat.friendList", new Object[0]);
/* 29 */       messageText = String.valueOf(messageText) + " :\n";
/* 30 */       StringBuilder s = new StringBuilder("");
/* 31 */       for (DofusArenaUser u : friendGroup.values()) {
/* 32 */         s.append(" +").append(u.getName()).append(" (");
/* 33 */         if (u.isOnline()) { s.append("onLine"); }
/* 34 */         else { s.append("offLine"); }
/* 35 */          s.append(")\n");
/*    */       } 
/* 37 */       messageText = String.valueOf(messageText) + s.toString();
/* 38 */       ChatMessage message = new ChatMessage(messageText);
/* 39 */       message.setPipeDestination(5);
/* 40 */       ChatManager.getInstance().pushMessage(message);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 51 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\chat\console\command\ListFriendsCommand\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */