/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aGu
 */
public final class agu_0
extends yv_1 {
    public agu_0() {
    }

    public agu_0(agu_0 agu_02) {
        super(agu_02);
    }

    public agu_0(float f, float f2, float f3) {
        this.d(f, f2, f3);
    }

    public agu_0(float f, float f2, float f3, float f4) {
        super(f, f2, f3, f4);
    }

    public final void d(float f, float f2, float f3) {
        this.Hk = f;
        this.Hl = f2;
        this.Hm = f3;
    }

    public final float aSs() {
        return this.getX() * this.getX() + this.getY() * this.getY() + this.id() * this.id();
    }

    public final float aSt() {
        return this.aSs() + this.Fe() * this.Fe();
    }

    public final float OG() {
        return (float)Math.sqrt(this.aSs());
    }

    public final float aSu() {
        return (float)Math.sqrt(this.aSt());
    }

    public final float aSv() {
        float f = this.OG();
        if (f != 0.0f) {
            float f2 = 1.0f / f;
            this.Hk *= f2;
            this.Hl *= f2;
            this.Hm *= f2;
            this.aCv *= f2;
        }
        return f;
    }

    public final float i(agu_0 agu_02) {
        return this.getX() * agu_02.getX() + this.getY() * agu_02.getY() + this.id() * agu_02.id();
    }

    public final void c(agu_0 agu_02, agu_0 agu_03) {
        this.d(agu_02.getY() * agu_03.id() - agu_02.id() * agu_03.getY(), agu_02.id() * agu_03.getX() - agu_02.getX() * agu_03.id(), agu_02.getX() * agu_03.getY() - agu_02.getY() * agu_03.getX(), 0.0f);
    }

    public final agu_0 bH(float f) {
        return new agu_0(this.getX() * f, this.getY() * f, this.id() * f, this.Fe() * f);
    }

    public final agu_0 bI(float f) {
        assert (f != 0.0f);
        return new agu_0(this.getX() / f, this.getY() / f, this.id() / f, this.Fe() / f);
    }

    public final agu_0 j(agu_0 agu_02) {
        return new agu_0(this.getX() + agu_02.getX(), this.getY() + agu_02.getY(), this.id() + agu_02.id(), this.Fe() + agu_02.Fe());
    }

    public final agu_0 k(agu_0 agu_02) {
        return new agu_0(this.getX() - agu_02.getX(), this.getY() - agu_02.getY(), this.id() - agu_02.id(), this.Fe() - agu_02.Fe());
    }

    public final agu_0 aSw() {
        return new agu_0(-this.getX(), -this.getY(), -this.id(), -this.Fe());
    }

    public final void am(float f) {
        this.d(this.getX() * f, this.getY() * f, this.id() * f, this.Fe() * f);
    }

    public final void bJ(float f) {
        assert (f != 0.0f);
        this.d(this.getX() / f, this.getY() / f, this.id() / f, this.Fe() / f);
    }

    public final void l(agu_0 agu_02) {
        this.d(this.getX() + agu_02.getX(), this.getY() + agu_02.getY(), this.id() + agu_02.id(), this.Fe() + agu_02.Fe());
    }

    public final void m(agu_0 agu_02) {
        this.d(this.getX() - agu_02.getX(), this.getY() - agu_02.getY(), this.id() - agu_02.id(), this.Fe() - agu_02.Fe());
    }

    public final void a(float f, agu_0 agu_02) {
        this.d(this.getX() + f * agu_02.getX(), this.getY() + f * agu_02.getY(), this.id() + f * agu_02.id(), this.Fe() + f * agu_02.Fe());
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("V4{x=");
        stringBuffer.append(this.Hk).append(", y=").append(this.Hl).append(", z=").append(this.Hm).append(", w=").append(this.aCv).append("}");
        return stringBuffer.toString();
    }
}

