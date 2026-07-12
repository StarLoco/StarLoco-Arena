/*     */ package org.postgresql.core;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
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
/*     */ public class Field
/*     */ {
/*     */   public static final int TEXT_FORMAT = 0;
/*     */   public static final int BINARY_FORMAT = 1;
/*     */   private final int length;
/*     */   private final int oid;
/*     */   private final int mod;
/*     */   private final String columnLabel;
/*     */   private String columnName;
/*     */   private Integer nullable;
/*     */   private Boolean autoIncrement;
/*  30 */   private int format = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int tableOid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int positionInTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Field(String name, int oid, int length, int mod) {
/*  50 */     this(name, name, oid, length, mod, 0, 0);
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
/*     */   public Field(String name, int oid, int length) {
/*  62 */     this(name, name, oid, length, 0, 0, 0);
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
/*     */   public Field(String columnLabel, String columnName, int oid, int length, int mod, int tableOid, int positionInTable) {
/*  77 */     this.columnLabel = columnLabel;
/*  78 */     this.columnName = columnName;
/*  79 */     this.oid = oid;
/*  80 */     this.length = length;
/*  81 */     this.mod = mod;
/*  82 */     this.tableOid = tableOid;
/*  83 */     this.positionInTable = positionInTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOID() {
/*  91 */     return this.oid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMod() {
/*  99 */     return this.mod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColumnLabel() {
/* 107 */     return this.columnLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 115 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFormat() {
/* 123 */     return this.format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormat(int format) {
/* 131 */     this.format = format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTableOid() {
/* 139 */     return this.tableOid;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPositionInTable() {
/* 144 */     return this.positionInTable;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNullable(Connection con) throws SQLException {
/* 149 */     if (this.nullable != null) {
/* 150 */       return this.nullable.intValue();
/*     */     }
/* 152 */     if (this.tableOid == 0 || this.positionInTable == 0) {
/*     */       
/* 154 */       this.nullable = new Integer(2);
/* 155 */       return this.nullable.intValue();
/*     */     } 
/*     */     
/* 158 */     ResultSet res = null;
/* 159 */     PreparedStatement ps = null;
/*     */     
/*     */     try {
/* 162 */       ps = con.prepareStatement("SELECT attnotnull FROM pg_catalog.pg_attribute WHERE attrelid = ? AND attnum = ?;");
/* 163 */       ps.setInt(1, this.tableOid);
/* 164 */       ps.setInt(2, this.positionInTable);
/* 165 */       res = ps.executeQuery();
/*     */       
/* 167 */       int nullResult = 2;
/* 168 */       if (res.next()) {
/* 169 */         nullResult = res.getBoolean(1) ? 0 : 1;
/*     */       }
/* 171 */       this.nullable = new Integer(nullResult);
/* 172 */       return nullResult;
/*     */     }
/*     */     finally {
/*     */       
/* 176 */       if (res != null)
/* 177 */         res.close(); 
/* 178 */       if (ps != null) {
/* 179 */         ps.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean getAutoIncrement(Connection con) throws SQLException {
/* 185 */     if (this.autoIncrement != null) {
/* 186 */       return this.autoIncrement.booleanValue();
/*     */     }
/* 188 */     if (this.tableOid == 0 || this.positionInTable == 0) {
/*     */       
/* 190 */       this.autoIncrement = Boolean.FALSE;
/* 191 */       return this.autoIncrement.booleanValue();
/*     */     } 
/*     */     
/* 194 */     ResultSet res = null;
/* 195 */     PreparedStatement ps = null;
/*     */     
/*     */     try {
/* 198 */       String sql = "SELECT def.adsrc FROM pg_catalog.pg_class c JOIN pg_catalog.pg_attribute a ON (a.attrelid=c.oid) LEFT JOIN pg_catalog.pg_attrdef def ON (a.attrelid=def.adrelid AND a.attnum = def.adnum) WHERE c.oid = ? and a.attnum = ? AND def.adsrc LIKE '%nextval(%'";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 203 */       ps = con.prepareStatement("SELECT def.adsrc FROM pg_catalog.pg_class c JOIN pg_catalog.pg_attribute a ON (a.attrelid=c.oid) LEFT JOIN pg_catalog.pg_attrdef def ON (a.attrelid=def.adrelid AND a.attnum = def.adnum) WHERE c.oid = ? and a.attnum = ? AND def.adsrc LIKE '%nextval(%'");
/*     */       
/* 205 */       ps.setInt(1, this.tableOid);
/* 206 */       ps.setInt(2, this.positionInTable);
/* 207 */       res = ps.executeQuery();
/*     */       
/* 209 */       if (res.next()) {
/*     */         
/* 211 */         this.autoIncrement = Boolean.TRUE;
/*     */       }
/*     */       else {
/*     */         
/* 215 */         this.autoIncrement = Boolean.FALSE;
/*     */       } 
/* 217 */       return this.autoIncrement.booleanValue();
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 222 */       if (res != null)
/* 223 */         res.close(); 
/* 224 */       if (ps != null) {
/* 225 */         ps.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getColumnName(Connection con) throws SQLException {
/* 231 */     if (this.columnName != null) {
/* 232 */       return this.columnName;
/*     */     }
/* 234 */     this.columnName = "";
/* 235 */     if (this.tableOid == 0 || this.positionInTable == 0)
/*     */     {
/* 237 */       return this.columnName;
/*     */     }
/*     */     
/* 240 */     ResultSet res = null;
/* 241 */     PreparedStatement ps = null;
/*     */     
/*     */     try {
/* 244 */       ps = con.prepareStatement("SELECT attname FROM pg_catalog.pg_attribute WHERE attrelid = ? AND attnum = ?");
/* 245 */       ps.setInt(1, this.tableOid);
/* 246 */       ps.setInt(2, this.positionInTable);
/* 247 */       res = ps.executeQuery();
/* 248 */       if (res.next()) {
/* 249 */         this.columnName = res.getString(1);
/*     */       }
/* 251 */       return this.columnName;
/*     */     }
/*     */     finally {
/*     */       
/* 255 */       if (res != null)
/* 256 */         res.close(); 
/* 257 */       if (ps != null)
/* 258 */         ps.close(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\Field.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */