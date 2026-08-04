/*
 * Decompiled with CFR 0.152.
 */
package com.xuggle.xuggler.io;

public class FfmpegIO {
    static Boolean aAa = false;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void init() {
        if (!aAa.booleanValue()) {
            Boolean bl2 = aAa;
            synchronized (bl2) {
                if (!aAa.booleanValue()) {
                    atA.a("xuggle-xuggler-io", new Long(3L));
                }
                aAa = true;
                arS.init();
            }
        }
    }

    public static synchronized void a(String string, arS arS2) {
        FfmpegIO.native_registerProtocolHandler(string, arS2);
    }

    public static int a(ui_1 ui_12, String string, int n2) {
        return FfmpegIO.native_url_open(ui_12, string, n2);
    }

    public static int a(ui_1 ui_12, byte[] byArray, int n2) {
        return FfmpegIO.native_url_read(ui_12, byArray, n2);
    }

    public static int a(ui_1 ui_12) {
        return FfmpegIO.native_url_close(ui_12);
    }

    public static int b(ui_1 ui_12, byte[] byArray, int n2) {
        return FfmpegIO.native_url_write(ui_12, byArray, n2);
    }

    public static long a(ui_1 ui_12, long l2, int n2) {
        return FfmpegIO.native_url_seek(ui_12, l2, n2);
    }

    private static native int native_registerProtocolHandler(String var0, arS var1);

    private static native int native_url_open(ui_1 var0, String var1, int var2);

    private static native int native_url_read(ui_1 var0, byte[] var1, int var2);

    private static native int native_url_write(ui_1 var0, byte[] var1, int var2);

    private static native long native_url_seek(ui_1 var0, long var1, int var3);

    private static native int native_url_close(ui_1 var0);

    static {
        FfmpegIO.init();
    }
}

