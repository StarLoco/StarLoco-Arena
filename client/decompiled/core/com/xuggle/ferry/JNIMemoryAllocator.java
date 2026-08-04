/*
 * Decompiled with CFR 0.152.
 */
package com.xuggle.ferry;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public final class JNIMemoryAllocator {
    private static final Bk ub = LD.p(JNIMemoryAllocator.class);
    private final Set emI = new HashSet();
    private final ReentrantLock emJ = new ReentrantLock();
    private static final int emK = 5;
    private static final double emL = 1.5;
    private static final boolean emM = true;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void ag(byte[] byArray) {
        this.emJ.lock();
        try {
            if (!this.emI.add(byArray)) assert (false) : "buffers already added";
        }
        finally {
            this.emJ.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void ah(byte[] byArray) {
        this.emJ.lock();
        try {
            if (!this.emI.remove(byArray)) assert (false) : "buffer not in memory";
        }
        finally {
            this.emJ.unlock();
        }
    }

    public byte[] pQ(int n2) {
        byte[] byArray = null;
        ox_1.abJ().abK();
        try {
            int n3 = 0;
            int n4 = 10;
            while (true) {
                try {
                    byArray = new byte[n2];
                }
                catch (OutOfMemoryError outOfMemoryError) {
                    if (++n3 >= 5) {
                        ox_1.abJ().abL();
                        pu_2.acb().aBs();
                        throw outOfMemoryError;
                    }
                    ub.b("retrying ({}) allocation of {} bytes", n3, (Object)n2);
                    try {
                        if (n3 <= 1) {
                            Thread.yield();
                        } else {
                            Thread.sleep(n4);
                            n4 = (int)((double)n4 * 1.5);
                        }
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        throw outOfMemoryError;
                    }
                    pu_2.acb().aBs();
                    continue;
                }
                break;
            }
            this.ag(byArray);
            byArray[byArray.length - 1] = 0;
        }
        catch (Throwable throwable) {
            byArray = null;
        }
        return byArray;
    }

    public void ai(byte[] byArray) {
        this.ah(byArray);
    }

    public static native void setAllocator(long var0, JNIMemoryAllocator var2);

    public static native JNIMemoryAllocator getAllocator(long var0);
}

