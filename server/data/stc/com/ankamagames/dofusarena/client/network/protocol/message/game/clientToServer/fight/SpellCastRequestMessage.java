/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight;
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
/*    */ 
/*    */ public class SpellCastRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_fighterId;
/*    */   private int m_spellId;
/*    */   private int m_castPositionX;
/*    */   private int m_castPositionY;
/*    */   private short m_castPositionZ;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 36 */     ByteBuffer bb = ByteBuffer.allocate(22);
/*    */     
/* 38 */     bb.putLong(this.m_fighterId);
/* 39 */     bb.putInt(this.m_spellId);
/* 40 */     bb.putInt(this.m_castPositionX);
/* 41 */     bb.putInt(this.m_castPositionY);
/* 42 */     bb.putShort(this.m_castPositionZ);
/*    */     
/* 44 */     return addClientHeader((byte)3, bb.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 54 */     return 8109;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setFighterId(long fighterId)
/*    */   {
/* 61 */     this.m_fighterId = fighterId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setSpellId(int spellId)
/*    */   {
/* 68 */     this.m_spellId = spellId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setCastPosition(int x, int y, short z)
/*    */   {
/* 77 */     this.m_castPositionX = x;
/* 78 */     this.m_castPositionY = y;
/* 79 */     this.m_castPositionZ = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\fight\SpellCastRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */