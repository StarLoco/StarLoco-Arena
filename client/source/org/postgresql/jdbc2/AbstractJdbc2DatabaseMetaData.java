/*      */ package org.postgresql.jdbc2;
/*      */ 
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import org.postgresql.Driver;
/*      */ import org.postgresql.core.BaseStatement;
/*      */ import org.postgresql.core.Field;
/*      */ import org.postgresql.util.GT;
/*      */ import org.postgresql.util.PSQLException;
/*      */ import org.postgresql.util.PSQLState;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractJdbc2DatabaseMetaData
/*      */ {
/*      */   private static final String keywords = "abort,acl,add,aggregate,append,archive,arch_store,backward,binary,boolean,change,cluster,copy,database,delimiter,delimiters,do,extend,explain,forward,heavy,index,inherits,isnull,light,listen,load,merge,nothing,notify,notnull,oids,purge,rename,replace,retrieve,returns,rule,recipe,setof,stdin,stdout,store,vacuum,verbose,version";
/*      */   protected final AbstractJdbc2Connection connection;
/*      */   protected static final int VARHDRSZ = 4;
/*      */   private int NAMEDATALEN;
/*      */   private int INDEX_MAX_KEYS;
/*      */   
/*      */   public AbstractJdbc2DatabaseMetaData(AbstractJdbc2Connection conn) {
/*   42 */     this.NAMEDATALEN = 0;
/*   43 */     this.INDEX_MAX_KEYS = 0;
/*      */     this.connection = conn;
/*      */   } protected int getMaxIndexKeys() throws SQLException {
/*   46 */     if (this.INDEX_MAX_KEYS == 0) {
/*      */       String str;
/*      */       
/*   49 */       if (this.connection.haveMinimumServerVersion("8.0")) {
/*   50 */         str = "SELECT setting FROM pg_catalog.pg_settings WHERE name='max_index_keys'";
/*      */       } else {
/*      */         String str1;
/*   53 */         if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */           
/*   55 */           str1 = "pg_catalog.pg_namespace n, pg_catalog.pg_type t1, pg_catalog.pg_type t2 WHERE t1.typnamespace=n.oid AND n.nspname='pg_catalog' AND ";
/*      */         }
/*      */         else {
/*      */           
/*   59 */           str1 = "pg_type t1, pg_type t2 WHERE ";
/*      */         } 
/*   61 */         str = "SELECT t1.typlen/t2.typlen FROM " + str1 + " t1.typelem=t2.oid AND t1.typname='oidvector'";
/*      */       } 
/*   63 */       ResultSet rs = this.connection.createStatement().executeQuery(str);
/*   64 */       if (!rs.next())
/*      */       {
/*   66 */         throw new PSQLException(GT.tr("Unable to determine a value for MaxIndexKeys due to missing system catalog data."), PSQLState.UNEXPECTED_ERROR);
/*      */       }
/*   68 */       this.INDEX_MAX_KEYS = rs.getInt(1);
/*   69 */       rs.close();
/*      */     } 
/*   71 */     return this.INDEX_MAX_KEYS;
/*      */   }
/*      */   
/*      */   protected int getMaxNameLength() throws SQLException {
/*   75 */     if (this.NAMEDATALEN == 0) {
/*      */       String str;
/*      */       
/*   78 */       if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */         
/*   80 */         str = "SELECT t.typlen FROM pg_catalog.pg_type t, pg_catalog.pg_namespace n WHERE t.typnamespace=n.oid AND t.typname='name' AND n.nspname='pg_catalog'";
/*      */       }
/*      */       else {
/*      */         
/*   84 */         str = "SELECT typlen FROM pg_type WHERE typname='name'";
/*      */       } 
/*   86 */       ResultSet rs = this.connection.createStatement().executeQuery(str);
/*   87 */       if (!rs.next())
/*      */       {
/*   89 */         throw new PSQLException(GT.tr("Unable to find name datatype in the system catalogs."), PSQLState.UNEXPECTED_ERROR);
/*      */       }
/*   91 */       this.NAMEDATALEN = rs.getInt("typlen");
/*   92 */       rs.close();
/*      */     } 
/*   94 */     return this.NAMEDATALEN - 1;
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
/*      */   public boolean allProceduresAreCallable() throws SQLException {
/*  107 */     if (Driver.logDebug)
/*  108 */       Driver.debug("allProceduresAreCallable"); 
/*  109 */     return true;
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
/*      */   public boolean allTablesAreSelectable() throws SQLException {
/*  121 */     if (Driver.logDebug)
/*  122 */       Driver.debug("allTablesAreSelectable"); 
/*  123 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getURL() throws SQLException {
/*  134 */     String url = this.connection.getURL();
/*  135 */     if (Driver.logDebug)
/*  136 */       Driver.debug("getURL " + url); 
/*  137 */     return url;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUserName() throws SQLException {
/*  148 */     String userName = this.connection.getUserName();
/*  149 */     if (Driver.logDebug)
/*  150 */       Driver.debug("getUserName " + userName); 
/*  151 */     return userName;
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
/*  162 */     boolean isReadOnly = this.connection.isReadOnly();
/*  163 */     if (Driver.logDebug)
/*  164 */       Driver.debug("isReadOnly " + isReadOnly); 
/*  165 */     return isReadOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nullsAreSortedHigh() throws SQLException {
/*  176 */     boolean nullSortedHigh = this.connection.haveMinimumServerVersion("7.2");
/*  177 */     if (Driver.logDebug)
/*  178 */       Driver.debug("nullsAreSortedHigh " + nullSortedHigh); 
/*  179 */     return nullSortedHigh;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nullsAreSortedLow() throws SQLException {
/*  190 */     if (Driver.logDebug)
/*  191 */       Driver.debug("nullsAreSortedLow false"); 
/*  192 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nullsAreSortedAtStart() throws SQLException {
/*  203 */     if (Driver.logDebug)
/*  204 */       Driver.debug("nullsAreSortedAtStart false"); 
/*  205 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean nullsAreSortedAtEnd() throws SQLException {
/*  216 */     boolean nullsAreSortedAtEnd = !this.connection.haveMinimumServerVersion("7.2");
/*  217 */     if (Driver.logDebug)
/*  218 */       Driver.debug("nullsAreSortedAtEnd " + nullsAreSortedAtEnd); 
/*  219 */     return nullsAreSortedAtEnd;
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
/*      */   public String getDatabaseProductName() throws SQLException {
/*  231 */     if (Driver.logDebug)
/*  232 */       Driver.debug("getDatabaseProductName PostgresSQL"); 
/*  233 */     return "PostgreSQL";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDatabaseProductVersion() throws SQLException {
/*  244 */     String versionNumber = this.connection.getDBVersionNumber();
/*  245 */     if (Driver.logDebug)
/*  246 */       Driver.debug("getDatabaseProductVersion " + versionNumber); 
/*  247 */     return versionNumber;
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
/*      */   public String getDriverName() throws SQLException {
/*  259 */     String driverName = "PostgreSQL Native Driver";
/*  260 */     if (Driver.logDebug)
/*  261 */       Driver.debug("getDriverName" + driverName); 
/*  262 */     return driverName;
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
/*      */   public String getDriverVersion() throws SQLException {
/*  274 */     String driverVersion = Driver.getVersion();
/*  275 */     if (Driver.logDebug)
/*  276 */       Driver.debug("getDriverVersion " + driverVersion); 
/*  277 */     return driverVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDriverMajorVersion() {
/*  287 */     return 8;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDriverMinorVersion() {
/*  297 */     return 1;
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
/*      */   public boolean usesLocalFiles() throws SQLException {
/*  309 */     if (Driver.logDebug)
/*  310 */       Driver.debug("usesLocalFiles false"); 
/*  311 */     return false;
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
/*      */   public boolean usesLocalFilePerTable() throws SQLException {
/*  323 */     if (Driver.logDebug)
/*  324 */       Driver.debug("usesLocalFilePerTable false"); 
/*  325 */     return false;
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
/*      */   public boolean supportsMixedCaseIdentifiers() throws SQLException {
/*  338 */     if (Driver.logDebug)
/*  339 */       Driver.debug("supportsMixedCaseIdentifiers false"); 
/*  340 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean storesUpperCaseIdentifiers() throws SQLException {
/*  351 */     if (Driver.logDebug)
/*  352 */       Driver.debug("storesUpperCaseIdentifiers false"); 
/*  353 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean storesLowerCaseIdentifiers() throws SQLException {
/*  364 */     if (Driver.logDebug)
/*  365 */       Driver.debug("storesLowerCaseIdentifiers true"); 
/*  366 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean storesMixedCaseIdentifiers() throws SQLException {
/*  377 */     if (Driver.logDebug)
/*  378 */       Driver.debug("storesMixedCaseIdentifiers false"); 
/*  379 */     return false;
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
/*      */   public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
/*  392 */     if (Driver.logDebug)
/*  393 */       Driver.debug("supportsMixedCaseQuotedIdentifiers true"); 
/*  394 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
/*  405 */     if (Driver.logDebug)
/*  406 */       Driver.debug("storesUpperCaseQuotedIdentifiers false"); 
/*  407 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
/*  418 */     if (Driver.logDebug)
/*  419 */       Driver.debug("storesLowerCaseQuotedIdentifiers false"); 
/*  420 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
/*  431 */     if (Driver.logDebug)
/*  432 */       Driver.debug("storesMixedCaseQuotedIdentifiers false"); 
/*  433 */     return false;
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
/*      */   public String getIdentifierQuoteString() throws SQLException {
/*  446 */     if (Driver.logDebug)
/*  447 */       Driver.debug("getIdentifierQuoteString \""); 
/*  448 */     return "\"";
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
/*      */   public String getSQLKeywords() throws SQLException {
/*  469 */     return "abort,acl,add,aggregate,append,archive,arch_store,backward,binary,boolean,change,cluster,copy,database,delimiter,delimiters,do,extend,explain,forward,heavy,index,inherits,isnull,light,listen,load,merge,nothing,notify,notnull,oids,purge,rename,replace,retrieve,returns,rule,recipe,setof,stdin,stdout,store,vacuum,verbose,version";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNumericFunctions() throws SQLException {
/*  478 */     if (Driver.logDebug)
/*  479 */       Driver.debug("getNumericFunctions"); 
/*  480 */     return "abs,acos,asin,atan,atan2,ceiling,cos,cot,degrees,exp,floor,log,log10,mod,pi,power,radians,rand,round,sign,sin,sqrt,tan,truncate";
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
/*      */   public String getStringFunctions() throws SQLException {
/*  497 */     if (Driver.logDebug)
/*  498 */       Driver.debug("getStringFunctions"); 
/*  499 */     String funcs = "ascii,char,concat,lcase,left,length,ltrim,repeat,rtrim,space,substring,ucase";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  515 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*  516 */       funcs = funcs + ",replace";
/*      */     }
/*      */     
/*  519 */     return funcs;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getSystemFunctions() throws SQLException {
/*  524 */     if (Driver.logDebug)
/*  525 */       Driver.debug("getSystemFunctions"); 
/*  526 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*  527 */       return "database,ifnull,user";
/*      */     }
/*      */     
/*  530 */     return "ifnull,user";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTimeDateFunctions() throws SQLException {
/*  537 */     if (Driver.logDebug)
/*  538 */       Driver.debug("getTimeDateFunctions"); 
/*  539 */     return "curdate,curtime,dayname,dayofmonth,dayofweek,dayofyear,hour,minute,month,monthname,now,quarter,second,week,year";
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
/*      */   public String getSearchStringEscape() throws SQLException {
/*  558 */     if (Driver.logDebug) {
/*  559 */       Driver.debug("getSearchStringEscape");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  569 */     return "\\\\";
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
/*      */   public String getExtraNameCharacters() throws SQLException {
/*  589 */     if (Driver.logDebug)
/*  590 */       Driver.debug("getExtraNameCharacters"); 
/*  591 */     return "";
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
/*      */   public boolean supportsAlterTableWithAddColumn() throws SQLException {
/*  603 */     if (Driver.logDebug)
/*  604 */       Driver.debug("supportsAlterTableWithAddColumn true"); 
/*  605 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsAlterTableWithDropColumn() throws SQLException {
/*  616 */     boolean dropColumn = this.connection.haveMinimumServerVersion("7.3");
/*  617 */     if (Driver.logDebug)
/*  618 */       Driver.debug("supportsAlterTableWithDropColumn " + dropColumn); 
/*  619 */     return dropColumn;
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
/*      */   public boolean supportsColumnAliasing() throws SQLException {
/*  642 */     if (Driver.logDebug)
/*  643 */       Driver.debug("supportsColumnAliasing true"); 
/*  644 */     return true;
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
/*      */   public boolean nullPlusNonNullIsNull() throws SQLException {
/*  656 */     if (Driver.logDebug)
/*  657 */       Driver.debug("nullPlusNonNullIsNull true"); 
/*  658 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean supportsConvert() throws SQLException {
/*  663 */     if (Driver.logDebug)
/*  664 */       Driver.debug("supportsConvert false"); 
/*  665 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean supportsConvert(int fromType, int toType) throws SQLException {
/*  670 */     if (Driver.logDebug)
/*  671 */       Driver.debug("supportsConvert false"); 
/*  672 */     return false;
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
/*      */   public boolean supportsTableCorrelationNames() throws SQLException {
/*  684 */     if (Driver.logDebug)
/*  685 */       Driver.debug("supportsTableCorrelationNames true"); 
/*  686 */     return true;
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
/*      */   public boolean supportsDifferentTableCorrelationNames() throws SQLException {
/*  698 */     if (Driver.logDebug)
/*  699 */       Driver.debug("supportsDifferentTableCorrelationNames false"); 
/*  700 */     return false;
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
/*      */   public boolean supportsExpressionsInOrderBy() throws SQLException {
/*  713 */     if (Driver.logDebug)
/*  714 */       Driver.debug("supportsExpressionsInOrderBy true"); 
/*  715 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsOrderByUnrelated() throws SQLException {
/*  726 */     boolean supportsOrderByUnrelated = this.connection.haveMinimumServerVersion("6.4");
/*  727 */     if (Driver.logDebug)
/*  728 */       Driver.debug("supportsOrderByUnrelated " + supportsOrderByUnrelated); 
/*  729 */     return supportsOrderByUnrelated;
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
/*      */   public boolean supportsGroupBy() throws SQLException {
/*  741 */     if (Driver.logDebug)
/*  742 */       Driver.debug("supportsGroupBy true"); 
/*  743 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsGroupByUnrelated() throws SQLException {
/*  754 */     boolean supportsGroupByUnrelated = this.connection.haveMinimumServerVersion("6.4");
/*  755 */     if (Driver.logDebug)
/*  756 */       Driver.debug("supportsGroupByUnrelated " + supportsGroupByUnrelated); 
/*  757 */     return supportsGroupByUnrelated;
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
/*      */   public boolean supportsGroupByBeyondSelect() throws SQLException {
/*  772 */     boolean supportsGroupByBeyondSelect = this.connection.haveMinimumServerVersion("6.4");
/*  773 */     if (Driver.logDebug)
/*  774 */       Driver.debug("supportsGroupByUnrelated " + supportsGroupByBeyondSelect); 
/*  775 */     return supportsGroupByBeyondSelect;
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
/*      */   public boolean supportsLikeEscapeClause() throws SQLException {
/*  787 */     boolean supportsLikeEscapeClause = this.connection.haveMinimumServerVersion("7.1");
/*  788 */     if (Driver.logDebug)
/*  789 */       Driver.debug("supportsLikeEscapeClause " + supportsLikeEscapeClause); 
/*  790 */     return supportsLikeEscapeClause;
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
/*      */   public boolean supportsMultipleResultSets() throws SQLException {
/*  803 */     if (Driver.logDebug)
/*  804 */       Driver.debug("supportsMultipleResultSets false"); 
/*  805 */     return false;
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
/*      */   public boolean supportsMultipleTransactions() throws SQLException {
/*  818 */     if (Driver.logDebug)
/*  819 */       Driver.debug("supportsMultipleTransactions true"); 
/*  820 */     return true;
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
/*      */   public boolean supportsNonNullableColumns() throws SQLException {
/*  835 */     if (Driver.logDebug)
/*  836 */       Driver.debug("supportsNonNullableColumns true"); 
/*  837 */     return true;
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
/*      */   public boolean supportsMinimumSQLGrammar() throws SQLException {
/*  854 */     if (Driver.logDebug)
/*  855 */       Driver.debug("supportsMinimumSQLGrammar TRUE"); 
/*  856 */     return true;
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
/*      */   public boolean supportsCoreSQLGrammar() throws SQLException {
/*  868 */     if (Driver.logDebug)
/*  869 */       Driver.debug("supportsCoreSQLGrammar FALSE "); 
/*  870 */     return false;
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
/*      */   public boolean supportsExtendedSQLGrammar() throws SQLException {
/*  883 */     if (Driver.logDebug)
/*  884 */       Driver.debug("supportsExtendedSQLGrammar FALSE"); 
/*  885 */     return false;
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
/*      */   public boolean supportsANSI92EntryLevelSQL() throws SQLException {
/*  902 */     boolean schemas = this.connection.haveMinimumServerVersion("7.3");
/*  903 */     if (Driver.logDebug)
/*  904 */       Driver.debug("supportsANSI92EntryLevelSQL " + schemas); 
/*  905 */     return schemas;
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
/*      */   public boolean supportsANSI92IntermediateSQL() throws SQLException {
/*  917 */     if (Driver.logDebug)
/*  918 */       Driver.debug("supportsANSI92IntermediateSQL false "); 
/*  919 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsANSI92FullSQL() throws SQLException {
/*  930 */     if (Driver.logDebug)
/*  931 */       Driver.debug("supportsANSI92FullSQL false "); 
/*  932 */     return false;
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
/*      */   public boolean supportsIntegrityEnhancementFacility() throws SQLException {
/*  944 */     if (Driver.logDebug)
/*  945 */       Driver.debug("supportsIntegrityEnhancementFacility true "); 
/*  946 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsOuterJoins() throws SQLException {
/*  957 */     boolean supportsOuterJoins = this.connection.haveMinimumServerVersion("7.1");
/*  958 */     if (Driver.logDebug)
/*  959 */       Driver.debug("supportsOuterJoins " + supportsOuterJoins); 
/*  960 */     return supportsOuterJoins;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsFullOuterJoins() throws SQLException {
/*  971 */     boolean supportsFullOuterJoins = this.connection.haveMinimumServerVersion("7.1");
/*  972 */     if (Driver.logDebug)
/*  973 */       Driver.debug("supportsFullOuterJoins " + supportsFullOuterJoins); 
/*  974 */     return supportsFullOuterJoins;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsLimitedOuterJoins() throws SQLException {
/*  985 */     boolean supportsLimitedOuterJoins = this.connection.haveMinimumServerVersion("7.1");
/*  986 */     if (Driver.logDebug)
/*  987 */       Driver.debug("supportsFullOuterJoins " + supportsLimitedOuterJoins); 
/*  988 */     return supportsLimitedOuterJoins;
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
/*      */   public String getSchemaTerm() throws SQLException {
/* 1001 */     if (Driver.logDebug)
/* 1002 */       Driver.debug("getSchemaTerm schema"); 
/* 1003 */     return "schema";
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
/*      */   public String getProcedureTerm() throws SQLException {
/* 1015 */     if (Driver.logDebug)
/* 1016 */       Driver.debug("getProcedureTerm function "); 
/* 1017 */     return "function";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCatalogTerm() throws SQLException {
/* 1028 */     if (Driver.logDebug)
/* 1029 */       Driver.debug("getCatalogTerm database "); 
/* 1030 */     return "database";
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
/*      */   public boolean isCatalogAtStart() throws SQLException {
/* 1044 */     if (Driver.logDebug)
/* 1045 */       Driver.debug("isCatalogAtStart not implemented"); 
/* 1046 */     return true;
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
/*      */   public String getCatalogSeparator() throws SQLException {
/* 1059 */     if (Driver.logDebug)
/* 1060 */       Driver.debug("getCatalogSeparator not implemented "); 
/* 1061 */     return ".";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSchemasInDataManipulation() throws SQLException {
/* 1072 */     boolean schemas = this.connection.haveMinimumServerVersion("7.3");
/* 1073 */     if (Driver.logDebug)
/* 1074 */       Driver.debug("supportsSchemasInDataManipulation " + schemas); 
/* 1075 */     return schemas;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSchemasInProcedureCalls() throws SQLException {
/* 1086 */     boolean schemas = this.connection.haveMinimumServerVersion("7.3");
/* 1087 */     if (Driver.logDebug)
/* 1088 */       Driver.debug("supportsSchemasInProcedureCalls " + schemas); 
/* 1089 */     return schemas;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSchemasInTableDefinitions() throws SQLException {
/* 1100 */     boolean schemas = this.connection.haveMinimumServerVersion("7.3");
/*      */     
/* 1102 */     if (Driver.logDebug)
/* 1103 */       Driver.debug("supportsSchemasInTableDefinitions " + schemas); 
/* 1104 */     return schemas;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSchemasInIndexDefinitions() throws SQLException {
/* 1115 */     boolean schemas = this.connection.haveMinimumServerVersion("7.3");
/* 1116 */     if (Driver.logDebug)
/* 1117 */       Driver.debug("supportsSchemasInIndexDefinitions " + schemas); 
/* 1118 */     return schemas;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
/* 1129 */     boolean schemas = this.connection.haveMinimumServerVersion("7.3");
/* 1130 */     if (Driver.logDebug)
/* 1131 */       Driver.debug("supportsSchemasInPrivilegeDefinitions " + schemas); 
/* 1132 */     return schemas;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsCatalogsInDataManipulation() throws SQLException {
/* 1143 */     if (Driver.logDebug)
/* 1144 */       Driver.debug("supportsCatalogsInDataManipulation false"); 
/* 1145 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsCatalogsInProcedureCalls() throws SQLException {
/* 1156 */     if (Driver.logDebug)
/* 1157 */       Driver.debug("supportsCatalogsInDataManipulation false"); 
/* 1158 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsCatalogsInTableDefinitions() throws SQLException {
/* 1169 */     if (Driver.logDebug)
/* 1170 */       Driver.debug("supportsCatalogsInTableDefinitions false"); 
/* 1171 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
/* 1182 */     if (Driver.logDebug)
/* 1183 */       Driver.debug("supportsCatalogsInIndexDefinitions false"); 
/* 1184 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
/* 1195 */     if (Driver.logDebug)
/* 1196 */       Driver.debug("supportsCatalogsInPrivilegeDefinitions false"); 
/* 1197 */     return false;
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
/*      */   public boolean supportsPositionedDelete() throws SQLException {
/* 1209 */     if (Driver.logDebug)
/* 1210 */       Driver.debug("supportsPositionedDelete false"); 
/* 1211 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsPositionedUpdate() throws SQLException {
/* 1222 */     if (Driver.logDebug)
/* 1223 */       Driver.debug("supportsPositionedUpdate false"); 
/* 1224 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsSelectForUpdate() throws SQLException {
/* 1235 */     return this.connection.haveMinimumServerVersion("6.5");
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
/*      */   public boolean supportsStoredProcedures() throws SQLException {
/* 1247 */     return true;
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
/*      */   public boolean supportsSubqueriesInComparisons() throws SQLException {
/* 1259 */     return true;
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
/*      */   public boolean supportsSubqueriesInExists() throws SQLException {
/* 1271 */     return true;
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
/*      */   public boolean supportsSubqueriesInIns() throws SQLException {
/* 1283 */     return true;
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
/*      */   public boolean supportsSubqueriesInQuantifieds() throws SQLException {
/* 1298 */     return true;
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
/*      */   public boolean supportsCorrelatedSubqueries() throws SQLException {
/* 1312 */     return this.connection.haveMinimumServerVersion("7.1");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsUnion() throws SQLException {
/* 1323 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsUnionAll() throws SQLException {
/* 1334 */     return this.connection.haveMinimumServerVersion("7.1");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
/* 1345 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
/* 1356 */     return false;
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
/*      */   public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
/* 1370 */     return true;
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
/*      */   public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
/* 1384 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxBinaryLiteralLength() throws SQLException {
/* 1395 */     return 0;
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
/*      */   public int getMaxCharLiteralLength() throws SQLException {
/* 1407 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxColumnNameLength() throws SQLException {
/* 1418 */     return getMaxNameLength();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxColumnsInGroupBy() throws SQLException {
/* 1429 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxColumnsInIndex() throws SQLException {
/* 1440 */     return getMaxIndexKeys();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxColumnsInOrderBy() throws SQLException {
/* 1451 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxColumnsInSelect() throws SQLException {
/* 1462 */     return 0;
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
/*      */   public int getMaxColumnsInTable() throws SQLException {
/* 1479 */     return 1600;
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
/*      */   public int getMaxConnections() throws SQLException {
/* 1496 */     return 8192;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxCursorNameLength() throws SQLException {
/* 1507 */     return getMaxNameLength();
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
/*      */   public int getMaxIndexLength() throws SQLException {
/* 1521 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxSchemaNameLength() throws SQLException {
/* 1526 */     return getMaxNameLength();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxProcedureNameLength() throws SQLException {
/* 1537 */     return getMaxNameLength();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxCatalogNameLength() throws SQLException {
/* 1542 */     return getMaxNameLength();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxRowSize() throws SQLException {
/* 1553 */     if (this.connection.haveMinimumServerVersion("7.1")) {
/* 1554 */       return 1073741824;
/*      */     }
/* 1556 */     return 8192;
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
/*      */   public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
/* 1568 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxStatementLength() throws SQLException {
/* 1579 */     if (this.connection.haveMinimumServerVersion("7.0")) {
/* 1580 */       return 0;
/*      */     }
/* 1582 */     return 16384;
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
/*      */   public int getMaxStatements() throws SQLException {
/* 1598 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxTableNameLength() throws SQLException {
/* 1609 */     return getMaxNameLength();
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
/*      */   public int getMaxTablesInSelect() throws SQLException {
/* 1621 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxUserNameLength() throws SQLException {
/* 1632 */     return getMaxNameLength();
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
/*      */   public int getDefaultTransactionIsolation() throws SQLException {
/* 1646 */     return 2;
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
/*      */   public boolean supportsTransactions() throws SQLException {
/* 1659 */     return true;
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
/*      */   public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
/* 1673 */     if (level == 8 || level == 2)
/*      */     {
/* 1675 */       return true; } 
/* 1676 */     if (this.connection.haveMinimumServerVersion("8.0") && (level == 1 || level == 4)) {
/* 1677 */       return true;
/*      */     }
/* 1679 */     return false;
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
/*      */   public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
/* 1691 */     return true;
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
/*      */   public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
/* 1703 */     return false;
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
/*      */   public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
/* 1727 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
/* 1738 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String escapeQuotes(String s) {
/* 1745 */     StringBuffer sb = new StringBuffer();
/* 1746 */     int length = s.length();
/* 1747 */     for (int i = 0; i < length; i++) {
/*      */       
/* 1749 */       char c = s.charAt(i);
/* 1750 */       if (c == '\'' || c == '\\')
/*      */       {
/* 1752 */         sb.append('\\');
/*      */       }
/* 1754 */       sb.append(c);
/*      */     } 
/* 1756 */     return sb.toString();
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
/*      */   public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
/*      */     String str;
/* 1794 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */       
/* 1796 */       str = "SELECT NULL AS PROCEDURE_CAT, n.nspname AS PROCEDURE_SCHEM, p.proname AS PROCEDURE_NAME, NULL, NULL, NULL, d.description AS REMARKS, 2 AS PROCEDURE_TYPE  FROM pg_catalog.pg_namespace n, pg_catalog.pg_proc p  LEFT JOIN pg_catalog.pg_description d ON (p.oid=d.objoid)  LEFT JOIN pg_catalog.pg_class c ON (d.classoid=c.oid AND c.relname='pg_proc')  LEFT JOIN pg_catalog.pg_namespace pn ON (c.relnamespace=pn.oid AND pn.nspname='pg_catalog')  WHERE p.pronamespace=n.oid ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1802 */       if (schemaPattern != null && !"".equals(schemaPattern))
/*      */       {
/* 1804 */         str = str + " AND n.nspname LIKE '" + escapeQuotes(schemaPattern) + "' ";
/*      */       }
/* 1806 */       if (procedureNamePattern != null)
/*      */       {
/* 1808 */         str = str + " AND p.proname LIKE '" + escapeQuotes(procedureNamePattern) + "' ";
/*      */       }
/* 1810 */       str = str + " ORDER BY PROCEDURE_SCHEM, PROCEDURE_NAME ";
/*      */     }
/* 1812 */     else if (this.connection.haveMinimumServerVersion("7.1")) {
/*      */       
/* 1814 */       str = "SELECT NULL AS PROCEDURE_CAT, NULL AS PROCEDURE_SCHEM, p.proname AS PROCEDURE_NAME, NULL, NULL, NULL, d.description AS REMARKS, 2 AS PROCEDURE_TYPE  FROM pg_proc p  LEFT JOIN pg_description d ON (p.oid=d.objoid) ";
/*      */ 
/*      */       
/* 1817 */       if (this.connection.haveMinimumServerVersion("7.2"))
/*      */       {
/* 1819 */         str = str + " LEFT JOIN pg_class c ON (d.classoid=c.oid AND c.relname='pg_proc') ";
/*      */       }
/* 1821 */       if (procedureNamePattern != null)
/*      */       {
/* 1823 */         str = str + " WHERE p.proname LIKE '" + escapeQuotes(procedureNamePattern) + "' ";
/*      */       }
/* 1825 */       str = str + " ORDER BY PROCEDURE_NAME ";
/*      */     }
/*      */     else {
/*      */       
/* 1829 */       str = "SELECT NULL AS PROCEDURE_CAT, NULL AS PROCEDURE_SCHEM, p.proname AS PROCEDURE_NAME, NULL, NULL, NULL, NULL AS REMARKS, 2 AS PROCEDURE_TYPE  FROM pg_proc p ";
/*      */       
/* 1831 */       if (procedureNamePattern != null)
/*      */       {
/* 1833 */         str = str + " WHERE p.proname LIKE '" + escapeQuotes(procedureNamePattern) + "' ";
/*      */       }
/* 1835 */       str = str + " ORDER BY PROCEDURE_NAME ";
/*      */     } 
/* 1837 */     return createMetaDataStatement().executeQuery(str);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
/*      */     String str;
/* 1888 */     Field[] f = new Field[13];
/* 1889 */     Vector v = new Vector();
/*      */     
/* 1891 */     f[0] = new Field("PROCEDURE_CAT", 1043, getMaxNameLength());
/* 1892 */     f[1] = new Field("PROCEDURE_SCHEM", 1043, getMaxNameLength());
/* 1893 */     f[2] = new Field("PROCEDURE_NAME", 1043, getMaxNameLength());
/* 1894 */     f[3] = new Field("COLUMN_NAME", 1043, getMaxNameLength());
/* 1895 */     f[4] = new Field("COLUMN_TYPE", 21, 2);
/* 1896 */     f[5] = new Field("DATA_TYPE", 21, 2);
/* 1897 */     f[6] = new Field("TYPE_NAME", 1043, getMaxNameLength());
/* 1898 */     f[7] = new Field("PRECISION", 23, 4);
/* 1899 */     f[8] = new Field("LENGTH", 23, 4);
/* 1900 */     f[9] = new Field("SCALE", 21, 2);
/* 1901 */     f[10] = new Field("RADIX", 21, 2);
/* 1902 */     f[11] = new Field("NULLABLE", 21, 2);
/* 1903 */     f[12] = new Field("REMARKS", 1043, getMaxNameLength());
/*      */ 
/*      */     
/* 1906 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */       
/* 1908 */       str = "SELECT n.nspname,p.proname,p.prorettype,p.proargtypes, t.typtype,t.typrelid  FROM pg_catalog.pg_proc p,pg_catalog.pg_namespace n, pg_catalog.pg_type t  WHERE p.pronamespace=n.oid AND p.prorettype=t.oid ";
/*      */ 
/*      */       
/* 1911 */       if (schemaPattern != null && !"".equals(schemaPattern))
/*      */       {
/* 1913 */         str = str + " AND n.nspname LIKE '" + escapeQuotes(schemaPattern) + "' ";
/*      */       }
/* 1915 */       if (procedureNamePattern != null)
/*      */       {
/* 1917 */         str = str + " AND p.proname LIKE '" + escapeQuotes(procedureNamePattern) + "' ";
/*      */       }
/* 1919 */       str = str + " ORDER BY n.nspname, p.proname ";
/*      */     }
/*      */     else {
/*      */       
/* 1923 */       str = "SELECT NULL AS nspname,p.proname,p.prorettype,p.proargtypes, t.typtype,t.typrelid  FROM pg_proc p,pg_type t  WHERE p.prorettype=t.oid ";
/*      */ 
/*      */       
/* 1926 */       if (procedureNamePattern != null)
/*      */       {
/* 1928 */         str = str + " AND p.proname LIKE '" + escapeQuotes(procedureNamePattern) + "' ";
/*      */       }
/* 1930 */       str = str + " ORDER BY p.proname ";
/*      */     } 
/*      */     
/* 1933 */     ResultSet rs = this.connection.createStatement().executeQuery(str);
/* 1934 */     while (rs.next()) {
/*      */       
/* 1936 */       byte[] schema = rs.getBytes("nspname");
/* 1937 */       byte[] procedureName = rs.getBytes("proname");
/* 1938 */       int returnType = rs.getInt("prorettype");
/* 1939 */       String returnTypeType = rs.getString("typtype");
/* 1940 */       int returnTypeRelid = rs.getInt("typrelid");
/* 1941 */       String strArgTypes = rs.getString("proargtypes");
/* 1942 */       StringTokenizer st = new StringTokenizer(strArgTypes);
/* 1943 */       Vector argTypes = new Vector();
/* 1944 */       while (st.hasMoreTokens())
/*      */       {
/* 1946 */         argTypes.addElement(new Integer(st.nextToken()));
/*      */       }
/*      */ 
/*      */       
/* 1950 */       if (!returnTypeType.equals("c")) {
/*      */         
/* 1952 */         byte[][] tuple = new byte[13][];
/* 1953 */         tuple[0] = null;
/* 1954 */         tuple[1] = schema;
/* 1955 */         tuple[2] = procedureName;
/* 1956 */         tuple[3] = this.connection.encodeString("returnValue");
/* 1957 */         tuple[4] = this.connection.encodeString(Integer.toString(5));
/* 1958 */         tuple[5] = this.connection.encodeString(Integer.toString(this.connection.getSQLType(returnType)));
/* 1959 */         tuple[6] = this.connection.encodeString(this.connection.getPGType(returnType));
/* 1960 */         tuple[7] = null;
/* 1961 */         tuple[8] = null;
/* 1962 */         tuple[9] = null;
/* 1963 */         tuple[10] = null;
/* 1964 */         tuple[11] = this.connection.encodeString(Integer.toString(2));
/* 1965 */         tuple[12] = null;
/* 1966 */         v.addElement(tuple);
/*      */       } 
/*      */ 
/*      */       
/* 1970 */       for (int i = 0; i < argTypes.size(); i++) {
/*      */         
/* 1972 */         int argOid = ((Integer)argTypes.elementAt(i)).intValue();
/* 1973 */         byte[][] tuple = new byte[13][];
/* 1974 */         tuple[0] = null;
/* 1975 */         tuple[1] = schema;
/* 1976 */         tuple[2] = procedureName;
/* 1977 */         tuple[3] = this.connection.encodeString("$" + (i + 1));
/* 1978 */         tuple[4] = this.connection.encodeString(Integer.toString(1));
/* 1979 */         tuple[5] = this.connection.encodeString(Integer.toString(this.connection.getSQLType(argOid)));
/* 1980 */         tuple[6] = this.connection.encodeString(this.connection.getPGType(argOid));
/* 1981 */         tuple[7] = null;
/* 1982 */         tuple[8] = null;
/* 1983 */         tuple[9] = null;
/* 1984 */         tuple[10] = null;
/* 1985 */         tuple[11] = this.connection.encodeString(Integer.toString(2));
/* 1986 */         tuple[12] = null;
/* 1987 */         v.addElement(tuple);
/*      */       } 
/*      */ 
/*      */       
/* 1991 */       if (returnTypeType.equals("c")) {
/*      */         
/* 1993 */         String columnsql = "SELECT a.attname,a.atttypid FROM pg_catalog.pg_attribute a WHERE a.attrelid = " + returnTypeRelid + " ORDER BY a.attnum ";
/* 1994 */         ResultSet columnrs = this.connection.createStatement().executeQuery(columnsql);
/* 1995 */         while (columnrs.next()) {
/*      */           
/* 1997 */           int columnTypeOid = columnrs.getInt("atttypid");
/* 1998 */           byte[][] tuple = new byte[13][];
/* 1999 */           tuple[0] = null;
/* 2000 */           tuple[1] = schema;
/* 2001 */           tuple[2] = procedureName;
/* 2002 */           tuple[3] = columnrs.getBytes("attname");
/* 2003 */           tuple[4] = this.connection.encodeString(Integer.toString(3));
/* 2004 */           tuple[5] = this.connection.encodeString(Integer.toString(this.connection.getSQLType(columnTypeOid)));
/* 2005 */           tuple[6] = this.connection.encodeString(this.connection.getPGType(columnTypeOid));
/* 2006 */           tuple[7] = null;
/* 2007 */           tuple[8] = null;
/* 2008 */           tuple[9] = null;
/* 2009 */           tuple[10] = null;
/* 2010 */           tuple[11] = this.connection.encodeString(Integer.toString(2));
/* 2011 */           tuple[12] = null;
/* 2012 */           v.addElement(tuple);
/*      */         } 
/*      */       } 
/*      */     } 
/* 2016 */     rs.close();
/*      */     
/* 2018 */     return ((BaseStatement)createMetaDataStatement()).createDriverResultSet(f, v);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
/*      */     String str1, str2, str3;
/* 2060 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */       
/* 2062 */       str3 = "SCHEMAS";
/* 2063 */       str1 = "SELECT NULL AS TABLE_CAT, n.nspname AS TABLE_SCHEM, c.relname AS TABLE_NAME,  CASE n.nspname LIKE 'pg\\\\_%' OR n.nspname = 'information_schema'  WHEN true THEN CASE  WHEN n.nspname = 'pg_catalog' OR n.nspname = 'information_schema' THEN CASE c.relkind   WHEN 'r' THEN 'SYSTEM TABLE'   WHEN 'v' THEN 'SYSTEM VIEW'   WHEN 'i' THEN 'SYSTEM INDEX'   ELSE NULL   END  WHEN n.nspname = 'pg_toast' THEN CASE c.relkind   WHEN 'r' THEN 'SYSTEM TOAST TABLE'   WHEN 'i' THEN 'SYSTEM TOAST INDEX'   ELSE NULL   END  ELSE CASE c.relkind   WHEN 'r' THEN 'TEMPORARY TABLE'   WHEN 'i' THEN 'TEMPORARY INDEX'   ELSE NULL   END  END  WHEN false THEN CASE c.relkind  WHEN 'r' THEN 'TABLE'  WHEN 'i' THEN 'INDEX'  WHEN 'S' THEN 'SEQUENCE'  WHEN 'v' THEN 'VIEW'  ELSE NULL  END  ELSE NULL  END  AS TABLE_TYPE, d.description AS REMARKS  FROM pg_catalog.pg_namespace n, pg_catalog.pg_class c  LEFT JOIN pg_catalog.pg_description d ON (c.oid = d.objoid AND d.objsubid = 0)  LEFT JOIN pg_catalog.pg_class dc ON (d.classoid=dc.oid AND dc.relname='pg_class')  LEFT JOIN pg_catalog.pg_namespace dn ON (dn.oid=dc.relnamespace AND dn.nspname='pg_catalog')  WHERE c.relnamespace = n.oid ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2098 */       if (schemaPattern != null && !"".equals(schemaPattern))
/*      */       {
/* 2100 */         str1 = str1 + " AND n.nspname LIKE '" + escapeQuotes(schemaPattern) + "' ";
/*      */       }
/* 2102 */       str2 = " ORDER BY TABLE_TYPE,TABLE_SCHEM,TABLE_NAME ";
/*      */     }
/*      */     else {
/*      */       
/* 2106 */       str3 = "NOSCHEMAS";
/* 2107 */       String tableType = " CASE c.relname LIKE 'pg\\\\_%'  WHEN true THEN CASE c.relname LIKE 'pg\\\\_toast\\\\_%'  WHEN true THEN CASE c.relkind   WHEN 'r' THEN 'SYSTEM TOAST TABLE'   WHEN 'i' THEN 'SYSTEM TOAST INDEX'   ELSE NULL   END  WHEN false THEN CASE c.relname LIKE 'pg\\\\_temp\\\\_%'   WHEN true THEN CASE c.relkind    WHEN 'r' THEN 'TEMPORARY TABLE'    WHEN 'i' THEN 'TEMPORARY INDEX'    ELSE NULL    END   WHEN false THEN CASE c.relkind    WHEN 'r' THEN 'SYSTEM TABLE'    WHEN 'v' THEN 'SYSTEM VIEW'    WHEN 'i' THEN 'SYSTEM INDEX'    ELSE NULL    END   ELSE NULL   END  ELSE NULL  END  WHEN false THEN CASE c.relkind  WHEN 'r' THEN 'TABLE'  WHEN 'i' THEN 'INDEX'  WHEN 'S' THEN 'SEQUENCE'  WHEN 'v' THEN 'VIEW'  ELSE NULL  END  ELSE NULL  END ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2140 */       str2 = " ORDER BY TABLE_TYPE,TABLE_NAME ";
/* 2141 */       if (this.connection.haveMinimumServerVersion("7.2")) {
/*      */         
/* 2143 */         str1 = "SELECT NULL AS TABLE_CAT, NULL AS TABLE_SCHEM, c.relname AS TABLE_NAME, " + tableType + " AS TABLE_TYPE, d.description AS REMARKS " + " FROM pg_class c " + " LEFT JOIN pg_description d ON (c.oid=d.objoid AND d.objsubid = 0) " + " LEFT JOIN pg_class dc ON (d.classoid = dc.oid AND dc.relname='pg_class') " + " WHERE true ";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 2149 */       else if (this.connection.haveMinimumServerVersion("7.1")) {
/*      */         
/* 2151 */         str1 = "SELECT NULL AS TABLE_CAT, NULL AS TABLE_SCHEM, c.relname AS TABLE_NAME, " + tableType + " AS TABLE_TYPE, d.description AS REMARKS " + " FROM pg_class c " + " LEFT JOIN pg_description d ON (c.oid=d.objoid) " + " WHERE true ";
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 2158 */         str1 = "SELECT NULL AS TABLE_CAT, NULL AS TABLE_SCHEM, c.relname AS TABLE_NAME, " + tableType + " AS TABLE_TYPE, NULL AS REMARKS " + " FROM pg_class c " + " WHERE true ";
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2164 */     if (tableNamePattern != null)
/*      */     {
/* 2166 */       str1 = str1 + " AND c.relname LIKE '" + escapeQuotes(tableNamePattern) + "' ";
/*      */     }
/* 2168 */     if (types != null) {
/* 2169 */       str1 = str1 + " AND (false ";
/* 2170 */       for (int i = 0; i < types.length; i++) {
/*      */         
/* 2172 */         Hashtable clauses = (Hashtable)tableTypeClauses.get(types[i]);
/* 2173 */         if (clauses != null) {
/*      */           
/* 2175 */           String clause = (String)clauses.get(str3);
/* 2176 */           str1 = str1 + " OR ( " + clause + " ) ";
/*      */         } 
/*      */       } 
/* 2179 */       str1 = str1 + ") ";
/*      */     } 
/* 2181 */     String sql = str1 + str2;
/*      */     
/* 2183 */     return createMetaDataStatement().executeQuery(sql);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 2188 */   private static final Hashtable tableTypeClauses = new Hashtable(); static {
/* 2189 */     Hashtable ht = new Hashtable();
/* 2190 */     tableTypeClauses.put("TABLE", ht);
/* 2191 */     ht.put("SCHEMAS", "c.relkind = 'r' AND n.nspname NOT LIKE 'pg\\\\_%' AND n.nspname <> 'information_schema'");
/* 2192 */     ht.put("NOSCHEMAS", "c.relkind = 'r' AND c.relname NOT LIKE 'pg\\\\_%'");
/* 2193 */     ht = new Hashtable();
/* 2194 */     tableTypeClauses.put("VIEW", ht);
/* 2195 */     ht.put("SCHEMAS", "c.relkind = 'v' AND n.nspname <> 'pg_catalog' AND n.nspname <> 'information_schema'");
/* 2196 */     ht.put("NOSCHEMAS", "c.relkind = 'v' AND c.relname NOT LIKE 'pg\\\\_%'");
/* 2197 */     ht = new Hashtable();
/* 2198 */     tableTypeClauses.put("INDEX", ht);
/* 2199 */     ht.put("SCHEMAS", "c.relkind = 'i' AND n.nspname NOT LIKE 'pg\\\\_%' AND n.nspname <> 'information_schema'");
/* 2200 */     ht.put("NOSCHEMAS", "c.relkind = 'i' AND c.relname NOT LIKE 'pg\\\\_%'");
/* 2201 */     ht = new Hashtable();
/* 2202 */     tableTypeClauses.put("SEQUENCE", ht);
/* 2203 */     ht.put("SCHEMAS", "c.relkind = 'S'");
/* 2204 */     ht.put("NOSCHEMAS", "c.relkind = 'S'");
/* 2205 */     ht = new Hashtable();
/* 2206 */     tableTypeClauses.put("SYSTEM TABLE", ht);
/* 2207 */     ht.put("SCHEMAS", "c.relkind = 'r' AND (n.nspname = 'pg_catalog' OR n.nspname = 'information_schema')");
/* 2208 */     ht.put("NOSCHEMAS", "c.relkind = 'r' AND c.relname LIKE 'pg\\\\_%' AND c.relname NOT LIKE 'pg\\\\_toast\\\\_%' AND c.relname NOT LIKE 'pg\\\\_temp\\\\_%'");
/* 2209 */     ht = new Hashtable();
/* 2210 */     tableTypeClauses.put("SYSTEM TOAST TABLE", ht);
/* 2211 */     ht.put("SCHEMAS", "c.relkind = 'r' AND n.nspname = 'pg_toast'");
/* 2212 */     ht.put("NOSCHEMAS", "c.relkind = 'r' AND c.relname LIKE 'pg\\\\_toast\\\\_%'");
/* 2213 */     ht = new Hashtable();
/* 2214 */     tableTypeClauses.put("SYSTEM TOAST INDEX", ht);
/* 2215 */     ht.put("SCHEMAS", "c.relkind = 'i' AND n.nspname = 'pg_toast'");
/* 2216 */     ht.put("NOSCHEMAS", "c.relkind = 'i' AND c.relname LIKE 'pg\\\\_toast\\\\_%'");
/* 2217 */     ht = new Hashtable();
/* 2218 */     tableTypeClauses.put("SYSTEM VIEW", ht);
/* 2219 */     ht.put("SCHEMAS", "c.relkind = 'v' AND (n.nspname = 'pg_catalog' OR n.nspname = 'information_schema') ");
/* 2220 */     ht.put("NOSCHEMAS", "c.relkind = 'v' AND c.relname LIKE 'pg\\\\_%'");
/* 2221 */     ht = new Hashtable();
/* 2222 */     tableTypeClauses.put("SYSTEM INDEX", ht);
/* 2223 */     ht.put("SCHEMAS", "c.relkind = 'i' AND (n.nspname = 'pg_catalog' OR n.nspname = 'information_schema') ");
/* 2224 */     ht.put("NOSCHEMAS", "c.relkind = 'v' AND c.relname LIKE 'pg\\\\_%' AND c.relname NOT LIKE 'pg\\\\_toast\\\\_%' AND c.relname NOT LIKE 'pg\\\\_temp\\\\_%'");
/* 2225 */     ht = new Hashtable();
/* 2226 */     tableTypeClauses.put("TEMPORARY TABLE", ht);
/* 2227 */     ht.put("SCHEMAS", "c.relkind = 'r' AND n.nspname LIKE 'pg\\\\_temp\\\\_%' ");
/* 2228 */     ht.put("NOSCHEMAS", "c.relkind = 'r' AND c.relname LIKE 'pg\\\\_temp\\\\_%' ");
/* 2229 */     ht = new Hashtable();
/* 2230 */     tableTypeClauses.put("TEMPORARY INDEX", ht);
/* 2231 */     ht.put("SCHEMAS", "c.relkind = 'i' AND n.nspname LIKE 'pg\\\\_temp\\\\_%' ");
/* 2232 */     ht.put("NOSCHEMAS", "c.relkind = 'i' AND c.relname LIKE 'pg\\\\_temp\\\\_%' ");
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
/*      */   public ResultSet getSchemas() throws SQLException {
/*      */     String str;
/* 2250 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */       
/* 2252 */       str = "SELECT nspname AS TABLE_SCHEM FROM pg_catalog.pg_namespace WHERE nspname <> 'pg_toast' AND nspname NOT LIKE 'pg\\\\_temp\\\\_%' ORDER BY TABLE_SCHEM";
/*      */     }
/*      */     else {
/*      */       
/* 2256 */       str = "SELECT ''::text AS TABLE_SCHEM ORDER BY TABLE_SCHEM";
/*      */     } 
/* 2258 */     return createMetaDataStatement().executeQuery(str);
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
/*      */   public ResultSet getCatalogs() throws SQLException {
/* 2279 */     Field[] f = new Field[1];
/* 2280 */     Vector v = new Vector();
/* 2281 */     f[0] = new Field(new String("TABLE_CAT"), 1043, getMaxNameLength());
/* 2282 */     byte[][] tuple = new byte[1][];
/* 2283 */     tuple[0] = this.connection.encodeString(this.connection.getCatalog());
/* 2284 */     v.addElement(tuple);
/*      */     
/* 2286 */     return ((BaseStatement)createMetaDataStatement()).createDriverResultSet(f, v);
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
/*      */   public ResultSet getTableTypes() throws SQLException {
/* 2305 */     String[] types = new String[tableTypeClauses.size()];
/* 2306 */     Enumeration e = tableTypeClauses.keys();
/* 2307 */     int i = 0;
/* 2308 */     while (e.hasMoreElements())
/*      */     {
/* 2310 */       types[i++] = e.nextElement();
/*      */     }
/* 2312 */     sortStringArray(types);
/*      */     
/* 2314 */     Field[] f = new Field[1];
/* 2315 */     Vector v = new Vector();
/* 2316 */     f[0] = new Field(new String("TABLE_TYPE"), 1043, getMaxNameLength());
/* 2317 */     for (i = 0; i < types.length; i++) {
/*      */       
/* 2319 */       byte[][] tuple = new byte[1][];
/* 2320 */       tuple[0] = this.connection.encodeString(types[i]);
/* 2321 */       v.addElement(tuple);
/*      */     } 
/*      */     
/* 2324 */     return ((BaseStatement)createMetaDataStatement()).createDriverResultSet(f, v);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
/* 2377 */     Vector v = new Vector();
/* 2378 */     Field[] f = new Field[18];
/*      */     
/* 2380 */     f[0] = new Field("TABLE_CAT", 1043, getMaxNameLength());
/* 2381 */     f[1] = new Field("TABLE_SCHEM", 1043, getMaxNameLength());
/* 2382 */     f[2] = new Field("TABLE_NAME", 1043, getMaxNameLength());
/* 2383 */     f[3] = new Field("COLUMN_NAME", 1043, getMaxNameLength());
/* 2384 */     f[4] = new Field("DATA_TYPE", 21, 2);
/* 2385 */     f[5] = new Field("TYPE_NAME", 1043, getMaxNameLength());
/* 2386 */     f[6] = new Field("COLUMN_SIZE", 23, 4);
/* 2387 */     f[7] = new Field("BUFFER_LENGTH", 1043, getMaxNameLength());
/* 2388 */     f[8] = new Field("DECIMAL_DIGITS", 23, 4);
/* 2389 */     f[9] = new Field("NUM_PREC_RADIX", 23, 4);
/* 2390 */     f[10] = new Field("NULLABLE", 23, 4);
/* 2391 */     f[11] = new Field("REMARKS", 1043, getMaxNameLength());
/* 2392 */     f[12] = new Field("COLUMN_DEF", 1043, getMaxNameLength());
/* 2393 */     f[13] = new Field("SQL_DATA_TYPE", 23, 4);
/* 2394 */     f[14] = new Field("SQL_DATETIME_SUB", 23, 4);
/* 2395 */     f[15] = new Field("CHAR_OCTET_LENGTH", 1043, getMaxNameLength());
/* 2396 */     f[16] = new Field("ORDINAL_POSITION", 23, 4);
/* 2397 */     f[17] = new Field("IS_NULLABLE", 1043, getMaxNameLength());
/*      */ 
/*      */     
/* 2400 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */       
/* 2402 */       str = "SELECT n.nspname,c.relname,a.attname,a.atttypid,a.attnotnull,a.atttypmod,a.attlen,a.attnum,def.adsrc,dsc.description  FROM pg_catalog.pg_namespace n  JOIN pg_catalog.pg_class c ON (c.relnamespace = n.oid)  JOIN pg_catalog.pg_attribute a ON (a.attrelid=c.oid)  LEFT JOIN pg_catalog.pg_attrdef def ON (a.attrelid=def.adrelid AND a.attnum = def.adnum)  LEFT JOIN pg_catalog.pg_description dsc ON (c.oid=dsc.objoid AND a.attnum = dsc.objsubid)  LEFT JOIN pg_catalog.pg_class dc ON (dc.oid=dsc.classoid AND dc.relname='pg_class')  LEFT JOIN pg_catalog.pg_namespace dn ON (dc.relnamespace=dn.oid AND dn.nspname='pg_catalog')  WHERE a.attnum > 0 AND NOT a.attisdropped ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2411 */       if (schemaPattern != null && !"".equals(schemaPattern))
/*      */       {
/* 2413 */         str = str + " AND n.nspname LIKE '" + escapeQuotes(schemaPattern) + "' ";
/*      */       }
/*      */     }
/* 2416 */     else if (this.connection.haveMinimumServerVersion("7.2")) {
/*      */       
/* 2418 */       str = "SELECT NULL::text AS nspname,c.relname,a.attname,a.atttypid,a.attnotnull,a.atttypmod,a.attlen,a.attnum,def.adsrc,dsc.description  FROM pg_class c  JOIN pg_attribute a ON (a.attrelid=c.oid)  LEFT JOIN pg_attrdef def ON (a.attrelid=def.adrelid AND a.attnum = def.adnum)  LEFT JOIN pg_description dsc ON (c.oid=dsc.objoid AND a.attnum = dsc.objsubid)  LEFT JOIN pg_class dc ON (dc.oid=dsc.classoid AND dc.relname='pg_class')  WHERE a.attnum > 0 ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 2426 */     else if (this.connection.haveMinimumServerVersion("7.1")) {
/*      */       
/* 2428 */       str = "SELECT NULL::text AS nspname,c.relname,a.attname,a.atttypid,a.attnotnull,a.atttypmod,a.attlen,a.attnum,def.adsrc,dsc.description  FROM pg_class c  JOIN pg_attribute a ON (a.attrelid=c.oid)  LEFT JOIN pg_attrdef def ON (a.attrelid=def.adrelid AND a.attnum = def.adnum)  LEFT JOIN pg_description dsc ON (a.oid=dsc.objoid)  WHERE a.attnum > 0 ";
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2438 */       str = "SELECT NULL::text AS nspname,c.relname,a.attname,a.atttypid,a.attnotnull,a.atttypmod,a.attlen,a.attnum,NULL AS adsrc,NULL AS description  FROM pg_class c, pg_attribute a  WHERE a.attrelid=c.oid AND a.attnum > 0 ";
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2443 */     if (tableNamePattern != null && !"".equals(tableNamePattern))
/*      */     {
/* 2445 */       str = str + " AND c.relname LIKE '" + escapeQuotes(tableNamePattern) + "' ";
/*      */     }
/* 2447 */     if (columnNamePattern != null && !"".equals(columnNamePattern))
/*      */     {
/* 2449 */       str = str + " AND a.attname LIKE '" + escapeQuotes(columnNamePattern) + "' ";
/*      */     }
/* 2451 */     String str = str + " ORDER BY nspname,relname,attnum ";
/*      */     
/* 2453 */     ResultSet rs = this.connection.createStatement().executeQuery(str);
/* 2454 */     while (rs.next()) {
/*      */       
/* 2456 */       byte[][] tuple = new byte[18][];
/* 2457 */       int typeOid = rs.getInt("atttypid");
/*      */       
/* 2459 */       tuple[0] = null;
/* 2460 */       tuple[1] = rs.getBytes("nspname");
/* 2461 */       tuple[2] = rs.getBytes("relname");
/* 2462 */       tuple[3] = rs.getBytes("attname");
/* 2463 */       tuple[4] = this.connection.encodeString(Integer.toString(this.connection.getSQLType(typeOid)));
/* 2464 */       String pgType = this.connection.getPGType(typeOid);
/* 2465 */       tuple[5] = this.connection.encodeString(pgType);
/*      */       
/* 2467 */       String defval = rs.getString("adsrc");
/*      */       
/* 2469 */       if (defval != null)
/*      */       {
/* 2471 */         if (pgType.equals("int4")) {
/*      */           
/* 2473 */           if (defval.indexOf("nextval(") != -1) {
/* 2474 */             tuple[5] = this.connection.encodeString("serial");
/*      */           }
/* 2476 */         } else if (pgType.equals("int8")) {
/*      */           
/* 2478 */           if (defval.indexOf("nextval(") != -1) {
/* 2479 */             tuple[5] = this.connection.encodeString("bigserial");
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2486 */       tuple[8] = this.connection.encodeString("0");
/*      */       
/* 2488 */       if (pgType.equals("bpchar") || pgType.equals("varchar")) {
/*      */         
/* 2490 */         int atttypmod = rs.getInt("atttypmod");
/* 2491 */         tuple[6] = this.connection.encodeString(Integer.toString((atttypmod != -1) ? (atttypmod - 4) : 0));
/*      */       }
/* 2493 */       else if (pgType.equals("numeric") || pgType.equals("decimal")) {
/*      */         
/* 2495 */         int attypmod = rs.getInt("atttypmod") - 4;
/* 2496 */         tuple[6] = this.connection.encodeString(Integer.toString(attypmod >> 16 & 0xFFFF));
/* 2497 */         tuple[8] = this.connection.encodeString(Integer.toString(attypmod & 0xFFFF));
/* 2498 */         tuple[9] = this.connection.encodeString("10");
/*      */       }
/* 2500 */       else if (pgType.equals("bit") || pgType.equals("varbit")) {
/*      */         
/* 2502 */         tuple[6] = rs.getBytes("atttypmod");
/* 2503 */         tuple[9] = this.connection.encodeString("2");
/*      */       }
/* 2505 */       else if (pgType.equals("time") || pgType.equals("timetz") || pgType.equals("timestamp") || pgType.equals("timestamptz")) {
/* 2506 */         int scale = rs.getInt("atttypmod");
/* 2507 */         if (scale == -1)
/* 2508 */           scale = 6; 
/* 2509 */         tuple[8] = this.connection.encodeString(Integer.toString(scale));
/* 2510 */         tuple[6] = rs.getBytes("attlen");
/*      */       }
/* 2512 */       else if (pgType.equals("interval")) {
/* 2513 */         int scale = rs.getInt("atttypmod");
/* 2514 */         if (scale == -1) {
/* 2515 */           scale = 6;
/*      */         } else {
/* 2517 */           scale &= 0xFFFF;
/* 2518 */         }  tuple[8] = this.connection.encodeString(Integer.toString(scale));
/* 2519 */         tuple[6] = rs.getBytes("attlen");
/*      */       }
/*      */       else {
/*      */         
/* 2523 */         tuple[6] = rs.getBytes("attlen");
/* 2524 */         tuple[9] = this.connection.encodeString("10");
/*      */       } 
/*      */       
/* 2527 */       tuple[7] = null;
/*      */       
/* 2529 */       tuple[10] = this.connection.encodeString(Integer.toString(rs.getBoolean("attnotnull") ? 0 : 1));
/* 2530 */       tuple[11] = rs.getBytes("description");
/* 2531 */       tuple[12] = rs.getBytes("adsrc");
/* 2532 */       tuple[13] = null;
/* 2533 */       tuple[14] = null;
/* 2534 */       tuple[15] = tuple[6];
/* 2535 */       tuple[16] = rs.getBytes("attnum");
/* 2536 */       tuple[17] = this.connection.encodeString(rs.getBoolean("attnotnull") ? "NO" : "YES");
/*      */       
/* 2538 */       v.addElement(tuple);
/*      */     } 
/* 2540 */     rs.close();
/*      */     
/* 2542 */     return ((BaseStatement)createMetaDataStatement()).createDriverResultSet(f, v);
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
/*      */   public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
/* 2574 */     Field[] f = new Field[8];
/* 2575 */     Vector v = new Vector();
/*      */     
/* 2577 */     if (table == null) {
/* 2578 */       table = "%";
/*      */     }
/* 2580 */     if (columnNamePattern == null) {
/* 2581 */       columnNamePattern = "%";
/*      */     }
/* 2583 */     f[0] = new Field("TABLE_CAT", 1043, getMaxNameLength());
/* 2584 */     f[1] = new Field("TABLE_SCHEM", 1043, getMaxNameLength());
/* 2585 */     f[2] = new Field("TABLE_NAME", 1043, getMaxNameLength());
/* 2586 */     f[3] = new Field("COLUMN_NAME", 1043, getMaxNameLength());
/* 2587 */     f[4] = new Field("GRANTOR", 1043, getMaxNameLength());
/* 2588 */     f[5] = new Field("GRANTEE", 1043, getMaxNameLength());
/* 2589 */     f[6] = new Field("PRIVILEGE", 1043, getMaxNameLength());
/* 2590 */     f[7] = new Field("IS_GRANTABLE", 1043, getMaxNameLength());
/*      */ 
/*      */     
/* 2593 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */       
/* 2595 */       str = "SELECT n.nspname,c.relname,u.usename,c.relacl,a.attname  FROM pg_catalog.pg_namespace n, pg_catalog.pg_class c, pg_catalog.pg_user u, pg_catalog.pg_attribute a  WHERE c.relnamespace = n.oid  AND u.usesysid = c.relowner  AND c.oid = a.attrelid  AND c.relkind = 'r'  AND a.attnum > 0 AND NOT a.attisdropped ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2602 */       if (schema != null && !"".equals(schema))
/*      */       {
/* 2604 */         str = str + " AND n.nspname = '" + escapeQuotes(schema) + "' ";
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 2609 */       str = "SELECT NULL::text AS nspname,c.relname,u.usename,c.relacl,a.attname FROM pg_class c, pg_user u,pg_attribute a  WHERE u.usesysid = c.relowner  AND c.oid = a.attrelid  AND a.attnum > 0  AND c.relkind = 'r' ";
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2617 */     String str = str + " AND c.relname = '" + escapeQuotes(table) + "' ";
/* 2618 */     if (columnNamePattern != null && !"".equals(columnNamePattern))
/*      */     {
/* 2620 */       str = str + " AND a.attname LIKE '" + escapeQuotes(columnNamePattern) + "' ";
/*      */     }
/* 2622 */     str = str + " ORDER BY attname ";
/*      */     
/* 2624 */     ResultSet rs = this.connection.createStatement().executeQuery(str);
/* 2625 */     while (rs.next()) {
/*      */       
/* 2627 */       byte[] schemaName = rs.getBytes("nspname");
/* 2628 */       byte[] tableName = rs.getBytes("relname");
/* 2629 */       byte[] column = rs.getBytes("attname");
/* 2630 */       String owner = rs.getString("usename");
/* 2631 */       String acl = rs.getString("relacl");
/* 2632 */       Hashtable permissions = parseACL(acl, owner);
/* 2633 */       String[] permNames = new String[permissions.size()];
/* 2634 */       Enumeration e = permissions.keys();
/* 2635 */       int i = 0;
/* 2636 */       while (e.hasMoreElements())
/*      */       {
/* 2638 */         permNames[i++] = e.nextElement();
/*      */       }
/* 2640 */       sortStringArray(permNames);
/* 2641 */       for (i = 0; i < permNames.length; i++) {
/*      */         
/* 2643 */         byte[] privilege = this.connection.encodeString(permNames[i]);
/* 2644 */         Vector grantees = (Vector)permissions.get(permNames[i]);
/* 2645 */         for (int j = 0; j < grantees.size(); j++) {
/*      */           
/* 2647 */           String grantee = grantees.elementAt(j);
/* 2648 */           String grantable = owner.equals(grantee) ? "YES" : "NO";
/* 2649 */           byte[][] tuple = new byte[8][];
/* 2650 */           tuple[0] = null;
/* 2651 */           tuple[1] = schemaName;
/* 2652 */           tuple[2] = tableName;
/* 2653 */           tuple[3] = column;
/* 2654 */           tuple[4] = this.connection.encodeString(owner);
/* 2655 */           tuple[5] = this.connection.encodeString(grantee);
/* 2656 */           tuple[6] = privilege;
/* 2657 */           tuple[7] = this.connection.encodeString(grantable);
/* 2658 */           v.addElement(tuple);
/*      */         } 
/*      */       } 
/*      */     } 
/* 2662 */     rs.close();
/*      */     
/* 2664 */     return ((BaseStatement)createMetaDataStatement()).createDriverResultSet(f, v);
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
/*      */   public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
/* 2699 */     Field[] f = new Field[7];
/* 2700 */     Vector v = new Vector();
/*      */     
/* 2702 */     f[0] = new Field("TABLE_CAT", 1043, getMaxNameLength());
/* 2703 */     f[1] = new Field("TABLE_SCHEM", 1043, getMaxNameLength());
/* 2704 */     f[2] = new Field("TABLE_NAME", 1043, getMaxNameLength());
/* 2705 */     f[3] = new Field("GRANTOR", 1043, getMaxNameLength());
/* 2706 */     f[4] = new Field("GRANTEE", 1043, getMaxNameLength());
/* 2707 */     f[5] = new Field("PRIVILEGE", 1043, getMaxNameLength());
/* 2708 */     f[6] = new Field("IS_GRANTABLE", 1043, getMaxNameLength());
/*      */ 
/*      */     
/* 2711 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */       
/* 2713 */       str = "SELECT n.nspname,c.relname,u.usename,c.relacl  FROM pg_catalog.pg_namespace n, pg_catalog.pg_class c, pg_catalog.pg_user u  WHERE c.relnamespace = n.oid  AND u.usesysid = c.relowner  AND c.relkind = 'r' ";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2718 */       if (schemaPattern != null && !"".equals(schemaPattern))
/*      */       {
/* 2720 */         str = str + " AND n.nspname LIKE '" + escapeQuotes(schemaPattern) + "' ";
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 2725 */       str = "SELECT NULL::text AS nspname,c.relname,u.usename,c.relacl FROM pg_class c, pg_user u  WHERE u.usesysid = c.relowner  AND c.relkind = 'r' ";
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2731 */     if (tableNamePattern != null && !"".equals(tableNamePattern))
/*      */     {
/* 2733 */       str = str + " AND c.relname LIKE '" + escapeQuotes(tableNamePattern) + "' ";
/*      */     }
/* 2735 */     String str = str + " ORDER BY nspname, relname ";
/*      */     
/* 2737 */     ResultSet rs = this.connection.createStatement().executeQuery(str);
/* 2738 */     while (rs.next()) {
/*      */       
/* 2740 */       byte[] schema = rs.getBytes("nspname");
/* 2741 */       byte[] table = rs.getBytes("relname");
/* 2742 */       String owner = rs.getString("usename");
/* 2743 */       String acl = rs.getString("relacl");
/* 2744 */       Hashtable permissions = parseACL(acl, owner);
/* 2745 */       String[] permNames = new String[permissions.size()];
/* 2746 */       Enumeration e = permissions.keys();
/* 2747 */       int i = 0;
/* 2748 */       while (e.hasMoreElements())
/*      */       {
/* 2750 */         permNames[i++] = e.nextElement();
/*      */       }
/* 2752 */       sortStringArray(permNames);
/* 2753 */       for (i = 0; i < permNames.length; i++) {
/*      */         
/* 2755 */         byte[] privilege = this.connection.encodeString(permNames[i]);
/* 2756 */         Vector grantees = (Vector)permissions.get(permNames[i]);
/* 2757 */         for (int j = 0; j < grantees.size(); j++) {
/*      */           
/* 2759 */           String grantee = grantees.elementAt(j);
/* 2760 */           String grantable = owner.equals(grantee) ? "YES" : "NO";
/* 2761 */           byte[][] tuple = new byte[7][];
/* 2762 */           tuple[0] = null;
/* 2763 */           tuple[1] = schema;
/* 2764 */           tuple[2] = table;
/* 2765 */           tuple[3] = this.connection.encodeString(owner);
/* 2766 */           tuple[4] = this.connection.encodeString(grantee);
/* 2767 */           tuple[5] = privilege;
/* 2768 */           tuple[6] = this.connection.encodeString(grantable);
/* 2769 */           v.addElement(tuple);
/*      */         } 
/*      */       } 
/*      */     } 
/* 2773 */     rs.close();
/*      */     
/* 2775 */     return ((BaseStatement)createMetaDataStatement()).createDriverResultSet(f, v);
/*      */   }
/*      */   
/*      */   private static void sortStringArray(String[] s) {
/* 2779 */     for (int i = 0; i < s.length - 1; i++) {
/*      */       
/* 2781 */       for (int j = i + 1; j < s.length; j++) {
/*      */         
/* 2783 */         if (s[i].compareTo(s[j]) > 0) {
/*      */           
/* 2785 */           String tmp = s[i];
/* 2786 */           s[i] = s[j];
/* 2787 */           s[j] = tmp;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Vector parseACLArray(String aclString) {
/* 2797 */     Vector acls = new Vector();
/* 2798 */     if (aclString == null || aclString.length() == 0)
/*      */     {
/* 2800 */       return acls;
/*      */     }
/* 2802 */     boolean inQuotes = false;
/*      */     
/* 2804 */     int beginIndex = 1;
/* 2805 */     char prevChar = ' ';
/* 2806 */     for (int i = beginIndex; i < aclString.length(); i++) {
/*      */ 
/*      */       
/* 2809 */       char c = aclString.charAt(i);
/* 2810 */       if (c == '"' && prevChar != '\\') {
/*      */         
/* 2812 */         inQuotes = !inQuotes;
/*      */       }
/* 2814 */       else if (c == ',' && !inQuotes) {
/*      */         
/* 2816 */         acls.addElement(aclString.substring(beginIndex, i));
/* 2817 */         beginIndex = i + 1;
/*      */       } 
/* 2819 */       prevChar = c;
/*      */     } 
/*      */     
/* 2822 */     acls.addElement(aclString.substring(beginIndex, aclString.length() - 1));
/*      */ 
/*      */     
/* 2825 */     for (int j = 0; j < acls.size(); j++) {
/*      */       
/* 2827 */       String acl = acls.elementAt(j);
/* 2828 */       if (acl.startsWith("\"") && acl.endsWith("\"")) {
/*      */         
/* 2830 */         acl = acl.substring(1, acl.length() - 1);
/* 2831 */         acls.setElementAt(acl, j);
/*      */       } 
/*      */     } 
/* 2834 */     return acls;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addACLPrivileges(String acl, Hashtable privileges) {
/* 2842 */     int equalIndex = acl.lastIndexOf("=");
/* 2843 */     String name = acl.substring(0, equalIndex);
/* 2844 */     if (name.length() == 0)
/*      */     {
/* 2846 */       name = "PUBLIC";
/*      */     }
/* 2848 */     String privs = acl.substring(equalIndex + 1);
/* 2849 */     for (int i = 0; i < privs.length(); i++) {
/*      */       String sqlpriv;
/* 2851 */       char c = privs.charAt(i);
/*      */       
/* 2853 */       switch (c) {
/*      */         
/*      */         case 'a':
/* 2856 */           sqlpriv = "INSERT";
/*      */           break;
/*      */         case 'r':
/* 2859 */           sqlpriv = "SELECT";
/*      */           break;
/*      */         case 'w':
/* 2862 */           sqlpriv = "UPDATE";
/*      */           break;
/*      */         case 'd':
/* 2865 */           sqlpriv = "DELETE";
/*      */           break;
/*      */         case 'R':
/* 2868 */           sqlpriv = "RULE";
/*      */           break;
/*      */         case 'x':
/* 2871 */           sqlpriv = "REFERENCES";
/*      */           break;
/*      */         case 't':
/* 2874 */           sqlpriv = "TRIGGER";
/*      */           break;
/*      */ 
/*      */         
/*      */         case 'X':
/* 2879 */           sqlpriv = "EXECUTE";
/*      */           break;
/*      */         case 'U':
/* 2882 */           sqlpriv = "USAGE";
/*      */           break;
/*      */         case 'C':
/* 2885 */           sqlpriv = "CREATE";
/*      */           break;
/*      */         case 'T':
/* 2888 */           sqlpriv = "CREATE TEMP";
/*      */           break;
/*      */         default:
/* 2891 */           sqlpriv = "UNKNOWN"; break;
/*      */       } 
/* 2893 */       Vector usersWithPermission = (Vector)privileges.get(sqlpriv);
/* 2894 */       if (usersWithPermission == null) {
/*      */         
/* 2896 */         usersWithPermission = new Vector();
/* 2897 */         privileges.put(sqlpriv, usersWithPermission);
/*      */       } 
/* 2899 */       usersWithPermission.addElement(name);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Hashtable parseACL(String aclArray, String owner) {
/* 2909 */     if (aclArray == null || aclArray == "")
/*      */     {
/*      */       
/* 2912 */       aclArray = "{" + owner + "=arwdRxt}";
/*      */     }
/* 2914 */     Vector acls = parseACLArray(aclArray);
/* 2915 */     Hashtable privileges = new Hashtable();
/* 2916 */     for (int i = 0; i < acls.size(); i++) {
/*      */       
/* 2918 */       String acl = acls.elementAt(i);
/* 2919 */       addACLPrivileges(acl, privileges);
/*      */     } 
/* 2921 */     return privileges;
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
/*      */   
/*      */   public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
/*      */     String str1;
/* 2961 */     Field[] f = new Field[8];
/* 2962 */     Vector v = new Vector();
/*      */     
/* 2964 */     f[0] = new Field("SCOPE", 21, 2);
/* 2965 */     f[1] = new Field("COLUMN_NAME", 1043, getMaxNameLength());
/* 2966 */     f[2] = new Field("DATA_TYPE", 21, 2);
/* 2967 */     f[3] = new Field("TYPE_NAME", 1043, getMaxNameLength());
/* 2968 */     f[4] = new Field("COLUMN_SIZE", 23, 4);
/* 2969 */     f[5] = new Field("BUFFER_LENGTH", 23, 4);
/* 2970 */     f[6] = new Field("DECIMAL_DIGITS", 21, 2);
/* 2971 */     f[7] = new Field("PSEUDO_COLUMN", 21, 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2979 */     String where = "";
/* 2980 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */       
/* 2982 */       str1 = " FROM pg_catalog.pg_namespace n, pg_catalog.pg_class ct, pg_catalog.pg_class ci, pg_catalog.pg_attribute a, pg_catalog.pg_index i ";
/* 2983 */       where = " AND ct.relnamespace = n.oid ";
/* 2984 */       if (schema != null && !"".equals(schema))
/*      */       {
/* 2986 */         where = where + " AND n.nspname = '" + escapeQuotes(schema) + "' ";
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 2991 */       str1 = " FROM pg_class ct, pg_class ci, pg_attribute a, pg_index i ";
/*      */     } 
/* 2993 */     String sql = "SELECT a.attname, a.atttypid " + str1 + " WHERE ct.oid=i.indrelid AND ci.oid=i.indexrelid " + " AND a.attrelid=ci.oid AND i.indisprimary " + " AND ct.relname = '" + escapeQuotes(table) + "' " + where + " ORDER BY a.attnum ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3001 */     ResultSet rs = this.connection.createStatement().executeQuery(sql);
/* 3002 */     while (rs.next()) {
/*      */       
/* 3004 */       byte[][] tuple = new byte[8][];
/* 3005 */       int columnTypeOid = rs.getInt("atttypid");
/* 3006 */       tuple[0] = this.connection.encodeString(Integer.toString(scope));
/* 3007 */       tuple[1] = rs.getBytes("attname");
/* 3008 */       tuple[2] = this.connection.encodeString(Integer.toString(this.connection.getSQLType(columnTypeOid)));
/* 3009 */       tuple[3] = this.connection.encodeString(this.connection.getPGType(columnTypeOid));
/* 3010 */       tuple[4] = null;
/* 3011 */       tuple[5] = null;
/* 3012 */       tuple[6] = null;
/* 3013 */       tuple[7] = this.connection.encodeString(Integer.toString(1));
/* 3014 */       v.addElement(tuple);
/*      */     } 
/*      */     
/* 3017 */     return ((BaseStatement)createMetaDataStatement()).createDriverResultSet(f, v);
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
/*      */   public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
/* 3050 */     Field[] f = new Field[8];
/* 3051 */     Vector v = new Vector();
/*      */     
/* 3053 */     f[0] = new Field("SCOPE", 21, 2);
/* 3054 */     f[1] = new Field("COLUMN_NAME", 1043, getMaxNameLength());
/* 3055 */     f[2] = new Field("DATA_TYPE", 21, 2);
/* 3056 */     f[3] = new Field("TYPE_NAME", 1043, getMaxNameLength());
/* 3057 */     f[4] = new Field("COLUMN_SIZE", 23, 4);
/* 3058 */     f[5] = new Field("BUFFER_LENGTH", 23, 4);
/* 3059 */     f[6] = new Field("DECIMAL_DIGITS", 21, 2);
/* 3060 */     f[7] = new Field("PSEUDO_COLUMN", 21, 2);
/*      */     
/* 3062 */     byte[][] tuple = new byte[8][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3074 */     tuple[0] = null;
/* 3075 */     tuple[1] = this.connection.encodeString("ctid");
/* 3076 */     tuple[2] = this.connection.encodeString(Integer.toString(this.connection.getSQLType("tid")));
/* 3077 */     tuple[3] = this.connection.encodeString("tid");
/* 3078 */     tuple[4] = null;
/* 3079 */     tuple[5] = null;
/* 3080 */     tuple[6] = null;
/* 3081 */     tuple[7] = this.connection.encodeString(Integer.toString(2));
/* 3082 */     v.addElement(tuple);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3087 */     return ((BaseStatement)createMetaDataStatement()).createDriverResultSet(f, v);
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
/*      */   public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
/* 3114 */     String str1, str2, where = "";
/* 3115 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */       
/* 3117 */       str1 = "SELECT NULL AS TABLE_CAT, n.nspname AS TABLE_SCHEM, ";
/* 3118 */       str2 = " FROM pg_catalog.pg_namespace n, pg_catalog.pg_class ct, pg_catalog.pg_class ci, pg_catalog.pg_attribute a, pg_catalog.pg_index i ";
/* 3119 */       where = " AND ct.relnamespace = n.oid ";
/* 3120 */       if (schema != null && !"".equals(schema))
/*      */       {
/* 3122 */         where = where + " AND n.nspname = '" + escapeQuotes(schema) + "' ";
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 3127 */       str1 = "SELECT NULL AS TABLE_CAT, NULL AS TABLE_SCHEM, ";
/* 3128 */       str2 = " FROM pg_class ct, pg_class ci, pg_attribute a, pg_index i ";
/*      */     } 
/* 3130 */     String sql = str1 + " ct.relname AS TABLE_NAME, " + " a.attname AS COLUMN_NAME, " + " a.attnum AS KEY_SEQ, " + " ci.relname AS PK_NAME " + str2 + " WHERE ct.oid=i.indrelid AND ci.oid=i.indexrelid " + " AND a.attrelid=ci.oid AND i.indisprimary ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3138 */     if (table != null && !"".equals(table))
/*      */     {
/* 3140 */       sql = sql + " AND ct.relname = '" + escapeQuotes(table) + "' ";
/*      */     }
/* 3142 */     sql = sql + where + " ORDER BY table_name, pk_name, key_seq";
/*      */     
/* 3144 */     return createMetaDataStatement().executeQuery(sql);
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
/*      */   protected ResultSet getImportedExportedKeys(String primaryCatalog, String primarySchema, String primaryTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
/*      */     String str1, str2;
/* 3159 */     Field[] f = new Field[14];
/*      */     
/* 3161 */     f[0] = new Field("PKTABLE_CAT", 1043, getMaxNameLength());
/* 3162 */     f[1] = new Field("PKTABLE_SCHEM", 1043, getMaxNameLength());
/* 3163 */     f[2] = new Field("PKTABLE_NAME", 1043, getMaxNameLength());
/* 3164 */     f[3] = new Field("PKCOLUMN_NAME", 1043, getMaxNameLength());
/* 3165 */     f[4] = new Field("FKTABLE_CAT", 1043, getMaxNameLength());
/* 3166 */     f[5] = new Field("FKTABLE_SCHEM", 1043, getMaxNameLength());
/* 3167 */     f[6] = new Field("FKTABLE_NAME", 1043, getMaxNameLength());
/* 3168 */     f[7] = new Field("FKCOLUMN_NAME", 1043, getMaxNameLength());
/* 3169 */     f[8] = new Field("KEY_SEQ", 21, 2);
/* 3170 */     f[9] = new Field("UPDATE_RULE", 21, 2);
/* 3171 */     f[10] = new Field("DELETE_RULE", 21, 2);
/* 3172 */     f[11] = new Field("FK_NAME", 1043, getMaxNameLength());
/* 3173 */     f[12] = new Field("PK_NAME", 1043, getMaxNameLength());
/* 3174 */     f[13] = new Field("DEFERRABILITY", 21, 2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3179 */     String where = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3192 */     if (this.connection.haveMinimumServerVersion("7.4")) {
/*      */       
/* 3194 */       String str = "SELECT NULL::text AS PKTABLE_CAT, pkn.nspname AS PKTABLE_SCHEM, pkc.relname AS PKTABLE_NAME, pka.attname AS PKCOLUMN_NAME, NULL::text AS FKTABLE_CAT, fkn.nspname AS FKTABLE_SCHEM, fkc.relname AS FKTABLE_NAME, fka.attname AS FKCOLUMN_NAME, pos.n AS KEY_SEQ, CASE con.confupdtype  WHEN 'c' THEN 0 WHEN 'n' THEN 2 WHEN 'd' THEN 4 WHEN 'r' THEN 1 WHEN 'a' THEN 3 ELSE NULL END AS UPDATE_RULE, CASE con.confdeltype  WHEN 'c' THEN 0 WHEN 'n' THEN 2 WHEN 'd' THEN 4 WHEN 'r' THEN 1 WHEN 'a' THEN 3 ELSE NULL END AS DELETE_RULE, con.conname AS FK_NAME, pkic.relname AS PK_NAME, CASE  WHEN con.condeferrable AND con.condeferred THEN 5 WHEN con.condeferrable THEN 6 ELSE 7 END AS DEFERRABILITY  FROM  pg_catalog.pg_namespace pkn, pg_catalog.pg_class pkc, pg_catalog.pg_attribute pka,  pg_catalog.pg_namespace fkn, pg_catalog.pg_class fkc, pg_catalog.pg_attribute fka,  pg_catalog.pg_constraint con, ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3221 */       if (this.connection.haveMinimumServerVersion("8.0")) {
/* 3222 */         str = str + " pg_catalog.generate_series(1, " + getMaxIndexKeys() + ") pos(n), ";
/*      */       } else {
/* 3224 */         str = str + " information_schema._pg_keypositions() pos(n), ";
/*      */       } 
/* 3226 */       str = str + " pg_catalog.pg_depend dep, pg_catalog.pg_class pkic  WHERE pkn.oid = pkc.relnamespace AND pkc.oid = pka.attrelid AND pka.attnum = con.confkey[pos.n] AND con.confrelid = pkc.oid  AND fkn.oid = fkc.relnamespace AND fkc.oid = fka.attrelid AND fka.attnum = con.conkey[pos.n] AND con.conrelid = fkc.oid  AND con.contype = 'f' AND con.oid = dep.objid AND pkic.oid = dep.refobjid AND pkic.relkind = 'i' AND dep.classid = 'pg_constraint'::regclass::oid AND dep.refclassid = 'pg_class'::regclass::oid ";
/*      */ 
/*      */ 
/*      */       
/* 3230 */       if (primarySchema != null && !"".equals(primarySchema))
/*      */       {
/* 3232 */         str = str + " AND pkn.nspname = '" + escapeQuotes(primarySchema) + "' ";
/*      */       }
/* 3234 */       if (foreignSchema != null && !"".equals(foreignSchema))
/*      */       {
/* 3236 */         str = str + " AND fkn.nspname = '" + escapeQuotes(foreignSchema) + "' ";
/*      */       }
/* 3238 */       if (primaryTable != null && !"".equals(primaryTable))
/*      */       {
/* 3240 */         str = str + " AND pkc.relname = '" + escapeQuotes(primaryTable) + "' ";
/*      */       }
/* 3242 */       if (foreignTable != null && !"".equals(foreignTable))
/*      */       {
/* 3244 */         str = str + " AND fkc.relname = '" + escapeQuotes(foreignTable) + "' ";
/*      */       }
/*      */       
/* 3247 */       if (primaryTable != null) {
/*      */         
/* 3249 */         str = str + " ORDER BY fkn.nspname,fkc.relname,pos.n";
/*      */       }
/*      */       else {
/*      */         
/* 3253 */         str = str + " ORDER BY pkn.nspname,pkc.relname,pos.n";
/*      */       } 
/*      */       
/* 3256 */       return createMetaDataStatement().executeQuery(str);
/*      */     } 
/* 3258 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */       
/* 3260 */       str1 = "SELECT DISTINCT n1.nspname as pnspname,n2.nspname as fnspname, ";
/* 3261 */       str2 = " FROM pg_catalog.pg_namespace n1  JOIN pg_catalog.pg_class c1 ON (c1.relnamespace = n1.oid)  JOIN pg_catalog.pg_index i ON (c1.oid=i.indrelid)  JOIN pg_catalog.pg_class ic ON (i.indexrelid=ic.oid)  JOIN pg_catalog.pg_attribute a ON (ic.oid=a.attrelid),  pg_catalog.pg_namespace n2  JOIN pg_catalog.pg_class c2 ON (c2.relnamespace=n2.oid),  pg_catalog.pg_trigger t1  JOIN pg_catalog.pg_proc p1 ON (t1.tgfoid=p1.oid),  pg_catalog.pg_trigger t2  JOIN pg_catalog.pg_proc p2 ON (t2.tgfoid=p2.oid) ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3272 */       if (primarySchema != null && !"".equals(primarySchema))
/*      */       {
/* 3274 */         where = where + " AND n1.nspname = '" + escapeQuotes(primarySchema) + "' ";
/*      */       }
/* 3276 */       if (foreignSchema != null && !"".equals(foreignSchema))
/*      */       {
/* 3278 */         where = where + " AND n2.nspname = '" + escapeQuotes(foreignSchema) + "' ";
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 3283 */       str1 = "SELECT DISTINCT NULL::text as pnspname, NULL::text as fnspname, ";
/* 3284 */       str2 = " FROM pg_class c1  JOIN pg_index i ON (c1.oid=i.indrelid)  JOIN pg_class ic ON (i.indexrelid=ic.oid)  JOIN pg_attribute a ON (ic.oid=a.attrelid),  pg_class c2,  pg_trigger t1  JOIN pg_proc p1 ON (t1.tgfoid=p1.oid),  pg_trigger t2  JOIN pg_proc p2 ON (t2.tgfoid=p2.oid) ";
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
/* 3295 */     String sql = str1 + "c1.relname as prelname, " + "c2.relname as frelname, " + "t1.tgconstrname, " + "a.attnum as keyseq, " + "ic.relname as fkeyname, " + "t1.tgdeferrable, " + "t1.tginitdeferred, " + "t1.tgnargs,t1.tgargs, " + "p1.proname as updaterule, " + "p2.proname as deleterule " + str2 + "WHERE " + "(t1.tgrelid=c1.oid " + "AND t1.tgisconstraint " + "AND t1.tgconstrrelid=c2.oid " + "AND p1.proname LIKE 'RI\\\\_FKey\\\\_%\\\\_upd') " + "AND " + "(t2.tgrelid=c1.oid " + "AND t2.tgisconstraint " + "AND t2.tgconstrrelid=c2.oid " + "AND p2.proname LIKE 'RI\\\\_FKey\\\\_%\\\\_del') " + "AND i.indisprimary " + where;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3324 */     if (primaryTable != null)
/*      */     {
/* 3326 */       sql = sql + "AND c1.relname='" + escapeQuotes(primaryTable) + "' ";
/*      */     }
/* 3328 */     if (foreignTable != null)
/*      */     {
/* 3330 */       sql = sql + "AND c2.relname='" + escapeQuotes(foreignTable) + "' ";
/*      */     }
/*      */     
/* 3333 */     sql = sql + "ORDER BY ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3340 */     if (primaryTable != null) {
/*      */       
/* 3342 */       if (this.connection.haveMinimumServerVersion("7.3"))
/*      */       {
/* 3344 */         sql = sql + "fnspname,";
/*      */       }
/* 3346 */       sql = sql + "frelname";
/*      */     }
/*      */     else {
/*      */       
/* 3350 */       if (this.connection.haveMinimumServerVersion("7.3"))
/*      */       {
/* 3352 */         sql = sql + "pnspname,";
/*      */       }
/* 3354 */       sql = sql + "prelname";
/*      */     } 
/*      */     
/* 3357 */     sql = sql + ",keyseq";
/*      */     
/* 3359 */     ResultSet rs = this.connection.createStatement().executeQuery(sql);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3377 */     Vector tuples = new Vector();
/*      */     
/* 3379 */     while (rs.next()) {
/*      */       
/* 3381 */       byte[][] tuple = new byte[14][];
/*      */       
/* 3383 */       tuple[1] = rs.getBytes(1);
/* 3384 */       tuple[5] = rs.getBytes(2);
/* 3385 */       tuple[2] = rs.getBytes(3);
/* 3386 */       tuple[6] = rs.getBytes(4);
/* 3387 */       String fKeyName = rs.getString(5);
/* 3388 */       String updateRule = rs.getString(12);
/*      */       
/* 3390 */       if (updateRule != null) {
/*      */ 
/*      */ 
/*      */         
/* 3394 */         String rule = updateRule.substring(8, updateRule.length() - 4);
/*      */         
/* 3396 */         int action = 3;
/*      */         
/* 3398 */         if (rule == null || "noaction".equals(rule))
/* 3399 */           action = 3; 
/* 3400 */         if ("cascade".equals(rule)) {
/* 3401 */           action = 0;
/* 3402 */         } else if ("setnull".equals(rule)) {
/* 3403 */           action = 2;
/* 3404 */         } else if ("setdefault".equals(rule)) {
/* 3405 */           action = 4;
/* 3406 */         } else if ("restrict".equals(rule)) {
/* 3407 */           action = 1;
/*      */         } 
/* 3409 */         tuple[9] = this.connection.encodeString(Integer.toString(action));
/*      */       } 
/*      */ 
/*      */       
/* 3413 */       String deleteRule = rs.getString(13);
/*      */       
/* 3415 */       if (deleteRule != null) {
/*      */ 
/*      */         
/* 3418 */         String rule = deleteRule.substring(8, deleteRule.length() - 4);
/*      */         
/* 3420 */         int action = 3;
/* 3421 */         if ("cascade".equals(rule)) {
/* 3422 */           action = 0;
/* 3423 */         } else if ("setnull".equals(rule)) {
/* 3424 */           action = 2;
/* 3425 */         } else if ("setdefault".equals(rule)) {
/* 3426 */           action = 4;
/* 3427 */         } else if ("restrict".equals(rule)) {
/* 3428 */           action = 1;
/* 3429 */         }  tuple[10] = this.connection.encodeString(Integer.toString(action));
/*      */       } 
/*      */ 
/*      */       
/* 3433 */       int keySequence = rs.getInt(6);
/*      */ 
/*      */       
/* 3436 */       String fkeyColumn = "";
/* 3437 */       String pkeyColumn = "";
/* 3438 */       String fkName = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3444 */       String targs = rs.getString(11);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3450 */       Vector tokens = tokenize(targs, "\\000");
/* 3451 */       if (tokens.size() > 0)
/*      */       {
/* 3453 */         fkName = tokens.elementAt(0);
/*      */       }
/*      */       
/* 3456 */       if (fkName.startsWith("<unnamed>"))
/*      */       {
/* 3458 */         fkName = targs;
/*      */       }
/*      */       
/* 3461 */       int element = 4 + (keySequence - 1) * 2;
/* 3462 */       if (tokens.size() > element)
/*      */       {
/* 3464 */         fkeyColumn = tokens.elementAt(element);
/*      */       }
/*      */       
/* 3467 */       element++;
/* 3468 */       if (tokens.size() > element)
/*      */       {
/* 3470 */         pkeyColumn = tokens.elementAt(element);
/*      */       }
/*      */       
/* 3473 */       tuple[3] = this.connection.encodeString(pkeyColumn);
/* 3474 */       tuple[7] = this.connection.encodeString(fkeyColumn);
/*      */       
/* 3476 */       tuple[8] = rs.getBytes(6);
/* 3477 */       tuple[11] = this.connection.encodeString(fkName);
/* 3478 */       tuple[12] = rs.getBytes(7);
/*      */ 
/*      */       
/* 3481 */       int deferrability = 7;
/* 3482 */       boolean deferrable = rs.getBoolean(8);
/* 3483 */       boolean initiallyDeferred = rs.getBoolean(9);
/* 3484 */       if (deferrable)
/*      */       {
/* 3486 */         if (initiallyDeferred) {
/* 3487 */           deferrability = 5;
/*      */         } else {
/* 3489 */           deferrability = 6;
/*      */         }  } 
/* 3491 */       tuple[13] = this.connection.encodeString(Integer.toString(deferrability));
/*      */       
/* 3493 */       tuples.addElement(tuple);
/*      */     } 
/*      */     
/* 3496 */     return ((BaseStatement)createMetaDataStatement()).createDriverResultSet(f, tuples);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
/* 3552 */     return getImportedExportedKeys(null, null, null, catalog, schema, table);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
/* 3610 */     return getImportedExportedKeys(catalog, schema, table, null, null, null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getCrossReference(String primaryCatalog, String primarySchema, String primaryTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
/* 3671 */     return getImportedExportedKeys(primaryCatalog, primarySchema, primaryTable, foreignCatalog, foreignSchema, foreignTable);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getTypeInfo() throws SQLException {
/*      */     String str;
/* 3722 */     Field[] f = new Field[18];
/* 3723 */     Vector v = new Vector();
/*      */     
/* 3725 */     f[0] = new Field("TYPE_NAME", 1043, getMaxNameLength());
/* 3726 */     f[1] = new Field("DATA_TYPE", 21, 2);
/* 3727 */     f[2] = new Field("PRECISION", 23, 4);
/* 3728 */     f[3] = new Field("LITERAL_PREFIX", 1043, getMaxNameLength());
/* 3729 */     f[4] = new Field("LITERAL_SUFFIX", 1043, getMaxNameLength());
/* 3730 */     f[5] = new Field("CREATE_PARAMS", 1043, getMaxNameLength());
/* 3731 */     f[6] = new Field("NULLABLE", 21, 2);
/* 3732 */     f[7] = new Field("CASE_SENSITIVE", 16, 1);
/* 3733 */     f[8] = new Field("SEARCHABLE", 21, 2);
/* 3734 */     f[9] = new Field("UNSIGNED_ATTRIBUTE", 16, 1);
/* 3735 */     f[10] = new Field("FIXED_PREC_SCALE", 16, 1);
/* 3736 */     f[11] = new Field("AUTO_INCREMENT", 16, 1);
/* 3737 */     f[12] = new Field("LOCAL_TYPE_NAME", 1043, getMaxNameLength());
/* 3738 */     f[13] = new Field("MINIMUM_SCALE", 21, 2);
/* 3739 */     f[14] = new Field("MAXIMUM_SCALE", 21, 2);
/* 3740 */     f[15] = new Field("SQL_DATA_TYPE", 23, 4);
/* 3741 */     f[16] = new Field("SQL_DATETIME_SUB", 23, 4);
/* 3742 */     f[17] = new Field("NUM_PREC_RADIX", 23, 4);
/*      */ 
/*      */     
/* 3745 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */       
/* 3747 */       str = "SELECT typname FROM pg_catalog.pg_type";
/*      */     }
/*      */     else {
/*      */       
/* 3751 */       str = "SELECT typname FROM pg_type";
/*      */     } 
/*      */     
/* 3754 */     ResultSet rs = this.connection.createStatement().executeQuery(str);
/*      */ 
/*      */     
/* 3757 */     byte[] b9 = this.connection.encodeString("9");
/* 3758 */     byte[] b10 = this.connection.encodeString("10");
/* 3759 */     byte[] bf = this.connection.encodeString("f");
/* 3760 */     byte[] bt = this.connection.encodeString("t");
/* 3761 */     byte[] bnn = this.connection.encodeString(Integer.toString(0));
/* 3762 */     byte[] bts = this.connection.encodeString(Integer.toString(3));
/*      */     
/* 3764 */     while (rs.next()) {
/*      */       
/* 3766 */       byte[][] tuple = new byte[18][];
/* 3767 */       String typname = rs.getString(1);
/* 3768 */       tuple[0] = this.connection.encodeString(typname);
/* 3769 */       tuple[1] = this.connection.encodeString(Integer.toString(this.connection.getSQLType(typname)));
/* 3770 */       tuple[2] = b9;
/* 3771 */       tuple[6] = bnn;
/* 3772 */       tuple[7] = bf;
/* 3773 */       tuple[8] = bts;
/* 3774 */       tuple[9] = bf;
/* 3775 */       tuple[10] = bf;
/* 3776 */       tuple[11] = bf;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3781 */       tuple[17] = b10;
/* 3782 */       v.addElement(tuple);
/*      */ 
/*      */       
/* 3785 */       if (typname.equals("int4")) {
/*      */         
/* 3787 */         byte[][] tuple1 = (byte[][])tuple.clone();
/*      */         
/* 3789 */         tuple1[0] = this.connection.encodeString("serial");
/* 3790 */         tuple1[11] = bt;
/* 3791 */         v.addElement(tuple1); continue;
/*      */       } 
/* 3793 */       if (typname.equals("int8")) {
/*      */         
/* 3795 */         byte[][] tuple1 = (byte[][])tuple.clone();
/*      */         
/* 3797 */         tuple1[0] = this.connection.encodeString("bigserial");
/* 3798 */         tuple1[11] = bt;
/* 3799 */         v.addElement(tuple1);
/*      */       } 
/*      */     } 
/*      */     
/* 3803 */     rs.close();
/*      */     
/* 3805 */     return ((BaseStatement)createMetaDataStatement()).createDriverResultSet(f, v);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getIndexInfo(String catalog, String schema, String tableName, boolean unique, boolean approximate) throws SQLException {
/* 3863 */     String str1, str2, where = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3875 */     if (this.connection.haveMinimumServerVersion("7.3")) {
/*      */       
/* 3877 */       str1 = "SELECT NULL AS TABLE_CAT, n.nspname AS TABLE_SCHEM, ";
/* 3878 */       str2 = " FROM pg_catalog.pg_namespace n, pg_catalog.pg_class ct, pg_catalog.pg_class ci, pg_catalog.pg_attribute a, pg_catalog.pg_am am ";
/* 3879 */       where = " AND n.oid = ct.relnamespace ";
/*      */       
/* 3881 */       if (!this.connection.haveMinimumServerVersion("7.4")) {
/* 3882 */         str2 = str2 + ", pg_catalog.pg_attribute ai, pg_catalog.pg_index i LEFT JOIN pg_catalog.pg_proc ip ON (i.indproc = ip.oid) ";
/* 3883 */         where = where + " AND ai.attnum = i.indkey[0] AND ai.attrelid = ct.oid ";
/*      */       } else {
/* 3885 */         str2 = str2 + ", pg_catalog.pg_index i ";
/*      */       } 
/* 3887 */       if (schema != null && !"".equals(schema))
/*      */       {
/* 3889 */         where = where + " AND n.nspname = '" + escapeQuotes(schema) + "' ";
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 3894 */       str1 = "SELECT NULL AS TABLE_CAT, NULL AS TABLE_SCHEM, ";
/* 3895 */       str2 = " FROM pg_class ct, pg_class ci, pg_attribute a, pg_am am, pg_attribute ai, pg_index i LEFT JOIN pg_proc ip ON (i.indproc = ip.oid) ";
/* 3896 */       where = " AND ai.attnum = i.indkey[0] AND ai.attrelid = ct.oid ";
/*      */     } 
/*      */     
/* 3899 */     String sql = str1 + " ct.relname AS TABLE_NAME, NOT i.indisunique AS NON_UNIQUE, NULL AS INDEX_QUALIFIER, ci.relname AS INDEX_NAME, " + " CASE i.indisclustered " + " WHEN true THEN " + '\001' + " ELSE CASE am.amname " + " WHEN 'hash' THEN " + '\002' + " ELSE " + '\003' + " END " + " END AS TYPE, " + " a.attnum AS ORDINAL_POSITION, ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3910 */     if (this.connection.haveMinimumServerVersion("7.4")) {
/*      */       
/* 3912 */       sql = sql + " CASE i.indexprs WHEN null THEN a.attname ELSE pg_get_indexdef(ci.oid,a.attnum,false) END AS COLUMN_NAME, ";
/*      */     }
/*      */     else {
/*      */       
/* 3916 */       sql = sql + " CASE i.indproc WHEN 0 THEN a.attname ELSE ip.proname || '(' || ai.attname || ')' END AS COLUMN_NAME, ";
/*      */     } 
/*      */ 
/*      */     
/* 3920 */     sql = sql + " NULL AS ASC_OR_DESC,  ci.reltuples AS CARDINALITY,  ci.relpages AS PAGES,  NULL AS FILTER_CONDITION " + str2 + " WHERE ct.oid=i.indrelid AND ci.oid=i.indexrelid AND a.attrelid=ci.oid AND ci.relam=am.oid " + where + " AND ct.relname = '" + escapeQuotes(tableName) + "' ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3929 */     if (unique)
/*      */     {
/* 3931 */       sql = sql + " AND i.indisunique ";
/*      */     }
/* 3933 */     sql = sql + " ORDER BY NON_UNIQUE, TYPE, INDEX_NAME, ORDINAL_POSITION ";
/* 3934 */     return createMetaDataStatement().executeQuery(sql);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Vector tokenize(String input, String delimiter) {
/* 3941 */     Vector result = new Vector();
/* 3942 */     int start = 0;
/* 3943 */     int end = input.length();
/* 3944 */     int delimiterSize = delimiter.length();
/*      */     
/* 3946 */     while (start < end) {
/*      */       
/* 3948 */       int delimiterIndex = input.indexOf(delimiter, start);
/* 3949 */       if (delimiterIndex < 0) {
/*      */         
/* 3951 */         result.addElement(input.substring(start));
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/* 3956 */       String token = input.substring(start, delimiterIndex);
/* 3957 */       result.addElement(token);
/* 3958 */       start = delimiterIndex + delimiterSize;
/*      */     } 
/*      */     
/* 3961 */     return result;
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
/*      */   public boolean supportsResultSetType(int type) throws SQLException {
/* 3976 */     return (type != 1005);
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
/*      */   public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
/* 3992 */     if (type == 1005) {
/* 3993 */       return false;
/*      */     }
/*      */     
/* 3996 */     if (concurrency == 1008) {
/* 3997 */       return true;
/*      */     }
/*      */     
/* 4000 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean ownUpdatesAreVisible(int type) throws SQLException {
/* 4007 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean ownDeletesAreVisible(int type) throws SQLException {
/* 4012 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean ownInsertsAreVisible(int type) throws SQLException {
/* 4018 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean othersUpdatesAreVisible(int type) throws SQLException {
/* 4023 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean othersDeletesAreVisible(int i) throws SQLException {
/* 4028 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean othersInsertsAreVisible(int type) throws SQLException {
/* 4033 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean updatesAreDetected(int type) throws SQLException {
/* 4038 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean deletesAreDetected(int i) throws SQLException {
/* 4043 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean insertsAreDetected(int type) throws SQLException {
/* 4048 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsBatchUpdates() throws SQLException {
/* 4056 */     return true;
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
/*      */   public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
/* 4074 */     String sql = "select null as type_cat, n.nspname as type_schem, t.typname as type_name,  null as class_name, CASE WHEN t.typtype='c' then 2002 else 2001 end as data_type, pg_catalog.obj_description(t.oid, 'pg_type')  as remarks, CASE WHEN t.typtype = 'd' then  (select CASE";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4079 */     for (Iterator i = this.connection.getPGTypeNamesWithSQLTypes(); i.hasNext(); ) {
/* 4080 */       String pgType = i.next();
/* 4081 */       int sqlType = this.connection.getSQLType(pgType);
/* 4082 */       sql = sql + " when typname = '" + escapeQuotes(pgType) + "' then " + sqlType;
/*      */     } 
/*      */     
/* 4085 */     sql = sql + " else 1111 end from pg_type where oid=t.typbasetype) else null end as base_type from pg_catalog.pg_type t, pg_catalog.pg_namespace n where t.typnamespace = n.oid and n.nspname != 'pg_catalog' and n.nspname != 'pg_toast'";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4091 */     String toAdd = "";
/* 4092 */     if (types != null) {
/*      */       
/* 4094 */       toAdd = toAdd + " and (false ";
/* 4095 */       for (int j = 0; j < types.length; j++) {
/*      */         
/* 4097 */         switch (types[j]) {
/*      */           
/*      */           case 2002:
/* 4100 */             toAdd = toAdd + " or t.typtype = 'c'";
/*      */             break;
/*      */           case 2001:
/* 4103 */             toAdd = toAdd + " or t.typtype = 'd'";
/*      */             break;
/*      */         } 
/*      */       } 
/* 4107 */       toAdd = toAdd + " ) ";
/*      */     }
/*      */     else {
/*      */       
/* 4111 */       toAdd = toAdd + " and t.typtype IN ('c','d') ";
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4116 */     if (typeNamePattern != null) {
/*      */ 
/*      */       
/* 4119 */       int firstQualifier = typeNamePattern.indexOf('.');
/* 4120 */       int secondQualifier = typeNamePattern.lastIndexOf('.');
/*      */       
/* 4122 */       if (firstQualifier != -1) {
/*      */         
/* 4124 */         if (firstQualifier != secondQualifier) {
/*      */ 
/*      */           
/* 4127 */           schemaPattern = typeNamePattern.substring(firstQualifier + 1, secondQualifier);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 4132 */           schemaPattern = typeNamePattern.substring(0, firstQualifier);
/*      */         } 
/*      */         
/* 4135 */         typeNamePattern = typeNamePattern.substring(secondQualifier + 1);
/*      */       } 
/* 4137 */       toAdd = toAdd + " and t.typname like '" + escapeQuotes(typeNamePattern) + "'";
/*      */     } 
/*      */ 
/*      */     
/* 4141 */     if (schemaPattern != null)
/*      */     {
/* 4143 */       toAdd = toAdd + " and n.nspname like '" + escapeQuotes(schemaPattern) + "'";
/*      */     }
/* 4145 */     sql = sql + toAdd;
/* 4146 */     sql = sql + " order by data_type, type_schem, type_name";
/* 4147 */     ResultSet rs = createMetaDataStatement().executeQuery(sql);
/*      */     
/* 4149 */     return rs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Connection getConnection() throws SQLException {
/* 4160 */     return (Connection)this.connection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean rowChangesAreDetected(int type) throws SQLException {
/* 4167 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean rowChangesAreVisible(int type) throws SQLException {
/* 4172 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Statement createMetaDataStatement() throws SQLException {
/* 4177 */     return this.connection.createStatement(1004, 1007);
/*      */   }
/*      */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc2\AbstractJdbc2DatabaseMetaData.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */