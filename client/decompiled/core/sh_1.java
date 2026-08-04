/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from sh
 */
final class sh_1
implements tr_2 {
    private final aGz aiS;

    sh_1(aGz aGz2) {
        this.aiS = aGz2;
    }

    public final boolean f(short s, short s2) {
        int n2 = this.aiS.ab(s);
        return n2 >= 0 && this.i(s2, this.aiS.cp(s));
    }

    private final boolean i(short s, short s2) {
        return s == s2;
    }
}

