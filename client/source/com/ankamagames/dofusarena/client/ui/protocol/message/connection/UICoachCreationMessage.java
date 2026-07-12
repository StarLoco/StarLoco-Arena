/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.connection;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
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
/*    */ public class UICoachCreationMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private LocalCoach m_localCoach;
/*    */   
/*    */   public UICoachCreationMessage() {
/* 26 */     onCheckOut();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 36 */     return 16400;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalCoach getLocalCoach() {
/* 43 */     return this.m_localCoach;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLocalCoach(LocalCoach localCoach) {
/* 50 */     this.m_localCoach = localCoach;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\connection\UICoachCreationMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */