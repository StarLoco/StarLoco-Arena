/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aNz
 */
public enum anz_2 {
    dZu(0),
    dZv(1),
    dZw(2),
    dZx(3),
    dZy(4);

    private final byte axW;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private anz_2() {
        void var3_1;
        this.axW = (byte)var3_1;
    }

    public byte lV() {
        return this.axW;
    }

    static anz_2 bu(byte by) {
        for (anz_2 anz_22 : anz_2.values()) {
            if (by != anz_22.axW) continue;
            return anz_22;
        }
        throw new IllegalArgumentException("Invalid " + anz_2.class.getName() + " id");
    }
}

