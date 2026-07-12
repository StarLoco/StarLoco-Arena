/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatPipe;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.userGroup.User;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.clientToServer.RemoveFriendMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.clientToServer.RemoveIgnoreMessage;
/*     */ import com.ankamagames.baseImpl.graphicalClient.chat.ChatPipeWrapper;
/*     */ import com.ankamagames.baseImpl.graphicalClient.chat.FieldedUser;
/*     */ import com.ankamagames.dofusarena.client.chat.ChatView;
/*     */ import com.ankamagames.dofusarena.client.chat.ChatViewManager;
/*     */ import com.ankamagames.dofusarena.client.chat.DofusArenaUser;
/*     */ import com.ankamagames.dofusarena.client.chat.DofusArenaUserGroupManager;
/*     */ import com.ankamagames.dofusarena.client.chat.UserFilter;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.FightInvitationRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.chat.UIChatContentMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.chat.UIChatPipeSelectionMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.chat.UIFriendUserMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.chat.UIUserMessage;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.event.MouseClickEvent;
/*     */ import com.ankamagames.xulor.event.listener.MouseClickListener;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
/*     */ import com.ankamagames.xulor.property.Property;
/*     */ import com.ankamagames.xulor.template.IPopupMenu;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class UIChatFrame implements MessageFrame
/*     */ {
/*  38 */   private static UIChatFrame m_instance = new UIChatFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static UIChatFrame getInstance()
/*     */   {
/*  44 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  53 */     switch (message.getId())
/*     */     {
/*     */     case 19000: 
/*  56 */       UIChatContentMessage msg = (UIChatContentMessage)message;
/*     */       
/*     */ 
/*     */ 
/*  60 */       ChatView view = ChatViewManager.getInstance().getView();
/*     */       
/*  62 */       if (view != null) { view.getConsole().parseInput(msg.getMessage());
/*     */       }
/*  64 */       return false;
/*     */     
/*     */ 
/*     */     case 19001: 
/*  68 */       UIChatPipeSelectionMessage msg = (UIChatPipeSelectionMessage)message;
/*     */       
/*  70 */       ChatView view = ChatViewManager.getInstance().getView(msg.getViewIndex());
/*     */       
/*  72 */       msg.getPipeWrapper().setOpen(msg.isListenPipe());
/*     */       
/*  74 */       if (msg.isListenPipe()) msg.getPipeWrapper().getChatPipe().addListener(view); else {
/*  75 */         msg.getPipeWrapper().getChatPipe().removeListener(view);
/*     */       }
/*  77 */       view.updateDisplayHistory();
/*     */       
/*  79 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 19002: 
/*  84 */       if (Xulor.getInstance().isLoaded("contactListDialog"))
/*     */       {
/*  86 */         Xulor.getInstance().unload("contactListDialog");
/*     */       } else {
/*  88 */         Xulor.getInstance().load("contactListDialog", Dialogs.getDialogPath("contactListDialog"), 256L, (short)19000);
/*     */       }
/*     */       
/*  91 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 19008: 
/*  96 */       if (Xulor.getInstance().isLoaded("emotesBarDialog"))
/*     */       {
/*  98 */         Xulor.getInstance().unload("emotesBarDialog");
/*     */       } else {
/* 100 */         Xulor.getInstance().load("emotesBarDialog", Dialogs.getDialogPath("emotesBarDialog"), 256L, (short)12000);
/*     */       }
/*     */       
/* 103 */       return false;
/*     */     
/*     */ 
/*     */     case 19007: 
/* 107 */       boolean state = Xulor.getInstance().getEnvironment().getPropertiesProvider().getBooleanProperty("chat.isMaximize");
/* 108 */       if ((state) && (Xulor.getInstance().isLoaded("contactListDialog"))) {
/* 109 */         Xulor.getInstance().unload("contactListDialog");
/*     */       }
/* 111 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("chat.isMaximize", Boolean.valueOf(!state));
/* 112 */       return false;
/*     */     
/*     */ 
/*     */     case 19009: 
/* 116 */       UserFilter filter = DofusArenaUserGroupManager.getInstance().getUserFilter();
/* 117 */       if (filter.isEnabled("friend")) {
/* 118 */         filter.setDisabled("friend");
/*     */       } else {
/* 120 */         filter.setEnabled("friend");
/*     */       }
/* 122 */       DofusArenaUserGroupManager.getInstance().updateProperty();
/* 123 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("contact.list.filter", filter);
/* 124 */       return false;
/*     */     
/*     */ 
/*     */     case 19010: 
/* 128 */       UserFilter filter = DofusArenaUserGroupManager.getInstance().getUserFilter();
/* 129 */       if (filter.isEnabled("ignore")) {
/* 130 */         filter.setDisabled("ignore");
/*     */       } else {
/* 132 */         filter.setEnabled("ignore");
/*     */       }
/* 134 */       DofusArenaUserGroupManager.getInstance().updateProperty();
/* 135 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("contact.list.filter", filter);
/* 136 */       return false;
/*     */     
/*     */ 
/*     */     case 19003: 
/* 140 */       final UIFriendUserMessage msg = (UIFriendUserMessage)message;
/*     */       
/* 142 */       final DofusArenaUser user = msg.getUser();
/*     */       
/* 144 */       IPopupMenu popupMenu = Xulor.getInstance().popupMenu();
/* 145 */       popupMenu.addLabel(user.getName(), null);
/*     */       
/* 147 */       if (DofusArenaUserGroupManager.getInstance().getIgnoreGroup().containsKey(user.getName().toLowerCase()))
/*     */       {
/* 149 */         popupMenu.addButton(DofusArenaTranslator.getInstance().getString("chat.removeFromIgnoreList", new Object[0]), null, new MouseClickListener() {
/*     */           public void run(MouseClickEvent event) {
/* 151 */             RemoveIgnoreMessage netMessage = new RemoveIgnoreMessage();
/* 152 */             netMessage.setIgnoreName(user.getName());
/* 153 */             DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */           }
/* 155 */         }, true);
/*     */       }
/*     */       
/*     */ 
/* 159 */       if (DofusArenaUserGroupManager.getInstance().getFriendGroup().containsKey(user.getName().toLowerCase()))
/*     */       {
/* 161 */         popupMenu.addButton(DofusArenaTranslator.getInstance().getString("chat.removeFromFriendList", new Object[0]), null, new MouseClickListener() {
/*     */           public void run(MouseClickEvent event) {
/* 163 */             RemoveFriendMessage netMessage = new RemoveFriendMessage();
/* 164 */             netMessage.setFriendName(user.getName());
/* 165 */             DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */           }
/* 167 */         }, true);
/*     */         
/*     */ 
/* 170 */         popupMenu.addButton(DofusArenaTranslator.getInstance().getString("defy", new Object[0]), null, new MouseClickListener() {
/*     */           public void run(MouseClickEvent event) {
/* 172 */             long userId = msg.getUser().getId();
/*     */             
/* 174 */             FightInvitationRequestMessage netMessage = new FightInvitationRequestMessage();
/* 175 */             netMessage.setTargetCoachId(userId);
/* 176 */             netMessage.setFightTypeId((byte)1);
/* 177 */             DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */           }
/* 179 */         }, true);
/*     */         
/*     */ 
/* 182 */         popupMenu.addButton(DofusArenaTranslator.getInstance().getString("chat.sendPrivateMessage", new Object[0]), null, new MouseClickListener() {
/*     */           public void run(MouseClickEvent event) {
/* 184 */             UIChatFrame.this.sendPrivateMessageTo(user);
/*     */           }
/* 186 */         }, true);
/*     */       }
/*     */       
/* 189 */       Xulor.getInstance().showPopupMenu(popupMenu);
/*     */       
/* 191 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 19005: 
/* 196 */       UIFriendUserMessage msg = (UIFriendUserMessage)message;
/*     */       
/* 198 */       DofusArenaUser friend = msg.getUser();
/* 199 */       if (friend != null) {
/* 200 */         friend.setNotify(!friend.isNotify());
/*     */       }
/*     */       
/* 203 */       return false;
/*     */     
/*     */ 
/*     */     case 19006: 
/* 207 */       UIUserMessage msg = (UIUserMessage)message;
/* 208 */       FieldedUser friendUser = msg.getUser();
/*     */       
/* 210 */       if (friendUser != null) {
/* 211 */         sendPrivateMessageTo(friendUser);
/*     */       }
/*     */       
/* 214 */       return false;
/*     */     }
/*     */     
/*     */     
/* 218 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 227 */     return 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(long id) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void sendPrivateMessageTo(User user)
/*     */   {
/* 262 */     Property fieldedProperty = Xulor.getInstance().getEnvironment().getPropertiesProvider().getProperty("chat.dialogView");
/* 263 */     fieldedProperty.setFieldValue("input", "/w " + user.getName() + " ");
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIChatFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */