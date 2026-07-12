/*     */ package org.postgresql.core.v2;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.ConnectException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import org.postgresql.Driver;
/*     */ import org.postgresql.core.ConnectionFactory;
/*     */ import org.postgresql.core.Encoding;
/*     */ import org.postgresql.core.Field;
/*     */ import org.postgresql.core.PGStream;
/*     */ import org.postgresql.core.ProtocolConnection;
/*     */ import org.postgresql.core.Query;
/*     */ import org.postgresql.core.QueryExecutor;
/*     */ import org.postgresql.core.ResultCursor;
/*     */ import org.postgresql.core.ResultHandler;
/*     */ import org.postgresql.core.Utils;
/*     */ import org.postgresql.util.GT;
/*     */ import org.postgresql.util.MD5Digest;
/*     */ import org.postgresql.util.PSQLException;
/*     */ import org.postgresql.util.PSQLState;
/*     */ import org.postgresql.util.UnixCrypt;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConnectionFactoryImpl
/*     */   extends ConnectionFactory
/*     */ {
/*     */   private static final int AUTH_REQ_OK = 0;
/*     */   private static final int AUTH_REQ_KRB4 = 1;
/*     */   private static final int AUTH_REQ_KRB5 = 2;
/*     */   private static final int AUTH_REQ_PASSWORD = 3;
/*     */   private static final int AUTH_REQ_CRYPT = 4;
/*     */   private static final int AUTH_REQ_MD5 = 5;
/*     */   private static final int AUTH_REQ_SCM = 6;
/*     */   
/*     */   public ProtocolConnection openConnectionImpl(String host, int port, String user, String database, Properties info) throws SQLException {
/*  46 */     boolean requireSSL = (info.getProperty("ssl") != null);
/*  47 */     boolean trySSL = requireSSL;
/*     */     
/*  49 */     if (Driver.logDebug) {
/*  50 */       Driver.debug("Trying to establish a protocol version 2 connection to " + host + ":" + port);
/*     */     }
/*  52 */     if (!Driver.sslEnabled()) {
/*     */       
/*  54 */       if (requireSSL)
/*  55 */         throw new PSQLException(GT.tr("The driver does not support SSL."), PSQLState.CONNECTION_FAILURE); 
/*  56 */       trySSL = false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     PGStream newStream = null;
/*     */     
/*     */     try {
/*  66 */       newStream = new PGStream(host, port);
/*     */ 
/*     */       
/*  69 */       if (trySSL) {
/*  70 */         newStream = enableSSL(newStream, requireSSL, info);
/*     */       }
/*     */       
/*  73 */       sendStartupPacket(newStream, user, database);
/*     */ 
/*     */       
/*  76 */       doAuthentication(newStream, user, info.getProperty("password"));
/*     */ 
/*     */       
/*  79 */       ProtocolConnectionImpl protoConnection = new ProtocolConnectionImpl(newStream, user, database);
/*  80 */       readStartupMessages(newStream, protoConnection);
/*     */ 
/*     */       
/*  83 */       runInitialQueries(protoConnection, info.getProperty("charSet"));
/*     */ 
/*     */       
/*  86 */       return protoConnection;
/*     */ 
/*     */     
/*     */     }
/*     */     catch (ConnectException cex) {
/*     */ 
/*     */       
/*  93 */       throw new PSQLException(GT.tr("Connection refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections."), PSQLState.CONNECTION_REJECTED, cex);
/*     */     }
/*     */     catch (IOException ioe) {
/*     */       
/*  97 */       if (newStream != null) {
/*     */         
/*     */         try {
/*     */           
/* 101 */           newStream.close();
/*     */         
/*     */         }
/* 104 */         catch (IOException e) {}
/*     */       }
/*     */ 
/*     */       
/* 108 */       throw new PSQLException(GT.tr("The connection attempt failed."), PSQLState.CONNECTION_UNABLE_TO_CONNECT, ioe);
/*     */     }
/*     */     catch (SQLException se) {
/*     */       
/* 112 */       if (newStream != null) {
/*     */         
/*     */         try {
/*     */           
/* 116 */           newStream.close();
/*     */         
/*     */         }
/* 119 */         catch (IOException e) {}
/*     */       }
/*     */ 
/*     */       
/* 123 */       throw se;
/*     */     } 
/*     */   }
/*     */   
/*     */   private PGStream enableSSL(PGStream pgStream, boolean requireSSL, Properties info) throws IOException, SQLException {
/* 128 */     if (Driver.logDebug) {
/* 129 */       Driver.debug(" FE=> SSLRequest");
/*     */     }
/*     */     
/* 132 */     pgStream.SendInteger4(8);
/* 133 */     pgStream.SendInteger2(1234);
/* 134 */     pgStream.SendInteger2(5679);
/* 135 */     pgStream.flush();
/*     */ 
/*     */     
/* 138 */     int beresp = pgStream.ReceiveChar();
/* 139 */     switch (beresp) {
/*     */       
/*     */       case 69:
/* 142 */         if (Driver.logDebug) {
/* 143 */           Driver.debug(" <=BE SSLError");
/*     */         }
/*     */         
/* 146 */         if (requireSSL) {
/* 147 */           throw new PSQLException(GT.tr("The server does not support SSL."), PSQLState.CONNECTION_FAILURE);
/*     */         }
/*     */         
/* 150 */         pgStream.close();
/* 151 */         return new PGStream(pgStream.getHost(), pgStream.getPort());
/*     */       
/*     */       case 78:
/* 154 */         if (Driver.logDebug) {
/* 155 */           Driver.debug(" <=BE SSLRefused");
/*     */         }
/*     */         
/* 158 */         if (requireSSL) {
/* 159 */           throw new PSQLException(GT.tr("The server does not support SSL."), PSQLState.CONNECTION_FAILURE);
/*     */         }
/* 161 */         return pgStream;
/*     */       
/*     */       case 83:
/* 164 */         if (Driver.logDebug) {
/* 165 */           Driver.debug(" <=BE SSLOk");
/*     */         }
/*     */         
/* 168 */         Driver.makeSSL(pgStream, info);
/* 169 */         return pgStream;
/*     */     } 
/*     */     
/* 172 */     throw new PSQLException(GT.tr("An error occured while setting up the SSL connection."), PSQLState.CONNECTION_FAILURE);
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
/*     */   private void sendStartupPacket(PGStream pgStream, String user, String database) throws IOException {
/* 186 */     if (Driver.logDebug) {
/* 187 */       Driver.debug(" FE=> StartupPacket(user=" + user + ",database=" + database + ")");
/*     */     }
/* 189 */     pgStream.SendInteger4(296);
/* 190 */     pgStream.SendInteger2(2);
/* 191 */     pgStream.SendInteger2(0);
/* 192 */     pgStream.Send(database.getBytes("US-ASCII"), 64);
/* 193 */     pgStream.Send(user.getBytes("US-ASCII"), 32);
/* 194 */     pgStream.Send(new byte[64]);
/* 195 */     pgStream.Send(new byte[64]);
/* 196 */     pgStream.Send(new byte[64]);
/* 197 */     pgStream.flush();
/*     */   }
/*     */   private void doAuthentication(PGStream pgStream, String user, String password) throws IOException, SQLException {
/*     */     while (true) {
/*     */       String errorMsg;
/*     */       int areq;
/*     */       String salt;
/*     */       byte[] md5Salt, encodedPassword;
/*     */       String result;
/*     */       byte[] digest, encodedResult;
/* 207 */       int beresp = pgStream.ReceiveChar();
/*     */       
/* 209 */       switch (beresp) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 69:
/* 218 */           errorMsg = pgStream.ReceiveString();
/* 219 */           if (Driver.logDebug)
/* 220 */             Driver.debug(" <=BE ErrorMessage(" + errorMsg + ")"); 
/* 221 */           throw new PSQLException(GT.tr("Connection rejected: {0}.", errorMsg), PSQLState.CONNECTION_REJECTED);
/*     */ 
/*     */ 
/*     */         
/*     */         case 82:
/* 226 */           areq = pgStream.ReceiveIntegerR(4);
/*     */ 
/*     */           
/* 229 */           switch (areq) {
/*     */ 
/*     */             
/*     */             case 4:
/* 233 */               salt = pgStream.ReceiveString(2);
/*     */               
/* 235 */               if (Driver.logDebug) {
/* 236 */                 Driver.debug(" <=BE AuthenticationReqCrypt(salt='" + salt + "')");
/*     */               }
/* 238 */               if (password == null) {
/* 239 */                 throw new PSQLException(GT.tr("The server requested password-based authentication, but no password was provided."), PSQLState.CONNECTION_REJECTED);
/*     */               }
/* 241 */               result = UnixCrypt.crypt(salt, password);
/* 242 */               encodedResult = result.getBytes("US-ASCII");
/*     */               
/* 244 */               if (Driver.logDebug) {
/* 245 */                 Driver.debug(" FE=> Password(crypt='" + result + "')");
/*     */               }
/* 247 */               pgStream.SendInteger4(4 + encodedResult.length + 1);
/* 248 */               pgStream.Send(encodedResult);
/* 249 */               pgStream.SendChar(0);
/* 250 */               pgStream.flush();
/*     */               continue;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case 5:
/* 257 */               md5Salt = pgStream.Receive(4);
/* 258 */               if (Driver.logDebug) {
/* 259 */                 Driver.debug(" <=BE AuthenticationReqMD5(salt=" + Utils.toHexString(md5Salt) + ")");
/*     */               }
/* 261 */               if (password == null) {
/* 262 */                 throw new PSQLException(GT.tr("The server requested password-based authentication, but no password was provided."), PSQLState.CONNECTION_REJECTED);
/*     */               }
/* 264 */               digest = MD5Digest.encode(user, password, md5Salt);
/* 265 */               if (Driver.logDebug) {
/* 266 */                 Driver.debug(" FE=> Password(md5digest=" + new String(digest, "US-ASCII") + ")");
/*     */               }
/* 268 */               pgStream.SendInteger4(4 + digest.length + 1);
/* 269 */               pgStream.Send(digest);
/* 270 */               pgStream.SendChar(0);
/* 271 */               pgStream.flush();
/*     */               continue;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case 3:
/* 278 */               if (Driver.logDebug) {
/* 279 */                 Driver.debug(" <=BE AuthenticationReqPassword");
/*     */               }
/* 281 */               if (password == null) {
/* 282 */                 throw new PSQLException(GT.tr("The server requested password-based authentication, but no password was provided."), PSQLState.CONNECTION_REJECTED);
/*     */               }
/* 284 */               if (Driver.logDebug) {
/* 285 */                 Driver.debug(" FE=> Password(password=<not shown>)");
/*     */               }
/* 287 */               encodedPassword = password.getBytes("US-ASCII");
/* 288 */               pgStream.SendInteger4(4 + encodedPassword.length + 1);
/* 289 */               pgStream.Send(encodedPassword);
/* 290 */               pgStream.SendChar(0);
/* 291 */               pgStream.flush();
/*     */               continue;
/*     */ 
/*     */ 
/*     */             
/*     */             case 0:
/* 297 */               if (Driver.logDebug) {
/* 298 */                 Driver.debug(" <=BE AuthenticationOk");
/*     */               }
/*     */               return;
/*     */           } 
/*     */           
/* 303 */           if (Driver.logDebug) {
/* 304 */             Driver.debug(" <=BE AuthenticationReq (unsupported type " + areq + ")");
/*     */           }
/* 306 */           throw new PSQLException(GT.tr("The authentication type {0} is not supported. Check that you have configured the pg_hba.conf file to include the client''s IP address or subnet, and that it is using an authentication scheme supported by the driver.", new Integer(areq)), PSQLState.CONNECTION_REJECTED);
/*     */       } 
/*     */ 
/*     */       
/*     */       break;
/*     */     } 
/* 312 */     throw new PSQLException(GT.tr("Protocol error.  Session setup failed."), PSQLState.CONNECTION_UNABLE_TO_CONNECT);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readStartupMessages(PGStream pgStream, ProtocolConnectionImpl protoConnection) throws IOException, SQLException {
/*     */     while (true) {
/*     */       int pid, ckey;
/*     */       String errorMsg, warnMsg;
/* 320 */       int beresp = pgStream.ReceiveChar();
/* 321 */       switch (beresp) {
/*     */         
/*     */         case 90:
/* 324 */           if (Driver.logDebug) {
/* 325 */             Driver.debug(" <=BE ReadyForQuery");
/*     */           }
/*     */           return;
/*     */         case 75:
/* 329 */           pid = pgStream.ReceiveIntegerR(4);
/* 330 */           ckey = pgStream.ReceiveIntegerR(4);
/* 331 */           if (Driver.logDebug)
/* 332 */             Driver.debug(" <=BE BackendKeyData(pid=" + pid + ",ckey=" + ckey + ")"); 
/* 333 */           protoConnection.setBackendKeyData(pid, ckey);
/*     */           continue;
/*     */         
/*     */         case 69:
/* 337 */           errorMsg = pgStream.ReceiveString();
/* 338 */           if (Driver.logDebug)
/* 339 */             Driver.debug(" <=BE ErrorResponse(" + errorMsg + ")"); 
/* 340 */           throw new PSQLException(GT.tr("Backend start-up failed: {0}.", errorMsg), PSQLState.CONNECTION_UNABLE_TO_CONNECT);
/*     */         
/*     */         case 78:
/* 343 */           warnMsg = pgStream.ReceiveString();
/* 344 */           if (Driver.logDebug)
/* 345 */             Driver.debug(" <=BE NoticeResponse(" + warnMsg + ")"); 
/* 346 */           protoConnection.addWarning(new SQLWarning(warnMsg)); continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 350 */     throw new PSQLException(GT.tr("Protocol error.  Session setup failed."), PSQLState.CONNECTION_UNABLE_TO_CONNECT);
/*     */   }
/*     */   
/*     */   private class SimpleResultHandler implements ResultHandler {
/*     */     private SQLException error;
/*     */     private Vector tuples;
/*     */     private final ProtocolConnectionImpl protoConnection;
/*     */     private final ConnectionFactoryImpl this$0;
/*     */     
/*     */     SimpleResultHandler(ConnectionFactoryImpl this$0, ProtocolConnectionImpl protoConnection) {
/* 360 */       this.this$0 = this$0;
/* 361 */       this.protoConnection = protoConnection;
/*     */     }
/*     */     
/*     */     Vector getResults() {
/* 365 */       return this.tuples;
/*     */     }
/*     */     
/*     */     public void handleResultRows(Query fromQuery, Field[] fields, Vector tuples, ResultCursor cursor) {
/* 369 */       this.tuples = tuples;
/*     */     }
/*     */ 
/*     */     
/*     */     public void handleCommandStatus(String status, int updateCount, long insertOID) {}
/*     */     
/*     */     public void handleWarning(SQLWarning warning) {
/* 376 */       this.protoConnection.addWarning(warning);
/*     */     }
/*     */     
/*     */     public void handleError(SQLException newError) {
/* 380 */       if (this.error == null) {
/* 381 */         this.error = newError;
/*     */       } else {
/* 383 */         this.error.setNextException(newError);
/*     */       } 
/*     */     }
/*     */     public void handleCompletion() throws SQLException {
/* 387 */       if (this.error != null) {
/* 388 */         throw this.error;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[][] runSetupQuery(ProtocolConnectionImpl protoConnection, String queryString, boolean wantResults) throws SQLException {
/* 395 */     QueryExecutor executor = protoConnection.getQueryExecutor();
/* 396 */     Query query = executor.createSimpleQuery(queryString);
/* 397 */     SimpleResultHandler handler = new SimpleResultHandler(this, protoConnection);
/*     */     
/* 399 */     int flags = 17;
/* 400 */     if (!wantResults) {
/* 401 */       flags |= 0x6;
/*     */     }
/*     */     
/*     */     try {
/* 405 */       executor.execute(query, null, handler, 0, 0, flags);
/*     */     }
/*     */     finally {
/*     */       
/* 409 */       query.close();
/*     */     } 
/*     */     
/* 412 */     if (!wantResults) {
/* 413 */       return null;
/*     */     }
/* 415 */     Vector tuples = handler.getResults();
/* 416 */     if (tuples == null || tuples.size() != 1) {
/* 417 */       throw new PSQLException(GT.tr("An unexpected result was returned by a query."), PSQLState.CONNECTION_UNABLE_TO_CONNECT);
/*     */     }
/* 419 */     return tuples.elementAt(0);
/*     */   }
/*     */   
/*     */   private void runInitialQueries(ProtocolConnectionImpl protoConnection, String charSet) throws SQLException, IOException {
/* 423 */     byte[][] results = runSetupQuery(protoConnection, "set datestyle = 'ISO'; select version(), case when pg_encoding_to_char(1) = 'SQL_ASCII' then 'UNKNOWN' else getdatabaseencoding() end", true);
/*     */     
/* 425 */     String rawDbVersion = protoConnection.getEncoding().decode(results[0]);
/* 426 */     StringTokenizer versionParts = new StringTokenizer(rawDbVersion);
/* 427 */     versionParts.nextToken();
/* 428 */     String dbVersion = versionParts.nextToken();
/*     */     
/* 430 */     protoConnection.setServerVersion(dbVersion);
/*     */     
/* 432 */     if (dbVersion.compareTo("7.3") >= 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 439 */       if (Driver.logDebug) {
/* 440 */         Driver.debug("Switching to UNICODE client_encoding");
/*     */       }
/* 442 */       runSetupQuery(protoConnection, "begin; set autocommit = on; set client_encoding = 'UNICODE'; commit", false);
/* 443 */       protoConnection.setEncoding(Encoding.getDatabaseEncoding("UNICODE"));
/*     */     }
/*     */     else {
/*     */       
/* 447 */       String dbEncoding = (results[1] == null) ? null : protoConnection.getEncoding().decode(results[1]);
/* 448 */       if (Driver.logDebug) {
/*     */         
/* 450 */         Driver.debug("Specified charset:  " + charSet);
/* 451 */         Driver.debug("Database encoding: " + dbEncoding);
/*     */       } 
/*     */       
/* 454 */       if (charSet != null) {
/*     */ 
/*     */         
/* 457 */         protoConnection.setEncoding(Encoding.getJVMEncoding(charSet));
/*     */       }
/* 459 */       else if (dbEncoding != null) {
/*     */ 
/*     */         
/* 462 */         protoConnection.setEncoding(Encoding.getDatabaseEncoding(dbEncoding));
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 468 */         protoConnection.setEncoding(Encoding.defaultEncoding());
/*     */       } 
/*     */     } 
/*     */     
/* 472 */     if (Driver.logDebug)
/* 473 */       Driver.debug("Connection encoding (using JVM's nomenclature): " + protoConnection.getEncoding()); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\v2\ConnectionFactoryImpl.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */