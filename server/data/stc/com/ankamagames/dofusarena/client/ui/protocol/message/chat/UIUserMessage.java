/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.chat;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphicalClient.chat.FieldedUser;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UIUserMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private FieldedUser m_user;
/*    */   
/*    */   public FieldedUser getUser()
/*    */   {
/* 17 */     return this.m_user;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setUser(FieldedUser user)
/*    */   {
/* 24 */     this.m_user = user;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\message\chat\UIUserMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */