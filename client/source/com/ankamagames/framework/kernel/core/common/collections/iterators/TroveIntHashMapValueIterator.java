/*    */ package com.ankamagames.framework.kernel.core.common.collections.iterators;
/*    */ 
/*    */ import gnu.trove.TIntObjectHashMap;
/*    */ import gnu.trove.TIntObjectIterator;
/*    */ import java.util.Iterator;
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
/*    */ public class TroveIntHashMapValueIterator<T>
/*    */   implements Iterator<T>
/*    */ {
/*    */   private TIntObjectIterator<T> m_iterator;
/*    */   
/*    */   public TroveIntHashMapValueIterator(TIntObjectHashMap<T> map) {
/* 23 */     this.m_iterator = map.iterator();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 34 */     return this.m_iterator.hasNext();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public T next() {
/* 45 */     this.m_iterator.advance();
/* 46 */     return (T)this.m_iterator.value();
/*    */   }
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
/*    */   public void remove() {
/* 64 */     this.m_iterator.remove();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\collections\iterators\TroveIntHashMapValueIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */