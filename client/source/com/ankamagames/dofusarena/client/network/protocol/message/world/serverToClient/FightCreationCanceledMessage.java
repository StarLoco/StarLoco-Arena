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
/*    */ public class FightCreationCanceledMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private long m_fightId;
/*    */   private byte m_cancelReason;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 28 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/* 29 */     this.m_fightId = buffer.getLong();
/* 30 */     this.m_cancelReason = buffer.get();
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 40 */     return 4310;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getFightId() {
/* 47 */     return this.m_fightId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getCancelReason() {
/* 54 */     return this.m_cancelReason;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\world\serverToClient\FightCreationCanceledMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */