/*    */ package com.ankamagames.framework.kernel.core.common.collections;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.collections.iterators.ArrayIterator;
/*    */ import java.lang.reflect.Array;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GrowingArray<T>
/*    */   implements Iterable<T>
/*    */ {
/* 23 */   private T[] m_elements = (Object[])Array.newInstance(Object.class, 0);
/*    */   
/*    */   public void add(T object) {
/* 26 */     if (object == null)
/* 27 */       return;
/* 28 */     int effectsCount = this.m_elements.length;
/* 29 */     Object[] newArray = (Object[])Array.newInstance(Object.class, effectsCount + 1);
/* 30 */     System.arraycopy(this.m_elements, 0, newArray, 0, effectsCount);
/* 31 */     newArray[effectsCount] = object;
/* 32 */     this.m_elements = newArray;
/*    */   }
/*    */   
/*    */   public void add(T[] elements) {
/* 36 */     if ((elements == null) || (elements.length == 0))
/* 37 */       return;
/* 38 */     int effectsCount = this.m_elements.length;
/* 39 */     Object[] newArray = (Object[])Array.newInstance(Object.class, effectsCount + elements.length);
/* 40 */     System.arraycopy(this.m_elements, 0, newArray, 0, effectsCount);
/* 41 */     System.arraycopy(this.m_elements, effectsCount, elements, 0, elements.length);
/* 42 */     this.m_elements = newArray;
/*    */   }
/*    */   
/*    */   public void set(T[] elements) {
/* 46 */     if (elements == null) {
/* 47 */       this.m_elements = ((Object[])Array.newInstance(Object.class, 0));
/* 48 */       return;
/*    */     }
/* 50 */     this.m_elements = elements;
/*    */   }
/*    */   
/*    */   public void set(int index, T element) {
/* 54 */     if (index < 0)
/* 55 */       return;
/* 56 */     if (index >= this.m_elements.length) {
/* 57 */       Object[] newArray = (Object[])Array.newInstance(Object.class, index + 1);
/* 58 */       System.arraycopy(this.m_elements, 0, newArray, 0, this.m_elements.length);
/* 59 */       this.m_elements = newArray;
/*    */     }
/* 61 */     this.m_elements[index] = element;
/*    */   }
/*    */   
/*    */   public T get(int index) {
/* 65 */     if ((index < 0) || (index >= this.m_elements.length))
/* 66 */       return null;
/* 67 */     return (T)this.m_elements[index];
/*    */   }
/*    */   
/*    */   public int size() {
/* 71 */     return this.m_elements.length;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 75 */     this.m_elements = ((Object[])Array.newInstance(Object.class, 0));
/*    */   }
/*    */   
/*    */   public Iterator<T> iterator() {
/* 79 */     return new ArrayIterator(this.m_elements, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\collections\GrowingArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */