/*
 * Decompiled with CFR 0.152.
 */
package com.xuggle.xuggler;

import com.xuggle.xuggler.XugglerJNI;

public class Xuggler {
    public static void main(String[] stringArray) {
        System.out.println("WARNING: The Converter main class has moved to: com.xuggle.xuggler.Converter");
    }

    public static native void init();

    static {
        XugglerJNI.noop();
    }
}

