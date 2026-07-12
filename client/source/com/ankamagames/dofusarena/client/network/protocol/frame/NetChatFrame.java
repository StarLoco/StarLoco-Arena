/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatManager;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.userGroup.User;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelContentMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelFlagsMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelJoinMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelMemberFlagsMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelMembersMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChatUserFlagsMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.FriendAddedMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.FriendListMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.FriendRemovedMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.IgnoreAddedMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.IgnoreListMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.IgnoreRemovedMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.NotificationFriendOfflineMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.NotificationFriendOnlineMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.NotificationIgnoreOfflineMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.NotificationIgnoreOnlineMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.PrivateContentMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.VicinityContentMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.ChannelNotFoundMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.UserNotFoundMessage;
/*     */ import com.ankamagames.dofusarena.client.chat.DofusArenaUser;
/*     */ import com.ankamagames.dofusarena.client.chat.DofusArenaUserGroupManager;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class NetChatFrame implements MessageFrame {
/*  33 */   protected static final Logger m_logger = Logger.getLogger(NetChatFrame.class);
/*     */   
/*  35 */   private static NetChatFrame m_instance = new NetChatFrame();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetChatFrame getInstance() {
/*  41 */     return m_instance; } public boolean onMessage(Message message) { ChannelContentMessage channelContentMessage; ChannelFlagsMessage channelFlagsMessage; ChannelJoinMessage channelJoinMessage; ChannelLeaveMessage channelLeaveMessage; ChannelMemberFlagsMessage channelMemberFlagsMessage; ChannelMemberKickMessage channelMemberKickMessage; ChannelMembersMessage channelMembersMessage; ChatUserFlagsMessage chatUserFlagsMessage; FriendAddedMessage friendAddedMessage; FriendRemovedMessage friendRemovedMessage; NotificationFriendOnlineMessage notificationFriendOnlineMessage; NotificationFriendOfflineMessage notificationFriendOfflineMessage; FriendListMessage friendListMessage; IgnoreAddedMessage ignoreAddedMessage; IgnoreRemovedMessage ignoreRemovedMessage; NotificationIgnoreOnlineMessage notificationIgnoreOnlineMessage; NotificationIgnoreOfflineMessage notificationIgnoreOfflineMessage; IgnoreListMessage ignoreListMessage; PrivateContentMessage privateContentMessage; VicinityContentMessage vicinityContentMessage; String str2; ChannelNotFoundMessage channelNotFoundMessage; String str1; UserNotFoundMessage msg; String errorMessage; ChatMessage chatMessage3; DofusArenaUser dofusArenaUser1; HashMap<String, DofusArenaUser> friendGroup; ArrayList<DofusArenaUser> friendList; DofusArenaUser user; HashMap<String, DofusArenaUser> ignoreGroup;
/*     */     ArrayList<DofusArenaUser> ignoreList;
/*     */     ChatMessage chatMessage2;
/*     */     String str4;
/*     */     ChatMessage chatMessage1;
/*     */     String str3;
/*     */     ChatMessage chatMessage;
/*     */     String notifyText;
/*     */     ChatMessage chatMessage4;
/*     */     ChatMessage notifyMessage;
/*  51 */     switch (message.getId()) {
/*     */       
/*     */       case 3140:
/*  54 */         channelContentMessage = (ChannelContentMessage)message;
/*     */         
/*  56 */         chatMessage3 = new ChatMessage(channelContentMessage.getMemberTalking(), channelContentMessage.getMessageContent());
/*  57 */         chatMessage3.setPipeDestination(3);
/*     */         
/*  59 */         ChatManager.getInstance().pushMessage(chatMessage3, channelContentMessage.getChannelName());
/*     */         
/*  61 */         return false;
/*     */ 
/*     */       
/*     */       case 3128:
/*  65 */         channelFlagsMessage = (ChannelFlagsMessage)message;
/*     */         
/*  67 */         return false;
/*     */ 
/*     */       
/*     */       case 3130:
/*  71 */         channelJoinMessage = (ChannelJoinMessage)message;
/*     */         
/*  73 */         return false;
/*     */ 
/*     */       
/*     */       case 3132:
/*  77 */         channelLeaveMessage = (ChannelLeaveMessage)message;
/*     */         
/*  79 */         return false;
/*     */ 
/*     */       
/*     */       case 3134:
/*  83 */         channelMemberFlagsMessage = (ChannelMemberFlagsMessage)message;
/*     */         
/*  85 */         return false;
/*     */ 
/*     */       
/*     */       case 3136:
/*  89 */         channelMemberKickMessage = (ChannelMemberKickMessage)message;
/*     */         
/*  91 */         return false;
/*     */ 
/*     */       
/*     */       case 3138:
/*  95 */         channelMembersMessage = (ChannelMembersMessage)message;
/*     */         
/*  97 */         return false;
/*     */ 
/*     */       
/*     */       case 3142:
/* 101 */         chatUserFlagsMessage = (ChatUserFlagsMessage)message;
/*     */         
/* 103 */         return false;
/*     */ 
/*     */       
/*     */       case 3156:
/* 107 */         friendAddedMessage = (FriendAddedMessage)message;
/*     */         
/* 109 */         dofusArenaUser1 = new DofusArenaUser(friendAddedMessage.getFriendName(), true, friendAddedMessage.getFriendId(), true);
/* 110 */         DofusArenaUserGroupManager.getInstance().addUser(DofusArenaUser.FRIEND, dofusArenaUser1);
/*     */         
/* 112 */         notifyText = DofusArenaTranslator.getInstance().getString("chat.notify.addFriend", new Object[] { friendAddedMessage.getFriendName() });
/* 113 */         notifyMessage = new ChatMessage(notifyText);
/* 114 */         notifyMessage.setPipeDestination(5);
/* 115 */         ChatManager.getInstance().pushMessage(notifyMessage);
/*     */         
/* 117 */         return false;
/*     */ 
/*     */       
/*     */       case 3160:
/* 121 */         friendRemovedMessage = (FriendRemovedMessage)message;
/*     */         
/* 123 */         if (!DofusArenaUserGroupManager.getInstance().removeUser(DofusArenaUser.FRIEND, friendRemovedMessage.getFriendName())) {
/* 124 */           String notifyError = DofusArenaTranslator.getInstance().getString("error.chat.userNotFound", new Object[] { friendRemovedMessage.getFriendName() });
/* 125 */           ChatMessage notifyErrorMessage = new ChatMessage(notifyError);
/* 126 */           notifyErrorMessage.setPipeDestination(4);
/* 127 */           ChatManager.getInstance().pushMessage(notifyErrorMessage);
/*     */         } else {
/* 129 */           String str = DofusArenaTranslator.getInstance().getString("chat.notify.removeFriend", new Object[] { friendRemovedMessage.getFriendName() });
/* 130 */           ChatMessage chatMessage5 = new ChatMessage(str);
/* 131 */           chatMessage5.setPipeDestination(5);
/* 132 */           ChatManager.getInstance().pushMessage(chatMessage5);
/*     */         } 
/*     */         
/* 135 */         return false;
/*     */ 
/*     */       
/*     */       case 3148:
/* 139 */         notificationFriendOnlineMessage = (NotificationFriendOnlineMessage)message;
/*     */         
/* 141 */         friendGroup = DofusArenaUserGroupManager.getInstance().getFriendGroup();
/* 142 */         if (friendGroup != null) {
/* 143 */           DofusArenaUser friend = friendGroup.get(notificationFriendOnlineMessage.getFriendName().toLowerCase());
/* 144 */           if (friend != null) {
/*     */             
/* 146 */             friend.setOnline(true);
/* 147 */             friend.setId(notificationFriendOnlineMessage.getUserId());
/*     */             
/* 149 */             if (friend.isNotify()) {
/* 150 */               String str = DofusArenaTranslator.getInstance().getString("chat.notify.friendOnline", new Object[] { notificationFriendOnlineMessage.getFriendName() });
/* 151 */               ChatMessage chatMessage5 = new ChatMessage(str);
/* 152 */               chatMessage5.setPipeDestination(5);
/* 153 */               ChatManager.getInstance().pushMessage(chatMessage5);
/*     */             } 
/*     */           } else {
/* 156 */             m_logger.error("Ami inconnu " + notificationFriendOnlineMessage.getFriendName());
/*     */           } 
/*     */         } 
/*     */         
/* 160 */         DofusArenaUserGroupManager.getInstance().updateProperty();
/*     */         
/* 162 */         return false;
/*     */ 
/*     */       
/*     */       case 3150:
/* 166 */         notificationFriendOfflineMessage = (NotificationFriendOfflineMessage)message;
/*     */         
/* 168 */         friendGroup = DofusArenaUserGroupManager.getInstance().getFriendGroup();
/* 169 */         if (friendGroup != null) {
/* 170 */           DofusArenaUser friend = friendGroup.get(notificationFriendOfflineMessage.getFriendName().toLowerCase());
/* 171 */           if (friend != null) {
/*     */             
/* 173 */             friend.setOnline(false);
/*     */             
/* 175 */             if (friend.isNotify()) {
/* 176 */               String str = DofusArenaTranslator.getInstance().getString("chat.notify.friendOffline", new Object[] { notificationFriendOfflineMessage.getFriendName() });
/* 177 */               ChatMessage chatMessage5 = new ChatMessage(str);
/* 178 */               chatMessage5.setPipeDestination(5);
/* 179 */               ChatManager.getInstance().pushMessage(chatMessage5);
/*     */             } 
/*     */           } else {
/* 182 */             m_logger.error("Ami inconnu " + notificationFriendOfflineMessage.getFriendName());
/*     */           } 
/*     */         } 
/*     */         
/* 186 */         DofusArenaUserGroupManager.getInstance().updateProperty();
/*     */         
/* 188 */         return false;
/*     */ 
/*     */       
/*     */       case 3144:
/* 192 */         friendListMessage = (FriendListMessage)message;
/*     */         
/* 194 */         friendList = new ArrayList<DofusArenaUser>();
/* 195 */         for (FriendListMessage.FriendInformation friendInformation : friendListMessage.getFriendInformationList()) {
/* 196 */           friendList.add(new DofusArenaUser(friendInformation.getName(), (friendInformation.getUserId() != -1L), friendInformation.getUserId(), friendInformation.getNotify()));
/*     */         }
/*     */ 
/*     */         
/* 200 */         DofusArenaUserGroupManager.getInstance().addUsers(DofusArenaUser.FRIEND, friendList);
/*     */         
/* 202 */         return false;
/*     */ 
/*     */       
/*     */       case 3158:
/* 206 */         ignoreAddedMessage = (IgnoreAddedMessage)message;
/*     */         
/* 208 */         user = new DofusArenaUser(ignoreAddedMessage.getIgnoreName());
/* 209 */         DofusArenaUserGroupManager.getInstance().addUser(DofusArenaUser.IGNORE, user);
/*     */         
/* 211 */         notifyText = DofusArenaTranslator.getInstance().getString("chat.notify.addIgnore", new Object[] { ignoreAddedMessage.getIgnoreName() });
/* 212 */         notifyMessage = new ChatMessage(notifyText);
/* 213 */         notifyMessage.setPipeDestination(5);
/* 214 */         ChatManager.getInstance().pushMessage(notifyMessage);
/*     */         
/* 216 */         return false;
/*     */ 
/*     */       
/*     */       case 3162:
/* 220 */         ignoreRemovedMessage = (IgnoreRemovedMessage)message;
/*     */         
/* 222 */         if (!DofusArenaUserGroupManager.getInstance().removeUser(DofusArenaUser.IGNORE, ignoreRemovedMessage.getIgnoreName())) {
/* 223 */           String notifyError = DofusArenaTranslator.getInstance().getString("error.chat.userNotFound", new Object[] { ignoreRemovedMessage.getIgnoreName() });
/* 224 */           ChatMessage notifyErrorMessage = new ChatMessage(notifyError);
/* 225 */           notifyErrorMessage.setPipeDestination(4);
/* 226 */           ChatManager.getInstance().pushMessage(notifyErrorMessage);
/*     */         } else {
/* 228 */           String str = DofusArenaTranslator.getInstance().getString("chat.notify.removeIgnore", new Object[] { ignoreRemovedMessage.getIgnoreName() });
/* 229 */           ChatMessage chatMessage5 = new ChatMessage(str);
/* 230 */           chatMessage5.setPipeDestination(5);
/* 231 */           ChatManager.getInstance().pushMessage(chatMessage5);
/*     */         } 
/*     */         
/* 234 */         return false;
/*     */ 
/*     */       
/*     */       case 3164:
/* 238 */         notificationIgnoreOnlineMessage = (NotificationIgnoreOnlineMessage)message;
/*     */         
/* 240 */         ignoreGroup = DofusArenaUserGroupManager.getInstance().getFriendGroup();
/* 241 */         if (ignoreGroup != null) {
/* 242 */           DofusArenaUser ignore = ignoreGroup.get(notificationIgnoreOnlineMessage.getIgnoreName().toLowerCase());
/* 243 */           if (ignore != null) {
/*     */             
/* 245 */             ignore.setOnline(true);
/* 246 */             ignore.setId(notificationIgnoreOnlineMessage.getUserId());
/*     */             
/* 248 */             String str = DofusArenaTranslator.getInstance().getString("chat.notify.ignoreOnline", new Object[] { notificationIgnoreOnlineMessage.getIgnoreName() });
/* 249 */             ChatMessage chatMessage5 = new ChatMessage(str);
/* 250 */             chatMessage5.setPipeDestination(5);
/* 251 */             ChatManager.getInstance().pushMessage(chatMessage5);
/*     */           } else {
/* 253 */             m_logger.error("Ignoré inconnu " + notificationIgnoreOnlineMessage.getIgnoreName());
/*     */           } 
/*     */         } 
/*     */         
/* 257 */         return false;
/*     */ 
/*     */       
/*     */       case 3166:
/* 261 */         notificationIgnoreOfflineMessage = (NotificationIgnoreOfflineMessage)message;
/*     */         
/* 263 */         ignoreGroup = DofusArenaUserGroupManager.getInstance().getFriendGroup();
/* 264 */         if (ignoreGroup != null) {
/* 265 */           User ignore = (User)ignoreGroup.get(notificationIgnoreOfflineMessage.getIgnoreName().toLowerCase());
/* 266 */           if (ignore != null) {
/*     */             
/* 268 */             ignore.setOnline(false);
/*     */             
/* 270 */             String str = DofusArenaTranslator.getInstance().getString("chat.notify.ignoreOffline", new Object[] { notificationIgnoreOfflineMessage.getIgnoreName() });
/* 271 */             ChatMessage chatMessage5 = new ChatMessage(str);
/* 272 */             chatMessage5.setPipeDestination(5);
/* 273 */             ChatManager.getInstance().pushMessage(chatMessage5);
/*     */           } else {
/* 275 */             m_logger.error("Ignoré inconnu " + notificationIgnoreOfflineMessage.getIgnoreName());
/*     */           } 
/*     */         } 
/*     */         
/* 279 */         return false;
/*     */ 
/*     */       
/*     */       case 3146:
/* 283 */         ignoreListMessage = (IgnoreListMessage)message;
/*     */         
/* 285 */         ignoreList = new ArrayList<DofusArenaUser>();
/* 286 */         for (String ignore : ignoreListMessage.getIgnoreList()) {
/* 287 */           ignoreList.add(new DofusArenaUser(ignore));
/*     */         }
/*     */ 
/*     */         
/* 291 */         DofusArenaUserGroupManager.getInstance().addUsers(DofusArenaUser.IGNORE, ignoreList);
/*     */         
/* 293 */         return false;
/*     */ 
/*     */       
/*     */       case 3154:
/* 297 */         privateContentMessage = (PrivateContentMessage)message;
/*     */         
/* 299 */         chatMessage2 = new ChatMessage(privateContentMessage.getMemberTalking(), privateContentMessage.getMemberIDTalking(), privateContentMessage.getMessageContent());
/* 300 */         chatMessage2.setPipeDestination(2);
/*     */         
/* 302 */         ChatManager.getInstance().pushMessage(chatMessage2, privateContentMessage.getMemberTalking());
/*     */         
/* 304 */         return false;
/*     */ 
/*     */       
/*     */       case 3152:
/* 308 */         vicinityContentMessage = (VicinityContentMessage)message;
/*     */         
/* 310 */         chatMessage2 = new ChatMessage(vicinityContentMessage.getMemberTalking(), vicinityContentMessage.getMemberIDTalking(), vicinityContentMessage.getMessageContent());
/* 311 */         chatMessage2.setPipeDestination(1);
/*     */         
/* 313 */         ChatManager.getInstance().pushMessage(chatMessage2);
/*     */         
/* 315 */         return false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3206:
/* 321 */         str2 = DofusArenaTranslator.getInstance().getString("error.chat.malformedCommand", new Object[0]);
/*     */         
/* 323 */         chatMessage2 = new ChatMessage(str2);
/* 324 */         chatMessage2.setPipeDestination(4);
/*     */         
/* 326 */         ChatManager.getInstance().pushMessage(chatMessage2);
/*     */         
/* 328 */         return false;
/*     */ 
/*     */       
/*     */       case 3202:
/* 332 */         channelNotFoundMessage = (ChannelNotFoundMessage)message;
/*     */         
/* 334 */         str4 = DofusArenaTranslator.getInstance().getString("error.chat.channelNotFound", new Object[] { channelNotFoundMessage.getChannelName() });
/*     */         
/* 336 */         chatMessage4 = new ChatMessage(str4);
/* 337 */         chatMessage4.setPipeDestination(4);
/*     */         
/* 339 */         ChatManager.getInstance().pushMessage(chatMessage4);
/*     */         
/* 341 */         return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 3214:
/* 346 */         str1 = DofusArenaTranslator.getInstance().getString("error.chat.targetIsYourself", new Object[0]);
/*     */         
/* 348 */         chatMessage1 = new ChatMessage(str1);
/* 349 */         chatMessage1.setPipeDestination(4);
/*     */         
/* 351 */         ChatManager.getInstance().pushMessage(chatMessage1);
/*     */         
/* 353 */         return false;
/*     */ 
/*     */       
/*     */       case 3204:
/* 357 */         msg = (UserNotFoundMessage)message;
/*     */         
/* 359 */         str3 = DofusArenaTranslator.getInstance().getString("error.chat.userNotFound", new Object[] { msg.getUserName() });
/*     */         
/* 361 */         chatMessage4 = new ChatMessage(str3);
/* 362 */         chatMessage4.setPipeDestination(4);
/*     */         
/* 364 */         ChatManager.getInstance().pushMessage(chatMessage4);
/*     */         
/* 366 */         return false;
/*     */ 
/*     */       
/*     */       case 3212:
/* 370 */         errorMessage = DofusArenaTranslator.getInstance().getString("error.chat.notYetImplemented", new Object[0]);
/*     */         
/* 372 */         chatMessage = new ChatMessage(errorMessage);
/* 373 */         chatMessage.setPipeDestination(4);
/*     */         
/* 375 */         ChatManager.getInstance().pushMessage(chatMessage);
/* 376 */         return false;
/*     */ 
/*     */       
/*     */       case 3210:
/* 380 */         errorMessage = DofusArenaTranslator.getInstance().getString("error.chat.notEnoughPrivileges", new Object[0]);
/*     */         
/* 382 */         chatMessage = new ChatMessage(errorMessage);
/* 383 */         chatMessage.setPipeDestination(4);
/*     */         
/* 385 */         ChatManager.getInstance().pushMessage(chatMessage);
/*     */         
/* 387 */         return false;
/*     */ 
/*     */       
/*     */       case 3216:
/* 391 */         errorMessage = DofusArenaTranslator.getInstance().getString("error.chat.operationNotPermited", new Object[0]);
/*     */         
/* 393 */         chatMessage = new ChatMessage(errorMessage);
/* 394 */         chatMessage.setPipeDestination(4);
/*     */         
/* 396 */         ChatManager.getInstance().pushMessage(chatMessage);
/*     */         
/* 398 */         return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 403 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 412 */     return 0L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetChatFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */