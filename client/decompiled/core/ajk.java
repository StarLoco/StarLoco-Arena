/*
 * Decompiled with CFR 0.152.
 */
public class ajk
extends ZT {
    private static final acl_0 aU = new ym_0(new dc_2());

    public ajk ayZ() {
        ajk ajk2;
        try {
            ajk2 = (ajk)aU.adr();
            ajk2.uG = aU;
        }
        catch (Exception exception) {
            ajk2 = new ajk();
            ajk2.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return ajk2;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().a(avx_0.dey);
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
    }

    public void aK() {
        if (this.bWm instanceof gn_0) {
            ((gn_0)this.bWm).PL().b(avx_0.dey);
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

