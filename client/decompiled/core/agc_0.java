/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from agc
 */
public class agc_0 {
    private Object dE;
    private float Hk;
    private float Hl;

    public agc_0(Object object, float f, float f2) {
        this.Hk = f;
        this.Hl = f2;
        this.dE = object;
    }

    public Object getValue() {
        return this.dE;
    }

    public void setValue(Object object) {
        this.dE = object;
    }

    public float getX() {
        return this.Hk;
    }

    public void x(float f) {
        this.Hk = f;
    }

    public float getY() {
        return this.Hl;
    }

    public void y(float f) {
        this.Hl = f;
    }
}

