/*    */ package gnu.trove;
/*    */ 
/*    */ import java.util.ConcurrentModificationException;
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
/*    */ abstract class TIterator
/*    */ {
/*    */   protected final THash _hash;
/*    */   protected int _expectedSize;
/*    */   protected int _index;
/*    */   
/*    */   public TIterator(THash hash) {
/* 45 */     this._hash = hash;
/* 46 */     this._expectedSize = this._hash.size();
/* 47 */     this._index = this._hash.capacity();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 57 */     return (nextIndex() >= 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove() {
/* 67 */     if (this._expectedSize != this._hash.size()) {
/* 68 */       throw new ConcurrentModificationException();
/*    */     }
/*    */ 
/*    */     
/* 72 */     float auto_compation_factor = this._hash._autoCompactionFactor;
/*    */     try {
/* 74 */       this._hash._autoCompactionFactor = 0.0F;
/* 75 */       this._hash.removeAt(this._index);
/*    */     } finally {
/*    */       
/* 78 */       this._hash._autoCompactionFactor = auto_compation_factor;
/*    */     } 
/*    */     
/* 81 */     this._expectedSize--;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final void moveToNextIndex() {
/* 91 */     if ((this._index = nextIndex()) < 0)
/* 92 */       throw new NoSuchElementException(); 
/*    */   }
/*    */   
/*    */   protected abstract int nextIndex();
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */