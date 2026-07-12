/*     */ package com.ankamagames.dofusarena.common.game.statistics;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.statistics.AbstractStatisticsReport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerStatisticsReport
/*     */   extends AbstractStatisticsReport
/*     */ {
/*     */   public AbstractStatisticsReport newInstance()
/*     */   {
/*  24 */     return new PlayerStatisticsReport();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initialize() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateFromReport(FightingPlayerStatisticsReport report)
/*     */   {
/*  40 */     long startTime = report.getReportEntryAsLong((short)201);
/*  41 */     long endTime = report.getReportEntryAsLong((short)202);
/*     */     
/*  43 */     long fightDuration = 0L;
/*  44 */     if ((endTime > 0L) && (startTime > 0L) && (endTime > startTime)) {
/*  45 */       fightDuration = endTime - startTime;
/*     */     }
/*  47 */     int victoryIncrease = report.getReportEntryAsInt((short)203);
/*     */     
/*  49 */     setReportEntry((short)2, getReportEntryAsLong((short)2) + fightDuration);
/*     */     
/*  51 */     setReportEntry((short)3, getReportEntryAsInt((short)3) + 1);
/*     */     
/*  53 */     if (victoryIncrease > 0) {
/*  54 */       setReportEntry((short)4, getReportEntryAsInt((short)4) + 1);
/*  55 */       setReportEntry((short)7, getReportEntryAsInt((short)7) + 1);
/*  56 */       setReportEntry((short)8, 0);
/*     */     } else {
/*  58 */       setReportEntry((short)5, getReportEntryAsInt((short)5) + 1);
/*  59 */       setReportEntry((short)8, getReportEntryAsInt((short)8) + 1);
/*  60 */       setReportEntry((short)7, 0);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTotalSoloFights(int numFights)
/*     */   {
/*  73 */     setReportEntry((short)3, numFights);
/*     */   }
/*     */   
/*     */   public void setTotalSoloLosses(int numLosses)
/*     */   {
/*  78 */     setReportEntry((short)5, numLosses);
/*     */   }
/*     */   
/*     */   public void setTotalPlayTime(long time) {
/*  82 */     setReportEntry((short)1, time);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getTotalFights()
/*     */   {
/*  89 */     return getReportEntryAsInt((short)3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getTotalFightsWon()
/*     */   {
/*  96 */     return getReportEntryAsInt((short)4);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getTotalFightsLost()
/*     */   {
/* 103 */     return getReportEntryAsInt((short)5);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getTotalFightsTime()
/*     */   {
/* 110 */     return getReportEntryAsLong((short)2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getTotalPlayTime()
/*     */   {
/* 117 */     return getReportEntryAsLong((short)1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getOneVsOneLadderPlayerStrength()
/*     */   {
/* 124 */     return getReportEntryAsInt((short)6);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getConsecutiveLosses()
/*     */   {
/* 132 */     return getReportEntryAsInt((short)8);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getConsecutiveWins()
/*     */   {
/* 140 */     return getReportEntryAsInt((short)7);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\statistics\PlayerStatisticsReport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */