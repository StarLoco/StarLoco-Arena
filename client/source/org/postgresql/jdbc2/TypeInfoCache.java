/*     */ package org.postgresql.jdbc2;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.postgresql.core.BaseConnection;
/*     */ import org.postgresql.core.BaseStatement;
/*     */ import org.postgresql.util.GT;
/*     */ import org.postgresql.util.PGobject;
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
/*     */ public class TypeInfoCache
/*     */ {
/*     */   private Map _pgNameToSQLType;
/*     */   private Map _pgNameToJavaClass;
/*     */   private Map _oidToPgName;
/*     */   private Map _pgNameToOid;
/*     */   private Map _pgNameToPgObject;
/*     */   private BaseConnection _conn;
/*     */   private PreparedStatement _getOidStatement;
/*     */   private PreparedStatement _getNameStatement;
/*  51 */   private static Object[][] types = new Object[][] { { "int2", new Integer(21), new Integer(5), "java.lang.Short" }, { "int4", new Integer(23), new Integer(4), "java.lang.Integer" }, { "oid", new Integer(26), new Integer(4), "java.lang.Integer" }, { "int8", new Integer(20), new Integer(-5), "java.lang.Long" }, { "money", new Integer(790), new Integer(8), "java.lang.Double" }, { "numeric", new Integer(1700), new Integer(2), "java.math.BigDecimal" }, { "float4", new Integer(700), new Integer(7), "java.lang.Float" }, { "float8", new Integer(701), new Integer(8), "java.lang.Double" }, { "bpchar", new Integer(1042), new Integer(1), "java.lang.String" }, { "varchar", new Integer(1043), new Integer(12), "java.lang.String" }, { "text", new Integer(25), new Integer(12), "java.lang.String" }, { "name", new Integer(19), new Integer(12), "java.lang.String" }, { "bytea", new Integer(17), new Integer(-2), "java.io.InputStream" }, { "bool", new Integer(16), new Integer(-7), "java.lang.Boolean" }, { "bit", new Integer(1560), new Integer(-7), "java.lang.Boolean" }, { "date", new Integer(1082), new Integer(91), "java.sql.Date" }, { "time", new Integer(1083), new Integer(92), "java.sql.Time" }, { "timetz", new Integer(1266), new Integer(92), "java.sql.Time" }, { "timestamp", new Integer(1114), new Integer(93), "java.sql.Timestamp" }, { "timestamptz", new Integer(1184), new Integer(93), "java.sql.Timestamp" } };
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
/*     */   public TypeInfoCache(BaseConnection conn) {
/*  76 */     this._conn = conn;
/*  77 */     this._oidToPgName = Collections.synchronizedMap(new HashMap());
/*  78 */     this._pgNameToOid = Collections.synchronizedMap(new HashMap());
/*  79 */     this._pgNameToSQLType = Collections.synchronizedMap(new HashMap());
/*  80 */     this._pgNameToJavaClass = Collections.synchronizedMap(new HashMap());
/*  81 */     this._pgNameToPgObject = Collections.synchronizedMap(new HashMap());
/*     */     
/*  83 */     for (int i = 0; i < types.length; i++) {
/*  84 */       this._pgNameToSQLType.put(types[i][0], types[i][2]);
/*  85 */       this._pgNameToJavaClass.put(types[i][0], types[i][3]);
/*  86 */       this._pgNameToOid.put(types[i][0], types[i][1]);
/*  87 */       this._oidToPgName.put(types[i][1], types[i][0]);
/*     */       
/*  89 */       String arrayType = "_" + types[i][0];
/*  90 */       this._pgNameToSQLType.put(arrayType, new Integer(2003));
/*  91 */       this._pgNameToJavaClass.put(arrayType, "java.sql.Array");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDataType(String type, Class klass) throws SQLException {
/*  97 */     if (!PGobject.class.isAssignableFrom(klass)) {
/*  98 */       throw new PSQLException(GT.tr("The class {0} does not implement org.postgresql.util.PGobject.", klass.toString()), PSQLState.INVALID_PARAMETER_TYPE);
/*     */     }
/* 100 */     this._pgNameToPgObject.put(type, klass);
/* 101 */     this._pgNameToJavaClass.put(type, klass.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator getPGTypeNamesWithSQLTypes() {
/* 106 */     return this._pgNameToSQLType.keySet().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSQLType(int oid) throws SQLException {
/* 111 */     return getSQLType(getPGType(oid));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSQLType(String pgTypeName) {
/* 116 */     Integer i = (Integer)this._pgNameToSQLType.get(pgTypeName);
/* 117 */     if (i != null)
/* 118 */       return i.intValue(); 
/* 119 */     return 1111;
/*     */   }
/*     */   
/*     */   public int getPGType(String pgTypeName) throws SQLException {
/*     */     String str;
/* 124 */     Integer oid = (Integer)this._pgNameToOid.get(pgTypeName);
/* 125 */     if (oid != null) {
/* 126 */       return oid.intValue();
/*     */     }
/*     */     
/* 129 */     if (this._conn.haveMinimumServerVersion("7.3")) {
/* 130 */       str = "SELECT oid FROM pg_catalog.pg_type WHERE typname = ?";
/*     */     } else {
/* 132 */       str = "SELECT oid FROM pg_type WHERE typname = ?";
/*     */     } 
/* 134 */     if (this._getOidStatement == null) {
/* 135 */       this._getOidStatement = this._conn.prepareStatement(str);
/*     */     }
/* 137 */     this._getOidStatement.setString(1, pgTypeName);
/*     */ 
/*     */     
/* 140 */     if (!((BaseStatement)this._getOidStatement).executeWithFlags(16)) {
/* 141 */       throw new PSQLException(GT.tr("No results were returned by the query."), PSQLState.NO_DATA);
/*     */     }
/* 143 */     oid = new Integer(0);
/* 144 */     ResultSet rs = this._getOidStatement.getResultSet();
/* 145 */     if (rs.next()) {
/* 146 */       oid = new Integer(rs.getInt(1));
/* 147 */       this._oidToPgName.put(oid, pgTypeName);
/*     */     } 
/* 149 */     this._pgNameToOid.put(pgTypeName, oid);
/* 150 */     rs.close();
/*     */     
/* 152 */     return oid.intValue();
/*     */   }
/*     */   
/*     */   public String getPGType(int oid) throws SQLException {
/*     */     String str1;
/* 157 */     if (oid == 0) {
/* 158 */       return null;
/*     */     }
/* 160 */     String pgTypeName = (String)this._oidToPgName.get(new Integer(oid));
/* 161 */     if (pgTypeName != null) {
/* 162 */       return pgTypeName;
/*     */     }
/*     */     
/* 165 */     if (this._conn.haveMinimumServerVersion("7.3")) {
/* 166 */       str1 = "SELECT typname FROM pg_catalog.pg_type WHERE oid = ?";
/*     */     } else {
/* 168 */       str1 = "SELECT typname FROM pg_type WHERE oid = ?";
/*     */     } 
/* 170 */     if (this._getNameStatement == null) {
/* 171 */       this._getNameStatement = this._conn.prepareStatement(str1);
/*     */     }
/* 173 */     this._getNameStatement.setInt(1, oid);
/*     */ 
/*     */     
/* 176 */     if (!((BaseStatement)this._getNameStatement).executeWithFlags(16)) {
/* 177 */       throw new PSQLException(GT.tr("No results were returned by the query."), PSQLState.NO_DATA);
/*     */     }
/* 179 */     ResultSet rs = this._getNameStatement.getResultSet();
/* 180 */     if (rs.next()) {
/* 181 */       pgTypeName = rs.getString(1);
/* 182 */       this._pgNameToOid.put(pgTypeName, new Integer(oid));
/* 183 */       this._oidToPgName.put(new Integer(oid), pgTypeName);
/*     */     } 
/* 185 */     rs.close();
/*     */     
/* 187 */     return pgTypeName;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getPGobject(String type) {
/* 192 */     return (Class)this._pgNameToPgObject.get(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getJavaClass(int oid) throws SQLException {
/* 197 */     String pgTypeName = getPGType(oid);
/* 198 */     return (String)this._pgNameToJavaClass.get(pgTypeName);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc2\TypeInfoCache.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */