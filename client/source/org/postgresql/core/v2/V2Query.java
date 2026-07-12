/*     */ package org.postgresql.core.v2;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import org.postgresql.core.ParameterList;
/*     */ import org.postgresql.core.Query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class V2Query
/*     */   implements Query
/*     */ {
/*     */   V2Query(String query, boolean withParameters) {
/*  21 */     if (!withParameters) {
/*     */       
/*  23 */       this.fragments = new String[] { query };
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  29 */     Vector v = new Vector();
/*  30 */     int lastParmEnd = 0;
/*     */     
/*  32 */     boolean inSingleQuotes = false;
/*  33 */     boolean inDoubleQuotes = false;
/*     */     
/*  35 */     for (int i = 0; i < query.length(); i++) {
/*     */       
/*  37 */       char c = query.charAt(i);
/*     */       
/*  39 */       switch (c) {
/*     */         
/*     */         case '\\':
/*  42 */           if (inSingleQuotes) {
/*  43 */             i++;
/*     */           }
/*     */           break;
/*     */         case '\'':
/*  47 */           inSingleQuotes = (!inDoubleQuotes && !inSingleQuotes);
/*     */           break;
/*     */         
/*     */         case '"':
/*  51 */           inDoubleQuotes = (!inSingleQuotes && !inDoubleQuotes);
/*     */           break;
/*     */         
/*     */         case '?':
/*  55 */           if (!inSingleQuotes && !inDoubleQuotes) {
/*     */             
/*  57 */             v.addElement(query.substring(lastParmEnd, i));
/*  58 */             lastParmEnd = i + 1;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/*  67 */     v.addElement(query.substring(lastParmEnd, query.length()));
/*     */     
/*  69 */     this.fragments = new String[v.size()];
/*  70 */     for (int j = 0; j < this.fragments.length; j++)
/*  71 */       this.fragments[j] = v.elementAt(j); 
/*     */   }
/*     */   
/*     */   public ParameterList createParameterList() {
/*  75 */     if (this.fragments.length == 1) {
/*  76 */       return NO_PARAMETERS;
/*     */     }
/*  78 */     return new SimpleParameterList(this.fragments.length - 1);
/*     */   }
/*     */   
/*     */   public String toString(ParameterList parameters) {
/*  82 */     StringBuffer sbuf = new StringBuffer(this.fragments[0]);
/*  83 */     for (int i = 1; i < this.fragments.length; i++) {
/*     */       
/*  85 */       if (parameters == null) {
/*  86 */         sbuf.append("?");
/*     */       } else {
/*  88 */         sbuf.append(parameters.toString(i));
/*  89 */       }  sbuf.append(this.fragments[i]);
/*     */     } 
/*  91 */     return sbuf.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */   
/*     */   String[] getFragments() {
/*  98 */     return this.fragments;
/*     */   }
/*     */   
/* 101 */   private static final ParameterList NO_PARAMETERS = new SimpleParameterList(0);
/*     */   private final String[] fragments;
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\v2\V2Query.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */