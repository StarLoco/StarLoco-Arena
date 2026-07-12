/*     */ package com.sun.gluegen.runtime;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.DoubleBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.LongBuffer;
/*     */ import java.nio.ShortBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StructAccessor
/*     */ {
/*     */   private ByteBuffer bb;
/*     */   private CharBuffer cb;
/*     */   private DoubleBuffer db;
/*     */   private FloatBuffer fb;
/*     */   private IntBuffer ib;
/*     */   private LongBuffer lb;
/*     */   private ShortBuffer sb;
/*     */   
/*     */   public StructAccessor(ByteBuffer paramByteBuffer)
/*     */   {
/*  56 */     this.bb = paramByteBuffer.order(ByteOrder.nativeOrder());
/*     */   }
/*     */   
/*     */   public ByteBuffer getBuffer() {
/*  60 */     return this.bb;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ByteBuffer slice(int paramInt1, int paramInt2)
/*     */   {
/*  68 */     this.bb.position(paramInt1);
/*  69 */     this.bb.limit(paramInt1 + paramInt2);
/*  70 */     ByteBuffer localByteBuffer = this.bb.slice();
/*  71 */     this.bb.position(0);
/*  72 */     this.bb.limit(this.bb.capacity());
/*  73 */     return localByteBuffer;
/*     */   }
/*     */   
/*     */   public byte getByteAt(int paramInt)
/*     */   {
/*  78 */     return this.bb.get(paramInt);
/*     */   }
/*     */   
/*     */   public void setByteAt(int paramInt, byte paramByte)
/*     */   {
/*  83 */     this.bb.put(paramInt, paramByte);
/*     */   }
/*     */   
/*     */   public char getCharAt(int paramInt)
/*     */   {
/*  88 */     return charBuffer().get(paramInt);
/*     */   }
/*     */   
/*     */   public void setCharAt(int paramInt, char paramChar)
/*     */   {
/*  93 */     charBuffer().put(paramInt, paramChar);
/*     */   }
/*     */   
/*     */   public double getDoubleAt(int paramInt)
/*     */   {
/*  98 */     return doubleBuffer().get(paramInt);
/*     */   }
/*     */   
/*     */   public void setDoubleAt(int paramInt, double paramDouble)
/*     */   {
/* 103 */     doubleBuffer().put(paramInt, paramDouble);
/*     */   }
/*     */   
/*     */   public float getFloatAt(int paramInt)
/*     */   {
/* 108 */     return floatBuffer().get(paramInt);
/*     */   }
/*     */   
/*     */   public void setFloatAt(int paramInt, float paramFloat)
/*     */   {
/* 113 */     floatBuffer().put(paramInt, paramFloat);
/*     */   }
/*     */   
/*     */   public int getIntAt(int paramInt)
/*     */   {
/* 118 */     return intBuffer().get(paramInt);
/*     */   }
/*     */   
/*     */   public void setIntAt(int paramInt1, int paramInt2)
/*     */   {
/* 123 */     intBuffer().put(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public long getLongAt(int paramInt)
/*     */   {
/* 128 */     return longBuffer().get(paramInt);
/*     */   }
/*     */   
/*     */   public void setLongAt(int paramInt, long paramLong)
/*     */   {
/* 133 */     longBuffer().put(paramInt, paramLong);
/*     */   }
/*     */   
/*     */   public short getShortAt(int paramInt)
/*     */   {
/* 138 */     return shortBuffer().get(paramInt);
/*     */   }
/*     */   
/*     */   public void setShortAt(int paramInt, short paramShort)
/*     */   {
/* 143 */     shortBuffer().put(paramInt, paramShort);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private CharBuffer charBuffer()
/*     */   {
/* 151 */     if (this.cb == null) {
/* 152 */       this.cb = this.bb.asCharBuffer();
/*     */     }
/* 154 */     return this.cb;
/*     */   }
/*     */   
/*     */   private DoubleBuffer doubleBuffer() {
/* 158 */     if (this.db == null) {
/* 159 */       this.db = this.bb.asDoubleBuffer();
/*     */     }
/* 161 */     return this.db;
/*     */   }
/*     */   
/*     */   private FloatBuffer floatBuffer() {
/* 165 */     if (this.fb == null) {
/* 166 */       this.fb = this.bb.asFloatBuffer();
/*     */     }
/* 168 */     return this.fb;
/*     */   }
/*     */   
/*     */   private IntBuffer intBuffer() {
/* 172 */     if (this.ib == null) {
/* 173 */       this.ib = this.bb.asIntBuffer();
/*     */     }
/* 175 */     return this.ib;
/*     */   }
/*     */   
/*     */   private LongBuffer longBuffer() {
/* 179 */     if (this.lb == null) {
/* 180 */       this.lb = this.bb.asLongBuffer();
/*     */     }
/* 182 */     return this.lb;
/*     */   }
/*     */   
/*     */   private ShortBuffer shortBuffer() {
/* 186 */     if (this.sb == null) {
/* 187 */       this.sb = this.bb.asShortBuffer();
/*     */     }
/* 189 */     return this.sb;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\sun\gluegen\runtime\StructAccessor.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       0.7.1
 */