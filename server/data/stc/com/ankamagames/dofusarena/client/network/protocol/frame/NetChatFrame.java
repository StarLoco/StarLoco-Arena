/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatManager;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.userGroup.User;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelContentMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.FriendAddedMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.FriendListMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.FriendListMessage.FriendInformation;
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
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class NetChatFrame implements com.ankamagames.framework.kernel.events.MessageFrame
/*     */ {
/*  33 */   protected static final Logger m_logger = Logger.getLogger(NetChatFrame.class);
/*     */   
/*  35 */   private static NetChatFrame m_instance = new NetChatFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static NetChatFrame getInstance()
/*     */   {
/*  41 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*     */     String notifyText;
/*     */     
/*     */     String notifyText;
/*     */     
/*  51 */     switch (message.getId())
/*     */     {
/*     */     case 3140: 
/*  54 */       ChannelContentMessage msg = (ChannelContentMessage)message;
/*     */       
/*  56 */       ChatMessage chatMessage = new ChatMessage(msg.getMemberTalking(), msg.getMessageContent());
/*  57 */       chatMessage.setPipeDestination(3);
/*     */       
/*  59 */       ChatManager.getInstance().pushMessage(chatMessage, msg.getChannelName());
/*     */       
/*  61 */       return false;
/*     */     
/*     */ 
/*     */     case 3128: 
/*  65 */       com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelFlagsMessage msg = (com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelFlagsMessage)message;
/*     */       
/*  67 */       return false;
/*     */     
/*     */ 
/*     */     case 3130: 
/*  71 */       com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelJoinMessage msg = (com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelJoinMessage)message;
/*     */       
/*  73 */       return false;
/*     */     
/*     */ 
/*     */     case 3132: 
/*  77 */       com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelLeaveMessage msg = (com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelLeaveMessage)message;
/*     */       
/*  79 */       return false;
/*     */     
/*     */ 
/*     */     case 3134: 
/*  83 */       com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelMemberFlagsMessage msg = (com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelMemberFlagsMessage)message;
/*     */       
/*  85 */       return false;
/*     */     
/*     */ 
/*     */     case 3136: 
/*  89 */       com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelMemberKickMessage msg = (com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelMemberKickMessage)message;
/*     */       
/*  91 */       return false;
/*     */     
/*     */ 
/*     */     case 3138: 
/*  95 */       com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelMembersMessage msg = (com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelMembersMessage)message;
/*     */       
/*  97 */       return false;
/*     */     
/*     */ 
/*     */     case 3142: 
/* 101 */       com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChatUserFlagsMessage msg = (com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChatUserFlagsMessage)message;
/*     */       
/* 103 */       return false;
/*     */     
/*     */ 
/*     */     case 3156: 
/* 107 */       FriendAddedMessage msg = (FriendAddedMessage)message;
/*     */       
/* 109 */       DofusArenaUser user = new DofusArenaUser(msg.getFriendName(), true, msg.getFriendId(), true);
/* 110 */       DofusArenaUserGroupManager.getInstance().addUser(DofusArenaUser.FRIEND, user);
/*     */       
/* 112 */       String notifyText = DofusArenaTranslator.getInstance().getString("chat.notify.addFriend", new Object[] { msg.getFriendName() });
/* 113 */       ChatMessage notifyMessage = new ChatMessage(notifyText);
/* 114 */       notifyMessage.setPipeDestination(5);
/* 115 */       ChatManager.getInstance().pushMessage(notifyMessage);
/*     */       
/* 117 */       return false;
/*     */     
/*     */ 
/*     */     case 3160: 
/* 121 */       FriendRemovedMessage msg = (FriendRemovedMessage)message;
/*     */       
/* 123 */       if (!DofusArenaUserGroupManager.getInstance().removeUser(DofusArenaUser.FRIEND, msg.getFriendName())) {
/* 124 */         String notifyError = DofusArenaTranslator.getInstance().getString("error.chat.userNotFound", new Object[] { msg.getFriendName() });
/* 125 */         ChatMessage notifyErrorMessage = new ChatMessage(notifyError);
/* 126 */         notifyErrorMessage.setPipeDestination(4);
/* 127 */         ChatManager.getInstance().pushMessage(notifyErrorMessage);
/*     */       } else {
/* 129 */         String notifyText = DofusArenaTranslator.getInstance().getString("chat.notify.removeFriend", new Object[] { msg.getFriendName() });
/* 130 */         ChatMessage notifyMessage = new ChatMessage(notifyText);
/* 131 */         notifyMessage.setPipeDestination(5);
/* 132 */         ChatManager.getInstance().pushMessage(notifyMessage);
/*     */       }
/*     */       
/* 135 */       return false;
/*     */     
/*     */ 
/*     */     case 3148: 
/* 139 */       NotificationFriendOnlineMessage msg = (NotificationFriendOnlineMessage)message;
/*     */       
/* 141 */       HashMap<String, DofusArenaUser> friendGroup = DofusArenaUserGroupManager.getInstance().getFriendGroup();
/* 142 */       if (friendGroup != null) {
/* 143 */         DofusArenaUser friend = (DofusArenaUser)friendGroup.get(msg.getFriendName().toLowerCase());
/* 144 */         if (friend != null)
/*     */         {
/* 146 */           friend.setOnline(true);
/* 147 */           friend.setId(msg.getUserId());
/*     */           
/* 149 */           if (friend.isNotify()) {
/* 150 */             String notifyText = DofusArenaTranslator.getInstance().getString("chat.notify.friendOnline", new Object[] { msg.getFriendName() });
/* 151 */             ChatMessage notifyMessage = new ChatMessage(notifyText);
/* 152 */             notifyMessage.setPipeDestination(5);
/* 153 */             ChatManager.getInstance().pushMessage(notifyMessage);
/*     */           }
/*     */         } else {
/* 156 */           m_logger.error("Ami inconnu " + msg.getFriendName());
/*     */         }
/*     */       }
/*     */       
/* 160 */       DofusArenaUserGroupManager.getInstance().updateProperty();
/*     */       
/* 162 */       return false;
/*     */     
/*     */ 
/*     */     case 3150: 
/* 166 */       NotificationFriendOfflineMessage msg = (NotificationFriendOfflineMessage)message;
/*     */       
/* 168 */       HashMap<String, DofusArenaUser> friendGroup = DofusArenaUserGroupManager.getInstance().getFriendGroup();
/* 169 */       if (friendGroup != null) {
/* 170 */         DofusArenaUser friend = (DofusArenaUser)friendGroup.get(msg.getFriendName().toLowerCase());
/* 171 */         if (friend != null)
/*     */         {
/* 173 */           friend.setOnline(false);
/*     */           
/* 175 */           if (friend.isNotify()) {
/* 176 */             notifyText = DofusArenaTranslator.getInstance().getString("chat.notify.friendOffline", new Object[] { msg.getFriendName() });
/* 177 */             ChatMessage notifyMessage = new ChatMessage(notifyText);
/* 178 */             notifyMessage.setPipeDestination(5);
/* 179 */             ChatManager.getInstance().pushMessage(notifyMessage);
/*     */           }
/*     */         } else {
/* 182 */           m_logger.error("Ami inconnu " + msg.getFriendName());
/*     */         }
/*     */       }
/*     */       
/* 186 */       DofusArenaUserGroupManager.getInstance().updateProperty();
/*     */       
/* 188 */       return false;
/*     */     
/*     */ 
/*     */     case 3144: 
/* 192 */       FriendListMessage msg = (FriendListMessage)message;
/*     */       
/* 194 */       ArrayList<DofusArenaUser> friendList = new ArrayList();
/* 195 */       for (FriendListMessage.FriendInformation friendInformation : msg.getFriendInformationList()) {
/* 196 */         friendList.add(new DofusArenaUser(friendInformation.getName(), friendInformation.getUserId() != -1L, friendInformation.getUserId(), friendInformation.getNotify()));
/*     */       }
/*     */       
/*     */ 
/* 200 */       DofusArenaUserGroupManager.getInstance().addUsers(DofusArenaUser.FRIEND, friendList);
/*     */       
/* 202 */       return false;
/*     */     
/*     */ 
/*     */     case 3158: 
/* 206 */       IgnoreAddedMessage msg = (IgnoreAddedMessage)message;
/*     */       
/* 208 */       DofusArenaUser user = new DofusArenaUser(msg.getIgnoreName());
/* 209 */       DofusArenaUserGroupManager.getInstance().addUser(DofusArenaUser.IGNORE, user);
/*     */       
/* 211 */       String notifyText = DofusArenaTranslator.getInstance().getString("chat.notify.addIgnore", new Object[] { msg.getIgnoreName() });
/* 212 */       ChatMessage notifyMessage = new ChatMessage(notifyText);
/* 213 */       notifyMessage.setPipeDestination(5);
/* 214 */       ChatManager.getInstance().pushMessage(notifyMessage);
/*     */       
/* 216 */       return false;
/*     */     
/*     */ 
/*     */     case 3162: 
/* 220 */       IgnoreRemovedMessage msg = (IgnoreRemovedMessage)message;
/*     */       
/* 222 */       if (!DofusArenaUserGroupManager.getInstance().removeUser(DofusArenaUser.IGNORE, msg.getIgnoreName())) {
/* 223 */         String notifyError = DofusArenaTranslator.getInstance().getString("error.chat.userNotFound", new Object[] { msg.getIgnoreName() });
/* 224 */         ChatMessage notifyErrorMessage = new ChatMessage(notifyError);
/* 225 */         notifyErrorMessage.setPipeDestination(4);
/* 226 */         ChatManager.getInstance().pushMessage(notifyErrorMessage);
/*     */       } else {
/* 228 */         String notifyText = DofusArenaTranslator.getInstance().getString("chat.notify.removeIgnore", new Object[] { msg.getIgnoreName() });
/* 229 */         ChatMessage notifyMessage = new ChatMessage(notifyText);
/* 230 */         notifyMessage.setPipeDestination(5);
/* 231 */         ChatManager.getInstance().pushMessage(notifyMessage);
/*     */       }
/*     */       
/* 234 */       return false;
/*     */     
/*     */ 
/*     */     case 3164: 
/* 238 */       NotificationIgnoreOnlineMessage msg = (NotificationIgnoreOnlineMessage)message;
/*     */       
/* 240 */       HashMap<String, DofusArenaUser> ignoreGroup = DofusArenaUserGroupManager.getInstance().getFriendGroup();
/* 241 */       if (ignoreGroup != null) {
/* 242 */         DofusArenaUser ignore = (DofusArenaUser)ignoreGroup.get(msg.getIgnoreName().toLowerCase());
/* 243 */         if (ignore != null)
/*     */         {
/* 245 */           ignore.setOnline(true);
/* 246 */           ignore.setId(msg.getUserId());
/*     */           
/* 248 */           String notifyText = DofusArenaTranslator.getInstance().getString("chat.notify.ignoreOnline", new Object[] { msg.getIgnoreName() });
/* 249 */           ChatMessage notifyMessage = new ChatMessage(notifyText);
/* 250 */           notifyMessage.setPipeDestination(5);
/* 251 */           ChatManager.getInstance().pushMessage(notifyMessage);
/*     */         } else {
/* 253 */           m_logger.error("Ignoré inconnu " + msg.getIgnoreName());
/*     */         }
/*     */       }
/*     */       
/* 257 */       return false;
/*     */     
/*     */ 
/*     */     case 3166: 
/* 261 */       NotificationIgnoreOfflineMessage msg = (NotificationIgnoreOfflineMessage)message;
/*     */       
/* 263 */       HashMap<String, DofusArenaUser> ignoreGroup = DofusArenaUserGroupManager.getInstance().getFriendGroup();
/* 264 */       if (ignoreGroup != null) {
/* 265 */         User ignore = (User)ignoreGroup.get(msg.getIgnoreName().toLowerCase());
/* 266 */         if (ignore != null)
/*     */         {
/* 268 */           ignore.setOnline(false);
/*     */           
/* 270 */           notifyText = DofusArenaTranslator.getInstance().getString("chat.notify.ignoreOffline", new Object[] { msg.getIgnoreName() });
/* 271 */           ChatMessage notifyMessage = new ChatMessage(notifyText);
/* 272 */           notifyMessage.setPipeDestination(5);
/* 273 */           ChatManager.getInstance().pushMessage(notifyMessage);
/*     */         } else {
/* 275 */           m_logger.error("Ignoré inconnu " + msg.getIgnoreName());
/*     */         }
/*     */       }
/*     */       
/* 279 */       return false;
/*     */     
/*     */ 
/*     */     case 3146: 
/* 283 */       IgnoreListMessage msg = (IgnoreListMessage)message;
/*     */       
/* 285 */       ArrayList<DofusArenaUser> ignoreList = new ArrayList();
/* 286 */       for (String ignore : msg.getIgnoreList()) {
/* 287 */         ignoreList.add(new DofusArenaUser(ignore));
/*     */       }
/*     */       
/*     */ 
/* 291 */       DofusArenaUserGroupManager.getInstance().addUsers(DofusArenaUser.IGNORE, ignoreList);
/*     */       
/* 293 */       return false;
/*     */     
/*     */ 
/*     */     case 3154: 
/* 297 */       PrivateContentMessage msg = (PrivateContentMessage)message;
/*     */       
/* 299 */       ChatMessage chatMessage = new ChatMessage(msg.getMemberTalking(), msg.getMemberIDTalking(), msg.getMessageContent());
/* 300 */       chatMessage.setPipeDestination(2);
/*     */       
/* 302 */       ChatManager.getInstance().pushMessage(chatMessage, msg.getMemberTalking());
/*     */       
/* 304 */       return false;
/*     */     
/*     */ 
/*     */     case 3152: 
/* 308 */       VicinityContentMessage msg = (VicinityContentMessage)message;
/*     */       
/* 310 */       ChatMessage chatMessage = new ChatMessage(msg.getMemberTalking(), msg.getMemberIDTalking(), msg.getMessageContent());
/* 311 */       chatMessage.setPipeDestination(1);
/*     */       
/* 313 */       ChatManager.getInstance().pushMessage(chatMessage);
/*     */       
/* 315 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 3206: 
/* 321 */       String errorMessage = DofusArenaTranslator.getInstance().getString("error.chat.malformedCommand", new Object[0]);
/*     */       
/* 323 */       ChatMessage chatMessage = new ChatMessage(errorMessage);
/* 324 */       chatMessage.setPipeDestination(4);
/*     */       
/* 326 */       ChatManager.getInstance().pushMessage(chatMessage);
/*     */       
/* 328 */       return false;
/*     */     
/*     */ 
/*     */     case 3202: 
/* 332 */       ChannelNotFoundMessage msg = (ChannelNotFoundMessage)message;
/*     */       
/* 334 */       String errorMessage = DofusArenaTranslator.getInstance().getString("error.chat.channelNotFound", new Object[] { msg.getChannelName() });
/*     */       
/* 336 */       ChatMessage chatMessage = new ChatMessage(errorMessage);
/* 337 */       chatMessage.setPipeDestination(4);
/*     */       
/* 339 */       ChatManager.getInstance().pushMessage(chatMessage);
/*     */       
/* 341 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 3214: 
/* 346 */       String errorMessage = DofusArenaTranslator.getInstance().getString("error.chat.targetIsYourself", new Object[0]);
/*     */       
/* 348 */       ChatMessage chatMessage = new ChatMessage(errorMessage);
/* 349 */       chatMessage.setPipeDestination(4);
/*     */       
/* 351 */       ChatManager.getInstance().pushMessage(chatMessage);
/*     */       
/* 353 */       return false;
/*     */     
/*     */ 
/*     */     case 3204: 
/* 357 */       UserNotFoundMessage msg = (UserNotFoundMessage)message;
/*     */       
/* 359 */       String errorMessage = DofusArenaTranslator.getInstance().getString("error.chat.userNotFound", new Object[] { msg.getUserName() });
/*     */       
/* 361 */       ChatMessage chatMessage = new ChatMessage(errorMessage);
/* 362 */       chatMessage.setPipeDestination(4);
/*     */       
/* 364 */       ChatManager.getInstance().pushMessage(chatMessage);
/*     */       
/* 366 */       return false;
/*     */     
/*     */ 
/*     */     case 3212: 
/* 370 */       String errorMessage = DofusArenaTranslator.getInstance().getString("error.chat.notYetImplemented", new Object[0]);
/*     */       
/* 372 */       ChatMessage chatMessage = new ChatMessage(errorMessage);
/* 373 */       chatMessage.setPipeDestination(4);
/*     */       
/* 375 */       ChatManager.getInstance().pushMessage(chatMessage);
/* 376 */       return false;
/*     */     
/*     */ 
/*     */     case 3210: 
/* 380 */       String errorMessage = DofusArenaTranslator.getInstance().getString("error.chat.notEnoughPrivileges", new Object[0]);
/*     */       
/* 382 */       ChatMessage chatMessage = new ChatMessage(errorMessage);
/* 383 */       chatMessage.setPipeDestination(4);
/*     */       
/* 385 */       ChatManager.getInstance().pushMessage(chatMessage);
/*     */       
/* 387 */       return false;
/*     */     
/*     */ 
/*     */     case 3216: 
/* 391 */       String errorMessage = DofusArenaTranslator.getInstance().getString("error.chat.operationNotPermited", new Object[0]);
/*     */       
/* 393 */       ChatMessage chatMessage = new ChatMessage(errorMessage);
/* 394 */       chatMessage.setPipeDestination(4);
/*     */       
/* 396 */       ChatManager.getInstance().pushMessage(chatMessage);
/*     */       
/* 398 */       return false;
/*     */     }
/*     */     
/*     */     
/*     */ 
/* 403 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 412 */     return 0L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetChatFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */