/*
 * Decompiled with CFR 0.152.
 */
final class aOt
implements di_2 {
    private final asc ema;

    aOt(asc asc2) {
        this.ema = asc2;
    }

    public final boolean c(int n2, byte by) {
        int n3 = this.ema.hJ(n2);
        return n3 >= 0 && this.b(by, this.ema.get(n2));
    }

    private final boolean b(byte by, byte by2) {
        return by == by2;
    }
}

