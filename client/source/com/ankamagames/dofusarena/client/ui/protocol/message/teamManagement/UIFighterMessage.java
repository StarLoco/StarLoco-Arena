/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
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
/*    */ public class UIFighterMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private Fighter m_fighter;
/*    */   
/*    */   public Fighter getFighter() {
/* 23 */     return this.m_fighter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFighter(Fighter fighter) {
/* 30 */     this.m_fighter = fighter;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\teamManagement\UIFighterMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */