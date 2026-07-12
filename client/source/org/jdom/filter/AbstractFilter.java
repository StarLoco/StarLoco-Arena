/*    */ package org.jdom.filter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractFilter
/*    */   implements Filter
/*    */ {
/*    */   private static final String CVS_ID = "@(#) $RCSfile: AbstractFilter.java,v $ $Revision: 1.5 $ $Date: 2004/02/27 11:32:58 $";
/*    */   
/*    */   public Filter negate() {
/* 71 */     return new NegateFilter(this);
/*    */   }
/*    */   
/*    */   public Filter or(Filter filter) {
/* 75 */     return new OrFilter(this, filter);
/*    */   }
/*    */   
/*    */   public Filter and(Filter filter) {
/* 79 */     return new AndFilter(this, filter);
/*    */   }
/*    */   
/*    */   public abstract boolean matches(Object paramObject);
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\filter\AbstractFilter.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */