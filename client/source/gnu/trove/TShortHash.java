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
/*     */ public abstract class TShortHash
/*     */   extends TPrimitiveHash
/*     */   implements TShortHashingStrategy
/*     */ {
/*     */   protected transient short[] _set;
/*     */   protected TShortHashingStrategy _hashingStrategy;
/*     */   
/*     */   public TShortHash() {
/*  49 */     this._hashingStrategy = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TShortHash(int initialCapacity) {
/*  60 */     super(initialCapacity);
/*  61 */     this._hashingStrategy = this;
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
/*     */   public TShortHash(int initialCapacity, float loadFactor) {
/*  73 */     super(initialCapacity, loadFactor);
/*  74 */     this._hashingStrategy = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TShortHash(TShortHashingStrategy strategy) {
/*  84 */     this._hashingStrategy = strategy;
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
/*     */   public TShortHash(int initialCapacity, TShortHashingStrategy strategy) {
/*  96 */     super(initialCapacity);
/*  97 */     this._hashingStrategy = strategy;
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
/*     */   public TShortHash(int initialCapacity, float loadFactor, TShortHashingStrategy strategy) {
/* 110 */     super(initialCapacity, loadFactor);
/* 111 */     this._hashingStrategy = strategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 118 */     TShortHash h = (TShortHash)super.clone();
/* 119 */     h._set = (short[])this._set.clone();
/* 120 */     return h;
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
/* 133 */     int capacity = super.setUp(initialCapacity);
/* 134 */     this._set = new short[capacity];
/* 135 */     return capacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(short val) {
/* 145 */     return (index(val) >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean forEach(TShortProcedure procedure) {
/* 156 */     byte[] states = this._states;
/* 157 */     short[] set = this._set;
/* 158 */     for (int i = set.length; i-- > 0;) {
/* 159 */       if (states[i] == 1 && !procedure.execute(set[i])) {
/* 160 */         return false;
/*     */       }
/*     */     } 
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeAt(int index) {
/* 172 */     this._set[index] = 0;
/* 173 */     super.removeAt(index);
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
/*     */   protected int index(short val) {
/* 187 */     byte[] states = this._states;
/* 188 */     short[] set = this._set;
/* 189 */     int length = states.length;
/* 190 */     int hash = this._hashingStrategy.computeHashCode(val) & Integer.MAX_VALUE;
/* 191 */     int index = hash % length;
/*     */     
/* 193 */     if (states[index] != 0 && (states[index] == 2 || set[index] != val)) {
/*     */ 
/*     */       
/* 196 */       int probe = 1 + hash % (length - 2);
/*     */       
/*     */       do {
/* 199 */         index -= probe;
/* 200 */         if (index >= 0)
/* 201 */           continue;  index += length;
/*     */       }
/* 203 */       while (states[index] != 0 && (states[index] == 2 || set[index] != val));
/*     */     } 
/*     */ 
/*     */     
/* 207 */     return (states[index] == 0) ? -1 : index;
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
/*     */   protected int insertionIndex(short val) {
/* 223 */     byte[] states = this._states;
/* 224 */     short[] set = this._set;
/* 225 */     int length = states.length;
/* 226 */     int hash = this._hashingStrategy.computeHashCode(val) & Integer.MAX_VALUE;
/* 227 */     int index = hash % length;
/*     */     
/* 229 */     if (states[index] == 0)
/* 230 */       return index; 
/* 231 */     if (states[index] == 1 && set[index] == val) {
/* 232 */       return -index - 1;
/*     */     }
/*     */     
/* 235 */     int probe = 1 + hash % (length - 2);
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
/* 249 */     if (states[index] != 2) {
/*     */       do
/*     */       {
/*     */         
/* 253 */         index -= probe;
/* 254 */         if (index >= 0)
/* 255 */           continue;  index += length;
/*     */       }
/* 257 */       while (states[index] == 1 && set[index] != val);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 263 */     if (states[index] == 2) {
/* 264 */       int firstRemoved = index;
/* 265 */       while (states[index] != 0 && (states[index] == 2 || set[index] != val)) {
/*     */         
/* 267 */         index -= probe;
/* 268 */         if (index < 0) {
/* 269 */           index += length;
/*     */         }
/*     */       } 
/* 272 */       return (states[index] == 1) ? (-index - 1) : firstRemoved;
/*     */     } 
/*     */     
/* 275 */     return (states[index] == 1) ? (-index - 1) : index;
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
/*     */   public final int computeHashCode(short val) {
/* 287 */     return HashFunctions.hash(val);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TShortHash.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */