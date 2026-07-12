/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.statistics;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.sql.SqlRequest;
/*     */ import com.ankamagames.framework.kernel.core.sql.SqlRequestChannel;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
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
/*     */ class StatisticsReportLoadRequest
/*     */   extends SqlRequest
/*     */ {
/*  27 */   private static final ObjectPool m_pool = new MonitoredPool(new ObjectFactory() {
/*  28 */     public StatisticsReportLoadRequest makeObject() { return new StatisticsReportLoadRequest(); }
/*  27 */   });
/*     */   private String m_sql;
/*     */   private short m_modelId;
/*     */   private long m_reportId;
/*     */   
/*     */   static StatisticsReportLoadRequest checkOut() {
/*     */     StatisticsReportLoadRequest msg;
/*     */     try {
/*  35 */       StatisticsReportLoadRequest msg = (StatisticsReportLoadRequest)m_pool.borrowObject();
/*  36 */       msg.setPool(m_pool);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  40 */       msg = new StatisticsReportLoadRequest();
/*  41 */       m_logger.error("Erreur lors d'un checkOut sur un message de type StatisticsReportLoadRequest : " + e.getMessage());
/*     */     }
/*  43 */     return msg;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setModelId(short modelId)
/*     */   {
/*  54 */     this.m_modelId = modelId;
/*     */   }
/*     */   
/*     */   void setReportId(long reportId) {
/*  58 */     this.m_reportId = reportId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Message execute(SqlRequestChannel channel)
/*     */     throws SQLException
/*     */   {
/*  70 */     this.m_sql = "SELECT * FROM tbl_dynamic_statistics WHERE statistics_model_id=? AND statistics_report_id=?;";
/*     */     
/*  72 */     StatisticsReportRequestMessage result = StatisticsReportRequestMessage.checkOut();
/*  73 */     result.setModelId(this.m_modelId);
/*  74 */     result.setReportId(this.m_reportId);
/*     */     try
/*     */     {
/*  77 */       PreparedStatement stmt = channel.getStatement(getId());
/*  78 */       if (stmt == null) {
/*  79 */         stmt = channel.getConnection().prepareStatement(this.m_sql);
/*  80 */         channel.putStatement(getId(), stmt);
/*     */       }
/*     */       
/*  83 */       stmt.setShort(1, this.m_modelId);
/*  84 */       stmt.setLong(2, this.m_reportId);
/*     */       
/*  86 */       ResultSet rs = stmt.executeQuery();
/*     */       
/*  88 */       if (rs.next()) {
/*  89 */         byte[] datas = rs.getBytes("statistics_report");
/*     */         
/*  91 */         if (datas == null) {
/*  92 */           result.setResult((byte)3);
/*  93 */           result.setErrorMessage("Ladders datas is empty");
/*     */         } else {
/*  95 */           result.setResult((byte)1);
/*  96 */           result.setSerializedReport(datas);
/*     */         }
/*     */       }
/*     */       else {
/* 100 */         result.setResult((byte)3);
/* 101 */         result.setErrorMessage("Resultset is Empty");
/*     */       }
/*     */     } catch (SQLException e) {
/* 104 */       result.setReportId(3L);
/* 105 */       result.setErrorMessage("Exception : " + e.toString());
/*     */     }
/*     */     
/* 108 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getPreferedChannel()
/*     */   {
/* 117 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/* 126 */     return hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onCheckOut()
/*     */   {
/* 133 */     this.m_sql = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onCheckIn()
/*     */   {
/* 140 */     this.m_sql = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\statistics\StatisticsReportLoadRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */