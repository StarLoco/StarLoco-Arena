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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class OrFilter
/*     */   extends AbstractFilter
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: OrFilter.java,v $ $Revision: 1.4 $ $Date: 2004/02/06 09:28:31 $";
/*     */   private Filter left;
/*     */   private Filter right;
/*     */   
/*     */   public OrFilter(Filter left, Filter right) {
/*  85 */     if (left == null || right == null) {
/*  86 */       throw new IllegalArgumentException("null filter not allowed");
/*     */     }
/*  88 */     this.left = left;
/*  89 */     this.right = right;
/*     */   }
/*     */   
/*     */   public boolean matches(Object obj) {
/*  93 */     return !(!this.left.matches(obj) && !this.right.matches(obj));
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/*  97 */     if (this == obj) {
/*  98 */       return true;
/*     */     }
/*     */     
/* 101 */     if (obj instanceof OrFilter) {
/* 102 */       OrFilter filter = (OrFilter)obj;
/* 103 */       if ((this.left.equals(filter.left) && this.right.equals(filter.right)) || (
/* 104 */         this.left.equals(filter.right) && this.right.equals(filter.left))) {
/* 105 */         return true;
/*     */       }
/*     */     } 
/* 108 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 112 */     return 31 * this.left.hashCode() + this.right.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 116 */     return (new StringBuffer(64))
/* 117 */       .append("[OrFilter: ")
/* 118 */       .append(this.left.toString())
/* 119 */       .append(",\n")
/* 120 */       .append("           ")
/* 121 */       .append(this.right.toString())
/* 122 */       .append("]")
/* 123 */       .toString();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\filter\OrFilter.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */