/*     */ package com.ankamagames.dofusarena.client.ui.actions;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*     */ import com.ankamagames.baseImpl.graphicalClient.chat.ChatPipeWrapper;
/*     */ import com.ankamagames.baseImpl.graphicalClient.chat.FieldedUser;
/*     */ import com.ankamagames.dofusarena.client.chat.ChatView;
/*     */ import com.ankamagames.dofusarena.client.chat.ChatViewManager;
/*     */ import com.ankamagames.dofusarena.client.chat.DofusArenaUser;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.chat.UIChatContentMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.chat.UIChatPipeSelectionMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.chat.UIFriendUserMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.chat.UIUserMessage;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.xulor.core.form.Form;
/*     */ import com.ankamagames.xulor.event.Event;
/*     */ import com.ankamagames.xulor.event.ItemClickEvent;
/*     */ import com.ankamagames.xulor.event.ItemDoubleClickEvent;
/*     */ import com.ankamagames.xulor.event.KeyPressedEvent;
/*     */ import com.ankamagames.xulor.event.MouseButtons;
/*     */ import com.ankamagames.xulor.property.Property;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatActions
/*     */ {
/*     */   public static final String PACKAGE = "dofusarena.chat";
/*     */   
/*     */   public static void processInputKeyEvent(KeyPressedEvent keyPressedEvent, Form form)
/*     */   {
/*  39 */     Property fieldedProperty = form.getProperty("chat.dialogView");
/*  40 */     switch (keyPressedEvent.getKeyClass()) {
/*     */     case F1: 
/*  42 */       form.synchronizeProperties();
/*  43 */       String input = fieldedProperty.getFieldStringValue("input");
/*     */       
/*  45 */       if (input.length() > 0) {
/*  46 */         UIChatContentMessage contentMessage = new UIChatContentMessage();
/*  47 */         contentMessage.setMessage(input);
/*  48 */         Worker.getInstance().pushMessage(contentMessage);
/*     */         
/*  50 */         fieldedProperty.setFieldValue("input", "");
/*     */       }
/*     */       
/*  53 */       break;
/*     */     
/*     */     case DIGIT: 
/*  56 */       ChatView view = ChatViewManager.getInstance().getView();
/*     */       
/*  58 */       if (view != null) { fieldedProperty.setFieldValue("input", view.getConsole().getHistoryUp());
/*     */       }
/*  60 */       break;
/*     */     
/*     */     case DOWN: 
/*  63 */       ChatView view = ChatViewManager.getInstance().getView();
/*     */       
/*  65 */       if (view != null) {
/*  66 */         fieldedProperty.setFieldValue("input", view.getConsole().getHistoryDown());
/*     */       }
/*     */       
/*     */       break;
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */   public static void checkPipe(Event event, ChatPipeWrapper pipe)
/*     */   {
/*  77 */     if (pipe != null) {
/*  78 */       UIChatPipeSelectionMessage message = new UIChatPipeSelectionMessage();
/*  79 */       message.setPipeWrapper(pipe);
/*  80 */       message.setListenPipe(!pipe.isOpen());
/*  81 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void openCloseContactList(Event event)
/*     */   {
/*  92 */     UIMessage message = new UIMessage();
/*  93 */     message.setId(19002);
/*  94 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void openCloseEmoteBar(Event event)
/*     */   {
/* 104 */     UIMessage message = new UIMessage();
/* 105 */     message.setId(19008);
/* 106 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void maximizeMinimizeChatWindow(Event event)
/*     */   {
/* 116 */     UIMessage message = new UIMessage();
/* 117 */     message.setId(19007);
/* 118 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void openContactPopupMenu(ItemClickEvent event)
/*     */   {
/* 127 */     if (event.getButton() == MouseButtons.BUTTON3) {
/* 128 */       Object value = event.getItemValue();
/* 129 */       if ((value != null) && ((value instanceof DofusArenaUser))) {
/* 130 */         UIFriendUserMessage message = new UIFriendUserMessage();
/* 131 */         message.setUser((DofusArenaUser)value);
/* 132 */         message.setId(19003);
/* 133 */         Worker.getInstance().pushMessage(message);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void checkNotify(Event event, DofusArenaUser friend)
/*     */   {
/* 146 */     if (friend != null)
/*     */     {
/* 148 */       UIFriendUserMessage message = new UIFriendUserMessage();
/* 149 */       message.setUser(friend);
/* 150 */       message.setId(19005);
/* 151 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void privateMessage(ItemDoubleClickEvent event)
/*     */   {
/* 161 */     Object value = event.getItemValue();
/* 162 */     if ((value != null) && ((value instanceof FieldedUser))) {
/* 163 */       UIUserMessage message = new UIUserMessage();
/* 164 */       message.setUser((DofusArenaUser)value);
/* 165 */       message.setId(19006);
/* 166 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void enableDisableFriendFilter(Event event)
/*     */   {
/* 177 */     UIMessage message = new UIMessage();
/* 178 */     message.setId(19009);
/* 179 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void enableDisableIgnoreFilter(Event event)
/*     */   {
/* 189 */     UIMessage message = new UIMessage();
/* 190 */     message.setId(19010);
/* 191 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\actions\ChatActions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */