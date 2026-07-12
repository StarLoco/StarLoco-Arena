/*     */ package com.sun.gluegen.runtime;
/*     */ 
/*     */ import java.nio.Buffer;
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
/*     */ public class BufferFactory
/*     */ {
/*     */   public static final int SIZEOF_BYTE = 1;
/*     */   public static final int SIZEOF_SHORT = 2;
/*     */   public static final int SIZEOF_CHAR = 2;
/*     */   public static final int SIZEOF_INT = 4;
/*     */   public static final int SIZEOF_FLOAT = 4;
/*     */   public static final int SIZEOF_LONG = 8;
/*     */   public static final int SIZEOF_DOUBLE = 8;
/*     */   
/*     */   public static ByteBuffer newDirectByteBuffer(int paramInt) {
/*  54 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(paramInt);
/*  55 */     byteBuffer.order(ByteOrder.nativeOrder());
/*  56 */     return byteBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDirect(Buffer paramBuffer) {
/*  63 */     if (paramBuffer == null) {
/*  64 */       return true;
/*     */     }
/*  66 */     if (paramBuffer instanceof ByteBuffer)
/*  67 */       return ((ByteBuffer)paramBuffer).isDirect(); 
/*  68 */     if (paramBuffer instanceof FloatBuffer)
/*  69 */       return ((FloatBuffer)paramBuffer).isDirect(); 
/*  70 */     if (paramBuffer instanceof DoubleBuffer)
/*  71 */       return ((DoubleBuffer)paramBuffer).isDirect(); 
/*  72 */     if (paramBuffer instanceof CharBuffer)
/*  73 */       return ((CharBuffer)paramBuffer).isDirect(); 
/*  74 */     if (paramBuffer instanceof ShortBuffer)
/*  75 */       return ((ShortBuffer)paramBuffer).isDirect(); 
/*  76 */     if (paramBuffer instanceof IntBuffer)
/*  77 */       return ((IntBuffer)paramBuffer).isDirect(); 
/*  78 */     if (paramBuffer instanceof LongBuffer) {
/*  79 */       return ((LongBuffer)paramBuffer).isDirect();
/*     */     }
/*  81 */     throw new RuntimeException("Unexpected buffer type " + paramBuffer.getClass().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDirectBufferByteOffset(Buffer paramBuffer) {
/*  90 */     if (paramBuffer == null) {
/*  91 */       return 0;
/*     */     }
/*  93 */     if (paramBuffer instanceof ByteBuffer)
/*  94 */       return paramBuffer.position(); 
/*  95 */     if (paramBuffer instanceof FloatBuffer)
/*  96 */       return paramBuffer.position() * 4; 
/*  97 */     if (paramBuffer instanceof IntBuffer)
/*  98 */       return paramBuffer.position() * 4; 
/*  99 */     if (paramBuffer instanceof ShortBuffer)
/* 100 */       return paramBuffer.position() * 2; 
/* 101 */     if (paramBuffer instanceof DoubleBuffer)
/* 102 */       return paramBuffer.position() * 8; 
/* 103 */     if (paramBuffer instanceof LongBuffer)
/* 104 */       return paramBuffer.position() * 8; 
/* 105 */     if (paramBuffer instanceof CharBuffer) {
/* 106 */       return paramBuffer.position() * 2;
/*     */     }
/*     */     
/* 109 */     throw new RuntimeException("Disallowed array backing store type in buffer " + paramBuffer.getClass().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getArray(Buffer paramBuffer) {
/* 118 */     if (paramBuffer == null) {
/* 119 */       return null;
/*     */     }
/* 121 */     if (paramBuffer instanceof ByteBuffer)
/* 122 */       return ((ByteBuffer)paramBuffer).array(); 
/* 123 */     if (paramBuffer instanceof FloatBuffer)
/* 124 */       return ((FloatBuffer)paramBuffer).array(); 
/* 125 */     if (paramBuffer instanceof IntBuffer)
/* 126 */       return ((IntBuffer)paramBuffer).array(); 
/* 127 */     if (paramBuffer instanceof ShortBuffer)
/* 128 */       return ((ShortBuffer)paramBuffer).array(); 
/* 129 */     if (paramBuffer instanceof DoubleBuffer)
/* 130 */       return ((DoubleBuffer)paramBuffer).array(); 
/* 131 */     if (paramBuffer instanceof LongBuffer)
/* 132 */       return ((LongBuffer)paramBuffer).array(); 
/* 133 */     if (paramBuffer instanceof CharBuffer) {
/* 134 */       return ((CharBuffer)paramBuffer).array();
/*     */     }
/*     */     
/* 137 */     throw new RuntimeException("Disallowed array backing store type in buffer " + paramBuffer.getClass().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIndirectBufferByteOffset(Buffer paramBuffer) {
/* 148 */     if (paramBuffer == null) {
/* 149 */       return 0;
/*     */     }
/* 151 */     int i = paramBuffer.position();
/* 152 */     if (paramBuffer instanceof ByteBuffer)
/* 153 */       return ((ByteBuffer)paramBuffer).arrayOffset() + i; 
/* 154 */     if (paramBuffer instanceof FloatBuffer)
/* 155 */       return 4 * (((FloatBuffer)paramBuffer).arrayOffset() + i); 
/* 156 */     if (paramBuffer instanceof IntBuffer)
/* 157 */       return 4 * (((IntBuffer)paramBuffer).arrayOffset() + i); 
/* 158 */     if (paramBuffer instanceof ShortBuffer)
/* 159 */       return 2 * (((ShortBuffer)paramBuffer).arrayOffset() + i); 
/* 160 */     if (paramBuffer instanceof DoubleBuffer)
/* 161 */       return 8 * (((DoubleBuffer)paramBuffer).arrayOffset() + i); 
/* 162 */     if (paramBuffer instanceof LongBuffer)
/* 163 */       return 8 * (((LongBuffer)paramBuffer).arrayOffset() + i); 
/* 164 */     if (paramBuffer instanceof CharBuffer) {
/* 165 */       return 2 * (((CharBuffer)paramBuffer).arrayOffset() + i);
/*     */     }
/*     */     
/* 168 */     throw new RuntimeException("Unknown buffer type " + paramBuffer.getClass().getName());
/*     */   }
/*     */   
/*     */   public static void rangeCheck(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 172 */     if (paramArrayOfbyte == null) {
/*     */       return;
/*     */     }
/*     */     
/* 176 */     if (paramArrayOfbyte.length < paramInt1 + paramInt2) {
/* 177 */       throw new ArrayIndexOutOfBoundsException("Required " + paramInt2 + " elements in array, only had " + (paramArrayOfbyte.length - paramInt1));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void rangeCheck(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 182 */     if (paramArrayOfchar == null) {
/*     */       return;
/*     */     }
/*     */     
/* 186 */     if (paramArrayOfchar.length < paramInt1 + paramInt2) {
/* 187 */       throw new ArrayIndexOutOfBoundsException("Required " + paramInt2 + " elements in array, only had " + (paramArrayOfchar.length - paramInt1));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void rangeCheck(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 192 */     if (paramArrayOfshort == null) {
/*     */       return;
/*     */     }
/*     */     
/* 196 */     if (paramArrayOfshort.length < paramInt1 + paramInt2) {
/* 197 */       throw new ArrayIndexOutOfBoundsException("Required " + paramInt2 + " elements in array, only had " + (paramArrayOfshort.length - paramInt1));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void rangeCheck(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 202 */     if (paramArrayOfint == null) {
/*     */       return;
/*     */     }
/*     */     
/* 206 */     if (paramArrayOfint.length < paramInt1 + paramInt2) {
/* 207 */       throw new ArrayIndexOutOfBoundsException("Required " + paramInt2 + " elements in array, only had " + (paramArrayOfint.length - paramInt1));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void rangeCheck(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 212 */     if (paramArrayOflong == null) {
/*     */       return;
/*     */     }
/*     */     
/* 216 */     if (paramArrayOflong.length < paramInt1 + paramInt2) {
/* 217 */       throw new ArrayIndexOutOfBoundsException("Required " + paramInt2 + " elements in array, only had " + (paramArrayOflong.length - paramInt1));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void rangeCheck(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/* 222 */     if (paramArrayOffloat == null) {
/*     */       return;
/*     */     }
/*     */     
/* 226 */     if (paramArrayOffloat.length < paramInt1 + paramInt2) {
/* 227 */       throw new ArrayIndexOutOfBoundsException("Required " + paramInt2 + " elements in array, only had " + (paramArrayOffloat.length - paramInt1));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void rangeCheck(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/* 232 */     if (paramArrayOfdouble == null) {
/*     */       return;
/*     */     }
/*     */     
/* 236 */     if (paramArrayOfdouble.length < paramInt1 + paramInt2) {
/* 237 */       throw new ArrayIndexOutOfBoundsException("Required " + paramInt2 + " elements in array, only had " + (paramArrayOfdouble.length - paramInt1));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void rangeCheck(Buffer paramBuffer, int paramInt) {
/* 242 */     if (paramBuffer == null) {
/*     */       return;
/*     */     }
/*     */     
/* 246 */     if (paramBuffer.remaining() < paramInt) {
/* 247 */       throw new IndexOutOfBoundsException("Required " + paramInt + " remaining elements in buffer, only had " + paramBuffer.remaining());
/*     */     }
/*     */   }
/*     */   
/*     */   public static void rangeCheckBytes(Buffer paramBuffer, int paramInt) {
/* 252 */     if (paramBuffer == null) {
/*     */       return;
/*     */     }
/*     */     
/* 256 */     int i = paramBuffer.remaining();
/* 257 */     int j = 0;
/* 258 */     if (paramBuffer instanceof ByteBuffer) {
/* 259 */       j = i;
/* 260 */     } else if (paramBuffer instanceof FloatBuffer) {
/* 261 */       j = i * 4;
/* 262 */     } else if (paramBuffer instanceof IntBuffer) {
/* 263 */       j = i * 4;
/* 264 */     } else if (paramBuffer instanceof ShortBuffer) {
/* 265 */       j = i * 2;
/* 266 */     } else if (paramBuffer instanceof DoubleBuffer) {
/* 267 */       j = i * 8;
/* 268 */     } else if (paramBuffer instanceof LongBuffer) {
/* 269 */       j = i * 8;
/* 270 */     } else if (paramBuffer instanceof CharBuffer) {
/* 271 */       j = i * 2;
/*     */     } 
/* 273 */     if (j < paramInt)
/* 274 */       throw new IndexOutOfBoundsException("Required " + paramInt + " remaining bytes in buffer, only had " + j); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\sun\gluegen\runtime\BufferFactory.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */