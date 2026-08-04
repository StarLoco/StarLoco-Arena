/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aNm
 */
public class anm_2
extends ZT {
    private static final acl_0 aU = new ym_0(new aar_2());
    private boolean bT;
    private int dZg;
    private static final int dZh = 0;
    private static final int dZi = 1;
    private static final int dZj = 2;

    public anm_2 aXs() {
        anm_2 anm_22;
        try {
            anm_22 = (anm_2)aU.adr();
            anm_22.uG = aU;
        }
        catch (Exception exception) {
            anm_22 = new anm_2();
            anm_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un Immobilization : " + exception.getMessage()));
        }
        anm_22.dZg = 0;
        anm_22.bT = false;
        return anm_22;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWl != null && !this.bWl.PR() && !this.bWl.PT()) {
            if (this.dZg == 2) {
                this.bWl.a(Lr.bra).kb(this.r);
                this.bWl.a(Lr.bra).jZ(this.r);
                this.bWl.a(Lr.brb).kb(-this.r);
                this.bWl.a(Lr.brb).ka(this.r);
                if (this.bWk == null || this.bWk.iP() != 12 && ((gn_0)this.bWl).LQ() != null) {
                    ((gn_0)this.bWl).LQ().m(or_0.VR.tI(), (short)(this.bWl.d(Lr.bqB) + this.bWl.d(Lr.bra)));
                    ((gn_0)this.bWl).LQ().m(or_0.VR.tI(), (short)(this.bWl.d(Lr.bqE) + this.bWl.d(Lr.bra)));
                    ((gn_0)this.bWl).LQ().m(or_0.VR.tI(), (short)(this.bWl.d(Lr.bqC) + this.bWl.d(Lr.bra)));
                    ((gn_0)this.bWl).LQ().m(or_0.VR.tI(), (short)(this.bWl.d(Lr.bqD) + this.bWl.d(Lr.bra)));
                }
            } else if (this.dZg == 1) {
                this.bWl.a(Lr.brb).kb(this.r);
                this.bWl.a(Lr.brb).jZ(this.r);
                this.bWl.a(Lr.bra).kb(-this.r);
                this.bWl.a(Lr.bra).ka(this.r);
            }
            this.bT = true;
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        this.r = (int)((xj_0)this.bWj).Tb()[0];
        if (this.r > 0) {
            this.dZg = 1;
        } else {
            this.dZg = 2;
            this.r = -this.r;
        }
    }

    public void aK() {
        if (this.bT) {
            if (this.dZg == 2) {
                this.bWl.a(Lr.brb).kb(this.r);
                this.bWl.a(Lr.brb).jZ(this.r);
                this.bWl.a(Lr.bra).kb(-this.r);
                this.bWl.a(Lr.bra).ka(this.r);
            } else if (this.dZg == 1) {
                this.bWl.a(Lr.bra).kb(this.r);
                this.bWl.a(Lr.bra).jZ(this.r);
                this.bWl.a(Lr.brb).kb(-this.r);
                this.bWl.a(Lr.brb).ka(this.r);
                if (this.bWk == null || this.bWk.iP() != 12 && ((gn_0)this.bWl).LQ() != null) {
                    ((gn_0)this.bWl).LQ().m(or_0.VR.tI(), (short)(this.bWl.d(Lr.bqB) + this.bWl.d(Lr.bra)));
                    ((gn_0)this.bWl).LQ().m(or_0.VR.tI(), (short)(this.bWl.d(Lr.bqE) + this.bWl.d(Lr.bra)));
                    ((gn_0)this.bWl).LQ().m(or_0.VR.tI(), (short)(this.bWl.d(Lr.bqC) + this.bWl.d(Lr.bra)));
                    ((gn_0)this.bWl).LQ().m(or_0.VR.tI(), (short)(this.bWl.d(Lr.bqD) + this.bWl.d(Lr.bra)));
                }
            }
        }
        super.aK();
    }

    public boolean aH() {
        return true;
    }

    public boolean aI() {
        return true;
    }

    public boolean aJ() {
        return true;
    }
}

