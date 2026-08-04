/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from azO
 */
public class azo_0 {
    float cM = Float.MIN_VALUE;
    float cN = Float.MAX_VALUE;
    float cO = Float.MIN_VALUE;
    float cP = Float.MAX_VALUE;
    float doM = Float.MIN_VALUE;
    float doN = Float.MAX_VALUE;

    public azo_0(float f, float f2, float f3, float f4, float f5, float f6) {
        this.b(f, f2, f3, f4, f5, f6);
    }

    public azo_0() {
    }

    public static azo_0 aMo() {
        return new azo_0();
    }

    public final void b(float f, float f2, float f3, float f4, float f5, float f6) {
        this.bt(f);
        this.bs(f2);
        this.bv(f3);
        this.bu(f4);
        this.bx(f5);
        this.bw(f6);
    }

    public final float aMp() {
        return this.cN;
    }

    public final void bs(float f) {
        if (this.cM > f) {
            this.cN = this.cM;
            this.cM = f;
        } else {
            this.cN = f;
        }
    }

    public final float aMq() {
        return this.cM;
    }

    public final void bt(float f) {
        if (this.cN < f) {
            this.cM = this.cN;
            this.cN = f;
        } else {
            this.cM = f;
        }
    }

    public final float aMr() {
        return this.cP;
    }

    public final void bu(float f) {
        if (this.cO > f) {
            this.cP = this.cO;
            this.cO = f;
        } else {
            this.cP = f;
        }
    }

    public final float aMs() {
        return this.cO;
    }

    public final void bv(float f) {
        if (this.cP < f) {
            this.cO = this.cP;
            this.cP = f;
        } else {
            this.cO = f;
        }
    }

    public final float aMt() {
        return this.doN;
    }

    public final void bw(float f) {
        if (this.doM > f) {
            this.doN = this.doM;
            this.doM = f;
        } else {
            this.doN = f;
        }
    }

    public final float aMu() {
        return this.doM;
    }

    public final void bx(float f) {
        if (this.doN < f) {
            this.doM = this.doN;
            this.doN = f;
        } else {
            this.doM = f;
        }
    }

    public final boolean n(float f, float f2, float f3) {
        return this.cM <= f && f <= this.cN && this.cO <= f2 && f2 <= this.cP && this.doM <= f3 && f3 <= this.doN;
    }

    public final boolean b(azo_0 azo_02) {
        if (azo_02 == null) {
            return false;
        }
        return this.cM <= azo_02.aMp() && azo_02.aMq() <= this.cN && this.cO <= azo_02.aMr() && azo_02.aMs() <= this.cP && this.doM <= azo_02.aMt() && azo_02.aMu() <= this.doN;
    }

    public final boolean c(azo_0 azo_02) {
        return this.cM <= azo_02.aMq() && azo_02.aMp() <= this.cN && this.cO <= azo_02.aMs() && azo_02.aMr() <= this.cP && this.doM <= azo_02.aMu() && azo_02.aMt() <= this.doN;
    }

    public final float bF() {
        return this.cN - this.cM;
    }

    public final float bG() {
        return this.cP - this.cM;
    }
}

