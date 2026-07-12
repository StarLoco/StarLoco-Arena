/*     */ package org.postgresql.jdbc2;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import org.postgresql.PGResultSetMetaData;
/*     */ import org.postgresql.core.BaseConnection;
/*     */ import org.postgresql.core.Field;
/*     */ import org.postgresql.util.GT;
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
/*     */ public abstract class AbstractJdbc2ResultSetMetaData
/*     */   implements PGResultSetMetaData
/*     */ {
/*     */   protected final BaseConnection connection;
/*     */   protected final Field[] fields;
/*     */   private Hashtable tableNameCache;
/*     */   private Hashtable schemaNameCache;
/*     */   
/*     */   public AbstractJdbc2ResultSetMetaData(BaseConnection connection, Field[] fields) {
/*  36 */     this.connection = connection;
/*  37 */     this.fields = fields;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnCount() throws SQLException {
/*  48 */     return this.fields.length;
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
/*     */   public boolean isAutoIncrement(int column) throws SQLException {
/*  61 */     Field field = getField(column);
/*  62 */     return field.getAutoIncrement((Connection)this.connection);
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
/*     */   public boolean isCaseSensitive(int column) throws SQLException {
/*  75 */     int sql_type = getSQLType(column);
/*     */     
/*  77 */     switch (sql_type) {
/*     */       
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*     */       case 91:
/*     */       case 92:
/*     */       case 93:
/*  87 */         return false;
/*     */     } 
/*  89 */     return true;
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
/*     */   public boolean isSearchable(int column) throws SQLException {
/* 107 */     int sql_type = getSQLType(column);
/*     */ 
/*     */ 
/*     */     
/* 111 */     switch (sql_type) {
/*     */       
/*     */       case 1111:
/* 114 */         return true;
/*     */     } 
/* 116 */     return true;
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
/*     */   public boolean isCurrency(int column) throws SQLException {
/* 131 */     String type_name = getPGType(column);
/*     */     
/* 133 */     return (type_name.equals("cash") || type_name.equals("money"));
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
/*     */   public int isNullable(int column) throws SQLException {
/* 145 */     Field field = getField(column);
/* 146 */     return field.getNullable((Connection)this.connection);
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
/*     */   public boolean isSigned(int column) throws SQLException {
/* 160 */     int sql_type = getSQLType(column);
/*     */     
/* 162 */     switch (sql_type) {
/*     */       
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/* 169 */         return true;
/*     */       case 91:
/*     */       case 92:
/*     */       case 93:
/* 173 */         return false;
/*     */     } 
/* 175 */     return false;
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
/*     */   public int getColumnDisplaySize(int column) throws SQLException {
/*     */     int secondSize;
/* 188 */     Field f = getField(column);
/* 189 */     String type_name = getPGType(column);
/* 190 */     int typmod = f.getMod();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     if (type_name.equals("int2"))
/* 198 */       return 6; 
/* 199 */     if (type_name.equals("int4") || type_name.equals("oid"))
/*     */     {
/* 201 */       return 11; } 
/* 202 */     if (type_name.equals("int8"))
/* 203 */       return 20; 
/* 204 */     if (type_name.equals("money"))
/* 205 */       return 12; 
/* 206 */     if (type_name.equals("float4"))
/* 207 */       return 11; 
/* 208 */     if (type_name.equals("float8"))
/* 209 */       return 20; 
/* 210 */     if (type_name.equals("char"))
/* 211 */       return 1; 
/* 212 */     if (type_name.equals("bool")) {
/* 213 */       return 1;
/*     */     }
/*     */     
/* 216 */     switch (typmod) {
/*     */       
/*     */       case 0:
/* 219 */         secondSize = 0;
/*     */         break;
/*     */       
/*     */       case -1:
/* 223 */         secondSize = 7;
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 229 */         secondSize = typmod + typmod % 2 + 1;
/*     */         break;
/*     */     } 
/*     */     
/* 233 */     if (type_name.equals("date")) {
/* 234 */       return 13;
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
/* 245 */     if (type_name.equals("time"))
/* 246 */       return 8 + secondSize; 
/* 247 */     if (type_name.equals("timetz"))
/* 248 */       return 8 + secondSize + 6; 
/* 249 */     if (type_name.equals("timestamp"))
/* 250 */       return 19 + secondSize; 
/* 251 */     if (type_name.equals("timestamptz")) {
/* 252 */       return 19 + secondSize + 6;
/*     */     }
/*     */     
/* 255 */     typmod -= 4;
/* 256 */     if (type_name.equals("bpchar") || type_name.equals("varchar"))
/*     */     {
/* 258 */       return typmod; } 
/* 259 */     if (type_name.equals("numeric")) {
/* 260 */       if (typmod + 4 == -1)
/* 261 */         return 1002; 
/* 262 */       int precision = typmod >> 16 & 0xFFFF;
/* 263 */       int scale = typmod & 0xFFFF;
/*     */       
/* 265 */       return 1 + precision + ((scale != 0) ? 1 : 0);
/*     */     } 
/*     */ 
/*     */     
/* 269 */     return f.getLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColumnLabel(int column) throws SQLException {
/* 279 */     Field f = getField(column);
/* 280 */     if (f != null)
/* 281 */       return f.getColumnLabel(); 
/* 282 */     return "field" + column;
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
/*     */   public String getColumnName(int column) throws SQLException {
/* 294 */     return getColumnLabel(column);
/*     */   }
/*     */   
/*     */   public String getBaseColumnName(int column) throws SQLException {
/* 298 */     Field field = getField(column);
/* 299 */     return field.getColumnName((Connection)this.connection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSchemaName(int column) throws SQLException {
/* 309 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseSchemaName(int column) throws SQLException {
/* 314 */     Field field = getField(column);
/* 315 */     if (field.getTableOid() == 0)
/*     */     {
/* 317 */       return "";
/*     */     }
/* 319 */     Integer tableOid = new Integer(field.getTableOid());
/* 320 */     if (this.schemaNameCache == null)
/*     */     {
/* 322 */       this.schemaNameCache = new Hashtable();
/*     */     }
/* 324 */     String schemaName = (String)this.schemaNameCache.get(tableOid);
/* 325 */     if (schemaName != null)
/*     */     {
/* 327 */       return schemaName;
/*     */     }
/*     */ 
/*     */     
/* 331 */     ResultSet res = null;
/* 332 */     PreparedStatement ps = null;
/*     */     
/*     */     try {
/* 335 */       String sql = "SELECT n.nspname FROM pg_catalog.pg_class c, pg_catalog.pg_namespace n WHERE n.oid = c.relnamespace AND c.oid = ?;";
/* 336 */       ps = this.connection.prepareStatement(sql);
/* 337 */       ps.setInt(1, tableOid.intValue());
/* 338 */       res = ps.executeQuery();
/* 339 */       schemaName = "";
/* 340 */       if (res.next())
/*     */       {
/* 342 */         schemaName = res.getString(1);
/*     */       }
/* 344 */       this.schemaNameCache.put(tableOid, schemaName);
/* 345 */       return schemaName;
/*     */     }
/*     */     finally {
/*     */       
/* 349 */       if (res != null)
/* 350 */         res.close(); 
/* 351 */       if (ps != null) {
/* 352 */         ps.close();
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
/*     */   public int getPrecision(int column) throws SQLException {
/*     */     Field field;
/* 366 */     int typmod, sql_type = getSQLType(column);
/*     */ 
/*     */     
/* 369 */     switch (sql_type) {
/*     */       
/*     */       case 5:
/* 372 */         return 5;
/*     */       case 4:
/* 374 */         return 10;
/*     */       case -5:
/* 376 */         return 19;
/*     */       case 7:
/* 378 */         return 8;
/*     */       case 6:
/* 380 */         return 16;
/*     */       case 8:
/* 382 */         return 16;
/*     */       case 1:
/*     */       case 12:
/* 385 */         field = getField(column);
/* 386 */         typmod = -1;
/* 387 */         if (field != null)
/*     */         {
/* 389 */           typmod = field.getMod();
/*     */         }
/* 391 */         if (typmod == -1)
/* 392 */           return 0; 
/* 393 */         return field.getMod() - 4;
/*     */       case 2:
/* 395 */         field = getField(column);
/* 396 */         if (field != null) {
/*     */ 
/*     */           
/* 399 */           if (field.getMod() == -1)
/*     */           {
/* 401 */             return -1;
/*     */           }
/* 403 */           return (0xFFFF0000 & field.getMod()) >> 16;
/*     */         } 
/*     */ 
/*     */         
/* 407 */         return 0;
/*     */     } 
/*     */     
/* 410 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScale(int column) throws SQLException {
/*     */     Field field;
/*     */     int typmod;
/*     */     String type;
/* 424 */     int sql_type = getSQLType(column);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 429 */     switch (sql_type) {
/*     */       
/*     */       case 7:
/* 432 */         return 8;
/*     */       case 6:
/* 434 */         return 16;
/*     */       case 8:
/* 436 */         return 16;
/*     */       case 2:
/* 438 */         field = getField(column);
/* 439 */         if (field != null) {
/*     */ 
/*     */           
/* 442 */           if (field.getMod() == -1)
/*     */           {
/* 444 */             return -1;
/*     */           }
/* 446 */           return (0xFFFF & field.getMod()) - 4;
/*     */         } 
/*     */ 
/*     */         
/* 450 */         return 0;
/*     */       
/*     */       case 92:
/*     */       case 93:
/* 454 */         typmod = -1;
/* 455 */         field = getField(column);
/* 456 */         if (field != null) {
/* 457 */           typmod = field.getMod();
/*     */         }
/* 459 */         if (typmod == -1) {
/* 460 */           return 6;
/*     */         }
/* 462 */         return typmod;
/*     */       case 1111:
/* 464 */         type = getColumnTypeName(column);
/*     */         
/* 466 */         if ("interval".equals(type)) {
/* 467 */           typmod = -1;
/* 468 */           field = getField(column);
/* 469 */           if (field != null) {
/* 470 */             typmod = field.getMod();
/*     */           }
/* 472 */           if (typmod == -1) {
/* 473 */             return 6;
/*     */           }
/* 475 */           return typmod & 0xFFFF;
/*     */         } 
/*     */         
/* 478 */         return 0;
/*     */     } 
/* 480 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTableName(int column) throws SQLException {
/* 491 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseTableName(int column) throws SQLException {
/* 496 */     Field field = getField(column);
/* 497 */     if (field.getTableOid() == 0)
/*     */     {
/* 499 */       return "";
/*     */     }
/* 501 */     Integer tableOid = new Integer(field.getTableOid());
/* 502 */     if (this.tableNameCache == null)
/*     */     {
/* 504 */       this.tableNameCache = new Hashtable();
/*     */     }
/* 506 */     String tableName = (String)this.tableNameCache.get(tableOid);
/* 507 */     if (tableName != null)
/*     */     {
/* 509 */       return tableName;
/*     */     }
/*     */ 
/*     */     
/* 513 */     ResultSet res = null;
/* 514 */     PreparedStatement ps = null;
/*     */     
/*     */     try {
/* 517 */       ps = this.connection.prepareStatement("SELECT relname FROM pg_catalog.pg_class WHERE oid = ?");
/* 518 */       ps.setInt(1, tableOid.intValue());
/* 519 */       res = ps.executeQuery();
/* 520 */       tableName = "";
/* 521 */       if (res.next())
/*     */       {
/* 523 */         tableName = res.getString(1);
/*     */       }
/* 525 */       this.tableNameCache.put(tableOid, tableName);
/* 526 */       return tableName;
/*     */     }
/*     */     finally {
/*     */       
/* 530 */       if (res != null)
/* 531 */         res.close(); 
/* 532 */       if (ps != null) {
/* 533 */         ps.close();
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
/*     */   public String getCatalogName(int column) throws SQLException {
/* 549 */     return "";
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
/*     */   public int getColumnType(int column) throws SQLException {
/* 563 */     return getSQLType(column);
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
/*     */   public String getColumnTypeName(int column) throws SQLException {
/* 575 */     return getPGType(column);
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
/*     */   public boolean isReadOnly(int column) throws SQLException {
/* 590 */     return false;
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
/*     */   public boolean isWritable(int column) throws SQLException {
/* 605 */     return !isReadOnly(column);
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
/*     */   public boolean isDefinitelyWritable(int column) throws SQLException {
/* 620 */     return false;
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
/*     */   protected Field getField(int columnIndex) throws SQLException {
/* 638 */     if (columnIndex < 1 || columnIndex > this.fields.length)
/* 639 */       throw new PSQLException(GT.tr("The column index is out of range: {0}, number of columns: {1}.", new Object[] { new Integer(columnIndex), new Integer(this.fields.length) }), PSQLState.INVALID_PARAMETER_VALUE); 
/* 640 */     return this.fields[columnIndex - 1];
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getPGType(int columnIndex) throws SQLException {
/* 645 */     return this.connection.getPGType(getField(columnIndex).getOID());
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getSQLType(int columnIndex) throws SQLException {
/* 650 */     return this.connection.getSQLType(getField(columnIndex).getOID());
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
/*     */   public String getColumnClassName(int column) throws SQLException {
/* 674 */     Field field = getField(column);
/* 675 */     int sql_type = getSQLType(column);
/*     */     
/* 677 */     switch (sql_type) {
/*     */       
/*     */       case -7:
/* 680 */         return "java.lang.Boolean";
/*     */       case 4:
/*     */       case 5:
/* 683 */         return "java.lang.Integer";
/*     */       case -5:
/* 685 */         return "java.lang.Long";
/*     */       case 2:
/* 687 */         return "java.math.BigDecimal";
/*     */       case 7:
/* 689 */         return "java.lang.Float";
/*     */       case 8:
/* 691 */         return "java.lang.Double";
/*     */       case 1:
/*     */       case 12:
/* 694 */         return "java.lang.String";
/*     */       case 91:
/* 696 */         return "java.sql.Date";
/*     */       case 92:
/* 698 */         return "java.sql.Time";
/*     */       case 93:
/* 700 */         return "java.sql.Timestamp";
/*     */       case -3:
/*     */       case -2:
/* 703 */         return "[B";
/*     */       case 2003:
/* 705 */         return "java.sql.Array";
/*     */     } 
/* 707 */     String type = getPGType(column);
/* 708 */     if ("unknown".equals(type))
/*     */     {
/* 710 */       return "java.lang.String";
/*     */     }
/* 712 */     return "java.lang.Object";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc2\AbstractJdbc2ResultSetMetaData.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */