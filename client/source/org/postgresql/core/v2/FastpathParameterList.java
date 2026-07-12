/*     */ package org.postgresql.core.v2;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import org.postgresql.core.PGStream;
/*     */ import org.postgresql.core.ParameterList;
/*     */ import org.postgresql.util.GT;
/*     */ import org.postgresql.util.PSQLException;
/*     */ import org.postgresql.util.PSQLState;
/*     */ import org.postgresql.util.StreamWrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FastpathParameterList
/*     */   implements ParameterList
/*     */ {
/*     */   private final Object[] paramValues;
/*     */   
/*     */   FastpathParameterList(int paramCount) {
/*  34 */     this.paramValues = new Object[paramCount];
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerOutParameter(int index, int sqlType) {}
/*     */   
/*     */   public int getInParameterCount() {
/*  41 */     return this.paramValues.length;
/*     */   }
/*     */   public void registerOutParameter(int index, int sqlType, int precision) {}
/*     */   public int getParameterCount() {
/*  45 */     return this.paramValues.length;
/*     */   }
/*     */   public int[] getTypeOIDs() {
/*  48 */     return null;
/*     */   }
/*     */   
/*     */   public void setIntParameter(int index, int value) throws SQLException {
/*  52 */     if (index < 1 || index > this.paramValues.length) {
/*  53 */       throw new PSQLException(GT.tr("The column index is out of range: {0}, number of columns: {1}.", new Object[] { new Integer(index), new Integer(this.paramValues.length) }), PSQLState.INVALID_PARAMETER_VALUE);
/*     */     }
/*  55 */     byte[] data = new byte[4];
/*  56 */     data[3] = (byte)value;
/*  57 */     data[2] = (byte)(value >> 8);
/*  58 */     data[1] = (byte)(value >> 16);
/*  59 */     data[0] = (byte)(value >> 24);
/*     */     
/*  61 */     this.paramValues[index - 1] = data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLiteralParameter(int index, String value, int oid) throws SQLException {
/*  66 */     throw new IllegalArgumentException("can't setLiteralParameter() on a fastpath parameter");
/*     */   }
/*     */   
/*     */   public void setStringParameter(int index, String value, int oid) throws SQLException {
/*  70 */     this.paramValues[index - 1] = value;
/*     */   }
/*     */   
/*     */   public void setBytea(int index, byte[] data, int offset, int length) throws SQLException {
/*  74 */     if (index < 1 || index > this.paramValues.length) {
/*  75 */       throw new PSQLException(GT.tr("The column index is out of range: {0}, number of columns: {1}.", new Object[] { new Integer(index), new Integer(this.paramValues.length) }), PSQLState.INVALID_PARAMETER_VALUE);
/*     */     }
/*  77 */     this.paramValues[index - 1] = new StreamWrapper(data, offset, length);
/*     */   }
/*     */   
/*     */   public void setBytea(int index, InputStream stream, int length) throws SQLException {
/*  81 */     if (index < 1 || index > this.paramValues.length) {
/*  82 */       throw new PSQLException(GT.tr("The column index is out of range: {0}, number of columns: {1}.", new Object[] { new Integer(index), new Integer(this.paramValues.length) }), PSQLState.INVALID_PARAMETER_VALUE);
/*     */     }
/*  84 */     this.paramValues[index - 1] = new StreamWrapper(stream, length);
/*     */   }
/*     */   
/*     */   public void setNull(int index, int oid) throws SQLException {
/*  88 */     throw new IllegalArgumentException("can't setNull() on a v2 fastpath parameter");
/*     */   }
/*     */   
/*     */   public String toString(int index) {
/*  92 */     if (index < 1 || index > this.paramValues.length) {
/*  93 */       throw new IllegalArgumentException("parameter " + index + " out of range");
/*     */     }
/*  95 */     return "<fastpath parameter>";
/*     */   }
/*     */   
/*     */   private void copyStream(PGStream pgStream, StreamWrapper wrapper) throws IOException {
/*  99 */     byte[] rawData = wrapper.getBytes();
/* 100 */     if (rawData != null) {
/*     */       
/* 102 */       pgStream.Send(rawData, wrapper.getOffset(), wrapper.getLength());
/*     */       
/*     */       return;
/*     */     } 
/* 106 */     pgStream.SendStream(wrapper.getStream(), wrapper.getLength());
/*     */   }
/*     */   
/*     */   void writeV2FastpathValue(int index, PGStream pgStream) throws IOException {
/* 110 */     index--;
/*     */     
/* 112 */     if (this.paramValues[index] instanceof StreamWrapper) {
/*     */       
/* 114 */       StreamWrapper wrapper = (StreamWrapper)this.paramValues[index];
/* 115 */       pgStream.SendInteger4(wrapper.getLength());
/* 116 */       copyStream(pgStream, wrapper);
/*     */     }
/* 118 */     else if (this.paramValues[index] instanceof byte[]) {
/*     */       
/* 120 */       byte[] data = (byte[])this.paramValues[index];
/* 121 */       pgStream.SendInteger4(data.length);
/* 122 */       pgStream.Send(data);
/*     */     }
/* 124 */     else if (this.paramValues[index] instanceof String) {
/*     */       
/* 126 */       byte[] data = pgStream.getEncoding().encode((String)this.paramValues[index]);
/* 127 */       pgStream.SendInteger4(data.length);
/* 128 */       pgStream.Send(data);
/*     */     }
/*     */     else {
/*     */       
/* 132 */       throw new IllegalArgumentException("don't know how to stream parameter " + index);
/*     */     } 
/*     */   }
/*     */   
/*     */   void checkAllParametersSet() throws SQLException {
/* 137 */     for (int i = 0; i < this.paramValues.length; i++) {
/*     */       
/* 139 */       if (this.paramValues[i] == null)
/* 140 */         throw new PSQLException(GT.tr("No value specified for parameter {0}.", new Integer(i + 1)), PSQLState.INVALID_PARAMETER_VALUE); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ParameterList copy() {
/* 145 */     FastpathParameterList newCopy = new FastpathParameterList(this.paramValues.length);
/* 146 */     System.arraycopy(this.paramValues, 0, newCopy.paramValues, 0, this.paramValues.length);
/* 147 */     return newCopy;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 151 */     Arrays.fill(this.paramValues, (Object)null);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\v2\FastpathParameterList.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */