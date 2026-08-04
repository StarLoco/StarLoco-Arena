/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ty
 */
public class ty_2
implements afr_1 {
    private final float[] ane = new float[]{1.0f, 1.0f, 1.0f};
    private float IQ;
    private float IR;
    private float IS;
    private boolean tY;

    public float f(double d, double d2, double d3) {
        return this.IQ;
    }

    public float g(double d, double d2, double d3) {
        return this.IR;
    }

    public float h(double d, double d2, double d3) {
        return this.IS;
    }

    public void a(ajf_1 ajf_12) {
        ajf_12.f(this.ane[0], this.ane[1], this.ane[2]);
    }

    public void update(int n2) {
        this.tY = false;
    }

    public boolean jU() {
        return this.tY;
    }

    public boolean zH() {
        return false;
    }

    public void c(float f, float f2, float f3) {
        this.ane[0] = f < 0.0f ? f + 1.0f : 1.0f;
        this.ane[1] = f2 < 0.0f ? f2 + 1.0f : 1.0f;
        this.ane[2] = f3 < 0.0f ? f3 + 1.0f : 1.0f;
        this.IQ = f;
        this.IR = f2;
        this.IS = f3;
        this.tY = true;
    }
}

