/*
 * Decompiled with CFR 0.152.
 */
final class kp
implements aht_0 {
    private final vy_1 Ea;

    kp(vy_1 vy_12) {
        this.Ea = vy_12;
    }

    public final boolean a(short s, byte by) {
        int n2 = this.Ea.ab(s);
        return n2 >= 0 && this.b(by, this.Ea.bp(s));
    }

    private final boolean b(byte by, byte by2) {
        return by == by2;
    }
}

