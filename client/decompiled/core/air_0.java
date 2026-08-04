/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aIR
 */
final class air_0
implements Bg {
    private final sd dQL;

    air_0(sd sd2) {
        this.dQL = sd2;
    }

    public final boolean c(Object object, float f) {
        int n2 = this.dQL.index(object);
        return n2 >= 0 && this.U(f, this.dQL.I(object));
    }

    private final boolean U(float f, float f2) {
        return f == f2;
    }
}

