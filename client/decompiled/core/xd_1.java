/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from xD
 */
public enum xd_1 {
    azj(false),
    azk(true),
    azl(false),
    azm(true);

    private final boolean azn;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private xd_1() {
        void var3_1;
        this.azn = var3_1;
    }

    public boolean Ep() {
        return this.azn;
    }

    public static xd_1 cS(String string) {
        xd_1[] xd_1Array;
        for (xd_1 xd_12 : xd_1Array = xd_1.values()) {
            if (!xd_12.name().equals(string.toUpperCase())) continue;
            return xd_12;
        }
        return xd_1Array[0];
    }

    public static boolean a(xd_1 xd_12, xd_1 xd_13) {
        return xd_12.ordinal() % 2 != xd_13.ordinal() % 2;
    }
}

