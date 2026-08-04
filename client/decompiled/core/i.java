/*
 * Decompiled with CFR 0.152.
 */
public abstract class i
extends abx_2 {
    private abx_2 o;

    protected i(aqw aqw2) {
        super(aqw2);
    }

    public final Float m() {
        return Float.valueOf(Math.abs(this.a((Number)this.o.get()).floatValue()));
    }

    protected abstract Float a(Number var1);

    public final void a(abx_2[] abx_2Array) {
        super.a(abx_2Array);
        this.o = abx_2Array[0];
    }
}

