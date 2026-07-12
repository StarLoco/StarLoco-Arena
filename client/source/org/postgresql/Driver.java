/*     */ package org.postgresql;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Driver;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.DriverPropertyInfo;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import org.postgresql.core.PGStream;
/*     */ import org.postgresql.jdbc3.Jdbc3Connection;
/*     */ import org.postgresql.ssl.MakeSSL;
/*     */ import org.postgresql.util.GT;
/*     */ import org.postgresql.util.PSQLDriverVersion;
/*     */ import org.postgresql.util.PSQLException;
/*     */ import org.postgresql.util.PSQLState;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Driver
/*     */   implements Driver
/*     */ {
/*     */   public static final int DEBUG = 2;
/*     */   public static final int INFO = 1;
/*     */   public static boolean logDebug = false;
/*     */   public static boolean logInfo = false;
/*     */   private Properties defaultProperties;
/*     */   
/*     */   static {
/*     */     try {
/*  63 */       DriverManager.registerDriver(new Driver());
/*     */     }
/*     */     catch (SQLException e) {
/*     */       
/*  67 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized Properties getDefaultProperties() throws IOException {
/*  75 */     if (this.defaultProperties != null) {
/*  76 */       return this.defaultProperties;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  82 */       this.defaultProperties = AccessController.<Properties>doPrivileged(new PrivilegedExceptionAction(this)
/*     */           {
/*     */             public Object run() throws IOException {
/*  85 */               return this.this$0.loadDefaultProperties();
/*     */             }
/*     */             
/*     */             private final Driver this$0;
/*     */           });
/*     */     } catch (PrivilegedActionException e) {
/*  91 */       throw (IOException)e.getException();
/*     */     } 
/*     */     
/*  94 */     return this.defaultProperties;
/*     */   }
/*     */   
/*     */   private Properties loadDefaultProperties() throws IOException {
/*  98 */     Properties merged = new Properties();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     ClassLoader cl = getClass().getClassLoader();
/* 109 */     if (cl == null) {
/* 110 */       cl = ClassLoader.getSystemClassLoader();
/*     */     }
/* 112 */     if (cl == null) {
/* 113 */       if (logDebug)
/* 114 */         debug("Can't find a classloader for the Driver; not loading driver configuration"); 
/* 115 */       return merged;
/*     */     } 
/*     */     
/* 118 */     if (logDebug) {
/* 119 */       debug("Loading driver configuration via classloader " + cl);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     ArrayList urls = new ArrayList();
/* 126 */     Enumeration urlEnum = cl.getResources("org/postgresql/driverconfig.properties");
/* 127 */     while (urlEnum.hasMoreElements())
/*     */     {
/* 129 */       urls.add(urlEnum.nextElement());
/*     */     }
/*     */     
/* 132 */     for (int i = urls.size() - 1; i >= 0; i--) {
/* 133 */       URL url = urls.get(i);
/* 134 */       if (logDebug)
/* 135 */         debug("Loading driver configuration from: " + url); 
/* 136 */       InputStream is = url.openStream();
/* 137 */       merged.load(is);
/* 138 */       is.close();
/*     */     } 
/*     */     
/* 141 */     return merged;
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
/*     */   public Connection connect(String url, Properties info) throws SQLException {
/*     */     Properties defaults;
/*     */     try {
/* 207 */       defaults = getDefaultProperties();
/*     */     }
/*     */     catch (IOException ioe) {
/*     */       
/* 211 */       throw new PSQLException(GT.tr("Error loading default settings from driverconfig.properties"), PSQLState.UNEXPECTED_ERROR, ioe);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 216 */     Properties props = new Properties(defaults);
/* 217 */     for (Enumeration e = info.propertyNames(); e.hasMoreElements(); ) {
/*     */       
/* 219 */       String propName = (String)e.nextElement();
/* 220 */       props.setProperty(propName, info.getProperty(propName));
/*     */     } 
/*     */ 
/*     */     
/* 224 */     if ((props = parseURL(url, props)) == null) {
/*     */       
/* 226 */       if (logDebug)
/* 227 */         debug("Error in url" + url); 
/* 228 */       return null;
/*     */     } 
/*     */     
/*     */     try {
/* 232 */       if (logDebug) {
/* 233 */         debug("connect " + url);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 243 */       long timeout = timeout(props);
/* 244 */       if (timeout <= 0L) {
/* 245 */         return makeConnection(url, props);
/*     */       }
/* 247 */       ConnectThread ct = new ConnectThread(url, props);
/* 248 */       (new Thread(ct, "PostgreSQL JDBC driver connection thread")).start();
/* 249 */       return ct.getResult(timeout);
/*     */     
/*     */     }
/*     */     catch (PSQLException ex1) {
/*     */ 
/*     */       
/* 255 */       throw ex1;
/*     */     }
/*     */     catch (Exception ex2) {
/*     */       
/* 259 */       if (logDebug)
/*     */       {
/* 261 */         debug("error", ex2);
/*     */       }
/* 263 */       throw new PSQLException(GT.tr("Something unusual has occured to cause the driver to fail. Please report this exception."), PSQLState.UNEXPECTED_ERROR, ex2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class ConnectThread implements Runnable {
/*     */     private final String url;
/*     */     private final Properties props;
/*     */     private Connection result;
/*     */     private Throwable resultException;
/*     */     private boolean abandoned;
/*     */     
/*     */     ConnectThread(String url, Properties props) {
/* 275 */       this.url = url;
/* 276 */       this.props = props;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       Connection connection;
/*     */       Throwable throwable;
/*     */       try {
/* 284 */         connection = Driver.makeConnection(this.url, this.props);
/* 285 */         throwable = null;
/*     */       } catch (Throwable t) {
/* 287 */         connection = null;
/* 288 */         throwable = t;
/*     */       } 
/*     */       
/* 291 */       synchronized (this) {
/* 292 */         if (this.abandoned) {
/* 293 */           if (connection != null) {
/*     */             try {
/* 295 */               connection.close();
/* 296 */             } catch (SQLException e) {}
/*     */           }
/*     */         } else {
/* 299 */           this.result = connection;
/* 300 */           this.resultException = throwable;
/* 301 */           notify();
/*     */         } 
/*     */       } 
/*     */     }
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
/*     */     public Connection getResult(long timeout) throws SQLException {
/* 316 */       long expiry = System.currentTimeMillis() + timeout;
/* 317 */       synchronized (this) {
/*     */         while (true) {
/* 319 */           if (this.result != null) {
/* 320 */             return this.result;
/*     */           }
/* 322 */           if (this.resultException != null) {
/* 323 */             if (this.resultException instanceof SQLException) {
/* 324 */               this.resultException.fillInStackTrace();
/* 325 */               throw (SQLException)this.resultException;
/*     */             } 
/* 327 */             throw new PSQLException(GT.tr("Something unusual has occured to cause the driver to fail. Please report this exception."), PSQLState.UNEXPECTED_ERROR, this.resultException);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 332 */           long delay = expiry - System.currentTimeMillis();
/* 333 */           if (delay <= 0L) {
/* 334 */             this.abandoned = true;
/* 335 */             throw new PSQLException(GT.tr("Connection attempt timed out."), PSQLState.CONNECTION_UNABLE_TO_CONNECT);
/*     */           } 
/*     */ 
/*     */           
/*     */           try {
/* 340 */             wait(delay);
/*     */           } catch (InterruptedException ie) {
/* 342 */             this.abandoned = true;
/* 343 */             throw new PSQLException(GT.tr("Interrupted while attempting to connect."), PSQLState.CONNECTION_UNABLE_TO_CONNECT);
/*     */           } 
/*     */         } 
/*     */       } 
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
/*     */   private static Connection makeConnection(String url, Properties props) throws SQLException {
/* 369 */     return (Connection)new Jdbc3Connection(host(props), port(props), user(props), database(props), props, url);
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
/*     */   public boolean acceptsURL(String url) throws SQLException {
/* 388 */     if (parseURL(url, null) == null)
/* 389 */       return false; 
/* 390 */     return true;
/*     */   }
/*     */   
/* 393 */   private static final Object[][] knownProperties = new Object[][] { { "PGDBNAME", Boolean.TRUE, "Database name to connect to; may be specified directly in the JDBC URL." }, { "user", Boolean.TRUE, "Username to connect to the database as.", null }, { "PGHOST", Boolean.FALSE, "Hostname of the PostgreSQL server; may be specified directly in the JDBC URL." }, { "PGPORT", Boolean.FALSE, "Port number to connect to the PostgreSQL server on; may be specified directly in the JDBC URL." }, { "password", Boolean.FALSE, "Password to use when authenticating." }, { "protocolVersion", Boolean.FALSE, "Force use of a particular protocol version when connecting; if set, disables protocol version fallback." }, { "ssl", Boolean.FALSE, "Control use of SSL; any nonnull value causes SSL to be required." }, { "sslfactory", Boolean.FALSE, "Provide a SSLSocketFactory class when using SSL." }, { "sslfactoryarg", Boolean.FALSE, "Argument forwarded to constructor of SSLSocketFactory class." }, { "logLevel", Boolean.FALSE, "Control the driver's log verbosity: 0 is off, 1 is INFO, 2 is DEBUG.", { "0", "1", "2" } }, { "allowEncodingChanges", Boolean.FALSE, "Allow the user to change the client_encoding variable." }, { "prepareThreshold", Boolean.FALSE, "Default statement prepare threshold (numeric)." }, { "charSet", Boolean.FALSE, "When connecting to a pre-7.3 server, the database encoding to assume is in use." }, { "compatible", Boolean.FALSE, "Force compatibility of some features with an older version of the driver.", { "7.1", "7.2", "7.3" } }, { "loginTimeout", Boolean.FALSE, "The login timeout, in seconds; 0 means no timeout beyond the normal TCP connection timout." } };
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
/*     */   public static final int MAJORVERSION = 8;
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
/*     */   public static final int MINORVERSION = 1;
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
/*     */   public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
/* 448 */     Properties copy = new Properties(info);
/* 449 */     parseURL(url, copy);
/*     */     
/* 451 */     DriverPropertyInfo[] props = new DriverPropertyInfo[knownProperties.length];
/* 452 */     for (int i = 0; i < knownProperties.length; i++) {
/*     */       
/* 454 */       String name = (String)knownProperties[i][0];
/* 455 */       props[i] = new DriverPropertyInfo(name, copy.getProperty(name));
/* 456 */       (props[i]).required = ((Boolean)knownProperties[i][1]).booleanValue();
/* 457 */       (props[i]).description = (String)knownProperties[i][2];
/* 458 */       if ((knownProperties[i]).length > 3) {
/* 459 */         (props[i]).choices = (String[])knownProperties[i][3];
/*     */       }
/*     */     } 
/* 462 */     return props;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMajorVersion() {
/* 473 */     return 8;
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
/*     */   public int getMinorVersion() {
/* 485 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 494 */     return "PostgreSQL 8.1 JDBC3 with SSL (build " + PSQLDriverVersion.buildNumber + ")";
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
/*     */   public boolean jdbcCompliant() {
/* 509 */     return false;
/*     */   }
/*     */   
/* 512 */   private static String[] protocols = new String[] { "jdbc", "postgresql" };
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
/*     */   Properties parseURL(String url, Properties defaults) throws SQLException {
/* 524 */     int state = -1;
/* 525 */     Properties urlProps = new Properties(defaults);
/*     */     
/* 527 */     String l_urlServer = url;
/* 528 */     String l_urlArgs = "";
/*     */     
/* 530 */     int l_qPos = url.indexOf('?');
/* 531 */     if (l_qPos != -1) {
/*     */       
/* 533 */       l_urlServer = url.substring(0, l_qPos);
/* 534 */       l_urlArgs = url.substring(l_qPos + 1);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 540 */     int ipv6start = l_urlServer.indexOf("[");
/* 541 */     int ipv6end = l_urlServer.indexOf("]");
/* 542 */     String ipv6address = null;
/* 543 */     if (ipv6start != -1 && ipv6end > ipv6start) {
/*     */       
/* 545 */       ipv6address = l_urlServer.substring(ipv6start + 1, ipv6end);
/* 546 */       l_urlServer = l_urlServer.substring(0, ipv6start) + "ipv6host" + l_urlServer.substring(ipv6end + 1);
/*     */     } 
/*     */ 
/*     */     
/* 550 */     StringTokenizer st = new StringTokenizer(l_urlServer, ":/", true);
/*     */     int count;
/* 552 */     for (count = 0; st.hasMoreTokens(); count++) {
/*     */       
/* 554 */       String token = st.nextToken();
/*     */ 
/*     */       
/* 557 */       if (count <= 3) {
/*     */         
/* 559 */         if (count % 2 != 1 || !token.equals(":"))
/*     */         {
/* 561 */           if (count % 2 == 0) {
/*     */             
/* 563 */             boolean found = (count == 0);
/* 564 */             for (int tmp = 0; tmp < protocols.length; tmp++) {
/*     */               
/* 566 */               if (token.equals(protocols[tmp]))
/*     */               {
/*     */ 
/*     */                 
/* 570 */                 if (count == 2 && tmp > 0) {
/*     */                   
/* 572 */                   urlProps.setProperty("Protocol", token);
/* 573 */                   found = true;
/*     */                 } 
/*     */               }
/*     */             } 
/*     */             
/* 578 */             if (!found) {
/* 579 */               return null;
/*     */             }
/*     */           } else {
/* 582 */             return null;
/*     */           }  } 
/* 584 */       } else if (count > 3) {
/*     */         
/* 586 */         if (count == 4 && token.equals("/"))
/* 587 */         { state = 0; }
/* 588 */         else if (count == 4)
/*     */         
/* 590 */         { urlProps.setProperty("PGDBNAME", token);
/* 591 */           state = -2; }
/*     */         
/* 593 */         else if (count == 5 && state == 0 && token.equals("/"))
/* 594 */         { state = 1; }
/* 595 */         else { if (count == 5 && state == 0)
/* 596 */             return null; 
/* 597 */           if (count == 6 && state == 1) {
/* 598 */             urlProps.setProperty("PGHOST", token);
/* 599 */           } else if (count == 7 && token.equals(":")) {
/* 600 */             state = 2;
/* 601 */           } else if (count == 8 && state == 2) {
/*     */             
/*     */             try
/*     */             {
/* 605 */               Integer portNumber = Integer.decode(token);
/* 606 */               urlProps.setProperty("PGPORT", portNumber.toString());
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 610 */               return null;
/*     */             }
/*     */           
/* 613 */           } else if ((count == 7 || count == 9) && (state == 1 || state == 2) && token.equals("/")) {
/*     */             
/* 615 */             state = -1;
/* 616 */           } else if (state == -1) {
/*     */             
/* 618 */             urlProps.setProperty("PGDBNAME", token);
/* 619 */             state = -2;
/*     */           }  }
/*     */       
/*     */       } 
/* 623 */     }  if (count <= 1)
/*     */     {
/* 625 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 629 */     if (ipv6address != null) {
/* 630 */       urlProps.setProperty("PGHOST", ipv6address);
/*     */     }
/*     */     
/* 633 */     StringTokenizer qst = new StringTokenizer(l_urlArgs, "&");
/* 634 */     for (count = 0; qst.hasMoreTokens(); count++) {
/*     */       
/* 636 */       String token = qst.nextToken();
/* 637 */       int l_pos = token.indexOf('=');
/* 638 */       if (l_pos == -1) {
/*     */         
/* 640 */         urlProps.setProperty(token, "");
/*     */       }
/*     */       else {
/*     */         
/* 644 */         urlProps.setProperty(token.substring(0, l_pos), token.substring(l_pos + 1));
/*     */       } 
/*     */     } 
/*     */     
/* 648 */     return urlProps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String host(Properties props) {
/* 657 */     return props.getProperty("PGHOST", "localhost");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int port(Properties props) {
/* 665 */     return Integer.parseInt(props.getProperty("PGPORT", "5432"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String user(Properties props) {
/* 673 */     return props.getProperty("user", "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String database(Properties props) {
/* 681 */     return props.getProperty("PGDBNAME", "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long timeout(Properties props) {
/*     */     try {
/* 690 */       return (long)(Float.parseFloat(props.getProperty("loginTimeout", "0")) * 1000.0F);
/*     */     } catch (NumberFormatException e) {
/* 692 */       return 0L;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SQLException notImplemented(Class callClass, String functionName) {
/* 711 */     String message = GT.tr("Method {0} is not yet implemented.", callClass.getName() + "." + functionName);
/* 712 */     if (logDebug)
/* 713 */       debug(message); 
/* 714 */     return (SQLException)new PSQLException(message, PSQLState.NOT_IMPLEMENTED);
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
/*     */   public static void setLogLevel(int logLevel) {
/* 726 */     logDebug = (logLevel >= 2);
/* 727 */     logInfo = (logLevel >= 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void debug(String msg) {
/* 735 */     if (logDebug)
/*     */     {
/* 737 */       DriverManager.println(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void debug(String msg, Exception ex) {
/* 746 */     if (logDebug) {
/*     */       
/* 748 */       DriverManager.println(msg);
/* 749 */       if (ex != null)
/*     */       {
/* 751 */         DriverManager.println(ex.toString());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void info(String msg) {
/* 761 */     if (logInfo)
/*     */     {
/* 763 */       DriverManager.println(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void info(String msg, Exception ex) {
/* 772 */     if (logInfo) {
/*     */       
/* 774 */       DriverManager.println(msg);
/* 775 */       if (ex != null)
/*     */       {
/* 777 */         DriverManager.println(ex.toString());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void makeSSL(PGStream stream, Properties info) throws IOException, SQLException {
/* 784 */     MakeSSL.convert(stream, info);
/*     */   }
/*     */   
/*     */   public static boolean sslEnabled() {
/* 788 */     boolean l_return = false;
/* 789 */     l_return = true;
/* 790 */     return l_return;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\Driver.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */