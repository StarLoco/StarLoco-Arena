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
/*    */ public class EmptyIterator<T>
/*    */   implements Iterator<T>
/*    */ {
/*    */   public boolean hasNext() {
/* 19 */     return false;
/*    */   }
/*    */   
/*    */   public T next() {
/* 23 */     throw new NoSuchElementException();
/*    */   }
/*    */   
/*    */   public void remove() {
/* 27 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\collections\iterators\EmptyIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */