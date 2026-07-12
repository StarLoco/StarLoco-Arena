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
/*    */ public class OpponentFoundMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private long m_fightId;
/*    */   private int m_bet;
/*    */   private byte m_fightTypeId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 29 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/* 30 */     this.m_fightId = buffer.getLong();
/* 31 */     this.m_bet = buffer.getInt();
/* 32 */     this.m_fightTypeId = buffer.get();
/* 33 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 42 */     return 2300;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getFightId()
/*    */   {
/* 49 */     return this.m_fightId;
/*    */   }
/*    */   
/*    */   public int getBet() {
/* 53 */     return this.m_bet;
/*    */   }
/*    */   
/*    */   public byte getFightTypeId() {
/* 57 */     return this.m_fightTypeId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\world\serverToClient\OpponentFoundMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */