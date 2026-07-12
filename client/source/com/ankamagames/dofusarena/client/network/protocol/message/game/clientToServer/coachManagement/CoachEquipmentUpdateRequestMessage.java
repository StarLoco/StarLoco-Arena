/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.coachManagement;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
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
/*    */ public class CoachEquipmentUpdateRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long[] m_equipmentArray;
/*    */   
/*    */   public byte[] encode() {
/* 30 */     ByteBuffer buffer = ByteBuffer.allocate(112);
/* 31 */     if (this.m_equipmentArray != null) {
/* 32 */       for (int i = 0; i < this.m_equipmentArray.length; i++) {
/* 33 */         buffer.putLong(this.m_equipmentArray[i]);
/*    */       }
/*    */     }
/* 36 */     return addClientHeader((byte)3, buffer.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 46 */     return 5201;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setEquipmentArray(long[] equipmentArray) {
/* 53 */     this.m_equipmentArray = equipmentArray;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\coachManagement\CoachEquipmentUpdateRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */