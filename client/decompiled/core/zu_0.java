/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from zu
 */
final class zu_0
implements st_1 {
    private final zy_0 aFs;

    zu_0(zy_0 zy_02) {
        this.aFs = zy_02;
    }

    public final boolean d(byte by, byte by2) {
        int n2 = this.aFs.D(by);
        return n2 >= 0 && this.b(by2, this.aFs.H(by));
    }

    private final boolean b(byte by, byte by2) {
        return by == by2;
    }
}

