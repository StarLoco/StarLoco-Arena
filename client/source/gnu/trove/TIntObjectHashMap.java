/*     */ package gnu.trove;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TIntObjectHashMap<V>
/*     */   extends TIntHash
/*     */   implements Externalizable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected transient V[] _values;
/*     */   
/*     */   public TIntObjectHashMap() {}
/*     */   
/*     */   public TIntObjectHashMap(int initialCapacity) {
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
/*     */   public TIntObjectHashMap(int initialCapacity, float loadFactor) {
/*  73 */     super(initialCapacity, loadFactor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntObjectHashMap(TIntHashingStrategy strategy) {
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
/*     */   public TIntObjectHashMap(int initialCapacity, TIntHashingStrategy strategy) {
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
/*     */   public TIntObjectHashMap(int initialCapacity, float loadFactor, TIntHashingStrategy strategy) {
/* 107 */     super(initialCapacity, loadFactor, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntObjectHashMap<V> clone() {
/* 114 */     TIntObjectHashMap<V> m = (TIntObjectHashMap<V>)super.clone();
/* 115 */     m._values = (V[])this._values.clone();
/* 116 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntObjectIterator<V> iterator() {
/* 123 */     return new TIntObjectIterator<V>(this);
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
/* 137 */     this._values = (V[])new Object[capacity];
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
/*     */   public V put(int key, V value) {
/* 151 */     V previous = null;
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
/* 178 */     V[] oldVals = this._values;
/* 179 */     byte[] oldStates = this._states;
/*     */     
/* 181 */     this._set = new int[newCapacity];
/* 182 */     this._values = (V[])new Object[newCapacity];
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
/*     */   public V get(int key) {
/* 203 */     int index = index(key);
/* 204 */     return (index < 0) ? null : this._values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 212 */     super.clear();
/* 213 */     int[] keys = this._set;
/* 214 */     V[] arrayOfV = this._values;
/* 215 */     byte[] states = this._states;
/*     */     
/* 217 */     for (int i = keys.length; i-- > 0; ) {
/* 218 */       keys[i] = 0;
/* 219 */       arrayOfV[i] = null;
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
/*     */   public V remove(int key) {
/* 231 */     V prev = null;
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
/* 248 */     if (!(other instanceof TIntObjectHashMap)) {
/* 249 */       return false;
/*     */     }
/* 251 */     TIntObjectHashMap that = (TIntObjectHashMap)other;
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
/*     */   private final class HashProcedure implements TIntObjectProcedure {
/* 265 */     private int h = 0;
/*     */     
/*     */     public int getHashCode() {
/* 268 */       return this.h;
/*     */     }
/*     */     
/*     */     public final boolean execute(int key, Object value) {
/* 272 */       this.h += TIntObjectHashMap.this._hashingStrategy.computeHashCode(key) ^ HashFunctions.hash(value);
/* 273 */       return true;
/*     */     }
/*     */     
/*     */     private HashProcedure() {}
/*     */   }
/*     */   
/*     */   private static final class EqProcedure implements TIntObjectProcedure {
/*     */     EqProcedure(TIntObjectHashMap otherMap) {
/* 281 */       this._otherMap = otherMap;
/*     */     }
/*     */     private final TIntObjectHashMap _otherMap;
/*     */     public final boolean execute(int key, Object value) {
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
/*     */     private final boolean eq(Object o1, Object o2) {
/* 296 */       return (o1 == o2 || (o1 != null && o1.equals(o2)));
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
/* 307 */     this._values[index] = null;
/* 308 */     super.removeAt(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getValues() {
/* 317 */     Object[] vals = new Object[size()];
/* 318 */     V[] v = this._values;
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
/*     */   public boolean containsValue(V val) {
/* 354 */     byte[] states = this._states;
/* 355 */     V[] vals = this._values;
/*     */ 
/*     */ 
/*     */     
/* 359 */     if (null == val) {
/* 360 */       for (int i = vals.length; i-- > 0;) {
/* 361 */         if (states[i] == 1 && val == vals[i])
/*     */         {
/* 363 */           return true;
/*     */         }
/*     */       } 
/*     */     } else {
/* 367 */       for (int i = vals.length; i-- > 0;) {
/* 368 */         if (states[i] == 1 && (val == vals[i] || val.equals(vals[i])))
/*     */         {
/* 370 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 374 */     return false;
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
/* 385 */     return contains(key);
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
/* 396 */     return forEach(procedure);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean forEachValue(TObjectProcedure<V> procedure) {
/* 407 */     byte[] states = this._states;
/* 408 */     V[] values = this._values;
/* 409 */     for (int i = values.length; i-- > 0;) {
/* 410 */       if (states[i] == 1 && !procedure.execute(values[i])) {
/* 411 */         return false;
/*     */       }
/*     */     } 
/* 414 */     return true;
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
/*     */   public boolean forEachEntry(TIntObjectProcedure<V> procedure) {
/* 426 */     byte[] states = this._states;
/* 427 */     int[] keys = this._set;
/* 428 */     V[] values = this._values;
/* 429 */     for (int i = keys.length; i-- > 0;) {
/* 430 */       if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
/* 431 */         return false;
/*     */       }
/*     */     } 
/* 434 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retainEntries(TIntObjectProcedure<V> procedure) {
/* 445 */     boolean modified = false;
/* 446 */     byte[] states = this._states;
/* 447 */     int[] keys = this._set;
/* 448 */     V[] values = this._values;
/* 449 */     for (int i = keys.length; i-- > 0;) {
/* 450 */       if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
/* 451 */         removeAt(i);
/* 452 */         modified = true;
/*     */       } 
/*     */     } 
/* 455 */     return modified;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformValues(TObjectFunction<V, V> function) {
/* 464 */     byte[] states = this._states;
/* 465 */     V[] values = this._values;
/* 466 */     for (int i = values.length; i-- > 0;) {
/* 467 */       if (states[i] == 1) {
/* 468 */         values[i] = function.execute(values[i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 478 */     out.writeByte(0);
/*     */ 
/*     */     
/* 481 */     out.writeInt(this._size);
/*     */ 
/*     */     
/* 484 */     SerializationProcedure writeProcedure = new SerializationProcedure(out);
/* 485 */     if (!forEachEntry(writeProcedure)) {
/* 486 */       throw writeProcedure.exception;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 494 */     in.readByte();
/*     */ 
/*     */     
/* 497 */     int size = in.readInt();
/* 498 */     setUp(size);
/*     */ 
/*     */     
/* 501 */     while (size-- > 0) {
/* 502 */       int key = in.readInt();
/* 503 */       V val = (V)in.readObject();
/* 504 */       put(key, val);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TIntObjectHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */