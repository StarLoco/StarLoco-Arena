/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.chat;
/*    */ 
/*    */ import gnu.trove.TIntObjectHashMap;
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
/*    */ public class ChatManager
/*    */ {
/* 16 */   private static final ChatManager m_instance = new ChatManager();
/*    */   private TIntObjectHashMap<ChatPipe> m_chatPipes;
/*    */   
/* 19 */   public static ChatManager getInstance() { return m_instance; }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addChatPipe(int key, ChatPipe pipe)
/*    */   {
/* 31 */     if (this.m_chatPipes == null) {
/* 32 */       this.m_chatPipes = new TIntObjectHashMap();
/*    */     }
/*    */     
/* 35 */     this.m_chatPipes.put(key, pipe);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public ChatPipe getChatPipe(int key)
/*    */   {
/* 43 */     return (ChatPipe)this.m_chatPipes.get(key);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void pushMessage(ChatMessage message, String subPipeKey)
/*    */   {
/* 51 */     ChatPipe pipe = (ChatPipe)this.m_chatPipes.get(message.getPipeDestination());
/* 52 */     pipe.pushMessage(message, subPipeKey);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void pushMessage(ChatMessage message)
/*    */   {
/* 59 */     ChatPipe pipe = (ChatPipe)this.m_chatPipes.get(message.getPipeDestination());
/* 60 */     pipe.pushMessage(message);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public ChatPipe getPipe(int pipeKey)
/*    */   {
/* 68 */     return (ChatPipe)this.m_chatPipes.get(pipeKey);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\chat\ChatManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */