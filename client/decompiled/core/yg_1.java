/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from yG
 */
public class yg_1
extends ari_0
implements aCZ {
    private final float aDh;
    private final float aDi;
    private boolean aDj;
    private final awt_0 aDk = new awt_0();
    private final WR aDl = new WR();
    private float Gv = 0.0f;
    private float Gw = 0.0f;
    private Du aDm = new et_0();
    private final ArrayList aDn = new ArrayList(0);
    private boolean aDo = false;
    private final agv_0 aDp = new agv_0();

    public yg_1(aba_2 aba_22, float f, float f2) {
        super(aba_22);
        this.aDh = f;
        this.aDi = f2;
        this.aDk.w(2.1f);
        this.aDl.w(2.1f);
        this.aDk.v(0.01);
        this.aDl.v(0.01);
        this.aDk.I(this.aDh);
    }

    public yg_1(aba_2 aba_22) {
        this(aba_22, 1.0f, 3.0f);
    }

    public void bI(int n2) {
        this.ez(n2);
        double d = this.aDm.getWorldX();
        double d2 = this.aDm.getWorldY();
        double d3 = this.aDm.getAltitude();
        this.aDl.g(this.cuD.i(d, d2), this.cuD.i(d, d2, d3));
        boolean bl2 = this.aDl.b(n2, 1.0 / this.oZ());
        this.Fz();
        if (bl2) {
            this.aEN();
        } else if (!this.aDo) {
            this.Fy();
        }
        if (this.aEM()) {
            this.aEL();
        }
    }

    protected final void ez(int n2) {
        double d = this.aDk.getValue();
        if (d != this.aDk.mD(n2)) {
            this.aEN();
        }
    }

    public void a(aco_0 aco_02) {
        this.aDl.b(aco_02);
    }

    public final void Fr() {
        double d = this.aDm.getWorldX();
        double d2 = this.aDm.getWorldY();
        double d3 = this.aDm.getAltitude();
        this.aDl.e(this.cuD.i(d, d2), this.cuD.i(d, d2, d3));
        this.Fz();
        this.aEN();
        this.aDo = false;
    }

    public final void Fs() {
        this.Fr();
        this.aEL();
    }

    public double Ft() {
        return this.aDk.aJI();
    }

    public void k(double d) {
        this.aDk.K(ej_0.c(d, this.aDh, this.aDi));
    }

    public void l(double d) {
        this.aDk.I(d);
        this.aEL();
    }

    public double oZ() {
        return this.aDk.getValue();
    }

    public double oV() {
        return this.cuD.j(this.aDl.ajD(), this.aDl.ajE(), this.aDm.getAltitude());
    }

    public double oW() {
        return this.cuD.k(this.aDl.ajD(), this.aDl.ajE(), this.aDm.getAltitude());
    }

    public double Fu() {
        return this.aDm.getAltitude();
    }

    public float Fv() {
        return this.aDh;
    }

    public float Fw() {
        return this.aDi;
    }

    public Du Fx() {
        Du du = this.aDm;
        if (du == null) {
            throw new IllegalStateException("@NotNull method com/ankamagames/baseImpl/graphics/isometric/camera/IsoCamera.getTrackingTarget must not return null");
        }
        return du;
    }

    public void c(Du du) {
        if (du == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/isometric/camera/IsoCamera.setTrackingTarget must not be null");
        }
        this.aDm = du;
        this.aDo = false;
    }

    public float[] a(xw_0 xw_02) {
        return ace.cjl;
    }

    public final void a(aw_2 aw_22) {
        if (!this.aDn.contains(aw_22)) {
            this.aDn.add(aw_22);
        }
    }

    public final void b(aw_2 aw_22) {
        this.aDn.remove(aw_22);
    }

    private void Fy() {
        this.aDo = true;
        for (int j = 0; j < this.aDn.size(); ++j) {
            ((aw_2)this.aDn.get(j)).BF();
        }
    }

    public final void Z(float f) {
        if (!this.aDj) {
            this.k(this.Ft() - (double)f);
        }
    }

    public final void aW(boolean bl2) {
        this.aDj = bl2;
    }

    public void aa(float f) {
        this.aDk.w(2.1f * f);
    }

    public void ab(float f) {
        this.aDl.w(2.1f * f);
    }

    public void w(float f, float f2) {
        this.Gv = f;
        this.Gw = f2;
        this.aEN();
    }

    private void Fz() {
        double d = this.aDm.getWorldX();
        double d2 = this.aDm.getWorldY();
        this.aDp.d((float)(d - d2), (float)(-(d + d2)), 0.0f);
    }

    public agv_0 FA() {
        return this.aDp;
    }

    public agv_0 FB() {
        return agv_0.dIL;
    }

    public float FC() {
        return this.aEK();
    }

    public int zU() {
        return 0;
    }

    public double oX() {
        return this.aDl.ajD() + (double)this.Gv;
    }

    public double oY() {
        return this.aDl.ajE() + (double)this.Gw;
    }

    public double getWorldX() {
        return this.aDm.getWorldX();
    }

    public double getWorldY() {
        return this.aDm.getWorldY();
    }

    public double getAltitude() {
        return this.aDm.getAltitude();
    }

    public int gn() {
        return (int)Math.round(this.getWorldX());
    }

    public int go() {
        return (int)Math.round(this.getWorldY());
    }

    public short gp() {
        return (short)Math.round(this.getAltitude());
    }
}

