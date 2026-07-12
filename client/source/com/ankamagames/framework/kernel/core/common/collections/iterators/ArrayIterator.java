/*    */ package com.ankamagames.framework.kernel.core.common.collections.iterators;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
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
/*    */ public class ArrayIterator<E>
/*    */   implements Iterator<E>
/*    */ {
/*    */   private E[] m_array;
/*    */   private int m_arrayLength;
/*    */   private boolean m_bReturnsNull;
/* 21 */   private int m_nextIndex = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArrayIterator(Object[] array, boolean returnsNull) {
/* 29 */     this.m_array = (E[])array;
/* 30 */     this.m_arrayLength = array.length;
/* 31 */     this.m_bReturnsNull = returnsNull;
/* 32 */     searchNextIndex();
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 36 */     return (this.m_nextIndex < this.m_arrayLength);
/*    */   }
/*    */   
/*    */   public E next() {
/* 40 */     if (!hasNext())
/* 41 */       throw new NoSuchElementException("Array end reached. Array Size : " + this.m_arrayLength); 
/* 42 */     E val = this.m_array[this.m_nextIndex];
/* 43 */     searchNextIndex();
/* 44 */     return val;
/*    */   }
/*    */   
/*    */   public void remove() {
/* 48 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   private void searchNextIndex() {
/* 52 */     if (this.m_bReturnsNull) {
/* 53 */       this.m_nextIndex++;
/*    */     } else {
/*    */       
/* 56 */       this.m_nextIndex++; for (; this.m_nextIndex < this.m_arrayLength && this.m_array[this.m_nextIndex] == null; this.m_nextIndex++);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\collections\iterators\ArrayIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */