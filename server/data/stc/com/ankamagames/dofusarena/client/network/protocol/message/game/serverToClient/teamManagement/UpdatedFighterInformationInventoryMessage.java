/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
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
/*    */ public class UpdatedFighterInformationInventoryMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private byte m_errorCode;
/*    */   private long m_fighterId;
/*    */   private byte[] m_serializedSpellInventory;
/*    */   private byte[] m_serializedEquipmentInventory;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 33 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 35 */     this.m_fighterId = buffer.getLong();
/* 36 */     this.m_errorCode = buffer.get();
/*    */     
/* 38 */     if (this.m_errorCode == 0)
/*    */     {
/* 40 */       int spellLength = buffer.getShort();
/* 41 */       this.m_serializedSpellInventory = new byte[spellLength];
/* 42 */       buffer.get(this.m_serializedSpellInventory);
/*    */       
/* 44 */       int equipmentLength = buffer.getShort();
/* 45 */       this.m_serializedEquipmentInventory = new byte[equipmentLength];
/* 46 */       buffer.get(this.m_serializedEquipmentInventory);
/*    */     }
/*    */     
/*    */ 
/* 50 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 60 */     return 6010;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getErrorCode()
/*    */   {
/* 67 */     return this.m_errorCode;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getFighterId()
/*    */   {
/* 74 */     return this.m_fighterId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte[] getSerializedEquipmentInventory()
/*    */   {
/* 81 */     return this.m_serializedEquipmentInventory;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte[] getSerializedSpellInventory()
/*    */   {
/* 88 */     return this.m_serializedSpellInventory;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\teamManagement\UpdatedFighterInformationInventoryMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */