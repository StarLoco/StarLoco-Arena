/*
 * Decompiled with CFR 0.152.
 */
package com.xuggle.ferry;

import com.xuggle.ferry.Ferry;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReference;

public class FerryJNI {
    FerryJNI() {
    }

    static void noop() {
    }

    public static native int getMemoryModel();

    public static native void setMemoryModel(int var0);

    public static final native long new_AtomicInteger__SWIG_0();

    public static final native long new_AtomicInteger__SWIG_1(int var0);

    public static final native void delete_AtomicInteger(long var0);

    public static final native int AtomicInteger_get(long var0, bI var2);

    public static final native void AtomicInteger_set(long var0, bI var2, int var3);

    public static final native int AtomicInteger_getAndSet(long var0, bI var2, int var3);

    public static final native int AtomicInteger_getAndIncrement(long var0, bI var2);

    public static final native int AtomicInteger_getAndDecrement(long var0, bI var2);

    public static final native int AtomicInteger_getAndAdd(long var0, bI var2, int var3);

    public static final native int AtomicInteger_incrementAndGet(long var0, bI var2);

    public static final native int AtomicInteger_decrementAndGet(long var0, bI var2);

    public static final native int AtomicInteger_addAndGet(long var0, bI var2, int var3);

    public static final native boolean AtomicInteger_compareAndSet(long var0, bI var2, int var3, int var4);

    public static final native boolean AtomicInteger_isAtomic(long var0, bI var2);

    public static final native int RefCounted_acquire(long var0, alp var2);

    public static final native int RefCounted_release(long var0, alp var2);

    public static final native int RefCounted_getCurrentNativeRefCount(long var0, alp var2);

    public static final native int Logger_LEVEL_ERROR_get();

    public static final native int Logger_LEVEL_WARN_get();

    public static final native int Logger_LEVEL_INFO_get();

    public static final native int Logger_LEVEL_DEBUG_get();

    public static final native int Logger_LEVEL_TRACE_get();

    public static final native long Logger_getLogger(String var0);

    public static final native long Logger_getStaticLogger(String var0);

    public static final native boolean Logger_log(long var0, si_0 var2, String var3, int var4, int var5, String var6);

    public static final native boolean Logger_error(long var0, si_0 var2, String var3, int var4, String var5);

    public static final native boolean Logger_warn(long var0, si_0 var2, String var3, int var4, String var5);

    public static final native boolean Logger_info(long var0, si_0 var2, String var3, int var4, String var5);

    public static final native boolean Logger_debug(long var0, si_0 var2, String var3, int var4, String var5);

    public static final native boolean Logger_trace(long var0, si_0 var2, String var3, int var4, String var5);

    public static final native boolean Logger_isLogging(long var0, si_0 var2, int var3);

    public static final native void Logger_setIsLogging(long var0, si_0 var2, int var3, boolean var4);

    public static final native boolean Logger_isGlobalLogging(int var0);

    public static final native void Logger_setGlobalIsLogging(int var0, boolean var1);

    public static final native String Logger_getName(long var0, si_0 var2);

    public static final native void delete_Logger(long var0);

    public static final native long Mutex_make();

    public static final native void Mutex_lock(long var0, WK var2);

    public static final native void Mutex_unlock(long var0, WK var2);

    public static final native int IBuffer_getBufferSize(long var0, di var2);

    public static final native long IBuffer_make__SWIG_0(long var0, alp var2, int var3);

    public static final native int IBuffer_getType(long var0, di var2);

    public static final native void IBuffer_setType(long var0, di var2, int var3);

    public static final native int IBuffer_getTypeSize(int var0);

    public static final native int IBuffer_getSize(long var0, di var2);

    public static final native long IBuffer_make__SWIG_1(long var0, alp var2, int var3, int var4, boolean var5);

    public static final native ByteBuffer IBuffer_java_getByteBuffer(long var0, di var2, int var3, int var4);

    public static final native byte[] IBuffer_getByteArray(long var0, di var2, int var3, int var4);

    public static final native long IBuffer_make__SWIG_2(long var0, alp var2, byte[] var3, int var4, int var5);

    public static final native long IBuffer_make__SWIG_3(long var0, alp var2, ByteBuffer var3, int var4, int var5);

    public static final native long RefCountedTester_make__SWIG_0();

    public static final native long RefCountedTester_make__SWIG_1(long var0, wf_0 var2);

    public static final native long SWIGMutexUpcast(long var0);

    public static final native long SWIGIBufferUpcast(long var0);

    public static final native long SWIGRefCountedTesterUpcast(long var0);

    static {
        atA.a("xuggle-ferry", new Long(3L));
        Ferry.init();
        di di2 = di.a(null, 2);
        AtomicReference<Object> atomicReference = new AtomicReference<Object>(null);
        di2.a(0, 2, atomicReference);
        pu_2 pu_22 = atomicReference.get();
        pu_22.delete();
        di2.delete();
    }
}

