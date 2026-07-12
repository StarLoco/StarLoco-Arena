/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.statistics;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.protocol.sql.ResultMessage;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class StatisticsReportRequestMessage
/*    */   extends ResultMessage
/*    */ {
/* 20 */   private static final ObjectPool m_pool = new MonitoredPool(new ObjectFactory() {
/* 21 */     public StatisticsReportRequestMessage makeObject() { return new StatisticsReportRequestMessage(); }
/* 20 */   });
/*    */   private short m_modelId;
/*    */   private long m_reportId;
/*    */   private byte[] m_serializedReport;
/*    */   
/*    */   static StatisticsReportRequestMessage checkOut() {
/*    */     StatisticsReportRequestMessage msg;
/*    */     try {
/* 28 */       StatisticsReportRequestMessage msg = (StatisticsReportRequestMessage)m_pool.borrowObject();
/* 29 */       msg.setPool(m_pool);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 33 */       msg = new StatisticsReportRequestMessage();
/* 34 */       m_logger.error("Erreur lors d'un checkOut sur un message de type StatisticsReportRequestMessage : " + e.getMessage());
/*    */     }
/* 36 */     return msg;
/*    */   }
/*    */   
/*    */   public void onCheckOut() {
/* 40 */     super.onCheckOut();
/* 41 */     this.m_serializedReport = null;
/*    */   }
/*    */   
/*    */   public void onCheckIn() {
/* 45 */     super.onCheckIn();
/* 46 */     this.m_serializedReport = null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   byte[] getSerializedReport()
/*    */   {
/* 58 */     return this.m_serializedReport;
/*    */   }
/*    */   
/*    */   void setSerializedReport(byte[] serializedReport) {
/* 62 */     this.m_serializedReport = serializedReport;
/*    */   }
/*    */   
/*    */   public short getModelId() {
/* 66 */     return this.m_modelId;
/*    */   }
/*    */   
/*    */   public void setModelId(short modelId) {
/* 70 */     this.m_modelId = modelId;
/*    */   }
/*    */   
/*    */   public long getReportId() {
/* 74 */     return this.m_reportId;
/*    */   }
/*    */   
/*    */   public void setReportId(long reportId) {
/* 78 */     this.m_reportId = reportId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 87 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\statistics\StatisticsReportRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */