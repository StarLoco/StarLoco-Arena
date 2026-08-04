/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from kv
 */
class kv_1
extends ari_0 {
    private static final double Eo = 0.005;
    private final awt_0 Ep = new awt_0();
    private float Eq = 0.1f;
    private float Er = 0.1f;
    private float Es = 1.0f;
    private double Et;
    private double Eu;
    private double Ev;

    private kv_1(qs_2 qs_22) {
        super(qs_22);
        this.Ep.w(2.0);
        this.Ep.v(0.005);
    }

    public double oV() {
        return 0.0;
    }

    public double oW() {
        return 0.0;
    }

    public final double oX() {
        return this.Et;
    }

    public final double oY() {
        return this.Eu;
    }

    public final double oZ() {
        return this.Ev;
    }

    public void bI(int n2) {
        this.Ep.mD(n2);
        if (this.aEM()) {
            this.aEL();
        }
    }

    public final void a(yg_1 yg_12, float f, float f2, float f3) {
        this.Eq = f;
        this.Er = f2;
        this.Es = f3;
        this.Ev = this.Es;
        if (yg_12 == null) {
            this.Et = 0.0;
            this.Eu = 0.0;
            this.Ep.I(0.0);
        } else {
            this.Et = yg_12.oX();
            this.Eu = yg_12.oY();
            this.Ep.I(yg_12.getAltitude());
        }
        this.aEN();
    }

    public final void a(YR yR, boolean bl2, int n2) {
        this.a(yR);
        this.Ep.K(yR.getAltitude());
        this.bI(n2);
        this.a(yR, bl2);
    }

    private void a(YR yR) {
        double d = yR.oX() * (double)this.Eq;
        double d2 = yR.oY() * (double)this.Eq;
        if (this.oX() != d || this.oY() != d2) {
            this.Et = d;
            this.Eu = d2;
            this.aEN();
        }
    }

    private void a(YR yR, boolean bl2) {
        double d = yR.oZ() - 1.0;
        double d2 = 0.005 * this.Ep.getValue();
        double d3 = (double)this.Es + (d + d2) * (double)this.Er;
        if (!bl2) {
            d3 *= 2.0;
        }
        if (this.oZ() != d3) {
            this.Ev = d3;
            this.aEN();
        }
    }

    /* synthetic */ kv_1(qs_2 qs_22, hi_2 hi_22) {
        this(qs_22);
    }
}

