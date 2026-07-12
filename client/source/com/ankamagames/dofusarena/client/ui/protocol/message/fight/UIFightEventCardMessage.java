/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.fight;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.game.event.Event;
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
/*    */ public class UIFightEventCardMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private Event m_event;
/*    */   
/*    */   public Event getEvent() {
/* 24 */     return this.m_event;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setEvent(Event event) {
/* 31 */     this.m_event = event;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\fight\UIFightEventCardMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */