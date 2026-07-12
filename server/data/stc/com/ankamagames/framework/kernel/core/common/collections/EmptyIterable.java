/*    */ package com.ankamagames.framework.kernel.core.common.collections;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.collections.iterators.EmptyIterator;
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
/*    */ public class EmptyIterable<T>
/*    */   implements Iterable<T>
/*    */ {
/*    */   public Iterator<T> iterator()
/*    */   {
/* 20 */     return new EmptyIterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\collections\EmptyIterable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */