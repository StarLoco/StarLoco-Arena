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
/*    */ public class DeletionTeamPresetMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private byte m_errorCode;
/*    */   private short m_teamPresetId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 29 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 31 */     this.m_errorCode = buffer.get();
/*    */     
/* 33 */     if (this.m_errorCode == 0) {
/* 34 */       this.m_teamPresetId = buffer.getShort();
/*    */     }
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 46 */     return 6022;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getErrorCode() {
/* 53 */     return this.m_errorCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public short getTeamPresetId() {
/* 60 */     return this.m_teamPresetId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\teamManagement\DeletionTeamPresetMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */