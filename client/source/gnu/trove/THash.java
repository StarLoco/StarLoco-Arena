/*     */ package gnu.trove;
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
/*     */ public abstract class THash
/*     */   implements Cloneable
/*     */ {
/*     */   protected transient int _size;
/*     */   protected transient int _free;
/*     */   protected static final float DEFAULT_LOAD_FACTOR = 0.5F;
/*     */   protected static final int DEFAULT_INITIAL_CAPACITY = 10;
/*     */   protected float _loadFactor;
/*     */   protected int _maxSize;
/*     */   protected int _autoCompactRemovesRemaining;
/*     */   protected float _autoCompactionFactor;
/*     */   
/*     */   public THash() {
/*  84 */     this(10, 0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public THash(int initialCapacity) {
/*  95 */     this(initialCapacity, 0.5F);
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
/*     */ 
/*     */   
/*     */   public THash(int initialCapacity, float loadFactor) {
/* 109 */     this._loadFactor = loadFactor;
/*     */ 
/*     */ 
/*     */     
/* 113 */     this._autoCompactionFactor = loadFactor;
/*     */     
/* 115 */     setUp((int)Math.ceil((initialCapacity / loadFactor)));
/*     */   }
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 120 */       return super.clone();
/* 121 */     } catch (CloneNotSupportedException cnse) {
/* 122 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 132 */     return (0 == this._size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 141 */     return this._size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int capacity();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(int desiredCapacity) {
/* 158 */     if (desiredCapacity > this._maxSize - size()) {
/* 159 */       rehash(PrimeFinder.nextPrime((int)Math.ceil((desiredCapacity + size() / this._loadFactor)) + 1));
/*     */       
/* 161 */       computeMaxSize(capacity());
/*     */     } 
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
/*     */   public void compact() {
/* 184 */     rehash(PrimeFinder.nextPrime((int)Math.ceil((size() / this._loadFactor)) + 1));
/* 185 */     computeMaxSize(capacity());
/*     */ 
/*     */     
/* 188 */     if (this._autoCompactionFactor != 0.0F) {
/* 189 */       computeNextAutoCompactionAmount(size());
/*     */     }
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
/*     */ 
/*     */   
/*     */   public void setAutoCompactionFactor(float factor) {
/* 204 */     if (factor < 0.0F) {
/* 205 */       throw new IllegalArgumentException("Factor must be >= 0: " + factor);
/*     */     }
/*     */     
/* 208 */     this._autoCompactionFactor = factor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAutoCompactionFactor() {
/* 215 */     return this._autoCompactionFactor;
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
/*     */ 
/*     */   
/*     */   public final void trimToSize() {
/* 229 */     compact();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeAt(int index) {
/* 239 */     this._size--;
/*     */ 
/*     */     
/* 242 */     if (this._autoCompactionFactor != 0.0F) {
/* 243 */       this._autoCompactRemovesRemaining--;
/*     */       
/* 245 */       if (this._autoCompactRemovesRemaining == 0)
/*     */       {
/*     */         
/* 248 */         compact();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 257 */     this._size = 0;
/* 258 */     this._free = capacity();
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
/*     */   
/*     */   protected int setUp(int initialCapacity) {
/* 271 */     int capacity = PrimeFinder.nextPrime(initialCapacity);
/* 272 */     computeMaxSize(capacity);
/* 273 */     computeNextAutoCompactionAmount(initialCapacity);
/*     */     
/* 275 */     return capacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void rehash(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void computeMaxSize(int capacity) {
/* 293 */     this._maxSize = Math.min(capacity - 1, (int)Math.floor((capacity * this._loadFactor)));
/*     */     
/* 295 */     this._free = capacity - this._size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void computeNextAutoCompactionAmount(int size) {
/* 304 */     if (this._autoCompactionFactor != 0.0F) {
/* 305 */       this._autoCompactRemovesRemaining = Math.round(size * this._autoCompactionFactor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void postInsertHook(boolean usedFreeSlot) {
/* 315 */     if (usedFreeSlot) {
/* 316 */       this._free--;
/*     */     }
/*     */ 
/*     */     
/* 320 */     if (++this._size > this._maxSize || this._free == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 325 */       int newCapacity = (this._size > this._maxSize) ? PrimeFinder.nextPrime(capacity() << 1) : capacity();
/* 326 */       rehash(newCapacity);
/* 327 */       computeMaxSize(capacity());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int calculateGrownCapacity() {
/* 332 */     return capacity() << 1;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\THash.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */