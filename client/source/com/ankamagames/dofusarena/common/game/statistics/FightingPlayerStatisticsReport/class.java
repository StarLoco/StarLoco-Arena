/*    */ package com.ankamagames.dofusarena.common.game.statistics.FightingPlayerStatisticsReport;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.statistics.AbstractStatisticsReport;
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
/*    */ public class FightingPlayerStatisticsReport
/*    */   extends AbstractStatisticsReport
/*    */ {
/*    */   public AbstractStatisticsReport newInstance() {
/* 24 */     return new com.ankamagames.dofusarena.common.game.statistics.FightingPlayerStatisticsReport();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initialize() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public long getBelongingPlayerId() {
/* 35 */     return getReportEntryAsLong((short)200);
/*    */   }
/*    */   
/*    */   public void setBelongingPlayerId(long belongingPlayerId) {
/* 39 */     setReportEntry((short)200, belongingPlayerId);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\statistics\FightingPlayerStatisticsReport\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */