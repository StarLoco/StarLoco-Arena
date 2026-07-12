/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.statistics;
/*     */ 
/*     */ import com.ankamagames.framework.annotations.Nullable;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentEntry;
/*     */ import com.ankamagames.framework.fileFormat.xml.XMLDocumentAccessor;
/*     */ import com.ankamagames.framework.kernel.ServerManager;
/*     */ import com.ankamagames.framework.kernel.SqlDatabase;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.sql.SqlRequest;
/*     */ import com.ankamagames.framework.kernel.core.sql.SqlRequestRecipient;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ public final class StatisticsReportManager
/*     */   implements SqlRequestRecipient
/*     */ {
/*  29 */   private static final Logger m_logger = Logger.getLogger(StatisticsReportManager.class);
/*  30 */   private static final StatisticsReportManager m_instance = new StatisticsReportManager();
/*     */   
/*  32 */   private final HashMap<Short, AbstractStatisticsReport> m_models = new HashMap<Short, AbstractStatisticsReport>();
/*  33 */   private final HashMap<Short, HashMap<Long, AbstractStatisticsReport>> m_reports = new HashMap<Short, HashMap<Long, AbstractStatisticsReport>>();
/*     */ 
/*     */   
/*     */   private String m_reportDatabase;
/*     */ 
/*     */ 
/*     */   
/*     */   public static StatisticsReportManager getInstance() {
/*  41 */     return m_instance;
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
/*     */   
/*     */   public void setReportDatabase(String reportDatabase) {
/*  54 */     this.m_reportDatabase = reportDatabase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AbstractStatisticsReport getReport(short modelId, long reportId) {
/*  64 */     HashMap<Long, AbstractStatisticsReport> reports = this.m_reports.get(Short.valueOf(modelId));
/*  65 */     if (reports != null)
/*  66 */       return reports.get(Long.valueOf(reportId)); 
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReportCount(short modelId) {
/*  75 */     HashMap<Long, AbstractStatisticsReport> reports = this.m_reports.get(Short.valueOf(modelId));
/*  76 */     if (reports != null)
/*  77 */       return reports.size(); 
/*  78 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReport(short modelId, long reportId, AbstractStatisticsReport report) {
/*  87 */     HashMap<Long, AbstractStatisticsReport> reports = this.m_reports.get(Short.valueOf(modelId));
/*  88 */     if (reports == null) {
/*  89 */       reports = new HashMap<Long, AbstractStatisticsReport>();
/*  90 */       this.m_reports.put(Short.valueOf(modelId), reports);
/*     */     } 
/*  92 */     reports.put(Long.valueOf(reportId), report);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadReport(short modelId, long reportId, StatisticsReportEventsHandler eventsHandler) {
/* 102 */     AbstractStatisticsReport report = getReport(modelId, reportId);
/*     */     
/* 104 */     if (report == null) {
/* 105 */       report = createReport(modelId, reportId);
/*     */     }
/* 107 */     report.setEventsHandler(eventsHandler);
/*     */     
/* 109 */     StatisticsReportLoadRequest loadRequest = StatisticsReportLoadRequest.checkOut();
/* 110 */     loadRequest.setModelId(modelId);
/* 111 */     loadRequest.setReportId(reportId);
/* 112 */     loadRequest.setRecipient(this);
/*     */     
/* 114 */     SqlDatabase db = ServerManager.getInstance().getSQLConnection(this.m_reportDatabase);
/* 115 */     if (db != null) {
/* 116 */       db.pushRequest(loadRequest, loadRequest.getPreferedChannel());
/*     */     } else {
/* 118 */       m_logger.error("La connecteur à la base de donnée (" + this.m_reportDatabase + ") n'existe pas");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveReport(AbstractStatisticsReport report, StatisticsReportEventsHandler eventsHandler) {
/* 127 */     if (report != null) {
/* 128 */       report.setEventsHandler(eventsHandler);
/*     */       
/* 130 */       StatisticsReportSaveRequest saveRequest = StatisticsReportSaveRequest.checkOut();
/* 131 */       saveRequest.setModelId(report.getModelId());
/* 132 */       saveRequest.setReportId(report.getReportId());
/* 133 */       saveRequest.setRecipient(this);
/* 134 */       saveRequest.setCreationMode(false);
/* 135 */       saveRequest.setSerializedReport(report.serializeReport());
/*     */       
/* 137 */       SqlDatabase db = ServerManager.getInstance().getSQLConnection(this.m_reportDatabase);
/* 138 */       if (db != null) {
/* 139 */         db.pushRequest(saveRequest, saveRequest.getPreferedChannel());
/*     */       } else {
/* 141 */         m_logger.error("La connecteur à la base de donnée (" + this.m_reportDatabase + ") n'existe pas");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveReport(short modelId, long reportId, StatisticsReportEventsHandler eventsHandler) {
/* 151 */     AbstractStatisticsReport report = getReport(modelId, reportId);
/* 152 */     if (report != null) {
/* 153 */       saveReport(report, eventsHandler);
/*     */     } else {
/* 155 */       m_logger.error("Pas de sauvegarde possible des statistiques de ce joueur (id=" + reportId + ") - Pas de rapport");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createAndSaveReport(short modelId, long reportId, StatisticsReportEventsHandler eventsHandler) {
/* 165 */     AbstractStatisticsReport report = createReport(modelId, reportId);
/* 166 */     report.setEventsHandler(eventsHandler);
/*     */     
/* 168 */     StatisticsReportSaveRequest saveRequest = StatisticsReportSaveRequest.checkOut();
/* 169 */     saveRequest.setModelId(modelId);
/* 170 */     saveRequest.setReportId(reportId);
/* 171 */     saveRequest.setRecipient(this);
/* 172 */     saveRequest.setCreationMode(true);
/* 173 */     saveRequest.setSerializedReport(report.serializeReport());
/*     */     
/* 175 */     SqlDatabase db = ServerManager.getInstance().getSQLConnection(this.m_reportDatabase);
/* 176 */     if (db != null) {
/* 177 */       db.pushRequest(saveRequest, saveRequest.getPreferedChannel());
/*     */     } else {
/* 179 */       m_logger.error("La connecteur à la base de donnée (" + this.m_reportDatabase + ") n'existe pas");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractStatisticsReport createReport(short modelId, long reportId) {
/* 189 */     AbstractStatisticsReport report = getReport(modelId, reportId);
/* 190 */     if (report == null) {
/* 191 */       report = newReportInstance(modelId);
/* 192 */       if (report != null) {
/* 193 */         report.setModelId(modelId);
/* 194 */         report.setReportId(reportId);
/* 195 */         setReport(modelId, reportId, report);
/*     */       } 
/*     */     } 
/* 198 */     return report;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractStatisticsReport createReport(byte[] serializedReport) {
/* 206 */     short modelId = AbstractStatisticsReport.getModelIdOfSerializedReport(serializedReport);
/*     */     
/* 208 */     AbstractStatisticsReport report = newReportInstance(modelId);
/* 209 */     if (report != null) {
/* 210 */       report.unserializeReport(serializedReport);
/*     */     } else {
/* 212 */       m_logger.error("Impossible de créér une instance du rapport de statistiques, le modèle n'est pas reconnu : modelId=" + modelId);
/*     */     } 
/* 214 */     return report;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AbstractStatisticsReport newReportInstance(short modelId) {
/* 222 */     AbstractStatisticsReport model = this.m_models.get(Short.valueOf(modelId));
/* 223 */     if (model != null) {
/* 224 */       AbstractStatisticsReport report = model.newInstance();
/* 225 */       if (report != null) {
/* 226 */         report.initializeFrom(model);
/*     */       }
/* 228 */       return report;
/*     */     } 
/* 230 */     return null;
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
/*     */   public void loadModelsFromXMLFile(String XMLFileName) throws Exception {
/* 242 */     XMLDocumentAccessor xMLDocumentAccessor = XMLDocumentAccessor.getInstance();
/* 243 */     DocumentContainer document = xMLDocumentAccessor.getNewDocumentContainer();
/*     */     
/* 245 */     xMLDocumentAccessor.open(XMLFileName);
/* 246 */     xMLDocumentAccessor.read(document);
/* 247 */     xMLDocumentAccessor.close();
/*     */     
/* 249 */     loadModels(document);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadModels(DocumentContainer modelsDocument) {
/* 266 */     DocumentEntry root = modelsDocument.getEntryByName("reportModels");
/* 267 */     if (root != null) {
/* 268 */       ArrayList<DocumentEntry> models = root.getDirectChildrenByName("reportModel");
/* 269 */       if (models != null) {
/*     */         
/* 271 */         for (DocumentEntry model : models) {
/* 272 */           DocumentEntry idParam = model.getParameterByName("id");
/* 273 */           DocumentEntry classParam = model.getParameterByName("class");
/*     */           
/* 275 */           if (idParam != null && classParam != null) {
/*     */             
/* 277 */             AbstractStatisticsReport reportModel = instanceFromClass(classParam.getStringValue());
/* 278 */             if (reportModel != null) {
/*     */               
/* 280 */               ArrayList<DocumentEntry> reportEntries = model.getDirectChildrenByName("reportEntry");
/* 281 */               if (reportEntries != null) {
/* 282 */                 for (DocumentEntry reportEntry : reportEntries) {
/* 283 */                   DocumentEntry reportIdParam = reportEntry.getParameterByName("id");
/* 284 */                   DocumentEntry reportTypeIdParam = reportEntry.getParameterByName("typeId");
/* 285 */                   DocumentEntry reportDefaultValueParam = reportEntry.getParameterByName("default");
/*     */                   
/* 287 */                   String entryType = reportTypeIdParam.getStringValue().toLowerCase();
/* 288 */                   short entryId = (short)reportIdParam.getIntValue();
/*     */                   
/* 290 */                   if (entryType.equals("int")) {
/* 291 */                     reportModel.addReportEntry(entryId, reportDefaultValueParam.getIntValue()); continue;
/* 292 */                   }  if (entryType.equals("long")) {
/* 293 */                     reportModel.addReportEntry(entryId, reportDefaultValueParam.getLongValue()); continue;
/* 294 */                   }  if (entryType.equals("float")) {
/* 295 */                     reportModel.addReportEntry(entryId, reportDefaultValueParam.getFloatValue());
/*     */                   }
/*     */                 } 
/*     */                 
/* 299 */                 short modelId = (short)idParam.getIntValue();
/* 300 */                 reportModel.setModelId(modelId);
/* 301 */                 this.m_models.put(Short.valueOf(modelId), reportModel);
/*     */                 continue;
/*     */               } 
/* 304 */               m_logger.warn("Aucune entrée trouvée pour ce modèle de rapport : ID=" + idParam.getIntValue());
/*     */               
/*     */               continue;
/*     */             } 
/* 308 */             m_logger.error("Impossible d'instancier le modèle de rapport de statistiques");
/*     */             
/*     */             continue;
/*     */           } 
/* 312 */           m_logger.error("Erreur de formatage de rapport : paramètre 'id' ou 'class' introuvable");
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 317 */         m_logger.warn("Pas de model de rapport de statistique définit ( entrées 'reportModel' introuvalbles)");
/*     */       } 
/*     */     } else {
/* 320 */       m_logger.error("Mauvais format de document : racine 'reportModels' introuvable.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AbstractStatisticsReport instanceFromClass(String className) {
/* 331 */     AbstractStatisticsReport report = null;
/*     */     
/*     */     try {
/* 334 */       Class<?> c = Class.forName(className, true, ClassLoader.getSystemClassLoader());
/* 335 */       report = (AbstractStatisticsReport)c.newInstance();
/* 336 */     } catch (ClassNotFoundException e) {
/* 337 */       e.printStackTrace();
/* 338 */     } catch (InstantiationException e) {
/* 339 */       e.printStackTrace();
/* 340 */     } catch (IllegalAccessException e) {
/* 341 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 344 */     return report;
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
/*     */   
/*     */   public boolean onMessage(Message message) {
/* 357 */     if (message instanceof StatisticsReportRequestMessage)
/*     */     
/* 359 */     { StatisticsReportRequestMessage result = (StatisticsReportRequestMessage)message;
/* 360 */       AbstractStatisticsReport report = getReport(result.getModelId(), result.getReportId());
/* 361 */       StatisticsReportEventsHandler<AbstractStatisticsReport> handler = report.getEventsHandler();
/*     */       
/* 363 */       if (handler != null)
/* 364 */       { switch (result.getResult())
/*     */         
/*     */         { case 3:
/* 367 */             handler.onReportLoadError(report, result.getErrorMessage());
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
/* 394 */             return false;case 1: report.unserializeReport(result.getSerializedReport()); handler.onReportLoaded(report); return false;case 4: handler.onReportSaveError(report, result.getErrorMessage()); return false;case 2: handler.onReportSaved(report); return false; }  m_logger.warn("Code de resultat non traite (code=" + result.getResult() + ")"); }  }  return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onExceptionRaised(SqlRequest request, Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 412 */     return 1L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\statistics\StatisticsReportManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */