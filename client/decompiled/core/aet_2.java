/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aeT
 */
final class aet_2
implements aca_2 {
    private int hN = 0;
    final /* synthetic */ aim_1 cpU;

    private aet_2(aim_1 aim_12) {
        this.cpU = aim_12;
    }

    public int dY() {
        return this.hN;
    }

    public final boolean a(byte by, int n2) {
        this.hN += this.cpU.auF.F(by) ^ ha_0.aQ(n2);
        return true;
    }

    /* synthetic */ aet_2(aim_1 aim_12, ey_0 ey_02) {
        this(aim_12);
    }
}

