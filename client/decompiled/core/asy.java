/*
 * Decompiled with CFR 0.152.
 */
public class asy
extends ZT {
    private static final acl_0 aU = new ym_0(new ahn_2());
    private boolean rM;

    public asy aFy() {
        asy asy2;
        try {
            asy2 = (asy)aU.adr();
            asy2.uG = aU;
        }
        catch (Exception exception) {
            asy2 = new asy();
            asy2.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un Petrified : " + exception.getMessage()));
        }
        asy2.rM = true;
        return asy2;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.rM) {
            if (this.bWm instanceof gn_0) {
                if (this.akf()) {
                    boolean bl3 = ((xj_0)this.bWj).iY(2) == 1.0f;
                    ZT zT = bl3 ? uc_0.a(this.bdv, (int)((xj_0)this.bWj).iY(1), this.bWm, (xj_0)this.bWj, this.bWl, this.bWk) : afi_0.b(this.bdv, (int)((xj_0)this.bWj).iY(1), this.bWm, (xj_0)this.bWj, this.bWl, this.bWk);
                    zT.akd();
                    zT.i(this.bWm);
                    zT.release();
                }
                this.b(xb_22, bl2);
                if (this.bWm instanceof gn_0) {
                    ((gn_0)this.bWm).PL().a(avx_0.dew);
                }
            }
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        int n2 = Math.min(100, (int)((xj_0)this.bWj).iY(0));
        this.rM = ou_1.he(100) <= n2;
    }

    public void aK() {
        if (this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().b(avx_0.dew);
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

