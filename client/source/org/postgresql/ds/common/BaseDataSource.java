/*     */ package org.postgresql.ds.common;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.Referenceable;
/*     */ import javax.naming.StringRefAddr;
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
/*     */ public abstract class BaseDataSource
/*     */   implements Referenceable
/*     */ {
/*     */   private transient PrintWriter logger;
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       Class.forName("org.postgresql.Driver");
/*     */     }
/*     */     catch (ClassNotFoundException e) {
/*     */       
/*  37 */       System.err.println("PostgreSQL DataSource unable to load PostgreSQL JDBC Driver");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private String serverName = "localhost";
/*     */ 
/*     */   
/*     */   private String databaseName;
/*     */ 
/*     */   
/*     */   private String user;
/*     */ 
/*     */   
/*     */   private String password;
/*     */   
/*     */   private int portNumber;
/*     */   
/*     */   private int prepareThreshold;
/*     */   
/*     */   private int loginTimeout;
/*     */ 
/*     */   
/*     */   public Connection getConnection() throws SQLException {
/*  65 */     return getConnection(this.user, this.password);
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
/*     */   public Connection getConnection(String user, String password) throws SQLException {
/*     */     try {
/*  82 */       Connection con = DriverManager.getConnection(getUrl(), user, password);
/*  83 */       if (this.logger != null)
/*     */       {
/*  85 */         this.logger.println("Created a non-pooled connection for " + user + " at " + getUrl());
/*     */       }
/*  87 */       return con;
/*     */     }
/*     */     catch (SQLException e) {
/*     */       
/*  91 */       if (this.logger != null)
/*     */       {
/*  93 */         this.logger.println("Failed to create a non-pooled connection for " + user + " at " + getUrl() + ": " + e);
/*     */       }
/*  95 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLoginTimeout() throws SQLException {
/* 104 */     return this.loginTimeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoginTimeout(int i) throws SQLException {
/* 112 */     this.loginTimeout = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrintWriter getLogWriter() throws SQLException {
/* 120 */     return this.logger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLogWriter(PrintWriter printWriter) throws SQLException {
/* 128 */     this.logger = printWriter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getServerName() {
/* 136 */     return this.serverName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServerName(String serverName) {
/* 146 */     if (serverName == null || serverName.equals("")) {
/*     */       
/* 148 */       this.serverName = "localhost";
/*     */     }
/*     */     else {
/*     */       
/* 152 */       this.serverName = serverName;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDatabaseName() {
/* 162 */     return this.databaseName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDatabaseName(String databaseName) {
/* 172 */     this.databaseName = databaseName;
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
/*     */   public String getUser() {
/* 187 */     return this.user;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUser(String user) {
/* 197 */     this.user = user;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPassword() {
/* 207 */     return this.password;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPassword(String password) {
/* 218 */     this.password = password;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPortNumber() {
/* 229 */     return this.portNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPortNumber(int portNumber) {
/* 239 */     this.portNumber = portNumber;
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
/*     */   public void setPrepareThreshold(int count) {
/* 251 */     this.prepareThreshold = count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrepareThreshold() {
/* 261 */     return this.prepareThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getUrl() {
/* 269 */     return "jdbc:postgresql://" + this.serverName + ((this.portNumber == 0) ? "" : (":" + this.portNumber)) + "/" + this.databaseName + "?loginTimeout=" + this.loginTimeout + "&prepareThreshold=" + this.prepareThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Reference createReference() {
/* 278 */     return new Reference(getClass().getName(), PGObjectFactory.class.getName(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference getReference() throws NamingException {
/* 286 */     Reference ref = createReference();
/* 287 */     ref.add(new StringRefAddr("serverName", this.serverName));
/* 288 */     if (this.portNumber != 0)
/*     */     {
/* 290 */       ref.add(new StringRefAddr("portNumber", Integer.toString(this.portNumber)));
/*     */     }
/* 292 */     ref.add(new StringRefAddr("databaseName", this.databaseName));
/* 293 */     if (this.user != null)
/*     */     {
/* 295 */       ref.add(new StringRefAddr("user", this.user));
/*     */     }
/* 297 */     if (this.password != null)
/*     */     {
/* 299 */       ref.add(new StringRefAddr("password", this.password));
/*     */     }
/*     */     
/* 302 */     ref.add(new StringRefAddr("prepareThreshold", Integer.toString(this.prepareThreshold)));
/* 303 */     ref.add(new StringRefAddr("loginTimeout", Integer.toString(this.loginTimeout)));
/* 304 */     return ref;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeBaseObject(ObjectOutputStream out) throws IOException {
/* 309 */     out.writeObject(this.serverName);
/* 310 */     out.writeObject(this.databaseName);
/* 311 */     out.writeObject(this.user);
/* 312 */     out.writeObject(this.password);
/* 313 */     out.writeInt(this.portNumber);
/* 314 */     out.writeInt(this.prepareThreshold);
/* 315 */     out.writeInt(this.loginTimeout);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readBaseObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 320 */     this.serverName = (String)in.readObject();
/* 321 */     this.databaseName = (String)in.readObject();
/* 322 */     this.user = (String)in.readObject();
/* 323 */     this.password = (String)in.readObject();
/* 324 */     this.portNumber = in.readInt();
/* 325 */     this.prepareThreshold = in.readInt();
/* 326 */     this.loginTimeout = in.readInt();
/*     */   }
/*     */   
/*     */   public abstract String getDescription();
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\ds\common\BaseDataSource.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */