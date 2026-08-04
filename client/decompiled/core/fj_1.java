/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from fJ
 */
public class fj_1
extends ZT {
    private static final acl_0 aU = new ym_0(new ek_1());
    private boolean rM;

    public fj_1 jn() {
        fj_1 fj_12;
        try {
            fj_12 = (fj_1)aU.adr();
            fj_12.uG = aU;
        }
        catch (Exception exception) {
            fj_12 = new fj_1();
            fj_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un Immobilization : " + exception.getMessage()));
        }
        fj_12.rM = true;
        return fj_12;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.rM) {
            if (this.bWm instanceof gn_0) {
                ((gn_0)this.bWm).PL().a(avx_0.deA);
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
            ((gn_0)this.bWm).PL().b(avx_0.deA);
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

