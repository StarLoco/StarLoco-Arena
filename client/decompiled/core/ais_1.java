/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ais
 */
public class ais_1
implements Du {
    private Du Ie;
    private int cyl;

    public ais_1(Du du, int n2) {
        if (du == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/isometric/DefaultIsoWorldTargetWithOffset.<init> must not be null");
        }
        this.Ie = du;
        this.cyl = n2;
    }

    public void b(double d, double d2) {
        this.Ie.b(d, d2);
    }

    public void a(double d, double d2, double d3) {
        this.Ie.a(d, d2, d3);
    }

    public float hA() {
        return this.Ie.hA();
    }

    public double getWorldX() {
        return this.Ie.getWorldX();
    }

    public double getWorldY() {
        return this.Ie.getWorldY();
    }

    public double getAltitude() {
        return this.Ie.getAltitude() + (double)this.cyl;
    }

    public int gn() {
        return this.Ie.gn();
    }

    public int go() {
        return this.Ie.go();
    }

    public short gp() {
        return (short)(this.Ie.gp() + this.cyl);
    }

    public Du qF() {
        return this.Ie;
    }

    public void a(Du du) {
        if (du == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/isometric/DefaultIsoWorldTargetWithOffset.setTarget must not be null");
        }
        this.Ie = du;
    }

    public int axW() {
        return this.cyl;
    }

    public void kQ(int n2) {
        this.cyl = n2;
    }
}

