/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ky
 */
public class ky_1
extends ZT {
    private static final acl_0 aU = new ym_0(new qm_1());
    private boolean rM;

    public ky_1 pb() {
        ky_1 ky_12;
        try {
            ky_12 = (ky_1)aU.adr();
            ky_12.uG = aU;
        }
        catch (Exception exception) {
            ky_12 = new ky_1();
            ky_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un Immobilization : " + exception.getMessage()));
        }
        ky_12.rM = true;
        return ky_12;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.rM) {
            if (this.bWm instanceof gn_0) {
                ((gn_0)this.bWm).PL().a(avx_0.deB);
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
            ((gn_0)this.bWm).PL().b(avx_0.deB);
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

