/*    */ package com.ankamagames.framework.kernel.core.common.collections.iterators;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MergedIterator<T>
/*    */   implements Iterator<T>
/*    */ {
/* 14 */   private List<Iterator<T>> m_iterators = new ArrayList<Iterator<T>>(2);
/* 15 */   private Iterator<T> lastIterator = null;
/* 16 */   private int lastIteratorIndex = 0;
/*    */   public MergedIterator() {}
/*    */   
/*    */   public MergedIterator(Iterator... its) {
/*    */     byte b;
/*    */     int i;
/*    */     Iterator[] arrayOfIterator;
/* 23 */     for (i = (arrayOfIterator = its).length, b = 0; b < i; ) { Iterator<? extends T> it = arrayOfIterator[b];
/* 24 */       this.m_iterators.add(it); b++; }
/*    */     
/* 26 */     if (this.lastIterator == null && its.length > 0)
/* 27 */       this.lastIterator = its[0]; 
/*    */   }
/*    */   
/* 30 */   public MergedIterator(Iterator<T> it) { this.m_iterators.add(it); this.lastIterator = it; } public void merge(Iterator<T> it) {
/* 31 */     this.m_iterators.add(it); if (this.lastIterator == null) this.lastIterator = it; 
/*    */   }
/*    */   public boolean hasNext() {
/* 34 */     return (this.lastIterator != null && this.lastIterator.hasNext());
/*    */   }
/*    */   public T next() {
/* 37 */     T o = this.lastIterator.next();
/* 38 */     if (!this.lastIterator.hasNext()) {
/* 39 */       this.lastIteratorIndex++;
/* 40 */       if (this.lastIteratorIndex >= this.m_iterators.size()) {
/* 41 */         this.lastIterator = null;
/*    */       } else {
/* 43 */         this.lastIterator = this.m_iterators.get(this.lastIteratorIndex);
/*    */       } 
/* 45 */     }  return o;
/*    */   }
/*    */   
/*    */   public void remove() {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\collections\iterators\MergedIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */