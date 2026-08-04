/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from EU
 */
public final class eu_2
extends yv_1 {
    public eu_2() {
    }

    public eu_2(eu_2 eu_22) {
        super(eu_22);
    }

    public eu_2(float f, float f2, float f3, float f4) {
        super(f, f2, f3, f4);
    }

    public eu_2(agu_0 agu_02, float f) {
        this.a(agu_02, f);
    }

    public final float OG() {
        return (float)Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.id() * this.id() + this.Fe() * this.Fe());
    }

    public final void normalize() {
        assert (this.OG() > 0.0f) : "Unable to normalize the quaternion since the norme is null";
        float f = this.OG();
        this.Hk /= f;
        this.Hl /= f;
        this.Hm /= f;
        this.aCv /= f;
    }

    public final void OH() {
        this.d(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public final void a(agu_0 agu_02, float f) {
        float f2 = f * 0.5f;
        float f3 = ej_0.i(f2);
        this.d(agu_02.getX() * f3, agu_02.getY() * f3, agu_02.id() * f3, ej_0.j(f2));
        this.normalize();
    }

    public final void d(float f, float f2, float f3) {
        eu_2 eu_22 = new eu_2(new agu_0(ej_0.i(f * 0.5f), 0.0f, 0.0f), ej_0.j(f * 0.5f));
        eu_2 eu_23 = new eu_2(new agu_0(0.0f, ej_0.i(f2 * 0.5f), 0.0f), ej_0.j(f2 * 0.5f));
        eu_2 eu_24 = new eu_2(new agu_0(0.0f, 0.0f, ej_0.i(f3 * 0.5f)), ej_0.j(f3 * 0.5f));
        this.a((yv_1)eu_22.a(eu_23).a(eu_24));
    }

    public final eu_2 OI() {
        return new eu_2(-this.getX(), -this.getY(), -this.id(), this.Fe());
    }

    public final eu_2 OJ() {
        eu_2 eu_22 = this.OI();
        float f = eu_22.getX() * eu_22.getX() + eu_22.getY() * eu_22.getY() + eu_22.id() * eu_22.id() + eu_22.Fe() * eu_22.Fe();
        eu_22.Hk /= f;
        eu_22.Hl /= f;
        eu_22.Hm /= f;
        eu_22.aCv /= f;
        return eu_22;
    }

    public final eu_2 a(eu_2 eu_22) {
        return new eu_2(this.Fe() * eu_22.getX() + this.getX() * eu_22.Fe() + this.getY() * eu_22.id() - this.id() * eu_22.getY(), this.Fe() * eu_22.getY() + this.getY() * eu_22.Fe() + this.id() * eu_22.getX() - this.getX() * eu_22.id(), this.Fe() * eu_22.id() + this.id() * eu_22.Fe() + this.getX() * eu_22.getY() - this.getY() * eu_22.getX(), this.Fe() * eu_22.Fe() - this.getX() * eu_22.getX() - this.getY() * eu_22.getY() - this.id() * eu_22.id());
    }

    public final eu_2 b(eu_2 eu_22) {
        return this.a(eu_22.OJ());
    }

    public final void c(eu_2 eu_22) {
        this.a((yv_1)this.a(eu_22));
    }

    public final void d(eu_2 eu_22) {
        this.a((yv_1)this.b(eu_22));
    }

    public void a(eu_2 eu_22, eu_2 eu_23, float f) {
        float f2 = eu_22.getX() * eu_23.getX() + eu_22.getY() * eu_23.getY() + eu_22.id() * eu_23.id() + eu_22.Fe() * eu_23.Fe();
        if (1.0f + f2 > 1.0E-5f) {
            float f3;
            float f4;
            if (1.0f - f2 > 1.0E-5f) {
                float f5 = ej_0.m(f2);
                float f6 = ej_0.i(f5);
                f4 = ej_0.i((1.0f - f) * f5) / f6;
                f3 = ej_0.i(f * f5) / f6;
            } else {
                f4 = 1.0f - f;
                f3 = f;
            }
            this.d(f4 * eu_22.getX() + f3 * eu_23.getX(), f4 * eu_22.getY() + f3 * eu_23.getY(), f4 * eu_22.id() + f3 * eu_23.id(), f4 * eu_22.Fe() + f3 * eu_23.Fe());
        } else {
            float f7 = ej_0.i((1.0f - f) * 1.5707964f);
            float f8 = ej_0.i(f * 1.5707964f);
            this.d(f7 * eu_22.getX() - f8 * eu_23.getY(), f7 * eu_22.getY() + f8 * eu_23.getX(), f7 * eu_22.id() - f8 * eu_23.Fe(), f7 * eu_22.Fe() + f8 * eu_23.id());
        }
    }
}

