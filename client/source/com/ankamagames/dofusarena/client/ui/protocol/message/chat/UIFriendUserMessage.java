/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.chat;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.chat.DofusArenaUser;
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
/*    */ public class UIFriendUserMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private DofusArenaUser m_user;
/*    */   
/*    */   public DofusArenaUser getUser() {
/* 23 */     return this.m_user;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setUser(DofusArenaUser user) {
/* 30 */     this.m_user = user;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\chat\UIFriendUserMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */