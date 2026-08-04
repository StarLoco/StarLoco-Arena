/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import org.apache.log4j.Logger;

/*
 * Renamed from hh
 */
public class hh_2
extends tp_1 {
    public static final ati_0 ve = new ati_0();
    protected boolean vf;
    protected boolean mY = false;
    protected boolean mX;
    protected String vg;
    private static final mp_0[] vh = new mp_0[]{Wz.ajg(), gp_2.Sb(), aau_0.apB(), fp.iF()};

    public hh_2() {
        this.b();
    }

    public void b() {
        ve.w(0.2f, 0.2f, 0.2f, 0.2f);
        ve.dZ(true);
        this.tJ = aPb.aYI();
        this.tJ.d(gw_2.jO());
        this.vg = "ANMInteractiveElementPath";
        this.vf = true;
        this.a(new kg_0(this));
    }

    public void j() {
        super.j();
        this.vg = "ANMInteractiveElementPath";
        this.ayl();
        this.dispose();
    }

    public void update() {
        do_1 do_12 = (do_1)this.amM;
        if (do_12 != null) {
            this.a(do_12.gn(), (double)do_12.go(), (double)do_12.gp());
            if (this.amP == Short.MIN_VALUE) {
                this.amP = (short)(do_12.gw() ? (int)do_12.gl() : Short.MIN_VALUE);
            }
            byte by = (byte)do_12.getState();
            this.a(by, do_12.gu(), do_12.gq(), do_12.L());
            this.amP = by;
            this.setVisible(do_12.isVisible());
            this.setSelectable(do_12.gs());
            this.mY = do_12.gr();
        } else {
            a.error((Object)"Cet ClientInteractiveElementView n'a pas de mod\u00e8le associ\u00e9.");
        }
    }

    public void aL(int n2) {
        super.aL(n2);
        String string = "";
        try {
            string = mu_1.rM().getString(this.vg);
            string = String.format(string, n2);
            this.lq(Integer.toString(n2));
            if (n2 != 0) {
                this.b(string, true);
            }
        }
        catch (Exception exception) {
            a.error((Object)"impossible de recuperer le path depuis la config ", (Throwable)exception);
        }
    }

    public boolean gs() {
        return this.mX;
    }

    public void setSelectable(boolean bl2) {
        this.mX = bl2;
    }

    public mp_0[] kD() {
        return vh;
    }

    public void as(String string) {
        this.vg = string;
    }

    public String kE() {
        if (this.amM != null && this.amM instanceof do_1) {
            return ((do_1)this.amM).gy();
        }
        return null;
    }

    public short ge() {
        return 28;
    }

    public void a(double d, double d2, double d3) {
        if (this.getWorldX() != d || this.getWorldY() != d2 || this.getAltitude() != d3) {
            GY.Ss().a(this.getId(), (int)d, (int)d2);
            super.a(d, d2, d3);
        }
    }

    static /* synthetic */ void a(hh_2 hh_22, ym_0 ym_02) {
        hh_22.a(ym_02);
    }

    static /* synthetic */ Logger dT() {
        return a;
    }

    static /* synthetic */ Logger kF() {
        return a;
    }

    static /* synthetic */ aPb a(hh_2 hh_22) {
        return hh_22.tJ;
    }

    static /* synthetic */ Entity3D b(hh_2 hh_22) {
        return hh_22.dLn;
    }

    static /* synthetic */ aPb c(hh_2 hh_22) {
        return hh_22.tJ;
    }

    static /* synthetic */ Entity3D d(hh_2 hh_22) {
        return hh_22.dLn;
    }
}

