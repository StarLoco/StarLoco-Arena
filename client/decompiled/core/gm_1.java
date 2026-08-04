/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Gm
 */
public class gm_1
extends ZT {
    private static final acl_0 aU = new ym_0(new ue_1());

    public gm_1 Pw() {
        gm_1 gm_12;
        try {
            gm_12 = (gm_1)aU.adr();
            gm_12.uG = aU;
        }
        catch (Exception exception) {
            gm_12 = new gm_1();
            gm_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return gm_12;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().a(avx_0.dez);
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
    }

    public void aK() {
        if (this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().b(avx_0.dez);
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

