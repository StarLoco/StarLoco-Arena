/*
 * Decompiled with CFR 0.152.
 */
public class TV {
    private double Et;
    private double Eu;
    private double bOU;
    private double bOV;

    public TV(double d, double d2, double d3, double d4) {
        this.Et = d;
        this.Eu = d2;
        this.bOU = d3;
        this.bOV = d4;
    }

    public double getX() {
        return this.Et;
    }

    public void p(double d) {
        this.Et = d;
    }

    public double getY() {
        return this.Eu;
    }

    public void q(double d) {
        this.Eu = d;
    }

    public double getWidth() {
        return this.bOU;
    }

    public void r(double d) {
        this.bOU = d;
    }

    public double getHeight() {
        return this.bOV;
    }

    public void s(double d) {
        this.bOV = d;
    }
}

