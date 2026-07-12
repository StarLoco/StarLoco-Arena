/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.statistics;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.sql.SqlRequest;
/*     */ import com.ankamagames.framework.kernel.core.sql.SqlRequestChannel;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
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
/*     */ class StatisticsReportSaveRequest
/*     */   extends SqlRequest
/*     */ {
/*  26 */   private static final ObjectPool m_pool = new MonitoredPool(new ObjectFactory() {
/*  27 */     public StatisticsReportSaveRequest makeObject() { return new StatisticsReportSaveRequest(); }
/*  26 */   });
/*     */   private String m_sql;
/*     */   private boolean m_creationMode;
/*     */   private short m_modelId;
/*     */   private long m_reportId;
/*     */   private byte[] m_serializedReport;
/*     */   
/*     */   static StatisticsReportSaveRequest checkOut() { StatisticsReportSaveRequest msg;
/*  34 */     try { StatisticsReportSaveRequest msg = (StatisticsReportSaveRequest)m_pool.borrowObject();
/*  35 */       msg.setPool(m_pool);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  39 */       msg = new StatisticsReportSaveRequest();
/*  40 */       m_logger.error("Erreur lors d'un checkOut sur un message de type StatisticsReportSaveRequest : " + e.getMessage());
/*     */     }
/*  42 */     return msg;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setCreationMode(boolean creationMode)
/*     */   {
/*  55 */     this.m_creationMode = creationMode;
/*     */   }
/*     */   
/*     */   void setModelId(short modelId) {
/*  59 */     this.m_modelId = modelId;
/*     */   }
/*     */   
/*     */   void setReportId(long reportId) {
/*  63 */     this.m_reportId = reportId;
/*     */   }
/*     */   
/*     */   void setSerializedReport(byte[] serializedReport) {
/*  67 */     this.m_serializedReport = serializedReport;
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
/*  79 */     StatisticsReportRequestMessage result = StatisticsReportRequestMessage.checkOut();
/*  80 */     result.setModelId(this.m_modelId);
/*  81 */     result.setReportId(this.m_reportId);
/*     */     
/*     */     try
/*     */     {
/*  85 */       boolean bTempStatement = false;
/*     */       PreparedStatement stmt;
/*  87 */       if (this.m_creationMode) {
/*  88 */         this.m_sql = "INSERT INTO tbl_dynamic_statistics(statistics_model_id,statistics_report_id,statistics_report) VALUES(?,?,?);";
/*  89 */         PreparedStatement stmt = channel.getConnection().prepareStatement(this.m_sql);
/*  90 */         bTempStatement = true;
/*     */         
/*  92 */         stmt.setShort(1, this.m_modelId);
/*  93 */         stmt.setLong(2, this.m_reportId);
/*  94 */         stmt.setBytes(3, this.m_serializedReport);
/*     */       }
/*     */       else {
/*  97 */         this.m_sql = "UPDATE tbl_dynamic_statistics SET statistics_report=? WHERE statistics_model_id=? AND statistics_report_id=?;";
/*     */         
/*  99 */         stmt = channel.getStatement(getId());
/* 100 */         if (stmt == null) {
/* 101 */           stmt = channel.getConnection().prepareStatement(this.m_sql);
/* 102 */           channel.putStatement(getId(), stmt);
/*     */         }
/*     */         
/* 105 */         stmt.setBytes(1, this.m_serializedReport);
/* 106 */         stmt.setShort(2, this.m_modelId);
/* 107 */         stmt.setLong(3, this.m_reportId);
/*     */       }
/*     */       
/*     */ 
/* 111 */       boolean resultIsRS = stmt.execute();
/*     */       
/* 113 */       if (resultIsRS) {
/* 114 */         result.setResult((byte)4);
/* 115 */         result.setErrorMessage("Update result is not an update count");
/*     */       }
/*     */       else {
/* 118 */         int updateCount = stmt.getUpdateCount();
/* 119 */         if (updateCount != 1) {
/* 120 */           result.setResult((byte)4);
/* 121 */           result.setErrorMessage("Erreur lors de la sauvegarde : updateCount attendu=1, retourné=" + updateCount);
/*     */         } else {
/* 123 */           result.setResult((byte)2);
/*     */         }
/*     */       }
/* 126 */       if (bTempStatement) {
/* 127 */         stmt.close();
/*     */       }
/*     */     }
/*     */     catch (SQLException e) {
/* 131 */       result.setResult((byte)4);
/* 132 */       result.setErrorMessage("Exception : " + e.toString());
/*     */     }
/*     */     
/*     */ 
/* 136 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getPreferedChannel()
/*     */   {
/* 145 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/* 154 */     return hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onCheckOut()
/*     */   {
/* 161 */     this.m_creationMode = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onCheckIn()
/*     */   {
/* 168 */     this.m_creationMode = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\statistics\StatisticsReportSaveRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */