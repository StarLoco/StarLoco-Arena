/*     */ package com.ankamagames.baseImpl.graphicalClient.chat;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatPipe;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatPipeListener;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleView;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractChatView
/*     */   implements ConsoleView, ChatPipeListener, FieldProvider
/*     */ {
/*     */   public static final String FIELDEDPROPERTY_NAME = "chat.dialogView";
/*     */   public static final String INPUT_FIELD = "input";
/*     */   public static final String HISTORY_FIELD = "history";
/*  31 */   public static final String[] FIELDS = { "history", "input" };
/*     */   
/*  33 */   private String m_input = "";
/*  34 */   private String m_history = "";
/*     */   
/*     */   protected ConsoleManager m_console;
/*     */   
/*  38 */   private List<ChatPipeWrapper> m_wrappedPipes = new ArrayList();
/*     */   private int m_viewId;
/*     */   
/*     */   public AbstractChatView(int viewId)
/*     */   {
/*  43 */     this.m_viewId = viewId;
/*     */     
/*  45 */     PropertiesProvider propertiesProvider = Xulor.getInstance().getEnvironment().getPropertiesProvider();
/*  46 */     propertiesProvider.setPropertyValue("chat.dialogView", this);
/*     */     
/*  48 */     this.m_console = new ConsoleManager();
/*  49 */     this.m_console.setUsePath(false);
/*  50 */     this.m_console.setUseMultiCommands(false);
/*  51 */     this.m_console.addView(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateDisplayHistory()
/*     */   {
/*  59 */     ArrayList<ChatMessage> visibleMessages = new ArrayList();
/*     */     
/*  61 */     for (ChatPipeWrapper pipe : this.m_wrappedPipes) {
/*  62 */       if (pipe.isOpen()) {
/*  63 */         addMessages(visibleMessages, pipe.getChatPipe());
/*     */       }
/*     */     }
/*  66 */     Collections.sort(visibleMessages);
/*     */     
/*  68 */     StringBuilder history = new StringBuilder();
/*  69 */     for (ChatMessage message : visibleMessages) {
/*  70 */       history.append(formatMessage(message));
/*     */     }
/*     */     
/*  73 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("chat.dialogView", "history", history.toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addMessages(ArrayList<ChatMessage> messages, ChatPipe pipe)
/*     */   {
/*  82 */     if (pipe == null) {
/*  83 */       return;
/*     */     }
/*  85 */     if (pipe.getMessages() != null) {
/*  86 */       for (ChatMessage message : pipe.getMessages()) {
/*  87 */         messages.add(message);
/*     */       }
/*     */     }
/*  90 */     if (pipe.getSubPipes() != null) {
/*  91 */       for (ChatPipe subPipe : pipe.getSubPipes().values()) {
/*  92 */         addMessages(messages, subPipe);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract String formatMessage(ChatMessage paramChatMessage);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void registerPipe(ChatPipe pipe, String name)
/*     */   {
/* 109 */     pipe.addListener(this);
/* 110 */     ChatPipeWrapper wp = new ChatPipeWrapper(pipe, name);
/* 111 */     this.m_wrappedPipes.add(wp);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<ChatPipeWrapper> getWrappedPipes()
/*     */   {
/* 118 */     return this.m_wrappedPipes;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onMessage(ChatMessage message)
/*     */   {
/* 127 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().appendPropertyValue("chat.dialogView", "history", formatMessage(message));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ConsoleManager getConsole()
/*     */   {
/* 134 */     return this.m_console;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getFields()
/*     */   {
/* 143 */     return FIELDS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getViewId()
/*     */   {
/* 150 */     return this.m_viewId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getFieldValue(String fieldName)
/*     */   {
/* 159 */     if (fieldName.equals("input"))
/* 160 */       return this.m_input;
/* 161 */     if (fieldName.equals("history")) {
/* 162 */       return this.m_history;
/*     */     }
/* 164 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFieldValue(String fieldName, Object value)
/*     */   {
/* 174 */     if (fieldName.equals("input")) {
/* 175 */       this.m_input = ((String)value);
/* 176 */     } else if (fieldName.equals("history")) {
/* 177 */       this.m_history = ((String)value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void prependFieldValue(String fieldName, Object value)
/*     */   {
/* 188 */     if (fieldName.equals("input")) {
/* 189 */       this.m_input = ((String)value + this.m_input);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void appendFieldValue(String fieldName, Object value)
/*     */   {
/* 200 */     if (fieldName.equals("history")) {
/* 201 */       this.m_history += (String)value;
/* 202 */     } else if (fieldName.equals("input")) {
/* 203 */       this.m_input += (String)value;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFieldSynchronisable(String fieldName)
/*     */   {
/* 213 */     if (fieldName.equals("input")) {
/* 214 */       return true;
/*     */     }
/* 216 */     return false;
/*     */   }
/*     */   
/*     */   public void setPrompt(String prompt) {}
/*     */   
/*     */   public void err(String text) {}
/*     */   
/*     */   public void log(String text) {}
/*     */   
/*     */   public void trace(String text) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\chat\AbstractChatView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */