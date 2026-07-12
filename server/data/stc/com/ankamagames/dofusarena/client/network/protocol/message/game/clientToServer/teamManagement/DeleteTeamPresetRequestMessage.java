/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.teamManagement;
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
/*    */ public class DeleteTeamPresetRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private short m_teamPresetId;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 29 */     ByteBuffer buffer = ByteBuffer.allocate(2);
/*    */     
/* 31 */     buffer.putShort(this.m_teamPresetId);
/*    */     
/* 33 */     return addClientHeader((byte)3, buffer.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 43 */     return 6023;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setTeamPresetId(short teamPresetId)
/*    */   {
/* 50 */     this.m_teamPresetId = teamPresetId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\teamManagement\DeleteTeamPresetRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */