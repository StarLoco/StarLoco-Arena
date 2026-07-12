/*      */ package org.postgresql.jdbc2;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Reader;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Date;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import org.postgresql.Driver;
/*      */ import org.postgresql.core.BaseConnection;
/*      */ import org.postgresql.core.Field;
/*      */ import org.postgresql.core.ParameterList;
/*      */ import org.postgresql.core.Query;
/*      */ import org.postgresql.core.ResultCursor;
/*      */ import org.postgresql.largeobject.LargeObject;
/*      */ import org.postgresql.largeobject.LargeObjectManager;
/*      */ import org.postgresql.util.GT;
/*      */ import org.postgresql.util.PGobject;
/*      */ import org.postgresql.util.PSQLException;
/*      */ import org.postgresql.util.PSQLState;
/*      */ 
/*      */ public abstract class AbstractJdbc2Statement implements BaseStatement {
/*   38 */   protected ArrayList batchStatements = null;
/*   39 */   protected ArrayList batchParameters = null;
/*      */   protected final int resultsettype;
/*      */   protected final int concurrency;
/*   42 */   protected int fetchdirection = 1000;
/*      */ 
/*      */   
/*      */   protected BaseConnection connection;
/*      */ 
/*      */   
/*   48 */   protected SQLWarning warnings = null;
/*      */ 
/*      */   
/*   51 */   protected int maxrows = 0;
/*      */ 
/*      */   
/*   54 */   protected int fetchSize = 0;
/*      */ 
/*      */   
/*   57 */   protected int timeout = 0;
/*      */ 
/*      */   
/*      */   protected boolean replaceProcessingEnabled = true;
/*      */   
/*   62 */   protected ResultWrapper result = null;
/*      */ 
/*      */   
/*   65 */   protected ResultWrapper firstUnclosedResult = null;
/*      */ 
/*      */   
/*      */   protected boolean adjustIndex = false;
/*      */ 
/*      */   
/*      */   protected boolean outParmBeforeFunc = false;
/*      */ 
/*      */   
/*      */   private static final short IN_SQLCODE = 0;
/*      */ 
/*      */   
/*      */   private static final short IN_STRING = 1;
/*      */ 
/*      */   
/*      */   private static final short IN_IDENTIFIER = 6;
/*      */   
/*      */   private static final short BACKSLASH = 2;
/*      */   
/*      */   private static final short ESC_TIMEDATE = 3;
/*      */   
/*      */   private static final short ESC_FUNCTION = 4;
/*      */   
/*      */   private static final short ESC_OUTERJOIN = 5;
/*      */   
/*      */   private static final short ESC_ESCAPECHAR = 7;
/*      */   
/*   92 */   private StringBuffer sbuf = new StringBuffer(35);
/*      */   
/*      */   protected final Query preparedQuery;
/*      */   
/*      */   protected final ParameterList preparedParameters;
/*      */   protected Query lastSimpleQuery;
/*      */   protected int m_prepareThreshold;
/*   99 */   protected int m_useCount = 0;
/*      */   
/*      */   private boolean isFunction;
/*      */   
/*      */   private int[] functionReturnType;
/*      */   
/*      */   private int[] testReturn;
/*      */   
/*      */   private boolean returnTypeSet;
/*      */   
/*      */   protected Object[] callResult;
/*      */   
/*  111 */   protected int maxfieldSize = 0;
/*      */   private boolean isClosed;
/*      */   private int lastIndex;
/*      */   
/*      */   public ResultSet createDriverResultSet(Field[] fields, Vector tuples) throws SQLException {
/*  116 */     return createResultSet(null, fields, tuples, null);
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
/*      */   public BaseConnection getPGConnection() {
/*  154 */     return this.connection;
/*      */   }
/*      */   
/*      */   public String getFetchingCursorName() {
/*  158 */     return null;
/*      */   }
/*      */   
/*      */   public int getFetchSize() {
/*  162 */     return this.fetchSize;
/*      */   }
/*      */   
/*      */   protected boolean wantsScrollableResultSet() {
/*  166 */     return (this.resultsettype != 1003);
/*      */   }
/*      */   
/*      */   protected boolean wantsHoldableResultSet() {
/*  170 */     return false;
/*      */   }
/*      */   public class StatementResultHandler implements ResultHandler { private SQLException error;
/*      */     private ResultWrapper results;
/*      */     private final AbstractJdbc2Statement this$0;
/*      */     
/*      */     public StatementResultHandler(AbstractJdbc2Statement this$0) {
/*  177 */       this.this$0 = this$0;
/*      */     }
/*      */ 
/*      */     
/*      */     ResultWrapper getResults() {
/*  182 */       return this.results;
/*      */     }
/*      */     
/*      */     private void append(ResultWrapper newResult) {
/*  186 */       if (this.results == null) {
/*  187 */         this.results = newResult;
/*      */       } else {
/*  189 */         this.results.append(newResult);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void handleResultRows(Query fromQuery, Field[] fields, Vector tuples, ResultCursor cursor) {
/*      */       try {
/*  195 */         ResultSet rs = this.this$0.createResultSet(fromQuery, fields, tuples, cursor);
/*  196 */         append(new ResultWrapper(rs));
/*      */       }
/*      */       catch (SQLException e) {
/*      */         
/*  200 */         handleError(e);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void handleCommandStatus(String status, int updateCount, long insertOID) {
/*  205 */       append(new ResultWrapper(updateCount, insertOID));
/*      */     }
/*      */     
/*      */     public void handleWarning(SQLWarning warning) {
/*  209 */       this.this$0.addWarning(warning);
/*      */     }
/*      */     
/*      */     public void handleError(SQLException newError) {
/*  213 */       if (this.error == null) {
/*  214 */         this.error = newError;
/*      */       } else {
/*  216 */         this.error.setNextException(newError);
/*      */       } 
/*      */     }
/*      */     public void handleCompletion() throws SQLException {
/*  220 */       if (this.error != null) {
/*  221 */         throw this.error;
/*      */       }
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet executeQuery(String p_sql) throws SQLException {
/*  234 */     if (this.preparedQuery != null) {
/*  235 */       throw new PSQLException(GT.tr("Can''t use query methods that take a query string on a PreparedStatement."), PSQLState.WRONG_OBJECT_TYPE);
/*      */     }
/*      */     
/*  238 */     if (!executeWithFlags(p_sql, 0)) {
/*  239 */       throw new PSQLException(GT.tr("No results were returned by the query."), PSQLState.NO_DATA);
/*      */     }
/*  241 */     if (this.result.getNext() != null) {
/*  242 */       throw new PSQLException(GT.tr("Multiple ResultSets were returned by the query."), PSQLState.TOO_MANY_RESULTS);
/*      */     }
/*      */     
/*  245 */     return this.result.getResultSet();
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
/*      */   public ResultSet executeQuery() throws SQLException {
/*  257 */     if (!executeWithFlags(0)) {
/*  258 */       throw new PSQLException(GT.tr("No results were returned by the query."), PSQLState.NO_DATA);
/*      */     }
/*  260 */     if (this.result.getNext() != null) {
/*  261 */       throw new PSQLException(GT.tr("Multiple ResultSets were returned by the query."), PSQLState.TOO_MANY_RESULTS);
/*      */     }
/*  263 */     return this.result.getResultSet();
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
/*      */   public int executeUpdate(String p_sql) throws SQLException {
/*  277 */     if (this.preparedQuery != null) {
/*  278 */       throw new PSQLException(GT.tr("Can''t use query methods that take a query string on a PreparedStatement."), PSQLState.WRONG_OBJECT_TYPE);
/*      */     }
/*  280 */     if (this.isFunction) {
/*      */       
/*  282 */       executeWithFlags(p_sql, 0);
/*  283 */       return 0;
/*      */     } 
/*  285 */     if (executeWithFlags(p_sql, 4)) {
/*  286 */       throw new PSQLException(GT.tr("A result was returned when none was expected."), PSQLState.TOO_MANY_RESULTS);
/*      */     }
/*  288 */     return getUpdateCount();
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
/*      */   public int executeUpdate() throws SQLException {
/*  302 */     if (this.isFunction) {
/*      */       
/*  304 */       executeWithFlags(0);
/*  305 */       return 0;
/*      */     } 
/*  307 */     if (executeWithFlags(4)) {
/*  308 */       throw new PSQLException(GT.tr("A result was returned when none was expected."), PSQLState.TOO_MANY_RESULTS);
/*      */     }
/*      */     
/*  311 */     return getUpdateCount();
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
/*      */   public boolean execute(String p_sql) throws SQLException {
/*  327 */     if (this.preparedQuery != null) {
/*  328 */       throw new PSQLException(GT.tr("Can''t use query methods that take a query string on a PreparedStatement."), PSQLState.WRONG_OBJECT_TYPE);
/*      */     }
/*      */     
/*  331 */     return executeWithFlags(p_sql, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean executeWithFlags(String p_sql, int flags) throws SQLException {
/*  336 */     checkClosed();
/*  337 */     p_sql = replaceProcessing(p_sql);
/*  338 */     Query simpleQuery = this.connection.getQueryExecutor().createSimpleQuery(p_sql);
/*  339 */     execute(simpleQuery, null, 0x1 | flags);
/*  340 */     this.lastSimpleQuery = simpleQuery;
/*  341 */     return (this.result != null && this.result.getResultSet() != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean execute() throws SQLException {
/*  346 */     return executeWithFlags(0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean executeWithFlags(int flags) throws SQLException {
/*  351 */     checkClosed();
/*      */     
/*  353 */     execute(this.preparedQuery, this.preparedParameters, flags);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  358 */     if (this.isFunction && this.returnTypeSet) {
/*      */       
/*  360 */       if (this.result == null || this.result.getResultSet() == null) {
/*  361 */         throw new PSQLException(GT.tr("A CallableStatement was executed with nothing returned."), PSQLState.NO_DATA);
/*      */       }
/*  363 */       ResultSet rs = this.result.getResultSet();
/*  364 */       if (!rs.next()) {
/*  365 */         throw new PSQLException(GT.tr("A CallableStatement was executed with nothing returned."), PSQLState.NO_DATA);
/*      */       }
/*      */       
/*  368 */       int cols = rs.getMetaData().getColumnCount();
/*  369 */       this.callResult = new Object[cols];
/*      */ 
/*      */       
/*  372 */       for (int i = 0; i < cols; i++) {
/*      */         
/*  374 */         this.callResult[i] = rs.getObject(i + 1);
/*  375 */         int columnType = rs.getMetaData().getColumnType(1);
/*  376 */         if (columnType != this.functionReturnType[i])
/*      */         {
/*      */           
/*  379 */           if (columnType == 8 && this.functionReturnType[i] == 7) {
/*      */ 
/*      */             
/*  382 */             if (this.callResult[i] != null) {
/*  383 */               this.callResult[i] = new Float(((Double)this.callResult[i]).floatValue());
/*      */             }
/*      */           } else {
/*      */             
/*  387 */             throw new PSQLException(GT.tr("A CallableStatement function was executed and the return was of type {0} however type {1} was registered.", new Object[] { "java.sql.Types=" + columnType, "java.sql.Types=" + this.functionReturnType[i] }), PSQLState.DATA_TYPE_MISMATCH);
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  395 */       rs.close();
/*  396 */       this.result = null;
/*  397 */       return false;
/*      */     } 
/*      */     
/*  400 */     return (this.result != null && this.result.getResultSet() != null);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void execute(Query queryToExecute, ParameterList queryParameters, int flags) throws SQLException {
/*  405 */     clearWarnings();
/*      */ 
/*      */     
/*  408 */     while (this.firstUnclosedResult != null) {
/*      */       
/*  410 */       if (this.firstUnclosedResult.getResultSet() != null)
/*  411 */         this.firstUnclosedResult.getResultSet().close(); 
/*  412 */       this.firstUnclosedResult = this.firstUnclosedResult.getNext();
/*      */     } 
/*      */     
/*  415 */     if (this.lastSimpleQuery != null) {
/*  416 */       this.lastSimpleQuery.close();
/*  417 */       this.lastSimpleQuery = null;
/*      */     } 
/*      */ 
/*      */     
/*  421 */     if (this.fetchSize > 0 && !wantsScrollableResultSet() && !this.connection.getAutoCommit() && !wantsHoldableResultSet()) {
/*  422 */       flags |= 0x8;
/*      */     }
/*      */     
/*  425 */     if (this.preparedQuery != null) {
/*      */       
/*  427 */       this.m_useCount++;
/*  428 */       if (this.m_prepareThreshold == 0 || this.m_useCount < this.m_prepareThreshold) {
/*  429 */         flags |= 0x1;
/*      */       }
/*      */     } 
/*  432 */     if (this.connection.getAutoCommit()) {
/*  433 */       flags |= 0x10;
/*      */     }
/*  435 */     StatementResultHandler handler = new StatementResultHandler(this);
/*  436 */     this.result = null;
/*  437 */     this.connection.getQueryExecutor().execute(queryToExecute, queryParameters, handler, this.maxrows, this.fetchSize, flags);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  443 */     this.result = this.firstUnclosedResult = handler.getResults();
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
/*      */   public void setCursorName(String name) throws SQLException {
/*  464 */     checkClosed();
/*      */   }
/*      */   
/*      */   public AbstractJdbc2Statement(AbstractJdbc2Connection c, int rsType, int rsConcurrency) throws SQLException {
/*  468 */     this.isClosed = false;
/*  469 */     this.lastIndex = 0; this.connection = c; this.preparedQuery = null; this.preparedParameters = null; this.lastSimpleQuery = null; this.resultsettype = rsType; this.concurrency = rsConcurrency; } public AbstractJdbc2Statement(AbstractJdbc2Connection connection, String sql, boolean isCallable, int rsType, int rsConcurrency) throws SQLException { this.isClosed = false; this.lastIndex = 0;
/*      */     this.connection = connection;
/*      */     this.lastSimpleQuery = null;
/*      */     String parsed_sql = replaceProcessing(sql);
/*      */     if (isCallable)
/*      */       parsed_sql = modifyJdbcCall(parsed_sql); 
/*      */     this.preparedQuery = connection.getQueryExecutor().createParameterizedQuery(parsed_sql);
/*      */     this.preparedParameters = this.preparedQuery.createParameterList();
/*      */     this.testReturn = new int[this.preparedParameters.getInParameterCount() + 1];
/*      */     this.functionReturnType = new int[this.preparedParameters.getInParameterCount() + 1];
/*      */     this.resultsettype = rsType;
/*  480 */     this.concurrency = rsConcurrency; } public int getUpdateCount() throws SQLException { checkClosed();
/*  481 */     if (this.result == null) {
/*  482 */       return -1;
/*      */     }
/*  484 */     if (this.isFunction) {
/*  485 */       return 1;
/*      */     }
/*  487 */     if (this.result.getResultSet() != null) {
/*  488 */       return -1;
/*      */     }
/*  490 */     return this.result.getUpdateCount(); }
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
/*      */   public boolean getMoreResults() throws SQLException {
/*  502 */     if (this.result == null) {
/*  503 */       return false;
/*      */     }
/*  505 */     this.result = this.result.getNext();
/*      */ 
/*      */     
/*  508 */     while (this.firstUnclosedResult != this.result) {
/*      */       
/*  510 */       if (this.firstUnclosedResult.getResultSet() != null)
/*  511 */         this.firstUnclosedResult.getResultSet().close(); 
/*  512 */       this.firstUnclosedResult = this.firstUnclosedResult.getNext();
/*      */     } 
/*      */     
/*  515 */     return (this.result != null && this.result.getResultSet() != null);
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
/*      */   public int getMaxRows() throws SQLException {
/*  528 */     checkClosed();
/*  529 */     return this.maxrows;
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
/*      */   public void setMaxRows(int max) throws SQLException {
/*  541 */     checkClosed();
/*  542 */     if (max < 0) {
/*  543 */       throw new PSQLException(GT.tr("Maximum number of rows must be a value grater than or equal to 0."), PSQLState.INVALID_PARAMETER_VALUE);
/*      */     }
/*  545 */     this.maxrows = max;
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
/*      */   public void setEscapeProcessing(boolean enable) throws SQLException {
/*  557 */     checkClosed();
/*  558 */     this.replaceProcessingEnabled = enable;
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
/*      */   public int getQueryTimeout() throws SQLException {
/*  571 */     checkClosed();
/*  572 */     return this.timeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQueryTimeout(int seconds) throws SQLException {
/*  583 */     checkClosed();
/*  584 */     if (seconds < 0) {
/*  585 */       throw new PSQLException(GT.tr("Query timeout must be a value greater than or equals to 0."), PSQLState.INVALID_PARAMETER_VALUE);
/*      */     }
/*  587 */     this.timeout = seconds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addWarning(SQLWarning warn) {
/*  596 */     if (this.warnings != null) {
/*  597 */       this.warnings.setNextWarning(warn);
/*      */     } else {
/*  599 */       this.warnings = warn;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SQLWarning getWarnings() throws SQLException {
/*  620 */     checkClosed();
/*  621 */     return this.warnings;
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
/*      */   public int getMaxFieldSize() throws SQLException {
/*  636 */     return this.maxfieldSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxFieldSize(int max) throws SQLException {
/*  647 */     checkClosed();
/*  648 */     if (max < 0) {
/*  649 */       throw new PSQLException(GT.tr("The maximum field size must be a value greater than or equal to 0."), PSQLState.INVALID_PARAMETER_VALUE);
/*      */     }
/*  651 */     this.maxfieldSize = max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearWarnings() throws SQLException {
/*  662 */     this.warnings = null;
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
/*      */   public ResultSet getResultSet() throws SQLException {
/*  674 */     checkClosed();
/*      */     
/*  676 */     if (this.result == null) {
/*  677 */       return null;
/*      */     }
/*  679 */     return this.result.getResultSet();
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
/*      */   public void close() throws SQLException {
/*  697 */     if (this.isClosed) {
/*      */       return;
/*      */     }
/*      */     
/*  701 */     while (this.firstUnclosedResult != null) {
/*      */       
/*  703 */       if (this.firstUnclosedResult.getResultSet() != null)
/*  704 */         this.firstUnclosedResult.getResultSet().close(); 
/*  705 */       this.firstUnclosedResult = this.firstUnclosedResult.getNext();
/*      */     } 
/*      */     
/*  708 */     if (this.lastSimpleQuery != null) {
/*  709 */       this.lastSimpleQuery.close();
/*      */     }
/*  711 */     if (this.preparedQuery != null) {
/*  712 */       this.preparedQuery.close();
/*      */     }
/*      */     
/*  715 */     this.result = this.firstUnclosedResult = null;
/*  716 */     this.isClosed = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalize() {
/*      */     try {
/*  726 */       close();
/*      */     
/*      */     }
/*  729 */     catch (SQLException e) {}
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
/*      */   protected String replaceProcessing(String p_sql) throws SQLException {
/*  746 */     if (this.replaceProcessingEnabled) {
/*      */ 
/*      */ 
/*      */       
/*  750 */       int len = p_sql.length();
/*  751 */       StringBuffer newsql = new StringBuffer(len);
/*  752 */       int i = 0;
/*  753 */       while (i < len) {
/*  754 */         i = parseSql(p_sql, i, newsql, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  760 */         if (i < len) {
/*  761 */           newsql.append(p_sql.charAt(i));
/*  762 */           i++;
/*      */         } 
/*      */       } 
/*  765 */       return newsql.toString();
/*      */     } 
/*      */ 
/*      */     
/*  769 */     return p_sql;
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
/*      */   protected static int parseSql(String p_sql, int i, StringBuffer newsql, boolean stopOnComma) throws SQLException {
/*  786 */     short state = 0;
/*  787 */     int len = p_sql.length();
/*  788 */     int nestedParenthesis = 0;
/*  789 */     boolean endOfNested = false;
/*      */ 
/*      */     
/*  792 */     i--;
/*  793 */     while (!endOfNested && ++i < len) {
/*      */       int posArgs;
/*  795 */       char c = p_sql.charAt(i);
/*  796 */       switch (state) {
/*      */         
/*      */         case 0:
/*  799 */           if (c == '\'') {
/*  800 */             state = 1;
/*  801 */           } else if (c == '"') {
/*  802 */             state = 6;
/*  803 */           } else if (c == '(') {
/*  804 */             nestedParenthesis++;
/*  805 */           } else if (c == ')') {
/*  806 */             nestedParenthesis--;
/*  807 */             if (nestedParenthesis < 0) {
/*  808 */               endOfNested = true; continue;
/*      */             } 
/*      */           } else {
/*  811 */             if (stopOnComma && c == ',' && nestedParenthesis == 0) {
/*  812 */               endOfNested = true; continue;
/*      */             } 
/*  814 */             if (c == '{' && 
/*  815 */               i + 1 < len) {
/*      */               
/*  817 */               char next = p_sql.charAt(i + 1);
/*  818 */               char nextnext = (i + 2 < len) ? p_sql.charAt(i + 2) : Character.MIN_VALUE;
/*  819 */               if (next == 'd' || next == 'D') {
/*      */                 
/*  821 */                 state = 3;
/*  822 */                 i++;
/*  823 */                 newsql.append("DATE ");
/*      */                 continue;
/*      */               } 
/*  826 */               if (next == 't' || next == 'T') {
/*      */                 
/*  828 */                 state = 3;
/*  829 */                 if (nextnext == 's' || nextnext == 'S') {
/*      */                   
/*  831 */                   i += 2;
/*  832 */                   newsql.append("TIMESTAMP ");
/*      */                   continue;
/*      */                 } 
/*  835 */                 i++;
/*  836 */                 newsql.append("TIME ");
/*      */                 
/*      */                 continue;
/*      */               } 
/*  840 */               if (next == 'f' || next == 'F') {
/*      */                 
/*  842 */                 state = 4;
/*  843 */                 i += (nextnext == 'n' || nextnext == 'N') ? 2 : 1;
/*      */                 continue;
/*      */               } 
/*  846 */               if (next == 'o' || next == 'O') {
/*      */                 
/*  848 */                 state = 5;
/*  849 */                 i += (nextnext == 'j' || nextnext == 'J') ? 2 : 1;
/*      */                 continue;
/*      */               } 
/*  852 */               if (next == 'e' || next == 'E') {
/*      */                 
/*  854 */                 state = 7;
/*      */                 continue;
/*      */               } 
/*      */             } 
/*      */           } 
/*  859 */           newsql.append(c);
/*      */ 
/*      */         
/*      */         case 1:
/*  863 */           if (c == '\'') {
/*  864 */             state = 0;
/*  865 */           } else if (c == '\\') {
/*  866 */             state = 2;
/*      */           } 
/*  868 */           newsql.append(c);
/*      */ 
/*      */         
/*      */         case 6:
/*  872 */           if (c == '"')
/*  873 */             state = 0; 
/*  874 */           newsql.append(c);
/*      */ 
/*      */         
/*      */         case 2:
/*  878 */           state = 1;
/*      */           
/*  880 */           newsql.append(c);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 4:
/*  886 */           posArgs = p_sql.indexOf('(', i);
/*  887 */           if (posArgs != -1) {
/*  888 */             String functionName = p_sql.substring(i, posArgs).trim();
/*      */             
/*  890 */             i = posArgs + 1;
/*  891 */             StringBuffer args = new StringBuffer();
/*  892 */             i = parseSql(p_sql, i, args, false);
/*      */             
/*  894 */             newsql.append(escapeFunction(functionName, args.toString()));
/*      */           } 
/*      */           
/*  897 */           i++;
/*  898 */           while (i < len && p_sql.charAt(i) != '}')
/*  899 */             newsql.append(p_sql.charAt(i)); 
/*  900 */           state = 0;
/*      */         
/*      */         case 3:
/*      */         case 5:
/*      */         case 7:
/*  905 */           if (c == '}') {
/*  906 */             state = 0; continue;
/*      */           } 
/*  908 */           newsql.append(c);
/*      */       } 
/*      */     
/*      */     } 
/*  912 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String escapeFunction(String functionName, String args) throws SQLException {
/*  923 */     int len = args.length();
/*  924 */     int i = 0;
/*  925 */     ArrayList parsedArgs = new ArrayList();
/*  926 */     while (i < len) {
/*  927 */       StringBuffer arg = new StringBuffer();
/*  928 */       int lastPos = i;
/*  929 */       i = parseSql(args, i, arg, true);
/*  930 */       if (lastPos != i) {
/*  931 */         parsedArgs.add(arg);
/*      */       }
/*  933 */       i++;
/*      */     } 
/*      */     
/*      */     try {
/*  937 */       Method escapeMethod = EscapedFunctions.getFunction(functionName);
/*  938 */       return (String)escapeMethod.invoke(null, new Object[] { parsedArgs });
/*      */     } catch (InvocationTargetException e) {
/*  940 */       if (e.getTargetException() instanceof SQLException) {
/*  941 */         throw (SQLException)e.getTargetException();
/*      */       }
/*  943 */       throw new PSQLException(e.getTargetException().getMessage(), PSQLState.SYSTEM_ERROR);
/*      */     }
/*      */     catch (Exception e) {
/*      */       
/*  947 */       StringBuffer buf = new StringBuffer();
/*  948 */       buf.append(functionName).append('(');
/*  949 */       for (int iArg = 0; iArg < parsedArgs.size(); iArg++) {
/*  950 */         buf.append(parsedArgs.get(iArg));
/*  951 */         if (iArg != parsedArgs.size() - 1)
/*  952 */           buf.append(','); 
/*      */       } 
/*  954 */       buf.append(')');
/*  955 */       return buf.toString();
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
/*      */   
/*      */   public int getInsertedOID() throws SQLException {
/*  973 */     checkClosed();
/*  974 */     if (this.result == null)
/*  975 */       return 0; 
/*  976 */     return (int)this.result.getInsertOID();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLastOID() throws SQLException {
/*  986 */     checkClosed();
/*  987 */     if (this.result == null)
/*  988 */       return 0L; 
/*  989 */     return this.result.getInsertOID();
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
/*      */   public void setNull(int parameterIndex, int sqlType) throws SQLException {
/*      */     int oid;
/* 1003 */     checkClosed();
/*      */ 
/*      */     
/* 1006 */     switch (sqlType) {
/*      */       
/*      */       case 4:
/* 1009 */         oid = 23;
/*      */         break;
/*      */       case -6:
/*      */       case 5:
/* 1013 */         oid = 21;
/*      */         break;
/*      */       case -5:
/* 1016 */         oid = 20;
/*      */         break;
/*      */       case 7:
/* 1019 */         oid = 700;
/*      */         break;
/*      */       case 6:
/*      */       case 8:
/* 1023 */         oid = 701;
/*      */         break;
/*      */       case 2:
/*      */       case 3:
/* 1027 */         oid = 1700;
/*      */         break;
/*      */       case 1:
/* 1030 */         oid = 1042;
/*      */         break;
/*      */       case -1:
/*      */       case 12:
/* 1034 */         oid = 1043;
/*      */         break;
/*      */       case 91:
/* 1037 */         oid = 1082;
/*      */         break;
/*      */       case 92:
/* 1040 */         oid = 1083;
/*      */         break;
/*      */       case 93:
/* 1043 */         oid = 1184;
/*      */         break;
/*      */       case -7:
/* 1046 */         oid = 16;
/*      */         break;
/*      */       case -4:
/*      */       case -3:
/*      */       case -2:
/* 1051 */         if (this.connection.haveMinimumCompatibleVersion("7.2")) {
/*      */           
/* 1053 */           oid = 17;
/*      */           
/*      */           break;
/*      */         } 
/* 1057 */         oid = 26;
/*      */         break;
/*      */       
/*      */       case 2004:
/*      */       case 2005:
/* 1062 */         oid = 26;
/*      */         break;
/*      */       case 0:
/*      */       case 1111:
/*      */       case 2001:
/*      */       case 2002:
/*      */       case 2003:
/* 1069 */         oid = 0;
/*      */         break;
/*      */       
/*      */       default:
/* 1073 */         throw new PSQLException(GT.tr("Unknown Types value."), PSQLState.INVALID_PARAMETER_TYPE);
/*      */     } 
/* 1075 */     if (this.adjustIndex)
/* 1076 */       parameterIndex--; 
/* 1077 */     this.preparedParameters.setNull(parameterIndex, oid);
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
/*      */   public void setBoolean(int parameterIndex, boolean x) throws SQLException {
/* 1090 */     checkClosed();
/* 1091 */     bindString(parameterIndex, x ? "1" : "0", 16);
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
/*      */   public void setByte(int parameterIndex, byte x) throws SQLException {
/* 1104 */     checkClosed();
/* 1105 */     bindLiteral(parameterIndex, Integer.toString(x), 21);
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
/*      */   public void setShort(int parameterIndex, short x) throws SQLException {
/* 1118 */     checkClosed();
/* 1119 */     bindLiteral(parameterIndex, Integer.toString(x), 21);
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
/*      */   public void setInt(int parameterIndex, int x) throws SQLException {
/* 1132 */     checkClosed();
/* 1133 */     bindLiteral(parameterIndex, Integer.toString(x), 23);
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
/*      */   public void setLong(int parameterIndex, long x) throws SQLException {
/* 1146 */     checkClosed();
/* 1147 */     bindLiteral(parameterIndex, Long.toString(x), 20);
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
/*      */   public void setFloat(int parameterIndex, float x) throws SQLException {
/* 1160 */     checkClosed();
/* 1161 */     bindLiteral(parameterIndex, Float.toString(x), 701);
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
/*      */   public void setDouble(int parameterIndex, double x) throws SQLException {
/* 1174 */     checkClosed();
/* 1175 */     bindLiteral(parameterIndex, Double.toString(x), 701);
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
/*      */   public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
/* 1189 */     checkClosed();
/* 1190 */     if (x == null) {
/* 1191 */       setNull(parameterIndex, 3);
/*      */     } else {
/* 1193 */       bindLiteral(parameterIndex, x.toString(), 1700);
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
/*      */   public void setString(int parameterIndex, String x) throws SQLException {
/* 1208 */     checkClosed();
/* 1209 */     setString(parameterIndex, x, 1043);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setString(int parameterIndex, String x, int oid) throws SQLException {
/* 1215 */     checkClosed();
/* 1216 */     if (x == null) {
/*      */       
/* 1218 */       if (this.adjustIndex)
/* 1219 */         parameterIndex--; 
/* 1220 */       this.preparedParameters.setNull(parameterIndex, oid);
/*      */     } else {
/*      */       
/* 1223 */       bindString(parameterIndex, x, oid);
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
/*      */ 
/*      */   
/*      */   public void setBytes(int parameterIndex, byte[] x) throws SQLException {
/* 1242 */     checkClosed();
/*      */     
/* 1244 */     if (null == x) {
/*      */       
/* 1246 */       setNull(parameterIndex, -3);
/*      */       
/*      */       return;
/*      */     } 
/* 1250 */     if (this.connection.haveMinimumCompatibleVersion("7.2")) {
/*      */ 
/*      */       
/* 1253 */       byte[] copy = new byte[x.length];
/* 1254 */       System.arraycopy(x, 0, copy, 0, x.length);
/* 1255 */       this.preparedParameters.setBytea(parameterIndex, copy, 0, x.length);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1260 */       LargeObjectManager lom = this.connection.getLargeObjectAPI();
/* 1261 */       int oid = lom.create();
/* 1262 */       LargeObject lob = lom.open(oid);
/* 1263 */       lob.write(x);
/* 1264 */       lob.close();
/* 1265 */       setInt(parameterIndex, oid);
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
/*      */   public void setDate(int parameterIndex, Date x) throws SQLException {
/* 1279 */     setDate(parameterIndex, x, null);
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
/*      */   public void setTime(int parameterIndex, Time x) throws SQLException {
/* 1292 */     setTime(parameterIndex, x, null);
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
/*      */   public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
/* 1305 */     setTimestamp(parameterIndex, x, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setCharacterStreamPost71(int parameterIndex, InputStream x, int length, String encoding) throws SQLException {
/* 1311 */     if (x == null) {
/*      */       
/* 1313 */       setNull(parameterIndex, 12);
/*      */       return;
/*      */     } 
/* 1316 */     if (length < 0) {
/* 1317 */       throw new PSQLException(GT.tr("Invalid stream length {0}.", new Integer(length)), PSQLState.INVALID_PARAMETER_VALUE);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1329 */       InputStreamReader l_inStream = new InputStreamReader(x, encoding);
/* 1330 */       char[] l_chars = new char[length];
/* 1331 */       int l_charsRead = 0;
/*      */       
/*      */       do {
/* 1334 */         int n = l_inStream.read(l_chars, l_charsRead, length - l_charsRead);
/* 1335 */         if (n == -1) {
/*      */           break;
/*      */         }
/* 1338 */         l_charsRead += n;
/*      */       }
/* 1340 */       while (l_charsRead != length);
/*      */ 
/*      */ 
/*      */       
/* 1344 */       setString(parameterIndex, new String(l_chars, 0, l_charsRead), 1043);
/*      */     }
/*      */     catch (UnsupportedEncodingException l_uee) {
/*      */       
/* 1348 */       throw new PSQLException(GT.tr("The JVM claims not to support the {0} encoding.", encoding), PSQLState.UNEXPECTED_ERROR, l_uee);
/*      */     }
/*      */     catch (IOException l_ioe) {
/*      */       
/* 1352 */       throw new PSQLException(GT.tr("Provided InputStream failed."), PSQLState.UNEXPECTED_ERROR, l_ioe);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 1374 */     checkClosed();
/* 1375 */     if (this.connection.haveMinimumCompatibleVersion("7.2")) {
/*      */       
/* 1377 */       setCharacterStreamPost71(parameterIndex, x, length, "ASCII");
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1383 */       setBinaryStream(parameterIndex, x, length);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 1404 */     checkClosed();
/* 1405 */     if (this.connection.haveMinimumCompatibleVersion("7.2")) {
/*      */       
/* 1407 */       setCharacterStreamPost71(parameterIndex, x, length, "UTF-8");
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1413 */       setBinaryStream(parameterIndex, x, length);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 1433 */     checkClosed();
/*      */     
/* 1435 */     if (x == null) {
/*      */       
/* 1437 */       setNull(parameterIndex, -3);
/*      */       
/*      */       return;
/*      */     } 
/* 1441 */     if (length < 0) {
/* 1442 */       throw new PSQLException(GT.tr("Invalid stream length {0}.", new Integer(length)), PSQLState.INVALID_PARAMETER_VALUE);
/*      */     }
/*      */     
/* 1445 */     if (this.connection.haveMinimumCompatibleVersion("7.2")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1453 */       this.preparedParameters.setBytea(parameterIndex, x, length);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1460 */       LargeObjectManager lom = this.connection.getLargeObjectAPI();
/* 1461 */       int oid = lom.create();
/* 1462 */       LargeObject lob = lom.open(oid);
/* 1463 */       OutputStream los = lob.getOutputStream();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1469 */         int c = x.read();
/* 1470 */         int p = 0;
/* 1471 */         while (c > -1 && p < length) {
/*      */           
/* 1473 */           los.write(c);
/* 1474 */           c = x.read();
/* 1475 */           p++;
/*      */         } 
/* 1477 */         los.close();
/*      */       }
/*      */       catch (IOException se) {
/*      */         
/* 1481 */         throw new PSQLException(GT.tr("Provided InputStream failed."), PSQLState.UNEXPECTED_ERROR, se);
/*      */       } 
/*      */       
/* 1484 */       setInt(parameterIndex, oid);
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
/*      */   public void clearParameters() throws SQLException {
/* 1500 */     this.preparedParameters.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   private PGType createInternalType(Object x, int targetType) throws PSQLException {
/* 1505 */     if (x instanceof Byte) return PGByte.castToServerType((Byte)x, targetType); 
/* 1506 */     if (x instanceof Short) return PGShort.castToServerType((Short)x, targetType); 
/* 1507 */     if (x instanceof Integer) return PGInteger.castToServerType((Integer)x, targetType); 
/* 1508 */     if (x instanceof Long) return PGLong.castToServerType((Long)x, targetType); 
/* 1509 */     if (x instanceof Double) return PGDouble.castToServerType((Double)x, targetType); 
/* 1510 */     if (x instanceof Float) return PGFloat.castToServerType((Float)x, targetType); 
/* 1511 */     if (x instanceof BigDecimal) return PGBigDecimal.castToServerType((BigDecimal)x, targetType);
/*      */     
/* 1513 */     if (x instanceof Number) return PGNumber.castToServerType((Number)x, targetType); 
/* 1514 */     if (x instanceof Boolean) return PGBoolean.castToServerType((Boolean)x, targetType); 
/* 1515 */     return (PGType)new PGUnknown(x);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setPGobject(int parameterIndex, PGobject x) throws SQLException {
/* 1520 */     String typename = x.getType();
/* 1521 */     int oid = this.connection.getPGType(typename);
/* 1522 */     if (oid == 0) {
/* 1523 */       throw new PSQLException(GT.tr("Unknown type {0}.", typename), PSQLState.INVALID_PARAMETER_TYPE);
/*      */     }
/* 1525 */     setString(parameterIndex, x.getValue(), oid);
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
/*      */   public void setObject(int parameterIndex, Object in, int targetSqlType, int scale) throws SQLException {
/* 1549 */     checkClosed();
/*      */     
/* 1551 */     if (in == null) {
/*      */       
/* 1553 */       setNull(parameterIndex, targetSqlType);
/*      */       
/*      */       return;
/*      */     } 
/* 1557 */     Object pgType = createInternalType(in, targetSqlType);
/* 1558 */     switch (targetSqlType) {
/*      */       
/*      */       case 4:
/* 1561 */         bindLiteral(parameterIndex, pgType.toString(), 23);
/*      */         return;
/*      */       case -6:
/*      */       case 5:
/* 1565 */         bindLiteral(parameterIndex, pgType.toString(), 21);
/*      */         return;
/*      */       case -5:
/* 1568 */         bindLiteral(parameterIndex, pgType.toString(), 20);
/*      */         return;
/*      */ 
/*      */       
/*      */       case 7:
/* 1573 */         bindLiteral(parameterIndex, pgType.toString(), 700);
/*      */         return;
/*      */       case 6:
/*      */       case 8:
/* 1577 */         bindLiteral(parameterIndex, pgType.toString(), 701);
/*      */         return;
/*      */       case 2:
/*      */       case 3:
/* 1581 */         bindLiteral(parameterIndex, pgType.toString(), 1700);
/*      */         return;
/*      */       case 1:
/* 1584 */         setString(parameterIndex, pgType.toString(), 1042);
/*      */         return;
/*      */       case -1:
/*      */       case 12:
/* 1588 */         setString(parameterIndex, pgType.toString());
/*      */         return;
/*      */       case 91:
/* 1591 */         if (in instanceof Date) {
/* 1592 */           setDate(parameterIndex, (Date)in);
/*      */         } else {
/*      */           Date date;
/*      */           
/* 1596 */           if (in instanceof Date) {
/* 1597 */             date = new Date(((Date)in).getTime());
/*      */           } else {
/* 1599 */             date = this.connection.getTimestampUtils().toDate(null, in.toString());
/*      */           } 
/* 1601 */           setDate(parameterIndex, date);
/*      */         } 
/*      */         return;
/*      */       case 92:
/* 1605 */         if (in instanceof Time) {
/* 1606 */           setTime(parameterIndex, (Time)in);
/*      */         } else {
/*      */           Time time;
/*      */           
/* 1610 */           if (in instanceof Date) {
/* 1611 */             time = new Time(((Date)in).getTime());
/*      */           } else {
/* 1613 */             time = this.connection.getTimestampUtils().toTime(null, in.toString());
/*      */           } 
/* 1615 */           setTime(parameterIndex, time);
/*      */         } 
/*      */         return;
/*      */       case 93:
/* 1619 */         if (in instanceof Timestamp) {
/* 1620 */           setTimestamp(parameterIndex, (Timestamp)in);
/*      */         } else {
/*      */           Timestamp timestamp;
/*      */           
/* 1624 */           if (in instanceof Date) {
/* 1625 */             timestamp = new Timestamp(((Date)in).getTime());
/*      */           } else {
/* 1627 */             timestamp = this.connection.getTimestampUtils().toTimestamp(null, in.toString());
/*      */           } 
/* 1629 */           setTimestamp(parameterIndex, timestamp);
/*      */         } 
/*      */         return;
/*      */       case -7:
/* 1633 */         bindLiteral(parameterIndex, pgType.toString(), 16);
/*      */         return;
/*      */       case -4:
/*      */       case -3:
/*      */       case -2:
/* 1638 */         setObject(parameterIndex, in);
/*      */         return;
/*      */       case 2004:
/* 1641 */         if (in instanceof Blob) {
/* 1642 */           setBlob(parameterIndex, (Blob)in);
/*      */         } else {
/* 1644 */           throw new PSQLException(GT.tr("Cannot cast an instance of {0} to type {1}", new Object[] { in.getClass().getName(), "Types.BLOB" }), PSQLState.INVALID_PARAMETER_TYPE);
/*      */         }  return;
/*      */       case 2005:
/* 1647 */         if (in instanceof Clob) {
/* 1648 */           setClob(parameterIndex, (Clob)in);
/*      */         } else {
/* 1650 */           throw new PSQLException(GT.tr("Cannot cast an instance of {0} to type {1}", new Object[] { in.getClass().getName(), "Types.CLOB" }), PSQLState.INVALID_PARAMETER_TYPE);
/*      */         }  return;
/*      */       case 2003:
/* 1653 */         if (in instanceof Array) {
/* 1654 */           setArray(parameterIndex, (Array)in);
/*      */         } else {
/* 1656 */           throw new PSQLException(GT.tr("Cannot cast an instance of {0} to type {1}", new Object[] { in.getClass().getName(), "Types.ARRAY" }), PSQLState.INVALID_PARAMETER_TYPE);
/*      */         }  return;
/*      */       case 1111:
/* 1659 */         if (in instanceof PGobject) {
/* 1660 */           setPGobject(parameterIndex, (PGobject)in);
/*      */         } else {
/* 1662 */           throw new PSQLException(GT.tr("Cannot cast an instance of {0} to type {1}", new Object[] { in.getClass().getName(), "Types.OTHER" }), PSQLState.INVALID_PARAMETER_TYPE);
/*      */         }  return;
/*      */     } 
/* 1665 */     throw new PSQLException(GT.tr("Unsupported Types value: {0}", new Integer(targetSqlType)), PSQLState.INVALID_PARAMETER_TYPE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
/* 1671 */     setObject(parameterIndex, x, targetSqlType, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObject(int parameterIndex, Object x) throws SQLException {
/* 1679 */     checkClosed();
/* 1680 */     if (x == null) {
/* 1681 */       setNull(parameterIndex, 1111);
/* 1682 */     } else if (x instanceof String) {
/* 1683 */       setString(parameterIndex, (String)x);
/* 1684 */     } else if (x instanceof BigDecimal) {
/* 1685 */       setBigDecimal(parameterIndex, (BigDecimal)x);
/* 1686 */     } else if (x instanceof Short) {
/* 1687 */       setShort(parameterIndex, ((Short)x).shortValue());
/* 1688 */     } else if (x instanceof Integer) {
/* 1689 */       setInt(parameterIndex, ((Integer)x).intValue());
/* 1690 */     } else if (x instanceof Long) {
/* 1691 */       setLong(parameterIndex, ((Long)x).longValue());
/* 1692 */     } else if (x instanceof Float) {
/* 1693 */       setFloat(parameterIndex, ((Float)x).floatValue());
/* 1694 */     } else if (x instanceof Double) {
/* 1695 */       setDouble(parameterIndex, ((Double)x).doubleValue());
/* 1696 */     } else if (x instanceof byte[]) {
/* 1697 */       setBytes(parameterIndex, (byte[])x);
/* 1698 */     } else if (x instanceof Date) {
/* 1699 */       setDate(parameterIndex, (Date)x);
/* 1700 */     } else if (x instanceof Time) {
/* 1701 */       setTime(parameterIndex, (Time)x);
/* 1702 */     } else if (x instanceof Timestamp) {
/* 1703 */       setTimestamp(parameterIndex, (Timestamp)x);
/* 1704 */     } else if (x instanceof Boolean) {
/* 1705 */       setBoolean(parameterIndex, ((Boolean)x).booleanValue());
/* 1706 */     } else if (x instanceof Blob) {
/* 1707 */       setBlob(parameterIndex, (Blob)x);
/* 1708 */     } else if (x instanceof Clob) {
/* 1709 */       setClob(parameterIndex, (Clob)x);
/* 1710 */     } else if (x instanceof Array) {
/* 1711 */       setArray(parameterIndex, (Array)x);
/* 1712 */     } else if (x instanceof PGobject) {
/* 1713 */       setPGobject(parameterIndex, (PGobject)x);
/*      */     }
/*      */     else {
/*      */       
/* 1717 */       throw new PSQLException(GT.tr("Can''t infer the SQL type to use for an instance of {0}. Use setObject() with an explicit Types value to specify the type to use.", x.getClass().getName()), PSQLState.INVALID_PARAMETER_TYPE);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerOutParameter(int parameterIndex, int sqlType, boolean setPreparedParameters) throws SQLException {
/* 1740 */     checkClosed();
/* 1741 */     switch (sqlType) {
/*      */ 
/*      */       
/*      */       case -6:
/* 1745 */         sqlType = 5;
/*      */         break;
/*      */       case -1:
/* 1748 */         sqlType = 12;
/*      */         break;
/*      */       case 3:
/* 1751 */         sqlType = 2;
/*      */         break;
/*      */       
/*      */       case 6:
/* 1755 */         sqlType = 8;
/*      */         break;
/*      */       case -4:
/*      */       case -3:
/* 1759 */         sqlType = -2;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 1764 */     if (!this.isFunction)
/* 1765 */       throw new PSQLException(GT.tr("This statement does not declare an OUT parameter.  Use '{' ?= call ... '}' to declare one."), PSQLState.STATEMENT_NOT_ALLOWED_IN_FUNCTION_CALL); 
/* 1766 */     checkIndex(parameterIndex);
/*      */     
/* 1768 */     if (setPreparedParameters) {
/* 1769 */       this.preparedParameters.registerOutParameter(parameterIndex, sqlType);
/*      */     }
/*      */ 
/*      */     
/* 1773 */     this.functionReturnType[parameterIndex - 1] = sqlType;
/* 1774 */     this.testReturn[parameterIndex - 1] = sqlType;
/*      */     
/* 1776 */     if (this.functionReturnType[parameterIndex - 1] == 1 || this.functionReturnType[parameterIndex - 1] == -1) {
/*      */       
/* 1778 */       this.testReturn[parameterIndex - 1] = 12;
/* 1779 */     } else if (this.functionReturnType[parameterIndex - 1] == 6) {
/* 1780 */       this.testReturn[parameterIndex - 1] = 7;
/* 1781 */     }  this.returnTypeSet = true;
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
/*      */   public void registerOutParameter(int parameterIndex, int sqlType, int scale, boolean setPreparedParameters) throws SQLException {
/* 1800 */     registerOutParameter(parameterIndex, sqlType, setPreparedParameters);
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
/*      */   public boolean wasNull() throws SQLException {
/* 1815 */     return (this.callResult[this.lastIndex - 1] == null);
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
/*      */   public String getString(int parameterIndex) throws SQLException {
/* 1828 */     checkClosed();
/* 1829 */     checkIndex(parameterIndex, 12, "String");
/* 1830 */     return (String)this.callResult[parameterIndex - 1];
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
/*      */   public boolean getBoolean(int parameterIndex) throws SQLException {
/* 1843 */     checkClosed();
/* 1844 */     checkIndex(parameterIndex, -7, "Boolean");
/* 1845 */     if (this.callResult[parameterIndex - 1] == null) {
/* 1846 */       return false;
/*      */     }
/* 1848 */     return ((Boolean)this.callResult[parameterIndex - 1]).booleanValue();
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
/*      */   public byte getByte(int parameterIndex) throws SQLException {
/* 1860 */     checkClosed();
/*      */     
/* 1862 */     checkIndex(parameterIndex, 5, "Byte");
/*      */     
/* 1864 */     if (this.callResult[parameterIndex - 1] == null) {
/* 1865 */       return 0;
/*      */     }
/* 1867 */     return ((Integer)this.callResult[parameterIndex - 1]).byteValue();
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
/*      */   public short getShort(int parameterIndex) throws SQLException {
/* 1880 */     checkClosed();
/* 1881 */     checkIndex(parameterIndex, 5, "Short");
/* 1882 */     if (this.callResult[parameterIndex - 1] == null)
/* 1883 */       return 0; 
/* 1884 */     return ((Integer)this.callResult[parameterIndex - 1]).shortValue();
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
/*      */   public int getInt(int parameterIndex) throws SQLException {
/* 1897 */     checkClosed();
/* 1898 */     checkIndex(parameterIndex, 4, "Int");
/* 1899 */     if (this.callResult[parameterIndex - 1] == null) {
/* 1900 */       return 0;
/*      */     }
/* 1902 */     return ((Integer)this.callResult[parameterIndex - 1]).intValue();
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
/*      */   public long getLong(int parameterIndex) throws SQLException {
/* 1914 */     checkClosed();
/* 1915 */     checkIndex(parameterIndex, -5, "Long");
/* 1916 */     if (this.callResult[parameterIndex - 1] == null) {
/* 1917 */       return 0L;
/*      */     }
/* 1919 */     return ((Long)this.callResult[parameterIndex - 1]).longValue();
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
/*      */   public float getFloat(int parameterIndex) throws SQLException {
/* 1931 */     checkClosed();
/* 1932 */     checkIndex(parameterIndex, 7, "Float");
/* 1933 */     if (this.callResult[parameterIndex - 1] == null) {
/* 1934 */       return 0.0F;
/*      */     }
/* 1936 */     return ((Float)this.callResult[parameterIndex - 1]).floatValue();
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
/*      */   public double getDouble(int parameterIndex) throws SQLException {
/* 1948 */     checkClosed();
/* 1949 */     checkIndex(parameterIndex, 8, "Double");
/* 1950 */     if (this.callResult[parameterIndex - 1] == null) {
/* 1951 */       return 0.0D;
/*      */     }
/* 1953 */     return ((Double)this.callResult[parameterIndex - 1]).doubleValue();
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
/*      */   public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
/* 1970 */     checkClosed();
/* 1971 */     checkIndex(parameterIndex, 2, "BigDecimal");
/* 1972 */     return (BigDecimal)this.callResult[parameterIndex - 1];
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
/*      */   public byte[] getBytes(int parameterIndex) throws SQLException {
/* 1985 */     checkClosed();
/* 1986 */     checkIndex(parameterIndex, -3, -2, "Bytes");
/* 1987 */     return (byte[])this.callResult[parameterIndex - 1];
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
/*      */   public Date getDate(int parameterIndex) throws SQLException {
/* 2000 */     checkClosed();
/* 2001 */     checkIndex(parameterIndex, 91, "Date");
/* 2002 */     return (Date)this.callResult[parameterIndex - 1];
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
/*      */   public Time getTime(int parameterIndex) throws SQLException {
/* 2014 */     checkClosed();
/* 2015 */     checkIndex(parameterIndex, 92, "Time");
/* 2016 */     return (Time)this.callResult[parameterIndex - 1];
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
/*      */   public Timestamp getTimestamp(int parameterIndex) throws SQLException {
/* 2029 */     checkClosed();
/* 2030 */     checkIndex(parameterIndex, 93, "Timestamp");
/* 2031 */     return (Timestamp)this.callResult[parameterIndex - 1];
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
/*      */   public Object getObject(int parameterIndex) throws SQLException {
/* 2057 */     checkClosed();
/* 2058 */     checkIndex(parameterIndex);
/* 2059 */     return this.callResult[parameterIndex - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2068 */     if (this.preparedQuery == null) {
/* 2069 */       return super.toString();
/*      */     }
/* 2071 */     return this.preparedQuery.toString(this.preparedParameters);
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
/*      */   private void bindLiteral(int paramIndex, String s, int oid) throws SQLException {
/* 2083 */     if (this.adjustIndex)
/* 2084 */       paramIndex--; 
/* 2085 */     this.preparedParameters.setLiteralParameter(paramIndex, s, oid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void bindString(int paramIndex, String s, int oid) throws SQLException {
/* 2095 */     if (this.adjustIndex)
/* 2096 */       paramIndex--; 
/* 2097 */     this.preparedParameters.setStringParameter(paramIndex, s, oid);
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
/*      */   private String modifyJdbcCall(String p_sql) throws SQLException {
/* 2109 */     checkClosed();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2115 */     this.isFunction = false;
/*      */     
/* 2117 */     int len = p_sql.length();
/* 2118 */     int state = 1;
/* 2119 */     boolean inQuotes = false, inEscape = false;
/* 2120 */     this.outParmBeforeFunc = false;
/* 2121 */     int startIndex = -1, endIndex = -1;
/* 2122 */     boolean syntaxError = false;
/* 2123 */     int i = 0;
/*      */     
/* 2125 */     while (i < len && !syntaxError) {
/*      */       
/* 2127 */       char ch = p_sql.charAt(i);
/*      */       
/* 2129 */       switch (state) {
/*      */         
/*      */         case 1:
/* 2132 */           if (ch == '{') {
/*      */             
/* 2134 */             i++;
/* 2135 */             state++; continue;
/*      */           } 
/* 2137 */           if (Character.isWhitespace(ch)) {
/*      */             
/* 2139 */             i++;
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 2144 */           i = len;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 2:
/* 2149 */           if (ch == '?') {
/*      */             
/* 2151 */             this.outParmBeforeFunc = this.isFunction = true;
/* 2152 */             i++;
/* 2153 */             state++; continue;
/*      */           } 
/* 2155 */           if (ch == 'c') {
/*      */             
/* 2157 */             state += 3; continue;
/*      */           } 
/* 2159 */           if (Character.isWhitespace(ch)) {
/*      */             
/* 2161 */             i++;
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 2166 */           syntaxError = true;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 3:
/* 2171 */           if (ch == '=') {
/*      */             
/* 2173 */             i++;
/* 2174 */             state++; continue;
/*      */           } 
/* 2176 */           if (Character.isWhitespace(ch)) {
/*      */             
/* 2178 */             i++;
/*      */             
/*      */             continue;
/*      */           } 
/* 2182 */           syntaxError = true;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 4:
/* 2187 */           if (ch == 'c' || ch == 'C') {
/*      */             
/* 2189 */             state++; continue;
/*      */           } 
/* 2191 */           if (Character.isWhitespace(ch)) {
/*      */             
/* 2193 */             i++;
/*      */             
/*      */             continue;
/*      */           } 
/* 2197 */           syntaxError = true;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 5:
/* 2202 */           if ((ch == 'c' || ch == 'C') && i + 4 <= len && p_sql.substring(i, i + 4).equalsIgnoreCase("call")) {
/*      */             
/* 2204 */             this.isFunction = true;
/* 2205 */             i += 4;
/* 2206 */             state++; continue;
/*      */           } 
/* 2208 */           if (Character.isWhitespace(ch)) {
/*      */             
/* 2210 */             i++;
/*      */             
/*      */             continue;
/*      */           } 
/* 2214 */           syntaxError = true;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 6:
/* 2219 */           if (Character.isWhitespace(ch)) {
/*      */ 
/*      */             
/* 2222 */             i++;
/* 2223 */             state++;
/* 2224 */             startIndex = i;
/*      */             
/*      */             continue;
/*      */           } 
/* 2228 */           syntaxError = true;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 7:
/* 2233 */           if (ch == '\'') {
/*      */             
/* 2235 */             inQuotes = !inQuotes;
/* 2236 */             i++; continue;
/*      */           } 
/* 2238 */           if (inQuotes && ch == '\\') {
/*      */ 
/*      */             
/* 2241 */             i += 2; continue;
/*      */           } 
/* 2243 */           if (!inQuotes && ch == '{') {
/*      */             
/* 2245 */             inEscape = !inEscape;
/* 2246 */             i++; continue;
/*      */           } 
/* 2248 */           if (!inQuotes && ch == '}') {
/*      */             
/* 2250 */             if (!inEscape) {
/*      */ 
/*      */               
/* 2253 */               endIndex = i;
/* 2254 */               i++;
/* 2255 */               state++;
/*      */               
/*      */               continue;
/*      */             } 
/* 2259 */             inEscape = false;
/*      */             continue;
/*      */           } 
/* 2262 */           if (!inQuotes && ch == ';') {
/*      */             
/* 2264 */             syntaxError = true;
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 2269 */           i++;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 8:
/* 2274 */           if (Character.isWhitespace(ch)) {
/*      */             
/* 2276 */             i++;
/*      */             
/*      */             continue;
/*      */           } 
/* 2280 */           syntaxError = true;
/*      */           continue;
/*      */       } 
/*      */ 
/*      */       
/* 2285 */       throw new IllegalStateException("somehow got into bad state " + state);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2290 */     if (i == len && !syntaxError) {
/*      */       
/* 2292 */       if (state == 1)
/* 2293 */         return p_sql; 
/* 2294 */       if (state != 8) {
/* 2295 */         syntaxError = true;
/*      */       }
/*      */     } 
/* 2298 */     if (syntaxError) {
/* 2299 */       throw new PSQLException(GT.tr("Malformed function or procedure escape syntax at offset {0}.", new Integer(i)), PSQLState.STATEMENT_NOT_ALLOWED_IN_FUNCTION_CALL);
/*      */     }
/*      */     
/* 2302 */     if (this.connection.haveMinimumServerVersion("8.1")) {
/*      */       
/* 2304 */       String s = p_sql.substring(startIndex, endIndex);
/* 2305 */       StringBuffer sb = new StringBuffer(s);
/* 2306 */       if (this.outParmBeforeFunc) {
/*      */ 
/*      */ 
/*      */         
/* 2310 */         boolean needComma = false;
/*      */ 
/*      */         
/* 2313 */         int opening = s.indexOf('(') + 1;
/* 2314 */         int closing = s.indexOf(')');
/* 2315 */         for (int j = opening; j < closing; j++) {
/*      */           
/* 2317 */           if (!Character.isWhitespace(sb.charAt(j))) {
/*      */             
/* 2319 */             needComma = true;
/*      */             break;
/*      */           } 
/*      */         } 
/* 2323 */         if (needComma) {
/*      */           
/* 2325 */           sb.insert(opening, "?,");
/*      */         }
/*      */         else {
/*      */           
/* 2329 */           sb.insert(opening, "?");
/*      */         } 
/*      */       } 
/*      */       
/* 2333 */       return "select * from " + sb.toString() + " as result";
/*      */     } 
/*      */ 
/*      */     
/* 2337 */     return "select " + p_sql.substring(startIndex, endIndex) + " as result";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkIndex(int parameterIndex, int type1, int type2, String getName) throws SQLException {
/* 2347 */     checkIndex(parameterIndex);
/* 2348 */     if (type1 != this.testReturn[parameterIndex - 1] && type2 != this.testReturn[parameterIndex - 1]) {
/* 2349 */       throw new PSQLException(GT.tr("Parameter of type {0} was registered, but call to get{1} (sqltype={2}) was made.", new Object[] { "java.sql.Types=" + this.testReturn, getName, "java.sql.Types=" + type1 }), PSQLState.MOST_SPECIFIC_TYPE_DOES_NOT_MATCH);
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
/*      */   protected void checkIndex(int parameterIndex, int type, String getName) throws SQLException {
/* 2361 */     checkIndex(parameterIndex);
/* 2362 */     if (type != this.testReturn[parameterIndex - 1]) {
/* 2363 */       throw new PSQLException(GT.tr("Parameter of type {0} was registered, but call to get{1} (sqltype={2}) was made.", new Object[] { "java.sql.Types=" + this.testReturn[parameterIndex - 1], getName, "java.sql.Types=" + type }), PSQLState.MOST_SPECIFIC_TYPE_DOES_NOT_MATCH);
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
/*      */   private void checkIndex(int parameterIndex) throws SQLException {
/* 2376 */     if (!this.isFunction)
/* 2377 */       throw new PSQLException(GT.tr("A CallableStatement was declared, but no call to registerOutParameter(1, <some type>) was made."), PSQLState.STATEMENT_NOT_ALLOWED_IN_FUNCTION_CALL); 
/* 2378 */     this.lastIndex = parameterIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPrepareThreshold(int newThreshold) throws SQLException {
/* 2386 */     checkClosed();
/*      */     
/* 2388 */     if (newThreshold < 0) {
/* 2389 */       newThreshold = 0;
/*      */     }
/* 2391 */     this.m_prepareThreshold = newThreshold;
/*      */   }
/*      */   
/*      */   public int getPrepareThreshold() {
/* 2395 */     return this.m_prepareThreshold;
/*      */   }
/*      */   
/*      */   public void setUseServerPrepare(boolean flag) throws SQLException {
/* 2399 */     setPrepareThreshold(flag ? 1 : 0);
/*      */   }
/*      */   
/*      */   public boolean isUseServerPrepare() {
/* 2403 */     return (this.preparedQuery != null && this.m_prepareThreshold != 0 && this.m_useCount + 1 >= this.m_prepareThreshold);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkClosed() throws SQLException {
/* 2408 */     if (this.isClosed) {
/* 2409 */       throw new PSQLException(GT.tr("This statement has been closed."), PSQLState.OBJECT_NOT_IN_STATE);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addBatch(String p_sql) throws SQLException {
/* 2417 */     checkClosed();
/*      */     
/* 2419 */     if (this.preparedQuery != null) {
/* 2420 */       throw new PSQLException(GT.tr("Can''t use query methods that take a query string on a PreparedStatement."), PSQLState.WRONG_OBJECT_TYPE);
/*      */     }
/*      */     
/* 2423 */     if (this.batchStatements == null) {
/*      */       
/* 2425 */       this.batchStatements = new ArrayList();
/* 2426 */       this.batchParameters = new ArrayList();
/*      */     } 
/*      */     
/* 2429 */     this.batchStatements.add(this.connection.getQueryExecutor().createSimpleQuery(p_sql));
/* 2430 */     this.batchParameters.add(null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearBatch() throws SQLException {
/* 2435 */     if (this.batchStatements != null) {
/*      */       
/* 2437 */       this.batchStatements.clear();
/* 2438 */       this.batchParameters.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private class BatchResultHandler
/*      */     implements ResultHandler
/*      */   {
/*      */     private BatchUpdateException batchException;
/*      */     private int resultIndex;
/*      */     private final Query[] queries;
/*      */     private final ParameterList[] parameterLists;
/*      */     private final int[] updateCounts;
/*      */     private final AbstractJdbc2Statement this$0;
/*      */     
/*      */     BatchResultHandler(AbstractJdbc2Statement this$0, Query[] queries, ParameterList[] parameterLists, int[] updateCounts) {
/* 2454 */       this.this$0 = this$0; this.batchException = null; this.resultIndex = 0;
/* 2455 */       this.queries = queries;
/* 2456 */       this.parameterLists = parameterLists;
/* 2457 */       this.updateCounts = updateCounts;
/*      */     }
/*      */     
/*      */     public void handleResultRows(Query fromQuery, Field[] fields, Vector tuples, ResultCursor cursor) {
/* 2461 */       handleError((SQLException)new PSQLException(GT.tr("A result was returned when none was expected."), PSQLState.TOO_MANY_RESULTS));
/*      */     }
/*      */ 
/*      */     
/*      */     public void handleCommandStatus(String status, int updateCount, long insertOID) {
/* 2466 */       if (this.resultIndex >= this.updateCounts.length) {
/*      */         
/* 2468 */         handleError((SQLException)new PSQLException(GT.tr("Too many update results were returned."), PSQLState.TOO_MANY_RESULTS));
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 2473 */       this.updateCounts[this.resultIndex++] = updateCount;
/*      */     }
/*      */     
/*      */     public void handleWarning(SQLWarning warning) {
/* 2477 */       this.this$0.addWarning(warning);
/*      */     }
/*      */     
/*      */     public void handleError(SQLException newError) {
/* 2481 */       if (this.batchException == null) {
/*      */         int[] arrayOfInt;
/*      */ 
/*      */         
/* 2485 */         if (this.resultIndex >= this.updateCounts.length) {
/* 2486 */           arrayOfInt = this.updateCounts;
/*      */         } else {
/*      */           
/* 2489 */           arrayOfInt = new int[this.resultIndex];
/* 2490 */           System.arraycopy(this.updateCounts, 0, arrayOfInt, 0, this.resultIndex);
/*      */         } 
/*      */         
/* 2493 */         String queryString = "<unknown>";
/* 2494 */         if (this.resultIndex < this.queries.length) {
/* 2495 */           queryString = this.queries[this.resultIndex].toString(this.parameterLists[this.resultIndex]);
/*      */         }
/* 2497 */         this.batchException = new BatchUpdateException(GT.tr("Batch entry {0} {1} was aborted.  Call getNextException to see the cause.", new Object[] { new Integer(this.resultIndex), queryString }), arrayOfInt);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2503 */       this.batchException.setNextException(newError);
/*      */     }
/*      */     
/*      */     public void handleCompletion() throws SQLException {
/* 2507 */       if (this.batchException != null) {
/* 2508 */         throw this.batchException;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public int[] executeBatch() throws SQLException {
/* 2514 */     checkClosed();
/*      */ 
/*      */     
/* 2517 */     clearWarnings();
/*      */     
/* 2519 */     if (this.batchStatements == null || this.batchStatements.isEmpty()) {
/* 2520 */       return new int[0];
/*      */     }
/* 2522 */     int size = this.batchStatements.size();
/* 2523 */     int[] updateCounts = new int[size];
/*      */ 
/*      */     
/* 2526 */     Query[] queries = (Query[])this.batchStatements.toArray((Object[])new Query[this.batchStatements.size()]);
/* 2527 */     ParameterList[] parameterLists = (ParameterList[])this.batchParameters.toArray((Object[])new ParameterList[this.batchParameters.size()]);
/* 2528 */     this.batchStatements.clear();
/* 2529 */     this.batchParameters.clear();
/*      */ 
/*      */     
/* 2532 */     while (this.firstUnclosedResult != null) {
/*      */       
/* 2534 */       if (this.firstUnclosedResult.getResultSet() != null)
/* 2535 */         this.firstUnclosedResult.getResultSet().close(); 
/* 2536 */       this.firstUnclosedResult = this.firstUnclosedResult.getNext();
/*      */     } 
/*      */     
/* 2539 */     if (this.lastSimpleQuery != null) {
/* 2540 */       this.lastSimpleQuery.close();
/* 2541 */       this.lastSimpleQuery = null;
/*      */     } 
/*      */     
/* 2544 */     int flags = 4;
/*      */ 
/*      */     
/* 2547 */     if (this.preparedQuery != null)
/*      */     {
/* 2549 */       this.m_useCount += queries.length;
/*      */     }
/* 2551 */     if (this.m_prepareThreshold == 0 || this.m_useCount < this.m_prepareThreshold) {
/* 2552 */       flags |= 0x1;
/*      */     }
/* 2554 */     if (this.connection.getAutoCommit()) {
/* 2555 */       flags |= 0x10;
/*      */     }
/* 2557 */     this.result = null;
/* 2558 */     BatchResultHandler handler = new BatchResultHandler(this, queries, parameterLists, updateCounts);
/* 2559 */     this.connection.getQueryExecutor().execute(queries, parameterLists, handler, this.maxrows, this.fetchSize, flags);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2566 */     return updateCounts;
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
/*      */   public void cancel() throws SQLException {
/* 2578 */     this.connection.cancelQuery();
/*      */   }
/*      */ 
/*      */   
/*      */   public Connection getConnection() throws SQLException {
/* 2583 */     return (Connection)this.connection;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getFetchDirection() {
/* 2588 */     return this.fetchdirection;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getResultSetConcurrency() {
/* 2593 */     return this.concurrency;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getResultSetType() {
/* 2598 */     return this.resultsettype;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFetchDirection(int direction) throws SQLException {
/* 2603 */     switch (direction) {
/*      */       
/*      */       case 1000:
/*      */       case 1001:
/*      */       case 1002:
/* 2608 */         this.fetchdirection = direction;
/*      */         return;
/*      */     } 
/* 2611 */     throw new PSQLException(GT.tr("Invalid fetch direction constant: {0}.", new Integer(direction)), PSQLState.INVALID_PARAMETER_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFetchSize(int rows) throws SQLException {
/* 2618 */     checkClosed();
/* 2619 */     if (rows < 0) {
/* 2620 */       throw new PSQLException(GT.tr("Fetch size must be a value greater to or equal to 0."), PSQLState.INVALID_PARAMETER_VALUE);
/*      */     }
/* 2622 */     this.fetchSize = rows;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addBatch() throws SQLException {
/* 2627 */     checkClosed();
/*      */     
/* 2629 */     if (this.batchStatements == null) {
/*      */       
/* 2631 */       this.batchStatements = new ArrayList();
/* 2632 */       this.batchParameters = new ArrayList();
/*      */     } 
/*      */ 
/*      */     
/* 2636 */     this.batchStatements.add(this.preparedQuery);
/* 2637 */     this.batchParameters.add(this.preparedParameters.copy());
/*      */   }
/*      */ 
/*      */   
/*      */   public ResultSetMetaData getMetaData() throws SQLException {
/* 2642 */     checkClosed();
/* 2643 */     ResultSet rs = getResultSet();
/*      */     
/* 2645 */     if (rs == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2650 */       int flags = 49;
/* 2651 */       StatementResultHandler handler = new StatementResultHandler(this);
/* 2652 */       this.connection.getQueryExecutor().execute(this.preparedQuery, this.preparedParameters, handler, 0, 0, flags);
/* 2653 */       ResultWrapper wrapper = handler.getResults();
/* 2654 */       if (wrapper != null) {
/* 2655 */         rs = wrapper.getResultSet();
/*      */       }
/*      */     } 
/*      */     
/* 2659 */     if (rs != null) {
/* 2660 */       return rs.getMetaData();
/*      */     }
/* 2662 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setArray(int i, Array x) throws SQLException {
/* 2667 */     checkClosed();
/*      */     
/* 2669 */     if (null == x) {
/*      */       
/* 2671 */       setNull(i, 2003);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2682 */     String typename = "_" + x.getBaseTypeName();
/* 2683 */     int oid = this.connection.getPGType(typename);
/* 2684 */     if (oid == 0) {
/* 2685 */       throw new PSQLException(GT.tr("Unknown type {0}.", typename), PSQLState.INVALID_PARAMETER_TYPE);
/*      */     }
/* 2687 */     setString(i, x.toString(), oid);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBlob(int i, Blob x) throws SQLException {
/* 2692 */     checkClosed();
/*      */     
/* 2694 */     if (x == null) {
/*      */       
/* 2696 */       setNull(i, 2004);
/*      */       
/*      */       return;
/*      */     } 
/* 2700 */     InputStream l_inStream = x.getBinaryStream();
/* 2701 */     LargeObjectManager lom = this.connection.getLargeObjectAPI();
/* 2702 */     int oid = lom.create();
/* 2703 */     LargeObject lob = lom.open(oid);
/* 2704 */     OutputStream los = lob.getOutputStream();
/* 2705 */     byte[] buf = new byte[4096];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2711 */       int bytesRemaining = (int)x.length();
/* 2712 */       int numRead = l_inStream.read(buf, 0, Math.min(buf.length, bytesRemaining));
/* 2713 */       while (numRead != -1 && bytesRemaining > 0)
/*      */       {
/* 2715 */         bytesRemaining -= numRead;
/* 2716 */         if (numRead == buf.length) {
/* 2717 */           los.write(buf);
/*      */         } else {
/* 2719 */           los.write(buf, 0, numRead);
/* 2720 */         }  numRead = l_inStream.read(buf, 0, Math.min(buf.length, bytesRemaining));
/*      */       }
/*      */     
/*      */     } catch (IOException se) {
/*      */       
/* 2725 */       throw new PSQLException(GT.tr("Unexpected error writing large object to database."), PSQLState.UNEXPECTED_ERROR, se);
/*      */     } finally {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/* 2731 */         los.close();
/* 2732 */         l_inStream.close();
/*      */       
/*      */       }
/* 2735 */       catch (Exception e) {}
/*      */     } 
/*      */     
/* 2738 */     setInt(i, oid);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCharacterStream(int i, Reader x, int length) throws SQLException {
/* 2743 */     checkClosed();
/*      */     
/* 2745 */     if (x == null) {
/* 2746 */       if (this.connection.haveMinimumServerVersion("7.2")) {
/* 2747 */         setNull(i, 12);
/*      */       } else {
/* 2749 */         setNull(i, 2005);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 2754 */     if (length < 0) {
/* 2755 */       throw new PSQLException(GT.tr("Invalid stream length {0}.", new Integer(length)), PSQLState.INVALID_PARAMETER_VALUE);
/*      */     }
/*      */     
/* 2758 */     if (this.connection.haveMinimumCompatibleVersion("7.2")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2766 */       char[] l_chars = new char[length];
/* 2767 */       int l_charsRead = 0;
/*      */ 
/*      */       
/*      */       try {
/*      */         do {
/* 2772 */           int n = x.read(l_chars, l_charsRead, length - l_charsRead);
/* 2773 */           if (n == -1) {
/*      */             break;
/*      */           }
/* 2776 */           l_charsRead += n;
/*      */         }
/* 2778 */         while (l_charsRead != length);
/*      */       
/*      */       }
/*      */       catch (IOException l_ioe) {
/*      */ 
/*      */         
/* 2784 */         throw new PSQLException(GT.tr("Provided Reader failed."), PSQLState.UNEXPECTED_ERROR, l_ioe);
/*      */       } 
/* 2786 */       setString(i, new String(l_chars, 0, l_charsRead));
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2793 */       LargeObjectManager lom = this.connection.getLargeObjectAPI();
/* 2794 */       int oid = lom.create();
/* 2795 */       LargeObject lob = lom.open(oid);
/* 2796 */       OutputStream los = lob.getOutputStream();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 2802 */         int c = x.read();
/* 2803 */         int p = 0;
/* 2804 */         while (c > -1 && p < length) {
/*      */           
/* 2806 */           los.write(c);
/* 2807 */           c = x.read();
/* 2808 */           p++;
/*      */         } 
/* 2810 */         los.close();
/*      */       }
/*      */       catch (IOException se) {
/*      */         
/* 2814 */         throw new PSQLException(GT.tr("Unexpected error writing large object to database."), PSQLState.UNEXPECTED_ERROR, se);
/*      */       } 
/*      */       
/* 2817 */       setInt(i, oid);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setClob(int i, Clob x) throws SQLException {
/* 2823 */     checkClosed();
/*      */     
/* 2825 */     if (x == null) {
/*      */       
/* 2827 */       setNull(i, 2005);
/*      */       
/*      */       return;
/*      */     } 
/* 2831 */     InputStream l_inStream = x.getAsciiStream();
/* 2832 */     int l_length = (int)x.length();
/* 2833 */     LargeObjectManager lom = this.connection.getLargeObjectAPI();
/* 2834 */     int oid = lom.create();
/* 2835 */     LargeObject lob = lom.open(oid);
/* 2836 */     OutputStream los = lob.getOutputStream();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2842 */       int c = l_inStream.read();
/* 2843 */       int p = 0;
/* 2844 */       while (c > -1 && p < l_length) {
/*      */         
/* 2846 */         los.write(c);
/* 2847 */         c = l_inStream.read();
/* 2848 */         p++;
/*      */       } 
/* 2850 */       los.close();
/*      */     }
/*      */     catch (IOException se) {
/*      */       
/* 2854 */       throw new PSQLException(GT.tr("Unexpected error writing large object to database."), PSQLState.UNEXPECTED_ERROR, se);
/*      */     } 
/*      */     
/* 2857 */     setInt(i, oid);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setNull(int i, int t, String s) throws SQLException {
/* 2862 */     checkClosed();
/* 2863 */     setNull(i, t);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRef(int i, Ref x) throws SQLException {
/* 2868 */     throw Driver.notImplemented(getClass(), "setRef(int,Ref)");
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDate(int i, Date d, Calendar cal) throws SQLException {
/* 2873 */     checkClosed();
/*      */     
/* 2875 */     if (d == null) {
/*      */       
/* 2877 */       setNull(i, 91);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 2882 */     if (cal != null) {
/* 2883 */       cal = (Calendar)cal.clone();
/*      */     }
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
/* 2904 */     bindString(i, this.connection.getTimestampUtils().toString(cal, d), 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTime(int i, Time t, Calendar cal) throws SQLException {
/* 2909 */     checkClosed();
/*      */     
/* 2911 */     if (t == null) {
/*      */       
/* 2913 */       setNull(i, 92);
/*      */       
/*      */       return;
/*      */     } 
/* 2917 */     if (cal != null) {
/* 2918 */       cal = (Calendar)cal.clone();
/*      */     }
/*      */ 
/*      */     
/* 2922 */     bindString(i, this.connection.getTimestampUtils().toString(cal, t), 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTimestamp(int i, Timestamp t, Calendar cal) throws SQLException {
/* 2927 */     checkClosed();
/*      */     
/* 2929 */     if (t == null) {
/* 2930 */       setNull(i, 93);
/*      */       
/*      */       return;
/*      */     } 
/* 2934 */     if (cal != null) {
/* 2935 */       cal = (Calendar)cal.clone();
/*      */     }
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
/*      */ 
/*      */     
/* 2967 */     bindString(i, this.connection.getTimestampUtils().toString(cal, t), 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Array getArray(int i) throws SQLException {
/* 2974 */     checkClosed();
/* 2975 */     checkIndex(i, 2003, "Array");
/* 2976 */     return (Array)this.callResult[i - 1];
/*      */   }
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
/* 2981 */     checkClosed();
/* 2982 */     checkIndex(parameterIndex, 2, "BigDecimal");
/* 2983 */     return (BigDecimal)this.callResult[parameterIndex - 1];
/*      */   }
/*      */ 
/*      */   
/*      */   public Blob getBlob(int i) throws SQLException {
/* 2988 */     throw Driver.notImplemented(getClass(), "getBlob(int)");
/*      */   }
/*      */ 
/*      */   
/*      */   public Clob getClob(int i) throws SQLException {
/* 2993 */     throw Driver.notImplemented(getClass(), "getClob(int)");
/*      */   }
/*      */ 
/*      */   
/*      */   public Object getObjectImpl(int i, Map map) throws SQLException {
/* 2998 */     if (map == null || map.isEmpty()) {
/* 2999 */       return getObject(i);
/*      */     }
/* 3001 */     throw Driver.notImplemented(getClass(), "getObjectImpl(int,Map)");
/*      */   }
/*      */ 
/*      */   
/*      */   public Ref getRef(int i) throws SQLException {
/* 3006 */     throw Driver.notImplemented(getClass(), "getRef(int)");
/*      */   }
/*      */ 
/*      */   
/*      */   public Date getDate(int i, Calendar cal) throws SQLException {
/* 3011 */     if (cal == null)
/* 3012 */       return getDate(i); 
/* 3013 */     Date tmp = getDate(i);
/* 3014 */     if (tmp == null)
/* 3015 */       return null; 
/* 3016 */     cal = changeTime(tmp, cal, false);
/* 3017 */     return new Date(cal.getTime().getTime());
/*      */   }
/*      */ 
/*      */   
/*      */   public Time getTime(int i, Calendar cal) throws SQLException {
/* 3022 */     if (cal == null)
/* 3023 */       return getTime(i); 
/* 3024 */     Date tmp = getTime(i);
/* 3025 */     if (tmp == null)
/* 3026 */       return null; 
/* 3027 */     cal = changeTime(tmp, cal, false);
/* 3028 */     return new Time(cal.getTime().getTime());
/*      */   }
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(int i, Calendar cal) throws SQLException {
/* 3033 */     if (cal == null)
/* 3034 */       return getTimestamp(i); 
/* 3035 */     Date tmp = getTimestamp(i);
/* 3036 */     if (tmp == null)
/* 3037 */       return null; 
/* 3038 */     cal = changeTime(tmp, cal, false);
/* 3039 */     return new Timestamp(cal.getTime().getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
/* 3045 */     throw Driver.notImplemented(getClass(), "registerOutParameter(int,int,String)");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static Calendar changeTime(Date t, Calendar cal, boolean Add) {
/* 3051 */     long millis = t.getTime();
/*      */     
/* 3053 */     if (millis == 9223372036825200000L || millis == -9223372036832400000L) {
/*      */ 
/*      */       
/* 3056 */       cal.setTime(t);
/* 3057 */       return cal;
/*      */     } 
/*      */     
/* 3060 */     int localoffset = t.getTimezoneOffset() * 60 * 1000 * -1;
/* 3061 */     int caloffset = cal.getTimeZone().getRawOffset();
/* 3062 */     if (cal.getTimeZone().inDaylightTime(t))
/* 3063 */       millis += 3600000L; 
/* 3064 */     caloffset = Add ? (caloffset - localoffset) : (-1 * (caloffset - localoffset));
/* 3065 */     Date tmpDate = new Date();
/* 3066 */     tmpDate.setTime(millis - caloffset);
/* 3067 */     cal.setTime(tmpDate);
/*      */     
/* 3069 */     tmpDate = null;
/* 3070 */     return cal;
/*      */   }
/*      */   
/*      */   public abstract ResultSet createResultSet(Query paramQuery, Field[] paramArrayOfField, Vector paramVector, ResultCursor paramResultCursor) throws SQLException;
/*      */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc2\AbstractJdbc2Statement.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */