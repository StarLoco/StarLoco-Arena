/*     */ package org.postgresql.core.v2;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Writer;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
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
/*     */ class SimpleParameterList
/*     */   implements ParameterList
/*     */ {
/*     */   private final Object[] paramValues;
/*     */   
/*     */   SimpleParameterList(int paramCount) {
/*  32 */     this.paramValues = new Object[paramCount];
/*     */   }
/*     */   
/*     */   public void registerOutParameter(int index, int sqlType) {}
/*     */   
/*     */   public int getInParameterCount() {
/*  38 */     return this.paramValues.length;
/*     */   }
/*     */   public void registerOutParameter(int index, int sqlType, int precision) {}
/*     */   public int getParameterCount() {
/*  42 */     return this.paramValues.length;
/*     */   }
/*     */   public int[] getTypeOIDs() {
/*  45 */     return null;
/*     */   }
/*     */   
/*     */   public void setIntParameter(int index, int value) throws SQLException {
/*  49 */     setLiteralParameter(index, "" + value, 23);
/*     */   }
/*     */   
/*     */   public void setLiteralParameter(int index, String value, int oid) throws SQLException {
/*  53 */     if (index < 1 || index > this.paramValues.length) {
/*  54 */       throw new PSQLException(GT.tr("The column index is out of range: {0}, number of columns: {1}.", new Object[] { new Integer(index), new Integer(this.paramValues.length) }), PSQLState.INVALID_PARAMETER_VALUE);
/*     */     }
/*  56 */     this.paramValues[index - 1] = value;
/*     */   }
/*     */   
/*     */   public void setStringParameter(int index, String value, int oid) throws SQLException {
/*  60 */     StringBuffer sbuf = new StringBuffer(2 + value.length() * 11 / 10);
/*  61 */     sbuf.append('\'');
/*  62 */     for (int i = 0; i < value.length(); i++) {
/*     */       
/*  64 */       char ch = value.charAt(i);
/*  65 */       if (ch == '\000')
/*  66 */         throw new PSQLException(GT.tr("Zero bytes may not occur in string parameters."), PSQLState.INVALID_PARAMETER_VALUE); 
/*  67 */       if (ch == '\\' || ch == '\'')
/*  68 */         sbuf.append('\\'); 
/*  69 */       sbuf.append(ch);
/*     */     } 
/*  71 */     sbuf.append('\'');
/*     */     
/*  73 */     setLiteralParameter(index, sbuf.toString(), oid);
/*     */   }
/*     */   
/*     */   public void setBytea(int index, byte[] data, int offset, int length) throws SQLException {
/*  77 */     if (index < 1 || index > this.paramValues.length) {
/*  78 */       throw new PSQLException(GT.tr("The column index is out of range: {0}, number of columns: {1}.", new Object[] { new Integer(index), new Integer(this.paramValues.length) }), PSQLState.INVALID_PARAMETER_VALUE);
/*     */     }
/*  80 */     this.paramValues[index - 1] = new StreamWrapper(data, offset, length);
/*     */   }
/*     */   
/*     */   public void setBytea(int index, InputStream stream, int length) throws SQLException {
/*  84 */     if (index < 1 || index > this.paramValues.length) {
/*  85 */       throw new PSQLException(GT.tr("The column index is out of range: {0}, number of columns: {1}.", new Object[] { new Integer(index), new Integer(this.paramValues.length) }), PSQLState.INVALID_PARAMETER_VALUE);
/*     */     }
/*  87 */     this.paramValues[index - 1] = new StreamWrapper(stream, length);
/*     */   }
/*     */   
/*     */   public void setNull(int index, int oid) throws SQLException {
/*  91 */     if (index < 1 || index > this.paramValues.length) {
/*  92 */       throw new PSQLException(GT.tr("The column index is out of range: {0}, number of columns: {1}.", new Object[] { new Integer(index), new Integer(this.paramValues.length) }), PSQLState.INVALID_PARAMETER_VALUE);
/*     */     }
/*  94 */     this.paramValues[index - 1] = NULL_OBJECT;
/*     */   }
/*     */   
/*     */   public String toString(int index) {
/*  98 */     if (index < 1 || index > this.paramValues.length) {
/*  99 */       throw new IllegalArgumentException("Parameter index " + index + " out of range");
/*     */     }
/* 101 */     if (this.paramValues[index - 1] == null)
/* 102 */       return "?"; 
/* 103 */     if (this.paramValues[index - 1] == NULL_OBJECT) {
/* 104 */       return "NULL";
/*     */     }
/* 106 */     return this.paramValues[index - 1].toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void streamBytea(StreamWrapper param, Writer encodingWriter) throws IOException {
/* 117 */     InputStream stream = param.getStream();
/* 118 */     char[] buffer = { '\\', '\\', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE };
/*     */     
/* 120 */     encodingWriter.write(39);
/* 121 */     for (int remaining = param.getLength(); remaining > 0; remaining--) {
/*     */       
/* 123 */       int nextByte = stream.read();
/*     */       
/* 125 */       buffer[2] = (char)(48 + (nextByte >> 6 & 0x3));
/* 126 */       buffer[3] = (char)(48 + (nextByte >> 3 & 0x7));
/* 127 */       buffer[4] = (char)(48 + (nextByte & 0x7));
/*     */       
/* 129 */       encodingWriter.write(buffer, 0, 5);
/*     */     } 
/*     */     
/* 132 */     encodingWriter.write(39);
/*     */   }
/*     */ 
/*     */   
/*     */   void writeV2Value(int index, Writer encodingWriter) throws IOException {
/* 137 */     if (this.paramValues[index - 1] instanceof StreamWrapper) {
/*     */       
/* 139 */       streamBytea((StreamWrapper)this.paramValues[index - 1], encodingWriter);
/*     */     }
/*     */     else {
/*     */       
/* 143 */       encodingWriter.write((String)this.paramValues[index - 1]);
/*     */     } 
/*     */   }
/*     */   
/*     */   void checkAllParametersSet() throws SQLException {
/* 148 */     for (int i = 0; i < this.paramValues.length; i++) {
/*     */       
/* 150 */       if (this.paramValues[i] == null)
/* 151 */         throw new PSQLException(GT.tr("No value specified for parameter {0}.", new Integer(i + 1)), PSQLState.INVALID_PARAMETER_VALUE); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ParameterList copy() {
/* 156 */     SimpleParameterList newCopy = new SimpleParameterList(this.paramValues.length);
/* 157 */     System.arraycopy(this.paramValues, 0, newCopy.paramValues, 0, this.paramValues.length);
/* 158 */     return newCopy;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 162 */     Arrays.fill(this.paramValues, (Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   private static final String NULL_OBJECT = new String("NULL");
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\v2\SimpleParameterList.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */