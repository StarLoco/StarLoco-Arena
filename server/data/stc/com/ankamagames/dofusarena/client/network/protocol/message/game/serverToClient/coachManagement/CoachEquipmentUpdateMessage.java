/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.coachManagement;
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
/*    */ public class CoachEquipmentUpdateMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private byte[] m_equipmentData;
/*    */   private long m_coachId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 29 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 31 */     this.m_coachId = buffer.getLong();
/* 32 */     this.m_equipmentData = new byte[buffer.getShort()];
/* 33 */     buffer.get(this.m_equipmentData);
/*    */     
/* 35 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 44 */     return 5202;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte[] getEquipmentData()
/*    */   {
/* 51 */     return this.m_equipmentData;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getCoachId()
/*    */   {
/* 58 */     return this.m_coachId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\coachManagement\CoachEquipmentUpdateMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */