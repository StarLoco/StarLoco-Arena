/*      */ package org.postgresql.jdbc2;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.DriverManager;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Statement;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import org.postgresql.Driver;
/*      */ import org.postgresql.PGNotification;
/*      */ import org.postgresql.core.BaseConnection;
/*      */ import org.postgresql.core.BaseStatement;
/*      */ import org.postgresql.core.ConnectionFactory;
/*      */ import org.postgresql.core.Encoding;
/*      */ import org.postgresql.core.Field;
/*      */ import org.postgresql.core.ProtocolConnection;
/*      */ import org.postgresql.core.Query;
/*      */ import org.postgresql.core.QueryExecutor;
/*      */ import org.postgresql.core.ResultCursor;
/*      */ import org.postgresql.core.ResultHandler;
/*      */ import org.postgresql.fastpath.Fastpath;
/*      */ import org.postgresql.geometric.PGbox;
/*      */ import org.postgresql.geometric.PGcircle;
/*      */ import org.postgresql.geometric.PGline;
/*      */ import org.postgresql.geometric.PGlseg;
/*      */ import org.postgresql.geometric.PGpath;
/*      */ import org.postgresql.geometric.PGpoint;
/*      */ import org.postgresql.geometric.PGpolygon;
/*      */ import org.postgresql.largeobject.LargeObjectManager;
/*      */ import org.postgresql.util.GT;
/*      */ import org.postgresql.util.PGInterval;
/*      */ import org.postgresql.util.PGmoney;
/*      */ import org.postgresql.util.PGobject;
/*      */ import org.postgresql.util.PSQLException;
/*      */ import org.postgresql.util.PSQLState;
/*      */ 
/*      */ public abstract class AbstractJdbc2Connection
/*      */   implements BaseConnection
/*      */ {
/*      */   private final String creatingURL;
/*      */   private final ProtocolConnection protoConnection;
/*      */   private final String compatible;
/*      */   private final String dbVersionNumber;
/*      */   private final Query commitQuery;
/*      */   private final Query rollbackQuery;
/*      */   private TypeInfoCache _typeCache;
/*      */   protected int prepareThreshold;
/*      */   public boolean autoCommit = true;
/*      */   public boolean readOnly = false;
/*   62 */   public SQLWarning firstWarning = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final TimestampUtils timestampUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Map typemap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Fastpath fastpath;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LargeObjectManager largeobject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DatabaseMetaData metadata;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimestampUtils getTimestampUtils() {
/*  140 */     return this.timestampUtils;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement() throws SQLException {
/*  150 */     return createStatement(1003, 1007);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql) throws SQLException {
/*  157 */     return prepareStatement(sql, 1003, 1007);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement prepareCall(String sql) throws SQLException {
/*  164 */     return prepareCall(sql, 1003, 1007);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map getTypeMap() throws SQLException {
/*  171 */     return this.typemap;
/*      */   }
/*      */ 
/*      */   
/*      */   public QueryExecutor getQueryExecutor() {
/*  176 */     return this.protoConnection.getQueryExecutor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addWarning(SQLWarning warn) {
/*  186 */     if (this.firstWarning != null) {
/*  187 */       this.firstWarning.setNextWarning(warn);
/*      */     } else {
/*  189 */       this.firstWarning = warn;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet execSQLQuery(String s) throws SQLException {
/*  197 */     BaseStatement stat = (BaseStatement)createStatement();
/*  198 */     boolean hasResultSet = stat.executeWithFlags(s, 16);
/*      */     
/*  200 */     while (!hasResultSet && stat.getUpdateCount() != -1) {
/*  201 */       hasResultSet = stat.getMoreResults();
/*      */     }
/*  203 */     if (!hasResultSet) {
/*  204 */       throw new PSQLException(GT.tr("No results were returned by the query."), PSQLState.NO_DATA);
/*      */     }
/*      */ 
/*      */     
/*  208 */     SQLWarning warnings = stat.getWarnings();
/*  209 */     if (warnings != null) {
/*  210 */       addWarning(warnings);
/*      */     }
/*  212 */     return stat.getResultSet();
/*      */   }
/*      */   
/*      */   public void execSQLUpdate(String s) throws SQLException {
/*  216 */     BaseStatement stmt = (BaseStatement)createStatement();
/*  217 */     if (stmt.executeWithFlags(s, 22)) {
/*  218 */       throw new PSQLException(GT.tr("A result was returned when none was expected."), PSQLState.TOO_MANY_RESULTS);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  223 */     SQLWarning warnings = stmt.getWarnings();
/*  224 */     if (warnings != null) {
/*  225 */       addWarning(warnings);
/*      */     }
/*  227 */     stmt.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCursorName(String cursor) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCursorName() throws SQLException {
/*  254 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getURL() throws SQLException {
/*  268 */     return this.creatingURL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUserName() throws SQLException {
/*  280 */     return this.protoConnection.getUser();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fastpath getFastpathAPI() throws SQLException {
/*  307 */     if (this.fastpath == null)
/*  308 */       this.fastpath = new Fastpath(this); 
/*  309 */     return this.fastpath;
/*      */   }
/*      */   
/*      */   protected AbstractJdbc2Connection(String host, int port, String user, String database, Properties info, String url) throws SQLException {
/*  313 */     this.fastpath = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  342 */     this.largeobject = null; this.creatingURL = url; int logLevel = 0; try {
/*      */       logLevel = Integer.parseInt(info.getProperty("loglevel", "0")); if (logLevel > 2 || logLevel < 1)
/*      */         logLevel = 0; 
/*      */     } catch (Exception l_e) {} if (logLevel > 0) {
/*      */       Driver.setLogLevel(logLevel); enableDriverManagerLogging();
/*      */     }  this.prepareThreshold = 5; try {
/*      */       this.prepareThreshold = Integer.parseInt(info.getProperty("prepareThreshold", "5")); if (this.prepareThreshold < 0)
/*      */         this.prepareThreshold = 0; 
/*      */     } catch (Exception e) {} if (Driver.logInfo)
/*      */       Driver.info(Driver.getVersion());  this.protoConnection = ConnectionFactory.openConnection(host, port, user, database, info); this.dbVersionNumber = this.protoConnection.getServerVersion(); this.compatible = info.getProperty("compatible", "8.1"); if (Driver.logDebug) {
/*      */       Driver.debug("    compatible = " + this.compatible);
/*      */       Driver.debug("    loglevel = " + logLevel);
/*      */       Driver.debug("    prepare threshold = " + this.prepareThreshold);
/*      */     } 
/*      */     this.timestampUtils = new TimestampUtils(haveMinimumServerVersion("7.4"));
/*      */     this.commitQuery = getQueryExecutor().createSimpleQuery("COMMIT");
/*      */     this.rollbackQuery = getQueryExecutor().createSimpleQuery("ROLLBACK");
/*      */     this._typeCache = new TypeInfoCache(this);
/*  360 */     initObjectTypes(info); } public Object getObject(String type, String value) throws SQLException { if (this.typemap != null) {
/*      */       
/*  362 */       SQLData d = (SQLData)this.typemap.get(type);
/*  363 */       if (d != null) {
/*      */ 
/*      */         
/*  366 */         if (Driver.logDebug)
/*  367 */           Driver.debug("getObject(String,String) with custom typemap"); 
/*  368 */         throw Driver.notImplemented(getClass(), "getObject(String,String)");
/*      */       } 
/*      */     } 
/*      */     
/*  372 */     PGobject obj = null;
/*      */     
/*  374 */     if (Driver.logDebug) {
/*  375 */       Driver.debug("Constructing object from type=" + type + " value=<" + value + ">");
/*      */     }
/*      */     
/*      */     try {
/*  379 */       Class klass = this._typeCache.getPGobject(type);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  387 */       if (klass != null) {
/*      */         
/*  389 */         obj = klass.newInstance();
/*  390 */         obj.setType(type);
/*  391 */         obj.setValue(value);
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  397 */         obj = new PGobject();
/*  398 */         obj.setType(type);
/*  399 */         obj.setValue(value);
/*      */       } 
/*      */       
/*  402 */       return obj;
/*      */     
/*      */     }
/*      */     catch (SQLException sx) {
/*      */       
/*  407 */       throw sx;
/*      */     }
/*      */     catch (Exception ex) {
/*      */       
/*  411 */       throw new PSQLException(GT.tr("Failed to create object for: {0}.", type), PSQLState.CONNECTION_FAILURE, ex);
/*      */     }  }
/*      */    public LargeObjectManager getLargeObjectAPI() throws SQLException {
/*      */     if (this.largeobject == null)
/*      */       this.largeobject = new LargeObjectManager(this); 
/*      */     return this.largeobject;
/*      */   } public void addDataType(String type, String name) {
/*      */     try {
/*  419 */       addDataType(type, Class.forName(name));
/*      */     }
/*      */     catch (Exception e) {
/*      */       
/*  423 */       throw new RuntimeException("Cannot register new type: " + e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addDataType(String type, Class klass) throws SQLException {
/*  429 */     this._typeCache.addDataType(type, klass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initObjectTypes(Properties info) throws SQLException {
/*  437 */     addDataType("box", PGbox.class);
/*  438 */     addDataType("circle", PGcircle.class);
/*  439 */     addDataType("line", PGline.class);
/*  440 */     addDataType("lseg", PGlseg.class);
/*  441 */     addDataType("path", PGpath.class);
/*  442 */     addDataType("point", PGpoint.class);
/*  443 */     addDataType("polygon", PGpolygon.class);
/*  444 */     addDataType("money", PGmoney.class);
/*  445 */     addDataType("interval", PGInterval.class);
/*      */     
/*  447 */     for (Enumeration e = info.propertyNames(); e.hasMoreElements(); ) {
/*      */       
/*  449 */       String propertyName = (String)e.nextElement();
/*  450 */       if (propertyName.startsWith("datatype.")) {
/*      */         Class klass;
/*  452 */         String typeName = propertyName.substring(9);
/*  453 */         String className = info.getProperty(propertyName);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  458 */           klass = Class.forName(className);
/*      */         }
/*      */         catch (ClassNotFoundException cnfe) {
/*      */           
/*  462 */           throw new PSQLException(GT.tr("Unable to load the class {0} responsible for the datatype {1}", new Object[] { className, typeName }), PSQLState.SYSTEM_ERROR, cnfe);
/*      */         } 
/*      */ 
/*      */         
/*  466 */         addDataType(typeName, klass);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() {
/*  484 */     this.protoConnection.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String nativeSQL(String sql) throws SQLException {
/*  499 */     StringBuffer buf = new StringBuffer(sql.length());
/*  500 */     AbstractJdbc2Statement.parseSql(sql, 0, buf, false);
/*  501 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized SQLWarning getWarnings() throws SQLException {
/*  517 */     SQLWarning newWarnings = this.protoConnection.getWarnings();
/*  518 */     if (this.firstWarning == null) {
/*  519 */       this.firstWarning = newWarnings;
/*      */     } else {
/*  521 */       this.firstWarning.setNextWarning(newWarnings);
/*      */     } 
/*  523 */     return this.firstWarning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void clearWarnings() throws SQLException {
/*  535 */     this.protoConnection.getWarnings();
/*  536 */     this.firstWarning = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReadOnly(boolean readOnly) throws SQLException {
/*  552 */     if (this.protoConnection.getTransactionState() != 0) {
/*  553 */       throw new PSQLException(GT.tr("Cannot change transaction read-only property in the middle of a transaction."), PSQLState.ACTIVE_SQL_TRANSACTION);
/*      */     }
/*      */     
/*  556 */     if (haveMinimumServerVersion("7.4") && readOnly != this.readOnly) {
/*      */       
/*  558 */       String readOnlySql = "SET SESSION CHARACTERISTICS AS TRANSACTION " + (readOnly ? "READ ONLY" : "READ WRITE");
/*  559 */       execSQLUpdate(readOnlySql);
/*      */     } 
/*      */     
/*  562 */     this.readOnly = readOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() throws SQLException {
/*  573 */     return this.readOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoCommit(boolean autoCommit) throws SQLException {
/*  596 */     if (this.autoCommit == autoCommit) {
/*      */       return;
/*      */     }
/*  599 */     if (!this.autoCommit) {
/*  600 */       commit();
/*      */     }
/*  602 */     this.autoCommit = autoCommit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAutoCommit() {
/*  613 */     return this.autoCommit;
/*      */   }
/*      */   
/*      */   private void executeTransactionCommand(Query query) throws SQLException {
/*  617 */     getQueryExecutor().execute(query, null, new TransactionCommandHandler(), 0, 0, 22);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void commit() throws SQLException {
/*  633 */     if (this.autoCommit) {
/*      */       return;
/*      */     }
/*  636 */     if (this.protoConnection.getTransactionState() != 0) {
/*  637 */       executeTransactionCommand(this.commitQuery);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rollback() throws SQLException {
/*  650 */     if (this.autoCommit) {
/*      */       return;
/*      */     }
/*  653 */     if (this.protoConnection.getTransactionState() != 0) {
/*  654 */       executeTransactionCommand(this.rollbackQuery);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTransactionIsolation() throws SQLException {
/*  665 */     String level = null;
/*      */     
/*  667 */     if (haveMinimumServerVersion("7.3")) {
/*      */ 
/*      */       
/*  670 */       ResultSet rs = execSQLQuery("SHOW TRANSACTION ISOLATION LEVEL");
/*  671 */       if (rs.next())
/*  672 */         level = rs.getString(1); 
/*  673 */       rs.close();
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/*  681 */       SQLWarning saveWarnings = getWarnings();
/*  682 */       clearWarnings();
/*      */ 
/*      */       
/*  685 */       execSQLUpdate("SHOW TRANSACTION ISOLATION LEVEL");
/*  686 */       SQLWarning warning = getWarnings();
/*  687 */       if (warning != null) {
/*  688 */         level = warning.getMessage();
/*      */       }
/*      */       
/*  691 */       clearWarnings();
/*  692 */       if (saveWarnings != null) {
/*  693 */         addWarning(saveWarnings);
/*      */       }
/*      */     } 
/*      */     
/*  697 */     if (level == null) {
/*  698 */       return 2;
/*      */     }
/*  700 */     level = level.toUpperCase();
/*  701 */     if (level.indexOf("READ COMMITTED") != -1)
/*  702 */       return 2; 
/*  703 */     if (level.indexOf("READ UNCOMMITTED") != -1)
/*  704 */       return 1; 
/*  705 */     if (level.indexOf("REPEATABLE READ") != -1)
/*  706 */       return 4; 
/*  707 */     if (level.indexOf("SERIALIZABLE") != -1) {
/*  708 */       return 8;
/*      */     }
/*  710 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransactionIsolation(int level) throws SQLException {
/*  728 */     if (this.protoConnection.getTransactionState() != 0) {
/*  729 */       throw new PSQLException(GT.tr("Cannot change transaction isolation level in the middle of a transaction."), PSQLState.ACTIVE_SQL_TRANSACTION);
/*      */     }
/*      */     
/*  732 */     String isolationLevelName = getIsolationLevelName(level);
/*  733 */     if (isolationLevelName == null) {
/*  734 */       throw new PSQLException(GT.tr("Transaction isolation level {0} not supported.", new Integer(level)), PSQLState.NOT_IMPLEMENTED);
/*      */     }
/*  736 */     String isolationLevelSQL = "SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL " + isolationLevelName;
/*  737 */     execSQLUpdate(isolationLevelSQL);
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getIsolationLevelName(int level) {
/*  742 */     boolean pg80 = haveMinimumServerVersion("8.0");
/*      */     
/*  744 */     if (level == 2)
/*      */     {
/*  746 */       return "READ COMMITTED";
/*      */     }
/*  748 */     if (level == 8)
/*      */     {
/*  750 */       return "SERIALIZABLE";
/*      */     }
/*  752 */     if (pg80 && level == 1)
/*      */     {
/*  754 */       return "READ UNCOMMITTED";
/*      */     }
/*  756 */     if (pg80 && level == 4)
/*      */     {
/*  758 */       return "REPEATABLE READ";
/*      */     }
/*      */     
/*  761 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCatalog(String catalog) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCatalog() throws SQLException {
/*  785 */     return this.protoConnection.getDatabase();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void finalize() throws Throwable {
/*  798 */     close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDBVersionNumber() {
/*  806 */     return this.dbVersionNumber;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int integerPart(String dirtyString) {
/*      */     int start;
/*  814 */     for (start = 0; start < dirtyString.length() && !Character.isDigit(dirtyString.charAt(start)); start++);
/*      */     
/*      */     int end;
/*  817 */     for (end = start; end < dirtyString.length() && Character.isDigit(dirtyString.charAt(end)); end++);
/*      */ 
/*      */     
/*  820 */     if (start == end) {
/*  821 */       return 0;
/*      */     }
/*  823 */     return Integer.parseInt(dirtyString.substring(start, end));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getServerMajorVersion() {
/*      */     try {
/*  833 */       StringTokenizer versionTokens = new StringTokenizer(this.dbVersionNumber, ".");
/*  834 */       return integerPart(versionTokens.nextToken());
/*      */     }
/*      */     catch (NoSuchElementException e) {
/*      */       
/*  838 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getServerMinorVersion() {
/*      */     try {
/*  849 */       StringTokenizer versionTokens = new StringTokenizer(this.dbVersionNumber, ".");
/*  850 */       versionTokens.nextToken();
/*  851 */       return integerPart(versionTokens.nextToken());
/*      */     }
/*      */     catch (NoSuchElementException e) {
/*      */       
/*  855 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean haveMinimumServerVersion(String ver) {
/*  866 */     return (this.dbVersionNumber.compareTo(ver) >= 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean haveMinimumCompatibleVersion(String ver) {
/*  883 */     return (this.compatible.compareTo(ver) >= 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public Encoding getEncoding() {
/*  888 */     return this.protoConnection.getEncoding();
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] encodeString(String str) throws SQLException {
/*      */     try {
/*  894 */       return getEncoding().encode(str);
/*      */     }
/*      */     catch (IOException ioe) {
/*      */       
/*  898 */       throw new PSQLException(GT.tr("Unable to translate data into the desired encoding."), PSQLState.DATA_ERROR, ioe);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSQLType(int oid) throws SQLException {
/*  911 */     return this._typeCache.getSQLType(oid);
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterator getPGTypeNamesWithSQLTypes() {
/*  916 */     return this._typeCache.getPGTypeNamesWithSQLTypes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPGType(String typeName) throws SQLException {
/*  926 */     return this._typeCache.getPGType(typeName);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getJavaClass(int oid) throws SQLException {
/*  931 */     return this._typeCache.getJavaClass(oid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPGType(int oid) throws SQLException {
/*  942 */     return this._typeCache.getPGType(oid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isClosed() throws SQLException {
/*  956 */     return this.protoConnection.isClosed();
/*      */   }
/*      */ 
/*      */   
/*      */   public void cancelQuery() throws SQLException {
/*  961 */     this.protoConnection.sendQueryCancel();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PGNotification[] getNotifications() throws SQLException {
/*  967 */     PGNotification[] notifications = this.protoConnection.getNotifications();
/*  968 */     return (notifications.length == 0) ? null : notifications;
/*      */   }
/*      */   private class TransactionCommandHandler implements ResultHandler { private SQLException error;
/*      */     private final AbstractJdbc2Connection this$0;
/*      */     
/*      */     private TransactionCommandHandler(AbstractJdbc2Connection this$0) {
/*  974 */       AbstractJdbc2Connection.this = AbstractJdbc2Connection.this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void handleResultRows(Query fromQuery, Field[] fields, Vector tuples, ResultCursor cursor) {}
/*      */     
/*      */     public void handleCommandStatus(String status, int updateCount, long insertOID) {}
/*      */     
/*      */     public void handleWarning(SQLWarning warning) {
/*  983 */       AbstractJdbc2Connection.this.addWarning(warning);
/*      */     }
/*      */     
/*      */     public void handleError(SQLException newError) {
/*  987 */       if (this.error == null) {
/*  988 */         this.error = newError;
/*      */       } else {
/*  990 */         this.error.setNextException(newError);
/*      */       } 
/*      */     }
/*      */     public void handleCompletion() throws SQLException {
/*  994 */       if (this.error != null)
/*  995 */         throw this.error; 
/*      */     } }
/*      */ 
/*      */   
/*      */   public int getPrepareThreshold() {
/* 1000 */     return this.prepareThreshold;
/*      */   }
/*      */   
/*      */   public void setPrepareThreshold(int newThreshold) {
/* 1004 */     this.prepareThreshold = (newThreshold <= 0) ? 0 : newThreshold;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTypeMapImpl(Map map) throws SQLException {
/* 1010 */     this.typemap = map;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void enableDriverManagerLogging() {
/* 1020 */     if (DriverManager.getLogWriter() == null)
/*      */     {
/* 1022 */       DriverManager.setLogWriter(new PrintWriter(System.out));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSQLType(String pgTypeName) {
/* 1029 */     return this._typeCache.getSQLType(pgTypeName);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getProtocolVersion() {
/* 1034 */     return this.protoConnection.getProtocolVersion();
/*      */   }
/*      */   
/*      */   public abstract DatabaseMetaData getMetaData() throws SQLException;
/*      */   
/*      */   public abstract Statement createStatement(int paramInt1, int paramInt2) throws SQLException;
/*      */   
/*      */   public abstract PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2) throws SQLException;
/*      */   
/*      */   public abstract CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2) throws SQLException;
/*      */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc2\AbstractJdbc2Connection.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */