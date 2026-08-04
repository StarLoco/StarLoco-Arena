/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from yv
 */
public class yv_1 {
    public float Hk;
    public float Hl;
    public float Hm;
    public float aCv;

    public yv_1() {
    }

    public yv_1(yv_1 yv_12) {
        this.b(yv_12);
    }

    public yv_1(float f, float f2, float f3, float f4) {
        this.d(f, f2, f3, f4);
    }

    public final void d(float f, float f2, float f3, float f4) {
        this.Hk = f;
        this.Hl = f2;
        this.Hm = f3;
        this.aCv = f4;
    }

    public final void a(yv_1 yv_12) {
        this.b(yv_12);
    }

    public final void x(float f) {
        this.Hk = f;
    }

    public final void y(float f) {
        this.Hl = f;
    }

    public final void X(float f) {
        this.Hm = f;
    }

    public final void Y(float f) {
        this.aCv = f;
    }

    public final void b(yv_1 yv_12) {
        this.Hk = yv_12.Hk;
        this.Hl = yv_12.Hl;
        this.Hm = yv_12.Hm;
        this.aCv = yv_12.aCv;
    }

    public final float getX() {
        return this.Hk;
    }

    public final float getY() {
        return this.Hl;
    }

    public final float id() {
        return this.Hm;
    }

    public final float Fe() {
        return this.aCv;
    }

    public final boolean c(yv_1 yv_12) {
        return ej_0.f(this.Hk, yv_12.Hk) && ej_0.f(this.Hl, yv_12.Hl) && ej_0.f(this.Hm, yv_12.Hm) && ej_0.f(this.aCv, yv_12.aCv);
    }
}

