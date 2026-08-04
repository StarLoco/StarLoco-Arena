/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from DK
 */
final class dk_2
implements sg_1 {
    private final aba_0 aOg;

    dk_2(aba_0 aba_02) {
        this.aOg = aba_02;
    }

    public final boolean f(long l2, long l3) {
        int n2 = this.aOg.az(l2);
        return n2 >= 0 && this.c(l3, this.aOg.du(l2));
    }

    private final boolean c(long l2, long l3) {
        return l2 == l3;
    }
}

