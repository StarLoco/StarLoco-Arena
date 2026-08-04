/*
 * Decompiled with CFR 0.152.
 */
import java.awt.geom.Point2D;

public class WR {
    private int IP;
    private double bVK;
    private double bVL;
    private double bVM;
    private double bVN;
    private double bVO;
    private double bVP;
    private double bVQ;
    private double bVR;
    private cl bVS = new np_0(100, 50);
    private aha_0 bVT = aco_0.ckP;

    public void b(aco_0 aco_02) {
        this.bVT = aco_02;
    }

    public void v(double d) {
        this.bVL = d;
    }

    protected double ajA() {
        return this.bVL;
    }

    public void w(double d) {
        this.bVK = d;
    }

    public void e(double d, double d2) {
        this.bVO = this.bVQ = d;
        this.bVM = this.bVQ;
        this.bVP = this.bVR = d2;
        this.bVN = this.bVR;
    }

    public void setX(int n2) {
        this.bVO = this.bVQ = (double)n2;
        this.bVM = this.bVQ;
    }

    public void setY(int n2) {
        this.bVP = this.bVR = (double)n2;
        this.bVN = this.bVR;
    }

    public void f(double d, double d2) {
        this.bVM = d;
        this.bVN = d2;
        this.IP = 0;
    }

    public void g(double d, double d2) {
        this.bVO = d;
        this.bVP = d2;
        this.bVM = this.bVQ;
        this.bVN = this.bVR;
        this.IP = 0;
    }

    public double ajB() {
        return this.bVO;
    }

    public double ajC() {
        return this.bVP;
    }

    public double ajD() {
        return this.bVQ;
    }

    public double ajE() {
        return this.bVR;
    }

    public boolean b(int n2, double d) {
        this.bVS.b(d);
        return this.iP(n2);
    }

    protected boolean iP(int n2) {
        double d = this.bVO - this.bVM;
        double d2 = this.bVP - this.bVN;
        Point2D point2D = this.bVS.a(d, d2);
        if (point2D == null) {
            return false;
        }
        this.IP += n2;
        float f = (float)((double)this.IP * this.bVK) / 1000.0f;
        if (f > 1.0f) {
            this.bVQ = this.bVO;
            this.bVR = this.bVP;
        } else {
            double d3 = this.bVO - point2D.getX();
            double d4 = this.bVP - point2D.getY();
            this.a(d3, d4, f);
        }
        return true;
    }

    private void a(double d, double d2, float f) {
        this.bVQ = this.bVT.b(this.bVM, d, f);
        this.bVR = this.bVT.b(this.bVN, d2, f);
    }
}

