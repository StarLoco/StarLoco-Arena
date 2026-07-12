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
/*    */ public class UILocalCoachMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private LocalCoach m_localCoach;
/*    */   
/*    */   public LocalCoach getLocalCoach()
/*    */   {
/* 23 */     return this.m_localCoach;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setCoach(LocalCoach localCoach)
/*    */   {
/* 30 */     this.m_localCoach = localCoach;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\message\coach\UILocalCoachMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */