/*
 * Decompiled with CFR 0.152.
 */
public enum aro {
    cPs(1),
    cPt(2),
    cPu(3),
    cPv(4),
    cPw(5),
    cPx(6),
    cPy(7),
    cPz(8),
    cPA(9),
    cPB(10);

    private static final afj_0 cPC;
    private final byte axW;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aro() {
        void var3_1;
        this.axW = var3_1;
    }

    public byte lV() {
        return this.axW;
    }

    public static aro aR(byte by) {
        return (aro)((Object)cPC.bk(by));
    }

    static {
        cPC = new afj_0();
        for (aro aro2 : aro.values()) {
            cPC.b(aro2.lV(), (Object)aro2);
        }
    }
}

