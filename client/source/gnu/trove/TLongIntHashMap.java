/*     */ package gnu.trove;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.lang.reflect.Array;
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
/*     */ public class TLongIntHashMap
/*     */   extends TLongHash
/*     */   implements Externalizable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected transient int[] _values;
/*     */   
/*     */   public TLongIntHashMap() {}
/*     */   
/*     */   public TLongIntHashMap(int initialCapacity) {
/*  61 */     super(initialCapacity);
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
/*     */   public TLongIntHashMap(int initialCapacity, float loadFactor) {
/*  73 */     super(initialCapacity, loadFactor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TLongIntHashMap(TLongHashingStrategy strategy) {
/*  82 */     super(strategy);
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
/*     */   public TLongIntHashMap(int initialCapacity, TLongHashingStrategy strategy) {
/*  94 */     super(initialCapacity, strategy);
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
/*     */   public TLongIntHashMap(int initialCapacity, float loadFactor, TLongHashingStrategy strategy) {
/* 107 */     super(initialCapacity, loadFactor, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 114 */     TLongIntHashMap m = (TLongIntHashMap)super.clone();
/* 115 */     m._values = (int[])this._values.clone();
/* 116 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TLongIntIterator iterator() {
/* 123 */     return new TLongIntIterator(this);
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
/* 136 */     int capacity = super.setUp(initialCapacity);
/* 137 */     this._values = new int[capacity];
/* 138 */     return capacity;
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
/*     */   public int put(long key, int value) {
/* 151 */     int previous = 0;
/* 152 */     int index = insertionIndex(key);
/* 153 */     boolean isNewMapping = true;
/* 154 */     if (index < 0) {
/* 155 */       index = -index - 1;
/* 156 */       previous = this._values[index];
/* 157 */       isNewMapping = false;
/*     */     } 
/* 159 */     byte previousState = this._states[index];
/* 160 */     this._set[index] = key;
/* 161 */     this._states[index] = 1;
/* 162 */     this._values[index] = value;
/* 163 */     if (isNewMapping) {
/* 164 */       postInsertHook((previousState == 0));
/*     */     }
/*     */     
/* 167 */     return previous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rehash(int newCapacity) {
/* 176 */     int oldCapacity = this._set.length;
/* 177 */     long[] oldKeys = this._set;
/* 178 */     int[] oldVals = this._values;
/* 179 */     byte[] oldStates = this._states;
/*     */     
/* 181 */     this._set = new long[newCapacity];
/* 182 */     this._values = new int[newCapacity];
/* 183 */     this._states = new byte[newCapacity];
/*     */     
/* 185 */     for (int i = oldCapacity; i-- > 0;) {
/* 186 */       if (oldStates[i] == 1) {
/* 187 */         long o = oldKeys[i];
/* 188 */         int index = insertionIndex(o);
/* 189 */         this._set[index] = o;
/* 190 */         this._values[index] = oldVals[i];
/* 191 */         this._states[index] = 1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long key) {
/* 203 */     int index = index(key);
/* 204 */     return (index < 0) ? 0 : this._values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 212 */     super.clear();
/* 213 */     long[] keys = this._set;
/* 214 */     int[] vals = this._values;
/* 215 */     byte[] states = this._states;
/*     */     
/* 217 */     for (int i = keys.length; i-- > 0; ) {
/* 218 */       keys[i] = 0L;
/* 219 */       vals[i] = 0;
/* 220 */       states[i] = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int remove(long key) {
/* 231 */     int prev = 0;
/* 232 */     int index = index(key);
/* 233 */     if (index >= 0) {
/* 234 */       prev = this._values[index];
/* 235 */       removeAt(index);
/*     */     } 
/* 237 */     return prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 248 */     if (!(other instanceof TLongIntHashMap)) {
/* 249 */       return false;
/*     */     }
/* 251 */     TLongIntHashMap that = (TLongIntHashMap)other;
/* 252 */     if (that.size() != size()) {
/* 253 */       return false;
/*     */     }
/* 255 */     return forEachEntry(new EqProcedure(that));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 259 */     HashProcedure p = new HashProcedure();
/* 260 */     forEachEntry(p);
/* 261 */     return p.getHashCode();
/*     */   }
/*     */   
/*     */   private final class HashProcedure implements TLongIntProcedure {
/* 265 */     private int h = 0;
/*     */     
/*     */     public int getHashCode() {
/* 268 */       return this.h;
/*     */     }
/*     */     
/*     */     public final boolean execute(long key, int value) {
/* 272 */       this.h += TLongIntHashMap.this._hashingStrategy.computeHashCode(key) ^ HashFunctions.hash(value);
/* 273 */       return true;
/*     */     }
/*     */     
/*     */     private HashProcedure() {}
/*     */   }
/*     */   
/*     */   private static final class EqProcedure implements TLongIntProcedure {
/*     */     EqProcedure(TLongIntHashMap otherMap) {
/* 281 */       this._otherMap = otherMap;
/*     */     }
/*     */     private final TLongIntHashMap _otherMap;
/*     */     public final boolean execute(long key, int value) {
/* 285 */       int index = this._otherMap.index(key);
/* 286 */       if (index >= 0 && eq(value, this._otherMap.get(key))) {
/* 287 */         return true;
/*     */       }
/* 289 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean eq(int v1, int v2) {
/* 296 */       return (v1 == v2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeAt(int index) {
/* 307 */     this._values[index] = 0;
/* 308 */     super.removeAt(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getValues() {
/* 317 */     int[] vals = new int[size()];
/* 318 */     int[] v = this._values;
/* 319 */     byte[] states = this._states;
/*     */     
/* 321 */     for (int i = v.length, j = 0; i-- > 0;) {
/* 322 */       if (states[i] == 1) {
/* 323 */         vals[j++] = v[i];
/*     */       }
/*     */     } 
/* 326 */     return vals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[] keys() {
/* 335 */     long[] keys = new long[size()];
/* 336 */     long[] k = this._set;
/* 337 */     byte[] states = this._states;
/*     */     
/* 339 */     for (int i = k.length, j = 0; i-- > 0;) {
/* 340 */       if (states[i] == 1) {
/* 341 */         keys[j++] = k[i];
/*     */       }
/*     */     } 
/* 344 */     return keys;
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
/*     */   public long[] keys(long[] a) {
/* 356 */     int size = size();
/* 357 */     if (a.length < size) {
/* 358 */       a = (long[])Array.newInstance(a.getClass().getComponentType(), size);
/*     */     }
/*     */ 
/*     */     
/* 362 */     long[] k = this._set;
/*     */     
/* 364 */     for (int i = k.length, j = 0; i-- > 0;) {
/* 365 */       if (k[i] != 0L && k[i] != 2L) {
/* 366 */         a[j++] = k[i];
/*     */       }
/*     */     } 
/* 369 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsValue(int val) {
/* 379 */     byte[] states = this._states;
/* 380 */     int[] vals = this._values;
/*     */     
/* 382 */     for (int i = vals.length; i-- > 0;) {
/* 383 */       if (states[i] == 1 && val == vals[i]) {
/* 384 */         return true;
/*     */       }
/*     */     } 
/* 387 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(long key) {
/* 398 */     return contains(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean forEachKey(TLongProcedure procedure) {
/* 409 */     return forEach(procedure);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean forEachValue(TIntProcedure procedure) {
/* 420 */     byte[] states = this._states;
/* 421 */     int[] values = this._values;
/* 422 */     for (int i = values.length; i-- > 0;) {
/* 423 */       if (states[i] == 1 && !procedure.execute(values[i])) {
/* 424 */         return false;
/*     */       }
/*     */     } 
/* 427 */     return true;
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
/*     */   public boolean forEachEntry(TLongIntProcedure procedure) {
/* 439 */     byte[] states = this._states;
/* 440 */     long[] keys = this._set;
/* 441 */     int[] values = this._values;
/* 442 */     for (int i = keys.length; i-- > 0;) {
/* 443 */       if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
/* 444 */         return false;
/*     */       }
/*     */     } 
/* 447 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retainEntries(TLongIntProcedure procedure) {
/* 458 */     boolean modified = false;
/* 459 */     byte[] states = this._states;
/* 460 */     long[] keys = this._set;
/* 461 */     int[] values = this._values;
/* 462 */     for (int i = keys.length; i-- > 0;) {
/* 463 */       if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
/* 464 */         removeAt(i);
/* 465 */         modified = true;
/*     */       } 
/*     */     } 
/* 468 */     return modified;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformValues(TIntFunction function) {
/* 477 */     byte[] states = this._states;
/* 478 */     int[] values = this._values;
/* 479 */     for (int i = values.length; i-- > 0;) {
/* 480 */       if (states[i] == 1) {
/* 481 */         values[i] = function.execute(values[i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean increment(long key) {
/* 493 */     return adjustValue(key, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean adjustValue(long key, int amount) {
/* 504 */     int index = index(key);
/* 505 */     if (index < 0) {
/* 506 */       return false;
/*     */     }
/* 508 */     this._values[index] = this._values[index] + amount;
/* 509 */     return true;
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
/*     */   public int adjustOrPutValue(long key, int adjust_amount, int put_amount) {
/*     */     boolean isNewMapping;
/* 526 */     int newValue, index = insertionIndex(key);
/*     */ 
/*     */     
/* 529 */     if (index < 0) {
/* 530 */       index = -index - 1;
/* 531 */       newValue = this._values[index] = this._values[index] + adjust_amount;
/* 532 */       isNewMapping = false;
/*     */     } else {
/* 534 */       newValue = this._values[index] = put_amount;
/* 535 */       isNewMapping = true;
/*     */     } 
/*     */     
/* 538 */     byte previousState = this._states[index];
/* 539 */     this._set[index] = key;
/* 540 */     this._states[index] = 1;
/*     */     
/* 542 */     if (isNewMapping) {
/* 543 */       postInsertHook((previousState == 0));
/*     */     }
/*     */     
/* 546 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 552 */     out.writeByte(0);
/*     */ 
/*     */     
/* 555 */     out.writeInt(this._size);
/*     */ 
/*     */     
/* 558 */     SerializationProcedure writeProcedure = new SerializationProcedure(out);
/* 559 */     if (!forEachEntry(writeProcedure)) {
/* 560 */       throw writeProcedure.exception;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 568 */     in.readByte();
/*     */ 
/*     */     
/* 571 */     int size = in.readInt();
/* 572 */     setUp(size);
/*     */ 
/*     */     
/* 575 */     while (size-- > 0) {
/* 576 */       long key = in.readLong();
/* 577 */       int val = in.readInt();
/* 578 */       put(key, val);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TLongIntHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */