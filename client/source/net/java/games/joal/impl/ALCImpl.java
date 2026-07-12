/*     */ package net.java.games.joal.impl;
/*     */ 
/*     */ import com.sun.gluegen.runtime.BufferFactory;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import net.java.games.joal.ALC;
/*     */ import net.java.games.joal.ALCcontext;
/*     */ import net.java.games.joal.ALCdevice;
/*     */ import net.java.games.joal.ALException;
/*     */ 
/*     */ public class ALCImpl implements ALC {
/*     */   public boolean alcCaptureCloseDevice(ALCdevice paramALCdevice) {
/*  17 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcCaptureCloseDevice;
/*  18 */     if (l == 0L) {
/*  19 */       throw new ALException("Method \"alcCaptureCloseDevice\" not available");
/*     */     }
/*  21 */     return dispatch_alcCaptureCloseDevice0((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), l);
/*     */   }
/*     */ 
/*     */   
/*     */   private native boolean dispatch_alcCaptureCloseDevice0(ByteBuffer paramByteBuffer, long paramLong);
/*     */ 
/*     */   
/*     */   public ALCdevice alcCaptureOpenDevice(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3) {
/*     */     ByteBuffer byteBuffer;
/*  30 */     boolean bool = BufferFactory.isDirect(paramByteBuffer);
/*  31 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcCaptureOpenDevice;
/*  32 */     if (l == 0L) {
/*  33 */       throw new ALException("Method \"alcCaptureOpenDevice\" not available");
/*     */     }
/*     */     
/*  36 */     if (bool) {
/*  37 */       byteBuffer = dispatch_alcCaptureOpenDevice0(paramByteBuffer, BufferFactory.getDirectBufferByteOffset(paramByteBuffer), paramInt1, paramInt2, paramInt3, l);
/*     */     } else {
/*  39 */       byteBuffer = dispatch_alcCaptureOpenDevice1(BufferFactory.getArray(paramByteBuffer), BufferFactory.getIndirectBufferByteOffset(paramByteBuffer), paramInt1, paramInt2, paramInt3, l);
/*     */     } 
/*  41 */     if (byteBuffer == null) return null; 
/*  42 */     return ALCdevice.create(byteBuffer.order(ByteOrder.nativeOrder()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native ByteBuffer dispatch_alcCaptureOpenDevice0(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
/*     */ 
/*     */   
/*     */   private native ByteBuffer dispatch_alcCaptureOpenDevice1(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
/*     */ 
/*     */   
/*     */   public ALCdevice alcCaptureOpenDevice(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  54 */     if (paramArrayOfbyte != null && paramArrayOfbyte.length <= paramInt1)
/*  55 */       throw new ALException("array offset argument \"devicename_offset\" (" + paramInt1 + ") equals or exceeds array length (" + paramArrayOfbyte.length + ")"); 
/*  56 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcCaptureOpenDevice;
/*  57 */     if (l == 0L) {
/*  58 */       throw new ALException("Method \"alcCaptureOpenDevice\" not available");
/*     */     }
/*     */     
/*  61 */     ByteBuffer byteBuffer = dispatch_alcCaptureOpenDevice1(paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramInt4, l);
/*     */     
/*  63 */     if (byteBuffer == null) return null; 
/*  64 */     return ALCdevice.create(byteBuffer.order(ByteOrder.nativeOrder()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void alcCaptureSamples(ALCdevice paramALCdevice, Buffer paramBuffer, int paramInt) {
/*  70 */     boolean bool = BufferFactory.isDirect(paramBuffer);
/*  71 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcCaptureSamples;
/*  72 */     if (l == 0L) {
/*  73 */       throw new ALException("Method \"alcCaptureSamples\" not available");
/*     */     }
/*  75 */     if (bool) {
/*  76 */       dispatch_alcCaptureSamples0((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), paramBuffer, BufferFactory.getDirectBufferByteOffset(paramBuffer), paramInt, l);
/*     */     } else {
/*  78 */       dispatch_alcCaptureSamples1((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), BufferFactory.getArray(paramBuffer), BufferFactory.getIndirectBufferByteOffset(paramBuffer), paramInt, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native void dispatch_alcCaptureSamples0(ByteBuffer paramByteBuffer, Object paramObject, int paramInt1, int paramInt2, long paramLong);
/*     */ 
/*     */   
/*     */   private native void dispatch_alcCaptureSamples1(ByteBuffer paramByteBuffer, Object paramObject, int paramInt1, int paramInt2, long paramLong);
/*     */ 
/*     */   
/*     */   public void alcCaptureStart(ALCdevice paramALCdevice) {
/*  91 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcCaptureStart;
/*  92 */     if (l == 0L) {
/*  93 */       throw new ALException("Method \"alcCaptureStart\" not available");
/*     */     }
/*  95 */     dispatch_alcCaptureStart0((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native void dispatch_alcCaptureStart0(ByteBuffer paramByteBuffer, long paramLong);
/*     */ 
/*     */   
/*     */   public void alcCaptureStop(ALCdevice paramALCdevice) {
/* 104 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcCaptureStop;
/* 105 */     if (l == 0L) {
/* 106 */       throw new ALException("Method \"alcCaptureStop\" not available");
/*     */     }
/* 108 */     dispatch_alcCaptureStop0((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native void dispatch_alcCaptureStop0(ByteBuffer paramByteBuffer, long paramLong);
/*     */ 
/*     */   
/*     */   public boolean alcCloseDevice(ALCdevice paramALCdevice) {
/* 117 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcCloseDevice;
/* 118 */     if (l == 0L) {
/* 119 */       throw new ALException("Method \"alcCloseDevice\" not available");
/*     */     }
/* 121 */     return dispatch_alcCloseDevice0((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), l);
/*     */   }
/*     */ 
/*     */   
/*     */   private native boolean dispatch_alcCloseDevice0(ByteBuffer paramByteBuffer, long paramLong);
/*     */ 
/*     */   
/*     */   public ALCcontext alcCreateContext(ALCdevice paramALCdevice, IntBuffer paramIntBuffer) {
/*     */     ByteBuffer byteBuffer;
/* 130 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 131 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcCreateContext;
/* 132 */     if (l == 0L) {
/* 133 */       throw new ALException("Method \"alcCreateContext\" not available");
/*     */     }
/*     */     
/* 136 */     if (bool) {
/* 137 */       byteBuffer = dispatch_alcCreateContext0((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*     */     } else {
/* 139 */       byteBuffer = dispatch_alcCreateContext1((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*     */     } 
/* 141 */     if (byteBuffer == null) return null; 
/* 142 */     return ALCcontext.create(byteBuffer.order(ByteOrder.nativeOrder()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native ByteBuffer dispatch_alcCreateContext0(ByteBuffer paramByteBuffer, Object paramObject, int paramInt, long paramLong);
/*     */ 
/*     */   
/*     */   private native ByteBuffer dispatch_alcCreateContext1(ByteBuffer paramByteBuffer, Object paramObject, int paramInt, long paramLong);
/*     */ 
/*     */   
/*     */   public ALCcontext alcCreateContext(ALCdevice paramALCdevice, int[] paramArrayOfint, int paramInt) {
/* 154 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt)
/* 155 */       throw new ALException("array offset argument \"attrlist_offset\" (" + paramInt + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 156 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcCreateContext;
/* 157 */     if (l == 0L) {
/* 158 */       throw new ALException("Method \"alcCreateContext\" not available");
/*     */     }
/*     */     
/* 161 */     ByteBuffer byteBuffer = dispatch_alcCreateContext1((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), paramArrayOfint, 4 * paramInt, l);
/*     */     
/* 163 */     if (byteBuffer == null) return null; 
/* 164 */     return ALCcontext.create(byteBuffer.order(ByteOrder.nativeOrder()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void alcDestroyContext(ALCcontext paramALCcontext) {
/* 170 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcDestroyContext;
/* 171 */     if (l == 0L) {
/* 172 */       throw new ALException("Method \"alcDestroyContext\" not available");
/*     */     }
/* 174 */     dispatch_alcDestroyContext0((paramALCcontext == null) ? null : paramALCcontext.getBuffer(), l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native void dispatch_alcDestroyContext0(ByteBuffer paramByteBuffer, long paramLong);
/*     */ 
/*     */   
/*     */   public ALCdevice alcGetContextsDevice(ALCcontext paramALCcontext) {
/* 183 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcGetContextsDevice;
/* 184 */     if (l == 0L) {
/* 185 */       throw new ALException("Method \"alcGetContextsDevice\" not available");
/*     */     }
/*     */     
/* 188 */     ByteBuffer byteBuffer = dispatch_alcGetContextsDevice0((paramALCcontext == null) ? null : paramALCcontext.getBuffer(), l);
/* 189 */     if (byteBuffer == null) return null; 
/* 190 */     return ALCdevice.create(byteBuffer.order(ByteOrder.nativeOrder()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native ByteBuffer dispatch_alcGetContextsDevice0(ByteBuffer paramByteBuffer, long paramLong);
/*     */ 
/*     */   
/*     */   public ALCcontext alcGetCurrentContext() {
/* 199 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcGetCurrentContext;
/* 200 */     if (l == 0L) {
/* 201 */       throw new ALException("Method \"alcGetCurrentContext\" not available");
/*     */     }
/*     */     
/* 204 */     ByteBuffer byteBuffer = dispatch_alcGetCurrentContext0(l);
/* 205 */     if (byteBuffer == null) return null; 
/* 206 */     return ALCcontext.create(byteBuffer.order(ByteOrder.nativeOrder()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native ByteBuffer dispatch_alcGetCurrentContext0(long paramLong);
/*     */ 
/*     */   
/*     */   public int alcGetEnumValue(ALCdevice paramALCdevice, ByteBuffer paramByteBuffer) {
/* 215 */     boolean bool = BufferFactory.isDirect(paramByteBuffer);
/* 216 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcGetEnumValue;
/* 217 */     if (l == 0L) {
/* 218 */       throw new ALException("Method \"alcGetEnumValue\" not available");
/*     */     }
/* 220 */     if (bool) {
/* 221 */       return dispatch_alcGetEnumValue0((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), paramByteBuffer, BufferFactory.getDirectBufferByteOffset(paramByteBuffer), l);
/*     */     }
/* 223 */     return dispatch_alcGetEnumValue1((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), BufferFactory.getArray(paramByteBuffer), BufferFactory.getIndirectBufferByteOffset(paramByteBuffer), l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native int dispatch_alcGetEnumValue0(ByteBuffer paramByteBuffer, Object paramObject, int paramInt, long paramLong);
/*     */ 
/*     */ 
/*     */   
/*     */   private native int dispatch_alcGetEnumValue1(ByteBuffer paramByteBuffer, Object paramObject, int paramInt, long paramLong);
/*     */ 
/*     */   
/*     */   public int alcGetEnumValue(ALCdevice paramALCdevice, byte[] paramArrayOfbyte, int paramInt) {
/* 236 */     if (paramArrayOfbyte != null && paramArrayOfbyte.length <= paramInt)
/* 237 */       throw new ALException("array offset argument \"enumname_offset\" (" + paramInt + ") equals or exceeds array length (" + paramArrayOfbyte.length + ")"); 
/* 238 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcGetEnumValue;
/* 239 */     if (l == 0L) {
/* 240 */       throw new ALException("Method \"alcGetEnumValue\" not available");
/*     */     }
/* 242 */     return dispatch_alcGetEnumValue1((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), paramArrayOfbyte, paramInt, l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int alcGetError(ALCdevice paramALCdevice) {
/* 249 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcGetError;
/* 250 */     if (l == 0L) {
/* 251 */       throw new ALException("Method \"alcGetError\" not available");
/*     */     }
/* 253 */     return dispatch_alcGetError0((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native int dispatch_alcGetError0(ByteBuffer paramByteBuffer, long paramLong);
/*     */ 
/*     */   
/*     */   public void alcGetIntegerv(ALCdevice paramALCdevice, int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/* 262 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 263 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcGetIntegerv;
/* 264 */     if (l == 0L) {
/* 265 */       throw new ALException("Method \"alcGetIntegerv\" not available");
/*     */     }
/* 267 */     if (bool) {
/* 268 */       dispatch_alcGetIntegerv0((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer), l);
/*     */     } else {
/* 270 */       dispatch_alcGetIntegerv1((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer), l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native void dispatch_alcGetIntegerv0(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*     */ 
/*     */   
/*     */   private native void dispatch_alcGetIntegerv1(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, Object paramObject, int paramInt3, long paramLong);
/*     */ 
/*     */   
/*     */   public void alcGetIntegerv(ALCdevice paramALCdevice, int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
/* 283 */     if (paramArrayOfint != null && paramArrayOfint.length <= paramInt3)
/* 284 */       throw new ALException("array offset argument \"data_offset\" (" + paramInt3 + ") equals or exceeds array length (" + paramArrayOfint.length + ")"); 
/* 285 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcGetIntegerv;
/* 286 */     if (l == 0L) {
/* 287 */       throw new ALException("Method \"alcGetIntegerv\" not available");
/*     */     }
/* 289 */     dispatch_alcGetIntegerv1((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), paramInt1, paramInt2, paramArrayOfint, 4 * paramInt3, l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer alcGetStringImpl(ALCdevice paramALCdevice, int paramInt) {
/* 296 */     ALProcAddressLookup.resetALCProcAddressTable();
/* 297 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcGetString;
/* 298 */     if (l == 0L) {
/* 299 */       throw new ALException("Method \"alcGetString\" not available");
/*     */     }
/*     */     
/* 302 */     ByteBuffer byteBuffer = dispatch_alcGetStringImpl0((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), paramInt, l);
/* 303 */     if (byteBuffer == null) return null; 
/* 304 */     return byteBuffer.order(ByteOrder.nativeOrder());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native ByteBuffer dispatch_alcGetStringImpl0(ByteBuffer paramByteBuffer, int paramInt, long paramLong);
/*     */ 
/*     */   
/*     */   public boolean alcIsExtensionPresent(ALCdevice paramALCdevice, String paramString) {
/* 313 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcIsExtensionPresent;
/* 314 */     if (l == 0L) {
/* 315 */       throw new ALException("Method \"alcIsExtensionPresent\" not available");
/*     */     }
/* 317 */     return dispatch_alcIsExtensionPresent0((paramALCdevice == null) ? null : paramALCdevice.getBuffer(), paramString, l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native boolean dispatch_alcIsExtensionPresent0(ByteBuffer paramByteBuffer, String paramString, long paramLong);
/*     */ 
/*     */   
/*     */   public boolean alcMakeContextCurrent(ALCcontext paramALCcontext) {
/* 326 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcMakeContextCurrent;
/* 327 */     if (l == 0L) {
/* 328 */       throw new ALException("Method \"alcMakeContextCurrent\" not available");
/*     */     }
/*     */     
/* 331 */     boolean bool = dispatch_alcMakeContextCurrent0((paramALCcontext == null) ? null : paramALCcontext.getBuffer(), l);
/* 332 */     ALProcAddressLookup.resetALProcAddressTable();
/* 333 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native boolean dispatch_alcMakeContextCurrent0(ByteBuffer paramByteBuffer, long paramLong);
/*     */ 
/*     */   
/*     */   public ALCdevice alcOpenDevice(String paramString) {
/* 342 */     ALProcAddressLookup.resetALCProcAddressTable();
/* 343 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcOpenDevice;
/* 344 */     if (l == 0L) {
/* 345 */       throw new ALException("Method \"alcOpenDevice\" not available");
/*     */     }
/*     */     
/* 348 */     ByteBuffer byteBuffer = dispatch_alcOpenDevice0(paramString, l);
/* 349 */     if (byteBuffer == null) return null; 
/* 350 */     return ALCdevice.create(byteBuffer.order(ByteOrder.nativeOrder()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native ByteBuffer dispatch_alcOpenDevice0(String paramString, long paramLong);
/*     */ 
/*     */   
/*     */   public void alcProcessContext(ALCcontext paramALCcontext) {
/* 359 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcProcessContext;
/* 360 */     if (l == 0L) {
/* 361 */       throw new ALException("Method \"alcProcessContext\" not available");
/*     */     }
/* 363 */     dispatch_alcProcessContext0((paramALCcontext == null) ? null : paramALCcontext.getBuffer(), l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native void dispatch_alcProcessContext0(ByteBuffer paramByteBuffer, long paramLong);
/*     */ 
/*     */   
/*     */   public void alcSuspendContext(ALCcontext paramALCcontext) {
/* 372 */     long l = (ALProcAddressLookup.getALCProcAddressTable())._addressof_alcSuspendContext;
/* 373 */     if (l == 0L) {
/* 374 */       throw new ALException("Method \"alcSuspendContext\" not available");
/*     */     }
/* 376 */     dispatch_alcSuspendContext0((paramALCcontext == null) ? null : paramALCcontext.getBuffer(), l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private native void dispatch_alcSuspendContext0(ByteBuffer paramByteBuffer, long paramLong);
/*     */ 
/*     */   
/*     */   public String alcGetString(ALCdevice paramALCdevice, int paramInt) {
/* 385 */     if (paramALCdevice == null && paramInt == 4101) {
/* 386 */       throw new ALException("Call alcGetDeviceSpecifiers to fetch all available device names");
/*     */     }
/*     */     
/* 389 */     ByteBuffer byteBuffer = alcGetStringImpl(paramALCdevice, paramInt);
/* 390 */     if (byteBuffer == null) {
/* 391 */       return null;
/*     */     }
/* 393 */     byte[] arrayOfByte = new byte[byteBuffer.capacity()];
/* 394 */     byteBuffer.get(arrayOfByte);
/*     */     try {
/* 396 */       return new String(arrayOfByte, "US-ASCII");
/* 397 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 398 */       throw new ALException(unsupportedEncodingException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] alcGetDeviceSpecifiers() {
/* 405 */     ByteBuffer byteBuffer = alcGetStringImpl(null, 4101);
/* 406 */     if (byteBuffer == null) {
/* 407 */       return null;
/*     */     }
/* 409 */     byte[] arrayOfByte = new byte[byteBuffer.capacity()];
/* 410 */     byteBuffer.get(arrayOfByte);
/*     */     try {
/* 412 */       ArrayList arrayList = new ArrayList();
/* 413 */       byte b = 0;
/* 414 */       while (b < arrayOfByte.length) {
/* 415 */         byte b1 = b;
/* 416 */         while (b < arrayOfByte.length && arrayOfByte[b] != 0)
/* 417 */           b++; 
/* 418 */         arrayList.add(new String(arrayOfByte, b1, b - b1, "US-ASCII"));
/* 419 */         b++;
/*     */       } 
/* 421 */       return arrayList.<String>toArray(new String[0]);
/* 422 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 423 */       throw new ALException(unsupportedEncodingException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\joal\impl\ALCImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */