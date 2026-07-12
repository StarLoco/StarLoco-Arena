/*     */ package org.postgresql.jdbc2;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Date;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import org.postgresql.Driver;
/*     */ import org.postgresql.core.BaseConnection;
/*     */ import org.postgresql.core.BaseResultSet;
/*     */ import org.postgresql.core.BaseStatement;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AbstractJdbc2Array
/*     */ {
/*  46 */   private BaseConnection conn = null;
/*  47 */   private Field field = null;
/*     */   private BaseResultSet rs;
/*  49 */   private int idx = 0;
/*  50 */   private String rawString = null;
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
/*     */   public AbstractJdbc2Array(BaseConnection conn, int idx, Field field, BaseResultSet rs) throws SQLException {
/*  63 */     this.conn = conn;
/*  64 */     this.field = field;
/*  65 */     this.rs = rs;
/*  66 */     this.idx = idx;
/*  67 */     this.rawString = rs.getFixedString(idx);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getArray() throws SQLException {
/*  72 */     return getArrayImpl(1L, 0, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getArray(long index, int count) throws SQLException {
/*  77 */     return getArrayImpl(index, count, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getArrayImpl(Map map) throws SQLException {
/*  82 */     return getArrayImpl(1L, 0, map);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getArrayImpl(long index, int count, Map map) throws SQLException {
/*  87 */     if (map != null && !map.isEmpty()) {
/*  88 */       throw Driver.notImplemented(getClass(), "getArrayImpl(long,int,Map)");
/*     */     }
/*  90 */     if (index < 1L)
/*  91 */       throw new PSQLException(GT.tr("The array index is out of range: {0}", new Long(index)), PSQLState.DATA_ERROR); 
/*  92 */     Object retVal = null;
/*     */     
/*  94 */     ArrayList array = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     if (this.rawString != null && !this.rawString.equals("{}")) {
/*     */       
/* 103 */       char[] chars = this.rawString.toCharArray();
/* 104 */       StringBuffer sbuf = new StringBuffer();
/* 105 */       boolean foundOpen = false;
/* 106 */       boolean insideString = false;
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
/* 120 */       int startOffset = 0;
/* 121 */       if (chars[0] == '[') {
/*     */         
/* 123 */         while (chars[startOffset] != '=')
/*     */         {
/* 125 */           startOffset++;
/*     */         }
/* 127 */         startOffset++;
/*     */       } 
/*     */       
/* 130 */       for (int j = startOffset; j < chars.length; j++) {
/*     */         
/* 132 */         if (chars[j] == '\\')
/*     */         
/* 134 */         { j++; }
/* 135 */         else { if (!insideString && chars[j] == '{') {
/*     */             
/* 137 */             if (foundOpen) {
/* 138 */               throw new PSQLException(GT.tr("Multi-dimensional arrays are currently not supported."), PSQLState.NOT_IMPLEMENTED);
/*     */             }
/* 140 */             foundOpen = true;
/*     */             continue;
/*     */           } 
/* 143 */           if (chars[j] == '"') {
/*     */             
/* 145 */             insideString = !insideString;
/*     */             continue;
/*     */           } 
/* 148 */           if ((!insideString && (chars[j] == ',' || chars[j] == '}')) || j == chars.length - 1) {
/*     */ 
/*     */             
/* 151 */             if (chars[j] != '"' && chars[j] != '}' && chars[j] != ',')
/* 152 */               sbuf.append(chars[j]); 
/* 153 */             array.add(sbuf.toString());
/* 154 */             sbuf = new StringBuffer(); continue;
/*     */           }  }
/*     */         
/* 157 */         sbuf.append(chars[j]); continue;
/*     */       } 
/*     */     } 
/* 160 */     String[] arrayContents = array.<String>toArray(new String[array.size()]);
/* 161 */     if (count == 0)
/* 162 */       count = arrayContents.length; 
/* 163 */     index--;
/* 164 */     if (index + count > arrayContents.length) {
/* 165 */       throw new PSQLException(GT.tr("The array index is out of range: {0}, number of elements: {1}.", new Object[] { new Long(index + count), new Long(arrayContents.length) }), PSQLState.DATA_ERROR);
/*     */     }
/* 167 */     int i = 0;
/* 168 */     GregorianCalendar cal = null;
/* 169 */     switch (getBaseType()) {
/*     */       
/*     */       case -7:
/* 172 */         retVal = new boolean[count];
/* 173 */         for (; count > 0; count--) {
/* 174 */           ((boolean[])retVal)[i++] = AbstractJdbc2ResultSet.toBoolean(arrayContents[(int)index++]);
/*     */         }
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
/* 231 */         return retVal;case 4: case 5: retVal = new int[count]; for (; count > 0; count--) ((int[])retVal)[i++] = AbstractJdbc2ResultSet.toInt(arrayContents[(int)index++]);  return retVal;case -5: retVal = new long[count]; for (; count > 0; count--) ((long[])retVal)[i++] = AbstractJdbc2ResultSet.toLong(arrayContents[(int)index++]);  return retVal;case 2: retVal = new BigDecimal[count]; for (; count > 0; count--) ((BigDecimal[])retVal)[i++] = AbstractJdbc2ResultSet.toBigDecimal(arrayContents[(int)index++], -1);  return retVal;case 7: retVal = new float[count]; for (; count > 0; count--) ((float[])retVal)[i++] = AbstractJdbc2ResultSet.toFloat(arrayContents[(int)index++]);  return retVal;case 8: retVal = new double[count]; for (; count > 0; count--) ((double[])retVal)[i++] = AbstractJdbc2ResultSet.toDouble(arrayContents[(int)index++]);  return retVal;case 1: case 12: retVal = new String[count]; for (; count > 0; count--) ((String[])retVal)[i++] = arrayContents[(int)index++];  return retVal;case 91: retVal = new Date[count]; for (; count > 0; count--) ((Date[])retVal)[i++] = this.conn.getTimestampUtils().toDate(null, arrayContents[(int)index++]);  return retVal;case 92: retVal = new Time[count]; for (; count > 0; count--) ((Time[])retVal)[i++] = this.conn.getTimestampUtils().toTime(null, arrayContents[(int)index++]);  return retVal;case 93: retVal = new Timestamp[count]; for (; count > 0; count--) ((Timestamp[])retVal)[i++] = this.conn.getTimestampUtils().toTimestamp(null, arrayContents[(int)index++]);  return retVal;
/*     */     } 
/*     */     if (Driver.logDebug)
/*     */       Driver.debug("getArrayImpl(long,int,Map) with " + getBaseTypeName()); 
/*     */     throw Driver.notImplemented(getClass(), "getArrayImpl(long,int,Map)"); } public int getBaseType() throws SQLException {
/* 236 */     return this.conn.getSQLType(getBaseTypeName());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseTypeName() throws SQLException {
/* 241 */     String fType = this.conn.getPGType(this.field.getOID());
/* 242 */     if (fType.charAt(0) == '_')
/* 243 */       fType = fType.substring(1); 
/* 244 */     return fType;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getResultSet() throws SQLException {
/* 249 */     return getResultSetImpl(1L, 0, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getResultSet(long index, int count) throws SQLException {
/* 254 */     return getResultSetImpl(index, count, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getResultSetImpl(Map map) throws SQLException {
/* 259 */     return getResultSetImpl(1L, 0, map);
/*     */   }
/*     */ 
/*     */   
/*     */   private void fillIntegerResultSet(long index, int[] intArray, Vector rows) throws SQLException {
/* 264 */     for (int i = 0; i < intArray.length; i++) {
/*     */       
/* 266 */       byte[][] tuple = new byte[2][0];
/* 267 */       tuple[0] = this.conn.encodeString(Integer.toString((int)index + i));
/* 268 */       tuple[1] = this.conn.encodeString(Integer.toString(intArray[i]));
/* 269 */       rows.addElement(tuple);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fillStringResultSet(long index, String[] strArray, Vector rows) throws SQLException {
/* 275 */     for (int i = 0; i < strArray.length; i++) {
/*     */       
/* 277 */       byte[][] tuple = new byte[2][0];
/* 278 */       tuple[0] = this.conn.encodeString(Integer.toString((int)index + i));
/* 279 */       tuple[1] = this.conn.encodeString(strArray[i]);
/* 280 */       rows.addElement(tuple);
/*     */     }  } public ResultSet getResultSetImpl(long index, int count, Map map) throws SQLException { boolean[] booleanArray; int i; long[] longArray; int j; BigDecimal[] bdArray; int k; float[] floatArray; int m; double[] doubleArray; int n; Date[] dateArray; int i1; Time[] timeArray;
/*     */     int i2;
/*     */     Timestamp[] timestampArray;
/*     */     int i3;
/*     */     BaseStatement stat, baseStatement1;
/* 286 */     Object array = getArrayImpl(index, count, map);
/* 287 */     Vector rows = new Vector();
/* 288 */     Field[] fields = new Field[2];
/* 289 */     fields[0] = new Field("INDEX", 21, 2);
/* 290 */     switch (getBaseType()) {
/*     */       
/*     */       case -7:
/* 293 */         booleanArray = (boolean[])array;
/* 294 */         fields[1] = new Field("VALUE", 16, 1);
/* 295 */         for (i = 0; i < booleanArray.length; i++) {
/*     */           
/* 297 */           byte[][] tuple = new byte[2][0];
/* 298 */           tuple[0] = this.conn.encodeString(Integer.toString((int)index + i));
/* 299 */           tuple[1] = this.conn.encodeString(booleanArray[i] ? "YES" : "NO");
/* 300 */           rows.addElement(tuple);
/*     */         } 
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
/* 404 */         stat = (BaseStatement)this.conn.createStatement(1004, 1007);
/* 405 */         return stat.createDriverResultSet(fields, rows);case 5: fields[1] = new Field("VALUE", 21, 2); fillIntegerResultSet(index, (int[])array, rows); baseStatement1 = (BaseStatement)this.conn.createStatement(1004, 1007); return baseStatement1.createDriverResultSet(fields, rows);case 4: fields[1] = new Field("VALUE", 23, 4); fillIntegerResultSet(index, (int[])array, rows); baseStatement1 = (BaseStatement)this.conn.createStatement(1004, 1007); return baseStatement1.createDriverResultSet(fields, rows);case -5: longArray = (long[])array; fields[1] = new Field("VALUE", 20, 8); for (j = 0; j < longArray.length; j++) { byte[][] tuple = new byte[2][0]; tuple[0] = this.conn.encodeString(Integer.toString((int)index + j)); tuple[1] = this.conn.encodeString(Long.toString(longArray[j])); rows.addElement(tuple); }  baseStatement1 = (BaseStatement)this.conn.createStatement(1004, 1007); return baseStatement1.createDriverResultSet(fields, rows);case 2: bdArray = (BigDecimal[])array; fields[1] = new Field("VALUE", 1700, -1); for (k = 0; k < bdArray.length; k++) { byte[][] tuple = new byte[2][0]; tuple[0] = this.conn.encodeString(Integer.toString((int)index + k)); tuple[1] = this.conn.encodeString(bdArray[k].toString()); rows.addElement(tuple); }  baseStatement1 = (BaseStatement)this.conn.createStatement(1004, 1007); return baseStatement1.createDriverResultSet(fields, rows);case 7: floatArray = (float[])array; fields[1] = new Field("VALUE", 700, 4); for (m = 0; m < floatArray.length; m++) { byte[][] tuple = new byte[2][0]; tuple[0] = this.conn.encodeString(Integer.toString((int)index + m)); tuple[1] = this.conn.encodeString(Float.toString(floatArray[m])); rows.addElement(tuple); }  baseStatement1 = (BaseStatement)this.conn.createStatement(1004, 1007); return baseStatement1.createDriverResultSet(fields, rows);case 8: doubleArray = (double[])array; fields[1] = new Field("VALUE", 701, 8); for (n = 0; n < doubleArray.length; n++) { byte[][] tuple = new byte[2][0]; tuple[0] = this.conn.encodeString(Integer.toString((int)index + n)); tuple[1] = this.conn.encodeString(Double.toString(doubleArray[n])); rows.addElement(tuple); }  baseStatement1 = (BaseStatement)this.conn.createStatement(1004, 1007); return baseStatement1.createDriverResultSet(fields, rows);case 1: fields[1] = new Field("VALUE", 1042, 1); fillStringResultSet(index, (String[])array, rows); baseStatement1 = (BaseStatement)this.conn.createStatement(1004, 1007); return baseStatement1.createDriverResultSet(fields, rows);case 12: fields[1] = new Field("VALUE", 1043, -1); fillStringResultSet(index, (String[])array, rows); baseStatement1 = (BaseStatement)this.conn.createStatement(1004, 1007); return baseStatement1.createDriverResultSet(fields, rows);case 91: dateArray = (Date[])array; fields[1] = new Field("VALUE", 1082, 4); for (i1 = 0; i1 < dateArray.length; i1++) { byte[][] tuple = new byte[2][0]; tuple[0] = this.conn.encodeString(Integer.toString((int)index + i1)); tuple[1] = this.conn.encodeString(this.conn.getTimestampUtils().toString((Calendar)null, dateArray[i1])); rows.addElement(tuple); }  baseStatement1 = (BaseStatement)this.conn.createStatement(1004, 1007); return baseStatement1.createDriverResultSet(fields, rows);case 92: timeArray = (Time[])array; fields[1] = new Field("VALUE", 1083, 8); for (i2 = 0; i2 < timeArray.length; i2++) { byte[][] tuple = new byte[2][0]; tuple[0] = this.conn.encodeString(Integer.toString((int)index + i2)); tuple[1] = this.conn.encodeString(this.conn.getTimestampUtils().toString((Calendar)null, timeArray[i2])); rows.addElement(tuple); }  baseStatement1 = (BaseStatement)this.conn.createStatement(1004, 1007); return baseStatement1.createDriverResultSet(fields, rows);case 93: timestampArray = (Timestamp[])array; fields[1] = new Field("VALUE", 1184, 8); for (i3 = 0; i3 < timestampArray.length; i3++) { byte[][] arrayOfByte = new byte[2][0]; arrayOfByte[0] = this.conn.encodeString(Integer.toString((int)index + i3)); arrayOfByte[1] = this.conn.encodeString(this.conn.getTimestampUtils().toString((Calendar)null, timestampArray[i3])); rows.addElement(arrayOfByte); }  baseStatement1 = (BaseStatement)this.conn.createStatement(1004, 1007); return baseStatement1.createDriverResultSet(fields, rows);
/*     */     } 
/*     */     if (Driver.logDebug)
/*     */       Driver.debug("getResultSetImpl(long,int,Map) with " + getBaseTypeName()); 
/*     */     throw Driver.notImplemented(getClass(), "getResultSetImpl(long,int,Map)"); } public String toString() {
/* 410 */     return this.rawString;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc2\AbstractJdbc2Array.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */