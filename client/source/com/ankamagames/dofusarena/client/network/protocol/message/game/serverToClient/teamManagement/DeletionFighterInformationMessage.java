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
/*    */ public class DeletionFighterInformationMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private byte m_errorCode;
/*    */   private long m_fighterInformationId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 29 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 31 */     this.m_errorCode = buffer.get();
/*    */     
/* 33 */     if (this.m_errorCode == 0) {
/* 34 */       this.m_fighterInformationId = buffer.getLong();
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
/* 46 */     return 6002;
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
/*    */   public long getFighterInformationId() {
/* 60 */     return this.m_fighterInformationId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\teamManagement\DeletionFighterInformationMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */