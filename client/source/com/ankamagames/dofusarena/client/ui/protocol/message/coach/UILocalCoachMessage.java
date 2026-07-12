/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.coach;
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
/*    */ public class UILocalCoachMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private LocalCoach m_localCoach;
/*    */   
/*    */   public LocalCoach getLocalCoach() {
/* 23 */     return this.m_localCoach;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCoach(LocalCoach localCoach) {
/* 30 */     this.m_localCoach = localCoach;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\coach\UILocalCoachMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */