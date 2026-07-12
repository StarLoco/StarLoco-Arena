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
/*    */ 
/*    */ public class ChatSimplePipe
/*    */   extends ChatPipe
/*    */ {
/*    */   public ChatSimplePipe(String internalName) {
/* 23 */     super(internalName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void pushMessage(ChatMessage message) {
/* 33 */     super.pushMessage(message);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\chat\pipe\ChatSimplePipe.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */