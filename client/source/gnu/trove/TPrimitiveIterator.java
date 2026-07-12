/*    */ package gnu.trove;
/*    */ 
/*    */ import java.util.ConcurrentModificationException;
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
/*    */ abstract class TPrimitiveIterator
/*    */   extends TIterator
/*    */ {
/*    */   protected final TPrimitiveHash _hash;
/*    */   
/*    */   public TPrimitiveIterator(TPrimitiveHash hash) {
/* 52 */     super(hash);
/* 53 */     this._hash = hash;
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
/*    */   protected final int nextIndex() {
/* 65 */     if (this._expectedSize != this._hash.size()) {
/* 66 */       throw new ConcurrentModificationException();
/*    */     }
/*    */     
/* 69 */     byte[] states = this._hash._states;
/* 70 */     int i = this._index;
/* 71 */     while (i-- > 0 && states[i] != 1);
/* 72 */     return i;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TPrimitiveIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */