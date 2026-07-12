package com.ankamagames.baseImpl.common.clientAndServer.game.statistics;

public interface StatisticsReportEventsHandler<ReportType extends AbstractStatisticsReport> {
  void onReportLoaded(ReportType paramReportType);
  
  void onReportLoadError(ReportType paramReportType, String paramString);
  
  void onReportSaved(ReportType paramReportType);
  
  void onReportSaveError(ReportType paramReportType, String paramString);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\statistics\StatisticsReportEventsHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */