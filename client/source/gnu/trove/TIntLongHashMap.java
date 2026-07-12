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
/*     */ public class TIntLongHashMap
/*     */   extends TIntHash
/*     */   implements Externalizable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected transient long[] _values;
/*     */   
/*     */   public TIntLongHashMap() {}
/*     */   
/*     */   public TIntLongHashMap(int initialCapacity) {
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
/*     */   public TIntLongHashMap(int initialCapacity, float loadFactor) {
/*  73 */     super(initialCapacity, loadFactor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntLongHashMap(TIntHashingStrategy strategy) {
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
/*     */   public TIntLongHashMap(int initialCapacity, TIntHashingStrategy strategy) {
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
/*     */   public TIntLongHashMap(int initialCapacity, float loadFactor, TIntHashingStrategy strategy) {
/* 107 */     super(initialCapacity, loadFactor, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 114 */     TIntLongHashMap m = (TIntLongHashMap)super.clone();
/* 115 */     m._values = (long[])this._values.clone();
/* 116 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntLongIterator iterator() {
/* 123 */     return new TIntLongIterator(this);
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
/* 137 */     this._values = new long[capacity];
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
/*     */   public long put(int key, long value) {
/* 151 */     long previous = 0L;
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
/* 177 */     int[] oldKeys = this._set;
/* 178 */     long[] oldVals = this._values;
/* 179 */     byte[] oldStates = this._states;
/*     */     
/* 181 */     this._set = new int[newCapacity];
/* 182 */     this._values = new long[newCapacity];
/* 183 */     this._states = new byte[newCapacity];
/*     */     
/* 185 */     for (int i = oldCapacity; i-- > 0;) {
/* 186 */       if (oldStates[i] == 1) {
/* 187 */         int o = oldKeys[i];
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
/*     */   public long get(int key) {
/* 203 */     int index = index(key);
/* 204 */     return (index < 0) ? 0L : this._values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 212 */     super.clear();
/* 213 */     int[] keys = this._set;
/* 214 */     long[] vals = this._values;
/* 215 */     byte[] states = this._states;
/*     */     
/* 217 */     for (int i = keys.length; i-- > 0; ) {
/* 218 */       keys[i] = 0;
/* 219 */       vals[i] = 0L;
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
/*     */   public long remove(int key) {
/* 231 */     long prev = 0L;
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
/* 248 */     if (!(other instanceof TIntLongHashMap)) {
/* 249 */       return false;
/*     */     }
/* 251 */     TIntLongHashMap that = (TIntLongHashMap)other;
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
/*     */   private final class HashProcedure implements TIntLongProcedure {
/* 265 */     private int h = 0;
/*     */     
/*     */     public int getHashCode() {
/* 268 */       return this.h;
/*     */     }
/*     */     
/*     */     public final boolean execute(int key, long value) {
/* 272 */       this.h += TIntLongHashMap.this._hashingStrategy.computeHashCode(key) ^ HashFunctions.hash(value);
/* 273 */       return true;
/*     */     }
/*     */     
/*     */     private HashProcedure() {}
/*     */   }
/*     */   
/*     */   private static final class EqProcedure implements TIntLongProcedure {
/*     */     EqProcedure(TIntLongHashMap otherMap) {
/* 281 */       this._otherMap = otherMap;
/*     */     }
/*     */     private final TIntLongHashMap _otherMap;
/*     */     public final boolean execute(int key, long value) {
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
/*     */     private final boolean eq(long v1, long v2) {
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
/* 307 */     this._values[index] = 0L;
/* 308 */     super.removeAt(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[] getValues() {
/* 317 */     long[] vals = new long[size()];
/* 318 */     long[] v = this._values;
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
/*     */   public int[] keys() {
/* 335 */     int[] keys = new int[size()];
/* 336 */     int[] k = this._set;
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
/*     */   public int[] keys(int[] a) {
/* 356 */     int size = size();
/* 357 */     if (a.length < size) {
/* 358 */       a = (int[])Array.newInstance(a.getClass().getComponentType(), size);
/*     */     }
/*     */ 
/*     */     
/* 362 */     int[] k = this._set;
/*     */     
/* 364 */     for (int i = k.length, j = 0; i-- > 0;) {
/* 365 */       if (k[i] != 0 && k[i] != 2) {
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
/*     */   public boolean containsValue(long val) {
/* 379 */     byte[] states = this._states;
/* 380 */     long[] vals = this._values;
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
/*     */   public boolean containsKey(int key) {
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
/*     */   public boolean forEachKey(TIntProcedure procedure) {
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
/*     */   public boolean forEachValue(TLongProcedure procedure) {
/* 420 */     byte[] states = this._states;
/* 421 */     long[] values = this._values;
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
/*     */   public boolean forEachEntry(TIntLongProcedure procedure) {
/* 439 */     byte[] states = this._states;
/* 440 */     int[] keys = this._set;
/* 441 */     long[] values = this._values;
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
/*     */   public boolean retainEntries(TIntLongProcedure procedure) {
/* 458 */     boolean modified = false;
/* 459 */     byte[] states = this._states;
/* 460 */     int[] keys = this._set;
/* 461 */     long[] values = this._values;
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
/*     */   public void transformValues(TLongFunction function) {
/* 477 */     byte[] states = this._states;
/* 478 */     long[] values = this._values;
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
/*     */   public boolean increment(int key) {
/* 493 */     return adjustValue(key, 1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean adjustValue(int key, long amount) {
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
/*     */   
/*     */   public long adjustOrPutValue(int key, long adjust_amount, long put_amount) {
/* 526 */     int index = insertionIndex(key);
/*     */ 
/*     */ 
/*     */     
/* 530 */     index = -index - 1;
/* 531 */     long newValue = this._values[index] = this._values[index] + adjust_amount;
/* 532 */     boolean isNewMapping = false;
/*     */     
/* 534 */     newValue = this._values[index] = put_amount;
/* 535 */     isNewMapping = true;
/*     */ 
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
/* 576 */       int key = in.readInt();
/* 577 */       long val = in.readLong();
/* 578 */       put(key, val);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TIntLongHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */