/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Cw
 */
public class cw_2
extends ZT {
    private static final acl_0 aU = new ym_0(new fl_0());
    private int aLB;

    public cw_2 JX() {
        cw_2 cw_22;
        try {
            cw_22 = (cw_2)aU.adr();
            cw_22.uG = aU;
        }
        catch (Exception exception) {
            cw_22 = new cw_2();
            cw_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un Stabilize : " + exception.getMessage()));
        }
        return cw_22;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().a(avx_0.dev);
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
    }

    public void aK() {
        if (this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().b(avx_0.dev);
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

