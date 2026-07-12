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
/*    */   public byte[] encode() {
/*    */     ByteBuffer buffer;
/* 33 */     TLongArrayList fighters = this.m_teamPreset.getFightersIds();
/*    */     
/* 35 */     if (fighters != null) {
/* 36 */       buffer = ByteBuffer.allocate(9 + fighters.size() * 8);
/*    */       
/* 38 */       buffer.putLong(this.m_fightId);
/*    */       
/* 40 */       buffer.put((byte)fighters.size()); byte b; int i; long[] arrayOfLong;
/* 41 */       for (i = (arrayOfLong = fighters.toNativeArray()).length, b = 0; b < i; ) { Long fighterId = Long.valueOf(arrayOfLong[b]);
/* 42 */         buffer.putLong(fighterId.longValue());
/*    */         b++; }
/*    */     
/*    */     } else {
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
/*    */   
/*    */   public int getId() {
/* 61 */     return 4303;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFightId(long fightID) {
/* 68 */     this.m_fightId = fightID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTeamPreset(TeamPreset teamPreset) {
/* 75 */     this.m_teamPreset = teamPreset;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\world\clientToServer\SetReadyForFightMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */