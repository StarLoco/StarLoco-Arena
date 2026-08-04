/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aru
 */
final class aru_0
implements vl_1 {
    private final ano_2 cPY;

    aru_0(ano_2 ano_22) {
        this.cPY = ano_22;
    }

    public final boolean ba(int n2, int n3) {
        int n4 = this.cPY.hJ(n2);
        return n4 >= 0 && this.a(n3, this.cPY.get(n2));
    }

    private final boolean a(int n2, int n3) {
        return n2 == n3;
    }
}

