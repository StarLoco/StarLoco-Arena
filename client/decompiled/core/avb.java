/*
 * Decompiled with CFR 0.152.
 */
final class avb
implements asz_0 {
    private final pk_0 cYw;

    avb(pk_0 pk_02) {
        this.cYw = pk_02;
    }

    public final boolean c(long l2, byte by) {
        int n2 = this.cYw.az(l2);
        return n2 >= 0 && this.b(by, this.cYw.cn(l2));
    }

    private final boolean b(byte by, byte by2) {
        return by == by2;
    }
}

