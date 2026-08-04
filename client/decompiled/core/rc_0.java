/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Rc
 */
public class rc_0
extends ZT {
    private static final acl_0 aU = new ym_0(new axg_0());
    private boolean rM;

    public rc_0 adB() {
        rc_0 rc_02;
        try {
            rc_02 = (rc_0)aU.adr();
            rc_02.uG = aU;
        }
        catch (Exception exception) {
            rc_02 = new rc_0();
            rc_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un Immobilization : " + exception.getMessage()));
        }
        rc_02.rM = true;
        return rc_02;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.rM) {
            if (this.bWm instanceof gn_0) {
                ((gn_0)this.bWm).PL().a(avx_0.dex);
                this.bWm.a(Lr.bqz).set(0);
            }
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        if (((xj_0)this.bWj).iY(0) >= 0.0f) {
            int n2 = Math.min(100, (int)((xj_0)this.bWj).iY(0));
            this.rM = ou_1.he(100) <= n2;
        } else {
            this.rM = true;
        }
    }

    public void aK() {
        if (this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().b(avx_0.dex);
            this.bWm.a(Lr.bqz).aAF();
        }
        super.aK();
    }

    public boolean aH() {
        return false;
    }

    public boolean aI() {
        return true;
    }

    public boolean aJ() {
        return false;
    }
}

