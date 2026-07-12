/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.statistics;
/*     */ 
/*     */ import com.ankamagames.framework.annotations.Nullable;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map.Entry;
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
/*     */ public abstract class AbstractStatisticsReport
/*     */ {
/*     */   private long m_reportId;
/*     */   private short m_modelId;
/*     */   private StatisticsReportEventsHandler m_eventsHandler;
/*  24 */   private final HashMap<Short, StatisticsEntry> m_statistics = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final byte[] serializeReport()
/*     */   {
/*  32 */     int size = 0;
/*  33 */     for (Map.Entry<Short, StatisticsEntry> statistic : this.m_statistics.entrySet()) {
/*  34 */       size += 13 + ((StatisticsEntry)statistic.getValue()).getType().getTypeSize();
/*     */     }
/*  36 */     ByteBuffer buffer = ByteBuffer.allocate(2 + size);
/*     */     
/*  38 */     buffer.putShort(this.m_modelId);
/*  39 */     buffer.putLong(this.m_reportId);
/*     */     
/*  41 */     buffer.putShort((short)this.m_statistics.size());
/*     */     
/*  43 */     for (Object statistic : this.m_statistics.entrySet()) {
/*  44 */       StatisticsEntry entry = (StatisticsEntry)((Map.Entry)statistic).getValue();
/*     */       
/*  46 */       buffer.putShort(((Short)((Map.Entry)statistic).getKey()).shortValue());
/*  47 */       buffer.put(entry.getType().getTypeId());
/*     */       
/*  49 */       if (entry.getType() == StatisticType.TYPE_INT) {
/*  50 */         buffer.putInt(entry.getIntValue());
/*  51 */       } else if (entry.getType() == StatisticType.TYPE_LONG) {
/*  52 */         buffer.putLong(entry.getLongValue());
/*  53 */       } else if (entry.getType() == StatisticType.TYPE_FLOAT) {
/*  54 */         buffer.putFloat(entry.getFloatValue());
/*     */       }
/*     */     }
/*  57 */     return buffer.array();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void unserializeReport(byte[] serializedReport)
/*     */   {
/*  66 */     ByteBuffer buffer = ByteBuffer.wrap(serializedReport);
/*     */     
/*  68 */     this.m_modelId = buffer.getShort();
/*  69 */     this.m_reportId = buffer.getLong();
/*     */     
/*  71 */     int nbEntries = buffer.getShort();
/*     */     
/*  73 */     for (int i = 0; i < nbEntries; i++)
/*     */     {
/*  75 */       short id = buffer.getShort();
/*  76 */       byte type = buffer.get();
/*     */       
/*  78 */       if (type == StatisticType.TYPE_INT.getTypeId()) {
/*  79 */         this.m_statistics.put(Short.valueOf(id), new StatisticsEntry(buffer.getInt()));
/*  80 */       } else if (type == StatisticType.TYPE_LONG.getTypeId()) {
/*  81 */         this.m_statistics.put(Short.valueOf(id), new StatisticsEntry(buffer.getLong()));
/*  82 */       } else if (type == StatisticType.TYPE_FLOAT.getTypeId()) {
/*  83 */         this.m_statistics.put(Short.valueOf(id), new StatisticsEntry(buffer.getFloat()));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static short getModelIdOfSerializedReport(byte[] serializedReport)
/*     */   {
/*  94 */     ByteBuffer buffer = ByteBuffer.wrap(serializedReport);
/*  95 */     return buffer.getShort();
/*     */   }
/*     */   
/*     */   static long getReportIdOfSerializedReport(byte[] serializedReport) {
/*  99 */     ByteBuffer buffer = ByteBuffer.wrap(serializedReport);
/* 100 */     buffer.getShort();
/* 101 */     return buffer.getLong();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   final void initializeFrom(AbstractStatisticsReport modelReport)
/*     */   {
/* 111 */     this.m_modelId = modelReport.m_modelId;
/*     */     
/* 113 */     for (Map.Entry<Short, StatisticsEntry> entry : modelReport.m_statistics.entrySet()) {
/* 114 */       StatisticType type = ((StatisticsEntry)entry.getValue()).getType();
/* 115 */       if (type == StatisticType.TYPE_INT) {
/* 116 */         addReportEntry(((Short)entry.getKey()).shortValue(), ((StatisticsEntry)entry.getValue()).getIntValue());
/* 117 */       } else if (type == StatisticType.TYPE_LONG) {
/* 118 */         addReportEntry(((Short)entry.getKey()).shortValue(), ((StatisticsEntry)entry.getValue()).getLongValue());
/* 119 */       } else if (type == StatisticType.TYPE_FLOAT) {
/* 120 */         addReportEntry(((Short)entry.getKey()).shortValue(), ((StatisticsEntry)entry.getValue()).getFloatValue());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   final void addReportEntry(short id, int defaultValue)
/*     */   {
/* 130 */     this.m_statistics.put(Short.valueOf(id), new StatisticsEntry(defaultValue));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   final void addReportEntry(short id, long defaultValue)
/*     */   {
/* 139 */     this.m_statistics.put(Short.valueOf(id), new StatisticsEntry(defaultValue));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   final void addReportEntry(short id, float defaultValue)
/*     */   {
/* 148 */     this.m_statistics.put(Short.valueOf(id), new StatisticsEntry(defaultValue));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void setReportEntry(short id, int value)
/*     */   {
/* 157 */     StatisticsEntry entry = (StatisticsEntry)this.m_statistics.get(Short.valueOf(id));
/* 158 */     if (entry == null) {
/* 159 */       addReportEntry(id, value);
/*     */     } else {
/* 161 */       entry.setIntValue(value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void setReportEntry(short id, long value)
/*     */   {
/* 171 */     StatisticsEntry entry = (StatisticsEntry)this.m_statistics.get(Short.valueOf(id));
/* 172 */     if (entry == null) {
/* 173 */       addReportEntry(id, value);
/*     */     } else {
/* 175 */       entry.setLongValue(value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void setReportEntry(short id, float value)
/*     */   {
/* 185 */     StatisticsEntry entry = (StatisticsEntry)this.m_statistics.get(Short.valueOf(id));
/* 186 */     if (entry == null) {
/* 187 */       addReportEntry(id, value);
/*     */     } else {
/* 189 */       entry.setFloatValue(value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Nullable
/*     */   final StatisticType getReportEntryType(short id)
/*     */   {
/* 200 */     StatisticsEntry entry = (StatisticsEntry)this.m_statistics.get(Short.valueOf(id));
/* 201 */     if (entry != null)
/* 202 */       return entry.getType();
/* 203 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getReportEntryAsInt(short id)
/*     */   {
/* 212 */     int value = 0;
/* 213 */     StatisticsEntry entry = (StatisticsEntry)this.m_statistics.get(Short.valueOf(id));
/* 214 */     if (entry != null) {
/* 215 */       if (entry.getType() == StatisticType.TYPE_FLOAT) {
/* 216 */         value = Math.round(entry.getFloatValue());
/*     */       } else
/* 218 */         value = entry.getIntValue();
/*     */     }
/* 220 */     return value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getReportEntryAsLong(short id)
/*     */   {
/* 229 */     long value = 0L;
/* 230 */     StatisticsEntry entry = (StatisticsEntry)this.m_statistics.get(Short.valueOf(id));
/* 231 */     if (entry != null) {
/* 232 */       if (entry.getType() == StatisticType.TYPE_FLOAT) {
/* 233 */         value = Math.round(entry.getFloatValue());
/*     */       } else
/* 235 */         value = entry.getLongValue();
/*     */     }
/* 237 */     return value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getReportEntryAsFloat(short id)
/*     */   {
/* 246 */     float value = 0.0F;
/* 247 */     StatisticsEntry entry = (StatisticsEntry)this.m_statistics.get(Short.valueOf(id));
/* 248 */     if (entry != null) {
/* 249 */       if (entry.getType() != StatisticType.TYPE_FLOAT) {
/* 250 */         value = (float)entry.getLongValue();
/*     */       } else
/* 252 */         value = entry.getFloatValue();
/*     */     }
/* 254 */     return value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final short getModelId()
/*     */   {
/* 262 */     return this.m_modelId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   final void setModelId(short modelId)
/*     */   {
/* 270 */     this.m_modelId = modelId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final long getReportId()
/*     */   {
/* 279 */     return this.m_reportId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   final void setReportId(long reportId)
/*     */   {
/* 287 */     this.m_reportId = reportId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   final StatisticsReportEventsHandler getEventsHandler()
/*     */   {
/* 295 */     return this.m_eventsHandler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   final void setEventsHandler(StatisticsReportEventsHandler eventsHandler)
/*     */   {
/* 303 */     this.m_eventsHandler = eventsHandler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract AbstractStatisticsReport newInstance();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void initialize();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 325 */     StringBuffer buffer = new StringBuffer();
/*     */     
/* 327 */     buffer.append("Statistiques (type = ").append(this.m_modelId).append(", id = ").append(this.m_reportId).append("){\r\n");
/*     */     
/* 329 */     for (Map.Entry<Short, StatisticsEntry> entry : this.m_statistics.entrySet())
/* 330 */       buffer.append("\t\t").append(entry.getKey()).append(" = ").append(entry.getValue()).append("\r\n");
/* 331 */     buffer.append("}\r\n");
/*     */     
/* 333 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\statistics\AbstractStatisticsReport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */