/*     */ package gnu.trove;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.lang.reflect.Array;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TObjectIntHashMap<K>
/*     */   extends TObjectHash<K>
/*     */   implements Externalizable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected transient int[] _values;
/*     */   
/*     */   public TObjectIntHashMap() {}
/*     */   
/*     */   public TObjectIntHashMap(int initialCapacity) {
/*  62 */     super(initialCapacity);
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
/*     */   public TObjectIntHashMap(int initialCapacity, float loadFactor) {
/*  74 */     super(initialCapacity, loadFactor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TObjectIntHashMap(TObjectHashingStrategy<K> strategy) {
/*  83 */     super(strategy);
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
/*     */   public TObjectIntHashMap(int initialCapacity, TObjectHashingStrategy<K> strategy) {
/*  95 */     super(initialCapacity, strategy);
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
/*     */   public TObjectIntHashMap(int initialCapacity, float loadFactor, TObjectHashingStrategy<K> strategy) {
/* 108 */     super(initialCapacity, loadFactor, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TObjectIntIterator<K> iterator() {
/* 115 */     return new TObjectIntIterator<K>(this);
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
/* 128 */     int capacity = super.setUp(initialCapacity);
/* 129 */     this._values = new int[capacity];
/* 130 */     return capacity;
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
/*     */   public int put(K key, int value) {
/* 142 */     int previous = 0;
/* 143 */     int index = insertionIndex(key);
/* 144 */     boolean isNewMapping = true;
/* 145 */     if (index < 0) {
/* 146 */       index = -index - 1;
/* 147 */       previous = this._values[index];
/* 148 */       isNewMapping = false;
/*     */     } 
/* 150 */     K oldKey = (K)this._set[index];
/* 151 */     this._set[index] = key;
/* 152 */     this._values[index] = value;
/*     */     
/* 154 */     if (isNewMapping) {
/* 155 */       postInsertHook((oldKey == FREE));
/*     */     }
/* 157 */     return previous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rehash(int newCapacity) {
/* 166 */     int oldCapacity = this._set.length;
/* 167 */     K[] oldKeys = (K[])this._set;
/* 168 */     int[] oldVals = this._values;
/*     */     
/* 170 */     this._set = new Object[newCapacity];
/* 171 */     Arrays.fill(this._set, FREE);
/* 172 */     this._values = new int[newCapacity];
/*     */     
/* 174 */     for (int i = oldCapacity; i-- > 0;) {
/* 175 */       if (oldKeys[i] != FREE && oldKeys[i] != REMOVED) {
/* 176 */         K o = oldKeys[i];
/* 177 */         int index = insertionIndex(o);
/* 178 */         if (index < 0) {
/* 179 */           throwObjectContractViolation(this._set[-index - 1], o);
/*     */         }
/* 181 */         this._set[index] = o;
/* 182 */         this._values[index] = oldVals[i];
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
/*     */   public int get(K key) {
/* 194 */     int index = index(key);
/* 195 */     return (index < 0) ? 0 : this._values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 203 */     super.clear();
/* 204 */     Object[] keys = this._set;
/* 205 */     int[] vals = this._values;
/*     */     
/* 207 */     for (int i = keys.length; i-- > 0; ) {
/* 208 */       keys[i] = FREE;
/* 209 */       vals[i] = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int remove(K key) {
/* 220 */     int prev = 0;
/* 221 */     int index = index(key);
/* 222 */     if (index >= 0) {
/* 223 */       prev = this._values[index];
/* 224 */       removeAt(index);
/*     */     } 
/* 226 */     return prev;
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
/* 237 */     if (!(other instanceof TObjectIntHashMap)) {
/* 238 */       return false;
/*     */     }
/* 240 */     TObjectIntHashMap that = (TObjectIntHashMap)other;
/* 241 */     if (that.size() != size()) {
/* 242 */       return false;
/*     */     }
/* 244 */     return forEachEntry(new EqProcedure(that));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TObjectIntHashMap<K> clone() {
/* 252 */     TObjectIntHashMap<K> clone = (TObjectIntHashMap<K>)super.clone();
/* 253 */     clone._values = new int[this._values.length];
/* 254 */     for (int i = 0; i < clone._values.length; i++) {
/* 255 */       clone._values[i] = this._values[i];
/*     */     }
/* 257 */     return clone;
/*     */   }
/*     */   
/*     */   private static final class EqProcedure
/*     */     implements TObjectIntProcedure {
/*     */     private final TObjectIntHashMap _otherMap;
/*     */     
/*     */     EqProcedure(TObjectIntHashMap otherMap) {
/* 265 */       this._otherMap = otherMap;
/*     */     }
/*     */     
/*     */     public final boolean execute(Object key, int value) {
/* 269 */       int index = this._otherMap.index(key);
/* 270 */       if (index >= 0 && eq(value, this._otherMap.get(key))) {
/* 271 */         return true;
/*     */       }
/* 273 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean eq(int v1, int v2) {
/* 280 */       return (v1 == v2);
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
/* 291 */     this._values[index] = 0;
/* 292 */     super.removeAt(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getValues() {
/* 301 */     int[] vals = new int[size()];
/* 302 */     int[] v = this._values;
/* 303 */     Object[] keys = this._set;
/*     */     
/* 305 */     for (int i = v.length, j = 0; i-- > 0;) {
/* 306 */       if (keys[i] != FREE && keys[i] != REMOVED) {
/* 307 */         vals[j++] = v[i];
/*     */       }
/*     */     } 
/* 310 */     return vals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] keys() {
/* 319 */     Object[] keys = new Object[size()];
/* 320 */     K[] k = (K[])this._set;
/*     */     
/* 322 */     for (int i = k.length, j = 0; i-- > 0;) {
/* 323 */       if (k[i] != FREE && k[i] != REMOVED) {
/* 324 */         keys[j++] = k[i];
/*     */       }
/*     */     } 
/* 327 */     return keys;
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
/*     */   public K[] keys(K[] a) {
/* 339 */     int size = size();
/* 340 */     if (a.length < size) {
/* 341 */       a = (K[])Array.newInstance(a.getClass().getComponentType(), size);
/*     */     }
/*     */ 
/*     */     
/* 345 */     K[] k = (K[])this._set;
/*     */     
/* 347 */     for (int i = k.length, j = 0; i-- > 0;) {
/* 348 */       if (k[i] != FREE && k[i] != REMOVED) {
/* 349 */         a[j++] = k[i];
/*     */       }
/*     */     } 
/* 352 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsValue(int val) {
/* 362 */     Object[] keys = this._set;
/* 363 */     int[] vals = this._values;
/*     */     
/* 365 */     for (int i = vals.length; i-- > 0;) {
/* 366 */       if (keys[i] != FREE && keys[i] != REMOVED && val == vals[i]) {
/* 367 */         return true;
/*     */       }
/*     */     } 
/* 370 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(K key) {
/* 381 */     return contains(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean forEachKey(TObjectProcedure<K> procedure) {
/* 392 */     return forEach(procedure);
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
/* 403 */     Object[] keys = this._set;
/* 404 */     int[] values = this._values;
/* 405 */     for (int i = values.length; i-- > 0;) {
/* 406 */       if (keys[i] != FREE && keys[i] != REMOVED && !procedure.execute(values[i]))
/*     */       {
/* 408 */         return false;
/*     */       }
/*     */     } 
/* 411 */     return true;
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
/*     */   public boolean forEachEntry(TObjectIntProcedure<K> procedure) {
/* 423 */     K[] keys = (K[])this._set;
/* 424 */     int[] values = this._values;
/* 425 */     for (int i = keys.length; i-- > 0;) {
/* 426 */       if (keys[i] != FREE && keys[i] != REMOVED && !procedure.execute(keys[i], values[i]))
/*     */       {
/*     */         
/* 429 */         return false;
/*     */       }
/*     */     } 
/* 432 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retainEntries(TObjectIntProcedure<K> procedure) {
/* 443 */     boolean modified = false;
/* 444 */     K[] keys = (K[])this._set;
/* 445 */     int[] values = this._values;
/* 446 */     for (int i = keys.length; i-- > 0;) {
/* 447 */       if (keys[i] != FREE && keys[i] != REMOVED && !procedure.execute(keys[i], values[i])) {
/*     */ 
/*     */         
/* 450 */         removeAt(i);
/* 451 */         modified = true;
/*     */       } 
/*     */     } 
/* 454 */     return modified;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformValues(TIntFunction function) {
/* 463 */     Object[] keys = this._set;
/* 464 */     int[] values = this._values;
/* 465 */     for (int i = values.length; i-- > 0;) {
/* 466 */       if (keys[i] != null && keys[i] != REMOVED) {
/* 467 */         values[i] = function.execute(values[i]);
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
/*     */   public boolean increment(K key) {
/* 479 */     return adjustValue(key, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean adjustValue(K key, int amount) {
/* 490 */     int index = index(key);
/* 491 */     if (index < 0) {
/* 492 */       return false;
/*     */     }
/* 494 */     this._values[index] = this._values[index] + amount;
/* 495 */     return true;
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
/*     */   public int adjustOrPutValue(K key, int adjust_amount, int put_amount) {
/*     */     boolean isNewMapping;
/* 512 */     int newValue, index = insertionIndex(key);
/*     */ 
/*     */     
/* 515 */     if (index < 0) {
/* 516 */       index = -index - 1;
/* 517 */       newValue = this._values[index] = this._values[index] + adjust_amount;
/* 518 */       isNewMapping = false;
/*     */     } else {
/* 520 */       newValue = this._values[index] = put_amount;
/* 521 */       isNewMapping = true;
/*     */     } 
/*     */     
/* 524 */     K oldKey = (K)this._set[index];
/* 525 */     this._set[index] = key;
/*     */     
/* 527 */     if (isNewMapping) {
/* 528 */       postInsertHook((oldKey == FREE));
/*     */     }
/*     */     
/* 531 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 537 */     out.writeByte(0);
/*     */ 
/*     */     
/* 540 */     out.writeInt(this._size);
/*     */ 
/*     */     
/* 543 */     SerializationProcedure writeProcedure = new SerializationProcedure(out);
/* 544 */     if (!forEachEntry(writeProcedure)) {
/* 545 */       throw writeProcedure.exception;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 553 */     in.readByte();
/*     */ 
/*     */     
/* 556 */     int size = in.readInt();
/* 557 */     setUp(size);
/*     */ 
/*     */     
/* 560 */     while (size-- > 0) {
/* 561 */       K key = (K)in.readObject();
/* 562 */       int val = in.readInt();
/* 563 */       put(key, val);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TObjectIntHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */