/*      */ package org.postgresql.core.v3;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.lang.ref.PhantomReference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import org.postgresql.Driver;
/*      */ import org.postgresql.PGNotification;
/*      */ import org.postgresql.core.Field;
/*      */ import org.postgresql.core.Notification;
/*      */ import org.postgresql.core.PGBindException;
/*      */ import org.postgresql.core.PGStream;
/*      */ import org.postgresql.core.ParameterList;
/*      */ import org.postgresql.core.Query;
/*      */ import org.postgresql.core.QueryExecutor;
/*      */ import org.postgresql.core.ResultCursor;
/*      */ import org.postgresql.core.ResultHandler;
/*      */ import org.postgresql.core.Utils;
/*      */ import org.postgresql.util.GT;
/*      */ import org.postgresql.util.PSQLException;
/*      */ import org.postgresql.util.PSQLState;
/*      */ import org.postgresql.util.PSQLWarning;
/*      */ import org.postgresql.util.ServerErrorMessage;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class QueryExecutorImpl
/*      */   implements QueryExecutor
/*      */ {
/*      */   private static final int MAX_BUFFERED_QUERIES = 256;
/*      */   private final HashMap parsedQueryMap;
/*      */   private final ReferenceQueue parsedQueryCleanupQueue;
/*      */   private final HashMap openPortalMap;
/*      */   private final ReferenceQueue openPortalCleanupQueue;
/*      */   private final ArrayList pendingParseQueue;
/*      */   private final ArrayList pendingBindQueue;
/*      */   private final ArrayList pendingExecuteQueue;
/*      */   private final ArrayList pendingDescribeStatementQueue;
/*      */   private long nextUniqueID;
/*      */   private final ProtocolConnectionImpl protoConnection;
/*      */   private final PGStream pgStream;
/*      */   private final boolean allowEncodingChanges;
/*      */   private final SimpleQuery beginTransactionQuery;
/*      */   
/*      */   public QueryExecutorImpl(ProtocolConnectionImpl protoConnection, PGStream pgStream, Properties info) {
/* 1084 */     this.parsedQueryMap = new HashMap();
/* 1085 */     this.parsedQueryCleanupQueue = new ReferenceQueue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1116 */     this.openPortalMap = new HashMap();
/* 1117 */     this.openPortalCleanupQueue = new ReferenceQueue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1587 */     this.pendingParseQueue = new ArrayList();
/* 1588 */     this.pendingBindQueue = new ArrayList();
/* 1589 */     this.pendingExecuteQueue = new ArrayList();
/* 1590 */     this.pendingDescribeStatementQueue = new ArrayList();
/*      */     
/* 1592 */     this.nextUniqueID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1597 */     this.beginTransactionQuery = new SimpleQuery(new String[] { "BEGIN" }); this.protoConnection = protoConnection; this.pgStream = pgStream; if (info.getProperty("allowEncodingChanges") != null) { this.allowEncodingChanges = Boolean.valueOf(info.getProperty("allowEncodingChanges")).booleanValue(); }
/*      */     else { this.allowEncodingChanges = false; }
/* 1599 */      } private static final SimpleQuery EMPTY_QUERY = new SimpleQuery(new String[] { "" });
/*      */   
/*      */   public Query createSimpleQuery(String sql) {
/*      */     return parseQuery(sql, false);
/*      */   }
/*      */   
/*      */   public Query createParameterizedQuery(String sql) {
/*      */     return parseQuery(sql, true);
/*      */   }
/*      */   
/*      */   private static Query parseQuery(String query, boolean withParameters) {
/*      */     ArrayList statementList = new ArrayList();
/*      */     ArrayList fragmentList = new ArrayList(15);
/*      */     boolean inQuotes = false;
/*      */     int fragmentStart = 0;
/*      */     boolean inSingleQuotes = false;
/*      */     boolean inDoubleQuotes = false;
/*      */     int inParen = 0;
/*      */     char[] aChars = query.toCharArray();
/*      */     for (int i = 0; i < aChars.length; i++) {
/*      */       char c = aChars[i];
/*      */       switch (c) {
/*      */         case '\\':
/*      */           if (inSingleQuotes)
/*      */             i++; 
/*      */           break;
/*      */         case '\'':
/*      */           inSingleQuotes = (!inDoubleQuotes && !inSingleQuotes);
/*      */           break;
/*      */         case '"':
/*      */           inDoubleQuotes = (!inSingleQuotes && !inDoubleQuotes);
/*      */           break;
/*      */         case '?':
/*      */           if (withParameters && !inSingleQuotes && !inDoubleQuotes) {
/*      */             fragmentList.add(query.substring(fragmentStart, i));
/*      */             fragmentStart = i + 1;
/*      */           } 
/*      */           break;
/*      */         case '(':
/*      */           if (!inSingleQuotes && !inDoubleQuotes)
/*      */             inParen++; 
/*      */           break;
/*      */         case ')':
/*      */           if (!inSingleQuotes && !inDoubleQuotes)
/*      */             inParen--; 
/*      */           break;
/*      */         case ';':
/*      */           if (!inSingleQuotes && !inDoubleQuotes && inParen == 0) {
/*      */             fragmentList.add(query.substring(fragmentStart, i));
/*      */             fragmentStart = i + 1;
/*      */             if (fragmentList.size() > 1 || ((String)fragmentList.get(0)).trim().length() > 0)
/*      */               statementList.add(fragmentList.toArray(new String[fragmentList.size()])); 
/*      */             fragmentList.clear();
/*      */           } 
/*      */           break;
/*      */       } 
/*      */     } 
/*      */     fragmentList.add(query.substring(fragmentStart));
/*      */     if (fragmentList.size() > 1 || ((String)fragmentList.get(0)).trim().length() > 0)
/*      */       statementList.add(fragmentList.toArray(new String[fragmentList.size()])); 
/*      */     if (statementList.isEmpty())
/*      */       return EMPTY_QUERY; 
/*      */     if (statementList.size() == 1)
/*      */       return new SimpleQuery(statementList.get(0)); 
/*      */     SimpleQuery[] subqueries = new SimpleQuery[statementList.size()];
/*      */     int[] offsets = new int[statementList.size()];
/*      */     int offset = 0;
/*      */     for (int j = 0; j < statementList.size(); j++) {
/*      */       String[] fragments = statementList.get(j);
/*      */       offsets[j] = offset;
/*      */       subqueries[j] = new SimpleQuery(fragments);
/*      */       offset += fragments.length - 1;
/*      */     } 
/*      */     return new CompositeQuery(subqueries, offsets);
/*      */   }
/*      */   
/*      */   public synchronized void execute(Query query, ParameterList parameters, ResultHandler handler, int maxRows, int fetchSize, int flags) throws SQLException {
/*      */     if (Driver.logDebug)
/*      */       Driver.debug("simple execute, handler=" + handler + ", maxRows=" + maxRows + ", fetchSize=" + fetchSize + ", flags=" + flags); 
/*      */     if (parameters == null)
/*      */       parameters = SimpleQuery.NO_PARAMETERS; 
/*      */     boolean describeOnly = ((0x20 & flags) != 0);
/*      */     if (!describeOnly)
/*      */       ((V3ParameterList)parameters).checkAllParametersSet(); 
/*      */     try {
/*      */       try {
/*      */         handler = sendQueryPreamble(handler, flags);
/*      */         sendQuery((V3Query)query, (V3ParameterList)parameters, maxRows, fetchSize, flags);
/*      */         sendSync();
/*      */         processResults(handler, flags);
/*      */       } catch (PGBindException se) {
/*      */         sendSync();
/*      */         processResults(handler, flags);
/*      */         handler.handleError((SQLException)new PSQLException(GT.tr("Unable to bind parameter values for statement."), PSQLState.INVALID_PARAMETER_VALUE, se.getIOException()));
/*      */       } 
/*      */     } catch (IOException e) {
/*      */       this.protoConnection.close();
/*      */       handler.handleError((SQLException)new PSQLException(GT.tr("An I/O error occured while sending to the backend."), PSQLState.CONNECTION_FAILURE, e));
/*      */     } 
/*      */     handler.handleCompletion();
/*      */   }
/*      */   
/*      */   private static class ErrorTrackingResultHandler implements ResultHandler {
/*      */     private final ResultHandler delegateHandler;
/*      */     private boolean sawError = false;
/*      */     
/*      */     ErrorTrackingResultHandler(ResultHandler delegateHandler) {
/*      */       this.delegateHandler = delegateHandler;
/*      */     }
/*      */     
/*      */     public void handleResultRows(Query fromQuery, Field[] fields, Vector tuples, ResultCursor cursor) {
/*      */       this.delegateHandler.handleResultRows(fromQuery, fields, tuples, cursor);
/*      */     }
/*      */     
/*      */     public void handleCommandStatus(String status, int updateCount, long insertOID) {
/*      */       this.delegateHandler.handleCommandStatus(status, updateCount, insertOID);
/*      */     }
/*      */     
/*      */     public void handleWarning(SQLWarning warning) {
/*      */       this.delegateHandler.handleWarning(warning);
/*      */     }
/*      */     
/*      */     public void handleError(SQLException error) {
/*      */       this.sawError = true;
/*      */       this.delegateHandler.handleError(error);
/*      */     }
/*      */     
/*      */     public void handleCompletion() throws SQLException {
/*      */       this.delegateHandler.handleCompletion();
/*      */     }
/*      */     
/*      */     boolean hasErrors() {
/*      */       return this.sawError;
/*      */     }
/*      */   }
/*      */   
/*      */   public synchronized void execute(Query[] queries, ParameterList[] parameterLists, ResultHandler handler, int maxRows, int fetchSize, int flags) throws SQLException {
/*      */     if (Driver.logDebug)
/*      */       Driver.debug("batch execute " + queries.length + " queries, handler=" + handler + ", maxRows=" + maxRows + ", fetchSize=" + fetchSize + ", flags=" + flags); 
/*      */     boolean describeOnly = ((0x20 & flags) != 0);
/*      */     if (!describeOnly)
/*      */       for (int i = 0; i < parameterLists.length; i++) {
/*      */         if (parameterLists[i] != null)
/*      */           ((V3ParameterList)parameterLists[i]).checkAllParametersSet(); 
/*      */       }  
/*      */     try {
/*      */       int queryCount = 0;
/*      */       handler = sendQueryPreamble(handler, flags);
/*      */       ErrorTrackingResultHandler trackingHandler = new ErrorTrackingResultHandler(handler);
/*      */       for (int i = 0; i < queries.length; i++) {
/*      */         queryCount++;
/*      */         if (queryCount >= 256) {
/*      */           sendSync();
/*      */           processResults(trackingHandler, flags);
/*      */           if (trackingHandler.hasErrors())
/*      */             break; 
/*      */           queryCount = 0;
/*      */         } 
/*      */         V3Query query = (V3Query)queries[i];
/*      */         V3ParameterList parameters = (V3ParameterList)parameterLists[i];
/*      */         if (parameters == null)
/*      */           parameters = SimpleQuery.NO_PARAMETERS; 
/*      */         sendQuery(query, parameters, maxRows, fetchSize, flags);
/*      */       } 
/*      */       if (!trackingHandler.hasErrors()) {
/*      */         sendSync();
/*      */         processResults(handler, flags);
/*      */       } 
/*      */     } catch (IOException e) {
/*      */       this.protoConnection.close();
/*      */       handler.handleError((SQLException)new PSQLException(GT.tr("An I/O error occured while sending to the backend."), PSQLState.CONNECTION_FAILURE, e));
/*      */     } 
/*      */     handler.handleCompletion();
/*      */   }
/*      */   
/*      */   private ResultHandler sendQueryPreamble(ResultHandler delegateHandler, int flags) throws IOException {
/*      */     processDeadParsedQueries();
/*      */     processDeadPortals();
/*      */     if ((flags & 0x10) != 0 || this.protoConnection.getTransactionState() != 0)
/*      */       return delegateHandler; 
/*      */     sendOneQuery(this.beginTransactionQuery, SimpleQuery.NO_PARAMETERS, 0, 0, 2);
/*      */     return new ResultHandler(this, delegateHandler) {
/*      */         private boolean sawBegin;
/*      */         private final ResultHandler val$delegateHandler;
/*      */         private final QueryExecutorImpl this$0;
/*      */         
/*      */         public void handleResultRows(Query fromQuery, Field[] fields, Vector tuples, ResultCursor cursor) {
/*      */           if (this.sawBegin)
/*      */             this.val$delegateHandler.handleResultRows(fromQuery, fields, tuples, cursor); 
/*      */         }
/*      */         
/*      */         public void handleCommandStatus(String status, int updateCount, long insertOID) {
/*      */           if (!this.sawBegin) {
/*      */             this.sawBegin = true;
/*      */             if (!status.equals("BEGIN"))
/*      */               handleError((SQLException)new PSQLException(GT.tr("Expected command status BEGIN, got {0}.", status), PSQLState.PROTOCOL_VIOLATION)); 
/*      */           } else {
/*      */             this.val$delegateHandler.handleCommandStatus(status, updateCount, insertOID);
/*      */           } 
/*      */         }
/*      */         
/*      */         public void handleWarning(SQLWarning warning) {
/*      */           this.val$delegateHandler.handleWarning(warning);
/*      */         }
/*      */         
/*      */         public void handleError(SQLException error) {
/*      */           this.val$delegateHandler.handleError(error);
/*      */         }
/*      */         
/*      */         public void handleCompletion() throws SQLException {
/*      */           this.val$delegateHandler.handleCompletion();
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public synchronized byte[] fastpathCall(int fnid, ParameterList parameters, boolean suppressBegin) throws SQLException {
/*      */     if (this.protoConnection.getTransactionState() == 0 && !suppressBegin) {
/*      */       if (Driver.logDebug)
/*      */         Driver.debug("Issuing BEGIN before fastpath call."); 
/*      */       ResultHandler handler = new ResultHandler(this) {
/*      */           private boolean sawBegin;
/*      */           private SQLException sqle;
/*      */           private final QueryExecutorImpl this$0;
/*      */           
/*      */           public void handleResultRows(Query fromQuery, Field[] fields, Vector tuples, ResultCursor cursor) {}
/*      */           
/*      */           public void handleCommandStatus(String status, int updateCount, long insertOID) {
/*      */             if (!this.sawBegin) {
/*      */               if (!status.equals("BEGIN"))
/*      */                 handleError((SQLException)new PSQLException(GT.tr("Expected command status BEGIN, got {0}.", status), PSQLState.PROTOCOL_VIOLATION)); 
/*      */               this.sawBegin = true;
/*      */             } else {
/*      */               handleError((SQLException)new PSQLException(GT.tr("Unexpected command status: {0}.", status), PSQLState.PROTOCOL_VIOLATION));
/*      */             } 
/*      */           }
/*      */           
/*      */           public void handleWarning(SQLWarning warning) {
/*      */             handleError(warning);
/*      */           }
/*      */           
/*      */           public void handleError(SQLException error) {
/*      */             if (this.sqle == null) {
/*      */               this.sqle = error;
/*      */             } else {
/*      */               this.sqle.setNextException(error);
/*      */             } 
/*      */           }
/*      */           
/*      */           public void handleCompletion() throws SQLException {
/*      */             if (this.sqle != null)
/*      */               throw this.sqle; 
/*      */           }
/*      */         };
/*      */       try {
/*      */         sendOneQuery(this.beginTransactionQuery, SimpleQuery.NO_PARAMETERS, 0, 0, 2);
/*      */         sendSync();
/*      */         processResults(handler, 0);
/*      */       } catch (IOException ioe) {
/*      */         throw new PSQLException(GT.tr("An I/O error occured while sending to the backend."), PSQLState.CONNECTION_FAILURE, ioe);
/*      */       } 
/*      */     } 
/*      */     try {
/*      */       sendFastpathCall(fnid, (SimpleParameterList)parameters);
/*      */       return receiveFastpathResult();
/*      */     } catch (IOException ioe) {
/*      */       this.protoConnection.close();
/*      */       throw new PSQLException(GT.tr("An I/O error occured while sending to the backend."), PSQLState.CONNECTION_FAILURE, ioe);
/*      */     } 
/*      */   }
/*      */   
/*      */   public ParameterList createFastpathParameters(int count) {
/*      */     return new SimpleParameterList(count);
/*      */   }
/*      */   
/*      */   private void sendFastpathCall(int fnid, SimpleParameterList params) throws SQLException, IOException {
/*      */     if (Driver.logDebug)
/*      */       Driver.debug(" FE=> FunctionCall(" + fnid + ", " + params.getParameterCount() + " params)"); 
/*      */     int paramCount = params.getParameterCount();
/*      */     int encodedSize = 0;
/*      */     for (int i = 1; i <= paramCount; i++) {
/*      */       if (params.isNull(i)) {
/*      */         encodedSize += 4;
/*      */       } else {
/*      */         encodedSize += 4 + params.getV3Length(i);
/*      */       } 
/*      */     } 
/*      */     this.pgStream.SendChar(70);
/*      */     this.pgStream.SendInteger4(10 + 2 * paramCount + 2 + encodedSize + 2);
/*      */     this.pgStream.SendInteger4(fnid);
/*      */     this.pgStream.SendInteger2(paramCount);
/*      */     for (int j = 1; j <= paramCount; j++)
/*      */       this.pgStream.SendInteger2(params.isBinary(j) ? 1 : 0); 
/*      */     this.pgStream.SendInteger2(paramCount);
/*      */     for (int k = 1; k <= paramCount; k++) {
/*      */       if (params.isNull(k)) {
/*      */         this.pgStream.SendInteger4(-1);
/*      */       } else {
/*      */         this.pgStream.SendInteger4(params.getV3Length(k));
/*      */         params.writeV3Value(k, this.pgStream);
/*      */       } 
/*      */     } 
/*      */     this.pgStream.SendInteger2(1);
/*      */     this.pgStream.flush();
/*      */   }
/*      */   
/*      */   public synchronized void processNotifies() throws SQLException {
/*      */     if (this.protoConnection.getTransactionState() != 0)
/*      */       return; 
/*      */     try {
/*      */       while (this.pgStream.hasMessagePending()) {
/*      */         SQLWarning warning;
/*      */         int c = this.pgStream.ReceiveChar();
/*      */         switch (c) {
/*      */           case 65:
/*      */             receiveAsyncNotify();
/*      */             continue;
/*      */           case 69:
/*      */             throw receiveErrorResponse();
/*      */           case 78:
/*      */             warning = receiveNoticeResponse();
/*      */             this.protoConnection.addWarning(warning);
/*      */             continue;
/*      */         } 
/*      */         throw new PSQLException(GT.tr("Unknown Response Type {0}.", new Character((char)c)), PSQLState.CONNECTION_FAILURE);
/*      */       } 
/*      */     } catch (IOException ioe) {
/*      */       throw new PSQLException(GT.tr("An I/O error occured while sending to the backend."), PSQLState.CONNECTION_FAILURE, ioe);
/*      */     } 
/*      */   }
/*      */   
/*      */   private byte[] receiveFastpathResult() throws IOException, SQLException {
/*      */     boolean endQuery = false;
/*      */     SQLException error = null;
/*      */     byte[] returnValue = null;
/*      */     while (!endQuery) {
/*      */       SQLException newError;
/*      */       SQLWarning warning;
/*      */       int msgLen, valueLen, c = this.pgStream.ReceiveChar();
/*      */       switch (c) {
/*      */         case 65:
/*      */           receiveAsyncNotify();
/*      */           continue;
/*      */         case 69:
/*      */           newError = receiveErrorResponse();
/*      */           if (error == null) {
/*      */             error = newError;
/*      */             continue;
/*      */           } 
/*      */           error.setNextException(newError);
/*      */           continue;
/*      */         case 78:
/*      */           warning = receiveNoticeResponse();
/*      */           this.protoConnection.addWarning(warning);
/*      */           continue;
/*      */         case 90:
/*      */           receiveRFQ();
/*      */           endQuery = true;
/*      */           continue;
/*      */         case 86:
/*      */           msgLen = this.pgStream.ReceiveIntegerR(4);
/*      */           valueLen = this.pgStream.ReceiveIntegerR(4);
/*      */           if (Driver.logDebug)
/*      */             Driver.debug(" <=BE FunctionCallResponse(" + valueLen + " bytes)"); 
/*      */           if (valueLen != -1) {
/*      */             byte[] buf = new byte[valueLen];
/*      */             this.pgStream.Receive(buf, 0, valueLen);
/*      */             returnValue = buf;
/*      */           } 
/*      */           continue;
/*      */       } 
/*      */       throw new PSQLException(GT.tr("Unknown Response Type {0}.", new Character((char)c)), PSQLState.CONNECTION_FAILURE);
/*      */     } 
/*      */     if (error != null)
/*      */       throw error; 
/*      */     return returnValue;
/*      */   }
/*      */   
/*      */   private void sendQuery(V3Query query, V3ParameterList parameters, int maxRows, int fetchSize, int flags) throws IOException, SQLException {
/*      */     SimpleQuery[] subqueries = query.getSubqueries();
/*      */     SimpleParameterList[] subparams = parameters.getSubparams();
/*      */     if (subqueries == null) {
/*      */       sendOneQuery((SimpleQuery)query, (SimpleParameterList)parameters, maxRows, fetchSize, flags);
/*      */     } else {
/*      */       for (int i = 0; i < subqueries.length; i++) {
/*      */         SimpleParameterList subparam = SimpleQuery.NO_PARAMETERS;
/*      */         if (subparams != null)
/*      */           subparam = subparams[i]; 
/*      */         sendOneQuery(subqueries[i], subparam, maxRows, fetchSize, flags);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void sendSync() throws IOException {
/*      */     if (Driver.logDebug)
/*      */       Driver.debug(" FE=> Sync"); 
/*      */     this.pgStream.SendChar(83);
/*      */     this.pgStream.SendInteger4(4);
/*      */     this.pgStream.flush();
/*      */   }
/*      */   
/*      */   private void sendParse(SimpleQuery query, SimpleParameterList params, boolean oneShot) throws IOException {
/*      */     int[] typeOIDs = params.getTypeOIDs();
/*      */     if (query.isPreparedFor(typeOIDs))
/*      */       return; 
/*      */     query.unprepare();
/*      */     String statementName = null;
/*      */     if (!oneShot) {
/*      */       statementName = "S_" + this.nextUniqueID++;
/*      */       query.setStatementName(statementName);
/*      */       query.setStatementTypes((int[])typeOIDs.clone());
/*      */     } 
/*      */     byte[] encodedStatementName = query.getEncodedStatementName();
/*      */     String[] fragments = query.getFragments();
/*      */     if (Driver.logDebug) {
/*      */       StringBuffer sbuf = new StringBuffer(" FE=> Parse(stmt=" + statementName + ",query=\"");
/*      */       for (int n = 0; n < fragments.length; n++) {
/*      */         if (n > 0)
/*      */           sbuf.append("$" + n); 
/*      */         sbuf.append(fragments[n]);
/*      */       } 
/*      */       sbuf.append("\",oids={");
/*      */       for (int i1 = 1; i1 <= params.getParameterCount(); i1++) {
/*      */         if (i1 != 1)
/*      */           sbuf.append(","); 
/*      */         sbuf.append("" + params.getTypeOID(i1));
/*      */       } 
/*      */       sbuf.append("})");
/*      */       Driver.debug(sbuf.toString());
/*      */     } 
/*      */     byte[][] parts = new byte[fragments.length * 2 - 1][];
/*      */     int j = 0;
/*      */     int encodedSize = 0;
/*      */     for (int i = 0; i < fragments.length; i++) {
/*      */       if (i != 0) {
/*      */         parts[j] = Utils.encodeUTF8("$" + i);
/*      */         encodedSize += (parts[j]).length;
/*      */         j++;
/*      */       } 
/*      */       parts[j] = Utils.encodeUTF8(fragments[i]);
/*      */       encodedSize += (parts[j]).length;
/*      */       j++;
/*      */     } 
/*      */     encodedSize = 4 + ((encodedStatementName == null) ? 0 : encodedStatementName.length) + 1 + encodedSize + 1 + 2 + 4 * params.getParameterCount();
/*      */     this.pgStream.SendChar(80);
/*      */     this.pgStream.SendInteger4(encodedSize);
/*      */     if (encodedStatementName != null)
/*      */       this.pgStream.Send(encodedStatementName); 
/*      */     this.pgStream.SendChar(0);
/*      */     for (int k = 0; k < parts.length; k++)
/*      */       this.pgStream.Send(parts[k]); 
/*      */     this.pgStream.SendChar(0);
/*      */     this.pgStream.SendInteger2(params.getParameterCount());
/*      */     for (int m = 1; m <= params.getParameterCount(); m++)
/*      */       this.pgStream.SendInteger4(params.getTypeOID(m)); 
/*      */     this.pendingParseQueue.add(query);
/*      */   }
/*      */   
/*      */   private void sendBind(SimpleQuery query, SimpleParameterList params, Portal portal) throws IOException {
/*      */     String statementName = query.getStatementName();
/*      */     byte[] encodedStatementName = query.getEncodedStatementName();
/*      */     byte[] encodedPortalName = (portal == null) ? null : portal.getEncodedPortalName();
/*      */     if (Driver.logDebug) {
/*      */       StringBuffer sbuf = new StringBuffer(" FE=> Bind(stmt=" + statementName + ",portal=" + portal);
/*      */       for (int m = 1; m <= params.getParameterCount(); m++)
/*      */         sbuf.append(",$" + m + "=<" + params.toString(m) + ">"); 
/*      */       sbuf.append(")");
/*      */       Driver.debug(sbuf.toString());
/*      */     } 
/*      */     long encodedSize = 0L;
/*      */     for (int i = 1; i <= params.getParameterCount(); i++) {
/*      */       if (params.isNull(i)) {
/*      */         encodedSize += 4L;
/*      */       } else {
/*      */         encodedSize += 4L + params.getV3Length(i);
/*      */       } 
/*      */     } 
/*      */     encodedSize = (4 + ((encodedPortalName == null) ? 0 : encodedPortalName.length) + 1 + ((encodedStatementName == null) ? 0 : encodedStatementName.length) + 1 + 2 + params.getParameterCount() * 2 + 2) + encodedSize + 2L;
/*      */     if (encodedSize > 1073741823L)
/*      */       throw new PGBindException(new IOException(GT.tr("Bind message length {0} too long.  This can be caused by very large or incorrect length specifications on InputStream parameters.", new Long(encodedSize)))); 
/*      */     this.pgStream.SendChar(66);
/*      */     this.pgStream.SendInteger4((int)encodedSize);
/*      */     if (encodedPortalName != null)
/*      */       this.pgStream.Send(encodedPortalName); 
/*      */     this.pgStream.SendChar(0);
/*      */     if (encodedStatementName != null)
/*      */       this.pgStream.Send(encodedStatementName); 
/*      */     this.pgStream.SendChar(0);
/*      */     this.pgStream.SendInteger2(params.getParameterCount());
/*      */     for (int j = 1; j <= params.getParameterCount(); j++)
/*      */       this.pgStream.SendInteger2(params.isBinary(j) ? 1 : 0); 
/*      */     this.pgStream.SendInteger2(params.getParameterCount());
/*      */     PGBindException bindException = null;
/*      */     for (int k = 1; k <= params.getParameterCount(); k++) {
/*      */       if (params.isNull(k)) {
/*      */         this.pgStream.SendInteger4(-1);
/*      */       } else {
/*      */         this.pgStream.SendInteger4(params.getV3Length(k));
/*      */         try {
/*      */           params.writeV3Value(k, this.pgStream);
/*      */         } catch (PGBindException be) {
/*      */           bindException = be = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     this.pgStream.SendChar(0);
/*      */     this.pgStream.SendChar(0);
/*      */     this.pendingBindQueue.add(portal);
/*      */     if (bindException != null)
/*      */       throw bindException; 
/*      */   }
/*      */   
/*      */   private void sendDescribePortal(Portal portal) throws IOException {
/*      */     if (Driver.logDebug)
/*      */       Driver.debug(" FE=> Describe(portal=" + portal + ")"); 
/*      */     byte[] encodedPortalName = (portal == null) ? null : portal.getEncodedPortalName();
/*      */     int encodedSize = 5 + ((encodedPortalName == null) ? 0 : encodedPortalName.length) + 1;
/*      */     this.pgStream.SendChar(68);
/*      */     this.pgStream.SendInteger4(encodedSize);
/*      */     this.pgStream.SendChar(80);
/*      */     if (encodedPortalName != null)
/*      */       this.pgStream.Send(encodedPortalName); 
/*      */     this.pgStream.SendChar(0);
/*      */   }
/*      */   
/*      */   private void sendDescribeStatement(SimpleQuery query, SimpleParameterList params, boolean describeOnly) throws IOException {
/*      */     if (Driver.logDebug)
/*      */       Driver.debug(" FE=> Describe(statement=" + query.getStatementName() + ")"); 
/*      */     byte[] encodedStatementName = query.getEncodedStatementName();
/*      */     int encodedSize = 5 + ((encodedStatementName == null) ? 0 : encodedStatementName.length) + 1;
/*      */     this.pgStream.SendChar(68);
/*      */     this.pgStream.SendInteger4(encodedSize);
/*      */     this.pgStream.SendChar(83);
/*      */     if (encodedStatementName != null)
/*      */       this.pgStream.Send(encodedStatementName); 
/*      */     this.pgStream.SendChar(0);
/*      */     this.pendingDescribeStatementQueue.add(new Object[] { query, params, new Boolean(describeOnly) });
/*      */   }
/*      */   
/*      */   private void sendExecute(Query query, Portal portal, int limit) throws IOException {
/*      */     if (Driver.logDebug)
/*      */       Driver.debug(" FE=> Execute(portal=" + portal + ",limit=" + limit + ")"); 
/*      */     byte[] encodedPortalName = (portal == null) ? null : portal.getEncodedPortalName();
/*      */     int encodedSize = (encodedPortalName == null) ? 0 : encodedPortalName.length;
/*      */     this.pgStream.SendChar(69);
/*      */     this.pgStream.SendInteger4(5 + encodedSize + 4);
/*      */     if (encodedPortalName != null)
/*      */       this.pgStream.Send(encodedPortalName); 
/*      */     this.pgStream.SendChar(0);
/*      */     this.pgStream.SendInteger4(limit);
/*      */     this.pendingExecuteQueue.add(new Object[] { query, portal });
/*      */   }
/*      */   
/*      */   private void sendClosePortal(String portalName) throws IOException {
/*      */     if (Driver.logDebug)
/*      */       Driver.debug(" FE=> ClosePortal(" + portalName + ")"); 
/*      */     byte[] encodedPortalName = (portalName == null) ? null : Utils.encodeUTF8(portalName);
/*      */     int encodedSize = (encodedPortalName == null) ? 0 : encodedPortalName.length;
/*      */     this.pgStream.SendChar(67);
/*      */     this.pgStream.SendInteger4(6 + encodedSize);
/*      */     this.pgStream.SendChar(80);
/*      */     if (encodedPortalName != null)
/*      */       this.pgStream.Send(encodedPortalName); 
/*      */     this.pgStream.SendChar(0);
/*      */   }
/*      */   
/*      */   private void sendCloseStatement(String statementName) throws IOException {
/*      */     if (Driver.logDebug)
/*      */       Driver.debug(" FE=> CloseStatement(" + statementName + ")"); 
/*      */     byte[] encodedStatementName = Utils.encodeUTF8(statementName);
/*      */     this.pgStream.SendChar(67);
/*      */     this.pgStream.SendInteger4(5 + encodedStatementName.length + 1);
/*      */     this.pgStream.SendChar(83);
/*      */     this.pgStream.Send(encodedStatementName);
/*      */     this.pgStream.SendChar(0);
/*      */   }
/*      */   
/*      */   private void sendOneQuery(SimpleQuery query, SimpleParameterList params, int maxRows, int fetchSize, int flags) throws IOException {
/*      */     int i;
/*      */     boolean noResults = ((flags & 0x4) != 0);
/*      */     boolean noMeta = ((flags & 0x2) != 0);
/*      */     boolean describeOnly = ((flags & 0x20) != 0);
/*      */     boolean usePortal = ((flags & 0x8) != 0 && !noResults && !noMeta && fetchSize > 0 && !describeOnly);
/*      */     boolean oneShot = ((flags & 0x1) != 0 && !usePortal);
/*      */     boolean describeStatement = (describeOnly || (params.hasUnresolvedTypes() && !oneShot));
/*      */     if (noResults) {
/*      */       i = 1;
/*      */     } else if (!usePortal) {
/*      */       i = maxRows;
/*      */     } else if (maxRows != 0 && fetchSize > maxRows) {
/*      */       i = maxRows;
/*      */     } else {
/*      */       i = fetchSize;
/*      */     } 
/*      */     sendParse(query, params, oneShot);
/*      */     if (describeStatement) {
/*      */       sendDescribeStatement(query, params, describeOnly);
/*      */       if (describeOnly)
/*      */         return; 
/*      */     } 
/*      */     Portal portal = null;
/*      */     if (usePortal) {
/*      */       String portalName = "C_" + this.nextUniqueID++;
/*      */       portal = new Portal(query, portalName);
/*      */     } 
/*      */     sendBind(query, params, portal);
/*      */     if (!noMeta && !describeStatement)
/*      */       sendDescribePortal(portal); 
/*      */     sendExecute(query, portal, i);
/*      */   }
/*      */   
/*      */   private void registerParsedQuery(SimpleQuery query) {
/*      */     String statementName = query.getStatementName();
/*      */     if (statementName == null)
/*      */       return; 
/*      */     PhantomReference cleanupRef = new PhantomReference(query, this.parsedQueryCleanupQueue);
/*      */     this.parsedQueryMap.put(cleanupRef, statementName);
/*      */     query.setCleanupRef(cleanupRef);
/*      */   }
/*      */   
/*      */   private void processDeadParsedQueries() throws IOException {
/*      */     PhantomReference deadQuery;
/*      */     while ((deadQuery = (PhantomReference)this.parsedQueryCleanupQueue.poll()) != null) {
/*      */       String statementName = (String)this.parsedQueryMap.remove(deadQuery);
/*      */       sendCloseStatement(statementName);
/*      */       deadQuery.clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void registerOpenPortal(Portal portal) {
/*      */     if (portal == null)
/*      */       return; 
/*      */     String portalName = portal.getPortalName();
/*      */     PhantomReference cleanupRef = new PhantomReference(portal, this.openPortalCleanupQueue);
/*      */     this.openPortalMap.put(cleanupRef, portalName);
/*      */     portal.setCleanupRef(cleanupRef);
/*      */   }
/*      */   
/*      */   private void processDeadPortals() throws IOException {
/*      */     PhantomReference deadPortal;
/*      */     while ((deadPortal = (PhantomReference)this.openPortalCleanupQueue.poll()) != null) {
/*      */       String portalName = (String)this.openPortalMap.remove(deadPortal);
/*      */       sendClosePortal(portalName);
/*      */       deadPortal.clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void processResults(ResultHandler handler, int flags) throws IOException {
/*      */     boolean noResults = ((flags & 0x4) != 0);
/*      */     Field[] fields = null;
/*      */     Vector tuples = null;
/*      */     boolean endQuery = false;
/*      */     boolean doneAfterRowDescNoData = false;
/*      */     int parseIndex = 0;
/*      */     int describeIndex = 0;
/*      */     int bindIndex = 0;
/*      */     int executeIndex = 0;
/*      */     while (!endQuery) {
/*      */       SimpleQuery parsedQuery;
/*      */       Object[] describeData;
/*      */       Portal boundPortal;
/*      */       SimpleQuery query;
/*      */       Object[] executeData;
/*      */       String status;
/*      */       SimpleParameterList params;
/*      */       Query currentQuery;
/*      */       Object arrayOfObject1[], tuple;
/*      */       boolean describeOnly;
/*      */       Portal currentPortal;
/*      */       Query query1;
/*      */       SQLException error;
/*      */       int numParams;
/*      */       Portal portal1;
/*      */       Object[] arrayOfObject2;
/*      */       SQLWarning warning;
/*      */       int i;
/*      */       Query query2;
/*      */       int l_len;
/*      */       Portal portal2;
/*      */       String name, value;
/*      */       int c = this.pgStream.ReceiveChar();
/*      */       switch (c) {
/*      */         case 65:
/*      */           receiveAsyncNotify();
/*      */           continue;
/*      */         case 49:
/*      */           this.pgStream.ReceiveIntegerR(4);
/*      */           parsedQuery = this.pendingParseQueue.get(parseIndex++);
/*      */           if (Driver.logDebug)
/*      */             Driver.debug(" <=BE ParseComplete [" + parsedQuery.getStatementName() + "]"); 
/*      */           registerParsedQuery(parsedQuery);
/*      */           continue;
/*      */         case 116:
/*      */           this.pgStream.ReceiveIntegerR(4);
/*      */           if (Driver.logDebug)
/*      */             Driver.debug(" <=BE ParameterDescription"); 
/*      */           describeData = this.pendingDescribeStatementQueue.get(describeIndex);
/*      */           query = (SimpleQuery)describeData[0];
/*      */           params = (SimpleParameterList)describeData[1];
/*      */           describeOnly = ((Boolean)describeData[2]).booleanValue();
/*      */           numParams = this.pgStream.ReceiveIntegerR(2);
/*      */           for (i = 1; i <= numParams; i++) {
/*      */             int typeOid = this.pgStream.ReceiveIntegerR(4);
/*      */             params.setResolvedType(i, typeOid);
/*      */           } 
/*      */           query.setStatementTypes((int[])params.getTypeOIDs().clone());
/*      */           if (describeOnly) {
/*      */             doneAfterRowDescNoData = true;
/*      */             continue;
/*      */           } 
/*      */           describeIndex++;
/*      */           continue;
/*      */         case 50:
/*      */           this.pgStream.ReceiveIntegerR(4);
/*      */           boundPortal = this.pendingBindQueue.get(bindIndex++);
/*      */           if (Driver.logDebug)
/*      */             Driver.debug(" <=BE BindComplete [" + boundPortal + "]"); 
/*      */           registerOpenPortal(boundPortal);
/*      */           continue;
/*      */         case 51:
/*      */           this.pgStream.ReceiveIntegerR(4);
/*      */           if (Driver.logDebug)
/*      */             Driver.debug(" <=BE CloseComplete"); 
/*      */           continue;
/*      */         case 110:
/*      */           this.pgStream.ReceiveIntegerR(4);
/*      */           if (Driver.logDebug)
/*      */             Driver.debug(" <=BE NoData"); 
/*      */           if (doneAfterRowDescNoData) {
/*      */             Object[] arrayOfObject = this.pendingDescribeStatementQueue.get(describeIndex++);
/*      */             Query query3 = (Query)arrayOfObject[0];
/*      */             if (fields != null || tuples != null)
/*      */               handler.handleResultRows(query3, fields, tuples, null); 
/*      */           } 
/*      */           continue;
/*      */         case 115:
/*      */           this.pgStream.ReceiveIntegerR(4);
/*      */           if (Driver.logDebug)
/*      */             Driver.debug(" <=BE PortalSuspended"); 
/*      */           executeData = this.pendingExecuteQueue.get(executeIndex++);
/*      */           currentQuery = (Query)executeData[0];
/*      */           currentPortal = (Portal)executeData[1];
/*      */           handler.handleResultRows(currentQuery, fields, tuples, currentPortal);
/*      */           fields = null;
/*      */           tuples = null;
/*      */           continue;
/*      */         case 67:
/*      */           status = receiveCommandStatus();
/*      */           doneAfterRowDescNoData = false;
/*      */           arrayOfObject1 = this.pendingExecuteQueue.get(executeIndex++);
/*      */           query1 = (Query)arrayOfObject1[0];
/*      */           portal1 = (Portal)arrayOfObject1[1];
/*      */           if (fields != null || tuples != null) {
/*      */             handler.handleResultRows(query1, fields, tuples, null);
/*      */           } else {
/*      */             interpretCommandStatus(status, handler);
/*      */           } 
/*      */           if (portal1 != null)
/*      */             portal1.close(); 
/*      */           continue;
/*      */         case 68:
/*      */           tuple = this.pgStream.ReceiveTupleV3();
/*      */           if (!noResults) {
/*      */             if (tuples == null)
/*      */               tuples = new Vector(); 
/*      */             tuples.addElement(tuple);
/*      */           } 
/*      */           if (Driver.logDebug)
/*      */             Driver.debug(" <=BE DataRow"); 
/*      */           continue;
/*      */         case 69:
/*      */           error = receiveErrorResponse();
/*      */           handler.handleError(error);
/*      */           continue;
/*      */         case 73:
/*      */           this.pgStream.ReceiveIntegerR(4);
/*      */           if (Driver.logDebug)
/*      */             Driver.debug(" <=BE EmptyQuery"); 
/*      */           arrayOfObject2 = this.pendingExecuteQueue.get(executeIndex++);
/*      */           query2 = (Query)arrayOfObject2[0];
/*      */           portal2 = (Portal)arrayOfObject2[1];
/*      */           handler.handleCommandStatus("EMPTY", 0, 0L);
/*      */           if (portal2 != null)
/*      */             portal2.close(); 
/*      */           continue;
/*      */         case 78:
/*      */           warning = receiveNoticeResponse();
/*      */           handler.handleWarning(warning);
/*      */           continue;
/*      */         case 83:
/*      */           l_len = this.pgStream.ReceiveIntegerR(4);
/*      */           name = this.pgStream.ReceiveString();
/*      */           value = this.pgStream.ReceiveString();
/*      */           if (Driver.logDebug)
/*      */             Driver.debug(" <=BE ParameterStatus(" + name + " = " + value + ")"); 
/*      */           if (name.equals("client_encoding") && !value.equalsIgnoreCase("UNICODE") && !this.allowEncodingChanges) {
/*      */             this.protoConnection.close();
/*      */             handler.handleError((SQLException)new PSQLException(GT.tr("The server''s client_encoding parameter was changed to {0}. The JDBC driver requires client_encoding to be UNICODE for correct operation.", value), PSQLState.CONNECTION_FAILURE));
/*      */             endQuery = true;
/*      */           } 
/*      */           if (name.equals("DateStyle") && !value.startsWith("ISO,")) {
/*      */             this.protoConnection.close();
/*      */             handler.handleError((SQLException)new PSQLException(GT.tr("The server''s DateStyle parameter was changed to {0}. The JDBC driver requires DateStyle to begin with ISO for correct operation.", value), PSQLState.CONNECTION_FAILURE));
/*      */             endQuery = true;
/*      */           } 
/*      */           continue;
/*      */         case 84:
/*      */           fields = receiveFields();
/*      */           tuples = new Vector();
/*      */           if (doneAfterRowDescNoData) {
/*      */             Object[] arrayOfObject = this.pendingDescribeStatementQueue.get(describeIndex++);
/*      */             Query query3 = (Query)arrayOfObject[0];
/*      */             if (fields != null || tuples != null)
/*      */               handler.handleResultRows(query3, fields, tuples, null); 
/*      */           } 
/*      */           continue;
/*      */         case 90:
/*      */           receiveRFQ();
/*      */           endQuery = true;
/*      */           while (parseIndex < this.pendingParseQueue.size()) {
/*      */             SimpleQuery failedQuery = this.pendingParseQueue.get(parseIndex++);
/*      */             failedQuery.unprepare();
/*      */           } 
/*      */           this.pendingParseQueue.clear();
/*      */           this.pendingDescribeStatementQueue.clear();
/*      */           this.pendingBindQueue.clear();
/*      */           this.pendingExecuteQueue.clear();
/*      */           continue;
/*      */         case 71:
/*      */         case 72:
/*      */         case 99:
/*      */         case 100:
/*      */           l_len = this.pgStream.ReceiveIntegerR(4);
/*      */           this.pgStream.Receive(l_len);
/*      */           handler.handleError((SQLException)new PSQLException(GT.tr("The driver currently does not support COPY operations."), PSQLState.NOT_IMPLEMENTED));
/*      */           continue;
/*      */       } 
/*      */       throw new IOException("Unexpected packet type: " + c);
/*      */     } 
/*      */   }
/*      */   
/*      */   public synchronized void fetch(ResultCursor cursor, ResultHandler handler, int fetchSize) throws SQLException {
/*      */     Portal portal = (Portal)cursor;
/*      */     ResultHandler delegateHandler = handler;
/*      */     handler = new ResultHandler(this, delegateHandler, portal) {
/*      */         private final ResultHandler val$delegateHandler;
/*      */         private final Portal val$portal;
/*      */         private final QueryExecutorImpl this$0;
/*      */         
/*      */         public void handleResultRows(Query fromQuery, Field[] fields, Vector tuples, ResultCursor cursor) {
/*      */           this.val$delegateHandler.handleResultRows(fromQuery, fields, tuples, cursor);
/*      */         }
/*      */         
/*      */         public void handleCommandStatus(String status, int updateCount, long insertOID) {
/*      */           handleResultRows(this.val$portal.getQuery(), null, new Vector(), null);
/*      */         }
/*      */         
/*      */         public void handleWarning(SQLWarning warning) {
/*      */           this.val$delegateHandler.handleWarning(warning);
/*      */         }
/*      */         
/*      */         public void handleError(SQLException error) {
/*      */           this.val$delegateHandler.handleError(error);
/*      */         }
/*      */         
/*      */         public void handleCompletion() throws SQLException {
/*      */           this.val$delegateHandler.handleCompletion();
/*      */         }
/*      */       };
/*      */     try {
/*      */       processDeadParsedQueries();
/*      */       processDeadPortals();
/*      */       sendExecute(portal.getQuery(), portal, fetchSize);
/*      */       sendSync();
/*      */       processResults(handler, 0);
/*      */     } catch (IOException e) {
/*      */       this.protoConnection.close();
/*      */       handler.handleError((SQLException)new PSQLException(GT.tr("An I/O error occured while sending to the backend."), PSQLState.CONNECTION_FAILURE, e));
/*      */     } 
/*      */     handler.handleCompletion();
/*      */   }
/*      */   
/*      */   private Field[] receiveFields() throws IOException {
/*      */     int l_msgSize = this.pgStream.ReceiveIntegerR(4);
/*      */     int size = this.pgStream.ReceiveIntegerR(2);
/*      */     Field[] fields = new Field[size];
/*      */     if (Driver.logDebug)
/*      */       Driver.debug(" <=BE RowDescription(" + size + ")"); 
/*      */     for (int i = 0; i < fields.length; i++) {
/*      */       String columnLabel = this.pgStream.ReceiveString();
/*      */       int tableOid = this.pgStream.ReceiveIntegerR(4);
/*      */       short positionInTable = (short)this.pgStream.ReceiveIntegerR(2);
/*      */       int typeOid = this.pgStream.ReceiveIntegerR(4);
/*      */       int typeLength = this.pgStream.ReceiveIntegerR(2);
/*      */       int typeModifier = this.pgStream.ReceiveIntegerR(4);
/*      */       int formatType = this.pgStream.ReceiveIntegerR(2);
/*      */       fields[i] = new Field(columnLabel, null, typeOid, typeLength, typeModifier, tableOid, positionInTable);
/*      */       fields[i].setFormat(formatType);
/*      */     } 
/*      */     return fields;
/*      */   }
/*      */   
/*      */   private void receiveAsyncNotify() throws IOException {
/*      */     int msglen = this.pgStream.ReceiveIntegerR(4);
/*      */     int pid = this.pgStream.ReceiveIntegerR(4);
/*      */     String msg = this.pgStream.ReceiveString();
/*      */     String param = this.pgStream.ReceiveString();
/*      */     this.protoConnection.addNotification((PGNotification)new Notification(msg, pid, param));
/*      */     if (Driver.logDebug)
/*      */       Driver.debug(" <=BE AsyncNotify(" + pid + "," + msg + "," + param + ")"); 
/*      */   }
/*      */   
/*      */   private SQLException receiveErrorResponse() throws IOException {
/*      */     int elen = this.pgStream.ReceiveIntegerR(4);
/*      */     String totalMessage = this.pgStream.ReceiveString(elen - 4);
/*      */     ServerErrorMessage errorMsg = new ServerErrorMessage(totalMessage);
/*      */     if (Driver.logDebug)
/*      */       Driver.debug(" <=BE ErrorMessage(" + errorMsg.toString() + ")"); 
/*      */     return (SQLException)new PSQLException(errorMsg);
/*      */   }
/*      */   
/*      */   private SQLWarning receiveNoticeResponse() throws IOException {
/*      */     int nlen = this.pgStream.ReceiveIntegerR(4);
/*      */     ServerErrorMessage warnMsg = new ServerErrorMessage(this.pgStream.ReceiveString(nlen - 4));
/*      */     if (Driver.logDebug)
/*      */       Driver.debug(" <=BE NoticeResponse(" + warnMsg.toString() + ")"); 
/*      */     return (SQLWarning)new PSQLWarning(warnMsg);
/*      */   }
/*      */   
/*      */   private String receiveCommandStatus() throws IOException {
/*      */     int l_len = this.pgStream.ReceiveIntegerR(4);
/*      */     String status = this.pgStream.ReceiveString(l_len - 5);
/*      */     this.pgStream.Receive(1);
/*      */     if (Driver.logDebug)
/*      */       Driver.debug(" <=BE CommandStatus(" + status + ")"); 
/*      */     return status;
/*      */   }
/*      */   
/*      */   private void interpretCommandStatus(String status, ResultHandler handler) {
/*      */     int update_count = 0;
/*      */     long insert_oid = 0L;
/*      */     if (status.startsWith("INSERT") || status.startsWith("UPDATE") || status.startsWith("DELETE") || status.startsWith("MOVE"))
/*      */       try {
/*      */         update_count = Integer.parseInt(status.substring(1 + status.lastIndexOf(' ')));
/*      */         if (status.startsWith("INSERT"))
/*      */           insert_oid = Long.parseLong(status.substring(1 + status.indexOf(' '), status.lastIndexOf(' '))); 
/*      */       } catch (NumberFormatException nfe) {
/*      */         handler.handleError((SQLException)new PSQLException(GT.tr("Unable to interpret the update count in command completion tag: {0}.", status), PSQLState.CONNECTION_FAILURE));
/*      */         return;
/*      */       }  
/*      */     handler.handleCommandStatus(status, update_count, insert_oid);
/*      */   }
/*      */   
/*      */   private void receiveRFQ() throws IOException {
/*      */     if (this.pgStream.ReceiveIntegerR(4) != 5)
/*      */       throw new IOException("unexpected length of ReadyForQuery message"); 
/*      */     char tStatus = (char)this.pgStream.ReceiveChar();
/*      */     if (Driver.logDebug)
/*      */       Driver.debug(" <=BE ReadyForQuery(" + tStatus + ")"); 
/*      */     switch (tStatus) {
/*      */       case 'I':
/*      */         this.protoConnection.setTransactionState(0);
/*      */         return;
/*      */       case 'T':
/*      */         this.protoConnection.setTransactionState(1);
/*      */         return;
/*      */       case 'E':
/*      */         this.protoConnection.setTransactionState(2);
/*      */         return;
/*      */     } 
/*      */     throw new IOException("unexpected transaction state in ReadyForQuery message: " + tStatus);
/*      */   }
/*      */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\v3\QueryExecutorImpl.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */