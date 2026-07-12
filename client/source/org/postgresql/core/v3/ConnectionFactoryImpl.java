/*     */ package org.postgresql.core.v3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.ConnectException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.util.Properties;
/*     */ import org.postgresql.Driver;
/*     */ import org.postgresql.core.ConnectionFactory;
/*     */ import org.postgresql.core.Encoding;
/*     */ import org.postgresql.core.PGStream;
/*     */ import org.postgresql.core.ProtocolConnection;
/*     */ import org.postgresql.core.Utils;
/*     */ import org.postgresql.util.GT;
/*     */ import org.postgresql.util.MD5Digest;
/*     */ import org.postgresql.util.PSQLException;
/*     */ import org.postgresql.util.PSQLState;
/*     */ import org.postgresql.util.PSQLWarning;
/*     */ import org.postgresql.util.ServerErrorMessage;
/*     */ import org.postgresql.util.UnixCrypt;
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
/*     */   private static class UnsupportedProtocolException
/*     */     extends IOException
/*     */   {
/*     */     private UnsupportedProtocolException() {}
/*     */   }
/*     */   
/*     */   public ProtocolConnection openConnectionImpl(String host, int port, String user, String database, Properties info) throws SQLException {
/*  50 */     boolean requireSSL = (info.getProperty("ssl") != null);
/*  51 */     boolean trySSL = requireSSL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     if (Driver.logDebug) {
/*  61 */       Driver.debug("Trying to establish a protocol version 3 connection to " + host + ":" + port);
/*     */     }
/*  63 */     if (!Driver.sslEnabled()) {
/*     */       
/*  65 */       if (requireSSL)
/*  66 */         throw new PSQLException(GT.tr("The driver does not support SSL."), PSQLState.CONNECTION_FAILURE); 
/*  67 */       trySSL = false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     PGStream newStream = null;
/*     */     
/*     */     try {
/*  77 */       newStream = new PGStream(host, port);
/*     */ 
/*     */       
/*  80 */       if (trySSL) {
/*  81 */         newStream = enableSSL(newStream, requireSSL, info);
/*     */       }
/*     */       
/*  84 */       String[][] params = { { "user", user }, { "database", database }, { "client_encoding", "UNICODE" }, { "DateStyle", "ISO" } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  91 */       sendStartupPacket(newStream, params);
/*     */ 
/*     */       
/*  94 */       doAuthentication(newStream, user, info.getProperty("password"));
/*     */ 
/*     */       
/*  97 */       ProtocolConnectionImpl protoConnection = new ProtocolConnectionImpl(newStream, user, database, info);
/*  98 */       readStartupMessages(newStream, protoConnection);
/*     */ 
/*     */       
/* 101 */       return protoConnection;
/*     */     
/*     */     }
/*     */     catch (UnsupportedProtocolException upe) {
/*     */       
/* 106 */       if (Driver.logDebug) {
/* 107 */         Driver.debug("Protocol not supported, abandoning connection.");
/*     */       }
/*     */       try {
/* 110 */         newStream.close();
/*     */       
/*     */       }
/* 113 */       catch (IOException e) {}
/*     */       
/* 115 */       return null;
/*     */ 
/*     */     
/*     */     }
/*     */     catch (ConnectException cex) {
/*     */ 
/*     */       
/* 122 */       throw new PSQLException(GT.tr("Connection refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections."), PSQLState.CONNECTION_REJECTED, cex);
/*     */     }
/*     */     catch (IOException ioe) {
/*     */       
/* 126 */       if (newStream != null) {
/*     */         
/*     */         try {
/*     */           
/* 130 */           newStream.close();
/*     */         
/*     */         }
/* 133 */         catch (IOException e) {}
/*     */       }
/*     */       
/* 136 */       throw new PSQLException(GT.tr("The connection attempt failed."), PSQLState.CONNECTION_UNABLE_TO_CONNECT, ioe);
/*     */     }
/*     */     catch (SQLException se) {
/*     */       
/* 140 */       if (newStream != null) {
/*     */         
/*     */         try {
/*     */           
/* 144 */           newStream.close();
/*     */         
/*     */         }
/* 147 */         catch (IOException e) {}
/*     */       }
/*     */       
/* 150 */       throw se;
/*     */     } 
/*     */   }
/*     */   
/*     */   private PGStream enableSSL(PGStream pgStream, boolean requireSSL, Properties info) throws IOException, SQLException {
/* 155 */     if (Driver.logDebug) {
/* 156 */       Driver.debug(" FE=> SSLRequest");
/*     */     }
/*     */     
/* 159 */     pgStream.SendInteger4(8);
/* 160 */     pgStream.SendInteger2(1234);
/* 161 */     pgStream.SendInteger2(5679);
/* 162 */     pgStream.flush();
/*     */ 
/*     */     
/* 165 */     int beresp = pgStream.ReceiveChar();
/* 166 */     switch (beresp) {
/*     */       
/*     */       case 69:
/* 169 */         if (Driver.logDebug) {
/* 170 */           Driver.debug(" <=BE SSLError");
/*     */         }
/*     */         
/* 173 */         if (requireSSL) {
/* 174 */           throw new PSQLException(GT.tr("The server does not support SSL."), PSQLState.CONNECTION_FAILURE);
/*     */         }
/*     */         
/* 177 */         pgStream.close();
/* 178 */         return new PGStream(pgStream.getHost(), pgStream.getPort());
/*     */       
/*     */       case 78:
/* 181 */         if (Driver.logDebug) {
/* 182 */           Driver.debug(" <=BE SSLRefused");
/*     */         }
/*     */         
/* 185 */         if (requireSSL) {
/* 186 */           throw new PSQLException(GT.tr("The server does not support SSL."), PSQLState.CONNECTION_FAILURE);
/*     */         }
/* 188 */         return pgStream;
/*     */       
/*     */       case 83:
/* 191 */         if (Driver.logDebug) {
/* 192 */           Driver.debug(" <=BE SSLOk");
/*     */         }
/*     */         
/* 195 */         Driver.makeSSL(pgStream, info);
/* 196 */         return pgStream;
/*     */     } 
/*     */     
/* 199 */     throw new PSQLException(GT.tr("An error occured while setting up the SSL connection."), PSQLState.CONNECTION_FAILURE);
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendStartupPacket(PGStream pgStream, String[][] params) throws IOException {
/* 204 */     if (Driver.logDebug) {
/*     */       
/* 206 */       String details = "";
/* 207 */       for (int k = 0; k < params.length; k++) {
/*     */         
/* 209 */         if (k != 0)
/* 210 */           details = details + ", "; 
/* 211 */         details = details + params[k][0] + "=" + params[k][1];
/*     */       } 
/* 213 */       Driver.debug(" FE=> StartupPacket(" + details + ")");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     int length = 8;
/* 220 */     byte[][] encodedParams = new byte[params.length * 2][];
/* 221 */     for (int i = 0; i < params.length; i++) {
/*     */       
/* 223 */       encodedParams[i * 2] = params[i][0].getBytes("US-ASCII");
/* 224 */       encodedParams[i * 2 + 1] = params[i][1].getBytes("US-ASCII");
/* 225 */       length += (encodedParams[i * 2]).length + 1 + (encodedParams[i * 2 + 1]).length + 1;
/*     */     } 
/*     */     
/* 228 */     length++;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     pgStream.SendInteger4(length);
/* 234 */     pgStream.SendInteger2(3);
/* 235 */     pgStream.SendInteger2(0);
/* 236 */     for (int j = 0; j < encodedParams.length; j++) {
/*     */       
/* 238 */       pgStream.Send(encodedParams[j]);
/* 239 */       pgStream.SendChar(0);
/*     */     } 
/*     */     
/* 242 */     pgStream.SendChar(0);
/* 243 */     pgStream.flush(); }
/*     */   private void doAuthentication(PGStream pgStream, String user, String password) throws IOException, SQLException { while (true) {
/*     */       int l_elen;
/*     */       ServerErrorMessage errorMsg;
/*     */       int l_msgLen, areq;
/*     */       byte[] rst, md5Salt, encodedPassword;
/*     */       String salt;
/*     */       byte[] digest;
/*     */       String result;
/*     */       byte[] encodedResult;
/* 253 */       int beresp = pgStream.ReceiveChar();
/*     */       
/* 255 */       switch (beresp) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 69:
/* 264 */           l_elen = pgStream.ReceiveIntegerR(4);
/* 265 */           if (l_elen > 30000)
/*     */           {
/*     */ 
/*     */             
/* 269 */             throw new UnsupportedProtocolException();
/*     */           }
/*     */           
/* 272 */           errorMsg = new ServerErrorMessage(pgStream.ReceiveString(l_elen - 4));
/* 273 */           if (Driver.logDebug)
/* 274 */             Driver.debug(" <=BE ErrorMessage(" + errorMsg + ")"); 
/* 275 */           throw new PSQLException(errorMsg);
/*     */ 
/*     */ 
/*     */         
/*     */         case 82:
/* 280 */           l_msgLen = pgStream.ReceiveIntegerR(4);
/*     */ 
/*     */           
/* 283 */           areq = pgStream.ReceiveIntegerR(4);
/*     */ 
/*     */           
/* 286 */           switch (areq) {
/*     */ 
/*     */             
/*     */             case 4:
/* 290 */               rst = new byte[2];
/* 291 */               rst[0] = (byte)pgStream.ReceiveChar();
/* 292 */               rst[1] = (byte)pgStream.ReceiveChar();
/* 293 */               salt = new String(rst, 0, 2, "US-ASCII");
/*     */               
/* 295 */               if (Driver.logDebug) {
/* 296 */                 Driver.debug(" <=BE AuthenticationReqCrypt(salt='" + salt + "')");
/*     */               }
/* 298 */               if (password == null) {
/* 299 */                 throw new PSQLException(GT.tr("The server requested password-based authentication, but no password was provided."), PSQLState.CONNECTION_REJECTED);
/*     */               }
/* 301 */               result = UnixCrypt.crypt(salt, password);
/* 302 */               encodedResult = result.getBytes("US-ASCII");
/*     */               
/* 304 */               if (Driver.logDebug) {
/* 305 */                 Driver.debug(" FE=> Password(crypt='" + result + "')");
/*     */               }
/* 307 */               pgStream.SendChar(112);
/* 308 */               pgStream.SendInteger4(4 + encodedResult.length + 1);
/* 309 */               pgStream.Send(encodedResult);
/* 310 */               pgStream.SendChar(0);
/* 311 */               pgStream.flush();
/*     */               continue;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case 5:
/* 318 */               md5Salt = pgStream.Receive(4);
/* 319 */               if (Driver.logDebug)
/*     */               {
/* 321 */                 Driver.debug(" <=BE AuthenticationReqMD5(salt=" + Utils.toHexString(md5Salt) + ")");
/*     */               }
/*     */               
/* 324 */               if (password == null) {
/* 325 */                 throw new PSQLException(GT.tr("The server requested password-based authentication, but no password was provided."), PSQLState.CONNECTION_REJECTED);
/*     */               }
/* 327 */               digest = MD5Digest.encode(user, password, md5Salt);
/*     */               
/* 329 */               if (Driver.logDebug)
/*     */               {
/* 331 */                 Driver.debug(" FE=> Password(md5digest=" + new String(digest, "US-ASCII") + ")");
/*     */               }
/*     */               
/* 334 */               pgStream.SendChar(112);
/* 335 */               pgStream.SendInteger4(4 + digest.length + 1);
/* 336 */               pgStream.Send(digest);
/* 337 */               pgStream.SendChar(0);
/* 338 */               pgStream.flush();
/*     */               continue;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case 3:
/* 345 */               if (Driver.logDebug) {
/*     */                 
/* 347 */                 Driver.debug(" <=BE AuthenticationReqPassword");
/* 348 */                 Driver.debug(" FE=> Password(password=<not shown>)");
/*     */               } 
/*     */               
/* 351 */               if (password == null) {
/* 352 */                 throw new PSQLException(GT.tr("The server requested password-based authentication, but no password was provided."), PSQLState.CONNECTION_REJECTED);
/*     */               }
/* 354 */               encodedPassword = password.getBytes("US-ASCII");
/*     */               
/* 356 */               pgStream.SendChar(112);
/* 357 */               pgStream.SendInteger4(4 + encodedPassword.length + 1);
/* 358 */               pgStream.Send(encodedPassword);
/* 359 */               pgStream.SendChar(0);
/* 360 */               pgStream.flush();
/*     */               continue;
/*     */ 
/*     */ 
/*     */             
/*     */             case 0:
/* 366 */               if (Driver.logDebug) {
/* 367 */                 Driver.debug(" <=BE AuthenticationOk");
/*     */               }
/*     */               return;
/*     */           } 
/*     */           
/* 372 */           if (Driver.logDebug) {
/* 373 */             Driver.debug(" <=BE AuthenticationReq (unsupported type " + areq + ")");
/*     */           }
/* 375 */           throw new PSQLException(GT.tr("The authentication type {0} is not supported. Check that you have configured the pg_hba.conf file to include the client''s IP address or subnet, and that it is using an authentication scheme supported by the driver.", new Integer(areq)), PSQLState.CONNECTION_REJECTED);
/*     */       } 
/*     */ 
/*     */       
/*     */       break;
/*     */     } 
/* 381 */     throw new PSQLException(GT.tr("Protocol error.  Session setup failed."), PSQLState.CONNECTION_UNABLE_TO_CONNECT); } private void readStartupMessages(PGStream pgStream, ProtocolConnectionImpl protoConnection) throws IOException, SQLException { int beresp; while (true) {
/*     */       char tStatus;
/*     */       int l_msgLen, pid, ckey, l_elen;
/*     */       ServerErrorMessage l_errorMsg;
/*     */       int l_nlen;
/*     */       ServerErrorMessage l_warnMsg;
/*     */       int l_len;
/*     */       String name, value;
/* 389 */       beresp = pgStream.ReceiveChar();
/* 390 */       switch (beresp) {
/*     */ 
/*     */         
/*     */         case 90:
/* 394 */           if (pgStream.ReceiveIntegerR(4) != 5) {
/* 395 */             throw new IOException("unexpected length of ReadyForQuery packet");
/*     */           }
/* 397 */           tStatus = (char)pgStream.ReceiveChar();
/* 398 */           if (Driver.logDebug) {
/* 399 */             Driver.debug(" <=BE ReadyForQuery(" + tStatus + ")");
/*     */           }
/*     */           
/* 402 */           switch (tStatus) {
/*     */             
/*     */             case 'I':
/* 405 */               protoConnection.setTransactionState(0);
/*     */               break;
/*     */             case 'T':
/* 408 */               protoConnection.setTransactionState(1);
/*     */               break;
/*     */             case 'E':
/* 411 */               protoConnection.setTransactionState(2);
/*     */               break;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 75:
/* 422 */           l_msgLen = pgStream.ReceiveIntegerR(4);
/* 423 */           if (l_msgLen != 12) {
/* 424 */             throw new PSQLException(GT.tr("Protocol error.  Session setup failed."), PSQLState.CONNECTION_UNABLE_TO_CONNECT);
/*     */           }
/* 426 */           pid = pgStream.ReceiveIntegerR(4);
/* 427 */           ckey = pgStream.ReceiveIntegerR(4);
/*     */           
/* 429 */           if (Driver.logDebug) {
/* 430 */             Driver.debug(" <=BE BackendKeyData(pid=" + pid + ",ckey=" + ckey + ")");
/*     */           }
/* 432 */           protoConnection.setBackendKeyData(pid, ckey);
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 69:
/* 437 */           l_elen = pgStream.ReceiveIntegerR(4);
/* 438 */           l_errorMsg = new ServerErrorMessage(pgStream.ReceiveString(l_elen - 4));
/*     */           
/* 440 */           if (Driver.logDebug) {
/* 441 */             Driver.debug(" <=BE ErrorMessage(" + l_errorMsg + ")");
/*     */           }
/* 443 */           throw new PSQLException(l_errorMsg);
/*     */ 
/*     */         
/*     */         case 78:
/* 447 */           l_nlen = pgStream.ReceiveIntegerR(4);
/* 448 */           l_warnMsg = new ServerErrorMessage(pgStream.ReceiveString(l_nlen - 4));
/*     */           
/* 450 */           if (Driver.logDebug) {
/* 451 */             Driver.debug(" <=BE NoticeResponse(" + l_warnMsg + ")");
/*     */           }
/* 453 */           protoConnection.addWarning((SQLWarning)new PSQLWarning(l_warnMsg));
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 83:
/* 458 */           l_len = pgStream.ReceiveIntegerR(4);
/* 459 */           name = pgStream.ReceiveString();
/* 460 */           value = pgStream.ReceiveString();
/*     */           
/* 462 */           if (Driver.logDebug) {
/* 463 */             Driver.debug(" <=BE ParameterStatus(" + name + " = " + value + ")");
/*     */           }
/* 465 */           if (name.equals("server_version")) {
/* 466 */             protoConnection.setServerVersion(value); continue;
/* 467 */           }  if (name.equals("client_encoding")) {
/*     */             
/* 469 */             if (!value.equals("UNICODE"))
/* 470 */               throw new PSQLException(GT.tr("Protocol error.  Session setup failed."), PSQLState.CONNECTION_UNABLE_TO_CONNECT); 
/* 471 */             pgStream.setEncoding(Encoding.getDatabaseEncoding("UNICODE"));
/*     */           } 
/*     */           continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 477 */     if (Driver.logDebug)
/* 478 */       Driver.debug("invalid message type=" + (char)beresp); 
/* 479 */     throw new PSQLException(GT.tr("Protocol error.  Session setup failed."), PSQLState.CONNECTION_UNABLE_TO_CONNECT); }
/*     */ 
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\v3\ConnectionFactoryImpl.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */