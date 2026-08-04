/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from afy
 */
public final class afy_0 {
    public static short aA(byte by) {
        if (by < 0) {
            return (short)(256 + by);
        }
        return by;
    }

    public static byte e(byte[] byArray, int n2) {
        return byArray[n2];
    }

    public static short f(byte[] byArray, int n2) {
        return (short)(afy_0.aA(byArray[n2]) | afy_0.aA(byArray[n2 + 1]) << 8);
    }

    public static int g(byte[] byArray, int n2) {
        return afy_0.aA(byArray[n2]) | afy_0.aA(byArray[n2 + 1]) << 8 | afy_0.aA(byArray[n2 + 2]) << 16 | afy_0.aA(byArray[n2 + 3]) << 24;
    }

    public static byte a(byte[] byArray, gs_0 gs_02) {
        int n2 = (Integer)gs_02.get();
        gs_02.set(n2 + 1);
        return afy_0.e(byArray, n2);
    }

    public static short b(byte[] byArray, gs_0 gs_02) {
        int n2 = (Integer)gs_02.get();
        gs_02.set(n2 + 2);
        return afy_0.f(byArray, n2);
    }

    public static int c(byte[] byArray, gs_0 gs_02) {
        int n2 = (Integer)gs_02.get();
        gs_02.set(n2 + 4);
        return afy_0.g(byArray, n2);
    }
}

