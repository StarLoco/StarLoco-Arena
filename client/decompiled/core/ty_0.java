/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from tY
 */
public final class ty_0
implements qq_1 {
    private final float Hk;
    private final float Hl;
    private final float Hm;
    private final boolean aop;
    private final int aoq;

    public ty_0(float f, float f2, float f3) {
        this(f, f2, f3, false, 0);
    }

    public ty_0(float f, float f2, float f3, boolean bl2) {
        this(f, f2, f3, bl2, 0);
    }

    public ty_0(float f, float f2, float f3, boolean bl2, int n2) {
        this.Hk = f;
        this.Hl = f2;
        this.Hm = f3;
        this.aop = bl2;
        this.aoq = n2;
    }

    public float zR() {
        return this.Hk - this.Hl;
    }

    public float zS() {
        return -(this.Hk + this.Hl);
    }

    public boolean zT() {
        return this.aop;
    }

    public int zU() {
        return this.aoq;
    }
}

