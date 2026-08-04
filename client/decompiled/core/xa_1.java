/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from XA
 */
final class xa_1
implements ro_1 {
    private final kl_1 bYQ;

    xa_1(kl_1 kl_12) {
        this.bYQ = kl_12;
    }

    public final boolean g(long l2, short s) {
        int n2 = this.bYQ.az(l2);
        return n2 >= 0 && this.i(s, this.bYQ.bU(l2));
    }

    private final boolean i(short s, short s2) {
        return s == s2;
    }
}

