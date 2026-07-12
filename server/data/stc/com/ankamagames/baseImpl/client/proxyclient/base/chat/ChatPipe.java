/*     */ package com.ankamagames.baseImpl.client.proxyclient.base.chat;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.userGroup.UserGroup;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
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
/*     */ 
/*     */ 
/*     */ public abstract class ChatPipe
/*     */ {
/*  21 */   private static int MAX_HISTORY_PER_PIPE = 100;
/*     */   
/*  23 */   private String m_internalName = null;
/*     */   
/*     */   private UserGroup m_users;
/*     */   private LinkedList<ChatMessage> m_messages;
/*     */   private List<ChatPipeListener> m_listeners;
/*  28 */   protected HashMap<String, ChatPipe> m_subPipes = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChatPipe(String internalName)
/*     */   {
/*  36 */     this.m_internalName = internalName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getInternalName()
/*     */   {
/*  43 */     return this.m_internalName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LinkedList<ChatMessage> getMessages()
/*     */   {
/*  52 */     return this.m_messages;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public UserGroup getUsers()
/*     */   {
/*  59 */     return this.m_users;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addSubPipe(String key, ChatPipe pipe)
/*     */   {
/*  70 */     if (this.m_subPipes == null) {
/*  71 */       this.m_subPipes = new HashMap();
/*     */     }
/*     */     
/*  74 */     this.m_subPipes.put(key, pipe);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void pushMessage(ChatMessage message)
/*     */   {
/*  83 */     if (this.m_messages == null) {
/*  84 */       this.m_messages = new LinkedList();
/*     */     }
/*     */     
/*  87 */     if (this.m_messages.size() > MAX_HISTORY_PER_PIPE) {
/*  88 */       this.m_messages.removeLast();
/*     */     }
/*     */     
/*  91 */     this.m_messages.addFirst(message);
/*     */     
/*  93 */     if (this.m_listeners != null) {
/*  94 */       for (ChatPipeListener listener : this.m_listeners) {
/*  95 */         listener.onMessage(message);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void pushMessage(ChatMessage message, String subPipeKey)
/*     */   {
/* 107 */     if (!this.m_subPipes.containsKey(subPipeKey)) {
/* 108 */       onSubPipeInexistant(subPipeKey);
/*     */     }
/*     */     
/* 111 */     ChatPipe subPipe = (ChatPipe)this.m_subPipes.get(subPipeKey);
/*     */     
/* 113 */     if (subPipe != null) {
/* 114 */       subPipe.pushMessage(message);
/*     */       
/* 116 */       if (this.m_listeners != null) {
/* 117 */         for (ChatPipeListener listener : this.m_listeners) {
/* 118 */           listener.onMessage(message);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void onSubPipeInexistant(String subPipeKey) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addListener(ChatPipeListener listener)
/*     */   {
/* 138 */     if (this.m_listeners == null) {
/* 139 */       this.m_listeners = new ArrayList();
/*     */     }
/*     */     
/* 142 */     if (!this.m_listeners.contains(listener)) {
/* 143 */       this.m_listeners.add(listener);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeListener(ChatPipeListener listener)
/*     */   {
/* 151 */     if (this.m_listeners != null) {
/* 152 */       this.m_listeners.remove(listener);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public List<ChatPipeListener> getListeners()
/*     */   {
/* 159 */     return this.m_listeners;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HashMap<String, ChatPipe> getSubPipes()
/*     */   {
/* 166 */     return this.m_subPipes;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\chat\ChatPipe.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */