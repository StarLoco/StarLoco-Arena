/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from avW
 */
public class avw_0
extends aMn {
    private double dhb = 4.8;
    private static double dhc = 9.81;
    private static double dhd = 1.0;
    private double dhe = dhd;
    private double dhf = dhc;
    private double bVM;
    private double bVN;
    private double dhg;
    private double dhh;
    private double dhi;
    private double dhj;
    private double dhk;
    private double dhl;
    private int IP;

    public avw_0(Du du, double d, double d2, double d3, double d4) {
        this(du, d, d2, d3, d4, dhd);
    }

    public avw_0(Du du, double d, double d2, double d3, double d4, double d5) {
        super(du);
        this.dhe = d5;
        this.bVM = this.Ie.getWorldX();
        this.bVN = this.Ie.getWorldY();
        this.dhg = this.Ie.getAltitude();
        this.dhh = d3 - this.dhg;
        d4 = Math.toRadians(d4 == 0.0 ? 1.0 : d4);
        double d6 = Math.sqrt(Math.pow(d - this.bVM, 2.0) + Math.pow(d2 - this.bVN, 2.0));
        double d7 = Math.sqrt(this.dhf * d6 / Math.sin(2.0 * d4));
        double d8 = Math.atan((d2 - this.bVN) / (d - this.bVM));
        if (d - this.bVM < 0.0) {
            d8 += Math.PI;
        }
        this.dhl = 2.0 * d7 * Math.sin(d4) / this.dhf;
        double d9 = d7 * Math.cos(d4);
        this.dhi = Math.cos(d8) * d9;
        this.dhj = Math.sin(d8) * d9;
        this.dhk = d7 * Math.sin(d4);
        this.dhh /= this.dhl;
    }

    public void bI(int n2) {
        this.IP += n2;
        double d = (double)this.IP * (this.dhe / 1000.0);
        if (this.Ie == null || d > this.dhl) {
            this.aWV();
            return;
        }
        double d2 = this.dhi * d + this.bVM;
        double d3 = this.dhj * d + this.bVN;
        double d4 = -this.dhf / 2.0 * Math.pow(d, 2.0) + this.dhk * d;
        double d5 = 8.6 * d4 + this.dhg + d * this.dhh;
        this.Ie.a(d2, d3, d5);
    }

    public double aJk() {
        return this.dhl * 1000.0 / this.dhe;
    }
}

