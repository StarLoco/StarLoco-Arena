/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.opengl;

public class FullscreenUtils {
    public static final FullscreenUtils aUN;

    private FullscreenUtils() {
    }

    public native void showMenuAndDock(boolean var1);

    static {
        if (atY.aGZ() == atY.cVy) {
            System.loadLibrary("fullscreenutils");
        }
        aUN = new FullscreenUtils();
    }
}

