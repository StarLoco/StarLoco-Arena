/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
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
/*    */ public class CoachCreationResultMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private byte m_errorCode;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 25 */     if (rawDatas.length == 1)
/* 26 */       this.m_errorCode = rawDatas[0]; 
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 36 */     return 2050;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getErrorCode() {
/* 43 */     return this.m_errorCode;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\world\serverToClient\CoachCreationResultMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */