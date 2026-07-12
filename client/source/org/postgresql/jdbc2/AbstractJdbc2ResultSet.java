/*      */ package org.postgresql.jdbc2;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Connection;
/*      */ import java.sql.Date;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.Ref;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Calendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import org.postgresql.Driver;
/*      */ import org.postgresql.core.BaseConnection;
/*      */ import org.postgresql.core.BaseStatement;
/*      */ import org.postgresql.core.Encoding;
/*      */ import org.postgresql.core.Field;
/*      */ import org.postgresql.core.Query;
/*      */ import org.postgresql.core.ResultCursor;
/*      */ import org.postgresql.largeobject.LargeObject;
/*      */ import org.postgresql.largeobject.LargeObjectManager;
/*      */ import org.postgresql.util.GT;
/*      */ import org.postgresql.util.PSQLException;
/*      */ import org.postgresql.util.PSQLState;
/*      */ 
/*      */ public abstract class AbstractJdbc2ResultSet implements BaseResultSet, PGRefCursorResultSet {
/*      */   private boolean updateable = false;
/*   45 */   private HashMap updateValues = null; private boolean doingUpdates = false;
/*      */   private boolean usingOID = false;
/*      */   private Vector primaryKeys;
/*      */   private boolean singleTable = false;
/*   49 */   private String tableName = null;
/*   50 */   private PreparedStatement updateStatement = null;
/*   51 */   private PreparedStatement insertStatement = null;
/*   52 */   private PreparedStatement deleteStatement = null;
/*   53 */   private PreparedStatement selectStatement = null;
/*      */   private int resultsettype;
/*      */   private int resultsetconcurrency;
/*   56 */   private int fetchdirection = 1002;
/*      */   
/*      */   protected final BaseConnection connection;
/*      */   
/*      */   protected final BaseStatement statement;
/*      */   protected final Field[] fields;
/*      */   protected final Query originalQuery;
/*      */   protected final int maxRows;
/*      */   protected final int maxFieldSize;
/*      */   protected Vector rows;
/*   66 */   protected int current_row = -1;
/*      */   protected int row_offset;
/*      */   protected byte[][] this_row;
/*   69 */   protected SQLWarning warnings = null;
/*      */   
/*      */   protected boolean wasNullFlag = false;
/*      */   protected boolean onInsertRow = false;
/*   73 */   private GregorianCalendar calendar = null;
/*   74 */   public byte[][] rowBuffer = null;
/*      */   
/*      */   protected int fetchSize;
/*      */   
/*      */   protected ResultCursor cursor;
/*      */   
/*      */   private HashMap columnNameIndexMap;
/*      */   
/*      */   private String refCursorName;
/*      */ 
/*      */   
/*      */   public abstract ResultSetMetaData getMetaData() throws SQLException;
/*      */   
/*      */   public AbstractJdbc2ResultSet(Query originalQuery, BaseStatement statement, Field[] fields, Vector tuples, ResultCursor cursor, int maxRows, int maxFieldSize, int rsType, int rsConcurrency) throws SQLException {
/*   88 */     this.originalQuery = originalQuery;
/*   89 */     this.connection = (BaseConnection)statement.getConnection();
/*   90 */     this.statement = statement;
/*   91 */     this.fields = fields;
/*   92 */     this.rows = tuples;
/*   93 */     this.cursor = cursor;
/*   94 */     this.maxRows = maxRows;
/*   95 */     this.maxFieldSize = maxFieldSize;
/*   96 */     this.resultsettype = rsType;
/*   97 */     this.resultsetconcurrency = rsConcurrency;
/*      */   }
/*      */ 
/*      */   
/*      */   public URL getURL(int columnIndex) throws SQLException {
/*  102 */     checkClosed();
/*  103 */     throw Driver.notImplemented(getClass(), "getURL(int)");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public URL getURL(String columnName) throws SQLException {
/*  109 */     return getURL(findColumn(columnName));
/*      */   }
/*      */ 
/*      */   
/*      */   protected Object internalGetObject(int columnIndex, Field field) throws SQLException {
/*  114 */     switch (getSQLType(columnIndex)) {
/*      */ 
/*      */       
/*      */       case -7:
/*  118 */         return getBoolean(columnIndex) ? Boolean.TRUE : Boolean.FALSE;
/*      */       case -6:
/*      */       case 4:
/*      */       case 5:
/*  122 */         return new Integer(getInt(columnIndex));
/*      */       case -5:
/*  124 */         return new Long(getLong(columnIndex));
/*      */       case 2:
/*      */       case 3:
/*  127 */         return getBigDecimal(columnIndex, (field.getMod() == -1) ? -1 : (field.getMod() - 4 & 0xFFFF));
/*      */       
/*      */       case 7:
/*  130 */         return new Float(getFloat(columnIndex));
/*      */       case 6:
/*      */       case 8:
/*  133 */         return new Double(getDouble(columnIndex));
/*      */       case -1:
/*      */       case 1:
/*      */       case 12:
/*  137 */         return getString(columnIndex);
/*      */       case 91:
/*  139 */         return getDate(columnIndex);
/*      */       case 92:
/*  141 */         return getTime(columnIndex);
/*      */       case 93:
/*  143 */         return getTimestamp(columnIndex, (Calendar)null);
/*      */       case -4:
/*      */       case -3:
/*      */       case -2:
/*  147 */         return getBytes(columnIndex);
/*      */       case 2003:
/*  149 */         return getArray(columnIndex);
/*      */       case 2005:
/*  151 */         return getClob(columnIndex);
/*      */       case 2004:
/*  153 */         return getBlob(columnIndex);
/*      */     } 
/*      */     
/*  156 */     String type = getPGType(columnIndex);
/*      */ 
/*      */     
/*  159 */     if (type.equals("unknown")) {
/*  160 */       return getString(columnIndex);
/*      */     }
/*      */     
/*  163 */     if (type.equals("refcursor")) {
/*      */ 
/*      */       
/*  166 */       String cursorName = getString(columnIndex);
/*  167 */       String fetchSql = "FETCH ALL IN \"" + cursorName + "\"";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  176 */       ResultSet rs = this.connection.execSQLQuery(fetchSql);
/*  177 */       ((AbstractJdbc2ResultSet)rs).setRefCursor(cursorName);
/*  178 */       return rs;
/*      */     } 
/*      */ 
/*      */     
/*  182 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkScrollable() throws SQLException {
/*  188 */     checkClosed();
/*  189 */     if (this.resultsettype == 1003) {
/*  190 */       throw new PSQLException(GT.tr("Operation requires a scrollable ResultSet, but this ResultSet is FORWARD_ONLY."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean absolute(int index) throws SQLException {
/*      */     int i;
/*  196 */     checkScrollable();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  201 */     if (index == 0) {
/*      */       
/*  203 */       beforeFirst();
/*  204 */       return false;
/*      */     } 
/*      */     
/*  207 */     int rows_size = this.rows.size();
/*      */ 
/*      */ 
/*      */     
/*  211 */     if (index < 0) {
/*      */       
/*  213 */       if (index >= -rows_size) {
/*  214 */         i = rows_size + index;
/*      */       } else {
/*      */         
/*  217 */         beforeFirst();
/*  218 */         return false;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  226 */     else if (index <= rows_size) {
/*  227 */       i = index - 1;
/*      */     } else {
/*      */       
/*  230 */       afterLast();
/*  231 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  235 */     this.current_row = i;
/*  236 */     this.this_row = this.rows.elementAt(i);
/*      */     
/*  238 */     this.rowBuffer = new byte[this.this_row.length][];
/*  239 */     System.arraycopy(this.this_row, 0, this.rowBuffer, 0, this.this_row.length);
/*  240 */     this.onInsertRow = false;
/*      */     
/*  242 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void afterLast() throws SQLException {
/*  248 */     checkScrollable();
/*      */     
/*  250 */     int rows_size = this.rows.size();
/*  251 */     if (rows_size > 0) {
/*  252 */       this.current_row = rows_size;
/*      */     }
/*  254 */     this.onInsertRow = false;
/*  255 */     this.this_row = null;
/*  256 */     this.rowBuffer = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void beforeFirst() throws SQLException {
/*  262 */     checkScrollable();
/*      */     
/*  264 */     if (this.rows.size() > 0) {
/*  265 */       this.current_row = -1;
/*      */     }
/*  267 */     this.onInsertRow = false;
/*  268 */     this.this_row = null;
/*  269 */     this.rowBuffer = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean first() throws SQLException {
/*  275 */     checkScrollable();
/*      */     
/*  277 */     if (this.rows.size() <= 0) {
/*  278 */       return false;
/*      */     }
/*  280 */     this.current_row = 0;
/*  281 */     this.this_row = this.rows.elementAt(this.current_row);
/*      */     
/*  283 */     this.rowBuffer = new byte[this.this_row.length][];
/*  284 */     System.arraycopy(this.this_row, 0, this.rowBuffer, 0, this.this_row.length);
/*  285 */     this.onInsertRow = false;
/*      */     
/*  287 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Array getArray(String colName) throws SQLException {
/*  293 */     return getArray(findColumn(colName));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Array getArray(int i) throws SQLException {
/*  299 */     checkResultSet(i);
/*      */     
/*  301 */     this.wasNullFlag = (this.this_row[i - 1] == null);
/*  302 */     if (this.wasNullFlag) {
/*  303 */       return null;
/*      */     }
/*  305 */     return createArray(i);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
/*  311 */     return getBigDecimal(columnIndex, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(String columnName) throws SQLException {
/*  317 */     return getBigDecimal(findColumn(columnName));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Blob getBlob(String columnName) throws SQLException {
/*  323 */     return getBlob(findColumn(columnName));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Blob getBlob(int paramInt) throws SQLException;
/*      */ 
/*      */   
/*      */   public Reader getCharacterStream(String columnName) throws SQLException {
/*  332 */     return getCharacterStream(findColumn(columnName));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Reader getCharacterStream(int i) throws SQLException {
/*  338 */     checkResultSet(i);
/*  339 */     this.wasNullFlag = (this.this_row[i - 1] == null);
/*  340 */     if (this.wasNullFlag) {
/*  341 */       return null;
/*      */     }
/*  343 */     if (((AbstractJdbc2Connection)this.connection).haveMinimumCompatibleVersion("7.2"))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  351 */       return new CharArrayReader(getString(i).toCharArray());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  356 */     Encoding encoding = this.connection.getEncoding();
/*  357 */     InputStream input = getBinaryStream(i);
/*      */ 
/*      */     
/*      */     try {
/*  361 */       return encoding.getDecodingReader(input);
/*      */     }
/*      */     catch (IOException ioe) {
/*      */       
/*  365 */       throw new PSQLException(GT.tr("Unexpected error while decoding character data from a large object."), PSQLState.UNEXPECTED_ERROR, ioe);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Clob getClob(String columnName) throws SQLException {
/*  373 */     return getClob(findColumn(columnName));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Clob getClob(int paramInt) throws SQLException;
/*      */ 
/*      */   
/*      */   public int getConcurrency() throws SQLException {
/*  382 */     checkClosed();
/*  383 */     return this.resultsetconcurrency;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate(int i, Calendar cal) throws SQLException {
/*  389 */     checkResultSet(i);
/*      */     
/*  391 */     if (cal != null) {
/*  392 */       cal = (Calendar)cal.clone();
/*      */     }
/*  394 */     return this.connection.getTimestampUtils().toDate(cal, getString(i));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Time getTime(int i, Calendar cal) throws SQLException {
/*  400 */     checkResultSet(i);
/*      */     
/*  402 */     if (cal != null) {
/*  403 */       cal = (Calendar)cal.clone();
/*      */     }
/*  405 */     return this.connection.getTimestampUtils().toTime(cal, getString(i));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(int i, Calendar cal) throws SQLException {
/*  411 */     checkResultSet(i);
/*      */     
/*  413 */     if (cal != null) {
/*  414 */       cal = (Calendar)cal.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  419 */     return this.connection.getTimestampUtils().toTimestamp(cal, getString(i));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate(String c, Calendar cal) throws SQLException {
/*  425 */     return getDate(findColumn(c), cal);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Time getTime(String c, Calendar cal) throws SQLException {
/*  431 */     return getTime(findColumn(c), cal);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(String c, Calendar cal) throws SQLException {
/*  437 */     return getTimestamp(findColumn(c), cal);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFetchDirection() throws SQLException {
/*  443 */     checkClosed();
/*  444 */     return this.fetchdirection;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getObjectImpl(String columnName, Map map) throws SQLException {
/*  450 */     return getObjectImpl(findColumn(columnName), map);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getObjectImpl(int i, Map map) throws SQLException {
/*  461 */     checkClosed();
/*  462 */     if (map == null || map.isEmpty()) {
/*  463 */       return getObject(i);
/*      */     }
/*  465 */     throw Driver.notImplemented(getClass(), "getObjectImpl(int,Map)");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Ref getRef(String columnName) throws SQLException {
/*  471 */     return getRef(findColumn(columnName));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Ref getRef(int i) throws SQLException {
/*  477 */     checkClosed();
/*      */     
/*  479 */     throw Driver.notImplemented(getClass(), "getRef(int)");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRow() throws SQLException {
/*  485 */     checkClosed();
/*      */     
/*  487 */     if (this.onInsertRow) {
/*  488 */       return 0;
/*      */     }
/*  490 */     int rows_size = this.rows.size();
/*      */     
/*  492 */     if (this.current_row < 0 || this.current_row >= rows_size) {
/*  493 */       return 0;
/*      */     }
/*  495 */     return this.row_offset + this.current_row + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement getStatement() throws SQLException {
/*  502 */     checkClosed();
/*  503 */     return (Statement)this.statement;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getType() throws SQLException {
/*  509 */     checkClosed();
/*  510 */     return this.resultsettype;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAfterLast() throws SQLException {
/*  516 */     checkClosed();
/*  517 */     if (this.onInsertRow) {
/*  518 */       return false;
/*      */     }
/*  520 */     int rows_size = this.rows.size();
/*  521 */     return (this.current_row >= rows_size && rows_size > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBeforeFirst() throws SQLException {
/*  527 */     checkClosed();
/*  528 */     if (this.onInsertRow) {
/*  529 */       return false;
/*      */     }
/*  531 */     return (this.row_offset + this.current_row < 0 && this.rows.size() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFirst() throws SQLException {
/*  537 */     checkClosed();
/*  538 */     if (this.onInsertRow) {
/*  539 */       return false;
/*      */     }
/*  541 */     return (this.row_offset + this.current_row == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLast() throws SQLException {
/*  547 */     checkClosed();
/*  548 */     if (this.onInsertRow) {
/*  549 */       return false;
/*      */     }
/*  551 */     int rows_size = this.rows.size();
/*      */     
/*  553 */     if (rows_size == 0) {
/*  554 */       return false;
/*      */     }
/*  556 */     if (this.current_row != rows_size - 1) {
/*  557 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  561 */     if (this.cursor == null)
/*      */     {
/*      */       
/*  564 */       return true;
/*      */     }
/*      */     
/*  567 */     if (this.maxRows > 0 && this.row_offset + this.current_row == this.maxRows)
/*      */     {
/*      */       
/*  570 */       return true;
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
/*  582 */     this.row_offset += rows_size - 1;
/*      */ 
/*      */     
/*  585 */     int fetchRows = this.fetchSize;
/*  586 */     if (this.maxRows != 0)
/*      */     {
/*  588 */       if (fetchRows == 0 || this.row_offset + fetchRows > this.maxRows) {
/*  589 */         fetchRows = this.maxRows - this.row_offset;
/*      */       }
/*      */     }
/*      */     
/*  593 */     this.connection.getQueryExecutor().fetch(this.cursor, new CursorResultHandler(this), fetchRows);
/*      */ 
/*      */     
/*  596 */     this.rows.insertElementAt(this.this_row, 0);
/*  597 */     this.current_row = 0;
/*      */ 
/*      */     
/*  600 */     return (this.rows.size() == 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean last() throws SQLException {
/*  605 */     checkScrollable();
/*      */     
/*  607 */     int rows_size = this.rows.size();
/*  608 */     if (rows_size <= 0) {
/*  609 */       return false;
/*      */     }
/*  611 */     this.current_row = rows_size - 1;
/*  612 */     this.this_row = this.rows.elementAt(this.current_row);
/*      */     
/*  614 */     this.rowBuffer = new byte[this.this_row.length][];
/*  615 */     System.arraycopy(this.this_row, 0, this.rowBuffer, 0, this.this_row.length);
/*  616 */     this.onInsertRow = false;
/*      */     
/*  618 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean previous() throws SQLException {
/*  624 */     checkScrollable();
/*      */     
/*  626 */     if (this.onInsertRow) {
/*  627 */       throw new PSQLException(GT.tr("Can''t use relative move methods while on the insert row."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */     
/*  630 */     if (this.current_row - 1 < 0) {
/*      */       
/*  632 */       this.current_row = -1;
/*  633 */       this.this_row = null;
/*  634 */       this.rowBuffer = null;
/*  635 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  639 */     this.current_row--;
/*      */     
/*  641 */     this.this_row = this.rows.elementAt(this.current_row);
/*  642 */     this.rowBuffer = new byte[this.this_row.length][];
/*  643 */     System.arraycopy(this.this_row, 0, this.rowBuffer, 0, this.this_row.length);
/*  644 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean relative(int rows) throws SQLException {
/*  650 */     checkScrollable();
/*      */     
/*  652 */     if (this.onInsertRow) {
/*  653 */       throw new PSQLException(GT.tr("Can''t use relative move methods while on the insert row."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */ 
/*      */     
/*  657 */     return absolute(this.current_row + 1 + rows);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFetchDirection(int direction) throws SQLException {
/*  663 */     checkClosed();
/*  664 */     switch (direction) {
/*      */       case 1000:
/*      */         break;
/*      */       
/*      */       case 1001:
/*      */       case 1002:
/*  670 */         checkScrollable();
/*      */         break;
/*      */       default:
/*  673 */         throw new PSQLException(GT.tr("Invalid fetch direction constant: {0}.", new Integer(direction)), PSQLState.INVALID_PARAMETER_VALUE);
/*      */     } 
/*      */ 
/*      */     
/*  677 */     this.fetchdirection = direction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void cancelRowUpdates() throws SQLException {
/*  684 */     checkClosed();
/*  685 */     if (this.onInsertRow)
/*      */     {
/*  687 */       throw new PSQLException(GT.tr("Cannot call cancelRowUpdates() when on the insert row."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */ 
/*      */     
/*  691 */     if (this.doingUpdates) {
/*      */       
/*  693 */       this.doingUpdates = false;
/*      */       
/*  695 */       clearRowBuffer(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void deleteRow() throws SQLException {
/*  703 */     checkUpdateable();
/*      */     
/*  705 */     if (this.onInsertRow)
/*      */     {
/*  707 */       throw new PSQLException(GT.tr("Cannot call deleteRow() when on the insert row."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */ 
/*      */     
/*  711 */     if (isBeforeFirst())
/*      */     {
/*  713 */       throw new PSQLException(GT.tr("Currently positioned before the start of the ResultSet.  You cannot call deleteRow() here."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */     
/*  716 */     if (isAfterLast())
/*      */     {
/*  718 */       throw new PSQLException(GT.tr("Currently positioned after the end of the ResultSet.  You cannot call deleteRow() here."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */     
/*  721 */     if (this.rows.size() == 0)
/*      */     {
/*  723 */       throw new PSQLException(GT.tr("There are no rows in this ResultSet."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  728 */     int numKeys = this.primaryKeys.size();
/*  729 */     if (this.deleteStatement == null) {
/*      */ 
/*      */ 
/*      */       
/*  733 */       StringBuffer deleteSQL = (new StringBuffer("DELETE FROM ")).append(this.tableName).append(" where ");
/*      */       
/*  735 */       for (int j = 0; j < numKeys; j++) {
/*      */         
/*  737 */         deleteSQL.append("\"");
/*  738 */         deleteSQL.append(((PrimaryKey)this.primaryKeys.get(j)).name);
/*  739 */         deleteSQL.append("\" = ?");
/*  740 */         if (j < numKeys - 1)
/*      */         {
/*  742 */           deleteSQL.append(" and ");
/*      */         }
/*      */       } 
/*      */       
/*  746 */       this.deleteStatement = this.connection.prepareStatement(deleteSQL.toString());
/*      */     } 
/*  748 */     this.deleteStatement.clearParameters();
/*      */     
/*  750 */     for (int i = 0; i < numKeys; i++)
/*      */     {
/*  752 */       this.deleteStatement.setObject(i + 1, ((PrimaryKey)this.primaryKeys.get(i)).getValue());
/*      */     }
/*      */ 
/*      */     
/*  756 */     this.deleteStatement.executeUpdate();
/*      */     
/*  758 */     this.rows.removeElementAt(this.current_row);
/*  759 */     this.current_row--;
/*  760 */     moveToCurrentRow();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void insertRow() throws SQLException {
/*  767 */     checkUpdateable();
/*      */     
/*  769 */     if (!this.onInsertRow)
/*      */     {
/*  771 */       throw new PSQLException(GT.tr("Not on the insert row."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*  773 */     if (this.updateValues.size() == 0)
/*      */     {
/*  775 */       throw new PSQLException(GT.tr("You must specify at least one column value to insert a row."), PSQLState.INVALID_PARAMETER_VALUE);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  785 */     StringBuffer insertSQL = (new StringBuffer("INSERT INTO ")).append(this.tableName).append(" (");
/*  786 */     StringBuffer paramSQL = new StringBuffer(") values (");
/*      */     
/*  788 */     Iterator columnNames = this.updateValues.keySet().iterator();
/*  789 */     int numColumns = this.updateValues.size();
/*      */     
/*  791 */     for (int i = 0; columnNames.hasNext(); i++) {
/*      */       
/*  793 */       String columnName = columnNames.next();
/*      */       
/*  795 */       insertSQL.append("\"");
/*  796 */       insertSQL.append(columnName);
/*  797 */       insertSQL.append("\"");
/*  798 */       if (i < numColumns - 1) {
/*      */         
/*  800 */         insertSQL.append(", ");
/*  801 */         paramSQL.append("?,");
/*      */       }
/*      */       else {
/*      */         
/*  805 */         paramSQL.append("?)");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  810 */     insertSQL.append(paramSQL.toString());
/*  811 */     this.insertStatement = this.connection.prepareStatement(insertSQL.toString());
/*      */     
/*  813 */     Iterator keys = this.updateValues.keySet().iterator();
/*      */     
/*  815 */     for (int j = 1; keys.hasNext(); j++) {
/*      */       
/*  817 */       String key = keys.next();
/*  818 */       Object o = this.updateValues.get(key);
/*  819 */       this.insertStatement.setObject(j, o);
/*      */     } 
/*      */     
/*  822 */     this.insertStatement.executeUpdate();
/*      */     
/*  824 */     if (this.usingOID) {
/*      */ 
/*      */ 
/*      */       
/*  828 */       long insertedOID = ((AbstractJdbc2Statement)this.insertStatement).getLastOID();
/*      */       
/*  830 */       this.updateValues.put("oid", new Long(insertedOID));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  835 */     updateRowBuffer();
/*      */     
/*  837 */     this.rows.addElement(this.rowBuffer);
/*      */ 
/*      */ 
/*      */     
/*  841 */     this.this_row = this.rowBuffer;
/*      */ 
/*      */     
/*  844 */     clearRowBuffer(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void moveToCurrentRow() throws SQLException {
/*  854 */     checkUpdateable();
/*      */     
/*  856 */     if (this.current_row < 0 || this.current_row >= this.rows.size()) {
/*      */       
/*  858 */       this.this_row = null;
/*  859 */       this.rowBuffer = null;
/*      */     }
/*      */     else {
/*      */       
/*  863 */       this.this_row = this.rows.elementAt(this.current_row);
/*      */       
/*  865 */       this.rowBuffer = new byte[this.this_row.length][];
/*  866 */       System.arraycopy(this.this_row, 0, this.rowBuffer, 0, this.this_row.length);
/*      */     } 
/*      */     
/*  869 */     this.onInsertRow = false;
/*  870 */     this.doingUpdates = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void moveToInsertRow() throws SQLException {
/*  877 */     checkUpdateable();
/*      */     
/*  879 */     if (this.insertStatement != null)
/*      */     {
/*  881 */       this.insertStatement = null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  886 */     clearRowBuffer(false);
/*      */     
/*  888 */     this.onInsertRow = true;
/*  889 */     this.doingUpdates = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void clearRowBuffer(boolean copyCurrentRow) throws SQLException {
/*  898 */     this.rowBuffer = new byte[this.fields.length][];
/*      */ 
/*      */     
/*  901 */     if (copyCurrentRow)
/*      */     {
/*  903 */       System.arraycopy(this.this_row, 0, this.rowBuffer, 0, this.this_row.length);
/*      */     }
/*      */ 
/*      */     
/*  907 */     this.updateValues.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean rowDeleted() throws SQLException {
/*  914 */     checkClosed();
/*  915 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean rowInserted() throws SQLException {
/*  921 */     checkClosed();
/*  922 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean rowUpdated() throws SQLException {
/*  928 */     checkClosed();
/*  929 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
/*  939 */     if (x == null) {
/*      */       
/*  941 */       updateNull(columnIndex);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*      */     try {
/*  947 */       InputStreamReader reader = new InputStreamReader(x, "ASCII");
/*  948 */       char[] data = new char[length];
/*  949 */       int numRead = 0;
/*      */       
/*      */       do {
/*  952 */         int n = reader.read(data, numRead, length - numRead);
/*  953 */         if (n == -1) {
/*      */           break;
/*      */         }
/*  956 */         numRead += n;
/*      */       }
/*  958 */       while (numRead != length);
/*      */ 
/*      */       
/*  961 */       updateString(columnIndex, new String(data, 0, numRead));
/*      */     }
/*      */     catch (UnsupportedEncodingException uee) {
/*      */       
/*  965 */       throw new PSQLException(GT.tr("The JVM claims not to support the encoding: {0}", "ASCII"), PSQLState.UNEXPECTED_ERROR, uee);
/*      */     }
/*      */     catch (IOException ie) {
/*      */       
/*  969 */       throw new PSQLException(GT.tr("Provided InputStream failed."), null, ie);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
/*  978 */     updateValue(columnIndex, x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
/*  988 */     if (x == null) {
/*      */       
/*  990 */       updateNull(columnIndex);
/*      */       
/*      */       return;
/*      */     } 
/*  994 */     byte[] data = new byte[length];
/*  995 */     int numRead = 0;
/*      */ 
/*      */     
/*      */     try {
/*      */       do {
/* 1000 */         int n = x.read(data, numRead, length - numRead);
/* 1001 */         if (n == -1) {
/*      */           break;
/*      */         }
/* 1004 */         numRead += n;
/*      */       }
/* 1006 */       while (numRead != length);
/*      */     
/*      */     }
/*      */     catch (IOException ie) {
/*      */ 
/*      */       
/* 1012 */       throw new PSQLException(GT.tr("Provided InputStream failed."), null, ie);
/*      */     } 
/*      */     
/* 1015 */     if (numRead == length) {
/*      */       
/* 1017 */       updateBytes(columnIndex, data);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1023 */       byte[] data2 = new byte[numRead];
/* 1024 */       System.arraycopy(data, 0, data2, 0, numRead);
/* 1025 */       updateBytes(columnIndex, data2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateBoolean(int columnIndex, boolean x) throws SQLException {
/* 1033 */     updateValue(columnIndex, new Boolean(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateByte(int columnIndex, byte x) throws SQLException {
/* 1040 */     updateValue(columnIndex, String.valueOf(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateBytes(int columnIndex, byte[] x) throws SQLException {
/* 1047 */     updateValue(columnIndex, x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
/* 1057 */     if (x == null) {
/*      */       
/* 1059 */       updateNull(columnIndex);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*      */     try {
/* 1065 */       char[] data = new char[length];
/* 1066 */       int numRead = 0;
/*      */       
/*      */       do {
/* 1069 */         int n = x.read(data, numRead, length - numRead);
/* 1070 */         if (n == -1) {
/*      */           break;
/*      */         }
/* 1073 */         numRead += n;
/*      */       }
/* 1075 */       while (numRead != length);
/*      */ 
/*      */       
/* 1078 */       updateString(columnIndex, new String(data, 0, numRead));
/*      */     }
/*      */     catch (IOException ie) {
/*      */       
/* 1082 */       throw new PSQLException(GT.tr("Provided Reader failed."), null, ie);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateDate(int columnIndex, Date x) throws SQLException {
/* 1090 */     updateValue(columnIndex, x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateDouble(int columnIndex, double x) throws SQLException {
/* 1097 */     updateValue(columnIndex, new Double(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateFloat(int columnIndex, float x) throws SQLException {
/* 1104 */     updateValue(columnIndex, new Float(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateInt(int columnIndex, int x) throws SQLException {
/* 1111 */     updateValue(columnIndex, new Integer(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateLong(int columnIndex, long x) throws SQLException {
/* 1118 */     updateValue(columnIndex, new Long(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateNull(int columnIndex) throws SQLException {
/* 1125 */     checkColumnIndex(columnIndex);
/* 1126 */     String columnTypeName = this.connection.getPGType(this.fields[columnIndex - 1].getOID());
/* 1127 */     updateValue(columnIndex, new NullObject(this, columnTypeName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateObject(int columnIndex, Object x) throws SQLException {
/* 1134 */     updateValue(columnIndex, x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateObject(int columnIndex, Object x, int scale) throws SQLException {
/* 1141 */     updateObject(columnIndex, x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void refreshRow() throws SQLException {
/* 1148 */     checkUpdateable();
/* 1149 */     if (this.onInsertRow) {
/* 1150 */       throw new PSQLException(GT.tr("Can''t refresh the insert row."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */     
/* 1153 */     if (isBeforeFirst() || isAfterLast() || this.rows.size() == 0) {
/*      */       return;
/*      */     }
/* 1156 */     StringBuffer selectSQL = new StringBuffer("select ");
/*      */     
/* 1158 */     int numColumns = Array.getLength(this.fields);
/*      */     
/* 1160 */     for (int i = 0; i < numColumns; i++) {
/*      */       
/* 1162 */       selectSQL.append(this.fields[i].getColumnName((Connection)this.connection));
/*      */       
/* 1164 */       if (i < numColumns - 1)
/*      */       {
/*      */         
/* 1167 */         selectSQL.append(", ");
/*      */       }
/*      */     } 
/*      */     
/* 1171 */     selectSQL.append(" from ").append(this.tableName).append(" where ");
/*      */     
/* 1173 */     int numKeys = this.primaryKeys.size();
/*      */     
/* 1175 */     for (int k = 0; k < numKeys; k++) {
/*      */ 
/*      */       
/* 1178 */       PrimaryKey primaryKey = this.primaryKeys.get(k);
/* 1179 */       selectSQL.append(primaryKey.name).append("= ?");
/*      */       
/* 1181 */       if (k < numKeys - 1)
/*      */       {
/* 1183 */         selectSQL.append(" and ");
/*      */       }
/*      */     } 
/* 1186 */     if (Driver.logDebug)
/* 1187 */       Driver.debug("selecting " + selectSQL.toString()); 
/* 1188 */     this.selectStatement = this.connection.prepareStatement(selectSQL.toString());
/*      */ 
/*      */     
/* 1191 */     for (int j = 0, m = 1; j < numKeys; j++, m++)
/*      */     {
/* 1193 */       this.selectStatement.setObject(m, ((PrimaryKey)this.primaryKeys.get(j)).getValue());
/*      */     }
/*      */     
/* 1196 */     AbstractJdbc2ResultSet rs = (AbstractJdbc2ResultSet)this.selectStatement.executeQuery();
/*      */     
/* 1198 */     if (rs.next())
/*      */     {
/* 1200 */       this.rowBuffer = rs.rowBuffer;
/*      */     }
/*      */     
/* 1203 */     this.rows.setElementAt(this.rowBuffer, this.current_row);
/* 1204 */     this.this_row = this.rowBuffer;
/* 1205 */     if (Driver.logDebug) {
/* 1206 */       Driver.debug("done updates");
/*      */     }
/* 1208 */     rs.close();
/* 1209 */     this.selectStatement.close();
/* 1210 */     this.selectStatement = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateRow() throws SQLException {
/* 1218 */     checkUpdateable();
/*      */     
/* 1220 */     if (this.onInsertRow)
/*      */     {
/* 1222 */       throw new PSQLException(GT.tr("Cannot call updateRow() when on the insert row."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */ 
/*      */     
/* 1226 */     if (isBeforeFirst() || isAfterLast() || this.rows.size() == 0)
/*      */     {
/* 1228 */       throw new PSQLException(GT.tr("Cannot update the ResultSet because it is either before the start or after the end of the results."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */ 
/*      */     
/* 1232 */     if (this.doingUpdates) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */         
/* 1238 */         StringBuffer updateSQL = new StringBuffer("UPDATE " + this.tableName + " SET  ");
/*      */         
/* 1240 */         int numColumns = this.updateValues.size();
/* 1241 */         Iterator columns = this.updateValues.keySet().iterator();
/*      */         
/* 1243 */         for (int i = 0; columns.hasNext(); i++) {
/*      */ 
/*      */           
/* 1246 */           String column = columns.next();
/* 1247 */           updateSQL.append("\"");
/* 1248 */           updateSQL.append(column);
/* 1249 */           updateSQL.append("\" = ?");
/*      */           
/* 1251 */           if (i < numColumns - 1)
/*      */           {
/*      */             
/* 1254 */             updateSQL.append(", ");
/*      */           }
/*      */         } 
/*      */         
/* 1258 */         updateSQL.append(" WHERE ");
/*      */         
/* 1260 */         int numKeys = this.primaryKeys.size();
/*      */         
/* 1262 */         for (int k = 0; k < numKeys; k++) {
/*      */ 
/*      */           
/* 1265 */           PrimaryKey primaryKey = this.primaryKeys.get(k);
/* 1266 */           updateSQL.append("\"");
/* 1267 */           updateSQL.append(primaryKey.name);
/* 1268 */           updateSQL.append("\" = ?");
/*      */           
/* 1270 */           if (k < numKeys - 1)
/*      */           {
/* 1272 */             updateSQL.append(" and ");
/*      */           }
/*      */         } 
/* 1275 */         if (Driver.logDebug)
/* 1276 */           Driver.debug("updating " + updateSQL.toString()); 
/* 1277 */         this.updateStatement = this.connection.prepareStatement(updateSQL.toString());
/*      */         
/* 1279 */         int m = 0;
/* 1280 */         Iterator iterator = this.updateValues.values().iterator();
/* 1281 */         for (; iterator.hasNext(); m++) {
/*      */           
/* 1283 */           Object o = iterator.next();
/* 1284 */           this.updateStatement.setObject(m + 1, o);
/*      */         } 
/*      */         
/* 1287 */         for (int j = 0; j < numKeys; j++, m++)
/*      */         {
/* 1289 */           this.updateStatement.setObject(m + 1, ((PrimaryKey)this.primaryKeys.get(j)).getValue());
/*      */         }
/*      */         
/* 1292 */         this.updateStatement.executeUpdate();
/* 1293 */         this.updateStatement.close();
/*      */         
/* 1295 */         this.updateStatement = null;
/* 1296 */         updateRowBuffer();
/*      */ 
/*      */         
/* 1299 */         if (Driver.logDebug)
/* 1300 */           Driver.debug("copying data"); 
/* 1301 */         System.arraycopy(this.rowBuffer, 0, this.this_row, 0, this.rowBuffer.length);
/*      */         
/* 1303 */         this.rows.setElementAt(this.rowBuffer, this.current_row);
/* 1304 */         if (Driver.logDebug)
/* 1305 */           Driver.debug("done updates"); 
/* 1306 */         this.updateValues.clear();
/* 1307 */         this.doingUpdates = false;
/*      */       }
/*      */       catch (SQLException e) {
/*      */         
/* 1311 */         if (Driver.logDebug)
/* 1312 */           Driver.debug(e.getClass().getName() + e); 
/* 1313 */         throw e;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateShort(int columnIndex, short x) throws SQLException {
/* 1324 */     updateValue(columnIndex, new Short(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateString(int columnIndex, String x) throws SQLException {
/* 1331 */     updateValue(columnIndex, x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateTime(int columnIndex, Time x) throws SQLException {
/* 1338 */     updateValue(columnIndex, x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
/* 1345 */     updateValue(columnIndex, x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateNull(String columnName) throws SQLException {
/* 1353 */     updateNull(findColumn(columnName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateBoolean(String columnName, boolean x) throws SQLException {
/* 1360 */     updateBoolean(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateByte(String columnName, byte x) throws SQLException {
/* 1367 */     updateByte(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateShort(String columnName, short x) throws SQLException {
/* 1374 */     updateShort(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateInt(String columnName, int x) throws SQLException {
/* 1381 */     updateInt(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateLong(String columnName, long x) throws SQLException {
/* 1388 */     updateLong(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateFloat(String columnName, float x) throws SQLException {
/* 1395 */     updateFloat(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateDouble(String columnName, double x) throws SQLException {
/* 1402 */     updateDouble(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {
/* 1409 */     updateBigDecimal(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateString(String columnName, String x) throws SQLException {
/* 1416 */     updateString(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateBytes(String columnName, byte[] x) throws SQLException {
/* 1423 */     updateBytes(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateDate(String columnName, Date x) throws SQLException {
/* 1430 */     updateDate(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateTime(String columnName, Time x) throws SQLException {
/* 1437 */     updateTime(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateTimestamp(String columnName, Timestamp x) throws SQLException {
/* 1444 */     updateTimestamp(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {
/* 1454 */     updateAsciiStream(findColumn(columnName), x, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateBinaryStream(String columnName, InputStream x, int length) throws SQLException {
/* 1464 */     updateBinaryStream(findColumn(columnName), x, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateCharacterStream(String columnName, Reader reader, int length) throws SQLException {
/* 1474 */     updateCharacterStream(findColumn(columnName), reader, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateObject(String columnName, Object x, int scale) throws SQLException {
/* 1481 */     updateObject(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void updateObject(String columnName, Object x) throws SQLException {
/* 1488 */     updateObject(findColumn(columnName), x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isUpdateable() throws SQLException {
/* 1498 */     checkClosed();
/*      */     
/* 1500 */     if (this.resultsetconcurrency == 1007) {
/* 1501 */       throw new PSQLException(GT.tr("ResultSets with concurrency CONCUR_READ_ONLY cannot be updated."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */     
/* 1504 */     if (this.updateable) {
/* 1505 */       return true;
/*      */     }
/* 1507 */     if (Driver.logDebug) {
/* 1508 */       Driver.debug("checking if rs is updateable");
/*      */     }
/* 1510 */     parseQuery();
/*      */     
/* 1512 */     if (!this.singleTable) {
/*      */       
/* 1514 */       if (Driver.logDebug)
/* 1515 */         Driver.debug("not a single table"); 
/* 1516 */       return false;
/*      */     } 
/*      */     
/* 1519 */     if (Driver.logDebug) {
/* 1520 */       Driver.debug("getting primary keys");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1526 */     this.primaryKeys = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1532 */     this.usingOID = false;
/* 1533 */     int oidIndex = 0;
/*      */     
/*      */     try {
/* 1536 */       oidIndex = findColumn("oid");
/*      */     
/*      */     }
/* 1539 */     catch (SQLException l_se) {}
/*      */ 
/*      */     
/* 1542 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1548 */     if (oidIndex > 0) {
/*      */       
/* 1550 */       i++;
/* 1551 */       this.primaryKeys.add(new PrimaryKey(this, oidIndex, "oid"));
/* 1552 */       this.usingOID = true;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1557 */       String[] s = quotelessTableName(this.tableName);
/* 1558 */       String quotelessTableName = s[0];
/* 1559 */       String quotelessSchemaName = s[1];
/* 1560 */       ResultSet rs = this.connection.getMetaData().getPrimaryKeys("", quotelessSchemaName, quotelessTableName);
/* 1561 */       for (; rs.next(); i++) {
/*      */         
/* 1563 */         String columnName = rs.getString(4);
/* 1564 */         int index = findColumn(columnName);
/*      */         
/* 1566 */         if (index > 0)
/*      */         {
/* 1568 */           this.primaryKeys.add(new PrimaryKey(this, index, columnName));
/*      */         }
/*      */       } 
/*      */       
/* 1572 */       rs.close();
/*      */     } 
/*      */     
/* 1575 */     if (Driver.logDebug) {
/* 1576 */       Driver.debug("no of keys=" + i);
/*      */     }
/* 1578 */     if (i < 1)
/*      */     {
/* 1580 */       throw new PSQLException(GT.tr("No primary key found for table {0}.", this.tableName), PSQLState.DATA_ERROR);
/*      */     }
/*      */ 
/*      */     
/* 1584 */     this.updateable = (this.primaryKeys.size() > 0);
/*      */     
/* 1586 */     if (Driver.logDebug) {
/* 1587 */       Driver.debug("checking primary key " + this.updateable);
/*      */     }
/* 1589 */     return this.updateable;
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
/*      */   public static String[] quotelessTableName(String fullname) {
/* 1606 */     StringBuffer buf = new StringBuffer(fullname);
/* 1607 */     String[] parts = { null, "" };
/* 1608 */     StringBuffer acc = new StringBuffer();
/* 1609 */     boolean betweenQuotes = false;
/* 1610 */     for (int i = 0; i < buf.length(); i++) {
/*      */       
/* 1612 */       char c = buf.charAt(i);
/* 1613 */       switch (c) {
/*      */         
/*      */         case '"':
/* 1616 */           if (i < buf.length() - 1 && buf.charAt(i + 1) == '"') {
/*      */ 
/*      */             
/* 1619 */             i++;
/* 1620 */             acc.append(c);
/*      */             
/*      */             break;
/*      */           } 
/* 1624 */           betweenQuotes = !betweenQuotes;
/*      */           break;
/*      */         
/*      */         case '.':
/* 1628 */           if (betweenQuotes) {
/*      */             
/* 1630 */             acc.append(c);
/*      */             
/*      */             break;
/*      */           } 
/* 1634 */           parts[1] = acc.toString();
/* 1635 */           acc = new StringBuffer();
/*      */           break;
/*      */         
/*      */         default:
/* 1639 */           acc.append(betweenQuotes ? c : Character.toLowerCase(c));
/*      */           break;
/*      */       } 
/*      */     
/*      */     } 
/* 1644 */     parts[0] = acc.toString();
/* 1645 */     return parts;
/*      */   }
/*      */ 
/*      */   
/*      */   private void parseQuery() {
/* 1650 */     String l_sql = this.originalQuery.toString(null);
/* 1651 */     StringTokenizer st = new StringTokenizer(l_sql, " \r\t\n");
/* 1652 */     boolean tableFound = false, tablesChecked = false;
/* 1653 */     String name = "";
/*      */     
/* 1655 */     this.singleTable = true;
/*      */     
/* 1657 */     while (!tableFound && !tablesChecked && st.hasMoreTokens()) {
/*      */       
/* 1659 */       name = st.nextToken();
/* 1660 */       if (!tableFound) {
/*      */         
/* 1662 */         if (name.toLowerCase().equals("from")) {
/*      */           
/* 1664 */           this.tableName = st.nextToken();
/* 1665 */           tableFound = true;
/*      */         } 
/*      */         
/*      */         continue;
/*      */       } 
/* 1670 */       tablesChecked = true;
/*      */       
/* 1672 */       this.singleTable = !name.equalsIgnoreCase(",");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateRowBuffer() throws SQLException {
/* 1681 */     Iterator columns = this.updateValues.keySet().iterator();
/*      */     
/* 1683 */     while (columns.hasNext()) {
/*      */       
/* 1685 */       String columnName = columns.next();
/* 1686 */       int columnIndex = findColumn(columnName) - 1;
/*      */       
/* 1688 */       Object valueObject = this.updateValues.get(columnName);
/* 1689 */       if (valueObject instanceof NullObject) {
/*      */         
/* 1691 */         this.rowBuffer[columnIndex] = null;
/*      */         
/*      */         continue;
/*      */       } 
/* 1695 */       switch (getSQLType(columnIndex + 1)) {
/*      */ 
/*      */         
/*      */         case -7:
/*      */         case -6:
/*      */         case -5:
/*      */         case 1:
/*      */         case 2:
/*      */         case 3:
/*      */         case 4:
/*      */         case 5:
/*      */         case 6:
/*      */         case 7:
/*      */         case 8:
/*      */         case 12:
/*      */         case 1111:
/* 1711 */           this.rowBuffer[columnIndex] = this.connection.encodeString(String.valueOf(valueObject));
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 91:
/* 1720 */           this.rowBuffer[columnIndex] = this.connection.encodeString(this.connection.getTimestampUtils().toString((Calendar)null, (Date)valueObject));
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 92:
/* 1725 */           this.rowBuffer[columnIndex] = this.connection.encodeString(this.connection.getTimestampUtils().toString((Calendar)null, (Time)valueObject));
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 93:
/* 1730 */           this.rowBuffer[columnIndex] = this.connection.encodeString(this.connection.getTimestampUtils().toString((Calendar)null, (Timestamp)valueObject));
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 0:
/*      */           continue;
/*      */       } 
/*      */ 
/*      */       
/* 1739 */       this.rowBuffer[columnIndex] = (byte[])valueObject;
/*      */     } 
/*      */   }
/*      */   public class CursorResultHandler implements ResultHandler { private SQLException error;
/*      */     private final AbstractJdbc2ResultSet this$0;
/*      */     
/*      */     public CursorResultHandler(AbstractJdbc2ResultSet this$0) {
/* 1746 */       this.this$0 = this$0;
/*      */     }
/*      */     
/*      */     public void handleResultRows(Query fromQuery, Field[] fields, Vector tuples, ResultCursor cursor) {
/* 1750 */       this.this$0.rows = tuples;
/* 1751 */       this.this$0.cursor = cursor;
/*      */     }
/*      */     
/*      */     public void handleCommandStatus(String status, int updateCount, long insertOID) {
/* 1755 */       handleError((SQLException)new PSQLException(GT.tr("Unexpected command status: {0}.", status), PSQLState.PROTOCOL_VIOLATION));
/*      */     }
/*      */ 
/*      */     
/*      */     public void handleWarning(SQLWarning warning) {
/* 1760 */       this.this$0.addWarning(warning);
/*      */     }
/*      */     
/*      */     public void handleError(SQLException newError) {
/* 1764 */       if (this.error == null) {
/* 1765 */         this.error = newError;
/*      */       } else {
/* 1767 */         this.error.setNextException(newError);
/*      */       } 
/*      */     }
/*      */     public void handleCompletion() throws SQLException {
/* 1771 */       if (this.error != null) {
/* 1772 */         throw this.error;
/*      */       }
/*      */     } }
/*      */ 
/*      */   
/*      */   public BaseStatement getPGStatement() {
/* 1778 */     return this.statement;
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
/*      */   public String getRefCursor() {
/* 1792 */     return this.refCursorName;
/*      */   }
/*      */   
/*      */   private void setRefCursor(String refCursorName) {
/* 1796 */     this.refCursorName = refCursorName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFetchSize(int rows) throws SQLException {
/* 1801 */     checkClosed();
/* 1802 */     if (rows < 0) {
/* 1803 */       throw new PSQLException(GT.tr("Fetch size must be a value greater to or equal to 0."), PSQLState.INVALID_PARAMETER_VALUE);
/*      */     }
/* 1805 */     this.fetchSize = rows;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getFetchSize() throws SQLException {
/* 1810 */     checkClosed();
/* 1811 */     return this.fetchSize;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean next() throws SQLException {
/* 1816 */     checkClosed();
/*      */     
/* 1818 */     if (this.onInsertRow) {
/* 1819 */       throw new PSQLException(GT.tr("Can''t use relative move methods while on the insert row."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */     
/* 1822 */     if (this.current_row + 1 >= this.rows.size()) {
/*      */       
/* 1824 */       if (this.cursor == null || (this.maxRows > 0 && this.row_offset + this.rows.size() >= this.maxRows)) {
/*      */         
/* 1826 */         this.current_row = this.rows.size();
/* 1827 */         this.this_row = null;
/* 1828 */         this.rowBuffer = null;
/* 1829 */         return false;
/*      */       } 
/*      */ 
/*      */       
/* 1833 */       this.row_offset += this.rows.size();
/*      */       
/* 1835 */       int fetchRows = this.fetchSize;
/* 1836 */       if (this.maxRows != 0)
/*      */       {
/* 1838 */         if (fetchRows == 0 || this.row_offset + fetchRows > this.maxRows) {
/* 1839 */           fetchRows = this.maxRows - this.row_offset;
/*      */         }
/*      */       }
/*      */       
/* 1843 */       this.connection.getQueryExecutor().fetch(this.cursor, new CursorResultHandler(this), fetchRows);
/*      */       
/* 1845 */       this.current_row = 0;
/*      */ 
/*      */       
/* 1848 */       if (this.rows.size() == 0)
/*      */       {
/* 1850 */         this.this_row = null;
/* 1851 */         this.rowBuffer = null;
/* 1852 */         return false;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1857 */       this.current_row++;
/*      */     } 
/*      */     
/* 1860 */     this.this_row = this.rows.elementAt(this.current_row);
/*      */     
/* 1862 */     this.rowBuffer = new byte[this.this_row.length][];
/* 1863 */     System.arraycopy(this.this_row, 0, this.rowBuffer, 0, this.this_row.length);
/* 1864 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws SQLException {
/* 1870 */     this.rows = null;
/* 1871 */     if (this.cursor != null) {
/* 1872 */       this.cursor.close();
/* 1873 */       this.cursor = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean wasNull() throws SQLException {
/* 1879 */     checkClosed();
/* 1880 */     return this.wasNullFlag;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getString(int columnIndex) throws SQLException {
/* 1885 */     checkResultSet(columnIndex);
/* 1886 */     this.wasNullFlag = (this.this_row[columnIndex - 1] == null);
/* 1887 */     if (this.wasNullFlag) {
/* 1888 */       return null;
/*      */     }
/* 1890 */     Encoding encoding = this.connection.getEncoding();
/*      */     
/*      */     try {
/* 1893 */       return trimString(columnIndex, encoding.decode(this.this_row[columnIndex - 1]));
/*      */     }
/*      */     catch (IOException ioe) {
/*      */       
/* 1897 */       throw new PSQLException(GT.tr("Invalid character data was found.  This is most likely caused by stored data containing characters that are invalid for the character set the database was created in.  The most common example of this is storing 8bit data in a SQL_ASCII database."), PSQLState.DATA_ERROR, ioe);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int columnIndex) throws SQLException {
/* 1903 */     return toBoolean(getString(columnIndex));
/*      */   }
/*      */   
/* 1906 */   private static final BigInteger BYTEMAX = new BigInteger(Byte.toString(127));
/* 1907 */   private static final BigInteger BYTEMIN = new BigInteger(Byte.toString(-128));
/*      */ 
/*      */   
/*      */   public byte getByte(int columnIndex) throws SQLException {
/* 1911 */     String s = getString(columnIndex);
/*      */     
/* 1913 */     if (s != null) {
/*      */       
/* 1915 */       s = s.trim();
/* 1916 */       if (s.length() == 0) {
/* 1917 */         return 0;
/*      */       }
/*      */       
/*      */       try {
/* 1921 */         return Byte.parseByte(s);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1926 */       catch (NumberFormatException e) {
/*      */         try {
/* 1928 */           BigDecimal n = new BigDecimal(s);
/* 1929 */           BigInteger i = n.toBigInteger();
/*      */           
/* 1931 */           int gt = i.compareTo(BYTEMAX);
/* 1932 */           int lt = i.compareTo(BYTEMIN);
/*      */           
/* 1934 */           if (gt > 0 || lt < 0)
/*      */           {
/* 1936 */             throw new PSQLException(GT.tr("Bad value for type {0} : {1}", new Object[] { "byte", s }), PSQLState.NUMERIC_VALUE_OUT_OF_RANGE);
/*      */           }
/*      */           
/* 1939 */           return i.byteValue();
/*      */         }
/*      */         catch (NumberFormatException ex) {
/*      */           
/* 1943 */           throw new PSQLException(GT.tr("Bad value for type {0} : {1}", new Object[] { "byte", s }), PSQLState.NUMERIC_VALUE_OUT_OF_RANGE);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1948 */     return 0;
/*      */   }
/*      */   
/* 1951 */   private static final BigInteger SHORTMAX = new BigInteger(Short.toString('翿'));
/* 1952 */   private static final BigInteger SHORTMIN = new BigInteger(Short.toString(-32768));
/*      */ 
/*      */   
/*      */   public short getShort(int columnIndex) throws SQLException {
/* 1956 */     String s = getFixedString(columnIndex);
/*      */     
/* 1958 */     if (s != null) {
/*      */       
/* 1960 */       s = s.trim();
/*      */       
/*      */       try {
/* 1963 */         return Short.parseShort(s);
/*      */ 
/*      */       
/*      */       }
/* 1967 */       catch (NumberFormatException e) {
/*      */         try {
/* 1969 */           BigDecimal n = new BigDecimal(s);
/* 1970 */           BigInteger i = n.toBigInteger();
/* 1971 */           int gt = i.compareTo(SHORTMAX);
/* 1972 */           int lt = i.compareTo(SHORTMIN);
/*      */           
/* 1974 */           if (gt > 0 || lt < 0)
/*      */           {
/* 1976 */             throw new PSQLException(GT.tr("Bad value for type {0} : {1}", new Object[] { "short", s }), PSQLState.NUMERIC_VALUE_OUT_OF_RANGE);
/*      */           }
/*      */           
/* 1979 */           return i.shortValue();
/*      */         
/*      */         }
/*      */         catch (NumberFormatException ne) {
/*      */           
/* 1984 */           throw new PSQLException(GT.tr("Bad value for type {0} : {1}", new Object[] { "short", s }), PSQLState.NUMERIC_VALUE_OUT_OF_RANGE);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1989 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(int columnIndex) throws SQLException {
/* 1994 */     return toInt(getFixedString(columnIndex));
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(int columnIndex) throws SQLException {
/* 1999 */     return toLong(getFixedString(columnIndex));
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(int columnIndex) throws SQLException {
/* 2004 */     return toFloat(getFixedString(columnIndex));
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(int columnIndex) throws SQLException {
/* 2009 */     return toDouble(getFixedString(columnIndex));
/*      */   }
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
/* 2014 */     return toBigDecimal(getFixedString(columnIndex), scale);
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
/*      */   public byte[] getBytes(int columnIndex) throws SQLException {
/* 2034 */     checkResultSet(columnIndex);
/* 2035 */     this.wasNullFlag = (this.this_row[columnIndex - 1] == null);
/* 2036 */     if (!this.wasNullFlag) {
/*      */       
/* 2038 */       if (this.fields[columnIndex - 1].getFormat() == 1)
/*      */       {
/*      */         
/* 2041 */         return this.this_row[columnIndex - 1];
/*      */       }
/* 2043 */       if (this.connection.haveMinimumCompatibleVersion("7.2")) {
/*      */ 
/*      */         
/* 2046 */         if (this.fields[columnIndex - 1].getOID() == 17)
/*      */         {
/* 2048 */           return trimBytes(columnIndex, PGbytea.toBytes(this.this_row[columnIndex - 1]));
/*      */         }
/*      */ 
/*      */         
/* 2052 */         return trimBytes(columnIndex, this.this_row[columnIndex - 1]);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2059 */       if (this.fields[columnIndex - 1].getOID() == 26) {
/*      */         
/* 2061 */         LargeObjectManager lom = this.connection.getLargeObjectAPI();
/* 2062 */         LargeObject lob = lom.open(getInt(columnIndex));
/* 2063 */         byte[] buf = lob.read(lob.size());
/* 2064 */         lob.close();
/* 2065 */         return trimBytes(columnIndex, buf);
/*      */       } 
/*      */ 
/*      */       
/* 2069 */       return trimBytes(columnIndex, this.this_row[columnIndex - 1]);
/*      */     } 
/*      */ 
/*      */     
/* 2073 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public Date getDate(int columnIndex) throws SQLException {
/* 2078 */     return getDate(columnIndex, (Calendar)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public Time getTime(int columnIndex) throws SQLException {
/* 2083 */     return getTime(columnIndex, (Calendar)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(int columnIndex) throws SQLException {
/* 2088 */     return getTimestamp(columnIndex, (Calendar)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public InputStream getAsciiStream(int columnIndex) throws SQLException {
/* 2093 */     checkResultSet(columnIndex);
/* 2094 */     this.wasNullFlag = (this.this_row[columnIndex - 1] == null);
/* 2095 */     if (this.wasNullFlag) {
/* 2096 */       return null;
/*      */     }
/* 2098 */     if (this.connection.haveMinimumCompatibleVersion("7.2")) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2108 */         return new ByteArrayInputStream(getString(columnIndex).getBytes("ASCII"));
/*      */       }
/*      */       catch (UnsupportedEncodingException l_uee) {
/*      */         
/* 2112 */         throw new PSQLException(GT.tr("The JVM claims not to support the encoding: {0}", "ASCII"), PSQLState.UNEXPECTED_ERROR, l_uee);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2118 */     return getBinaryStream(columnIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream getUnicodeStream(int columnIndex) throws SQLException {
/* 2124 */     checkResultSet(columnIndex);
/* 2125 */     this.wasNullFlag = (this.this_row[columnIndex - 1] == null);
/* 2126 */     if (this.wasNullFlag) {
/* 2127 */       return null;
/*      */     }
/* 2129 */     if (this.connection.haveMinimumCompatibleVersion("7.2")) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2139 */         return new ByteArrayInputStream(getString(columnIndex).getBytes("UTF-8"));
/*      */       }
/*      */       catch (UnsupportedEncodingException l_uee) {
/*      */         
/* 2143 */         throw new PSQLException(GT.tr("The JVM claims not to support the encoding: {0}", "UTF-8"), PSQLState.UNEXPECTED_ERROR, l_uee);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2149 */     return getBinaryStream(columnIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream getBinaryStream(int columnIndex) throws SQLException {
/* 2155 */     checkResultSet(columnIndex);
/* 2156 */     this.wasNullFlag = (this.this_row[columnIndex - 1] == null);
/* 2157 */     if (this.wasNullFlag) {
/* 2158 */       return null;
/*      */     }
/* 2160 */     if (this.connection.haveMinimumCompatibleVersion("7.2")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2168 */       byte[] b = getBytes(columnIndex);
/* 2169 */       if (b != null) {
/* 2170 */         return new ByteArrayInputStream(b);
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 2175 */     else if (this.fields[columnIndex - 1].getOID() == 26) {
/*      */       
/* 2177 */       LargeObjectManager lom = this.connection.getLargeObjectAPI();
/* 2178 */       LargeObject lob = lom.open(getInt(columnIndex));
/* 2179 */       return lob.getInputStream();
/*      */     } 
/*      */     
/* 2182 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getString(String columnName) throws SQLException {
/* 2187 */     return getString(findColumn(columnName));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(String columnName) throws SQLException {
/* 2192 */     return getBoolean(findColumn(columnName));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getByte(String columnName) throws SQLException {
/* 2198 */     return getByte(findColumn(columnName));
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(String columnName) throws SQLException {
/* 2203 */     return getShort(findColumn(columnName));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(String columnName) throws SQLException {
/* 2208 */     return getInt(findColumn(columnName));
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(String columnName) throws SQLException {
/* 2213 */     return getLong(findColumn(columnName));
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(String columnName) throws SQLException {
/* 2218 */     return getFloat(findColumn(columnName));
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(String columnName) throws SQLException {
/* 2223 */     return getDouble(findColumn(columnName));
/*      */   }
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException {
/* 2228 */     return getBigDecimal(findColumn(columnName), scale);
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] getBytes(String columnName) throws SQLException {
/* 2233 */     return getBytes(findColumn(columnName));
/*      */   }
/*      */ 
/*      */   
/*      */   public Date getDate(String columnName) throws SQLException {
/* 2238 */     return getDate(findColumn(columnName), (Calendar)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public Time getTime(String columnName) throws SQLException {
/* 2243 */     return getTime(findColumn(columnName), (Calendar)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(String columnName) throws SQLException {
/* 2248 */     return getTimestamp(findColumn(columnName), (Calendar)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public InputStream getAsciiStream(String columnName) throws SQLException {
/* 2253 */     return getAsciiStream(findColumn(columnName));
/*      */   }
/*      */ 
/*      */   
/*      */   public InputStream getUnicodeStream(String columnName) throws SQLException {
/* 2258 */     return getUnicodeStream(findColumn(columnName));
/*      */   }
/*      */ 
/*      */   
/*      */   public InputStream getBinaryStream(String columnName) throws SQLException {
/* 2263 */     return getBinaryStream(findColumn(columnName));
/*      */   }
/*      */ 
/*      */   
/*      */   public SQLWarning getWarnings() throws SQLException {
/* 2268 */     checkClosed();
/* 2269 */     return this.warnings;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearWarnings() throws SQLException {
/* 2274 */     checkClosed();
/* 2275 */     this.warnings = null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addWarning(SQLWarning warnings) {
/* 2280 */     if (this.warnings != null) {
/* 2281 */       this.warnings.setNextWarning(warnings);
/*      */     } else {
/* 2283 */       this.warnings = warnings;
/*      */     } 
/*      */   }
/*      */   
/*      */   public String getCursorName() throws SQLException {
/* 2288 */     checkClosed();
/* 2289 */     return null;
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
/*      */   public Object getObject(int columnIndex) throws SQLException {
/* 2310 */     checkResultSet(columnIndex);
/*      */     
/* 2312 */     this.wasNullFlag = (this.this_row[columnIndex - 1] == null);
/* 2313 */     if (this.wasNullFlag) {
/* 2314 */       return null;
/*      */     }
/* 2316 */     Field field = this.fields[columnIndex - 1];
/*      */ 
/*      */     
/* 2319 */     if (field == null) {
/*      */       
/* 2321 */       this.wasNullFlag = true;
/* 2322 */       return null;
/*      */     } 
/*      */     
/* 2325 */     Object result = internalGetObject(columnIndex, field);
/* 2326 */     if (result != null) {
/* 2327 */       return result;
/*      */     }
/* 2329 */     return this.connection.getObject(getPGType(columnIndex), getString(columnIndex));
/*      */   }
/*      */ 
/*      */   
/*      */   public Object getObject(String columnName) throws SQLException {
/* 2334 */     return getObject(findColumn(columnName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int findColumn(String columnName) throws SQLException {
/* 2342 */     checkClosed();
/* 2343 */     if (this.columnNameIndexMap == null) {
/*      */       
/* 2345 */       this.columnNameIndexMap = new HashMap(this.fields.length * 2);
/* 2346 */       for (int i = 0; i < this.fields.length; i++)
/*      */       {
/* 2348 */         this.columnNameIndexMap.put(this.fields[i].getColumnLabel().toLowerCase(), new Integer(i + 1));
/*      */       }
/*      */     } 
/*      */     
/* 2352 */     Integer index = (Integer)this.columnNameIndexMap.get(columnName);
/* 2353 */     if (index != null)
/*      */     {
/* 2355 */       return index.intValue();
/*      */     }
/*      */     
/* 2358 */     index = (Integer)this.columnNameIndexMap.get(columnName.toLowerCase());
/* 2359 */     if (index != null) {
/*      */       
/* 2361 */       this.columnNameIndexMap.put(columnName, index);
/* 2362 */       return index.intValue();
/*      */     } 
/*      */     
/* 2365 */     throw new PSQLException(GT.tr("The column name {0} was not found in this ResultSet.", columnName), PSQLState.UNDEFINED_COLUMN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumnOID(int field) {
/* 2375 */     return this.fields[field - 1].getOID();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFixedString(int col) throws SQLException {
/* 2386 */     String s = getString(col);
/*      */ 
/*      */     
/* 2389 */     this.wasNullFlag = (this.this_row[col - 1] == null);
/* 2390 */     if (this.wasNullFlag) {
/* 2391 */       return null;
/*      */     }
/*      */     
/* 2394 */     if (s.length() < 2) {
/* 2395 */       return s;
/*      */     }
/*      */     
/* 2398 */     char ch = s.charAt(0);
/* 2399 */     if (ch == '(')
/*      */     {
/* 2401 */       s = "-" + PGtokenizer.removePara(s).substring(1);
/*      */     }
/* 2403 */     if (ch == '$') {
/*      */       
/* 2405 */       s = s.substring(1);
/*      */     }
/* 2407 */     else if (ch == '-' && s.charAt(1) == '$') {
/*      */       
/* 2409 */       s = "-" + s.substring(2);
/*      */     } 
/*      */     
/* 2412 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getPGType(int column) throws SQLException {
/* 2417 */     return this.connection.getPGType(this.fields[column - 1].getOID());
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getSQLType(int column) throws SQLException {
/* 2422 */     return this.connection.getSQLType(this.fields[column - 1].getOID());
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkUpdateable() throws SQLException {
/* 2427 */     checkClosed();
/*      */     
/* 2429 */     if (!isUpdateable()) {
/* 2430 */       throw new PSQLException(GT.tr("ResultSet is not updateable.  The query that generated this result set must select only one table, and must select all primary keys from that table. See the JDBC 2.1 API Specification, section 5.6 for more details."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */     
/* 2433 */     if (this.updateValues == null)
/*      */     {
/*      */       
/* 2436 */       this.updateValues = new HashMap((int)(this.fields.length / 0.75D), 0.75F);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void checkClosed() throws SQLException {
/* 2441 */     if (this.rows == null) {
/* 2442 */       throw new PSQLException(GT.tr("This ResultSet is closed."), PSQLState.CONNECTION_DOES_NOT_EXIST);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void checkColumnIndex(int column) throws SQLException {
/* 2447 */     if (column < 1 || column > this.fields.length) {
/* 2448 */       throw new PSQLException(GT.tr("The column index is out of range: {0}, number of columns: {1}.", new Object[] { new Integer(column), new Integer(this.fields.length) }), PSQLState.INVALID_PARAMETER_VALUE);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void checkResultSet(int column) throws SQLException {
/* 2453 */     checkClosed();
/* 2454 */     if (this.this_row == null) {
/* 2455 */       throw new PSQLException(GT.tr("ResultSet not positioned properly, perhaps you need to call next."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/* 2457 */     checkColumnIndex(column);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean toBoolean(String s) {
/* 2464 */     if (s != null) {
/*      */       
/* 2466 */       s = s.trim();
/*      */       
/* 2468 */       if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("t")) {
/* 2469 */         return true;
/*      */       }
/*      */       
/*      */       try {
/* 2473 */         if (Double.valueOf(s).doubleValue() == 1.0D) {
/* 2474 */           return true;
/*      */         }
/*      */       }
/* 2477 */       catch (NumberFormatException e) {}
/*      */     } 
/*      */     
/* 2480 */     return false;
/*      */   }
/*      */   
/* 2483 */   private static final BigInteger INTMAX = new BigInteger(Integer.toString(2147483647));
/* 2484 */   private static final BigInteger INTMIN = new BigInteger(Integer.toString(-2147483648));
/*      */ 
/*      */   
/*      */   public static int toInt(String s) throws SQLException {
/* 2488 */     if (s != null) {
/*      */       
/*      */       try {
/*      */         
/* 2492 */         s = s.trim();
/* 2493 */         return Integer.parseInt(s);
/*      */ 
/*      */       
/*      */       }
/* 2497 */       catch (NumberFormatException e) {
/*      */         try {
/* 2499 */           BigDecimal n = new BigDecimal(s);
/* 2500 */           BigInteger i = n.toBigInteger();
/*      */           
/* 2502 */           int gt = i.compareTo(INTMAX);
/* 2503 */           int lt = i.compareTo(INTMIN);
/*      */           
/* 2505 */           if (gt > 0 || lt < 0)
/*      */           {
/* 2507 */             throw new PSQLException(GT.tr("Bad value for type {0} : {1}", new Object[] { "int", s }), PSQLState.NUMERIC_VALUE_OUT_OF_RANGE);
/*      */           }
/*      */           
/* 2510 */           return i.intValue();
/*      */         
/*      */         }
/*      */         catch (NumberFormatException ne) {
/*      */           
/* 2515 */           throw new PSQLException(GT.tr("Bad value for type {0} : {1}", new Object[] { "int", s }), PSQLState.NUMERIC_VALUE_OUT_OF_RANGE);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 2520 */     return 0;
/*      */   }
/* 2522 */   private static final BigInteger LONGMAX = new BigInteger(Long.toString(Long.MAX_VALUE));
/* 2523 */   private static final BigInteger LONGMIN = new BigInteger(Long.toString(Long.MIN_VALUE));
/*      */ 
/*      */   
/*      */   public static long toLong(String s) throws SQLException {
/* 2527 */     if (s != null) {
/*      */       
/*      */       try {
/*      */         
/* 2531 */         s = s.trim();
/* 2532 */         return Long.parseLong(s);
/*      */ 
/*      */       
/*      */       }
/* 2536 */       catch (NumberFormatException e) {
/*      */         try {
/* 2538 */           BigDecimal n = new BigDecimal(s);
/* 2539 */           BigInteger i = n.toBigInteger();
/* 2540 */           int gt = i.compareTo(LONGMAX);
/* 2541 */           int lt = i.compareTo(LONGMIN);
/*      */           
/* 2543 */           if (gt > 0 || lt < 0)
/*      */           {
/* 2545 */             throw new PSQLException(GT.tr("Bad value for type {0} : {1}", new Object[] { "long", s }), PSQLState.NUMERIC_VALUE_OUT_OF_RANGE);
/*      */           }
/*      */           
/* 2548 */           return i.longValue();
/*      */         }
/*      */         catch (NumberFormatException ne) {
/*      */           
/* 2552 */           throw new PSQLException(GT.tr("Bad value for type {0} : {1}", new Object[] { "long", s }), PSQLState.NUMERIC_VALUE_OUT_OF_RANGE);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 2557 */     return 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal toBigDecimal(String s, int scale) throws SQLException {
/* 2563 */     if (s != null) {
/*      */       BigDecimal val;
/*      */       
/*      */       try {
/* 2567 */         s = s.trim();
/* 2568 */         val = new BigDecimal(s);
/*      */       }
/*      */       catch (NumberFormatException e) {
/*      */         
/* 2572 */         throw new PSQLException(GT.tr("Bad value for type {0} : {1}", new Object[] { "BigDecimal", s }), PSQLState.NUMERIC_VALUE_OUT_OF_RANGE);
/*      */       } 
/*      */       
/* 2575 */       if (scale == -1) {
/* 2576 */         return val;
/*      */       }
/*      */       try {
/* 2579 */         return val.setScale(scale);
/*      */       }
/*      */       catch (ArithmeticException e) {
/*      */         
/* 2583 */         throw new PSQLException(GT.tr("Bad value for type {0} : {1}", new Object[] { "BigDecimal", s }), PSQLState.NUMERIC_VALUE_OUT_OF_RANGE);
/*      */       } 
/*      */     } 
/*      */     
/* 2587 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static float toFloat(String s) throws SQLException {
/* 2592 */     if (s != null) {
/*      */       
/*      */       try {
/*      */         
/* 2596 */         s = s.trim();
/* 2597 */         return Float.parseFloat(s);
/*      */       }
/*      */       catch (NumberFormatException e) {
/*      */         
/* 2601 */         throw new PSQLException(GT.tr("Bad value for type {0} : {1}", new Object[] { "float", s }), PSQLState.NUMERIC_VALUE_OUT_OF_RANGE);
/*      */       } 
/*      */     }
/*      */     
/* 2605 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public static double toDouble(String s) throws SQLException {
/* 2610 */     if (s != null) {
/*      */       
/*      */       try {
/*      */         
/* 2614 */         s = s.trim();
/* 2615 */         return Double.parseDouble(s);
/*      */       }
/*      */       catch (NumberFormatException e) {
/*      */         
/* 2619 */         throw new PSQLException(GT.tr("Bad value for type {0} : {1}", new Object[] { "double", s }), PSQLState.NUMERIC_VALUE_OUT_OF_RANGE);
/*      */       } 
/*      */     }
/*      */     
/* 2623 */     return 0.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isColumnTrimmable(int columnIndex) throws SQLException {
/* 2628 */     switch (getSQLType(columnIndex)) {
/*      */       
/*      */       case -4:
/*      */       case -3:
/*      */       case -2:
/*      */       case -1:
/*      */       case 1:
/*      */       case 12:
/* 2636 */         return true;
/*      */     } 
/* 2638 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] trimBytes(int p_columnIndex, byte[] p_bytes) throws SQLException {
/* 2645 */     if (this.maxFieldSize > 0 && p_bytes.length > this.maxFieldSize && isColumnTrimmable(p_columnIndex)) {
/*      */       
/* 2647 */       byte[] l_bytes = new byte[this.maxFieldSize];
/* 2648 */       System.arraycopy(p_bytes, 0, l_bytes, 0, this.maxFieldSize);
/* 2649 */       return l_bytes;
/*      */     } 
/*      */ 
/*      */     
/* 2653 */     return p_bytes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String trimString(int p_columnIndex, String p_string) throws SQLException {
/* 2661 */     if (this.maxFieldSize > 0 && p_string.length() > this.maxFieldSize && isColumnTrimmable(p_columnIndex))
/*      */     {
/* 2663 */       return p_string.substring(0, this.maxFieldSize);
/*      */     }
/*      */ 
/*      */     
/* 2667 */     return p_string;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateValue(int columnIndex, Object value) throws SQLException {
/* 2672 */     checkUpdateable();
/*      */     
/* 2674 */     if (!this.onInsertRow && (isBeforeFirst() || isAfterLast() || this.rows.size() == 0))
/*      */     {
/* 2676 */       throw new PSQLException(GT.tr("Cannot update the ResultSet because it is either before the start or after the end of the results."), PSQLState.INVALID_CURSOR_STATE);
/*      */     }
/*      */ 
/*      */     
/* 2680 */     checkColumnIndex(columnIndex);
/*      */     
/* 2682 */     this.doingUpdates = !this.onInsertRow;
/* 2683 */     if (value == null) {
/* 2684 */       updateNull(columnIndex);
/*      */     } else {
/* 2686 */       this.updateValues.put(this.fields[columnIndex - 1].getColumnName((Connection)this.connection), value);
/*      */     } 
/*      */   }
/*      */   
/*      */   private class PrimaryKey { int index;
/*      */     String name;
/*      */     private final AbstractJdbc2ResultSet this$0;
/*      */     
/*      */     PrimaryKey(AbstractJdbc2ResultSet this$0, int index, String name) {
/* 2695 */       this.this$0 = this$0;
/* 2696 */       this.index = index;
/* 2697 */       this.name = name;
/*      */     }
/*      */     
/*      */     Object getValue() throws SQLException {
/* 2701 */       return this.this$0.getObject(this.index);
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   class NullObject
/*      */     extends PGobject
/*      */   {
/*      */     private final AbstractJdbc2ResultSet this$0;
/*      */     
/*      */     NullObject(AbstractJdbc2ResultSet this$0, String type) {
/* 2712 */       this.this$0 = this$0;
/* 2713 */       setType(type);
/*      */     }
/*      */     
/*      */     public String getValue() {
/* 2717 */       return null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc2\AbstractJdbc2ResultSet.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */