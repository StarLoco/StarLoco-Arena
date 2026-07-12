/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
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
/*    */ public class UIFighterCardMessage
/*    */   extends UIFighterMessage
/*    */ {
/*    */   private FighterCard m_fighterCard;
/*    */   
/*    */   public FighterCard getFighterCard()
/*    */   {
/* 22 */     return this.m_fighterCard;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setFighterCard(FighterCard fighterCard)
/*    */   {
/* 29 */     this.m_fighterCard = fighterCard;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\message\teamManagement\UIFighterCardMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */