/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.teamManagement;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import java.nio.ByteBuffer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UpdateFighterInventoryRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private Fighter m_fighter;
/*    */   
/*    */   public byte[] encode() {
/* 31 */     byte[] serializedSpellInventory = new byte[0];
/* 32 */     byte[] serializedEquipmentInventory = new byte[0];
/* 33 */     if (this.m_fighter != null) {
/* 34 */       serializedSpellInventory = this.m_fighter.getSpellInventory().serialize();
/* 35 */       serializedEquipmentInventory = this.m_fighter.getEquipmentInventory().serialize();
/*    */     } 
/*    */ 
/*    */     
/* 39 */     ByteBuffer buffer = ByteBuffer.allocate(10 + serializedSpellInventory.length + 2 + serializedEquipmentInventory.length);
/*    */     
/* 41 */     buffer.putLong(this.m_fighter.getId());
/*    */     
/* 43 */     buffer.putShort((short)serializedSpellInventory.length);
/* 44 */     buffer.put(serializedSpellInventory);
/*    */     
/* 46 */     buffer.putShort((short)serializedEquipmentInventory.length);
/* 47 */     buffer.put(serializedEquipmentInventory);
/*    */     
/* 49 */     return addClientHeader((byte)3, buffer.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 59 */     return 6011;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFighter(Fighter fighter) {
/* 67 */     this.m_fighter = fighter;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\teamManagement\UpdateFighterInventoryRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */