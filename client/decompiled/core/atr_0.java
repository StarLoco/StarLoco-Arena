/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from atR
 */
public enum atr_0 {
    cUF(1),
    cUG(100),
    cUH(101),
    cUI(110),
    cUJ(200),
    cUK(210),
    cUL(220),
    cUM(230),
    cUN(231),
    cUO(232),
    cUP(250),
    cUQ(251),
    cUR(300),
    cUS(360),
    cUT(400),
    cUU(500),
    cUV(600),
    cUW(700),
    cUX(800),
    cUY(801),
    cUZ(802),
    cVa(900),
    cVb(901),
    cVc(902),
    cVd(1000),
    cVe(1001),
    cVf(1100),
    cVg(1101),
    cVh(1102),
    cVi(1200),
    cVj(1300),
    cVk(1400),
    cVl(1500),
    cVm(1600);

    private int aW;
    private static final lb_0 cVn;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private atr_0() {
        void var3_1;
        this.aW = var3_1;
    }

    public int getId() {
        return this.aW;
    }

    public static atr_0 mk(int n2) {
        return (atr_0)((Object)cVn.get(n2));
    }

    static {
        cVn = new lb_0();
        for (atr_0 atr_02 : atr_0.values()) {
            cVn.c(atr_02.getId(), (Object)atr_02);
        }
    }
}

