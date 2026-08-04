/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from awT
 */
public class awt_0 {
    private int IP;
    private double diA;
    private double diB;
    private double bVK;
    private double bVL;
    private double cWz;

    public void v(double d) {
        this.bVL = d;
    }

    public void w(double d) {
        this.bVK = d;
    }

    public void I(double d) {
        this.diB = this.cWz = d;
        this.diA = this.cWz;
    }

    public double getValue() {
        return this.cWz;
    }

    public void J(double d) {
        this.diA = d;
        this.IP = 0;
    }

    public void K(double d) {
        this.diB = d;
        this.diA = this.cWz;
        this.IP = 0;
    }

    public double aJI() {
        return this.diB;
    }

    public double mD(int n2) {
        if (Math.abs(this.diB - this.cWz) < this.bVL) {
            this.I(this.diB);
            return this.diB;
        }
        this.IP += n2;
        float f = (float)((double)this.IP * this.bVK) / 1000.0f;
        if (f > 1.0f) {
            this.cWz = this.diB;
        } else {
            float f2 = ej_0.i(f * 1.5707964f);
            this.cWz = this.diA + (this.diB - this.diA) * (double)f2;
        }
        return this.cWz;
    }
}

