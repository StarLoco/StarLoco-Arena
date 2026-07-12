/*     */ package gnu.trove;
/*     */ 
/*     */ import java.util.ConcurrentModificationException;
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
/*     */ public class TObjectIntIterator<K>
/*     */   extends TIterator
/*     */ {
/*     */   private final TObjectIntHashMap<K> _map;
/*     */   
/*     */   public TObjectIntIterator(TObjectIntHashMap<K> map) {
/* 100 */     super(map);
/* 101 */     this._map = map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int nextIndex() {
/* 111 */     if (this._expectedSize != this._hash.size()) {
/* 112 */       throw new ConcurrentModificationException();
/*     */     }
/*     */     
/* 115 */     Object[] set = this._map._set;
/* 116 */     int i = this._index;
/* 117 */     while (i-- > 0 && (set[i] == null || set[i] == TObjectHash.REMOVED || set[i] == TObjectHash.FREE));
/*     */     
/* 119 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void advance() {
/* 128 */     moveToNextIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K key() {
/* 139 */     return (K)this._map._set[this._index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int value() {
/* 150 */     return this._map._values[this._index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int setValue(int val) {
/* 162 */     int old = value();
/* 163 */     this._map._values[this._index] = val;
/* 164 */     return old;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TObjectIntIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */