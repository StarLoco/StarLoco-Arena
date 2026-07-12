/*     */ package org.jdom.filter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class NegateFilter
/*     */   extends AbstractFilter
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: NegateFilter.java,v $ $Revision: 1.3 $ $Date: 2004/02/06 09:28:31 $";
/*     */   private Filter filter;
/*     */   
/*     */   public NegateFilter(Filter filter) {
/*  80 */     this.filter = filter;
/*     */   }
/*     */   
/*     */   public boolean matches(Object obj) {
/*  84 */     return this.filter.matches(obj) ^ true;
/*     */   }
/*     */   
/*     */   public Filter negate() {
/*  88 */     return this.filter;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/*  92 */     if (this == obj) {
/*  93 */       return true;
/*     */     }
/*     */     
/*  96 */     if (obj instanceof NegateFilter) {
/*  97 */       return this.filter.equals(((NegateFilter)obj).filter);
/*     */     }
/*  99 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 103 */     return this.filter.hashCode() ^ 0xFFFFFFFF;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 107 */     return (new StringBuffer(64))
/* 108 */       .append("[NegateFilter: ")
/* 109 */       .append(this.filter.toString())
/* 110 */       .append("]")
/* 111 */       .toString();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\filter\NegateFilter.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */