/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.statistics.StatisticsReportManager;
/*    */ import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;
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
/*    */ public class PlayerStatisticsReportMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private PlayerStatisticsReport m_playerStatisics;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 30 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 32 */     int size = buffer.getShort() & 0xFFFF;
/* 33 */     byte[] serializedReport = new byte[size];
/* 34 */     if (size > 0) {
/* 35 */       buffer.get(serializedReport);
/*    */     }
/*    */     
/* 38 */     this.m_playerStatisics = (PlayerStatisticsReport)StatisticsReportManager.getInstance().createReport(serializedReport);
/*    */     
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 49 */     return 2400;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerStatisticsReport getPlayerStatisics() {
/* 56 */     return this.m_playerStatisics;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\world\serverToClient\PlayerStatisticsReportMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */