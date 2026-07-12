/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.chat;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphicalClient.chat.ChatPipeWrapper;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
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
/*    */ public class UIChatPipeSelectionMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private ChatPipeWrapper m_pipeWrapper;
/*    */   private boolean m_listenPipe;
/*    */   private int m_viewIndex;
/*    */   
/*    */   public int getId()
/*    */   {
/* 28 */     return 19001;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ChatPipeWrapper getPipeWrapper()
/*    */   {
/* 35 */     return this.m_pipeWrapper;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setPipeWrapper(ChatPipeWrapper pipeWrapper)
/*    */   {
/* 42 */     this.m_pipeWrapper = pipeWrapper;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getViewIndex()
/*    */   {
/* 49 */     return this.m_viewIndex;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setViewIndex(int viewIndex)
/*    */   {
/* 56 */     this.m_viewIndex = viewIndex;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isListenPipe()
/*    */   {
/* 63 */     return this.m_listenPipe;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setListenPipe(boolean listenPipe)
/*    */   {
/* 70 */     this.m_listenPipe = listenPipe;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\message\chat\UIChatPipeSelectionMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */