/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.world.clientToServer;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
/*    */ import com.ankamagames.dofusarena.common.game.team.TeamPreset;
/*    */ import gnu.trove.TLongArrayList;
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
/*    */ public class SetReadyForFightMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_fightId;
/*    */   private TeamPreset m_teamPreset;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 33 */     TLongArrayList fighters = this.m_teamPreset.getFightersIds();
/*    */     ByteBuffer buffer;
/* 35 */     if (fighters != null) {
/* 36 */       ByteBuffer buffer = ByteBuffer.allocate(9 + fighters.size() * 8);
/*    */       
/* 38 */       buffer.putLong(this.m_fightId);
/*    */       
/* 40 */       buffer.put((byte)fighters.size());
/* 41 */       long[] arrayOfLong; int j = (arrayOfLong = fighters.toNativeArray()).length; for (int i = 0; i < j; i++) { Long fighterId = Long.valueOf(arrayOfLong[i]);
/* 42 */         buffer.putLong(fighterId.longValue());
/*    */       }
/*    */     }
/*    */     else {
/* 46 */       buffer = ByteBuffer.allocate(9);
/* 47 */       buffer.putLong(this.m_fightId);
/* 48 */       buffer.put((byte)0);
/*    */     }
/*    */     
/* 51 */     return addClientHeader((byte)2, buffer.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 61 */     return 4303;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setFightId(long fightID)
/*    */   {
/* 68 */     this.m_fightId = fightID;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setTeamPreset(TeamPreset teamPreset)
/*    */   {
/* 75 */     this.m_teamPreset = teamPreset;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\world\clientToServer\SetReadyForFightMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */