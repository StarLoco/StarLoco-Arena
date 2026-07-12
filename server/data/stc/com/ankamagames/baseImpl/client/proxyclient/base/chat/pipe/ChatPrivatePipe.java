/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.chat.pipe;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatMessage;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatPipe;
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
/*    */ public class ChatPrivatePipe
/*    */   extends ChatPipe
/*    */ {
/*    */   public ChatPrivatePipe(String internalName)
/*    */   {
/* 23 */     super(internalName);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void pushMessage(ChatMessage message)
/*    */   {
/* 33 */     super.pushMessage(message);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void onSubPipeInexistant(String subPipeKey)
/*    */   {
/* 43 */     addSubPipe(subPipeKey, new ChatSimplePipe(subPipeKey));
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\chat\pipe\ChatPrivatePipe.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */