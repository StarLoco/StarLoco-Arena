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
/*    */ public class UIFighterEquipmentMessage
/*    */   extends UIFighterMessage
/*    */ {
/*    */   private FighterCard m_equipment;
/*    */   private short m_position;
/*    */   
/*    */   public FighterCard getEquipment()
/*    */   {
/* 23 */     return this.m_equipment;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setEquipment(FighterCard equipment)
/*    */   {
/* 30 */     this.m_equipment = equipment;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public short getPosition()
/*    */   {
/* 37 */     return this.m_position;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setPosition(short position)
/*    */   {
/* 44 */     this.m_position = position;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\message\teamManagement\UIFighterEquipmentMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */