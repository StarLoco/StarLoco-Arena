/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from kj
 */
final class kj_2
implements xf {
    private final auf DS;

    kj_2(auf auf2) {
        this.DS = auf2;
    }

    public final boolean c(int n2, long l2) {
        int n3 = this.DS.hJ(n2);
        return n3 >= 0 && this.c(l2, this.DS.get(n2));
    }

    private final boolean c(long l2, long l3) {
        return l2 == l3;
    }
}

