/*
 * Decompiled with CFR 0.152.
 */
public abstract class h
extends abx_2 {
    private abx_2 l;
    private abx_2 m;

    protected h(aqw aqw2) {
        super(aqw2);
    }

    public final Float m() {
        Number number = (Number)this.l.get();
        Number number2 = (Number)this.m.get();
        return this.a(number, number2);
    }

    protected abstract Float a(Number var1, Number var2);

    public final void a(abx_2[] abx_2Array) {
        super.a(abx_2Array);
        this.l = abx_2Array[0];
        this.m = abx_2Array[1];
    }
}

