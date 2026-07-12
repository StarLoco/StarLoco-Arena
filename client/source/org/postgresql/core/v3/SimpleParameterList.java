/*     */ package org.postgresql.core.v3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import org.postgresql.core.PGStream;
/*     */ import org.postgresql.core.ParameterList;
/*     */ import org.postgresql.core.Utils;
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
/*     */ class SimpleParameterList
/*     */   implements V3ParameterList
/*     */ {
/*     */   private static final int IN = 1;
/*     */   private static final int OUT = 2;
/*     */   private static final int INOUT = 3;
/*     */   private final Object[] paramValues;
/*     */   private final int[] paramTypes;
/*     */   private final int[] direction;
/*     */   private final byte[][] encoded;
/*     */   
/*     */   SimpleParameterList(int paramCount) {
/*  40 */     this.paramValues = new Object[paramCount];
/*  41 */     this.paramTypes = new int[paramCount];
/*  42 */     this.encoded = new byte[paramCount][];
/*  43 */     this.direction = new int[paramCount];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerOutParameter(int index, int sqlType) throws SQLException {
/*  49 */     if (index < 1 || index > this.paramValues.length) {
/*  50 */       throw new PSQLException(GT.tr("The column index is out of range: {0}, number of columns: {1}.", new Object[] { new Integer(index), new Integer(this.paramValues.length) }), PSQLState.INVALID_PARAMETER_VALUE);
/*     */     }
/*  52 */     this.direction[index - 1] = this.direction[index - 1] | 0x2;
/*     */   }
/*     */   
/*     */   private void bind(int index, Object value, int oid) throws SQLException {
/*  56 */     if (index < 1 || index > this.paramValues.length) {
/*  57 */       throw new PSQLException(GT.tr("The column index is out of range: {0}, number of columns: {1}.", new Object[] { new Integer(index), new Integer(this.paramValues.length) }), PSQLState.INVALID_PARAMETER_VALUE);
/*     */     }
/*  59 */     index--;
/*     */     
/*  61 */     this.encoded[index] = null;
/*  62 */     this.paramValues[index] = value;
/*  63 */     this.direction[index] = this.direction[index] | 0x1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     if (oid == 0 && this.paramTypes[index] != 0) {
/*     */       return;
/*     */     }
/*  71 */     this.paramTypes[index] = oid;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getParameterCount() {
/*  76 */     return this.paramValues.length;
/*     */   }
/*     */   
/*     */   public int getInParameterCount() {
/*  80 */     int count = 0;
/*  81 */     for (int i = 0; i < this.paramTypes.length; i++) {
/*     */       
/*  83 */       if (this.direction[i] != 2)
/*     */       {
/*  85 */         count++;
/*     */       }
/*     */     } 
/*  88 */     return count;
/*     */   }
/*     */   
/*     */   public void setIntParameter(int index, int value) throws SQLException {
/*  92 */     byte[] data = new byte[4];
/*  93 */     data[3] = (byte)value;
/*  94 */     data[2] = (byte)(value >> 8);
/*  95 */     data[1] = (byte)(value >> 16);
/*  96 */     data[0] = (byte)(value >> 24);
/*  97 */     bind(index, data, 23);
/*     */   }
/*     */   
/*     */   public void setLiteralParameter(int index, String value, int oid) throws SQLException {
/* 101 */     bind(index, value, oid);
/*     */   }
/*     */   
/*     */   public void setStringParameter(int index, String value, int oid) throws SQLException {
/* 105 */     bind(index, value, oid);
/*     */   }
/*     */   
/*     */   public void setBytea(int index, byte[] data, int offset, int length) throws SQLException {
/* 109 */     bind(index, new StreamWrapper(data, offset, length), 17);
/*     */   }
/*     */   
/*     */   public void setBytea(int index, InputStream stream, int length) throws SQLException {
/* 113 */     bind(index, new StreamWrapper(stream, length), 17);
/*     */   }
/*     */   
/*     */   public void setNull(int index, int oid) throws SQLException {
/* 117 */     bind(index, NULL_OBJECT, oid);
/*     */   }
/*     */   
/*     */   public String toString(int index) {
/* 121 */     index--;
/* 122 */     if (this.paramValues[index] == null)
/* 123 */       return "?"; 
/* 124 */     if (this.paramValues[index] == NULL_OBJECT) {
/* 125 */       return "NULL";
/*     */     }
/* 127 */     return this.paramValues[index].toString();
/*     */   }
/*     */   
/*     */   public void checkAllParametersSet() throws SQLException {
/* 131 */     for (int i = 0; i < this.paramTypes.length; i++) {
/*     */       
/* 133 */       if (this.direction[i] != 2 && this.paramValues[i] == null) {
/* 134 */         throw new PSQLException(GT.tr("No value specified for parameter {0}.", new Integer(i + 1)), PSQLState.INVALID_PARAMETER_VALUE);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void streamBytea(PGStream pgStream, StreamWrapper wrapper) throws IOException {
/* 143 */     byte[] rawData = wrapper.getBytes();
/* 144 */     if (rawData != null) {
/*     */       
/* 146 */       pgStream.Send(rawData, wrapper.getOffset(), wrapper.getLength());
/*     */       
/*     */       return;
/*     */     } 
/* 150 */     pgStream.SendStream(wrapper.getStream(), wrapper.getLength());
/*     */   }
/*     */   
/*     */   public int[] getTypeOIDs() {
/* 154 */     return this.paramTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getTypeOID(int index) {
/* 162 */     if (this.direction[index - 1] == 2) {
/*     */       
/* 164 */       this.paramTypes[index - 1] = 2278;
/* 165 */       this.paramValues[index - 1] = "null";
/*     */     } 
/*     */     
/* 168 */     return this.paramTypes[index - 1];
/*     */   }
/*     */   
/*     */   boolean hasUnresolvedTypes() {
/* 172 */     for (int i = 0; i < this.paramTypes.length; i++) {
/* 173 */       if (this.paramTypes[i] == 0)
/* 174 */         return true; 
/*     */     } 
/* 176 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   void setResolvedType(int index, int oid) {
/* 181 */     if (this.paramTypes[index - 1] == 0) {
/* 182 */       this.paramTypes[index - 1] = oid;
/* 183 */     } else if (this.paramTypes[index - 1] != oid) {
/* 184 */       throw new IllegalArgumentException("Can't change resolved type for param: " + index + " from " + this.paramTypes[index] + " to " + oid);
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isNull(int index) {
/* 189 */     return (this.paramValues[index - 1] == NULL_OBJECT);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isBinary(int index) {
/* 194 */     return this.paramValues[index - 1] instanceof StreamWrapper;
/*     */   }
/*     */   
/*     */   int getV3Length(int index) {
/* 198 */     index--;
/*     */ 
/*     */     
/* 201 */     if (this.paramValues[index] == NULL_OBJECT) {
/* 202 */       throw new IllegalArgumentException("can't getV3Length() on a null parameter");
/*     */     }
/*     */     
/* 205 */     if (this.paramValues[index] instanceof byte[]) {
/* 206 */       return ((byte[])this.paramValues[index]).length;
/*     */     }
/*     */     
/* 209 */     if (this.paramValues[index] instanceof StreamWrapper) {
/* 210 */       return ((StreamWrapper)this.paramValues[index]).getLength();
/*     */     }
/*     */     
/* 213 */     if (this.encoded[index] == null)
/*     */     {
/*     */       
/* 216 */       this.encoded[index] = Utils.encodeUTF8(this.paramValues[index].toString());
/*     */     }
/*     */     
/* 219 */     return (this.encoded[index]).length;
/*     */   }
/*     */   
/*     */   void writeV3Value(int index, PGStream pgStream) throws IOException {
/* 223 */     index--;
/*     */ 
/*     */     
/* 226 */     if (this.paramValues[index] == NULL_OBJECT) {
/* 227 */       throw new IllegalArgumentException("can't writeV3Value() on a null parameter");
/*     */     }
/*     */     
/* 230 */     if (this.paramValues[index] instanceof byte[]) {
/*     */       
/* 232 */       pgStream.Send((byte[])this.paramValues[index]);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 237 */     if (this.paramValues[index] instanceof StreamWrapper) {
/*     */       
/* 239 */       streamBytea(pgStream, (StreamWrapper)this.paramValues[index]);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 244 */     if (this.encoded[index] == null)
/* 245 */       this.encoded[index] = Utils.encodeUTF8((String)this.paramValues[index]); 
/* 246 */     pgStream.Send(this.encoded[index]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterList copy() {
/* 252 */     SimpleParameterList newCopy = new SimpleParameterList(this.paramValues.length);
/* 253 */     System.arraycopy(this.paramValues, 0, newCopy.paramValues, 0, this.paramValues.length);
/* 254 */     System.arraycopy(this.paramTypes, 0, newCopy.paramTypes, 0, this.paramTypes.length);
/* 255 */     System.arraycopy(this.direction, 0, newCopy.direction, 0, this.direction.length);
/* 256 */     return newCopy;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 260 */     Arrays.fill(this.paramValues, (Object)null);
/* 261 */     Arrays.fill(this.paramTypes, 0);
/* 262 */     Arrays.fill((Object[])this.encoded, (Object)null);
/* 263 */     Arrays.fill(this.direction, 0);
/*     */   }
/*     */   public SimpleParameterList[] getSubparams() {
/* 266 */     return null;
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
/* 278 */   private static final Object NULL_OBJECT = new Object();
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\v3\SimpleParameterList.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */