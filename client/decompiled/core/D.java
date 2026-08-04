/*
 * Decompiled with CFR 0.152.
 */
final class D
implements aca_2 {
    private final aim_1 ax;

    D(aim_1 aim_12) {
        this.ax = aim_12;
    }

    public final boolean a(byte by, int n2) {
        int n3 = this.ax.D(by);
        return n3 >= 0 && this.a(n2, this.ax.aD(by));
    }

    private final boolean a(int n2, int n3) {
        return n2 == n3;
    }
}

