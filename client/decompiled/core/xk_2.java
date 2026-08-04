/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from XK
 */
final class xk_2
implements gg_1 {
    private final axu bZq;

    xk_2(axu axu2) {
        this.bZq = axu2;
    }

    public final boolean a(byte by, short s) {
        int n2 = this.bZq.D(by);
        return n2 >= 0 && this.i(s, this.bZq.ba(by));
    }

    private final boolean i(short s, short s2) {
        return s == s2;
    }
}

