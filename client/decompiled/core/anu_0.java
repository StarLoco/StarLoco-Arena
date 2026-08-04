/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from anu
 */
final class anu_0
implements asz_0 {
    private int hN = 0;
    final /* synthetic */ pk_0 cJh;

    private anu_0(pk_0 pk_02) {
        this.cJh = pk_02;
    }

    public int dY() {
        return this.hN;
    }

    public final boolean c(long l2, byte by) {
        this.hN += this.cJh.atz.aP(l2) ^ ha_0.aQ(by);
        return true;
    }

    /* synthetic */ anu_0(pk_0 pk_02, abp_0 abp_02) {
        this(pk_02);
    }
}

