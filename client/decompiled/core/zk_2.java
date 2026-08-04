/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Zk
 */
final class zk_2
implements Dw {
    private final no ccJ;

    zk_2(no no2) {
        this.ccJ = no2;
    }

    public final boolean i(int n2, short s) {
        int n3 = this.ccJ.hJ(n2);
        return n3 >= 0 && this.i(s, this.ccJ.get(n2));
    }

    private final boolean i(short s, short s2) {
        return s == s2;
    }
}

