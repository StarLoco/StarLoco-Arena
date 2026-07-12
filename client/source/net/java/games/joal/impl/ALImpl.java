/*      */ package net.java.games.joal.impl;
/*      */ 
/*      */ import com.sun.gluegen.runtime.BufferFactory;
/*      */ import java.nio.Buffer;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.DoubleBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import net.java.games.joal.AL;
/*      */ import net.java.games.joal.ALException;
/*      */ 
/*      */ public class ALImpl
/*      */   implements AL {
/*      */   public void alAuxiliaryEffectSlotf(int paramInt1, int paramInt2, float paramFloat) {
/*   15 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alAuxiliaryEffectSlotf;
/*   16 */     if (l == 0L) {
/*   17 */       throw new ALException("Method \"alAuxiliaryEffectSlotf\" not available");
/*      */     }
/*   19 */     dispatch_alAuxiliaryEffectSlotf0(paramInt1, paramInt2, paramFloat, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alAuxiliaryEffectSlotf0(int paramInt1, int paramInt2, float paramFloat, long paramLong);
/*      */ 
/*      */   
/*      */   public void alAuxiliaryEffectSlotfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/*   28 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/*   29 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alAuxiliaryEffectSlotfv;
/*   30 */     if (l == 0L) {
/*   31 */       throw new ALException("Method \"alAuxiliaryEffectSlotfv\" not available");
/*      */     }
/*   33 */     if (bool) {
/*   34 */       dispatch_alAuxiliaryEffectSlotfv0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/*   36 */       dispatch_alAuxiliaryEffectSlotfv1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alAuxiliaryEffectSlotfv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alAuxiliaryEffectSlotfv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alAuxiliaryEffectSlotfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/*   49 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/*   50 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/*   51 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alAuxiliaryEffectSlotfv;
/*   52 */     if (l == 0L) {
/*   53 */       throw new ALException("Method \"alAuxiliaryEffectSlotfv\" not available");
/*      */     }
/*   55 */     dispatch_alAuxiliaryEffectSlotfv1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alAuxiliaryEffectSloti(int paramInt1, int paramInt2, int paramInt3) {
/*   62 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alAuxiliaryEffectSloti;
/*   63 */     if (l == 0L) {
/*   64 */       throw new ALException("Method \"alAuxiliaryEffectSloti\" not available");
/*      */     }
/*   66 */     dispatch_alAuxiliaryEffectSloti0(paramInt1, paramInt2, paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alAuxiliaryEffectSloti0(int paramInt1, int paramInt2, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alAuxiliaryEffectSlotiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/*   75 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*   76 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alAuxiliaryEffectSlotiv;
/*   77 */     if (l == 0L) {
/*   78 */       throw new ALException("Method \"alAuxiliaryEffectSlotiv\" not available");
/*      */     }
/*   80 */     if (bool) {
/*   81 */       dispatch_alAuxiliaryEffectSlotiv0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*   83 */       dispatch_alAuxiliaryEffectSlotiv1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alAuxiliaryEffectSlotiv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alAuxiliaryEffectSlotiv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alAuxiliaryEffectSlotiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/*   96 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/*   97 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*   98 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alAuxiliaryEffectSlotiv;
/*   99 */     if (l == 0L) {
/*  100 */       throw new ALException("Method \"alAuxiliaryEffectSlotiv\" not available");
/*      */     }
/*  102 */     dispatch_alAuxiliaryEffectSlotiv1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alBuffer3f(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3) {
/*  109 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alBuffer3f;
/*  110 */     if (l == 0L) {
/*  111 */       throw new ALException("Method \"alBuffer3f\" not available");
/*      */     }
/*  113 */     dispatch_alBuffer3f0(paramInt1, paramInt2, paramFloat1, paramFloat2, paramFloat3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alBuffer3f0(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alBuffer3i(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  122 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alBuffer3i;
/*  123 */     if (l == 0L) {
/*  124 */       throw new ALException("Method \"alBuffer3i\" not available");
/*      */     }
/*  126 */     dispatch_alBuffer3i0(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alBuffer3i0(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong);
/*      */ 
/*      */   
/*      */   public void alBufferData(int paramInt1, int paramInt2, Buffer paramBuffer, int paramInt3, int paramInt4) {
/*  135 */     boolean bool = BufferFactory.isDirect(paramBuffer);
/*  136 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alBufferData;
/*  137 */     if (l == 0L) {
/*  138 */       throw new ALException("Method \"alBufferData\" not available");
/*      */     }
/*  140 */     if (bool) {
/*  141 */       dispatch_alBufferData0(paramInt1, paramInt2, paramBuffer, BufferFactory.getDirectBufferByteOffset(paramBuffer), paramInt3, paramInt4, l);
/*      */     } else {
/*  143 */       dispatch_alBufferData1(paramInt1, paramInt2, BufferFactory.getArray(paramBuffer), BufferFactory.getIndirectBufferByteOffset(paramBuffer), paramInt3, paramInt4, l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alBufferData0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, int paramInt4, int paramInt5, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alBufferData1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, int paramInt4, int paramInt5, long paramLong);
/*      */ 
/*      */   
/*      */   public void alBufferf(int paramInt1, int paramInt2, float paramFloat) {
/*  156 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alBufferf;
/*  157 */     if (l == 0L) {
/*  158 */       throw new ALException("Method \"alBufferf\" not available");
/*      */     }
/*  160 */     dispatch_alBufferf0(paramInt1, paramInt2, paramFloat, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alBufferf0(int paramInt1, int paramInt2, float paramFloat, long paramLong);
/*      */ 
/*      */   
/*      */   public void alBufferfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/*  169 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/*  170 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alBufferfv;
/*  171 */     if (l == 0L) {
/*  172 */       throw new ALException("Method \"alBufferfv\" not available");
/*      */     }
/*  174 */     if (bool) {
/*  175 */       dispatch_alBufferfv0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/*  177 */       dispatch_alBufferfv1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alBufferfv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alBufferfv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alBufferfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/*  190 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/*  191 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/*  192 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alBufferfv;
/*  193 */     if (l == 0L) {
/*  194 */       throw new ALException("Method \"alBufferfv\" not available");
/*      */     }
/*  196 */     dispatch_alBufferfv1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alBufferi(int paramInt1, int paramInt2, int paramInt3) {
/*  203 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alBufferi;
/*  204 */     if (l == 0L) {
/*  205 */       throw new ALException("Method \"alBufferi\" not available");
/*      */     }
/*  207 */     dispatch_alBufferi0(paramInt1, paramInt2, paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alBufferi0(int paramInt1, int paramInt2, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alBufferiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/*  216 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  217 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alBufferiv;
/*  218 */     if (l == 0L) {
/*  219 */       throw new ALException("Method \"alBufferiv\" not available");
/*      */     }
/*  221 */     if (bool) {
/*  222 */       dispatch_alBufferiv0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  224 */       dispatch_alBufferiv1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alBufferiv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alBufferiv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alBufferiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/*  237 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/*  238 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  239 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alBufferiv;
/*  240 */     if (l == 0L) {
/*  241 */       throw new ALException("Method \"alBufferiv\" not available");
/*      */     }
/*  243 */     dispatch_alBufferiv1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alDeleteAuxiliaryEffectSlots(int paramInt, IntBuffer paramIntBuffer) {
/*  250 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  251 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDeleteAuxiliaryEffectSlots;
/*  252 */     if (l == 0L) {
/*  253 */       throw new ALException("Method \"alDeleteAuxiliaryEffectSlots\" not available");
/*      */     }
/*  255 */     if (bool) {
/*  256 */       dispatch_alDeleteAuxiliaryEffectSlots0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  258 */       dispatch_alDeleteAuxiliaryEffectSlots1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alDeleteAuxiliaryEffectSlots0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alDeleteAuxiliaryEffectSlots1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alDeleteAuxiliaryEffectSlots(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  271 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/*  272 */       throw new ALException("array offset argument \"slots_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  273 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDeleteAuxiliaryEffectSlots;
/*  274 */     if (l == 0L) {
/*  275 */       throw new ALException("Method \"alDeleteAuxiliaryEffectSlots\" not available");
/*      */     }
/*  277 */     dispatch_alDeleteAuxiliaryEffectSlots1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alDeleteBuffers(int paramInt, IntBuffer paramIntBuffer) {
/*  284 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  285 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDeleteBuffers;
/*  286 */     if (l == 0L) {
/*  287 */       throw new ALException("Method \"alDeleteBuffers\" not available");
/*      */     }
/*  289 */     if (bool) {
/*  290 */       dispatch_alDeleteBuffers0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  292 */       dispatch_alDeleteBuffers1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alDeleteBuffers0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alDeleteBuffers1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alDeleteBuffers(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  305 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/*  306 */       throw new ALException("array offset argument \"buffers_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  307 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDeleteBuffers;
/*  308 */     if (l == 0L) {
/*  309 */       throw new ALException("Method \"alDeleteBuffers\" not available");
/*      */     }
/*  311 */     dispatch_alDeleteBuffers1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alDeleteEffects(int paramInt, IntBuffer paramIntBuffer) {
/*  318 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  319 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDeleteEffects;
/*  320 */     if (l == 0L) {
/*  321 */       throw new ALException("Method \"alDeleteEffects\" not available");
/*      */     }
/*  323 */     if (bool) {
/*  324 */       dispatch_alDeleteEffects0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  326 */       dispatch_alDeleteEffects1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alDeleteEffects0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alDeleteEffects1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alDeleteEffects(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  339 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/*  340 */       throw new ALException("array offset argument \"effects_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  341 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDeleteEffects;
/*  342 */     if (l == 0L) {
/*  343 */       throw new ALException("Method \"alDeleteEffects\" not available");
/*      */     }
/*  345 */     dispatch_alDeleteEffects1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alDeleteFilters(int paramInt, IntBuffer paramIntBuffer) {
/*  352 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  353 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDeleteFilters;
/*  354 */     if (l == 0L) {
/*  355 */       throw new ALException("Method \"alDeleteFilters\" not available");
/*      */     }
/*  357 */     if (bool) {
/*  358 */       dispatch_alDeleteFilters0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  360 */       dispatch_alDeleteFilters1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alDeleteFilters0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alDeleteFilters1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alDeleteFilters(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  373 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/*  374 */       throw new ALException("array offset argument \"filters_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  375 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDeleteFilters;
/*  376 */     if (l == 0L) {
/*  377 */       throw new ALException("Method \"alDeleteFilters\" not available");
/*      */     }
/*  379 */     dispatch_alDeleteFilters1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alDeleteSources(int paramInt, IntBuffer paramIntBuffer) {
/*  386 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  387 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDeleteSources;
/*  388 */     if (l == 0L) {
/*  389 */       throw new ALException("Method \"alDeleteSources\" not available");
/*      */     }
/*  391 */     if (bool) {
/*  392 */       dispatch_alDeleteSources0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  394 */       dispatch_alDeleteSources1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alDeleteSources0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alDeleteSources1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alDeleteSources(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  407 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/*  408 */       throw new ALException("array offset argument \"sources_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  409 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDeleteSources;
/*  410 */     if (l == 0L) {
/*  411 */       throw new ALException("Method \"alDeleteSources\" not available");
/*      */     }
/*  413 */     dispatch_alDeleteSources1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alDisable(int paramInt) {
/*  420 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDisable;
/*  421 */     if (l == 0L) {
/*  422 */       throw new ALException("Method \"alDisable\" not available");
/*      */     }
/*  424 */     dispatch_alDisable0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alDisable0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public void alDistanceModel(int paramInt) {
/*  433 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDistanceModel;
/*  434 */     if (l == 0L) {
/*  435 */       throw new ALException("Method \"alDistanceModel\" not available");
/*      */     }
/*  437 */     dispatch_alDistanceModel0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alDistanceModel0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public void alDopplerFactor(float paramFloat) {
/*  446 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDopplerFactor;
/*  447 */     if (l == 0L) {
/*  448 */       throw new ALException("Method \"alDopplerFactor\" not available");
/*      */     }
/*  450 */     dispatch_alDopplerFactor0(paramFloat, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alDopplerFactor0(float paramFloat, long paramLong);
/*      */ 
/*      */   
/*      */   public void alDopplerVelocity(float paramFloat) {
/*  459 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alDopplerVelocity;
/*  460 */     if (l == 0L) {
/*  461 */       throw new ALException("Method \"alDopplerVelocity\" not available");
/*      */     }
/*  463 */     dispatch_alDopplerVelocity0(paramFloat, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alDopplerVelocity0(float paramFloat, long paramLong);
/*      */ 
/*      */   
/*      */   public void alEffectf(int paramInt1, int paramInt2, float paramFloat) {
/*  472 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alEffectf;
/*  473 */     if (l == 0L) {
/*  474 */       throw new ALException("Method \"alEffectf\" not available");
/*      */     }
/*  476 */     dispatch_alEffectf0(paramInt1, paramInt2, paramFloat, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alEffectf0(int paramInt1, int paramInt2, float paramFloat, long paramLong);
/*      */ 
/*      */   
/*      */   public void alEffectfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/*  485 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/*  486 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alEffectfv;
/*  487 */     if (l == 0L) {
/*  488 */       throw new ALException("Method \"alEffectfv\" not available");
/*      */     }
/*  490 */     if (bool) {
/*  491 */       dispatch_alEffectfv0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/*  493 */       dispatch_alEffectfv1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alEffectfv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alEffectfv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alEffectfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/*  506 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/*  507 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/*  508 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alEffectfv;
/*  509 */     if (l == 0L) {
/*  510 */       throw new ALException("Method \"alEffectfv\" not available");
/*      */     }
/*  512 */     dispatch_alEffectfv1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alEffecti(int paramInt1, int paramInt2, int paramInt3) {
/*  519 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alEffecti;
/*  520 */     if (l == 0L) {
/*  521 */       throw new ALException("Method \"alEffecti\" not available");
/*      */     }
/*  523 */     dispatch_alEffecti0(paramInt1, paramInt2, paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alEffecti0(int paramInt1, int paramInt2, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alEffectiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/*  532 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  533 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alEffectiv;
/*  534 */     if (l == 0L) {
/*  535 */       throw new ALException("Method \"alEffectiv\" not available");
/*      */     }
/*  537 */     if (bool) {
/*  538 */       dispatch_alEffectiv0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  540 */       dispatch_alEffectiv1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alEffectiv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alEffectiv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alEffectiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/*  553 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/*  554 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  555 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alEffectiv;
/*  556 */     if (l == 0L) {
/*  557 */       throw new ALException("Method \"alEffectiv\" not available");
/*      */     }
/*  559 */     dispatch_alEffectiv1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alEnable(int paramInt) {
/*  566 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alEnable;
/*  567 */     if (l == 0L) {
/*  568 */       throw new ALException("Method \"alEnable\" not available");
/*      */     }
/*  570 */     dispatch_alEnable0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alEnable0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public void alFilterf(int paramInt1, int paramInt2, float paramFloat) {
/*  579 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alFilterf;
/*  580 */     if (l == 0L) {
/*  581 */       throw new ALException("Method \"alFilterf\" not available");
/*      */     }
/*  583 */     dispatch_alFilterf0(paramInt1, paramInt2, paramFloat, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alFilterf0(int paramInt1, int paramInt2, float paramFloat, long paramLong);
/*      */ 
/*      */   
/*      */   public void alFilterfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/*  592 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/*  593 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alFilterfv;
/*  594 */     if (l == 0L) {
/*  595 */       throw new ALException("Method \"alFilterfv\" not available");
/*      */     }
/*  597 */     if (bool) {
/*  598 */       dispatch_alFilterfv0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/*  600 */       dispatch_alFilterfv1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alFilterfv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alFilterfv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alFilterfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/*  613 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/*  614 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/*  615 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alFilterfv;
/*  616 */     if (l == 0L) {
/*  617 */       throw new ALException("Method \"alFilterfv\" not available");
/*      */     }
/*  619 */     dispatch_alFilterfv1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alFilteri(int paramInt1, int paramInt2, int paramInt3) {
/*  626 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alFilteri;
/*  627 */     if (l == 0L) {
/*  628 */       throw new ALException("Method \"alFilteri\" not available");
/*      */     }
/*  630 */     dispatch_alFilteri0(paramInt1, paramInt2, paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alFilteri0(int paramInt1, int paramInt2, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alFilteriv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/*  639 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  640 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alFilteriv;
/*  641 */     if (l == 0L) {
/*  642 */       throw new ALException("Method \"alFilteriv\" not available");
/*      */     }
/*  644 */     if (bool) {
/*  645 */       dispatch_alFilteriv0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  647 */       dispatch_alFilteriv1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alFilteriv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alFilteriv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alFilteriv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/*  660 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/*  661 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  662 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alFilteriv;
/*  663 */     if (l == 0L) {
/*  664 */       throw new ALException("Method \"alFilteriv\" not available");
/*      */     }
/*  666 */     dispatch_alFilteriv1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGenAuxiliaryEffectSlots(int paramInt, IntBuffer paramIntBuffer) {
/*  673 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  674 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGenAuxiliaryEffectSlots;
/*  675 */     if (l == 0L) {
/*  676 */       throw new ALException("Method \"alGenAuxiliaryEffectSlots\" not available");
/*      */     }
/*  678 */     if (bool) {
/*  679 */       dispatch_alGenAuxiliaryEffectSlots0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  681 */       dispatch_alGenAuxiliaryEffectSlots1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGenAuxiliaryEffectSlots0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGenAuxiliaryEffectSlots1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGenAuxiliaryEffectSlots(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  694 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/*  695 */       throw new ALException("array offset argument \"slots_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  696 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGenAuxiliaryEffectSlots;
/*  697 */     if (l == 0L) {
/*  698 */       throw new ALException("Method \"alGenAuxiliaryEffectSlots\" not available");
/*      */     }
/*  700 */     dispatch_alGenAuxiliaryEffectSlots1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGenBuffers(int paramInt, IntBuffer paramIntBuffer) {
/*  707 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  708 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGenBuffers;
/*  709 */     if (l == 0L) {
/*  710 */       throw new ALException("Method \"alGenBuffers\" not available");
/*      */     }
/*  712 */     if (bool) {
/*  713 */       dispatch_alGenBuffers0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  715 */       dispatch_alGenBuffers1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGenBuffers0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGenBuffers1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGenBuffers(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  728 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/*  729 */       throw new ALException("array offset argument \"buffers_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  730 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGenBuffers;
/*  731 */     if (l == 0L) {
/*  732 */       throw new ALException("Method \"alGenBuffers\" not available");
/*      */     }
/*  734 */     dispatch_alGenBuffers1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGenEffects(int paramInt, IntBuffer paramIntBuffer) {
/*  741 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  742 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGenEffects;
/*  743 */     if (l == 0L) {
/*  744 */       throw new ALException("Method \"alGenEffects\" not available");
/*      */     }
/*  746 */     if (bool) {
/*  747 */       dispatch_alGenEffects0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  749 */       dispatch_alGenEffects1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGenEffects0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGenEffects1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGenEffects(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  762 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/*  763 */       throw new ALException("array offset argument \"effects_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  764 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGenEffects;
/*  765 */     if (l == 0L) {
/*  766 */       throw new ALException("Method \"alGenEffects\" not available");
/*      */     }
/*  768 */     dispatch_alGenEffects1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGenFilters(int paramInt, IntBuffer paramIntBuffer) {
/*  775 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  776 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGenFilters;
/*  777 */     if (l == 0L) {
/*  778 */       throw new ALException("Method \"alGenFilters\" not available");
/*      */     }
/*  780 */     if (bool) {
/*  781 */       dispatch_alGenFilters0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  783 */       dispatch_alGenFilters1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGenFilters0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGenFilters1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGenFilters(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  796 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/*  797 */       throw new ALException("array offset argument \"filters_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  798 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGenFilters;
/*  799 */     if (l == 0L) {
/*  800 */       throw new ALException("Method \"alGenFilters\" not available");
/*      */     }
/*  802 */     dispatch_alGenFilters1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGenSources(int paramInt, IntBuffer paramIntBuffer) {
/*  809 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  810 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGenSources;
/*  811 */     if (l == 0L) {
/*  812 */       throw new ALException("Method \"alGenSources\" not available");
/*      */     }
/*  814 */     if (bool) {
/*  815 */       dispatch_alGenSources0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  817 */       dispatch_alGenSources1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGenSources0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGenSources1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGenSources(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  830 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/*  831 */       throw new ALException("array offset argument \"sources_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  832 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGenSources;
/*  833 */     if (l == 0L) {
/*  834 */       throw new ALException("Method \"alGenSources\" not available");
/*      */     }
/*  836 */     dispatch_alGenSources1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetAuxiliaryEffectSlotf(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/*  843 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/*  844 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetAuxiliaryEffectSlotf;
/*  845 */     if (l == 0L) {
/*  846 */       throw new ALException("Method \"alGetAuxiliaryEffectSlotf\" not available");
/*      */     }
/*  848 */     if (bool) {
/*  849 */       dispatch_alGetAuxiliaryEffectSlotf0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/*  851 */       dispatch_alGetAuxiliaryEffectSlotf1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetAuxiliaryEffectSlotf0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetAuxiliaryEffectSlotf1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetAuxiliaryEffectSlotf(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/*  864 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/*  865 */       throw new ALException("array offset argument \"value_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/*  866 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetAuxiliaryEffectSlotf;
/*  867 */     if (l == 0L) {
/*  868 */       throw new ALException("Method \"alGetAuxiliaryEffectSlotf\" not available");
/*      */     }
/*  870 */     dispatch_alGetAuxiliaryEffectSlotf1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetAuxiliaryEffectSlotfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/*  877 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/*  878 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetAuxiliaryEffectSlotfv;
/*  879 */     if (l == 0L) {
/*  880 */       throw new ALException("Method \"alGetAuxiliaryEffectSlotfv\" not available");
/*      */     }
/*  882 */     if (bool) {
/*  883 */       dispatch_alGetAuxiliaryEffectSlotfv0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/*  885 */       dispatch_alGetAuxiliaryEffectSlotfv1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetAuxiliaryEffectSlotfv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetAuxiliaryEffectSlotfv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetAuxiliaryEffectSlotfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/*  898 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/*  899 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/*  900 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetAuxiliaryEffectSlotfv;
/*  901 */     if (l == 0L) {
/*  902 */       throw new ALException("Method \"alGetAuxiliaryEffectSlotfv\" not available");
/*      */     }
/*  904 */     dispatch_alGetAuxiliaryEffectSlotfv1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetAuxiliaryEffectSloti(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/*  911 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  912 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetAuxiliaryEffectSloti;
/*  913 */     if (l == 0L) {
/*  914 */       throw new ALException("Method \"alGetAuxiliaryEffectSloti\" not available");
/*      */     }
/*  916 */     if (bool) {
/*  917 */       dispatch_alGetAuxiliaryEffectSloti0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  919 */       dispatch_alGetAuxiliaryEffectSloti1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetAuxiliaryEffectSloti0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetAuxiliaryEffectSloti1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetAuxiliaryEffectSloti(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/*  932 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/*  933 */       throw new ALException("array offset argument \"value_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  934 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetAuxiliaryEffectSloti;
/*  935 */     if (l == 0L) {
/*  936 */       throw new ALException("Method \"alGetAuxiliaryEffectSloti\" not available");
/*      */     }
/*  938 */     dispatch_alGetAuxiliaryEffectSloti1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetAuxiliaryEffectSlotiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/*  945 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/*  946 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetAuxiliaryEffectSlotiv;
/*  947 */     if (l == 0L) {
/*  948 */       throw new ALException("Method \"alGetAuxiliaryEffectSlotiv\" not available");
/*      */     }
/*  950 */     if (bool) {
/*  951 */       dispatch_alGetAuxiliaryEffectSlotiv0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/*  953 */       dispatch_alGetAuxiliaryEffectSlotiv1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetAuxiliaryEffectSlotiv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetAuxiliaryEffectSlotiv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetAuxiliaryEffectSlotiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/*  966 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/*  967 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/*  968 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetAuxiliaryEffectSlotiv;
/*  969 */     if (l == 0L) {
/*  970 */       throw new ALException("Method \"alGetAuxiliaryEffectSlotiv\" not available");
/*      */     }
/*  972 */     dispatch_alGetAuxiliaryEffectSlotiv1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean alGetBoolean(int paramInt) {
/*  979 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBoolean;
/*  980 */     if (l == 0L) {
/*  981 */       throw new ALException("Method \"alGetBoolean\" not available");
/*      */     }
/*  983 */     return dispatch_alGetBoolean0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native boolean dispatch_alGetBoolean0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetBooleanv(int paramInt, ByteBuffer paramByteBuffer) {
/*  992 */     boolean bool = BufferFactory.isDirect(paramByteBuffer);
/*  993 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBooleanv;
/*  994 */     if (l == 0L) {
/*  995 */       throw new ALException("Method \"alGetBooleanv\" not available");
/*      */     }
/*  997 */     if (bool) {
/*  998 */       dispatch_alGetBooleanv0(paramInt, paramByteBuffer, BufferFactory.getDirectBufferByteOffset(paramByteBuffer), l);
/*      */     } else {
/* 1000 */       dispatch_alGetBooleanv1(paramInt, BufferFactory.getArray(paramByteBuffer), BufferFactory.getIndirectBufferByteOffset(paramByteBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBooleanv0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBooleanv1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetBooleanv(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
/* 1013 */     if (paramArrayOfbyte != null && paramArrayOfbyte.length <= paramInt2)
/* 1014 */       throw new ALException("array offset argument \"data_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfbyte.length + ")"); 
/* 1015 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBooleanv;
/* 1016 */     if (l == 0L) {
/* 1017 */       throw new ALException("Method \"alGetBooleanv\" not available");
/*      */     }
/* 1019 */     dispatch_alGetBooleanv1(paramInt1, paramArrayOfbyte, paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetBuffer3f(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, FloatBuffer paramFloatBuffer2, FloatBuffer paramFloatBuffer3) {
/* 1026 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer1);
/* 1027 */     if (bool != BufferFactory.isDirect(paramFloatBuffer2))
/* 1028 */       throw new ALException("Argument \"value2\" : Buffers passed to this method must all be either direct or indirect"); 
/* 1029 */     if (bool != BufferFactory.isDirect(paramFloatBuffer3))
/* 1030 */       throw new ALException("Argument \"value3\" : Buffers passed to this method must all be either direct or indirect"); 
/* 1031 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBuffer3f;
/* 1032 */     if (l == 0L) {
/* 1033 */       throw new ALException("Method \"alGetBuffer3f\" not available");
/*      */     }
/* 1035 */     if (bool) {
/* 1036 */       dispatch_alGetBuffer3f0(paramInt1, paramInt2, paramFloatBuffer1, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer1), paramFloatBuffer2, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer2), paramFloatBuffer3, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer3), l);
/*      */     } else {
/* 1038 */       dispatch_alGetBuffer3f1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer1), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer1), BufferFactory.getArray(paramFloatBuffer2), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer2), BufferFactory.getArray(paramFloatBuffer3), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer3), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBuffer3f0(int paramInt1, int paramInt2, Object paramObject1, int paramInt3, Object paramObject2, int paramInt4, Object paramObject3, int paramInt5, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBuffer3f1(int paramInt1, int paramInt2, Object paramObject1, int paramInt3, Object paramObject2, int paramInt4, Object paramObject3, int paramInt5, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetBuffer3f(int paramInt1, int paramInt2, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2, int paramInt4, float[] paramArrayOffloat3, int paramInt5) {
/* 1051 */     if (paramArrayOffloat1 != null && paramArrayOffloat1.length <= paramInt3)
/* 1052 */       throw new ALException("array offset argument \"value1_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat1.length + ")"); 
/* 1053 */     if (paramArrayOffloat2 != null && paramArrayOffloat2.length <= paramInt4)
/* 1054 */       throw new ALException("array offset argument \"value2_offset\" (" + paramInt4 + ") equals or exceeds array length (" + paramArrayOffloat2.length + ")"); 
/* 1055 */     if (paramArrayOffloat3 != null && paramArrayOffloat3.length <= paramInt5)
/* 1056 */       throw new ALException("array offset argument \"value3_offset\" (" + paramInt5 + ") equals or exceeds array length (" + paramArrayOffloat3.length + ")"); 
/* 1057 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBuffer3f;
/* 1058 */     if (l == 0L) {
/* 1059 */       throw new ALException("Method \"alGetBuffer3f\" not available");
/*      */     }
/* 1061 */     dispatch_alGetBuffer3f1(paramInt1, paramInt2, paramArrayOffloat1, 4 * paramInt3, paramArrayOffloat2, 4 * paramInt4, paramArrayOffloat3, 4 * paramInt5, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetBuffer3i(int paramInt1, int paramInt2, IntBuffer paramIntBuffer1, IntBuffer paramIntBuffer2, IntBuffer paramIntBuffer3) {
/* 1068 */     boolean bool = BufferFactory.isDirect(paramIntBuffer1);
/* 1069 */     if (bool != BufferFactory.isDirect(paramIntBuffer2))
/* 1070 */       throw new ALException("Argument \"value2\" : Buffers passed to this method must all be either direct or indirect"); 
/* 1071 */     if (bool != BufferFactory.isDirect(paramIntBuffer3))
/* 1072 */       throw new ALException("Argument \"value3\" : Buffers passed to this method must all be either direct or indirect"); 
/* 1073 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBuffer3i;
/* 1074 */     if (l == 0L) {
/* 1075 */       throw new ALException("Method \"alGetBuffer3i\" not available");
/*      */     }
/* 1077 */     if (bool) {
/* 1078 */       dispatch_alGetBuffer3i0(paramInt1, paramInt2, paramIntBuffer1, BufferFactory.getDirectBufferByteOffset(paramIntBuffer1), paramIntBuffer2, BufferFactory.getDirectBufferByteOffset(paramIntBuffer2), paramIntBuffer3, BufferFactory.getDirectBufferByteOffset(paramIntBuffer3), l);
/*      */     } else {
/* 1080 */       dispatch_alGetBuffer3i1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer1), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer1), BufferFactory.getArray(paramIntBuffer2), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer2), BufferFactory.getArray(paramIntBuffer3), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer3), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBuffer3i0(int paramInt1, int paramInt2, Object paramObject1, int paramInt3, Object paramObject2, int paramInt4, Object paramObject3, int paramInt5, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBuffer3i1(int paramInt1, int paramInt2, Object paramObject1, int paramInt3, Object paramObject2, int paramInt4, Object paramObject3, int paramInt5, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetBuffer3i(int paramInt1, int paramInt2, int[] paramArrayOfint1, int paramInt3, int[] paramArrayOfint2, int paramInt4, int[] paramArrayOfint3, int paramInt5) {
/* 1093 */     if (paramArrayOfint1 != null && paramArrayOfint1.length <= paramInt3)
/* 1094 */       throw new ALException("array offset argument \"value1_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint1.length + ")"); 
/* 1095 */     if (paramArrayOfint2 != null && paramArrayOfint2.length <= paramInt4)
/* 1096 */       throw new ALException("array offset argument \"value2_offset\" (" + paramInt4 + ") equals or exceeds array length (" + paramArrayOfint2.length + ")"); 
/* 1097 */     if (paramArrayOfint3 != null && paramArrayOfint3.length <= paramInt5)
/* 1098 */       throw new ALException("array offset argument \"value3_offset\" (" + paramInt5 + ") equals or exceeds array length (" + paramArrayOfint3.length + ")"); 
/* 1099 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBuffer3i;
/* 1100 */     if (l == 0L) {
/* 1101 */       throw new ALException("Method \"alGetBuffer3i\" not available");
/*      */     }
/* 1103 */     dispatch_alGetBuffer3i1(paramInt1, paramInt2, paramArrayOfint1, 4 * paramInt3, paramArrayOfint2, 4 * paramInt4, paramArrayOfint3, 4 * paramInt5, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetBufferf(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/* 1110 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 1111 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBufferf;
/* 1112 */     if (l == 0L) {
/* 1113 */       throw new ALException("Method \"alGetBufferf\" not available");
/*      */     }
/* 1115 */     if (bool) {
/* 1116 */       dispatch_alGetBufferf0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/* 1118 */       dispatch_alGetBufferf1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBufferf0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBufferf1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetBufferf(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/* 1131 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/* 1132 */       throw new ALException("array offset argument \"value_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/* 1133 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBufferf;
/* 1134 */     if (l == 0L) {
/* 1135 */       throw new ALException("Method \"alGetBufferf\" not available");
/*      */     }
/* 1137 */     dispatch_alGetBufferf1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetBufferfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/* 1144 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 1145 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBufferfv;
/* 1146 */     if (l == 0L) {
/* 1147 */       throw new ALException("Method \"alGetBufferfv\" not available");
/*      */     }
/* 1149 */     if (bool) {
/* 1150 */       dispatch_alGetBufferfv0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/* 1152 */       dispatch_alGetBufferfv1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBufferfv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBufferfv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetBufferfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/* 1165 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/* 1166 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/* 1167 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBufferfv;
/* 1168 */     if (l == 0L) {
/* 1169 */       throw new ALException("Method \"alGetBufferfv\" not available");
/*      */     }
/* 1171 */     dispatch_alGetBufferfv1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetBufferi(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/* 1178 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 1179 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBufferi;
/* 1180 */     if (l == 0L) {
/* 1181 */       throw new ALException("Method \"alGetBufferi\" not available");
/*      */     }
/* 1183 */     if (bool) {
/* 1184 */       dispatch_alGetBufferi0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 1186 */       dispatch_alGetBufferi1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBufferi0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBufferi1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetBufferi(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/* 1199 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/* 1200 */       throw new ALException("array offset argument \"value_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 1201 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBufferi;
/* 1202 */     if (l == 0L) {
/* 1203 */       throw new ALException("Method \"alGetBufferi\" not available");
/*      */     }
/* 1205 */     dispatch_alGetBufferi1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetBufferiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/* 1212 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 1213 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBufferiv;
/* 1214 */     if (l == 0L) {
/* 1215 */       throw new ALException("Method \"alGetBufferiv\" not available");
/*      */     }
/* 1217 */     if (bool) {
/* 1218 */       dispatch_alGetBufferiv0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 1220 */       dispatch_alGetBufferiv1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBufferiv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetBufferiv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetBufferiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/* 1233 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/* 1234 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 1235 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetBufferiv;
/* 1236 */     if (l == 0L) {
/* 1237 */       throw new ALException("Method \"alGetBufferiv\" not available");
/*      */     }
/* 1239 */     dispatch_alGetBufferiv1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double alGetDouble(int paramInt) {
/* 1246 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetDouble;
/* 1247 */     if (l == 0L) {
/* 1248 */       throw new ALException("Method \"alGetDouble\" not available");
/*      */     }
/* 1250 */     return dispatch_alGetDouble0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native double dispatch_alGetDouble0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetDoublev(int paramInt, DoubleBuffer paramDoubleBuffer) {
/* 1259 */     boolean bool = BufferFactory.isDirect(paramDoubleBuffer);
/* 1260 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetDoublev;
/* 1261 */     if (l == 0L) {
/* 1262 */       throw new ALException("Method \"alGetDoublev\" not available");
/*      */     }
/* 1264 */     if (bool) {
/* 1265 */       dispatch_alGetDoublev0(paramInt, paramDoubleBuffer, BufferFactory.getDirectBufferByteOffset(paramDoubleBuffer), l);
/*      */     } else {
/* 1267 */       dispatch_alGetDoublev1(paramInt, BufferFactory.getArray(paramDoubleBuffer), BufferFactory.getIndirectBufferByteOffset(paramDoubleBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetDoublev0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetDoublev1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetDoublev(int paramInt1, double[] paramArrayOfdouble, int paramInt2) {
/* 1280 */     if (paramArrayOfdouble != null && paramArrayOfdouble.length <= paramInt2)
/* 1281 */       throw new ALException("array offset argument \"data_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfdouble.length + ")"); 
/* 1282 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetDoublev;
/* 1283 */     if (l == 0L) {
/* 1284 */       throw new ALException("Method \"alGetDoublev\" not available");
/*      */     }
/* 1286 */     dispatch_alGetDoublev1(paramInt1, paramArrayOfdouble, 8 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetEffectf(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/* 1293 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 1294 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetEffectf;
/* 1295 */     if (l == 0L) {
/* 1296 */       throw new ALException("Method \"alGetEffectf\" not available");
/*      */     }
/* 1298 */     if (bool) {
/* 1299 */       dispatch_alGetEffectf0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/* 1301 */       dispatch_alGetEffectf1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetEffectf0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetEffectf1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetEffectf(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/* 1314 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/* 1315 */       throw new ALException("array offset argument \"value_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/* 1316 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetEffectf;
/* 1317 */     if (l == 0L) {
/* 1318 */       throw new ALException("Method \"alGetEffectf\" not available");
/*      */     }
/* 1320 */     dispatch_alGetEffectf1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetEffectfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/* 1327 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 1328 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetEffectfv;
/* 1329 */     if (l == 0L) {
/* 1330 */       throw new ALException("Method \"alGetEffectfv\" not available");
/*      */     }
/* 1332 */     if (bool) {
/* 1333 */       dispatch_alGetEffectfv0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/* 1335 */       dispatch_alGetEffectfv1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetEffectfv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetEffectfv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetEffectfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/* 1348 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/* 1349 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/* 1350 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetEffectfv;
/* 1351 */     if (l == 0L) {
/* 1352 */       throw new ALException("Method \"alGetEffectfv\" not available");
/*      */     }
/* 1354 */     dispatch_alGetEffectfv1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetEffecti(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/* 1361 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 1362 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetEffecti;
/* 1363 */     if (l == 0L) {
/* 1364 */       throw new ALException("Method \"alGetEffecti\" not available");
/*      */     }
/* 1366 */     if (bool) {
/* 1367 */       dispatch_alGetEffecti0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 1369 */       dispatch_alGetEffecti1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetEffecti0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetEffecti1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetEffecti(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/* 1382 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/* 1383 */       throw new ALException("array offset argument \"value_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 1384 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetEffecti;
/* 1385 */     if (l == 0L) {
/* 1386 */       throw new ALException("Method \"alGetEffecti\" not available");
/*      */     }
/* 1388 */     dispatch_alGetEffecti1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetEffectiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/* 1395 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 1396 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetEffectiv;
/* 1397 */     if (l == 0L) {
/* 1398 */       throw new ALException("Method \"alGetEffectiv\" not available");
/*      */     }
/* 1400 */     if (bool) {
/* 1401 */       dispatch_alGetEffectiv0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 1403 */       dispatch_alGetEffectiv1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetEffectiv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetEffectiv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetEffectiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/* 1416 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/* 1417 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 1418 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetEffectiv;
/* 1419 */     if (l == 0L) {
/* 1420 */       throw new ALException("Method \"alGetEffectiv\" not available");
/*      */     }
/* 1422 */     dispatch_alGetEffectiv1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int alGetEnumValue(String paramString) {
/* 1429 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetEnumValue;
/* 1430 */     if (l == 0L) {
/* 1431 */       throw new ALException("Method \"alGetEnumValue\" not available");
/*      */     }
/* 1433 */     return dispatch_alGetEnumValue0(paramString, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native int dispatch_alGetEnumValue0(String paramString, long paramLong);
/*      */ 
/*      */   
/*      */   public int alGetError() {
/* 1442 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetError;
/* 1443 */     if (l == 0L) {
/* 1444 */       throw new ALException("Method \"alGetError\" not available");
/*      */     }
/* 1446 */     return dispatch_alGetError0(l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native int dispatch_alGetError0(long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetFilterf(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/* 1455 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 1456 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetFilterf;
/* 1457 */     if (l == 0L) {
/* 1458 */       throw new ALException("Method \"alGetFilterf\" not available");
/*      */     }
/* 1460 */     if (bool) {
/* 1461 */       dispatch_alGetFilterf0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/* 1463 */       dispatch_alGetFilterf1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetFilterf0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetFilterf1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetFilterf(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/* 1476 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/* 1477 */       throw new ALException("array offset argument \"value_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/* 1478 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetFilterf;
/* 1479 */     if (l == 0L) {
/* 1480 */       throw new ALException("Method \"alGetFilterf\" not available");
/*      */     }
/* 1482 */     dispatch_alGetFilterf1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetFilterfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/* 1489 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 1490 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetFilterfv;
/* 1491 */     if (l == 0L) {
/* 1492 */       throw new ALException("Method \"alGetFilterfv\" not available");
/*      */     }
/* 1494 */     if (bool) {
/* 1495 */       dispatch_alGetFilterfv0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/* 1497 */       dispatch_alGetFilterfv1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetFilterfv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetFilterfv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetFilterfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/* 1510 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/* 1511 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/* 1512 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetFilterfv;
/* 1513 */     if (l == 0L) {
/* 1514 */       throw new ALException("Method \"alGetFilterfv\" not available");
/*      */     }
/* 1516 */     dispatch_alGetFilterfv1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetFilteri(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/* 1523 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 1524 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetFilteri;
/* 1525 */     if (l == 0L) {
/* 1526 */       throw new ALException("Method \"alGetFilteri\" not available");
/*      */     }
/* 1528 */     if (bool) {
/* 1529 */       dispatch_alGetFilteri0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 1531 */       dispatch_alGetFilteri1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetFilteri0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetFilteri1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetFilteri(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/* 1544 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/* 1545 */       throw new ALException("array offset argument \"value_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 1546 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetFilteri;
/* 1547 */     if (l == 0L) {
/* 1548 */       throw new ALException("Method \"alGetFilteri\" not available");
/*      */     }
/* 1550 */     dispatch_alGetFilteri1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetFilteriv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/* 1557 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 1558 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetFilteriv;
/* 1559 */     if (l == 0L) {
/* 1560 */       throw new ALException("Method \"alGetFilteriv\" not available");
/*      */     }
/* 1562 */     if (bool) {
/* 1563 */       dispatch_alGetFilteriv0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 1565 */       dispatch_alGetFilteriv1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetFilteriv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetFilteriv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetFilteriv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/* 1578 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/* 1579 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 1580 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetFilteriv;
/* 1581 */     if (l == 0L) {
/* 1582 */       throw new ALException("Method \"alGetFilteriv\" not available");
/*      */     }
/* 1584 */     dispatch_alGetFilteriv1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float alGetFloat(int paramInt) {
/* 1591 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetFloat;
/* 1592 */     if (l == 0L) {
/* 1593 */       throw new ALException("Method \"alGetFloat\" not available");
/*      */     }
/* 1595 */     return dispatch_alGetFloat0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native float dispatch_alGetFloat0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetFloatv(int paramInt, FloatBuffer paramFloatBuffer) {
/* 1604 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 1605 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetFloatv;
/* 1606 */     if (l == 0L) {
/* 1607 */       throw new ALException("Method \"alGetFloatv\" not available");
/*      */     }
/* 1609 */     if (bool) {
/* 1610 */       dispatch_alGetFloatv0(paramInt, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/* 1612 */       dispatch_alGetFloatv1(paramInt, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetFloatv0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetFloatv1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetFloatv(int paramInt1, float[] paramArrayOffloat, int paramInt2) {
/* 1625 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt2)
/* 1626 */       throw new ALException("array offset argument \"data_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/* 1627 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetFloatv;
/* 1628 */     if (l == 0L) {
/* 1629 */       throw new ALException("Method \"alGetFloatv\" not available");
/*      */     }
/* 1631 */     dispatch_alGetFloatv1(paramInt1, paramArrayOffloat, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int alGetInteger(int paramInt) {
/* 1638 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetInteger;
/* 1639 */     if (l == 0L) {
/* 1640 */       throw new ALException("Method \"alGetInteger\" not available");
/*      */     }
/* 1642 */     return dispatch_alGetInteger0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native int dispatch_alGetInteger0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetIntegerv(int paramInt, IntBuffer paramIntBuffer) {
/* 1651 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 1652 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetIntegerv;
/* 1653 */     if (l == 0L) {
/* 1654 */       throw new ALException("Method \"alGetIntegerv\" not available");
/*      */     }
/* 1656 */     if (bool) {
/* 1657 */       dispatch_alGetIntegerv0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 1659 */       dispatch_alGetIntegerv1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetIntegerv0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetIntegerv1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetIntegerv(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 1672 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/* 1673 */       throw new ALException("array offset argument \"data_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 1674 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetIntegerv;
/* 1675 */     if (l == 0L) {
/* 1676 */       throw new ALException("Method \"alGetIntegerv\" not available");
/*      */     }
/* 1678 */     dispatch_alGetIntegerv1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetListener3f(int paramInt, FloatBuffer paramFloatBuffer1, FloatBuffer paramFloatBuffer2, FloatBuffer paramFloatBuffer3) {
/* 1685 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer1);
/* 1686 */     if (bool != BufferFactory.isDirect(paramFloatBuffer2))
/* 1687 */       throw new ALException("Argument \"value2\" : Buffers passed to this method must all be either direct or indirect"); 
/* 1688 */     if (bool != BufferFactory.isDirect(paramFloatBuffer3))
/* 1689 */       throw new ALException("Argument \"value3\" : Buffers passed to this method must all be either direct or indirect"); 
/* 1690 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetListener3f;
/* 1691 */     if (l == 0L) {
/* 1692 */       throw new ALException("Method \"alGetListener3f\" not available");
/*      */     }
/* 1694 */     if (bool) {
/* 1695 */       dispatch_alGetListener3f0(paramInt, paramFloatBuffer1, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer1), paramFloatBuffer2, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer2), paramFloatBuffer3, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer3), l);
/*      */     } else {
/* 1697 */       dispatch_alGetListener3f1(paramInt, BufferFactory.getArray(paramFloatBuffer1), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer1), BufferFactory.getArray(paramFloatBuffer2), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer2), BufferFactory.getArray(paramFloatBuffer3), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer3), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetListener3f0(int paramInt1, Object paramObject1, int paramInt2, Object paramObject2, int paramInt3, Object paramObject3, int paramInt4, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetListener3f1(int paramInt1, Object paramObject1, int paramInt2, Object paramObject2, int paramInt3, Object paramObject3, int paramInt4, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetListener3f(int paramInt1, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2, int paramInt3, float[] paramArrayOffloat3, int paramInt4) {
/* 1710 */     if (paramArrayOffloat1 != null && paramArrayOffloat1.length <= paramInt2)
/* 1711 */       throw new ALException("array offset argument \"value1_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOffloat1.length + ")"); 
/* 1712 */     if (paramArrayOffloat2 != null && paramArrayOffloat2.length <= paramInt3)
/* 1713 */       throw new ALException("array offset argument \"value2_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat2.length + ")"); 
/* 1714 */     if (paramArrayOffloat3 != null && paramArrayOffloat3.length <= paramInt4)
/* 1715 */       throw new ALException("array offset argument \"value3_offset\" (" + paramInt4 + ") equals or exceeds array length (" + paramArrayOffloat3.length + ")"); 
/* 1716 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetListener3f;
/* 1717 */     if (l == 0L) {
/* 1718 */       throw new ALException("Method \"alGetListener3f\" not available");
/*      */     }
/* 1720 */     dispatch_alGetListener3f1(paramInt1, paramArrayOffloat1, 4 * paramInt2, paramArrayOffloat2, 4 * paramInt3, paramArrayOffloat3, 4 * paramInt4, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetListener3i(int paramInt, IntBuffer paramIntBuffer1, IntBuffer paramIntBuffer2, IntBuffer paramIntBuffer3) {
/* 1727 */     boolean bool = BufferFactory.isDirect(paramIntBuffer1);
/* 1728 */     if (bool != BufferFactory.isDirect(paramIntBuffer2))
/* 1729 */       throw new ALException("Argument \"value2\" : Buffers passed to this method must all be either direct or indirect"); 
/* 1730 */     if (bool != BufferFactory.isDirect(paramIntBuffer3))
/* 1731 */       throw new ALException("Argument \"value3\" : Buffers passed to this method must all be either direct or indirect"); 
/* 1732 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetListener3i;
/* 1733 */     if (l == 0L) {
/* 1734 */       throw new ALException("Method \"alGetListener3i\" not available");
/*      */     }
/* 1736 */     if (bool) {
/* 1737 */       dispatch_alGetListener3i0(paramInt, paramIntBuffer1, BufferFactory.getDirectBufferByteOffset(paramIntBuffer1), paramIntBuffer2, BufferFactory.getDirectBufferByteOffset(paramIntBuffer2), paramIntBuffer3, BufferFactory.getDirectBufferByteOffset(paramIntBuffer3), l);
/*      */     } else {
/* 1739 */       dispatch_alGetListener3i1(paramInt, BufferFactory.getArray(paramIntBuffer1), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer1), BufferFactory.getArray(paramIntBuffer2), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer2), BufferFactory.getArray(paramIntBuffer3), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer3), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetListener3i0(int paramInt1, Object paramObject1, int paramInt2, Object paramObject2, int paramInt3, Object paramObject3, int paramInt4, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetListener3i1(int paramInt1, Object paramObject1, int paramInt2, Object paramObject2, int paramInt3, Object paramObject3, int paramInt4, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetListener3i(int paramInt1, int[] paramArrayOfint1, int paramInt2, int[] paramArrayOfint2, int paramInt3, int[] paramArrayOfint3, int paramInt4) {
/* 1752 */     if (paramArrayOfint1 != null && paramArrayOfint1.length <= paramInt2)
/* 1753 */       throw new ALException("array offset argument \"value1_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint1.length + ")"); 
/* 1754 */     if (paramArrayOfint2 != null && paramArrayOfint2.length <= paramInt3)
/* 1755 */       throw new ALException("array offset argument \"value2_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint2.length + ")"); 
/* 1756 */     if (paramArrayOfint3 != null && paramArrayOfint3.length <= paramInt4)
/* 1757 */       throw new ALException("array offset argument \"value3_offset\" (" + paramInt4 + ") equals or exceeds array length (" + paramArrayOfint3.length + ")"); 
/* 1758 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetListener3i;
/* 1759 */     if (l == 0L) {
/* 1760 */       throw new ALException("Method \"alGetListener3i\" not available");
/*      */     }
/* 1762 */     dispatch_alGetListener3i1(paramInt1, paramArrayOfint1, 4 * paramInt2, paramArrayOfint2, 4 * paramInt3, paramArrayOfint3, 4 * paramInt4, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetListenerf(int paramInt, FloatBuffer paramFloatBuffer) {
/* 1769 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 1770 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetListenerf;
/* 1771 */     if (l == 0L) {
/* 1772 */       throw new ALException("Method \"alGetListenerf\" not available");
/*      */     }
/* 1774 */     if (bool) {
/* 1775 */       dispatch_alGetListenerf0(paramInt, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/* 1777 */       dispatch_alGetListenerf1(paramInt, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetListenerf0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetListenerf1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetListenerf(int paramInt1, float[] paramArrayOffloat, int paramInt2) {
/* 1790 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt2)
/* 1791 */       throw new ALException("array offset argument \"value_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/* 1792 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetListenerf;
/* 1793 */     if (l == 0L) {
/* 1794 */       throw new ALException("Method \"alGetListenerf\" not available");
/*      */     }
/* 1796 */     dispatch_alGetListenerf1(paramInt1, paramArrayOffloat, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetListenerfv(int paramInt, FloatBuffer paramFloatBuffer) {
/* 1803 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 1804 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetListenerfv;
/* 1805 */     if (l == 0L) {
/* 1806 */       throw new ALException("Method \"alGetListenerfv\" not available");
/*      */     }
/* 1808 */     if (bool) {
/* 1809 */       dispatch_alGetListenerfv0(paramInt, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/* 1811 */       dispatch_alGetListenerfv1(paramInt, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetListenerfv0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetListenerfv1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetListenerfv(int paramInt1, float[] paramArrayOffloat, int paramInt2) {
/* 1824 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt2)
/* 1825 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/* 1826 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetListenerfv;
/* 1827 */     if (l == 0L) {
/* 1828 */       throw new ALException("Method \"alGetListenerfv\" not available");
/*      */     }
/* 1830 */     dispatch_alGetListenerfv1(paramInt1, paramArrayOffloat, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetListeneri(int paramInt, IntBuffer paramIntBuffer) {
/* 1837 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 1838 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetListeneri;
/* 1839 */     if (l == 0L) {
/* 1840 */       throw new ALException("Method \"alGetListeneri\" not available");
/*      */     }
/* 1842 */     if (bool) {
/* 1843 */       dispatch_alGetListeneri0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 1845 */       dispatch_alGetListeneri1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetListeneri0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetListeneri1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetListeneri(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 1858 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/* 1859 */       throw new ALException("array offset argument \"value_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 1860 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetListeneri;
/* 1861 */     if (l == 0L) {
/* 1862 */       throw new ALException("Method \"alGetListeneri\" not available");
/*      */     }
/* 1864 */     dispatch_alGetListeneri1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetListeneriv(int paramInt, IntBuffer paramIntBuffer) {
/* 1871 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 1872 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetListeneriv;
/* 1873 */     if (l == 0L) {
/* 1874 */       throw new ALException("Method \"alGetListeneriv\" not available");
/*      */     }
/* 1876 */     if (bool) {
/* 1877 */       dispatch_alGetListeneriv0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 1879 */       dispatch_alGetListeneriv1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetListeneriv0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetListeneriv1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetListeneriv(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 1892 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/* 1893 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 1894 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetListeneriv;
/* 1895 */     if (l == 0L) {
/* 1896 */       throw new ALException("Method \"alGetListeneriv\" not available");
/*      */     }
/* 1898 */     dispatch_alGetListeneriv1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetSource3f(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, FloatBuffer paramFloatBuffer2, FloatBuffer paramFloatBuffer3) {
/* 1905 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer1);
/* 1906 */     if (bool != BufferFactory.isDirect(paramFloatBuffer2))
/* 1907 */       throw new ALException("Argument \"value2\" : Buffers passed to this method must all be either direct or indirect"); 
/* 1908 */     if (bool != BufferFactory.isDirect(paramFloatBuffer3))
/* 1909 */       throw new ALException("Argument \"value3\" : Buffers passed to this method must all be either direct or indirect"); 
/* 1910 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetSource3f;
/* 1911 */     if (l == 0L) {
/* 1912 */       throw new ALException("Method \"alGetSource3f\" not available");
/*      */     }
/* 1914 */     if (bool) {
/* 1915 */       dispatch_alGetSource3f0(paramInt1, paramInt2, paramFloatBuffer1, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer1), paramFloatBuffer2, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer2), paramFloatBuffer3, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer3), l);
/*      */     } else {
/* 1917 */       dispatch_alGetSource3f1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer1), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer1), BufferFactory.getArray(paramFloatBuffer2), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer2), BufferFactory.getArray(paramFloatBuffer3), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer3), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetSource3f0(int paramInt1, int paramInt2, Object paramObject1, int paramInt3, Object paramObject2, int paramInt4, Object paramObject3, int paramInt5, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetSource3f1(int paramInt1, int paramInt2, Object paramObject1, int paramInt3, Object paramObject2, int paramInt4, Object paramObject3, int paramInt5, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetSource3f(int paramInt1, int paramInt2, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2, int paramInt4, float[] paramArrayOffloat3, int paramInt5) {
/* 1930 */     if (paramArrayOffloat1 != null && paramArrayOffloat1.length <= paramInt3)
/* 1931 */       throw new ALException("array offset argument \"value1_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat1.length + ")"); 
/* 1932 */     if (paramArrayOffloat2 != null && paramArrayOffloat2.length <= paramInt4)
/* 1933 */       throw new ALException("array offset argument \"value2_offset\" (" + paramInt4 + ") equals or exceeds array length (" + paramArrayOffloat2.length + ")"); 
/* 1934 */     if (paramArrayOffloat3 != null && paramArrayOffloat3.length <= paramInt5)
/* 1935 */       throw new ALException("array offset argument \"value3_offset\" (" + paramInt5 + ") equals or exceeds array length (" + paramArrayOffloat3.length + ")"); 
/* 1936 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetSource3f;
/* 1937 */     if (l == 0L) {
/* 1938 */       throw new ALException("Method \"alGetSource3f\" not available");
/*      */     }
/* 1940 */     dispatch_alGetSource3f1(paramInt1, paramInt2, paramArrayOffloat1, 4 * paramInt3, paramArrayOffloat2, 4 * paramInt4, paramArrayOffloat3, 4 * paramInt5, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetSource3i(int paramInt1, int paramInt2, IntBuffer paramIntBuffer1, IntBuffer paramIntBuffer2, IntBuffer paramIntBuffer3) {
/* 1947 */     boolean bool = BufferFactory.isDirect(paramIntBuffer1);
/* 1948 */     if (bool != BufferFactory.isDirect(paramIntBuffer2))
/* 1949 */       throw new ALException("Argument \"value2\" : Buffers passed to this method must all be either direct or indirect"); 
/* 1950 */     if (bool != BufferFactory.isDirect(paramIntBuffer3))
/* 1951 */       throw new ALException("Argument \"value3\" : Buffers passed to this method must all be either direct or indirect"); 
/* 1952 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetSource3i;
/* 1953 */     if (l == 0L) {
/* 1954 */       throw new ALException("Method \"alGetSource3i\" not available");
/*      */     }
/* 1956 */     if (bool) {
/* 1957 */       dispatch_alGetSource3i0(paramInt1, paramInt2, paramIntBuffer1, BufferFactory.getDirectBufferByteOffset(paramIntBuffer1), paramIntBuffer2, BufferFactory.getDirectBufferByteOffset(paramIntBuffer2), paramIntBuffer3, BufferFactory.getDirectBufferByteOffset(paramIntBuffer3), l);
/*      */     } else {
/* 1959 */       dispatch_alGetSource3i1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer1), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer1), BufferFactory.getArray(paramIntBuffer2), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer2), BufferFactory.getArray(paramIntBuffer3), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer3), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetSource3i0(int paramInt1, int paramInt2, Object paramObject1, int paramInt3, Object paramObject2, int paramInt4, Object paramObject3, int paramInt5, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetSource3i1(int paramInt1, int paramInt2, Object paramObject1, int paramInt3, Object paramObject2, int paramInt4, Object paramObject3, int paramInt5, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetSource3i(int paramInt1, int paramInt2, int[] paramArrayOfint1, int paramInt3, int[] paramArrayOfint2, int paramInt4, int[] paramArrayOfint3, int paramInt5) {
/* 1972 */     if (paramArrayOfint1 != null && paramArrayOfint1.length <= paramInt3)
/* 1973 */       throw new ALException("array offset argument \"value1_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint1.length + ")"); 
/* 1974 */     if (paramArrayOfint2 != null && paramArrayOfint2.length <= paramInt4)
/* 1975 */       throw new ALException("array offset argument \"value2_offset\" (" + paramInt4 + ") equals or exceeds array length (" + paramArrayOfint2.length + ")"); 
/* 1976 */     if (paramArrayOfint3 != null && paramArrayOfint3.length <= paramInt5)
/* 1977 */       throw new ALException("array offset argument \"value3_offset\" (" + paramInt5 + ") equals or exceeds array length (" + paramArrayOfint3.length + ")"); 
/* 1978 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetSource3i;
/* 1979 */     if (l == 0L) {
/* 1980 */       throw new ALException("Method \"alGetSource3i\" not available");
/*      */     }
/* 1982 */     dispatch_alGetSource3i1(paramInt1, paramInt2, paramArrayOfint1, 4 * paramInt3, paramArrayOfint2, 4 * paramInt4, paramArrayOfint3, 4 * paramInt5, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetSourcef(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/* 1989 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 1990 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetSourcef;
/* 1991 */     if (l == 0L) {
/* 1992 */       throw new ALException("Method \"alGetSourcef\" not available");
/*      */     }
/* 1994 */     if (bool) {
/* 1995 */       dispatch_alGetSourcef0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/* 1997 */       dispatch_alGetSourcef1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetSourcef0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetSourcef1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetSourcef(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/* 2010 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/* 2011 */       throw new ALException("array offset argument \"value_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/* 2012 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetSourcef;
/* 2013 */     if (l == 0L) {
/* 2014 */       throw new ALException("Method \"alGetSourcef\" not available");
/*      */     }
/* 2016 */     dispatch_alGetSourcef1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetSourcefv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/* 2023 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 2024 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetSourcefv;
/* 2025 */     if (l == 0L) {
/* 2026 */       throw new ALException("Method \"alGetSourcefv\" not available");
/*      */     }
/* 2028 */     if (bool) {
/* 2029 */       dispatch_alGetSourcefv0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/* 2031 */       dispatch_alGetSourcefv1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetSourcefv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetSourcefv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetSourcefv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/* 2044 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/* 2045 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/* 2046 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetSourcefv;
/* 2047 */     if (l == 0L) {
/* 2048 */       throw new ALException("Method \"alGetSourcefv\" not available");
/*      */     }
/* 2050 */     dispatch_alGetSourcefv1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetSourcei(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/* 2057 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 2058 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetSourcei;
/* 2059 */     if (l == 0L) {
/* 2060 */       throw new ALException("Method \"alGetSourcei\" not available");
/*      */     }
/* 2062 */     if (bool) {
/* 2063 */       dispatch_alGetSourcei0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 2065 */       dispatch_alGetSourcei1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetSourcei0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetSourcei1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetSourcei(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/* 2078 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/* 2079 */       throw new ALException("array offset argument \"value_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 2080 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetSourcei;
/* 2081 */     if (l == 0L) {
/* 2082 */       throw new ALException("Method \"alGetSourcei\" not available");
/*      */     }
/* 2084 */     dispatch_alGetSourcei1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alGetSourceiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/* 2091 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 2092 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetSourceiv;
/* 2093 */     if (l == 0L) {
/* 2094 */       throw new ALException("Method \"alGetSourceiv\" not available");
/*      */     }
/* 2096 */     if (bool) {
/* 2097 */       dispatch_alGetSourceiv0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 2099 */       dispatch_alGetSourceiv1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetSourceiv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alGetSourceiv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alGetSourceiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/* 2112 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/* 2113 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 2114 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetSourceiv;
/* 2115 */     if (l == 0L) {
/* 2116 */       throw new ALException("Method \"alGetSourceiv\" not available");
/*      */     }
/* 2118 */     dispatch_alGetSourceiv1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String alGetString(int paramInt) {
/* 2125 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alGetString;
/* 2126 */     if (l == 0L) {
/* 2127 */       throw new ALException("Method \"alGetString\" not available");
/*      */     }
/* 2129 */     return dispatch_alGetString0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native String dispatch_alGetString0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public boolean alIsAuxiliaryEffectSlot(int paramInt) {
/* 2138 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alIsAuxiliaryEffectSlot;
/* 2139 */     if (l == 0L) {
/* 2140 */       throw new ALException("Method \"alIsAuxiliaryEffectSlot\" not available");
/*      */     }
/* 2142 */     return dispatch_alIsAuxiliaryEffectSlot0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native boolean dispatch_alIsAuxiliaryEffectSlot0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public boolean alIsBuffer(int paramInt) {
/* 2151 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alIsBuffer;
/* 2152 */     if (l == 0L) {
/* 2153 */       throw new ALException("Method \"alIsBuffer\" not available");
/*      */     }
/* 2155 */     return dispatch_alIsBuffer0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native boolean dispatch_alIsBuffer0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public boolean alIsEffect(int paramInt) {
/* 2164 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alIsEffect;
/* 2165 */     if (l == 0L) {
/* 2166 */       throw new ALException("Method \"alIsEffect\" not available");
/*      */     }
/* 2168 */     return dispatch_alIsEffect0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native boolean dispatch_alIsEffect0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public boolean alIsEnabled(int paramInt) {
/* 2177 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alIsEnabled;
/* 2178 */     if (l == 0L) {
/* 2179 */       throw new ALException("Method \"alIsEnabled\" not available");
/*      */     }
/* 2181 */     return dispatch_alIsEnabled0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native boolean dispatch_alIsEnabled0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public boolean alIsExtensionPresent(String paramString) {
/* 2190 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alIsExtensionPresent;
/* 2191 */     if (l == 0L) {
/* 2192 */       throw new ALException("Method \"alIsExtensionPresent\" not available");
/*      */     }
/* 2194 */     return dispatch_alIsExtensionPresent0(paramString, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native boolean dispatch_alIsExtensionPresent0(String paramString, long paramLong);
/*      */ 
/*      */   
/*      */   public boolean alIsFilter(int paramInt) {
/* 2203 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alIsFilter;
/* 2204 */     if (l == 0L) {
/* 2205 */       throw new ALException("Method \"alIsFilter\" not available");
/*      */     }
/* 2207 */     return dispatch_alIsFilter0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native boolean dispatch_alIsFilter0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public boolean alIsSource(int paramInt) {
/* 2216 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alIsSource;
/* 2217 */     if (l == 0L) {
/* 2218 */       throw new ALException("Method \"alIsSource\" not available");
/*      */     }
/* 2220 */     return dispatch_alIsSource0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native boolean dispatch_alIsSource0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public void alListener3f(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 2229 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alListener3f;
/* 2230 */     if (l == 0L) {
/* 2231 */       throw new ALException("Method \"alListener3f\" not available");
/*      */     }
/* 2233 */     dispatch_alListener3f0(paramInt, paramFloat1, paramFloat2, paramFloat3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alListener3f0(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alListener3i(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 2242 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alListener3i;
/* 2243 */     if (l == 0L) {
/* 2244 */       throw new ALException("Method \"alListener3i\" not available");
/*      */     }
/* 2246 */     dispatch_alListener3i0(paramInt1, paramInt2, paramInt3, paramInt4, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alListener3i0(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
/*      */ 
/*      */   
/*      */   public void alListenerf(int paramInt, float paramFloat) {
/* 2255 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alListenerf;
/* 2256 */     if (l == 0L) {
/* 2257 */       throw new ALException("Method \"alListenerf\" not available");
/*      */     }
/* 2259 */     dispatch_alListenerf0(paramInt, paramFloat, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alListenerf0(int paramInt, float paramFloat, long paramLong);
/*      */ 
/*      */   
/*      */   public void alListenerfv(int paramInt, FloatBuffer paramFloatBuffer) {
/* 2268 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 2269 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alListenerfv;
/* 2270 */     if (l == 0L) {
/* 2271 */       throw new ALException("Method \"alListenerfv\" not available");
/*      */     }
/* 2273 */     if (bool) {
/* 2274 */       dispatch_alListenerfv0(paramInt, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/* 2276 */       dispatch_alListenerfv1(paramInt, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alListenerfv0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alListenerfv1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alListenerfv(int paramInt1, float[] paramArrayOffloat, int paramInt2) {
/* 2289 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt2)
/* 2290 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/* 2291 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alListenerfv;
/* 2292 */     if (l == 0L) {
/* 2293 */       throw new ALException("Method \"alListenerfv\" not available");
/*      */     }
/* 2295 */     dispatch_alListenerfv1(paramInt1, paramArrayOffloat, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alListeneri(int paramInt1, int paramInt2) {
/* 2302 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alListeneri;
/* 2303 */     if (l == 0L) {
/* 2304 */       throw new ALException("Method \"alListeneri\" not available");
/*      */     }
/* 2306 */     dispatch_alListeneri0(paramInt1, paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alListeneri0(int paramInt1, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alListeneriv(int paramInt, IntBuffer paramIntBuffer) {
/* 2315 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 2316 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alListeneriv;
/* 2317 */     if (l == 0L) {
/* 2318 */       throw new ALException("Method \"alListeneriv\" not available");
/*      */     }
/* 2320 */     if (bool) {
/* 2321 */       dispatch_alListeneriv0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 2323 */       dispatch_alListeneriv1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alListeneriv0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alListeneriv1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alListeneriv(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 2336 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/* 2337 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 2338 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alListeneriv;
/* 2339 */     if (l == 0L) {
/* 2340 */       throw new ALException("Method \"alListeneriv\" not available");
/*      */     }
/* 2342 */     dispatch_alListeneriv1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alSource3f(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 2349 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSource3f;
/* 2350 */     if (l == 0L) {
/* 2351 */       throw new ALException("Method \"alSource3f\" not available");
/*      */     }
/* 2353 */     dispatch_alSource3f0(paramInt1, paramInt2, paramFloat1, paramFloat2, paramFloat3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alSource3f0(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSource3i(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 2362 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSource3i;
/* 2363 */     if (l == 0L) {
/* 2364 */       throw new ALException("Method \"alSource3i\" not available");
/*      */     }
/* 2366 */     dispatch_alSource3i0(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alSource3i0(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourcePause(int paramInt) {
/* 2375 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourcePause;
/* 2376 */     if (l == 0L) {
/* 2377 */       throw new ALException("Method \"alSourcePause\" not available");
/*      */     }
/* 2379 */     dispatch_alSourcePause0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alSourcePause0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourcePausev(int paramInt, IntBuffer paramIntBuffer) {
/* 2388 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 2389 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourcePausev;
/* 2390 */     if (l == 0L) {
/* 2391 */       throw new ALException("Method \"alSourcePausev\" not available");
/*      */     }
/* 2393 */     if (bool) {
/* 2394 */       dispatch_alSourcePausev0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 2396 */       dispatch_alSourcePausev1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourcePausev0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourcePausev1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourcePausev(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 2409 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/* 2410 */       throw new ALException("array offset argument \"sids_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 2411 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourcePausev;
/* 2412 */     if (l == 0L) {
/* 2413 */       throw new ALException("Method \"alSourcePausev\" not available");
/*      */     }
/* 2415 */     dispatch_alSourcePausev1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alSourcePlay(int paramInt) {
/* 2422 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourcePlay;
/* 2423 */     if (l == 0L) {
/* 2424 */       throw new ALException("Method \"alSourcePlay\" not available");
/*      */     }
/* 2426 */     dispatch_alSourcePlay0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alSourcePlay0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourcePlayv(int paramInt, IntBuffer paramIntBuffer) {
/* 2435 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 2436 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourcePlayv;
/* 2437 */     if (l == 0L) {
/* 2438 */       throw new ALException("Method \"alSourcePlayv\" not available");
/*      */     }
/* 2440 */     if (bool) {
/* 2441 */       dispatch_alSourcePlayv0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 2443 */       dispatch_alSourcePlayv1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourcePlayv0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourcePlayv1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourcePlayv(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 2456 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/* 2457 */       throw new ALException("array offset argument \"sids_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 2458 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourcePlayv;
/* 2459 */     if (l == 0L) {
/* 2460 */       throw new ALException("Method \"alSourcePlayv\" not available");
/*      */     }
/* 2462 */     dispatch_alSourcePlayv1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alSourceQueueBuffers(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/* 2469 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 2470 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourceQueueBuffers;
/* 2471 */     if (l == 0L) {
/* 2472 */       throw new ALException("Method \"alSourceQueueBuffers\" not available");
/*      */     }
/* 2474 */     if (bool) {
/* 2475 */       dispatch_alSourceQueueBuffers0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 2477 */       dispatch_alSourceQueueBuffers1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourceQueueBuffers0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourceQueueBuffers1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourceQueueBuffers(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/* 2490 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/* 2491 */       throw new ALException("array offset argument \"bids_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 2492 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourceQueueBuffers;
/* 2493 */     if (l == 0L) {
/* 2494 */       throw new ALException("Method \"alSourceQueueBuffers\" not available");
/*      */     }
/* 2496 */     dispatch_alSourceQueueBuffers1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alSourceRewind(int paramInt) {
/* 2503 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourceRewind;
/* 2504 */     if (l == 0L) {
/* 2505 */       throw new ALException("Method \"alSourceRewind\" not available");
/*      */     }
/* 2507 */     dispatch_alSourceRewind0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alSourceRewind0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourceRewindv(int paramInt, IntBuffer paramIntBuffer) {
/* 2516 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 2517 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourceRewindv;
/* 2518 */     if (l == 0L) {
/* 2519 */       throw new ALException("Method \"alSourceRewindv\" not available");
/*      */     }
/* 2521 */     if (bool) {
/* 2522 */       dispatch_alSourceRewindv0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 2524 */       dispatch_alSourceRewindv1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourceRewindv0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourceRewindv1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourceRewindv(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 2537 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/* 2538 */       throw new ALException("array offset argument \"sids_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 2539 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourceRewindv;
/* 2540 */     if (l == 0L) {
/* 2541 */       throw new ALException("Method \"alSourceRewindv\" not available");
/*      */     }
/* 2543 */     dispatch_alSourceRewindv1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alSourceStop(int paramInt) {
/* 2550 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourceStop;
/* 2551 */     if (l == 0L) {
/* 2552 */       throw new ALException("Method \"alSourceStop\" not available");
/*      */     }
/* 2554 */     dispatch_alSourceStop0(paramInt, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alSourceStop0(int paramInt, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourceStopv(int paramInt, IntBuffer paramIntBuffer) {
/* 2563 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 2564 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourceStopv;
/* 2565 */     if (l == 0L) {
/* 2566 */       throw new ALException("Method \"alSourceStopv\" not available");
/*      */     }
/* 2568 */     if (bool) {
/* 2569 */       dispatch_alSourceStopv0(paramInt, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 2571 */       dispatch_alSourceStopv1(paramInt, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourceStopv0(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourceStopv1(int paramInt1, Object paramObject, int paramInt2, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourceStopv(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 2584 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt2)
/* 2585 */       throw new ALException("array offset argument \"sids_offset\" (" + paramInt2 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 2586 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourceStopv;
/* 2587 */     if (l == 0L) {
/* 2588 */       throw new ALException("Method \"alSourceStopv\" not available");
/*      */     }
/* 2590 */     dispatch_alSourceStopv1(paramInt1, paramArrayOfint, 4 * paramInt2, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alSourceUnqueueBuffers(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/* 2597 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 2598 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourceUnqueueBuffers;
/* 2599 */     if (l == 0L) {
/* 2600 */       throw new ALException("Method \"alSourceUnqueueBuffers\" not available");
/*      */     }
/* 2602 */     if (bool) {
/* 2603 */       dispatch_alSourceUnqueueBuffers0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 2605 */       dispatch_alSourceUnqueueBuffers1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourceUnqueueBuffers0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourceUnqueueBuffers1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourceUnqueueBuffers(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/* 2618 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/* 2619 */       throw new ALException("array offset argument \"bids_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 2620 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourceUnqueueBuffers;
/* 2621 */     if (l == 0L) {
/* 2622 */       throw new ALException("Method \"alSourceUnqueueBuffers\" not available");
/*      */     }
/* 2624 */     dispatch_alSourceUnqueueBuffers1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alSourcef(int paramInt1, int paramInt2, float paramFloat) {
/* 2631 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourcef;
/* 2632 */     if (l == 0L) {
/* 2633 */       throw new ALException("Method \"alSourcef\" not available");
/*      */     }
/* 2635 */     dispatch_alSourcef0(paramInt1, paramInt2, paramFloat, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alSourcef0(int paramInt1, int paramInt2, float paramFloat, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourcefv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/* 2644 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 2645 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourcefv;
/* 2646 */     if (l == 0L) {
/* 2647 */       throw new ALException("Method \"alSourcefv\" not available");
/*      */     }
/* 2649 */     if (bool) {
/* 2650 */       dispatch_alSourcefv0(paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } else {
/* 2652 */       dispatch_alSourcefv1(paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourcefv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourcefv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourcefv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/* 2665 */     if (paramArrayOffloat != null && paramArrayOffloat.length <= paramInt3)
/* 2666 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOffloat.length + ")"); 
/* 2667 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourcefv;
/* 2668 */     if (l == 0L) {
/* 2669 */       throw new ALException("Method \"alSourcefv\" not available");
/*      */     }
/* 2671 */     dispatch_alSourcefv1(paramInt1, paramInt2, paramArrayOffloat, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alSourcei(int paramInt1, int paramInt2, int paramInt3) {
/* 2678 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourcei;
/* 2679 */     if (l == 0L) {
/* 2680 */       throw new ALException("Method \"alSourcei\" not available");
/*      */     }
/* 2682 */     dispatch_alSourcei0(paramInt1, paramInt2, paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public native void dispatch_alSourcei0(int paramInt1, int paramInt2, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourceiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/* 2691 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 2692 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourceiv;
/* 2693 */     if (l == 0L) {
/* 2694 */       throw new ALException("Method \"alSourceiv\" not available");
/*      */     }
/* 2696 */     if (bool) {
/* 2697 */       dispatch_alSourceiv0(paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*      */     } else {
/* 2699 */       dispatch_alSourceiv1(paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourceiv0(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   private native void dispatch_alSourceiv1(int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*      */ 
/*      */   
/*      */   public void alSourceiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/* 2712 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/* 2713 */       throw new ALException("array offset argument \"values_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 2714 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSourceiv;
/* 2715 */     if (l == 0L) {
/* 2716 */       throw new ALException("Method \"alSourceiv\" not available");
/*      */     }
/* 2718 */     dispatch_alSourceiv1(paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void alSpeedOfSound(float paramFloat) {
/* 2725 */     long l = (ALProcAddressLookup.getALProcAddressTable())._addressof_alSpeedOfSound;
/* 2726 */     if (l == 0L) {
/* 2727 */       throw new ALException("Method \"alSpeedOfSound\" not available");
/*      */     }
/* 2729 */     dispatch_alSpeedOfSound0(paramFloat, l);
/*      */   }
/*      */   
/*      */   public native void dispatch_alSpeedOfSound0(float paramFloat, long paramLong);
/*      */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\joal\impl\ALImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */