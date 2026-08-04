/*
 * Decompiled with CFR 0.152.
 */
package com.xuggle.ferry;

import com.xuggle.ferry.FerryJNI;

public class Ferry {
    public static native void init();

    public static final int dU(long l2) {
        return FerryJNI.RefCounted_release(l2, null);
    }

    static {
        FerryJNI.noop();
    }
}

