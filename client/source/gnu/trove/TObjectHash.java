/*     */ package gnu.trove;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public abstract class TObjectHash<T>
/*     */   extends THash
/*     */   implements TObjectHashingStrategy<T>
/*     */ {
/*     */   static final long serialVersionUID = -3461112548087185871L;
/*     */   protected transient Object[] _set;
/*     */   protected TObjectHashingStrategy<T> _hashingStrategy;
/*  42 */   protected static final Object REMOVED = new Object(), FREE = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TObjectHash() {
/*  50 */     this._hashingStrategy = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TObjectHash(TObjectHashingStrategy<T> strategy) {
/*  61 */     this._hashingStrategy = strategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TObjectHash(int initialCapacity) {
/*  72 */     super(initialCapacity);
/*  73 */     this._hashingStrategy = this;
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
/*     */   public TObjectHash(int initialCapacity, TObjectHashingStrategy<T> strategy) {
/*  86 */     super(initialCapacity);
/*  87 */     this._hashingStrategy = strategy;
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
/*     */   public TObjectHash(int initialCapacity, float loadFactor) {
/*  99 */     super(initialCapacity, loadFactor);
/* 100 */     this._hashingStrategy = this;
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
/*     */   public TObjectHash(int initialCapacity, float loadFactor, TObjectHashingStrategy<T> strategy) {
/* 114 */     super(initialCapacity, loadFactor);
/* 115 */     this._hashingStrategy = strategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TObjectHash<T> clone() {
/* 122 */     TObjectHash<T> h = (TObjectHash<T>)super.clone();
/* 123 */     h._set = (Object[])this._set.clone();
/* 124 */     return h;
/*     */   }
/*     */   
/*     */   protected int capacity() {
/* 128 */     return this._set.length;
/*     */   }
/*     */   
/*     */   protected void removeAt(int index) {
/* 132 */     this._set[index] = REMOVED;
/* 133 */     super.removeAt(index);
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
/*     */   protected int setUp(int initialCapacity) {
/* 145 */     int capacity = super.setUp(initialCapacity);
/* 146 */     this._set = new Object[capacity];
/* 147 */     Arrays.fill(this._set, FREE);
/* 148 */     return capacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean forEach(TObjectProcedure<T> procedure) {
/* 159 */     Object[] set = this._set;
/* 160 */     for (int i = set.length; i-- > 0;) {
/* 161 */       if (set[i] != FREE && set[i] != REMOVED && !procedure.execute((T)set[i]))
/*     */       {
/*     */         
/* 164 */         return false;
/*     */       }
/*     */     } 
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Object obj) {
/* 177 */     return (index((T)obj) >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int index(T obj) {
/* 187 */     TObjectHashingStrategy<T> hashing_strategy = this._hashingStrategy;
/*     */     
/* 189 */     Object[] set = this._set;
/* 190 */     int length = set.length;
/* 191 */     int hash = hashing_strategy.computeHashCode(obj) & Integer.MAX_VALUE;
/* 192 */     int index = hash % length;
/* 193 */     Object cur = set[index];
/*     */     
/* 195 */     if (cur == FREE) return -1;
/*     */ 
/*     */     
/* 198 */     if (cur == REMOVED || !hashing_strategy.equals((T)cur, obj)) {
/*     */       
/* 200 */       int probe = 1 + hash % (length - 2);
/*     */       
/*     */       do {
/* 203 */         index -= probe;
/* 204 */         if (index < 0) {
/* 205 */           index += length;
/*     */         }
/* 207 */         cur = set[index];
/*     */       }
/* 209 */       while (cur != FREE && (cur == REMOVED || !this._hashingStrategy.equals((T)cur, obj)));
/*     */     } 
/*     */     
/* 212 */     return (cur == FREE) ? -1 : index;
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
/*     */   protected int insertionIndex(T obj) {
/* 226 */     TObjectHashingStrategy<T> hashing_strategy = this._hashingStrategy;
/*     */     
/* 228 */     Object[] set = this._set;
/* 229 */     int length = set.length;
/* 230 */     int hash = hashing_strategy.computeHashCode(obj) & Integer.MAX_VALUE;
/* 231 */     int index = hash % length;
/* 232 */     Object cur = set[index];
/*     */     
/* 234 */     if (cur == FREE)
/* 235 */       return index; 
/* 236 */     if (cur != REMOVED && hashing_strategy.equals((T)cur, obj)) {
/* 237 */       return -index - 1;
/*     */     }
/*     */     
/* 240 */     int probe = 1 + hash % (length - 2);
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
/* 253 */     if (cur != REMOVED) {
/*     */       do
/*     */       {
/*     */         
/* 257 */         index -= probe;
/* 258 */         if (index < 0) {
/* 259 */           index += length;
/*     */         }
/* 261 */         cur = set[index];
/*     */       
/*     */       }
/* 264 */       while (cur != FREE && cur != REMOVED && !hashing_strategy.equals((T)cur, obj));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 270 */     if (cur == REMOVED) {
/* 271 */       int firstRemoved = index;
/*     */       
/* 273 */       while (cur != FREE && (cur == REMOVED || !hashing_strategy.equals((T)cur, obj))) {
/* 274 */         index -= probe;
/* 275 */         if (index < 0) {
/* 276 */           index += length;
/*     */         }
/* 278 */         cur = set[index];
/*     */       } 
/*     */       
/* 281 */       return (cur != FREE) ? (-index - 1) : firstRemoved;
/*     */     } 
/*     */ 
/*     */     
/* 285 */     return (cur != FREE) ? (-index - 1) : index;
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
/*     */   public final int computeHashCode(T o) {
/* 298 */     return (o == null) ? 0 : o.hashCode();
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
/*     */   public final boolean equals(T o1, T o2) {
/* 312 */     return (o1 == null) ? ((o2 == null)) : o1.equals(o2);
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
/*     */   protected final void throwObjectContractViolation(Object o1, Object o2) throws IllegalArgumentException {
/* 328 */     throw new IllegalArgumentException("Equal objects must have equal hashcodes. During rehashing, Trove discovered that the following two objects claim to be equal (as in java.lang.Object.equals()) but their hashCodes (or those calculated by your TObjectHashingStrategy) are not equal.This violates the general contract of java.lang.Object.hashCode().  See bullet point two in that method's documentation. object #1 =" + o1 + "; object #2 =" + o2);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TObjectHash.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */