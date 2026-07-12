/*     */ package com.ankamagames.baseImpl.common.clientAndServer.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MersenneTwister
/*     */   extends Random
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2932129847991607657L;
/* 133 */   private static final MersenneTwister _instance = new MersenneTwister();
/*     */   
/*     */ 
/*     */   private static final int N = 624;
/*     */   
/*     */ 
/*     */   private static final int M = 397;
/*     */   
/*     */ 
/*     */   private static final int MATRIX_A = -1727483681;
/*     */   
/*     */   private static final int UPPER_MASK = Integer.MIN_VALUE;
/*     */   
/*     */   private static final int LOWER_MASK = Integer.MAX_VALUE;
/*     */   
/*     */   private static final int TEMPERING_MASK_B = -1658038656;
/*     */   
/*     */   private static final int TEMPERING_MASK_C = -272236544;
/*     */   
/*     */   private int[] mt;
/*     */   
/*     */   private int mti;
/*     */   
/*     */   private int[] mag01;
/*     */   
/*     */   private double __nextNextGaussian;
/*     */   
/*     */   private boolean __haveNextNextGaussian;
/*     */   
/*     */ 
/*     */   public MersenneTwister()
/*     */   {
/* 165 */     this(System.currentTimeMillis());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MersenneTwister(long seed)
/*     */   {
/* 175 */     super(seed);
/* 176 */     setSeed(seed);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public MersenneTwister(int[] array)
/*     */   {
/* 184 */     super(System.currentTimeMillis());
/* 185 */     setSeed(array);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setSeed(long seed)
/*     */   {
/* 197 */     super.setSeed(seed);
/*     */     
/*     */ 
/*     */ 
/* 201 */     this.__haveNextNextGaussian = false;
/*     */     
/* 203 */     this.mt = new int['ɰ'];
/*     */     
/* 205 */     this.mag01 = new int[2];
/* 206 */     this.mag01[0] = 0;
/* 207 */     this.mag01[1] = -1727483681;
/*     */     
/* 209 */     this.mt[0] = ((int)(seed & 0xFFFFFFF));
/* 210 */     for (this.mti = 1; this.mti < 624; this.mti += 1)
/*     */     {
/* 212 */       this.mt[this.mti] = 
/* 213 */         (1812433253 * (this.mt[(this.mti - 1)] ^ this.mt[(this.mti - 1)] >>> 30) + this.mti);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 218 */       this.mt[this.mti] &= 0xFFFFFFFF;
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
/*     */   public synchronized void setSeed(int[] array)
/*     */   {
/* 234 */     setSeed(19650218L);
/* 235 */     int i = 1;int j = 0;
/* 236 */     for (int k = 624 > array.length ? 624 : array.length; 
/* 237 */         k != 0; k--)
/*     */     {
/* 239 */       this.mt[i] = ((this.mt[i] ^ (this.mt[(i - 1)] ^ this.mt[(i - 1)] >>> 30) * 1664525) + array[j] + j);
/* 240 */       this.mt[i] &= 0xFFFFFFFF;
/* 241 */       i++;
/* 242 */       j++;
/* 243 */       if (i >= 624) { this.mt[0] = this.mt['ɯ'];i = 1; }
/* 244 */       if (j >= array.length) j = 0;
/*     */     }
/* 246 */     for (k = 623; k != 0; k--)
/*     */     {
/* 248 */       this.mt[i] = ((this.mt[i] ^ (this.mt[(i - 1)] ^ this.mt[(i - 1)] >>> 30) * 1566083941) - i);
/* 249 */       this.mt[i] &= 0xFFFFFFFF;
/* 250 */       i++;
/* 251 */       if (i >= 624)
/*     */       {
/* 253 */         this.mt[0] = this.mt['ɯ'];i = 1;
/*     */       }
/*     */     }
/* 256 */     this.mt[0] = Integer.MIN_VALUE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected synchronized int next(int bits)
/*     */   {
/* 268 */     if (this.mti >= 624)
/*     */     {
/*     */ 
/*     */ 
/* 272 */       for (int kk = 0; kk < 227; kk++)
/*     */       {
/* 274 */         int y = this.mt[kk] & 0x80000000 | this.mt[(kk + 1)] & 0x7FFFFFFF;
/* 275 */         this.mt[kk] = (this.mt[(kk + 397)] ^ y >>> 1 ^ this.mag01[(y & 0x1)]);
/*     */       }
/* 277 */       for (; kk < 623; kk++)
/*     */       {
/* 279 */         int y = this.mt[kk] & 0x80000000 | this.mt[(kk + 1)] & 0x7FFFFFFF;
/* 280 */         this.mt[kk] = (this.mt[(kk + 65309)] ^ y >>> 1 ^ this.mag01[(y & 0x1)]);
/*     */       }
/* 282 */       int y = this.mt['ɯ'] & 0x80000000 | this.mt[0] & 0x7FFFFFFF;
/* 283 */       this.mt['ɯ'] = (this.mt['ƌ'] ^ y >>> 1 ^ this.mag01[(y & 0x1)]);
/*     */       
/* 285 */       this.mti = 0;
/*     */     }
/*     */     
/* 288 */     int y = this.mt[(this.mti++)];
/* 289 */     y ^= y >>> 11;
/* 290 */     y ^= y << 7 & 0x9D2C5680;
/* 291 */     y ^= y << 15 & 0xEFC60000;
/* 292 */     y ^= y >>> 18;
/*     */     
/* 294 */     return y >>> 32 - bits;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private synchronized void writeObject(ObjectOutputStream out)
/*     */     throws IOException
/*     */   {
/* 304 */     out.defaultWriteObject();
/*     */   }
/*     */   
/*     */ 
/*     */   private synchronized void readObject(ObjectInputStream in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 311 */     in.defaultReadObject();
/*     */   }
/*     */   
/*     */   public boolean nextBoolean()
/*     */   {
/* 316 */     return next(1) != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean nextBoolean(float probability)
/*     */   {
/* 326 */     if ((probability < 0.0F) || (probability > 1.0F))
/* 327 */       throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
/* 328 */     if (probability == 0.0F) return false;
/* 329 */     if (probability == 1.0F) return true;
/* 330 */     return nextFloat() < probability;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean nextBoolean(double probability)
/*     */   {
/* 339 */     if ((probability < 0.0D) || (probability > 1.0D))
/* 340 */       throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
/* 341 */     if (probability == 0.0D) return false;
/* 342 */     if (probability == 1.0D) return true;
/* 343 */     return nextDouble() < probability;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int nextInt(int n)
/*     */   {
/* 350 */     if (n < 0) {
/* 351 */       throw new IllegalArgumentException("n must be >= 0");
/*     */     }
/* 353 */     if (n == 0) {
/* 354 */       return 0;
/*     */     }
/* 356 */     if ((n & -n) == n) {
/* 357 */       return (int)(n * next(31) >> 31);
/*     */     }
/*     */     int bits;
/*     */     int val;
/*     */     do {
/* 362 */       bits = next(31);
/* 363 */       val = bits % n;
/*     */     }
/* 365 */     while (bits - val + (n - 1) < 0);
/* 366 */     return val;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long nextLong(long n)
/*     */   {
/* 375 */     if (n < 0L) {
/* 376 */       throw new IllegalArgumentException("n must be > 0");
/*     */     }
/* 378 */     if (n == 0L) {
/* 379 */       return 0L;
/*     */     }
/*     */     long bits;
/*     */     long val;
/*     */     do {
/* 384 */       bits = nextLong() >>> 1;
/* 385 */       val = bits % n;
/*     */     }
/* 387 */     while (bits - val + (n - 1L) < 0L);
/* 388 */     return val;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double nextDouble()
/*     */   {
/* 396 */     return ((next(26) << 27) + next(27)) / 
/* 397 */       9.007199254740992E15D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float nextFloat()
/*     */   {
/* 405 */     return next(24) / 1.6777216E7F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void nextBytes(byte[] bytes)
/*     */   {
/* 414 */     for (int x = 0; x < bytes.length; x++) { bytes[x] = ((byte)next(8));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public char nextChar()
/*     */   {
/* 422 */     return (char)next(16);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public short nextShort()
/*     */   {
/* 429 */     return (short)next(16);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public byte nextByte()
/*     */   {
/* 436 */     return (byte)next(8);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized double nextGaussian()
/*     */   {
/* 448 */     if (this.__haveNextNextGaussian)
/*     */     {
/* 450 */       this.__haveNextNextGaussian = false;
/* 451 */       return this.__nextNextGaussian;
/*     */     }
/*     */     double v1;
/*     */     double v2;
/*     */     double s;
/*     */     do
/*     */     {
/* 458 */       v1 = 2.0D * nextDouble() - 1.0D;
/* 459 */       v2 = 2.0D * nextDouble() - 1.0D;
/* 460 */       s = v1 * v1 + v2 * v2;
/* 461 */     } while ((s >= 1.0D) || (s == 0.0D));
/* 462 */     double multiplier = Math.sqrt(-2.0D * Math.log(s) / s);
/* 463 */     this.__nextNextGaussian = (v2 * multiplier);
/* 464 */     this.__haveNextNextGaussian = true;
/* 465 */     return v1 * multiplier;
/*     */   }
/*     */   
/*     */   public static final synchronized MersenneTwister getInstance()
/*     */   {
/* 470 */     return _instance;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\utils\MersenneTwister.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */