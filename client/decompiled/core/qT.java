/*
 * Decompiled with CFR 0.152.
 */
final class qT
implements hm_0 {
    private int hN = 0;
    final /* synthetic */ zm_1 afA;

    private qT(zm_1 zm_12) {
        this.afA = zm_12;
    }

    public int dY() {
        return this.hN;
    }

    public final boolean a(short s, Object object) {
        this.hN += this.afA.aqw.ad(s) ^ ha_0.q(object);
        return true;
    }

    /* synthetic */ qT(zm_1 zm_12, VK vK) {
        this(zm_12);
    }
}

