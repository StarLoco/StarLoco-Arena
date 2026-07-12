/*     */ package com.ankamagames.framework.kernel.core.common.collections;
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
/*     */ public class ByteArray
/*     */ {
/*     */   private static final int DEFAULT_GROWTH = 10;
/*     */   protected byte[] m_baseArray;
/*     */   protected int m_capacity;
/*     */   protected int m_size;
/*     */   protected int m_growth;
/*     */   
/*     */   public ByteArray()
/*     */   {
/*  25 */     this.m_baseArray = new byte[10];
/*  26 */     this.m_capacity = 10;
/*  27 */     this.m_size = 0;
/*  28 */     this.m_growth = 10;
/*     */   }
/*     */   
/*     */   public ByteArray(ByteArray array) {
/*  32 */     this.m_capacity = (this.m_size = array.m_size);
/*  33 */     this.m_baseArray = new byte[array.m_size];
/*  34 */     this.m_growth = array.m_growth;
/*     */   }
/*     */   
/*     */   public ByteArray(int size) {
/*  38 */     if (size < 0)
/*  39 */       throw new IllegalArgumentException("La taille du tableau doit être >= 0");
/*  40 */     this.m_baseArray = new byte[size];
/*  41 */     this.m_capacity = size;
/*  42 */     this.m_size = 0;
/*  43 */     this.m_growth = 10;
/*     */   }
/*     */   
/*     */   public ByteArray(int size, int growth) {
/*  47 */     if (size < 0)
/*  48 */       throw new IllegalArgumentException("La taille du tableau doit être >= 0");
/*  49 */     if (growth < 1) {
/*  50 */       throw new IllegalArgumentException("L'incrément de taille growth doit être >= 1");
/*     */     }
/*  52 */     this.m_baseArray = new byte[size];
/*  53 */     this.m_capacity = size;
/*  54 */     this.m_size = 0;
/*  55 */     this.m_growth = growth;
/*     */   }
/*     */   
/*     */   public void put(byte value) {
/*  59 */     ensureCapacity(this.m_size + 1);
/*  60 */     this.m_baseArray[this.m_size] = value;
/*  61 */     this.m_size += 1;
/*     */   }
/*     */   
/*     */   public void put(byte[] values) {
/*  65 */     int len = values.length;
/*  66 */     ensureCapacity(this.m_size + len);
/*  67 */     System.arraycopy(values, 0, this.m_baseArray, this.m_size, len);
/*  68 */     this.m_size += len;
/*     */   }
/*     */   
/*     */   public void put(byte[] values, int len) {
/*  72 */     ensureCapacity(this.m_size + len);
/*  73 */     System.arraycopy(values, 0, this.m_baseArray, this.m_size, len);
/*  74 */     this.m_size += len;
/*     */   }
/*     */   
/*     */   public void put(byte[] values, int from, int len) {
/*  78 */     ensureCapacity(this.m_size + len - from);
/*  79 */     System.arraycopy(values, from, this.m_baseArray, this.m_size, len);
/*  80 */     this.m_size += len;
/*     */   }
/*     */   
/*     */   public void put(ByteArray array) {
/*  84 */     put(array.m_baseArray, 0, array.m_size);
/*     */   }
/*     */   
/*     */   public void putBoolean(boolean v) {
/*  88 */     put((byte)(v ? 1 : 0));
/*     */   }
/*     */   
/*     */   public void putChar(char v) {
/*  92 */     put((byte)(0xFF & v >> '\b'));
/*  93 */     put((byte)(0xFF & v));
/*     */   }
/*     */   
/*     */   public void putShort(short v) {
/*  97 */     put((byte)(0xFF & v >> 8));
/*  98 */     put((byte)(0xFF & v));
/*     */   }
/*     */   
/*     */   public void putInt(int v) {
/* 102 */     put(new byte[] {
/* 103 */       (byte)(0xFF & v >> 24), 
/* 104 */       (byte)(0xFF & v >> 16), 
/* 105 */       (byte)(0xFF & v >> 8), 
/* 106 */       (byte)(0xFF & v) });
/*     */   }
/*     */   
/*     */   public void putLong(long v)
/*     */   {
/* 111 */     put(new byte[] {
/* 112 */       (byte)(int)(0xFF & v >> 56), 
/* 113 */       (byte)(int)(0xFF & v >> 48), 
/* 114 */       (byte)(int)(0xFF & v >> 40), 
/* 115 */       (byte)(int)(0xFF & v >> 32), 
/* 116 */       (byte)(int)(0xFF & v >> 24), 
/* 117 */       (byte)(int)(0xFF & v >> 16), 
/* 118 */       (byte)(int)(0xFF & v >> 8), 
/* 119 */       (byte)(int)(0xFF & v) });
/*     */   }
/*     */   
/*     */   public void putFloat(float v)
/*     */   {
/* 124 */     putInt(Float.floatToIntBits(v));
/*     */   }
/*     */   
/*     */   public void putDouble(double v) {
/* 128 */     putLong(Double.doubleToLongBits(v));
/*     */   }
/*     */   
/*     */   public byte get(int index) {
/* 132 */     return this.m_baseArray[index];
/*     */   }
/*     */   
/*     */   public int size() {
/* 136 */     return this.m_size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] internalArray()
/*     */   {
/* 144 */     return this.m_baseArray;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] toArray()
/*     */   {
/* 152 */     byte[] tmp = new byte[this.m_size];
/* 153 */     System.arraycopy(this.m_baseArray, 0, tmp, 0, this.m_size);
/* 154 */     return tmp;
/*     */   }
/*     */   
/*     */   private void ensureCapacity(int capacity) {
/* 158 */     if (capacity > this.m_capacity)
/*     */     {
/* 160 */       this.m_capacity = (capacity + this.m_growth);
/* 161 */       byte[] tmp = new byte[this.m_capacity];
/* 162 */       System.arraycopy(this.m_baseArray, 0, tmp, 0, this.m_size);
/* 163 */       this.m_baseArray = tmp;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 171 */     Arrays.fill(this.m_baseArray, (byte)0);
/* 172 */     this.m_size = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\collections\ByteArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */