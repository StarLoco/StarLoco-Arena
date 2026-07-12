/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient;
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
/*    */ public class ReadyForFightMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private byte m_errorCode;
/*    */   private long m_coachId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 29 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/* 30 */     this.m_errorCode = buffer.get();
/* 31 */     if (this.m_errorCode == 0) {
/* 32 */       this.m_coachId = buffer.getLong();
/*    */     }
/* 34 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 43 */     return 4306;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getErrorCode()
/*    */   {
/* 50 */     return this.m_errorCode;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getCoachId()
/*    */   {
/* 57 */     return this.m_coachId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\world\serverToClient\ReadyForFightMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */