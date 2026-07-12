/*     */ package com.ankamagames.framework.kernel;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.framework.kernel.core.net.ConnectionHandler;
/*     */ import com.ankamagames.framework.kernel.core.net.ConnectionWriter;
/*     */ import com.ankamagames.framework.kernel.impl.AdminServerInstance;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ public final class ServerManager
/*     */ {
/*  24 */   private static final Logger m_logger = Logger.getLogger(ServerManager.class);
/*     */   
/*  26 */   private static final ServerManager m_instance = new ServerManager();
/*     */   private final ArrayList<ServerInstance> m_servers;
/*     */   private final ArrayList<ConnectorInstance> m_connectors;
/*     */   private final ArrayList<SqlDatabase> m_databases;
/*     */   
/*     */   private ServerManager() {
/*  32 */     this.m_servers = new ArrayList();
/*  33 */     this.m_connectors = new ArrayList();
/*  34 */     this.m_databases = new ArrayList();
/*  35 */     start();
/*     */   }
/*     */   
/*     */   public static ServerManager getInstance() {
/*  39 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void start()
/*     */   {
/*  49 */     Worker.getInstance().start();
/*  50 */     ConnectionWriter.getInstance().start();
/*  51 */     AdminServerInstance.getInstance().getConnectionHandler().setExternalName("AdminListener");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void startAdminListener(String bindAddress, int bindPort, String keyStoreFileName, String storeType, String alias, String password)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       AdminServerInstance.getInstance().initialize(keyStoreFileName, storeType, alias, password);
/*  79 */       AdminServerInstance.getInstance().start(bindAddress, bindPort);
/*     */     } catch (Exception e) {
/*  81 */       m_logger.error("Impossible d'initialiser l'administration du serveur : " + e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ServerInstance createServerInstance(String externalName)
/*     */   {
/*  91 */     ServerInstance listener = new ServerInstance(externalName);
/*  92 */     this.m_servers.add(listener);
/*     */     
/*  94 */     ConnectionHandler ch = listener.getConnectionHandler();
/*  95 */     ch.setExternalName(externalName);
/*     */     
/*  97 */     return listener;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ConnectorInstance createConnector(String externalName)
/*     */   {
/* 107 */     ConnectorInstance connector = new ConnectorInstance(externalName);
/* 108 */     this.m_connectors.add(connector);
/*     */     
/* 110 */     ConnectionHandler ch = connector.getConnectionHandler();
/* 111 */     ch.setExternalName(externalName);
/*     */     
/* 113 */     return connector;
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
/*     */   public SqlDatabase createSQLConnection(String dbName, String host, String username, String password, int nbChannels, String externalName)
/*     */   {
/* 127 */     SqlDatabase db = new SqlDatabase(dbName, host, username, password, nbChannels);
/* 128 */     db.setExternalName(externalName);
/* 129 */     db.initialize();
/* 130 */     this.m_databases.add(db);
/* 131 */     return db;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SqlDatabase getSQLConnection(String dbName)
/*     */   {
/* 140 */     for (SqlDatabase db : this.m_databases) {
/* 141 */       if (db.getDbName().equals(dbName))
/* 142 */         return db;
/*     */     }
/* 144 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<ServerInstance> getListeners()
/*     */   {
/* 152 */     return this.m_servers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<ConnectorInstance> getConnectors()
/*     */   {
/* 160 */     return this.m_connectors;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<SqlDatabase> getDatabases()
/*     */   {
/* 168 */     return this.m_databases;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\ServerManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */