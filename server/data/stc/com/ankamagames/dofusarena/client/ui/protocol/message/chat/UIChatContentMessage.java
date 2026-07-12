/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.chat;
/*    */ 
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
/*    */ 
/*    */ public class UIChatContentMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private String m_message;
/*    */   
/*    */   public int getId()
/*    */   {
/* 26 */     return 19000;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setMessage(String message)
/*    */   {
/* 33 */     this.m_message = message;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 40 */     return this.m_message;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\message\chat\UIChatContentMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */