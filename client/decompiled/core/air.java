/*
 * Decompiled with CFR 0.152.
 */
public enum air {
    cxZ(0),
    cya(1),
    cyb(768),
    cyc(769),
    cyd(770),
    cye(771),
    cyf(774),
    cyg(775),
    cyh(772),
    cyi(773),
    cyj(776);

    private int adB;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private air() {
        void var3_1;
        this.adB = var3_1;
    }

    public int vf() {
        return this.adB;
    }

    public static air kP(int n2) {
        for (air air2 : air.values()) {
            if (air2.vf() != n2) continue;
            return air2;
        }
        return cxZ;
    }
}

