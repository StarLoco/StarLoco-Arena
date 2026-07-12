/*     */ package com.ankamagames.framework.kernel;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.monitor.Monitored;
/*     */ import com.ankamagames.framework.kernel.core.sql.SqlRequest;
/*     */ import com.ankamagames.framework.kernel.core.sql.SqlRequestChannel;
/*     */ import com.ankamagames.framework.kernel.utils.ExceptionFormatter;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.postgresql.jdbc3.Jdbc3SimpleDataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SqlDatabase
/*     */   implements Monitored
/*     */ {
/*  23 */   protected static final Logger m_logger = Logger.getLogger(SqlDatabase.class);
/*     */   
/*     */ 
/*     */   protected final DataSource m_dataSource;
/*     */   
/*     */   protected final ArrayList<SqlRequestChannel> m_channels;
/*     */   
/*     */   protected int m_externalId;
/*     */   
/*     */   protected String m_externalName;
/*     */   
/*     */   protected final int m_nbChannels;
/*     */   
/*     */   private String m_dbName;
/*     */   
/*     */   private String m_dbHost;
/*     */   
/*     */   private String m_dbUserName;
/*     */   
/*     */   private String m_dbPassword;
/*     */   
/*     */ 
/*     */   public SqlDatabase(String dbName, String host, String username, String password, int nbChannels)
/*     */   {
/*  47 */     Jdbc3SimpleDataSource source = new Jdbc3SimpleDataSource();
/*     */     
/*  49 */     source.setDatabaseName(dbName);
/*     */     
/*     */ 
/*  52 */     source.setServerName(host);
/*  53 */     source.setUser(username);
/*  54 */     source.setPassword(password);
/*     */     
/*  56 */     this.m_dataSource = source;
/*  57 */     this.m_channels = new ArrayList();
/*  58 */     this.m_nbChannels = nbChannels;
/*     */     
/*  60 */     this.m_dbName = dbName;
/*  61 */     this.m_dbHost = host;
/*  62 */     this.m_dbUserName = username;
/*  63 */     this.m_dbPassword = password;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void initialize()
/*     */   {
/*  70 */     for (int i = 0; i < this.m_nbChannels; i++) {
/*  71 */       SqlRequestChannel channel = new SqlRequestChannel(this.m_dataSource);
/*     */       
/*  73 */       channel.setExternalName(this.m_externalName + "_" + i);
/*     */       
/*  75 */       this.m_channels.add(channel);
/*  76 */       channel.start();
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
/*     */   public boolean pushRequest(SqlRequest request)
/*     */   {
/*  89 */     SqlRequestChannel preferedChannel = null;
/*  90 */     int minChannelLoad = Integer.MAX_VALUE;
/*     */     
/*  92 */     int preferedChannelId = request.getPreferedChannel();
/*     */     
/*  94 */     if ((preferedChannelId < 0) || (this.m_channels.size() <= preferedChannelId)) {
/*  95 */       for (SqlRequestChannel channel : this.m_channels) {
/*  96 */         if (channel.getPendingRequestsCount() < minChannelLoad) {
/*  97 */           preferedChannel = channel;
/*  98 */           minChannelLoad = channel.getPendingRequestsCount();
/*     */         }
/*     */       }
/*     */     } else {
/* 102 */       preferedChannel = (SqlRequestChannel)this.m_channels.get(preferedChannelId);
/*     */     }
/*     */     
/* 105 */     if (preferedChannel != null) {
/* 106 */       preferedChannel.pushRequest(request);
/* 107 */       return true;
/*     */     }
/* 109 */     m_logger.error("Pas de cannal disponible pour poster la requête");
/* 110 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean pushRequest(SqlRequest request, int channelIndex)
/*     */   {
/*     */     try
/*     */     {
/* 122 */       SqlRequestChannel channel = (SqlRequestChannel)this.m_channels.get(channelIndex);
/* 123 */       channel.pushRequest(request);
/* 124 */       return true;
/*     */     } catch (Exception ex) {
/* 126 */       m_logger.error(ExceptionFormatter.toString(ex));
/*     */     }
/* 128 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getChannelsCount()
/*     */   {
/* 136 */     return this.m_channels.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getExternalName()
/*     */   {
/* 145 */     return this.m_externalName;
/*     */   }
/*     */   
/*     */   public void setExternalName(String name) {
/* 149 */     this.m_externalName = name;
/*     */   }
/*     */   
/*     */   public int getExternalID() {
/* 153 */     return this.m_externalId;
/*     */   }
/*     */   
/*     */   public void setExternalID(int id) {
/* 157 */     this.m_externalId = id;
/*     */   }
/*     */   
/*     */   public String getDbName() {
/* 161 */     return this.m_dbName;
/*     */   }
/*     */   
/*     */   public String getDbHost() {
/* 165 */     return this.m_dbHost;
/*     */   }
/*     */   
/*     */   public String getDbUserName() {
/* 169 */     return this.m_dbUserName;
/*     */   }
/*     */   
/*     */   public String getDbPassword() {
/* 173 */     return this.m_dbPassword;
/*     */   }
/*     */   
/*     */   public int[] getAllPendingRequests() {
/* 177 */     int size = this.m_channels.size();
/* 178 */     int[] pendingRequests = new int[size];
/* 179 */     for (int i = 0; i < size; i++) {
/* 180 */       pendingRequests[i] = ((SqlRequestChannel)this.m_channels.get(i)).getPendingRequestsCount();
/* 181 */       int[] arrayOfInt1; int j = (arrayOfInt1 = pendingRequests).length; for (int i = 0; i < j; i++) { int c = arrayOfInt1[i];
/* 182 */         System.err.print(c + " - "); }
/* 183 */       System.err.println("");
/*     */     }
/* 185 */     return pendingRequests;
/*     */   }
/*     */   
/*     */   public int getChannelPendingRequest(int channelIndex) {
/* 189 */     if ((channelIndex >= 0) && (channelIndex < this.m_channels.size() - 1)) {
/* 190 */       return ((SqlRequestChannel)this.m_channels.get(channelIndex)).getPendingRequestsCount();
/*     */     }
/* 192 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\SqlDatabase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */