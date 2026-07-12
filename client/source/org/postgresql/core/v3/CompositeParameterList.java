/*     */ package org.postgresql.core.v3;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.sql.SQLException;
/*     */ import org.postgresql.core.ParameterList;
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
/*     */ class CompositeParameterList
/*     */   implements V3ParameterList
/*     */ {
/*     */   private final int total;
/*     */   private final SimpleParameterList[] subparams;
/*     */   private final int[] offsets;
/*     */   
/*     */   CompositeParameterList(SimpleParameterList[] subparams, int[] offsets) {
/*  30 */     this.subparams = subparams;
/*  31 */     this.offsets = offsets;
/*  32 */     this.total = offsets[offsets.length - 1] + subparams[offsets.length - 1].getInParameterCount();
/*     */   }
/*     */   
/*     */   private final int findSubParam(int index) throws SQLException {
/*  36 */     if (index < 1 || index > this.total) {
/*  37 */       throw new PSQLException(GT.tr("The column index is out of range: {0}, number of columns: {1}.", new Object[] { new Integer(index), new Integer(this.total) }), PSQLState.INVALID_PARAMETER_VALUE);
/*     */     }
/*  39 */     for (int i = this.offsets.length - 1; i >= 0; i--) {
/*  40 */       if (this.offsets[i] < index)
/*  41 */         return i; 
/*     */     } 
/*  43 */     throw new IllegalArgumentException("I am confused; can't find a subparam for index " + index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerOutParameter(int index, int sqlType) {}
/*     */   
/*     */   public int getDirection(int i) {
/*  50 */     return 0;
/*     */   }
/*     */   public int getParameterCount() {
/*  53 */     return this.total;
/*     */   }
/*     */   public int getInParameterCount() {
/*  56 */     return this.total;
/*     */   }
/*     */   
/*     */   public int[] getTypeOIDs() {
/*  60 */     int[] oids = new int[this.total];
/*  61 */     for (int i = 0; i < this.offsets.length; i++) {
/*  62 */       int[] subOids = this.subparams[i].getTypeOIDs();
/*  63 */       System.arraycopy(subOids, 0, oids, this.offsets[i], subOids.length);
/*     */     } 
/*  65 */     return oids;
/*     */   }
/*     */   
/*     */   public void setIntParameter(int index, int value) throws SQLException {
/*  69 */     int sub = findSubParam(index);
/*  70 */     this.subparams[sub].setIntParameter(index - this.offsets[sub], value);
/*     */   }
/*     */   
/*     */   public void setLiteralParameter(int index, String value, int oid) throws SQLException {
/*  74 */     int sub = findSubParam(index);
/*  75 */     this.subparams[sub].setStringParameter(index - this.offsets[sub], value, oid);
/*     */   }
/*     */   
/*     */   public void setStringParameter(int index, String value, int oid) throws SQLException {
/*  79 */     int sub = findSubParam(index);
/*  80 */     this.subparams[sub].setStringParameter(index - this.offsets[sub], value, oid);
/*     */   }
/*     */   
/*     */   public void setBytea(int index, byte[] data, int offset, int length) throws SQLException {
/*  84 */     int sub = findSubParam(index);
/*  85 */     this.subparams[sub].setBytea(index - this.offsets[sub], data, offset, length);
/*     */   }
/*     */   
/*     */   public void setBytea(int index, InputStream stream, int length) throws SQLException {
/*  89 */     int sub = findSubParam(index);
/*  90 */     this.subparams[sub].setBytea(index - this.offsets[sub], stream, length);
/*     */   }
/*     */   
/*     */   public void setNull(int index, int oid) throws SQLException {
/*  94 */     int sub = findSubParam(index);
/*  95 */     this.subparams[sub].setNull(index - this.offsets[sub], oid);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(int index) {
/*     */     try {
/* 101 */       int sub = findSubParam(index);
/* 102 */       return this.subparams[sub].toString(index - this.offsets[sub]);
/*     */     }
/*     */     catch (SQLException e) {
/*     */       
/* 106 */       throw new IllegalStateException(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public ParameterList copy() {
/* 111 */     SimpleParameterList[] copySub = new SimpleParameterList[this.subparams.length];
/* 112 */     for (int sub = 0; sub < this.subparams.length; sub++) {
/* 113 */       copySub[sub] = (SimpleParameterList)this.subparams[sub].copy();
/*     */     }
/* 115 */     return new CompositeParameterList(copySub, this.offsets);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 119 */     for (int sub = 0; sub < this.subparams.length; sub++)
/*     */     {
/* 121 */       this.subparams[sub].clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public SimpleParameterList[] getSubparams() {
/* 126 */     return this.subparams;
/*     */   }
/*     */   
/*     */   public void checkAllParametersSet() throws SQLException {
/* 130 */     for (int sub = 0; sub < this.subparams.length; sub++)
/* 131 */       this.subparams[sub].checkAllParametersSet(); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\v3\CompositeParameterList.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */