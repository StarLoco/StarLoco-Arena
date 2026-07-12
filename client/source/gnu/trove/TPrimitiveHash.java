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
/*     */ public abstract class TPrimitiveHash
/*     */   extends THash
/*     */ {
/*     */   protected transient byte[] _states;
/*     */   protected static final byte FREE = 0;
/*     */   protected static final byte FULL = 1;
/*     */   protected static final byte REMOVED = 2;
/*     */   
/*     */   public TPrimitiveHash() {}
/*     */   
/*     */   public TPrimitiveHash(int initialCapacity) {
/*  68 */     this(initialCapacity, 0.5F);
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
/*     */   public TPrimitiveHash(int initialCapacity, float loadFactor) {
/*  82 */     this._loadFactor = loadFactor;
/*  83 */     setUp((int)Math.ceil((initialCapacity / loadFactor)));
/*     */   }
/*     */   
/*     */   public Object clone() {
/*  87 */     TPrimitiveHash h = (TPrimitiveHash)super.clone();
/*  88 */     h._states = (byte[])this._states.clone();
/*  89 */     return h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int capacity() {
/*  99 */     return this._states.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeAt(int index) {
/* 108 */     this._states[index] = 2;
/* 109 */     super.removeAt(index);
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
/* 122 */     int capacity = super.setUp(initialCapacity);
/* 123 */     this._states = new byte[capacity];
/* 124 */     return capacity;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TPrimitiveHash.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */