/*     */ package org.postgresql.core.v2;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.util.Vector;
/*     */ import org.postgresql.Driver;
/*     */ import org.postgresql.PGNotification;
/*     */ import org.postgresql.core.Field;
/*     */ import org.postgresql.core.Notification;
/*     */ import org.postgresql.core.PGStream;
/*     */ import org.postgresql.core.ParameterList;
/*     */ import org.postgresql.core.Query;
/*     */ import org.postgresql.core.QueryExecutor;
/*     */ import org.postgresql.core.ResultCursor;
/*     */ import org.postgresql.core.ResultHandler;
/*     */ import org.postgresql.util.GT;
/*     */ import org.postgresql.util.PSQLException;
/*     */ import org.postgresql.util.PSQLState;
/*     */ 
/*     */ public class QueryExecutorImpl
/*     */   implements QueryExecutor
/*     */ {
/*     */   private final ProtocolConnectionImpl protoConnection;
/*     */   private final PGStream pgStream;
/*     */   
/*     */   public QueryExecutorImpl(ProtocolConnectionImpl protoConnection, PGStream pgStream) {
/*  29 */     this.protoConnection = protoConnection;
/*  30 */     this.pgStream = pgStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Query createSimpleQuery(String sql) {
/*  38 */     return new V2Query(sql, false);
/*     */   }
/*     */   
/*     */   public Query createParameterizedQuery(String sql) {
/*  42 */     return new V2Query(sql, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterList createFastpathParameters(int count) {
/*  50 */     return new FastpathParameterList(count);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized byte[] fastpathCall(int fnid, ParameterList parameters, boolean suppressBegin) throws SQLException {
/*  55 */     if (this.protoConnection.getTransactionState() == 0 && !suppressBegin) {
/*     */ 
/*     */       
/*  58 */       if (Driver.logDebug) {
/*  59 */         Driver.debug("Issuing BEGIN before fastpath call.");
/*     */       }
/*  61 */       ResultHandler handler = new ResultHandler(this) {
/*     */           private boolean sawBegin;
/*     */           private SQLException sqle;
/*     */           private final QueryExecutorImpl this$0;
/*     */           
/*     */           public void handleResultRows(Query fromQuery, Field[] fields, Vector tuples, ResultCursor cursor) {}
/*     */           
/*     */           public void handleCommandStatus(String status, int updateCount, long insertOID) {
/*  69 */             if (!this.sawBegin) {
/*     */               
/*  71 */               if (!status.equals("BEGIN")) {
/*  72 */                 handleError((SQLException)new PSQLException(GT.tr("Expected command status BEGIN, got {0}.", status), PSQLState.PROTOCOL_VIOLATION));
/*     */               }
/*  74 */               this.sawBegin = true;
/*     */             }
/*     */             else {
/*     */               
/*  78 */               handleError((SQLException)new PSQLException(GT.tr("Unexpected command status: {0}.", status), PSQLState.PROTOCOL_VIOLATION));
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void handleWarning(SQLWarning warning) {
/*  88 */             handleError(warning);
/*     */           }
/*     */           
/*     */           public void handleError(SQLException error) {
/*  92 */             if (this.sqle == null) {
/*     */               
/*  94 */               this.sqle = error;
/*     */             }
/*     */             else {
/*     */               
/*  98 */               this.sqle.setNextException(error);
/*     */             } 
/*     */           }
/*     */           
/*     */           public void handleCompletion() throws SQLException {
/* 103 */             if (this.sqle != null) {
/* 104 */               throw this.sqle;
/*     */             }
/*     */           }
/*     */         };
/*     */ 
/*     */       
/*     */       try {
/* 111 */         V2Query query = (V2Query)createSimpleQuery("");
/* 112 */         SimpleParameterList params = (SimpleParameterList)query.createParameterList();
/* 113 */         sendQuery(query, params, "BEGIN");
/* 114 */         processResults(query, handler, 0);
/*     */       }
/*     */       catch (IOException ioe) {
/*     */         
/* 118 */         throw new PSQLException(GT.tr("An I/O error occured while sending to the backend."), PSQLState.CONNECTION_FAILURE, ioe);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 124 */       sendFastpathCall(fnid, (FastpathParameterList)parameters);
/* 125 */       return receiveFastpathResult();
/*     */     }
/*     */     catch (IOException ioe) {
/*     */       
/* 129 */       throw new PSQLException(GT.tr("An I/O error occured while sending to the backend."), PSQLState.CONNECTION_FAILURE, ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendFastpathCall(int fnid, FastpathParameterList params) throws IOException {
/* 135 */     int count = params.getParameterCount();
/*     */     
/* 137 */     if (Driver.logDebug) {
/* 138 */       Driver.debug(" FE=> FastpathCall(fnid=" + fnid + ",paramCount=" + count + ")");
/*     */     }
/* 140 */     this.pgStream.SendChar(70);
/* 141 */     this.pgStream.SendChar(0);
/* 142 */     this.pgStream.SendInteger4(fnid);
/* 143 */     this.pgStream.SendInteger4(count);
/*     */     
/* 145 */     for (int i = 1; i <= count; i++) {
/* 146 */       params.writeV2FastpathValue(i, this.pgStream);
/*     */     }
/* 148 */     this.pgStream.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void processNotifies() throws SQLException {
/* 153 */     if (this.protoConnection.getTransactionState() != 0) {
/*     */       return;
/*     */     }
/*     */     try {
/* 157 */       while (this.pgStream.hasMessagePending()) {
/* 158 */         int c = this.pgStream.ReceiveChar();
/* 159 */         switch (c) {
/*     */           case 65:
/* 161 */             receiveAsyncNotify();
/*     */             continue;
/*     */           case 69:
/* 164 */             throw receiveErrorMessage();
/*     */           
/*     */           case 78:
/* 167 */             this.protoConnection.addWarning(receiveNotification());
/*     */             continue;
/*     */         } 
/* 170 */         throw new PSQLException(GT.tr("Unknown Response Type {0}.", new Character((char)c)), PSQLState.CONNECTION_FAILURE);
/*     */       } 
/*     */     } catch (IOException ioe) {
/*     */       
/* 174 */       throw new PSQLException(GT.tr("An I/O error occured while sending to the backend."), PSQLState.CONNECTION_FAILURE, ioe);
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] receiveFastpathResult() throws IOException, SQLException {
/* 179 */     SQLException error = null;
/* 180 */     boolean endQuery = false;
/* 181 */     byte[] result = null;
/*     */     
/* 183 */     while (!endQuery) {
/*     */       SQLException newError;
/* 185 */       int c = this.pgStream.ReceiveChar();
/*     */       
/* 187 */       switch (c) {
/*     */         
/*     */         case 65:
/* 190 */           receiveAsyncNotify();
/*     */           continue;
/*     */         
/*     */         case 69:
/* 194 */           newError = receiveErrorMessage();
/* 195 */           if (error == null) {
/* 196 */             error = newError; continue;
/*     */           } 
/* 198 */           error.setNextException(newError);
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 78:
/* 203 */           this.protoConnection.addWarning(receiveNotification());
/*     */           continue;
/*     */         
/*     */         case 86:
/* 207 */           c = this.pgStream.ReceiveChar();
/* 208 */           if (c == 71) {
/*     */             
/* 210 */             if (Driver.logDebug) {
/* 211 */               Driver.debug(" <=BE FastpathResult");
/*     */             }
/*     */             
/* 214 */             int len = this.pgStream.ReceiveIntegerR(4);
/* 215 */             result = this.pgStream.Receive(len);
/* 216 */             c = this.pgStream.ReceiveChar();
/*     */ 
/*     */           
/*     */           }
/* 220 */           else if (Driver.logDebug) {
/* 221 */             Driver.debug(" <=BE FastpathVoidResult");
/*     */           } 
/*     */           
/* 224 */           if (c != 48) {
/* 225 */             throw new PSQLException(GT.tr("Unknown Response Type {0}.", new Character((char)c)), PSQLState.CONNECTION_FAILURE);
/*     */           }
/*     */           continue;
/*     */         
/*     */         case 90:
/* 230 */           if (Driver.logDebug)
/* 231 */             Driver.debug(" <=BE ReadyForQuery"); 
/* 232 */           endQuery = true;
/*     */           continue;
/*     */       } 
/*     */       
/* 236 */       throw new PSQLException(GT.tr("Unknown Response Type {0}.", new Character((char)c)), PSQLState.CONNECTION_FAILURE);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     if (error != null) {
/* 243 */       throw error;
/*     */     }
/* 245 */     return result;
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
/*     */   public synchronized void execute(Query query, ParameterList parameters, ResultHandler handler, int maxRows, int fetchSize, int flags) throws SQLException {
/* 258 */     execute((V2Query)query, (SimpleParameterList)parameters, handler, maxRows, flags);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void execute(Query[] queries, ParameterList[] parameters, ResultHandler handler, int maxRows, int fetchSize, int flags) throws SQLException {
/* 268 */     ResultHandler delegateHandler = handler;
/* 269 */     handler = new ResultHandler(this, delegateHandler) { private final ResultHandler val$delegateHandler; private final QueryExecutorImpl this$0;
/*     */         public void handleResultRows(Query fromQuery, Field[] fields, Vector tuples, ResultCursor cursor) {
/* 271 */           this.val$delegateHandler.handleResultRows(fromQuery, fields, tuples, cursor);
/*     */         }
/*     */         
/*     */         public void handleCommandStatus(String status, int updateCount, long insertOID) {
/* 275 */           this.val$delegateHandler.handleCommandStatus(status, updateCount, insertOID);
/*     */         }
/*     */         
/*     */         public void handleWarning(SQLWarning warning) {
/* 279 */           this.val$delegateHandler.handleWarning(warning);
/*     */         }
/*     */         
/*     */         public void handleError(SQLException error) {
/* 283 */           this.val$delegateHandler.handleError(error);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void handleCompletion() throws SQLException {} }
/*     */       ;
/* 290 */     for (int i = 0; i < queries.length; i++) {
/* 291 */       execute((V2Query)queries[i], (SimpleParameterList)parameters[i], handler, maxRows, flags);
/*     */     }
/* 293 */     delegateHandler.handleCompletion();
/*     */   }
/*     */   
/*     */   public void fetch(ResultCursor cursor, ResultHandler handler, int rows) throws SQLException {
/* 297 */     throw Driver.notImplemented(getClass(), "fetch(ResultCursor,ResultHandler,int)");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void execute(V2Query query, SimpleParameterList parameters, ResultHandler handler, int maxRows, int flags) throws SQLException {
/* 308 */     if ((flags & 0x20) != 0) {
/*     */       return;
/*     */     }
/* 311 */     if (parameters == null) {
/* 312 */       parameters = (SimpleParameterList)query.createParameterList();
/*     */     }
/* 314 */     parameters.checkAllParametersSet();
/*     */     
/* 316 */     String queryPrefix = null;
/* 317 */     if (this.protoConnection.getTransactionState() == 0 && (flags & 0x10) == 0) {
/*     */ 
/*     */ 
/*     */       
/* 321 */       queryPrefix = "BEGIN;";
/*     */ 
/*     */       
/* 324 */       ResultHandler delegateHandler = handler;
/* 325 */       handler = new ResultHandler(this, delegateHandler) { private boolean sawBegin; private final ResultHandler val$delegateHandler;
/*     */           private final QueryExecutorImpl this$0;
/*     */           
/*     */           public void handleResultRows(Query fromQuery, Field[] fields, Vector tuples, ResultCursor cursor) {
/* 329 */             if (this.sawBegin)
/* 330 */               this.val$delegateHandler.handleResultRows(fromQuery, fields, tuples, cursor); 
/*     */           }
/*     */           
/*     */           public void handleCommandStatus(String status, int updateCount, long insertOID) {
/* 334 */             if (!this.sawBegin) {
/*     */               
/* 336 */               if (!status.equals("BEGIN")) {
/* 337 */                 handleError((SQLException)new PSQLException(GT.tr("Expected command status BEGIN, got {0}.", status), PSQLState.PROTOCOL_VIOLATION));
/*     */               }
/* 339 */               this.sawBegin = true;
/*     */             }
/*     */             else {
/*     */               
/* 343 */               this.val$delegateHandler.handleCommandStatus(status, updateCount, insertOID);
/*     */             } 
/*     */           }
/*     */           
/*     */           public void handleWarning(SQLWarning warning) {
/* 348 */             this.val$delegateHandler.handleWarning(warning);
/*     */           }
/*     */           
/*     */           public void handleError(SQLException error) {
/* 352 */             this.val$delegateHandler.handleError(error);
/*     */           }
/*     */           
/*     */           public void handleCompletion() throws SQLException {
/* 356 */             this.val$delegateHandler.handleCompletion();
/*     */           } }
/*     */         ;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 363 */       sendQuery(query, parameters, queryPrefix);
/* 364 */       processResults(query, handler, maxRows);
/*     */     }
/*     */     catch (IOException e) {
/*     */       
/* 368 */       this.protoConnection.close();
/* 369 */       handler.handleError((SQLException)new PSQLException(GT.tr("An I/O error occured while sending to the backend."), PSQLState.CONNECTION_FAILURE, e));
/*     */     } 
/*     */     
/* 372 */     handler.handleCompletion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sendQuery(V2Query query, SimpleParameterList params, String queryPrefix) throws IOException {
/* 379 */     if (Driver.logDebug) {
/* 380 */       Driver.debug(" FE=> Query(\"" + ((queryPrefix == null) ? "" : queryPrefix) + query.toString(params) + "\")");
/*     */     }
/* 382 */     this.pgStream.SendChar(81);
/*     */     
/* 384 */     Writer encodingWriter = this.pgStream.getEncodingWriter();
/*     */     
/* 386 */     if (queryPrefix != null) {
/* 387 */       encodingWriter.write(queryPrefix);
/*     */     }
/* 389 */     String[] fragments = query.getFragments();
/* 390 */     for (int i = 0; i < fragments.length; i++) {
/*     */       
/* 392 */       encodingWriter.write(fragments[i]);
/* 393 */       if (i < params.getParameterCount()) {
/* 394 */         params.writeV2Value(i + 1, encodingWriter);
/*     */       }
/*     */     } 
/* 397 */     encodingWriter.write(0);
/* 398 */     this.pgStream.flush();
/*     */   }
/*     */   
/*     */   protected void processResults(Query originalQuery, ResultHandler handler, int maxRows) throws IOException {
/* 402 */     Field[] fields = null;
/* 403 */     Vector tuples = null;
/*     */     
/* 405 */     boolean endQuery = false;
/* 406 */     while (!endQuery) {
/*     */       Object tuple; String status; int i; Object object1; String portalName;
/* 408 */       int c = this.pgStream.ReceiveChar();
/*     */       
/* 410 */       switch (c) {
/*     */         
/*     */         case 65:
/* 413 */           receiveAsyncNotify();
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 66:
/* 418 */           if (fields == null) {
/* 419 */             throw new IOException("Data transfer before field metadata");
/*     */           }
/* 421 */           if (Driver.logDebug) {
/* 422 */             Driver.debug(" <=BE BinaryRow");
/*     */           }
/* 424 */           tuple = this.pgStream.ReceiveTupleV2(fields.length, true);
/* 425 */           for (i = 0; i < fields.length; i++)
/* 426 */             fields[i].setFormat(1); 
/* 427 */           if (maxRows == 0 || tuples.size() < maxRows) {
/* 428 */             tuples.addElement(tuple);
/*     */           }
/*     */           continue;
/*     */         
/*     */         case 67:
/* 433 */           status = this.pgStream.ReceiveString();
/*     */           
/* 435 */           if (Driver.logDebug) {
/* 436 */             Driver.debug(" <=BE CommandStatus(" + status + ")");
/*     */           }
/* 438 */           if (fields != null) {
/*     */             
/* 440 */             handler.handleResultRows(originalQuery, fields, tuples, null);
/* 441 */             fields = null;
/*     */             
/*     */             continue;
/*     */           } 
/* 445 */           interpretCommandStatus(status, handler);
/*     */           continue;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 68:
/* 452 */           if (fields == null) {
/* 453 */             throw new IOException("Data transfer before field metadata");
/*     */           }
/* 455 */           if (Driver.logDebug) {
/* 456 */             Driver.debug(" <=BE DataRow");
/*     */           }
/* 458 */           object1 = this.pgStream.ReceiveTupleV2(fields.length, false);
/* 459 */           if (maxRows == 0 || tuples.size() < maxRows) {
/* 460 */             tuples.addElement(object1);
/*     */           }
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 69:
/* 466 */           handler.handleError(receiveErrorMessage());
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 73:
/* 471 */           if (Driver.logDebug)
/* 472 */             Driver.debug(" <=BE EmptyQuery"); 
/* 473 */           c = this.pgStream.ReceiveChar();
/* 474 */           if (c != 0) {
/* 475 */             throw new IOException("Expected \\0 after EmptyQuery, got: " + c);
/*     */           }
/*     */           continue;
/*     */         case 78:
/* 479 */           handler.handleWarning(receiveNotification());
/*     */           continue;
/*     */         
/*     */         case 80:
/* 483 */           portalName = this.pgStream.ReceiveString();
/* 484 */           if (Driver.logDebug) {
/* 485 */             Driver.debug(" <=BE PortalName(" + portalName + ")");
/*     */           }
/*     */           continue;
/*     */         case 84:
/* 489 */           fields = receiveFields();
/* 490 */           tuples = new Vector();
/*     */           continue;
/*     */         
/*     */         case 90:
/* 494 */           if (Driver.logDebug)
/* 495 */             Driver.debug(" <=BE ReadyForQuery"); 
/* 496 */           endQuery = true;
/*     */           continue;
/*     */       } 
/*     */       
/* 500 */       throw new IOException("Unexpected packet type: " + c);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Field[] receiveFields() throws IOException {
/* 511 */     int size = this.pgStream.ReceiveIntegerR(2);
/* 512 */     Field[] fields = new Field[size];
/*     */     
/* 514 */     if (Driver.logDebug) {
/* 515 */       Driver.debug(" <=BE RowDescription(" + fields.length + ")");
/*     */     }
/* 517 */     for (int i = 0; i < fields.length; i++) {
/*     */       
/* 519 */       String columnLabel = this.pgStream.ReceiveString();
/* 520 */       int typeOid = this.pgStream.ReceiveIntegerR(4);
/* 521 */       int typeLength = this.pgStream.ReceiveIntegerR(2);
/* 522 */       int typeModifier = this.pgStream.ReceiveIntegerR(4);
/* 523 */       fields[i] = new Field(columnLabel, columnLabel, typeOid, typeLength, typeModifier, 0, 0);
/*     */     } 
/*     */     
/* 526 */     return fields;
/*     */   }
/*     */   
/*     */   private void receiveAsyncNotify() throws IOException {
/* 530 */     int pid = this.pgStream.ReceiveIntegerR(4);
/* 531 */     String msg = this.pgStream.ReceiveString();
/*     */     
/* 533 */     if (Driver.logDebug) {
/* 534 */       Driver.debug(" <=BE AsyncNotify(pid=" + pid + ",msg=" + msg + ")");
/*     */     }
/* 536 */     this.protoConnection.addNotification((PGNotification)new Notification(msg, pid));
/*     */   }
/*     */   
/*     */   private SQLException receiveErrorMessage() throws IOException {
/* 540 */     String errorMsg = this.pgStream.ReceiveString().trim();
/* 541 */     if (Driver.logDebug)
/* 542 */       Driver.debug(" <=BE ErrorResponse(" + errorMsg + ")"); 
/* 543 */     return (SQLException)new PSQLException(errorMsg, PSQLState.UNKNOWN_STATE);
/*     */   }
/*     */   
/*     */   private SQLWarning receiveNotification() throws IOException {
/* 547 */     String warnMsg = this.pgStream.ReceiveString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 553 */     int severityMark = warnMsg.indexOf(":");
/* 554 */     warnMsg = warnMsg.substring(severityMark + 1).trim();
/* 555 */     if (Driver.logDebug)
/* 556 */       Driver.debug(" <=BE NoticeResponse(" + warnMsg + ")"); 
/* 557 */     return new SQLWarning(warnMsg);
/*     */   }
/*     */   
/*     */   private void interpretCommandStatus(String status, ResultHandler handler) throws IOException {
/* 561 */     int update_count = 0;
/* 562 */     long insert_oid = 0L;
/*     */     
/* 564 */     if (status.equals("BEGIN")) {
/* 565 */       this.protoConnection.setTransactionState(1);
/* 566 */     } else if (status.equals("COMMIT") || status.equals("ROLLBACK")) {
/* 567 */       this.protoConnection.setTransactionState(0);
/* 568 */     } else if (status.startsWith("INSERT") || status.startsWith("UPDATE") || status.startsWith("DELETE") || status.startsWith("MOVE")) {
/*     */ 
/*     */       
/*     */       try {
/* 572 */         update_count = Integer.parseInt(status.substring(1 + status.lastIndexOf(' ')));
/* 573 */         if (status.startsWith("INSERT")) {
/* 574 */           insert_oid = Long.parseLong(status.substring(1 + status.indexOf(' '), status.lastIndexOf(' ')));
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException nfe) {
/*     */         
/* 579 */         handler.handleError((SQLException)new PSQLException(GT.tr("Unable to interpret the update count in command completion tag: {0}.", status), PSQLState.CONNECTION_FAILURE));
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 584 */     handler.handleCommandStatus(status, update_count, insert_oid);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\v2\QueryExecutorImpl.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */