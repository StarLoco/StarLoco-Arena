/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.kernel.core.maths.Matrix44;

public final class avz
extends ams_0 {
    private agu_0 deM;
    private agu_0 deN;
    private eu_2 deO;
    private Matrix44 deP;
    private boolean tY;

    public avz() {
        this.deM = new agu_0();
        this.deO = new eu_2();
        this.deN = new agu_0();
        this.deP = Matrix44.acr();
        this.OH();
    }

    public avz(avz avz2) {
        this.a(avz2);
    }

    public final void OH() {
        this.deM.d(1.0f, 1.0f, 1.0f, 1.0f);
        this.deN.d(0.0f, 0.0f, 0.0f, 1.0f);
        this.deO.OH();
        this.uz.OH();
        this.deP.OH();
        this.tY = false;
    }

    public final void a(avz avz2) {
        this.deM = new agu_0(avz2.aIK());
        this.deN = new agu_0(avz2.aIG());
        this.deO = new eu_2(avz2.aII());
        this.uz.d(avz2.ki());
    }

    public Matrix44 ki() {
        this.update();
        return this.uz;
    }

    public aba_1 pc() {
        return aba_1.dqU;
    }

    public ams_0 pd() {
        return new avz(this);
    }

    public final void e(agu_0 agu_02) {
        this.deN.a(agu_02);
        this.tY = true;
    }

    public final void e(float f, float f2, float f3) {
        this.deN.d(f, f2, f3);
        this.tY = true;
    }

    public final void g(agu_0 agu_02) {
        this.deN.l(agu_02);
        this.tY = true;
    }

    public final agu_0 aIF() {
        return new agu_0(this.deN);
    }

    public final agu_0 aIG() {
        return this.deN;
    }

    public final void f(eu_2 eu_22) {
        this.deO.a((yv_1)eu_22);
        this.tY = true;
    }

    public final void g(eu_2 eu_22) {
        this.deO.c(eu_22);
        this.tY = true;
    }

    public final eu_2 aIH() {
        return new eu_2(this.deO);
    }

    public final eu_2 aII() {
        return this.deO;
    }

    public final void f(agu_0 agu_02) {
        this.deM.a(agu_02);
        this.tY = true;
    }

    public final void m(float f, float f2, float f3) {
        this.deM.d(f, f2, f3);
        this.tY = true;
    }

    public final void h(agu_0 agu_02) {
        this.deM.l(agu_02);
        this.tY = true;
    }

    public final agu_0 aIJ() {
        return new agu_0(this.deM);
    }

    public final agu_0 aIK() {
        return this.deM;
    }

    public Matrix44 aIL() {
        return this.deP;
    }

    public void i(Matrix44 matrix44) {
        this.deP = matrix44;
    }

    public boolean aIM() {
        return this.tY;
    }

    public void eo(boolean bl2) {
        this.tY = bl2;
    }

    private void update() {
        if (!this.tY) {
            return;
        }
        this.uz.e(this.deO);
        if (!this.deP.isIdentity()) {
            this.uz.f(this.deP);
        }
        this.uz.e(this.deN);
        this.uz.f(this.deM);
        this.tY = false;
    }
}

