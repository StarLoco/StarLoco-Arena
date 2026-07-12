/*     */ package org.postgresql.core.v3;
/*     */ 
/*     */ import java.lang.ref.PhantomReference;
/*     */ import org.postgresql.core.ParameterList;
/*     */ import org.postgresql.core.Utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SimpleQuery
/*     */   implements V3Query
/*     */ {
/*     */   private final String[] fragments;
/*     */   private String statementName;
/*     */   private byte[] encodedStatementName;
/*     */   private PhantomReference cleanupRef;
/*     */   private int[] preparedTypes;
/*     */   
/*     */   SimpleQuery(String[] fragments) {
/*  26 */     this.fragments = fragments;
/*     */   }
/*     */   
/*     */   public ParameterList createParameterList() {
/*  30 */     if (this.fragments.length == 1) {
/*  31 */       return NO_PARAMETERS;
/*     */     }
/*  33 */     return new SimpleParameterList(this.fragments.length - 1);
/*     */   }
/*     */   
/*     */   public String toString(ParameterList parameters) {
/*  37 */     StringBuffer sbuf = new StringBuffer(this.fragments[0]);
/*  38 */     for (int i = 1; i < this.fragments.length; i++) {
/*     */       
/*  40 */       if (parameters == null) {
/*  41 */         sbuf.append('?');
/*     */       } else {
/*  43 */         sbuf.append(parameters.toString(i));
/*  44 */       }  sbuf.append(this.fragments[i]);
/*     */     } 
/*  46 */     return sbuf.toString();
/*     */   }
/*     */   
/*     */   public String toString() {
/*  50 */     return toString(null);
/*     */   }
/*     */   
/*     */   public void close() {
/*  54 */     unprepare();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleQuery[] getSubqueries() {
/*  62 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String[] getFragments() {
/*  70 */     return this.fragments;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setStatementName(String statementName) {
/*  76 */     this.statementName = statementName;
/*  77 */     this.encodedStatementName = Utils.encodeUTF8(statementName);
/*     */   }
/*     */   
/*     */   void setStatementTypes(int[] paramTypes) {
/*  81 */     this.preparedTypes = paramTypes;
/*     */   }
/*     */   
/*     */   String getStatementName() {
/*  85 */     return this.statementName;
/*     */   }
/*     */   
/*     */   boolean isPreparedFor(int[] paramTypes) {
/*  89 */     if (this.statementName == null) {
/*  90 */       return false;
/*     */     }
/*     */     
/*  93 */     for (int i = 0; i < paramTypes.length; i++) {
/*  94 */       if (paramTypes[i] != this.preparedTypes[i])
/*  95 */         return false; 
/*     */     } 
/*  97 */     return true;
/*     */   }
/*     */   
/*     */   byte[] getEncodedStatementName() {
/* 101 */     return this.encodedStatementName;
/*     */   }
/*     */   
/*     */   void setCleanupRef(PhantomReference cleanupRef) {
/* 105 */     this.cleanupRef = cleanupRef;
/*     */   }
/*     */   
/*     */   void unprepare() {
/* 109 */     if (this.cleanupRef != null) {
/*     */       
/* 111 */       this.cleanupRef.clear();
/* 112 */       this.cleanupRef.enqueue();
/* 113 */       this.cleanupRef = null;
/*     */     } 
/*     */     
/* 116 */     this.statementName = null;
/* 117 */     this.encodedStatementName = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   static final SimpleParameterList NO_PARAMETERS = new SimpleParameterList(0);
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\v3\SimpleQuery.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */