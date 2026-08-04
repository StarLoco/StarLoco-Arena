/*
 * Decompiled with CFR 0.152.
 */
import java.awt.geom.Point2D;

/*
 * Renamed from NP
 */
public class np_0
implements cl {
    public int bAB;
    public int bAC;
    public int bAD;
    public int bAE;
    final Point2D bAF = new Point2D.Double();
    double bAG = 1.0;

    public np_0(int n2, int n3) {
        this.bAB = -n2;
        this.bAC = n2;
        this.bAD = -n3;
        this.bAE = n3;
    }

    public void b(double d) {
        this.bAG = d;
    }

    public Point2D a(double d, double d2) {
        if (Math.abs(d) / (double)this.bAC >= Math.abs(d2) / (double)this.bAE) {
            double d3 = (double)this.bAB * this.bAG;
            if (d <= d3 - 0.01) {
                double d4 = d3;
                double d5 = d2 * d4 / d;
                this.bAF.setLocation(d4, d5);
                return this.bAF;
            }
            double d6 = (double)this.bAC * this.bAG;
            if (d >= d6 + 0.01) {
                double d7 = d6;
                double d8 = d2 * d7 / d;
                this.bAF.setLocation(d7, d8);
                return this.bAF;
            }
        } else {
            double d9 = (double)this.bAD * this.bAG;
            if (d2 <= d9 - 0.01) {
                double d10 = d9;
                double d11 = d * d10 / d2;
                this.bAF.setLocation(d11, d10);
                return this.bAF;
            }
            double d12 = (double)this.bAE * this.bAG;
            if (d2 >= d12 + 0.01) {
                double d13 = d12;
                double d14 = d * d13 / d2;
                this.bAF.setLocation(d14, d13);
                return this.bAF;
            }
        }
        return null;
    }
}

