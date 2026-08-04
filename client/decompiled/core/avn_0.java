/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from avn
 */
public class avn_0 {
    private static r_0 ddU;
    private static r_0 ddV;

    public static r_0 aIq() {
        return ddV;
    }

    public static void d(r_0 r_02) {
        if (r_02 != ddU) {
            if (ddV != ddU) {
                ddV.D();
            }
            ddV = r_02;
        }
    }

    public static void close() {
        if (ddV != ddU) {
            ddV.D();
            ddV = ddU;
        }
    }

    static {
        ddV = ddU = null;
    }
}

