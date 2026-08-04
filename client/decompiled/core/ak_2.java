/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aK
 */
public class ak_2 {
    public float cM = Float.MAX_VALUE;
    public float cN = -3.4028235E38f;
    public float cO = Float.MAX_VALUE;
    public float cP = -3.4028235E38f;

    public ak_2(ak_2 ak_22) {
        this.d(ak_22.cM, ak_22.cN, ak_22.cO, ak_22.cP);
    }

    public ak_2(int n2, int n3, int n4, int n5) {
        this.d(n2, n3, n4, n5);
    }

    public ak_2() {
    }

    public final void reset() {
        this.cM = Float.MAX_VALUE;
        this.cO = Float.MAX_VALUE;
        this.cN = -3.4028235E38f;
        this.cP = -3.4028235E38f;
    }

    public final void d(float f, float f2, float f3, float f4) {
        this.cM = f;
        this.cN = f2;
        this.cO = f3;
        this.cP = f4;
    }

    public final float bB() {
        return this.cM;
    }

    public final void d(float f) {
        this.cM = f;
    }

    public final float bC() {
        return this.cN;
    }

    public final void e(float f) {
        this.cN = f;
    }

    public final float bD() {
        return this.cO;
    }

    public final void f(float f) {
        this.cO = f;
    }

    public final float bE() {
        return this.cP;
    }

    public final void g(float f) {
        this.cP = f;
    }

    public final float bF() {
        return this.cN - this.cM + 1.0f;
    }

    public final float bG() {
        return this.cP - this.cO + 1.0f;
    }

    public final boolean a(float f, float f2) {
        return f >= this.cM && f <= this.cN && f2 >= this.cO && f2 <= this.cP;
    }

    public final boolean a(ak_2 ak_22) {
        if (ak_22.cM > this.cN) {
            return false;
        }
        if (ak_22.cN < this.cM) {
            return false;
        }
        if (ak_22.cO > this.cP) {
            return false;
        }
        return !(ak_22.cP < this.cO);
    }

    public final void b(float f, float f2) {
        this.cM = Math.min(f, this.cM);
        this.cN = Math.max(f, this.cN);
        this.cO = Math.min(f2, this.cO);
        this.cP = Math.max(f2, this.cP);
    }

    public final void b(ak_2 ak_22) {
        this.cM = Math.min(ak_22.cM, this.cM);
        this.cN = Math.max(ak_22.cN, this.cN);
        this.cO = Math.min(ak_22.cO, this.cO);
        this.cP = Math.max(ak_22.cP, this.cP);
    }

    public String toString() {
        return "(" + this.cM + ", " + this.cO + ") - (" + this.cN + ", " + this.cP + ")";
    }
}

