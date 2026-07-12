package com.ankamagames.baseImpl.common.clientAndServer.game.statistics;

public abstract interface StatisticsReportEventsHandler<ReportType extends AbstractStatisticsReport>
{
  public abstract void onReportLoaded(ReportType paramReportType);
  
  public abstract void onReportLoadError(ReportType paramReportType, String paramString);
  
  public abstract void onReportSaved(ReportType paramReportType);
  
  public abstract void onReportSaveError(ReportType paramReportType, String paramString);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\statistics\StatisticsReportEventsHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */